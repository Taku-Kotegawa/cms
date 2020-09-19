package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReissueInfo implements Serializable {
    /**
     * トークン
     */
    @Id
    private String token;

    /**
     * ユーザID
     */
    private String username;

    /**
     * 秘密情報
     */
    private String secret;

    /**
     * 有効期限
     */
    private LocalDateTime expiryDate;
}
