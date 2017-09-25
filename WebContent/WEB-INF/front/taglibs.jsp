<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String frontPath = path + "/common/front";
%>
<c:choose>
	 <c:when test="${sessionScope.FRONT_USER_SESSION == null}">
	 	<c:set var="hasLogin" value="false"></c:set>
	 </c:when>
	 <c:otherwise>
	 	<c:set var="hasLogin" value="true"></c:set>
 	</c:otherwise>
</c:choose>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="frontPath" value="<%=frontPath%>"></c:set>
<c:set var="showTitle" value="${CACHE_SYS_CONFIG.sysWebsitename }"></c:set>
<c:set var="showKeywords" value="${CACHE_SYS_CONFIG.sysWebsitekeyword } 融宝科技 P2P-www.irongbao.com" />
<c:set var="description" value="${CACHE_SYS_CONFIG.sysWebsitedescribe } 融宝科技 P2P-www.irongbao.com" />

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="ie-stand" /><!-- 指定360使用IE标准内核 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	var $_path="${path}";
	var $_frontPath="${frontPath}";
	/* 百度统计代码  start */
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?3cff28daaf205d59c261e25c17eb8567";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
	/* 百度统计代码 end */
</script>

<!-- jquery -->
<script type="text/javascript" src="${frontPath }/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${frontPath }/js/jquery.js"></script>
<script type="text/javascript" src="${frontPath }/js/jquery.slide.js"></script>
<script type="text/javascript" src="${frontPath }/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${frontPath }/js/tab.js"></script>
<script type="text/javascript" src="${path }/common/js/common.js"></script>
<script type="text/javascript" src="${frontPath }/js/raphael.js"></script>
<script type="text/javascript" src="${frontPath }/js/BaseInfo.js"></script>
<script type="text/javascript" src="${frontPath }/js/page.js"></script>
<script type="text/javascript" src="${frontPath }/js/global-1.1.0.min.js"></script>

<script type="text/javascript" src="${frontPath }/js/content.js"></script>
<script type="text/javascript" src="${frontPath }/js/move2.js"></script><%-- <script type="text/javascript" src="${frontPath }/js/highcharts.js"></script> --%>
<script type="text/javascript" src="${frontPath }/js/jquery.SuperSlide.2.1.js"></script>
<script type="text/javascript" src="${frontPath }/js/common.js"></script>
<script type="text/javascript" src="${frontPath }/js/cebianlan.js"></script>
<script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
<script type="text/javascript" src="${frontPath }/js/ZeroClipboard.js"></script>

<script type="text/javascript" src="${path }/common/thirdParty/jquery.alert.v1.2/jquery.alert.js"></script>
<link rel="stylesheet" href="${frontPath }/js/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${frontPath }/js/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${frontPath }/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${frontPath }/js/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${frontPath }/js/city.js"></script>
<!-- My97DatePicker -->
<!-- 加密 -->
<script type="text/javascript" src="${path }/common/js/desencrypt.js"></script>
<script type="text/javascript" src="${path }/common/js/tripledes.js"></script>
<script type="text/javascript" src="${path }/common/js/mode-ecb.js"></script>
<!-- 验证 -->
<link rel="stylesheet" type="text/css" href="${path }/common/thirdParty/validate/validate.css" />
<script src="${path }/common/thirdParty/validate/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/common/thirdParty/validate/jquery.validate.message_cn.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="${path }/common/thirdParty/jquery.alert.v1.2/jquery.alert.css"/>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/basic.css"/>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/Jane-css.css"/>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/new.css"/>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/style.css"/>
<link rel="shortcut icon" href="${path }/favicon.ico" /> 