package jp.co.stnet.cms.app.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 従業員管理の編集画面のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonForm implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8595643245585576896L;

    // TODO validation をカスタマイズ


    /**
     * ID
     */
    private Long id;

    /**
     * バージョン
     */
    private Long version;

    /**
     * ステータス
     */
    private String status;


    /**
     * 氏名
     */
    @NotNull
    private String name;

    /**
     * 年齢
     */
    @NotNull
    private Integer age;


    public interface Create {
    }

    public interface Update {
    }

}