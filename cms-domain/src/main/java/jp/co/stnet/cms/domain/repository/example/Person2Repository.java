package jp.co.stnet.cms.domain.repository.example;


import jp.co.stnet.cms.domain.model.example.Person2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Person2Repository extends JpaRepository<Person2, Long> {
}
