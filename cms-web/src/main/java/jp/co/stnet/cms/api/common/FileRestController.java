package jp.co.stnet.cms.api.common;

import jp.co.stnet.cms.app.common.uploadfile.UploadFileResult;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.FileType;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Slf4j
@RestController
@RequestMapping("file")
public class FileRestController {

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UploadFileResult store(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(value = "filetype", required = false) String type,
            @AuthenticationPrincipal LoggedInUser loggedInUser,
            HttpServletRequest request) {

        try {

            FileType fileType = FileType.getByValue(type);
            if (fileType == null) {
                fileType = FileType.DEFAULT;
            }

            // ファイルサイズのチェック
            if (fileType.getFileSize() != null && !fileType.getFileSize().isEmpty()) {
                if (Integer.valueOf(fileType.getFileSize()) * 1024 * 1024 < multipartFile.getSize()) {
                    return UploadFileResult.builder()
                            .message("Upload Fail. [ファイルが大きすぎます。(" + fileType.getFileSize() + "MBまで)]")
                            .build();
                }
            }

            // ファイルの拡張子チェック
            if (fileType.getExtensionPattern() != null && !fileType.getExtensionPattern().isEmpty()) {
                String extension = multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

                String[] patterns = fileType.getExtensionPattern().split(";");
                boolean find = false;
                for (String pattern : patterns) {
                    if (extension.equals(pattern)) {
                        find = true;
                    }
                }

                if (!find) {
                    return UploadFileResult.builder()
                            .message("Upload Fail. [指定されたファイルは選択できません。(" + fileType.getExtensionPattern() + ")]")
                            .build();
                }
            }

            FileManaged fileManaged = fileManagedSharedService.store(multipartFile, type, false);

            return UploadFileResult.builder()
                    .fid(fileManaged.getFid())
                    .uuid(fileManaged.getUuid())
                    .name(fileManaged.getOriginalFilename())
                    .type(fileManaged.getFilemime())
                    .size(fileManaged.getFilesize())
                    .message("Upload Success.")
                    .url(fileManaged.getUuid() + "/download")
//                    .deleteUrl(request.getContextPath() + "/api/file/" + fileManaged.getUuid() + "/delete")
                    .build();

        } catch (Exception e) {
            return UploadFileResult.builder()
                    .message("Upload Fail. [" + e.getMessage() + "]")
                    .build();
        }

    }

    @GetMapping("test")
    public String test(@AuthenticationPrincipal LoggedInUser loggedInUser) {


        if (false) {
            throw new OptimisticLockingFailureException("testtest");
        }

        return "test";
    }


//    @GetMapping("{uuid}/delete")
//    @ResponseStatus(HttpStatus.OK)
//    public UploadFileResult delete(@PathVariable("uuid") String uuid) {
//        fileManagedSharedService.delete(uuid);
//        return new UploadFileResult().builder()
//                .message("file deleted.")
//                .uuid(uuid)
//                .build();
//
//    }

}
