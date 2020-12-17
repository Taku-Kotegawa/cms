<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>従業員編集</h4>
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
    <form:form modelAttribute="personForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-12">

          <c:set var="id" value="${person.id}" />

          <!-- 一覧に戻る -->
          <c:if test="${buttonState.gotoList__view}">
            <a id="gotoList" href="${pageContext.request.contextPath}${op.getListUrl()}" class="btn btn-button mr-2"
              <c:if test="${buttonState.gotoList__disabled}">disabled</c:if>>${op.getLABEL_LIST()}</a>
          </c:if>

          <!-- 参照 -->
          <c:if test="${buttonState.view__view}">
            <a id="view" href="${pageContext.request.contextPath}${op.getViewUrl(id)}" class="btn btn-button mr-2" <c:if
              test="${buttonState.view__disabled}">disabled</c:if> >${op.getLABEL_VIEW()}</a>
          </c:if>

          <!-- 編集 -->
          <c:if test="${buttonState.gotoUpdate__view}">
            <a id="gotoUpdate" href="${pageContext.request.contextPath}${op.getEditUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.gotoUpdate__disabled}">disabled</c:if>>${op.getLABEL_EDIT()}</a>
          </c:if>

          <!-- 下書き保存 -->
          <c:if test="${buttonState.saveDraft__view}">
            <button id="saveDraft" name="saveDraft" value="true" type="submit" class="btn btn-button mr-2" <c:if
              test="${buttonState.saveDraft__disabled}">disabled</c:if>>${op.getLABEL_SAVE_DRAFT()}</button>
          </c:if>

          <!-- 下書き取消 -->
          <c:if test="${buttonState.cancelDraft__view}">
            <a id="cancelDraft" href="${pageContext.request.contextPath}${op.getCancelDraftUrl(id)}"
              class="btn btn-button mr-2" <c:if test="${buttonState.cancelDraft__disabled}">disabled</c:if>
          >${op.getLABEL_CANCEL_DRAFT()}</a>
          </c:if>

          <!-- 保存 -->
          <c:if test="${buttonState.save__view}">
            <button id="save" name="save" type="submit" class="btn btn-button mr-2" <c:if
              test="${buttonState.save__disabled}">disabled</c:if>>保存</button>
          </c:if>

          <!-- 無効 -->
          <c:if test="${buttonState.invalid__view}">
            <a id="delete" href="${pageContext.request.contextPath}${op.getInvalidUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.invalid__disabled}">disabled</c:if> >${op.getLABEL_INVALID()}</a>
          </c:if>

          <!-- 無効解除 -->
          <c:if test="${buttonState.valid__view}">
            <a id="delete" href="${pageContext.request.contextPath}${op.getValidUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.valid__disabled}">disabled</c:if> >${op.getLABEL_VALID()}</a>
          </c:if>

          <!-- 削除 -->
          <c:if test="${buttonState.delete__view}">
            <a id="delete" href="${pageContext.request.contextPath}${op.getDeleteUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.delete__disabled}">disabled</c:if> >${op.getLABEL_DELETE()}</a>
          </c:if>

        </div>
      </div>

      <hr />

        <form:hidden path="version" />

          <c:if test="${fieldState.status__view}">
            <div class="row mb-3">
              <div class="col-11 ">
                <!-- ラベル -->
                <form:label path="status">ステータス</form:label>
                <div class="form-control__view">
                  ${f:h(CL_STATUS[person.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.name__input || fieldState.name__view}">
                <form:label path="name">氏名</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.name__input}">
                <form:input path="name" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.name__disabled}" readonly="${fieldState.name__readonly}" />
                <form:errors path="name" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.name__hidden}">
                <form:hidden path="name" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.name__view}">
                <div class="form-control form-control__view">
                  ${f:h(person.name)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.age__input || fieldState.age__view}">
                <form:label path="age">年齢</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.age__input}">
                <form:input path="age" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.age__disabled}" readonly="${fieldState.age__readonly}" />
                <form:errors path="age" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.age__hidden}">
                <form:hidden path="age" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.age__view}">
                <div class="form-control form-control__view">
                  ${f:h(person.age)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.code__input || fieldState.code__view}">
                <form:label path="code">コード</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.code__input}">
                <form:select path="code" cssClass="form-control select2"
                  cssErrorClass="form-control select2 is-invalid">
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_SAMPLE}" />
                </form:select>
                <form:errors path="code" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.code__hidden}">
                <form:hidden path="code" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.code__view}">
                <div class="form-control form-control__view">
                  ${f:h(CL_STATUS[simpleEntity.code])}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.attachedFile01Uuid__input || fieldState.attachedFile01Uuid__view}">
                <form:label path="attachedFile01Uuid">ファイル</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.attachedFile01Uuid__input}">
                <input id="attachedFile01" type="file" class="form-control form-control-file file-managed"
                  data-file-type="person" data-extention-pattern="png jpg gif" <c:if
                  test="${personForm.attachedFile01Uuid != null}">style="display: none;"</c:if> />
                <form:errors path="attachedFile01Uuid" cssClass="invalid-feedback" />
                <c:if test="${personForm.attachedFile01Managed != null}">
                  <div id="attachedFile01__attached-block" class="input-group">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(personForm.attachedFile01Uuid)}"
                        target="_blank"
                        class="link-attached">${personForm.attachedFile01Managed.originalFilename}</a>
                      <c:if test="${fieldState.attachedFile01Uuid__input && !fieldState.attachedFile01Uuid__readonly}">
                        <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('attachedFile01')"></i>
                      </c:if>
                      <form:input type="hidden" path="attachedFile01Uuid" />
                    </span>
                  </div>
                </c:if>
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.attachedFile01Uuid__view}">
                <div>
                  <c:if test="${person.attachedFile01Managed != null}">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(person.attachedFile01Managed.uuid)}"
                        target="_blank" class="link-attached">${person.attachedFile01Managed.originalFilename}</a>
                    </span>
                  </c:if>
                </div>
              </c:if>
            </div>
          </div>


    </form:form>

    <br>
    <br>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
<script src="${pageContext.request.contextPath}/resources/app/js/form-onload-default.js"></script>