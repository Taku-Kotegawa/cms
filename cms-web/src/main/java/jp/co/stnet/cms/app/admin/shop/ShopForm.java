package jp.co.stnet.cms.app.admin.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;

/**
 * 店所管理の編集画面のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopForm implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 7397686789484547382L;

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
     * 店所コード
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]*$")
    private String shopCode;

    /**
     * 並び順
     */
    @NotNull
    private Integer weight;

    /**
     * 店所名
     */
    private String title;


    public interface Create {
    }

    public interface Update {
    }

}