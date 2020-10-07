<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <h4>Simple Entity List</h4>
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

    <table id="list" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th class="text-center px-1" data-filter="disable"></th>
          <th data-filter="disable"></th>
          <th data-filter="disable"></th>
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
          <th class="text-center">操作</th>
          <th class="text-center">#</th>
          <th class="text-center">テキスト</th>
          <th class="text-center">テキスト(数値・整数)</th>
          <th class="text-center">テキスト(数値・小数あり)</th>
          <th class="text-center">テキスト(真偽値)</th>
          <th class="text-center">URL</th>
          <th>ステータス</th>
          <th class="text-center">最終更新日時</th>
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
        // TODO 開始列番号を指定
        var startcolnum = 0;
        $('tr.filter th').each(function () {
          var idx = $(this).index();
          if (startcolnum <= idx && $(this).data("filter") != 'disable') {
            $(this).html('<input type="text" id="col_filter_' + idx + '" data-column="' + idx +
              '" class="dataTables_column_filter form-control" />');
          }
        });

        var table = $('#list').DataTable({
          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [{
              data: 'id',
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
              data: 'id',
            },
            {
              data: 'text01',
            },
            {
              data: 'text02',
            },
            {
              data: 'text03',
            },
            {
              data: 'text04',
            },
            {
              data: 'text05',
            },
            {
              data: 'statusLabel',
              className: 'text-center',
              // orderable: false,
              // searchable: false,
            },
            {
              data: 'lastModifiedDate',
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
</script>
