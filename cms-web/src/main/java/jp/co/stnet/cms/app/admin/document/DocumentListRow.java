package jp.co.stnet.cms.app.admin.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.domain.model.report.Report;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.stnet.cms.domain.model.common.FileManaged;

/**
 * 帳票ファイル管理の一覧の行のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentListRow implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8058964242496301506L;

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
     * ドキュメント名
     */
    private String title;

    /**
     * レポートコード
     */
    private Report report;

    /**
     * レポート(ラベル)
     */
    private String reportLabel;

    /**
     * 店所コード
     */
    private String shopCode;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 集計期間
     */
    private Integer period;

    /**
     * 発順
     */
    private Integer hatsujun;

    /**
     * お客さま番号(自)
     */
    private String customerNumberFrom;

    /**
     * お客さま番号(至)
     */
    private String customerNumberTo;

    /**
     * 有効期限
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate expirationDate;

    /**
     * 帳票出力日時
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime outputDate;

    /**
     * 添付ファイル
     */
    private FileManaged attachedFileManaged;

}