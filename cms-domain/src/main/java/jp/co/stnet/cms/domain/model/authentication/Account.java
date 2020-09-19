package jp.co.stnet.cms.domain.model.authentication;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Account extends AbstractEntity<String> implements Serializable {

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
    private String profile;
    /**
     * ロール
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


}
