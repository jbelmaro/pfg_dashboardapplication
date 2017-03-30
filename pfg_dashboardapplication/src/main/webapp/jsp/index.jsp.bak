<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>

<!DOCTYPE html>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>M2M Dashboard Application Page</title>
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
	src="<%=request.getContextPath()%>/dhtmlwindow/windowfiles/dhtmlwindow.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>

<script type="text/javascript">
	// Llamado cuando se cargue la página
	posicionarMenu();

	$(window).scroll(function() {
		posicionarMenu();
	});

	function posicionarMenu() {
		var altura_del_header = $('header').outerHeight(true);
		var altura_del_menu = $('nav').outerHeight(true);

		if ($(window).scrollTop() >= altura_del_header) {
			$('nav').addClass('fixed');
			$('.content').css('margin-top', (altura_del_menu) + 'px');
		} else {
			$('nav').removeClass('fixed');
			$('.content').css('margin-top', '0');
		}
	}

	$(document).ready(function() {
		$(".menu").click(function() {
			var url = $(this).attr('rel');
			if ($("#menu_logged").length > 0){
				if($.cookie("USER")!=null){
					$("#principal").attr('src', url);
					}
				else{
					location.reload();
				}
			}else{
				$("#principal").attr('src', url);

			}		
		});

	});
	$(document).ready(function() {
		$("#login").submit(function(e) {
			
			//var posted = $.post( "http://10.95.14.162:8080/M2MService/j_spring_security_check", $( "#login" ).serialize());
			e.preventDefault();
			$.ajax({			
				type : "POST",
				url : "gestion/login?username="
						+ $("input[name='j_username']").val()
						+ "&password="
						+ $("input[name='j_password']").val(),
				success : function(data) {
					$.cookie("USER", $("input[name='j_username']").val());
					location.reload();
				},
				error : function(request, status, error) {
					location.reload();
					alert("Login erróneo");
				},
			});

			return false;
		});
	});
</script>
</head>
<body>
	<div class="container">
		
		<header>
			<core:choose>
				<core:when test="${cookie.USER.value != null}">
					<%@ include file="/jspf/header.jspf"%>
					<%@ include file="/jspf/logout.jspf"%>
				</core:when>
				<core:otherwise>
					<%@ include file="/jspf/header.jspf"%>

					<%@ include file="/jspf/login.jspf"%>
				</core:otherwise>
			</core:choose>
		</header>
		<nav>
			<core:choose>
				<core:when test="${cookie.USER.value != null}">
					<%@ include file="/jspf/menu_logged.jspf"%>

				</core:when>
				<core:otherwise>
					<%@ include file="/jspf/menu_not_logged.jspf"%>
				</core:otherwise>
			</core:choose>
		</nav>
		<%@ include file="/jspf/contenido.jspf"%>
		<%@ include file="/jspf/pie.jspf"%>




	</div>
</body>
</html>
