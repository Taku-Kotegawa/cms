package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@Transactional
class SimpleEntityRevisionRepositoryTest {

    @Autowired
    SimpleEntityRepository target;

    @Test
    void test_001() {

        List<String> text05 = new ArrayList<>();
        text05.add("a");
        text05.add("b");

        SimpleEntity actual = target.save(
                SimpleEntity.builder()
                        .text05(text05)
                        .build()
        );

        System.out.println(actual.toString());


        SimpleEntity actual2 = target.findById(1l).orElse(null);
        System.out.println(actual2.toString());


    }

}