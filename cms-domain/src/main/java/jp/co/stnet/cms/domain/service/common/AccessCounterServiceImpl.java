package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.model.common.Status;
import jp.co.stnet.cms.domain.repository.common.AccessCounterRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

@Slf4j
@Service
@Transactional
public class AccessCounterServiceImpl extends AbstractNodeService<AccessCounter, Long> implements AccessCounterService {

    @Autowired
    AccessCounterRepository accessCounterRepository;

    @Override
    protected JpaRepository<AccessCounter, Long> getRepository() {
        return accessCounterRepository;
    }

    @Override
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return null;
    }

    @Override
    public AccessCounter findByUrl(String url) {
        return accessCounterRepository.findByUrl(url).orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001)));
    }

    @Override
    public Long countUp(String url) {
        AccessCounter accessCounter = accessCounterRepository.findByUrl(url)
                .orElse(AccessCounter.builder()
                        .url(url)
                        .count(0L)
                        .status(Status.VALID.getCodeValue())
                        .build());
        accessCounter.setCount(accessCounter.getCount() + 1);
        accessCounterRepository.save(accessCounter);
        return accessCounter.getCount();
    }
}
