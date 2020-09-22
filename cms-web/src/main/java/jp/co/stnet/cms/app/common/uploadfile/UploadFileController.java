package jp.co.stnet.cms.app.common.uploadfile;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("file")
public class UploadFileController {

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @ModelAttribute
    public UploadFileForm setUp() {
        return new UploadFileForm();
    }

    @GetMapping(value = "upload", params = "form")
    public String form(Model model, UploadFileForm form) {
        return "example/fileuploadForm";
    }


    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadFileResult post(
            @RequestParam("upload_file") MultipartFile multipartFile,
            @AuthenticationPrincipal LoggedInUser loggedInUser,
            HttpServletRequest request) {


        if(multipartFile.isEmpty()){
            // 異常終了時の処理
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


}
