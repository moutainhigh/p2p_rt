<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-充值</title>
<link rel="stylesheet" type="text/css"
	href="${frontPath }/css/account.css" />
</head>
<style type="text/css">
.input {
	border: 1px solid #b4b9bd;
	height: 18px;
	line-height: 18px;
	padding: 11px 5px;
	width: 240px;
}

#overlay {
	background: #929395;
	filter: alpha(opacity = 60); /* IE透明 */
	opacity: 0.6; /* 透明度 */
	display: none;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	z-index: 1000;
	display: none;
}

.offlineWindow {
	position: absolute;
	margin: 18% 30% 10% 30%;
	width: 200px;
	height: 20px;
	background-color: #fff;
	border: 3px solid #999;
	z-index: 1001;
	display: none;
	padding-left: 10px;
}

.closeWindow {
	position: absolute;
	margin: 18% 30% 10% 30%;
	width: 500px;
	height: 250px;
	background-color: #fff;
	border: 10px solid #999;
	z-index: 1001;
	display: none;
	padding-left: 10px;
}

.closeRight {
	float: right;
	margin: 2px 15px 15px 0px;
	font-size: 24px;
	color: #0099cb;
}

.font1 {
	clear: both;
	font-family: "宋体";
	font-size: 18px;
}

.font2 {
	color: #4d4d4d;
	font-size: 16px;
}

.font3 {
	clear: both;
	font-family: "宋体";
	font-size: 18px;
	color: #00709E;
}

.font2 a {
	color: #0099cb;
}
 .J-ma-topup img{
 width:134px;
 height:35px;
} 
</style>
<body>
<!-- 遮罩层 -->
<div id="overlay" style="border:1px solid red;display: none;"></div>
 <!-- 在现充值遮罩显示 -->
<div class="closeWindow" id="onlineWindow"  style="display: none;">
    <a class="closeRight" style="cursor: hand;" onclick="hideOverlay()" href="#">✖关闭</a><br/><br/><hr style="width: 100%"/>
    <span class="font1">请在新页面支付完成付款后选择:</span><br/><br/>
    <span class="font2"><strong style="font-weight:bold; ">充值成功</strong>&nbsp;&nbsp;|&nbsp;&nbsp;您可以选择：<a href="${path }/account/rechargeValue.do">查看充值记录</a></span><br/><br/>
    <span class="font2"><strong style="font-weight:bold; ">充值失败</strong>&nbsp;&nbsp;|&nbsp;&nbsp;您可以致电:${websitetel}</span>
</div> 
<!-- 网上支付遮罩显示 -->
<div class="offlineWindow" id="offlineWindow" style="display: none;">
    <span class="font3" style="text-align:center">正在提交，请稍后......</span><br/><br/>
</div>
	<jsp:include page="/top.do"></jsp:include>


	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
			<div class="J-ma-nav jboxsize">
				<input type="hidden" value="left6" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>

			</div>

			<!---充值 star--->
			<div class="J-ma-invest jboxsize J-ma-zqzr">
				<div class="J-mainv-list J-ma-zqzr-list">
					<dl class="clearfix J-mainv-click">
						<dd class="J-mainvlist-current">充值</dd>
					</dl>
					<div class="J-mainvl-con">
						<div class="J-ma-topup clearfix">
							<form action="${path }/account/onlineRecharge.do" method="post"
								id="rechargeForm" target="_blank">
