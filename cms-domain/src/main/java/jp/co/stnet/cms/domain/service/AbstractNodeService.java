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
import jp.co.stnet.cms.domain.model.StatusInterface;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Transactional
public abstract class AbstractNodeService<T extends AbstractEntity<ID> & StatusInterface, ID> implements NodeIService<T, ID>  {

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
        T before;
        if (entity.getId() != null) {
            before = getRepository().getOne(entity.getId());
            if (Objects.equals(entity, beanMapper.map(before, clazz))) {
                throw new NoChangeBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2001)));
            }
        }

        // 自動更新項目(createdBy, createdDate, lastModifiedBy, lastModifiedDate)を取得するため、DB更新(flush) + キャッシュクリア(detach)
//        before = beanMapper.map(entity, clazz);
        entity = getRepository().saveAndFlush(entity);
        entityManager.detach(entity);
        return getRepository().findById(entity.getId()).orElse(null);
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        List<T> saved = new ArrayList<>();
        for (T entity : entities) {
            saved.add(save(entity));
        }
        return saved;
    }

    @Override
    public T invalid(ID id) {
        T before = findById(id);
        if (!before.getStatus().equals(Status.VALID.getCodeValue())) {
            throw new IllegalStateException("ID: " + id.toString());
        }
        before.setStatus(Status.INVALID.getCodeValue());
        return save(before);
    }

    @Override
    public T valid(ID id) {
        T before = findById(id);
        if (!before.getStatus().equals(Status.INVALID.getCodeValue())) {
            throw new IllegalStateException("ID: " + id.toString());
        }
        before.setStatus(Status.VALID.getCodeValue());
        return save(before);
    }


    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(Iterable<T> entities) {
        for (T entity : entities) {
            delete(entity.getId());
        }
    }

    @Override
    public Page<T> findPageByInput(DataTablesInput input) {
        return new PageImpl<T>(
                getJPQLQuery(input, false, clazz).getResultList(),
                getPageable(input),
                (Long) getJPQLQuery(input, true, clazz).getSingleResult());
    }

    protected Query getJPQLQuery(DataTablesInput input, boolean count, Class clazz) {
        return getJPQLQuery(input, count, clazz, null);
    }

    protected Query getJPQLQuery(DataTablesInput input, boolean count, Class clazz, Class maxRevClazz) {

        boolean hasFieldFilter = false;

        StringBuilder sql = new StringBuilder();
        if (!count) {
            sql.append("SELECT c FROM ");
        } else {
            sql.append("SELECT count(c) FROM ");
        }

        sql.append(clazz.getSimpleName());
        sql.append(" c ");

        if (maxRevClazz != null) {
            sql.append(" INNER JOIN ");
            sql.append(maxRevClazz.getSimpleName());
            sql.append(" m ON m.rid = c.rid AND c.revType < 2 ");
        }

        // フィールドフィルタ
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                if (!hasFieldFilter) {
                    sql.append(" WHERE ");
                    hasFieldFilter = true;
                } else {
                    sql.append(" AND ");
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
        if (!hasFieldFilter && !StringUtils.isEmpty(globalSearch)) {
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

        // Limit


        TypedQuery typedQuery;
        if (!count) {
            typedQuery = entityManager.createQuery(sql.toString(), clazz);
            typedQuery.setFirstResult(input.getStart());
            typedQuery.setMaxResults(input.getLength());
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
        if (!hasFieldFilter && !StringUtils.isEmpty(globalSearch)) {
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
