package jp.co.stnet.cms.domain.service.report;

import jp.co.stnet.cms.domain.common.datatables.Column;
import jp.co.stnet.cms.domain.common.datatables.DataTablesInput;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.Role;
import jp.co.stnet.cms.domain.model.report.PageIdx;
import jp.co.stnet.cms.domain.repository.report.PageIdxRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class PageIdxServiceImpl extends AbstractNodeService<PageIdx, Long> implements PageIdxService {

    @Autowired
    PageIdxRepository pageIdxRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected JpaRepository<PageIdx, Long> getRepository() {
        return this.pageIdxRepository;
    }

    @Override
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));
    }

    @Override
    public SearchResult<PageIdx> searchByInput(DataTablesInput input) {

        SearchSession searchSession = Search.session(entityManager);

        AggregationKey<Map<String, Long>> countsByGenreKey = AggregationKey.of("countsByGenre");

        int pageSize = input.getLength();
        long offset = input.getStart();

        SearchScope<PageIdx> scope = searchSession.scope( PageIdx.class );

        SearchResult<PageIdx> result = searchSession.search(PageIdx.class)
                .where(
                        f -> {
                            BooleanPredicateClausesStep<?> b = f.bool();
                            int filterFieldNum = 0;

                            for (Column column : input.getColumns()) {
                                String fieldName = column.getData();
                                String value = input.getColumn(fieldName).getSearch().getValue();
                                if (value != null) {
                                    b = b.must(f.match().fields(fieldName).matching(value));
                                    filterFieldNum++;
                                }
                            }

                            if (filterFieldNum > 0) {
                                return b;
                            } else {
                                return f.matchAll();
                            }
                        }
                )
                .aggregation(countsByGenreKey, f -> f.terms()
                        .field("customerNumber", String.class))
                .sort(f -> f.score())
                .fetch((int) offset, pageSize);

        return result;
    }
}
