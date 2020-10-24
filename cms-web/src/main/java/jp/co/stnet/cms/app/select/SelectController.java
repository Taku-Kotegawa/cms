package jp.co.stnet.cms.app.select;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInputDraft;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.common.scheduled.CsvUtils;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.example.Select;
import jp.co.stnet.cms.domain.model.example.SelectRevision;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.example.SelectService;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("select")
@TransactionTokenCheck("select")
public class SelectController {

    private final String BASE_PATH = "select";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";

    @Autowired
    SelectService selectService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private SelectForm setUp() {
        return new SelectForm();
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
    public DataTablesOutput<SelectListRow> listJson(@Validated DataTablesInputDraft input) {

        OperationsUtil op = new OperationsUtil(null);

        List<SelectListRow> list = new ArrayList<>();
        List<Select> selectList = new ArrayList<>();
        Long recordsFiltered = 0L;


        if (input.getDraft() == null || input.getDraft()) { // 下書き含む最新
            Page<Select> selectPage = selectService.findPageByInput(input);
            selectList.addAll(selectPage.getContent());
            recordsFiltered = selectPage.getTotalElements();

        } else {
            Page<SelectRevision> selectPage2 = selectService.findMaxRevPageByInput(input);
            for (SelectRevision selectRevision : selectPage2.getContent()) {
                selectList.add(beanMapper.map(selectRevision, Select.class));
            }
            recordsFiltered = selectPage2.getTotalElements();
        }

        for (Select select : selectList) {
            SelectListRow selectListRow = beanMapper.map(select, SelectListRow.class);
            selectListRow.setOperations(getToggleButton(select.getId().toString(), op));
            selectListRow.setDT_RowId(select.getId().toString());

            // ステータスラベル
            selectListRow.setStatusLabel(Status.getByValue(select.getStatus()).getCodeLabel());

            list.add(selectListRow);
        }

        DataTablesOutput<SelectListRow> output = new DataTablesOutput<>();
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
        model.addAttribute("csvFileName", "Select.csv");
        return "csvDownloadView";
    }

    @GetMapping(value = "/list/tsv")
    public String listTsv(@Validated DataTablesInputDraft input, Model model) {
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getTsvDefault());
        model.addAttribute("csvFileName", "Select.tsv");
        return "csvDownloadView";
    }

    private void setModelForCsv(DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.CSV.MAX_LENGTH);

        List<SelectCsvBean> list = new ArrayList<>();
        List<Select> selectList = new ArrayList<>();

        if (input.getDraft()) { // 下書き含む最新
            Page<Select> selectPage = selectService.findPageByInput(input);
            selectList.addAll(selectPage.getContent());

        } else {
            Page<SelectRevision> selectPage2 = selectService.findMaxRevPageByInput(input);
            for (SelectRevision selectRevision : selectPage2.getContent()) {
                selectList.add(beanMapper.map(selectRevision, Select.class));
            }
        }

        for (Select select : selectList) {
            SelectCsvBean row = beanMapper.map(select, SelectCsvBean.class);
            row.setStatusLabel(Status.getByValue(select.getStatus()).getCodeLabel());
            list.add(row);
        }

        model.addAttribute("exportCsvData", list);
        model.addAttribute("class", SelectCsvBean.class);
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
    public String createForm(SelectForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        selectService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            Select source = selectService.findById(copy);
            beanMapper.map(source, form);
            form.setId(null);
        }

//        if (form.getAttachedFile01Uuid() != null) {
//            form.setAttachedFile01Managed(fileManagedSharedService.findByUuid(form.getAttachedFile01Uuid()));
//        }

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
    public String create(@Validated({SelectForm.Create.class, Default.class}) SelectForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        selectService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        Select select = beanMapper.map(form, Select.class);

        try {
            if ("true".equals(saveDraft)) {
                select.setStatus(Status.VALID.getCodeValue());
                selectService.saveDraft(select);
            } else {
                select.setStatus(Status.VALID.getCodeValue());
                selectService.save(select);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0001));

        return "redirect:" + op().getEditUrl(select.getId().toString());
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(SelectForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        selectService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        Select select = selectService.findById(id);
        model.addAttribute("select", select);

        // 状態=無効の場合、参照画面に強制遷移
        if (select.getStatus().equals(Status.INVALID.getCodeValue())) {
            model.addAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0008));
            return view(model, loggedInUser, id, null);
        }

