package jp.co.stnet.cms.app.admin.account;


import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.service.authentication.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("admin/account")
public final class AdminAccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    Mapper beanMapper;

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model) {
        return "admin/account/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list/json", method = RequestMethod.GET)
    public DataTablesOutput<AccountListBean> getListJson(@Valid DataTablesInput input) {

        List<AccountListBean> list = new ArrayList<>();
        List<Account> accountList = accountService.findAllByInput(input);

        Page<Account> accountPage = accountService.findPageByInput(input);


        for(Account account : accountList) {
            AccountListBean accountListBean = beanMapper.map(account, AccountListBean.class);

            accountListBean.setOperations("<a href=\"http://www.stnet.co.jp\">参照</a>");
            accountListBean.setDT_RowId(account.getUsername() + "_");
            accountListBean.setDT_RowClass("abcclass");

            Map<String, String> attr = new HashMap<>();
            attr.put("width", "100px");
            accountListBean.setDT_RowAttr(attr);

            list.add(accountListBean);
        }

        DataTablesOutput<AccountListBean> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(accountPage.getTotalElements());

        return output;
    }


}
