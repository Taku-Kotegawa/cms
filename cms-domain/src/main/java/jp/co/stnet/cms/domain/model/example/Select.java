package jp.co.stnet.cms.domain.model.example;


import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SelectTable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Select extends AbstractEntity<Long> implements Serializable, StatusInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * ID
     */
    private Long id;

    /**
     * ステータス
     */
    private String status;

    /**
     * 文字列型
     */
    private String select01;

    /**
     * 整数型
     */
    private String select02;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}

