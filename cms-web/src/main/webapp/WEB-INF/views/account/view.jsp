<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>アカウト情報</h3>
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
    <div class="row">

      <div class="col-5 text-center">
        <img src="${f:h(pageContext.request.contextPath)}/account/image" width="100" height="100" />
      </div>
      <div class="col-31">
        <table>
          <tr>
            <th>Username</th>
            <td>${f:h(account.username)}</td>
          </tr>
          <tr>
            <th>First name</th>
            <td>${f:h(account.firstName)}</td>
          </tr>
          <tr>
            <th>Last name</th>
            <td>${f:h(account.lastName)}</td>
          </tr>
          <tr>
            <th>E-Mail</th>
            <td>${f:h(account.email)}</td>
          </tr>
          <tr>
            <th>URL</th>
            <td>${f:link(account.url)}</td>
          </tr>
          <tr>
            <th>Profile</th>
            <td>${f:br(f:h(account.profile))}</td>
          </tr>
        </table>
      </div>
    </div>

    <br>
    <div class="row form-group">
      <a href="${f:h(pageContext.request.contextPath)}/" class="btn-button">ホーム</a>

      <a id="changePassword" href="${f:h(pageContext.request.contextPath)}/password?form"
        class="btn-button ml-2">パスワード変更</a>
    </div>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<script>
  $(function () {

    var options = {
    message: 'test message',
    title: 'Header title',
    size: 'lg',
    subtitle: 'smaller text header',
                    buttons: [
                    { text: '閉じる', close: true, style: 'danger' },
                    { text: 'New content', close: false, style: 'success' }
                    ],
    };
    eModal.alert(options);

    eModal.ajax('http://localhost:8080/cms-web/common/dialog/test', 'Jobs - Form apply')
      .then(ajaxOnLoadCallback);

  });


</script>
