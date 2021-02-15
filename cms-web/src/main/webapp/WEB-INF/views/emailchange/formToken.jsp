<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>メールアドレスの変更(暗証番号の入力)</h4>
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

    <p>
    新しいメールアドレスに届いた暗証番号を入力してください。<br>
    </p>

    <form:form action="${f:h(pageContext.request.contextPath)}/emailchange?change" method="POST"
      modelAttribute="emailChangeTokenForm" autocomplete="off">

      <form:hidden path="token" />

      <div class="row">
          <div class="col-6">
            <form:label path="secret" cssClass="form-label">
              暗証番号
            </form:label>
            <form:input path="secret" cssClass="form-control" cssErrorClass="form-control is-invalid" />
            <form:errors path="secret" cssClass="invalid-feedback" />
          </div>
      </div>
      <br>
      <div class="row">
        <div class="col-36">
          <input id="submit" type="submit" value="保存(メールアドレスの変更)" class="btn btn-button" />
        </div>
      </div>

    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>