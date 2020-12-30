package jp.co.stnet.cms.domain.model.authentication;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


class PermissionRoleTest {

    @Test
    void test_001() {
        Map<String, Map<String, Object>> map = new HashMap<>();
        Arrays.asList(Permission.values())
                .forEach(e -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("value", e.name());
                    item.put("label", e.getLabel());
                    item.put("category", e.getCategory());
                    map.put(e.name(), item);
                });

        System.out.println(map);
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCodeLabel() {
    }

    @Test
    void getCodeValue() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getWait() {
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}