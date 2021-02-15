<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>メールアドレスの変更(変更の完了)</h4>
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
      <br>
      新しいメールアドレスが登録されました。<br>
      <br>
      <a id="changePassword" href="${f:h(pageContext.request.contextPath)}/account"
              class="btn btn-button ml-2">マイアカウント情報</a>
    </p>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>