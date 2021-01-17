<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>店所編集</h4>
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
    <form:form modelAttribute="shopForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-12">

          <c:set var="id" value="${shop.id}" />

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
                  ${f:h(CL_STATUS[shop.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.shopCode__input || fieldState.shopCode__view}">
                <form:label path="shopCode">店所コード</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.shopCode__input}">
                <form:input path="shopCode" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.shopCode__disabled}" readonly="${fieldState.shopCode__readonly}" />
                <form:errors path="shopCode" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.shopCode__hidden}">
                <form:hidden path="shopCode" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.shopCode__view}">
                <div class="form-control form-control__view">
                  ${f:h(shop.shopCode)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.title__input || fieldState.title__view}">
                <form:label path="title">店所名</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.title__input}">
                <form:input path="title" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.title__disabled}" readonly="${fieldState.title__readonly}" />
                <form:errors path="title" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.title__hidden}">
                <form:hidden path="title" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.title__view}">
                <div class="form-control form-control__view">
                  ${f:h(shop.title)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.weight__input || fieldState.weight__view}">
                <form:label path="weight">並び順</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.weight__input}">
                <form:input type="number" path="weight" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.weight__disabled}" readonly="${fieldState.weight__readonly}" />
                <form:errors path="weight" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.weight__hidden}">
                <form:hidden path="weight" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.weight__view}">
                <div class="form-control form-control__view">
                  ${f:h(shop.weight)}
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
