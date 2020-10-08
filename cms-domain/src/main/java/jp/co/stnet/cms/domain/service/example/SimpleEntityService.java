package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import jp.co.stnet.cms.domain.service.NodeIService;
import org.springframework.data.domain.Page;

public interface SimpleEntityService extends NodeIService<SimpleEntity, Long> {
    Page<SimpleEntityRevision> findMaxRevPageByInput(DataTablesInput input);
}
