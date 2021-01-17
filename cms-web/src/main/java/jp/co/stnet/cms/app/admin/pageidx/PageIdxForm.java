package jp.co.stnet.cms.app.admin.pageidx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.time.LocalDate;

/**
 * ページ索引管理の編集画面のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageIdxForm implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 651673036813239365L;

    // TODO validation をカスタマイズ


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
    private String status;


    /**
     * お客さま番号
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]*$")
    private String customerNumber;

    /**
     * お客さま名
     */
    @NotNull
    private String customerName;

    /**
     * 開始ページ番号
     */
    @NotNull
    private Integer startPage;

    /**
     * 添付ファイル
     */
    @NotNull
    private String attachedFileUuid;

    /**
     * ドキュメントID
     */
    @NotNull
    private Long documentId;

    /**
     * キーワード1
     */
    private String keyword1;

    /**
     * キーワード2
     */
    private String keyword2;

    /**
     * キーワード3
     */
    private String keyword3;

    /**
     * キーワード4
     */
    private String keyword4;

    /**
     * キーワード5
     */
    private String keyword5;

    /**
     * キーワード6
     */
    private String keyword6;

    /**
     * キーワード7
     */
    private String keyword7;

    /**
     * キーワード8
     */
    private String keyword8;

    /**
     * キーワード9
     */
    private String keyword9;

    /**
     * キーワード10
     */
    private String keyword10;

    /**
     * キー日付1
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate1;

    /**
     * キー日付2
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate2;

    /**
     * キー日付3
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate3;

    /**
     * キー日付4
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate4;

    /**
     * キー日付5
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate5;

    /**
     * キー日付6
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate6;

    /**
     * キー日付7
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate7;

    /**
     * キー日付8
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate8;

    /**
     * キー日付9
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate9;

    /**
     * キー日付10
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate keydate10;


    public interface Create {
    }

    public interface Update {
    }

}