package jp.co.stnet.cms.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @Column(nullable = false)
    private Integer revType;

    @Column(nullable = false)
    private Long version;

    private String createdBy;

    private String lastModifiedBy;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @Column(nullable = false)
    private String status;
}