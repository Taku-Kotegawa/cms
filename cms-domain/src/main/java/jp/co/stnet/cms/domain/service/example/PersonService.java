package jp.co.stnet.cms.domain.service.example;


import jp.co.stnet.cms.domain.model.example.Person;
import jp.co.stnet.cms.domain.service.NodeIService;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.data.domain.Pageable;


public interface PersonService extends NodeIService<Person, Long> {


    void test(String term);

    SearchResult<Person> search(String term, Pageable pageable);

    String highlight(String text, String term);

}
