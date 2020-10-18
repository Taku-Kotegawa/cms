package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.repository.common.VariableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class VariableSharedServiceImpl implements VariableSharedService {

    @Autowired
    VariableRepository variableRepository;

    @Override
    public List<Variable> findAllByType(String type) {
        return variableRepository.findByType(type);
    }

    @Override
    public List<Variable> findAllByTypeAndCode(String type, String code) {
        return variableRepository.findAllByTypeAndCode(type, code);
    }

}
