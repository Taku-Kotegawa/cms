package jp.co.stnet.cms.domain.repository.example;

import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.example.SimpleEntityMaxRev;
import jp.co.stnet.cms.domain.model.example.SimpleEntityRevision;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SimpleEntityRevisionRepositoryTest {

    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

//    @Autowired
//    AuditingHandler auditingHandler;
//
//    @Mock(lenient = true)
//    DateTimeProvider dateTimeProvider;

    @Autowired
    SimpleEntityRevisionRepository target;

    SimpleEntityRevision createEntityRev(Long id) {
        SimpleEntityRevision simpleEntityRevision = new SimpleEntityRevision();
        simpleEntityRevision.setId(id);
        simpleEntityRevision.setStatus("");
        simpleEntityRevision.setText01("");
        simpleEntityRevision.setText02(0);
        simpleEntityRevision.setText03(0.0F);
        simpleEntityRevision.setText04(false);
        simpleEntityRevision.setText05(Lists.newArrayList());
        simpleEntityRevision.setRadio01(false);
        simpleEntityRevision.setRadio02("");
        simpleEntityRevision.setCheckbox01("");
        simpleEntityRevision.setCheckbox02(Lists.newArrayList());
        simpleEntityRevision.setTextarea01("");
        simpleEntityRevision.setDate01(LocalDate.now());
        simpleEntityRevision.setDatetime01(LocalDateTime.now());
        simpleEntityRevision.setSelect01("");
        simpleEntityRevision.setSelect02(Lists.newArrayList());
        simpleEntityRevision.setSelect03("");
        simpleEntityRevision.setSelect04(Lists.newArrayList());
        simpleEntityRevision.setCombobox01("");
        simpleEntityRevision.setCombobox02("");
        simpleEntityRevision.setCombobox03(Lists.newArrayList());
        simpleEntityRevision.setAttachedFile01Uuid("");
        simpleEntityRevision.setAttachedFile01Managed(new FileManaged());
        simpleEntityRevision.setLineItems(new ArrayList<>());
        simpleEntityRevision.setRevType(0);
        simpleEntityRevision.setVersion(0L);
        return simpleEntityRevision;
    }


    @BeforeEach
    void setUp() {
//        auditingHandler.setDateTimeProvider(dateTimeProvider);

        // テスト前に保存されているエンティティを削除(テスト後にロールバックして復元)
//        target.deleteAll();
//        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class findByIdLatestRev extends baseTest {
        @Test
        @DisplayName("[正常系]データ登録と取得ができる。")
        void test001() {

            // 準備
            long beforeNumber = target.count();
            SimpleEntityRevision saved = target.saveAndFlush(createEntityRev(1L));
            entityManager.detach(saved);
            entityManager.clear();
            long afterNumber = target.count();

            // 実行
            SimpleEntityRevision actual = target.getOne(saved.getRid());

            // 検証
            assertThat(beforeNumber + 1).isEqualTo(afterNumber);
            assertThat(actual.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("[正常系] 最新のリビジョンが取得できる。")
        void test002() {

            // 準備
            SimpleEntityRevision saved1 = target.saveAndFlush(createEntityRev(1L));
            SimpleEntityRevision saved2 = target.saveAndFlush(createEntityRev(1L));
            SimpleEntityRevision saved3 = target.saveAndFlush(createEntityRev(1L));
            SimpleEntityRevision saved4 = target.saveAndFlush(createEntityRev(1L));
            Long maxRevId = saved4.getRid();

            // 最大リビジョンを保存
            SimpleEntityMaxRev maxRev = new SimpleEntityMaxRev();
            maxRev.setId(1L);
            maxRev.setRid(maxRevId);
            entityManager.merge(maxRev);

            // 実行
            SimpleEntityRevision actual = target.findByIdLatestRev(1L);

            // 検証
            assertThat(actual.getRid()).isEqualTo(maxRevId);
        }
    }
}
