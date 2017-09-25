<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
	<c:when test="${pChannel.id == 77 }"><c:set var = 'pChannelUrl' value="zhuanti"></c:set></c:when>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${channel.channelTitle}-浩茗金融${showTitle }</title>
	<meta name="Keywords" content="${channel.keyword }">
	<meta name="Description" content="${channel.channelDescribe }">
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/ztzxContentDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/ztzxContentList.css" media="screen" />
	<style type="text/css">
		.item{
			    display: block;
			    height: 240px;
			    border: 1px solid #CCCCCC;
			    padding: 15px 40px 18px 16px;
			}
		.right_div p strong{
			font-weight: bold;
		}
	</style>
</head>
<body>
	<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-banner"><img src="${frontPath }/images/J-zxzt-001.png"></div>
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div>当前位置：
				<a href="${path }/">首页</a>
				><a href="${path }/zhuanti">${pChannel.channelTitle }</a>
				><a>${channel.channelTitle }</a>
			</div>
			<div class="J-hcc-con jfl jboxsize page" style="width:72%;">
				<div><h1 style="font-size: 24px; text-align: center; background: #FFF; color: #666;">${channel.channelTitle }</h1></div>
				<!---J-hccc-nr--->
				<ul class="J-aboutus-con" id="innerpage" style="padding: 0px 30px 0px;">
					<c:forEach items="${contents}" var="item" varStatus="status">
						<c:if test="${status.index <2 && item.external_link_title!=undefined&&item.external_link_title!=''&&item.external_link_title!='null'}">
							<li class="item">
								<a href="${item.external_link_title }" target="_blank" class="clearfix" <c:if test="${status.index ==0 }">style="width: 53%;"</c:if>>
									<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
								</a>
								<div <c:if test="${status.index ==0 }">style="width: 46%;float: left;"</c:if>>
									${item.content_summary }
									<a href="${item.external_link_title }" target="_blank" class="clearfix" style="display: -webkit-inline-box;color: #F55B23;">
										【查看详情】
									</a>
								</div>
								<c:if test="${status.index ==0 }">
									<a href="${path}/invest" target="_blank"><img src="${path }${item.attach_path }" style="float: right; margin: -29px -20px;"/></a>
								</c:if>
							</li>
						</c:if>
						<c:if test="${status.index <2 && (item.external_link_title=='null' || item.external_link_title==undefined || item.external_link_title=='')}">
							<li class="item">
								<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix" <c:if test="${status.index ==0 }">style="width: 53%;"</c:if>>
									<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
								</a>
								<div <c:if test="${status.index ==0 }">style="width: 46%;float: left;"</c:if>>
									${item.content_summary }
									<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix" style="display: -webkit-inline-box;color: #F55B23;">
										【查看详情】
									</a>
								</div>
								<c:if test="${status.index ==0 }">
									<a href="${path}/invest" target="_blank"><img src="${path }${item.attach_path }" style="float: right; margin: -29px -20px;"/></a>
								</c:if>
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<div class="c_banner">
					<div class="c_banner_title">
						<ul>
							<li><img src="${frontPath }/images/lab.png">&nbsp;&nbsp;专注于消费品供应链</li>
							<li><img src="${frontPath }/images/lab.png">&nbsp;&nbsp;稳健的P2B商业模式</li>
						</ul>
						<p><span>至今<i>${userCount }</i>人投资,投资总额达<i>${tenderSum }</i>元</span></p>
					</div>
					<c:if test="${sessionScope.FRONT_USER_SESSION == null}">
						<div class="indexlog">
							<div class="head_txt">预期年化利率</div>
							<div class="per_txt">8%－15%</div>
							<a class="re_btn" href="${path }/login" rel="nofollow">登录</a>
							<a class="wh_btn" href="${path }/register" rel="nofollow">注册</a>
						</div>
					</c:if>
					<c:if test="${sessionScope.FRONT_USER_SESSION != null}">
						<div class="indexlog">
							<div class="head_txt">预期年化利率</div>
							<div class="per_txt">8%－15%</div>
							<p class="text-overflow">您好，
							<c:if test="${sessionScope.FRONT_USER_SESSION.userAccount eq null}">
		                       ${sessionScope.FRONT_USER_SESSION.userAccount}
		                    </c:if>
		                    <c:if test="${sessionScope.FRONT_USER_SESSION.userAccount ne null}">
		                       ${sessionScope.FRONT_USER_SESSION.userAccount}
		                    </c:if>
							</p>
							<a class="re_btn" href="${path}/account/accountIndex.html">我的账户</a>
						</div>
					</c:if>
				</div>
				<c:if test="${contents != null && fn:length(contents) >=3}">
					<ul class="J-aboutus-con" id="innerpage" style="padding: 0px 29px 0px;">
						<c:forEach items="${contents}" var="item" varStatus="status">
							<c:if test="${status.index ==2 && item.external_link_title!=undefined&&item.external_link_title!=''&&item.external_link_title!='null'}">
								<li class="item">
									<a href="${item.external_link_title }" target="_blank" class="clearfix" <c:if test="${status.index ==0 }">style="width: 53%;"</c:if>>
										<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
									</a>
									<div>
										${item.content_summary }
										<a href="${item.external_link_title }" target="_blank" class="clearfix" style="display: -webkit-inline-box;color: #F55B23;">
											【查看详情】
										</a>
									</div>
								</li>
							</c:if>
							<c:if test="${status.index ==2 && (item.external_link_title=='null' || item.external_link_title==undefined || item.external_link_title=='')}">
								<li class="item">
									<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix" <c:if test="${status.index ==0 }">style="width: 53%;"</c:if>>
										<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
									</a>
									<div>
										${item.content_summary }
										<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix" style="display: -webkit-inline-box;color: #F55B23;">
											【查看详情】
										</a>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
				<c:if test="${contents != null && fn:length(contents) >3}">
					<ul class="J-aboutus-con" id="innerpage" style="padding-top: 0px; border: 1px solid #CCC; width: 678px; padding: 15px 26px 20px; margin-left: 30px;">
						<li><p style="font-family: 'Arial-BoldMT', 'Arial Bold', 'Arial'; font-weight: 700; font-style: normal; font-size: 27px; color: #333333; text-align: left; line-height: normal;"><strong>相关文章</strong></p></li>
						<c:forEach items="${contents}" var="item" varStatus="status">
							<c:if test="${status.index >2 && status.index <=8 && item.external_link_title!=undefined && item.external_link_title!='' && item.external_link_title!='null'}">
								<li>
									<a href="${item.external_link_title }" target="_blank" class="clearfix">
										<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
									</a>
									<div>
										<a href="${item.external_link_title }" target="_blank" class="clearfix">
											${item.content_summary }
										</a>
									</div>
								</li>
							</c:if>
							<c:if test="${status.index >2 && status.index <=8 && (item.external_link_title=='null' || item.external_link_title==undefined || item.external_link_title=='')}">
								<li>
									<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix">
										<p><strong style="font-size: 20px;">${item.content_title }</strong></p>
									</a>
									<div>
										<a href="${path}/content/contentDetails/${pChannel.id }_${channel.id }_${item.id }.html" target="_blank" class="clearfix">
											${item.content_summary }
										</a>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="J-hcc-right jfl" style="width: 26%; border: 1px solid #CCCCCC; padding: 7px 0px 0px 9px; margin-top: 76px;">
				<div class="right_div">
					<p><strong><span style="font-size:20px;">热门推荐</span></strong><span style="font-size: 16px; float: right; padding-right: 5px; line-height: 37px;"><a href="${path}/${pChannelUrl }">更多></a></span></p>
					<ul>
						<c:forEach items="${contentsList}" var="item" varStatus="status">
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
							<li><a href="${path}/${pChannelUrl }/${relationChannel.id }">${relationChannel.channelTitle }</a></li>
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
	//加载初始变量
	var BORROW_ALL_STATUS = {};
	<c:forEach var="item" items="${BORROW_ALL_STATUS}">
		BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
	</c:forEach>
	
	$(function() {
		//页面右侧热销产品
		getBottomItem("${path }/borrow/showBorrowStatusInfoPageByParam.do",getBottomItemInfo,"bottomItem");
	});
	//滚动	
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