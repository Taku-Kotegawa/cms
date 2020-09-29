package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.service.NodeIService;
import org.springframework.security.access.AccessDeniedException;

public interface AccountService extends NodeIService<Account, String> {

    /**
     * 権限チェックを行う。
     * @param Operation 操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @return true=操作する権限を持つ, false=操作する権限なし
     * @throws AccessDeniedException @PostAuthorizeを用いてfalse時にスロー
     */
    Boolean hasAuthority(String Operation, LoggedInUser loggedInUser);


    /**
     * 画像とアカウント情報を一緒に保存する。
     * @param entity
     * @param ImageUuid
     * @return
     */
    public Account save(Account entity, String ImageUuid);

}
