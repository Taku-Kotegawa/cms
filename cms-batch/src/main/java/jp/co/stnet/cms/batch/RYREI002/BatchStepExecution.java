package jp.co.stnet.cms.batch.RYREI002;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class BatchStepExecution {
    private Long stepExecutionId = null;

    private String stepName = null;

    private String status = null;

    private List<String> failureExceptions = new ArrayList<>();
}
