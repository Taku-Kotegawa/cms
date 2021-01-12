package jp.co.stnet.cms.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

@Getter
@AllArgsConstructor
public enum Report implements EnumCodeList.CodeListItem {

    SAMPLE1("サンプル", "IF0001");

    private String title;
    private String reportInterfaceId;

    @Override
    public String getCodeLabel() {
        return this.title;
    }

    @Override
    public String getCodeValue() {
        return name();
    }
}
