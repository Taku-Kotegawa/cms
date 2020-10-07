package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.service.NodeIService;

public interface AccountService extends NodeIService<Account, String> {

    /**
     * 画像とアカウント情報を一緒に保存する。
     * @param entity
     * @param ImageUuid
     * @return
     */
    public Account save(Account entity, String ImageUuid);

}
