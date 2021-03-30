package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.PasswordHistory;
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
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PasswordHistoryRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    @Autowired
    PasswordHistoryRepository target;

    @Autowired
    AuditingHandler auditingHandler;

    @Mock(lenient = true)
    DateTimeProvider dateTimeProvider;

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

    private PasswordHistory createEntity(String id, LocalDateTime useFrom) {

        if (id == null || useFrom == null) {
            throw new IllegalArgumentException("id or useFrom must not be null.");
        }

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(useFrom));

        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUsername(rightPad("username:" + id, 255, "0"));
        passwordHistory.setPassword(rightPad("password:" + id, 255, "0"));
        return passwordHistory;
    }

    private void insertTable(PasswordHistory... passwordHistories) {
        for (PasswordHistory passwordHistory : passwordHistories) {
            target.save(passwordHistory);
        }
    }

    @Nested
    class findByUsername extends baseTest {
        @Test
        @DisplayName("[正常系]検索できる。(2件)")
        void test001() {
            //準備
            insertTable(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 10)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 11)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 12)),
                    createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 13))
            );
            entityManager.flush();
            String targetUsername = createEntity("2", LocalDateTime.now()).getUsername();

            //実行
            List<PasswordHistory> actual = target.findByUsername(targetUsername);

            //検証
            assertThat(actual).size().isEqualTo(2);
        }

        @Test
        @DisplayName("[正常系]検索できない。(0件)")
        void test002() {
            //準備
            insertTable(
                    createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 10)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 11)),
                    createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 12)),
                    createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 13))
            );
            entityManager.flush();

            //実行
            List<PasswordHistory> actual = target.findByUsername("not exist");

            //検証
            assertThat(actual).size().isEqualTo(0);
        }
    }

    @Nested
    class findByUsernameAndUseFromAfter extends baseTest {
        @Test
        @DisplayName("[正常系]検索できる。(1件)")
        void test001() {
            //準備
            target.save(createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 11)));
            target.save(createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 12)));
            target.save(createEntity("1", LocalDateTime.of(2001, 12, 31, 23, 59, 13)));
            target.save(createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 11)));
            target.save(createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 12)));
            target.save(createEntity("2", LocalDateTime.of(2001, 12, 31, 23, 59, 13))); //対象
            target.save(createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 11)));
            target.save(createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 12)));
            target.save(createEntity("3", LocalDateTime.of(2001, 12, 31, 23, 59, 13)));
            entityManager.flush();
            String targetUsername = createEntity("2", LocalDateTime.now()).getUsername();

            //実行
            List<PasswordHistory> actual = target.findByUsernameAndUseFromAfter(targetUsername, LocalDateTime.of(2001, 12, 31, 23, 59, 12));

            //検証
            assertThat(actual.get(0).getUseFrom()).isEqualTo(LocalDateTime.of(2001, 12, 31, 23, 59, 13));
            assertThat(actual).size().isEqualTo(1);
        }
    }
}