package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.TempFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Deprecated
@Repository
public interface TempFileRepository extends JpaRepository<TempFile, String> {

    long deleteByUploadedDateLessThan(LocalDateTime deleteTo);

}
