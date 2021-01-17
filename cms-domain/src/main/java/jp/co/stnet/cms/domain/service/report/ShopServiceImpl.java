package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.Permission;
import jp.co.stnet.cms.domain.model.authentication.Role;
import jp.co.stnet.cms.domain.model.report.Shop;
import jp.co.stnet.cms.domain.repository.report.ShopRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ShopServiceImpl extends AbstractNodeService<Shop, Long> implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Override
    protected JpaRepository<Shop, Long> getRepository() {
        return this.shopRepository;
    }

    @Override
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {

        log.info(loggedInUser.getAuthorities().toString());

        loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority(Permission.VIEW_OWN_NODE.name()));

        return true;
    }

    @Override
    protected boolean isFilterINClause(String fieldName) {
        if ("status".equals(fieldName)) {
            return true;
        }
        return false;
    }

}
