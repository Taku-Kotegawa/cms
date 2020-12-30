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

<section class="content-header">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
  </div>
</section>
<section class="content">
  <div class="container">
    <!-- ここより下にメインコンテンツを記入 -->
    <form method="get">
      <div class="row">
        <div class="col-18 offset-1">
          <div class="input-group">
            <input class="form-control" name="q" value="${f:h(q)}" />
            <div class="input-group-append">
              <button class="btn btn-button">検索</button>
            </div>
          </div>
        </div>
    </form>
  </div> <!-- <div class="row"> -->

  <br>
  <br>
  <div class="row">
    <div class="col-28 pr-5 pl-5">
      <div>
        <c:if test="${totalHitCount != null}">
          <p>${f:h(totalHitCount)} 件が検索にヒットしました。</p>
        </c:if>
      </div>
      <div>
        <c:forEach var="item" items="${hits}" varStatus="status">
          <div class="mb-5">
            <h4>
              <a href="${item.id}">${f:h(item.attachedFile01Managed.originalFilename)}</a>
              <span class="h6"><a
                  href="${f:h(pageContext.request.contextPath)}/person/${item.attachedFile01Uuid}/download"><i
                    class="fas fa-file-download ml-2"></i></a></span>
            </h4>
            <p class="">${item.contentHighlight}</p>
          </div>
        </c:forEach>
      </div>
      <div class="mx-auto text-center">
        <t:pagination page="${page}" outerElementClass="pagination" activeClass="active" disabledClass="disabled"
        queryTmpl="page={page}&size={size}&q=${f:h(q)}" />
      </div>
    </div>
    <div class="col-8 p-4">
      <div class="h-100 border-left p-4">
        <div class="ml-4">
          <h6>ファセット検索</h6>
          <hr>
          <c:forEach var="code" items="${countsByGenre}" varStatus="status">
            <div>
              ${f:h(CL_SAMPLE[code.key])} <span class="badge badge-pill badge-secondary">${code.value}</span>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>


  <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>