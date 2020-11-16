package jp.co.stnet.cms.batch.job03;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.exception.NoChangeBusinessException;
import jp.co.stnet.cms.domain.model.example.SimpleEntity;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRepository;
import jp.co.stnet.cms.domain.repository.example.SimpleEntityRevisionRepository;
import jp.co.stnet.cms.domain.service.example.SimpleEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import static jp.co.stnet.cms.domain.common.Constants.MSG;

/**
 * job02ジョブ実装
 * CSV -> VARIABLEテーブル
 */
@Component
@Transactional
public class Job03Tasklet implements Tasklet {

    @Autowired
    ItemStreamReader<SimpleEntityCsv> csvReader;

    @Autowired
    SmartValidator smartValidator;

    @Autowired
    SimpleEntityRepository simpleEntityRepository;

    @Autowired
    SimpleEntityRevisionRepository simpleEntityRevisionRepository;

    @Autowired
    SimpleEntityService simpleEntityService;

    @Autowired
    Mapper beanMapper;

    private static final Logger log = LoggerFactory.getLogger("JobLogger");

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Long jobInstanceId = chunkContext.getStepContext().getJobInstanceId();
        String jobName = chunkContext.getStepContext().getJobName();
        Long jobExecutionId = chunkContext.getStepContext().getStepExecution().getJobExecutionId();

        MDC.put("jobInstanceId", jobInstanceId.toString());
        MDC.put("jobName", jobName);
        MDC.put("jobExecutionId", jobExecutionId.toString());
        MDC.put("jobName_jobExecutionId", jobName + "_" + jobExecutionId.toString());

        int count_read   = 0;
        int count_insert = 0;
        int count_update = 0;
        int count_delete = 0;
        int count_skip   = 0;

        // DB操作時の例外発生の有無を記録する
        boolean hasDBAccessException = false;

        // CSVファイルを１行を格納するBean
        SimpleEntityCsv csvLine = null;

        // CSVファイルの入力チェック結果を格納するBindingResult
        BindingResult result = new BeanPropertyBindingResult(csvLine, "csvLine");

        log.info("Start");

        try {
            /*
             * 入力チェック(全件チェック)
             */
            csvReader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((csvLine = csvReader.read()) != null) {
                smartValidator.validate(csvLine, result);
            }
            if (result.hasErrors()) {
                result.getAllErrors().forEach(r -> log.error(r.toString()));
                csvReader.close();
                log.error(MSG.VALIDATION_ERROR_STOP);
                throw new ValidationException(MSG.VALIDATION_ERROR_STOP);
            }
            csvReader.close();

            /*
             * テーブル更新(一括コミット)
             */
            csvReader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((csvLine = csvReader.read()) != null) {
                count_read++;
                try {

                    SimpleEntity v = map(csvLine);
                    if (csvLine.getId() == null) {
                        // 新規登録
                        simpleEntityService.save(v);
                        count_insert++;
                    } else {
                        // 更新
                        SimpleEntity current = simpleEntityRepository.findById(Long.parseLong(csvLine.getId())).orElse(null);
                        if (current != null) {
                            if ("99".equals(csvLine.getStatus())) {
                                    simpleEntityService.delete(current.getId());
                                    count_delete++;
                            } else {
                                try {
                                    v.setVersion(current.getVersion());
                                    simpleEntityService.save(v);
                                    count_update++;
                                } catch (NoChangeBusinessException e) {
                                    count_skip++;
                                }
                            }

                        } else {
                            count_skip++;
                        }
                    }

                } catch (Exception e) {
                    log.error("Exception: " + e.getLocalizedMessage());
                    hasDBAccessException = true;
                }
            }

            if (hasDBAccessException) {
                log.error(MSG.DB_ACCESS_ERROR_STOP);
                throw new Exception(MSG.DB_ACCESS_ERROR_STOP);
            }

        } catch (Exception e) {
            log.error("Exception: " + e.getLocalizedMessage());
            throw e;

        } finally {
            csvReader.close();
        }

        log.info("読込件数:    " + count_read);
        log.info("挿入件数:    " + count_insert);
        log.info("更新件数:    " + count_update);
        log.info("削除件数:    " + count_delete);
        log.info("スキップ件数: " + count_skip);
        log.info("End");

        MDC.clear();

        return RepeatStatus.FINISHED;
    }

    /**
     * CSV->Variable変換
     *
     * @param csv CSV一行を表すModel
     * @return VariableのModel
     */
    private SimpleEntity map(SimpleEntityCsv csv) {
        String JOB_EXECUTOR = "job_user";
        SimpleEntity v = beanMapper.map(csv, SimpleEntity.class);
        return v;
    }

}
