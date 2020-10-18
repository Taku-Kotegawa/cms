package jp.co.stnet.cms.app.admin.variable;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInputDraft;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.common.scheduled.CsvUtils;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.model.common.VariableType;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.common.VariableService;
import jp.co.stnet.cms.domain.service.common.VariableSharedService;
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

import javax.inject.Named;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/variable")
@TransactionTokenCheck("variable")
public class VariableController {

    private final String BASE_PATH = "admin/variable";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";

    @Autowired
    VariableService variableService;

    @Autowired
    VariableSharedService variableSharedService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private VariableForm setUp() {
        return new VariableForm();
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
    public DataTablesOutput<VariableListRow> listJson(@Validated DataTablesInputDraft input) {

        OperationsUtil op = new OperationsUtil(null);

        List<VariableListRow> list = new ArrayList<>();
        List<Variable> variableList = new ArrayList<>();
        Long recordsFiltered = 0L;


        if (input.getDraft() == null || input.getDraft()) { // 下書き含む最新
            Page<Variable> variablePage = variableService.findPageByInput(input);
            variableList.addAll(variablePage.getContent());
            recordsFiltered = variablePage.getTotalElements();

        } else {
//            Page<VariableRevision> variablePage2 = variableService.findMaxRevPageByInput(input);
//            for (VariableRevision variableRevision : variablePage2.getContent()) {
//                variableList.add(beanMapper.map(variableRevision, Variable.class));
//            }
//            recordsFiltered = variablePage2.getTotalElements();
        }

        for (Variable variable : variableList) {
            VariableListRow variableListRow = beanMapper.map(variable, VariableListRow.class);
            variableListRow.setOperations(getToggleButton(variable.getId().toString(), op));
            variableListRow.setDT_RowId(variable.getId().toString());

            // ステータスラベル
            variableListRow.setStatusLabel(Status.getByValue(variable.getStatus()).getCodeLabel());

            list.add(variableListRow);
        }

        DataTablesOutput<VariableListRow> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(recordsFiltered);

        return output;
    }

    @GetMapping(value = "/list/csv")
    public String listCsv(@Validated DataTablesInputDraft input, Model model) {
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getCsvDefault());
        model.addAttribute("csvFileName", "Variable.csv");
//        model.addAttribute("handler", VariableCsvBean.getHandler());
        return "csvDownloadView";
    }

