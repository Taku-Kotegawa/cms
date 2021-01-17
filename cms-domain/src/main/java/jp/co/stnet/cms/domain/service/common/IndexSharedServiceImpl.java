package jp.co.stnet.cms.domain.service.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@Transactional
public class IndexSharedServiceImpl implements IndexSharedService {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void reindexing(String entityName) throws InterruptedException, ClassNotFoundException {

        try {

            Class<?> clazz = Class.forName(entityName);
            SearchSession searchSession = Search.session(entityManager);
            MassIndexer indexer = searchSession.massIndexer(clazz)
                    .threadsToLoadObjects(7);
            indexer.startAndWait();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

//        SearchSession searchSession = Search.session( entityManager );
//
//        MassIndexer indexer = searchSession.massIndexer( Person.class )
//                .threadsToLoadObjects( 7 );
//
//        indexer.startAndWait();


    }
}
