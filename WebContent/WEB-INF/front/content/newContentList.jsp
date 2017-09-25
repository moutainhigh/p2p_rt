<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 分页条数 -->
<c:set var = 'pageSize' value="10"></c:set>
<c:choose>
	<c:when test="${channel.id == 76 }"><c:set var = 'channelUrl' value="zixun"></c:set></c:when>
	<c:when test="${channel.id == 77 }"><c:set var = 'channelUrl' value="zhuanti"></c:set></c:when>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${channel.channelTitle}-浩茗金融${showTitle }</title>
	<meta name="Keywords" content="${channel.keyword }">
	<meta name="Description" content="${channel.channelDescribe }">
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/newContentDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/newContentList.css" media="screen" />
</head>
<body>
	<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-banner"><img src="${path }/common/front/images/J-zxzt-001.png"></div>
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div>当前位置：
				<a href="${path }/">首页</a>
				><a>${channel.channelTitle }</a>
			</div>
			<div class="J-hcc-con jfl jboxsize page" style="width:74%;">
				<!---J-hccc-nr--->
				<ul class="J-aboutus-con" id="innerpage">
					<c:forEach items="${contents}" var="item" varStatus="status">
						<c:if test="${item.external_link_title!=undefined&&item.external_link_title!=''&&item.external_link_title!='null'}">
							<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>"
								<c:if test="${status.index>=10 }">style="display:none;"</c:if>>
								<a href="${item.external_link_title }" target="_blank" class="clearfix">
									<p><strong>${item.content_title }</strong></p><span><fmt:formatDate value="${item.content_add_datetime }" type="date" pattern="yyyy-MM-dd"/></span>
								</a>
								<div>
									<a href="${item.external_link_title }" target="_blank" class="clearfix">
										${item.content_summary }
									</a>
								</div>
							</li>
						</c:if>
						<c:if test="${item.external_link_title=='null' || item.external_link_title==undefined || item.external_link_title==''}">
							<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>"
								<c:if test="${status.index>=10 }">style="display:none;"</c:if>>
								<a href="${path}/${channelUrl }/${item.id }" target="_blank" class="clearfix">
									<p><strong>${item.content_title }</strong></p><span><fmt:formatDate value="${item.content_add_datetime }" type="date" pattern="yyyy-MM-dd"/></span>
								</a>
								<div>
									<a href="${path}/${channelUrl }/${item.id }" target="_blank" class="clearfix">
										${item.content_summary }
									</a>
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<div id="page" style="text-align: right; padding-right: 47px;">
					<a href="javascript: lastPage();"><上一页</a> 
					<span id="pageIndex" style="color: #F7772D;"></span>/<span id="totalPages"></span> 
					<a href="javascript: nextPage();">下一页></a>
				</div>
			</div>
			<div class="J-hcc-right jfl" style="width:25%;">
				<div class="right_div gonggao_div">
					<div class="right_div_title">
						<span>浩茗公告</span><a href="/notice" target="_blank">更多</a>
					</div>
					<div class="right_div_body">
						<ul id="aqbz"></ul>
					</div>
				</div>
				<div class="right_div">
					<div class="right_div_title">
						<span>理财风云榜</span><a href="javaScript:void(0);" style="cursor: default;">&nbsp;&nbsp;&nbsp;&nbsp;</a>
					</div>
					<div class="right_div_body paihang" id="zongList"></div>
			    </div>
			    <div class="right_div meiti_div">
					<div class="right_div_title">
						<span>媒体资讯</span><a href="/p2p_rt/report" target="_blank">更多</a>
					</div>
					<div class="right_div_body">
						<ul id="video"></ul>
					</div>
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
	$(function() {
		// 浩茗公告
		getContentList();
		//网站排行榜
		getUserTenderData();
	});

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
<script type="text/javascript" src="${frontPath }/js/script.js"></script>
</html>