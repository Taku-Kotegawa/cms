package jp.co.stnet.cms.domain.repository.example;


import jp.co.stnet.cms.domain.model.example.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