    @GetMapping(value = "/list/tsv")
    public String listTsv(@Validated DataTablesInputDraft input, Model model) {
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getTsvDefault());
        model.addAttribute("csvFileName", "Variable.tsv");
//        model.addAttribute("handler", VariableCsvBean.getHandler());
        return "csvDownloadView";
    }

    private void setModelForCsv(DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.CSV.MAX_LENGTH);

        List<VariableCsvBean> list = new ArrayList<>();
        List<Variable> variableList = new ArrayList<>();

        if (input.getDraft()) { // 下書き含む最新
            Page<Variable> variablePage = variableService.findPageByInput(input);
            variableList.addAll(variablePage.getContent());

        } else {
//            Page<VariableRevision> variablePage2 = variableService.findMaxRevPageByInput(input);
//            for (VariableRevision variableRevision : variablePage2.getContent()) {
//                variableList.add(beanMapper.map(variableRevision, Variable.class));
//            }
        }

        for (Variable variable : variableList) {
            VariableCsvBean row = beanMapper.map(variable, VariableCsvBean.class);
            row.setStatusLabel(Status.getByValue(variable.getStatus()).getCodeLabel());
            list.add(row);
        }

        model.addAttribute("exportCsvData", list);
        model.addAttribute("class", VariableCsvBean.class);
    }


    private String getToggleButton(String id, OperationsUtil op) {

        StringBuffer link = new StringBuffer();
        link.append("<div class=\"btn-group\">");
        link.append("<a href=\"" + op.getEditUrl(id) + "\" class=\"btn btn-button btn-sm\" style=\"white-space: nowrap\">" + op.getLABEL_EDIT() + "</a>");
        link.append("<button type=\"button\" class=\"btn btn-button btn-sm dropdown-toggle dropdown-toggle-split\"data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">");
        link.append("</button>");
        link.append("<div class=\"dropdown-menu\">");
        link.append("<a class=\"dropdown-item\" href=\"" + op.getViewUrl(id) + "\">" + op.getLABEL_VIEW() + "</a>");
        link.append("<a class=\"dropdown-item\" href=\"" + op.getCopyUrl(id) + "\">" + op.getLABEL_COPY() + "</a>");
        link.append("<a class=\"dropdown-item\" href=\"" + op.getInvalidUrl(id) + "\">" + op.getLABEL_INVALID() + "</a>");
        link.append("</div>");
        link.append("</div>");

        return link.toString();
    }

    protected OperationsUtil op() {
        return new OperationsUtil(BASE_PATH);
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(VariableForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy,
                             @RequestParam(value = "variable_type", required = false) String variableType) {

        variableService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        form.setType(variableType);

        if (copy != null) {
            Variable source = variableService.findById(copy);
            beanMapper.map(source, form);
            form.setId(null);
        }

        if (form.getFile1Uuid() != null) {
            form.setFile1Managed(fileManagedSharedService.findByUuid(form.getFile1Uuid()));
        }

        model.addAttribute("fieldLabel", getFieldLabel(variableType));
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({VariableForm.Create.class, Default.class}) VariableForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        variableService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null, form.getType());
        }

        Variable variable = beanMapper.map(form, Variable.class);

        try {
            if ("true".equals(saveDraft)) {
//                variable.setStatus(Status.VALID.getCodeValue());
//                variableService.saveDraft(variable);
            } else {
                variable.setStatus(Status.VALID.getCodeValue());
                variableService.save(variable);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null, form.getType());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0001));

        return "redirect:" + op().getEditUrl(variable.getId().toString());
    }

    private Map<String, String> getFieldLabel(String code) {
        Map<String, String> labels = new HashMap<>();
        labels.put("value1", "値１");
        labels.put("value2", "値２");
        labels.put("value3", "値３");
        labels.put("value4", "値４");
        labels.put("value5", "値５");
        labels.put("value6", "値６");
        labels.put("value7", "値７");
        labels.put("value8", "値８");
        labels.put("value9", "値９");
        labels.put("value10", "値１０");
        labels.put("valint1", "数値１");
        labels.put("valint2", "数値２");
        labels.put("valint3", "数値３");
        labels.put("valint4", "数値４");
        labels.put("valint5", "数値５");
        labels.put("date1", "日付１");
        labels.put("date2", "日付２");
        labels.put("date3", "日付３");
        labels.put("date4", "日付４");
        labels.put("date5", "日付５");
        labels.put("textarea", "テキストエリア");
        labels.put("file1", "ファイル");
        labels.put("remark", "備考");

        List<Variable> variables = variableSharedService.findAllByTypeAndCode(VariableType.VARIABLE_LABEL.getCodeValue(), code);
        if (variables.size() > 0 && variables.get(0).getTextarea() != null) {
            String[] t = variables.get(0).getTextarea().split(",");
            for (int i = 0; i < t.length; i++) {
                String[] v = t[i].split("=");
                if (v.length == 2) {
                    labels.put(v[0].trim(), StringUtils.stripToEmpty(v[1]));
                } else if (v.length == 1) {
                    labels.put(v[0].trim(), "");
                }
            }
        }
        return labels;
    }


    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(VariableForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        variableService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        Variable variable = variableService.findById(id);
        model.addAttribute("variable", variable);

        // 状態=無効の場合、参照画面に強制遷移
        if (variable.getStatus().equals(Status.INVALID.getCodeValue())) {
            model.addAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0008));
            return view(model, loggedInUser, id, null);
        }

        // 入力チェック再表示の場合、formの情報をDBの値で上書きしない
        if (form.getVersion() == null) {
            beanMapper.map(variable, form);
        }

        if (form.getFile1Uuid() != null) {
            form.setFile1Managed(fileManagedSharedService.findByUuid(form.getFile1Uuid()));
        }

        model.addAttribute("fieldLabel", getFieldLabel(form.getType()));
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, variable).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, variable).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({VariableForm.Update.class, Default.class}) VariableForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        variableService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        Variable variable = beanMapper.map(form, Variable.class);

        try {
            if ("true".equals(saveDraft)) {
//                variableService.saveDraft(variable);
            } else {
                variable.setStatus(Status.VALID.getCodeValue());
                variableService.save(variable);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0004));

        return "redirect:" + op().getEditUrl(variable.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        variableService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            variableService.delete(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0007));

        return "redirect:" + op().getListUrl();
    }

    /**
     * 無効化
     */
    @GetMapping(value = "{id}/invalid")
    public String invalid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("id") Long id) {

        variableService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        Variable entity = variableService.findById(id);

        try {
            entity = variableService.invalid(id);
        } catch (BusinessException e) {
            redirect.addFlashAttribute(e.getResultMessages());
            return "redirect:" + op().getEditUrl(id.toString());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));

        return "redirect:" + op().getViewUrl(id.toString());
    }

    /**
     * 無効解除
     */
    @GetMapping(value = "{id}/valid")
    public String valid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                        @PathVariable("id") Long id) {

        variableService.hasAuthority(Constants.OPERATION.VALID, loggedInUser);

        Variable entity = variableService.findById(id);

        try {
            entity = variableService.valid(id);
        } catch (BusinessException e) {
            redirect.addFlashAttribute(e.getResultMessages());
            return "redirect:" + op().getViewUrl(id.toString());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));

        return "redirect:" + op().getEditUrl(id.toString());
    }

