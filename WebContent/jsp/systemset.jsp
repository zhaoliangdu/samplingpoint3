<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../top.jsp"></jsp:include>

<div class="container-fluid">
	<form role="form"
		action="${pageContext.servletContext.contextPath }/updatesysset"
		method="post" class="form-inline"> 
		<div class="panel panel-default" style="padding: 10px 40px;">
			   <span style="color:red;">${sysmsg}</span>
			<table>
				<tr>  
					<td>类型选择： <select class="form-control" onchange="changeset()"
						id="chosev">
							<option value="1">场强</option>
							<option value="2">信噪比</option>
							<option value="3">误包率</option>
					</select><input type="hidden" value="${typeId }" id="tid" name="typeId" />
					</td>
				</tr>
				<tr>
					<td>场强范围：<input type="number" class="form-control" id="min"
						value="${min }" name="min" placeholder="最小值" /></td>
					<td>—<input type="number" id="max" value="${max }" name="max"
						class="form-control" placeholder="最大值" /></td>
				</tr>
			</table>
			<table>
				<tr align="center">
					<td>分类数量：<input type="number" class="form-control" id="score"
						value="${score }" name="score" placeholder="分值" /></td>
				</tr>
			</table>
			<table border="0px" cellspacing="0" style="width: 200px;">
				<tr id="ctr">
					<th>场强值：</th>
					<th>颜色：</th>
				</tr>
				<tr align="center">
					<td>
						<table class="layui-table" style="text-align: left; float: left;"
							id="range">
							<c:forEach items="${rangelist }" var="range">
								<tr>
									<td><input type='text' readonly='readonly'
										value='${range }' class='form-control'></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td>
						<table class="layui-table" id="colors">
							<c:forEach items="${colorlist }" var="colors">
								<tr>
									<td><input type='text' class='jscolor form-control'
										value='${colors }' name='colors' /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			<br>
			<center>
				<button type="submit" class="btn btn-primary" onclick="save()" >保存</button>
				<a href="resetset" class="btn btn-dark">恢复默认设置</a>
			</center>
		</div>
	</form>
</div>
<input hidden="hidden" id="locationset"
	value="${pageContext.servletContext.contextPath}/systemset?typeId=" />
</body>
</html>
<script>
	$(document).ready(function() {
		var tid = $("#tid").val();
		if (tid != "") {
			$("#chosev").val(tid);
		}
	});

	$("#typeval").text(typeval);
	function changeset() {
		var typeId = $("#chosev option:selected").val();

		window.location.href = $("#locationset").val() + typeId;
	}
</script>

<script>
	function save() {
		if ($("#score").val() > 15) {
			$("#score").val(15);
			alert("请输入小于15的整数！");
			return false;
		}
	}
</script>

