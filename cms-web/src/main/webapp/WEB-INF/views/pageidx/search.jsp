<style>
  em {
    color: red;
  }

  .pagination {
    display: inline-block;
    padding-left: 0;
    margin: 20px 0;
    border-radius: 4px;
  }

  .pagination>li {
    display: inline;
  }

  .pagination>li>a,
  .pagination>li>span {
    position: relative;
    float: left;
    padding: 6px 12px;
    margin-left: -1px;
    line-height: 1.428571429;
    text-decoration: none;
    background-color: #ffffff;
    border: 1px solid #dddddd;
  }

  .pagination>li:first-child>a,
  .pagination>li:first-child>span {
    margin-left: 0;
    border-bottom-left-radius: 4px;
    border-top-left-radius: 4px;
  }

  .pagination>li:last-child>a,
  .pagination>li:last-child>span {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
  }

  .pagination>li>a:hover,
  .pagination>li>span:hover,
  .pagination>li>a:focus,
  .pagination>li>span:focus {
    background-color: #eeeeee;
  }

  .pagination>.active>a,
  .pagination>.active>span,
  .pagination>.active>a:hover,
  .pagination>.active>span:hover,
  .pagination>.active>a:focus,
  .pagination>.active>span:focus {
    z-index: 2;
    color: #ffffff;
    cursor: default;
    background-color: #428bca;
    border-color: #428bca;
  }

  .pagination>.disabled>span,
  .pagination>.disabled>a,
  .pagination>.disabled>a:hover,
  .pagination>.disabled>a:focus {
    color: #999999;
    cursor: not-allowed;
    background-color: #ffffff;
    border-color: #dddddd;
  }
</style>


<section class="content">
  <div class="container">

    <div class="py-5">
      <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    </div>

    <!-- ここより下にメインコンテンツを記入 -->
    <form:form modelAttribute="pageIdxCriteriaForm" enctype="multipart/form-data" autocomplete="off" method="get">

      <div class="row">
        <div class="col-28">
          <div>
            <c:if test="${totalHitCount != null}">
              <p>${f:h(totalHitCount)} 件が検索にヒットしました。</p>
            </c:if>
          </div>

          <table class="table-sm table-striped table-hover">
            <thead>
              <tr>
                <th>お客番</th>
                <th>氏名</th>
                <th>店所</th>
                <th>年度</th>
                <th>年月</th>
                <th>発順</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${page.content}" varStatus="status">
                <tr>
                  <td>${item.customerNumber}</td>
                  <td>${item.customerName}</td>
                  <td>${item.document.shop.title}</td>
                  <td>${item.document.year}</td>
                  <td>${item.document.period}</td>
                  <td>${item.document.hatsujun}</td>
                  <td>${item.startPage} / ${item.attachedFileUuid}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <div class="mx-auto text-center">
            <t:pagination page="${page}" outerElementClass="pagination" activeClass="active" disabledClass="disabled"
              queryTmpl="page={page}&size={size}${f:h(query)}" />
            </div>

        </div>
        <div class="col-8 px-4">
          <div class="h-100 border-left px-4">

            <form:label path="customerNumber">お客様番号</form:label>
            <div class="input-group">
              <form:input cssClass="form-control" cssErrorClass="form-control is-invalid" path="customerNumber" />
              <div class="input-group-append">
                <button class="btn btn-button py-0">検索</button>
                <a href="${f:h(pageContext.request.contextPath)}/pageidx/search" class="btn btn-button py-0" style="display: flex; align-items: center;">クリア</a>
              </div>
            </div>
            <form:errors path="customerNumber" cssClass="invalid-feedback" />
            <br>

            <h6 class="border-bottom">店所</h6>
            <c:forEach var="shop" items="${shopList}" varStatus="status">
              <div>
                <form:checkbox path="shopCodes" value="${shop.key}"></form:checkbox>
                <label for="shopCodes${status.index + 1}" class="form-check-label">${shop.value}</label>
              </div>
            </c:forEach>

            <br>
            <div>
              <h6 class="border-bottom">年度</h6>
              <c:forEach var="year" items="${countsByYear}" varStatus="status">
                <div>
                  <form:checkbox path="year" value="${year.key}"></form:checkbox>
                  <label for="year${status.index + 1}" class="form-check-label">${f:h(year.key)} <span
                      class="badge badge-pill badge-secondary">${year.value}</span></label>
                </div>
              </c:forEach>
            </div>

            <br>
            <div>
              <h6 class="border-bottom">年月</h6>
              <c:forEach var="period" items="${countsByPeriod}" varStatus="status">
                <div>
                  <form:checkbox path="period" value="${period.key}"></form:checkbox>
                  <label for="period${status.index + 1}" class="form-check-label">${f:h(period.key)} <span
                      class="badge badge-pill badge-secondary">${period.value}</span></label>
                </div>
              </c:forEach>
            </div>

          </div>
        </div>
      </div>

    </form:form>
    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>


<!-- <script>
  $(document)
    .ready(
      function () {
        $('input[type="checkbox"]').on('change', function () {
          $('form').submit();
        });
      });
</script> -->