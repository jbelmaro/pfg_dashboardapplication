<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>

<!DOCTYPE html>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>M2M Dashboard Application Devices Page</title>
<script type="text/javascript"
	src="http://www.google.com/jsapi?key=ABQIAAAAnP8JWZ5h8E7LFAD9NqVUwxSeX1v6nbhYXW-wX1PxoOWc4Ggu3BSJDJ9DsFY8gXkhAgs1GxBeYOlatg"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/moment-with-langs.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>\dhtmlwindow/windowfiles/dhtmlwindow.js"></script>
<link
	href='http://fonts.googleapis.com/css?family=Lato:100&subset=latin,latin-ext'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:900&subset=latin,latin-ext'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/dashboardApplicationStyle.css">
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBKIVBcatC_P62zHcoBt8kuaqI7DTVIr8c&sensor=false"></script>

</head>
<SCRIPT type="text/javascript">
	
	
		$(document).ready(
		    function(){
		        $("#detalle").click(function () {
		        	if( $(".dispositivosRow").hasClass("highlighted")){
		        	$(".dispositivosExpand").hide();
		        	$("#detalleInfo").show();
		        	$("#detalleInfo #dD1").text();
		        	var index = $("tbody tr.highlighted").index();
		        	var json ="";
		        	var urlPeticion = $("tbody tr.highlighted").find("#td1").html();
		        	        	
		        	$.ajax({
		        	    type: "GET",
		        	    url: "http://"+window.location.host+"/M2MService/M2MRestService/assets/"+urlPeticion,
		        	    dataType: "json",
		        	    contentType: "application/json",
		        	    success: function (data) {
							json = JSON.stringify(data.data);
							var device = jQuery.parseJSON(json);
				        	$("#detalleInfo #dD1").text(device.asset.name);
				        	$("#detalleInfo #dD2").text(device.name);
				        	$("#detalleInfo #dD3").text(device.model);
				        	$("#detalleInfo #dD5").text(device.status);
				        	$("#detalleInfo #dD6").text(JSON.stringify(data.data.DeviceProps.serialNumber));
				        	$("#detalleInfo #dD7").text(moment(device.registrationTime).format("DD MMMM YYYY, h:mm:ss a"));
				        	$("#detalleInfo #dD8").text(moment(device.creationTime).format("DD MMMM YYYY, h:mm:ss a"));
							$("#verHistorico").attr("rel",JSON.stringify(device.sensorData));
							
		        	    }

		        	});
		        	}
		        });
		    });
		$(document).ready(
		    function(){
		        $("#nuevo").click(function () {
		        	$(".dispositivosExpand").hide();
		        	$("#nuevoInfo").show();
		        });
		    });
		$(document).ready(
		    function(){
		        $("#borrar").click(function () {
		        	if( $(".dispositivosRow").hasClass("highlighted")){ 
			        	$(".dispositivosExpand").hide();
			        	var urlPeticion = $("tbody tr.highlighted").find("#td1").html();
			        	
			        	var r=confirm("Se va a eliminar el dispositivo: " + urlPeticion);
			        	if (r==true)
			        	  {
			        		$.support.cors = true;
			        		$.ajax({
							    type: 'DELETE',
							    beforeSend: function (request)
					            {
					                request.setRequestHeader("token", "dashboardapplication");
					                request.setRequestHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");

					            },
							    url: "gestion/devices/"+urlPeticion,
							    success: function(xml, textStatus, xhr) {
							        if(xhr.status==200){
							        	alert("Se ha eliminado el dispositivo: " + urlPeticion);
							        	location.reload();
									    
							        }
							        else{alert("No se ha realizado correctamente");}
						        	  console.log(xhr.status);
							    },
							    complete: function(xhr, textStatus) {
							        console.log(xhr.status);
							    } 
							});
			        		

			        	  }
			        	
			        	
			        }
		        });
		    });
		$(document).ready(
		    function(){
		        $("#activar").click(function () {
		        	if( $(".dispositivosRow").hasClass("highlighted")){ 
			        	$(".dispositivosExpand").hide();
			        	$("#activarInfo").show();
			        }
		        });
		    });
		$(document).ready(
		    function(){
		        $("#desactivar").click(function () {
		        	if( $(".dispositivosRow").hasClass("highlighted")){ 
			        	$(".dispositivosExpand").hide();
			        	$("#desactivarInfo").show();
			        }
		        });
		    });
		
		$(document).ready(function() {

		    $(".dispositivosRow").click(function() {
		    	$("tbody tr.dispositivosRow").removeClass("highlighted");
		        $(this).addClass("highlighted");
		        $(".dispositivosExpand").hide();
		        $('#resultadosHistorico').hide();
		    });
		});

		var mapCenter = new google.maps.LatLng(41.6411899, -4.7324666); //Google map Coordinates
	      var map;
	      var googleMapOptions;
	      var marker = new google.maps.Marker();
	      function initialize() //function initializes Google map
	      {
	        googleMapOptions =
	        {
	            center: mapCenter, // map center
	            zoom: 13, //zoom level, 0 = earth view to higher value
	            
	            zoomControl: true, //enable zoom control
	            zoomControlOptions: {
	                style: google.maps.ZoomControlStyle.SMALL //zoom control size
	             },
	            scaleControl: true, // enable scale control
	            mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
	        };
	        map = new google.maps.Map(document.getElementById("googleMap"), googleMapOptions);
	      }
	      google.maps.event.addDomListener(window, 'load', initialize);
	      
	        function addMyMarker(latitud,longitud) { //function that will add markers on button click
	        	
	        	$('#googleMapContainer').show();
	        	
	        	marker.setMap(null);
	        	google.maps.event.trigger(map, 'resize');
	        	map.setCenter(mapCenter);
	        	marker = new google.maps.Marker({
	                position:new google.maps.LatLng(latitud, longitud),
	                map: map,
	                animation: google.maps.Animation.DROP
	            });
	        	
	        }
	        $(document).ready(
	    		    function(){
	    		        $("#verHistorico").click(function () {
	    		        	$('#resultadosHistorico').show();
	    		        	var resultados = jQuery.parseJSON($("#verHistorico").attr("rel"));
	    		        	var cList = $('#resultadosHistorico ul');
	    		        	cList.empty();
	    		        	$('#resultadosHistorico h3').text("Histórico");
	    		        	$.each(resultados, function(i)
	    		        	{
	    		        	    var li = $('<li/>')
	    		        	        .addClass('ui-menu-item')
	    		        	        .attr('role', 'menuitem')
	    		        	        .appendTo(cList);
	    		        	    var aaa = $('<a/>')
	    		        	        .addClass('ui-all')
	    		        	        .html("<b>"+resultados[i].ms.p +":</b> " + resultados[i].ms.v +" "+resultados[i].ms.u)
	    		        	        .appendTo(li);
	    		        	});
	    		        });
	    		    });
	        $(document).ready(function(){
	        	
				$(".deviceForm").on('submit', function(e) { 
				    e.preventDefault();

					value = $('#estacionBaseSelected option:selected').val();
		        	loc = value.split(",");
					var device = {
      	    	          "name":  $('input[name=nombre]').val(),
      	    	          "model": $('#modelSelected option:selected').val(),
      	    	          "isConcentrator": false,
      	    	          "asset": {
      	    	        	  "name":  $('input[name=nombre]').val(),
      	    	        	  "description":  $('input[name=descripcion]').val(),
      	    	        	  },
      	    	          "DeviceProps":{
      	    	        	  "commandURL": "http://command.url",  
      	    	          },
      	    	          "location":{
      	    	        	  "latitude": parseFloat(loc[0]),
      	    	        	  "longitude": parseFloat(loc[1]),
      	    	          },
      	    		};
					$.support.cors = true;

					$.ajax({
					    type: 'POST',
					    beforeSend: function (request)
			            {					                
			                request.setRequestHeader("token", "dashboardapplication");
			                request.setRequestHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
						},
					    url: "gestion/devices/",
					    dataType:"json",
					    contentType: "application/json",
					    data: JSON.stringify(device),
					    success: function(xml, textStatus, xhr) {
					    	if(xhr.status==200){
					        	alert("Se ha registrado correctamente");
					        	location.reload();
							    
					        }
					        else{alert("No se ha realizado correctamente");}
					    	console.log(xhr.status);
					    },
					    complete: function(xhr, textStatus) {
					    	
					        console.log(xhr.status);
					    } 
					});
				});
	        });

	</SCRIPT>
