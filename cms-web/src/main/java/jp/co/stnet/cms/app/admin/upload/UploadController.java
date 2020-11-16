package jp.co.stnet.cms.app.admin.upload;

import jp.co.stnet.cms.app.common.uploadfile.UploadFileForm;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/upload")
public class UploadController {

    private final String BASE_PATH = "admin/upload";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_CONFIRM = BASE_PATH + "/confirm";
    private final String JSP_COMPLETE = BASE_PATH + "/complete";
    private final String JSP_FAILE = BASE_PATH + "/faile";


    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    JobExplorer jobExplorer;

    @ModelAttribute
    UploadFileForm setup() {
        return new UploadFileForm();
    }

//    @GetMapping(value = "upload", params = "form")
//    public String uploadForm(UploadForm form, Model model,
//                             @RequestHeader(value = "Referer", required=false) String referer,
//                             @AuthenticationPrincipal LoggedInUser loggedInUser) {
//
//        model.addAttribute("referer", referer);
//
//        model.addAttribute("buttonState", getButtonStateMap(null).asMap());
//        model.addAttribute("fieldState", getFiledStateMap(null).asMap());
//        return JSP_FORM;
//    }

//    @PostMapping(value = "upload", params = "confirm")
//    public String confirm(@Validated UploadForm form, BindingResult result, Model model,
//                  @AuthenticationPrincipal LoggedInUser loggedInUser) {
//
//        FileManaged uploadFile = fileManagedSharedService.findByUuid(form.getUploadFileUuid());
//        String uploadFileAbsolutePath = fileManagedSharedService.getFileStoreBaseDir() + uploadFile.getUri();
//
//
//        String jobName = "job02";
//        String jobParams = "inputFile=" + uploadFileAbsolutePath;
//        Long jobExecutionId = null;
//
//
//        try {
//            jobExecutionId = jobOperator.start(jobName, jobParams);
//
//        } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
//
//            e.printStackTrace();
//
//            return JSP_FAILE;
//        }
//
//        model.addAttribute(jobName);
//        model.addAttribute(jobParams);
//        model.addAttribute(jobExecutionId);
//
//        model.addAttribute("buttonState", getButtonStateMap(null).asMap());
//        model.addAttribute("fieldState", getFiledStateMap(null).asMap());
//
//        // リダイレクト
//        return JSP_CONFIRM;
//    }


    /**
     * ボタンの状態設定
     */
    private StateMap getButtonStateMap(String operation) {


        List<String> includeKeys = new ArrayList<>();
        includeKeys.add(Constants.BUTTON.GOHOME);
        includeKeys.add(Constants.BUTTON.VALIDATE);

        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrueAll();

        return buttonState;
    }

    /**
     * フィールドの状態設定
     */
    private StateMap getFiledStateMap(String operation) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        StateMap fieldState = new StateMap(UploadForm.class, new ArrayList<>(), excludeKeys);

        fieldState.setInputTrueAll();

        return fieldState;
    }


}
