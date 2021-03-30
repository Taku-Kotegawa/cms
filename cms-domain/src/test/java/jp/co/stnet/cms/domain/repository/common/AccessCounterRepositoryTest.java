package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.AccessCounter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnnecessaryLocalVariable")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class AccessCounterRepositoryTest {

    // @Nested を使うとclassごとにcontextファイルを指定しなければならないので、それを一括指定する。
    @SuppressWarnings("InnerClassMayBeStatic")
    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @Autowired
    AccessCounterRepository target;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * AccessCounterテーブルにデータを挿入する。
     *
     * @param accessCounters AccessCounter(カンマ区切りで複数指定可)
     */
    private void insertIntoDatabase(AccessCounter... accessCounters) {
        for (AccessCounter accessCounter : accessCounters) {
            accessCounter.setVersion(null); //必ず新規登録
            target.save(accessCounter);
        }
    }

    /**
     * テストエンティティの作成(各フィールドに最大桁数のダミーデータをセット)
     *
     * @param id テストデータを一意に特定する番号
     * @return AccessCounterエンティティ
     */
    private AccessCounter createEntity(String id) {

        AccessCounter accessCounter = AccessCounter.builder()
                .status("0")
                .url(rightPad(id, 255, "0"))
                .count(Long.MAX_VALUE)
                .build();

        return accessCounter;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @SuppressWarnings("ConstantConditions")
    @Nested
    class getOne extends baseTest {
        @Test
        @DisplayName("[正常系]データ挿入できること")
        void test001() {
            //準備
            AccessCounter expected = createEntity("1");
            AccessCounter after = target.saveAndFlush(expected);
            entityManager.flush();
            entityManager.clear();

            //実行
            AccessCounter actual = target.getOne(after.getId());

            //検証
            assertThat(actual).isEqualTo(after);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Nested
    class findByUrl extends baseTest {
        @Test
        @DisplayName("[正常系]URLでデータを検索できる。")
        void test001() {
            //準備
            insertIntoDatabase(
                    createEntity("1"),
                    createEntity("2"),
                    createEntity("3")
            );
            entityManager.flush();
            entityManager.clear();

            AccessCounter expected = createEntity("2");

            //実行
            AccessCounter actual = target.findByUrl(expected.getUrl()).orElse(null);

            //検証
            assertThat(actual.getUrl()).isEqualTo(expected.getUrl());
        }
    }



}