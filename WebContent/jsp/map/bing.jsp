<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../../top.jsp"></jsp:include>
<script type='text/javascript'
	src='https://www.bing.com/api/maps/mapcontrol?branch=experimental&callback=loadMapScenario'
	async defer></script>
<style>
body, html, #bingMap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
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

<div id="bingMap"></div>

<script>
	var bingmap;
	//加载bing地图
	function loadMapScenario() {
		bingmap = new Microsoft.Maps.Map(
				document.getElementById('bingMap'),
				{
					credentials : 'AtrHYGI-KJZEpGZS7YyXlC0ABXqFMrBFRrgp_oRG3lJdHOc5Th-VO9F1nIBGkCJe',
					center : new Microsoft.Maps.Location(32.200, 106.001),
					zoom : 4
				});
	}
	//地图显示台站
	function showemitter() {
		var transmitck = document.getElementById("ischeck").checked;
		if (transmitck) {
			$
					.ajax({
						url : "${pageContext.servletContext.contextPath }/gettranlocation",
						type : "post",
						success : function(tran) {
							var pushpins = new Array(tran.length);
							pushpins = new Microsoft.Maps.Pushpin(
									new Microsoft.Maps.Location(tran.latitude,
											tran.longitude), {
										text : 'T',
										title : tran.emitterName,
										subTitle : ''
									});
							bingmap.entities.push(pushpins); 
						}
					});
		} else {
			for (var i = bingmap.entities.getLength() - 1; i >= 0; i--) {
				var pushpin = bingmap.entities.get(i);
				if (pushpin instanceof Microsoft.Maps.Pushpin) {
					bingmap.entities.removeAt(i);
				}
			}
		}
	}
	//加载采样点信息
	function loadpoint() {
		var testModeId = $("#datatype").val();
		var typeId = $("#mtype").val();
		$("#loadingTip").remove();
		$('<div id="loadingTip">加载数据，请稍候...</div>').appendTo($("#bingMap"));
		for (var i = bingmap.entities.getLength() - 1; i >= 0; i--) {
			var pushpin = bingmap.entities.get(i);
			if (pushpin instanceof Microsoft.Maps.Pushpin) {
				bingmap.entities.removeAt(i);
			}
		}

		$
				.ajax({
					url : "${pageContext.servletContext.contextPath }/getpoints",
					type : "post",
					data : {
						"testModeId" : testModeId,
						"typeId" : typeId
					},
					success : function(val) {
						var colorArray = val[2];
						if (document.createElement('canvas').getContext) {
							$("#loadingTip").remove();
							if (val[1] == "") {
								$(
										'<div id="loadingTip" style="background-color:red">数据不存在！</div>')
										.appendTo($("#bingMap"));

							} else {
								$(
										'<div id="loadingTip" style="background-color:#6699ff">加载完成！</div>')
										.appendTo($("#bingMap"));

							}

							var pushpins = new Array(val[1].length);
							for (var i = 0; i < val[1].length; i++) {
								for (var j = 0; j < val[0].length; j++) {
									if (typeId == 0) {
										if (val[1][i].field >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].field < parseInt(val[0][j]
														.split("-")[1])) {
											pushpins[i] = new Microsoft.Maps.Pushpin(
													new Microsoft.Maps.Location(
															val[1][i].lat,
															val[1][i].lon), {
														color : colorArray[j]
													});
										}
									} else if (typeId == 1) {
										if (val[1][i].snr >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].snr < parseInt(val[0][j]
														.split("-")[1])) {
											pushpins[i] = new Microsoft.Maps.Pushpin(
													new Microsoft.Maps.Location(
															val[1][i].lat,
															val[1][i].lon), {
														color : colorArray[j]
													});
										}
									} else {
										if (val[1][i].ldpc >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].ldpc < parseInt(val[0][j]
														.split("-")[1])) {
											pushpins[i] = new Microsoft.Maps.Pushpin(
													new Microsoft.Maps.Location(
															val[1][i].lat,
															val[1][i].lon), {
														color : colorArray[j]
													});
										}
									}

								}
							}

							var infobox = new Microsoft.Maps.Infobox(
									pushpins[0].getLocation(), {
										visible : false
									});
							infobox.setMap(bingmap);
							for (var a = 0; a < pushpins.length; a++) {
								 
								pushpins[a].metadata = {
									title : '采样点信息',
									description : "<hr>时间："
											+ val[1][a].time
													.substring(0,(val[1][a].time.length) - 2)
											+ "<br>频率：" + val[1][a].frequency
											+ "(MHz)<br>经度：" + val[1][a].lon
											+ "<br>纬度：" + val[1][a].lat
											+ "<br>场强值：" + val[1][a].field
											+ "<br>高度：" + val[1][a].height
											+ "<br>距离：" + val[1][a].distance
											+ "<br>信噪比：" + val[1][a].snr
											+ "<br>方位角：" + val[1][a].angle
								};

								Microsoft.Maps.Events
										.addHandler(
												pushpins[a],
												'click',
												function(args) {
													infobox.setOptions({
																location : args.target
																		.getLocation(),
																title : args.target.metadata.title,
																description : args.target.metadata.description,
																visible : true
															});
												});
								bingmap.entities.push(pushpins[a]);
							}
						} else {
							alert('请在chrome、safari、IE8+以上浏览器查看本示例');
						}
					}
				});
	}
</script>
