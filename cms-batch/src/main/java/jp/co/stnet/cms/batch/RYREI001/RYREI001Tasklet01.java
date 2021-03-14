package jp.co.stnet.cms.batch.RYREI001;

import com.github.dozermapper.core.Mapper;
import jp.co.stnet.cms.domain.common.MimeTypes;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.repository.common.FileManagedRepository;
import jp.co.stnet.cms.domain.repository.report.DocumentRepository;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.report.DocumentService;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

import static jp.co.stnet.cms.domain.common.Constants.MSG;

/**
 * RYREI001ジョブ実装
 * Document取込
 * CSV -> VARIABLEテーブル
 */
@Component
@Transactional
public class RYREI001Tasklet01 implements Tasklet {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ItemStreamReader<RYREI001Lst00Csv> csvReader01;

    @Autowired
    SmartValidator smartValidator;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentService documentService;

    @Autowired
    Mapper beanMapper;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    @Autowired
    FileManagedRepository fileManagedRepository;

    private static final Logger log = LoggerFactory.getLogger("JobLogger");

    private static final String INITIAL_STATUS = "1";
    private static final String JOB_EXECUTOR = "job_user";

    @Value("${file.store.basedir}")
    private String STORE_BASEDIR;

    @Value("${file.store.default_file_type}")
    private String DEFAULT_FILE_TYPE;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        String inputFile01 = chunkContext.getStepContext().getJobParameters().get("inputFile01").toString();
        //File file = chunkContext.getStepContext().

        Long jobInstanceId = chunkContext.getStepContext().getJobInstanceId();
        String jobName = chunkContext.getStepContext().getJobName();
        Long jobExecutionId = chunkContext.getStepContext().getStepExecution().getJobExecutionId();

        MDC.put("jobInstanceId", jobInstanceId.toString());
        MDC.put("jobName", jobName);
        MDC.put("jobExecutionId", jobExecutionId.toString());
        MDC.put("jobName_jobExecutionId", jobName + "_" + jobExecutionId.toString());

        int count_read   = 0;
        int count_insert = 0;

        // DB操作時の例外発生の有無を記録する
        boolean hasDBAccessException = false;

        // CSVファイルを１行を格納するBean
        RYREI001Lst00Csv csvLine = null;

        // CSVファイルの入力チェック結果を格納するBindingResult
        BindingResult result = new BeanPropertyBindingResult(csvLine, "csvLine");

        log.info("Start");

        try {
            /*
             * 入力チェック(全件チェック)
             */
            csvReader01.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((csvLine = csvReader01.read()) != null) {
                smartValidator.validate(csvLine, result);
            }
            if (result.hasErrors()) {
                result.getAllErrors().forEach(r -> log.error(r.toString()));
                csvReader01.close();
                log.error(MSG.VALIDATION_ERROR_STOP);
                throw new ValidationException(MSG.VALIDATION_ERROR_STOP);
            }
            csvReader01.close();

            /*
             * テーブル更新(一括コミット)
             */
            csvReader01.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((csvLine = csvReader01.read()) != null) {
                count_read++;
                try {
                    //ファイルアップロード
                    File storeFile = new File(inputFile01 +  "/" + csvLine.getAttachedFile());
                    String mimeType = "";
                    mimeType = MimeTypes.getMimeType(FilenameUtils.getExtension(storeFile.getName()));
                    FileManaged fileManaged = fileManagedSharedService.store(storeFile,mimeType);

                    // 新規登録
                    Document v = map(csvLine);
                    v.setStatus(INITIAL_STATUS);
                    v.setAttachedFileUuid(fileManaged.getUuid());
                    //v.setCreatedBy(JOB_EXECUTOR);
                    documentService.save(v);

                        count_insert++;
                } catch (Exception e) {
                    log.error("Exception: " + e.getMessage());
                    hasDBAccessException = true;
                }
            }

            if (hasDBAccessException) {
                log.error(MSG.DB_ACCESS_ERROR_STOP);
                throw new Exception(MSG.DB_ACCESS_ERROR_STOP);
            }

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw e;

        } finally {
            csvReader01.close();
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
    private Document map(RYREI001Lst00Csv csv) {
        Document v = beanMapper.map(csv, Document.class);
        return v;
    }

}
