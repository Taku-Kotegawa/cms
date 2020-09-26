package jp.co.stnet.cms.app.common.downloadview;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.web.download.AbstractFileDownloadView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Component
public class FileManagedDownloadView extends AbstractFileDownloadView {

    @Autowired
    FileManagedSharedService fileManagedSharedService;
    @Value("${file.store.basedir}")
    private String STORE_BASEDIR;

    @Override
    protected InputStream getInputStream(Map<String, Object> model, HttpServletRequest request) throws IOException {
        FileManaged fileManaged = (FileManaged) model.get("fileManaged");
        return new FileInputStream(new File(STORE_BASEDIR + fileManaged.getUri()));
    }

    @Override
    protected void addResponseHeader(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        FileManaged fileManaged = (FileManaged) model.get("fileManaged");
        response.setContentType(fileManaged.getFilemime());
        response.setHeader("Content-Disposition", fileManaged.getAttachmentContentDisposition().toString());
        log.info(response.toString());
    }
}
