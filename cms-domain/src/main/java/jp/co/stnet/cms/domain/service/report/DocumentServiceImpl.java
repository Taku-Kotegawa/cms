package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.Role;
import jp.co.stnet.cms.domain.model.report.Document;
import jp.co.stnet.cms.domain.model.report.Report;
import jp.co.stnet.cms.domain.repository.report.DocumentRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class DocumentServiceImpl extends AbstractNodeService<Document, Long> implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @PersistenceContext
    EntityManager entityManager;


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

    @Override
    public SearchResult<Document> searchByAttachedFile(String attachedFile) {
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<Document> scope = searchSession.scope( Document.class );
        SearchResult<Document> result = searchSession.search(Document.class)
                .where(
                        f -> {
                            BooleanPredicateClausesStep<?> b = f.bool();
                            if (attachedFile != null) {
                                b = b.must(f.match().fields("attachedFile").matching(attachedFile));
                            }
                            return b;
                        }
                )
                .sort(f -> f.score())
                .fetch( 1 );
        return result;
    }

    @Override
    public List<Document> findByTitle(String title) {
        return documentRepository.findByTitle(title);
    }

}
