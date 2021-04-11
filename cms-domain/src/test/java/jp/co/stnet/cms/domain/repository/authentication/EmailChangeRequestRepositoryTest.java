package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class EmailChangeRequestRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @Autowired
    EmailChangeRequestRepository target;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    @BeforeEach
    void setUp() {
        target.deleteAll();
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
    }

    private EmailChangeRequest createEntity(String id, LocalDateTime expiryDate) {

        LocalDateTime tmpExpiryDate;

        if (expiryDate == null) {
            tmpExpiryDate = LocalDateTime.of(2021, 12, 31, 23, 59, 59);
        } else {
            tmpExpiryDate = expiryDate;
        }

        return EmailChangeRequest.builder()
                .token(rightPad("token: " + id, 255, "0"))
                .username(rightPad("username: " + id, 255, "0"))
                .secret(rightPad("secret: " + id, 255, "0"))
                .newMail(rightPad("newmail: " + id, 255, "0"))
                .expiryDate(tmpExpiryDate)
                .build();
    }

    /**
     * データを挿入する(flushしないと書き込まれない)
     *
     * @param emailChangeRequests 　EmailChangeRequestの配列
     */
    private void insertTables(EmailChangeRequest... emailChangeRequests) {
        for (EmailChangeRequest emailChangeRequest : emailChangeRequests) {
            target.save(emailChangeRequest);
        }
    }

    @Nested
    class deleteByExpiryDateLessThan extends baseTest {
        @Test
        @DisplayName("[正] データ挿入の検証(件数の妥当性)")
        void test001() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57)),
                    createEntity("2", LocalDateTime.of(2021, 12, 31, 23, 59, 58)),
                    createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59))
            );
            entityManager.flush();
            entityManager.clear();
            EmailChangeRequest expected = createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59));

            // 実行
            List<EmailChangeRequest> actual = target.findAll();

            // 検証
            assertThat(actual).size().isEqualTo(3);
        }

        @Test
        @DisplayName("[正] 登録されたデータが正しく取得できること")
        void test002() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57))
            );
            entityManager.flush();
            entityManager.clear();
            EmailChangeRequest expected = createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57));

            // 実行
            EmailChangeRequest actual = target.findById(expected.getToken()).orElse(null);

            // 検証
            assertThat(actual).isEqualTo(expected);
        }


        @Test
        @DisplayName("[正] データの削除[0件]")
        void test003() {
            // 準備
            target.deleteAll();
            entityManager.flush();
            insertTables(
                    createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57)),
                    createEntity("2", LocalDateTime.of(2021, 12, 31, 23, 59, 58)),
                    createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59))
            );
            entityManager.flush();
            entityManager.clear();
            EmailChangeRequest expected = createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59));


            // 実行
            long actual = target.deleteByExpiryDateLessThan(LocalDateTime.of(2021, 12, 31, 23, 59, 57));

            // 検証
            assertThat(actual).isEqualTo(0l);
        }

        @Test
        @DisplayName("[正] データの削除、戻り値が削除件数になっている。")
        void test004() {
            // 準備
            insertTables(
                    createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57)), // 対象
                    createEntity("2", LocalDateTime.of(2021, 12, 31, 23, 59, 58)),
                    createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            long actual = target.deleteByExpiryDateLessThan(LocalDateTime.of(2021, 12, 31, 23, 59, 58));

            // 検証
            assertThat(actual).isEqualTo(1l);
        }

        @Test
        @DisplayName("[正] データの削除、残っているデータの妥当性")
        void test005() {
            // 準備

            // 削除処理の後、テーブルに残っているデータを準備
            List<EmailChangeRequest> expected = new ArrayList<>();
            expected.add(createEntity("2", LocalDateTime.of(2021, 12, 31, 23, 59, 58)));
            expected.add(createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59)));

            insertTables(
                    createEntity("1", LocalDateTime.of(2021, 12, 31, 23, 59, 57)), // 削除対象
                    createEntity("2", LocalDateTime.of(2021, 12, 31, 23, 59, 58)),
                    createEntity("3", LocalDateTime.of(2021, 12, 31, 23, 59, 59))
            );
            entityManager.flush();
            entityManager.clear();

            // 実行
            target.deleteByExpiryDateLessThan(LocalDateTime.of(2021, 12, 31, 23, 59, 58));
            List<EmailChangeRequest> actual = target.findAll();

            // 検証
            assertThat(actual).isEqualTo(expected);
        }

    }
}