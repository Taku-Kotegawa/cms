<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>パスワードリセット</h3>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <form:form action="${f:h(pageContext.request.contextPath)}/reissue/resetpassword" method="POST"
      modelAttribute="passwordResetForm" autocomplete="off">

      <div class="form-group row">
        <form:label path="username" cssClass="col-4 col-form-label">
          ユーザ名 *
        </form:label>
        <div class="col-10">
          ${f:h(passwordResetForm.username)}
          <form:hidden path="username" value="${f:h(passwordResetorm.username)}" />
        </div>
      </div>

      <form:hidden path="token" value="${f:h(passwordResetForm.token)}" />

      <div class="form-group row">
        <form:label path="secret" cssClass="col-4 col-form-label">
          暗証番号 *
        </form:label>
        <div class="col-10">
          <form:password path="secret" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="secret" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="newPassword" cssClass="col-4 col-form-label">
          新しいパスワード *
        </form:label>
        <div class="col-10">
          <form:password path="newPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="newPassword" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="confirmNewPassword" cssClass="col-4 col-form-label">
          新しいパスワード(確認) *
        </form:label>
        <div class="col-10">
          <form:password path="confirmNewPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="confirmNewPassword" cssClass="invalid-feedback" />
        </div>
      </div>
      <br>

      <input id="submit" type="submit" value="Reset password" class="btn-button" />
    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
