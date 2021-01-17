package jp.co.stnet.cms.app.admin.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import jp.co.stnet.cms.domain.model.report.Report;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.validator.constraints.*;
import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.stnet.cms.domain.model.common.FileManaged;

/**
 * 帳票ファイル管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class DocumentCsvBean implements Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID = 8058964242496301506L;

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
    /** ドキュメント名 */
    @CsvColumn(name = "ドキュメント名")
    @NotNull
    private String title;
    /** レポートコード */
    @CsvColumn(name = "レポートコード")
    private Report report;
    @CsvColumn(name = "レポートコード")
    private String reportLabel;
    /** 店所コード */
    @CsvColumn(name = "店所コード")
    private String shopCode;
    /** 年度 */
    @CsvColumn(name = "年度")
    private Integer year;
    /** 集計期間 */
    @CsvColumn(name = "集計期間")
    private Integer period;
    /** 発順 */
    @CsvColumn(name = "発順")
    private Integer hatsujun;
    /** お客さま番号(自) */
    @CsvColumn(name = "お客さま番号(自)")
    private String customerNumberFrom;
    /** お客さま番号(至) */
    @CsvColumn(name = "お客さま番号(至)")
    private String customerNumberTo;
    /** 有効期限 */
    @CsvColumn(name = "有効期限", format="yyyy/MM/dd")
    private Date expirationDate;
    /** 帳票出力日時 */
    @CsvColumn(name = "帳票出力日時", format="yyyy/mm/dd hh:mm:ss")
    private Date outputDate;
    /** 添付ファイル */
    @CsvColumn(name = "添付ファイル")
    private String attachedFileUuid;
    /** 添付ファイル(Managed) */
    @CsvColumn(name = "添付ファイル(Managed)")
    private FileManaged attachedFileManaged;
}