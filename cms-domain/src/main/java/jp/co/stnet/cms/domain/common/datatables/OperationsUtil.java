package jp.co.stnet.cms.domain.common.datatables;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OperationsUtil {

    private String baseUrl;
    private String BUTTON_CLASS = "btn btn-button";

    // 1
    private String LABEL_CREATE = "新規作成";
    // 2
    private String LABEL_VIEW = "参照";
    // 3
    private String LABEL_EDIT = "編集";
    // 4
    private String LABEL_DELETE = "削除";
    // 5
    private String LABEL_COPY = "複製";
    // 6
    private String LABEL_LIST = "一覧に戻る";
    // 7
    private String LABEL_UNLOCK = "ロック解除";
    // 8
    private String LABEL_SAVE_DRAFT = "下書き保存";
    // 9
    private String LABEL_CANCEL_DRAFT = "下書き取消";
    // 10
    private String LABEL_INVALID = "無効化";
    // 11
    private String LABEL_VALID = "無効解除";
    // 12
    private String LABEL_DOWNLOAD = "ダウンロード";
    // 13
    private String LABEL_SWITCH_USER = "スイッチ";

    // 1
    private String URL_CREATE = "create?form";
    // 2
    private String URL_VIEW = "{id}";
    // 3
    private String URL_EDIT = "{id}/update?form";
    // 4
    private String URL_DELETE = "{id}/delete";
    // 5
    private String URL_COPY = "create?form&copy={id}";
    // 6
    private String URL_LIST = "list";
    // 7
    private String URL_UNLOCK = "{id}/unlock";
    // 8 該当URLなし
    // 9
    private String URL_CANCEL_DRAFT = "{id}/cancel_draft";
    // 10
    private String URL_INVALID = "{id}/invalid";
    // 11
    private String URL_VALID = "{id}/valid";
    // 12
    private String URL_DOWNLOAD = "{id}/download";
    // 13
    private String URL_SWITCH_USER = "impersonate?username={id}";

    public OperationsUtil(String baseUrl) {
        if (baseUrl == null) {
            baseUrl = "";
        } else {
            if (!StringUtils.startsWith(baseUrl, "/")) {
                baseUrl = "/" + baseUrl;
            }
            if (!StringUtils.endsWith(baseUrl, "/")) {
                baseUrl = baseUrl + "/";
            }
        }
        this.baseUrl = baseUrl;
    }

    // ------ URL -------------------------------------------------

    // 1
    public String getCreateUrl() {
        return baseUrl + URL_CREATE;
    }

    // 2
    public String getEditUrl(String id) {
        return baseUrl + convId(URL_EDIT, id);
    }

    // 3
    public String getDeleteUrl(String id) {
        return baseUrl + convId(URL_DELETE, id);
    }

    // 4
    public String getViewUrl(String id) {
        return baseUrl + convId(URL_VIEW, id);
    }

    // 5
    public String getCopyUrl(String id) {
        return baseUrl + convId(URL_COPY, id);
    }

    // 6
    public String getListUrl() {
        return baseUrl + URL_LIST;
    }

    // 7
    public String getUnlockUrl(String id) {
        return baseUrl + convId(URL_UNLOCK, id);
    }

    // 9
    public String getCancelDraftUrl(String id) {
        return baseUrl + convId(URL_CANCEL_DRAFT, id);
    }

    // 10
    public String getInvalidUrl(String id) {
        return baseUrl + convId(URL_INVALID, id);
    }

    // 11
    public String getValidUrl(String id) {
        return baseUrl + convId(URL_VALID, id);
    }

    // 12
    public String getDownloadUrl(String uuid) {
        return baseUrl + convId(URL_DOWNLOAD, uuid);
    }

    // 13
    public String getSwitchUserUrl(String uuid) {
        return baseUrl + convId(URL_SWITCH_USER, uuid);
    }

    // ------ Link<A> -----------------------------------------------
    // 1
    public String getCreateLink() {
        return link(getCreateUrl(), LABEL_CREATE);
    }

    // 2
    public String getEditLink(String id) {
        return link(getEditUrl(id), LABEL_EDIT);
    }

    // 3
    public String getDeleteLink(String id) {
        return link(getDeleteUrl(id), LABEL_DELETE);
    }

    // 4
    public String getViewLink(String id) {
        return link(getViewUrl(id), LABEL_VIEW);
    }

    // 5
    public String getCopyLink(String id) {
        return link(getCopyUrl(id), LABEL_COPY);
    }

    // 6
    public String getListLink() {
        return link(getListUrl(), LABEL_LIST);
    }

    // 7
    public String getUnlockLink(String id) {
        return link(getUnlockUrl(id), LABEL_UNLOCK);
    }

    // 9
    public String getCancelDraftLink(String id) {
        return link(getCancelDraftUrl(id), LABEL_CANCEL_DRAFT);
    }

    // 10
    public String getInvalidLink(String id) {
        return link(getInvalidUrl(id), LABEL_INVALID);
    }

    // 11
    public String getValidLink(String id) {
        return link(getValidUrl(id), LABEL_VALID);
    }

    // 12
    public String getDownloadLink(String id) {
        return link(getDownloadUrl(id), LABEL_DOWNLOAD);
    }

    // 13
    public String getSwitchUserLink(String id) {
        return link(getSwitchUserUrl(id), LABEL_SWITCH_USER);
    }


    // ------ Button Link<A> -----------------------------------------
    // 1
    public String getCreateButton() {
        return link(getCreateUrl(), LABEL_CREATE, BUTTON_CLASS);
    }

    // 2
    public String getListButton() {
        return link(getListUrl(), LABEL_LIST, BUTTON_CLASS);
    }

    // 3
    public String getEditButton(String id) {
        return link(getEditUrl(id), LABEL_EDIT, BUTTON_CLASS);
    }

    // 4
    public String getDeleteButton(String id) {
        return link(getDeleteUrl(id), LABEL_DELETE, BUTTON_CLASS);
    }

    // 5
    public String getViewButton(String id) {
        return link(getViewUrl(id), LABEL_VIEW, BUTTON_CLASS);
    }

    // 6
    public String getCopyButton(String id) {
        return link(getCopyUrl(id), LABEL_COPY, BUTTON_CLASS);
    }

    // 7
    public String getUnlockButton(String id) {
        return link(getUnlockUrl(id), LABEL_UNLOCK, BUTTON_CLASS);
    }

    // 9
    public String getCancelDraftButton(String id) {
        return link(getCancelDraftUrl(id), LABEL_CANCEL_DRAFT, BUTTON_CLASS);
    }

    // 10
    public String getInvalidButton(String id) {
        return link(getInvalidUrl(id), LABEL_INVALID, BUTTON_CLASS);
    }

    // 11
    public String getValidButton(String id) {
        return link(getValidUrl(id), LABEL_VALID, BUTTON_CLASS);
    }

    // 12
    public String getDownloadButton(String id) {
        return link(getDownloadUrl(id), LABEL_DOWNLOAD, BUTTON_CLASS);
    }

    // 13
    public String getSwitchUserButton(String id) {
        return link(getSwitchUserUrl(id), LABEL_SWITCH_USER, BUTTON_CLASS);
    }

    // ------ private function --------------------------------------

    private String convId(String template, String id) {
        return template.replace("{id}", id);
    }

    private String link(String url, String label) {
        return link(url, label, "");
    }

    private String link(String url, String label, String classVal) {
        return "<A HREF=\"" + url + "\" class=\"" + classVal + "\" >" + label + "</A>";
    }

}
