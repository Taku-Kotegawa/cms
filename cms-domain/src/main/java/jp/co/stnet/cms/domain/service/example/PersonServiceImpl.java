package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.example.Person;
import jp.co.stnet.cms.domain.repository.example.PersonRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class PersonServiceImpl extends AbstractNodeService<Person, Long> implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    protected JpaRepository<Person, Long> getRepository() {
        return this.personRepository;
    }

    @Override
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }
}
