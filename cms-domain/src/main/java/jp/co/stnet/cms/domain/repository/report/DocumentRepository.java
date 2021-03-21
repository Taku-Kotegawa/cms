package jp.co.stnet.cms.domain.repository.report;

import jp.co.stnet.cms.domain.model.report.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * タイトルで検索する。
     *
     * @param title タイトル
     * @return ヒットしたデータのリスト
     */
    List<Document> findByTitle(String title);

}
