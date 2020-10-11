package jp.co.stnet.cms.domain.model.common;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import lombok.*;
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
@Table(indexes = {@Index(columnList = "uuid, status")})
public class FileManaged extends AbstractEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    private String uuid;

    private String originalFilename;

    @Column(unique = true)
    private String uri;

    private String filemime;

    private Long filesize;

    private String filetype;

    /**
     * false: temporary, true: permanent
     */
    @Column(columnDefinition = "boolean default false")
    private boolean status;

    /**
     *
     * @return
     */
    public MediaType getMediaType() {
        String[] mimeArray = filemime.split("/");
        return new MediaType(mimeArray[0], mimeArray[1]);
    }

    /**
     *
     * @return
     */
    public ContentDisposition getAttachmentContentDisposition() {
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

    }

    private boolean isOpenWindows() {
        if (MediaType.APPLICATION_PDF_VALUE.equals(filemime)) {
            return true;
        } else {
            return false;
        }
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
