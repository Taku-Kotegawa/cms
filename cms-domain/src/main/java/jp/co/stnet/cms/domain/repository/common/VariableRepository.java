package jp.co.stnet.cms.domain.repository.common;

import jp.co.stnet.cms.domain.model.common.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Long> {
}
