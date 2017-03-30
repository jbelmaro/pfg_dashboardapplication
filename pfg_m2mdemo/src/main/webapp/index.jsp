<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>M2M Service Page</title>
        <script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAAnP8JWZ5h8E7LFAD9NqVUwxSeX1v6nbhYXW-wX1PxoOWc4Ggu3BSJDJ9DsFY8gXkhAgs1GxBeYOlatg"></script> 
    </head>
    <body>
    <script type="text/javascript">
    //<![CDATA[  
		    google.load('maps', '2');var map;	
    function tercero(){	
    if (GBrowserIsCompatible()) { 
        function createMarker(point,html) {
        var marker = new GMarker(point);
        GEvent.addListener(marker, "click", function() {
          marker.openInfoWindowHtml(html);});
        return marker;}			
      var map = new GMap2(document.getElementById("map3"));
      map.addControl(new GLargeMapControl());
      map.addControl(new GMapTypeControl());	  
      map.setCenter(new GLatLng(40.734771,-3.02948),13);}	  
      var point = new GLatLng(40.734771,-3.02948);
      var marker = createMarker(point,'Primer punto')
      map.addOverlay(marker);
      var point = new GLatLng(40.734771,-3.01248);
      var marker = createMarker(point,'La Universidad de la Habana')
      map.addOverlay(marker);}
    //]]>
    </script>
    <input style="margin:12px 0 12px 0;font-size:18px;padding:4px;" type="button" value="Cargar mapa" onclick="tercero()">

	
	
	<div id="map3" style="width:500px;height:300px;border:2px solid violet;"></div>

	<h1>Create Request</h1>
	     <form method="POST" action="<%= request.getContextPath()%>/M2MServlet" target="result">
      
	<select name="path">
	 <option value="devices">Device</option> 
	 <option value="assets">Asset</option> 
	 <option value="models">Model</option> 
	 </select>
		  <h4>Request:</h4>
        <textarea name="create" rows="6" cols="40"></textarea><br/>
        <input type="submit">
 	</form>
    <br/>
    <h1>Get Data</h1>
    	 <form method="GET" action="<%= request.getContextPath()%>/M2MServlet" target="result">
    	 	<select name="pathGet">
				 <option value="devices">Device</option> 
				 <option value="assets">Asset</option> 
				 <option value="models">Model</option> 
			 </select>
			 <input type="submit">
    	 </form>
    	 
    <br/>
    <h2>Response:</h2>
    <iframe id= "result" name="result">
    </iframe>
    
    </body>
</html>
