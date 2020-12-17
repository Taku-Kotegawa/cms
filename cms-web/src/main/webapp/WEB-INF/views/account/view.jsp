<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<style>
  .modal.fade .modal-dialog,
  .modal-backdrop.fade
  {
      -moz-transition: none !important;
      -o-transition: none !important;
      -webkit-transition: none !important;
      transition: none !important;
  
      -moz-transform: none !important;
      -ms-transform: none !important;
      -o-transform: none !important;
      -webkit-transform: none !important;
      transform: none !important;
  }
  </style>

<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>アカウト情報</h3>
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

      <div class="col-5 text-center">
        <img src="${f:h(pageContext.request.contextPath)}/account/image" width="100" height="100" />
      </div>
      <div class="col-31">
        <table>
          <tr>
            <th>Username</th>
            <td>${f:h(account.username)}</td>
          </tr>
          <tr>
            <th>First name</th>
            <td>${f:h(account.firstName)}</td>
          </tr>
          <tr>
            <th>Last name</th>
            <td>${f:h(account.lastName)}</td>
          </tr>
          <tr>
            <th>E-Mail</th>
            <td>${f:h(account.email)}</td>
          </tr>
          <tr>
            <th>URL</th>
            <td>${f:link(account.url)}</td>
          </tr>
          <tr>
            <th>Profile</th>
            <td>${f:br(f:h(account.profile))}</td>
          </tr>
        </table>
      </div>
    </div>

    <br>
    <div class="row form-group">
      <a href="${f:h(pageContext.request.contextPath)}/" class="btn-button">ホーム</a>

      <a id="changePassword" href="${f:h(pageContext.request.contextPath)}/password?form"
        class="btn-button ml-2">パスワード変更</a>

      <button id="openDialog" onclick="openDialog('/cms-web/common/dialog/test')"
      class="btn-button ml-2">ダイアログ</a>


    </div>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<script>

  function openDialog(url) {

    var options = {
        url: url,
        // title:'Header title',
        size: eModal.size.lg,
    };
    eModal.ajax(options);
  }


  $(function () {

    $(document).on('shown.bs.modal', function (event) {
      console.log("shown");
      console.log(event);
      aaa();
  	});


    var options = {
    message: 'test message',
    title: 'Header title',
    size: 'lg',
    subtitle: 'smaller text header',
                    buttons: [
                    { text: '閉じる', close: true, style: 'danger' },
                    { text: 'New content', close: false, style: 'success' }
                    ],
    };
    // eModal.alert(options);

    // var r = eModal.ajax('http://localhost:8080/cms-web/common/dialog/test', 'Jobs - Form apply')
    // .done(function(result) {
    //   // setTimeout(function(){
    //   //   aaa();
    //   // }, 200)
    // }).fail(function(result) {
    //   console.log("fail");
        
    // });    

    // console.log(r);

  });


</script>




<script>

  function aaa() {

    console.log("call aaa");

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
        'url': '../person/list/json',
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
          // render: $.fn.dataTable.render.text(),
        },
        {
          data: 'version',
          // render: $.fn.dataTable.render.text(),
        },
        {
          data: 'statusLabel',
          // render: $.fn.dataTable.render.text(),
        },
        {
          data: 'name',
          // render: $.fn.dataTable.render.text(),
        },
        {
          data: 'age',
          // render: $.fn.dataTable.render.text(),
        },
        {
          data: 'attachedFile01Managed.originalFilename',
          // render: $.fn.dataTable.render.text(),
          "defaultContent": " - ",
        },
      ],

      // 初期ソート
      'order': [
        [2, 'asc']
      ],

      // ボタンの表示
      'buttons': ['colvis', 'stateClear', 'csvdownload', 'tsvdownload', 'createnew'],

      // データロード後処理
      'initComplete': function (settings, json) {
        // グローバルフィルターのEnterキーでサーバに送信
        fnGlobalFilterOnReturn(table);
      },
    });

    // ページネーション後に画面トップに戻る
    // table.on('page.dt', function () {
    //   $('html, body').animate({
    //     scrollTop: 0
    //   }, 300);
    // });

    // 項目単位フィルタの追加
    // addFieldFilter(table)

    // 項目単位フィルタを追加(列の並び順対応版)
    // addFieldFilter2(table)


    // $('#draft').change().on('change', function (e, s) {
    //   localStorage.dataTables_Draft = e.target.checked;
    //   table.draw();
    //   fnColumns(table);
    // });

    // if (localStorage.dataTables_Draft == 'false') {
    //   $('#draft')[0].checked = false;
    //   table.draw();
    // }

  };

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