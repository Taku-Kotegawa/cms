package jp.co.stnet.cms.app.admin.account;

import jp.co.stnet.cms.app.common.validation.NotContainControlChars;
import jp.co.stnet.cms.app.common.validation.NotContainControlCharsExceptNewlines;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AccountForm {

    @NotNull                    //null禁止
    @NotContainControlChars     //制御文字禁止
    @Size(min = 4, max = 128)   //4文字以上128文字以下
    private String username;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    private String firstName;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    private String lastName;

    @NotNull
    @NotContainControlChars
    @Size(min = 1, max = 128)
    private String email;

    @NotNull(groups = Create.class)
    @NotContainControlChars
    private String password;

    @NotNull
    @NotContainControlChars
    private String url;

    // ダミー
    private String image;

    @NotNull
    private String imageUuid;

    @NotNull
    @NotContainControlCharsExceptNewlines
    private String profile;

    public interface Create {
    }

    public interface Update {
    }

}
