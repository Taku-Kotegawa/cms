<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Lucene 索引再作成</h4>
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

    <ul>
      <li><a href="${f:h(pageContext.request.contextPath)}/admin/index/jp.co.stnet.cms.domain.model.example.Person/reindexing">Person</a></li>
      <li><a href="${f:h(pageContext.request.contextPath)}/admin/index/jp.co.stnet.cms.domain.model.report.Document/reindexing">Document</a></li>
      <li><a href="${f:h(pageContext.request.contextPath)}/admin/index/jp.co.stnet.cms.domain.model.report.PageIdx/reindexing">PageIdx</a></li>

    </ul>



    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
