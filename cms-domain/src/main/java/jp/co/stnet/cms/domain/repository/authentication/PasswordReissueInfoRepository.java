package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.PasswordReissueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PasswordReissueInfoRepository extends JpaRepository<PasswordReissueInfo, String> {

    List<PasswordReissueInfo> findByExpiryDateLessThan(LocalDateTime date);

    List<PasswordReissueInfo> deleteByExpiryDateLessThan(LocalDateTime date);

}
