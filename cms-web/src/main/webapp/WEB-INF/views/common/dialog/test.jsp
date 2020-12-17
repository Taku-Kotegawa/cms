<table id="list" class="table-sm table-striped">
    <thead>
      <tr class="filter">
        <th class="text-center px-1" data-filter="disable"></th>
        <th data-filter="disable"></th>

        <th></th>
        <th></th>
        <th data-filter="disable">
          <select id="col_filter_4" data-column="4" class="dataTables_column_filter form-control">
            <option value=""></option>
            <c:forEach items="${CL_STATUS}" var="obj">
              <option value="${obj.key}">${obj.value}</option>
            </c:forEach>
          </select>
        </th>
        <th></th>
        <th></th>
        <th></th>

      </tr>
      <tr class="title">
        <th class="text-center px-0"></th>
        <th class="text-center">操作</th>

        <th class="text-center">ID</th>
        <th class="text-center">バージョン</th>
        <th class="text-center">ステータス</th>
        <th class="text-center">氏名</th>
        <th class="text-center">年齢</th>
        <th class="text-center">ファイル名</th>

      </tr>
    </thead>
    <tbody></tbody>
  </table>

  