package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;

import java.time.LocalDateTime;

public interface EmailChangeService {

    /**
     * メール変更を仮受付しトークンを発行し、エンティティを保存しつつ、メールを送る。
     *
     * @param username ユーザ名
     * @param mail     新メールアドレス
     * @return トークン
     */
    String createAndSendMailChangeRequest(String username, String mail);

    /**
     * 保存しているメール変更要求をトークンで取り出す
     *
     * @param token
     * @return
     */
    EmailChangeRequest findOne(String token);

    /**
     * ユーザアカウントのメールアドレスを変更する。(トークンチェックあり)
     *
     * @param token
     * @Exception トークンが存在しない、トークンの有効期限が超過
     */
    void changeEmail(String token);

    /**
     * 有効期限を超えるメール変更要求を削除する。(スケジュール機能で定期的に実行される想定)
     *
     * @param date 現在の日付
     */
    void removeExpired(LocalDateTime date);

}
