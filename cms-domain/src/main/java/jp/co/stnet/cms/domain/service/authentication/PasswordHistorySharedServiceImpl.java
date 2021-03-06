package jp.co.stnet.cms.domain.service.authentication;

import jp.co.stnet.cms.domain.model.authentication.PasswordHistory;
import jp.co.stnet.cms.domain.repository.authentication.PasswordHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PasswordHistorySharedServiceImpl implements jp.co.stnet.cms.domain.service.authentication.PasswordHistorySharedService {

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public int insert(PasswordHistory history) {
        passwordHistoryRepository.save(history);
        return 1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findHistoriesByUseFrom(String username, LocalDateTime useFrom) {
        return passwordHistoryRepository.findByUsernameAndUseFromAfter(username, useFrom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findLatest(String username, int limit) {
        PasswordHistory prob = PasswordHistory.builder().username(username).build();
        Pageable pageable = PageRequest.of(0, limit, Sort.Direction.DESC, "useFrom");
        Page<PasswordHistory> page = passwordHistoryRepository.findAll(Example.of(prob), pageable);
        return page.getContent();
    }
}
