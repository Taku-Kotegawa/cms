package jp.co.stnet.cms.domain.service.authentication;


import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.repository.authentication.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> findByDatatablesInput(DataTablesInput input) {
        return null;
    }

    @Override
    public Long countByDatatablesInput(DataTablesInput input) {
        return null;
    }

    @Override
    public Long initMany(int num, int loopCount) {
        return null;
    }
}
