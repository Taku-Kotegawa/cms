package jp.co.stnet.cms.domain.repository.example;

import jp.co.stnet.cms.domain.model.example.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @Test
    void test_002() {
        Person person = target.findById(1L).orElse(null);
        person.setAge(12);
        target.save(person);
    }

    @Test
    void test_003() {
        Person person = target.findById(1L).orElse(null);
        person.setId(null);
        person.setVersion(null);
        person.setAge(13);
        target.save(person);
    }


}