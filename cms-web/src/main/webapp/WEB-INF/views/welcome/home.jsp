<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Hello world!</h3>
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
      <a id="info" href="${f:h(pageContext.request.contextPath)}/account/" class="btn-button mr-2">
        アカウント情報
      </a>

      <sec:authorize url="/unlock">
        <div>
          <a id="unlock" href="${f:h(pageContext.request.contextPath)}/unlock?form" class="btn-button mr-2">
            ロック解除
          </a>
        </div>
      </sec:authorize>

      <form:form action="${f:h(pageContext.request.contextPath)}/logout" autocomplete="off">
        <button id="logout" class="btn-button">ログアウト</button>
      </form:form>
    </div>
    <div class="row mb-5">
      <div class="col-36">
      </div>
    </div>


    <div class="row">
      <div class="col-36">
        <h4>管理者メニュー</h4>
        <ul>
          <li> <a href="${f:h(pageContext.request.contextPath)}/simpleentity/list">SimpleEntity List</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/admin/account/list">アカウント</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/admin/role/list">ロール</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/person/list">Person</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/admin/variable/list">Variable</a></li>
        </ul>
      </div>
    </div>
  </div>

  <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<footer class="main-footer">
  <div class="float-right d-none d-sm-block">
    <a href="#" class="btn-button">BackToTop</a>
  </div>

  <a href="#" class="btn-button">BackToTop</a>
</footer>
