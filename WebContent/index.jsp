<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="top.jsp"></jsp:include>
<style>
body, html, #allmap {
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

<div id="allmap"></div>
<input type="hidden" id="mode" value="${mode }" />
<input type="hidden" id="emitter" value="${emitter }" />
<input type="hidden" id="lng" value="${lng }" />
<input type="hidden" id="lat" value="${lat }" />
<input type="hidden" id="filepath" value="${filepath }" />
</body>
</html>
<script>  
	$(document).ready(function() {
				var mode = $("#mode").val();
				var emitter = $("#emitter").val();
				var lng = $("#lng").val();
				var lat = $("#lat").val();
				var filepath = $("#filepath").val();
				$("#loadingTip").remove();
				if (mode != "" && emitter != "" && lng != "" && lat != ""
						&& filepath != "") {
					$("<div id='loadingTip' >加载数据，请稍候...</div>").appendTo($("#allmap"));
					$.ajax({
						url : "importdata",
						type : "post",
						data : {
							"mode" : mode,
							"emitter" : emitter,
							"lng" : lng,
							"lat" : lat,
							"filepath" : filepath
						},
						success : function(val) {
							$("<div id='loadingTip' >"+val+"</div>").appendTo($("#allmap"));
						}
					});
				}
			});
</script>
<script>
	//百度地图API功能
	var map = new BMap.Map("allmap", {});
	map.centerAndZoom(new BMap.Point(106.000, 30.000), 5);
	map.enableScrollWheelZoom(true);
	map.enableInertialDragging();

	map.enableContinuousZoom();
	map.addControl(new BMap.MapTypeControl());
	//城市列表
	var size = new BMap.Size(10, 20);
	map.addControl(new BMap.CityListControl({
		anchor : BMAP_ANCHOR_TOP_LEFT,
		offset : size,
	}));
	$("<span id='lnglat'></span>").appendTo("#allmap");
	var top_right_navigation = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT,
		type : BMAP_NAVIGATION_CONTROL_SMALL
	}); //右上角，仅包含平移和缩放按钮 
	map.addControl(top_right_navigation);
	//鼠标移动显示经纬度
	map.addEventListener("mousemove", function(e) {
		$("#lnglat").text(
				"经度：" + e.point.lng.toFixed(4) + ",纬度："
						+ e.point.lat.toFixed(4));
	});
	var myDis = new BMapLib.DistanceTool(map);
	//开启鼠标测距
	function openDis() {
		myDis.open();
	}
	//定义海量点集合
	var pointCollections = new Array();

	function showemitter() {
		var opts = {
			width : 280, // 信息窗口宽度
			height : 150, // 信息窗口高度
			title : "台站信息", // 信息窗口标题
			enableMessage : true
		//设置允许信息窗发送短息
		};
		var transmitck = document.getElementById("ischeck").checked;
		if (transmitck) {
			$.ajax({
				url : "gettranlocation",
				type : "post",
				success : function(tran) {

					markers = new BMap.Marker(new BMap.Point(tran.longitude,
							tran.latitude));
					map.addOverlay(markers);
					labels = new BMap.Label(tran.emitterName, {
						offset : new BMap.Size(20, -10)
					});
					labels.setStyle({
						maxWidth : "none"
					});
					markers.setLabel(labels);
					content = "地区：" + tran.emitterName + "<br>" + "经度："
							+ tran.longitude + "<br>纬度：" + tran.latitude;
					addClickHandler(content, markers);
				}
			});

			function addClickHandler(content, marker) {
				marker.addEventListener("click", function(e) {
					openInfo(content, e)
				});
			}
			function openInfo(content, e) {
				var p = e.target;
				var point = new BMap.Point(p.getPosition().lng,
						p.getPosition().lat);
				var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
				map.openInfoWindow(infoWindow, point); //开启信息窗口
			}
		} else {
			var allOverlay = map.getOverlays();
			for (var i = 0; i < allOverlay.length; i++) {
				map.removeOverlay(allOverlay[i]);
			}
		}

	}
	//添加采样点
	function loadpoint() {
		var datatype = $("#datatype").val();
		var mtype = $("#mtype").val();

		$("#loadingTip").remove();
		$("<div id='loadingTip' >加载数据，请稍候...</div>").appendTo($("#allmap"));
		var allOverlay = map.getOverlays();
		for (var i = 0; i < allOverlay.length; i++) {
			map.removeOverlay(allOverlay[i]);
		}

		var opts = {
			width : 250, // 信息窗口宽度
			height : 300, // 信息窗口高度
			title : "采样点详细信息", // 信息窗口标题
		};
		var options = {
			size : BMAP_POINT_SIZE_BIGGER,
			shape : BMAP_POINT_SHAPE_CIRCLE,
		}

		$
				.ajax({
					url : "getpoints",
					type : "post",
					data : {
						"testModeId" : datatype,
						"typeId" : mtype
					},
					success : function(val) {
						$("#loadingTip").remove();
						if(val[1]!=""){ 
							$("<div id='loadingTip'  style='background-color:#6699ff'>加载完成！</div>")
									.appendTo($("#allmap"));
						}else{
							$("<div id='loadingTip'  style='background-color:red'>数据不存在！</div>")
							.appendTo($("#allmap"));
						}
						
						if (document.createElement('canvas').getContext) {
							var pointss = new Array(val[0].length);
							for (var i = 0; i < pointss.length; i++) {
								pointss[i] = [];
							}
							var pointvals = new Array();
							for (var i = 0; i < val[1].length; i++) {
								pointvals[i] = new BMap.Point(val[1][i].lon,
										val[1][i].lat);
								for (var j = 0; j < val[0].length; j++) {
									if (mtype == 1) {
										if (val[1][i].field >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].field < parseInt(val[0][j]
														.split("-")[1])) {
											pointss[j].push(pointvals[i]);
										}
									} else if (mtype == 2) {
										if (val[1][i].snr >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].snr < parseInt(val[0][j]
														.split("-")[1])) {
											pointss[j].push(pointvals[i]);
										}
									} else {
										if (val[1][i].ldpc >= parseInt(val[0][j]
												.split("-")[0])
												&& val[1][i].ldpc < parseInt(val[0][j]
														.split("-")[1])) {
											pointss[j].push(pointvals[i]);
										}
									}
								}
							}

							var pointCollection;
							for (var i = 0; i < pointss.length; i++) {
								var array = [];
								array = pointss[i];
								var options = {
									size : BMAP_POINT_SIZE_BIG,
									shape : BMAP_POINT_SHAPE_CIRCLE,
									color : val[2][i]
								}
								pointCollectioni = new BMap.PointCollection(
										pointss[i], options);
								//点击采样点显示详细信息
								pointCollectioni.addEventListener('click',
										function(e) {
											var infoSample = getSampleInfo(
													e.point.lng, e.point.lat);
											openInfo(infoSample, e.point.lng,
													e.point.lat);
										});
								pointCollections[i] = pointCollectioni;
								map.addOverlay(pointCollectioni); // 添加Overlay

								function openInfo(content, lng, lat) {
									var point = new BMap.Point(lng, lat);
									var infoWindow = new BMap.InfoWindow(
											content, opts); // 创建信息窗口对象
									map.openInfoWindow(infoWindow, point); //开启信息窗口
								}
							}
							//根据经纬度加载采样点信息
							function getSampleInfo(lng, lat) {
								for (var i = 0; i < val[1].length; i++) {
									var everyLng = val[1][i].lon;
									var everyLat = val[1][i].lat;
									var point = new BMap.Point(lng, lat);
									var pointEvery = new BMap.Point(everyLng,
											everyLat);
									if (pointEvery.equals(point)) {
										var sampleInfo;
										if (datatype == 1) {
											sampleInfo = "<br>时间："
													+ val[1][i].time
															.substring(
																	0,
																	(val[1][i].time.length) - 2)
													+ "<br>频率："
													+ val[1][i].frequency
													+ "(MHz)<br>经度："
													+ val[1][i].lon + "<br>纬度："
													+ val[1][i].lat
													+ "<br>场强值："
													+ val[1][i].field
													+ "<br>误包率："
													+ val[1][i].ldpc
													+ "<br>信噪比："
													+ val[1][i].snr + "<br>高度："
													+ val[1][i].height
													+ "<br>距离："
													+ val[1][i].distance
													+ "<br>方位角："
													+ val[1][i].angle;
										} else {
											sampleInfo = "<br>时间："
													+ val[1][i].time
															.substring(
																	0,
																	(val[1][i].time.length) - 2)
													+ "<br>频率："
													+ val[1][i].frequency
													+ "(MHz)<br>经度："
													+ val[1][i].lon + "<br>纬度："
													+ val[1][i].lat
													+ "<br>场强值："
													+ val[1][i].field
													+ "<br>信噪比："
													+ val[1][i].snr + "<br>高度："
													+ val[1][i].height
													+ "<br>距离："
													+ val[1][i].distance
													+ "<br>方位角："
													+ val[1][i].angle;
										}
										return sampleInfo;
									}
								}
							}

						} else {
							alert('请在chrome、safari、IE8+以上浏览器查看本示例');
						}
					}
				});
	}
</script>
