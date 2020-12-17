<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>管理者メニュー</h3>
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

    <div class="card">
      <div class="card-body py-3">
        <h5>アカウント</h5>
        <ul class="list-unstyled mb-0">
          <li class="mb-2">
            <i class="fas fa-angle-right"></i>
            <a href="${f:h(pageContext.request.contextPath)}/admin/account/list">
              アカウント一覧
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
        </ul>
      </div>
    </div>

    <div class="card">
      <div class="card-body py-3">
        <h5>ジョブ</h5>
        <ul class="list-unstyled mb-0">
          <li class="mb-2">
            <i class="fas fa-angle-right"></i>
            <a href="${f:h(pageContext.request.contextPath)}/job/summary">
              ジョブサマリ(最終実行結果)
            </a>
          </li>
          <li class="mb-2">
            <i class="fas fa-angle-right"></i>
            <a href="${f:h(pageContext.request.contextPath)}/job/result">
              ジョブ処理結果
            </a>
          </li>
        </ul>
      </div>
    </div>

    <div class="card">
      <div class="card-body py-3">
        <h5>マスタ</h5>
        <ul class="list-unstyled mb-0">
          <li class="mb-2">
            <i class="fas fa-angle-right"></i>
            <a href="${f:h(pageContext.request.contextPath)}/admin/variable/list">
              変数管理
            </a>
          </li>
        </ul>
      </div>
    </div>

    <div class="card">
      <div class="card-body py-3">
        <h5>Hibernate Search</h5>
        <ul class="list-unstyled mb-0">
          <li class="mb-2">
            <i class="fas fa-angle-right"></i>
            <a href="${f:h(pageContext.request.contextPath)}/admin/index/manage">
              索引再作成
            </a>
          </li>
        </ul>
      </div>
    </div>    

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
