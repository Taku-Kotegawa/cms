package jp.co.stnet.cms.domain.model.common;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {@Index(columnList = "URL", unique = true)})
public class AccessCounter extends AbstractEntity<Long> implements Serializable, StatusInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    /**
     * url
     */
    private String url;

    /**
     * アクセス数
     */
    private Long count;

    @Override
    public boolean isNew() {
        return url == null;
    }

}