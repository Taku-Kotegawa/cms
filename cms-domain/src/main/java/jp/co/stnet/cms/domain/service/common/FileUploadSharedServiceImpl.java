package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.common.TempFile;
import jp.co.stnet.cms.domain.repository.common.TempFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.time.LocalDateTime;
import java.util.UUID;

import static jp.co.stnet.cms.domain.common.message.MessageKeys.E_SL_FW_5001;


@Slf4j
@Service
public class FileUploadSharedServiceImpl implements FileUploadSharedService {

    @Autowired
    TempFileRepository tempFileRepository;

    @Override
    public String uploadTempFile(TempFile tempFile) {
        tempFile.setId(UUID.randomUUID().toString());
        tempFileRepository.save(tempFile);
        return tempFile.getId();
    }

    @Override
    public TempFile findTempFile(String id) {
        return tempFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResultMessages.error().add(E_SL_FW_5001, id)));
    }

    @Override
    public void deleteTempFile(String id) {
        tempFileRepository.deleteById(id);
    }

    @Override
    public void cleanUp(LocalDateTime deleteTo) {
        tempFileRepository.deleteByUploadedDateLessThan(deleteTo);
    }
}
