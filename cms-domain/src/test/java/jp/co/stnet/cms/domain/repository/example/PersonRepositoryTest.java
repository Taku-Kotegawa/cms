package jp.co.stnet.cms.domain.repository.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//@SpringJUnitConfig()
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PersonRepositoryTest {

    @Autowired
    PersonRepository target;
//
//    @Autowired
//    Person2Repository target2;

    @AfterAll
    static void tearDown() {
    }

    @BeforeAll
    void setUp() {
    }

    @BeforeEach
    void setUpEach() {
    }

    @AfterEach
    void tearDownEach() {
    }

    @Test
    void test_001() {
//        target.save(
//                Person.builder()
//                .name("taku.kotegawa")
//                .age(11)
//                .status("1")
//                .build()
//        );


    }

    @Test
    void test_002() {
//        target2.save(
//                Person2.builder()
//                .name("person2")
//                .age(12)
//                .memo("person2")
//                .status("1")
//                .build()
//        );


    }


}