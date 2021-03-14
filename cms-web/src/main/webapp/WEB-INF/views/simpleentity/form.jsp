<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Simple Entity</h4>
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
    <form:form modelAttribute="simpleEntityForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />
      <!-- 隠しフィールド -->
      <form:hidden path="id" />
      <form:hidden path="version" />
      <form:hidden path="status" />
      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- ここにメインコンテンツを追加 -->

      <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-24">

          <c:set var="id" value="${simpleEntity.id}" />

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

          <!-- 複製 -->
          <c:if test="${buttonState.copy__view}">
            <a id="copy" href="${pageContext.request.contextPath}${op.getCopyUrl(id)}" class="btn btn-button mr-2" <c:if
              test="${buttonState.copy__disabled}">disabled</c:if> >${op.getLABEL_COPY()}</a>
          </c:if>

        </div>
      </div>

      <hr />

      <div class="row">
        <div class="col-18">

          <c:if test="${fieldState.status__view}">
            <div class="row mb-3">
              <div class="col-24">
                <!-- ラベル -->
                <form:label path="text01">ステータス</form:label>
                <div class="form-control__view">
                  ${f:h(CL_STATUS[simpleEntity.status])}
                </div>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.text01__input || fieldState.text01__view}">
                <form:label path="text01">テキストフィールド</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.text01__input}">
                <form:input path="text01" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.text01__disabled}" readonly="${fieldState.text01__readonly}" />
                <form:errors path="text01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.text01__hidden}">
                <form:hidden path="text01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.text01__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.text01)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.text02__input || fieldState.text02__view}">
                <form:label path="text02">テキストフィールド(数値・整数)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.text02__input}">
                <form:input path="text02" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.text02__disabled}" readonly="${fieldState.text02__readonly}" />
                <form:errors path="text02" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.text02__hidden}">
                <form:hidden path="text02" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.text02__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.text02)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.text03__input || fieldState.text03__view}">
                <form:label path="text03">テキストフィールド(数値・小数あり)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.text03__input}">
                <form:input path="text03" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.text03__disabled}" readonly="${fieldState.text03__readonly}" />
                <form:errors path="text03" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.text03__hidden}">
                <form:hidden path="text03" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.text03__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.text03)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.text04__input || fieldState.text04__view}">
                <form:label path="text04">テキストフィールド(真偽値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.text04__input}">
                <form:input path="text04" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.text04__disabled}" readonly="${fieldState.text04__readonly}" />
                <form:errors path="text04" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.text04__hidden}">
                <form:hidden path="text04" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.text04__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.text04)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.text05__input || fieldState.text05__view}">
                <form:label path="text05">テキストフィールド(複数の値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.text05__input}">
                <form:input path="text05" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  disabled="${fieldState.text05__disabled}" readonly="${fieldState.text05__readonly}" />
                <form:errors path="text05" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.text05__hidden}">
                <form:hidden path="text05" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.text05__view}">
                <div class="form-control form-control__view">
                  <c:forEach var="item" items="${simpleEntity.text05}" varStatus="status">
                    <span>${f:h(item)}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.radio01__input || fieldState.radio01__view}">
                <form:label path="radio01">ラジオボタン(真偽値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.radio01__input}">
                <div class="form-check-inline" style="width:100%">
                  <form:radiobutton path="radio01" cssClass="" cssErrorClass="is-invalid" value="true" />
                  <form:label path="radio01" for="radio011">はい</form:label>
                  <form:radiobutton path="radio01" cssClass="" cssErrorClass="is-invalid" value="false" />
                  <form:label path="radio01" for="radio012">いいえ</form:label>
                </div>
                <form:errors path="radio01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.radio01__hidden}">
                <form:hidden path="radio01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.radio01__view}">
                <div class="form-control form-control__view">
                  <c:if test="${simpleEntity.radio01}">[◯]はい [　]いいえ</c:if>
                  <c:if test="${!simpleEntity.radio01}">[　]はい [◯]いいえ</c:if>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.radio02__input || fieldState.radio02__view}">
                <form:label path="radio02">ラジオボタン(文字列)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.radio02__input}">
                <div class="form-check-inline" style="width:100%">
                  <form:radiobutton path="radio02" cssClass="" cssErrorClass="is-invalid" value="はい" />
                  <form:label path="radio02" for="radio021">はい</form:label>
                  <form:radiobutton path="radio02" cssClass="" cssErrorClass="is-invalid" value="いいえ" />
                  <form:label path="radio02" for="radio022">いいえ</form:label>
                </div>
                <form:errors path="radio02" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.radio02__hidden}">
                <form:hidden path="radio02" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.radio02__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.radio02)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.checkbox01__input || fieldState.checkbox01__view}">
                <form:label path="checkbox01">チェックボックス(文字列)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.checkbox01__input}">
                <div class="form-check-inline" style="width:100%">
                  <form:checkbox path="checkbox01" cssClass="" cssErrorClass="is-invalid" value="yes" />
                  <form:label path="checkbox01" for="checkbox011">利用規約に合意する</form:label>
                </div>
                <form:errors path="checkbox01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.checkbox01__hidden}">
                <form:hidden path="checkbox01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.checkbox01__view}">
                <div class="form-control form-control__view">
                  <c:if test="${simpleEntity.checkbox01 == 'yes'}">[レ]</c:if>
                  <c:if test="${simpleEntity.checkbox01 != 'yes'}">[　]</c:if>
                  利用規約に合意する
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.checkbox02__input || fieldState.checkbox02__view}">
                <form:label path="checkbox02">チェックボックス(複数の値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.checkbox02__input}">
                <div class="form-check-inline" style="width:100%">
                  <form:checkboxes path="checkbox02" cssClass="" cssErrorClass="is-invalid" items="${CL_STATUS}" />
                </div>
                <form:errors path="checkbox02" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.checkbox02__hidden}">
                <form:hidden path="checkbox02" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.checkbox02__view}">
                <div class="form-control form-control__view">
                  <!-- <c:forEach var="item" items="${simpleEntity.select02}" varStatus="status">
                <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
              </c:forEach> -->
                  <c:forEach var="item" items="${CL_STATUS}" varStatus="status">
                    <span>
                      <c:if test="${simpleEntity.checkbox02.contains(item.key)}">[レ]</c:if>
                      <c:if test="${!simpleEntity.checkbox02.contains(item.key)}">[　]</c:if>
                      ${item.value}
                    </span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.textarea01__input || fieldState.textarea01__view}">
                <form:label path="textarea01">テキストエリア</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.textarea01__input}">
                <form:textarea path="textarea01" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  rows="5" />
                <form:errors path="textarea01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.textarea01__hidden}">
                <form:hidden path="textarea01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.textarea01__view}">
                <div class="">
                  ${f:br(f:h(simpleEntity.textarea01))}
                </div>
              </c:if>
            </div>
          </div>

        </div>
        <div class="col-18">

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.date01__input || fieldState.date01__view}">
                <form:label path="date01">日付</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.date01__input}">
                <div class="input-group" id="date01-input-group" data-target-input="nearest">
                  <form:input path="date01" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#date01-input-group" />
                  <div class="input-group-append" data-target="#date01-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="date01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.date01__hidden}">
                <form:hidden path="date01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.date01__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.date01.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.datetime01__input || fieldState.datetime01__view}">
                <form:label path="datetime01">日付時刻</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.datetime01__input}">
                <div class="input-group datetime" id="datetime01-input-group" data-target-input="nearest">
                  <form:input path="datetime01" cssClass="form-control datetimepicker-input"
                    cssErrorClass="form-control datetimepicker-input is-invalid"
                    data-target="#datetime01-input-group" />
                  <div class="input-group-append" data-target="#datetime01-input-group" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                  </div>
                </div>
                <form:errors path="datetime01" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.datetime01__hidden}">
                <form:hidden path="datetime01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.datetime01__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.datetime01.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.select01__input || fieldState.select01__view}">
                <form:label path="select01">select</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select01__input}">
                <form:select path="select01" cssClass="form-control" cssErrorClass="form-control is-invalid">
                  <form:option value="" label="--Select--" />
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
                  ${f:h(CL_STATUS[simpleEntity.select01])}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.select02__input || fieldState.select02__view}">
                <form:label path="select02">select(複数の値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select02__input}">
                <form:select path="select02" multiple="true" cssClass="form-control"
                  cssErrorClass="form-control is-invalid">
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
                  <c:forEach var="item" items="${simpleEntity.select02}" varStatus="status">
                    <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.select03__input || fieldState.select03__view}">
                <form:label path="select03">select2</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select03__input}">
                <form:select path="select03" cssClass="form-control select2"
                  cssErrorClass="form-control select2 is-invalid">
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_STATUS}" />
                </form:select>
                <form:errors path="select03" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.select03__hidden}">
                <form:hidden path="select03" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.select03__view}">
                <div class="form-control form-control__view">
                  ${f:h(CL_STATUS[simpleEntity.select03])}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.select04__input || fieldState.select04__view}">
                <form:label path="select04">select2(複数の値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.select04__input}">
                <form:select path="select04" multiple="true" cssClass="form-control select2"
                  cssErrorClass="form-control select2 is-invalid">
                  <form:options items="${CL_STATUS}" />
                </form:select>
                <form:errors path="select04" cssClass="invalid-feedback" />
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.select04__hidden}">
                <form:hidden path="select04" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.select04__view}">
                <div class="form-control form-control__view">
                  <c:forEach var="item" items="${simpleEntity.select04}" varStatus="status">
                    <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.combobox01__input || fieldState.combobox01__view}">
                <form:label path="combobox01">コンボボックス</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.combobox01__input}">
                <div class="dropdown input-group">
                  <form:input path="combobox01" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                  <div class="input-group-append" data-toggle="dropdown">
                    <div class="input-group-text"><i class="fas fa-angle-down"></i></div>
                  </div>
                  <ul class="dropdown-menu dropdown-menu-right combobox" aria-labelledby="dropdownMenuButton">
                    <input class="form-control dropdown-menu-input" id="myInput" type="text" placeholder="Filter..">
                    <c:forEach var="item" items="${CL_STATUS}" varStatus="status">
                      <li class="autocomplete" data-autocomplete="${item.value}" data-target="combobox01">${item.value}
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.combobox01__hidden}">
                <form:hidden path="combobox01" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.combobox01__view}">
                <div class="form-control form-control__view">
                  ${f:h(simpleEntity.combobox01)}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.combobox02__input || fieldState.combobox02__view}">
                <form:label path="combobox02">コンボボックス(Select2-tags)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.combobox02__input}">
                <form:select path="combobox02" cssClass="select2-tags form-control"
                  cssErrorClass="select2-tags form-control is-invalid">
                  <form:option value="" label="--Select--" />
                  <form:options items="${CL_STATUS}" />
                </form:select>
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.combobox02__hidden}">
                <form:hidden path="combobox02" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.combobox02__view}">
                <div class="form-control form-control__view">
                  ${f:h(CL_STATUS[simpleEntity.combobox02])}
                </div>
              </c:if>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.combobox03__input || fieldState.combobox03__view}">
                <form:label path="combobox03">コンボボックス(Select2-tags 複数の値)</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.combobox03__input}">
                <form:select multiple="true" path="combobox03" cssClass="select2-tags form-control"
                  cssErrorClass="select2-tags form-control is-invalid">
                  <form:options items="${CL_STATUS}" />
                </form:select>
              </c:if>
              <!-- 隠しフィールド-->
              <c:if test="${fieldState.combobox03__hidden}">
                <form:hidden path="combobox03" />
              </c:if>
              <!-- 参照用-->
              <c:if test="${fieldState.combobox03__view}">
                <div class="form-control form-control__view">
                  <c:forEach var="item" items="${simpleEntity.combobox03}" varStatus="status">
                    <span>${f:h(CL_STATUS[item])}<c:if test="${!status.last}">,</c:if></span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>


          <div class="row mb-3">
            <div class="col-24">
              <!-- ラベル -->
              <c:if test="${fieldState.attachedFile01Uuid__input || fieldState.attachedFile01Uuid__view}">
                <form:label path="attachedFile01Uuid">画像*</form:label>
              </c:if>
              <!-- 入力 -->
              <c:if test="${fieldState.attachedFile01Uuid__input}">
                <input id="attachedFile01" type="file" class="form-control form-control-file file-managed"
                  data-file-type="simpleentity" <c:if
                  test="${simpleEntityForm.attachedFile01Uuid != null}">style="display: none;"</c:if> />
              <form:errors path="attachedFile01Uuid" cssClass="invalid-feedback" />
              <c:if test="${simpleEntityForm.attachedFile01Managed != null}">
                <div id="attachedFile01__attached-block" class="input-group">
                  <span>
                    <i class="far fa-file ml-2"></i>
                    <a href="${pageContext.request.contextPath}${op.getDownloadUrl(simpleEntityForm.attachedFile01Uuid)}"
                      target="_blank"
                      class="link-attached">${simpleEntityForm.attachedFile01Managed.originalFilename}</a>
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
                  <c:if test="${simpleEntity.attachedFile01Managed != null}">
                    <span>
                      <i class="far fa-file ml-2"></i>
                      <a href="${pageContext.request.contextPath}${op.getDownloadUrl(simpleEntity.attachedFile01Managed.uuid)}"
                        target="_blank" class="link-attached">${simpleEntity.attachedFile01Managed.originalFilename}</a>
                    </span>
                  </c:if>
                </div>
              </c:if>
            </div>
          </div>


        </div>
      </div>

      <br>
      <div class="row">
        <div class="col-30">

          <table id="lineItemTable" class="table-sm">
            <thead>
              <tr>
                <td class="row">
                  <div class="col-18">商品名</div>
                  <div class="col-6">単価</div>
                  <div class="col-9">数量</div>
                  <div class="col-3 text-center">削除</div>
                </td>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${simpleEntityForm.lineItems}" varStatus="status">
                <tr>
                  <td class="row">
                    <div class="col-18">
                      <form:input path="lineItems[${status.index}].itemName" class="form-control" />
                    </div>
                    <div class="col-6">
                      <form:input type="number" path="lineItems[${status.index}].unitPrise" class="form-control" />
                    </div>
                    <div class="col-9">
                      <form:input type="number" path="lineItems[${status.index}].itemNumber" class="form-control" />
                    </div>
                    <div class="col-3 text-center">
                      <button type="button" class="btn-button" onclick="lineDelete(this)">削除</button>
                    </div>

                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <div class="row">
            <div class="col-36">
              <button id="addLinebutton" formaction="#addLinebutton" name="addlineitem" class="btn-button">行追加(POST)</button>
              <button type="button" class="btn-button" onclick="addLine('lineItemTable')">行追加(JavaScript)</button>
            </div>
          </div>

        </div>
      </div>

    </form:form>

    <br>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
<script src="${pageContext.request.contextPath}/resources/app/js/form-onload-default.js"></script>

<script>
  function lineDelete(e) {

    let table = e.closest('table')
    if (table.rows.length == 2) {
      alert('全ての行は削除できません。');
      return false;
    }
    let tr = e.closest('tr').remove();
  }

  function addLine(table_id) {
    let table = document.getElementById(table_id);
    let newRowIdx = table.rows.length - 1;
    let firstRow = table.rows[1];
    let newRow = firstRow.cloneNode(true);
    let replaceRowHTML = newRow.innerHTML.replace(/lineItems\[0\]/g, 'lineItems[' + newRowIdx + ']');
    replaceRowHTML = replaceRowHTML.replace(/lineItems0/g, 'lineItems' + newRowIdx);
    replaceRowHTML = replaceRowHTML.replace(/value=".*"/g, '');
    let row = table.insertRow(-1);
    row.innerHTML = replaceRowHTML;  
  }
</script>
