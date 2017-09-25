<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>${channel.channelTitle }-浩茗金融</title>
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/index.js"></script>
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
	<!--最新公告--><c:set var = 'pageSize' value="10"></c:set>
	<div class="J-helpcenter jmt30 jmb30">
	    <div class="J-hc-con nav-con clearfix">
	    	<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<c:forEach items="${channelList }" var="channels">
					<c:if test="${channel.id==channels.id }">
						${channels.channelTitle }
					</c:if>
				</c:forEach>
			</div>
			<div class="J-hcc-nav jfl J-aboutus-nav">
				<ul>
					<c:forEach items="${channelList }" var="channels">
						<li <c:if test="${channel.id==channels.id }"> class="choose"</c:if>>
							<c:if test="${channels.channelIsUrl==0 }">
								<a <c:if test="${channel.id==channels.id }"></c:if> <c:if test="${channel.id==channels.id }"> </c:if>  
									href="<c:url value='/content/contentList/0/${channels.id }.do'/>">${channels.channelTitle }</a>
							</c:if>
							<c:if test="${channels.channelIsUrl!=0 }">
								<a <c:if test="${channel.id==channels.id }"></c:if> href="<c:url value='${channels.channelUrl }'/>">${channels.channelTitle }</a>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
            <div class="J-hcc-con jfl jboxsize page">
                <!---J-hccc-nr--->
                <ul class="J-aboutus-con" id="innerpage">
                	<c:forEach items="${contents}" var= "item" varStatus="status">
	                		<c:if test="${item.external_link_title!=undefined&&item.external_link_title!=''&&item.external_link_title!='null'}">
	                			<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>" <c:if test="${status.index>=10 }">style="display:none;"</c:if>><em>&middot;</em><a href="${item.external_link_title }" target="_blank" class="clearfix"><p>${item.content_title }</p><span><fmt:formatDate value="${item.content_publish_time }" type="date"/></span></a></li>
	                		</c:if>
	                		<c:if test="${item.external_link_title=='null' || item.external_link_title==undefined || item.external_link_title==''}">
		                		<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>" <c:if test="${status.index>=10 }">style="display:none;"</c:if>><em>&middot;</em><a href="${path}/content/contentDetails/${channel.id }_${item.id }.html" target="_blank" class="clearfix"><p>${item.content_title }</p><span><fmt:formatDate value="${item.content_publish_time }" type="date"/></span></a></li>
                			</c:if>
                	</c:forEach>
                </ul>
                <div id="page" style="text-align: right;padding-right: 20px;">
                	<a href="javascript: lastPage();"><上一页</a>
                	<span id="pageIndex" style="color: #F7772D;"></span>/<span id="totalPages"></span>
					<a href="javascript: nextPage();">下一页></a>
				</div>
            </div>
        </div>
    </div>
    <!--尾部-->
    <jsp:include page="/foot.do"></jsp:include>
    <%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
        var paramsObject={};
        var channelId='${channel.id }';
        var list; // the list of data
        var records  = 0;
        var totalPages; // the total of pages
        var pageSize = '${pageSize}'; // each size of page
        var pageIndex = 1; // the index of current page
        
        paramsObject.channelId=channelId;

        $(function(){
        	records  = $("#innerpage").children().length;	
       		totalPages = Math.ceil(records / pageSize);
       		if(records == 0){
	        	$("#innerpage").html('<li>没有数据!</li>');
	        	$("#page").css('display','none');
       		}else{
       			$("#pageIndex").html(pageIndex);
       		}
        	$("#totalPages").html(totalPages);
        });
		       
		function nextPage() {
			pageIndex += 1;
			if (pageIndex >= totalPages) {
				pageIndex = totalPages;
			}
			$("#innerpage li").css('display','none');
			$("."+pageIndex).css('display','block');
			
			$("#pageIndex").html(pageIndex);
		}
		
		function lastPage() {
			pageIndex -= 1;
			if (pageIndex <= 1) {
				pageIndex = 1;
			}
			
			$("#innerpage li").css('display','none');
			$("."+pageIndex).css('display','block');

			$("#pageIndex").html(pageIndex);
		}
			
</script>
</html>