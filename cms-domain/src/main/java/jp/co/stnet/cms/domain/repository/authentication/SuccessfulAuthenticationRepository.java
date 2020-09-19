package jp.co.stnet.cms.domain.repository.authentication;


import jp.co.stnet.cms.domain.model.authentication.SuccessfulAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuccessfulAuthenticationRepository extends JpaRepository<SuccessfulAuthentication, String> {
}
