<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>北京宝盈科技-采样点信息系统</title>
<jsp:include page="resource/resource.jsp"></jsp:include>
<style type="text/css">
#loadingTip {
	position: absolute;
	z-index: 9999;
	top: 0;
	left: 45%;
	padding: 3px 10px;
	background: red;
	color: #fff;
	font-size: 14px;
}

#tip {
	padding-left: 10px;
	padding-right: 10px;
	position: absolute;
	font-size: 12px;
	right: 10px;
	top: 55px;
	border-radius: 3px;
	line-height: 30px;
}

body, html, #allmap, #container, #bingMap {
	width: 100%;
	height: 100%;
	z-index: 999;
	margin: 0;
	overflow: hidden;
	font-family: "微软雅黑";
}

#lnglat {
	position: absolute;
	z-index: 9999;
	top: 0;
	left: 0;
	padding: 3px 10px;
	background: #6699ff;
	color: #fff;
	font-size: 14px;
}
</style>
</head>
<script type="text/javascript">
	//显示当前时间
	function startTime() {
		var date = new Date();
		var dates = date.toLocaleString();
		document.getElementById("time").innerHTML = "当前时间：" + dates;
	}
	setInterval("startTime()", 100);
</script>

<body oncontextmenu=self.event.returnValue=false
	style="font-size: 18px;">
	<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
		<a class="navbar-brand" href="${pageContext.servletContext.contextPath }/index.jsp">采样点信息系统 <img
			src="${pageContext.servletContext.contextPath }/resource/images/logo.gif"
			alt="北京宝盈科技" /></a>
		<ul class="navbar-nav">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="${pageContext.servletContext.contextPath }/index.jsp" id="navbardrop"
				data-toggle="dropdown"> 地图 </a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="#" onclick="openDis()">距离计算</a> <a
						class="dropdown-item" href="#" data-toggle="modal"
						data-target="#loadpoint">加载采样点</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"
						class="form-check-input" id="ischeck" onclick="showemitter()" />显示台站

				</div></li>
			<li class="nav-item"><a href="${pageContext.servletContext.contextPath }/index.jsp" class="nav-link">
					百度地图</a></li>
			<li class="nav-item"><a href="${pageContext.servletContext.contextPath }/map/gaode" class="nav-link">
					高德地图</a></li>
			<li class="nav-item"><a href="${pageContext.servletContext.contextPath }/map/bing" class="nav-link">
					必应地图</a></li>
			<li class="nav-item"><a href="${pageContext.servletContext.contextPath }/systemset/getsystemset?typeId=1"
				class="nav-link"> 系统设置</a></li>
			<li class="nav-item"><a href="${pageContext.servletContext.contextPath }/dataview/datalist?type=1" class="nav-link">数据列表</a></li>
		</ul>
		<span id="time" style="color: #ffffff; float: right;"></span>&nbsp;&nbsp;<span
			style='color: #ffffff;'>天气情况：</span>
		<div>
			<div id="tp-weather-widget">
				<script>
					(function(T, h, i, n, k, P, a, g, e) {
						g = function() {
							P = h.createElement(i);
							a = h.getElementsByTagName(i)[0];
							P.src = k;
							P.charset = "utf-8";
							P.async = 1;
							a.parentNode.insertBefore(P, a)
						};
						T["ThinkPageWeatherWidgetObject"] = n;
						T[n] || (T[n] = function() {
							(T[n].q = T[n].q || []).push(arguments)
						});
						T[n].l = +new Date();
						if (T.attachEvent) {
							T.attachEvent("onload", g)
						} else {
							T.addEventListener("load", g, false)
						}
					}(window, document, "script", "tpwidget",
							"//widget.seniverse.com/widget/chameleon.js"))
				</script>
				<script>
					tpwidget("init", {
						"flavor" : "slim",
						"location" : "WX4FBXXFKE4F",
						"geolocation" : "disabled",
						"language" : "zh-chs",
						"unit" : "c",
						"theme" : "chameleon",
						"container" : "tp-weather-widget",
						"bubble" : "disabled",
						"alarmType" : "badge",
						"color" : "#FFFFFF",
						"uid" : "U9EC08A15F",
						"hash" : "14dff75e7253d3a8b9727522759f3455"
					});
					tpwidget("show");
				</script>
			</div>
		</div>
	</nav>

	<!-- 添加台站 -->
	<!-- 模态框 -->
	<div class="modal fade" id="loadpoint">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">加载采样点</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<label>选择数据：</label> <select class="form-control"
						required="required" id="datatype">
						<option value="1">数字电视</option>
						<option value="2">调频/调幅</option>
						<option value="3">CDR</option>
						<option value="4">模拟电视</option>
					</select> <label>选择类型：</label> <select class="form-control" id="mtype">
						<option value="1">场强</option>
						<option value="2">信噪比</option>
						<option value="3">误包率</option>
					</select>
				</div>
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="loadpoint()"
						data-dismiss="modal">加载</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="mode" value="${mode }" />
	<input type="hidden" id="emitter" value="${emitter }" />
	<input type="hidden" id="lng" value="${lng }" />
	<input type="hidden" id="lat" value="${lat }" />
	<input type="hidden" id="filepath" value="${filepath }" />