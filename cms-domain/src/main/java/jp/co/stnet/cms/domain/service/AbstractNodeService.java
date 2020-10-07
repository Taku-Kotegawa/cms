package jp.co.stnet.cms.domain.service;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.BeanUtils;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.datatables.Column;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.Order;
import jp.co.stnet.cms.domain.common.exception.NoChangeBusinessException;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.query.QueryEscapeUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Slf4j
@Transactional
public abstract class AbstractNodeService<T extends AbstractEntity<ID>, ID> implements NodeIService<T, ID> {

    protected final Class<T> clazz;
    protected final Map<String, String> fieldMap;
    @Autowired
    Mapper beanMapper;
    @PersistenceContext
    EntityManager entityManager;

    protected AbstractNodeService(Class<T> clazz) {
        this.clazz = clazz;
        this.fieldMap = BeanUtils.getFileds(clazz, "");
    }

    abstract protected JpaRepository<T, ID> getRepository();

    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, id)));
    }

    @Override
    public T save(T entity) {
        if (entity.getId() != null) {
            T before = getRepository().getOne(entity.getId());
            // beanMapperを経由するのはPersistentBag対策
            if (Objects.equals(entity, beanMapper.map(before, clazz))) {
                throw new NoChangeBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2001)));
            }
        }

        // 自動更新項目(createdBy, createdDate, lastModifiedBy, lastModifiedDate)を取得するため、DB更新(flush) + キャッシュクリア(detach)
        entityManager.detach(getRepository().saveAndFlush(entity));
        return getRepository().findById(entity.getId()).orElse(null);
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        List<T> saved = new ArrayList<>();
        Iterator<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            saved.add(save(iterator.next()));
        }
        return saved;
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(Iterable<T> entities) {
        Iterator<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            delete(iterator.next().getId());
        }
    }


    @Override
    public T invalid(ID id) {
        T before = findById(id);
        before.setStatus(Status.INVALID.getCodeValue());
        return save(before);
    }

    @Override
    public Page<T> findPageByInput(DataTablesInput input) {

        return new PageImpl<T>(
                getJPQLQuery(input, false).getResultList(),
                getPageable(input),
                getJPQLQuery(input, true).getFirstResult());

    }

    protected Query getJPQLQuery(DataTablesInput input, boolean count) {

        boolean hasFieldFilter = false;

        StringBuffer sql = new StringBuffer();
        if (count == false) {
            sql.append("SELECT c FROM ");
        } else {
            sql.append("SELECT count(c) FROM ");
        }

        sql.append(clazz.getSimpleName());
        sql.append(" c ");

        // フィールドフィルタ
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                if (hasFieldFilter == true) {
                    sql.append(" AND ");
                } else {
                    sql.append(" WHERE ");
                    hasFieldFilter = true;
                }
                if (isDate(column.getData())) {
                    sql.append("function('date_format', ");
                    sql.append("c." + convColumnName(column.getData()));
                    sql.append(", '%Y/%m/%d')");
                } else if (isDateTime(column.getData())) {
                    sql.append("function('date_format', ");
                    sql.append("c." + convColumnName((column.getData())));
                    sql.append(", '%Y/%m/%d %T')");

                } else if (isNumeric(convColumnName(column.getData()))) {
                    sql.append("function('format', ");
                    sql.append("c." + convColumnName(column.getData()));
                    sql.append(", 0)");
                } else {
                    sql.append("c." + convColumnName(column.getData()));
                }

                sql.append(" LIKE :");
                sql.append(convColumnName(column.getData()));
//                sql.append(QueryEscapeUtils.toContainingCondition(column.getSearch().getValue()));
                sql.append(" ESCAPE '~'");
            }
        }

        // グローバルフィルタ
        String globalSearch = input.getSearch().getValue();
        if (hasFieldFilter == false && !StringUtils.isEmpty(globalSearch)) {
            sql.append(" WHERE 1 = 2 ");
            for (Column column : input.getColumns()) {
                if (column.getSearchable()) {
                    sql.append(" OR ");
                    if (isDate(column.getData())) {
                        sql.append("function('date_format', ");
                        sql.append("c." + convColumnName(column.getData()));
                        sql.append(", '%Y/%m/%d')");
                    } else if (isDateTime(column.getData())) {
                        sql.append("function('date_format', ");
                        sql.append("c." + convColumnName(column.getData()));
                        sql.append(", '%Y/%m/%d %T')");
                    } else {
                        sql.append("c." + convColumnName(column.getData()));
                    }
                    sql.append(" LIKE :globalSearch ESCAPE '~'");
//                    sql.append(substringLabel(column.getData()));
//                    sql.append(QueryEscapeUtils.toContainingCondition(globalSearch));
//                    sql.append(" ESCAPE '~'");
                }
            }
        }

        // Order BY
        List<String> orderClause = new ArrayList<>();
        for (Order order : input.getOrder()) {
            orderClause.add(
                    "c." + convColumnName(input.getColumns().get(order.getColumn()).getData()) + " " + order.getDir());
        }
        sql.append(" ORDER BY ");
        sql.append(StringUtils.join(orderClause, ','));


        TypedQuery typedQuery;
        if (count == false) {
            System.out.println(sql.toString());
            typedQuery = entityManager.createQuery(sql.toString(), clazz);
        } else {
            typedQuery = entityManager.createQuery(sql.toString(), Long.class);
        }

        // フィールドフィルタ
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                typedQuery.setParameter(convColumnName(column.getData()), QueryEscapeUtils.toContainingCondition(column.getSearch().getValue()));
            }
        }

        // グローバルフィルタ
        if (hasFieldFilter == false && !StringUtils.isEmpty(globalSearch)) {
            typedQuery.setParameter("globalSearch", QueryEscapeUtils.toContainingCondition(globalSearch));
        }

        return typedQuery;

    }

    protected boolean isDate(String fieldName) {
        return "java.time.LocalDate".equals(fieldMap.get(fieldName));
    }

    protected boolean isDateTime(String fieldName) {
        return "java.time.LocalDateTime".equals(fieldMap.get(fieldName));
    }

    protected boolean isNumeric(String fieldName) {
        return "java.lang.Integer".equals(fieldMap.get(fieldName))
                || "java.lang.Long".equals(fieldMap.get(fieldName))
                || "java.lang.BigDecimal".equals(fieldMap.get(fieldName))
                || "java.lang.Float".equals(fieldMap.get(fieldName))
                || "java.lang.Double".equals(fieldMap.get(fieldName));
    }

//    protected TypedQuery<T> getQuery(DataTablesInput input) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> q = cb.createQuery(clazz);
//        Root<T> r = q.from(clazz);
//        q.select(r);
//
//        q.where(
//                cb.and(
//                        cb.like(r.get("firstName"), "Taro")
//                )
//        );
//
//
//        q.orderBy(
//                cb.desc(r.get("firstName"))
//        );
//
//        return entityManager.createQuery(q);
//    }


    private String convColumnName(String org) {
        if (StringUtils.endsWith(org, "Label")) {
            return StringUtils.left(org, org.length() - 5);
        } else {
            return org;
        }

    }

    protected Pageable getPageable(DataTablesInput input) {
        return PageRequest.of(input.getStart() / input.getLength(), input.getLength());
    }

}
