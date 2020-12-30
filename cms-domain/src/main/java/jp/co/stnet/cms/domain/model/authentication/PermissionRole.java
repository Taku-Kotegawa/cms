package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(indexes = {@Index(columnList = "PERMISSION, ROLE", unique = true)})
@IdClass(PermissionRolePK.class)
public class PermissionRole {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permission permission;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
