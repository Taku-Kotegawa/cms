package jp.co.stnet.cms.domain.model.authentication;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FailedEmailChangeRequestPK implements Serializable {

    /**
     * トークン
     */
    private String token;

    /**
     * 試行日時
     */
    private LocalDateTime attemptDate;
}
