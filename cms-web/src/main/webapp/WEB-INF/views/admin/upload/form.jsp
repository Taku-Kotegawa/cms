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
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <!-- １．フォームタグで囲む -->
    <form:form modelAttribute="uploadForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />


      <div class="row operation-button-block">
        <div class="col-36">
          <!-- ホーム画面に戻る -->
          <c:if test="${buttonState.goHome__view}">
            <a id="gotoList" href="${pageContext.request.contextPath}" class="btn btn-button mr-2" <c:if
              test="${buttonState.goHome__disabled}">disabled</c:if> >戻る</a>
          </c:if>
        </div>
      </div>
      <hr>
      <div>
        <div class="row mb-3">
          <div class="col-12">
            <!-- ラベル -->
            <c:if test="${fieldState.uploadFileUuid__input || fieldState.uploadFileUuid__view}">
              <form:label path="uploadFileUuid">画像*</form:label>
            </c:if>
            <!-- 入力 -->
            <c:if test="${fieldState.uploadFileUuid__input}">
              <input id="uploadFile" type="file" class="form-control form-control-file file-managed"
                data-file-type="simpleentity" data-extention-pattern="png jpg gif" <c:if
                test="${simpleEntityForm.uploadFileUuid != null}">style="display: none;"</c:if> />
            <form:errors path="uploadFileUuid" cssClass="invalid-feedback" />
            <c:if test="${simpleEntityForm.uploadFileManaged != null}">
              <div id="uploadFile__attached-block" class="input-group">
                <span>
                  <i class="far fa-file ml-2"></i>
                  <a href="${pageContext.request.contextPath}${op.getDownloadUrl(simpleEntityForm.uploadFileUuid)}"
                    target="_blank" class="link-attached">${simpleEntityForm.uploadFileManaged.originalFilename}</a>
                  <c:if test="${fieldState.uploadFileUuid__input && !fieldState.uploadFileUuid__readonly}">
                    <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('uploadFile')"></i>
                  </c:if>
                  <form:input type="hidden" path="uploadFileUuid" />
                </span>
              </div>
            </c:if>
            </c:if>
            <!-- 参照用-->
            <c:if test="${fieldState.uploadFileUuid__view}">
              <div>
                <c:if test="${simpleEntity.uploadFileManaged != null}">
                  <span>
                    <i class="far fa-file ml-2"></i>
                    <a href="${pageContext.request.contextPath}${op.getDownloadUrl(simpleEntity.uploadFileManaged.uuid)}"
                      target="_blank" class="link-attached">${simpleEntity.uploadFileManaged.originalFilename}</a>
                  </span>
                </c:if>
              </div>
            </c:if>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-24">
            <button type="submit" name="confirm" value="confirm" class="btn-button">アップロード</button>
          </div>
        </div>
      </div>
  </div>

  </form:form>

  <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
