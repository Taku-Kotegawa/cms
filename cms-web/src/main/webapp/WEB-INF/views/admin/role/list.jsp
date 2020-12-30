<section class="content-header">
  <div class="container-fluid px-5">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>ロール</h4>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container-fluid px-5">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->
    <div class="text-right">
      <!-- ページタイトル右の余白 -->
      <a href="${f:h(pageContext.request.contextPath)}/admin/account/list" class="badge badge-secondary">ユーザ</a>
      <a href="${f:h(pageContext.request.contextPath)}/admin/permission/list" class="badge badge-secondary">パーミッション</a>
    </div>

    <div>
      <p>ロールとパーミッションを用いてセキュリティの細かな調整を行えます。ロールは特定のパーミッションの組み合わせを定義するために使われます。<br>
      <a href="${f:h(pageContext.request.contextPath)}/admin/permission/list">パーミッション</a>ページで、ロートとパーミッションの組み合わせを設定できます。</p>
      <p>なお、ロール自体の追加はこの画面からできません。ロールEnum(jp.co.stnet.cms.domain.model.authentication.Role)を編集してください。</p>
    </div>

    <table class="table-sm table-striped table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>ID</th>
          <th>ロール</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="obj" items="${roleList}" varStatus="status">
          <tr>
            <td><c:out value="${status.index}"/></td>
            <td><c:out value="${obj.value}"/></td>
            <td><c:out value="${obj.label}"/></td>          
            <td><a href="#">権限の編集</a></td>          
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

