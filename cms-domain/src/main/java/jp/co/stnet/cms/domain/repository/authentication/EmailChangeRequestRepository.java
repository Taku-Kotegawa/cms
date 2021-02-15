package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailChangeRequestRepository extends JpaRepository<EmailChangeRequest, String> {

    List<EmailChangeRequest> deleteByExpiryDateLessThan(LocalDateTime date);

}
