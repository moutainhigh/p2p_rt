<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${channelDetails.channelTitle }-浩茗金融${showTitle }</title>
	<meta name="Keywords" content="关于我们">
	<meta name="Description" content="浩茗金融（www.hmjr99.com）是国内首家消费品供应链金融投资平台，项目来源为一级消费品龙头企业，资金流向安全稳定，能有效帮助投资者实现资金流动，在短期内获得高利收益。">
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/index.js"></script>
	<link type="text/css" rel="stylesheet" href="${frontPath }/css/Jane-css.css"/>
</head>
<body>
	<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
	<div class="about-banner"></div>
	<!--广告--><!--内容-->
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<c:forEach items="${channelList }" var="channels">
					<c:if test="${channelDetails.id==channels.id }">
						<a>${channels.channelTitle }</a>
					</c:if>
				</c:forEach>
			</div>
			<div class="J-hcc-nav jfl J-aboutus-nav">
				<ul>
					<c:forEach items="${channelList }" var="channels">
						<li <c:if test="${channelDetails.id==channels.id }"> class="choose" </c:if>>
							<c:if test="${channels.channelIsUrl==0 }">
								<a  <c:if test="${channelDetails.id==channels.id }"></c:if> href="<c:url value='/content/contentList/0/${channels.id }.do'/>">${channels.channelTitle }</a>
							</c:if>
							<c:if test="${channels.channelIsUrl!=0 }">
								<a  <c:if test="${channelDetails.id==channels.id }"></c:if>  href="<c:url value='${channels.channelUrl }'/>">${channels.channelTitle }</a>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="J-hcc-con jfl jboxsize j-newgsjj">
				<h1 class="hide">${channelDetails.channelTitle }</h1>
				${channelDetails.channelContent }
			</div>
		</div>
	</div> 
	<!--尾部-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	$(function(){	
		changeTopHover(6);
		getStatistics();
	});
</script>
</html>
