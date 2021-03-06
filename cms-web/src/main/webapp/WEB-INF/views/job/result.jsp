<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>Job Results</h4>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
        <a href="javascript:window.close();" class="btn btn-button">閉じる</a>
        <a href="summary" class="btn btn-button">サマリに戻る</a>
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <div class="row mb-3">
      <div class="col-8">
        <label>ジョブの選択</label>
        <select id="targetJob" class="form-control">
          <c:forEach items="${jobList}" var="job">
            <option <c:if test="${job == selectedJob}">selected</c:if>>${job}</option>
          </c:forEach>
        </select>
      </div>
      <div class="col-28 align-self-end">
        <a href="" class="btn btn-button">更新</a>
      </div>
    </div>

    <table class="table table-sm">
    <thead>
      <tr>
        <th>id</th>
        <th>startTime<br>endTime</th>
        <th>status</th>
        <th>jobParameters</th>
        <th>log</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${jobResults}" var="result">
      <tr>
        <td>${result.id}</td>
        <td style="white-space: nowrap;">
          <fmt:formatDate value="${result.startTime}" pattern="yyyy/MM/dd HH:mm:ss" /><br>
          <fmt:formatDate value="${result.endTime}" pattern="yyyy/MM/dd HH:mm:ss" />
        </td>
        <td>${result.status}</td>
        <td>${result.jobParameters}</td>
        <td class="text-center"><a href="${pageContext.request.contextPath}/job/joblog?jobexecutionid=${result.id}">参照</a></td>
      </tr>
    </c:forEach>
    </tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<script>
  $(document)
  .ready(
  function () {
    $('#targetJob').change(function() {
        console.log(this);
        var url = "${pageContext.request.contextPath}/job/result?targetjob=" + $(this).val();
        location.href = url;
    });
  });
</script>
