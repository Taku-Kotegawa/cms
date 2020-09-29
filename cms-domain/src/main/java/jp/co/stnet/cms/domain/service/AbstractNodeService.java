package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.BeanUtils;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.datatables.Column;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.Order;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractNodeService<T, ID> implements NodeIService<T, ID> {

    protected final Class<T> clazz;

    protected final Map<String, String> fieldMap;

    @PersistenceContext
    EntityManager entityManager;

    protected AbstractNodeService(Class<T> clazz) {
        this.clazz = clazz;
        this.fieldMap = BeanUtils.getFileds(clazz, "");
    }

    abstract protected JpaRepository<T, ID> getRepository();

    @Override
    public abstract T invalid(ID id);

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
                    sql.append("c." + column.getData());
                    sql.append(", '%Y/%m/%d')");
                } else if (isDateTime(column.getData())) {
                    sql.append("function('date_format', ");
                    sql.append("c." + column.getData());
                    sql.append(", '%Y/%m/%d %T')");
                } else {
                    sql.append("c." + column.getData());
                }

                sql.append(" LIKE '");
                sql.append(QueryEscapeUtils.toContainingCondition(column.getSearch().getValue()));
                sql.append("' ESCAPE '~'");
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
                        sql.append("c." + column.getData());
                        sql.append(", '%Y/%m/%d')");
                    } else if (isDateTime(column.getData())) {
                        sql.append("function('date_format', ");
                        sql.append("c." + column.getData());
                        sql.append(", '%Y/%m/%d %T')");
                    } else {
                        sql.append("c." + column.getData());
                    }
                    sql.append(" LIKE '");
                    sql.append(QueryEscapeUtils.toContainingCondition(globalSearch));
                    sql.append("' ESCAPE '~'");
                }
            }
        }

        // Order BY
        List<String> orderClause = new ArrayList<>();
        for (Order order : input.getOrder()) {
            orderClause.add(
                    "c." + input.getColumns().get(order.getColumn()).getData() + " " + order.getDir());
        }
        sql.append(" ORDER BY ");
        sql.append(StringUtils.join(orderClause, ','));


        if (count == false) {
            return entityManager.createQuery(sql.toString(), clazz);
        } else {
            return entityManager.createQuery(sql.toString(), Long.class);
        }

    }

    protected boolean isDate(String fieldName) {
        return "java.time.LocalDate".equals(fieldMap.get(fieldName));
    }

    protected boolean isDateTime(String fieldName) {
        return "java.time.LocalDateTime".equals(fieldMap.get(fieldName));
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

    protected Pageable getPageable(DataTablesInput input) {
        return PageRequest.of(input.getStart() / input.getLength(), input.getLength());
    }


    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, id)));
    }


    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(Iterable<T> entities) {
        getRepository().deleteAll(entities);
    }


}
