package jp.co.stnet.cms.app.common.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.PermissionRole;
import jp.co.stnet.cms.domain.service.authentication.AccountService;
import jp.co.stnet.cms.domain.service.authentication.AccountSharedService;
import jp.co.stnet.cms.domain.service.authentication.PermissionRoleSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationUserDetailService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    PermissionRoleSharedService permissionRoleSharedService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        // フィルタで取得したAuthorizationヘッダの値
        String credential = token.getCredentials().toString();

        credential = credential.replace("Bearer ", "");

        // 空の場合は認証エラーとする
        if (credential.isEmpty()) {
            throw new UsernameNotFoundException("Authorization header must not be empty.");
        }

        Account account = accountService.findByApiKey(credential);

        if (account == null) {
            throw new UsernameNotFoundException("Invalid authorization header.");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roleIds = new ArrayList<>();

        for (String roleLabel : account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
            roleIds.add(roleLabel);
        }

        for (PermissionRole permissionRole : permissionRoleSharedService.findAllByRole(roleIds)) {
            authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission().name()));
        }


        return new LoggedInUser(account,
                accountSharedService.isLocked(account.getUsername()),
                accountSharedService.getLastLoginDate(account.getUsername()),
                authorities);

    }
}