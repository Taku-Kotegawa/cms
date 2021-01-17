package jp.co.stnet.cms.app.admin.pageidx;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.app.admin.pageidx.PageIdxForm.Create;
import jp.co.stnet.cms.app.admin.pageidx.PageIdxForm.Update;
import jp.co.stnet.cms.app.admin.upload.UploadForm;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInputDraft;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.common.scheduled.CsvUtils;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.report.DocumentService;
import jp.co.stnet.cms.domain.service.report.PageIdxService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
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
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/pageidx")
@TransactionTokenCheck("pageidx")
public class PageIdxController {

    // JSPのパス設定
    private final String BASE_PATH = "admin/pageidx";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";
    private final String JSP_UPLOAD_FORM = "upload/form";
    private final String JSP_UPLOAD_COMPLETE = "upload/complete";
    private final String JSP_SEARCH = BASE_PATH + "/search";

    // CSV/Excelのファイル名(拡張子除く)
    private final String DOWNLOAD_FILENAME = "pageidx";

    // アップロード用のインポートジョブID
    private final String UPLOAD_JOB_ID = "job03";

    @Autowired
    PageIdxService pageIdxService;

    @Autowired
    DocumentService documentService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private PageIdxForm setUp() {
        return new PageIdxForm();
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
     * @param input DataTablesからの要求(Server-side処理)
     * @return JSON
     */
    @ResponseBody
    @GetMapping(value = "/list/json")
    public DataTablesOutput<PageIdxListRow> listJson(@Validated DataTablesInputDraft input) {

        List<PageIdxListRow> listRows = new ArrayList<>();
        List<PageIdx> pageIdxList = new ArrayList<>();
        Long recordsFiltered = 0L;

        if (input.getDraft()) { // 下書き含む最新
            Page<PageIdx> pageIdxPage = pageIdxService.findPageByInput(input);
            pageIdxList.addAll(pageIdxPage.getContent());
            recordsFiltered = pageIdxPage.getTotalElements();

        } else {
//            Page<PageIdxRevision> pageIdxPage2 = pageIdxService.findMaxRevPageByInput(input);
//            for (PageIdxRevision pageIdxRevision : pageIdxPage2.getContent()) {
//                pageIdxList.add(beanMapper.map(pageIdxRevision, PageIdx.class));
//            }
//            recordsFiltered = pageIdxPage2.getTotalElements();
        }

        for (PageIdxBean bean : getBeanList(pageIdxList)) {
            PageIdxListRow pageIdxListRow = beanMapper.map(bean, PageIdxListRow.class);
            pageIdxListRow.setOperations(getToggleButton(bean.getId().toString(), op(null)));
            pageIdxListRow.setDT_RowId(bean.getId().toString());
            // ステータスラベル
            pageIdxListRow.setStatusLabel(Status.getByValue(bean.getStatus()).getCodeLabel());
            listRows.add(pageIdxListRow);
        }

        DataTablesOutput<PageIdxListRow> output = new DataTablesOutput<>();
        output.setData(listRows);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(recordsFiltered);

        return output;
    }

    /**
     * CSVファイルのダウンロード
     *
     * @param input DataTablesからの要求(Server-side処理)
     * @param model モデル
     * @return ファイルダウンロード用View
     */
    @GetMapping(value = "/list/csv")
    public String listCsv(@Validated DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.CSV.MAX_LENGTH);
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getCsvDefault());
        model.addAttribute("csvFileName", DOWNLOAD_FILENAME + ".csv");
        return "csvDownloadView";
    }

    /**
     * TSVファイルのダウンロード
     *
     * @param input DataTablesからの要求(Server-side処理)
     * @param model モデル
     * @return ファイルダウンロード用View
     */
    @GetMapping(value = "/list/tsv")
    public String listTsv(@Validated DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.CSV.MAX_LENGTH);
        setModelForCsv(input, model);
        model.addAttribute("csvConfig", CsvUtils.getTsvDefault());
        model.addAttribute("csvFileName", DOWNLOAD_FILENAME + ".tsv");
        return "csvDownloadView";
    }

    /**
     * Excelファイルのダウンロード
     *
     * @param input DataTablesからの要求(Server-side処理)
     * @param model モデル
     * @return ファイルダウンロード用View
     */
    @GetMapping(value = "/list/excel")
    public String listExcel(@Validated DataTablesInputDraft input, Model model) {
        input.setStart(0);
        input.setLength(Constants.EXCEL.MAX_LENGTH);
        // TODO: 汎用的な仕組みに改造が必要
        model.addAttribute("list", pageIdxService.findPageByInput(input).getContent());
        model.addAttribute("excelFileName", DOWNLOAD_FILENAME + ".xlsx");
        return "excelDownloadView";
    }

    /**
     * csvDownloadViewに渡すデータ準備
     *
     * @param input DataTablesからの要求(Server-side処理)
     * @param model データをセットするモデル
     */
    private void setModelForCsv(DataTablesInputDraft input, Model model) {

        List<PageIdxCsvBean> csvBeans = new ArrayList<>();
        List<PageIdx> pageIdxList = new ArrayList<>();

        if (input.getDraft() == null || input.getDraft()) { // 下書き含む最新
            Page<PageIdx> pageIdxPage = pageIdxService.findPageByInput(input);
            pageIdxList.addAll(pageIdxPage.getContent());

        } else {
//            Page<PageIdxRevision> pageIdxPage2 = pageIdxService.findMaxRevPageByInput(input);
//            for (PageIdxRevision pageIdxRevision : pageIdxPage2.getContent()) {
//                pageIdxList.add(beanMapper.map(pageIdxRevision, PageIdx.class));
//            }
        }

        for (PageIdx pageIdx : getBeanList(pageIdxList)) {
            PageIdxCsvBean row = beanMapper.map(pageIdx, PageIdxCsvBean.class);
            customMap(row, pageIdx);
            row.setStatusLabel(Status.getByValue(pageIdx.getStatus()).getCodeLabel());
            csvBeans.add(row);
        }

        model.addAttribute("exportCsvData", csvBeans);
        model.addAttribute("class", PageIdxCsvBean.class);
    }

    /**
     * 一覧画面、CSVファイルのためのデータ変換
     *
     * @param entities
     * @return
     */
    private List<PageIdxBean> getBeanList(List<PageIdx> entities) {
        List<PageIdxBean> beans = new ArrayList<>();
        for (PageIdx entity : entities) {
            PageIdxBean bean = beanMapper.map(entity, PageIdxBean.class);

            beans.add(bean);
        }
        return beans;
    }

    /**
     * CSVファイル固有要件のデータ変換
     *
     * @param row     変換後のCSVビーン
     * @param pageIdx エンティティ
     */
    private void customMap(PageIdxCsvBean row, PageIdx pageIdx) {

    }

