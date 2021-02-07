<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>メールアドレスの変更</h4>
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
      新しいメールアドレスを入力するとそのアドレスに暗証番号が送られます。<br>
      次の画面で暗証番号を入力するとメールアドレスの変更が完了します。<br>
    </p>

    <form:form action="${f:h(pageContext.request.contextPath)}/emailchange" method="POST"
      modelAttribute="emailChangeForm" autocomplete="off">

      <div class="row">
        <div class="col-12">
          <form:label path="newEmail" cssClass="form-label">
            新しいメールアドレス
          </form:label>
          <form:input path="newEmail" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="newEmail" cssClass="invalid-feedback" />  
        </div>
      </div>
      <br>
      <div class="row">
        <input id="submit" type="submit" value="暗証番号の送信" class="btn btn-button" />
      </div>

    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>