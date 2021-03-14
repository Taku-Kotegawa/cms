package jp.co.stnet.cms.batch.RYREI002;

import jp.co.stnet.cms.domain.common.validation.Parseable;
import lombok.Data;

import javax.validation.constraints.Size;

import static jp.co.stnet.cms.domain.common.validation.ParseableType.TO_INT;

//todo 実データに合わせて変更
@Data
public class RYREI002Lst00Csv {
    /**
     * タイプ
     */
    @Size(max = 255)
    private String type;
    /**
     * 帳票名
     */
    @Size(max = 255)
    private String report;
    /**
     * 帳票タイトル
     */
    @Size(max = 255)
    private String title;
    /**
     * ファイル名
     */
    @Size(max = 255)
    private String attachedFile;
    /**
     * 店所
     */
    @Size(max = 3)
    private String shopCode;
    /**
     * 年度
     */
    @Parseable(value = TO_INT)
    private String year;
    /**
     * 発順
     */
    @Parseable(value = TO_INT)
    private String hatsujun;
    /**
     * 集計期間
     */
    @Parseable(value = TO_INT)
    private String  period;
    /**
     * お客さま番号（自）
     */
    @Size(max = 255)
    private String customerNumberFrom;
    /**
     * お客さま番号（至）
     */
    @Size(max = 255)
    private String customerNumberTo;
    /**
     * 有効期限
     */
    private String expirationDate;
    /**
     * 帳票出力日時
     */
    private String outputDate;

}
