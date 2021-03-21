package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Variableリポジトリ.
 */
@Repository
public interface VariableRepository extends JpaRepository<Variable, Long> {

    /**
     * タイプで検索する。
     *
     * @param type タイプ
     * @return ヒットしたデータのリスト
     */
    List<Variable> findByType(String type);

    /**
     * タイプとコードで検索する。
     *
     * @param type タイプ
     * @param code コード
     * @return ヒットしたデータのリスト
     */
    List<Variable> findAllByTypeAndCode(String type, String code);

}
