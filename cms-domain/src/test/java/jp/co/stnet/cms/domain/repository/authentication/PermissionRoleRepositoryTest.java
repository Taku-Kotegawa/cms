package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.Permission;
import jp.co.stnet.cms.domain.model.authentication.PermissionRole;
import jp.co.stnet.cms.domain.model.authentication.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = {"classpath:test-context.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
class PermissionRoleRepositoryTest {

    @Autowired
    PermissionRoleRepository target;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test001() {

        target.save(
                PermissionRole.builder().permission(Permission.VIEW_ALL_NODE).role(Role.ADMIN).build()
        );

    }

}