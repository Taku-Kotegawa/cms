package jp.co.stnet.cms.app.welcome;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.model.common.VariableType;
import jp.co.stnet.cms.domain.repository.common.VariableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HelloController {

    @Autowired
    VariableRepository variableRepository;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Locale locale, Model model) {

        String shortMessage = "";
        List<Variable> variableList = variableRepository.findByType(VariableType.SHORT_MESSAGE.name());
        variableList = variableList.stream().sorted(Comparator.comparing(Variable::getCode).reversed()).collect(Collectors.toList());

        for (Variable v : variableList) {
            if (v.getStatus().equals(Status.VALID.getCodeValue())
                && (v.getDate1() == null || v.getDate1().compareTo(LocalDate.now()) <= 0)
                && (v.getDate2() == null || v.getDate2().compareTo(LocalDate.now()) >= 0)
                && StringUtils.isNotEmpty(v.getTextarea())) {
                if (!shortMessage.isEmpty()) {
                    shortMessage = shortMessage + "\n";
                }
                shortMessage =  shortMessage + v.getTextarea();
            }
        }

        model.addAttribute("shortMessage", shortMessage);

        return "welcome/home";
    }

}
