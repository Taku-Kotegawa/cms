package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.PasswordReissueInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordReissueInfoRepository extends JpaRepository<PasswordReissueInfo, String> {

    List<PasswordReissueInfo> findByExpiryDateLessThan(LocalDateTime date);

    List<PasswordReissueInfo> deleteByExpiryDateLessThan(LocalDateTime date);

}