        // 入力チェック再表示の場合、formの情報をDBの値で上書きしない
        if (form.getVersion() == null) {
            beanMapper.map(select, form);
        }

//        if (form.getAttachedFile01Uuid() != null) {
//            form.setAttachedFile01Managed(fileManagedSharedService.findByUuid(form.getAttachedFile01Uuid()));
//        }

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, select).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, select).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({SelectForm.Update.class, Default.class}) SelectForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        selectService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        Select select = beanMapper.map(form, Select.class);

        try {
            if ("true".equals(saveDraft)) {
                selectService.saveDraft(select);
            } else {
                select.setStatus(Status.VALID.getCodeValue());
                selectService.save(select);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0004));

        return "redirect:" + op().getEditUrl(select.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        selectService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            selectService.delete(id);
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

        selectService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        Select entity = selectService.findById(id);

        try {
            entity = selectService.invalid(id);
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

        selectService.hasAuthority(Constants.OPERATION.VALID, loggedInUser);

        Select entity = selectService.findById(id);

        try {
            entity = selectService.valid(id);
        } catch (BusinessException e) {
            redirect.addFlashAttribute(e.getResultMessages());
            return "redirect:" + op().getViewUrl(id.toString());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));

        return "redirect:" + op().getEditUrl(id.toString());
    }

    /**
     * 下書き取消
     */
    @GetMapping(value = "{id}/cancel_draft")
    public String cancelDraft(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                              @PathVariable("id") Long id) {

        selectService.hasAuthority(Constants.OPERATION.CANCEL_DRAFT, loggedInUser);

        Select entity = null;
        try {
            entity = selectService.cancelDraft(id);
        } catch (BusinessException e) {
            redirect.addFlashAttribute(e.getResultMessages());
            return "redirect:" + op().getEditUrl(id.toString());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));

        if (entity != null) {
            return "redirect:" + op().getEditUrl(id.toString());
        } else {
            return "redirect:" + op().getListUrl();
        }
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id,
                       @RequestParam(value = "rev", required = false) Long rev) {

        selectService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        Select select;
        if ( rev == null) {
            // 下書きを含む最新
            select = selectService.findById(id);

        } else if (rev == 0) {
            // 有効な最新リビジョン
            select = beanMapper.map(selectService.findByIdLatestRev(id), Select.class);

        } else {
            // リビジョン番号指定
            select = beanMapper.map(selectService.findByRid(rev), Select.class);
        }

        model.addAttribute("select", select);

//        if (select.getAttachedFile01Uuid() != null) {
//            select.setAttachedFile01Managed(fileManagedSharedService.findByUuid(select.getAttachedFile01Uuid()));
//        }

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, select).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, select).asMap());
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

        selectService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        model.addAttribute(fileManagedSharedService.findByUuid(uuid));
        return "fileManagedDownloadView";
    }

    /**
     * ボタンの状態設定
     */
    private StateMap getButtonStateMap(String operation, Select record) {

        if (record == null) {
            record = new Select();
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
        includeKeys.add(Constants.BUTTON.SAVE_DRAFT);
        includeKeys.add(Constants.BUTTON.CANCEL_DRAFT);

        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            buttonState.setViewTrue(Constants.BUTTON.SAVE);
            buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {

            if (Status.DRAFT.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.CANCEL_DRAFT);
                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
            }

            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
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
    private StateMap getFiledStateMap(String operation, Select record) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("id");
        excludeKeys.add("version");

        StateMap fieldState = new StateMap(SelectForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            fieldState.setInputTrueAll();
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {
            fieldState.setInputTrueAll();
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
