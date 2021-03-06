package jp.co.stnet.cms.app.authentication.passwordchange;

import jp.co.stnet.cms.domain.common.validation.ConfirmOldPassword;
import jp.co.stnet.cms.domain.common.validation.NotReusedPassword;
import jp.co.stnet.cms.domain.common.validation.StrongPassword;
import lombok.Data;
import org.terasoluna.gfw.common.validator.constraints.Compare;

import java.io.Serializable;

@Data
@Compare(left = "newPassword", right = "confirmNewPassword", operator = Compare.Operator.EQUAL)
@StrongPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@NotReusedPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@ConfirmOldPassword(usernamePropertyName = "username", oldPasswordPropertyName = "oldPassword")
public class PasswordChangeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;

}
