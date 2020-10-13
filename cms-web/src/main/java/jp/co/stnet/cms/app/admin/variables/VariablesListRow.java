package jp.co.stnet.cms.app.admin.variables;

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
 * 変数管理の一覧の行のBean
 *
 * @author Automatically generated
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariablesListRow implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8254599239709457334L;

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
     * ステータス
     */
    private String statusLabel;

    /**
     * ID
     */
    private Long id;
    /**
     * カテゴリ
     */
    private String category;
    /**
     * タイプ
     */
    private String type;
    /**
     * 値
     */
    private String value;
    /**
     * 備考
     */
    private String remark;

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