package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, String> {

    List<PasswordHistory> findByUsername(String username);

    List<PasswordHistory> findByUsernameAndUseFromAfter(String username, LocalDateTime useFrom);

}
