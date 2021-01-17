package jp.co.stnet.cms.domain.model.report;

import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Indexed
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Document extends AbstractEntity<Long> implements Serializable, StatusInterface {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ステータス
     */
    @KeywordField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private String status;


    /**
     * ドキュメント名
     */
    @KeywordField
    @Column(nullable = false)
    private String title;

//    /**
//     * レポートコード
//     */
//    @Column(nullable = false)
//    private String reportCode;

    /**
     * レポート(Enum)
     */
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.NO)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Report report;

    public String getReportLabel() {
        return report.getTitle();
    }

    /**
     * 店所コード
     */
    @KeywordField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private String shopCode;

    @ManyToOne
    @JoinColumn(name = "shopCode", referencedColumnName = "shopCode", unique=true, insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Shop shop;

    /**
     * 年度
     */
    @GenericField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private Integer year;

    /**
     * 集計期間
     */
    @GenericField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private Integer period;

    /**
     * 発順
     */
    @GenericField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private Integer hatsujun;

    /**
     * お客さま番号(自)
     */
    private String customerNumberFrom;

    /**
     * お客さま番号(至)
     */
    private String customerNumberTo;

    /**
     * 有効期限
     */
    private LocalDate expirationDate;

    /**
     * 帳票出力日時
     */
    @GenericField(aggregable = Aggregable.YES)
    private LocalDateTime outputDate;

    /**
     * 添付ファイル
     */
    private String attachedFileUuid;

    @OneToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "attachedFileUuid", referencedColumnName = "uuid", unique=true, insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.NO)
    private FileManaged attachedFileManaged;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
