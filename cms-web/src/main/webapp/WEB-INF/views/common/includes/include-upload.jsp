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
              data-file-type="uploadFile" <c:if test="${uploadForm.uploadFileUuid != null}">style="display: none;"</c:if>/>
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
          <div class="col-12">
            <!-- ラベル -->
            <form:label path="fileType">ファイル形式*</form:label>
            <!-- 入力 -->
            <form:select path="fileType" cssClass="form-control" cssErrorClass="form-control is-invalid">
              <form:option value="CSV" label="CSV(カンマ区切り, ダブルクォーテーション括り)" />
              <form:option value="TSV" label="TSV(タブ区切り)" />
            </form:select>
            <form:errors path="fileType" cssClass="invalid-feedback" />
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-12">
            <!-- ラベル -->
            <form:label path="encoding">文字コード*</form:label>
            <!-- 入力 -->
            <form:select path="encoding" cssClass="form-control" cssErrorClass="form-control is-invalid">
              <form:option value="UTF-8" label="UTF-8" />
              <form:option value="MS932" label="SJIS" />
            </form:select>
            <form:errors path="encoding" cssClass="invalid-feedback" />
          </div>
        </div>

        <br>

        <div class="row mb-3">
          <div class="col-36">
            <a href="${referer}" class="btn btn-button">キャンセル</a>
            <button type="submit" name="confirm" value="confirm" class="btn btn-button">アップロード</button>
          </div>
        </div>

      </div>

    </form:form>

    <br>
    <br>
