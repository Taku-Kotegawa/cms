package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.AbstractMaxRevEntity;
import jp.co.stnet.cms.domain.model.AbstractRevisionEntity;
import jp.co.stnet.cms.domain.model.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public abstract class AbstractNodeRevService<T extends AbstractEntity<ID>, U extends AbstractRevisionEntity, V extends AbstractMaxRevEntity<ID>, ID> extends AbstractNodeService<T, ID> {

    private final Class<U> revClass;

    private final Class<V> maxRevClass;

    protected AbstractNodeRevService(Class<T> clazz, Class<U> revClass, Class<V> maxRevClass) {
        super(clazz);
        this.revClass = revClass;
        this.maxRevClass = maxRevClass;
    }

    abstract protected JpaRepository<U, ID> getRevisionRepository();

    @Override
    public T save(T entity) {
        Boolean isNew = entity.isNew();
        T saved = super.save(entity);
        U rev = beanMapper.map(saved, revClass);
        rev.setRevType(isNew ? 0 : 1);
        getRevisionRepository().saveAndFlush(rev);
        saveMaxRev(saved.getId(), rev.getRid());

        return saved;
    }

    public T cancelDraft(T entity) {
        T current = findById(entity.getId());
        if (current.getStatus().equals(Status.DRAFT.getCodeValue())) {
            throw new IllegalStateException("ID: " + entity.getId());
        }
        U before = findMaxRevById(entity.getId());
        beanMapper.map(before, entity);
        return save(entity);
    }

    @Override
    public void delete(ID id) {
        T current = findById(id);
        U rev = beanMapper.map(current, revClass);
        rev.setRevType(2);
        getRevisionRepository().saveAndFlush(rev);
        saveMaxRev(id, rev.getRid());
        super.delete(id);
    }

    public U findMaxRevById(ID id) {
        V max = entityManager.find(maxRevClass, id);
        return getRevisionRepository().findById(max.getId()).orElse(null);
    }

    public void saveMaxRev(ID id, Long rid) {
        V max = beanMapper.map(new Object(), maxRevClass);
        max.setId(id);
        max.setRid(rid);
        entityManager.merge(max);
    }

    public Page<U> findMaxRevPageByInput(DataTablesInput input) {

        return new PageImpl<U>(
                getJPQLQuery(input, false, revClass, maxRevClass).getResultList(),
                getPageable(input),
                getJPQLQuery(input, true, revClass, maxRevClass).getFirstResult());

    }


}
