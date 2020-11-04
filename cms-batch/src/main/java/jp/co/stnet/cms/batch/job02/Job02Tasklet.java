package jp.co.stnet.cms.batch.job02;

import com.github.dozermapper.core.Mapper;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.time.LocalDateTime;
import java.util.List;

import static jp.co.stnet.cms.domain.common.Constants.MSG;

/**
 * job02ジョブ実装
 * CSV -> VARIABLEテーブル
 */
@Component
public class Job02Tasklet implements Tasklet {

    @Autowired
    VariableRepository variableRepository;

    @Autowired
    ItemStreamReader<VariableCsv> csvReader;

    @Autowired
    SmartValidator smartValidator;

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
        int count_skip   = 0;

        // DB操作時の例外発生の有無を記録する
        boolean hasDBAccessException = false;

        // CSVファイルを１行を格納するmodel
        VariableCsv csvLine = null;

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
                Variable v = map(csvLine);
                try {
                    Variable current = findByTypeAndCode(v.getType(),v.getCode());
                    if (current == null) {
                        v.setVersion(0L);
                        count_insert = count_insert + variableRepository.insert(v);
                    } else {
                        v.setId(current.getId());
                        v.setFile1uuid(current.getFile1uuid()); // 添付ファイルはCSVで更新しない
                        if (!v.equals(current)) {
                            log.info(v.toString());
                            log.info(current.toString());
                            v.setVersion(current.getVersion() + 1L);    // リポジトリ側で対処している場合は不要
                            v.setCreatedBy(current.getCreatedBy());     // リポジトリ側で対処している場合は不要
                            v.setCreatedDate(current.getCreatedDate()); // リポジトリ側で対処している場合は不要
                            count_update = count_update = variableRepository.updateByPrimaryKeyWithBLOBs(v);
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

        } finally {
            csvReader.close();
        }

        log.info("読込件数:    " + count_read);
        log.info("挿入件数:    " + count_insert);
        log.info("更新件数:    " + count_update);
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
    private Variable map(VariableCsv csv) {
        String JOB_EXECUTOR = "job_user";

        Variable v = beanMapper.map(csv, Variable.class);
        v.setCreatedDate(LocalDateTime.now());
        v.setLastModifiedDate(LocalDateTime.now());
        v.setCreatedBy(JOB_EXECUTOR);
        v.setLastModifiedBy(JOB_EXECUTOR);
        return v;
    }

    private Variable findByTypeAndCode(String type, String code) {
        VariableExample example = new VariableExample();
        example.or().andTypeEqualTo(type).andCodeEqualTo(code);
        List<Variable> list = variableRepository.selectByExampleWithBLOBs(example);

        if (list.size()>0) {
            return list.get(0);
        } else {
            return null;
        }

    }

}
