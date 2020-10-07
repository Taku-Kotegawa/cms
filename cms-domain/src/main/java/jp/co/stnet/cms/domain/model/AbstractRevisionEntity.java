package jp.co.stnet.cms.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private Integer revType;

    private Long version;

    private String createdBy;

    private String lastModifiedBy;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private String status;
}