<!-- 							 <ol class="clearfix"> -->
<!-- 								<li><input type="radio" name="rechargeType" onclick="changePay(1);"checked="checked" value="1" id="rechargeType">支付方式</li> -->
<!-- 								<li><input type="radio" name="rechargeType" onclick="changePay(2);"value="2" id="rechargeType">充值银行&nbsp;&nbsp;&nbsp;</li> -->
<!-- 							 </ol> -->
                                <input type="hidden" name="rechargeType" value="1"/>
                                <ol class="clearfix" style="padding-top:185px">
                                    <li>支付方式:</li>
                                    <li id="li-offline" style="display: none;">选择银行:</li>
                                </ol>
								<ul>
                                    <li class="J-matu-list clearfix">
                                        <div class="J-matul-L jfl">
                                            <p>可用金额</p>
                                            <p>充值金额</p>
                                            <p>手续费用</p>
                                            <p>到账余额</p>
                                        </div>
                                        <div class="J-matul-R jfl">
                                            <p>
                                                <b id="availableMoneys">${userAccount.availableMoney }</b>元
                                            </p>
                                            <p class="clearfix">
                                            <input  type="text"
                                            value="0" id="chargeSum" name="rechargeMoney"
                                            onblur="changeMon()" onkeyup="moneyKeyup();" placeholder="请输入您要充值的金额"> 元
                                                <!-- <input type="text" id="chargeSum" placeholder="请输入您要充值的金额" />元 -->
                                            </p>
                                            <p>
                                                <b>0.00</b>元<em><img src="${frontPath }/images/J-cz-012.png" style="width:19px; height:19px;" title="目前充值不收取任何手续费"/></em>
                                            </p>
                                            <p>
                                                <b id="finalAva">${userAccount.availableMoney }</b>元
                                            </p>
                                        </div>
                                    </li>
							    <li id="online" style="margin-bottom: 3px;">
									<dl>
										<c:forEach var="pay" items="${list }" varStatus="idxStatus" begin="1" >
										<dd style="margin-bottom: 5px;">
											<label>
												<input  name="rechargeId" type="radio" value="${pay.id }"
												<c:if test="${idxStatus.index==1 }">checked="checked" </c:if> onclick="changePayType();feeFun();" />
												<span>
													<c:if test="${not empty pay.paymentIco }">
														<img src="${path }${pay.paymentIco}" />
													</c:if>
													<c:if test="${empty pay.paymentIco }">
														<span style="display: inline-block;height: 35px;line-height: 35px;border: 0px;text-align: center;">${pay.paymentName }</span>
													</c:if>
												</span>
											</label>
										</dd>
									</c:forEach>
									<c:forEach var="pay" items="${list }" varStatus="idxStatus" begin="0" end="0">
										<dd style="margin-bottom: 5px;">
											<label>
												<input  name="rechargeId" type="radio" value="${pay.id }" onclick="changePayType();feeFun();" />
												<span>
													<c:if test="${not empty pay.paymentIco }">
														<img src="${path }${pay.paymentIco}" />
													</c:if>
													<c:if test="${empty pay.paymentIco }">
														<span style="display: inline-block;height: 35px;line-height: 35px;border: 0px;text-align: center;">${pay.paymentName }</span>
													</c:if>
												</span>
											</label>
										</dd>
									</c:forEach>
									</dl>
								</li>
								<li id="offline" style="display: none;">
									<dl>
                                        <div>个人网银</div>
										<c:forEach var="paymentConfig" items="${paymentConfigList}" varStatus="idxStatus">
											<dd style="margin-bottom: 5px;">
												<label>
													<input name="rechargePayment" type="radio" value="${paymentConfig.paymentCode }" />
													<span style="text-align: left;" >
														<c:if test="${not empty paymentConfig.paymentIco }">
															<img src="${path }${paymentConfig.paymentIco}" alt="${paymentConfig.paymentName }" />
														</c:if>
														<c:if test="${empty paymentConfig.paymentIco }">
															<span title="${paymentConfig.paymentDescription }" style="display: inline-block;height: 35px;line-height: 35px;border: 0px;text-align: center;">${paymentConfig.paymentName }</span>
														</c:if>
													</span>
												</label>
											</dd>
										</c:forEach>
									</dl>
                                    <dl>
                                        <div>企业网银</div>
                                        <c:forEach var="paymentConfig" items="${paymentConfigListERP}" varStatus="idxStatus">
                                            <dd style="margin-bottom: 5px;">
                                                <label>
                                                    <input name="rechargePayment" type="radio" value="${paymentConfig.paymentCode }" />
                                                    <span style="text-align: left; position: relative;" >
                                                        <c:if test="${not empty paymentConfig.paymentIco }">
                                                            <img src="${path }${paymentConfig.paymentIco}" alt="${paymentConfig.paymentName }" />
                                                        </c:if>
                                                        <c:if test="${empty paymentConfig.paymentIco }">
                                                            <span title="${paymentConfig.paymentDescription }" style="display: inline-block;height: 35px;line-height: 35px;border: 0px;text-align: center;">${paymentConfig.paymentName }</span>
                                                        </c:if>
                                                        <div class="bank-qiye"></div>
                                                    </span>
                                                </label>
                                            </dd>
                                        </c:forEach>
                                    </dl>
									<!-- <div class="J-matu-morebank" style="display: none;">
										<dl>
											<c:forEach var="paymentConfig" items="${paymentConfigList}" varStatus="idxStatus">
												<c:if test="${idxStatus.count gt 3 }">
												
													<dd style="margin-bottom: 5px;">
														<label>
															<input name="rechargePayment" type="radio" <c:if test="${idxStatus.index==0 }">checked="checked"</c:if>
																	value="${paymentConfig.paymentCode }" />
															<span style="text-align: center;">
																<c:if test="${not empty paymentConfig.paymentIco }">
																	<img src="${path }${paymentConfig.paymentIco}" alt="${paymentConfig.paymentName }" />
																</c:if>
																<c:if test="${empty paymentConfig.paymentIco }">
																	<span title="${paymentConfig.paymentDescription }" style="display: inline-block;height: 35px;line-height: 35px;border: 0px;text-align: center;">${paymentConfig.paymentName }</span>
																</c:if>
															</span>
														</label>
													</dd>
												</c:if>
											</c:forEach>
										</dl>
									</div>
									<a href="javascript:" class="J-matu-toggle">展开更多银行</a> -->
								</li>
									<li class="J-matu-list clearfix">
										<div class="clearfix"></div>
										<div class="J-matul-btn clearfix ">
											<a onclick="frmSubmit();" class="J-matulb-a">充值</a><i>如果充值遇到问题，请联系客服热线：<a
												href="javascript:">${websitetel}</a></i>
										</div>
									</li>

								</ul>
								<%-- <input type="hidden" name="rechargeType" id="rechargeType"
								value="${payment.paymentType}" /> --%> <!-- <input type="hidden"
								name="rechargeId" id="rechargeId" /> -->
							</form>
						</div>

						<div class="J-mazdtb-action jboxsize">
							<h5>温馨提示：</h5>
							<ul>
								<li><span>&minus;</span>
									<p>目前充值不收取任何手续费；</p></li>
								<li><span>&minus;</span>
									<p>充值金额需大于或等于10.00元；</p></li>
								<li><span>&minus;</span>
									<p>您的资金将从充值银行卡转入合作支付公司的监管账户；</p></li>
								<li><span>&minus;</span>
									<p>每日的充值限额依据各银行限额为准，请注意您的银行卡充值限制，以免造成不便；</p></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!---充值 end--->

		</div>
	</div>
	<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
	<div id='tishizheceng' style="position: absolute; z-index: 99998; top: 0px; left: 0px; width: 100%; height: 1584px; opacity: 0.3; background: rgb(255, 255, 255);display: none;"></div>
	<div id='tishikuang' class="gSys_msg_box" style="position: absolute; z-index: 99999; padding: 0px; margin: 0px; width: 460px; top: 300px; left: 561.5px;display: none;">
		<div class="ptitle"><span class="fn-bg rc-l"></span><h4>提示</h4><span class="fn-bg rc-r"></span><a href="javascript:closePop();" class="fn-bg aclose" title="关闭">关闭</a></div>
		<div class="pbox"><div class="pmsg"><b title="提示:" class="ico alert"></b><div class="ct" id="rechargeContent"></div></div></div>
		<div class="popt">
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="closePop()">取消</span></div></div>
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="makeSure()">确定</span></div></div>
		</div>
	</div>
