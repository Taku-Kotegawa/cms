package jp.co.stnet.cms.domain.model.common;


import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(indexes = { @Index(name = "IDX_VARIABLE1", columnList = "type,code", unique = true) })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false) //falseに設定すること
@EntityListeners(AuditingEntityListener.class)
public class Variable extends AbstractEntity<Long> implements Serializable, StatusInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    /**
     * タイプ
     */
    @NotNull
    private String type;

    /**
     * コード
     */
    @NotNull
    private String code;

    /**
     * 値1
     */
    private String value1;

    /**
     * 値2
     */
    private String value2;

    /**
     * 値3
     */
    private String value3;

    /**
     * 値4
     */
    private String value4;

    /**
     * 値5
     */
    private String value5;

    /**
     * 値6
     */
    private String value6;

    /**
     * 値7
     */
    private String value7;

    /**
     * 値8
     */
    private String value8;

    /**
     * 値9
     */
    private String value9;

    /**
     * 値10
     */
    private String value10;

    /**
     * 日付1
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date1;

    /**
     * 日付2
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date2;

    /**
     * 日付3
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date3;

    /**
     * 日付4
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date4;

    /**
     * 日付5
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date5;

    /**
     * 整数1
     */
    private Integer valint1;

    /**
     * 整数2
     */
    private Integer valint2;

    /**
     * 整数3
     */
    private Integer valint3;

    /**
     * 整数4
     */
    private Integer valint4;

    /**
     * 整数5
     */
    private Integer valint5;

    /**
     * テキストエリア
     */
    @Column(columnDefinition = "TEXT")
    private String textarea;

    /**
     * ファイル1
     */
    private String file1Uuid;

    @Transient
    private FileManaged file1Managed;

    /**
     * 備考
     */
    private String remark;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}