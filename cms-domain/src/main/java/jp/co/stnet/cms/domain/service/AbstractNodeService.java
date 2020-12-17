package jp.co.stnet.cms.domain.service;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.BeanUtils;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.datatables.Column;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.Order;
import jp.co.stnet.cms.domain.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.domain.common.exception.NoChangeBusinessException;
import jp.co.stnet.cms.domain.common.exception.OptimisticLockingFailureBusinessException;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import jp.co.stnet.cms.domain.model.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.query.QueryEscapeUtils;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
@Transactional
public abstract class AbstractNodeService<T extends AbstractEntity<ID> & StatusInterface, ID> implements NodeIService<T, ID> {

    protected final Class<T> clazz;

    protected final Map<String, String> fieldMap;

    protected final Map<String, Annotation> elementCollectionFieldsMap;

    protected final Map<String, Annotation> relationFieldsMap;


    @Autowired
    Mapper beanMapper;

    @PersistenceContext
    EntityManager entityManager;

    protected AbstractNodeService(Class<T> clazz) {
        this.clazz = clazz;
        this.fieldMap = BeanUtils.getFileds(this.clazz, null);
        this.elementCollectionFieldsMap = BeanUtils.getFieldByAnnotation(clazz, null, ElementCollection.class);
        this.relationFieldsMap = BeanUtils.getFieldByAnnotation(clazz, null, OneToOne.class);
        relationFieldsMap.putAll(BeanUtils.getFieldByAnnotation(clazz, null, ManyToOne.class));
    }

    protected AbstractNodeService() {
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.fieldMap = BeanUtils.getFileds(this.clazz, null);
        this.elementCollectionFieldsMap = BeanUtils.getFieldByAnnotation(clazz, null, ElementCollection.class);
        this.relationFieldsMap = BeanUtils.getFieldByAnnotation(clazz, null, OneToOne.class);
        relationFieldsMap.putAll(BeanUtils.getFieldByAnnotation(clazz, null, ManyToOne.class));
    }

