package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.Role;
import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.model.report.Report;
import jp.co.stnet.cms.domain.repository.report.DocumentRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DocumentServiceImpl extends AbstractNodeService<Document, Long> implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;


    @Override
    protected JpaRepository<Document, Long> getRepository() {
        return this.documentRepository;
    }

    @Override
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));
    }

    @Override
    protected List<Object> getEnumListByName(String fieldName, List<String> values) {
        List<Object> list = new ArrayList<>();
        for (String value : values) {
            if ("jp.co.stnet.cms.domain.model.report.Report".equals(fieldMap.get(fieldName))) {
                try {
                    list.add(Report.valueOf(value));
                } catch (IllegalArgumentException e) {
                    // Enumが取得できない場合は何もしない
                }
            }
        }
        return list;
    }

}
