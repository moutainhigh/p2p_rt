<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />


<title>${showTitle }</title>
<style>
.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 68);
}

.white_content {
	display: none;
	position: absolute;
	top: 25%;
	left: 25%;
	width: 55%;
	height: 55%;
	padding: 20px;
	border: 10px solid orange;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>
 <style> 
 .closeRight{float: right; margin:15px 15px 15px 0px; font-size: 24px; color:#0099cb;}
        .black_overlay{ 
    background: #929395;
    filter: alpha(opacity=60); /* IE的透明度 */
    opacity: 0.6;  /* 透明度 */
    display: none;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 1000; /* 此处的图层要大于页面 */
    display:none;

        } 
        .white_content { 
            display: none; 
            position: absolute; 
            top: 25%; 
            left: 25%; 
            width: 55%; 
            height: 55%; 
            padding: 20px; 
            border: 10px solid #999; 
            background-color: white; 
            z-index:1002; 
            overflow: auto; 
        } 
        .tenderBtn {
        width:130px;height:36px; background:url(${cssJsPath}/images/bg/btn_bg.png) no-repeat;
         display:block; text-align:center;color:#fff;margin-top:10px;
        }
    </style> 
</head>
<body>
	<jsp:include flush="false" page="/top.do"></jsp:include>
	<div class="border"></div>
	<!-- header end-->
	<div id="wrapper">
		<div class="invest-title the-toback">
			<a href="javascript:window.history.go(-1)" class="toback r3"><i
				class="icons arrow-gray-dotl"></i>返回</a>
			<h2>直投区详情</h2>
			<jsp:include flush="false" page="/showAvailableMoney.do"></jsp:include>
			<b class="line"></b>
		</div>
		<div class="invest-top">
			<div class="model-box">
				<div class="head">
					<h2>${borrow.borrowTitle }</h2>
					&nbsp;&nbsp;&nbsp;&nbsp;<font color="#F82800" size="2px">
					<c:if test="${borrow.partAmount > 0 }">
					(此标有分摊奖励总额${borrow.partAmount }元)
					</c:if>
					<c:if test="${borrow.proportionRate > 0 }">
					(投标会获得${borrow.proportionRate }%的奖励)
					</c:if>
					</font>
				 	<!-- <span class="icons type"> 
					</span> -->
 
				</div>
				<div class="profit">
					<dl class="f">
						<dt>借款金额</dt>
						<dd>
							<ins>￥</ins>
							<em>${borrow.borrowSum }</em>
						</dd>
					</dl>
					<dl>
						<dt>
							<b>年利率</b><a href="javascript:;"
								class="icons yhelp ToolTips ToolTipCol"
								data-text="借款人借款需支付的借款利率，以年度百分比表示">帮助</a>
						</dt>
						<dd>
							<!--  -->
							<em>${annualInterestRateStr[0] }<span class="flot">.${annualInterestRateStr[1] }</span></em>%
						</dd>
					</dl>
					<dl>
						<dt>还款期限</dt>
						<dd>
							<c:if test="${borrow.isDay==1}">
							<em>${borrow.borrowTimeLimit }</em>天
							</c:if>
							<c:if test="${borrow.isDay==2}">
							<em>${borrow.borrowTimeLimit }</em>个月
							</c:if>
						</dd>
					</dl>
				</div>

				<div class="repay">
					<span class="t">${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}<a href="javascript:;"
						class="icons yinfo ToolTips ToolTipCol"
						data-text="投资直投区项目每月可收回本金和收益之和">帮助</a>
						<%-- <ins>￥</ins><em>${borrow.monthlyRepayment }</em> --%></span>
				</div>

				<div class="explain" >
					<span style="font-weight: bolder;font-size: 14px;color: #475058;" >借款说明</span> 
					<span id="showInfo" >
						${borrow.borrowContent}
						 <%-- <c:choose>
								<c:when test="${fn:length(borrow.borrowContent) > 50}">
									<c:out
										value="${fn:substring(borrow.borrowContent,0,50)}" />
										<span id="hideExplainMore"><span class="explain-dot">...</span>[<a
						class="explain-button" href="javascript:showInfo();">详情</a>]</span>
								</c:when>
								<c:otherwise>
									<c:out value="${borrow.borrowContent}" />
								</c:otherwise>
							</c:choose>  --%>
						 
					</span>
				</div>
				<div class="expl">
					<ul>
						<li class="ratio-per">
							<div class="per" data-rel="100.00">
								<div style="width:${percentage}%"></div>
							</div> <strong class="ratio">${percentage}%</strong>
						<li id="dt">
						</li>
						<li><span class="vouch"><i
								class="icons ver-green-down"></i>100%本息担保</span></li>
					</ul>
				</div>
			</div>
			<div class="model-box invest-sum">
				<div class="head">
					<h2>投资金额</h2>
					<b>可投￥<em><span class="leftAmount">
					<c:if test="${empty userAccount }">
						0.00
					</c:if>
					<c:if test="${ not empty userAccount }">
						
					<c:if test="${(borrow.borrowSum-borrow.tenderSum)>userAccount.availableMoney }">${ userAccount.availableMoney}</c:if>
					<c:if test="${(borrow.borrowSum-borrow.tenderSum)<userAccount.availableMoney }">${borrow.borrowSum-borrow.tenderSum}</c:if>
					</c:if></span></em>元
					</b>
				</div>


				<div class="doned" style="display: block">剩余可投资金额：${borrow.borrowSum-borrow.tenderSum }元</div>

				<div class="iwant-in">


					<!-- gbtn-disabled -->

					<span id="tenderBtnSpan" class="btnDisabled">投 标</span>
				</div>
			</div>
		</div>
		<div class="jTabs">
			<ul id="investTabs" style="left: 30px">
				<li class="jTab" style="margin-right: 15px;" id="investRecordsBtn">投资记录<span class="r5"></span></li>
				<li class="jTab" style="margin-right: 15px;">帐户资金情况</li>
         	    <li class="jTab" style="margin-right: 15px;">借款资金情况</li>
                <li class="jTab" style="margin-right: 15px;">投标资金情况</li>
                <li class="jTab" style="margin-right: 15px;">信用额度情况</li>
			</ul>
		</div>
		<div id="investPanel" class="model-auto">
			<div class="jPanel" style="display: block;" id="investRecords">
				<div class="model-box rec-items" id="tab">
					<ul class="items title">
						<li class="col_1">投资人</li>
						<li class="col_2">债权人</li>
						<li class="col_3">投资金额(￥)</li>
						<li class="col_4">投资时间</li>
						<li class="col_5">状态</li>
					</ul>
					<div id="investRecordsTable">
						<jsp:include page="../page.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="couponDialog" class="dialog-main" style="width: 600px">
		<div class="dialog-head" style="background: #e7ecee">
			<h2>选择当前可用红包</h2>
			<a href="javascript:;" class="closeModal r3"><i class="icons">关闭</i></a>
		</div>
		<div class="redbag-loan" id="couponEntry">
			<ul class="items">
				<li class="co1_1">类型</li>
				<li class="co1_2 pd">剩余可用金额(￥)</li>
				<li class="co1_3">剩余有效期</li>
				<li class="co1_4">本次可用金额</li>
				<li class="co1_5">选择</li>
			</ul>

		</div>
		<div class="dialog-foot">
			<div class="bank-action">
				<a href="javascript:;" class="closeModal r3 bank-delete"><i
					class="icons yclose"></i>取消</a><a href="javascript:selectCoupon();"
					class="r3 bank-bind"><i class="icons cm-white" id=""></i>确认</a>
			</div>
		</div>
	</div>
	<!--end-->
	<jsp:include page="/foot.do"></jsp:include>
	<div class="toubiao white_content"
		style="width: 765px; height: 390px; background: #fff; display: none;"
		id="tenderDiv">
		<a class="closeRight" style="cursor: hand;" onclick="closeDialog()" href="#">✖</a>
		<form id="tenderFrm" action="">
			<input type="hidden" name="annualInterestRate"
				value="${borrow.annualInterestRate }"> <input type="hidden"
				name="borrowSum" value="${borrow.borrowSum }"> <input
				type="hidden" name="borrowTimeLimit"
				value="${borrow.borrowTimeLimit }"> <input type="hidden" id="borrowId"
				name="borrowId" value="${borrow.id }"> <input type="hidden"
				name="bidKind" value="${borrow.bidKind }">
			<input type="hidden" name="SignBorrowId" value="${SignBorrowId }">
			<h2
				style="height: 60px; width: 765px; font: bold 18px/60px Verdana, Arial, Helvetica, sans-serif; text-indent: 30px; color: #333; border-bottom: 1px solid #ddd; margin-bottom: 20px;">${borrow.borrowTitle }</h2>
			<ul
				style="font: 14px/32px Verdana, Arial, Helvetica, sans-serif; float: left; margin-left: 30px; width: 300px;">
				<li>借款人：${borrow.user.userAccount }</li>
				<li>借款金额：￥${borrow.borrowSum }元</li>
				<c:choose>
					<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
					<li>每份金额：￥${borrow.amountPerCopies }元</li>
					<li>总份数：<fmt:formatNumber value="${(borrow.borrowSum)/borrow.amountPerCopies }" pattern="#" type="number"/>
					份</li>
					<li>还剩份数：<font id="remainCopies"><fmt:formatNumber value="${(borrow.borrowSum-borrow.tenderSum)/borrow.amountPerCopies }" pattern="#" type="number"/></font>
					份</li>
					</c:when>
					<c:otherwise>
					<li>还需借款：￥${borrow.borrowSum-borrow.tenderSum }</li>
					</c:otherwise>
				</c:choose>
				<li>借款年利率：${borrow.annualInterestRate }%</li>
				<li>已经完成：${percentage }%</li>
				<li>借款期限： <c:if test="${borrow.isDay==1}">
							<em>${borrow.borrowTimeLimit }</em>天
							</c:if>
							<c:if test="${borrow.isDay==2}">
							<em>${borrow.borrowTimeLimit }</em>个月
							</c:if></li>
				<li>还款方式：${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</li>
			</ul>
			<ul
				style="font: 14px/32px Verdana, Arial, Helvetica, sans-serif; float: left;">
				<li>您的可用余额：<strong
					style="font: bold 16px Arial, Helvetica, sans-serif; color: #ec7529;">${userAccount.availableMoney }</strong>元<a
					href="${path }/account/recharge.html" target="_blank"> [我要充值]</a></li>
				<li>最多投标总额:<c:if test="${borrow.maxAmount <= 0}">不限制</c:if>
					<c:if test="${borrow.maxAmount > 0}">${borrow.maxAmount }</c:if>
				</li>
				<li>最少投标总额:<c:choose>
					<c:when test="${CACHE_SYS_FEES_RATE.sysInvestMinmoney > borrow.minAmount}">
					${CACHE_SYS_FEES_RATE.sysInvestMinmoney}
					</c:when>
					<c:otherwise>
					${borrow.minAmount }
					</c:otherwise>
				</c:choose>
				
				</li>
				<c:choose>
					<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
					<li><span>购买份数：</span> <input size="2" onkeyup="value=value.replace(/[^0-9|.]/g,'')"
					style="border: 1px solid #B4B9BD; height: 24px; line-height: 24px; padding: 0;width: 120px"
					type="text" name="tenderCopies"
					class="input" maxlength="7"> <span>份</span>
				</li>
					</c:when>
					<c:otherwise>
					<li><span>投标金额：</span> <input size="2" onkeyup="value=value.replace(/[^0-9|.]/g,'')"
					style="border: 1px solid #B4B9BD; height: 24px; line-height: 24px; padding: 0;width: 120px"
					type="text" id="signName" name="tenderAmount"
					class="input" maxlength="18"> <span>元</span>
					<input type="button" onclick="fillTenderMoney()"value="自动填充金额"style="padding:5px; margin:12px 0; border:1px solid #EC7529; background-color:#EC7529;color:#fff; -webkit-border-radius:3px; -moz-border-radius:3px; border-radius:3px; outline:0;"/>
				</li>
					</c:otherwise>
				</c:choose>
				
				<li><span>支付密码：</span> 
				<input type="hidden" name="publickey" id="publickey" value="${publickey}" />
				<input 
					style="border: 1px solid #B4B9BD; height: 24px; line-height: 24px; padding: 0;width: 120px"
					type="password" name="payPassword" id="payPassword"
					class="input" maxlength="18"></li>
				<c:if test="${not empty borrow.tenderPassword }">
				<li><span>投标密码：</span> <input
					style="border: 1px solid #B4B9BD; height: 24px; line-height: 24px; padding: 0;width: 120px"
					type="password" name="tenderPassword" id="tenderPassword"
					class="input" maxlength="18"></li>
				</c:if>
				<li><span>&nbsp;&nbsp;&nbsp;验证码：</span> <input maxlength="4"
					style="width: 60px; margin-bottom: 10px;" name="captcha"
					type="text" id="captcha"> <img
					id="captchaImg" 
					src="${path }/captcha.svl"
					onclick="this.src='${path }/captcha.svl?d='+new Date()*1"
					valign="bottom" alt="点击更新" title="点击更新" /></li>
				<li><a href="javascript:saveTender()"
					style="width:130px;height:36px; background:url(${cssJsPath}/images/bg/btn_bg.png) no-repeat; display:block; text-align:center;color:#fff;margin-top:10px;">确认投标</a>
					</li>
				<li style="color: #f00;">注意：点击按钮表示您将确认投标金额并同意支付.</li>
			</ul>
		</form>
	</div>
	<div id="fade" class="black_overlay"></div> 
</body>
<script type="text/javascript">
//遍历标状态
var ALL_STATUS = {};
<c:forEach var="item" items="${BORROW_ALL_STATUS}"> 
	ALL_STATUS["${item.key}"] = "${item.value}";
</c:forEach>
//遍历投标状态
var TENDER_STATUS = {};
<c:forEach var="item" items="${TENDER_ALL_STATUS}"> 
	TENDER_STATUS["${item.key}"] = "${item.value}";
</c:forEach>
var hasLogin = ${hasLogin};
var noSetPayPassword = false;
<c:if test="${not empty noSetPayPassword}">
noSetPayPassword = ${noSetPayPassword};
</c:if>

function saveTender() {
	var availableMoney=${userAccount.availableMoney }+"";
	$("#tenderFrm").validate({						  
		rules: {
			<c:choose>
			<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
			tenderCopies: {
				required: true,
				number:true,
				min:1,
		//		max:$("#remainCopies").text(),
				digits:true
			},
			</c:when>
			<c:otherwise>
			tenderAmount: {
				required: true,
				number:true
				/* max:availableMoney */
			},
			</c:otherwise>
		</c:choose>
			
		<c:if test="${not empty borrow.tenderPassword }">
			tenderPassword: {
				required: true
			},
		</c:if>
			payPassword: {
				required: true
			},
			captcha : {
				required : true
			}
			
			
		},
		//指定错误信息位置
		errorPlacement: function (error, element) { 
			//	if(element.)
				/*var curName = element.attr("name");
				 if(curName == 'tenderAmount'){
			//		error.appendTo(element.parent());
				}else{
					
				} */
				error.appendTo(element.parent());
				
      			
		}
		
	});
	if($("#tenderFrm").valid()){
		tenderFrm.payPassword.value=encryptByDES(tenderFrm.payPassword.value,tenderFrm.publickey.value);
		/* <c:if test="${not empty borrow.tenderPassword }">
		   tenderFrm.tenderPassword.value=encryptByDES(tenderFrm.tenderPassword.value,tenderFrm.publickey.value); 
		</c:if> */
		<c:choose>
		<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
			var copies = $("input[name='tenderCopies']").val();
			var perMoney = ${borrow.amountPerCopies };
			var totalMoney = copies*perMoney;
			confirmc("确认购买" + copies + "份，总金额"+totalMoney+"元?", tenderConfirmed);
		</c:when>
		<c:otherwise>
			var tenderMoney = $("input[name='tenderAmount']").val();
			confirmc("确认投资金额" + tenderMoney + "元?", tenderConfirmed);
		</c:otherwise>
	</c:choose>
		
	}
	
}
function tenderConfirmed() {
	var data = $('#tenderFrm').serialize();
	$.ajax({
		cache : false,
		type : "POST",
		url : "${path }/borrowTender/tender.do",
		data : data,
		async : false,
		error : function(request) {
			initCaptcha();
			alertc("服务器异常");				
		},
		success : function(data) {
			initCaptcha();
			var callBack = null;
			if(data.code == '200'){ //成功
				callBack = closeDialog;
			}
			alertc(data.msg,callBack);
		}
	});
}

	function showTender() {
		if(!hasLogin){
			window.location.href = "${path }/login";
			return;
		}
		if(noSetPayPassword){
			alertc("请先设置支付密码!",jumpToSetPayPassword);
			return;
		}
		//	$("#tenderDiv").css("display","block");
		document.getElementById('tenderDiv').style.display = 'block';
		document.getElementById('fade').style.display = 'block';
		initCaptcha();
		document.getElementById("tenderFrm").reset();
		$("#fade").css({ "width": $(document).width(), "height": $(document).height() });
	}
	function jumpToSetPayPassword(){
		window.location.href = "${path }/verify/saveUserPayPass.do";
	}
	function disabledBtn(text){
		$("#tenderBtnSpan").html(text);
		$("#tenderBtnSpan").attr("class","btnDisabled");
		$("#tenderBtnSpan").unbind("click");
	}
	function enabledBtn(){
		$("#tenderBtnSpan").html("投标");
		$("#tenderBtnSpan").attr("class","gbtn");
		$("#tenderBtnSpan").click(function(){
			showTender();
		});
	}

	function closeDialog() {
		document.getElementById('tenderDiv').style.display = 'none';
		document.getElementById('fade').style.display = 'none';
		window.location.reload();
	}

	var borrowTime = 0;
	var nowDate=0;
	var bStatus = "${borrow.borrowStatus }";
	var Published = ${Published}; //发布状态
	var intereValId = null;
	$(function() {
	//	closeInfo();
		if(Published){
			borrowTime = ${allowTenderTime };
			if(bStatus == '2'){
				enabledBtn();
			}else{
			//	$("#tenderBtnSpan").html();
				disabledBtn(ALL_STATUS[bStatus]);
			}
		}else{
			borrowTime = ${publishTime };
			disabledBtn("待开始");
			
		}
		nowDate=${nowDate};
		//初始加载分页
		var borrowId=$("#borrowId").val();
		var data = {"borrowId":borrowId};
		getPageFrom(data,
				"${path }/borrow/shwoBorrowTenderInfoByPage.do",
				generateInvestRecordsTable, "investRecordsTable");
		if(bStatus == '2'){
			changeEndTime();
			intereValId = setInterval(changeEndTime,1000);
		}else{
			document.getElementById("dt").innerHTML="发布时间：<fmt:formatDate value='${borrow.publishDatetime }' pattern='yyyy年MM月dd日'/>";
		}
		
	});


	 
	
	function getTimeStr(){
		 var a,b,c,d,cy=cd=ch=cm=cs=0;
		 d=borrowTime;
		 nowDate=nowDate+1000;
		 b = nowDate;
		 time_distance=Math.floor((d-b));
		int_day=Math.floor(time_distance/86400000); // 1000*60*60*24
		int_hour=Math.floor((time_distance-int_day*86400000)/3600000);
		int_minute=Math.floor((time_distance-int_hour*3600000-int_day*86400000)/60000);
		int_second=Math.floor((time_distance-int_minute*60000-int_hour*3600000-int_day*86400000)/1000);
			if(int_day<0){
				int_day="0";
				int_hour="0"
				int_minute="0";
				int_second="0";
			}
		  	if(int_hour<10)
   			int_hour="0"+int_hour;
   			if(int_minute<10)
   			int_minute="0"+int_minute;
   			if(int_second<10)
   			int_second="0"+int_second;
	
   			if(int_day <= 0 && int_hour <= 0 && int_minute<=0 && int_second <= 0){
   				dealTimeZero();
   			}
   			
		 	return int_day+'天'+int_hour+'时'+int_minute+'分'+int_second+'秒';
		}

		function changeEndTime(){	
			if(Published){
				document.getElementById("dt").innerHTML="倒计时:"+getTimeStr();
			}else{
				document.getElementById("dt").innerHTML="开始倒计时:"+getTimeStr();
			}
		 	
		}
	function dealTimeZero(){
		window.clearInterval(intereValId);
		if(Published){
		//	alert(1);
			disabledBtn("已流标");
		}else{
			Published = true;
			enabledBtn();
		}
	}
	
	
	//拼成表格
	function generateInvestRecordsTable(data) {
		var investTime = data.tender_addtime;
		investTime=toDate(investTime,"yyyy-MM-dd hh:mm");
		htmlStr = '<ul class="items">';
		var fullNameInvestor = data.investorAccount;
		 var investor = fullNameInvestor.subChsString(0, 2)+'***'+fullNameInvestor.subChsString(fullNameInvestor.lengthStr() - 2, 2);             
		htmlStr += '<li class="col_1"><span class="fl">' + investor
				+ '</span></li>';

		var fullNameCreditor = data.borrowAccount;
		var creditor = fullNameCreditor.subChsString(0, 2)+'***'+fullNameCreditor.subChsString(fullNameCreditor.lengthStr() - 2, 2);  
		htmlStr += '<li class="col_2">' + creditor + '</li>';
		htmlStr += '<li class="col_3">' + Util.numFormat(data.tender_amount,2) + '</li>';
		htmlStr += '<li class="col_4">' + investTime + '</li>';
		
		var tenderStatus = data.tender_status+"";
	//	htmlStr += '<li class="col_5"><span class="succ"><i class="icons cm-green"></i>'+TENDER_STATUS[tenderStatus]+'</span></li>';
	htmlStr += '<li class="col_5"><span class="">'+TENDER_STATUS[tenderStatus]+'</span></li>';
		htmlStr += '</ul>';

		return htmlStr;

	};
	//自动填充金额
	function fillTenderMoney(){
		var needMoney=${borrow.borrowSum-borrow.tenderSum };//还需金额
		var availableMoney=${userAccount.availableMoney };//可用余额
		var maxAmount=${borrow.maxAmount};//最多投标
		var minAmount=${borrow.minAmount};//最小投标
		if(availableMoney < needMoney){//可用余额不足
			if(availableMoney <minAmount){
				$("#signName").val(0);
			}else if(availableMoney>maxAmount && maxAmount>0){
				$("#signName").val(maxAmount);
			}else{
				$("#signName").val(availableMoney);
			};
			 
		}else{//可用余额够
			if(needMoney>maxAmount && maxAmount>0){
				$("#signName").val(maxAmount);
			}else{
				$("#signName").val(needMoney);
			};
			
		};
	}

	/**
	function closeInfo(){
		alert(1);
		var strHtml="";
		<c:choose>
		<c:when test="${fn:length(borrow.borrowContent) > 50}">
			strHtml+="${fn:substring(borrow.borrowContent,0,50)}"
				+"<span id='hideExplainMore'><span class='explain-dot'>...</span>"
				+"</span>[<a class='explain-button' href='javascript:showInfo();'>详情</a>]</span> ";
		</c:when>
		<c:otherwise>
		strHtml+="${borrow.borrowContent}";
		</c:otherwise>
	</c:choose>
		
		$("#showInfo").html(strHtml);
	}
	
	function showInfo(){
		var strHtml="";
		strHtml+="${borrow.borrowContent }"
		+"[<a class='explain-button' href='javascript:closeInfo();'>关闭</a>]</span>";
		$("#showInfo").html(strHtml);
	}**/
	
	  
	
</script>
<!-- 账户公开信息 -->
<script type="text/javascript">
	$("#investTabs").find("li").each(function (i,v){
		  $(v).bind("click",function (){
			  var userId=${borrow.userId}+"";
			  var openAccount=${borrow.openAccount}+"";
			  var openBorrow=${borrow.openBorrow}+"";
			  var openTender=${borrow.openTender}+"";
			  var openCredit=${borrow.openCredit}+""; 
			  var html="<ul style='padding:20px 30px;'><li>用户没有公开</li></ul>";
			  if(i==0){
					 location.reload();
			   }
			  if(i==1){//帐户资金情况
					  var url="../../showBorrowInfo/account.do";
					  $.post(url,{userId:userId},function (data){
						 if(data){
							 if(openAccount==1){
								  html="<ul style='padding:20px 30px;'>"+
							              "<li>总金额："+data.allMoney+"元</li>"+
							              "<li>可用金额："+data.availableMoney+"元</li>"+
							              "<li>冻结金额："+data.unavailableMoney+"元</li>"+
								       "</ul>"; 
							   } ;
							  $("#tab").html(html);
						 };
					  });
				 
				  
			  }else if(i==2){//借款资金情况  
				  var url="../../showBorrowInfo/borrowAccount.do";
				  $.post(url,{userId:userId},function (data){
					 if(data){
						 if(openBorrow==1){
							 var waitRepayAll=data.waitRepayCapital+data.waitRepayInterest+"";
							 waitRepayAll=waitRepayAll.substring(0,waitRepayAll.indexOf(".") + 3);
							 html="<ul style='padding:20px 30px;'>"+
							              "<li>借款总金额："+data.borrowSum+"元</li>"+
							              "<li>待还总金额："+waitRepayAll+"元</li>"+
							          "</ul>"; 
						 };
						 $("#tab").html(html);
					 } ;
				  });
			  }else if(i==3){//投标资金情况
				  var url="../../showBorrowInfo/tender.do";
				  $.post(url,{userId:userId},function (data){
					 if(data){
						 if(openTender==1){
							 var effectiveAmount=data.effectiveAmount;
							 if(effectiveAmount == undefined){
								 effectiveAmount = "0.00";
							 }
							 var waitRepossessedCapital=data.waitRepossessedCapital;
							 var waitRepossessedInterest=data.waitRepossessedInterest;
							 if(waitRepossessedCapital==undefined){
								 waitRepossessedCapital=0.00;
							 }
							 if(waitRepossessedInterest==undefined){
								 waitRepossessedInterest=0.00;
							 }
							 var waitRepossessed=waitRepossessedCapital+waitRepossessedInterest;
							 html="<ul style='padding:20px 30px;'>"+
					                  "<li>投资总金额:"+effectiveAmount+"元</li>"+
			            	           "<li>待收金额："+waitRepossessed+"元</li>"+
			            	           "<li>投资次数："+data.tenderNum+"次</li>"
							     "</ul>"; 
						 } ;
						 $("#tab").html(html);
					 };
				  });
				  
			  }else if(i==4){//信用额度情况
				  var url="../../showBorrowInfo/borrowInfo.do";
				  $.post(url,{userId:userId},function (data){
					 if(data){
						 if(openCredit==1){
							 if(data.aheadRepayTime==undefined){
								 data.aheadRepayTime="0";
							 }
							  html="<ul style='padding:20px 30px;'>"+
							              "<li>成功借款"+data.borrowSuccess+"次</li>"+
							              "<li>借款流标"+data.flowStatus+"次</li>"+
							              "<li>成功还款"+data.repaySuccessStaus+"次</li>"+
							              "<li>提前还款"+data.aheadRepayTime+"次</li>"+
							              "<li>"+data.repaying+"笔待还款</li>"+
							              "<li>逾期"+(data.overStatus+data.overSuccessRepay)+"笔</li>"+
							              "<li>逾期还款成功"+data.overSuccessRepay+"笔</li>"+
							          "</ul>"; 
						 } ;
						 $("#tab").html(html);
					 };
				  });
			  };
			  
			  
		  });
	  });
	</script>
</html>
