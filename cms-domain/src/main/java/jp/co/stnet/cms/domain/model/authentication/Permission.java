package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

@AllArgsConstructor
@Getter
public enum Permission implements EnumCodeList.CodeListItem {

    VIEW_ALL_NODE("全コンテンツの参照", "NODE"),
    VIEW_OWN_NODE("自分のコンテンツの参照", "NODE"),
    ADMIN_PERMISSION("パーミッションの管理", "ADMIN");

    private final String label;
    private final String category;

    @Override
    public String getCodeLabel() {
        return label;
    }

    @Override
    public String getCodeValue() {
        return name();
    }

}
