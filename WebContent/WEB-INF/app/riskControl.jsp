<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>   
<!DOCTYPE html>
<html lang="en">
    <head>
    <meta charset="utf-8">
    <%@include file="taglibs.jsp"%>
    <title>风险控制</title>
    <meta http-equiv="Expires" content="-1">               
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache"> 
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${configjscss }/css/jquery.mobile-1.4.2.min.css">
	<link rel="stylesheet" type="text/css" href="${configjscss }/css/common.css">
    <script type="text/javascript" src="${configjscss }/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${configjscss }/js/jquery.mobile-1.4.2.min.js"></script>
    </head>
    <body>
    <div class="ui-page ui-page-theme-a ui-page-active cz" data-role="page" >
      <!-- <div class="header">
        <a class="back_arr" href="javascript:void(0);"  onclick="history.back(-1)"></a>
        <h1>风险控制</h1>
      </div>header
       -->
      <!---滴滴 star---->
       <div class="jdd-dd">
        <a rel="external" href="${pathApp }/content/appGetH5Content.do?contentId=principalAndInterestSecurity"><img src="${configjscss }/images/jdd001.png"/></a>
        <a rel="external" href="${pathApp }/content/appGetH5Content.do?contentId=moneySafe"><img src="${configjscss }/images/jdd002.png"/></a>
        <a rel="external" href="${pathApp }/content/appGetH5Content.do?contentId=riskControlSystem"><img src="${configjscss }/images/jdd003.png"/></a>
       </div>
      <!---滴滴 end---->
      
       
    </div>
   
    </body>
</html>