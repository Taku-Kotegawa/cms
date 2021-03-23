package jp.co.stnet.cms.domain.common.auditing;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Data JPA Audit 機能を実装するクラス.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /**
     * @return {@code @CreatedBy, @LastModifiedBy} にセットするログインユーザ名。
     * ログインしていない場合は"unknown"を返す。
     */
    @SuppressWarnings("NullableProblems")
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