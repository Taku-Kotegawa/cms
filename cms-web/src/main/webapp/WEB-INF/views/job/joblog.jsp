<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Job Log</h4>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
        <a href="" class="btn btn-button">更新</a>
        <a href="javascript:window.close();" class="btn btn-button">閉じる</a>
        <a href="result?targetjob=${jobName}" class="btn btn-button">ジョブログ一覧に戻る</a>
        <a href="summary" class="btn btn-button">サマリに戻る</a>
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

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
