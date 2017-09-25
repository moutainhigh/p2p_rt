<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../taglibs.jsp"%>
	<title>网站资费-${showTitle }</title>
	<meta name ="Keywords" content="网站资费">
	<meta name="Description" content="浩茗金融平台中的充值费用,利息管理费,以及提现收费说明等,都能得到一一解答,让你投的明白.">
	<script type="text/javascript" src="${frontPath }/js/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/Jane-css.css"/>
    
    <script type="text/javascript" src="${frontPath }/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${frontPath }/jS/jquery.SuperSlide.2.1.js"></script>
	<script type="text/javascript" src="${frontPath }/js/common.js"></script>
    <script type="text/javascript" src="${frontPath }/js/cebianlan.js"></script>
    <script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
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
	<!--最新公告-->
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div class="J-hcc-nav jfl">
				<h3>帮助中心</h3>
				<ul>
				  <li><a href="${path }/help">产品介绍</a></li>
				  <%-- <li><a href="${path }/content/mcjs.do">名词解释</a></li> --%>
				  <li><a href="${path }/content/wzzf.html" class="select">网站资费</a></li>
				  <%-- <li><a href="${path }/content/zcfg.do">政策法规</a></li>
				  <li><a href="${path }/content/ysbh.do">隐私保护</a></li> --%>
				</ul>
			</div>
			<div class="J-hcc-con jfl jboxsize">
				<h1>网站资费</h1>
				<div class="J-hccc-nr J-hc-charges">
					<dl>
						<dt>一、充值费用</dt>
						<dd>线上充值不收取任何手续费。</dd>
					</dl>
					<dl>
						<dt>二、利息管理费</dt>
						<dd>在目前推广期间，免收平台利息管理费。</dd>
					</dl>
					<dl>
						<dt>三、提现说明</dt>
						<dd>
							<h4>1、提现金额：</h4>
							<div>目前单笔提现限额范围是：100元--100000元，每日累计不超过10次。</div>
							<h4>2、提现时间：</h4>
							<div>
								<span>(1) </span>
								<p>工作日17:00之前提交的提现申请,资金一般会于T+1日到账（双休日及法定节假日顺延到账）；17:00之后提交的申请，资金一般会于T+2日到账（双休日及法定节假日顺延到账）。双休日及法定节假日暂不处理提现，将顺延到假后第一个工作日统一处理。</p>
							</div>
							<div>
								<span>(2) </span>
								<p>网银转账后，实际到账时间根据打款银行、地区以及日期（工作日、法定节假日）的不同，到账时间会有所差异，一般到账时间不超过24小时。如有疑问，请直接联系客服。</p>       
							</div>
							<h4>3、提现费用：</h4>
							<div>
								<span>(1)</span>
								<p>单笔提现手续费：2元/笔；</p>  
								<span>(2) </span>
								<p>未投资便提现的额外收取提现金额的1%为手续费。</p>   
								<p>举例：如您充值了1000元，投资了200元，800元未投资直接提现，将收取的提现手续费为：2元+800*1%=10元。</p>   
							</div>
						 </dd>
					</dl>
				</div>
				<!---J-hccc-nr--->
			</div>
		</div>
	</div>
	<!--尾部-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
</html>