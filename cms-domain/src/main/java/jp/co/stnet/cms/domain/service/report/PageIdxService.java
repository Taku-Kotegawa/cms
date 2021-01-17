package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.service.NodeIService;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.data.domain.Page;

public interface PageIdxService extends NodeIService<PageIdx, Long> {


    SearchResult<PageIdx> searchByInput(DataTablesInput input);

}
