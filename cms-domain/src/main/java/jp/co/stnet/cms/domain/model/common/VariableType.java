package jp.co.stnet.cms.domain.model.common;

import org.terasoluna.gfw.common.codelist.EnumCodeList;

public enum VariableType implements EnumCodeList.CodeListItem {

    VARIABLE_LABEL("VARIABLE_LABEL", "Variable編集画面ラベル");

    private final String value;
    private final String label;

    VariableType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static VariableType getByValue(String value) {
        for (VariableType type : VariableType.values()) {
            if (type.getCodeValue().equals(value)) {
                return type;
            }
        }
        return null;
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
