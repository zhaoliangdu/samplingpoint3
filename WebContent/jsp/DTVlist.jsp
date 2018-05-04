<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="datalist.jsp"></jsp:include>
<div class="" style="width: 100%;">
	<table width="100%"
		class="table table-striped table-bordered table-hover myTable"
		style="text-align: center">
		<thead class="thead-light">
			<tr style="font-size: 15px">
				<th>ID</th>
				<th>时间</th>
				<th>地区</th>
				<th>经度</th>
				<th>纬度</th>
				<th>高度</th>
				<th>速度</th>
				<th>测试模式</th>
				<th>频率</th>
				<th>带宽</th>
				<th>音频场强</th>
				<th>信噪比</th>
				<th>MER</th>
				<th>误包率</th>
				<th>距离</th>
				<th>方位角</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="digitaldata">
				<tr> 
					<td>${digitaldata.id }</td>
					<td>${digitaldata.time }</td>
					<td>${digitaldata.area }</td>
					<td>${digitaldata.transforLng }</td>
					<td>${digitaldata.transforLat }</td>
					<td>${digitaldata.height }</td>
					<td>${digitaldata.speed }</td>
					<td>数字电视</td>
					<td>${digitaldata.frequency }</td>
					<td>${digitaldata.wideBand }</td>
					<td>${digitaldata.fieldStrength }</td>
					<td>${digitaldata.snr }</td>
					<td>${digitaldata.mer }</td>
					<td>${digitaldata.ldpc }</td>
					<td>${digitaldata.distance }</td>
					<td>${digitaldata.angle }</td>
					<td><a href="#" onclick="deldigital(${digitaldata.id })"
						class="btn btn-danger">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script>
	function deldigital(id) {
		if(confirm("确定删除吗？")){
			$.ajax({
				url:"deldigitaldata",
				type:"post",
				data:{"id":id},
				success:function(msg){
					alert(msg);
					location.reload();
				}
			});
		}else{
			return false;	
		}
	}
</script>