package jp.co.stnet.cms.app.common.uploadfile;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.repository.authentication.RoleRepository;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("file")
public class UploadFileController {

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    RoleRepository target;

    @ModelAttribute
    public UploadFileForm setUp() {
        return new UploadFileForm();
    }

    @GetMapping(value = "upload", params = "form")
    public String form(Model model, UploadFileForm form) {


//        List<Permission> permissions = new ArrayList<>();
//        permissions.add(Permission.builder().permission("create_all_account").label("全ユーザの追加").build());
//        permissions.add(Permission.builder().permission("edit_all_account").label("全ユーザの編集").build());
//
//        target.save(Role.builder()
//                .role("USER")
//                .label("利用者")
//                .permissions(permissions).build());
//
//
//        Role role = target.findById("USER").orElse(null);
//
//        log.info("Role = " + role);



        return "example/fileuploadForm";
    }

    @PostMapping("save")
    public String save(Model model, @Validated UploadFileForm form, BindingResult bindingResult,
                       @AuthenticationPrincipal LoggedInUser loggedInUser, HttpServletRequest request) {

        return "redirect:/file/upload?form";
    }

    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        FileManaged fileManaged = fileManagedSharedService.findOne(uuid);
        model.addAttribute(fileManaged);
        return "fileManagedDownloadView";
    }

}
