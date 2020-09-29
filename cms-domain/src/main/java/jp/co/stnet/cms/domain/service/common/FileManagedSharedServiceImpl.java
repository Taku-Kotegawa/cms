package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.common.MimeTypes;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.repository.common.FileManagedRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
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
    public byte[] getFile(Long fid) {
        String filePath = STORE_BASEDIR + findById(fid).getUri();
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, filePath));
        }
    }

    @Override
    public FileManaged findById(Long fid) {
        return fileManagedRepository.findById(fid).orElse(null);
    }

    @Override
    public FileManaged findByUuid(String uuid) {
        return fileManagedRepository.findByUuid(uuid).orElse(null);
    }

    @Override
    public FileManaged store(MultipartFile file, String fileType, Boolean status) throws IOException {

        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        if (StringUtils.isEmpty(fileType)) {
            fileType = DEFAULT_FILE_TYPE;
        }

        String uuid = UUID.randomUUID().toString();

        String storeDir = STORE_BASEDIR
                + File.separator + fileType
                + File.separator + uuid.substring(0, 1);

        mkdirs(storeDir);

        File storeFile = new File(storeDir + File.separator + uuid);
        String mimeType = "";

        mimeType = MimeTypes.getMimeType(FilenameUtils.getExtension(file.getOriginalFilename()));

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream fileStream =
                    new BufferedOutputStream(new FileOutputStream(storeFile));
            fileStream.write(bytes);
            fileStream.close();

        } catch (IOException e) {
            // 異常終了時の処理
            throw e;
        }

        return fileManagedRepository.save(
                FileManaged.builder()
                        .uuid(uuid)
                        .filetype(fileType)
                        .originalFilename(file.getOriginalFilename())
                        .filemime(mimeType)
                        .filesize(file.getSize())
                        .status(status)
                        .uri(storeFile.getAbsolutePath().substring(STORE_BASEDIR.length()))
                        .build());

    }

    @Override
    public void delete(Long fid) {
        FileManaged fileManaged = fileManagedRepository.findById(fid).orElse(null);
        if (fileManaged != null) {
            File file = new File(STORE_BASEDIR + fileManaged.getUri());
            file.delete();
        }
        fileManagedRepository.deleteById(fid);
    }

    @Override
    public void delete(String uuid) {
        FileManaged file = fileManagedRepository.findByUuidAndStatus(uuid, false).orElse(null);
        if (file != null) {
            delete(file.getFid());
        }
    }

    @Override
    public void cleanup(LocalDateTime deleteTo) {
        List<FileManaged> files = fileManagedRepository.findAllByCreatedDateLessThanAndStatus(deleteTo, false);
        for (FileManaged file : files) {
            delete(file.getFid());
        }

        //TODO 空のフォルダを削除

    }

    private File mkdirs(String filePath) {
        File uploadDir = new File(filePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }

}
