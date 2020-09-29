<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <h4>アカウントリスト</h4>
      </div>
      <div class="col-18 text-right">
      </div>
    </div>
  </div>
</section>

<section class="content">
  <div class="container">

    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <table id="acountlist" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th class="text-center px-1">
            <button class="btn" onclick="checkAll();">
              <i class="fas fa-check"></i>
            </button>
          </th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
        </tr>
        <tr class="title">
          <th class="text-center px-0"></th>
          <th>操作</th>
          <th>ユーザ名</th>
          <th>名</th>
          <th>姓</th>
          <th>e-mail</th>
          <th>URL</th>
          <th>最終更新日時</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<!-- Page script -->
<script>
  $(document)
    .ready(
      function () {

        // 項目単位フィルタ用のInputフィールドを追加する。
        var startcolnum = 2;
        $('tr.filter th').each(function () {
          var idx = $(this).index();
          if (startcolnum <= idx) {
            $(this).html('<input type="text" id="col_filter_' + idx + '" data-column="' + idx +
              '" class="dataTables_column_filter form-control" />');
          }
        });

        var table = $('#acountlist').DataTable({
          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [{
              data: 'username',
              className: 'text-center',
              orderable: false,
              searchable: false,
              checkboxes: {
                selectRow: true
              },
            },
            {
              data: 'operations',
              orderable: false,
              searchable: false,
            },
            {
              data: 'username',
            },
            {
              data: 'firstName',
            },
            {
              data: 'lastName',
            },
            {
              data: 'email',
            },
            {
              data: 'url',
            },
            {
              'data': 'lastModifiedDate',
            },
          ],

          // 初期ソート
          'order': [
            [2, 'asc']
          ],

          // ボタンの表示
          'buttons': ['colvis', 'stateClear', 'createnew'],

          // データロード後処理
          'initComplete': function (settings, json) {
            // グローバルフィルターのEnterキーでサーバに送信
            fnGlobalFilterOnReturn(table);
          },
        });

        // ページネーション後に画面トップに戻る
        table.on('page.dt', function () {
          $('html, body').animate({
            scrollTop: 0
          }, 300);
        });

        // 項目単位フィルタの追加
        // addFieldFilter(table)

        // 項目単位フィルタを追加(列の並び順対応版)
        addFieldFilter2(table)

      });

  // 一括チェック処理(Server-side用、要StateSave)
  function checkAll() {
    var storageKey = "DataTables_acountlist_/cms-web/account/list";
    var url = '/cms-web/account/list/allkeyjson';
    var stateSaveData = JSON.parse(localStorage.getItem(storageKey));
    $.getJSON(url, function (data) {
      data.forEach(function (value) {
        stateSaveData.checkboxes[0][value] = 1;
      });
      localStorage.setItem(storageKey, JSON.stringify(stateSaveData));
      location.reload()
    });
  }
</script>
