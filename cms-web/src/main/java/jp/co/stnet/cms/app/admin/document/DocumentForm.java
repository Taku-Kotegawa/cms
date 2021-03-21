package jp.co.stnet.cms.app.admin.document;

import jp.co.stnet.cms.domain.model.report.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.stnet.cms.domain.model.common.FileManaged;

/**
 * 帳票ファイル管理の編集画面のBean
 * @author Automatically generated
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentForm implements Serializable {
 
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8058964242496301506L;

    // TODO validation をカスタマイズ


    /**
     * 内部ID
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
     * ドキュメント名
     */
    @NotNull
    private String title;

    /**
     * レポートコード
     */
//    @NotNull
//    private String reportCode;

    @NotNull
    private Report report;

    /**
     * 店所コード
     */
    @NotNull
    private String shopCode;

    /**
     * 年度
     */
    @NotNull
    private Integer year;

    /**
     * 集計期間
     */
    @NotNull
    private Integer period;

    /**
     * 発順
     */
    @NotNull
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
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate expirationDate;

    /**
     * 帳票出力日時
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime outputDate;

    /**
     * 添付ファイル
     */
    private String attachedFileUuid;

    /**
     * 添付ファイル(FileManaged)
     */
    private FileManaged attachedFileManaged;


    public interface Create {
    }

    public interface Update {
    }

}