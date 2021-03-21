package jp.co.stnet.cms.domain.common;

import com.orangesignal.csv.CsvConfig;

/**
 * OrangeSignalのCSV変換を支援するユーティリティ
 */
public class CsvUtils {

    /**
     * CSV形式のデフォルトフォーマットを返す。
     *
     * @return CsvConfig(ダブルクォテーション括り, カンマ区切り)
     */
    public static CsvConfig getCsvDefault() {
        CsvConfig config = new CsvConfig(',', '"', '"');
        config.setNullString("");
        return config;
    }

    /**
     * TSV形式のデフォルトフォーマットを返す。
     *
     * @return CsvConfig(TAB区切り)
     */
    public static CsvConfig getTsvDefault() {
        return new CsvConfig('\t');
    }

}
