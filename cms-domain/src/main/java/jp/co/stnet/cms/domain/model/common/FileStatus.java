package jp.co.stnet.cms.domain.model.common;

import org.terasoluna.gfw.common.codelist.EnumCodeList;

public enum FileStatus implements EnumCodeList.CodeListItem {

    TEMPORARY("0", "一時"),
    PERMANENT("1", "恒久");

    private final String value;
    private final String label;

    FileStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getCodeLabel() {
        return label;
    }

    @Override
    public String getCodeValue() {
        return value;
    }
}
