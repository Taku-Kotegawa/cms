<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>ファイルアップロード</h3>
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

    <form:form id="upload_form" action="upload" method="POST" modelAttribute="uploadFileForm"
      enctype="multipart/form-data" autocomplete="off">

      <div class="row">
        <div class="col-12">
          <label>アップロードファイル *</label>
          <div id="upload_file_input-block" class="input-group">
            <input form="upload_form" id="upload_file" name="file" type="file"
              class="form-control form-control-file" onChange="upload(this);"/>
            <!-- <button id="upload_file_upload" type="button" onclick="upload();">アップロード</button> -->
          </div>
          <div id="upload_file_attached-block" class="input-group">
            <!-- <span id="upload_file_attached"><a href="#">ファイル名.txt</a><button type="button">削除</button></span> -->
          </div>
        </div>
      </div>
      <div class="row">
        <label>アップロードファイル *</label>
      </div>



    </form:form>




    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<!-- JQuery UI ----------------------------------------------------------------------------------------------------------->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/jquery-ui/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/jquery-ui/jquery-ui.min.css">

<script
  src="${pageContext.request.contextPath}/resources/plugins/node_modules/blueimp-file-upload/js/jquery.fileupload.js">
</script>

<style>
  .link-attached {
    padding: 6px 6px 6px 4px;
    display: inline-block;
  }
</style>

<script type="text/javascript">

  var contextPath = "${pageContext.request.contextPath}";





  function file_delete() {
      document.getElementById("upload_file_input-block").style.display = "";
      var attaced_block = document.getElementById("upload_file_attached-block");
      while (attaced_block.firstChild) {
        attaced_block.removeChild(attaced_block.firstChild);
      }
      var input = document.getElementById("upload_file");
      input.value = "";
  }


  function upload(element) {

      // var $upload_file = document.getElementById('upload_file')
      var $upload_file = element;
      var $upload_form = $upload_file.form;

      if (!$upload_file.value) {
        alert("アップロードファイルを指定してください。");
        return;
      }

      if ($upload_file.files[0].size > 10*1024*1024) {
        alert("ファイルが大きすぎます。");
        return;
      }

      var regex = /\\|\//;
      var array = $upload_file.value.split(regex);
      var ajaxUrl = contextPath + "/api/file";

      if (window.FormData) {
        var formData = new FormData($upload_form);
        $.ajax({
          type: "POST", // HTTP通信の種類
          url: ajaxUrl, // リクエストを送信する先のURL
          dataType: "json", // サーバーから返されるデータの型
          data: formData, // サーバーに送信するデータ
          processData: false,
          contentType: false,
          enctype: 'multipart/form-data',

        }).done(function (data) { // Ajax通信が成功した時の処理



          document.getElementById("upload_file_input-block").style.display = "none";

          var block = document.getElementById('upload_file_attached-block');
          var span = document.createElement('span');
          span.id = "upload_file_attached";
          span.class = "form-control";
          span.data_atttached = "data";
          block.appendChild(span);

          var file_icon = document.createElement('i');
          file_icon.classList = "far fa-file ml-2";
          span.appendChild(file_icon);

          var link = document.createElement('a');
          link.href = data.url;
          link.textContent = data.name;
          link.target = "_blank";
          link.classList = "link-attached";
          span.appendChild(link);

          var trush_icon = document.createElement('i');
          trush_icon.classList = "far fa-trash-alt";
          trush_icon.style = "color: brown;";
          trush_icon.onclick = function () {
            file_delete();
          };
          span.appendChild(trush_icon);

          var uuid = document.createElement('input');
          uuid.type = "hidden";
          uuid.name = "upload_file_uuid";
          uuid.value = data.uuid;
          span.appendChild(uuid);

          var fid = document.createElement('input');
          fid.type = "hidden";
          fid.name = "upload_file_fid";
          fid.value = data.fid;
          span.appendChild(fid);

        }).fail(function (XMLHttpRequest, textStatus, errorThrown) { // Ajax通信が失敗した時の処理
          console.log(XMLHttpRequest);
          console.log(textStatus);
          console.log(errorThrown);
          alert("アップロードが失敗しました。");

        });
      } else {
        alert("アップロードに対応できていないブラウザです。");
      }
  }
</script>
