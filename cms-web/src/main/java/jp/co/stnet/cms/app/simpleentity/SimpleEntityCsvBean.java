package jp.co.stnet.cms.app.simpleentity;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@CsvEntity
public class SimpleEntityCsvBean implements Serializable {

    @CsvColumn(name = "ID")
    private Long id;

    /**
     * ステータス
     */
    @CsvColumn(name = "ステータス")
    private String statusLabel;

    /**
     * テキストフィールド
     */
    @CsvColumn(name = "テキスト")
    private String text01;

    /**
     * テキストフィールド(数値・整数)
     */
    @CsvColumn(name = "テキスト(数値・整数)")
    private Integer text02;

    /**
     * テキストフィールド(数値・小数あり)
     */
    @CsvColumn(name = "テキスト(数値・小数あり)")
    private Float text03;

    /**
     * テキストフィールド(真偽値)
     */
    @CsvColumn(name = "真偽値")
    private Boolean text04;

    /**
     * コレクション(文字列)
     */
    @CsvColumn(name = "複数の値")
    private String text05;

}
