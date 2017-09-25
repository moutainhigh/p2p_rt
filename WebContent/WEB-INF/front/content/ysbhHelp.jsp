<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/Jane-css.css"/>
    
    <script type="text/javascript" src="${frontPath }/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${frontPath }/jS/jquery.SuperSlide.2.1.js"></script>
	<script type="text/javascript" src="${frontPath }/js/common.js"></script>
    <script type="text/javascript" src="${frontPath }/js/cebianlan.js"></script>
    <script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
<style type="text/css">
.on{
background-color:#ee2121;
color:white;
}
</style>
</head>
<body>
<!--头部-->
<jsp:include page="/top.do"></jsp:include>
<div class="about-banner"></div>
<!--广告--><!--内容-->
<!--最新公告-->
    <div class="J-helpcenter jmt30 jmb30">
     <div class="J-hc-con nav-con clearfix">
      <div class="J-hcc-nav jfl">
       <h3>帮助中心</h3>
       <ul>
         <li><a href="${path }/help">产品介绍</a></li>
       <%--   <li><a href="${path }/content/mcjs.do">名词解释</a></li> --%>
         <li><a href="${path }/content/wzzf.html">网站资费</a></li>
        <%--  <li><a href="${path }/content/zcfg.do">政策法规</a></li>
         <li><a href="${path }/content/ysbh.do" class="select">隐私保护</a></li> --%>
       </ul>
      </div>
     <div class="J-hcc-con jfl jboxsize">
       <h1>隐私保护</h1>
       <div class="J-hccc-nr J-hc-protection">
        <h6  title="浩茗金融严格遵守国家相关法律法规，对用户的隐私信息进行严格的保护。">浩茗金融严格遵守国家相关法律法规，对用户的隐私信息进行严格的保护。</h6>
        <ul>
         <li>1、我们采用业界最先进的加密技术，用户的注册信息、账户收支信息都已进行高强度的加密处理，不会被不法分子窃取到。</li>
         <li>2、浩茗金融设有严格的安全系统，未经允许的员工不可获取您的相关信息。</li>
         <li>3、浩茗金融绝不会将您的账户信息、银行信息以任何形式透露给第三方。</li>
        </ul>
        <dl>
         <dt>个人信息安全：</dt>
         <dd>浩茗金融是一个实名认证平台，浩茗金融会保证用户信息隐私的安全，用户在平台上交流的过程中，也要时刻注意保护个人隐私，截图注意覆盖个人信息，不要透露真实姓名与住址等，以防个人信息被盗取造成损失。</dd>
        </dl>
        
       </div>
       <!---J-hccc-nr--->
      </div>
     </div>
    </div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>
</html>
