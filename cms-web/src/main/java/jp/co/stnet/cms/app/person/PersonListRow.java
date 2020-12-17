package jp.co.stnet.cms.app.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 従業員管理の一覧の行のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonListRow implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8595643245585576896L;

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
    private String statusLabel;

    /**
     * 氏名
     */
    private String name;

    /**
     * 年齢
     */
    private Integer age;

    private String code;

    /**
     * 添付ファイル(FileManaged UUID)
     */
    private String attachedFile01Uuid;

    /**
     * 添付ファイル(FileManaged)
     */
    private FileManaged attachedFile01Managed;

}