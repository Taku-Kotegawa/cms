package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// https://qiita.com/rubytomato@github/items/3c1ce7f91c576ec14423
// https://github.com/bamba6/imogene/blob/233ef0df101ac2f0a5c4f7c18e8475818e878edc/dev-libs/org.imogene.lib.common/src/main/java/org/imogene/lib/common/dao/ImogBeanDaoImpl.java

public abstract class AbstractNodeService<T, ID> implements NodeIService<T, ID> {

    protected final Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    protected AbstractNodeService(Class<T> clazz) {
        this.clazz = clazz;
    }

    abstract protected JpaRepository<T, ID> getRepository();

    @Override
    public abstract T invalid(ID id);

    @Override
    public Page<T> findPageByInput(DataTablesInput input) {

        List<T> content = findAllByInput(input);
        Pageable pageable = null;
        long total = 99999L;


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = cb.createQuery(clazz);

        Root<T> root = criteriaQuery.from(clazz);

        criteriaQuery.select(root);


        criteriaQuery.orderBy();



//        query.where(DaoUtil.<T> toPredicate(criterion, builder, root));
//        if (property == null) {
//            property = "modified";
//        }
//        Order o = asc ? builder.asc(DaoUtil.getCascadeRoot(root, property)) : builder.desc(DaoUtil.getCascadeRoot(root,
//                property));
//        query.orderBy(o, builder.desc(root.<String> get("id")));

        return new PageImpl<T>(entityManager.createQuery(criteriaQuery).getResultList(), pageable, total);

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
