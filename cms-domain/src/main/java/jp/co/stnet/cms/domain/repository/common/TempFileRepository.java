package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.TempFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TempFileRepository extends JpaRepository<TempFile, String> {

    long deleteByUploadedDateLessThan(LocalDateTime deleteTo);

}
