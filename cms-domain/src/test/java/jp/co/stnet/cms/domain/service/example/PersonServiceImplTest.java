package jp.co.stnet.cms.domain.service.example;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.model.example.Person;
import jp.co.stnet.cms.domain.repository.example.PersonRepository;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock(lenient = true)
    PersonRepository personRepository;

    @Mock(lenient = true)
    FileManagedSharedService fileManagedSharedService;

    @Mock(lenient = true)
    EntityManager entityManager;

    @Spy
    Mapper beanMapper = DozerBeanMapperBuilder.create().withMappingFiles("META-INF/dozer/dozer-localdate-mapping.xml").build();

    @InjectMocks
    PersonServiceImpl target;

    @Value("classpath*:/META-INF/dozer/**/*-mapping.xml")
    Resource[] resources;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {

    }

    @Nested
    class findById {

        @Test
        @DisplayName("[正常系]IDを指定してエンティティを取得できる。")
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
        @DisplayName("[異常系]指定したIDのエンティテイが存在しない場合、ResourceNotFoundExceptionをスローする。(対象データが見つかりません。)")
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

            // Optionalでnullを返す。
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


    /**
     * エンティティを作成する。
     *
     * @param Id ID
     * @return エンティティ
     */
    private Person createEntity(Long Id) {
        Person person = new Person();
        person.setId(Id);
        person.setStatus("");
        person.setName("");
        person.setAge(0);
        person.setCode("");
        person.setContent("");
        person.setAttachedFile01Uuid("");
        person.setAttachedFile01Managed(new FileManaged());
        person.setVersion(0L);
        person.setCreatedBy("");
        person.setLastModifiedBy("");
        person.setCreatedDate(LocalDateTime.now());
        person.setLastModifiedDate(LocalDateTime.now());

        return person;
    }

    @Nested
    class beforeSave {

        /*
         * テスト目的
         *  1.attachedFile01Uuidが変更になった場合、contentが更新されることを確認する
         *  2.contentの中から連続するホワイト文字を除去する
         *  3.ファイルの読み書きに失敗した場合、ビジネス例外をスローする
         *  4.オフィス文書からテキストの抽出に失敗した場合、ビジネス例外をスローする
         *
         * 1. のテストパターン
         *     保存されていた値  新たに保存する値　contentの更新
         * (1) null            null           更新しない
         * (2) null            uuid-1         更新する
         * (3) uuid-1          uuid-1         更新しない
         * (4) uuid-1          uuid-2         更新する
         * (5) uuid-2          null           nullで更新する。
         *
         * 2. のテストパターン
         * (1) 連続する半角スペース、タブ、改行コードを半角スペースに変換する
         */

        @Test
        @DisplayName("[正常系](1) null            null           更新しない")
        void test001() throws IOException, TikaException {
            //準備
            String uuid = null;

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid(uuid);
            entity.setContent(null);

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid(uuid);

            when(fileManagedSharedService.getContent(uuid)).thenReturn("content");

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isNull();
        }

        @Test
        @DisplayName("[正常系](2) null            uuid-1         更新する")
        void test002() throws IOException, TikaException {
            //準備
            final String UUID_1 = "uuid-1";

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid(UUID_1);
            entity.setContent(null);

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid(null);

            when(fileManagedSharedService.getContent(UUID_1)).thenReturn("content");

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isEqualTo("content");
        }

        @Test
        @DisplayName("[正常系](3) uuid-1          uuid-1         更新しない")
        void test003() throws IOException, TikaException {
            //準備
            final String UUID_1 = "uuid-1";

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid(UUID_1);
            entity.setContent(null);

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid(UUID_1);
            current.setContent("saved_content");

            when(fileManagedSharedService.getContent(UUID_1)).thenReturn("content");

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isEqualTo("saved_content");
        }

        @Test
        @DisplayName("[正常系](4) uuid-1          uuid-2         更新する")
        void test004() throws IOException, TikaException {
            //準備
            final String UUID_1 = "uuid-1";
            final String UUID_2 = "uuid-2";

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid(UUID_2);
            entity.setContent(null);

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid(UUID_1);
            current.setContent("saved_content");

            when(fileManagedSharedService.getContent(UUID_2)).thenReturn("new_content");

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isEqualTo("new_content");
        }

        @Test
        @DisplayName("[正常系](5) uuid-2          null           nullで更新する。")
        void test005() throws IOException, TikaException {
            //準備
            final String UUID_1 = null;
            final String UUID_2 = "uuid-2";

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid(UUID_1);
            entity.setContent("dummy_content"); // 本来はbeforeSaveより前にセットされることない

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid(UUID_2);
            current.setContent("saved_content");

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isNull();
        }

        @Test
        @DisplayName("[正常系](1)")
        void test101() throws IOException, TikaException {
            //準備
            final String CONTENT = "a b 　\n\r\n\tc  d";

            Person entity = createEntity(1L);
            entity.setAttachedFile01Uuid("1");

            Person current = createEntity(1L);
            current.setAttachedFile01Uuid("2");

            when(fileManagedSharedService.getContent("1")).thenReturn(CONTENT);

            //実行
            target.beforeSave(entity, current);

            //検証
            assertThat(entity.getContent()).isEqualTo("a b c d");
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("[正常系](1)新規登録")
        void test101() throws IOException, TikaException {
            //準備
            Person entity = createEntity(null);
            entity.setAttachedFile01Uuid(null);

            Person saved = createEntity(1L);
            saved.setAttachedFile01Uuid(null);

            when(personRepository.saveAndFlush(any())).thenReturn(saved);
            when(personRepository.findById(anyLong())).thenReturn(Optional.of(saved));

            //実行
            Person actual = target.save(entity);

            //検証
            assertThat(actual).isEqualTo(saved);
        }

    }

    @Nested
    class afterSave {
        @Test
        @DisplayName("[正常系]添付ファイルの削除できること")
        void test001() {
//            target.afterSave(null,null);
        }

    }

    @Nested
    class compareEntity {
        @Test
        @DisplayName("[正常系]エンティティの比較(1)")
        void test001() {
            //実行
            boolean actual = target.compareEntity(null, null);
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]エンティティの比較(2)")
        void test002() {
            //実行
            boolean actual = target.compareEntity(new Person(), null);
            //検証
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("[正常系]エンティティの比較(3)")
        void test003() {
            //実行
            boolean actual = target.compareEntity(null, new Person());
            //検証
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("[正常系]エンティティの比較(4)")
        void test004() {
            //実行
            boolean actual = target.compareEntity(new Person(), new Person());
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]エンティティの比較(5)")
        void test005() {
            //実行
            boolean actual = target.compareEntity(createEntity(1L), createEntity(1L));
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]エンティティの比較(6)")
        void test006() {
            //実行
            boolean actual = target.compareEntity(createEntity(1L), createEntity(2L));
            //検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class invalid_1 {
        @Test
        @DisplayName("[正常系]指定したIDのステータスがinValidに変更される。")
        void test001() {
            //準備
            Person expected = createEntity(1L);
            expected.setStatus(Status.VALID.getCodeValue());

            Person copy = createEntity(1L);
            copy.setStatus(Status.VALID.getCodeValue());

            Person invalid = createEntity(1L);
            invalid.setStatus(Status.INVALID.getCodeValue());


            when(personRepository.findById(1L)).thenReturn(
                    Optional.of(expected),
                    Optional.of(copy),
                    Optional.of(invalid));
            when(personRepository.saveAndFlush(any())).thenReturn(expected);

            //実行
            Person actual = target.invalid(1L);

            //検証
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[異常系]指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
        void test002() {
            //準備
            when(personRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                //実行
                target.invalid(1L);
            })
                    //検証
                    .isInstanceOf(ResourceNotFoundException.class);
        }

        @Test
        @DisplayName("[異常系]指定したIDのステータスがvalidでない場合、IllegalStateBusinessExceptionをスローする。")
        void test003() {
                //準備
                Person invalid = createEntity(1L);
                invalid.setStatus(Status.INVALID.getCodeValue());

                when(personRepository.findById(1L)).thenReturn(Optional.of(invalid));

                assertThatThrownBy(() -> {
                    //実行
                    target.invalid(1L);
                })
                        //検証
                        .isInstanceOf(IllegalStateBusinessException.class);
        }

    }

//    @Nested
//    class invalid_2 {
//
//        @Test
//        @DisplayName("[正常系]")
//        void test001() {
//            //準備
//            List<Long> ids = new ArrayList<>();
//            ids.add(1L);
//            ids.add(2L);
//
//            Person invalid1 = createEntity(1L);
//            invalid1.setStatus(Status.VALID.getCodeValue());
//            Person invalid2 = createEntity(2L);
//            invalid2.setStatus(Status.VALID.getCodeValue());
//
//            when(personRepository.findById(1L)).thenReturn(Optional.of(invalid1));
//            when(personRepository.findById(2L)).thenReturn(Optional.of(invalid2));
//
//            when(personRepository.saveAndFlush(any())).thenReturn(invalid1);
//            when(beanMapper.map(any(), any())).thenReturn(invalid1);
//
//            List<Person> actual = (List<Person>) target.invalid(ids);
//
//            assertThat(actual).size().isEqualTo(2);
//
//        }
//    }

    @Nested
    class valid {
        @Test
        @DisplayName("[正常系]指定したIDのステータスがValidに変更される。")
        void test001() throws IOException, TikaException {
            //準備
            Person expected_1st = createEntity(1L);
            expected_1st.setStatus(Status.INVALID.getCodeValue());

            Person expected_2nd = createEntity(1L);
            expected_2nd.setStatus(Status.INVALID.getCodeValue());

            Person validEntity = createEntity(1L);
            validEntity.setStatus(Status.VALID.getCodeValue());

            when(personRepository.findById(1L)).thenReturn(
                    Optional.of(expected_1st), // valid()
                    Optional.of(expected_2nd), // save() 保存前
                    Optional.of(validEntity)); // save() 保存後
            when(personRepository.saveAndFlush(any())).thenReturn(validEntity);
            when(fileManagedSharedService.getContent(expected_1st.getAttachedFile01Uuid())).thenReturn("");

            //実行
            Person actual = target.valid(1L);

            //検証
            assertThat(actual.getStatus()).isEqualTo(Status.VALID.getCodeValue());
        }

        @Test
        @DisplayName("[異常系]指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
        void test002() {
            //準備
            when(personRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                //実行
                target.valid(1L);
            })
                    //検証
                    .isInstanceOf(ResourceNotFoundException.class);
        }

        @Test
        @DisplayName("[異常系]指定したIDのステータスがinvalidでない場合、IllegalStateBusinessExceptionをスローする。")
        void test003() {
            //準備
            Person valid = createEntity(1L);
            valid.setStatus(Status.VALID.getCodeValue());

            when(personRepository.findById(1L)).thenReturn(Optional.of(valid));

            assertThatThrownBy(() -> {
                //実行
                target.valid(1L);
            })
                    //検証
                    .isInstanceOf(IllegalStateBusinessException.class);
        }
    }

    private FileManaged createFileManaged() {
        FileManaged fileManaged = new FileManaged();
        fileManaged.setId(0L);
        fileManaged.setUuid("");
        fileManaged.setOriginalFilename("");
        fileManaged.setUri("");
        fileManaged.setFileMime("");
        fileManaged.setFileSize(0L);
        fileManaged.setFileType("");
        fileManaged.setStatus("");
        fileManaged.setVersion(0L);
        fileManaged.setCreatedBy("");
        fileManaged.setLastModifiedBy("");
        fileManaged.setCreatedDate(LocalDateTime.now());
        fileManaged.setLastModifiedDate(LocalDateTime.now());

        return fileManaged;
    }

    @Nested
    class delete {
        @Test
        @DisplayName("[正常系]指定したIDのデータを削除する")
        void test001() {
            //準備
            Person expected = createEntity(1L);
            expected.setAttachedFile01Uuid("UUID-1");

            when(personRepository.findById(1L)).thenReturn(Optional.of(expected));
            when(fileManagedSharedService.findByUuid(expected.getAttachedFile01Uuid())).thenReturn(createFileManaged());
            doNothing().when(fileManagedSharedService).deleteFile("");

            //実行
            target.delete(1L);

        }

        @Test
        @DisplayName("[異常系]指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
        void test002() {
            //準備
            when(personRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                //実行
                target.delete(1L);
            })
                    //検証
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }


    @Nested
    class findPageByInput {
        @Test
        @DisplayName("[正常系] -- 保留 --")
        void test001() {
            //準備
//            DataTablesInput expected = new DataTablesInput();

            //実行
//            target.findPageByInput(expected);
        }

    }

    @Nested
    class getJPQLQuery {
    }

    @Nested
    class testGetJPQLQuery {
    }

    @Nested
    class isLocalDate {
        @Disabled
        @Test
        @DisplayName("[正常系]そのフィールドがLocalDateの場合、Trueを返す。(テストスキップ)")
        void test001() {

            // 実行
            Boolean actual = target.isLocalDate("xxxxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがLocalDateでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isLocalDate("not_date");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isLocalDateTime {
        @Test
        @DisplayName("[正常系]そのフィールドがLocalDateTimeの場合、Trueを返す。")
        void test001() {

            // 実行
            Boolean actual = target.isLocalDateTime("createdDate");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがLocalDateTimeでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isLocalDateTime("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isNumber {
        @Test
        @DisplayName("[正常系]そのフィールドがNumberのサブクラスの場合、Trueを返す。(Long)")
        void test001() {

            // 実行
            Boolean actual = target.isNumber("id");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがNumberのサブクラスの場合、Trueを返す。(Integer)")
        void test002() {

            // 実行
            Boolean actual = target.isNumber("age");

            // 検証
            assertThat(actual).isTrue();
        }
        @Test
        @DisplayName("[正常系]そのフィールドがNumberのサブクラスでない場合、Falseを返す。")
        void test003() {

            // 実行
            Boolean actual = target.isNumber("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isID {
        @Test
        @DisplayName("[正常系]そのフィールドが@Idをもつの場合、Trueを返す。")
        void test001() {

            // 実行
            Boolean actual = target.isId("id");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドが@Idを持たない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isId("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isCollection {
        @Disabled
        @Test
        @DisplayName("[正常系]そのフィールドがコレクションのサブクラスの場合、Trueを返す。(テストスキップ)")
        void test001() {

            // 実行
            Boolean actual = target.isCollection("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがコレクションのサブクラスでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isCollection("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isBoolean {
        @Disabled
        @Test
        @DisplayName("[正常系]そのフィールドがBooleanの場合、Trueを返す。(テストスキップ)")
        void test001() {

            // 実行
            Boolean actual = target.isBoolean("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがBooleanでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isBoolean("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class isEnum {
        @Disabled
        @Test
        @DisplayName("[正常系]そのフィールドがEnumの場合、Trueを返す。(テストスキップ)")
        void test001() {

            // 実行
            Boolean actual = target.isEnum("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]そのフィールドがEnumでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isEnum("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Disabled
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