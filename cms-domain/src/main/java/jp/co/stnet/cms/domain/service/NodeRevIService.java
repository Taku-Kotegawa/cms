package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.AbstractRevisionEntity;
import org.springframework.data.domain.Page;

public interface NodeRevIService<T extends AbstractEntity<ID>, U extends AbstractRevisionEntity, ID> extends NodeIService<T, ID> {

    /**
     * 1件の下書き保存
     * @param entity
     * @return
     */
    T saveDraft(T entity);

    /**
     * 下書き削除
     * @param id
     * @return
     */
    T cancelDraft(ID id);

    /**
     *
     * @param input
     * @return
     */
    Page<U> findMaxRevPageByInput(DataTablesInput input);

    /**
     *　有効な最新リビジョンを取得
     * @param id
     * @return
     */
    U findByIdLatestRev(ID id);

    /**
     * リビジョン番号で取得
     * @param rid
     * @return
     */
    U findByRid(Long rid);

}
