package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.authentication.Account;

import java.util.List;

public interface AccountService {

    List<Account> findByDatatablesInput(DataTablesInput input);

    Long countByDatatablesInput(DataTablesInput input);

    Long initMany(int num, int loopCount);
}
