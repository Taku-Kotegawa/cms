package jp.co.stnet.cms.domain.repository.authentication;


import jp.co.stnet.cms.domain.model.authentication.FailedAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedAuthenticationRepository extends JpaRepository<FailedAuthentication, String> {
    long deleteByUsername(String username);
}
