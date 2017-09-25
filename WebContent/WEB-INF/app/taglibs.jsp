<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String pathWeb = request.getContextPath()+"/mobile";
	String pathApp= request.getContextPath()+"/mobile";
	String configjscss = path + "/common/app";
%>

<c:set var="path" value="<%=path%>"></c:set>
<c:set var="pathWeb" value="<%=pathWeb%>"></c:set>
<c:set var="configjscss" value="<%=configjscss%>"></c:set>
<c:set var="pathApp" value="<%=pathApp%>"></c:set>
<script type="text/javascript">
	var path = "${path}/mobile/";
	var paths = "${path}";
</script>
<c:set var="showTitle" value="${CACHE_SYS_CONFIG.sysWebsitename }"></c:set>

<link rel="stylesheet" type="text/css" href="${configjscss }/css/basic.css">
<link rel="stylesheet" type="text/css" href="${configjscss }/css/common.css">
<link rel="stylesheet" type="text/css" href="${configjscss }/css/jquery.mobile-1.4.2.min.css">


<script type="text/javascript" src="${configjscss }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${configjscss }/js/jquery.mobile-1.4.2.min.js"></script>



	
