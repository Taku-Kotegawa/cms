package jp.co.stnet.cms.domain.common.datatables;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataTablesInputDraft extends DataTablesInput {

    private Boolean draft;
}
