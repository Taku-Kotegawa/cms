package jp.co.stnet.cms.domain.model.common;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class FileManaged extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    private String uuid;

    private String originalFilename;

    @Column(unique = true)
    private String uri;

    private String filemime;

    private Long filesize;

    /**
     * false: temporary, true: permanent
     */
    @Column(columnDefinition = "boolean default false")
    private boolean status;

}
