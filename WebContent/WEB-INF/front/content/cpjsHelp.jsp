<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../taglibs.jsp"%>
	<title>产品介绍-${showTitle }</title>
	<meta name="Keywords" content="产品介绍">
	<meta name="Description" content="浩茗金融平台旗下有浩茗计划,私募基金,以及债权市场等多个产品.浩茗计划是平台严格控制的投资项目,私募基金是一种非公开宣传的,私下向特定投资人募集资金进行的一种集合投资.客户可以通过债权市场根据偏好的投资期限、利率,认购他人转让的债权,提高资金的流动性.">
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
				  <li><a href="${path }/help" class="select">产品介绍</a></li>
				  <li><a href="${path }/content/wzzf.html">网站资费</a></li>
				</ul>
			</div>
			<div class="J-hcc-con jfl jboxsize">
				<h1>产品介绍</h1>
				<div class="J-hccc-nr J-product-infro">
					<dl style="background:url(${frontPath}/images/J-product-pic1.png) no-repeat 0 center;">
				    	<dd>
				    		<h3>浩茗计划</h3>
			    			<p>经过平台严格风控的投资项目，包括企业经营贷及多种形式的贷款投资产品；产品类型分新手专享、争分夺秒、浩茗理财计划1月标、浩茗理财计划3月标、浩茗理财计划6月标、浩茗理财计划9月标、浩茗理财计划12月标。</p>
				   		</dd>
				  	</dl>
				  	<dl style="background:url(${frontPath}/images/J-product-pic2.png) no-repeat 0 center;">
				   		<dd>
				    		<h3>私募基金</h3>
				    		<p>指相对于受中国政府主管部门监管的，向不特定投资人公开发行受益凭证的证券投资基金而言，是一种非公开宣传的，私下向特定投资人募集资金进行的一种集合投资。其方式基本有两种，一是基于签订委托投资合同的契约型集合投资基金，二是基于共同出资入股成立股份公司的公司型集合投资基金。</p>
				   		</dd>
				  	</dl>
				  	<dl style="background:url(${frontPath}/images/J-product-pic3.png) no-repeat 0 center;">
			   			<dd>
				    		<h3>债权市场</h3>
				    		<p>债权市场是将已投产品卖出的场所,以便提前收回资金,提高流动性,同时还可根据偏好的投资期限、利率，认购他人转让的债权。债权市场是将已投产品卖出的场所,以便提前收回资金,提高流动性,同时还可根据偏好的投资期限、利率，认购他人转让的债权。</p>
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