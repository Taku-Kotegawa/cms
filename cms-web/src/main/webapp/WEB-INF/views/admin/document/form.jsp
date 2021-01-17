<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>帳票ファイル編集</h4>
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
    <form:form modelAttribute="documentForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-12">

          <c:set var="id" value="${document.id}" />

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
                  ${f:h(CL_STATUS[document.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.title__input || fieldState.title__view}">
                <form:label path="title">ドキュメント名</form:label>
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
                  ${f:h(document.title)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.report__input || fieldState.report__view}">
                <form:label path="report">レポートコード</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.report__input}">

                <form:select path="report" cssClass="form-control select2"
                  cssErrorClass="form-control select2 is-invalid">
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_REPORT}" />
                </form:select>

                <form:errors path="report" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.report__hidden}">
                <form:hidden path="report" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.report__view}">
                <div class="form-control form-control__view">
                  ${f:h(CL_REPORT[document.report])}
                </div>
              </c:if>
            </div>
          </div>          

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.shopCode__input || fieldState.shopCode__view}">
                <form:label path="shopCode">店所コード</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.shopCode__input}">
                <!-- <form:input path="shopCode" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.shopCode__disabled}" readonly="${fieldState.shopCode__readonly}" /> -->

                 <form:select path="shopCode" cssClass="form-control select2"
                  cssErrorClass="form-control select2 is-invalid">
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_SHOP}" />
                </form:select>

                <form:errors path="shopCode" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.shopCode__hidden}">
                <form:hidden path="shopCode" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.shopCode__view}">
                <div class="form-control form-control__view">
                  ${f:h(CL_SHOP[document.shopCode])}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.year__input || fieldState.year__view}">
                <form:label path="year">年度</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.year__input}">
                <form:input path="year" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.year__disabled}" readonly="${fieldState.year__readonly}" />
                <form:errors path="year" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.year__hidden}">
                <form:hidden path="year" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.year__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.year)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.period__input || fieldState.period__view}">
                <form:label path="period">集計期間</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.period__input}">
                <form:input path="period" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.period__disabled}" readonly="${fieldState.period__readonly}" />
                <form:errors path="period" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.period__hidden}">
                <form:hidden path="period" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.period__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.period)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.hatsujun__input || fieldState.hatsujun__view}">
                <form:label path="hatsujun">発順</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.hatsujun__input}">
                <form:input path="hatsujun" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.hatsujun__disabled}" readonly="${fieldState.hatsujun__readonly}" />
                <form:errors path="hatsujun" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.hatsujun__hidden}">
                <form:hidden path="hatsujun" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.hatsujun__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.hatsujun)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.customerNumberFrom__input || fieldState.customerNumberFrom__view}">
                <form:label path="customerNumberFrom">お客さま番号(自)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.customerNumberFrom__input}">
                <form:input path="customerNumberFrom" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.customerNumberFrom__disabled}" readonly="${fieldState.customerNumberFrom__readonly}" />
                <form:errors path="customerNumberFrom" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.customerNumberFrom__hidden}">
                <form:hidden path="customerNumberFrom" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.customerNumberFrom__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.customerNumberFrom)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.customerNumberTo__input || fieldState.customerNumberTo__view}">
                <form:label path="customerNumberTo">お客さま番号(至)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.customerNumberTo__input}">
                <form:input path="customerNumberTo" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.customerNumberTo__disabled}" readonly="${fieldState.customerNumberTo__readonly}" />
                <form:errors path="customerNumberTo" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.customerNumberTo__hidden}">
                <form:hidden path="customerNumberTo" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.customerNumberTo__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.customerNumberTo)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.expirationDate__input || fieldState.expirationDate__view}">
                <form:label path="expirationDate">有効期限</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.expirationDate__input}">
                <div class="input-group" id="expirationDate-input-group" data-target-input="nearest">
                  <form:input path="expirationDate" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#expirationDate-input-group" />
                  <div class="input-group-append" data-target="#expirationDate-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="expirationDate" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.expirationDate__hidden}">
                <form:hidden path="expirationDate" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.expirationDate__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.expirationDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.outputDate__input || fieldState.outputDate__view}">
                <form:label path="outputDate">帳票出力日時</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.outputDate__input}">
                <div class="input-group datetime" id="outputDate-input-group" data-target-input="nearest">
                  <form:input path="outputDate" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid"
                    data-target="#outputDate-input-group" />
                  <div class="input-group-append" data-target="#outputDate-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="outputDate" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.outputDate__hidden}">
                <form:hidden path="outputDate" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.outputDate__view}">
                <div class="form-control form-control__view">
                  ${f:h(document.outputDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))}
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
                <input id="attachedFile" type="file" class="form-control form-control-file file-managed"
                  data-file-type="document" data-extention-pattern="png jpg gif" <c:if
                  test="${documentForm.attachedFileUuid != null}">style="display: none;"</c:if> />
                <form:errors path="attachedFileUuid" cssClass="invalid-feedback" />
                <c:if test="${documentForm.attachedFileManaged != null}">
                  <div id="attachedFile__attached-block" class="input-group">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(documentForm.attachedFileUuid)}"
                        target="_blank"
                        class="link-attached">${documentForm.attachedFileManaged.originalFilename}</a>
                      <c:if test="${fieldState.attachedFileUuid__input && !fieldState.attachedFileUuid__readonly}">
                        <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('attachedFile')"></i>
                      </c:if>
                      <form:input type="hidden" path="attachedFileUuid" />
                    </span>
                  </div>
                </c:if>
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.attachedFileUuid__view}">
                <div>
                  <c:if test="${document.attachedFileManaged != null}">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(document.attachedFileManaged.uuid)}"
                        target="_blank" class="link-attached">${document.attachedFileManaged.originalFilename}</a>
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
