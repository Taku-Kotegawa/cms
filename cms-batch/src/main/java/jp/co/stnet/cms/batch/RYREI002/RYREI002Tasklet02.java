package jp.co.stnet.cms.batch.RYREI002;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.batch.RYREI001.RYREI001Idx00Csv;
import jp.co.stnet.cms.domain.common.CustomDateFactory;
import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.repository.report.PageIdxRepository;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.report.DocumentService;
import jp.co.stnet.cms.domain.service.report.PageIdxService;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jp.co.stnet.cms.domain.common.Constants.MSG;

/**
 * RYREI001ジョブ実装
 * CSV -> VARIABLEテーブル
 */
@Component
@Transactional
public class RYREI002Tasklet02 implements Tasklet {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ItemStreamReader<RYREI002Idx00Csv> csvReader02;

    @Autowired
    SmartValidator smartValidator;

    @Autowired
    PageIdxRepository pageIdxRepository;

    @Autowired
    PageIdxService pageIdxService;

    @Autowired
    DocumentService documentService;

    @Autowired
    Mapper beanMapper;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    CustomDateFactory dateFactory;

    private static final Logger log = LoggerFactory.getLogger("JobLogger");

    private static final String INITIAL_STATUS = "1";
    private static final String JOB_EXECUTOR = "job_user";

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Long jobInstanceId = chunkContext.getStepContext().getJobInstanceId();
        String jobName = chunkContext.getStepContext().getJobName();
        Long jobExecutionId = chunkContext.getStepContext().getStepExecution().getJobExecutionId();

        MDC.put("jobInstanceId", jobInstanceId.toString());
        MDC.put("jobName", jobName);
        MDC.put("jobExecutionId", jobExecutionId.toString());
        MDC.put("jobName_jobExecutionId", jobName + "_" + jobExecutionId.toString());

        int count_read = 0;
        int count_insert = 0;

        // DB操作時の例外発生の有無を記録する
        boolean hasDBAccessException = false;

        // CSVファイルを１行を格納するBean
        RYREI002Idx00Csv csvLine = null;

        // CSVファイルの入力チェック結果を格納するBindingResult
        BindingResult result = new BeanPropertyBindingResult(csvLine, "csvLine");

        log.info("Start");

        try {
            /*
             * 入力チェック(全件チェック)
             */
            csvReader02.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((csvLine = csvReader02.read()) != null) {
                smartValidator.validate(csvLine, result);
            }
            if (result.hasErrors()) {
                result.getAllErrors().forEach(r -> log.error(r.toString()));
                csvReader02.close();
                log.error(MSG.VALIDATION_ERROR_STOP);
                throw new ValidationException(MSG.VALIDATION_ERROR_STOP);
            }
            csvReader02.close();

            /*
             * テーブル更新(一括コミット)
             */
            csvReader02.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            int i = 0;
            String prevFileName = "";
            List<Document> documents = new ArrayList<>();
            while ((csvLine = csvReader02.read()) != null) {
                count_read++;
                try {

                    i++;

                    PageIdx v = map(csvLine);

                    if (!prevFileName.equals(csvLine.getFileName())) {
                        documents = documentService.findByTitle(csvLine.getFileName());
                        prevFileName = csvLine.getFileName();
                    }

                    if (!documents.isEmpty()) {
                        v.setDocumentId(documents.get(0).getId());
                        v.setAttachedFileUuid(documents.get(0).getAttachedFileUuid());
                    }

//                    entityManager.persist(v);
//                    pageIdxRepository.saveAndFlush(v);
                    pageIdxService.save(v);

                    if (i % 100 == 0) {
                        entityManager.flush();
                        entityManager.clear();
                        System.out.println(i + " saved");
                    }

                    count_insert++;

                } catch (Exception e) {
                    log.error("Exception: " + e.getMessage());
                    System.out.println(e.getMessage());
                    hasDBAccessException = true;
                }
            }

            if (hasDBAccessException) {
                log.error(MSG.DB_ACCESS_ERROR_STOP);
                throw new Exception(MSG.DB_ACCESS_ERROR_STOP);
            }

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            System.out.println(e.getMessage());
            throw e;

        } finally {
            csvReader02.close();
        }

        log.info("読込件数:    " + count_read);
        log.info("挿入件数:    " + count_insert);
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
    private PageIdx map(RYREI002Idx00Csv csv) {
        PageIdx v = beanMapper.map(csv, PageIdx.class);
        v.setStatus(INITIAL_STATUS);
        v.setCreatedBy(JOB_EXECUTOR);
        v.setCreatedDate(dateFactory.newLocalDateTime());
        v.setLastModifiedBy((JOB_EXECUTOR));
        v.setLastModifiedDate(dateFactory.newLocalDateTime());
        return v;
    }

}
