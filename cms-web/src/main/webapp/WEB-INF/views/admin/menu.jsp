<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>管理メニュー</h3>
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

    <ul class="list-unstyled ml-3">

      <li class="mb-2">
        <i class="fas fa-angle-right"></i>
        <a href="${f:h(pageContext.request.contextPath)}/admin/account/list">
          アカウント管理
        </a>
      </li>

      <sec:authorize url="/unlock">
        <li class="mb-2">
          <i class="fas fa-angle-right"></i>
          <a id="unlock" href="${f:h(pageContext.request.contextPath)}/unlock?form">
            ロック解除
          </a>
        </li>
      </sec:authorize>

      <li class="mb-2">
        <i class="fas fa-angle-right"></i>
        <a href="${f:h(pageContext.request.contextPath)}/admin/variable/list">
          変数管理
        </a>
      </li>

    </ul>


    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
