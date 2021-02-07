<c:if test="${not empty shortMessage}">
  <div class="shortMessage container-flued py-1 px-3">
    ${f:br(f:h(shortMessage))}
  </div>
</c:if>
<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Hello world!</h4>
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
      <div class="col-36">
        <h4>メニュー</h4>
        <ul>
          <li> <a href="${f:h(pageContext.request.contextPath)}/simpleentity/list">SimpleEntity List</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/person/list">Person</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/person/search">Person(Search)</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/select/list">Select</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/pdfbox?form">pdfbox</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/account/">Accout + Modal Dialog</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/pageidx/search">お客さま番号検索(シンプル)</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/admin/pageidx/search">お客さま番号検索(DataTables)</a></li>
        </ul>
      </div>
    </div>

    <div class="row">
      <div class="col-36">
        <div class="card">
          <div class="card-body">
            <h5>ヒント</h5>
            <p><code>cms-web/src/main/webapp/WEB-INF/views/layout/template.jsp</code>の
              <code>include-debug.jsp</code>のコメントを外すとページ下部にデバッグ情報が表示される様になる。</p>
          </div>
        </div>
      </div>
    </div>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>

</section>

<!-- <footer class="main-footer">
  <div class="float-right d-none d-sm-block">
    <a href="#" class="btn-button">BackToTop</a>
  </div>

  <a href="#" class="btn-button">BackToTop</a>
</footer> -->