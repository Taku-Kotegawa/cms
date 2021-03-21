package jp.co.stnet.cms.domain.model.report;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 店所エンティティ
 */
@SuppressWarnings({"LombokDataInspection", "LombokEqualsAndHashCodeInspection"})
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Shop extends AbstractEntity<Long> implements Serializable, StatusInterface {

    /**
     * 内部ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ステータス
     */
    @Column(nullable = false)
    private String status;

    /**
     * 店所コード
     */
    @Column(nullable = false, unique = true)
    private String shopCode;

    /**
     * 並び順
     */
    @Column(nullable = false)
    private Integer weight;

    /**
     * 店所名
     */
    private String title;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
