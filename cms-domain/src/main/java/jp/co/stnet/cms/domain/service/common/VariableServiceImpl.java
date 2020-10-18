package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.repository.common.VariableRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class VariableServiceImpl extends AbstractNodeService<Variable,  Long> implements VariableService {

    @Autowired
    VariableRepository variableRepository;

    @Override
    protected JpaRepository<Variable, Long> getRepository() {
        return variableRepository;
    }

    @Override
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {

        // TODO 権限チェックの追加
        return true;

    }
}
