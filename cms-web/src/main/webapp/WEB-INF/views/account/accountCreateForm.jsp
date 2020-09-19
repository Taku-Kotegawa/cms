<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>ユーザ新規登録</h3>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <!-- ここより下にメインコンテンツを記入 -->

    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />

    <form:form action="${f:h(pageContext.request.contextPath)}/account/create" method="POST"
      modelAttribute="accountCreateForm" enctype="multipart/form-data" autocomplete="off">

      <div class="form-group row">
        <form:label path="username" cssClass="col-4 col-form-label">
          ユーザ名 *
        </form:label>
        <form:input path="username" cssClass="form-control col-10" cssErrorClass="form-control col-10 is-invalid"
          placeholder="tkotegawa" />
        <form:errors path="username" cssClass="col-10 invalid-feedback ml-2 " />
      </div>

      <div class="form-group row">
        <form:label path="firstName" cssClass="col-4 col-form-label">姓 *</form:label>
        <form:input path="firstName" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="小手川" />
        <form:errors path="firstName" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="lastName" cssClass="col-4 col-form-label">名 *</form:label>
        <form:input path="lastName" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="拓" />
        <form:errors path="lastName" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="email" cssClass="col-4 col-form-label">E-mail *</form:label>
        <form:input path="email" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="tkotegawa@domainexample.co.jp" />
        <form:errors path="email" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="confirmEmail" cssClass="col-4 col-form-label">
          E-mail(確認) *
        </form:label>
        <form:input path="confirmEmail" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="" />
        <form:errors path="confirmEmail" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="url" cssClass="col-4 col-form-label">
          URL
        </form:label>
        <form:input path="url" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="http://www.stnet.co.jp" />
        <form:errors path="url" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="image" cssClass="col-4 col-form-label">
          画像ファイル *
        </form:label>
        <form:input type="file" path="image" cssClass="form-control-file col-10"
          cssErrorClass="form-control-file col-10 is-invalid" placeholder="" />
        <form:errors path="image" cssClass="col-10 invalid-feedback" />
      </div>

      <div class="form-group row">
        <form:label path="profile" cssClass="col-4 col-form-label">
          プロフィール
        </form:label>
        <form:textarea path="profile" cssClass="col-10 form-control" cssErrorClass="form-control col-10 is-invalid"
          placeholder="よろしければ、あなたのプロフィールを書いて下さい。" />
        <form:errors path="profile" cssClass="col-10 invalid-feedback" />
      </div>
      <br>
      <div class="form-group row">
        <div class="col-4 pt-2">
          <a href="${f:h(pageContext.request.contextPath)}/login">ログイン画面に戻る</a>
        </div>
        <div class="col-10 text-right">
          <input type="submit" id="confirm" name="confirm" value="確認へ進む" />
        </div>
      </div>

    </form:form>


    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
