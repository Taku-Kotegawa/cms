package jp.co.stnet.cms.batch.RYREI001;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BatchStepExecution {
    private Long stepExecutionId = null;

    private String stepName = null;

    private String status = null;

    private List<String> failureExceptions = new ArrayList<>();
}
