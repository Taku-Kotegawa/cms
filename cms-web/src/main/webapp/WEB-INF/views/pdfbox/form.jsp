<t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Page Title</h3>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <!-- ここより下にメインコンテンツを記入 -->

    <form:form modelAttribute="pdfboxForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->


            <div class="form-group row">
                <div class="col-6">
                    <form:label path="startPage">開始ページ番号</form:label>
                    <form:input type="number" path="startPage" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                    <form:errors path="startPage" cssClass="invalid-feedback" />
                </div>

                <div class="col-6">
                    <form:label path="endPage">終了ページ番号</form:label>
                    <form:input type="number" path="endPage" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                    <form:errors path="endPage" cssClass="invalid-feedback" />
                </div>
            </div>

            <div class="form-group row">
                <div class="col-36">
                    <input class="btn-button" id="download" name="download" type="submit" value="ダウンロード"/>
                </div>
            </div>

    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

