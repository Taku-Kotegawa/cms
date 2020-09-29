package jp.co.stnet.cms.domain.service;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import org.springframework.data.domain.Page;

public interface NodeIService<T, ID> {


    /**
     * IDで検索
     */
    T findById(ID id);


    /**
     * DataTables用の検索(Page)
     */
    Page<T> findPageByInput(DataTablesInput input);

    /**
     * １件の保存
     */
    T save(T entity);

    /**
     * 複数件の保存
     */
    Iterable<T> save(Iterable<T> entities);

    /**
     * １件の削除
     */
    void delete(ID id);

    /**
     * 複数件の削除
     */
    void delete(Iterable<T> entities);

    /**
     * １件の無効化
     */
    T invalid(ID id);

}
