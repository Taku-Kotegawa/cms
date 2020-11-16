package jp.co.stnet.cms.domain.common.scheduled;

import com.orangesignal.csv.CsvConfig;

public class CsvUtils {

    public static CsvConfig getCsvDefault() {
        CsvConfig config = new CsvConfig(',', '"', '"'); // ダブルクォテーション括り, カンマ区切り
        config.setNullString("");
        return config;
    }


    public static CsvConfig getTsvDefault() {
        CsvConfig config = new CsvConfig('\t'); // TAB区切り
        return config;
    }

}
