<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>
<%@ include file="/WEB-INF/views/common/includes/include-multipleselect.jsp" %>

<section class="content-header">
  <div class="container-flued mx-5">
    <div class="row mb-2">
      <div class="col-18">
        <h4>帳票ファイル一覧</h4>
      </div>
      <div class="col-18 text-right">
        <a href="http://localhost:8080/cms-web/admin/pageidx/list">ページ索引</a>
      </div>
    </div>
  </div>
</section>

<section class="content">
  <div class="container-flued mx-5">

    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <div class="form-check-inline" style="width:100%">
      <input id="draft" type="hidden" checked="checked">
      <!-- <label for="checkbox011">下書きを含む</label> -->
    </div>

    <table id="list" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th class="text-center px-1" data-filter="disable"></th>
          <th data-filter="disable"></th>
          <th></th>
          <th data-filter="disable">
            <select id="col_filter_3" data-column="3" class="dataTables_column_filter form-control multipleSelect"
              multiple>
              <!-- <option value=""></option> -->
              <c:forEach items="${CL_STATUS}" var="obj">
                <option value="${obj.key}">${obj.value}</option>
              </c:forEach>
            </select>
          </th>
          <th></th>
          <th data-filter="disable">
            <select id="col_filter_5" data-column="5" class="dataTables_column_filter form-control multipleSelect"
              multiple>
              <!-- <option value=""></option> -->
              <c:forEach items="${CL_REPORT}" var="obj">
                <option value="${obj.key}">${obj.value}</option>
              </c:forEach>
            </select>
          </th>
          <th data-filter="disable">
            <select id="col_filter_6" data-column="6" class="dataTables_column_filter form-control multipleSelect"
              multiple>
              <!-- <option value=""></option> -->
              <c:forEach items="${CL_SHOP}" var="obj">
                <option value="${obj.key}">${obj.value}</option>
              </c:forEach>
            </select>
          </th>
          <th></th>
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
          <th class="text-center">ID</th>
          <th class="text-center">ステータス</th>
          <th class="text-center">ドキュメント名</th>
          <th class="text-center">レポート</th>
          <th class="text-center">店所コード</th>
          <th class="text-center">年度</th>
          <th class="text-center">集計期間</th>
          <th class="text-center">発順</th>
          <th class="text-center">お客さま番号(自)</th>
          <th class="text-center">お客さま番号(至)</th>
          <th class="text-center">有効期限</th>
          <th class="text-center">帳票出力日時</th>
          <th class="text-center">添付ファイル</th>          
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
            'url': 'list/json',
            'data': myflatten
          },

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
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'statusLabel',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'title',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'reportLabel',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'shopCode',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'year',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'period',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'hatsujun',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'customerNumberFrom',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'customerNumberTo',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'expirationDate',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'outputDate',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'attachedFileManaged.originalFilename',
              render: $.fn.dataTable.render.text(),
              "defaultContent": "",
            },            
          ],

          // 初期ソート
          'order': [
            [2, 'asc']
          ],

          // ボタンの表示
          'buttons': ['bulkdelete', 'bulkinvalid', 'bulkvalid', 'colvis', 'stateClear', 'csvdownload',
            'tsvdownload', 'exceldownload', 'upload', 'createnew'
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

  function myflatten(params, settings) {
    params = flatten(params, settings);
    params.draft = $('#draft')[0].checked;
    return params;
  }
</script>