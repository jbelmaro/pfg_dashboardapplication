<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>

<!DOCTYPE html>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>M2M Dashboard Application Stations Page</title>
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
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBKIVBcatC_P62zHcoBt8kuaqI7DTVIr8c&sensor=false"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.0.3.js"></script>
<script>
	function initialize() {
		$
				.ajax({
					type : "GET",
					url : "http://"+window.location.host+"/M2MService/M2MRestService/devices",
					dataType : "json",
					contentType : "application/json",
					success : function(data) {

						
						var googleMapOptions = {
							zoom : 3, //zoom level, 0 = earth view to higher value

							zoomControl : true, //enable zoom control
							zoomControlOptions : {
								style : google.maps.ZoomControlStyle.SMALL
							//zoom control size
							},
							scaleControl : true, // enable scale control
							mapTypeId : google.maps.MapTypeId.ROADMAP
						// google map type
						};
						var map = new google.maps.Map(document
								.getElementById('googleMap'), googleMapOptions);
						var bounds = new google.maps.LatLngBounds();

						locationRegistered = new Array();
						for ( var i in data.data) {
							var html = "<p>Dispositivo: </p><a id=\"verHistorico\" class=\"menu\" href=\"#\" onclick=\"myFunction('"
									+ data.data[i].asset.name
									+ "')\" rel=\""
									+ data.data[i].asset.name
									+ "\">"
									+ data.data[i].asset.name + "</a>";
							var loc = [ data.data[i].asset.location.latitude,
									data.data[i].asset.location.longitude, html ];
							var exist = 0;
							for ( var j in locationRegistered) {
								if ((loc[0] == locationRegistered[j][0])
										&& (loc[1] == locationRegistered[j][1])) {
									exist = 1;
									var texto = locationRegistered[j][2];
									texto += "<br>" + html;
									locationRegistered[j][2] = texto;

								}
								;
							}
							if (exist == 0) {

								locationRegistered.push(loc);
							}
							;

						}
						for ( var i in locationRegistered) {
							//var latlng = new google.maps.LatLng(p[0], p[1]);
							latlng = new google.maps.LatLng(
									locationRegistered[i][0],
									locationRegistered[i][1]);
							bounds.extend(latlng);

							marker = new google.maps.Marker({
								map : map,
								position : latlng,
								clickable : true,
								animation : google.maps.Animation.DROP
							});
							marker.html = locationRegistered[i][2];
							infoWindow = new google.maps.InfoWindow({maxWidth: 300, maxHeight: 300});
							google.maps.event
									.addListener(
											marker,
											'click',
											function() {
												infoWindow
														.setContent("<div style='height:100px;width:200px'>"
																+ this.html
																+ "</div>");
												infoWindow.open(map, this);
											});

						}
						map.fitBounds(bounds);

					}
				});

	}

	google.maps.event.addDomListener(window, 'load', initialize);

	function myFunction(dispositivo) {

		$('#resultadosHistorico').show();
		$.ajax({
			type : "GET",
			url : "http://"+window.location.host+"/M2MService/M2MRestService/assets/"
					+ dispositivo,
			dataType : "json",
			success : function(data) {
				json = JSON.stringify(data);
				var resultados = jQuery.parseJSON(json);
				var cList = $('#resultadosHistorico ul');
				cList.empty();
				$('#resultadosHistorico h3').text("Último Registro");
				$.each(resultados.data.sensorData, function(i) {
					var li = $('<li/>').addClass('ui-menu-item').attr('role',
							'menuitem').appendTo(cList);
					var aaa = $('<a/>').addClass('ui-all').html(
							"<b>" + resultados.data.sensorData[i].ms.p
									+ ":</b> "
									+ resultados.data.sensorData[i].ms.v + " "
									+ resultados.data.sensorData[i].ms.u)
							.appendTo(li);
				});
			}
		});
	}
</script>
</head>

<body>
	<div class="estaciones">
		<H1>ESTACIONES</H1>
		<div id="googleMap"
			style="float: left; width: 60%; height: 600px; border: 10px solid; border-radius: 5px;"></div>
		<div id="resultadosHistorico"
			style="float: right; display: none; margin-top: 20px;">
			<h3></h3>
			<ul id="listaResultados">
			</ul>
		</div>
	</div>
</body>