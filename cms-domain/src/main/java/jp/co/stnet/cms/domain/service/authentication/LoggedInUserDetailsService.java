package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.repository.authentication.AccountRepository;
import jp.co.stnet.cms.domain.repository.authentication.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class LoggedInUserDetailsService implements UserDetailsService {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountSharedService.findOne(username);

            if (account != null && account.getStatus().equals(false)) {
                throw new UsernameNotFoundException("user not found");
            }

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<String> roleIds = new ArrayList<>();

            if (!account.getRoles().isEmpty()) {
                for (String roleLabel : account.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
                    roleIds.add(roleLabel);
                }
            }
            for (String permission : roleRepository.findPermissions(roleIds)) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            return new LoggedInUser(account,
                    accountSharedService.isLocked(username),
                    accountSharedService.getLastLoginDate(username),
                    authorities);

        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("user not found", e);
        }
    }

}
