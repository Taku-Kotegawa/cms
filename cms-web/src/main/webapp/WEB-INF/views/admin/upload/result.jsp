<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Job Results</h3>
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
        <a href="" class="btn-button">更新</a>
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
        <td>${result.startTime}<br>${result.endTime}</td>
        <td>${result.status}</td>
        <td>${result.jobParameters}</td>
        <td class="text-center"><a href="${pageContext.request.contextPath}/admin/upload/joblog?jobexecutionid=${result.id}">参照</a></td>
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
        var url = "${pageContext.request.contextPath}/admin/upload/result?targetjob=" + $(this).val();
        location.href = url;
    });
  });
</script>
