package jp.co.stnet.cms.domain.service.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UnlockServiceImpl implements jp.co.stnet.cms.domain.service.authentication.UnlockService {

    @Autowired
    jp.co.stnet.cms.domain.service.authentication.AuthenticationEventSharedService authenticationEventSharedService;

    @Override
    public void unlock(String username) {
        authenticationEventSharedService.deleteFailureEventByUsername(username);
    }
}
