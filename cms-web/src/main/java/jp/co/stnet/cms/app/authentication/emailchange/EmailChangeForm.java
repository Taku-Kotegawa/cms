package jp.co.stnet.cms.app.authentication.emailchange;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EmailChangeForm {


    /**
     * 新規メール
     */
    @Email
    @NotNull
    private String newEmail;
}
