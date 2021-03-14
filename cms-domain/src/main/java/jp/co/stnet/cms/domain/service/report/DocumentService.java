package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.service.NodeIService;
import org.hibernate.search.engine.search.query.SearchResult;

import java.util.List;

public interface DocumentService extends NodeIService<Document, Long> {

    SearchResult<Document> searchByAttachedFile(String attachedFile);

    List<Document> findByTitle(String title);
}
