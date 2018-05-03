<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="datalist.jsp"></jsp:include>

<div>
	<table class="table table-striped table-bordered table-hover myTable"
		style="text-align: center">
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
				<th>带宽</th>
				<th>场强</th>
				<th>频偏</th>
				<th>信噪比</th>
				<th>调制度</th>
				<th>距离</th>
				<th>方位角</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="radiodata">
				<tr>
					<td>${radiodata.id }</td>
					<td>${radiodata.time }</td>
					<td>${radiodata.area }</td>
					<td>${radiodata.transforLng }</td>
					<td>${radiodata.transforLat }</td>
					<td>${radiodata.height }</td>
					<td>${radiodata.speed }</td>
					<c:if test="${radiodata.testModeId==2 }">
						<td>调频</td>
					</c:if>
					<c:if test="${radiodata.testModeId==3 }">
						<td>调幅</td>
					</c:if>
					<td>${radiodata.frequency }</td>
					<td>${radiodata.wideBand }</td>
					<td>${radiodata.fieldStrength }</td>
					<td>${radiodata.frequencyOffset }</td>
					<td>${radiodata.signalNoiseRatio }</td>
					<td>${radiodata.regulationSystem }</td>
					<td>${radiodata.distance }</td>
					<td>${radiodata.angle }</td>
					<td><a href="#"
						onclick="delradiodata(${radiodata.id })"
						class="btn btn-danger">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script>
	function delradiodata(id){
		if(confirm("确定删除吗？")){
			$.ajax({
				url:"${pageContext.servletContext.contextPath }/delradiodata",
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
