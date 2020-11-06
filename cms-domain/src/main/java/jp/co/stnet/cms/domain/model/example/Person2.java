package jp.co.stnet.cms.domain.model.example;


import jp.co.stnet.cms.domain.model.AbstractEntity;
import jp.co.stnet.cms.domain.model.StatusInterface;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSON")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Person2 extends AbstractEntity<Long> implements Serializable, StatusInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    private String name;

    private Integer age;

    private String memo;

    @Override
    public boolean isNew() {
        return id == null;
    }

}