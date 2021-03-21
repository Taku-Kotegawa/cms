package jp.co.stnet.cms.app.select;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * セレクトテスト管理の一覧の行のBean
 *
 * @author Automatically generated
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectListRow implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3680738593761907450L;

    /**
     * 操作
     */
    private String operations;

    /**
     * DataTables RowID
     */
    private String DT_RowId;

    /**
     * DataTables RowClass
     */
    private String DT_RowClass;

    /**
     * DataTables RT_RowAttr
     */
    private Map<String, String> DT_RowAttr;

    /**
     * 最終更新日
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    /**
     * 内部ID
     */
    private Long id;
    /**
     * ステータス
     */
    private String statusLabel;
    /**
     * 文字列型
     */
    private String select01;
    /**
     * 整数型
     */
    private String select02;

    @JsonProperty("DT_RowId")
    public String getDT_RowId() {
        return id.toString();
    }

    @JsonProperty("DT_RowClass")
    public String getDT_RowClass() {
        return DT_RowClass;
    }

    @JsonProperty("DT_RowAttr")
    public Map<String, String> getDT_RowAttr() {
        return DT_RowAttr;
    }

}