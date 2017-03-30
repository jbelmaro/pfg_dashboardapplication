<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>

<!DOCTYPE html>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>M2M Dashboard Application Informs Page</title>
<script type="text/javascript"
	src="http://www.google.com/jsapi?key=ABQIAAAAnP8JWZ5h8E7LFAD9NqVUwxSeX1v6nbhYXW-wX1PxoOWc4Ggu3BSJDJ9DsFY8gXkhAgs1GxBeYOlatg"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/dashboardApplicationStyle.css">
<link
	href='http://fonts.googleapis.com/css?family=Lato:100&subset=latin,latin-ext'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:900&subset=latin,latin-ext'
	rel='stylesheet' type='text/css'>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.0.3.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/jquery.jqplot.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/dist/jquery.jqplot.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.logAxisRenderer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.meterGaugeRenderer.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.cursor.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.highlighter.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dist/plugins/jqplot.barRenderer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.tabify-1.4.source.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/moment-with-langs.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$('#estacionBaseSelected')
								.change(
										function() {

											// You can access the value of your select field using the .val() method
											alert('Mostrar nivel de gases medidos en la estacion de '
													+ $(
															'#estacionBaseSelected option:selected')
															.text());

											// You can perform an ajax request using the .ajax() method
											$
													.ajax({
														type : "GET",
														url : "gestion/getGases/"
																+ $(
																		"#estacionBaseSelected option:selected")
																		.val(),
														dataType : "json",
														success : function(data) {
															$('#chart4').empty();
															$.jqplot.config.enablePlugins = true;
															$('#chart4')
																	.jqplot(
																			[ data ],
																			{
																				title : 'Nivel de Gases',

																				animate : !$.jqplot.use_excanvas,
																				series : [ {
																					neighborThreshold : 0
																				} ],
																				seriesDefaults : {
																					renderer : $.jqplot.BarRenderer,
																					pointLabels : {
																						show : true,
																						location : 'n',
																						stackedValue : true
																					},
																					rendererOptions : {
																						smooth : true,
																						animation : {
																							show : true
																						},
																						varyBarColor : true,
																						barPadding : 8, // number of pixels between adjacent bars in the same
																						// group (same category or bin).
																						barMargin : 4, // number of pixels between adjacent groups of bars.
																						barDirection : 'vertical', // vertical or horizontal.
																						barWidth : 25, // width of the bars.  null to calculate automatically.

																					},
																				},
																				axes : {
																					xaxis : {
																						renderer : $.jqplot.CategoryAxisRenderer
																					},
																					yaxis : {
																						min : 0,
																						 padMin: 0,
																						
																						tickOptions : {
																							suffix : " ppm"
																						}
																					}
																				},
																				cursor : {
																					show: true, 
																		            zoom: true
																				}

																			});
														},
														error : function(
																request,
																status, error) {
															/*alert("ERROR RECIBIDO: "
																	+ error
																	+ "\nSe ha producido un error al obtener los datos.\nIntroduzca de nuevo los parámetros y vuelva a intentarlo");*/
														}
													});

										});

						$
								.ajax({
									type : "GET",
									url : "gestion/getGases/"
											+ $(
													"#estacionBaseSelected option:selected")
													.val(),
									dataType : "json",
									success : function(data) {

										$.jqplot.config.enablePlugins = true;
										$('#chart4').empty();
										$('#chart4')
												.jqplot(
														[ data ],
														{
															title : 'Nivel de Gases',

															animate : !$.jqplot.use_excanvas,
															series : [ {
																neighborThreshold : 0
															} ],
															seriesDefaults : {
																renderer : $.jqplot.BarRenderer,
																pointLabels : {
																	show : true,
																	location : 'n',
																	stackedValue : true
																},
																rendererOptions : {
																	smooth : true,
																	animation : {
																		show : true
																	},
																	varyBarColor : true,
																	barPadding : 8, // number of pixels between adjacent bars in the same
																	// group (same category or bin).
																	barMargin : 4, // number of pixels between adjacent groups of bars.
																	barDirection : 'vertical', // vertical or horizontal.
																	barWidth : 25, // width of the bars.  null to calculate automatically.

																},
															},
															axes : {
																xaxis : {
																	renderer : $.jqplot.CategoryAxisRenderer
																},
																yaxis : {
																	min : 0,
																	padMin: 0,
																	
																	tickOptions : {
																		suffix : " ppm"
																	}
																}
															},
															cursor : {
																show: true, 
													            zoom: true
															}

														});
									},
									error : function(request, status, error) {
									/*	alert("ERROR RECIBIDO: "
												+ error
												+ "\nSe ha producido un error al obtener los datos.\nIntroduzca de nuevo los parámetros y vuelva a intentarlo");
									*/}
								});
						$
								.ajax({
									type : "GET",
									url : "http://"+window.location.host+"/M2MService/M2MRestService/alerts",
									dataType : "json",
									success : function(data) {
										json = JSON.stringify(data);
										var alertas = jQuery.parseJSON(json);
										var cList = $('.rectangle-list');
										if (alertas.length > 0) {
											cList.empty();
											$
													.each(
															alertas,
															function(i) {
																var li = $(
																		'<li/>')
																		.appendTo(
																				cList);
																var a = $(
																		'<a/>')
																		.html(
																				"Dispositivo: "
																						+ alertas[i].deviceId
																						+ " Fecha: "
																						+ parseISO8601(
																								alertas[i].fechaRegistro)
																								.toLocaleString()
																						+ " Parametro: "
																						+ alertas[i].parametro
																						+ " Valor: "
																						+ alertas[i].valor)
																		.appendTo(
																				li);
															});
										}
									}

								});

					});
	$(document).ready(function() {
		
		$.ajax({
			type : "GET",
			url : "http://"+window.location.host+"/M2MService/M2MRestService/assets",
			dataType : "json",
			success : function(data) {
				json = JSON.stringify(data);
				var resultados = jQuery.parseJSON(json);
				
				var cList = $('#informeSemanal ul');
				cList.empty();
				var estacion = 1;
				var chartNum = 0;
				$.each(resultados.data, function(i) {
					if(resultados.data[i].asset.name.toLowerCase().indexOf("contriego") >= 0){
						li=null;
						div =null;
						if(estacion==1){
							li = $('<li/>').addClass('active').appendTo(cList);
						}
						else{							
							li = $('<li/>').appendTo(cList);
						}
						
						aa = $('<a/>').attr('href','#estacion'+(estacion)+"-tab").html('Estación '+ (estacion) ).appendTo(li);
						
						name = resultados.data[i].asset.name;
						
						if(estacion==1){
							div = $('<div/>').addClass('contenidoSemanal').attr('id', 'estacion'+(estacion)).appendTo('#informeSemanal');

						}
						else{										
							div = $('<div/>').addClass('contenidoSemanal').attr('id', 'estacion'+(estacion)).appendTo('#informeSemanal');


						}
						
						chart =  $('<div/>').attr('id', 'chart'+(estacion)).attr('style',"height: 100%; width: 450px;").appendTo(div);	
						if(estacion==1){
							
						}else{
						}
						direccion = "gestion/informes/"+ estacion+"/contriego/volume/"+moment().subtract('days', 7).format("YYYY-MM-DD[T]hh:mm:ss[Z]")+"%3C%3E"+moment().format("YYYY-MM-DD[T]hh:mm:ss[Z]");
						estacion++;
						$.ajax({
							type : "GET", //name.replace( /^\D+/g, '')
							url : direccion,
							dataType : "json",
							success : function(data) {
								datos = new Array();

								for (var i = 0; i < data.length; i++) {
									datos.push(data[i]);
								}
								goog = eval(datos);

								addToPlot(goog);	
								
							}
						});
						function addToPlot(datos){
							
							arrayConfigParameters = {};
							var labels;
							if(datos.length!=0)
								labels=true;
							else labels= false;
							opts = {
									seriesColors : [ "#057C05" ],
									title : "Consumo de Agua",
									highlighter : {
										show : true,
										sizeAdjust : 4,
										tooltipOffset : 15
									},
									grid : {
										background : "#FFFFFF",
										drawBorder : true,
										shadow : false,
										gridLineColor : '#000000',
										gridLineWidth : 1
									},
									series : [ {
										neighborThreshold : 0,
										lineWidth : 4,
										showMarker : true
									} ],
									axesDefaults : {
										rendererOptions : {
											baselineWidth : 1,
											baselineColor : '#000000'
										}

									},
									axes : {
										xaxis : {
											renderer : $.jqplot.DateAxisRenderer,
											numberTicks : 6,
											tickOptions : {
												formatString : " %#d/%#m/%y",
												showLabel : labels
											},
											showTicks : labels
										},
										yaxis : {
											min : 0,
											padMin: 0,
											
											tickOptions : {
												suffix : " L",
											},
											showTicks : true
										}
									},
									cursor : {
										show: false, 
							            zoom: false
									}
									
								};
							
							var chartName = "chart" +(chartNum+1);
							var chartEstacionName = "#estacion" +(chartNum+1);
							if(datos.length!=0)
								$.jqplot(chartName, [ datos ], opts);
							else {
								$.jqplot(chartName, [[null]], opts);
							}
							console.log("NOMBRE: "+ chartName + " " + chartEstacionName);
							
							chartNum++;
							if(chartNum>1){
								$(chartEstacionName).attr('style','display:none;');
								
							}else{
								
							}
						};	
								
					};
				});
			}
		});
		
	});
	$(document).ready(function() {
		$('#menu').tabify();
	});
	 $(document).ready(function() {
		$("#ultimasAlertas").on('click','#actualizar',function() {
			$
			.ajax({
				type : "GET",
				url : "http://"+window.location.host+"/M2MService/M2MRestService/alerts",
				dataType : "json",
				success : function(data) {
					json = JSON.stringify(data);
					var alertas = jQuery.parseJSON(json);
					var cList = $('.rectangle-list');
					if (alertas.length > 0) {
						cList.empty();
						$
								.each(
										alertas,
										function(i) {
											var li = $(
													'<li/>')
													.appendTo(
															cList);
											var a = $(
													'<a/>')
													.html(
															"Dispositivo: "
																	+ alertas[i].deviceId
																	+ " Fecha: "
																	+ parseISO8601(
																			alertas[i].fechaRegistro)
																			.toLocaleString()
																	+ " Parametro: "
																	+ alertas[i].parametro
																	+ " Valor: "
																	+ alertas[i].valor)
													.appendTo(
															li);
										});
					}
				}

			});
		});
	});
	 $(document).ready(function() {
			$("#ultimasAlertas").on('click','#limpiar',function() {
				var cList = $('.rectangle-list');
				cList.empty();
			});
	 });
	function parseISO8601(str) {
		// we assume str is a UTC date ending in 'Z'

		var parts = str.split('T'), dateParts = parts[0].split('-'), timeParts = parts[1]
				.split('Z'), timeSubParts = timeParts[0].split(':'), timeSecParts = timeSubParts[2]
				.split('.'), timeHours = Number(timeSubParts[0]), _date = new Date;

		_date.setUTCFullYear(Number(dateParts[0]));
		_date.setUTCMonth(Number(dateParts[1]) - 1);
		_date.setUTCDate(Number(dateParts[2]));
		_date.setUTCHours(Number(timeHours));
		_date.setUTCMinutes(Number(timeSubParts[1]));
		_date.setUTCSeconds(Number(timeSecParts[0]));
		if (timeSecParts[1])
			_date.setUTCMilliseconds(Number(timeSecParts[1]));

		// by using setUTC methods the date has already been converted to local time(?)
		return _date;
	}
