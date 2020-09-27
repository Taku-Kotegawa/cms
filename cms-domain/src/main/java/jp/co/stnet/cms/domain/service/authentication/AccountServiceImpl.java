package jp.co.stnet.cms.domain.service.authentication;


import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.repository.authentication.AccountRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl extends AbstractNodeService<Account, String> implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    protected AccountServiceImpl() {
        super(Account.class);
    }

    @Override
    protected JpaRepository<Account, String> getRepository() {
        return accountRepository;
    }

    @Override
    public List<Account> findAllByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public Account invalid(String id) {
        Account account = findById(id);
        account.setStatus(false);
        return save(account);
    }



}
