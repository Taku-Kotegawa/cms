package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import jp.co.stnet.cms.domain.model.example.SimpleEntityMaxRev;
import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import jp.co.stnet.cms.domain.repository.NodeRevRepository;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRepository;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRevisionRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeRevService;
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
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }

    @Override
    protected boolean compareEntity(SimpleEntity entity, SimpleEntity currentCopy) {
        currentCopy.setAttachedFile01Managed(null);
        return Objects.equals(entity, currentCopy);
    }
}
