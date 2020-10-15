package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import jp.co.stnet.cms.domain.repository.NodeRevRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleEntityRevisionRepository extends NodeRevRepository<SimpleEntityRevision, Long> {

    @Query("SELECT c FROM SimpleEntityRevision c INNER JOIN SimpleEntityMaxRev m ON m.rid = c.rid AND c.revType < 2 WHERE m.id = :id")
    SimpleEntityRevision findByIdLatestRev(@Param("id") Long id);

}
