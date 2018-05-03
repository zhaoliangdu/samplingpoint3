<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="http://webapi.amap.com/maps?v=1.4.0&key=100ac2f2d36ccc13f62f1f86617ca9e3"></script>
<script type="text/javascript"
	src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
<jsp:include page="../../top.jsp"></jsp:include>
<div id="container"></div>
<div id="tip">
	<input type="button" class="btn btn-secondary" value="面积量测"
		onclick="mianji()" />
</div>
</body>
</html>
<script>
	var map;
	AMapUI.loadUI([ 'control/BasicControl' ], function(BasicControl) {
		var layerCtrl1 = new BasicControl.LayerSwitcher({
			position : 'tr'
		});
		//高德地图  
		map = new AMap.Map('container', {
			resizeEnable : true,
			rotateEnable : true,
			pitchEnable : true,
			expandZoomRange : true,
			zoom : 4,
			center : [ 108.95, 30.26 ],
			layers : layerCtrl1.getEnabledLayers()
		});
		map.addControl(layerCtrl1);
		var markers = [], positions = [];
		var clickEventListener = map.on('mousemove', function(e) {
			document.getElementById("lnglat").innerHTML = "经度："
					+ e.lnglat.getLng().toFixed(4) + ',纬度：'
					+ e.lnglat.getLat().toFixed(4)
		});
		$("<div id='lnglat' ></div>").appendTo($("#container"));

		var contextMenu = new AMap.ContextMenu(); //创建右键菜单
		//右键放大
		contextMenu.addItem("放大一级", function() {
			map.zoomIn();
		}, 0);
		//右键缩小
		contextMenu.addItem("缩小一级", function() {
			map.zoomOut();
		}, 1);
		//右键显示全国范围
		contextMenu.addItem("缩放至全国范围", function(e) {
			map.setZoomAndCenter(4, [ 108.95, 30.26 ]);
		}, 2);
		//右键添加Marker标记
		contextMenu.addItem("添加标记", function(e) {
			var marker = new AMap.Marker({
				map : map,
				position : contextMenuPositon
			//基点位置
			});
		}, 3);

		//地图绑定鼠标右击事件——弹出右键菜单
		map.on('rightclick', function(e) {
			contextMenu.open(map, e.lnglat);
			contextMenuPositon = e.lnglat;
		});
	});
 
	function showemitter() {
		var gdck = document.getElementById("ischeck").checked;

		if (gdck) {
			$.ajax({
				url : "${pageContext.servletContext.contextPath }/gettranlocation",
				type : "post",
				success : function(tran) {

					var infoWindow = new AMap.InfoWindow({
						offset : new AMap.Pixel(0, -30)
					});

					var marker = new AMap.Marker({
						position : [ tran.longitude, tran.latitude ],
						map : map
					});
					marker.content = "台站名称：" + tran.emitterName + "<br>"
							+ "经度：" + tran.longitude + ",纬度：" + tran.latitude;
					marker.on('click', markerClick);
					marker.emit('click', {
						target : marker
					});
					function markerClick(e) {
						infoWindow.setContent(e.target.content);
						infoWindow.open(map, e.target.getPosition());
					}
					map.setFitView();
				}
			});
		}
	}
	function mianji() {
		map.plugin([ "AMap.MouseTool" ], function() {
			var mouseTool = new AMap.MouseTool(map);
			//鼠标工具插件添加draw事件监听
			AMap.event.addListener(mouseTool, "draw", function callback(e) {
				var eObject = e.obj;//obj属性就是鼠标事件完成所绘制的覆盖物对象。
			});
			mouseTool.measureArea(); //调用鼠标工具的面积量测功能
		});
	}

	//加载采样点
	function loadpoint() {
		$("#loadingTip").remove();
		$('<div id="loadingTip">加载数据，请稍候...</div>').appendTo($("#container"));
		var testModeId = $("#datatype").val();
		var typeId = $("#mtype").val();

		$
				.ajax({
					url : "${pageContext.servletContext.contextPath }/getpoints",
					type : "post",
					data : {
						"testModeId" : testModeId,
						"typeId" : typeId
					},
					success : function(val) {
						$("#loadingTip").remove();
						var colorArray = val[2];
						if (val[1] != "") {
							$(
									'<div id="loadingTip" style="background-color:#6699ff">加载完成!</div>')
									.appendTo($("#container"));
							 
						} else {
							$(
									'<div id="loadingTip" style="background-color:red">数据不存在!</div>')
									.appendTo($("#container"));
							return;
						}
						AMapUI.load([ 'ui/misc/PointSimplifier', 'lib/utils',
								'lib/$' ], function(PointSimplifier, utils, $) {

							if (!PointSimplifier.supportCanvas) {
								alert('当前环境不支持 Canvas！');
								return;
							}
							//启动页面
							initPage(PointSimplifier, utils, $);
						});

						function initPage(PointSimplifier, utils, $) {
							function MyCanvasRender(pointSimplifierIns, opts) {
								//直接调用父类的构造函数
								MyCanvasRender.__super__.constructor.apply(
										this, arguments);
							}

							//继承自默认的Canvas引擎（http://webapi.amap.com/ui/1.0/ui/misc/PointSimplifier/render/canvas.js）
							utils.inherit(MyCanvasRender,
									PointSimplifier.Render.Canvas);
							utils
									.extend(
											MyCanvasRender.prototype,
											{
												renderNormalPoints : function(
														zoom, activePoints,
														shadowPoints) {
													//先按默认逻辑处理shadowPoints
													MyCanvasRender.__super__.renderNormalPoints
															.call(this, zoom,
																	null,
																	shadowPoints);
													var pointStyle = this
															.getOption('pointStyle'), getPointsGroupKey = this
															.getOption('getPointsGroupKey'), pointStyleGroup = this
															.getOption('pointStyleGroup'), pointSimplifierIns = this
															.getPointSimplifierInstance(), groups = {};
													//按key分组
													for (var i = 0, len = activePoints.length; i < len; i++) {

														var point = activePoints[i], dataIndex = point.idx, dataItem = pointSimplifierIns
																.getDataItemByIndex(dataIndex);

														var key = getPointsGroupKey
																.call(
																		this,
																		dataItem,
																		dataIndex);
														if (!groups[key]) {
															groups[key] = [];
														}
														groups[key]
																.push(activePoints[i]);
													}
													//分组绘制
													for ( var k in groups) {
														//继承pointStyle中的默认属性
														var styleOptions = utils
																.extend(
																		{},
																		pointStyle,
																		pointStyleGroup[k]);

														//调用父类的绘制函数
														this
																.drawPointsWithStyleOptions(
																		groups[k],
																		styleOptions);
													}
												}
											});
							var cindex;
							var pointSimplifierIns = new PointSimplifier(
									{
										zIndex : 300,
										map : map,
										getPosition : function(item) {
											if (!item) {
												return null;
											}
											return item.position;
										},
										getHoverTitle : function(dataItem, i) {
											return "<hr>时间："
													+ val[1][i].time
															.substring(
																	0,
																	(val[1][i].time.length) - 2)
													+ "<br>频率："
													+ val[1][i].frequency
													+ "(MHz)<br>经度："
													+ val[1][i].lon
													+ "&nbsp;&nbsp;纬度："
													+ val[1][i].lat
													+ "<br>场强值："
													+ val[1][i].field
													+ "<br>高度："
													+ val[1][i].height
													+ "<br>距离："
													+ val[1][i].distance
													+ "<br>信噪比："
													+ val[1][i].snr
													+ "<br>方位角："
													+ val[1][i].angle;
										},
										//赋值为 MyCanvasRender
										renderConstructor : MyCanvasRender,
										renderOptions : {
											getPointsGroupKey : function(
													dataItem, dataIndex) {
												//这里直接按索引取余  
												for (var i = 0; i < val[1].length; i++) {
													if (dataItem.position[0] == val[1][i].lon && dataItem.position[1] == val[1][i].lat) {
														for (var c = 0; c < val[0].length; c++) {
															if (val[1][i].field > val[0][c].split("-")[0]
																	&& val[1][i].field <= val[0][c].split("-")[1]) {
																cindex = c;
																break;
															}
														}
														break;
													}

												}
												return "g" + cindex;
											},
											//分组配置
											pointStyleGroup : {
												'g0' : {
													fillStyle : colorArray[0],
													width : 16,
													height : 16
												},
												'g1' : {
													fillStyle : colorArray[1],
													width : 16,
													height : 16
												},
												'g2' : {
													fillStyle : colorArray[2],
													width : 16,
													height : 16
												},
												'g3' : {
													fillStyle : colorArray[3],
													width : 16,
													height : 16
												},
												'g4' : {
													fillStyle : colorArray[4],
													width : 16,
													height : 16
												},
												'g5' : {
													fillStyle : colorArray[5],
													width : 16,
													height : 16
												},
												'g6' : {
													fillStyle : colorArray[6],
													width : 16,
													height : 16
												},
												'g7' : {
													fillStyle : colorArray[7],
													width : 16,
													height : 16
												},
												'g8' : {
													fillStyle : colorArray[8],
													width : 16,
													height : 16
												},
												'g9' : {
													fillStyle : colorArray[9],
													width : 16,
													height : 16
												},
												'g10' : {
													fillStyle : colorArray[10],
													width : 16,
													height : 16
												},
												'g11' : {
													fillStyle : colorArray[11],
													width : 16,
													height : 16
												},
												'g12' : {
													fillStyle : colorArray[12],
													width : 16,
													height : 16
												},
												'g13' : {
													fillStyle : colorArray[13],
													width : 16,
													height : 16
												},
												'g14' : {
													fillStyle : colorArray[14],
													width : 16,
													height : 16
												}
											}

										}
									});

							//创建数据点
							var data = createPoints(val[1]);

							//设置数据源，data需要是一个数组
							pointSimplifierIns.setData(data);

							//监听事件
							pointSimplifierIns.on(
									'pointClick pointMouseover pointMouseout',
									function(e, record) {
										console.log(e.type, record);
									});
						}

						//创建数据
						function createPoints(val) {
							var data = [];
							for (var i = 0; i < val.length; i++) {
								data.push({
									position : [ val[i].lon, val[i].lat ],
									map : map
								});
							}
							return data;
						}
					}
				});
	}
</script>
