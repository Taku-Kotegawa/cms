package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.FailedEmailChangeRequest;
import jp.co.stnet.cms.domain.model.authentication.FailedEmailChangeRequestPK;
import jp.co.stnet.cms.domain.model.authentication.FailedPasswordReissue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FailedEmailChangeRequestRepository extends JpaRepository<FailedEmailChangeRequest, FailedEmailChangeRequestPK> {

    Long countByToken(String token);

    List<FailedEmailChangeRequest> deleteByAttemptDateLessThan(LocalDateTime attemptDate);

    List<FailedEmailChangeRequest> deleteByToken(String token);

}
