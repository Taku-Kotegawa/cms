package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.PermissionRole;
import jp.co.stnet.cms.domain.model.authentication.PermissionRolePK;
import jp.co.stnet.cms.domain.model.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PermissionRoleRepository extends JpaRepository<PermissionRole, PermissionRolePK> {

//    @Query(nativeQuery = true, value = "SELECT DISTINCT PERMISSIONS_PERMISSION FROM ROLE_PERMISSION WHERE ROLE_ROLE IN (:roles)")
//    List<String> findPermissions(@Param("roles") List<String> roles);

//    List<PermissionRole> findAllByPermissionAndRole(Permission permission, Role role);

    List<PermissionRole> findByRoleIn(Collection<Role> roles);

}