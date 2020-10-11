package jp.co.stnet.cms.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractEntity<ID> implements Persistable<ID> {

    @Version
    @Column(nullable = false)
    private Long version;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

}