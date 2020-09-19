package jp.co.stnet.cms.domain.model.common;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class TempFile implements Serializable {

    @Id
    private String id;

    /**
     * オリジナルファイル名
     */
    private String originalName;

    /**
     * アップロード日時
     */
    @CreatedDate
    private LocalDateTime uploadedDate;

    /**
     * ファイル本体
     */
    @Lob
    private byte[] body;

}
