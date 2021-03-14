package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import jp.co.stnet.cms.domain.model.example.SimpleEntityMaxRev;
import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import jp.co.stnet.cms.domain.repository.NodeRevRepository;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRepository;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRevisionRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeRevService;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class SimpleEntityServiceImpl extends AbstractNodeRevService<SimpleEntity, SimpleEntityRevision, SimpleEntityMaxRev, Long> implements SimpleEntityService {

    @Autowired
    SimpleEntityRepository simpleEntityRepository;

    @Autowired
    SimpleEntityRevisionRepository simpleEntityRevisionRepository;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    protected SimpleEntityServiceImpl() {
        super(SimpleEntity.class, SimpleEntityRevision.class, SimpleEntityMaxRev.class);
    }

    @Override
    protected JpaRepository<SimpleEntity, Long> getRepository() {
        return this.simpleEntityRepository;
    }

    @Override
    protected NodeRevRepository<SimpleEntityRevision, Long> getRevisionRepository() {
        return this.simpleEntityRevisionRepository;
    }

    @Override
    public SimpleEntity save(SimpleEntity entity) {
        SimpleEntity simpleEntity = super.save(entity);

        // 添付ファイル確定
        fileManagedSharedService.permanent(entity.getAttachedFile01Uuid());

        return simpleEntity;
    }

    @Override
    public SimpleEntity saveDraft(SimpleEntity entity) {
        SimpleEntity simpleEntity =  super.saveDraft(entity);

        // 添付ファイル確定
        fileManagedSharedService.permanent(entity.getAttachedFile01Uuid());

        return simpleEntity;
    }

    @Override
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }

    @Override
    protected boolean compareEntity(SimpleEntity entity, SimpleEntity currentCopy) {
        currentCopy.setAttachedFile01Managed(null);
        return Objects.equals(entity, currentCopy);
    }
}
