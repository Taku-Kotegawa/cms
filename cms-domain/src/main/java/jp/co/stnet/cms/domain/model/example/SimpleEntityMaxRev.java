package jp.co.stnet.cms.domain.model.example;

import jp.co.stnet.cms.domain.model.AbstractMaxRevEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimpleEntityMaxRev extends AbstractMaxRevEntity<Long> implements Serializable {

}
