package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

@AllArgsConstructor
@Getter
public enum Role implements EnumCodeList.CodeListItem {

    ADMIN("ADMIN", "管理者", 1),
    USER("USER", "一般ユーザ", 2);

    private final String value;
    private final String label;
    private final int wait;

    @Override
    public String getCodeLabel() {
        return name();
    }

    @Override
    public String getCodeValue() {
        return value;
    }
}
