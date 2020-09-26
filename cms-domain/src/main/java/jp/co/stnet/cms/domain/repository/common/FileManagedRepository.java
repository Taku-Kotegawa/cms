package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileManagedRepository extends JpaRepository<FileManaged, Long>{

    Optional<FileManaged> findByUuid(String uuid);

    Optional<FileManaged> findByUuidAndStatus(String uuid, Boolean status);

    List<FileManaged> findAllByCreatedDateLessThanAndStatus(LocalDateTime deleteTo, Boolean status);

}
