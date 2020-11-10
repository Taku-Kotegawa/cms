<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>セレクトテスト編集</h4>
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
    <form:form modelAttribute="selectForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-12">

          <c:set var="id" value="${select.id}" />

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
                  ${f:h(CL_STATUS[select.status])}
                </div>
              </div>
            </div>
          </c:if>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.select01__input || fieldState.select01__view}">
                <form:label path="select01">文字列型</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select01__input}">
                <form:select path="select01" cssClass="form-control select2" cssErrorClass="form-control is-invalid">
                  <!-- <form:option value="" label="--Select--" /> -->
                  <form:options items="${CL_STATUS}" />
                </form:select>
                <form:errors path="select01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.select01__hidden}">
                <form:hidden path="select01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.select01__view}">
                <div class="form-control form-control__view">
                  <c:forEach var="item" items="${select.select01}" varStatus="status">
                    <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>



          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.select02__input || fieldState.select02__view}">
                <form:label path="select02">整数型</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select02__input}">
                <form:select path="select02" cssClass="form-control select2" cssErrorClass="form-control is-invalid">
                  <!-- <form:option value="" label="--Select--" /> -->
                  <form:options items="${CL_STATUS}" />
                </form:select>
                <form:errors path="select02" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.select02__hidden}">
                <form:hidden path="select02" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.select02__view}">
                <div class="form-control form-control__view">
                  <c:forEach var="item" items="${select.select02}" varStatus="status">
                    <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
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