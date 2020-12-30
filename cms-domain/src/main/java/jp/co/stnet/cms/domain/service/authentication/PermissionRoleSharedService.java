package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.PermissionRole;
import jp.co.stnet.cms.domain.model.authentication.Role;

import java.util.Collection;
import java.util.List;

public interface PermissionRoleSharedService {

    List<PermissionRole> findAllByRole(Collection<String> roleIds);
}
