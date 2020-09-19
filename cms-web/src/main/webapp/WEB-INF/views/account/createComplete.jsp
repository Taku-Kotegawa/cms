<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h1>ようこそ ${f:h(firstName)} ${f:h(lastName)}!</h1>
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

        <div>
          あたなの初期パスワードは「<span id=password>${f:h(password)}</span>」です。
          初回ログイン時に変更してください。</div>
        <br>
        <a href="${f:h(pageContext.request.contextPath)}/login">ログイン画面へ</a>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
