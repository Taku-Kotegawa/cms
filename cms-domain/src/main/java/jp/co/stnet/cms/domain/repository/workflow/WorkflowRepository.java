package jp.co.stnet.cms.domain.repository.workflow;

import jp.co.stnet.cms.domain.model.workflow.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
}
