<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>

<title>${showTitle }</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/invest.css" />
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
<script type="text/javascript" src="${frontPath }/js/page4transferInfo.js"></script>
</head>
<body>
	<!--头部-->
	<jsp:include flush="false" page="/top.do"></jsp:include>
	<!-- banner -->

	<div class="content xm_con">
		<div class="content_in">
			<div>
				<a href="${path }/">首页</a>
				>
				<a href="${path }/creditassignment">债权转让</a>
				>
				<a>${borrow.borrowTitle }</a>
			</div>
			<div class="plan_con">
				<div class="title">
					<h1>${borrow.borrowTitle }</h1>
					<span>稳定高收益 多项选择</span> <a href="${path }/invest">更多项目 ></a>
				</div>
				<div class="plan_txt">
					<div class="plan_l">
						<div class="txt_con">
							<p>
								<i>年化收益</i><span>${borrow.annualInterestRate }<em
									style="font-size: 38px;">%</em></span>
							</p>
							<ul>
								<li><i>转让金额：</i><span class="bold">${borrowTransfer.transferMoney}元</span></li>
								<li><i>剩余期限：</i><span>${leftDays }天</span></li>
								<li><i>还款方式：</i><span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span></li>
							</ul>
							<ul>
								<li><i>转让人：</i><span>${borrowUserAccount }</span></li>
								<li><i>发布日期：</i><span><fmt:formatDate
											value="${borrowTransfer.createTime}" type="both"
											pattern="yyyy-MM-dd" /></span></li>
							</ul>
						</div>
						<div class="num_time add_time">
							<p class="time" id="endTimeTransfer">
								 	已经结束：<span><i>00</i> 天 <i>00</i> 时 <i>00</i> 分 <i>00</i>
										秒</span> 

							</p>
						</div>
					</div>
					<!-- plan_l -->
					<form id="tenderFrm" action="">
					<input type="hidden" name="transferId" id="transferId" value="${borrowTransfer.id }" />
					<input type="hidden" name="publickey" id="publickey" value="${publickey}" />
					<div class="plan_r">
						<div class="money_con">
							<p>
								账户余额<span>${userAccount.availableMoney}</span>元
							</p>
							<a href=" ${path }/account/recharge.html" target="_blank">充值</a>
						</div>
						<div class="input add_input">
							<input type="text" placeholder="${borrowTransfer.transferMoney+10}元起投 10元递增" id="auctionMoney" onkeyup="value=value.replace(/[^0-9]/g,'')" name="auctionMoney"> <a href="#"
								class="balan">元</a> <input type="hidden" name="transferId"
								id="transferId" value="${borrowTransfer.id }" />
						</div>
						<div class="input">
							<input type="password" value="" id="payPassword" placeholder="请输入支付密码"name="payPassword">
						</div>
						<a href="#" class="join_btn" id="tenderBtnSpan">立即竞标</a>
					</div>
					</form>
					<!-- plan_r -->
				</div>
			</div>
			<!-- plan_con -->

			<div class="plan_del">
				<div class="title">
					<a href="#" class="onchoice">项目详情</a> <a href="#">借款企业信息</a> <a
						href="#">认购记录</a>
				</div>
				<div class="detail_con">
					<ul class="pro1 detial1">
						<li><i>计划名称</i><span>${borrow.borrowTitle}</span></li>
						<li><i>理财计划简介</i><span>${borrowConent}</span></li>
						<li><i>可加入日期</i><span><fmt:formatDate
									value="${borrow.publishDatetime}" type="both"
									pattern="yyyy-MM-dd" /> 至 <fmt:formatDate
									value="${borrow.allowTenderTime}" type="both"
									pattern="yyyy-MM-dd" /></span></li>
						<li><i>锁定期</i><span> <c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}天
									</c:if> <c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}个月
									</c:if></span></li>
						<li><i>预期年化收益率</i><span>${borrow.annualInterestRate }%</span></li>
						<li><i>加入条件</i><span><c:choose>
									<c:when
										test="${CACHE_SYS_FEES_RATE.sysInvestMinmoney > borrow.minAmount}">
					${CACHE_SYS_FEES_RATE.sysInvestMinmoney}
					</c:when>
									<c:otherwise>
					${borrow.minAmount }
					</c:otherwise>
								</c:choose>元起</span></li>
						<li><i>收益方式</i><span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span></li>
						<li><i>起息日</i><span>起息日为该理财计划锁定期开始日当天</span></li>
						<li><i>标的资产</i><span>优质住宅房产抵押贷款</span></li>
						<li><i>保障方式</i><span>优质住宅房产抵押 100%本金保障</span></li>
						<li><i>退出说明</i><span>转让专区转让</span></li>
						<li><i>费用说明</i><span>加入费率：0.00%；退出费用：0%</span></li>
						<li><i>协议文本</i><span>见<a href="#">《浩茗投资协议》</a></span></li>
					</ul>
					<div class="pro1" style="display: none;">借款企业信息</div>
					<div class="pro1" style="display: none;">
						<table class="pro_num">
							<thead>
								<tr>
									<th width="190">认购人</th>
									<th width="240">认购金额(￥)</th>
									<th width="190">当前年利率</th>
									<th width="215">认购时间</th>
									<th width="205">状态</th>
								</tr>
							</thead>
							<tbody id="borrowRecord"></tbody>

						</table>
						<div class="page_num de_num" style="padding-top: 40px;" id="divs">
							<a href="javascript:void(0);" class="arr1"><</a> <a
								href="javascript:void(0);" class="page">1</a> <a
								href="javascript:void(0);">2</a> <a href="javascript:void(0);">3</a>
							<a href="javascript:void(0);" class="dian">...</a> <a
								href="javascript:void(0);">8</a> <a href="javascript:void(0);"
								class="arr1">></a>
						</div>
						<!-- detail_con -->
					</div>
				</div>
			</div>
			<!-- plan_del -->
		</div>
		<!-- content_in -->
	</div>
	<!-- content -->

	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
	
	<!-- 竞拍提示 start -->
	<div id='tishizheceng' style="position: absolute; z-index: 99998; top: 0px; left: 0px; width: 100%; height: 1584px; opacity: 0.3; background: rgb(255, 255, 255);display: none;"></div>
	<div id='tishikuang' class="gSys_msg_box" style="position: absolute; z-index: 99999; padding: 0px; margin: 0px; width: 460px; top: 300px; left: 561.5px;display: none;">
		<div class="ptitle"><span class="fn-bg rc-l"></span><h4>提示</h4><span class="fn-bg rc-r"></span><a href="javascript:closePop();" class="fn-bg aclose" title="关闭">关闭</a></div>
		<div class="pbox"><div class="pmsg"><b title="提示:" class="ico alert"></b><div class="ct" id="rechargeContent"></div></div></div>
		<div class="popt">
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="closePop()">取消</span></div></div>
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="makeSure()">确定</span></div></div>
		</div>
	</div>
	<!-- 竞拍提示 end -->
	
	
	<script type="text/javascript">
	var borrowTransferStatus = "${borrowTransfer.transferStatus}";
	var endTime = 0;
	var nowDate = 0;
	$(function(){
			var data={};
			var transferId=$("#transferId").val();
			var data = {"transferId":transferId};
			getPageFrom4TransferInfo(data,
					"${path }/borrowTransfer/getBorrowTransferAuction.do",
					getBorrowTransferAuction, "borrowRecord",null,"divs");
			endTime = ${endTime};
			nowDate = ${nowTime};
			if (borrowTransferStatus == '1') {
				if (nowDate - endTime < 0) {
					intereValId = setInterval(changeEndTime, 1000);
					$("#tenderBtnSpan").html("立即竞标");
					abledBtn();
					 $("#tenderBtnSpan").click(function() {
						showTender();
					}); 
				} else {
					$("#tenderBtnSpan").html("已结束");
					disabledBtn();
				}
			} else if(borrowTransferStatus == '3'){
				$("#tenderBtnSpan").html("转让失败");
				disabledBtn();
			}else{
				$("#tenderBtnSpan").html("已转让");
				disabledBtn();
			}
		} )
		 
