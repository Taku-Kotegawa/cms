package jp.co.stnet.cms.domain.repository.report;

import jp.co.stnet.cms.domain.model.report.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
