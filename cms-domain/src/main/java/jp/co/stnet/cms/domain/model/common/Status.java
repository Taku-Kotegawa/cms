package jp.co.stnet.cms.domain.model.common;

import org.terasoluna.gfw.common.codelist.EnumCodeList;

public enum Status implements EnumCodeList.CodeListItem {

    DRAFT("0", "下書き"),
    VALID("1", "有効"),
    INVALID("2", "無効");

    private final String value;
    private final String label;

    Status(String value, String label) {
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


    public static Status getByValue(String value) {
        for (Status status : Status.values()) {
            if (status.getCodeValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

}
