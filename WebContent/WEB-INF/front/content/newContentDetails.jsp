<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${pChannel.id == 77 }"><c:set var = 'pChannelUrl' value="zhuanti"></c:set></c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../taglibs.jsp"%>
	<meta name="Description" content="${contentDetails.contentSummary }">
	<title>${contentDetails.contentTitle }-${showTitle }</title>
	<script type="text/javascript" src="${frontPath }/js/newContentDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/newContentDetails.css" media="screen" />
</head>
<body>
<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-banner"><img src="${frontPath }/images/J-zxzt-001.png"></div>
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div>当前位置：
				<a href="${path }/">首页</a>
				><c:set var="isUrl">${channel.channelIsUrl }</c:set>
				<c:choose>
					<c:when test="${pChannel != null && pChannel.id == 77}">
						<a href="${path }/zhuanti">${pChannel.channelTitle }</a>>
						<a href="${path }/zhuanti/${channel.id }">${channel.channelTitle }</a>>
					</c:when>
					<c:otherwise>
						<c:if test="${isUrl eq '0' }">
							<a href="<c:url value='/content/contentList/0/${channel.id }.do'/>">${channel.channelTitle }</a>>
						</c:if>
						<c:if test="${'0' ne isUrl }">
							<a href="<c:url value='${channel.channelUrl }'/>">${channel.channelTitle }</a>>
						</c:if>
					</c:otherwise>
				</c:choose>
				<font class="currentTitle">${contentDetails.contentTitle }</font>
			</div>
			<div class="J-hcc-con jfl jboxsize" style="width:72%;">
				<div class="j-ab-add">
					<h1>${contentDetails.contentTitle }</h1> 
			        <dl>
				        <dd>作者：${contentDetails.contentAuthor }</dd>
				        <dd>文章来源：${contentDetails.contentSource }</dd>
				        <dd>点击次数：${contentDetails.contentClick +1}次</dd>
				        <dd>更新时间：${contentDetails.updateTimeStr }</dd>
			        </dl>
			        <p>${contentDetails.contentTxt }</p>
			        <!-- JiaThis Button BEGIN -->
					<div class="jiathis_style_24x24" style="margin-top: 28px;">
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
						<a class="jiathis_counter_style"></a>
					</div>
					<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
					<!-- JiaThis Button END -->
				</div>
				<div>
					<div style="float:left" id="pre"></div>
					<div style="float:right;margin-right: 47px;" id="next"></div>
				</div>
				<div class="relationContent" id="relationContent">
					<span class="relationContentTitle">相关文章</span>
				</div>
			</div>
			<c:if test="${channel.id == 76 }">
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
			</c:if>
			
			<c:if test="${pChannel.id != null || pChannel.id == 77 }">
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
							<c:forEach items="${relationChannels }" var="relationChannel" varStatus="status">
								<li><a href="${path}/${pChannelUrl }/${relationChannel.id }">${relationChannel.channelTitle }</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
	</div> 
	<!--尾部-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	var relationContents = new Array();
	var currentId = '${contentDetails.id }';
	var pChannel = '${pChannel.id}';
    var channelId ='${channel.id }';
	
	//加载初始变量
	var BORROW_ALL_STATUS = {};
	<c:forEach var="item" items="${BORROW_ALL_STATUS}">
		BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
	</c:forEach>
	
	$(function(){
		if (channelId == 76){
			// 浩茗公告
			getContentList();
			//网站排行榜
			getUserTenderData();
		}
		if(pChannel!='' && (pChannel == 77 || pChannel == '77')){
			//页面右侧热销产品
			getBottomItem("${path }/borrow/showBorrowStatusInfoPageByParam.do",getBottomItemInfo,"bottomItem");
		}
		
		//上一篇、下一篇、先关文章
		relatedContent();
	});
	
	 var paramsObject={};
     var contentUrl = "";
 	 if(channelId == 76){
 		contentUrl = "/zixun/";
 	 }else if(pChannel == 77){
 		contentUrl = "/content/contentDetails/${pChannel.id}_${channel.id }_";
 	 }
     paramsObject.channelId=channelId;
     paramsObject.channelDisplay = '(0,1)';

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