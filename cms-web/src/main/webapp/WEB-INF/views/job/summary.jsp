<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Job Summary</h4>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
        <a href="javascript:window.close();" class="btn btn-button">閉じる</a>
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <table>
      <thead>
        <tr>
          <th>Job Name</th>
          <th>Exe ID</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${jobSummary}" var="item">
          <tr>
            <td>
            <a
              href="${pageContext.request.contextPath}/job/result?targetjob=${item.jobName}">${item.jobName}</a>
            </td>
            <td>
              ${item.jobExecution.id}
            </td>
            <td>
              ${item.jobExecution.startTime}
            </td>
            <td>
              ${item.jobExecution.endTime}
            </td>
            <td>
              ${item.jobExecution.status}
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
