<%-- <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
       		<c:forEach items="${childrenChannel }" var="chan">
			<c:choose>
				<c:when test="${chan.channelCode eq channelCode }">
					<!-- #ff6600 -->
						<a  style="color:red;"
							href="${path }/content/aboutUs/${chan.channelCode}.do">${chan.channelTitle
							}</a>
					

				</c:when>
				<c:otherwise>
					
						<a href="${path }/content/aboutUs/${chan.channelCode}.do">${chan.channelTitle
							}</a>
					
				</c:otherwise>

			</c:choose>
		</c:forEach>
         <li><a href="${path }/help">产品介绍</a></li>
         <li><a href="${path }/content/mcjs.do">名词解释</a></li>
         <li><a href="${path }/content/wzzf.html">网站资费</a></li>
         <li><a href="${path }/content/zcfg.do">政策法规</a></li>
         <li><a href="${path }/content/ysbh.do">隐私保护</a></li>
       </ul>
      </div>
     </div>
    </div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>
</html>
 --%>