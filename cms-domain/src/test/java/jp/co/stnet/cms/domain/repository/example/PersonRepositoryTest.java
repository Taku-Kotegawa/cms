package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;

@SpringJUnitConfig(locations = {"classpath:test-context.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PersonRepositoryTest {

    @Autowired
    PersonRepository target;

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
                .build()
        );

        target.findAll();
    }

}