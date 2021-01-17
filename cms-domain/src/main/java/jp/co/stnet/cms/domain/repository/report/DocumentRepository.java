package jp.co.stnet.cms.domain.repository.report;

import jp.co.stnet.cms.domain.model.report.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
