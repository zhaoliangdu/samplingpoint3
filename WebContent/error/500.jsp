<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" errorPage="500.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body style="background-color: #6699ff;">
	<center>
		<span style="color: white;"> <a
			href="${pageContext.servletContext.contextPath }/index.jsp">返回首页</a>
			<h1>500 服务器错误，请重新启动</h1>
			<br> <%=exception.getMessage()%>
			</div>
	</center>
</body>
</html>