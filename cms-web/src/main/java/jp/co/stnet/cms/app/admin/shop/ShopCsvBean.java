package jp.co.stnet.cms.app.admin.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
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

/**
 * 店所管理のCSVファイルのBean
 */
@Data
@CsvEntity
public class ShopCsvBean implements Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID = 7397686789484547382L;

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
    /** 店所コード */
    @CsvColumn(name = "店所コード")
    @NotNull
    private String shopCode;
    /** 並び順 */
    @CsvColumn(name = "並び順")
    private Integer weight;
    /** 店所名 */
    @CsvColumn(name = "店所名")
    private String title;
}