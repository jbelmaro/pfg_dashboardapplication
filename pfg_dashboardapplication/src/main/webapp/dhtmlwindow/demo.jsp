<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core"%>
<%@ page import="java.net.URL" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<link rel="stylesheet" href="windowfiles/dhtmlwindow.css" type="text/css" />

<script type="text/javascript" src="windowfiles/dhtmlwindow.js">

/***********************************************
* DHTML Window Widget- ï¿½ Dynamic Drive (www.dynamicdrive.com)
* This notice must stay intact for legal use.
* Visit http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>

<body>

<div id="somediv" style="display:none">
<p style="height: 400px">This is some content within a DIV, shown inside this window instead</p>
</div>

<!-- 1) DHTML Window Example 1: -->

<script type="text/javascript">

var googlewin=dhtmlwindow.open("googlebox", "iframe", "<%=request.getContextPath()%>/GETTID_LoginAction.do", "Servery", "width=590px,height=350px,resize=1,scrolling=1,center=1", "recal")

googlewin.onclose=function(){ //Run custom code when window is being closed (return false to cancel action):
return window.confirm("Close window 1?")
}

</script>

<p>Play around with Window 1 (iframe content)</p>
<ul>
<li><a href="#" onclick="googlewin.show(); return false">Show Window 1</a></li>
<li><a href="#" onclick="googlewin.hide(); return false">Hide Window 1</a></li>
<li><a href="#" onclick="googlewin.load('iframe', 'http://www.cssdrive.com', 'CSS Drive'); return false">Change Window's URL to CSS Drive</a></li>
<li><a href="#" onclick="googlewin.setSize(800, 500); return false">Resize Window to 800px by 600px</a></li>
</ul>


<!-- 2) DHTML Window Example 2: -->

<script type="text/javascript">

var inlinewin=dhtmlwindow.open("broadcastbox", "inline", "This is some inline content. <a href='http://dynamicdrive.com'>Dynamic Drive</a>", "#2: Broadcast Title", "width=300px,height=120px,left=150px,top=10px,resize=1,scrolling=0", "recal")

</script>

<p>Play around with Window 2 (inline content)</p>
<ul>
<li><a href="#" onclick="inlinewin.show(); return false">Show Window 2</a></li>
<li><a href="#" onclick="inlinewin.load('inline', 'The content in this window has changed!', 'New Broadcast Title'); return false">Change Inline content for Window 2</a></li>
<li><a href="#" onclick="inlinewin.moveTo(400, 200); return false">Move Window 2</a></li>
</ul>


<!-- 3) DHTML Window Example 3: -->

<p>Play around with Window 3 (Ajax content)</p>

<script type="text/javascript">
function openmypage(){ //Define arbitrary function to run desired DHTML Window widget codes
ajaxwin=dhtmlwindow.open("ajaxbox", "ajax", "<%=request.getContextPath()%>/resources/img/fotoshopFlechaDes.png", "#3: Ajax Win Title", "width=450px,height=300px,left=300px,top=100px,resize=1,scrolling=1")
ajaxwin.onclose=function(){return window.confirm("Close window 3?")} //Run custom code when window is about to be closed
}
</script>
<ul>
<li><b><a href="#" onclick="openmypage(); return false">Create/ Open Window 3</a> (in IE, this Ajax demo must be run online!)</b></li>
<li><a href="#" onclick="ajaxwin.moveTo('middle', 'middle'); return false">Center Window 3</a></li>
<li><a href="#" onclick="ajaxwin.load('ajax', 'windowfiles/external2.htm', 'New Ajax Page'); return false">Load another page's content in Window 3</a></li>
<li><a href="#" onclick="ajaxwin.show(); return false">Show Window 3</a></li>
</ul>




<!-- 4) DHTML Window Example 4: -->

<p>Play around with Window 4 (Content from a DIV on page)</p>
<ul>
<li><a href="#" onclick="divwin=dhtmlwindow.open('divbox', 'div', 'somediv', '#4: DIV Window Title', 'width=450px,height=300px,left=200px,top=150px,resize=1,scrolling=1'); return false"><b>Create/ Open Window 4</b></a></li>
<li><a href="#" onclick="divwin.isResize(false); return false">Disable Window 4 Resize</a></li>
<li><a href="#" onclick="divwin.isScrolling(false); return false">Disable Window 4 Scrollbars</a></li>
<li><a href="#" onclick="divwin.show(); return false">Show Window 4</a></li>
</ul>

<h3><a href="http://www.dynamicdrive.com/dynamicindex8/dhtmlwindow/">DHTML Window Widget- Download and documentation</a></h3>

