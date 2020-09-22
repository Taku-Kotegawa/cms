package jp.co.stnet.cms.domain.service.common;

import com.google.common.io.Files;
import jp.co.stnet.cms.domain.common.MimeTypes;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.repository.common.FileManagedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class FileManagedSharedServiceImpl implements FileManagedSharedService {

    @Autowired
    FileManagedRepository fileManagedRepository;

    @Value("${file.store.basedir}")
    private String STORE_BASEDIR;

    @Value("${file.store.default_file_type}")
    private String DEFAULT_FILE_TYPE;

    @Override
    public FileManaged store(MultipartFile file, String fileType, Boolean status) {

        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        if (StringUtils.isEmpty(fileType)) {
            fileType = DEFAULT_FILE_TYPE;
        }

        String uuid = UUID.randomUUID().toString();

        String storeDir = STORE_BASEDIR
                + File.separator + fileType
                + File.separator + uuid.substring(0, 2);

        mkdirs(storeDir);

        File storeFile = new File(storeDir + File.separator + uuid);
        String mimeType = "";

        mimeType = MimeTypes.getMimeType(Files.getFileExtension(file.getOriginalFilename()));


        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream fileStream =
                    new BufferedOutputStream(new FileOutputStream(storeFile));
            fileStream.write(bytes);
            fileStream.close();

//            mimeType = Files.probeContentType(storeFile.toPath());
//            Tika tika = new Tika();
//            mimeType = tika.detect(storeFile);

        } catch (Exception e) {
            // 異常終了時の処理
            e.printStackTrace();
        }

        return fileManagedRepository.save(
                FileManaged.builder()
                        .uuid(uuid)
                        .originalFilename(file.getOriginalFilename())
                        .filemime(mimeType)
                        .filesize(file.getSize())
                        .status(status)
                        .uri(storeFile.getAbsolutePath().substring(STORE_BASEDIR.length(), storeFile.getAbsolutePath().length()))
                        .build());

    }


    private File mkdirs(String filePath) {
        File uploadDir = new File(filePath);
        // 既に存在する場合はプレフィックスをつける
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }


    @Override
    public void delete(FileManaged fileManaged) {
    }
}
