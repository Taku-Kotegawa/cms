package jp.co.stnet.cms.domain.model.example;

import jp.co.stnet.cms.domain.model.AbstractRevisionEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SelectRevision extends AbstractRevisionEntity implements Serializable {

    /**
     * ID
     */
    @Column(nullable = false)
    private Long id;

    /**
     * ステータス
     */
    @Column(nullable = false)
    private String status;

    /**
     * 文字列型
     */
    @NotNull
    private String select01;

    /**
     * 整数型
     */
    @NotNull
    private String select02;

}
