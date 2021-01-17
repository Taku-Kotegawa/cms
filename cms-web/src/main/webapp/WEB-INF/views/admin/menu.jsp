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

    <div class="row">

      <!-- 左側 -->

      <div class="col-18 px-3">

        <div class="admin-panel">
          <h5>アカウント</h5>
          <hr>
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

            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a id="permission" href="${f:h(pageContext.request.contextPath)}/admin/permission/list">
                パーミッション
              </a>
            </li>

            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a id="role" href="${f:h(pageContext.request.contextPath)}/admin/role/list">
                ロール
              </a>
            </li>            

          </ul>
        </div>

        <div class="admin-panel">
          <h5>ジョブ</h5>
          <hr>
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

        <div class="admin-panel">
          <h5>マスタ</h5>
          <hr>
          <ul class="list-unstyled mb-0">
            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a href="${f:h(pageContext.request.contextPath)}/admin/variable/list">
                バリアブル
              </a>
            </li>
          </ul>
        </div>

      </div>

      <!-- 右側 -->
      <div class="col-18 px-3">

        <div class="admin-panel">
          <h5>Hibernate Search</h5>
          <hr>
          <ul class="list-unstyled mb-0">
            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a href="${f:h(pageContext.request.contextPath)}/admin/index/manage">
                索引再作成
              </a>
            </li>
          </ul>
        </div>

        <div class="admin-panel">
          <h5>帳票管理</h5>
          <hr>
          <ul class="list-unstyled mb-0">
            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a href="${f:h(pageContext.request.contextPath)}/admin/shop/list">
                店所マスタ
              </a>
            </li>
          </ul>

          <ul class="list-unstyled mb-0">
            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a href="${f:h(pageContext.request.contextPath)}/admin/document/list">
                ドキュメント管理
              </a>
            </li>
          </ul>

          <ul class="list-unstyled mb-0">
            <li class="mb-2">
              <i class="fas fa-angle-right"></i>
              <a href="${f:h(pageContext.request.contextPath)}/admin/pageidx/list">
                ページ番号管理
              </a>
            </li>
          </ul>

        </div>

      </div>

    </div>


    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>