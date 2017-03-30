<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>

<!DOCTYPE html>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>M2M Dashboard Application Alerts Page</title>
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
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/moment.min.js"></script>
</head>
<SCRIPT type="text/javascript">
	$(document).ready(function() {

		$('.tablaAlertas .alertasRow').each(function() {
			var time = $(this).find('#td6').text();
			var timeParsed = parseISO8601(time);
			$(this).find('#td6').text(timeParsed.toLocaleString());

			var splitted = $(this).find('#td5').text().split(":");
			$(this).find('#td5').text(splitted[splitted.length - 1]);
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

	$(document).ready(function() {

		$(".alertasRow").click(function() {
			$("tbody tr.alertasRow").removeClass("highlighted");
			$(this).addClass("highlighted");
		});
	});
	$(document).ready(function() {
		$("#nuevo").click(function() {
			$(".dispositivosExpand").hide();
			$("#nuevoInfo").show();
		});
	});
	$(document)
			.ready(
					function() {
						$("#borrar")
								.click(
										function() {
											if ($(".alertasRow").hasClass(
													"highlighted")) {
												$(".dispositivosExpand").hide();
												var urlPeticion = $(
														"tbody tr.highlighted")
														.find("#td1").html();

												var r = confirm("Se va a eliminar la alerta: "
														+ urlPeticion);
												if (r == true) {
													$.support.cors = true;
													$.ajax({
													    type: 'DELETE',
													    beforeSend: function (request)
											            {   
											                request.setRequestHeader("token", "dashboardapplication");
											                request.setRequestHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");

											            },
													    url: "gestion/alerts/"+urlPeticion,
													    success: function(xml, textStatus, xhr) {
													        if(xhr.status==200){
													        	alert("Se ha eliminado la Alerta: " + urlPeticion);
													        	location.reload();
															    
													        }
													        else{alert("No se ha realizado correctamente");}
													    	console.log(xhr.status);
													    },
													    complete: function(xhr, textStatus) {
													    	
													        console.log(xhr.status);
													    } 
													    });
												} else {
												}

											}
										});
					});
$(document).ready(function() {$(".alertasForm").on('submit',function(e) {
    e.preventDefault();

											
		var alert = {
			"name" : $(
					'input[name=nombre]')
					.val(),
			"type" : $(
					'#typeSelected option:selected')
					.val(),
			"filter" : {
				"property" : $(
						'#propertySelected option:selected')
						.val(),
				"operator" : $(
						'#operatorSelected option:selected')
						.val(),
				"max" : $(
						'input[name=value]')
						.val(),
				"min" : $(
						'input[name=value]')
						.val(),
			},
			"resource" : {
				"assets" : [],
				"groups" : [
	
				]
			},
			"notifyURI" : "http://"+window.location.host+"/M2MService/M2MRestService/alerts"
		};
		if ($(
				'#operatorSelected option:selected')
				.val() == "<") {
			alert.filter.max = $(
					'input[name=value]')
					.val();
			alert.filter.min = "";
		} else {
			alert.filter.min = $(
					'input[name=value]')
					.val();
			alert.filter.max = "";
		}
		var assets = [];
		$(
				'#assetSelected :selected')
				.each(
						function(i,
								selected) {
							assets[i] = {
								"name" : $(
										selected)
										.val()
							};
						});
		alert.resource.assets = assets;
		$.support.cors = true;
		$.ajax({
		    type: 'POST',
		    url: "gestion/alerts/",
		    beforeSend: function (request)
            {											                
                request.setRequestHeader("token", "dashboardapplication");					                
                request.setRequestHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");

            },		
		    dataType:"json",
		    contentType: "application/json",
		    data: JSON.stringify(alert),
		    success: function(xml, textStatus, xhr) {
		    	if(xhr.status==200){
		        //	alert("Se ha registrado correctamente");
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
	<div class="alertas">
		<H1>ALERTAS</H1>
		<table class="tablaAlertas">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Identificador</th>
					<th style="width: 40%;">Filtro</th>
					<th>Tipo</th>
					<th>Propiedad</th>
					<th style="width: 30%;">Fecha de Creación</th>
				</tr>
			</thead>

			<tbody>
				<core:forEach var="alert" items="${requestScope.alerts.data}">
					<tr class="alertasRow">
						<td id="td1">${alert.name}
						<td id="td2">${alert.service}
						<td id="td3" style="width: 40%;"><b>Mínimo: </b>${alert.filter.min}
							<b>Operador: </b>${alert.filter.operator} <b>Máximo: </b>${alert.filter.max}
						<td id="td4">${alert.type}
						<td id="td5">${alert.filter.property}
						<td id="td6" style="width: 30%;">${alert.creationTime}
					</tr>
				</core:forEach>

			</tbody>
		</table>
		<br>
		<br>
		<div class="botones">
			<a id="nuevo" href="#" class="button">Nuevo</a> <a id="borrar"
				href="#" class="button">Borrar</a>
		</div>
		<div id="nuevoInfo" class="dispositivosExpand">
			<br>
			<br>
			<P>NUEVA ALERTA</P>
			<br>
			<div class="formularioDetalle">
				<form class="alertasForm" action="#" method="post">

					<label for="nombre">Nombre</label> <input type="text" name="nombre"
						required /> <label for="assetSelected">Dispositivo</label> <select
						id="assetSelected" multiple>
						<core:forEach var="asset" items="${requestScope.assets.data}">
							<option value="${asset.asset.name}">${asset.asset.name}</option>
						</core:forEach>
					</select> <label for="typeSelected">Tipo</label> <select id="typeSelected">
						<option value="Observation">Observación</option>
						<option value="Register">Registro</option>
					</select> <label for="propertySelected">Propiedad</label> <select
						id="propertySelected">
						
						<core:forEach var="property"
							items="${requestScope.medpart.data.capabilities}">
							<option class="medpart" value="${property.property}">${property.name}</option>
						</core:forEach>
						<core:forEach var="property"
							items="${requestScope.contriego_model.data.capabilities}">
							<option class="contriego_model" value="${property.property}">${property.name}</option>
						</core:forEach>
						<core:forEach var="property"
							items="${requestScope.estmeteo_model.data.capabilities}">
							<option  class="estmeteo_model" value="${property.property}">${property.name}</option>
						</core:forEach>
					</select> <label for="operatorSelected">Operador</label> <select
						id="operatorSelected">
						<option value="<">Menor</option>
						<option value=">">Mayor</option>
						<option value="=">Igual</option>
					</select> <label for="value">Valor</label> <input type="number" name="value"
						required step="0.000000001" />
					<button class="button" class="submit" type="submit">Enviar</button>
				</form>
			</div>
		</div>
	</div>
</body>