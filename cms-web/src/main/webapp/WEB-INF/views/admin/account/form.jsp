<section class="content-header">
  <div class="container">
    <div class="row mb-2 border-bottom">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>アカウント新規登録</h4>
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

    <form:form modelAttribute="accountForm" enctype="multipart/form-data" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />
      <!-- 隠しフィールド -->

      <div class="row mb-3">
        <div class="col-4">
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
      </div>
      <div class="row mb-3">
        <div class="col-6">
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

        <div class="col-6">
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
        <div class="col-8">
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

      <div class="row mb-3">
        <div class="col-8">
          <!-- ラベル -->
          <c:if test="${fieldState.url__input || fieldState.url__view}">
            <form:label path="url">URL*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.url__input}">
            <form:input path="url" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.url__disabled}" readonly="${fieldState.url__readonly}" />
            <form:errors path="url" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.url__hidden}">
            <form:hidden path="url" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.url__view}">
            <div class="form-control form-control__view">
              ${f:h(account.url)}
            </div>
          </c:if>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-8">
          <!-- ラベル -->
          <c:if test="${fieldState.password__input || fieldState.password__view}">
            <form:label path="password">パスワード</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.password__input}">
            <form:input type="password" path="password" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.password__disabled}" readonly="${fieldState.password__readonly}" />
            新規は必須、変更は必要な場合のみ
            <form:errors path="password" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.password__hidden}">
            <!-- パスワードは隠しフィールドの要件なし -->
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.password__view}">
            <!-- パスワードは表示要件なし -->
          </c:if>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.profile__input || fieldState.profile__view}">
            <form:label path="profile">プロフィール*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.profile__input}">
            <form:textarea path="profile" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.profile__disabled}" readonly="${fieldState.profile__readonly}" />
            <form:errors path="profile" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.profile__hidden}">
            <form:hidden path="profile" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.profile__view}">
            <div class="form-control form-control__view">
              ${f:h(account.profile)}
            </div>
          </c:if>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.image__input || fieldState.image__view}">
            <form:label path="image">画像*</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.image__input}">
            <input id="image" type="file" class="form-control form-control-file file-managed"
              　data-file-type="fileupload" data-extention-pattern="png jpg gif"
                <c:if test="${accountForm.imageUuid != null}">style="display: none;"</c:if> />
            <form:errors path="imageUuid" cssClass="invalid-feedback" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.image__view}">
          </c:if>
        </div>
        <c:if test="${imageFileManaged != null}">
          <div id="image__attached-block" class="input-group">
            <span>
              <i class="far fa-file ml-2"></i>
              <a href="${pageContext.request.contextPath}/admin/account/${imageFileManaged.uuid}/download" target="_blank"
                class="link-attached">${imageFileManaged.originalFilename}</a>
              <c:if test="${fieldState.image__input && !fieldState.image__readonly}">
                <i class="far fa-trash-alt" style="color: brown;" onclick="file_detach('image')"></i>
              </c:if>
              <form:input type="hidden" path="imageUuid" />
            </span>
          </div>
        </c:if>
      </div>

      <div class="row mb-3">
        <div class="col-12">
          <!-- ステータスは新規登録時非表示、それ以外は常に表示のみ -->
          <!-- ラベル -->
          <c:if test="${fieldState.status__view}">
            <label>ステータス</label>
            <div class="">
              <c:if test="${account.status == 1}">有効</c:if>
              <c:if test="${account.status == 2}">無効</c:if>
              <c:if test="${isLocked}">(ロック)</c:if>
            </div>
          </c:if>
        </div>
      </div>

      <br />

      <div class="col-36">

        <!-- 一覧に戻る -->
        <c:if test="${buttonState.gotoList__view}">
          <a id="gotoList" href="${f:h(pageContext.request.contextPath)}/admin/account/list" class="btn btn-button mr-2" <c:if
            test="${buttonState.gotoList__disabled}">disabled</c:if> >一覧に戻る</a>
        </c:if>

        <!-- 参照 -->
        <c:if test="${buttonState.view__view}">
          <a id="view" href="${f:h(pageContext.request.contextPath)}/admin/account/${account.username}"
            class="btn btn-button mr-2" <c:if
            test="${buttonState.view__disabled}">disabled</c:if> >参照</a>
        </c:if>

        <!-- 編集 -->
        <c:if test="${buttonState.gotoUpdate__view}">
          <a id="gotoUpdate"
            href="${f:h(pageContext.request.contextPath)}/admin/account/${account.username}/update?form"
            class="btn btn-button mr-2" <c:if
            test="${buttonState.gotoUpdate__disabled}">disabled</c:if> >編集</a>
        </c:if>

        <!-- 保存 -->
        <c:if test="${buttonState.save__view}">
          <button id="save" name="save" type="submit" class="btn btn-button mr-2" <c:if
            test="${buttonState.save__disabled}">disabled</c:if>>保存</button>
        </c:if>

        <!-- 無効化 -->
        <c:if test="${buttonState.invalid__view}">
          <a id="invalid" href="${f:h(pageContext.request.contextPath)}/admin/account/${account.username}/invalid"
            class="btn btn-button mr-2" <c:if
            test="${buttonState.invalid__disabled}">disabled</c:if> >無効化</a>
        </c:if>

        <!-- 削除 -->
        <c:if test="${buttonState.delete__view}">
          <a id="delete" href="${f:h(pageContext.request.contextPath)}/admin/account/${account.username}/delete"
            class="btn btn-button mr-2" <c:if
            test="${buttonState.delete__disabled}">disabled</c:if> >削除(復元不能)</a>
        </c:if>

        <!-- ロック解除 -->
        <c:if test="${buttonState.unlock__view && isLocked}">
          <a id="unlock" href="${f:h(pageContext.request.contextPath)}/admin/account/${account.username}/unlock"
            class="btn btn-button mr-2" <c:if test="${buttonState.unlock__disabled}">disabled</c:if> >ロック解除</a>
        </c:if>
      </div>

    </form:form>
    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
