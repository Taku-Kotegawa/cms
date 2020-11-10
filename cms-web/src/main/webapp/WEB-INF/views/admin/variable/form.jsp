<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>変数編集</h4>
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
    <form:form modelAttribute="variableForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 左寄せに配置 -->
        <div class="col-18">

          <c:set var="id" value="${variable.id}" />

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

          <!-- 無効解除 -->
          <c:if test="${buttonState.valid__view}">
            <a id="delete" href="${pageContext.request.contextPath}${op.getValidUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.valid__disabled}">disabled</c:if> >${op.getLABEL_VALID()}</a>
          </c:if>
        </div>
        <!-- 左寄せに配置 -->
        <div class="col-18 text-right">
          <!-- 無効 -->
          <c:if test="${buttonState.invalid__view}">
            <a id="delete" href="${pageContext.request.contextPath}${op.getInvalidUrl(id)}" class="btn btn-button mr-2"
              <c:if test="${buttonState.invalid__disabled}">disabled</c:if> >${op.getLABEL_INVALID()}</a>
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
                  ${f:h(CL_STATUS[variable.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-6">
              <!-- ラベル -->
              <c:if test="${fieldState.type__input || fieldState.type__view}">
                <form:label path="type">タイプ</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.type__input}">
                <form:select path="type" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.type__disabled}" >
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_VARIABLETYPE}" />
                </form:select>
                <form:errors path="type" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.type__hidden}">
                <form:hidden path="type" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.type__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.type)}
                </div>
              </c:if>
            </div>

            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.code__input || fieldState.code__view}">
                <form:label path="code">コード</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.code__input}">
                <form:input path="code" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.code__disabled}" readonly="${fieldState.code__readonly}" />
                <form:errors path="code" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.code__hidden}">
                <form:hidden path="code" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.code__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.code)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value1__input || fieldState.value1__view}">
                <form:label path="value1">${fieldLabel.value1}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value1__input}">
                <form:input path="value1" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value1__disabled}" readonly="${fieldState.value1__readonly}" />
                <form:errors path="value1" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value1__hidden}">
                <form:hidden path="value1" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value1__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value1)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value2__input || fieldState.value2__view}">
                <form:label path="value2">${fieldLabel.value2}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value2__input}">
                <form:input path="value2" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value2__disabled}" readonly="${fieldState.value2__readonly}" />
                <form:errors path="value2" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value2__hidden}">
                <form:hidden path="value2" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value2__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value2)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value3__input || fieldState.value3__view}">
                <form:label path="value3">${fieldLabel.value3}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value3__input}">
                <form:input path="value3" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value3__disabled}" readonly="${fieldState.value3__readonly}" />
                <form:errors path="value3" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value3__hidden}">
                <form:hidden path="value3" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value3__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value3)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value4__input || fieldState.value4__view}">
                <form:label path="value4">${fieldLabel.value4}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value4__input}">
                <form:input path="value4" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value4__disabled}" readonly="${fieldState.value4__readonly}" />
                <form:errors path="value4" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value4__hidden}">
                <form:hidden path="value4" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value4__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value4)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value5__input || fieldState.value5__view}">
                <form:label path="value5">${fieldLabel.value5}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value5__input}">
                <form:input path="value5" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value5__disabled}" readonly="${fieldState.value5__readonly}" />
                <form:errors path="value5" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value5__hidden}">
                <form:hidden path="value5" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value5__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value5)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value6__input || fieldState.value6__view}">
                <form:label path="value6">${fieldLabel.value6}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value6__input}">
                <form:input path="value6" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value6__disabled}" readonly="${fieldState.value6__readonly}" />
                <form:errors path="value6" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value6__hidden}">
                <form:hidden path="value6" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value6__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value6)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value7__input || fieldState.value7__view}">
                <form:label path="value7">${fieldLabel.value7}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value7__input}">
                <form:input path="value7" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value7__disabled}" readonly="${fieldState.value7__readonly}" />
                <form:errors path="value7" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value7__hidden}">
                <form:hidden path="value7" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value7__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value7)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value8__input || fieldState.value8__view}">
                <form:label path="value8">${fieldLabel.value8}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value8__input}">
                <form:input path="value8" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value8__disabled}" readonly="${fieldState.value8__readonly}" />
                <form:errors path="value8" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value8__hidden}">
                <form:hidden path="value8" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value8__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value8)}
                </div>
              </c:if>
            </div>
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value9__input || fieldState.value9__view}">
                <form:label path="value9">${fieldLabel.value9}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value9__input}">
                <form:input path="value9" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value9__disabled}" readonly="${fieldState.value9__readonly}" />
                <form:errors path="value9" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value9__hidden}">
                <form:hidden path="value9" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value9__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value9)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.value10__input || fieldState.value10__view}">
                <form:label path="value10">${fieldLabel.value10}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.value10__input}">
                <form:input path="value10" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.value10__disabled}" readonly="${fieldState.value10__readonly}" />
                <form:errors path="value10" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.value10__hidden}">
                <form:hidden path="value10" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.value10__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.value10)}
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.date1__input || fieldState.date1__view}">
                <form:label path="date1">${fieldLabel.date1}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date1__input}">
                <div class="input-group" id="date1-input-group" data-target-input="nearest">
                  <form:input path="date1" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date1-input-group" />
                  <div class="input-group-append" data-target="#date1-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date1" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date1__hidden}">
                <form:hidden path="date1" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date1__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.date1.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.date2__input || fieldState.date2__view}">
                <form:label path="date2">${fieldLabel.date2}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date2__input}">
                <div class="input-group" id="date2-input-group" data-target-input="nearest">
                  <form:input path="date2" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date2-input-group" />
                  <div class="input-group-append" data-target="#date2-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date2" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date2__hidden}">
                <form:hidden path="date2" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date2__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.date2.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.date3__input || fieldState.date3__view}">
                <form:label path="date3">${fieldLabel.date3}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date3__input}">
                <div class="input-group" id="date3-input-group" data-target-input="nearest">
                  <form:input path="date3" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date3-input-group" />
                  <div class="input-group-append" data-target="#date3-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date3" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date3__hidden}">
                <form:hidden path="date3" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date3__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.date3.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.date4__input || fieldState.date4__view}">
                <form:label path="date4">${fieldLabel.date4}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date4__input}">
                <div class="input-group" id="date4-input-group" data-target-input="nearest">
                  <form:input path="date4" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date4-input-group" />
                  <div class="input-group-append" data-target="#date4-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date4" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date4__hidden}">
                <form:hidden path="date4" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date4__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.date4.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.date5__input || fieldState.date5__view}">
                <form:label path="date5">${fieldLabel.date5}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date5__input}">
                <div class="input-group" id="date5-input-group" data-target-input="nearest">
                  <form:input path="date5" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date5-input-group" />
                  <div class="input-group-append" data-target="#date5-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date5" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date5__hidden}">
                <form:hidden path="date5" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date5__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.date5.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.valint1__input || fieldState.valint1__view}">
                <form:label path="valint1">${fieldLabel.valint1}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.valint1__input}">
                <form:input path="valint1" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.valint1__disabled}" readonly="${fieldState.valint1__readonly}" />
                <form:errors path="valint1" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.valint1__hidden}">
                <form:hidden path="valint1" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.valint1__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.valint1)}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.valint2__input || fieldState.valint2__view}">
                <form:label path="valint2">${fieldLabel.valint2}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.valint2__input}">
                <form:input path="valint2" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.valint2__disabled}" readonly="${fieldState.valint2__readonly}" />
                <form:errors path="valint2" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.valint2__hidden}">
                <form:hidden path="valint2" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.valint2__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.valint2)}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.valint3__input || fieldState.valint3__view}">
                <form:label path="valint3">${fieldLabel.valint3}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.valint3__input}">
                <form:input path="valint3" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.valint3__disabled}" readonly="${fieldState.valint3__readonly}" />
                <form:errors path="valint3" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.valint3__hidden}">
                <form:hidden path="valint3" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.valint3__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.valint3)}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.valint4__input || fieldState.valint4__view}">
                <form:label path="valint4">${fieldLabel.valint4}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.valint4__input}">
                <form:input path="valint4" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.valint4__disabled}" readonly="${fieldState.valint4__readonly}" />
                <form:errors path="valint4" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.valint4__hidden}">
                <form:hidden path="valint4" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.valint4__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.valint4)}
                </div>
              </c:if>
            </div>
            <div class="col-7">
              <!-- ラベル -->
              <c:if test="${fieldState.valint5__input || fieldState.valint5__view}">
                <form:label path="valint5">${fieldLabel.valint5}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.valint5__input}">
                <form:input path="valint5" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.valint5__disabled}" readonly="${fieldState.valint5__readonly}" />
                <form:errors path="valint5" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.valint5__hidden}">
                <form:hidden path="valint5" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.valint5__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.valint5)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-36">
              <!-- ラベル -->
              <c:if test="${fieldState.textarea__input || fieldState.textarea__view}">
                <form:label path="textarea">${fieldLabel.textarea}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.textarea__input}">
                <form:textarea path="textarea" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  rows="5" />
                <form:errors path="textarea" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.textarea__hidden}">
                <form:hidden path="textarea" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.textarea__view}">
                <div class="">
                  ${f:br(f:h(variable.textarea))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <!-- ラベル -->
              <c:if test="${fieldState.file1Uuid__input || fieldState.file1Uuid__view}">
                <form:label path="file1Uuid">${fieldLabel.file1}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.file1Uuid__input}">
                <input id="file1" type="file" class="form-control form-control-file file-managed"
                  data-file-type="variable" data-extention-pattern="png jpg gif" <c:if
                  test="${variableForm.file1Uuid != null}">style="display: none;"</c:if> />
              <form:errors path="file1Uuid" cssClass="invalid-feedback" />
              <c:if test="${variableForm.file1Managed != null}">
                <div id="file1__attached-block" class="input-group">
                  <span>
                    <i class="far fa-file ml-2"></i>
                    <a href="${pageContext.request.contextPath}${op.getDownloadUrl(variableForm.file1Uuid)}"
                      target="_blank" class="link-attached">${variableForm.file1Managed.originalFilename}</a>
                    <c:if test="${fieldState.file1Uuid__input && !fieldState.file1Uuid__readonly}">
                      <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('file1')"></i>
                    </c:if>
                    <form:input type="hidden" path="file1Uuid" />
                  </span>
                </div>
              </c:if>
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.file1Uuid__view}">
                <div>
                  <c:if test="${variable.file1Managed != null}">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(variable.file1Managed.uuid)}"
                        target="_blank" class="link-attached">${variable.file1Managed.originalFilename}</a>
                    </span>
                  </c:if>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-36">
              <!-- ラベル -->
              <c:if test="${fieldState.remark__input || fieldState.remark__view}">
                <form:label path="remark">${fieldLabel.remark}</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.remark__input}">
                <form:input path="remark" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.remark__disabled}" readonly="${fieldState.remark__readonly}" />
                <form:errors path="remark" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.remark__hidden}">
                <form:hidden path="remark" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.remark__view}">
                <div class="form-control form-control__view">
                  ${f:h(variable.remark)}
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