package jp.co.stnet.cms.app.admin.variable;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 変数管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class VariableCsvBean implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8260066904115119558L;

    /**
     * ID
     */
    @CsvColumn(name = "ID")
    private Long id;

    /**
     * バージョン
     */
    @CsvColumn(name = "バージョン")
    private Long version;

    /**
     * ステータス
     */
    @CsvColumn(name = "ステータス")
    private String status;

    /**
     * ステータス
     */
    @CsvColumn(name = "ステータス")
    private String statusLabel;

    /**
     * タイプ
     */
    @CsvColumn(name = "タイプ")
    @NotNull
    private String type;

    /**
     * コード
     */
    @CsvColumn(name = "コード")
    @NotNull
    private String code;

    /**
     * 値1
     */
    @CsvColumn(name = "値1")
    private String value1;

    /**
     * 値2
     */
    @CsvColumn(name = "値2")
    private String value2;

    /**
     * 値3
     */
    @CsvColumn(name = "値3")
    private String value3;

    /**
     * 値4
     */
    @CsvColumn(name = "値4")
    private String value4;

    /**
     * 値5
     */
    @CsvColumn(name = "値5")
    private String value5;

    /**
     * 値6
     */
    @CsvColumn(name = "値6")
    private String value6;

    /**
     * 値7
     */
    @CsvColumn(name = "値7")
    private String value7;

    /**
     * 値8
     */
    @CsvColumn(name = "値8")
    private String value8;

    /**
     * 値9
     */
    @CsvColumn(name = "値9")
    private String value9;

    /**
     * 値10
     */
    @CsvColumn(name = "値10")
    private String value10;

    /**
     * 日付1
     */
    @CsvColumn(name = "日付1", format = "yyyy/MM/dd")
    private Date date1;

    /**
     * 日付2
     */
    @CsvColumn(name = "日付2", format = "yyyy/MM/dd")
    private Date date2;

    /**
     * 日付3
     */
    @CsvColumn(name = "日付3", format = "yyyy/MM/dd")
    private Date date3;

    /**
     * 日付4
     */
    @CsvColumn(name = "日付4", format = "yyyy/MM/dd")
    private Date date4;

    /**
     * 日付5
     */
    @CsvColumn(name = "日付5", format = "yyyy/MM/dd")
    private Date date5;

    /**
     * 整数1
     */
    @CsvColumn(name = "整数1")
    private Integer valint1;

    /**
     * 整数2
     */
    @CsvColumn(name = "整数2")
    private Integer valint2;

    /**
     * 整数3
     */
    @CsvColumn(name = "整数3")
    private Integer valint3;

    /**
     * 整数4
     */
    @CsvColumn(name = "整数4")
    private Integer valint4;

    /**
     * 整数5
     */
    @CsvColumn(name = "整数5")
    private Integer valint5;

    /**
     * テキストエリア
     */
    @CsvColumn(name = "テキストエリア")
    private String textarea;

    /**
     * ファイル1
     */
    @CsvColumn(name = "ファイル1")
    private String file1Uuid;

    /**
     * 備考
     */
    @CsvColumn(name = "備考")
    private String remark;

}