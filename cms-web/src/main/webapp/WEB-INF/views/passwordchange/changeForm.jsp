<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-sm-6">
        <h3>パスワード変更</h3>
      </div>
      <div class="col-sm-6">
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">

    <form:form action="${f:h(pageContext.request.contextPath)}/password" method="POST"
      modelAttribute="passwordChangeForm" autocomplete="off">

      <div class="form-group row">
        <form:label path="username" cssClass="col-5 col-form-label">
          ユーザ名
        </form:label>
        <div class="col-10">
          ${f:h(account.username)}
          <form:hidden path="username" value="${f:h(account.username)}" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="oldPassword" cssClass="col-5 col-form-label">
          現在のパスワード
        </form:label>
        <div class="col-10">
          <form:password path="oldPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="oldPassword" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="newPassword" cssClass="col-5 col-form-label">
          新しいパスワード
        </form:label>
        <div class="col-10">
          <form:password path="newPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="newPassword" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="confirmNewPassword" cssClass="col-5 col-form-label">
          新しいパスワード(確認)
        </form:label>
        <div class="col-10">
          <form:password path="confirmNewPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="confirmNewPassword" cssClass="invalid-feedback" />
        </div>
      </div>
      <br>
      <div class="row">
        <input id="submit" type="submit" value="パスワード変更"" class="btn-button" />
      </div>
    </form:form>
  </div>
</section>
