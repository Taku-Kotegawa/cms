package jp.co.stnet.cms.app.simpleentity;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInputDraft;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.example.SimpleEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("simpleentity")
@TransactionTokenCheck("simpleentity")
public class SimpleEntityController {

    private final String BASE_PATH = "/simpleentity/";
    private final String JSP_LIST = "simpleentity/list";
    private final String JSP_FORM = "simpleentity/form";
    private final String JSP_VIEW = "simpleentity/view";

    @Autowired
    SimpleEntityService simpleEntityService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Inject
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private SimpleEntityForm setUp() {
        return new SimpleEntityForm();
    }

    // ---------------- 一覧 -----------------------------------------------------

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model) {
        return JSP_LIST;
    }

    /**
     * DataTables用のJSONの作成
     *
     * @param input DataTablesから要求
     * @return JSON
     */
    @ResponseBody
    @GetMapping(value = "/list/json")
    public DataTablesOutput<SimpleEntityListRow> getListJson(@Validated DataTablesInputDraft input) {

        OperationsUtil op = new OperationsUtil("");

        List<SimpleEntityListRow> list = new ArrayList<>();
        List<SimpleEntity> simpleEntityList = new ArrayList<>();
        Long recordsFiltered = 0L;


        if (input.getDraft() != null) { // ドラフト含む／含まないを切替
            Page<SimpleEntity> simpleEntityPage = simpleEntityService.findPageByInput(input);
            simpleEntityList.addAll(simpleEntityPage.getContent());
            recordsFiltered = simpleEntityPage.getTotalElements();

        } else {
            Page<SimpleEntityRevision> simpleEntityPage2 = simpleEntityService.findMaxRevPageByInput(input);
            for (SimpleEntityRevision simpleEntityRevision : simpleEntityPage2.getContent()) {
                simpleEntityList.add(beanMapper.map(simpleEntityRevision, SimpleEntity.class));
            }
            recordsFiltered = simpleEntityPage2.getTotalElements();
        }


        for (SimpleEntity simpleEntity : simpleEntityList) {
            SimpleEntityListRow simpleEntityListRow = beanMapper.map(simpleEntity, SimpleEntityListRow.class);
            simpleEntityListRow.setOperations(op.getToggleButton(simpleEntity.getId().toString()));
            simpleEntityListRow.setDT_RowId(simpleEntity.getId().toString());

            // ステータスラベル
            String statusLabel = simpleEntity.getStatus().equals(Status.VALID.getCodeValue()) ? Status.VALID.getCodeLabel() : Status.INVALID.getCodeLabel();
            simpleEntityListRow.setStatusLabel(statusLabel);

            list.add(simpleEntityListRow);
        }

        DataTablesOutput<SimpleEntityListRow> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(recordsFiltered);

        return output;
    }

    // ---------------- 新規登録 -----------------------------------------------------

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(SimpleEntityForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            SimpleEntity source = simpleEntityService.findById(copy);
            beanMapper.map(source, form);
            form.setId(null);
        }

        // ボタン・フィールドの状態を設定
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return JSP_FORM;
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({SimpleEntityForm.Create.class, Default.class}) SimpleEntityForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        SimpleEntity simpleEntity = beanMapper.map(form, SimpleEntity.class);
        simpleEntity.setStatus(Status.VALID.getCodeValue());

        try {
            simpleEntityService.save(simpleEntity);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0001);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(simpleEntity.getId().toString());
    }

    // ---------------- 編集 ---------------------------------------------------------

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(SimpleEntityForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        // DBからデータ取得し、modelとformにセット
        SimpleEntity simpleEntity = simpleEntityService.findById(id);
        model.addAttribute("simpleEntity", simpleEntity);
        if (form.getVersion() == null) {
            beanMapper.map(simpleEntity, form);
        }

        // 添付フィアルの情報をセット
        FileManaged fileManaged = fileManagedSharedService.findByUuid(simpleEntity.getAttachedFile01Uuid());
        model.addAttribute("imageFileManaged", fileManaged);

        // ボタン・フィールドの状態を設定
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.UPDATE, simpleEntity).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.UPDATE, simpleEntity).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({SimpleEntityForm.Update.class, Default.class}) SimpleEntityForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        SimpleEntity simpleEntity = beanMapper.map(form, SimpleEntity.class);

        try {
            simpleEntityService.save(simpleEntity);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0004);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(simpleEntity.getId().toString());
    }

    // ---------------- 削除 ---------------------------------------------------------

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            simpleEntityService.delete(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0007);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getListUrl();
    }

    // ---------------- 無効化 ---------------------------------------------------------

    @GetMapping(value = "{id}/invalid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String invalid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        try {
            simpleEntityService.invalid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getViewUrl(id.toString());
    }

    // ---------------- 参照 ---------------------------------------------------------

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        SimpleEntity simpleEntity = simpleEntityService.findById(id);
        model.addAttribute("simpleEntity", simpleEntity);

        // 添付フィアルの情報をセット
        FileManaged fileManaged = fileManagedSharedService.findByUuid(simpleEntity.getAttachedFile01Uuid());
        model.addAttribute("imageFileManaged", fileManaged);

        // ボタン・フィールドの状態を設定
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, simpleEntity).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, simpleEntity).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return JSP_FORM;
    }


    // ---------------- ダウンロード -----------------------------------------------

    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        simpleEntityService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        FileManaged fileManaged = fileManagedSharedService.findByUuid(uuid);
        model.addAttribute(fileManaged);
        return "fileManagedDownloadView";
    }

    // ---------------- 共通(private) -----------------------------------------------

    /**
     * @param operation
     * @param record
     * @return
     */
    private StateMap getButtonStateMap(String operation, SimpleEntity record) {

        if (record == null) {
            record = new SimpleEntity();
        }

        List<String> includeKeys = new ArrayList<>();
        includeKeys.add(Constants.BUTTON.GOTOLIST);
        includeKeys.add(Constants.BUTTON.GOTOUPDATE);
        includeKeys.add(Constants.BUTTON.VIEW);
        includeKeys.add(Constants.BUTTON.SAVE);
        includeKeys.add(Constants.BUTTON.INVALID);
        includeKeys.add(Constants.BUTTON.DELETE);
        includeKeys.add(Constants.BUTTON.UNLOCK);

        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            buttonState.setViewTrue(Constants.BUTTON.SAVE);
        }

        // 編集
        if (Constants.OPERATION.UPDATE.equals(operation)) {

            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
            }

            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
            }

        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {

            // スタータスが公開時
            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.GOTOUPDATE);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
                buttonState.setViewTrue(Constants.BUTTON.UNLOCK);
            }

            // スタータスが無効
            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
            }
        }

        return buttonState;
    }

    /**
     * @param operation
     * @param record
     * @return
     */
    private StateMap getFiledStateMap(String operation, SimpleEntity record) {
        List<String> excludeKeys = new ArrayList<>();

        // 常設の隠しフィールドは状態管理しない

        StateMap fieldState = new StateMap(SimpleEntityForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            fieldState.setInputTrueAll();
        }

        // 編集
        if (Constants.OPERATION.UPDATE.equals(operation)) {
            fieldState.setInputTrueAll();
            fieldState.setReadOnlyTrue("id");

            // スタータスが無効
            if (Status.INVALID.toString().equals(record.getStatus())) {
                fieldState.setReadOnlyTrueAll();
            }
        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {
            fieldState.setViewTrueAll();
        }

        return fieldState;
    }

}
