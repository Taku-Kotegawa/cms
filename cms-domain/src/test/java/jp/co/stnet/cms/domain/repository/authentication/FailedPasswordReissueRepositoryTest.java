package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.FailedPasswordReissue;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FailedPasswordReissueRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @Autowired
    FailedPasswordReissueRepository target;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    /**
     * エンティティを作成する。
     * @param id キー
     * @param attemptDate 試行日付
     * @return エンティティ
     */
    private FailedPasswordReissue createEntity(String id, LocalDateTime attemptDate) {
        if (id == null || attemptDate == null) {
            throw new IllegalArgumentException("id or attemptDate must be null");
        }
        FailedPasswordReissue failedPasswordReissue = new FailedPasswordReissue();
        failedPasswordReissue.setToken(rightPad("id: " + id, 255, "0"));
        failedPasswordReissue.setAttemptDate(attemptDate);
        return failedPasswordReissue;
    }

    /**
     * データを挿入する(flushしないと書き込まれない)
     *
     * @param failedPasswordReissue 　FailedPasswordReissueの配列
     */
    private void insertTables(FailedPasswordReissue... failedPasswordReissue) {
        for (FailedPasswordReissue emailChangeRequest : failedPasswordReissue) {
            target.save(emailChangeRequest);
        }
    }

    @BeforeEach
    void setUp() {
        target.deleteAll();
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class findByToken extends baseTest {
        @Test
        @DisplayName("[正] トークンで検索できる。(1件)")
        void test001() {
            // 準備
            FailedPasswordReissue expected = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            List<FailedPasswordReissue> actual = target.findByToken(expected.getToken());

            // 検証
            assertThat(actual).size().isEqualTo(1);
        }

        @Test
        @DisplayName("[正] トークンで検索できる。(2件)")
        void test002() {
            // 準備
            FailedPasswordReissue expected1 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            FailedPasswordReissue expected2 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,58));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected1,
                    expected2,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            List<FailedPasswordReissue> actual = target.findByToken(expected1.getToken());

            // 検証
            assertThat(actual).size().isEqualTo(2);
        }

        @Test
        @DisplayName("[正] トークンで検索できる。(0件)")
        void test003() {
            // 準備
            FailedPasswordReissue expected = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            List<FailedPasswordReissue> actual = target.findByToken(expected.getToken());

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }
    }

    @Nested
    class countByToken extends baseTest {
        @Test
        @DisplayName("[正] トークンで検索できる。(1件)")
        void test001() {
            // 準備
            FailedPasswordReissue expected = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            long actual = target.countByToken(expected.getToken());

            // 検証
            assertThat(actual).isEqualTo(1);
        }

        @Test
        @DisplayName("[正] トークンで検索できる。(2件)")
        void test002() {
            // 準備
            FailedPasswordReissue expected1 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            FailedPasswordReissue expected2 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,58));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected1,
                    expected2,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            long actual = target.countByToken(expected1.getToken());

            // 検証
            assertThat(actual).isEqualTo(2);
        }

        @Test
        @DisplayName("[正] トークンで検索できる。(0件)")
        void test003() {
            // 準備
            FailedPasswordReissue expected = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            long actual = target.countByToken(expected.getToken());

            // 検証
            assertThat(actual).isEqualTo(0);
        }

    }

    @Nested
    class deleteByToken extends baseTest {
        @Test
        @DisplayName("[正] トークンで削除できる。(2件)")
        void test001() {
            // 準備
            FailedPasswordReissue expected1 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,58));
            FailedPasswordReissue expected2 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected1,
                    expected2,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            long actual = target.deleteByToken(expected1.getToken());

            // 検証
            assertThat(actual).isEqualTo(2);
        }

        @Test
        @DisplayName("[正] トークンで削除できる。(残ったレコード数3件)")
        void test002() {
            // 準備
            FailedPasswordReissue expected1 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,58));
            FailedPasswordReissue expected2 = createEntity("1", LocalDateTime.of(2001,12,31,23,59,59));
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59)),
                    expected1,
                    expected2,
                    createEntity("2", LocalDateTime.of(2001,12,31,23,59,59)),
                    createEntity("3", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            target.deleteByToken(expected1.getToken());

            // 検証
            long actual = target.count();
            assertThat(actual).isEqualTo(3);
        }
    }

    @Nested
    class deleteByAttemptDateLessThan extends baseTest {
        @Test
        @DisplayName("[正] トークンで削除できる。(1件)")
        void test001() {
            // 準備
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,57)), // 削除対象
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,58)),
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            long actual = target.deleteByAttemptDateLessThan(LocalDateTime.of(2001,12,31,23,59,58));

            // 検証
            assertThat(actual).isEqualTo(1);
        }

        @Test
        @DisplayName("[正] トークンで削除できる。(残ったレコード数2件)")
        void test002() {
            // 準備
            insertTables(
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,57)), // 削除対象
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,58)),
                    createEntity("0", LocalDateTime.of(2001,12,31,23,59,59))
            );

            // 実行
            target.deleteByAttemptDateLessThan(LocalDateTime.of(2001,12,31,23,59,58));
            long actual = target.count();

            // 検証
            assertThat(actual).isEqualTo(2);
        }
    }
}