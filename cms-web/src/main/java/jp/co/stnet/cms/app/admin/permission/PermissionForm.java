package jp.co.stnet.cms.app.admin.permission;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class PermissionForm {

    private Map<String, Map<String, Boolean>> permissionRoles;

}
