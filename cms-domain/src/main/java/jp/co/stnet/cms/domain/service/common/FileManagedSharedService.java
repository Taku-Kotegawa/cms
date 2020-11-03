package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public interface FileManagedSharedService {

    byte[] getFile(Long fid);

    FileManaged findById(Long fid);

    FileManaged findByUuid(String uuid);

    FileManaged store(MultipartFile file, String fileType, Boolean status) throws IOException;

    void delete(Long id);

    void delete(String uuid);

    void cleanup(LocalDateTime deleteTo);

    String getFileStoreBaseDir();

}
