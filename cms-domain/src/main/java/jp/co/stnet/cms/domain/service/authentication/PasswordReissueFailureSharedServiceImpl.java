package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.FailedPasswordReissue;
import jp.co.stnet.cms.domain.repository.authentication.FailedPasswordReissueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class PasswordReissueFailureSharedServiceImpl implements PasswordReissueFailureSharedService {

    @Autowired
    FailedPasswordReissueRepository failedPasswordReissueRepository;

    @Override
    public void resetFailure(String username, String token) {
        failedPasswordReissueRepository.save(
                FailedPasswordReissue.builder().token(token).attemptDate(LocalDateTime.now()).build()
        );
    }
}
