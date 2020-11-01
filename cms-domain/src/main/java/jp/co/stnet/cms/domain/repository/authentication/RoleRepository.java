package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT PERMISSIONS_PERMISSION FROM ROLE_PERMISSION WHERE ROLE_ROLE IN (:roles)")
    List<String> findPermissions(@Param("roles") List<String> roles);

}
