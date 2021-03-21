package jp.co.stnet.cms.domain.repository.authentication;

import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EmailChangeRequestのリポジトリ.
 */
@Repository
public interface EmailChangeRequestRepository extends JpaRepository<EmailChangeRequest, String> {

    /**
     * 有効期限が指定された日時以前のデータを削除する。
     *
     * @param date 日付
     * @return 削除した件数
     */
    long deleteByExpiryDateLessThan(LocalDateTime date);

}
