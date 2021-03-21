package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.service.NodeIService;

import java.util.Optional;

/**
 * AccessCounterService
 */
public interface AccessCounterService extends NodeIService<AccessCounter, Long> {

    /**
     * URLで検索する。
     *
     * @param url URL
     * @return ヒットしたデータのリスト
     */
    Optional<AccessCounter> findByUrl(String url);

    /**
     * アクセス数をカウントアップする。
     *
     * @param url URL
     * @return カウントアップ後のアクセス数
     */
    long countUp(String url);

    /**
     * 権限が有無を確認する。(常にTrueを返す)
     *
     * @param operation 操作
     * @param loggedInUser ログインユーザ情報
     * @return true:権限あり,false:権限なし
     */
    Boolean hasAuthority(String operation, LoggedInUser loggedInUser);

}
