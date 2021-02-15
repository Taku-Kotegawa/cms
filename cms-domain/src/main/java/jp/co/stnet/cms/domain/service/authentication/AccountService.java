package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.service.NodeIService;

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
     * @param username
     * @return
     */
    public String generateApiKey(String username);

    /**
     * API KEY を削除する。
     * @param username
     * @return
     */
    public Account deleteApiKey(String username);


    /**
     * API KEY を保存する。
     * @param username
     * @return
     */
    public Account saveApiKey(String username);


    public Account findByApiKey(String apiKey);

}
