package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.SelectRevision;
import jp.co.stnet.cms.domain.repository.NodeRevRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRevisionRepository extends NodeRevRepository<SelectRevision, Long> {

    @Query("SELECT c FROM SelectRevision c INNER JOIN SelectMaxRev m ON m.rid = c.rid AND c.revType < 2 WHERE m.id = :id")
    SelectRevision findByIdLatestRev(@Param("id") Long id);

}
