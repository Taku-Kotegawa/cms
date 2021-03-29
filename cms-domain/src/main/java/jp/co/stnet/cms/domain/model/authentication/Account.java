package jp.co.stnet.cms.domain.model.authentication;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * ユーザアカウントエンティティ
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {@Index(columnList = "apiKey", unique = true)})
public class Account extends AbstractEntity<String> implements Serializable, StatusInterface {

    /**
     * ユーザID
     */
    @Id
    private String username;

    /**
     * パスワード
     */
    private String password;

    /**
     * 名
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * メールアドレス
     */
    private String email;

    /**
     * URL
     */
    private String url;

    /**
     * プロフィール
     */
    @Column(length = 1000)
    private String profile;

    /**
     * ロール
     */
//    @ElementCollection(fetch = FetchType.EAGER)
    @ElementCollection
    private List<String> roles;

    /**
     * ステータス
     */
    private String status;

    /**
     * 画像UUID
     */
    private String imageUuid;

    /**
     * API KEY
     */
    @Column(unique = true)
    private String apiKey;

    /**
     * ログイン許可IPアドレス
     */
    private String allowedIp;

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return getVersion() == null;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Account)) return false;
        final Account other = (Account) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$profile = this.getProfile();
        final Object other$profile = other.getProfile();
        if (this$profile == null ? other$profile != null : !this$profile.equals(other$profile)) return false;
        final Object this$roles = this.getRoles();
        final Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$imageUuid = this.getImageUuid();
        final Object other$imageUuid = other.getImageUuid();
        if (this$imageUuid == null ? other$imageUuid != null : !this$imageUuid.equals(other$imageUuid)) return false;
        final Object this$apiKey = this.getApiKey();
        final Object other$apiKey = other.getApiKey();
        if (this$apiKey == null ? other$apiKey != null : !this$apiKey.equals(other$apiKey)) return false;
        final Object this$allowedIp = this.getAllowedIp();
        final Object other$allowedIp = other.getAllowedIp();
        if (this$allowedIp == null ? other$allowedIp != null : !this$allowedIp.equals(other$allowedIp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Account;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $profile = this.getProfile();
        result = result * PRIME + ($profile == null ? 43 : $profile.hashCode());
        final Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $imageUuid = this.getImageUuid();
        result = result * PRIME + ($imageUuid == null ? 43 : $imageUuid.hashCode());
        final Object $apiKey = this.getApiKey();
        result = result * PRIME + ($apiKey == null ? 43 : $apiKey.hashCode());
        final Object $allowedIp = this.getAllowedIp();
        result = result * PRIME + ($allowedIp == null ? 43 : $allowedIp.hashCode());
        return result;
    }
}
