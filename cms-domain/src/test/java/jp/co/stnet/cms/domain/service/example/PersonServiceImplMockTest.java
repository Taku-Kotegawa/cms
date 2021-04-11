package jp.co.stnet.cms.domain.service.example;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import jp.co.stnet.cms.domain.common.datatables.Column;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.common.datatables.Order;
import jp.co.stnet.cms.domain.common.datatables.Search;
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
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class PersonServiceImplMockTest {

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

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {

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

    DataTablesInput createInput() {
        DataTablesInput input = new DataTablesInput();
        input.setDraw(0);
        input.setStart(0);
        input.setLength(0);
        input.setSearch(new Search());
        input.setOrder(Lists.newArrayList());
        input.setColumns(
                createColumns(
                        // DataTablesに設定するカラムのキーを並べる
                        "id", "status", "name", "age", "code", "content", "attachedFile01Uuid", "attachedFile01Managed.originalFilename")
        );
        return input;
    }

    Order createOrder(int no) {
        Order order = new Order();
        order.setColumn(no);
        order.setDir("asc");
        return order;
    }

    Order createOrder(int no, String dir) {
        Order order = new Order();
        order.setColumn(no);
        order.setDir(dir);
        return order;
    }

    List<Order> createOrders(int... columnNos) {
        List<Order> orders = new ArrayList<>();
        for (int no : columnNos) {
            orders.add(createOrder(no));
        }
        return orders;
    }

    List<Column> createColumns(String... fieldNames) {
        List<Column> columns = new ArrayList<>();
        for (String fieldName : fieldNames) {
            Column column = new Column();
            column.setSearch(new Search());
            column.getSearch().setRegex(false);
            column.setSearchValue("");
            column.setData(fieldName);
            column.setName("");
            column.setSearchable(true);
            column.setOrderable(true);
            columns.add(column);
        }
        return columns;
    }

    @Nested
    class findById {
        @Test
        @DisplayName("[正] 指定したIDでエンティティを取得する。")
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
        @DisplayName("[異] 指定したIDが存在しない場合、ResourceNotFoundExceptionをスローする。(メッセージ=対象データが見つかりません。)")
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

    @SuppressWarnings("ConstantConditions")
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
        @DisplayName("[正] attachedFile01Uuidが変更になった場合、contentを更新する。(1)保存されていた値=null, 新たに保存する値=null, 期待結果=更新しない")
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
        @DisplayName("[正] attachedFile01Uuidが変更になった場合、contentを更新する。(2)保存されていた値=null, 新たに保存する値=uuid-1, 期待結果=更新する")
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
        @DisplayName("[正] attachedFile01Uuidが変更になった場合、contentを更新する。(3)保存されていた値=uuid-1, 新たに保存する値=uuid-1, 期待結果=更新しない")
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
        @DisplayName("[正] attachedFile01Uuidが変更になった場合、contentを更新する。(4)保存されていた値=uuid-1, 新たに保存する値=uuid-2, 期待結果=更新する")
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
        @DisplayName("[正] attachedFile01Uuidが変更になった場合、contentを更新する。(5)保存されていた値=uuid-2, 新たに保存する値=null, 期待結果=更新する")
        void test005() {
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
        @DisplayName("[正] 連続する半角スペース、タブ、改行コードを半角スペースに変換する。")
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
        @DisplayName("[正] エンティティを保存する。(1)新規登録")
        void test101() {
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

    @Disabled
    @Nested
    class afterSave {
        @Test
        @DisplayName("[正] 添付ファイルの削除する。(Mockテストでは何も検証できないので省略)")
        void test001() {
        }
    }

    @Nested
    class compareEntity {
        @Test
        @DisplayName("[正] エンティティの比較(1) null - null")
        void test001() {
            //実行
            boolean actual = target.compareEntity(null, null);
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] エンティティの比較(2) Person - null")
        void test002() {
            //実行
            boolean actual = target.compareEntity(new Person(), null);
            //検証
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("[正] エンティティの比較(3) null - Person")
        void test003() {
            //実行
            boolean actual = target.compareEntity(null, new Person());
            //検証
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("[正] エンティティの比較(4) Person - Person")
        void test004() {
            //実行
            boolean actual = target.compareEntity(new Person(), new Person());
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] エンティティの比較(5) Person(1L) - Person(1L)")
        void test005() {
            //実行
            boolean actual = target.compareEntity(createEntity(1L), createEntity(1L));
            //検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] エンティティの比較(6) Person(1L) - Person(2L)")
        void test006() {
            //実行
            boolean actual = target.compareEntity(createEntity(1L), createEntity(2L));
            //検証
            assertThat(actual).isFalse();
        }
    }

    @SuppressWarnings("unchecked")
    @Nested
    class invalid_1 {
        @Test
        @DisplayName("[正] 指定したIDのステータスをinvalidに変更する。")
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
        @DisplayName("[異] 指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
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
        @DisplayName("[異] 指定したIDのステータスがvalidでない場合、IllegalStateBusinessExceptionをスローする。")
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

    @SuppressWarnings("unchecked")
    @Nested
    class valid {
        @Test
        @DisplayName("[正] 指定したIDのステータスをvalidに変更する。")
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
        @DisplayName("[異] 指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
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
        @DisplayName("[異] 指定したIDのステータスがinvalidでない場合、IllegalStateBusinessExceptionをスローする。")
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

    @Nested
    class delete {
        @Test
        @DisplayName("[正] 指定したIDを削除する")
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
        @DisplayName("[異] 指定したIDが存在しない場合は、ResourceNotFoundExceptionをスローする。")
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

    @Disabled
    @Nested
    @DisplayName("findPageByInputはMockテストを省略(リポジトリに大きく依存するため)")
    class findPageByInput {
        @Test
        @DisplayName("[正] -- 保留 --")
        void test001() {
            //準備
//            DataTablesInput expected = new DataTablesInput();

            //実行
//            target.findPageByInput(expected);
        }

    }

    @Nested
    class isLocalDate {
        @Disabled
        @Test
        @DisplayName("[正] 指定したフィールドがLocalDateの場合、Trueを返す。(該当フィールドが存在しないため、テストスキップ)")
        void test001() {
            // 実行
            Boolean actual = target.isLocalDate("xxxxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[異] 指定したフィールドがLocalDateでない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドがLocalDateTimeの場合、Trueを返す。")
        void test001() {

            // 実行
            Boolean actual = target.isLocalDateTime("createdDate");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがLocalDateTimeでない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドがNumberのサブクラスの場合、Trueを返す。(Long)")
        void test001() {
            // 実行
            Boolean actual = target.isNumber("id");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがNumberのサブクラスの場合、Trueを返す。(Integer)")
        void test002() {
            // 実行
            Boolean actual = target.isNumber("age");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがNumberのサブクラスでない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドが@Idをもつの場合、Trueを返す。")
        void test001() {
            // 実行
            Boolean actual = target.isId("id");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドが@Idを持たない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドがコレクションのサブクラスの場合、Trueを返す。(該当フィールドが存在しないため、テストスキップ)")
        void test001() {
            // 実行
            Boolean actual = target.isCollection("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがコレクションのサブクラスでない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドがBooleanの場合、Trueを返す。(該当フィールドが存在しないため、テストスキップ)")
        void test001() {
            // 実行
            Boolean actual = target.isBoolean("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがBooleanでない場合、Falseを返す。")
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
        @DisplayName("[正] 指定したフィールドがEnumの場合、Trueを返す。(該当フィールドが存在しないため、テストスキップ)")
        void test001() {

            // 実行
            Boolean actual = target.isEnum("xxx");

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正] 指定したフィールドがEnumでない場合、Falseを返す。")
        void test002() {

            // 実行
            Boolean actual = target.isEnum("createdBy");

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Disabled
    @Nested
    @DisplayName("getEnumListByName (該当フィールドが存在しないためテストスキップ)")
    class getEnumListByName {
    }

    @Nested
    class convertColumnName {
        @Test
        @DisplayName("[正] Labelで終わる文字列からLabelを除去する")
        void test001() {
            //実行
            String actual = target.convertColumnName("testLabel");

            //検証
            assertThat(actual).isEqualTo("test");
        }

        @Test
        @DisplayName("[正] Labelの大文字小文字が正確でないと除去されない")
        void test002() {
            //実行
            String actual = target.convertColumnName("testLABEL");

            //検証
            assertThat(actual).isEqualTo("testLABEL");
        }

        @Test
        @DisplayName("[正] Labelの位置は末尾でなければ除去されない")
        void test003() {
            //実行
            String actual = target.convertColumnName("testLabela");

            //検証
            assertThat(actual).isEqualTo("testLabela");
        }
    }

    @Nested
    class replacedColumnName {
        @Test
        @DisplayName("[正] '.' を '_' に置換する。")
        void test001() {
            //実行
            String actual = target.replacedColumnName("t.e.s.t");

            //検証
            assertThat(actual).isEqualTo("t_e_s_t");
        }
    }

    @Disabled
    @Nested
    @DisplayName("isCollectionElementは該当フィールドが存在しないため、テストスキップ")
    class isCollectionElement {
    }

    @Nested
    class isRelation {
        @Test
        @DisplayName("[正] 指定したフィールドが結合先のエンティティのフィールドの場合、Trueを返す。(フィールド名は「エンティティ.フィールド名」形式)")
        void test001() {
            // 実行
            boolean actual = target.isRelation("attachedFile01Managed.originalFilename");

            // 検証
            assertThat(actual).isEqualTo(true);
        }

        @Test
        @DisplayName("[正] 指定したフィールドが結合先のエンティティのフィールドでない場合、Falseを返す。(フィールド名は「エンティティ.フィールド名」形式)")
        void test002() {
            // 実行
            boolean actual = target.isRelation("originalFilename");

            // 検証
            assertThat(actual).isEqualTo(false);
        }
    }

    @Nested
    class isFilterINClause {
        @Test
        @DisplayName("[正] 指定したフィールドがINで検索する対象の場合、Trueを返す。")
        void test001() {
            // 実行
            boolean actual = target.isFilterINClause("status");

            // 検証
            assertThat(actual).isEqualTo(true);
        }

        @Test
        @DisplayName("[正] 指定したフィールドがINで検索する対象でない場合、Falseを返す。")
        void test002() {
            // 実行
            boolean actual = target.isFilterINClause("dummy");

            // 検証
            assertThat(actual).isEqualTo(false);
        }
    }

    @Nested
    class getRelationEntity {
        @Test
        @DisplayName("[正] 指定したフィールドが結合先のフィールドの場合、エンティティ名を返す。")
        void test001() {
            // 実行
            String actual = target.getRelationEntity("attachedFile01Managed.originalFilename");

            // 検証
            assertThat(actual).isEqualTo("attachedFile01Managed");
        }

        @Test
        @DisplayName("[正] 指定したフィールドが結合先のフィールドでない場合、nullを返す。")
        void test002() {
            // 実行
            String actual = target.getRelationEntity("originalFilename");

            // 検証
            assertThat(actual).isNull();
        }
    }

    @Nested
    class getOrderClause {
        @Test
        @DisplayName("[正] Orderが未指定の場合、空白を返す。")
        void test001() {
            //準備
            DataTablesInput input = createInput();

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isBlank();
        }

        @Test
        @DisplayName("[正] Orderが指定されていた場合、OrderBy句文字列を返す。(1)１つ指定")
        void test002() {
            //準備
            DataTablesInput input = createInput();
            input.setOrder(createOrders(0));

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" ORDER BY c.id asc");
        }

        @Test
        @DisplayName("[正] Orderが指定されていた場合、OrderBy句文字列を返す。(2)複数指定")
        void test003() {
            //準備
            DataTablesInput input = createInput();
            input.setOrder(createOrders(0, 1));

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" ORDER BY c.id asc,c.status asc");
        }

        @Test
        @DisplayName("[正] Orderが指定されていた場合、OrderBy句文字列を返す。(3)列番号で指定")
        void test004() {
            //準備
            DataTablesInput input = createInput();
            input.setOrder(createOrders(0, 4));

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" ORDER BY c.id asc,c.code asc");
        }

        @Test
        @DisplayName("[正] Orderが指定されていた場合、OrderBy句文字列を返す。(4)ソートの優先順位を指定")
        void test005() {
            //準備
            DataTablesInput input = createInput();
            input.setOrder(createOrders(4, 0));

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" ORDER BY c.code asc,c.id asc");
        }

        @Test
        @DisplayName("[正] Orderが指定されていた場合、OrderBy句文字列を返す。(5)昇順降順を指定")
        void test006() {
            //準備
            DataTablesInput input = createInput();
            input.setOrder(new ArrayList<>());
            input.getOrder().add(createOrder(2, "desc"));
            input.getOrder().add(createOrder(5, "asc"));
            input.getOrder().add(createOrder(7, "desc"));

            // 実行
            StringBuilder actual = target.getOrderClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" ORDER BY c.name desc,c.content asc,c.attachedFile01Managed.originalFilename desc");
        }
    }

    @Nested
    class getFieldFilterWhereClause {
        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(1)検索条件なしの場合、空白文字列を返す。")
        void test001() {
            //準備
            DataTablesInput input = createInput();

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(input.getColumns().get(0));

            // 検証
            assertThat(actual.toString()).isBlank();
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(2)キー")
        void test002() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(0);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.id = :id");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(3)INで検索するもの")
        void test003() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(1);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.status IN (:status)");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(4)LIKEで部分一致検索をするもの")
        void test004() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(2);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.name LIKE :name ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(5)数値型をLIKEで部分一致検索をするもの")
        void test005() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(3);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND function('CONVERT', c.age, CHAR) LIKE :age ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(6)LIKEで部分一致検索をするもの")
        void test006() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(4);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.code LIKE :code ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(7)LIKEで部分一致検索をするもの")
        void test007() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(5);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.content LIKE :content ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(8)LIKEで部分一致検索をするもの")
        void test008() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(6);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.attachedFile01Uuid LIKE :attachedFile01Uuid ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールドフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(9)LIKEで部分一致検索をするもの")
        void test009() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(7);
            column.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getFieldFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" AND c.attachedFile01Managed.originalFilename LIKE :attachedFile01Managed_originalFilename ESCAPE '~'");
        }
    }

    @Nested
    class getGlobalFilterWhereClause {
        @Test
        @DisplayName("[正] グローバルフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(1)キー")
        void test001() {
            //準備
            DataTablesInput input = createInput();
            Column column = input.getColumns().get(0);

            // 実行
            StringBuilder actual = target.getGlobalFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" OR function('CONVERT', c.id, CHAR) LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] グローバルフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(2)LIKE検索")
        void test002() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc");
            Column column = input.getColumns().get(1);

            // 実行
            StringBuilder actual = target.getGlobalFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" OR c.status LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] グローバルフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(2)LIKE検索")
        void test003() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc");
            Column column = input.getColumns().get(2);

            // 実行
            StringBuilder actual = target.getGlobalFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" OR c.name LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] グローバルフィルターに検索条件が指定された場合、該当フィールドを絞り込む等号文字列を返す。(3)数値をLIKE検索")
        void test004() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc");
            Column column = input.getColumns().get(3);

            // 実行
            StringBuilder actual = target.getGlobalFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo(" OR function('CONVERT', c.age, CHAR) LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] フィールド単位の設定で検索不可(Searchable=false)に指定されているフィールドは、等号文字列を作成せず空白文字列を返す。")
        void test005() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc");
            Column column = input.getColumns().get(3);
            column.setSearchable(false);

            // 実行
            StringBuilder actual = target.getGlobalFilterWhereClause(column);

            // 検証
            assertThat(actual.toString()).isEqualTo("");
        }
    }

    @Nested
    class getWhereClause {
        @Test
        @DisplayName("[正] グローバルフィルダー、フィールドフィルターのいづれかに検索条件が指定されていた場合、Where句文字列を返す。(1)未指定の場合、空白文字列")
        void test001() {
            //準備
            DataTablesInput input = createInput();

            // 実行
            StringBuilder actual = target.getWhereClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo("");
        }

        @Test
        @DisplayName("[正] グローバルフィルダー、フィールドフィルターのいづれかに検索条件が指定されていた場合、Where句文字列を返す。(2)グローバルフィルタに指定")
        void test002() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc");

            // 実行
            StringBuilder actual = target.getWhereClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" WHERE 1 = 2  OR function('CONVERT', c.id, CHAR) LIKE :globalSearch ESCAPE '~' OR c.status LIKE :globalSearch ESCAPE '~' OR c.name LIKE :globalSearch ESCAPE '~' OR function('CONVERT', c.age, CHAR) LIKE :globalSearch ESCAPE '~' OR c.code LIKE :globalSearch ESCAPE '~' OR c.content LIKE :globalSearch ESCAPE '~' OR c.attachedFile01Uuid LIKE :globalSearch ESCAPE '~' OR c.attachedFile01Managed.originalFilename LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] グローバルフィルダー、フィールドフィルターのいづれかに検索条件が指定されていた場合、Where句文字列を返す。(3)フィールドフィルタに指定")
        void test003() {
            //準備
            DataTablesInput input = createInput();
            input.getColumns().get(3).getSearch().setValue("abc");
            input.getColumns().get(5).getSearch().setValue("efg");

            // 実行
            StringBuilder actual = target.getWhereClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" WHERE 1 = 1  AND function('CONVERT', c.age, CHAR) LIKE :age ESCAPE '~' AND c.content LIKE :content ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] グローバルフィルダー、フィールドフィルターのいづれかに検索条件が指定されていた場合、Where句文字列を返す。(4)両方設定するとフィールドフィルタを優先")
        void test004() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc"); //グローバルフィルタ
            input.getColumns().get(3).getSearch().setValue("abc"); //フィールドフィルタ
            input.getColumns().get(5).getSearch().setValue("efg"); //フィールドフィルタ

            // 実行
            StringBuilder actual = target.getWhereClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" WHERE 1 = 1  AND function('CONVERT', c.age, CHAR) LIKE :age ESCAPE '~' AND c.content LIKE :content ESCAPE '~'");
        }
    }

    @Nested
    class getSelectFromClause {
        @Test
        @DisplayName("[正] 指定したエンティティを主テーブルとする「SELECT 〜 FROM <エンティティ>」文字列を返す。(1)count=false")
        void test001() {
            // 実行
            StringBuilder actual = target.getSelectFromClause(Person.class, false);

            // 検証
            assertThat(actual.toString()).isEqualTo("SELECT distinct c FROM Person c ");
        }

        @Test
        @DisplayName("[正] 指定したエンティティを主テーブルとする「SELECT 〜 FROM <エンティティ>」文字列を返す。(2)count=true")
        void test002() {
            // 実行
            StringBuilder actual = target.getSelectFromClause(Person.class, true);

            // 検証
            assertThat(actual.toString()).isEqualTo("SELECT count(distinct c) FROM Person c ");
        }
    }

    @Nested
    class getJPQL {
        @Test
        @DisplayName("[正] DataTablesから渡される検索条件を実現するSQL(JPQL)を返す。(1)検索条件なし")
        void test001() {
            //準備
            DataTablesInput input = createInput();
//            input.getSearch().setValue("abc"); //グローバルフィルタ
//            input.getColumns().get(3).getSearch().setValue("abc"); //フィールドフィルタ
//            input.getColumns().get(5).getSearch().setValue("efg"); //フィールドフィルタ

            // 実行
            String actual = target.getJPQL(input , false, Person.class);

            // 検証
            assertThat(actual).isEqualTo("SELECT distinct c FROM Person c  LEFT JOIN c.attachedFile01Managed");
        }

        @Test
        @DisplayName("[正] DataTablesから渡される検索条件を実現するSQL(JPQL)を返す。(2)グローバルフィルタの指定あり")
        void test002() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc"); //グローバルフィルタ

            // 実行
            String actual = target.getJPQL(input , false, Person.class);

            // 検証
            assertThat(actual).isEqualTo("SELECT distinct c FROM Person c  LEFT JOIN c.attachedFile01Managed WHERE 1 = 2  OR function('CONVERT', c.id, CHAR) LIKE :globalSearch ESCAPE '~' OR c.status LIKE :globalSearch ESCAPE '~' OR c.name LIKE :globalSearch ESCAPE '~' OR function('CONVERT', c.age, CHAR) LIKE :globalSearch ESCAPE '~' OR c.code LIKE :globalSearch ESCAPE '~' OR c.content LIKE :globalSearch ESCAPE '~' OR c.attachedFile01Uuid LIKE :globalSearch ESCAPE '~' OR c.attachedFile01Managed.originalFilename LIKE :globalSearch ESCAPE '~'");
        }

        @Test
        @DisplayName("[正] DataTablesから渡される検索条件を実現するSQL(JPQL)を返す。(3)フィールドフィルタの指定あり")
        void test003() {
            //準備
            DataTablesInput input = createInput();
            input.getSearch().setValue("abc"); //グローバルフィルタ(フィールドフィルタで上書きされる)
            input.getColumns().get(3).getSearch().setValue("abc"); //フィールドフィルタ
            input.getColumns().get(5).getSearch().setValue("efg"); //フィールドフィルタ

            // 実行
            String actual = target.getJPQL(input , false, Person.class);

            // 検証
            assertThat(actual).isEqualTo("SELECT distinct c FROM Person c  LEFT JOIN c.attachedFile01Managed WHERE 1 = 1  AND function('CONVERT', c.age, CHAR) LIKE :age ESCAPE '~' AND c.content LIKE :content ESCAPE '~'");
        }
    }

    @Nested
    class getLeftOuterJoinClause {
        @Test
        @DisplayName("[正] リレーションが張られている場合、LEFT JOIN句文字列を返す。")
        void test001() {
            //準備
            DataTablesInput input = createInput();

            // 実行
            StringBuilder actual = target.getLeftOuterJoinClause(input);

            // 検証
            assertThat(actual.toString()).isEqualTo(" LEFT JOIN c.attachedFile01Managed");
        }
    }


}