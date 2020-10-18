package jp.co.stnet.cms.app.simpleentity;

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

import javax.inject.Named;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("simpleentity")
@TransactionTokenCheck("simpleentity")
public class SimpleEntityController {

    private final String BASE_PATH = "simpleentity";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";

    @Autowired
    SimpleEntityService simpleEntityService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private SimpleEntityForm setUp() {
        return new SimpleEntityForm();
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
    public DataTablesOutput<SimpleEntityListRow> listJson(@Validated DataTablesInputDraft input) {

        OperationsUtil op = new OperationsUtil(null);

        List<SimpleEntityListRow> list = new ArrayList<>();
        List<SimpleEntity> simpleEntityList = new ArrayList<>();
        Long recordsFiltered = 0L;


        if (input.getDraft()) { // 下書き含む最新
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
            simpleEntityListRow.setOperations(getToggleButton(simpleEntity.getId().toString(), op));
            simpleEntityListRow.setDT_RowId(simpleEntity.getId().toString());

            // ステータスラベル
            simpleEntityListRow.setStatusLabel(Status.getByValue(simpleEntity.getStatus()).getCodeLabel());

            list.add(simpleEntityListRow);
        }

        DataTablesOutput<SimpleEntityListRow> output = new DataTablesOutput<>();
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
        model.addAttribute("csvFileName", "SimpleEntity.csv");
        return "csvDownloadView";
    }

    @GetMapping(value = "/list/tsv")
    public String listTsv(@Validated DataTablesInputDraft input, Model model) {
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getTsvDefault());
        model.addAttribute("csvFileName", "SimpleEntity.tsv");
        return "csvDownloadView";
    }

    private void setModelForCsv(DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.CSV.MAX_LENGTH);

        List<SimpleEntityCsvBean> list = new ArrayList<>();
        List<SimpleEntity> simpleEntityList = new ArrayList<>();

        if (input.getDraft() == null || input.getDraft()) { // 下書き含む最新
            Page<SimpleEntity> simpleEntityPage = simpleEntityService.findPageByInput(input);
            simpleEntityList.addAll(simpleEntityPage.getContent());

        } else {
            Page<SimpleEntityRevision> simpleEntityPage2 = simpleEntityService.findMaxRevPageByInput(input);
            for (SimpleEntityRevision simpleEntityRevision : simpleEntityPage2.getContent()) {
                simpleEntityList.add(beanMapper.map(simpleEntityRevision, SimpleEntity.class));
            }
        }

        for (SimpleEntity simpleEntity : simpleEntityList) {
            SimpleEntityCsvBean row = beanMapper.map(simpleEntity, SimpleEntityCsvBean.class);
            row.setStatusLabel(Status.getByValue(simpleEntity.getStatus()).getCodeLabel());
            list.add(row);
        }

        model.addAttribute("exportCsvData", list);
        model.addAttribute("class", SimpleEntityCsvBean.class);
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
    public String createForm(SimpleEntityForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        simpleEntityService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            SimpleEntity source = simpleEntityService.findById(copy);
            beanMapper.map(source, form);
            form.setId(null);
        }

        // TODO 削除
        if (form.getAttachedFile01Uuid() != null) {
            form.setAttachedFile01Managed(fileManagedSharedService.findByUuid(form.getAttachedFile01Uuid()));
        }

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
    public String create(@Validated({SimpleEntityForm.Create.class, Default.class}) SimpleEntityForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        simpleEntityService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        SimpleEntity simpleEntity = beanMapper.map(form, SimpleEntity.class);

        try {
            if ("true".equals(saveDraft)) {
                simpleEntity.setStatus(Status.VALID.getCodeValue());
                simpleEntityService.saveDraft(simpleEntity);
            } else {
                simpleEntity.setStatus(Status.VALID.getCodeValue());
                simpleEntityService.save(simpleEntity);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0001));

        return "redirect:" + op().getEditUrl(simpleEntity.getId().toString());
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(SimpleEntityForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        simpleEntityService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        SimpleEntity simpleEntity = simpleEntityService.findById(id);
        model.addAttribute("simpleEntity", simpleEntity);

        // 状態=無効の場合、参照画面に強制遷移
        if (simpleEntity.getStatus().equals(Status.INVALID.getCodeValue())) {
            model.addAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0008));
            return view(model, loggedInUser, id, null);
        }

