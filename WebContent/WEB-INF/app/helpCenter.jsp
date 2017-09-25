<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<%@include file="taglibs.jsp"%>
<title>${showTitle }-帮助中心</title>
<link rel="stylesheet" type="text/css"
	href="${configjscss }/css/jquery.mobile-1.4.2.min.css">
<link rel="stylesheet" type="text/css"
	href="${configjscss }/css/common.css">
<script type="text/javascript"
	src="${configjscss }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${configjscss }/js/jquery.mobile-1.4.2.min.js"></script>
</head>
 <body>
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page" style="padding-bottom: 5.4rem;" >
    <!--   <div class="header">
        <a class="back_arr" href="javascript:;" onclick="history.back(-1)"></a>
        <h1>帮助中心</h1>
      </div>header -->

      <div class="xxz_con">   
        <ul>
          <li>
          <div  style="cursor:pointer;" onclick="javascript:window.location.href='${ pathWeb}/content/appGetH5Content.do?contentId=newUserGuide'">
            <h5><a rel="external" href="javascript:;"   class="txt_l" >新手必读</a><span><img src="${configjscss }/images/icon_bzzx.png"></span></h5>
           </div>
          </li>
        <li>
            <h5><a rel="external" href="#" class="txt_l" >产品介绍</a><span><img src="${configjscss }/images/icon_bzzx.png"></span></h5>
           
          </li>
           <li>
            <h5><a rel="external" href="#" class="txt_l" >注册与绑卡</a><span><img src="${configjscss }/images/icon_bzzx.png"></span></h5>
           
          </li>
          <li>
            <h5><a rel="external" href="#" class="txt_l" >投资操作</a><span><img src="${configjscss }/images/icon_bzzx.png"></span></h5>
           
          </li>
            <li>
            <h5><a rel="external" href="#" class="txt_l" >收益保障</a><span><img src="${configjscss }/images/icon_bzzx.png"></span></h5>
           
          </li>
        </ul>
      </div><!-- xxz_con -->
    </div>
    </body>
</html>