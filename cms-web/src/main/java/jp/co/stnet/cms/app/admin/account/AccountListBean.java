package jp.co.stnet.cms.app.admin.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.domain.model.authentication.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AccountListBean extends Account {

    private String operations;

    private String DT_RowId;

    private String DT_RowClass;

    private Map<String, String> DT_RowAttr;

    @JsonProperty("DT_RowId")
    public String getDT_RowId() {
        return DT_RowId;
    }

    @JsonProperty("DT_RowClass")
    public String getDT_RowClass() {
        return DT_RowClass;
    }

    @JsonProperty("DT_RowAttr")
    public Map<String, String> getDT_RowAttr() {
        return DT_RowAttr;
    }

    @Override
    @JsonIgnore
    public String getPassword() { return null; }

    private String statusLabel;
}

