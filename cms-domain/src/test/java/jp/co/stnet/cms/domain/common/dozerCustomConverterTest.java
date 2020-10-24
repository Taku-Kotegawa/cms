package jp.co.stnet.cms.domain.common;

import com.github.dozermapper.core.Mapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class dozerCustomConverterTest {

    @Autowired
    Mapper beanMapper;

    @Test
    public void test001() {

        A a = new A();

        List<String> fieldA = new ArrayList<>();
        fieldA.add("aaa");
        fieldA.add("bbb");
        a.setFieldA(fieldA);
        System.out.println(a);

        B b = beanMapper.map(a, B.class);
//        System.out.println(b);

    }

    @Data
    class A {
        private List fieldA;
    }

    @Data
    class B {
        private String fieldA;
    }

}
