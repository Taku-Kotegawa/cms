package jp.co.stnet.cms.app.authentication.emailchange;

import jp.co.stnet.cms.app.authentication.passwordchange.PasswordChangeForm;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.service.authentication.EmailChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("emailchange")
public class EmailChangeController {

    @Autowired
    EmailChangeService emailChangeService;

    @ModelAttribute
    public EmailChangeForm setUpForm() {
        return new EmailChangeForm();
    }

    @ModelAttribute
    public EmailChangeTokenForm setUpTokenForm() {
        return new EmailChangeTokenForm();
    }

    @GetMapping("form")
    public String showForm() {
        return "emailchange/form";
    }

    @PostMapping
    public String request(@Validated EmailChangeForm form,BindingResult bindingResult, Model model,
                          RedirectAttributes attributes, @AuthenticationPrincipal LoggedInUser loggedInUser){

        if (bindingResult.hasErrors()) {
            return showForm();
        }

        String token = emailChangeService.createAndSendMailChangeRequest(loggedInUser.getUsername(), form.getNewEmail());

        attributes.addAttribute("token", token);

        return "redirect:/emailchange/formToken";
    }

    @GetMapping("formToken")
    public String showFormToken() {
        return "emailchange/formToken";
    }

    @PostMapping(params = "token")
    public String change(@Validated EmailChangeForm form,BindingResult bindingResult, Model model,
                         @AuthenticationPrincipal LoggedInUser userDetails){


        return "redirect:/emailchange?complete";
    }

    @GetMapping(params = "complete")
    public String complete() {
        return "emailchange/complate";
    }



}
