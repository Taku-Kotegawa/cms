package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, Long> {
}
