package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.Variable;

import java.util.List;

public interface VariableSharedService {

    List<Variable> findAllByType(String type);

    List<Variable> findAllByTypeAndCode(String type, String code);

}
