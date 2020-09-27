package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String role;

    private String label;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable=false)
    private List<Permission> permissions;
}
