package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.FailedPasswordReissue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FailedPasswordReissueRepository extends JpaRepository<FailedPasswordReissue, String> {

    List<FailedPasswordReissue> findByToken(String token);

    List<FailedPasswordReissue> deleteByToken(String token);

    long countByToken(String token);

    List<FailedPasswordReissue> deleteByAttemptDateLessThan(LocalDateTime attemptDate);

}
