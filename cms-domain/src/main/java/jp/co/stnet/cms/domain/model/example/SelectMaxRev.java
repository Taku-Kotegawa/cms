package jp.co.stnet.cms.domain.model.example;

import jp.co.stnet.cms.domain.model.AbstractMaxRevEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * SelectMexRevエンティティ.(複数の値をフィールドをStringに格納するサンプル)
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SelectMaxRev extends AbstractMaxRevEntity<Long> implements Serializable {
}
