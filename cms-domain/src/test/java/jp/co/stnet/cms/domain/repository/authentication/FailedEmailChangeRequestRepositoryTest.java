package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.FailedEmailChangeRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FailedEmailChangeRequestRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    @Autowired
    FailedEmailChangeRequestRepository target;

    private FailedEmailChangeRequest createEntity(String id, LocalDateTime attemptDate) {
        return FailedEmailChangeRequest.builder()
                .token(rightPad("token:" + id, 255, "0"))
                .attemptDate(attemptDate)
                .build();

    }

    private void insertTables(FailedEmailChangeRequest... failedEmailChangeRequests) {
        for (FailedEmailChangeRequest failedEmailChangeRequest : failedEmailChangeRequests) {
            target.save(failedEmailChangeRequest);
        }
    }


    @BeforeEach
    void setUp() {
        // テスト前に保存されているエンティティを削除(テスト後にロールバックして復元)
        target.deleteAll();
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
    }


    @Nested
    class countByToken extends baseTest {
        @Test
        @DisplayName("[正] 1件が検索できる。")
        void test001() {
            // 準備
            FailedEmailChangeRequest expected = createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50));

            insertTables(
                    expected
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.countByToken(expected.getToken());

            // 検証
            assertThat(actual).isEqualTo(1l);
        }

        @Test
        @DisplayName("[正] 2件が検索できる。")
        void test002() {
            // 準備
            FailedEmailChangeRequest expected = createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50));

            insertTables(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50)),
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 51))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.countByToken(expected.getToken());

            // 検証
            assertThat(actual).isEqualTo(2l);
        }
    }

    @Nested
    class deleteByAttemptDateLessThan extends baseTest {
        @Test
        @DisplayName("[正] 削除できる。(1件)")
        void test001() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50)), //削除対象
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 51)),
                    createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 52))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.deleteByAttemptDateLessThan(LocalDateTime.of(2001, 12, 31, 23, 59, 51));

            // 検証
            assertThat(actual).isEqualTo(1l);
        }

        @Test
        @DisplayName("[正] 削除できる。(2件)")
        void test002() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50)), //削除対象
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 51)), //削除対象
                    createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 52))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.deleteByAttemptDateLessThan(LocalDateTime.of(2001, 12, 31, 23, 59, 52));

            // 検証
            assertThat(actual).isEqualTo(2l);
        }


    }

    @Nested
    class deleteByToken extends baseTest {
        @Test
        @DisplayName("[正] 削除できる。(3件)")
        void test002() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 50)),
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 51)),
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 52)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 50)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 51))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.countByToken(createEntity("1", null).getToken());

            // 検証
            assertThat(actual).isEqualTo(3l);
        }
    }
}