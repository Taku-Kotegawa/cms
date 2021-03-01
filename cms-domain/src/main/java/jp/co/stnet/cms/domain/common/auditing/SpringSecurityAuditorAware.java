package jp.co.stnet.cms.domain.common.auditing;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        try {
            LoggedInUser loggedInUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(loggedInUser.getUsername());

        } catch (Exception e) {
            return Optional.of("unknown");

        }
    }
}