<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>ページ索引編集</h4>
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
    <form:form modelAttribute="pageIdxForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-12">

          <c:set var="id" value="${pageIdx.id}" />

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
                  ${f:h(CL_STATUS[pageIdx.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.customerNumber__input || fieldState.customerNumber__view}">
                <form:label path="customerNumber">お客さま番号</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.customerNumber__input}">
                <form:input path="customerNumber" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.customerNumber__disabled}" readonly="${fieldState.customerNumber__readonly}" />
                <form:errors path="customerNumber" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.customerNumber__hidden}">
                <form:hidden path="customerNumber" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.customerNumber__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.customerNumber)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.customerName__input || fieldState.customerName__view}">
                <form:label path="customerName">お客さま名</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.customerName__input}">
                <form:input path="customerName" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.customerName__disabled}" readonly="${fieldState.customerName__readonly}" />
                <form:errors path="customerName" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.customerName__hidden}">
                <form:hidden path="customerName" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.customerName__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.customerName)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.startPage__input || fieldState.startPage__view}">
                <form:label path="startPage">開始ページ番号</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.startPage__input}">
                <form:input path="startPage" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.startPage__disabled}" readonly="${fieldState.startPage__readonly}" />
                <form:errors path="startPage" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.startPage__hidden}">
                <form:hidden path="startPage" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.startPage__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.startPage)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.attachedFileUuid__input || fieldState.attachedFileUuid__view}">
                <form:label path="attachedFileUuid">添付ファイル</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.attachedFileUuid__input}">
                <form:input path="attachedFileUuid" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.attachedFileUuid__disabled}" readonly="${fieldState.attachedFileUuid__readonly}" />
                <form:errors path="attachedFileUuid" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.attachedFileUuid__hidden}">
                <form:hidden path="attachedFileUuid" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.attachedFileUuid__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.attachedFileUuid)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.documentId__input || fieldState.documentId__view}">
                <form:label path="documentId">ドキュメントID</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.documentId__input}">
                <form:input path="documentId" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.documentId__disabled}" readonly="${fieldState.documentId__readonly}" />
                <form:errors path="documentId" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.documentId__hidden}">
                <form:hidden path="documentId" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.documentId__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.documentId)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword1__input || fieldState.keyword1__view}">
                <form:label path="keyword1">キーワード1</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword1__input}">
                <form:input path="keyword1" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword1__disabled}" readonly="${fieldState.keyword1__readonly}" />
                <form:errors path="keyword1" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword1__hidden}">
                <form:hidden path="keyword1" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword1__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword1)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword2__input || fieldState.keyword2__view}">
                <form:label path="keyword2">キーワード2</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword2__input}">
                <form:input path="keyword2" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword2__disabled}" readonly="${fieldState.keyword2__readonly}" />
                <form:errors path="keyword2" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword2__hidden}">
                <form:hidden path="keyword2" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword2__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword2)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword3__input || fieldState.keyword3__view}">
                <form:label path="keyword3">キーワード3</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword3__input}">
                <form:input path="keyword3" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword3__disabled}" readonly="${fieldState.keyword3__readonly}" />
                <form:errors path="keyword3" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword3__hidden}">
                <form:hidden path="keyword3" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword3__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword3)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword4__input || fieldState.keyword4__view}">
                <form:label path="keyword4">キーワード4</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword4__input}">
                <form:input path="keyword4" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword4__disabled}" readonly="${fieldState.keyword4__readonly}" />
                <form:errors path="keyword4" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword4__hidden}">
                <form:hidden path="keyword4" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword4__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword4)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword5__input || fieldState.keyword5__view}">
                <form:label path="keyword5">キーワード5</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword5__input}">
                <form:input path="keyword5" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword5__disabled}" readonly="${fieldState.keyword5__readonly}" />
                <form:errors path="keyword5" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword5__hidden}">
                <form:hidden path="keyword5" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword5__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword5)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword6__input || fieldState.keyword6__view}">
                <form:label path="keyword6">キーワード6</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword6__input}">
                <form:input path="keyword6" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword6__disabled}" readonly="${fieldState.keyword6__readonly}" />
                <form:errors path="keyword6" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword6__hidden}">
                <form:hidden path="keyword6" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword6__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword6)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword7__input || fieldState.keyword7__view}">
                <form:label path="keyword7">キーワード7</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword7__input}">
                <form:input path="keyword7" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword7__disabled}" readonly="${fieldState.keyword7__readonly}" />
                <form:errors path="keyword7" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword7__hidden}">
                <form:hidden path="keyword7" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword7__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword7)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword8__input || fieldState.keyword8__view}">
                <form:label path="keyword8">キーワード8</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword8__input}">
                <form:input path="keyword8" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword8__disabled}" readonly="${fieldState.keyword8__readonly}" />
                <form:errors path="keyword8" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword8__hidden}">
                <form:hidden path="keyword8" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword8__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword8)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword9__input || fieldState.keyword9__view}">
                <form:label path="keyword9">キーワード9</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword9__input}">
                <form:input path="keyword9" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword9__disabled}" readonly="${fieldState.keyword9__readonly}" />
                <form:errors path="keyword9" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword9__hidden}">
                <form:hidden path="keyword9" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword9__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword9)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keyword10__input || fieldState.keyword10__view}">
                <form:label path="keyword10">キーワード10</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keyword10__input}">
                <form:input path="keyword10" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.keyword10__disabled}" readonly="${fieldState.keyword10__readonly}" />
                <form:errors path="keyword10" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keyword10__hidden}">
                <form:hidden path="keyword10" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keyword10__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keyword10)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate1__input || fieldState.keydate1__view}">
                <form:label path="keydate1">キー日付1</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate1__input}">
                <div class="input-group" id="keydate1-input-group" data-target-input="nearest">
                  <form:input path="keydate1" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate1-input-group" />
                  <div class="input-group-append" data-target="#keydate1-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate1" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate1__hidden}">
                <form:hidden path="keydate1" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate1__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate1.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate2__input || fieldState.keydate2__view}">
                <form:label path="keydate2">キー日付2</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate2__input}">
                <div class="input-group" id="keydate2-input-group" data-target-input="nearest">
                  <form:input path="keydate2" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate2-input-group" />
                  <div class="input-group-append" data-target="#keydate2-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate2" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate2__hidden}">
                <form:hidden path="keydate2" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate2__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate2.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate3__input || fieldState.keydate3__view}">
                <form:label path="keydate3">キー日付3</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate3__input}">
                <div class="input-group" id="keydate3-input-group" data-target-input="nearest">
                  <form:input path="keydate3" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate3-input-group" />
                  <div class="input-group-append" data-target="#keydate3-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate3" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate3__hidden}">
                <form:hidden path="keydate3" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate3__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate3.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate4__input || fieldState.keydate4__view}">
                <form:label path="keydate4">キー日付4</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate4__input}">
                <div class="input-group" id="keydate4-input-group" data-target-input="nearest">
                  <form:input path="keydate4" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate4-input-group" />
                  <div class="input-group-append" data-target="#keydate4-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate4" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate4__hidden}">
                <form:hidden path="keydate4" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate4__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate4.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate5__input || fieldState.keydate5__view}">
                <form:label path="keydate5">キー日付5</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate5__input}">
                <div class="input-group" id="keydate5-input-group" data-target-input="nearest">
                  <form:input path="keydate5" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate5-input-group" />
                  <div class="input-group-append" data-target="#keydate5-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate5" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate5__hidden}">
                <form:hidden path="keydate5" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate5__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate5.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate6__input || fieldState.keydate6__view}">
                <form:label path="keydate6">キー日付6</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate6__input}">
                <div class="input-group" id="keydate6-input-group" data-target-input="nearest">
                  <form:input path="keydate6" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate6-input-group" />
                  <div class="input-group-append" data-target="#keydate6-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate6" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate6__hidden}">
                <form:hidden path="keydate6" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate6__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate6.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate7__input || fieldState.keydate7__view}">
                <form:label path="keydate7">キー日付7</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate7__input}">
                <div class="input-group" id="keydate7-input-group" data-target-input="nearest">
                  <form:input path="keydate7" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate7-input-group" />
                  <div class="input-group-append" data-target="#keydate7-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate7" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate7__hidden}">
                <form:hidden path="keydate7" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate7__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate7.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate8__input || fieldState.keydate8__view}">
                <form:label path="keydate8">キー日付8</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate8__input}">
                <div class="input-group" id="keydate8-input-group" data-target-input="nearest">
                  <form:input path="keydate8" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate8-input-group" />
                  <div class="input-group-append" data-target="#keydate8-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate8" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate8__hidden}">
                <form:hidden path="keydate8" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate8__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate8.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate9__input || fieldState.keydate9__view}">
                <form:label path="keydate9">キー日付9</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate9__input}">
                <div class="input-group" id="keydate9-input-group" data-target-input="nearest">
                  <form:input path="keydate9" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate9-input-group" />
                  <div class="input-group-append" data-target="#keydate9-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate9" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate9__hidden}">
                <form:hidden path="keydate9" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate9__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate9.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.keydate10__input || fieldState.keydate10__view}">
                <form:label path="keydate10">キー日付10</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.keydate10__input}">
                <div class="input-group" id="keydate10-input-group" data-target-input="nearest">
                  <form:input path="keydate10" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#keydate10-input-group" />
                  <div class="input-group-append" data-target="#keydate10-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="keydate10" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.keydate10__hidden}">
                <form:hidden path="keydate10" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.keydate10__view}">
                <div class="form-control form-control__view">
                  ${f:h(pageIdx.keydate10.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
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
