package jp.co.stnet.cms.app.person;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 従業員管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class PersonCsvBean implements Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID = 8595643245585576896L;

    // TODO 項目のカスタマイズ

    /** ID */
    @CsvColumn(name = "ID")
    private Long id;

    /** バージョン */
    @CsvColumn(name = "バージョン")
    private Long version;

    /** ステータス */
    @CsvColumn(name = "ステータス")
    private String status;

    /** ステータス */
    @CsvColumn(name = "ステータス")
    private String statusLabel;

    /** 氏名 */
    @CsvColumn(name = "氏名")
    private String name;

    /** 年齢 */
    @CsvColumn(name = "年齢")
    private Integer age;

    @CsvColumn(name = "コード")
    private String code;
}