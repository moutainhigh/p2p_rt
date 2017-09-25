<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 分页条数 -->
<c:set var = 'pageSize' value="10"></c:set>
<c:choose>
	<c:when test="${channel.id == 77 }"><c:set var = 'currentChannelUrl' value="zhuanti"></c:set></c:when>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${channel.channelTitle}-浩茗金融${showTitle }</title>
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
			<div class="J-hcc-con jfl jboxsize page" style="width:72%;">
				<!---J-hccc-nr--->
				<ul class="J-aboutus-con" id="innerpage">
					<c:forEach items="${channelList}" var="item" varStatus="status">
						<c:if test="${item.channelIsUrl ==1 }">
							<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>"
								<c:if test="${status.index>=10 }">style="display:none;"</c:if>>
								<a href="${item.channelUrl }" target="_blank" class="clearfix">
									<p><strong>${item.channelTitle }</strong></p><span><fmt:formatDate value="${item.channelCreateTime }" type="date" pattern="yyyy-MM-dd"/></span>
								</a>
								<div>
									<a href="${item.channelUrl }" target="_blank" class="clearfix">
										${item.channelDescribe }
									</a>
								</div>
							</li>
						</c:if>
						<c:if test="${item.channelIsUrl ==0 }">
							<li class="<fmt:formatNumber type='number' value='${(status.index+1)/pageSize + ((status.index+1)%pageSize>0?1:0) - 0.49}' pattern='#0'/>"
								<c:if test="${status.index>=10 }">style="display:none;"</c:if>>
								<a href="${path}/${currentChannelUrl }/${item.id }" target="_blank" class="clearfix">
									<p><strong>${item.channelTitle }</strong></p><span><fmt:formatDate value="${item.channelCreateTime }" type="date" pattern="yyyy-MM-dd"/></span>
								</a>
								<div>
									<a href="${path}/${currentChannelUrl }/${item.id }" target="_blank" class="clearfix">
										${item.channelDescribe }
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
			<div class="J-hcc-right jfl" style="width: 26%; border: 1px solid #CCCCCC; padding: 7px 0px 0px 9px; margin-top: 39px;">
				<div class="right_div">
					<p><strong><span style="font-size:20px;">热门推荐</span></strong><span style="font-size: 16px; float: right; padding-right: 5px; line-height: 37px;"><a href="${path}/${pChannelUrl }">更多></a></span></p>
					<ul>
						<c:forEach items="${contentList}" var="item" varStatus="status">
							<c:if test="${status.index <10}">
								<li><a href="${path}/content/contentDetails/${item.channel_id }_${item.id }.html" target="_blank">
									<c:choose>  
									    <c:when test="${fn:length(item.content_title) > 16}">  
									        <c:out value="${fn:substring(item.content_title, 0, 16) }..." /></a>
									    </c:when>  
									   <c:otherwise>  
									      <c:out value="${item.content_title}" />  
									    </c:otherwise>  
									</c:choose> 
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="J-hcc-right jfl" style="width: 27%; padding: 7px 0px 0px 0px; margin-top: 16px;">
				<div class="right_div">
					<p><strong><span style="font-size:20px;">&nbsp;热销产品</span></strong></p>
					<div id="bottomItem" style="background: #F0F0F0;overflow: hidden; height: 412px;"></div>
				</div>
			</div>
			<div class="J-hcc-right jfl" style="width: 26%; border: 1px solid #CCCCCC; padding: 7px 0px 0px 9px;">
				<div class="right_div">
					<p><strong><span style="font-size:20px;">热门专题</span></strong><span style="font-size: 16px; float: right; padding-right: 5px; line-height: 37px;"><a href="${path}/${pChannelUrl }">更多></a></span></p>
					<ul>
						<c:forEach items="${relationChannels}" var="relationChannel" varStatus="status">
							<li><a href="${path}/${currentChannelUrl }/${relationChannel.id }">${relationChannel.channelTitle }</a></li>
						</c:forEach>
					</ul>
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
	
	//加载初始变量
	var BORROW_ALL_STATUS = {};
	<c:forEach var="item" items="${BORROW_ALL_STATUS}">
		BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
	</c:forEach>
	
	paramsObject.channelId = channelId;
	$(function() {
		//页面右侧热销产品
		getBottomItem("${path }/borrow/showBorrowStatusInfoPageByParam.do",getBottomItemInfo,"bottomItem");
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
	
	 // 滚动	
 	var doscroll = function() {
 		var $parent = $('#bottomItem');
 		var $first = $parent.find('.project_body:first');
 		var height = $first.height();
 		$first.animate({
 			marginTop : -height + 'px'
 		}, 700, function() {
 			$first.css('marginTop', 0).appendTo($parent);
 		});
 	};
 	setInterval(function() {
 		doscroll();
 	}, 3500);
</script>
<script type="text/javascript" src="${frontPath }/js/script.js"></script>
</html>