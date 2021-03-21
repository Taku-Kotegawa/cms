package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.Variable;

import java.util.List;

/**
 * VariableSharedService
 */
public interface VariableSharedService {

    /**
     * タイプで検索する。
     *
     * @param type タイプ
     * @return ヒットしたデータのリスト
     */
    List<Variable> findAllByType(String type);

    /**
     * タイプとコードで検索する。
     *
     * @param type タイプ
     * @param code コード
     * @return ヒットしたデータのリスト
     */
    List<Variable> findAllByTypeAndCode(String type, String code);

}
