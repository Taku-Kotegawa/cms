package jp.co.stnet.cms.app.admin.index;

import jp.co.stnet.cms.domain.service.common.IndexSharedService;
import jp.co.stnet.cms.domain.service.example.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/index")
public class ManageController {

    @Autowired
    IndexSharedService indexSharedService;

    @Autowired
    PersonService personService;

    @GetMapping("manage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String manage(Model model) {
        return "admin/index/manage";
    }


    @GetMapping("view")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String view(Model model, @RequestParam String term) {

        personService.test(term);


        return "admin/index/manage";
    }


    @GetMapping("{entityName}/reindexing")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String reindexing(Model model, @PathVariable() String entityName) {


        try {
            indexSharedService.reindexing(entityName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return "redirect:/admin/index/manage";
    }

}
