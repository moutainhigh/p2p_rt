<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="common/front/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="common/front/css/Jane-css.css"/>
	<title>浩铭金融</title>
</head>
<body>
	<div style="width: 1100px; margin: 0 auto; position: absolute; height: 500px; left: 50%; top: 50%; margin-left: -550px; margin-top: -200px;">
		<img src="tg001.png" class="j-tgimg" usemap="#planetmap"/>
		<map name="planetmap" id="planetmap">
		  <area shape="rect" coords="444,326,534,355" href ="javascript:history.go(-1);" style="cursor: pointer" />
		  <area shape="rect" coords="549,326,641,355" href ="<%=path%>" style="cursor: pointer" />
	    </map> 
	</div>
</body>
</html>
