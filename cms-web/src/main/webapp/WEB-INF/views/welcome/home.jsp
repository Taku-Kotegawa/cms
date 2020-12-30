<c:if test="${not empty shortMessage}">
  <div class="shortMessage container-flued py-1 px-3">
    ${f:br(f:h(shortMessage))}
  </div>  
</c:if>
<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Hello world!</h3>
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

    <div class="row">
      <div class="col-36">
        <h4>メニュー</h4>
        <ul>
          <li> <a href="${f:h(pageContext.request.contextPath)}/simpleentity/list">SimpleEntity List</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/person/list">Person</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/person/search">Person(Search)</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/select/list">Select</a></li>
          <li> <a href="${f:h(pageContext.request.contextPath)}/pdfbox?form">pdfbox</a></li>
        </ul>
      </div>
    </div>
  </div>

  <!-- ここより上にメインコンテンツを記入 -->
  </div>
  
</section>

<footer class="main-footer">
  <div class="float-right d-none d-sm-block">
    <a href="#" class="btn-button">BackToTop</a>
  </div>

  <a href="#" class="btn-button">BackToTop</a>
</footer>
