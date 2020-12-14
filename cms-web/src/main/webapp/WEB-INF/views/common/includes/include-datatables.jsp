<!-- DataTables 本体 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-responsive/css/responsive.bootstrap4.min.css"> -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>

<!-- Checkboxes-->
<script type="text/javascript"
  src="${pageContext.request.contextPath}/resources/plugins/jquery-datatables-checkboxes/js/dataTables.checkboxes.min.js">
</script>

<!-- Extention Buttons -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-buttons/css/buttons.bootstrap4.min.css">
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-buttons/js/buttons.html5.min.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>

<!-- fiexed Hader -->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedheader/css/fixedHeader.bootstrap4.min.css"> -->
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedheader/js/dataTables.fixedHeader.min.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedheader/js/fixedHeader.bootstrap4.min.js"></script> -->

<!-- ColReorder -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-colreorder/css/colReorder.bootstrap4.min.css">
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-colreorder/js/dataTables.colReorder.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-colreorder/js/colReorder.bootstrap4.min.js"></script>


<!-- fiexed Column-->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedcolumns/css/fixedColumns.bootstrap4.min.css"> -->
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedcolumns/js/dataTables.fixedColumns.min.js"></script> -->
<!-- <script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/datatables-fixedcolumns/js/fixedColumns.bootstrap4.min.js"></script> -->



<script src="${pageContext.request.contextPath}/resources/app/js/datatables-default.js"></script>

<style>
/* DataTablesの行下線が消える障害の解消 */
table.dataTable {
  border-collapse: collapse !important
}

/* フィールドフィルター */
tr.filter th {
  padding: 4px 8px;
}

/* scrollx でのスタイル崩れ修正 */
.dataTables_scrollBody > table > thead > tr {
  height: 0 !important;
  border-bottom-width: 0px !important;
}


/* DataTables Checkbox */
table.dataTable .dt-checkboxes-cell {
  padding-top: 7.8px;
}

.page-link {
  color: #212529;
  border: unset;
}

.page-item.active .page-link {
    color: #212529;
    background-color: #e5e5e2;
}

.page-item.disabled .page-link {
  color: #8c959d;  
}


.table-striped tbody tr:nth-of-type(odd) {
    background-color: unset;
}

.table-striped tbody tr:nth-of-type(even) {
    background-color: rgba(0, 0, 0, 0.02);
}

.table-hover tbody tr:hover {
    color: #212529;
    background-color: rgba(0, 0, 0, 0.075);
}

</style>
