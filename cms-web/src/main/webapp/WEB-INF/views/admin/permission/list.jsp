<section class="content-header">
  <div class="container-fluid px-5">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>パーミッション</h4>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container-fluid px-5">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <div class="text-right">
      <!-- ページタイトル右の余白 -->
      <a href="${f:h(pageContext.request.contextPath)}/admin/account/list" class="badge badge-secondary">ユーザ</a>
      <a href="${f:h(pageContext.request.contextPath)}/admin/role/list" class="badge badge-secondary">ロール</a>
    </div>

    <div>
      <p>ロールとパーミッションの組み合わせを設定します。<br>
      パーミッション自身の登録はこの画面から行えません。パーミッションEnum(jp.co.stnet.cms.domain.model.authentication.Permission)を編集してください。</p>
    </div>


    <form:form modelAttribute="permissionForm" autocomplete="off">
      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <div class="row mb-2">
        <div class="col-36 text-right">
          <input type="submit" value="保存" class="btn-button" />
        </div>
      </div>

      <table class="table-sm table-hover table-striped">
        <thead>
          <th>カテゴリ</th>
          <th>パーミッション(コード)</th>
          <c:forEach var="role" items="${roleList}" varStatus="status">
            <th class="text-center">${role.label}<br>(${role.codeValue})</th>
          </c:forEach>
        </thead>
        <tbody>
          <c:forEach var="obj" items="${permissionList}" varStatus="status">
            <tr>
              <td>${obj.category}</td>
              <td>${obj.label}(${obj.codeValue})</td>
              <c:forEach var="role" items="${roleList}" varStatus="status">
                <td class="text-center align-middle">
                  <form:checkbox path="permissionRoles[${obj.codeValue}][${role.codeValue}]" cssClass="align-middle" />
                  <c:if test="${permissionForm.permissionRoles[obj.codeValue][role.codeValue]}">[✓]</c:if>
                  <c:if test="${!permissionForm.permissionRoles[obj.codeValue][role.codeValue]}">[　]</c:if>
                </td>
              </c:forEach>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="row">
        <div class="col-36 text-right">
          <input type="submit" value="保存" class="btn-button" />
        </div>
      </div>


    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>