</body>
<script type="text/javascript">
var bankStatus ='${bankCheck}'; 
$(function(){
	if(bankStatus=='ok'){
		alertc("请先进行绑定银行卡操作");
		setInterval(aa,3000);
	}
	
})
function aa(){
	window.location.href="${path}/account/bankCard.html";
}
function changePay(obj){
	if(obj==2){
		$("#online").hide(); 
		 $("#offline").show();
	}
	if(obj==1){
		 $("#offline").hide();
		 $("#online").show(); 
	}
}
var fee = {};
<c:forEach var="item" items="${list}"> 
<c:if test="${item.paymentFeeType eq 1}">
fee["${item.id}"] = "BL_${item.paymentMaxFee}";
</c:if>
<c:if test="${item.paymentFeeType == 2}">
fee["${item.id}"] = "GD_${item.paymentMaxMoney}";
</c:if>
<c:if test="${item.paymentFeeType == 0}">
fee["${item.id}"] = "NO";
</c:if>
</c:forEach>

var msg="${message}";
if(msg){
	alertc(msg);
}
	changeLeft(4);
	//支付方式
	function clickPayment(id,type,detail){
		$("#"+id).attr("checked",true);
		$("#rechargeType").val(type);
		if(type == "${PAYMENT_TYPE_OOF}"){
			alertc(detail);
			$("#remark").show();
			$("#rechargePayment").val(detail);
		}else{
			$("#remark").hide();
			$("#rechargeId").val(id);
			feeFun(id);
		}
		changeMon();
	}
	//金额框校验
	function moneyKeyup() {
		var value = $("#chargeSum").val();
		$("#chargeSum").val(value.replace(/[^0-9.]/g, ''));
		if (isNaN(value)) {
			$("#chargeSum").val('0');
		}
	}
	//计算充值后余额
	function changeMon(){
		var num=$("#chargeSum").val();//充值金额
	    if  (num==''){
	    	  $("#chargeSum").val('0');
	    	  num = 0;
		}
        $("#finalAva").text((parseFloat($("#availableMoneys").text())+parseFloat(num)).toFixed(2));
        
		if($("#rechargeType").val() == '${PAYMENT_TYPE_ON}'){ //网上充值
		 var pattern =/^[0-9]+([.]\d{1,2})?$/;
		 if(!pattern.test(num)){
			 alertc("请输入数字,最高保留两位小数");
		    return false;
		 }
		var ava=${userAccount.availableMoney};//可用余额
		if(!feeLv){
			feeLv=0;
		}
		var fees = fee[$("#rechargeForm").find("input[name='bank']:checked").attr("id")]; 
		var total = parseFloat(num)+parseFloat(ava);
		var sxf = 0;
		if(fees.split("_")[0]=="BL"){
			feeLv = fees.split("_")[1];
			sxf = (num*feeLv/1000);
		}
		if(fees.split("_")[0]=="GD"){
			feeLv = fees.split("_")[1];
			sxf = feeLv;
		}
		total = total-sxf;
		$("#feeB").html(addCommas(sxf));
		if(num<=sxf){
			alertc("充值金额要大于手续费");
			return;
		}
		if($("#rechargeForm").find("input[name='bank']:checked").val() == null){
			$("#sumBalance").text("请选择充值方式").css("color","");
		}else if($("#chargeSum").val()==""){
			$("#sumBalance").text("请输入充值金额").css("color","");
		}else{
			$("#sumBalance").text(total.toFixed(2));
		}
	}else if($("#rechargeType").val() == '${PAYMENT_TYPE_OOF}'){
		if($("#chargeSum").val()==""){
			$("#sumBalance").text("请输入充值金额").css("color","");
		}else{
			var ava="${userAccount.availableMoney}";//可用余额
			var total=parseFloat(num)+parseFloat(ava);
			$("#sumBalance").text(total.toFixed(2));
		}
	}
}
	//提交充值记录
	function frmSubmit(){
		var num=$("#chargeSum").val();
	    var pattern =/^[0-9]+([.]\d{1,2})?$/;
	    /* if($(".sl_r_d").is(":checked")!=true){
	    	 alertc("请选中充值银行！");
	    	 return;
	    }  */
	    var payId = $("input[name='rechargeId']:checked").val();
	    var cardId = $("input[name='rechargePayment']:checked").val();
		if(num<10){
		   alertc("充值金额必须大于等于10元！");
		}else  if(!pattern.test(num)){
		   alertc("请输入数字,最高保留两位小数");
		  return false;
		}else  if(num>10000000){
	   		alertc("充值金额需小于1千万！");
		  return false;
		}else if(num<=parseFloat($("#feeB").html())){
			alertc("充值金额要大于手续费");
			return;
		}else if (payId == 2 && (cardId ==undefined || cardId =='')) {
			alertc("请选择银行卡");
			return;
		}else{
			$("#rechargeContent").html("确定充值"+$("#chargeSum").val()+"元！");
			$("#tishizheceng").show();
			$("#tishikuang").show();
		}
	}
	
	function makeSure(){
		closePop();
		var rechargeType = $("input[name='rechargeType']:checked").val();
		//线下充值
		if(rechargeType==2){
			$("#rechargeForm").attr("target","");
			 $.ajax({
		            dataType: 'json',
		            url:'${path }/account/xianxiaRecharge.do',
		            data:$("#rechargeForm").serialize(),
		            type: 'POST',
		            success: function(data) {
	            		alertc(data.msg,reloadPage);
		            }
		        });      
		}else{
			$("#rechargeForm").submit();
		}
	}
	function closePop(){
		$("#tishizheceng").hide();
		$("#tishikuang").hide();
	}
	//===========================点击展开关闭效果====================================  
	function openShutManager(oSourceObj, oTargetObj, shutAble, oOpenTip,
			oShutTip) {
		var sourceObj = typeof oSourceObj == "string" ? document
				.getElementById(oSourceObj) : oSourceObj;
		var targetObj = typeof oTargetObj == "string" ? document
				.getElementById(oTargetObj) : oTargetObj;
		var openTip = oOpenTip || "";
		var shutTip = oShutTip || "";
		if (targetObj.style.display != "none") {
			if (shutAble)
				return;
			targetObj.style.display = "none";
			if (openTip && shutTip) {
				sourceObj.innerHTML = shutTip;
			}
		} else {
			targetObj.style.display = "block";
			if (openTip && shutTip) {
				sourceObj.innerHTML = openTip;
			}
		}
	}
	var feeLv;//线上充值费率
	function feeFun(id){
		var payId = $("#"+id).val();
		var fees = fee[payId]; 
		if(fees.split("_")[0]=="BL"){
			feeLv = fees.split("_")[1];
		}
		if(fees.split("_")[0]=="GD"){
			feeLv = fees.split("_")[1];
		}
		if(fees=="NO"){
			feeLv = 0;
		}
		changeMon();
	}
	$("#rechargeForm").validate({
		focusInvalid:false,
		 rules: {
			 chargeSum:{
				 required:true,
				 number:true,
				 min:0
			 }
		 },
		 submitHandler:function(form){
			  if($('#rechargeType').val() == '1'){ //网上充值
					 showOnlineOverlay();
			 }else{
				 showOfflineOverlay();
				 if($('#rechargeType').val() == '1'){ //网上充值
					 showOnlineOverlay();
				}
			 }
			 
			 form.submit();
		 }
	});
	function  changeTabs(obj){
		if(obj==1){
			$("#problems").removeClass("current");	
			$("#tipss").addClass("current");
			$("#tips").css("display","block");
			$("#problem").css("display","none");
		}else{
			$("#tipss").removeClass("current");	
			$("#problems").addClass("current");
			$("#problem").css("display","block");
			$("#tips").css("display","none");
		}
		
	}
	function myFun(obj){
		if(obj==2){
			$("#dis").hide();
			$("#dis1").show();
			$("#feeDiv").hide();
			$("#remarkDiv").show();
		}
		if(obj==1){
			$("#dis").show();
			$("#dis1").hide();
			$("#remarkDiv").hide();
		}
	}
	
	function reloadPage(){
		window.location.reload(true);
	}
	
	 /* 显示遮罩层 */
	 function showOnlineOverlay() { 
	      $("#overlay").height(pageHeight());
	      $("#overlay").width(pageWidth());
	     // fadeTo第一个参数为速度，第二个为透明度
	     // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
	      $("#overlay").fadeTo(200, 0.5);
	      document.getElementById("onlineWindow").style.display='block';
	 }
	 /* 显示遮罩层 */
	 function showOfflineOverlay() { 
	      $("#overlay").height(pageHeight());
	      $("#overlay").width(pageWidth());
	     // fadeTo第一个参数为速度，第二个为透明度
	     // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
	      $("#overlay").fadeTo(200, 0.5);
	      document.getElementById("offlineWindow").style.display='block';
	 }
	 
	 /* 隐藏覆盖层 */
	  function hideOverlay() {
		 $("#overlay").fadeOut(200);
	      //document.getElementById("onlineWindow").style.display='none';
	      $("#onlineWindow").hide();
	  }

	 /* 当前页面高度 */
	  function pageHeight() {
	      return document.body.scrollHeight;
	 }
	 
	 /* 当前页面宽度 */
	 function pageWidth() {
	     return document.body.scrollWidth;
	 }
	 $(document).ready(function() {
		 if ($.browser.msie && $.browser.version <= 9) {
			$(".test:eq(0)").css("line-height","22px");
		 }
	 });
	 
	 /* 改变支付方式*/
	 function changePayType() {
		 var payId = $("input[name='rechargeId']:checked").val();
		 if (payId == 2) {
			 $("#offline").show();
             $("#li-offline").show();
		 } else {
             $("#offline").hide();
             $("#li-offline").hide();
		 }
	 }
	
</script>
</html>