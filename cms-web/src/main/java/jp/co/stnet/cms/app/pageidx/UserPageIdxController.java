package jp.co.stnet.cms.app.pageidx;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.app.person.PersonSearchRow;
import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.model.report.PageIdxCriteria;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.report.DocumentService;
import jp.co.stnet.cms.domain.service.report.PageIdxService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.gfw.common.codelist.CodeList;

import javax.inject.Named;
import java.text.NumberFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("pageidx")
public class UserPageIdxController {

    // JSPのパス設定
    private final String BASE_PATH = "pageidx";
    private final String JSP_SEARCH = BASE_PATH + "/search";

    @Autowired
    PageIdxService pageIdxService;

    @Autowired
    DocumentService documentService;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    @Named("CL_SHOP")
    CodeList shopCodeList;

    @Autowired
    Mapper beanMapper;


    @ModelAttribute
    public PageIdxCriteriaForm setUp() {
        return new PageIdxCriteriaForm();
    }

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "search")
    public String search(Model model, @Validated PageIdxCriteriaForm form, BindingResult bindingResult,
                         @PageableDefault(size = 5) Pageable pageable, @AuthenticationPrincipal LoggedInUser loggedInUser) {


        if (bindingResult.hasErrors()) {
            return JSP_SEARCH;
        }

        NumberFormat comFormat = NumberFormat.getNumberInstance();

        SearchResult<PageIdx> result = pageIdxService.search(beanMapper.map(form, PageIdxCriteria.class), pageable);

        Page<PageIdx> page = new PageImpl<>(result.hits(), pageable, result.total().hitCount());

        model.addAttribute("page", page);

        Map<String, Long> countsByShop = result.aggregation(AggregationKey.of("countsByShop"));
        Map<String, String> shopList = new LinkedHashMap<>();
        for(Map.Entry<String, Long> entry : countsByShop.entrySet()) {
            shopList.put(entry.getKey(), shopCodeList.asMap().get(entry.getKey()) + "<span class=\"badge badge-pill badge-secondary\">" + comFormat.format(entry.getValue()) + "</span>");
        }


        String query = "";
        if (form.getShopCodes() != null) {
            for (String shopCode : form.getShopCodes()) {
                query += "&shopCodes=" + shopCode;
            }
        }

        if (form.getYear() != null) {
            for (Integer year : form.getYear()) {
                query += "&year=" + year.toString();
            }
        }

        if (form.getPeriod() != null) {
            for (Integer period : form.getPeriod()) {
                query += "&period=" + period.toString();
            }
        }

        model.addAttribute("query", query);
        model.addAttribute("shopList", shopList);
        model.addAttribute("countsByYear", result.aggregation(AggregationKey.of("countsByYear")));
        model.addAttribute("countsByPeriod", result.aggregation(AggregationKey.of("countsByPeriod")));

        return JSP_SEARCH;
    }


}
