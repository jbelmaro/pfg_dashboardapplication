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
	src="<%=request.getContextPath()%>/js/moment-with-langs.js"></script>
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
	src="<%=request.getContextPath()%>/js/jQuery.download.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#fenomenos").hide();
		$("#particulas").hide();
		$("#riego").hide();

		$('#parametrosMenu').change(function() {
			if ($(this).val() == '0') {
				$("#fenomenos").show();
				$("#particulas").hide();
				$("#riego").hide();
				$("#parametrosMenu option[value='none']").hide();

			} else if ($(this).val() == '1') {
				$("#fenomenos").hide();
				$("#particulas").show();
				$("#riego").hide();
				$("#parametrosMenu option[value='none']").hide();
			} else if ($(this).val() == '2') {
				$("#fenomenos").hide();
				$("#particulas").hide();
				$("#riego").show();
				$("#parametrosMenu option[value='none']").hide();
			}

		});

	});

	$(document).ready(function() {
		$("#inicio").hide();
		$("#final").hide();
		$("#intervalo").hide();
		$("input[class='myradio']").click(function() {

			if (this.value == 0) {
				$("#intervalo").hide();
				$("#final").hide();
				$("#inicio").show();
				$("input[type='datetime-local']").val("mm/dd/yyyy --:-- --");
			} else if (this.value == 1) {
				$("#intervalo").hide();
				$("#inicio").hide();
				$("#final").show();
				$("input[type='datetime-local']").val("mm/dd/yyyy --:-- --");
			} else if (this.value == 2) {
				$("#final").hide();
				$("#inicio").hide();
				$("#intervalo").show();
				$("input[type='datetime-local']").val("mm/dd/yyyy --:-- --");
			}
		});

	});

	$(document)
			.ready(
					function() {

						$("#historico").hide();

						$("#queryValues")
								.on(
										'submit',
										function(e) {
											e.preventDefault();
											$("#chart3").html('');
											var medida;
											var dispositivo;
											var intervalo;

											if ($('#parametrosMenu').val() == 0) {
												medida = $('#fenomenoMenu')
														.val();
												dispositivo = "estmeteo";
											} else if ($('#parametrosMenu')
													.val() == 1) {
												medida = [];
												dispositivo = "medpart";
												$('#particulasMenu :selected')
														.each(
																function(i,
																		selected) {
																	medida[i] = $(
																			selected)
																			.val();
																});
											} else if ($('#parametrosMenu')
													.val() == 2) {
												//medida = "waterFlow";
												medida = "volume";

												dispositivo = "contriego";
											}

											var query = $('#location').val()
													+ "/" + dispositivo + "/"
													+ medida + "/";

											if ($('input[name=tiempo]:checked')
													.val() == 0) {
												intervalo = "!>"
														+ $(
																'input[name=fechaInicio]')
																.val() + ":00Z";
											} else if ($(
													'input[name=tiempo]:checked')
													.val() == 1) {
												intervalo = "!<"
														+ $(
																'input[name=fechaFinal]')
																.val() + ":00Z";
											} else if ($(
													'input[name=tiempo]:checked')
													.val() == 2) {
												intervalo = $(
														'input[name=fechaInicioIntervalo]')
														.val()
														+ ":00Z"
														+ "<>"
														+ $(
																'input[name=fechaFinalIntervalo]')
																.val() + ":00Z";
											}

											if (intervalo != null) {
												query += intervalo + "/";
											}

											$("#chart3 img").show();
											$.ajax({
														type : "GET",
														url : "gestion/informes/"
																+ query,
														dataType : "json",
														success : function(data) {
															datos = new Array();

															for (var i = 0; i < data.length; i++) {
																datos
																		.push(data[i]);
															}
															if (data.length==0){
																setTimeout(
																		gogo([[null]]),
																		4);
															}else{setTimeout(
																	gogo(datos),
																	4);
															}
															

															$('#historico').attr('data-content',data);
														},
														error : function(
																request,
																status, error) {
															alert("ERROR RECIBIDO: "
																	+ error
																	+ "\nSe ha producido un error al obtener los datos o no existen datos registrados.\nIntroduzca de nuevo los parámetros y vuelva a intentarlo");
														}
													});

										});
					});

	function gogo(datos) {
		$("#chart3 img").hide();
		$.jqplot.config.enablePlugins = true;
		// For these examples, don't show the to image button.

		$.jqplot._noToImageButton = true;

		goog = eval(datos);
		ySuffixTemp = " °C";
		ySuffixPresAtm = " hPa";
		ySuffixPluv = " mm/m²";
		ySuffixVeloc = " km/h";
		ySuffixGas = " ppm";
		ySuffixFlujo = " L";
		ySuffixLum = " lx";
		ySuffixPercent = " %";
		xFormatDay = " %#d/%#m/%y";
		xFormatHour = " %F %R";
		xFormatMonth = " %#m/%y";
		xFormatYear = " %Y";
		arrayConfigParameters = {};
		var labels;
		 if(datos.length!=0){
			 labels=true;
		 }else { labels=false;
		 }
		var datosFinal = new Array();

		if ($('#fenomenoMenu').val() == 'temperature') {
			arrayConfigParameters["ySuffix"] = ySuffixTemp;
			arrayConfigParameters["title"] = "Temperatura";
			$('#fenomenoMenu').val('none');
		} else if ($('#fenomenoMenu').val() == 'atmosphericPressure') {
			arrayConfigParameters["ySuffix"] = ySuffixPresAtm;
			arrayConfigParameters["title"] = "Presión Atmosférica";
			$('#fenomenoMenu').val('none');
		} else if ($('#fenomenoMenu').val() == 'rainfall') {
			arrayConfigParameters["ySuffix"] = ySuffixPluv;
			arrayConfigParameters["title"] = "Pluviosidad";
			$('#fenomenoMenu').val('none');
		} else if ($('#fenomenoMenu').val() == 'windSpeed') {
			arrayConfigParameters["ySuffix"] = ySuffixVeloc;
			arrayConfigParameters["title"] = "Velocidad del Viento";
			$('#fenomenoMenu').val('none');
		} else if ($('#parametrosMenu').val() == '1') {
			arrayConfigParameters["ySuffix"] = ySuffixGas;
			arrayConfigParameters["title"] = "Nivel de Calidad del Aire";
			//arrayConfigParameters["seriesRenderer"]=$.jqplot.BarRenderer;
			$('#fenomenoMenu').val('none');
		} else if ($('#parametrosMenu').val() == '2') {
			arrayConfigParameters["ySuffix"] = ySuffixFlujo;
			arrayConfigParameters["title"] = "Flujo de Agua";
			$('#fenomenoMenu').val('none');
		} else if ($('#fenomenoMenu').val() == 'lumix') {
			arrayConfigParameters.push([ "ySuffix", ySuffixLum ]);
			arrayConfigParameters["title"] = "Luminuosidad";
			$('#fenomenoMenu').val('none');
		} else if ($('#fenomenoMenu').val() == 'relativeHumidity') {
			arrayConfigParameters["ySuffix"] = ySuffixPercent;
			arrayConfigParameters["title"] = "Humedad Relativa";
			$('#fenomenoMenu').val('none');
		}
		arrayConfigParameters["xRenderer"] = $.jqplot.DateAxisRenderer;
		if ($('#visualizacion').val() == 'hour') {
			
			arrayConfigParameters["xFormat"] = xFormatHour;
			datosFinal = datos;

		} else if ($('#visualizacion').val() == 'day') {

			arrayFiltrado = new Array();
			j=0;
			cantidad=0;
			total=0;
			for(i=0;i<datos.length;i++){
				

				if(arrayFiltrado.length==0){
					arrayFiltrado[j] = datos[i];
					total=arrayFiltrado[j][1];
					cantidad++;
				}else{
					if((moment(datos[i][0]).days()==moment(arrayFiltrado[j][0]).days())&&
							(moment(datos[i][0]).months()==moment(arrayFiltrado[j][0]).months())&&
							(moment(datos[i][0]).years()==moment(arrayFiltrado[j][0]).years())){
						total+=datos[i][1];
						cantidad++;
						if((i+1)==datos.length){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
						}
					}
					else{
						if((total!=0)&&(cantidad!=0)){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
						else{
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
					}
				}
			}
			
			datosFinal = arrayFiltrado;
			
			arrayConfigParameters["xFormat"] = xFormatDay;
			
		} else if ($('#visualizacion').val() == 'month') {
			arrayFiltrado = new Array();
			j=0;
			cantidad=0;
			total=0;
			for(i=0;i<datos.length;i++){
				

				if(arrayFiltrado.length==0){
					arrayFiltrado[j] = datos[i];
					total=arrayFiltrado[j][1];
					cantidad++;
				}else{
					if((moment(datos[i][0]).months()==moment(arrayFiltrado[j][0]).months())&&
							(moment(datos[i][0]).years()==moment(arrayFiltrado[j][0]).years())){
						total+=datos[i][1];
						cantidad++;
						if((i+1)==datos.length){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
						}
					}
					else{
						if((total!=0)&&(cantidad!=0)){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
						else{
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
					}
				}
			}
			
			datosFinal = arrayFiltrado;
			arrayConfigParameters["xFormat"] = xFormatMonth;

		} else if ($('#visualizacion').val() == 'year') {
			arrayFiltrado = new Array();
			j=0;
			cantidad=0;
			total=0;
			for(i=0;i<datos.length;i++){
				

				if(arrayFiltrado.length==0){
					arrayFiltrado[j] = datos[i];
					total=arrayFiltrado[j][1];
					cantidad++;
				}else{
					if(moment(datos[i][0]).years()==moment(arrayFiltrado[j][0]).years()){
						total+=datos[i][1];
						cantidad++;
						if((i+1)==datos.length){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
						}
					}
					else{
						if((total!=0)&&(cantidad!=0)){
							total= total / cantidad;
							arrayFiltrado[j][1]=total;
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
						else{
							j++;
							arrayFiltrado[j] = datos[i];
							total=datos[i][1];
							cantidad=1;
						}
					}
				}
			}
			
			datosFinal = arrayFiltrado;
			arrayConfigParameters["xFormat"] = xFormatYear;

		}

		$("#historico").show();
		$("#visualizacion option[value='none']").hide();
		opts = {
			seriesColors : [ "#057C05" ],
			title : arrayConfigParameters.title,
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
			seriesDefaults : {
				renderer : arrayConfigParameters.seriesRenderer,
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
					barWidth : 15, // width of the bars.  null to calculate automatically.

				}
			},
			series : [ {
				neighborThreshold : 0,
				lineWidth : 4,
				showMarker : labels
			} ],
			axesDefaults : {
				rendererOptions : {
					baselineWidth : 1,
					baselineColor : '#000000'
				}

			},
			axes : {
				xaxis : {
					renderer : arrayConfigParameters.xRenderer,
					numberTicks : 6,
					tickOptions : {
						formatString : arrayConfigParameters.xFormat,
						showLabel : labels
					},
					tickInterval : arrayConfigParameters.tickInterval,
					showTicks : labels
				},
				yaxis : {
					min : 0,
					pad: 0,
					autoscale: true,
					tickOptions : {
						suffix : arrayConfigParameters.ySuffix
					}
				}
			},
			cursor : {
				show: true, 
	            zoom: true
			}

		};
		if (($('#parametrosMenu').val() == 1)
				&& ($('#particulasMenu :selected').length > 1)) {

			plot3 = $.jqplot('chart3', datosFinal, opts);
		} else if(datos!=[[null]]){

			plot3 = $.jqplot('chart3', [ eval(datosFinal) ], opts);
		} else{plot3 = $.jqplot('chart3', [[null]], opts); }
	}
	$(document)
	.ready(
			function() {
				
		$('#historico').on("click",function(){
			$.download($('#historico').attr("href"),$('#historico').attr('data-content') );
			/*$.ajax({
			    type: 'POST',
			    url: $('#historico').attr("href"),
			    contentType: "text/plain",
			    dataType:"application/xlsx",
			    data:  $('#historico').attr('data-content'),
			    success: function(data, textStatus, xhr) { alert(xhr.status); },
	
			    error:function(jqXHR, textStatus, errorThrown) {
			    	  alert(textStatus +" "+ jqXHR.status);
			    }
			});*/
		});
	});
</script>

</head>
<body>
	<H1>INFORMES</H1>
	<div class="formulario">
		<form id="queryValues" onsubmit="return false;">
			<fieldset style="padding: 3px;">
				<legend>Seleccione atributos para la consulta</legend>
				<label style="font-weight: bold;">Intervalo de tiempo</label> <br>
				<ul>
					<li><label for="intervalo">Intervalo</label> <input
						name="tiempo" class="myradio" type="radio" value="2" /></li>
					<li><label for="final">Final</label> <input name="tiempo"
						class="myradio" type="radio" value="1" /></li>
					<li><label for="inicio">Inicio</label> <input name="tiempo"
						class="myradio" type="radio" value="0" /></li>
				</ul>
				<div id="intervalo">

					<ul>
						<li><label>Inicio</label> <input name="fechaInicioIntervalo"
							type="datetime-local" /></li>
						<li><label>Final </label> <input name="fechaFinalIntervalo"
							type="datetime-local" /></li>
					</ul>
				</div>
				<div id="final">
					<label>Final</label> <input name="fechaFinal" type="datetime-local" />
				</div>
				<div id="inicio">
					<label>Inicio</label> <input name="fechaInicio"
						type="datetime-local" />
				</div>
				<div>
					<label style="font-weight: bold;">Estación</label> <select
						id="location">
						<option value="none">--</option>
						<option value="1">La Rosaleda</option>
						<option value="2">Plaza de Juan de Austria</option>
						<option value="3">Parque de la Paz</option>
						
					</select>
				</div>
				<div>
					<label style="font-weight: bold;">Visualización</label> <select
						id="visualizacion">
						<option value="hour">Hora</option>
						<option value="day">Día</option>
						<option value="month">Mes</option>
						<option value="year">Año</option>
					</select>
				</div>
				<div>
					<label style="font-weight: bold;">Parámetros</label> <select
						id="parametrosMenu">
						<option value="none">--</option>
						<option value="0">Fenómenos Atmosféricos</option>
						<option value="1">Medida de Partículas</option>
						<option value="2">Riego</option>
					</select>
				</div>
				<div id="fenomenos">
					<label>Fenómenos Atmosféricos</label> <select id="fenomenoMenu">
						<option value="none">--</option>
						<option value="temperature">Temperatura</option>
						<option value="atmosphericPressure">Presión Atmosférica</option>
						<option value="rainfall">Precipitación</option>
						<option value="windSpeed">Velocidad del Viento</option>
						<!--<option value="windDirection">Dirección del Viento</option>-->
						<option value="relativeHumidity">Humedad Relativa</option>
						<option value="lumix">Luminosidad</option>
					</select>
				</div>
				<div id="particulas">
					<label>Medida de Partículas</label> <select multiple
						id="particulasMenu">
						<option value="none">--</option>
						<option value="NO2Concentration">NO2</option>
						<!--  <option value="NOConcentration">NO</option>-->
						<option value="O2Concentration">O2</option>
						<option value="O3Concentration">O3</option>
						<option value="CH4Concentration">CH4</option>
						<option value="CO2Concentration">CO2</option>
						<option value="COConcentration">CO</option>
						<option value="GasConcentration">Gas</option>
					</select>
				</div>
				<div id="riego"></div>

				<input id="submitButton" type="submit" class="button" value="Enviar" />
			</fieldset>
		</form>
	</div>


	<div id="chart3" style="height: 450px; width: 670px; float: left;">
		<img
			style="float: left; position: relative; left: 50%; width: 100px; display: none;"
			src='<%=request.getContextPath()%>/resources/img/loading.gif'>
	</div>

	<button id="historico" class="button" href="<%=request.getContextPath()%>/gestion/getHistorico/">Generar Histórico</button>




</body>