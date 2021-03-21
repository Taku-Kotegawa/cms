package jp.co.stnet.cms.app.select;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * セレクトテスト管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class SelectCsvBean implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3680738593761907450L;

    // TODO 項目のカスタマイズ

    /**
     * 内部ID
     */
    @CsvColumn(name = "ID")
    private Long id;
    /**
     * ステータス
     */
    @CsvColumn(name = "ステータス")
    private String statusLabel;
    /**
     * 文字列型
     */
    @CsvColumn(name = "文字列型")
    @NotNull
    private String select01;
    /**
     * 整数型
     */
    @CsvColumn(name = "整数型")
    @NotNull
    private String select02;
}