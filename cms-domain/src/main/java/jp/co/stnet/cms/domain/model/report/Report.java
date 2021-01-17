package jp.co.stnet.cms.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

@Getter
@AllArgsConstructor
public enum Report implements EnumCodeList.CodeListItem {

    SAMPLE1("サンプル", "IF0001"),
    EG01("お客さまシート(低圧)", "IF0002");

    @KeywordField(aggregable = Aggregable.YES)
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