<body>
	<div class="dispositivos">
		<H1>DISPOSITIVOS</H1>
		<table class="tablaDispositivos">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Descripción</th>
					<th>Modelo</th>
					<th>Localización</th>
					<th>Estado</th>
				</tr>
			</thead>

			<tbody>
				<core:if test="${requestScope.devices.count!=0}">
				<core:forEach var="device" items="${requestScope.devices.data}">
					<tr class="dispositivosRow">
						<td id="td1">${device.asset.name}
						<td id="td2">${device.asset.description}
						<td id="td3">${device.model}
						<td id="td4"><a id="getPosition" href="#"
							onClick="addMyMarker(${device.asset.location.latitude},${device.asset.location.longitude})">${device.asset.location.latitude}/${device.asset.location.longitude}</a>
						<td id="td5">${device.status}
					</tr>
				</core:forEach>
				</core:if>
			</tbody>
		</table>
		<br>
		<br>
		<div class="botones">
			<a id="detalle" href="#" class="button">Ver</a> <a id="nuevo"
				href="#" class="button">Nuevo</a> <a id="borrar" href="#"
				class="button">Borrar</a> <a id="activar" href="#" class="button">Activar</a>
			<a id="desactivar" href="#" class="button">Desactivar</a>
		</div>
		<br>
		<div id="detalleInfo" class="dispositivosExpand">
			<br>
			<br>
			<P>DETALLE</P>
			<div class="dispositivoDetalle">
				<table style="padding-bottom: 20px;">
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Identificador</th>
							<th>Modelo</th>
							<th>Localización</th>
							<th>Estado</th>
							<th>Nº de Serie</th>
							<th>Fecha de Registro</th>
							<th>Fecha de Creacion</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="dD1">
							<td id="dD2">
							<td id="dD3">
							<td id="dD4">
							<td id="dD5">
							<td id="dD6">
							<td id="dD7">
							<td id="dD8">
						</tr>
					</tbody>
				</table>
				<a id="verHistorico" class="button">Ver Último Registro</a>
				<div id="resultadosHistorico"
					style="display: none; margin-top: 20px;">
					<h3></h3>
					<ul id="listaResultados">
					</ul>
				</div>




			</div>
		</div>

		<div id="nuevoInfo" class="dispositivosExpand">
			<br>
			<br>
			<P>NUEVO DISPOSITIVO</P>
			<br>
			<div class="formularioDetalle">
				<form class="deviceForm" action="#" method="post">

					<label for="nombre">Nombre</label> <input type="text" name="nombre"
						required /> <label for="descripcion">Descripción</label> <input
						type="text" name="descripcion" required /> <label
						for="modelSelected">Modelo</label> <select id="modelSelected">
						<core:forEach var="model" items="${requestScope.models.data}">
							<option value="${model.name}">${model.name}</option>
						</core:forEach>
					</select>
					<label for="estacionBaseSelected" >Estación Base</label> 
					<select id="estacionBaseSelected">
						<option value="41.6519137,-4.7332118">La Rosaleda</option>
						<option value="41.638823,-4.742078">Plaza de Juan de Austria</option>
						<option value="41.6346014,-4.7260144">Parque de la Paz</option>
					</select>

					<button class="button" class="submit" type="submit"
						target="nuevoResult">Enviar</button>

				</form>
			</div>
			<div class="formularioResultado">
				<iframe id="nuevoResult" name="nuevoResult" frameborder="0" seamless></iframe>
			</div>
		</div>
		<div id="googleMapContainer" style="display: none">
			<div id="googleMap"
				style="position: absolute; right: 0; bottom: 20px; float: right; width: 450px; height: 300px; border: 10px solid; border-radius: 5px;"></div>
		</div>
	</div>
</body>