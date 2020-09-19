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
public class FailedPasswordReissue implements Serializable {
    /**
     * トークン
     */
    @Id
    private String token;

    /**
     * 試行日時
     */
    private LocalDateTime attemptDate;
}
