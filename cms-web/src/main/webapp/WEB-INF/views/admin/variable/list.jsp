<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<section class="content-header  px-5">
  <div class="container-fluid">
    <div class="row mb-2">
      <div class="col-18">
        <h4>変数一覧</h4>
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
    <div>
      <p>変更してもコードリストに即時反映しません。再起動が必要。(TODO: コードリストのリロード機能を追加)<br>
      新規登録ボタン押下し登録フォームを開いたときに、一覧画面で選択している「タイプ」を初期値設定する様にしたい。</p>
    </div>

    <table id="list" class="table-sm table-striped">
      <thead>
        <tr class="filter">
          <th class="text-center px-1" data-filter="disable"></th>
          <th data-filter="disable"></th>
          <th></th>
          <th></th>
          <th data-filter="disable">
            <select id="col_filter_4" data-column="4" class="dataTables_column_filter form-control">
              <option value=""></option>
              <c:forEach items="${CL_STATUS}" var="obj">
                <option value="${obj.key}">${obj.value}</option>
              </c:forEach>
            </select>
          </th>
          <th data-filter="disable">
            <select id="type_filter" class="form-control">
              <c:forEach items="${CL_VARIABLETYPE}" var="obj">
                <option value="${obj.key}" <c:if test="${obj.key == variableType.codeValue}">selected</c:if>>${obj.value}
                </option>
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
          <th></th>
        </tr>
        <tr class="title">
          <th class="text-center px-0"></th>
          <th class="text-center">操作</th>
          <th class="text-center">ID</th>
          <th class="text-center">バージョン</th>
          <th class="text-center">ステータス</th>
          <th class="text-center">タイプ</th>
          <th class="text-center">コード</th>
          <th class="text-center">${variableType.labelValue1}</th>
          <th class="text-center">${variableType.labelValue2}</th>
          <th class="text-center">${variableType.labelValue3}</th>
          <th class="text-center">${variableType.labelValue4}</th>
          <th class="text-center">${variableType.labelValue5}</th>
          <th class="text-center">${variableType.labelValue6}</th>
          <th class="text-center">${variableType.labelValue7}</th>
          <th class="text-center">${variableType.labelValue8}</th>
          <th class="text-center">${variableType.labelValue9}</th>
          <th class="text-center">${variableType.labelValue10}</th>
          <th class="text-center">${variableType.labelDate1}</th>
          <th class="text-center">${variableType.labelDate2}</th>
          <th class="text-center">${variableType.labelDate3}</th>
          <th class="text-center">${variableType.labelDate4}</th>
          <th class="text-center">${variableType.labelDate5}</th>
          <th class="text-center">${variableType.labelValint1}</th>
          <th class="text-center">${variableType.labelValint2}</th>
          <th class="text-center">${variableType.labelValint3}</th>
          <th class="text-center">${variableType.labelValint4}</th>
          <th class="text-center">${variableType.labelValint5}</th>
          <th class="text-center">${variableType.labelTextarea}</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<!-- Page script -->
<script>
  let basepath = '${f:h(pageContext.request.contextPath)}';

  let stateSaveClear = '${stateSaveClear}';

  ${columnVisible}

  $(document)
    .ready(
      function () {

        $('#type_filter').change(function () {
          table.state.clear();
          window.location.href = basepath + '/admin/variable/list?type=' + $(this).val();
        });


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
            'data': flatten
          },

          // stateSave: false,

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
              visible: false,
            },
            {
              data: 'version',
              render: $.fn.dataTable.render.text(),
              visible: false,
            },
            {
              data: 'statusLabel',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'type',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'code',
              render: $.fn.dataTable.render.text(),
            },
            {
              data: 'value1',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value1'],
            },
            {
              data: 'value2',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value2'],
            },
            {
              data: 'value3',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value3'],
            },
            {
              data: 'value4',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value4'],
            },
            {
              data: 'value5',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value5'],
            },
            {
              data: 'value6',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value6'],
            },
            {
              data: 'value7',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value7'],
            },
            {
              data: 'value8',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value8'],
            },
            {
              data: 'value9',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value9'],
            },
            {
              data: 'value10',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['value10'],
            },
            {
              data: 'date1',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['date1'],
            },
            {
              data: 'date2',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['date2'],
            },
            {
              data: 'date3',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['date3'],
            },
            {
              data: 'date4',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['date4'],
            },
            {
              data: 'date5',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['date5'],
            },
            {
              data: 'valint1',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['valint1'],
            },
            {
              data: 'valint2',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['valint2'],
            },
            {
              data: 'valint3',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['valint3'],
            },
            {
              data: 'valint4',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['valint4'],
            },
            {
              data: 'valint5',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['valint5'],
            },
            {
              data: 'textarea',
              render: $.fn.dataTable.render.text(),
              visible: columnVisible['textarea'],
            },
          ],
          // 初期ソート
          'order': [
            [2, 'asc']
          ],
          "searchCols": [
            null,
            null,
            null,
            null,
            null,
            {
              "search": '${variableType.codeValue}'
            },
          ],

          // ボタンの表示
          'buttons': ['colvis', 'stateClear', 'csvdownload', 'tsvdownload', 'upload', 'createnew'],

          // データロード後処理
          'initComplete': function (settings, json) {
            // グローバルフィルターのEnterキーでサーバに送信
            fnGlobalFilterOnReturn(table);
          },
        });

        if (table.column(5).search() != '${variableType.codeValue}') {
          table.state.clear();
          window.location.reload();
        }

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