package jp.co.stnet.cms.batch.job04;

import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.service.common.IndexSharedService;
import jp.co.stnet.cms.domain.service.report.PageIdxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Job04Tasklet implements Tasklet {


    @Autowired
    PageIdxService pageIdxService;

    @Autowired
    IndexSharedService indexSharedService;

    private static final Logger log = LoggerFactory.getLogger("JobLogger");


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Long jobInstanceId = chunkContext.getStepContext().getJobInstanceId();
        String jobName = chunkContext.getStepContext().getJobName();
        Long jobExecutionId = chunkContext.getStepContext().getStepExecution().getJobExecutionId();
        Map<String, Object> jobParams = chunkContext.getStepContext().getJobParameters();
        String entityName = (String) jobParams.get("entityName");

        MDC.put("jobInstanceId", jobInstanceId.toString());
        MDC.put("jobName", jobName);
        MDC.put("jobExecutionId", jobExecutionId.toString());
        MDC.put("jobName_jobExecutionId", jobName + "_" + jobExecutionId.toString());

        log.info("Start");

        // ルシーンのファイルがロックされるのでWebアプリが起動している状態でジョブからインデックスの再作成はできない。(エラーになる)
//        indexSharedService.reindexingSync("jp.co.stnet.cms.domain.model.report.PageIdx");

        PageIdx pageIdx = PageIdx.builder()
                .customerNumber("111aaacccccccc")
                .customerName("aaabbbc")
                .startPage(3)
                .documentId(3L)
                .attachedFileUuid("aaaaaaab")
                .status("1")
                .build();

        pageIdxService.save(pageIdx);


        log.info("End");

        MDC.clear();

        return RepeatStatus.FINISHED;

    }
}
