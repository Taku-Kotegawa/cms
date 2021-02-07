<section class="content-header">
  <div class="container">
    <div class="row">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>ログイン中ユーザ一覧</h4>
      </div>
      <div class="col-18 text-right nav-tabs-wrapper">
        <ul class="nav mb-0 nav-tabs justify-content-end">
          <li class="nav-item">
            <a class="nav-link" href="list">アカウント</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="#">ログイン中</a>
          </li>
        </ul>        
      </div>
    </div>
  </div>
</section>


<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <table class="table-sm">
      <thead>
        <tr>
          <th>Username</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="item" items="${activeUserList}" varStatus="status">
          <tr>
            <th>
              ${item.username}
            </th>
          </tr>
        </c:forEach>

      </tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

