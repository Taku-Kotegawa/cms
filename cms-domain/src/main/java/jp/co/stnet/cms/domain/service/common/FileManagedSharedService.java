package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagedSharedService {

    FileManaged store(MultipartFile file, String fileType, Boolean status);

    void delete(FileManaged fileManaged);

}
