package jp.co.stnet.cms.domain.repository.authentication;


import jp.co.stnet.cms.domain.model.authentication.FailedAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedAuthenticationRepository extends JpaRepository<FailedAuthentication, String> {
    long deleteByUsername(String username);
}
