package jp.co.stnet.cms.app.admin.variables;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.app.admin.variables.VariablesForm.Create;
import jp.co.stnet.cms.app.admin.variables.VariablesForm.Update;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInputDraft;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.common.VariableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import javax.inject.Named;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * 変数管理のController
 *
 * @author Automatically generated
 */
@Slf4j
@Controller
@RequestMapping("variables")
@TransactionTokenCheck("variables")
public class VariablesController {

    private final String BASE_PATH = "variables";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";

    // TODO Inject Service
    @Named("CL_STATUS")
    CodeList statusCodeList;
    @Autowired
    private VariableService variableService;
    @Autowired
    private FileManagedSharedService fileManagedSharedService;
    @Autowired
    private Mapper beanMapper;

    // TODO Inject CodeList
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute
    private VariablesForm setUp() {
        return new VariablesForm();
    }


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
    public DataTablesOutput<VariablesListRow> getListJson(@Validated DataTablesInputDraft input) {

        OperationsUtil op = new OperationsUtil(null);

        List<VariablesListRow> list = new ArrayList<>();
        List<Variable> variablesList = new ArrayList<>();
        Long recordsFiltered = 0L;


        if (input.getDraft() != null) { // ドラフト含む／含まないを切替
            Page<Variable> variablesPage = variableService.findPageByInput(input);
            variablesList.addAll(variablesPage.getContent());
            recordsFiltered = variablesPage.getTotalElements();

        } else {
//            Page<VariablesRevision> variablesPage2 = variableService.findMaxRevPageByInput(input);
//            for (VariablesRevision variablesRevision : variablesPage2.getContent()) {
//                variablesList.add(beanMapper.map(variablesRevision, Variable.class));
//            }
//            recordsFiltered = variablesPage2.getTotalElements();
        }

        for (Variable variables : variablesList) {
            VariablesListRow variablesListRow = beanMapper.map(variables, VariablesListRow.class);
            variablesListRow.setOperations(op.getToggleButton(variables.getId().toString()));
            variablesListRow.setDT_RowId(variables.getId().toString());

            // ステータスラベル
            variablesListRow.setStatusLabel(Status.getByValue(variables.getStatus()).getCodeLabel());

            list.add(variablesListRow);
        }

        DataTablesOutput<VariablesListRow> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(recordsFiltered);

        return output;
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(VariablesForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            Variable source = variableService.findById(copy);
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
    public String create(@Validated({Create.class, Default.class}) VariablesForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        Variable variables = beanMapper.map(form, Variable.class);
        variables.setStatus(Status.VALID.getCodeValue());

        try {
            variableService.save(variables);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0001);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(variables.getId().toString());
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(VariablesForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        // DBからデータ取得し、modelとformにセット
        Variable variables = variableService.findById(id);

        // 状態=無効の場合参照画面に強制遷移
        if (variables.getStatus().equals(Status.INVALID.getCodeValue())) {

            ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0008);
            model.addAttribute(messages);
            return view(model, loggedInUser, id);

        }

        model.addAttribute("variables", variables);
        if (form.getVersion() == null) {
            beanMapper.map(variables, form);
        }

        // 添付フィアルの情報をセット
//        FileManaged fileManaged = fileManagedSharedService.findByUuid(variables.getAttachedFile01Uuid());
//        model.addAttribute("attachedFile01FileManaged", fileManaged);

        // ボタン・フィールドの状態を設定
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, variables).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, variables).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({Update.class, Default.class}) VariablesForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        Variable variables = beanMapper.map(form, Variable.class);

        try {
            variableService.save(variables);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0004);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(variables.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            variableService.delete(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0007);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getListUrl();
    }

    @GetMapping(value = "{id}/invalid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String invalid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        try {
            variableService.invalid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getViewUrl(id.toString());
    }

    @GetMapping(value = "{id}/valid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String valid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                        @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        try {
            variableService.valid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getViewUrl(id.toString());
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        Variable variables = variableService.findById(id);
        model.addAttribute("variables", variables);

        // 添付フィアルの情報をセット
//        FileManaged fileManaged = fileManagedSharedService.findByUuid(variables.getAttachedFile01Uuid());
//        model.addAttribute("attachedFile01FileManaged", fileManaged);

        // ボタン・フィールドの状態を設定
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, variables).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, variables).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return JSP_FORM;
    }

    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        variableService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        FileManaged fileManaged = fileManagedSharedService.findByUuid(uuid);
        model.addAttribute(fileManaged);
        return "fileManagedDownloadView";
    }

    /**
     * ボタンの状態設定
     *
     * @param operation
     * @param record
     * @return
     */
    private StateMap getButtonStateMap(String operation, Variable record) {

        if (record == null) {
            record = new Variable();
        }

        List<String> includeKeys = new ArrayList<>();
        includeKeys.add(Constants.BUTTON.GOTOLIST);
        includeKeys.add(Constants.BUTTON.GOTOUPDATE);
        includeKeys.add(Constants.BUTTON.VIEW);
        includeKeys.add(Constants.BUTTON.SAVE);
        includeKeys.add(Constants.BUTTON.INVALID);
        includeKeys.add(Constants.BUTTON.VALID);
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
        if (Constants.OPERATION.SAVE.equals(operation)) {

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
                buttonState.setViewTrue(Constants.BUTTON.VALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
            }
        }

        return buttonState;
    }

    /**
     * フィールドの状態を設定
     *
     * @param operation
     * @param record
     * @return
     */
    private StateMap getFiledStateMap(String operation, Variable record) {
        List<String> excludeKeys = new ArrayList<>();

        // 常設の隠しフィールドは状態管理しない

        StateMap fieldState = new StateMap(VariablesForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            fieldState.setInputTrueAll();
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {
            fieldState.setInputTrueAll();
            fieldState.setReadOnlyTrue("id");
            fieldState.setViewTrue("status");

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