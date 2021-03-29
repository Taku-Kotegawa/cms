package jp.co.stnet.cms.domain.repository.authentication;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.model.authentication.Account;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class AccountRepositoryTest {

    // @Nested を使うとclassごとにcontextファイルを指定しなければならないので、それを一括指定する。
    @SuppressWarnings("InnerClassMayBeStatic")
    @ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:META-INF/spring/cms-infra.xml"})
    @Transactional
    abstract class baseTest {
    }

    @Autowired
    AccountRepository target;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Mapper beanMapper;

    /**
     * accountテーブルにデータを挿入する。
     *
     * @param accounts Account(カンマ区切りで複数指定可)
     */
    private void insertIntoDatabase(Account... accounts) {
        for (Account account : accounts) {
            account.setVersion(null); //必ず新規登録
            target.save(account);
        }
    }

    /**
     * テストエンティティの作成(各フィールドに最大桁数のダミーデータをセット)
     *
     * @param id テストデータを一意に特定する番号
     * @return Accountエンティティ
     */
    private Account createAccount(String id) {

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");

        return Account.builder()
                .username(id)
                .username(rightPad(id, 88, "0"))
                .password(rightPad("Password:" + id, 255, "0"))
                .firstName(rightPad("FirstName:" + id, 255, "0"))
                .lastName(rightPad("LastName:" + id, 255, "0"))
                .email(rightPad("Email:" + id, 255, "0"))
                .url(rightPad("Url:" + id, 255, "0"))
                .profile(rightPad("Profile:" + id, 1000, "0"))
                .roles(roles)
                .status("0")
                .imageUuid(rightPad("ApiKey:" + id, 255, "0"))
                .apiKey(rightPad("ApiKey:" + id, 255, "0"))
                .allowedIp(rightPad("AllowedIp:" + id, 255, "0"))
                .build();
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
        @DisplayName("[正常系] 最大桁数のデータ登録")
        void test001() {
            //準備
            Account expected = createAccount("1");
            target.save(expected);
            String id = expected.getId();
            entityManager.flush();
            entityManager.clear();

            //実行
            Account actual = target.getOne(id);

            //検証

            // getOneで取り出したAccountはListのオブジェクト比較が失敗するため、オブジェクをコピーする。
            Account actualCopy = beanMapper.map(actual, Account.class);

            assertThat(actualCopy).isEqualTo(expected);

        }
    }

    @Nested
    class findByApiKey extends baseTest {
        @Test
        @DisplayName("[正常系] API-KEYで検索")
        void test001() {
            //準備
            insertIntoDatabase(
                    createAccount("1"),
                    createAccount("2"),
                    createAccount("3")
            );
            entityManager.flush();
            entityManager.clear();

            Account expected = createAccount("2");

            //実行
            Account actual = target.findByApiKey(expected.getApiKey());

            //検証
            assertThat(actual.getApiKey()).isEqualTo(expected.getApiKey());
        }

        @Test
        @DisplayName("[正常系] API-KEYで検索して、検索できない場合はnullを返す。")
        void test002() {
            //準備
            insertIntoDatabase(
                    createAccount("1"),
                    createAccount("2"),
                    createAccount("3")
            );
            entityManager.flush();
            entityManager.clear();

            //実行
            Account actual = target.findByApiKey("not exist");

            //検証
            assertThat(actual).isNull();
        }
    }

}