package jp.co.stnet.cms.app.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class menuController {

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String menu(Model model) {
        return "admin/menu";
    }

}
