package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.AbstractRevisionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public abstract class AbstractNodeRevService<T extends AbstractEntity<ID>, U extends AbstractRevisionEntity, ID> extends AbstractNodeService<T, ID> {

    private final Class<U> revClass;

    protected AbstractNodeRevService(Class<T> clazz, Class<U> revClass) {
        super(clazz);
        this.revClass = revClass;
    }

    abstract protected JpaRepository<U, ID> getRevisionRepository();

    @Override
    public T save(T entity) {
        Boolean isNew = entity.isNew();
        T saved = super.save(entity);
        U rev = beanMapper.map(saved, revClass);
        rev.setRevType(isNew ? 0 : 1);
        getRevisionRepository().saveAndFlush(rev);
        return saved;
    }

    @Override
    public void delete(ID id) {
        T before = findById(id);
        U rev = beanMapper.map(before, revClass);
        rev.setRevType(2);
        getRevisionRepository().saveAndFlush(rev);
        super.delete(id);
    }

}
