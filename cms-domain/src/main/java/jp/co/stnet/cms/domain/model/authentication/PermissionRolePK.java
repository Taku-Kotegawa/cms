package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

public class PermissionRolePK implements Serializable {

    private Permission permission;

    private Role role;

}
