package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.common.MimeTypes;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.FileStatus;
import jp.co.stnet.cms.domain.repository.common.FileManagedRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

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
    public byte[] getFile(String uuid) {
        return getFile(findByUuid(uuid).getFid());
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

        String storeFilePath = storeDir + File.separator + uuid;

        mkdirs(storeDir);

        File storeFile = new File(storeFilePath);
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

        FileManaged fileManaged = FileManaged.builder()
                .uuid(uuid)
                .filetype(fileType)
                .originalFilename(file.getOriginalFilename())
                .filemime(mimeType)
                .filesize(file.getSize())
                .status(FileStatus.TEMPORARY.getCodeValue())
                .uri(storeFilePath.substring(STORE_BASEDIR.length()).replace('\\', '/'))
                .build();

        // コマンドランチャーから実行した場合に、セットされない問題を回避する
        // Webからの登録の場合は、@EntityListeners(AuditingEntityListener.class) により自動設定される
        fileManaged.setCreatedBy("JOB_USER");
        fileManaged.setLastModifiedBy("JOB_USER");
        fileManaged.setCreatedDate(LocalDateTime.now());
        fileManaged.setLastModifiedDate(LocalDateTime.now());

        return fileManagedRepository.save(fileManaged);

    }

    @Override
    public FileManaged store(File file, String fileType) throws IOException {

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

        String storeFilePath = storeDir + File.separator + uuid;

        mkdirs(storeDir);

        File storeFile = new File(storeFilePath);
        String mimeType = "";
        mimeType = MimeTypes.getMimeType(FilenameUtils.getExtension(file.getName()));

        try {
            //file.renameTo(storeFile);
            FileInputStream fileStreamIN = new FileInputStream(file);
            BufferedOutputStream fileStream =
                    new BufferedOutputStream(new FileOutputStream(storeFile));
            byte[] bytes = new byte[256];
            int len;
            while ((len = fileStreamIN.read(bytes)) != -1) {
                fileStream.write(bytes);
            }

            fileStream.close();


            FileManaged fileManaged = FileManaged.builder()
                    .uuid(uuid)
                    .filetype(fileType)
                    .originalFilename(file.getName())
                    .filemime(mimeType)
                    .filesize(file.length())
                    .status(FileStatus.TEMPORARY.getCodeValue())
                    .uri(storeFilePath.substring(STORE_BASEDIR.length()).replace('\\', '/'))
                    .build();

            // コマンドランチャーから実行した場合に、セットされない問題を回避する
            // Webからの登録の場合は、@EntityListeners(AuditingEntityListener.class) により自動設定される
            fileManaged.setCreatedBy("JOB_USER");
            fileManaged.setLastModifiedBy("JOB_USER");
            fileManaged.setCreatedDate(LocalDateTime.now());
            fileManaged.setLastModifiedDate(LocalDateTime.now());

            return fileManagedRepository.save(fileManaged);

        } catch (IOException e) {
            // 異常終了時の処理
            throw e;
        }

    }

    @Override
    public void delete(Long fid) {
        FileManaged fileManaged = fileManagedRepository.findById(fid).orElse(null);
        if (fileManaged != null) {
            // 物理ファイル削除
            deleteFile(fileManaged.getUri());
        }
        fileManagedRepository.deleteById(fid);
    }

    @Override
    public void delete(String uuid) {
        FileManaged file = fileManagedRepository.findByUuid(uuid).orElse(null);
        if (file != null) {
            delete(file.getFid());
        }
    }

    @Override
    public void cleanup(LocalDateTime deleteTo) {
        List<FileManaged> files = fileManagedRepository.findAllByCreatedDateLessThanAndStatus(deleteTo, FileStatus.TEMPORARY.getCodeValue());
        for (FileManaged file : files) {
            delete(file.getFid());
        }

        //TODO 空のフォルダを削除

    }

    @Override
    public String getFileStoreBaseDir() {
        return STORE_BASEDIR + "/";
    }

    @Override
    public String getContent(String uuid) throws IOException, TikaException {
        Tika tika = new Tika();
        return tika.parseToString(new FileInputStream(new File(STORE_BASEDIR + findByUuid(uuid).getUri())));
    }

    @Override
    public void permanent(String uuid) {
        FileManaged file = fileManagedRepository.findByUuidAndStatus(uuid, FileStatus.TEMPORARY.getCodeValue()).orElse(null);
        if (file != null) {
            file.setStatus(FileStatus.PERMANENT.getCodeValue());
            fileManagedRepository.save(file);
        }
    }

    @Override
    public FileManaged copyFile(String sourceUuid) throws IOException {
        FileManaged fileManaged = fileManagedRepository.findByUuid(sourceUuid).orElse(null);

        if (fileManaged == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        String uuid = UUID.randomUUID().toString();

        String storeDir = STORE_BASEDIR
                + File.separator + fileManaged.getFiletype()
                + File.separator + uuid.substring(0, 1);

        String storeFilePath = storeDir + File.separator + uuid;

        mkdirs(storeDir);

        String sourceDir = STORE_BASEDIR
                + File.separator + fileManaged.getFiletype()
                + File.separator + sourceUuid.substring(0, 1);

        String sourceFilePath = sourceDir + File.separator + sourceUuid;

        try {
            Path sourcePath = Paths.get(sourceFilePath);
            Path storePath = Paths.get(storeFilePath);
            Files.copy(sourcePath, storePath);
        } catch (IOException e) {
            // 異常終了時の処理
            throw e;
        }

        return fileManagedRepository.save(
                FileManaged.builder()
                        .uuid(uuid)
                        .filetype(fileManaged.getFiletype())
                        .originalFilename(fileManaged.getOriginalFilename())
                        .filemime(fileManaged.getFilemime())
                        .filesize(fileManaged.getFilesize())
                        .status(FileStatus.TEMPORARY.getCodeValue())
                        .uri(storeFilePath.substring(STORE_BASEDIR.length()).replace('\\', '/'))
                        .build());

    }

    @Override
    public void deleteFile(String uri) {

        if (uri != null) {
            File file = new File(STORE_BASEDIR + uri.replace('/', '\\'));
            file.delete();
        }
    }

    private File mkdirs(String filePath) {
        File uploadDir = new File(filePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }

    private String escapeContent(String rawContent) {

        rawContent = rawContent.replaceAll("[　]+", " ")
                .replaceAll("[ ]+", " ")
                .replaceAll("[\t]+", " ")
                .replaceAll("[ |\t]+", " ")
                .replaceAll("[\\n|\\r\\n|\\r]+", " ")
                .replaceAll("\\n|\\r\\n|\\r", " ");

        return escapeHtml4(rawContent);
    }

}
