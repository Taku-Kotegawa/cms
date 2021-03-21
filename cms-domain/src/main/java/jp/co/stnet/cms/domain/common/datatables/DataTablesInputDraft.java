package jp.co.stnet.cms.domain.common.datatables;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DataTables(Server-Side)からのリクエストを格納するクラス(リクエスト全体+下書きフラグ)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataTablesInputDraft extends DataTablesInput {

    /**
     * 下書きを含むフラグ
     */
    private Boolean draft;

}
