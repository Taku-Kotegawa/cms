package jp.co.stnet.cms.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<U> {

    @Version
    private Long version;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}