package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
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
        target.save(
                Person.builder()
                .name("taku.kotegawa")
                .age(11)
                .status("1")
                .build()
        );


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