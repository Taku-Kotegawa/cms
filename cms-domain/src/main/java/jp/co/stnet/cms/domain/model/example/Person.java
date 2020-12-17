package jp.co.stnet.cms.domain.model.example;


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

@Entity
@Indexed
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Person extends AbstractEntity<Long> implements Serializable, StatusInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @FullTextField(analyzer = "japanese")
    private String name;

    @GenericField(aggregable = Aggregable.YES)
    private Integer age;

    @KeywordField(aggregable = Aggregable.YES)
    private String code;

    @FullTextField(analyzer = "japanese")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    /**
     * ファイル(FileManaged)
     */
    private String attachedFile01Uuid;

    /**
     * ファイル(FileManaged)
     */
    @OneToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "attachedFile01Uuid", referencedColumnName = "uuid", unique=true, insertable = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.NO)
    private FileManaged attachedFile01Managed;

    @Override
    public boolean isNew() {
        return id == null;
    }

}
