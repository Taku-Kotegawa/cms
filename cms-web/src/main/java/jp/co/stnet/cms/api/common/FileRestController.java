package jp.co.stnet.cms.api.common;

import jp.co.stnet.cms.app.common.uploadfile.UploadFileResult;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("file")
public class FileRestController {

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UploadFileResult upload(
            @RequestParam("file") MultipartFile multipartFile,
            @AuthenticationPrincipal LoggedInUser loggedInUser,
            HttpServletRequest request) {

        if (multipartFile.isEmpty()) {
        }

        try {

            FileManaged fileManaged = fileManagedSharedService.store(multipartFile, "test", false);

            return UploadFileResult.builder()
                    .fid(fileManaged.getFid())
                    .uuid(fileManaged.getUuid())
                    .name(fileManaged.getOriginalFilename())
                    .type(fileManaged.getFilemime())
                    .size(fileManaged.getFilesize())
                    .message("Upload Success.")
                    .url(request.getContextPath() + "/file/download/" + fileManaged.getFid().toString())
                    .deleteUrl(request.getContextPath() + "/file/delete/" + fileManaged.getFid().toString())
                    .build();


        } catch (Exception e) {
            return UploadFileResult.builder()
                    .message("Upload Fail. [" + e.getMessage() + "]")
                    .build();
        }

    }


    @GetMapping("{fid}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {


    }

}
