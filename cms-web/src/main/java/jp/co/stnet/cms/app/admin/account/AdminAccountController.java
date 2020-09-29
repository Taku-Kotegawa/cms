package jp.co.stnet.cms.app.admin.account;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.app.admin.account.AccountForm.Create;
import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.StateMap;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.common.datatables.OperationsUtil;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.service.authentication.AccountService;
import jp.co.stnet.cms.domain.service.authentication.AccountSharedService;
import jp.co.stnet.cms.domain.service.authentication.PasswordChangeService;
import jp.co.stnet.cms.domain.service.authentication.UnlockService;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/account")
@TransactionTokenCheck("admin/account")
public final class AdminAccountController {

    private final String BASE_PATH = "/admin/account/";
    private final String JSP_LIST = "admin/account/list";
    private final String JSP_FORM = "admin/account/form";

    @Autowired
    AccountService accountService;

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    UnlockService unlockService;

    @Autowired
    PasswordChangeService passwordChangeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Mapper beanMapper;

    @ModelAttribute
    private AccountForm setUp() {
        return new AccountForm();
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
    public DataTablesOutput<AccountListBean> getListJson(@Validated DataTablesInput input) {

        OperationsUtil op = new OperationsUtil("");

        List<AccountListBean> list = new ArrayList<>();
        Page<Account> accountPage = accountService.findPageByInput(input);

        for (Account account : accountPage.getContent()) {
            AccountListBean accountListBean = beanMapper.map(account, AccountListBean.class);
            accountListBean.setOperations(op.getToggleButton(account.getUsername()));
            accountListBean.setDT_RowId(account.getUsername());
            list.add(accountListBean);
        }

        DataTablesOutput<AccountListBean> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(accountPage.getTotalElements());

        return output;
    }

    // ---------------- 新規登録 -----------------------------------------------------

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(AccountForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) String copy) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (!StringUtils.isEmpty(copy)) {
            Account source = accountService.findById(copy);
            beanMapper.map(source, form);
            form.setUsername(null);
        }

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(Constants.OPERATION.CREATE, null);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(Constants.OPERATION.CREATE, null);
        model.addAttribute("fieldState", filedState.asMap());

        return JSP_FORM;
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({Create.class, Default.class}) AccountForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        Account account = beanMapper.map(form, Account.class);
        account.setStatus(Constants.STATUS.VALID);
        account.setPassword(passwordEncoder.encode(form.getPassword()));

