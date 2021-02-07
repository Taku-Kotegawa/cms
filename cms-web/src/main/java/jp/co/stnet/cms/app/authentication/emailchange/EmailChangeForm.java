package jp.co.stnet.cms.app.authentication.emailchange;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EmailChangeForm {


    /**
     * 新規メール
     */
    @Email
    private String newEmail;
}
