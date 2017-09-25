<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>浩茗金融-消费品供应链金融服务平台-P2B投资新理念</title>
	<meta name="Keywords" content="互联网投资,供应链金融,互联网金融">
	<meta name="Description" content="浩茗金融（www.hmjr99.com）是资金端项目来源为一级消费品龙头企业的互联网金融投资平台。作为专业的互联网金融机构,平台始终秉承“专业、安全、规范“的理念,致力于打造中国最专业的消费品类供应链金融服务商。为用户提供高收益低风险的优质投资理财产品,并服务消费者和小微企业主的个人融资需求。浩茗金融,引领互联网金融业务的发展！">
	<%@include file="taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/index.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/firstPage.css" media="screen" />
	<style>
		.zg_plan .percent_small em {
			font-size: 46px;
			position: relative;
			top: -130px;
			width: 170px;
			text-align: center;
			display: block;
			color: #f17072;
		}
		.zg_plan .percent_small em i {
			font-size: 16px;
		}
	</style>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-add-banner">
		<!--banner-->
		<div class="w_banner">
			<div id="sliders" class="slide banner" data-slide='{"action":"click","fn":"banner_ext","time":"8000"}'></div>
			<div class="frontCover"></div>
			<c:if test="${sessionScope.FRONT_USER_SESSION == null}">
				<!-- 登录、注册     -->
				<div class="indexlog">
					<div class="head_txt">预期年化利率</div>
					<div class="per_txt">8%－15%</div>
					<a class="re_btn" href="${path }/login" rel="nofollow">登录</a>
					<a class="wh_btn" href="${path }/register" rel="nofollow">注册</a>
				</div>
				<!--indexlog end-->
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
					<a class="re_btn" href="${path}/account/accountIndex.html" >我的账户</a>
				</div>
			</c:if>
		</div>
		<!--banner-->
	</div>
	<!--banner结束-->

	<div class="content">
		<div class="content_in first_content">
			<div class="safe_con">
				<ul>
					<li>
						<img class="pic" src="${frontPath}/images/p1.png">
						<span>新型投资模式</span>
						<div>产融结合模式，金融服务实体</div>
					</li>
					<li>
						<img class="pic" src="${frontPath}/images/p2.png">
						<span>资金安全保障</span>
						<div>权威机构托管，无线下资金池</div>
					</li>
					<li>
						<img class="pic" src="${frontPath}/images/p3.png">
						<span>信息公开透明</span>
						<div>信息完全披露，投资去向透明</div>
					</li>
					<li style="margin-right:0px">
						<img class="pic" src="${frontPath}/images/p4.png">
						<span>多重风控措施</span>
						<div>实地尽职调查，三方共管账户</div>
					</li>
				</ul>
			</div>
			<!-- 新手专区-->
			<div class="clearfix">
				<div class="main_left">
					<div>
						<c:if test="${fn:length(XSBList) <= 0}">
							<div class="main_data_div"><div class="biao_title xszx"></div><img src="${frontPath }/images/d3.png" style="margin-top: -29px;"></div>
						</c:if>
						<c:if test="${fn:length(XSBList) > 0}">
							<c:forEach items="${XSBList }" var="item" varStatus="status">
								<c:choose>
									<c:when test="${item.borrow_status == 2 }">
										<c:choose>
											<c:when test="${item.butn_status eq 'allow' }">
												<div class="touziDiv touzi"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">我要投资</div></a></div>
											</c:when>
											<c:when test="${item.butn_status eq 'over' }">
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">已经结束</div></a></div>
											</c:when>
											<c:otherwise>
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">待开始</div></a></div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div class="touziDiv HkzDksYjs"><a href='+${path }/invests/${item.id }' target="_blank"><div class="touziSpan">${BORROW_ALL_STATUS[item.borrow_status] }</div></a></div>
									</c:otherwise>
								</c:choose>
								<div class="main_data_div">
									<c:if test="${ 'full' eq item.isFull }">
										<div class="full">
											<img src="${frontPath }/images/full.png">
										</div>
									</c:if>
									<div class="biao_title xszx"></div>
									<div class="biao_body">
										<div>
											<span>${item.min_amount }元起投</span><span>${item.maxAmount }</span><span>${item.reypaymentStr }</span>
										</div>
										<div>
											<span>预期年化利率:&nbsp;<i>${item.annualInterestRate }%</i></span><span>${item.borrow_time_limit }${item.isDayStr }</span>
										</div>
										<div>
											<span class="process"><span class="process_bar" style="width: ${item.percent }%"></span></span><span class="baifenbi">${item.percent }%</span>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
					<div>
						<c:if test="${fn:length(LBlist) <= 0}">
							<div class="main_data_div"><div class="biao_title xszx"></div><img src="${frontPath }/images/d3.png" style="margin-top: -29px;"></div>
						</c:if>
						<c:if test="${fn:length(LBlist) > 0}">
							<c:forEach items="${LBlist }" var="item" varStatus="status">
								<c:choose>
									<c:when test="${item.borrow_status == 2 }">
										<c:choose>
											<c:when test="${item.butn_status eq 'allow' }">
												<div class="touziDiv touzi"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">我要投资</div></a></div>
											</c:when>
											<c:when test="${item.butn_status eq 'over' }">
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">已经结束</div></a></div>
											</c:when>
											<c:otherwise>
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">待开始</div></a></div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div class="touziDiv HkzDksYjs"><a href='+${path }/invests/${item.id }' target="_blank"><div class="touziSpan">${BORROW_ALL_STATUS[item.borrow_status] }</div></a></div>
									</c:otherwise>
								</c:choose>
								<div class="main_data_div">
									<c:if test="${ 'full' eq item.isFull }">
										<div class="full">
											<img src="${frontPath }/images/full.png">
										</div>
									</c:if>
									<div class="biao_title wxtj"></div>
									<div class="biao_body">
										<div>
											<span>${item.min_amount }元起投</span><span>${item.maxAmount }</span><span>${item.reypaymentStr }</span>
										</div>
										<div>
											<span>预期年化利率:&nbsp;<i>${item.annualInterestRate }%</i></span><span>${item.borrow_time_limit }${item.isDayStr }</span>
										</div>
										<div>
											<span class="process"><span class="process_bar" style="width: ${item.percent }%"></span></span><span class="baifenbi">${item.percent }%</span>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
					<div>
						<c:if test="${fn:length(HDBlist) <= 0}">
							<div class="main_data_div"><div class="biao_title xszx"></div><img src="${frontPath }/images/d3.png" style="margin-top: -29px;"></div>
						</c:if>
						<c:if test="${fn:length(HDBlist) > 0}">
							<c:forEach items="${HDBlist }" var="item" varStatus="status">
								<c:choose>
									<c:when test="${item.borrow_status == 2 }">
										<c:choose>
											<c:when test="${item.butn_status eq 'allow' }">
												<div class="touziDiv touzi"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">我要投资</div></a></div>
											</c:when>
											<c:when test="${item.butn_status eq 'over' }">
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">已经结束</div></a></div>
											</c:when>
											<c:otherwise>
												<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">待开始</div></a></div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div class="touziDiv HkzDksYjs"><a href='${path }/invests/${item.id }' target="_blank"><div class="touziSpan">${BORROW_ALL_STATUS[item.borrow_status] }</div></a></div>
									</c:otherwise>
								</c:choose>
								<div class="main_data_div">
									<c:if test="${ 'full' eq item.isFull }">
										<div class="full">
											<img src="${frontPath }/images/full.png">
										</div>
									</c:if>
									<div class="biao_title dqlc"></div>
									<div class="biao_body">
										<div>
											<span>${item.min_amount }元起投</span><span>${item.maxAmount }</span><span>${item.reypaymentStr }</span>
										</div>
										<div>
											<span>预期年化利率:&nbsp;<i>${item.annualInterestRate }%</i></span><span>${item.borrow_time_limit }${item.isDayStr }</span>
										</div>
										<div>
											<span class="process"><span class="process_bar" style="width: ${item.percent }%"></span></span><span class="baifenbi">${item.percent }%</span>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
			<!--recommend liuy-->
			
			<!-- 其它标的推荐 begin -->
			<div class="project_title">
				<h5><span>&nbsp;&nbsp;理财项目</span><a href="<c:url value='/invest'/>" class="more" style="margin-right: 16px;">更多&nbsp;&nbsp;&gt;</a></h5>
			</div>
			<div class="otherProject" style="background: #FEF8F8;">
				<c:forEach items="${borrows }" var="item" varStatus="status">
					<div class="project_body">
						<div class="project_main">
							<div class="project_main_left">
								<h4>
									<a alt="${item.borrow_title }" title="${item.borrow_title }" href="${path }/invests/${item.id }" target="_blank">${item.borrow_title }<img src="${frontPath }/images/zst.png"></a>
									<span class="hezuojigou">${item.bidRankStr }</span><span class="difengxian">&nbsp;${item.creditStr }&nbsp;</span>
								</h4>
								<div class="showProject">
									<p class="blue yqnh">
										<em style="padding-top: 0px;">预期年化利率</em><span><i class="nianhualilv">${item.annualInterestRate }</i>%</span>
									</p>
									<p class="blue">
										<em>投资期限</em><span class="special">${item.borrow_time_limit }${item.isDayStr }</span>
									</p>
									<p class="blue">
										<em>还款方式</em><span>${item.reypaymentStr }</span>
									</p>
									<p class="blue">
										<em>信用等级</em><span>${item.bidRankStr }</span>
									</p>
									<p class="l_txt">
										<em>借款进度</em><span class="process"><span class="process_bar" style="width: ${item.percent }%"></span></span>
											<span><i class="needpay">${item.tender_sum }/${item.borrow_sum }</i><i class="percent">${item.percent }%</i></span>
									</p>
									<p class="l_txt">
										<c:choose>
											<c:when test="${ 'full' eq item.isFull }">
												<img src="${frontPath }/images/full.png" style="margin-left: 28px;width: 68px;margin-top: -7px;">
											</c:when>
											<c:otherwise>
												<em style="padding-top: 16px;">项目总额</em><span class="special">${item.borrow_sum }<font>元</font></span>
											</c:otherwise>
										</c:choose>
									</p>
								</div>
							</div>
							<c:choose>
								<c:when test="${item.borrow_status == 2 }">
									<c:choose>
										<c:when test="${item.butn_status eq 'allow' }">
											<a href="${path }/invests/${item.id }" class="invest_btn" target="_blank"><div class="project_main_button touzi"><div class="project_main_button_left">我要投资</div></div></a>
										</c:when>
										<c:when test="${item.butn_status eq 'over' }">
											<a href="${path }/invests/${item.id }" class="invest_btn" target="_blank"><div class="project_main_button HkzDksYjs"><div class="project_main_button_left">已经结束</div></div></a>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0);" class="invest_btn"><div class="project_main_button HkzDksYjs"><div class="project_main_button_left">预告中</div></div></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="${path }/invests/${item.id }" class="invest_btn"><div class="project_main_button HkzDksYjs"><div class="project_main_button_left">${BORROW_ALL_STATUS[item.borrow_status] }</div></div></a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- 其它标的推荐 end -->
			<div class="clearfix">
				<div class="right_div contents_div gonggaos_div">
					<div class="right_div_title">
						<span>浩茗公告</span><a href="<c:url value='/notice'/>" target="_blank">更多</a>
					</div>
					<div class="right_div_body">
						<ul>
							<c:forEach items="${videoImportantNotice }" var="item" varStatus="status">
								<li>
									<c:if test="${item.external_link_title != undefined && item.external_link_title !='' && item.external_link_title != 'null'}">
										<a title="${item.content_title}" href="${item.external_link_title }" target="_blank">
									</c:if> 
									<c:if test="${item.external_link_title == 'null' || item.external_link_title == undefined || item.external_link_title == ''}">
										<a title="${item.content_title}" href="${path }/content/contentDetails/${item.channel_id }/${item.id }.html" target="_blank">
									</c:if> 
									<c:if test="${status.index == 0 }">
										<img src="${frontPath}/images/tb6.png">
									</c:if> 
									<c:if test="${status.index > 0 }">
										<img src="${frontPath}/images/tb5.png">
									</c:if> 
									<c:choose>
										<c:when test="${fn:length(item.content_title) > 25}">
											<c:out value="${fn:substring(item.content_title, 0, 25)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.content_title}" />
										</c:otherwise>
									</c:choose>
									<p><fmt:formatDate pattern="MM-dd" value="${item.content_add_datetime }" /></p>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="right_div contents_div zixuns_div">
					<div class="right_div_title">
						<span>媒体资讯</span><a href="<c:url value='/zixun'/>" target="_blank">更多</a>
					</div>
					<div class="right_div_body">
						<ul>
							<c:forEach items="${zxzxs }" var="item" varStatus="status">
								<li>
									<c:if test="${item.external_link_title != undefined && item.external_link_title !='' && item.external_link_title != 'null'}">
										<a title="${item.content_title}" href="${item.external_link_title }" target="_blank">
									</c:if> 
									<c:if test="${item.external_link_title == 'null' || item.external_link_title == undefined || item.external_link_title == ''}">
										<a title="${item.content_title}" href="${path }/content/contentDetails/${item.channel_id }/${item.id }.html" target="_blank">
									</c:if> 
									<c:if test="${status.index == 0 }">
										<img src="${frontPath}/images/tb6.png">
									</c:if> 
									<c:if test="${status.index > 0 }">
										<img src="${frontPath}/images/tb5.png">
									</c:if> 
									<c:choose>
										<c:when test="${fn:length(item.content_title) > 25}">
											<c:out value="${fn:substring(item.content_title, 0, 25)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.content_title}" />
										</c:otherwise>
									</c:choose>
									<p><fmt:formatDate pattern="MM-dd" value="${item.content_add_datetime }" /></p>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- content_in -->
		
		<!---Jxzg-partners start-->
		<div class="hzhb">
		    <div class="hzm">
		        <p>合作伙伴</p>
		    </div>
		</div>
		<div class="Jxzg-partners">
			<!-- <h5>合作伙伴</h5> -->
			<ul id="collFriend"></ul>
		</div>
		<!---Jxzg-partners end-->
	</div>
	<!-- content -->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="onlineSupport.jsp"%>  
	<!--圆圈特效-->
	<script type="text/javascript" src="${frontPath }/js/raphael.js"></script>
	<script type="text/javascript" src="${frontPath }/js/BaseInfo.js"></script>
	<script type="text/javascript">
		/*延迟加载*/
		//加载初始变量
		var BORROW_ALL_STATUS = {};
		<c:forEach var="item" items="${BORROW_ALL_STATUS}">
			BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
		</c:forEach>

		var BORROW_ALL_REPAYMENT_STYLE = {};
		<c:forEach var="item" items="${BORROW_ALL_REPAYMENT_STYLE}">
			BORROW_ALL_REPAYMENT_STYLE["${item.key}"] = "${item.value}";
		</c:forEach>

		//贷款用途
		var BORROW_ALL_BORROW_USE = {};
		<c:forEach var="item" items="${BORROW_ALL_BORROW_USE}">
			BORROW_ALL_BORROW_USE["${item.key}"] = "${item.value}";
		</c:forEach>
	    
		$(function() {
			//加载banner
			getContentListPic();
			//首页各种标的信息
			//getBorrowRecommend();
			//得到首页底部的理财项目
			//getBottomItem("${path }/borrow/showBorrowStatusInfoPageByParam.do",getBottomItemInfo,"bottomItem");
			//网站的实时数据
			getStatistics();
			//网站排行榜
			//getUserTenderData();
			//设置活动标的时间
			changeTopHover(1);
			//合作伙伴
			getContentList();
		});
	</script>
</body>
<script type="text/javascript" src="${frontPath }/js/script.js"></script>
</html>