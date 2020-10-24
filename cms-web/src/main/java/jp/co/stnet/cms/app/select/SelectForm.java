package jp.co.stnet.cms.app.select;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * セレクトテスト管理の編集画面のBean
 *
 * @author Automatically generated
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectForm implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3680738593761907450L;

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
     * 文字列型
     */
    @NotNull
    private List<String> select01;

    /**
     * 整数型
     */
    @NotNull
    private List<Integer> select02;

    public interface Create {
    }

    public interface Update {
    }

}