//    /**
//     * リビジョンテーブルのデータを通常のエンティティに変換
//     * @param entities リビジョンエンティティのリスト
//     * @return エンティティのリスト
//     */
//    private List<PageIdxBean> getBeanListByRev(List<PageIdxRevision> entities) {
//        List<PageIdx> beans = new ArrayList<>();
//        for(PageIdxRevision entity : entities) {
//            PageIdx bean = beanMapper.map(entities, PageIdx.class);
//            beans.add(bean);
//        }
//        return getBeanList(beans);
//    }

    /**
     * 一覧画面のトグルボタンHTMLの生成
     *
     * @param id エンティティの内部ID
     * @param op OperationsUtil リンクURLを生成するクラス
     * @return HTML
     */
    private String getToggleButton(String id, OperationsUtil op) {

        // fixedColumnを使うとトグルボタンは使えない。
//        StringBuffer link = new StringBuffer();
//        link.append("<div class=\"whitespace-nowrap\">");
//        link.append("<a class=\"whitespace-nowrap\" href=\"" + op.getEditUrl(id) + "\">" + op.getLABEL_EDIT() + "</a>");
//        link.append(" | ");
//        link.append("<a class=\"whitespace-nowrap\" href=\"" + op.getViewUrl(id) + "\">" + op.getLABEL_VIEW() + "</a></li>");
//        link.append("</div>");

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

    private OperationsUtil op() {
        return new OperationsUtil(BASE_PATH);
    }

    private OperationsUtil op(String param) {
        return new OperationsUtil(param);
    }

    /**
     * ダウンロード
     */
    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        pageIdxService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        model.addAttribute(fileManagedSharedService.findByUuid(uuid));
        return "fileManagedDownloadView";
    }

    /**
     * 一括削除
     */
    @PostMapping("bulk_delete")
    public String bulkDelete(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        pageIdxService.hasAuthority(Constants.OPERATION.BULK_DELETE, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<PageIdx> deleteEntities = new ArrayList<>();
        for (String key : strKeys) {
            PageIdx entity = pageIdxService.findById(Long.valueOf(key));
            if (entity.getStatus().equals(Status.INVALID.getCodeValue())) {
                deleteEntities.add(entity);
            }
        }

        pageIdxService.delete(deleteEntities);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0003));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 一括無効化(有効データのみ対象)
     */
    @PostMapping("bulk_invalid")
    public String bulkInvalid(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        pageIdxService.hasAuthority(Constants.OPERATION.BULK_INVALID, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<Long> ids = new ArrayList<>();
        for (String key : strKeys) {
            Long id = Long.valueOf(key);
            PageIdx entity = pageIdxService.findById(id);
            if (entity.getStatus().equals(Status.VALID.getCodeValue())) {
                ids.add(id);
            }
        }

        pageIdxService.invalid(ids);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 一括有効化(無効データのみ対象)
     */
    @PostMapping("bulk_valid")
    public String bulkValid(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        pageIdxService.hasAuthority(Constants.OPERATION.BULK_VALID, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<Long> ids = new ArrayList<>();
        for (String key : strKeys) {
            Long id = Long.valueOf(key);
            PageIdx entity = pageIdxService.findById(id);
            if (entity.getStatus().equals(Status.INVALID.getCodeValue())) {
                ids.add(id);
            }
        }

        pageIdxService.valid(ids);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(PageIdxForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        pageIdxService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            PageIdx source = pageIdxService.findById(copy);
            beanMapper.map(source, form);
            form.setId(null);
        }

        setFileManagedToForm(form);

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.CREATE, null).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * UUIDからFileManagedオブジェクトを取得し、formにセットする。
     *
     * @param form フォーム
     */
    private void setFileManagedToForm(PageIdxForm form) {
        // TODO ファイルフィールドごとに調整
//        if (form.getAttachedFile01Uuid() != null) {
//            form.setAttachedFile01Managed(fileManagedSharedService.findByUuid(form.getAttachedFile01Uuid()));
//        }
    }

//    /**
//     * UUIDからFileManagedオブジェクトを取得し、Entityにセットする。
//     * @param entity エンティティ
//     */
//    private void setFileManagedToEntity(PageIdx entity) {
//        // TODO ファイルフィールドごとに調整
//        if (entity.getAttachedFile01Uuid() != null) {
//            entity.setAttachedFile01Managed(fileManagedSharedService.findByUuid(entity.getAttachedFile01Uuid()));
//        }
//    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({Create.class, Default.class}) PageIdxForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        pageIdxService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        documentService.findById(form.getDocumentId()); // TODO エラーエッセー時の表示

        PageIdx pageIdx = beanMapper.map(form, PageIdx.class);

        try {
            if ("true".equals(saveDraft)) {
                pageIdx.setStatus(Status.VALID.getCodeValue());
//                pageIdxService.saveDraft(pageIdx);
            } else {
                pageIdx.setStatus(Status.VALID.getCodeValue());
                pageIdxService.save(pageIdx);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0001));

        return "redirect:" + op().getEditUrl(pageIdx.getId().toString());
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(PageIdxForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        pageIdxService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        PageIdx pageIdx = pageIdxService.findById(id);
        model.addAttribute("pageIdx", pageIdx);

        // 状態=無効の場合、参照画面に強制遷移
        if (pageIdx.getStatus().equals(Status.INVALID.getCodeValue())) {
            model.addAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0008));
            return view(model, loggedInUser, id, null);
        }

        // 入力チェック再表示の場合、formの情報をDBの値で上書きしない
        if (form.getVersion() == null) {
            beanMapper.map(pageIdx, form);
        }

        setFileManagedToForm(form);

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, pageIdx).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, pageIdx).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({Update.class, Default.class}) PageIdxForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        pageIdxService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        documentService.findById(form.getDocumentId()); // TODO エラーエッセー時の表示

        PageIdx pageIdx = beanMapper.map(form, PageIdx.class);

        try {
            if ("true".equals(saveDraft)) {
//                pageIdxService.saveDraft(pageIdx);
            } else {
                pageIdx.setStatus(Status.VALID.getCodeValue());
                pageIdxService.save(pageIdx);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0004));

        return "redirect:" + op().getEditUrl(pageIdx.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        pageIdxService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            pageIdxService.delete(id);
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

        pageIdxService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        PageIdx entity = pageIdxService.findById(id);

        try {
            entity = pageIdxService.invalid(id);
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

        pageIdxService.hasAuthority(Constants.OPERATION.VALID, loggedInUser);

        // 存在チェックを兼ねる
        PageIdx entity = pageIdxService.findById(id);

        try {
            entity = pageIdxService.valid(id);
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
//        pageIdxService.hasAuthority(Constants.OPERATION.CANCEL_DRAFT, loggedInUser);
//
//        // 存在チェックを兼ねる
//        PageIdx entity = pageIdxService.findById(id);
//
//        try {
//            entity = pageIdxService.cancelDraft(id);
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

        pageIdxService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // PageIdx pageIdx = pageIdxService.findById(id);　// TODO: リビジョン管理がない場合はシンプルにできる

        PageIdx pageIdx = null;

        if (rev == null) {
            // 下書きを含む最新
            pageIdx = pageIdxService.findById(id);

        } else if (rev == 0) {
            // 有効な最新リビジョン
//            pageIdx = beanMapper.map(pageIdxService.findByIdLatestRev(id), PageIdx.class);

        } else {
            // リビジョン番号指定
//            pageIdx = beanMapper.map(pageIdxService.findByRid(rev), PageIdx.class);
        }

//        setFileManagedToEntity(pageIdx);

        model.addAttribute("pageIdx", pageIdx);

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, pageIdx).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, pageIdx).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * ボタンの状態設定
     */
    private StateMap getButtonStateMap(String operation, PageIdx record) {

        if (record == null) {
            record = new PageIdx();
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
        includeKeys.add(Constants.BUTTON.COPY);

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
                buttonState.setViewTrue(Constants.BUTTON.COPY);
            }

            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
//                buttonState.setViewTrue(Constants.BUTTON.SAVE_DRAFT);
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
                buttonState.setViewTrue(Constants.BUTTON.COPY);
            }

            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.VALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
                buttonState.setViewTrue(Constants.BUTTON.COPY);
            }

        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {

            // スタータスが公開時
            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.GOTOUPDATE);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
                buttonState.setViewTrue(Constants.BUTTON.UNLOCK);
                buttonState.setViewTrue(Constants.BUTTON.COPY);
            }

            // スタータスが無効
            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.VALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
                buttonState.setViewTrue(Constants.BUTTON.COPY);
            }
        }

        return buttonState;
    }

    /**
     * フィールドの状態設定
     */
    private StateMap getFiledStateMap(String operation, PageIdx record) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("id");
        excludeKeys.add("version");

        StateMap fieldState = new StateMap(PageIdxForm.class, new ArrayList<>(), excludeKeys);

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

    /**
     * アップロードファイル指定画面の表示
     */
    @GetMapping(value = "upload", params = "form")
    public String uploadForm(@ModelAttribute UploadForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser) {

        form.setJobName("job03");

        FileManaged uploadFileManaged = fileManagedSharedService.findByUuid(form.getUploadFileUuid());
        form.setUploadFileManaged(uploadFileManaged);

        model.addAttribute("pageTitle", "Import PageIdx");
        model.addAttribute("referer", "list");

        return JSP_UPLOAD_FORM;
    }

    /**
     * アップロード処理(バッチ実行)
     */
    @PostMapping(value = "upload")
    public String upload(@Validated UploadForm form, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        final String jobName = UPLOAD_JOB_ID;
        Long jobExecutionId = null;

        if (result.hasErrors()) {
            return uploadForm(form, model, loggedInUser);
        }

        FileManaged uploadFile = fileManagedSharedService.findByUuid(form.getUploadFileUuid());
        String uploadFileAbsolutePath = fileManagedSharedService.getFileStoreBaseDir() + uploadFile.getUri();
        String jobParams = "inputFile=" + uploadFileAbsolutePath;


        if (!jobName.equals(form.getJobName())) {
            return uploadForm(form, model, loggedInUser);
        }


        try {
            jobExecutionId = jobOperator.start(jobName, jobParams);

        } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
            e.printStackTrace();

            // メッセージをセットして、フォーム画面に戻る。

        }

        redirectAttributes.addAttribute("jobName", jobName);
        redirectAttributes.addAttribute("jobExecutionId", jobExecutionId);

        return "redirect:upload?complete";
    }

    /**
     * アップロード完了画面
     */
    @GetMapping(value = "upload", params = "complete")
    public String uploadComplete(Model model, @RequestParam Map<String, String> params, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        model.addAttribute("returnBackBtn", "一覧画面に戻る");
        model.addAttribute("returnBackUrl", op().getListUrl());
        model.addAttribute("jobName", params.get("jobName"));
        model.addAttribute("jobExecutionId", params.get("jobExecutionId"));
        return JSP_UPLOAD_COMPLETE;
    }

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "search")
    public String search(Model model) {
        return JSP_SEARCH;
    }

    /**
     * (search)DataTables用のJSONの作成
     *
     * @param input DataTablesからの要求(Server-side処理)
     * @return JSON
     */
    @ResponseBody
    @GetMapping(value = "/search/json")
    public DataTablesOutput<PageIdxListRow> searchJson(@Validated DataTablesInputDraft input) {

        List<PageIdxListRow> listRows = new ArrayList<>();
        SearchResult<PageIdx> searchResult = pageIdxService.searchByInput(input);

        for (PageIdx bean : searchResult.hits()) {
            PageIdxListRow pageIdxListRow = beanMapper.map(bean, PageIdxListRow.class);
            pageIdxListRow.setDT_RowId(bean.getId().toString());
            listRows.add(pageIdxListRow);
        }

        DataTablesOutput<PageIdxListRow> output = new DataTablesOutput<>();
        output.setData(listRows);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(searchResult.total().hitCount());

        return output;
    }




}
