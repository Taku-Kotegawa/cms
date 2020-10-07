package jp.co.stnet.cms.app.simpleentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEntityListRow implements Serializable {

    private String operations;

    private String DT_RowId;

    private String DT_RowClass;

    private Map<String, String> DT_RowAttr;

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

    // ------------------------------------------------

    private Long id;

    /**
     * ステータス
     */
    private String statusLabel;

    /**
     * テキストフィールド
     */
    private String text01;

    /**
     * テキストフィールド(数値・整数)
     */
    private Integer text02;

    /**
     * テキストフィールド(数値・小数あり)
     */
    private Float text03;

    /**
     * テキストフィールド(真偽値)
     */
    private Boolean text04;


    private Collection<String> text05;

}
