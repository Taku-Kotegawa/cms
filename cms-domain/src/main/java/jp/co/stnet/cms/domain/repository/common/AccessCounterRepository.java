package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessCounterRepository extends JpaRepository<AccessCounter, Long> {

    Optional<AccessCounter> findByUrl(String url);

}