        // 入力チェック再表示の場合、formの情報をDBの値で上書きしない
        if (form.getVersion() == null) {
            beanMapper.map(simpleEntity, form);
        }

        // TODO 削除
        if (form.getAttachedFile01Uuid() != null) {
            form.setAttachedFile01Managed(fileManagedSharedService.findByUuid(form.getAttachedFile01Uuid()));
        }

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, simpleEntity).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, simpleEntity).asMap());
        model.addAttribute("op", op());

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
                         @PathVariable("id") Long id,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        simpleEntityService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        SimpleEntity simpleEntity = beanMapper.map(form, SimpleEntity.class);

        try {
            if ("true".equals(saveDraft)) {
                simpleEntityService.saveDraft(simpleEntity); // TODO 置き換え
            } else {
                simpleEntity.setStatus(Status.VALID.getCodeValue());
                simpleEntityService.save(simpleEntity);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0004));

        return "redirect:" + op().getEditUrl(simpleEntity.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        simpleEntityService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            simpleEntityService.delete(id);
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

        simpleEntityService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        SimpleEntity entity = simpleEntityService.findById(id);

        try {
            entity = simpleEntityService.invalid(id);
        } catch (BusinessException e) {
            redirect.addFlashAttribute(e.getResultMessages());
            return "redirect:" + op().getEditUrl(id.toString());
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));

        return "redirect:" + op().getViewUrl(id.toString());
    }

    // TODO メソッド削除
    /**
     * 無効解除
     */
    @GetMapping(value = "{id}/valid")
    public String valid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                        @PathVariable("id") Long id) {

        simpleEntityService.hasAuthority(Constants.OPERATION.VALID, loggedInUser);

        SimpleEntity entity = simpleEntityService.findById(id);

        try {
            entity = simpleEntityService.valid(id);
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

        simpleEntityService.hasAuthority(Constants.OPERATION.CANCEL_DRAFT, loggedInUser);

        SimpleEntity entity = null;
        try {
            entity = simpleEntityService.cancelDraft(id);
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

        simpleEntityService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // SimpleEntity simpleEntity = simpleEntityService.findById(id);　// TODO 置き換え

        SimpleEntity simpleEntity;
        if ( rev == null) {
            // 下書きを含む最新
            simpleEntity = simpleEntityService.findById(id);

        } else if (rev == 0) {
            // 有効な最新リビジョン
            simpleEntity = beanMapper.map(simpleEntityService.findByIdLatestRev(id), SimpleEntity.class);

        } else {
            // リビジョン番号指定
            simpleEntity = beanMapper.map(simpleEntityService.findByRid(rev), SimpleEntity.class);
        }

        model.addAttribute("simpleEntity", simpleEntity);

        // TODO 削除
        if (simpleEntity.getAttachedFile01Uuid() != null) {
            simpleEntity.setAttachedFile01Managed(fileManagedSharedService.findByUuid(simpleEntity.getAttachedFile01Uuid()));
        }

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, simpleEntity).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, simpleEntity).asMap());
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

        simpleEntityService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        model.addAttribute(fileManagedSharedService.findByUuid(uuid));
        return "fileManagedDownloadView";
    }

    /**
     * ボタンの状態設定
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
        includeKeys.add(Constants.BUTTON.VALID);
        includeKeys.add(Constants.BUTTON.DELETE);
        includeKeys.add(Constants.BUTTON.UNLOCK);
        includeKeys.add(Constants.BUTTON.SAVE_DRAFT); // TODO 削除
        includeKeys.add(Constants.BUTTON.CANCEL_DRAFT); // TODO 削除

        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            buttonState.setViewTrue(Constants.BUTTON.SAVE);
            buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT); // TODO 削除
        }

        // 編集
        if (Constants.OPERATION.SAVE.equals(operation)) {

            if (Status.DRAFT.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.CANCEL_DRAFT); // TODO 削除
                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT); // TODO 削除
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
            }

            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT); // TODO 削除
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
    private StateMap getFiledStateMap(String operation, SimpleEntity record) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("id");
        excludeKeys.add("version");

        StateMap fieldState = new StateMap(SimpleEntityForm.class, new ArrayList<>(), excludeKeys);

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
