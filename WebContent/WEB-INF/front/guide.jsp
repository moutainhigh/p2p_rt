<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="taglibs.jsp"%>
	<title>新手指引-${showTitle }</title>
	<meta name="Keywords" content="新手指引">
	<meta name="Description" content="浩茗金融（www.hmjr99.com）为广大理财人群提供更多元的投资选择与更优质的综合理财服务。投资理财用户可通过新手专区进行小标投资，短期内收获高额稳定收益。">
	<script type="text/javascript" src="${frontPath }/js/jquery-fullpage.js"></script>
	<script type="text/javascript" src="${frontPath }/js/page-js.js"></script>
	<script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
</head>
<body>
	<div id="fullpage" class="page">
		<!-- 第一屏 -->
		<div class="section page1">
            <div class="new_head">
    	  		<!-- 公用头部 -->
				<jsp:include page="/top.do"></jsp:include>
		    </div>
			<div class="con">
				<div class="con1">
					<div class="txt">
					   <h4>什么是浩茗金融</h4>
					   <p>上海杰券金融信息服务有限公司（浩茗金融），于2016年1月在上海成立，致力于打造中国最专业的消费品类供应链金融服务商。平台始终秉承“专业、安全、规范“的理念。在供应链金融的细分市场深耕。</p>
					   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平台的资产端项目来源定位于优质一级消费品龙头企业，经过三级风控体系严审，为保障投资者的稳健收益打下了夯实的基础;</p>
					   <a href="${path }/register" class="ren_btn">立即注册</a>
				    </div>
				    <!--  
				    <div class="pic draw">
				    	<img src="${frontPath }/images/page1_1.png">
				    	<div class="circle1"><img src="${frontPath }/images/page1_2.png" alt=""></div>
				    	<div class="circle2"><img src="${frontPath }/images/page1_3.png" alt=""></div>
				    	<div class="circle3"><img src="${frontPath }/images/page1_4.png" alt=""></div>
				    </div>
				    -->
				</div>
			</div>
		</div><!-- section -->
		<!-- 第二屏 -->
		<div class="section page2">
			<div class="con">
				<div class="con1">
				    <div class="title">
				    	<h3>为什么选择浩茗金融</h3>
				    	<p>三层风控•7道安全防线&nbsp;&nbsp;&nbsp; 全力实现本息投资安全！</p>
				    </div>
				    <div class="pic draw ad">
				    	<img src="${frontPath }/images/page2_1.png">
				    	<ul>
							<li class="li1">
								<div class="txt">
									<h5>更安全</h5>
								</div>
								<div class="txt">
									<p>支持安全套接层协议和128位加密技术，专业风控团队，第三方资金监管，100%本息保障</p>
								</div>
							</li>
							<li class="li2">
								<div class="txt">
									<h5>低门槛</h5>
								</div>
								<div class="txt">
									<p>100元起步，零花钱即可投资,被誉为"吊丝们的理财神器"</p>
								</div>
							</li>
							<li class="li3">
								<div class="txt">
									<h5>高收益</h5>
									<p>40倍银行存款利息，4倍余额宝收益，实现7%-12%的高收益</p>
								</div>
							</li>
							<li class="li4">
								<div class="txt">
									<h5>易流通</h5>
									<p>突然想用钱收回投资，自主定价挂牌转让，流动性极强</p>
								</div>
							</li>
						</ul>
				    </div>
				</div>
			</div>
		</div><!-- section -->
		<!-- 第三屏 -->
		<div class="section page3">
			<div class="con">
				<div class="con1">
				    <div class="title">
				    	<h3>浩茗金融安全保障</h3>
				    	<p>六大保障措施，项目多重筛选，让您无忧理财</p>
				    </div>
					<div class="txt">
						<ul>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>债权组合分散风险</h4>
								<p>采用"智能分散投资"思路,选取通过严格风控审核的上万份债权,经过后台模型的智能计算,生成债权包含组合,通过分散投资来规避部分坏账风险。</p>
							</li>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>风险备用金</h4>
								<p>为了保障平台的投资者资金安全,浩茗金融设立了风险准备金机制。如果投资人出借资金出现逾期,风险准备金将启动,对投资人进行全额本息垫付。
									"风险准备金账户"目前已由浩茗金融的股东注资200万,后期根据成交额的增长做相应的调整。</p>
							</li>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>本息保障计划</h4>
								<p>浩茗金融理财所投项目均来自有"充分还款保证"的借贷项目</p>
							</li>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>第三方公司保障</h4>
								<p>第三方公司提供连带责任担保 </p>
							</li>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>严格的风控体系</h4>
								<p style="width:60%">依托杰券在资本金融的强大实力,浩茗金融采用国内外先进的科学信用审核与风险控制体系,推出了高效先进的信用审核及风控体系</p>
							</li>
							<li>
								<img src="${frontPath }/images/page3_1.png">
								<h4>平台安全获国际认证</h4>
								<p style="width:60%">浩茗金融平台已通过国际知名机构安全认证(CSASTAR、ISO/IEC 27001)、信息安全等级保护，您可以完全放心</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div><!-- section -->
		<!-- 第四屏 -->
		<div class="section page4">
			<div class="con">
				<div class="con1">
				    <div class="title draw">
				    	<h3>浩茗金融平台产品</h3>
				    	<p>低门槛、高收益、投资灵活，理财无忧</p>
				    </div>
					<ul class="txt">
						<li>
							<h5>新手标</h5>
							<p>经过平台严格风控的投资项目，包括企业经营贷及多种形式的贷款投资产品。</p>
						</li>
						<li>
							<h5>活动标</h5>
							<p>经过平台严格风控的投资项目，包括企业经营贷及多种形式的贷款投资产品。</p>
						</li>
						<li style="margin-right:0;">
							<h5>定期理财</h5>
							<p>债权市场是将已投产品卖出的场所,以便提前收回资金提高流动性,同时还可根据偏好的投资期限、利率，认购他人转让的债权。</p>
						</li>
					</ul>
				</div>
			</div>
		</div><!-- section -->
		<!-- 第五屏 -->
		<div class="section page5">
			<div class="con">
				<div class="con1">
				    <div class="title">
				    	<h3 style="margin-top:10px;">如何投资</h3>
				    </div>
				    <!-- 
					<div class="pic">
						<div class="pic1">
							<a class="yel" href="javascript:void(0);" >注册</a>
							<a href="javascript:void(0);">实名认证</a>
							<a href="javascript:void(0);">充值</a>
							<a href="javascript:void(0);">投资</a>
							<a href="javascript:void(0);" style="margin-right:0;">获得收益</a>
						</div>
						<div class="pic2">
							<img src="${frontPath }/images/page5_2.png" style="display:block;" width="658px" height="392px">
							<img src="${frontPath }/images/pageNew5_2.png" width="658px" height="392px">
							<img src="${frontPath }/images/pageNew5_3.png" width="658px" height="392px">
							<img src="${frontPath }/images/pageNew5_4.png" width="658px" height="392px">
							<img src="${frontPath }/images/pageNew5_3.png"  width="658px" height="392px">
						</div>
						<div class="could1"><img src="${frontPath }/images/page5_4.png" ></div>
					    <div class="could2"><img src="${frontPath }/images/page5_3.png" ></div>
					    <div class="could3"><img src="${frontPath }/images/page5_5.png" ></div>
					</div>
				 -->
				</div>
			</div>
		</div><!-- section -->
		<!-- 第六屏 -->
		<div class="section page6">
			<div class="con">
				<div class="con1">
				    <div class="title">
				    	<h3>新手有礼</h3>
				    	<h4>注册认证+投资+邀请好友</h4>
				    	<h4>||</h4>
				    	<h4><i> 惊喜不断</i></h4>
				    </div>
					<a href="${path }/register" class="get_btn">我要注册</a>
				</div>
			</div>
		</div><!-- section -->
	</div><!-- fullpage -->
    <div class="big_arr"><img src="${frontPath }/images/page_btn.png" height="69" width="31" alt=""></div>
</body>
<script type="text/javascript">
	$(function() {
		changeTopHover(3);
	})
</script>
</html>