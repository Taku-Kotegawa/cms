<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>ユーザ新規登録(確認)</h4>
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
    <form:form action="${f:h(pageContext.request.contextPath)}/account/create" method="POST"
      modelAttribute="accountCreateForm" autocomplete="off">

      <div class="form-group row">
        <form:label path="username" cssClass="col-4 col-form-label">
          ユーザ名 *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.username)}
          <form:hidden path="username" />
        </div>
      </div>
      <div class="form-group row">
        <form:label path="firstName" cssClass="col-4 col-form-label">姓 *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.firstName)}
          <form:hidden path="firstName" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="lastName" cssClass="col-4 col-form-label">名 *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.lastName)}
          <form:hidden path="lastName" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="email" cssClass="col-4 col-form-label">E-mail
          *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.email)}
          <form:hidden path="email" />
          <form:hidden path="confirmEmail" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="url" cssClass="col-4 col-form-label">
          URL
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.url)}
          <form:hidden path="url" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="image" cssClass="col-4 col-form-label">
          画像ファイル *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.image.originalFilename)}
          <form:hidden path="imageId" />
        </div>
      </div>

      <div class="form-group row">
        <form:label path="profile" cssClass="col-4 col-form-label">
          プロフィール
        </form:label>
        <div class="col-10 form-control">
          ${f:br(f:h(accountCreateForm.profile))}
          <form:hidden path="profile" />
        </div>
      </div>
      <br>
      <div class="form-group row">
        <div class="col-4 pt-2">
          <input type="submit" id="redo" name="redo" value="Back" />
        </div>
        <div class="col-10 text-right">
          <input type="submit" id="create" value="Create new account" />
        </div>
      </div>
    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
