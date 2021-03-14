package jp.co.stnet.cms.domain.model.common;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {@Index(columnList = "uuid, status")})
public class FileManaged extends AbstractEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    private String uuid;

    @KeywordField
    private String originalFilename;

    @Column(unique = true)
    private String uri;

    private String filemime;

    private Long filesize;

    private String filetype;

    /**
     * false: temporary(0), true: permanent(1)
     */
    @Column(columnDefinition = "varchar(255) default '0'")
    private String status;

    /**
     * @return
     */
    public MediaType getMediaType() {
        if (filemime != null) {
            String[] mimeArray = filemime.split("/");
            return new MediaType(mimeArray[0], mimeArray[1]);
        } else {
            return null;
        }
    }

    /**
     * @return
     */
    public ContentDisposition getAttachmentContentDisposition() {
        if (originalFilename != null) {
            String encodedFilename = "";
            try {
                encodedFilename = URLEncoder.encode(originalFilename, "UTF-8");
            } catch (
                    UnsupportedEncodingException e) {
                encodedFilename = originalFilename;
            }
            if (isOpenWindows()) {
                return ContentDisposition.builder("filename=\"" + encodedFilename + "\"").build();
            } else {
                return ContentDisposition.builder("attachment;filename=\"" + encodedFilename + "\"").build();
            }
        } else {
            return null;
        }
    }

    private boolean isOpenWindows() {
        return MediaType.APPLICATION_PDF_VALUE.equals(filemime);
    }

    @Override
    public Long getId() {
        return fid;
    }

    @Override
    public boolean isNew() {
        return fid == null;
    }

}
