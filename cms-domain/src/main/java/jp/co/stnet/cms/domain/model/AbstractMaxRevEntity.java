package jp.co.stnet.cms.domain.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractMaxRevEntity<ID> {
    @Id
    private ID id;
    private Long rid;
}
