<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
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

    <p class="border px-4 py-2">
    データ取り込みを開始しました。貴方のジョブ番号は、<b>ID = ${jobExecutionId}</b> です。<br>
    処理結果は、ジョブログを確認してください。<br>
    処理完了まで時間が要する場合があります。
    </p>

    <a href="${pageContext.request.contextPath}/${returnBackUrl}" class="btn btn-button">${f:h(returnBackBtn)}</a>

    <a href="${pageContext.request.contextPath}/job/joblog?jobexecutionid=${jobExecutionId}"
             target="_blank" class="btn btn-button">ジョブログを確認する</a>

    <a href="${pageContext.request.contextPath}/job/result?targetjob=${jobName}"
             target="_blank" class="btn btn-button">ジョブ一覧を表示する</a>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