    abstract protected JpaRepository<T, ID> getRepository();

    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, id)));
    }

    /**
     * 保存前処理
     */
    protected void beforeSave(T entity, T current){};

    /**
     * 保存処理
     * @param entity 更新するエンティティ
     * @return entity 保存後のエンティティ
     */
    @Override
    public T save(T entity) {

        T currentCopy = null;

        if (!entity.isNew()) {
            T current = findById(entity.getId());
            currentCopy = beanMapper.map(current, clazz);

            // 下書き保存時、本保存から変更がなければ保存しない(ステータスを変更チェックの対象から除外)
            if (current.getStatus().equals(Status.VALID.getCodeValue())
                    && entity.getStatus().equals(Status.DRAFT.getCodeValue())) {
                currentCopy.setStatus(Status.DRAFT.getCodeValue());
            }

            if (Objects.equals(entity, currentCopy)) {
                throw new NoChangeBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2001)));
            }
        }

        try {
            beforeSave(entity, currentCopy);
            entity = getRepository().saveAndFlush(entity);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureBusinessException(ResultMessages.error().add(MessageKeys.E_CM_FW_8001));
        } catch (DataIntegrityViolationException e) {
            throw new OptimisticLockingFailureBusinessException(ResultMessages.error().add(MessageKeys.E_CM_FW_8002, e.getMessage()));
        }

        entityManager.detach(entity);
        return getRepository().findById(Objects.requireNonNull(entity.getId())).orElse(null);
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
        T entity = beanMapper.map(findById(id), clazz);
        if (!entity.getStatus().equals(Status.VALID.getCodeValue())) {
            throw new IllegalStateBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2003)));
        }
        entity.setStatus(Status.INVALID.getCodeValue());
        return save(entity);
    }

    @Override
    public T valid(ID id) {
        T entity = beanMapper.map(findById(id), clazz);
        if (!entity.getStatus().equals(Status.INVALID.getCodeValue())) {
            throw new IllegalStateBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2004)));
        }
        entity.setStatus(Status.VALID.getCodeValue());
        return save(entity);
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

    /**
     *
     *
     *
     *
     *
     *
     * @param input
     * @param count
     * @param clazz
     * @param maxRevClazz
     * @return
     */
    protected Query getJPQLQuery(DataTablesInput input, boolean count, Class clazz, Class maxRevClazz) {

        boolean hasFieldFilter = false;
        Set<String> relationEntitySet = new HashSet<>();

        StringBuilder sql = new StringBuilder();
        if (!count) {
            sql.append("SELECT distinct c FROM ");
        } else {
            sql.append("SELECT count(distinct c) FROM ");
        }

        sql.append(clazz.getSimpleName());
        sql.append(" c ");

        if (maxRevClazz != null) {
            sql.append(" INNER JOIN ");
            sql.append(maxRevClazz.getSimpleName());
            sql.append(" m ON m.rid = c.rid AND c.revType < 2 ");
        }

        // @OneToOne etc, @ElementCollection フィールドのための結合
        for (Column column : input.getColumns()) {
            String originalColumnName = column.getData();
            String convertedColumnName = convertColumnName(originalColumnName);

            if (isCollectionElement(convertedColumnName)) {
                sql.append(" LEFT JOIN c.");
                sql.append(convertedColumnName);
            }

            String relationEntityName = getRelationEntity(originalColumnName);
            if (relationEntityName != null) {
                relationEntitySet.add(relationEntityName);
            }
        }

        for (String entityName : relationEntitySet) {
            sql.append(" LEFT JOIN c.");
            sql.append(entityName);
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
                String originalColumnName = column.getData();
                String convertedColumnName = convertColumnName(originalColumnName);
                if (isDate(convertedColumnName)) {
                    sql.append("function('date_format', c.");
                    sql.append(convertedColumnName);
                    sql.append(", '%Y/%m/%d') LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else if (isDateTime(convertedColumnName)) {
                    sql.append("function('date_format', c.");
                    sql.append(convertedColumnName);
                    sql.append(", '%Y/%m/%d %T') LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else if (isNumeric(convertedColumnName)) {
                    sql.append("function('CONVERT', c.");
                    sql.append(convertedColumnName);
                    sql.append(", CHAR) LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else if (isCollection(convertedColumnName)) {
                    sql.append(convertedColumnName);
                    sql.append(" LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else if (isRelation(originalColumnName)) {
                    sql.append("c." + originalColumnName);
                    sql.append(" LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else if (isBoolean(convertedColumnName)) {
                    sql.append("function('FORMAT', c.");
                    sql.append(convertedColumnName);
                    sql.append(", 0) LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                } else {
                    sql.append("c.");
                    sql.append(convertedColumnName);
                    sql.append(" LIKE :");
                    sql.append(convertedColumnName);
                    sql.append(" ESCAPE '~'");
                }
            }
        }

        // グローバルフィルタ
        String globalSearch = input.getSearch().getValue();
        if (!hasFieldFilter && !StringUtils.isEmpty(globalSearch)) {
            sql.append(" WHERE 1 = 2 ");
            for (Column column : input.getColumns()) {
                if (column.getSearchable()) {
                    sql.append(" OR ");
                    String originalColumnName = column.getData();
                    String convertColumnName = convertColumnName(originalColumnName);
                    if (isDate(convertColumnName)) {
                        sql.append("function('date_format', c.");
                        sql.append(convertColumnName);
                        sql.append(", '%Y/%m/%d') LIKE :globalSearch ESCAPE '~'");
                    } else if (isDateTime(convertColumnName)) {
                        sql.append("function('date_format', c.");
                        sql.append(convertColumnName);
                        sql.append(", '%Y/%m/%d %T') LIKE :globalSearch ESCAPE '~'");
                    } else if (isNumeric(convertColumnName)) {
                        sql.append("function('CONVERT', c.");
                        sql.append(convertColumnName);
                        sql.append(", CHAR) LIKE :globalSearch ESCAPE '~'");
                    } else if (isCollection(convertColumnName)) {
                        sql.append(convertColumnName);
                        sql.append(" LIKE :globalSearch ESCAPE '~'");
                    } else if (isBoolean(convertColumnName)) {
                        sql.append("function('CONVERT', c.");
                        sql.append(convertColumnName);
                        sql.append(", CHAR) LIKE :globalSearch ESCAPE '~'");
                    } else if (isRelation(originalColumnName)) {
                        sql.append("c." + originalColumnName);
                        sql.append(" LIKE :globalSearch ESCAPE '~'");
                    } else {
                        sql.append("c.");
                        sql.append(convertColumnName);
                        sql.append(" LIKE :globalSearch ESCAPE '~'");
                    }
                }
            }
        }

        // Order BY
        List<String> orderClauses = new ArrayList<>();
        for (Order order : input.getOrder()) {
            String originalFiledName = input.getColumns().get(order.getColumn()).getData();
            String convertColumnName = convertColumnName(originalFiledName);

            String orderClause;
            if (isCollection(convertColumnName)) {
                orderClause = convertColumnName + " " + order.getDir();
            } else if (isRelation(originalFiledName)) {
                orderClause = "c." + originalFiledName + " " + order.getDir();
            } else {
                orderClause = "c." + convertColumnName + " " + order.getDir();
            }
            orderClauses.add(orderClause);
        }
        sql.append(" ORDER BY ");
        sql.append(StringUtils.join(orderClauses, ','));

        System.out.println(sql);

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
                // 検索文字列を%で囲む
                typedQuery.setParameter(convertColumnName(column.getData()), QueryEscapeUtils.toContainingCondition(column.getSearch().getValue()));
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

    protected boolean isCollection(String fieldName) {
        return "java.util.Collection".equals(fieldMap.get(fieldName))
                || "java.util.List".equals(fieldMap.get(fieldName));
    }

    protected boolean isBoolean(String fieldName) {
        return "java.lang.Boolean".equals(fieldMap.get(fieldName));
    }

    protected String convertColumnName(String org) {

        if (StringUtils.contains(org, ".")) {
            return StringUtils.substring(org, org.indexOf(".") + 1);
        }

        if (StringUtils.endsWith(org, "Label")) {
            return StringUtils.left(org, org.length() - 5);
        } else {
            return org;
        }
    }

    protected boolean isCollectionElement(String fieldName) {
        return elementCollectionFieldsMap.containsKey(fieldName);
    }

    protected boolean isRelation(String fieldName) {
        if (getRelationEntity(fieldName) != null) {
            return true;
        }
        return false;
    }

    protected String getRelationEntity(String fieldName) {

        String entityName = null;
        if (fieldName.contains(".")) {
            entityName = fieldName.substring(0, fieldName.indexOf("."));
        }

        if (relationFieldsMap.containsKey(entityName)) {
            return entityName;
        } else {
            return null;
        }
    }

    protected Pageable getPageable(DataTablesInput input) {
        return PageRequest.of(input.getStart() / input.getLength(), input.getLength());
    }

}
