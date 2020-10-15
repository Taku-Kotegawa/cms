package jp.co.stnet.cms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NodeRevRepository<T, ID> extends JpaRepository<T, Long> {

    T findByIdLatestRev(ID id);

}
