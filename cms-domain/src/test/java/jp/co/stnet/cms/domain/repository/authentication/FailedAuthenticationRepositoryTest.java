package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;
import jp.co.stnet.cms.domain.model.authentication.FailedAuthentication;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FailedAuthenticationRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    @Autowired
    FailedAuthenticationRepository target;

    // https://github.com/binglind/alchemy/blob/master/alchemy-web/src/test/java/com/dfire/platform/alchemy/service/UserServiceIT.java
    // @Created をMockする。
    @Autowired
    private AuditingHandler auditingHandler;

    // @Created をMockする。
    @Mock(lenient = true)
    private DateTimeProvider dateTimeProvider;

    private final LocalDateTime dummyTime = LocalDateTime.of(2001, 12, 31, 23, 59, 58);


    @BeforeEach
    void setUp() {
        auditingHandler.setDateTimeProvider(dateTimeProvider);

        // テスト前に保存されているエンティティを削除(テスト後にロールバックして復元)
        target.deleteAll();
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
    }

    private FailedAuthentication createEntity(String id, LocalDateTime authenticationTimestamp) {
        // @Created をMockする。
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(authenticationTimestamp));

        return FailedAuthentication.builder()
                .username(rightPad("usernae: " + id, 255, "0"))
                .build();

        // @Createdで設定される作成日付に任意のテストデータを登録する方法がわからない。
    }

    private void insertTables(FailedAuthentication... failedAuthentications) {
        for (FailedAuthentication failedAuthentication : failedAuthentications) {
            target.save(failedAuthentication);
        }
    }


    @Nested
    class deleteByUsername extends baseTest {
        @Test
        @DisplayName("[正] データ挿入の妥当性検証")
        void test001() {
            // 準備
            FailedAuthentication expected = createEntity("1", dummyTime);

            insertTables(
                    expected
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            List<FailedAuthentication> actual = target.findAll();

            // 検証
            assertThat(actual.get(0)).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正] データの削除、戻り値に削除件数が返る[1件]")
        void test002() {
            // 準備
            target.deleteAll();
            entityManager.flush();
            insertTables(
                    createEntity("1", dummyTime),
                    createEntity("2", dummyTime),
                    createEntity("3", dummyTime),
                    createEntity("3", dummyTime)
            );
            entityManager.flush();
            entityManager.clear();
            FailedAuthentication expected = createEntity("3", dummyTime);

            // 実行
            long actual = target.deleteByUsername(expected.getUsername());

            // 検証
            assertThat(actual).isEqualTo(2l);
        }

        @Test
        @DisplayName("[正] データの削除後、テーブルに残ったデータの妥当である。")
        void test003() {
            // 準備
            insertTables(
                    createEntity("1", dummyTime),
                    createEntity("2", dummyTime),
                    createEntity("3", dummyTime),
                    createEntity("3", dummyTime)
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            target.deleteByUsername(createEntity("3", dummyTime).getUsername());
            List<FailedAuthentication> actual = target.findAll();

            // 検証
            assertThat(actual).size().isEqualTo(2);
        }

    }
}