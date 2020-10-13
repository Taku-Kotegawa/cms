package jp.co.stnet.cms.app.admin.variables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 変数管理の編集画面のBean
 *
 * @author Automatically generated
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariablesForm implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8254599239709457334L;

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
     * カテゴリ
     */
    @NotNull
    @Pattern(regexp = "^[A-Z]*$")
    private String category;

    /**
     * タイプ
     */
    @NotNull
    @Pattern(regexp = "^[A-Z]*$")
    private String type;

    /**
     * 値
     */
    @NotNull
    @ExistInCodeList(codeListId = "CL_ORDERSTATUS")
    private String value;

    /**
     * 備考
     */
    private String remark;

    public interface Create {
    }

    public interface Update {
    }


}