</script>
</head>
<body id="contenidoIndex">
	<div id="niveles">
		<h2>Niveles de Gases</h2>
		<div id="estacionSelector">
			<label style="font-weight: bold;">Estación Base</label> <select
				id="estacionBaseSelected">
				<option value="1/medpart/">La Rosaleda</option>
				<option value="2/medpart/">Plaza de Juan de Austria</option>
				<option value="3/medpart/">Parque de la Paz</option>
			</select>
		</div>
		<div id="chart4"
			style="margin-top: 10px; float: right; height: 80%; width: 100%;"></div>
	</div>
	<div id="informeSemanal">
		<h2>Consumo Agua Semanal</h2>
		<ul id="menu">

		</ul>
	</div>
	<div id="ultimasAlertas">
		<h2>Última Hora</h2>
		<img id="actualizar" class="button"
			src="<%=request.getContextPath()%>/resources/img/refresh.png"
			style="width: 25px; padding: 2px 4px" alt="" />
		<img id="limpiar" class="button"
			src="<%=request.getContextPath()%>/resources/img/trash.png"
			style="width: 25px; padding: 2px 4px" alt="" />
		<div style="height: 90%; width: 100%; overflow: auto;">
			<ol class="rectangle-list">
				<li><a href="">No se han registrado alertas</a></li>
			</ol>
		</div>
	</div>
	<div id="recomendaciones">
		<h2>Recomendaciones</h2>

	</div>
</body>
</html>
