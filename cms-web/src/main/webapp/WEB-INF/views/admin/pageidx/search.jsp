<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>
<%@ include file="/WEB-INF/views/common/includes/include-multipleselect.jsp" %>

<style>
  .dataTables_filter, .dataTables_info { display: none; }
</style>


<section class="content-header">
  <div class="container-flued mx-5">
    <div class="row mb-2">
      <div class="col-18">
        <h4>お客さま番号検索</h4>
      </div>
      <div class="col-18 text-right">
      </div>
    </div>
  </div>
</section>

<section class="content">
  <div class="container-flued mx-5">

    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <table id="list" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th></th>
          <th></th>
          <th data-filter="disable"></th>
          <th data-filter="disable"></th>
          <th data-filter="disable"></th>
          <th></th>
          <th data-filter="disable"></th>
          <th data-filter="disable"></th>
        </tr>
        <tr class="title">
          <th class="text-center">お客さま番号</th>
          <th class="text-center">お客さま名</th>
          <th class="text-center">開始ページ番号</th>
          <th class="text-center">添付ファイル</th>
          <th class="text-center">ドキュメントID</th>
          <th class="text-center">キーワード1</th>
          <th class="text-center">キーワード2</th>
          <th class="text-center">キーワード3</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>

    <form:form id="bulk-operation-form"></form:form>

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

          'ajax': {
            'url': 'search/json',
            'data': flatten
          },

          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [
            {
              data: 'customerNumber',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'customerName',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'startPage',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'attachedFileUuid',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'documentId',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'document.shopCode',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'document.shop.title',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'keyword3',
              render: $.fn.dataTable.render.text(),
            },
          ],

          // 初期ソート
          'order': [
            [2, 'asc']
          ],

          // ボタンの表示
          'buttons': ['colvis', 'stateClear'
          ],

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


        $('#draft').change().on('change', function (e, s) {
          localStorage.dataTables_Draft = e.target.checked;
          table.draw();
          fnColumns(table);
        });

        if (localStorage.dataTables_Draft == 'false') {
          $('#draft')[0].checked = false;
          table.draw();
        }

      });

</script>