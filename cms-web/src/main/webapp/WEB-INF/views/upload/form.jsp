<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>${pageTitle}</h3>
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

    <form:form modelAttribute="uploadForm" enctype="multipart/form-data" autocomplete="off">

      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <form:hidden path="jobName" />

      <div>
        <div class="row mb-3">
          <div class="col-12">
            <!-- ラベル -->
            <form:label path="uploadFileUuid">インポートファイル*</form:label>
            <!-- 入力 -->
            <input id="uploadFile" type="file" class="form-control form-control-file file-managed"
              data-file-type="simpleentity" data-extention-pattern="png jpg gif" <c:if
              test="${uploadForm.uploadFileUuid != null}">style="display: none;"</c:if>/>
            <form:errors path="uploadFileUuid" cssClass="invalid-feedback" />
            <c:if test="${uploadForm.uploadFileManaged != null}">
              <div id="uploadFile__attached-block" class="input-group">
                <span>
                  <i class="far fa-file ml-2"></i>
                  <a href="${pageContext.request.contextPath}${op.getDownloadUrl(uploadForm.uploadFileUuid)}"
                    target="_blank" class="link-attached">${uploadForm.uploadFileManaged.originalFilename}</a>
                  <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('uploadFile')"></i>
                  <form:input type="hidden" path="uploadFileUuid" />
                </span>
              </div>
            </c:if>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-36">
            <a href="${referer}" class="btn btn-button">キャンセル</a>
            <button type="submit" name="confirm" value="confirm" class="btn btn-button">アップロード</button>
          </div>
        </div>
        <br>
        <br>
        <div class="row mb-3">
          <div class="col-12">
            <h5>インポートファイルの条件</h5>
            <table class="table-sm">
              <tbody>
                <tr class="border-top">
                  <th>ファイル形式</th>
                  <td>CSV</td>
                </tr>
                <tr>
                  <th>文字コード</th>
                  <td>UTF-8</td>
                </tr>
                <tr>
                  <th>レイアウト</th>
                  <td>CSVダウンロードファイルと同じ</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
  </div>

  </form:form>

  <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