        try {
            accountService.save(account);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0001);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(account.getUsername());
    }

    // ---------------- 編集 ---------------------------------------------------------

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{username}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(AccountForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        // DBからデータ取得し、modelとformにセット
        Account account = accountService.findById(username);
        beanMapper.map(account, form);
        form.setPassword(null);
        model.addAttribute("account", account);

        // 添付フィアルの情報をセット
        FileManaged fileManaged = fileManagedSharedService.findByUuid(account.getImageUuid());
        model.addAttribute("imageFileManaged", fileManaged);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(Constants.OPERATION.UPDATE, account);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(Constants.OPERATION.UPDATE, account);
        model.addAttribute("fieldState", filedState.asMap());

        return JSP_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{username}/update")
    @TransactionTokenCheck
    public String update(@Validated({AccountForm.Update.class, Default.class}) AccountForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, username);
        }

        Account account = accountService.findById(username);
        beanMapper.map(form, account);

        // パスワード欄が入力された場合にパスワード設定
        if (!StringUtils.isEmpty(form.getPassword())) {
            passwordChangeService.updatePassword(form.getUsername(), form.getPassword());
        }

        try {
            accountService.save(account);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, username);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0004);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(account.getUsername());
    }

    // ---------------- 削除 ---------------------------------------------------------

    /**
     * 削除
     */
    @GetMapping(value = "{username}/delete")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.DELETE, loggedInUser);

        try {
            accountService.delete(username);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0007);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getListUrl();
    }

    // ---------------- 無効化 ---------------------------------------------------------

    @GetMapping(value = "{username}/invalid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String invalid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.INVALID, loggedInUser);

        try {
            accountService.invalid(username);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getViewUrl(username);
    }

    // ---------------- 参照 ---------------------------------------------------------

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{username}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        Account account = accountService.findById(username);
        model.addAttribute("account", account);

        // ロック状態確認
        model.addAttribute("isLocked", accountSharedService.isLocked(username));

        // 添付フィアルの情報をセット
        FileManaged fileManaged = fileManagedSharedService.findByUuid(account.getImageUuid());
        model.addAttribute("imageFileManaged", fileManaged);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(Constants.OPERATION.VIEW, account);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(Constants.OPERATION.VIEW, account);
        model.addAttribute("fieldState", filedState.asMap());

        return JSP_FORM;
    }

    // ---------------- ロック解除 ---------------------------------------------------------

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{username}/unlock")
    public String unlock(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("username") String username) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

        // 存在しなければ例外
        Account account = accountService.findById(username);

        // ロック解除
        unlockService.unlock(username);

        // 参照画面へ
        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getViewUrl(username);
    }

    // ---------------- ダウンロード -----------------------------------------------

    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        accountService.hasAuthority(Constants.OPERATION.UPDATE, loggedInUser);

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
    private StateMap getButtonStateMap(String operation, Account record) {

        if (record == null) {
            record = new Account();
        }

        List<String> includeKeys = new ArrayList<>();
        includeKeys.add(Constants.BUTTON.GOTOLIST);
        includeKeys.add(Constants.BUTTON.GOTOUPDATE);
        includeKeys.add(Constants.BUTTON.VIEW);
        includeKeys.add(Constants.BUTTON.SAVE);
        includeKeys.add(Constants.BUTTON.INVALID);
        includeKeys.add(Constants.BUTTON.DELETE);
        includeKeys.add(Constants.BUTTON.UNLOCK);

        StateMap stateMap = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        stateMap.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            stateMap.setViewTrue(Constants.BUTTON.SAVE);
        }

        // 編集
        if (Constants.OPERATION.UPDATE.equals(operation)) {

            if (Constants.STATUS.VALID.equals(record.getStatus())) {
                stateMap.setViewTrue(Constants.BUTTON.SAVE);
                stateMap.setViewTrue(Constants.BUTTON.VIEW);
            }

            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setViewTrue(Constants.BUTTON.VIEW);
                stateMap.setViewTrue(Constants.BUTTON.DELETE);
            }

        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {

            // スタータスが公開時
            if (Constants.STATUS.VALID.equals(record.getStatus())) {
                stateMap.setViewTrue(Constants.BUTTON.GOTOUPDATE);
                stateMap.setViewTrue(Constants.BUTTON.INVALID);
                stateMap.setViewTrue(Constants.BUTTON.DELETE);
                stateMap.setViewTrue(Constants.BUTTON.UNLOCK);
            }

            // スタータスが無効
            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setViewTrue(Constants.BUTTON.DELETE);
            }
        }

        return stateMap;
    }

    /**
     * @param operation
     * @param record
     * @return
     */
    private StateMap getFiledStateMap(String operation, Account record) {
        List<String> excludeKeys = new ArrayList<>();

        // 常設の隠しフィールドは状態管理しない

        StateMap stateMap = new StateMap(AccountForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            stateMap.setInputTrueAll();
        }

        // 編集
        if (Constants.OPERATION.UPDATE.equals(operation)) {
            stateMap.setInputTrueAll();
            stateMap.setReadOnlyTrue("username");

            // スタータスが無効
            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setReadOnlyTrueAll();
            }
        }

        // 参照
        if (Constants.OPERATION.VIEW.equals(operation)) {
            stateMap.setViewTrueAll();
            stateMap.setViewFalse("password");
        }

        return stateMap;
    }
}