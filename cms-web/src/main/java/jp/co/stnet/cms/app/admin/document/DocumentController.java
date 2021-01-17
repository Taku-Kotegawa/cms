package jp.co.stnet.cms.app.admin.document;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.app.admin.document.DocumentForm.Create;
import jp.co.stnet.cms.app.admin.document.DocumentForm.Update;
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
import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.model.report.Report;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.example.MyBatchService;
import jp.co.stnet.cms.domain.service.report.DocumentService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("admin/document")
@TransactionTokenCheck("document")
public class DocumentController {

    // JSPのパス設定
    private final String BASE_PATH = "admin/document";
    private final String JSP_LIST = BASE_PATH + "/list";
    private final String JSP_FORM = BASE_PATH + "/form";
    private final String JSP_VIEW = BASE_PATH + "/view";
    private final String JSP_UPLOAD_FORM = "upload/form";
    private final String JSP_UPLOAD_COMPLETE = "upload/complete";

    // CSV/Excelのファイル名(拡張子除く)
    private final String DOWNLOAD_FILENAME = "document";

    // アップロード用のインポートジョブID
    private final String UPLOAD_JOB_ID = "job03";

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
    private MyBatchService service; // 非同期処理のサンプル

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private DocumentForm setUp() {
        return new DocumentForm();
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
    public DataTablesOutput<DocumentListRow> listJson(@Validated DataTablesInputDraft input) {

        List<DocumentListRow> listRows = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();
        Long recordsFiltered = 0L;

        if (input.getDraft()) { // 下書き含む最新
            Page<Document> documentPage = documentService.findPageByInput(input);
            documentList.addAll(documentPage.getContent());
            recordsFiltered = documentPage.getTotalElements();

        } else {
//            Page<DocumentRevision> documentPage2 = documentService.findMaxRevPageByInput(input);
//            for (DocumentRevision documentRevision : documentPage2.getContent()) {
//                documentList.add(beanMapper.map(documentRevision, Document.class));
//            }
//            recordsFiltered = documentPage2.getTotalElements();
        }

        for (DocumentBean bean : getBeanList(documentList)) {
            DocumentListRow documentListRow = beanMapper.map(bean, DocumentListRow.class);
            documentListRow.setOperations(getToggleButton(bean.getId().toString(), op(null)));
            documentListRow.setDT_RowId(bean.getId().toString());
            // ステータスラベル
            documentListRow.setStatusLabel(Status.getByValue(bean.getStatus()).getCodeLabel());
            listRows.add(documentListRow);
        }

        DataTablesOutput<DocumentListRow> output = new DataTablesOutput<>();
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
        model.addAttribute("list", documentService.findPageByInput(input).getContent());
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

        List<DocumentCsvBean> csvBeans = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();

        if (input.getDraft() == null || input.getDraft()) { // 下書き含む最新
            Page<Document> documentPage = documentService.findPageByInput(input);
            documentList.addAll(documentPage.getContent());

        } else {
//            Page<DocumentRevision> documentPage2 = documentService.findMaxRevPageByInput(input);
//            for (DocumentRevision documentRevision : documentPage2.getContent()) {
//                documentList.add(beanMapper.map(documentRevision, Document.class));
//            }
        }

        for (Document document : getBeanList(documentList)) {
            DocumentCsvBean row = beanMapper.map(document, DocumentCsvBean.class);
            customMap(row, document);
            row.setStatusLabel(Status.getByValue(document.getStatus()).getCodeLabel());

            csvBeans.add(row);
        }

        model.addAttribute("exportCsvData", csvBeans);
        model.addAttribute("class", DocumentCsvBean.class);
    }

    /**
     * 一覧画面、CSVファイルのためのデータ変換
     *
     * @param entities
     * @return
     */
    private List<DocumentBean> getBeanList(List<Document> entities) {
        List<DocumentBean> beans = new ArrayList<>();
        for (Document entity : entities) {
            DocumentBean bean = beanMapper.map(entity, DocumentBean.class);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * CSVファイル固有要件のデータ変換
     *
     * @param row      変換後のCSVビーン
     * @param document エンティティ
     */
    private void customMap(DocumentCsvBean row, Document document) {

    }

//    /**
//     * リビジョンテーブルのデータを通常のエンティティに変換
//     * @param entities リビジョンエンティティのリスト
//     * @return エンティティのリスト
//     */
//    private List<DocumentBean> getBeanListByRev(List<DocumentRevision> entities) {
//        List<Document> beans = new ArrayList<>();
//        for(DocumentRevision entity : entities) {
//            Document bean = beanMapper.map(entities, Document.class);
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

        documentService.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        model.addAttribute(fileManagedSharedService.findByUuid(uuid));
        return "fileManagedDownloadView";
    }

    /**
     * 一括削除
     */
    @PostMapping("bulk_delete")
    public String bulkDelete(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        documentService.hasAuthority(Constants.OPERATION.BULK_DELETE, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<Document> deleteEntities = new ArrayList<>();
        for (String key : strKeys) {
            Document entity = documentService.findById(Long.valueOf(key));
            if (entity.getStatus().equals(Status.INVALID.getCodeValue())) {
                deleteEntities.add(entity);
            }
        }

        documentService.delete(deleteEntities);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0003));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 一括無効化(有効データのみ対象)
     */
    @PostMapping("bulk_invalid")
    public String bulkInvalid(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        documentService.hasAuthority(Constants.OPERATION.BULK_INVALID, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<Long> ids = new ArrayList<>();
        for (String key : strKeys) {
            Long id = Long.valueOf(key);
            Document entity = documentService.findById(id);
            if (entity.getStatus().equals(Status.VALID.getCodeValue())) {
                ids.add(id);
            }
        }

        documentService.invalid(ids);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 一括有効化(無効データのみ対象)
     */
    @PostMapping("bulk_valid")
    public String bulkValid(Model model, String selectedKey, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        documentService.hasAuthority(Constants.OPERATION.BULK_VALID, loggedInUser);

        String[] strKeys = selectedKey.split(",");
        List<Long> ids = new ArrayList<>();
        for (String key : strKeys) {
            Long id = Long.valueOf(key);
            Document entity = documentService.findById(id);
            if (entity.getStatus().equals(Status.INVALID.getCodeValue())) {
                ids.add(id);
            }
        }

        documentService.valid(ids);

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0002));
        return "redirect:" + op().getListUrl();
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(DocumentForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        documentService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            Document source = documentService.findById(copy);
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
    private void setFileManagedToForm(DocumentForm form) {
        // TODO ファイルフィールドごとに調整
        if (form.getAttachedFileUuid() != null) {
            form.setAttachedFileManaged(fileManagedSharedService.findByUuid(form.getAttachedFileUuid()));
        }
    }

//    /**
//     * UUIDからFileManagedオブジェクトを取得し、Entityにセットする。
//     * @param entity エンティティ
//     */
//    private void setFileManagedToEntity(Document entity) {
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
    public String create(@Validated({Create.class, Default.class}) DocumentForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        documentService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        Document document = beanMapper.map(form, Document.class);

        // レポートコード -> Enum
//        document.setReport(Report.valueOf(form.getReportCode()));


        try {
            if ("true".equals(saveDraft)) {
//                document.setStatus(Status.VALID.getCodeValue());
//                documentService.saveDraft(document);
            } else {
                document.setStatus(Status.VALID.getCodeValue());
                documentService.save(document);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0001));

        return "redirect:" + op().getEditUrl(document.getId().toString());
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(DocumentForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {

        documentService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        Document document = documentService.findById(id);
        model.addAttribute("document", document);

        // 状態=無効の場合、参照画面に強制遷移
        if (document.getStatus().equals(Status.INVALID.getCodeValue())) {
            model.addAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0008));
            return view(model, loggedInUser, id, null);
        }

        // 入力チェック再表示の場合、formの情報をDBの値で上書きしない
        if (form.getVersion() == null) {
            beanMapper.map(document, form);
        }

        setFileManagedToForm(form);

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.SAVE, document).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.SAVE, document).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({Update.class, Default.class}) DocumentForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "saveDraft", required = false) String saveDraft) {

        documentService.hasAuthority(Constants.OPERATION.SAVE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        Document document = beanMapper.map(form, Document.class);

        try {
            if ("true".equals(saveDraft)) {
//                documentService.saveDraft(document);
            } else {
                document.setStatus(Status.VALID.getCodeValue());
                documentService.save(document);
            }
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        redirect.addFlashAttribute(ResultMessages.info().add(MessageKeys.I_CM_FW_0004));

        return "redirect:" + op().getEditUrl(document.getId().toString());
    }

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        documentService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            documentService.delete(id);
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

        documentService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        Document entity = documentService.findById(id);

        try {
            entity = documentService.invalid(id);
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

        documentService.hasAuthority(Constants.OPERATION.VALID, loggedInUser);

        // 存在チェックを兼ねる
        Document entity = documentService.findById(id);

        try {
            entity = documentService.valid(id);
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
//        documentService.hasAuthority(Constants.OPERATION.CANCEL_DRAFT, loggedInUser);
//
//        // 存在チェックを兼ねる
//        Document entity = documentService.findById(id);
//
//        try {
//            entity = documentService.cancelDraft(id);
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

        documentService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // Document document = documentService.findById(id);　// TODO: リビジョン管理がない場合はシンプルにできる

        Document document = null;

        if (rev == null) {
            // 下書きを含む最新
            document = documentService.findById(id);

        } else if (rev == 0) {
            // 有効な最新リビジョン
//            document = beanMapper.map(documentService.findByIdLatestRev(id), Document.class);

        } else {
            // リビジョン番号指定
//            document = beanMapper.map(documentService.findByRid(rev), Document.class);
        }

//        setFileManagedToEntity(document);

        model.addAttribute("document", document);

        model.addAttribute("buttonState", getButtonStateMap(Constants.OPERATION.VIEW, document).asMap());
        model.addAttribute("fieldState", getFiledStateMap(Constants.OPERATION.VIEW, document).asMap());
        model.addAttribute("op", op());

        return JSP_FORM;
    }

    /**
     * ボタンの状態設定
     */
    private StateMap getButtonStateMap(String operation, Document record) {

        if (record == null) {
            record = new Document();
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
    private StateMap getFiledStateMap(String operation, Document record) {

        // 常設の隠しフィールドは状態管理しない
        List<String> excludeKeys = new ArrayList<>();
        excludeKeys.add("id");
        excludeKeys.add("version");

        StateMap fieldState = new StateMap(DocumentForm.class, new ArrayList<>(), excludeKeys);

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

        model.addAttribute("pageTitle", "Import Document");
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

}
