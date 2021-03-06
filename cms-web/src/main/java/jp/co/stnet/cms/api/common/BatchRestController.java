package jp.co.stnet.cms.api.common;

import jp.co.stnet.cms.app.common.job.JobStarter;
import jp.co.stnet.cms.batch.RYREI002.BatchExecution;
import jp.co.stnet.cms.batch.RYREI002.BatchOperation;
import jp.co.stnet.cms.batch.RYREI002.BatchStepExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("batch")
public class BatchRestController {
    @Autowired
    JobOperator jobOperator;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobStarter jobStarter;

    @PostMapping(value = "{jobName}")
    public ResponseEntity<BatchOperation> launch(@PathVariable("jobName") String jobName,
                                                 @RequestBody BatchOperation requestResource) {

        BatchOperation responseResource = new BatchOperation();
        responseResource.setJobName(jobName);
        try {

//            Long jobExecutionId = jobOperator.start(jobName, requestResource.getJobParams());
            Long jobExecutionId = jobStarter.start(jobName, requestResource.getJobParams());
            responseResource.setJobExecutionId(jobExecutionId);
            requestResource.setJobParams(requestResource.getJobParams());
            return ResponseEntity.ok().body(responseResource);
        } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException |
                ValidationException e) {
            e.printStackTrace();
            responseResource.setError(e);
            return ResponseEntity.badRequest().body(responseResource);
        }
    }

    @GetMapping(value = "{jobExecutionId}")
    @ResponseStatus(HttpStatus.OK)
    public BatchExecution getJob(@PathVariable("jobExecutionId") Long jobExecutionId) {

        BatchExecution responseResource = new BatchExecution();
        responseResource.setJobExecutionId(jobExecutionId);

        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);

        if (jobExecution == null) {
            responseResource.setErrorMessage("Job execution not found.");
        } else {
            mappingExecutionInfo(jobExecution, responseResource);
        }

        return responseResource;
    }

    private void mappingExecutionInfo(JobExecution src, BatchExecution dest) {
        dest.setJobName(src.getJobInstance().getJobName());
        for (StepExecution se : src.getStepExecutions()) {
            BatchStepExecution ser = new BatchStepExecution();
            ser.setStepExecutionId(se.getId());
            ser.setStepName(se.getStepName());
            ser.setStatus(se.getStatus().toString());
            for (Throwable th : se.getFailureExceptions()) {
                ser.getFailureExceptions().add(th.toString());
            }
            dest.getBatchStepExecutions().add(ser);
        }
        dest.setStatus(src.getStatus().toString());
        dest.setExitStatus(src.getExitStatus().toString());
    }
}
