package jp.co.stnet.cms.domain.service.permission;

import jp.co.stnet.cms.domain.model.authentication.Permission;
import jp.co.stnet.cms.domain.model.authentication.PermissionRole;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    Map<String, Map<String, Boolean>> findAllMap();

    void deleteAll();

    List<PermissionRole> saveAll(Map<String, Map<String, Boolean>> map);

}
