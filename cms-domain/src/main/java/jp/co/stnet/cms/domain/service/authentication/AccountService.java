package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.service.NodeIService;

/**
 * Accountサービス.
 */
public interface AccountService extends NodeIService<Account, String> {

//    /**
//     * 画像とアカウント情報を一緒に保存する。
//     * @param entity
//     * @param ImageUuid
//     * @return
//     */
//    public Account save(Account entity, String ImageUuid);

    /**
     * API KEY を発行、保存する。
     *
     * @param username ユーザ名
     * @return API KEY
     */
    String generateApiKey(String username);

    /**
     * API KEY を削除する。
     *
     * @param username ユーザ名
     * @return Account
     */
    Account deleteApiKey(String username);

    /**
     * API KEY を保存する。
     *
     * @param username ユーザ名
     * @return Account
     */
    Account saveApiKey(String username);

    /**
     * 　API KEY で検索する。
     *
     * @param apiKey API KEY
     * @return Account
     */
    Account findByApiKey(String apiKey);

}
