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
    <form:form modelAttribute="staffForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />
      <!-- 隠しフィールド -->
      <form:hidden path="id" />
      <form:hidden path="version" />
      <!-- 必要に応じて隠しフィールドを追加する -->

      <!-- ここにメインコンテンツを追加 -->

    <!-- 操作ボタンを追加(メインコンテンツの中に追加する様に) -->
      <div class="row operation-button-block">

        <!-- 右寄せに配置 -->
        <div class="col-36 text-right">

          <!-- 一覧に戻る -->
          <c:if test="${buttonState.gotoList__view}">
            <a id="gotoList" href="list" class="btn btn-button mr-2"
            <c:if test="${buttonState.gotoList__disabled}">disabled</c:if> >一覧に戻る</a>
          </c:if>

          <!-- 参照 -->
          <c:if test="${buttonState.view__view}">
            <a id="view" href="${account.username}" class="btn btn-button mr-2"
            <c:if test="${buttonState.view__disabled}">disabled</c:if> >参照</a>
          </c:if>

          <!-- 編集 -->
          <c:if test="${buttonState.gotoUpdate__view}">
            <a id="gotoUpdate" href="${account.username}/update?form" class="btn btn-button mr-2"
            <c:if test="${buttonState.gotoUpdate__disabled}">disabled</c:if> >編集</a>
          </c:if>

          <!-- 保存 -->
          <c:if test="${buttonState.save__view}">
            <button id="save" name="save" type="submit" class="btn btn-button mr-2"
            <c:if test="${buttonState.save__disabled}">disabled</c:if>>保存</button>
          </c:if>

          <!-- 無効化 -->
          <c:if test="${buttonState.invalid__view}">
            <a id="invalid" href="${account.username}/invalid" class="btn btn-button mr-2"
            <c:if test="${buttonState.invalid__disabled}">disabled</c:if> >無効化</a>
          </c:if>

          <!-- 削除 -->
          <c:if test="${buttonState.delete__view}">
            <a id="delete" href="${account.username}/delete" class="btn btn-button mr-2"
            <c:if test="${buttonState.delete__disabled}">disabled</c:if> >削除(復元不能)</a>
          </c:if>

        </div>
      </div>

      <hr />

      <div class="row mb-3">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.username__input || fieldState.username__view}">
            <form:label path="username">ユーザ名*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.username__input}">
            <form:input path="username" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.username__disabled}" readonly="${fieldState.username__readonly}" />
            <form:errors path="username" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.username__hidden}">
            <form:hidden path="username" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.username__view}">
            <div class="form-control form-control__view">
              ${f:h(account.username)}
            </div>
          </c:if>
        </div>
        
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.firstName__input || fieldState.firstName__view}">
            <form:label path="firstName">姓*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.firstName__input}">
            <form:input path="firstName" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.firstName__disabled}" readonly="${fieldState.firstName__readonly}" />
            <form:errors path="firstName" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.firstName__hidden}">
            <form:hidden path="firstName" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.firstName__view}">
            <div class="form-control form-control__view">
              ${f:h(account.firstName)}
            </div>
          </c:if>
        </div>        
        
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.lastName__input || fieldState.lastName__view}">
            <form:label path="lastName">名*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.lastName__input}">
            <form:input path="lastName" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.lastName__disabled}" readonly="${fieldState.lastName__readonly}" />
            <form:errors path="lastName" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.lastName__hidden}">
            <form:hidden path="lastName" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.lastName__view}">
            <div class="form-control form-control__view">
              ${f:h(account.lastName)}
            </div>
          </c:if>
        </div>   
      </div>
        

      <div class="row mb-3">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.email__input || fieldState.email__view}">
            <form:label path="email">E-Mail*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.email__input}">
            <form:input path="email" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.email__disabled}" readonly="${fieldState.email__readonly}" />
            <form:errors path="email" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.email__hidden}">
            <form:hidden path="email" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.email__view}">
            <div class="form-control form-control__view">
              ${f:h(account.email)}
            </div>
          </c:if>
        </div>   



      </div>



    </form:form>



    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

