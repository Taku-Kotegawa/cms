<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>
<%-- <%@ include file="/WEB-INF/views/common/includes/include-multipleselect.jsp" %> --%>

<section class="content-header px-5">
  <div class="container-fluid">
    <div class="row mb-2">
      <div class="col-18">
        <h4>Simple Entity List</h4>
      </div>
      <div class="col-18 text-right">
      </div>
    </div>
  </div>
</section>

<section class="content px-5">
  <div class="container-fluid">

    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->
    <p>
    fixedColumnsやScroll-Xを利用することで、大きな一覧表を表示する例。<br>
    固定ヘッダーや固定列内でtoggle-buttonやmultiple-selectを使うと見切れてしまうので、使用する場合は特に注意が必要。
    </p>

    <div class="form-check-inline" style="width:100%">
      <input id="draft" type="checkbox" checked="checked">
      <label for="draft">下書きを含む</label>
    </div>

    <table id="list" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th class="text-center px-1" data-filter="disable"></th>
          <th data-filter="disable"></th>
          <!-- (1) Start -->
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th data-filter="disable">
            <select id="col_filter_8" data-column="8" class="dataTables_column_filter form-control">
              <option value=""></option>
              <option value="1">はい</option>
              <option value="0">いいえ</option>
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
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th data-filter="disable">
            <select id="col_filter_9" data-column="9" class="dataTables_column_filter form-control">
              <option value=""></option>
              <c:forEach items="${CL_STATUS}" var="obj">
                <option value="${obj.key}">${obj.value}</option>
              </c:forEach>
            </select>
          </th>
          <th></th>
          <!-- (1) End -->
        </tr>
        <tr class="title">
          <th class="text-center px-0"></th>
          <th class="text-center">操作</th>
          <!-- (2) Start -->
          <th class="text-center">#</th>
          <th class="text-center">テキスト</th>
          <th class="text-center">テキスト<br>(数値・整数)</th>
          <th class="text-center">テキスト<br>(数値・小数あり)</th>
          <th class="text-center">テキスト<br>(真偽値)</th>
          <th class="text-center">テキスト<br>(複数の値)</th>
          <th class="text-center">ラジオボタン<br>(真偽値)</th>
          <th class="text-center">ラジオボタン<br>(文字列)</th>
          <th class="text-center">チェックボックス<br>(文字列)</th>
          <th class="text-center">チェックボックス<br>(複数の値)</th>
          <th class="text-center">日付</th>
          <th class="text-center">日付時刻</th>
          <th class="text-center">select</th>
          <th class="text-center">select<br>(複数の値)</th>
          <th class="text-center">select2</th>
          <th class="text-center">select2<br>(複数の値)</th>
          <th class="text-center">コンボボックス</th>
          <th class="text-center">コンボボックス<br>(Select2-tags)</th>
          <th class="text-center">コンボボックス<br>(Select2-tags 複数の値)</th>
          <th class="text-center">ファイル名</th>
          <th class="text-center">ステータス</th>
          <th class="text-center">最終更新日時</th>
          <!-- (2) End -->
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

          'ajax': {
            'url': 'list/json',
            'data': myflatten
          },

          scrollX: true,

          fixedColumns: {
            leftColumns: 3
          },          

          // select: 'multi',         

          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [
            {
              data: 'id',
              className: 'text-center',
              orderable: false,
              searchable: false,
              checkboxes: {
               'selectRow': false
              }
            },
            {
              data: 'operations',
              orderable: false,
              searchable: false,
            },
          //<!-- (3) Start -->
            {
              data: 'id',
              orderable: true,
              searchable: true,
            },
            {
              data: 'text01',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'text02',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'text03',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'text04',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'text05',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'radio01Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'radio02',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'checkbox01Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'checkbox02Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'date01',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'datetime01',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'select01Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'select02Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'select03Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'select04Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'combobox01',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'combobox02Label',
              render: $.fn.dataTable.render.text(),
            },
            { 
              data: 'combobox03Label',
              render: $.fn.dataTable.render.text(),
            },
            // { 
            //   data: 'attachedFile01FileName',
            //   render: $.fn.dataTable.render.text(),
            // },
            {
              data: 'attachedFile01Managed.originalFilename',
              render: $.fn.dataTable.render.text(),
              "defaultContent": " - ",
            },            
            {
              data: 'statusLabel',
              className: 'text-center',
              // orderable: false,
              // searchable: false,
            },
            {
              data: 'lastModifiedDate',
              className: 'text-center',
            },
          //<!-- (3) End -->
          ],

          // 初期ソート
          'order': [
            [2, 'asc']
          ],

          // ボタンの表示
          'buttons': ['colvis', 'stateClear', 'csvdownload', 'tsvdownload', 'exceldownload', 'upload', 'createnew'],

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

          // checkboxesテスト
          getSelectedKey(table, 0);
        });

        $('#draft').change().on('change', function (e, s) {
          localStorage.dataTables_Draft = e.target.checked;
          table.draw();
          fnColumns(table);
        });

        // 項目単位フィルタの追加
        // addFieldFilter(table) // 通常版
        addFieldFilter2(table) // (列の並び順対応版)

        // 画面表示時の下書きチェックボックスの復元
        if (localStorage.dataTables_Draft == 'false') {
          $('#draft')[0].checked = false;
          table.draw();
        } else if (localStorage.dataTables_Draft != 'false' && $('#draft')[0].checked == false) {
          $('#draft')[0].checked = true;
          table.draw();
        }

      });

  function myflatten(params, settings) {
    params = flatten(params, settings);
    if ($('#draft')[0] == undefined) {
        params.draft = true;
    } else {
        params.draft = $('#draft')[0].checked
    }
    return params;
  }
</script>