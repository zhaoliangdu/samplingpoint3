<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../top.jsp"></jsp:include>
<script src="http://cdn.gbtags.com/datatables/1.10.5/js/jquery.js"></script>
<script
	src="http://cdn.gbtags.com/datatables/1.10.5/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript"
	src="http://cdn.datatables.net/plug-ins/28e7751dbec/integration/bootstrap/3/dataTables.bootstrap.js"></script>
&nbsp;
<div class="container">
	<nav class="navbar navbar-expand-sm bg-light">
		<div class='navbar-brand' style="border:1px solid #6699ff;">数据类型选择： <strong>${type }</strong> </div>
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.servletContext.contextPath }/dataview/datalist?type=1">数字电视</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.servletContext.contextPath }/dataview/datalist?type=2">模拟电视</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.servletContext.contextPath }/dataview/datalist?type=3">CDR</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.servletContext.contextPath }/dataview/datalist?type=4">调幅调频</a>
			</li>
		</ul>
	</nav>
</div>
<hr>
<script>
	$(document).ready(function() {
		$('.myTable').dataTable({
			language : {
				"sProcessing" : "处理中...",
				"sLengthMenu" : "&nbsp;&nbsp;&nbsp;&nbsp;显示 _MENU_ 项结果",
				"sZeroRecords" : "没有匹配结果",
				"sInfo" : "&nbsp;&nbsp;&nbsp;&nbsp;共 _TOTAL_ 项",
				"sInfoEmpty" : "&nbsp;&nbsp;&nbsp;&nbsp;显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索:",
				"sUrl" : "",
				"sEmptyTable" : "表中数据为空",
				"sLoadingRecords" : "载入中...",
				"sInfoThousands" : ",",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上页",
					"sNext" : "下页",
					"sLast" : "末页"
				},
				"oAria" : {
					"sSortAscending" : ": 以升序排列此列",
					"sSortDescending" : ": 以降序排列此列"
				}
			}

		});
	});
</script>
