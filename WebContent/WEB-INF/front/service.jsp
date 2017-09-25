<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="taglibs.jsp"%>
<title>${showTitle }</title>
</head>
<body>
	<div class="dabao">
		<jsp:include page="/top.do"></jsp:include>
		<div style="background-image:url(${frontPath}/images/ny/fwzq_02.jpg);" class="ny-banner"></div>
		<div class="gywm-bao">
<div style="border:none; padding-bottom:50px;" class="gywm-nrbao">
<h2>热门问题</h2>
<div class="rmwt-nrk">

<div class="rmwt-leftk"> <ul>
  <c:forEach items="${contents}" var="content" begin="0" end="11">
  <li>
  <img width="4" height="4" src="${frontPath}/images/ny/dian_05.jpg">
  <a href="<c:url value='/content/contentDetails/28/${content.id }.do'/>">&nbsp;&nbsp;${content.content_title}</a></li>
    </c:forEach>  </ul>
  </div>
  
</div>
<h2>您想要了解三营的哪款理财产品</h2>
<div class="cp-k">
<span style="margin-left:150px;">
<a href="<c:url value='/content/contentDetails/28/64.do'/>" style="cursor: pointer;">
<img width="279" height="271" src="${frontPath}/images/ny/fwzq_05.jpg">
</a>
</span>
<span style="margin-right:0px;">
<a href="<c:url value='/content/contentDetails/28/63.do'/>" style="cursor: pointer;">
<img width="273" height="271" src="${frontPath}/images/ny/fwzq_07.jpg">
</a></span>
</div>
<h2>常见问题</h2>
<div class="cjwt-k">
  <div class="cjwt-nrk">
  <span><a href="<c:url value='/content/contentList/0/28.do'/>" style="cursor: pointer;"><img width="156" height="170" src="${frontPath}/images/ny/fwzq_12.jpg"></a></span>
   <span><a href="<c:url value='/content/contentDetails/29/66.do'/>" style="cursor: pointer;"><img width="156" height="172" src="${frontPath}/images/ny/fwzq_14.jpg"></a></span>
      <span><a href="<c:url value='/content/contentDetails/29/67.do'/>" style="cursor: pointer;"><img width="154" height="168" src="${frontPath}/images/ny/fwzq_23.jpg"></a></span>
        <span><a href="<c:url value='/content/contentDetails/29/68.do'/>" style="cursor: pointer;"><img width="157" height="170" src="${frontPath}/images/ny/fwzq_16.jpg"></a></span>
          <span><a href="<c:url value='/content/contentDetails/29/69.do'/>" style="cursor: pointer;"><img width="156" height="170" src="${frontPath}/images/ny/fwzq_18.jpg"></a></span>
            <span style="margin-right:0px;"><a href="<c:url value='/content/contentDetails/29/70.do'/>" style="cursor: pointer;"><img width="156" height="170" src="${frontPath}/images/ny/fwzq_20.jpg"></a></span>
  </div>
</div>
<h2>三营公开课</h2>
<div class="rmwt-nrk">
  <div class="rmwt-leftk"> <ul>
  <c:forEach items="${cons}" var="con" begin="0" end="11">
  <li>
  <img width="4" height="4" src="${frontPath}/images/ny/dian_05.jpg">
  <a href="<c:url value='/content/contentDetails/28/${con.id }.do'/>">&nbsp;&nbsp;${con.content_title}</a></li>
    </c:forEach>  </ul>
  </div>
  
</div>
<h2>一对一理财服务</h2>
<div class="lc-k">
  <div class="lc-left">
  <dl>
  <dd><img width="41" height="38" src="${frontPath}/images/ny/fwzq_32.jpg"></dd>
  <dt style=" font-size:18px;">在线客服</dt>
   <dt>工作时间：8:00-22:00</dt>
  </dl>
  </div>
  <div class="lc-left">
    <dl>
      <dd><img width="42" height="38" src="${frontPath}/images/ny/fwzq_35.jpg"></dd>
      <dt style=" font-size:18px;">${CACHE_SYS_CONFIG.sysWebsitetel}<span style="font-size:14px;">（仅收市话费）</span></dt>
      <dt>工作时间：8:00-22:00</dt>
    </dl>
  </div>
  <div class="lc-left">
    <dl>
      <dd><img width="43" height="38" src="${frontPath}/images/ny/fwzq_38.jpg"></dd>
      <dt style=" font-size:18px;"><a href="${path }/discussion/index.do">问答专区</a></dt>
      <dt></dt>
    </dl>
  </div>
</div>
</div>
</div>
		<jsp:include page="/foot.do"></jsp:include>
	</div>
</body>
<script type="text/javascript">
$(function() {
	getStatistics();
})
/**
 * 首页统计
 */
function getStatistics() {
	$.get("${path}/getStatistics.do", function(data, status) {
		var totleMoney=data.totleMoney.toFixed(2);
		$("#totleMoney").html("￥"+addCommas(totleMoney));
		var userNum=data.userNum;
		$("#userNum").html(addCommas(userNum));
	});
}
</script>
</html>