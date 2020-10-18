package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Long> {

    List<Variable> findByType(String type);

    List<Variable> findAllByTypeAndCode(String type, String code);

}
