package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountImageRepository extends JpaRepository<AccountImage, String> {
}