//不能转让时
function disabledBtn(){
	$('#auctionMoney').attr("disabled","disabled");
	$('#payPassword').attr("disabled","disabled");
	$('#tenderBtnSpan').attr("disabled","disabled");
}
	
//可以转让时
function abledBtn(){
	$('#auctionMoney').removeAttr("disabled","none");
	$('#payPassword').removeAttr("disabled","none");
	$('#tenderBtnSpan').removeAttr("disabled","none");
}	

var noSetPayPassword = false;
<c:if test="${not empty payPassword}">
	noSetPayPassword =$('#payPassword').val();
</c:if>
function showTender() {
	if(noSetPayPassword){
		alertc("请先设置交易密码！");
		return;
	}
	var transferMoney = $('#auctionMoney').val();
	if(transferMoney =="" || transferMoney == "100元起投 100元递增" ){
		alertc("请输入竞拍价格,110.00元起投 10元递增");
		return;
	}
	var payPasswordNow = $('#payPassword').val();
	if(payPasswordNow == "" ){
		alertc("请输入交易密码");
		return;
	}
	$("#rechargeContent").html("你的竞拍价格为"+transferMoney+",请确定是否竞拍？");
	$("#tishizheceng").show();
	$("#tishikuang").show();
}

function makeSure(){
	closePop();
	saveTransfer();
}
function closePop(){
	$("#tishizheceng").hide();
	$("#tishikuang").hide();
}

