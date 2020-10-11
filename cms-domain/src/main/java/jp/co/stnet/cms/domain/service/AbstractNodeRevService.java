package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.AbstractMaxRevEntity;
import jp.co.stnet.cms.domain.model.AbstractRevisionEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import jp.co.stnet.cms.domain.model.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public abstract class AbstractNodeRevService<T extends AbstractEntity<ID> & StatusInterface, U extends AbstractRevisionEntity, V extends AbstractMaxRevEntity<ID>, ID> extends AbstractNodeService<T, ID> implements NodeRevIService<T, U, ID> {

    private final Class<U> revClass;

    private final Class<V> maxRevClass;

    protected AbstractNodeRevService(Class<T> clazz, Class<U> revClass, Class<V> maxRevClass) {
        super(clazz);
        this.revClass = revClass;
        this.maxRevClass = maxRevClass;
    }

    abstract protected JpaRepository<U, Long> getRevisionRepository();

    @Override
    public T save(T entity) {
        Boolean isNew = entity.isNew();
        if (entity.getStatus() == null) {
            entity.setStatus(Status.VALID.getCodeValue());
        }
        T saved = super.save(entity);

        // 本保存はリビジョンを保存する
        U rev = beanMapper.map(saved, revClass);
        rev.setRevType(isNew ? 0 : 1);
        getRevisionRepository().saveAndFlush(rev);
        saveMaxRev(saved.getId(), rev.getRid());

        return saved;
    }

    @Override
    public T saveDraft(T entity) {
        Boolean isNew = entity.isNew();
        entity.setStatus(Status.DRAFT.getCodeValue());
        return super.save(entity);
        // 下書き保存はリビジョンを保存しない
    }

    @Override
    public T cancelDraft(ID id) {
        T current = findById(id);
        if (!current.getStatus().equals(Status.DRAFT.getCodeValue())) {
            throw new IllegalStateException("ID: " + id);
        }
        U before = findMaxRevById(id);
        if (before == null) {
            super.delete(id);
            return null;
        } else {
            before.setVersion(current.getVersion());
            return getRepository().saveAndFlush(beanMapper.map(before, clazz));
        }
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
        if (max == null) { return null; }
        return getRevisionRepository().findById(max.getRid()).orElse(null);
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
                (Long) getJPQLQuery(input, true, revClass, maxRevClass).getSingleResult());
    }


}
