<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="taglibs.jsp"%>
	<meta charset="utf-8">
	<title>安全保障-${showTitle }</title>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/safeGuarantee.css" media="screen" />
	<style type="text/css">
		.nav .slogan{
	    	padding: 0px 10px;
		}
	</style>
</head>

<body>
	<div class="new_head">
		<jsp:include page="/top.do"></jsp:include>
    </div>
	<div style="background: url(${frontPath }/images/safeGuarantee/top_bg.jpg) no-repeat scroll center bottom;">
		<div style="height: 665px;"></div>
	</div>
	<div class="section">
		<div class="w1000">
			<div class="text600 fl pt50">
				<h3 class="title">
					<i>Ⅰ</i>交易安全
				</h3>
				<div class="nr">浩茗金融采用国际先进的网站加密技术，运用手机动态验证码，实施信息多重安全监控。浩茗金融严格履行遵守相应协议，不会将任何个人信息资料提供给第三方。</div>
			</div>
			<div class="fr">
				<img src="${frontPath }/images/safeGuarantee/pic1.jpg">
			</div>
			<div class="line"></div>
		</div>
	</div>
	<div class="section">
		<div class="w1000 pt50">
			<div class="text600 fr">
				<h3 class="title">
					<i>Ⅱ</i>资金安全
				</h3>
				<div class="nr">浩茗金融所有投资资金的流转均通过第三方资金托管机构宝付支付完成，客户可以清晰地看到账户资金的流向，并可实时查询账户资金详情，账户资金的转出也只能转出至客户个人认证并绑定在平台上的唯一银行账号，做到资金独立监管和资金严格监控，实现平台与资金隔离，保证客户投资的安全性。</div>
			</div>
			<div class="fl">
				<img src="${frontPath }/images/safeGuarantee/pic2.jpg">
			</div>
			<div class="line"></div>
		</div>
	</div>
	<div class="section">
		<div class="w1000">
			<div class="text600 fl pt50">
				<h3 class="title">
					<i>Ⅲ</i>交易安全
				</h3>
				<div class="nr">浩茗金融采用国际先进的网站加密技术，运用手机动态验证码，实施信息多重安全监控。浩茗金融严格履行遵守相应协议，不会将任何个人信息资料提供给第三方。</div>
			</div>
			<div class="fr">
				<img src="${frontPath }/images/safeGuarantee/pic3.jpg">
			</div>
			<div class="line"></div>
		</div>
	</div>
	<div class="section">
		<div class="w1000 pt50">
			<div class="text600 fr">
				<h3 class="title">
					<i>Ⅳ</i>资金安全
				</h3>
				<div class="nr">浩茗金融投资的风控核心技术脱胎于欧洲的IPC技术，在其基础上辅以信贷的流程设计以及大数据风险评级模式，通过双调三审一会制度完成风险审核。
					项目放款完成后，设立首期与常规贷后调查制度；建立项目台账，实时跟踪还款状态，反馈预警信号及时进行动态调整。</div>
			</div>
			<div class="fl">
				<img src="${frontPath }/images/safeGuarantee/pic4.jpg">
			</div>
			<div class="line"></div>
		</div>
	</div>
	<div class="section">
		<div class="w1000">
			<div class="text600 fl pt50">
				<h3 class="title">
					<i>Ⅴ</i>法律保障
				</h3>
				<div class="nr">
					<p>1.关于撮合服务的合法性
						根据《合同法》第23章关于“居间合同”的规定，特别是第424条规定的“居间合同是居间人向委托人报告订立合同的机会或提供订立合同的媒介服务委托人支付报酬的合同”。</p>

					<p>2.关于电子合同的可执行性及合法性
						根据《电子签名法》规定，民事活动中合同或者其他文件、单证等文书，当事人可以约定使用电子签名、数据电文，不能因为合同采用电子签名、数据电文就否定其法律效力。同时，《电子签名法》中还规定，可靠的电子签名与手写签名或者盖章具有同等的法律效力。</p>

					<p>3.电子合同的合法性
						根据最高人民法院《关于人民法院审理借贷案件的若干意见》第6条：“民间借贷的利率可以适当高于银行的利率，各地人民法院可以根据本地区的实际情况具体掌握，但最高不得超过银行同类贷款利率的四倍（包含利率本数）。超出此限度的，超出部分的利息不予保护。”</p>
				</div>
			</div>
			<div class="fr pt220">
				<img src="${frontPath }/images/safeGuarantee/pic5.jpg">
			</div>
		</div>
	</div>
	<div class="button_box">
		<div style="text-align: center;">
			<img src="${frontPath }/images/safeGuarantee/ming.png">
			<input name="" type="button" class="button" style="display:block;" onclick="javascrtpt:window.location.href='${path }/login'">
		</div>
	</div>
	<div
		style="background: url(${frontPath }/images/safeGuarantee/foot_bg.jpg) no-repeat scroll center bottom;">
		<div style="height: 138px;"></div>
	</div>
</body>
</html>