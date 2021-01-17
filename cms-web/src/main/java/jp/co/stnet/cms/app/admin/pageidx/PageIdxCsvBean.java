package jp.co.stnet.cms.app.admin.pageidx;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * ページ索引管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class PageIdxCsvBean implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 651673036813239365L;

    // TODO 項目のカスタマイズ

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
     * お客さま番号
     */
    @CsvColumn(name = "お客さま番号")
    @NotNull
    private String customerNumber;
    /**
     * お客さま名
     */
    @CsvColumn(name = "お客さま名")
    private String customerName;
    /**
     * 開始ページ番号
     */
    @CsvColumn(name = "開始ページ番号")
    private Integer startPage;
    /**
     * 添付ファイル
     */
    @CsvColumn(name = "添付ファイル")
    private String attachedFileUuid;
    /**
     * ドキュメントID
     */
    @CsvColumn(name = "ドキュメントID")
    private Long documentId;
    /**
     * キーワード1
     */
    @CsvColumn(name = "キーワード1")
    private String keyword1;
    /**
     * キーワード2
     */
    @CsvColumn(name = "キーワード2")
    private String keyword2;
    /**
     * キーワード3
     */
    @CsvColumn(name = "キーワード3")
    private String keyword3;
    /**
     * キーワード4
     */
    @CsvColumn(name = "キーワード4")
    private String keyword4;
    /**
     * キーワード5
     */
    @CsvColumn(name = "キーワード5")
    private String keyword5;
    /**
     * キーワード6
     */
    @CsvColumn(name = "キーワード6")
    private String keyword6;
    /**
     * キーワード7
     */
    @CsvColumn(name = "キーワード7")
    private String keyword7;
    /**
     * キーワード8
     */
    @CsvColumn(name = "キーワード8")
    private String keyword8;
    /**
     * キーワード9
     */
    @CsvColumn(name = "キーワード9")
    private String keyword9;
    /**
     * キーワード10
     */
    @CsvColumn(name = "キーワード10")
    private String keyword10;
    /**
     * キー日付1
     */
    @CsvColumn(name = "キー日付1", format = "yyyy/MM/dd")
    private Date keydate1;
    /**
     * キー日付2
     */
    @CsvColumn(name = "キー日付2", format = "yyyy/MM/dd")
    private Date keydate2;
    /**
     * キー日付3
     */
    @CsvColumn(name = "キー日付3", format = "yyyy/MM/dd")
    private Date keydate3;
    /**
     * キー日付4
     */
    @CsvColumn(name = "キー日付4", format = "yyyy/MM/dd")
    private Date keydate4;
    /**
     * キー日付5
     */
    @CsvColumn(name = "キー日付5", format = "yyyy/MM/dd")
    private Date keydate5;
    /**
     * キー日付6
     */
    @CsvColumn(name = "キー日付6", format = "yyyy/MM/dd")
    private Date keydate6;
    /**
     * キー日付7
     */
    @CsvColumn(name = "キー日付7", format = "yyyy/MM/dd")
    private Date keydate7;
    /**
     * キー日付8
     */
    @CsvColumn(name = "キー日付8", format = "yyyy/MM/dd")
    private Date keydate8;
    /**
     * キー日付9
     */
    @CsvColumn(name = "キー日付9", format = "yyyy/MM/dd")
    private Date keydate9;
    /**
     * キー日付10
     */
    @CsvColumn(name = "キー日付10", format = "yyyy/MM/dd")
    private Date keydate10;
}