package jp.co.stnet.cms.app.admin.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;

/**
 * 店所管理の一覧の行のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopListRow implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 7397686789484547382L;

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

    /**
     * 内部ID
     */
    private Long id;

    /**
     * ステータス
     */
    private String statusLabel;

    /**
     * 店所コード
     */
    private String shopCode;

    /**
     * 並び順
     */
    private Integer weight;

    /**
     * 店所名
     */
    private String title;

}