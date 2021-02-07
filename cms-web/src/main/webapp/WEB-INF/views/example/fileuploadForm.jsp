<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>ファイルアップロード</h4>
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

    <form:form id="upload_form" action="save" method="POST" modelAttribute="uploadFileForm"
      enctype="multipart/form-data" autocomplete="off">

      <div class="row">
        <div class="col-12">
          <label>アップロードファイル *</label>
          <input form="upload_form" id="uploadFile" type="file" class="form-control form-control-file file-managed" data-file-type="fileupload" />
            <!-- onChange="file_attach(this);" /> -->
        </div>
      </div>
      <div class="row">
      </div>
      <br>
      <hr>
      <div class="row">
        <button type="submit" class="btn-button">保存</button>
      </div>

    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<!-- JQuery UI ----------------------------------------------------------------------------------------------------------->
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/jquery-ui/jquery-ui.min.js"></script> -->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/jquery-ui/jquery-ui.min.css"> -->

<!-- <script
  src="${pageContext.request.contextPath}/resources/plugins/node_modules/blueimp-file-upload/js/jquery.fileupload.js">
</script> -->

<style>
  .link-attached {
    padding: 6px 6px 6px 4px;
    display: inline-block;
  }
</style>

<script type="text/javascript">
$(document).ready(function(){
  // ManagedFileのイベント登録
  $('.file-managed').change(function(event){
    file_attach($(this).get(0))
  });
});
</script>
