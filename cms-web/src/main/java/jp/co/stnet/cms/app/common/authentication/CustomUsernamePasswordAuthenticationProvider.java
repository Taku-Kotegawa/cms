package jp.co.stnet.cms.app.common.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.PermissionRole;
import jp.co.stnet.cms.domain.model.authentication.Role;
import jp.co.stnet.cms.domain.service.authentication.AccountSharedService;
import jp.co.stnet.cms.domain.service.authentication.PermissionRoleSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CustomUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    PermissionRoleSharedService permissionRoleSharedService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);

        LoggedInUser loggedInUser = (LoggedInUser) userDetails;
        Account account = loggedInUser.getAccount();

        CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthentication =
                (CustomUsernamePasswordAuthenticationToken) authentication;

        // ログイン画面の「Login As Administrator」にチェックが入っている場合、ADMINロールを保持している必要がある。
        if (customUsernamePasswordAuthentication.getLoginAsAdministrator()) {
            if (!account.getRoles().contains(Role.ADMIN.name())) {
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials"));
            }
        }

    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {

        CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthentication =
                (CustomUsernamePasswordAuthenticationToken) authentication;

        Boolean loginAsAdministrator = customUsernamePasswordAuthentication.getLoginAsAdministrator();

        Account account = ((LoggedInUser) user).getAccount();

        Collection<GrantedAuthority> authorities = new HashSet<>();

        List<String> roleIds = new ArrayList<>();

        for (String roleLabel : account.getRoles()) {

            // Administrator権限でログインしない場合、ADMINロールを除外
            // asAdmin=true, role=admin -> true
            // asAdmin=false, role=admin -> false
            // asAdmin=true, role=other -> true
            // asAdmin=false, role=other -> true
            if (!(!customUsernamePasswordAuthentication.getLoginAsAdministrator() && roleLabel.equals(Role.ADMIN.name()))) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
                roleIds.add(roleLabel);
            }
        }

        for (PermissionRole permissionRole : permissionRoleSharedService.findAllByRole(roleIds)) {
            authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission().name()));
        }

        return new CustomUsernamePasswordAuthenticationToken(user,
                authentication.getCredentials(), loginAsAdministrator,
                authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
