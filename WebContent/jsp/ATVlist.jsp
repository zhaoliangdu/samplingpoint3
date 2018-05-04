<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="datalist.jsp"></jsp:include>
<div class="" style="width: 100%;">
	<table class="table table-striped  table-bordered table-hover myTable"
		style="text-align: center; width: 100%">
		<thead class="thead-light">
			<tr style="font-size: 14px">
				<th>ID</th>
				<th>时间</th>
				<th>地区</th>
				<th>经度</th>
				<th>纬度</th>
				<th>高度</th>
				<th>速度</th>
				<th>测试模式</th>
				<th>频率</th>
				<th>音频场强</th>
				<th>信噪比</th>
				<th>距离</th>
				<th>方位角</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="analogdata">
				<tr>
					<td>${analogdata.id }</td>
					<td>${analogdata.time }</td>
					<td>${analogdata.area }</td>
					<td>${analogdata.transforLng }</td>
					<td>${analogdata.transforLat }</td>
					<td>${analogdata.height }</td>
					<td>${analogdata.speed }</td>
					<td>模拟电视</td>
					<td>${analogdata.frequency }</td>
					<td>${analogdata.fieldStrength }</td>
					<td>${analogdata.snr }</td>
					<td>${analogdata.distance }</td>
					<td>${analogdata.angle }</td>
					<td><a href="#" onclick="delanalog(${analogdata.id })"
						class="btn btn-danger">删除</a></td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script>
	function delanalog(id){
		if(confirm("确定删除吗？")){
			$.ajax({
				url:"delanalogdata",
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