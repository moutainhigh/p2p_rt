<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
<script type="text/javascript"src="${frontPath }/js/browser.js"></script>
</head>
<body>
	<jsp:include flush="false" page="/top.do"></jsp:include>
	<div id="content" style="background-color:#f2f2f2;" ><!--实时财务-->
	    <div id="hkct-sscw" style="height:125px;">
	    <div class="xt-cw" style="margin-top:8px;"></div>
		<div class="hkct-k" style="background-color:#ee2121; height:90px; ">
	 			 <ul style="padding-top:10px;">
					<li style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">交易总额</p><p class="hkct-sscw-p2" id="totleMoney"></p></li>
	            	<li  style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">待收总额</p><p class="hkct-sscw-p2" id="allInterestAndRewardStr"></p></li>
	            	<li  style="line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">今日回款总额</p><p class="hkct-sscw-p2" id="repossessed"></p></li>
	            	<li style="border:none; line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">注册人数</p><p class="hkct-sscw-p2" id="userNum"></p></li>
	            </ul>
	    </div>
	    </div>
		  <div id="hkct-tz" style="background-color:#f2f2f2;">
			<div class="hkct-k" style="border:1px solid #cccccc; overflow:hidden; margin-bottom:30px;">
			  <div class="xt-blc"><img src="${frontPath}/images/ny/biao_03.jpg" width="953" height="31" style="margin-top:40px; margin-left:25px;" /></div>
			  
			  <c:forEach items="${borrowTypeList }" var="borrowType">
				  <div class="xt-biaobj">
				  	  <div class="wyjk-k">
						  <dl>
						  <dd style="margin-left:40px; margin-right:50px;">
						
						    <c:if test="${borrowType.code=='LI' }">
								<img 
									src="${frontPath}/images/ny/dida.png"  width="139" >
							</c:if>
							<c:if test="${borrowType.code=='ZHUAN' }">
								<img 
									src="${frontPath}/images/ny/liuda.png"  width="139" >
							</c:if>
							<c:if test="${borrowType.code=='MIAO' }">
								<img 
									src="${frontPath}/images/ny/miaoda.png"  width="139" >
							</c:if>
							<c:if test="${borrowType.code=='JING' }">
								<img
									src="${frontPath}/images/ny/jingda.png"  width="139" >
							</c:if>
							<c:if test="${borrowType.code=='XING' }">
								<img 
									src="${frontPath}/images/ny/xingda.png"  width="139"  >
							</c:if>
							<c:if test="${borrowType.code=='DING_DF' }">
								<img 
									src="${frontPath}/images/ny/liuda.png"  width="139" >
							</c:if>
						  </dd>
						  <dt style="font-size:18px; color:#000; font-weight:bold; line-height: 15px;">${borrowType.name }</dt>
						  <dt style=" margin-left:10px; "><img src="${frontPath}/images/ny/biao_10.jpg" width="84" height="14" /></dt><br/>
						  <!--  <dt style="font-weight:bold;">标的性质：</dt>
						  <dt>小微企业现场审核抵押发布借款</dt><br/> -->
						    <dt style="font-weight:bold;">标的简介：</dt>
						  <dt style="width:700px;">${borrowType.remark }<!-- <a href="#" style="color:#ee2121;">[点击详细]</a> --></dt><br/>
						  <dt style="color:#fff; font-size:14px;  float:right; margin-right:40px;" class="dd-an">
						  <c:if test="${borrowType.code=='LI' }">
								<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
							<c:if test="${borrowType.code=='ZHUAN' }">
								<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
							<c:if test="${borrowType.code=='MIAO' }">
									<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
							<c:if test="${borrowType.code=='JING' }">
									<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
							<c:if test="${borrowType.code=='XING' }">
									<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
							<c:if test="${borrowType.code=='DING_DF' }">
								<a style="color:#fff; " href="${path }/iborrow/borrowInvitation/${borrowType.code}.do">发布${borrowType.name }</a>
							</c:if>
						  </dl>
					  </div>
				  </div>
			  </c:forEach>
			  
			  
			  <%-- <div class="xt-biaobj"><div class="wyjk-k">
			  <dl>
			  <dd style="margin-left:40px; margin-right:50px;"><img src="${frontPath}/images/ny/biao_13.jpg" width="120" height="114" /></dd>
			  <dt style="font-size:18px; color:#000; font-weight:bold; line-height: 15px;">信用标</dt>
			  <dt style=" margin-left:10px; "><img src="${frontPath}/images/ny/biao_10.jpg" width="84" height="14" /></dt><br/>
			   <dt style="font-weight:bold;">标的性质：</dt>
			  <dt>小微企业现场审核抵押发布借款</dt><br/>
			    <dt style="font-weight:bold;">标的简介：</dt>
			  <dt style="width:700px;">给力借款标显示标记"力"，是联正财富投资，台州P2P网络借贷，最大、最安全的网络借贷平台经过线下严格核查借款人资产负债，抵押担保手续（不仅限）、有关政府以及商业银行推荐、优质资产和股权质押， 确保风险控制在合理的范围内。<a href="#" style="color:#ee2121;">[点击详细]</a></dt><br/>
			  <dt style="color:#fff; font-size:14px;  float:right; margin-right:40px;" class="dd-an"><a style="color:#fff; "  href="发标页面.html">发布信用标</a></dt>
			  </dl>
			  </div></div>
			  <div class="xt-biaobj"><div class="wyjk-k">
			  <dl>
			  <dd style="margin-left:40px; margin-right:50px;"><img src="${frontPath}/images/ny/biao_13.jpg" width="120" height="114" /></dd>
			  <dt style="font-size:18px; color:#000; font-weight:bold; line-height: 15px;">秒还标</dt>
			  <dt style=" margin-left:10px; "><img src="${frontPath}/images/ny/biao_10.jpg" width="84" height="14" /></dt><br/>
			   <dt style="font-weight:bold;">标的性质：</dt>
			  <dt>小微企业现场审核抵押发布借款</dt><br/>
			    <dt style="font-weight:bold;">标的简介：</dt>
			  <dt style="width:700px;">给力借款标显示标记"力"，是联正财富投资，台州P2P网络借贷，最大、最安全的网络借贷平台经过线下严格核查借款人资产负债，抵押担保手续（不仅限）、有关政府以及商业银行推荐、优质资产和股权质押， 确保风险控制在合理的范围内。<a href="#" style="color:#ee2121;">[点击详细]</a></dt><br/>
			  <dt style="color:#fff; font-size:14px;  float:right; margin-right:40px;" class="dd-an"><a style="color:#fff; "  href="发标页面.html">发布秒还标</a></dt>
			  </dl>
			  </div></div>
			  <div class="xt-biaobj"><div class="wyjk-k">
			  <dl>
			  <dd style="margin-left:40px; margin-right:50px;"><img src="${frontPath}/images/ny/biao_13.jpg" width="120" height="114" /></dd>
			  <dt style="font-size:18px; color:#000; font-weight:bold; line-height: 15px;">流转标</dt>
			  <dt style=" margin-left:10px; "><img src="${frontPath}/images/ny/biao_10.jpg" width="84" height="14" /></dt><br/>
			   <dt style="font-weight:bold;">标的性质：</dt>
			  <dt>小微企业现场审核抵押发布借款</dt><br/>
			    <dt style="font-weight:bold;">标的简介：</dt>
			  <dt style="width:700px;">给力借款标显示标记"力"，是联正财富投资，台州P2P网络借贷，最大、最安全的网络借贷平台经过线下严格核查借款人资产负债，抵押担保手续（不仅限）、有关政府以及商业银行推荐、优质资产和股权质押， 确保风险控制在合理的范围内。<a href="#" style="color:#ee2121;">[点击详细]</a></dt><br/>
			  <dt style="color:#fff; font-size:14px;  float:right; margin-right:40px;" class="dd-an"><a style="color:#fff; "  href="发标页面.html">发布流转标</a></dt>
			  </dl>
			  </div></div>
			  <div class="xt-biaobj"><div class="wyjk-k">
			  <dl>
			  <dd style="margin-left:40px; margin-right:50px;"><img src="${frontPath}/images/ny/biao_13.jpg" width="120" height="114" /></dd>
			  <dt style="font-size:18px; color:#000; font-weight:bold; line-height: 15px;">天标</dt>
			  <dt style=" margin-left:10px; "><img src="${frontPath}/images/ny/biao_10.jpg" width="84" height="14" /></dt><br/>
			   <dt style="font-weight:bold;">标的性质：</dt>
			  <dt>小微企业现场审核抵押发布借款</dt><br/>
			    <dt style="font-weight:bold;">标的简介：</dt>
			  <dt style="width:700px;">给力借款标显示标记"力"，是联正财富投资，台州P2P网络借贷，最大、最安全的网络借贷平台经过线下严格核查借款人资产负债，抵押担保手续（不仅限）、有关政府以及商业银行推荐、优质资产和股权质押， 确保风险控制在合理的范围内。<a href="#" style="color:#ee2121;">[点击详细]</a></dt><br/>
			  <dt style="color:#fff; font-size:14px;  float:right; margin-right:40px;" class="dd-an"><a style="color:#fff; "  href="发标页面.html">发布天标</a></dt>
			  </dl>
			  </div></div> --%>
			</div>
		  </div>
	</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	getStatistics();
	changeTopHover(3);
});
</script>
</html>

