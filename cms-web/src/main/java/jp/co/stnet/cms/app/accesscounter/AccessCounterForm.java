package jp.co.stnet.cms.app.accesscounter;

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

/**
 * アクセスカウンター管理の編集画面のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessCounterForm implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1396585187753711980L;

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
     * ステータス
     */
    private String statusLabel;

    /**
     * URL
     */
    private String url;

    /**
     * アクセス数
     */
    private Long count;


    public interface Create {
    }

    public interface Update {
    }

}