function getBorrowTransferAuction(data,i){
	//alert()
	var createTime = data.create_time;
	var status="";
	if(data.status==1){
		status="完成";
	}else if(data.status==2){
		status="作废";
	}
	
	var fullNameCreditor = data.userAccount;
	var creditor = fullNameCreditor.subChsString(0, 2)+'***'+fullNameCreditor.subChsString(fullNameCreditor.lengthStr() - 2, 2);  
	createTime=toDate(createTime,"yyyy-MM-dd hh:mm:ss");
	htmlStr = '';
	
	if(i/2 == 0){
		htmlStr = '<tr style="background: #f7f8fa;">';
	}else{
		htmlStr = '<tr>';
	}
	htmlStr = htmlStr +'<td>'
			+creditor+'</td><td>￥'
			+Util.numFormat(data.auction_money,2)+'</td><td>'
			+data.cur_interest_rate+'%</td><td>'
			+createTime+'</td><td>'
			+status+'</td></tr>';
	return htmlStr;
}

 
//更改时间
function changeEndTime(){	
	$("#endTimeTransfer").html("剩余时间：<span>"+getTimeStr()+"</span>");
}	
	
//倒计时
function getTimeStr(){
	 var a,b,c,d,cy=cd=ch=cm=cs=0;
	 d=endTime;
	 
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
			
	 	return '<i>'+int_day+'</i> 天 <i>'+int_hour+'</i> 时 <i>'+int_minute+'</i> 分 <i>'+int_second+'</i> 秒';
	} 
	
function saveTransfer() {
	tenderFrm.payPassword.value = encryptByDES(tenderFrm.payPassword.value,
			tenderFrm.publickey.value);
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : "${path }/borrowTransfer/saveTransfer.do",
		data : $('#tenderFrm').serialize(),
		error : function(request) {
			alertc("服务器异常");
		},
		success : function(data) {
			if (data.code == '200') {
				alertc(data.msg);
				window.location.reload();
			} else {
				$("#captchaImg").click();
				$("input[name='captcha']").val("");
				alertc(data.msg);
			}
		}
	});
}
</script>
</html>

