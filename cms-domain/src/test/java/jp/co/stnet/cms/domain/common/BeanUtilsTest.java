package jp.co.stnet.cms.domain.common;

import jp.co.stnet.cms.domain.model.common.Status;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BeanUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


//    @Test
//    void getFieldList() {
//    }
//
//    @Test
//    void testGetFieldList() {
//    }
//
//    @Test
//    void getFieldByAnnotation() {
//    }
//
//    @Test
//    void getSignature() {
//    }

    @Nested
    class getFields {

        /**
         * 準備: フィールドを持たないクラス
         */
        @Data
        class SampleModel01 { }

        @Test
        @DisplayName("[正常]フィールドを持たないクラスの場合、空のMapを返す。")
        void test001() {
            // 準備
            Map<String, String> expected = new HashMap<>();

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel01.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

        /**
         * 基本的なクラスのフィールドを持つ
         */
        @Data
        class SampleModel02 {
            private int field1;
            private boolean field2;
            private String field3;
            private Integer field4;
        }

        @Test
        @DisplayName("[正常]基本的な型のフィールドを持つクラスは、フィールド名とクラスを格納したMapを返す。")
        void test002() {
            // 準備
            Map<String, String> expected = new HashMap<>();
            expected.put("field1", "int");
            expected.put("field2", "boolean");
            expected.put("field3", "java.lang.String");
            expected.put("field4", "java.lang.Integer");

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel02.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

        /**
         * getter を持たない
         */
        class SampleModel03 {
            private int field1;
            private boolean field2;
            private String field3;
            private Integer field4;
        }

        @Test
        @DisplayName("[正常]getterを持たない場合、空のMapを返す。")
        void test003() {
            // 準備
            Map<String, String> expected = new HashMap<>();

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel03.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

        /**
         * LIST, MAP, ENUM など
         */
        @Data
        class SampleModel04 {
            private List<String> field1;
            private Map<String, String> field2;
            private Status field3;
        }

        @Test
        @DisplayName("[正常] LIST, MAP, ENUM のフィールドの場合、フィールド名とクラスを格納したMapを返す。")
        void test004() {
            // 準備
            Map<String, String> expected = new HashMap<>();
            expected.put("field1", "java.util.List");
            expected.put("field2", "java.util.Map");
            expected.put("field3", "jp.co.stnet.cms.domain.model.common.Status");

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel04.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }


        /**
         * 他のクラスをフィールドに持つ場合
         */
        @Data
        class SampleModel05 {
            private SampleModel02 field1;
        }

        @Test
        @DisplayName("[正常] 他のクラスをフィールドに持つ場合、フィールドに指定したクラス内のフィールドとクラスもMapに格納される。")
        void test005() {
            // 準備
            Map<String, String> expected = new HashMap<>();
            expected.put("field1", "jp.co.stnet.cms.domain.common.BeanUtilsTest$getFields$SampleModel02");
            expected.put("field1-field1", "int");
            expected.put("field1-field2", "boolean");
            expected.put("field1-field3", "java.lang.String");
            expected.put("field1-field4", "java.lang.Integer");

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel05.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

        /**
         * 他のモデルの List, Map を持つ場合
         */
        @Data
        class SampleModel06 {
            private List<SampleModel02> field1;
            private Map<String, SampleModel02> field2;
        }

        @Test
        @DisplayName("[正常] 他のモデルのLIST, MAP のフィールドの場合、フィールド名とクラスを格納したMapを返す。")
        void test006() {
            // 準備
            Map<String, String> expected = new HashMap<>();
            expected.put("field1", "java.util.List");
            expected.put("field2", "java.util.Map");

            // 実行
            Map<String, String> actual = BeanUtils.getFields(SampleModel06.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

    }


    @Nested
    class getFieldList {

        @Test
        @DisplayName("[正常] 他のクラスをフィールドに持つ場合、フィールドに指定したクラス内のフィールドとクラスもMapに格納される。")
        void test001() {
            // 準備
            List<String> expected = new ArrayList<>();
            expected.add("field1-field1");
            expected.add("field1-field2");
            expected.add("field1-field3");
            expected.add("field1-field4");
            expected.add("field1");

            // 実行
            List<String> actual = BeanUtils.getFieldList(getFields.SampleModel05.class, "");

            // 検証
            assertThat(actual).isEqualTo(expected);
            log.info("actual : " + actual.toString());
        }

    }





}