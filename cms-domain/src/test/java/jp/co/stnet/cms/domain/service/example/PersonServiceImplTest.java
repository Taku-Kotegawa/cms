package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.example.Person;
import jp.co.stnet.cms.domain.repository.example.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl target;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
//        when(personRepository.findAll()).thenReturn(null);
    }

    @AfterEach
    void tearDown() {

    }


    @Nested
    class findById {

        @Test
        @DisplayName("[正常系]")
        void test001() {
            //準備
            Person expected = new Person();
            expected.setId(1L);
            expected.setStatus("");
            expected.setName("");
            expected.setAge(0);
            expected.setCode("");
            expected.setContent("");
            expected.setAttachedFile01Uuid("");
            expected.setAttachedFile01Managed(new FileManaged());
            expected.setVersion(0L);
            expected.setCreatedBy("");
            expected.setLastModifiedBy("");
            expected.setCreatedDate(LocalDateTime.now());
            expected.setLastModifiedDate(LocalDateTime.now());

            when(personRepository.findById(1L)).thenReturn(Optional.of(expected));

            //実行
            Person actual = target.findById(1L);

            //検証
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[異常系]")
        void test002() {
            //準備
            Person expected = new Person();
            expected.setId(1L);
            expected.setStatus("");
            expected.setName("");
            expected.setAge(0);
            expected.setCode("");
            expected.setContent("");
            expected.setAttachedFile01Uuid("");
            expected.setAttachedFile01Managed(new FileManaged());
            expected.setVersion(0L);
            expected.setCreatedBy("");
            expected.setLastModifiedBy("");
            expected.setCreatedDate(LocalDateTime.now());
            expected.setLastModifiedDate(LocalDateTime.now());

            when(personRepository.findById(1L)).thenReturn(Optional.empty());
//            when(personRepository.findById(1l)).thenReturn(Optional.ofNullable(null)); // 上の行と同じ効果を発揮


            assertThatThrownBy(() -> {
                //実行
                target.findById(1L);
            })
                    //検証
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.valueOf(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, 1)));
        }


    }

    @Nested
    class beforeSave {
    }

    @Nested
    class save {
    }

    @Nested
    class compareEntity {
    }

    @Nested
    class testSave {
    }

    @Nested
    class invalid {
    }

    @Nested
    class testInvalid {
    }

    @Nested
    class valid {
    }

    @Nested
    class testValid {
    }

    @Nested
    class delete {
    }

    @Nested
    class testDelete {
    }

    @Nested
    class findPageByInput {
    }

    @Nested
    class getJPQLQuery {
    }

    @Nested
    class testGetJPQLQuery {
    }

    @Nested
    class isDate {
    }

    @Nested
    class isDateTime {
    }

    @Nested
    class isNumeric {
    }

    @Nested
    class isCollection {
    }

    @Nested
    class isBoolean {
    }

    @Nested
    class isEnum {
    }

    @Nested
    class getEnumListByName {
    }

    @Nested
    class convertColumnName {
    }

    @Nested
    class replacedColumnName {
    }

    @Nested
    class isCollectionElement {
    }

    @Nested
    class isRelation {
    }

    @Nested
    class isFilterINClause {
    }

    @Nested
    class getRelationEntity {
    }

    @Nested
    class getPageable {
    }

    @Nested
    class getRepository {
    }

    @Nested
    class testSave1 {
    }

    @Nested
    class testDelete1 {
    }

    @Nested
    class hasAuthority {
    }

    @Nested
    class test1 {
    }

    @Nested
    class search {
    }

    @Nested
    class testIsFilterINClause {
    }

    @Nested
    class highlight {
    }
}