//    /**
//     * 下書き取消
//     */
//    @GetMapping(value = "{id}/cancel_draft")
//    public String cancelDraft(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
//                              @PathVariable("id") Long id) {
//
//        variableService.hasAuthority(Constants.OPERATION.CANCEL_DRAFT, loggedInUser);
//
//        Variable entity = null;
//        try {
//            entity = variableService.cancelDraft(id);
//        } catch (BusinessException e) {
//            redirect.addFlashAttribute(e.getResultMessages());
//            return "redirect:" + op().getEditUrl(id.toString());
//        }
//
//        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));
//
//        if (entity != null) {
//            return "redirect:" + op().getEditUrl(id.toString());
//        } else {
//            return "redirect:" + op().getListUrl();
//        }
//    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id,
                       @RequestParam(value = "rev", required = false) Long rev) {

        variableService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        Variable variable = null;
        if (rev == null) {
            // 下書きを含む最新
            variable = variableService.findById(id);

        } else if (rev == 0) {
            // 有効な最新リビジョン
//            variable = beanMapper.map(variableService.findByIdLatestRev(id), Variable.class);

        } else {
            // リビジョン番号指定
//            variable = beanMapper.map(variableService.findByRid(rev), Variable.class);
        }

        model.addAttribute("variable", variable);

        if (variable.getFile1Uuid() != null) {
            variable.setFile1Managed(fileManagedSharedService.findByUuid(variable.getFile1Uuid()));
        }

        model.addAttribute("fieldLabel", getFieldLabel(variable.getType()));
        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, variable).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, variable).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * ダウンロード
     */
    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        variableService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        model.addAttribute(fileManagedSharedService.findByUuid(uuid));
        return "fileManagedDownloadView";
    }

    /**
     * ボタンの状態設定
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
//        includeKeys.add(Constants.BUTTON.SAVE_DRAFT);
//        includeKeys.add(Constants.BUTTON.CANCEL_DRAFT);

        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            buttonState.setViewTrue(Constants.BUTTON.SAVE);
//            buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {

            if (Status.DRAFT.getCodeValue().equals(record.getStatus())) {
//                buttonState.setViewTrue(Constants.BUTTON.CANCEL_DRAFT);
//                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
            }

            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
//                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
            }

            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.VALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
            }

        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {

            // スタータスが公開時
            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.GOTOUPDATE);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
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
     * フィールドの状態設定
     */
    private StateMap getFiledStateMap(String operation, Variable record) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("id");
        excludeKeys.add("version");
        excludeKeys.add("file1Managed");
        excludeKeys.add("file1Managed-createdBy");
        excludeKeys.add("file1Managed-fid");
        excludeKeys.add("file1Managed-filemime");
        excludeKeys.add("file1Managed-filesize");
        excludeKeys.add("file1Managed-filetype");
        excludeKeys.add("file1Managed-lastModifiedBy");
        excludeKeys.add("file1Managed-lastModifiedDate");
        excludeKeys.add("file1Managed-originalFilename");
        excludeKeys.add("file1Managed-status");
        excludeKeys.add("file1Managed-uri");
        excludeKeys.add("file1Managed-uuid");
        excludeKeys.add("file1Managed-version");

        StateMap fieldState = new StateMap(VariableForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            fieldState.setInputTrueAll();
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {
            fieldState.setInputTrueAll();
            fieldState.setViewTrue("status");
            fieldState.setDisabledTrue("type");
            fieldState.setHiddenTrue("type");
            fieldState.setReadOnlyTrue("code");

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
