<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Expires" content="-1">               
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache"> 
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
   <%@include file="taglibs.jsp"%>
    <title>${showTitle }-邮箱认证</title>
      <link rel="stylesheet" type="text/css" href="${configjscss }/css/jquery.mobile-1.4.2.min.css">
	<link rel="stylesheet" type="text/css" href="${configjscss }/css/common.css">
    <script type="text/javascript" src="${configjscss }/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${configjscss }/js/jquery.mobile-1.4.2.min.js"></script>
    </head>
    <body>
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page" style="background:#edf1f4;">
    <c:if test="${code == '0' }">
      <div class="header">
        <a class="back_arr" href="javascript:void(0);" onclick="history.back(-1)"></a>
        <h1>激活成功</h1>
      </div><!-- header -->
      <section>
       <div class="j-jhcg">
        <img src="${configjscss }/images/icon_txcg.png"/>
        <p>恭喜您，邮箱激活成功！</p>
       </div>
      </section>
     </c:if>
     
      <c:if test="${code == '-1' }">
      <div class="header">
        <a class="back_arr" href="javascript:void(0);" onclick="history.back(-1)"></a>
        <h1>激活失败</h1>
      </div><!-- header -->
      <section>
       <div class="j-jhcg">
        <img src="${configjscss }/images/icon_txcg2.png"/>
        <p>${message }</p>
       </div>
      </section>
     </c:if>
    </div>
    </body>
</html>