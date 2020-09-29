package jp.co.stnet.cms.domain.common.datatables;

import lombok.Data;

@Data
public class OperationsUtil {

    private String baseUrl;
    private String BUTTON_CLASS = "btn btn-button";
    private String LABEL_CREATE = "新規作成";
    private String LABEL_VIEW = "参照";
    private String LABEL_EDIT = "編集";
    private String LABEL_DELETE = "削除";
    private String LABEL_COPY = "複製";
    private String LABEL_LIST = "一覧に戻る";
    private String LABEL_UNLOCK = "ロック解除";
    private String URL_CREATE = "create?form";
    private String URL_VIEW = "{id}";
    private String URL_EDIT = "{id}/update?form";
    private String URL_DELETE = "{id}/delete";
    private String URL_COPY = "create?form&copy={id}";
    private String URL_LIST = "list";
    private String URL_UNLOCK = "{id}/unlock";

    public OperationsUtil(String baseUrl) {
        if (baseUrl == null) {
            baseUrl = "";
        }
        this.baseUrl = baseUrl;
    }

    // ------ URL -------------------------------------------------

    public String getCreateUrl() {
        return baseUrl + URL_CREATE;
    }

    public String getEditUrl(String id) {
        return baseUrl + convId(URL_EDIT, id);
    }

    public String getDeleteUrl(String id) {
        return baseUrl + convId(URL_DELETE, id);
    }

    public String getViewUrl(String id) {
        return baseUrl + convId(URL_VIEW, id);
    }

    public String getCopyUrl(String id) {
        return baseUrl + convId(URL_COPY, id);
    }

    public String getListUrl() {
        return baseUrl + URL_LIST;
    }

    public String getUnlockUrl(String id) {
        return baseUrl + convId(URL_UNLOCK, id);
    }

    // ------ Link<A> -----------------------------------------------

    public String getCreateLink() {
        return link(getCreateUrl(), LABEL_CREATE);
    }

    public String getListLink() {
        return link(getListUrl(), LABEL_LIST);
    }

    public String getEditLink(String id) {
        return link(getEditUrl(id), LABEL_EDIT);
    }

    public String getDeleteLink(String id) {
        return link(getDeleteUrl(id), LABEL_DELETE);
    }

    public String getViewLink(String id) {
        return link(getViewUrl(id), LABEL_VIEW);
    }

    public String getCopyLink(String id) {
        return link(getCopyUrl(id), LABEL_COPY);
    }

    public String getUnlockLink(String id) {
        return link(getUnlockUrl(id), LABEL_UNLOCK);
    }


    // ------ Button Link<A> -----------------------------------------

    public String getCreateButton() {
        return link(getCreateUrl(), LABEL_CREATE, BUTTON_CLASS);
    }

    public String getListButton() {
        return link(getListUrl(), LABEL_LIST, BUTTON_CLASS);
    }

    public String getEditButton(String id) {
        return link(getEditUrl(id), LABEL_EDIT, BUTTON_CLASS);
    }

    public String getDeleteButton(String id) {
        return link(getDeleteUrl(id), LABEL_DELETE, BUTTON_CLASS);
    }

    public String getViewButton(String id) {
        return link(getViewUrl(id), LABEL_VIEW, BUTTON_CLASS);
    }

    public String getCopyButton(String id) {
        return link(getCopyUrl(id), LABEL_COPY, BUTTON_CLASS);
    }

    public String getUnlockButton(String id) {
        return link(getUnlockUrl(id), LABEL_UNLOCK, BUTTON_CLASS);
    }


    // ------ Toggle Button --------------------------------------

    public String getToggleButton(String id) {

        StringBuffer link = new StringBuffer();
        link.append("<div class=\"btn-group\">");
        link.append("<a href=\"" + getEditUrl(id) + "\" class=\"btn btn-button btn-sm\" style=\"white-space: nowrap\">" + LABEL_EDIT + "</a>");
        link.append("<button type=\"button\" class=\"btn btn-button btn-sm dropdown-toggle dropdown-toggle-split\"data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">");
        link.append("</button>");
        link.append("<div class=\"dropdown-menu\">");
        link.append("<a class=\"dropdown-item\" href=\"" + getViewUrl(id) + "\">" + LABEL_VIEW + "</a>");
        link.append("<a class=\"dropdown-item\" href=\"" + getCopyUrl(id) + "\">" + LABEL_COPY + "</a>");
        link.append("<a class=\"dropdown-item\" href=\"" + getDeleteUrl(id) + "\">" + LABEL_DELETE + "</a>");
        link.append("</div>");
        link.append("</div>");

        return link.toString();
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