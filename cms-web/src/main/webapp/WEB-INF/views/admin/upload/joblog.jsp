<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Job Log</h4>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
        <a href="${pageContext.request.contextPath}/admin/upload/result?targetjob=${jobName}" class="btn btn-button">一覧に戻る</a>
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <div>
    ジョブ名: ${jobName}, ID: ${jobExecutionId}
    </div>
    <div class="border p-3">
        <c:forEach items="${jobLog}" var="line">
            ${line}<br>
        </c:forEach>
    </div>
    <div class="text-right">
        <br>
        <a href="${pageContext.request.contextPath}/admin/upload/result?targetjob=${jobName}" class="btn btn-button">一覧に戻る</a>
    </div>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

