<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>高收益投资理财产品_个人投资理财新选择-浩茗金融</title>
	<meta  name="Keywords" content="理财产品,高收益投资,个人投资理财">
	<meta name="Description" content="互联网理财是时下最受热议的话题，浩茗金融（www.hmjr99.com）是一个高收益低风险的互联网金融投资平台，提供多种中短期高利的投资理财产品，成为当下个人投资理财的新选择。">
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/showBorrowInfos.js"></script>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="content tz_con">
		<div class="content_in">
			<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<a>我要理财</a>
			</div>
			<div class="tap">
				<div class="tap_list">
					<a href="${path }/invest" class="onchose">项目列表</a> <a
						href="${path }/creditassignment">债权转让</a>
				</div>
				<ul class="J-liemu">
					<li id="borrowtypeall" ><i style="font-size: 16px;">标的种类</i>
						<em onclick="sort('all','borrowtype')"  >不限</em>
						<span onclick="sort(8,'borrowtype')">新手专享标</span>
						<span onclick="sort(9,'borrowtype')">白酒标</span>
						<span onclick="sort(10,'borrowtype')">红酒标</span>
						<span onclick="sort(11,'borrowtype')">30天标</span>
						<span onclick="sort(12,'borrowtype')">60天标</span>
						<span onclick="sort(13,'borrowtype')">90天标</span>
						<span onclick="sort(14,'borrowtype')">争分夺秒标</span></li>
					<li id="statusall"><i style="font-size: 16px;">项目状态</i>
						<em onclick="sort('all','status')" >不限</em>
						<span onclick="sort(1,'status')">预告中</span>
						<span onclick="sort(2,'status')">投资中</span>
						<span onclick="sort(5,'status')">收益中</span>
						<span onclick="sort(6,'status')">已完成</span></li>
					<li id="rateall"><i style="font-size: 16px;">利率范围</i>
						<em onclick="sort('all','rate')" >不限</em>
						<span onclick="sort(12,'rate')">12%以下</span>
						<span onclick="sort(1218,'rate')">12%~18%</span>
						<span onclick="sort(18,'rate')">18%以上</span></li>
					<li id="dateall"><i style="font-size: 16px;">借款期限</i>
						<em onclick="sort('all','date')" >不限</em>
						<span onclick="sort(3090,'date')">1~3个月</span>
						<span onclick="sort(90180,'date')">3~6个月</span>
						<span onclick="sort(180360,'date')">6~12个月</span></li>
					<li id="securitymodeall"><i style="font-size: 16px;">还款方式</i>
						<em onclick="sort('all','securitymode')" >不限</em>
						<span onclick="sort(2,'securitymode')">先息后本</span>
						<span onclick="sort(1,'securitymode')" style="width: 85px;">到期还本付息</span></li>
				</ul>
			</div>
			<ul class="tap_con" id="hkct-tz-list">
				<li>
					<h4>浩茗计划-六月期<img src="${frontPath }/images/zst.png" style="padding-left: 7px; width: 18px;"/>
						&nbsp;<span class="hezuojigou">合作机构</span>
						&nbsp;<span class="difengxian">低风险</span></h4>
					<div class="txt">
						<p class="l_txt">
							<span><i>10</i>%</span><em>年化收益</em>
						</p>
						<p><span>6个月</span><em>投资期限</em></p>
						<p><span>一次结清</span><em>还款方式</em></p>
						<p><span>200,000元</span><em>项目总额</em></p>
						<span class="circle">
							<div class="percent_small">
								<font>65.3%</font>
								<svg style="overflow: hidden; position: relative; left: -0.5px;"
									xmlns="http://www.w3.org/2000/svg" width="52" version="1.1"
									height="52"></svg>
							</div> 
						</span> 
						<a href="项目详情.html" class="invest_btn">马上投资</a>
					</div>
				</li>
			</ul>
			
			
			<!-- <ul class="tap_con" id="hkct-tz-list">
				<li>
					<h4>浩茗计划-六月期</h4>
					<div class="txt">
						<p class="l_txt">
							<em>年化收益</em><span><i>10</i>%</span>
						</p>
						<p><em>投资期限</em><span>6个月</span></p>
						<p><em>还款方式</em><span>一次结清</span></p>
						<p><em>项目总额</em><span>200,000元</span></p>
						<span class="circle">
							<div class="percent_small">
								<font>65.3%</font>
								<svg style="overflow: hidden; position: relative; left: -0.5px;"
									xmlns="http://www.w3.org/2000/svg" width="52" version="1.1"
									height="52"></svg>
							</div> 
						</span> 
						<a href="项目详情.html" class="invest_btn">马上投资</a>
					</div>
				</li>
			</ul> -->
		</div>
		<!-- content_in -->
	</div>
	<!-- content -->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
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
		changeTopHover(2);
		getPageFrom(data, "${path }/borrow/showBorrowStatusInfoPageByParam.do",getBorrowInfo, "hkct-tz-list");
	});

</script>
</html>