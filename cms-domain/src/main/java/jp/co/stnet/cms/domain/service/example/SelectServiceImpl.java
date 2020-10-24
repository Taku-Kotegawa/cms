package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.example.Select;
import jp.co.stnet.cms.domain.model.example.SelectMaxRev;
import jp.co.stnet.cms.domain.model.example.SelectRevision;
import jp.co.stnet.cms.domain.repository.NodeRevRepository;
import jp.co.stnet.cms.domain.repository.example.SelectRepository;
import jp.co.stnet.cms.domain.repository.example.SelectRevisionRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeRevService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SelectServiceImpl extends AbstractNodeRevService<Select, SelectRevision, SelectMaxRev, Long> implements SelectService {


    @Autowired
    SelectRepository selectRepository;

    @Autowired
    SelectRevisionRepository selectRevisionRepository;

    protected SelectServiceImpl() {
        super(Select.class, SelectRevision.class, SelectMaxRev.class);
    }

    @Override
    protected NodeRevRepository<SelectRevision, Long> getRevisionRepository() {
        return this.selectRevisionRepository;
    }

    @Override
    protected JpaRepository<Select, Long> getRepository() {
        return this.selectRepository;
    }

    @Override
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }
}
