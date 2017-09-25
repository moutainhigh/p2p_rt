<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../../taglibs.jsp"%>
	<title>${showTitle }-我的账户</title>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
  			<div class="J-ma-nav jboxsize">
   				<!--左侧栏目  -->
				<input type="hidden" value="left1" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
			</div>
			<div class="J-ma-conR">
				<div class="J-macR-top clearfix">
					<ul>
						<li>可用余额</li>
						<li><h1>¥${userAccount.availableMoney }</h1></li>
						<li class="J-ma-btn"><a href="${path }/account/recharge.html">充值</a></li>
					</ul>
					<ol>
						<li><em>累计收益</em></li>
						<li><h1>${totalIncome}</h1></li>
						<li>昨日收益<span>${yesterdayInterest }</span></li>
					</ol>
					<ol>
						<c:choose>
							<c:when test="${empty experienceGoldUser}"></c:when>
							<c:otherwise>
								<c:if test="${not empty isOverTime }">
									<li>体验金</li>
									<li><h1>
										<c:if test="${ experienceGoldInt==10}">¥8888</c:if>
						     			<c:if test="${ experienceGoldInt==1 }"><p>¥1000</p></c:if>
						     		</h1></li>
						   			<li>
						   		 		<li>体验金收益<span>${experienceGoldUser.experienceGoldInterest }</span>
						   					<c:if test="${experienceGoldUser.experienceGoldStatus==1 }">(已发放)</c:if>
						     				<c:if test="${experienceGoldUser.experienceGoldStatus==0 }">(首投后发放)</c:if>
						   				</li>
								</c:if>
								<c:if test="${ empty isOverTime }">
									<li>体验金</li>
									<li><h1>
										<c:if test="${ experienceGoldInt==10}">¥8888</c:if>
										<c:if test="${ experienceGoldInt==1 }">¥1000</c:if></h1>
									</li>
									<li>
									   <li>体验金收益<span>${experienceGoldUser.experienceGoldInterest }</span>(生息中)</li>
									</li>
								</c:if>
							</c:otherwise>
						</c:choose>
					</ol>
				</div>
				<!---饼状图star---->
				<div class="J-macR-biao clearfix">
					<div class="J-macRb-wen">
						<ol>
							<li>待收本息<span id="total"></span><em id="totalH"></em> 元</li>
							<li><a href="${path }/invest/inBid.html" target="_blank">查看投资中项目</a></li>
							<li class="J-macRbw-tittwo">待还本息<span id="repay"></span><em id="repayH"></em> 元</li>
						</ol>
						<ul>
							<li style="margin-top:180px;">冻结金额<span id="free"></span><em id="freeH"></em> 元</li>
						</ul>
					</div>
					<div style="width:400px; height:300px; position:relative; margin:0 auto; z-index:200;">
						<div id="container" style="min-width:400px; height:280px; max-width: 500px; margin: 0 auto"></div>
						<div style="position:relative; top:-177px; text-align:center; ">
							<p style="font-size:28px; font-weight:bold;color:#666;" >${userAccount.allMoney}</p>
							<p style="font-size:20px; color:#666;">总资产</p>
						</div>
					</div>
				</div>
				<!---饼状图end--->
				<div class="J-macR-list">
					<ul class="J-macRl-tit clearfix jmb15">
						<li>资金记录</li>
					 <!--  <li style="float:right;"><a href="#" target="_blank">更多&gt;</a></li> -->
					</ul>
					<div class="J-macRl-con accountIndex">
						<dl>
							 <dd>交易时间</dd>
							 <dd>交易类型</dd>
							 <dd>交易详情</dd>
							 <dd>金额</dd>
							 <dd>余额</dd>
						</dl>
						<div id="investRecordsTable" align="center"></div>
					</div>
				</div>
				<!--list end--->
				<!-- <div class="J-macR-banner"><img src="${frontPath }/images/yq_banner.png"/></div> -->
			</div>
		</div>
	</div>
	<!---J-xzg-myaccount end--->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
$(function() {
	var totalAmount ='${totalH}';
	var total = totalAmount.subString(0,totalAmount.indexOf('.'));
	var totalH = totalAmount.subString(totalAmount.indexOf('.'),totalAmount.length);
	if(totalAmount == '0'){
		$("#total").html(0);
		 $("#totalH").html(00); 
	}else{
		$("#total").html(total);
		 $("#totalH").html(totalH); 
	}
	
	var repayAmount = '${repayH}';
	var repayInteest = '${userAccount.waitRepayInterest}';
	var repay = repayAmount.subString(0,repayAmount.indexOf('.'));
	var repayH = repayAmount.subString(repayAmount.indexOf('.'),repayAmount.length);
	if(repayAmount == '0'){
		$("#repay").html(0);
		$("#repayH").html(00); 
	}else{
		$("#repay").html(repay);
		$("#repayH").html(repayH); 
	}
	
	var freeAmount = '${userAccount.unavailableMoney }';
	var free = freeAmount.subString(0,freeAmount.indexOf('.'));
	var freeH = freeAmount.subString(freeAmount.indexOf('.'),freeAmount.length);
	if(freeAmount == '0'){
		$("#free").html(0);
		$("#freeH").html(00);
	}else{
		$("#free").html(free);
		$("#freeH").html(freeH);
	}
		var data = {
			"tojsp" : 1
		};
		
		getPageTable(data, "${path }/account/transcationRecordPage.do",
				generateInvestRecordsTable, "investRecordsTable", 8);
	});
	
	//遍历类型
	var tradeCode = {};
	<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}">
	tradeCode["${item.key}"] = "${item.value}";
	</c:forEach>

	//拼成表格
	function generateInvestRecordsTable(data) {
		var tradeCodeUser = '';
		var dealMoeny = '';
		var tradeCodes = '';
		if(data==null){
			var htmlStr = '暂无数据';
		}
		
		if (data.tradeCodeUser != null) {
			tradeCodeUser = data.tradeCodeUser;
		}
		if (data.dealMoney != null && data.dealMoney != '') {
			dealMoeny = (data.unavailableMoney > 0 ? '-':'+')+data.dealMoney;
		}
		if (data.tradeCode != null && data.tradeCode != '') {
			tradeCodes = tradeCode[data.tradeCode];
		}
		htmlStr= "<ul><li>"+data.alLogAddTime+" </li><li>"+tradeCodes+"</li><li>"+tradeCodeUser+"</li><li>"+dealMoeny+"</li><li>"+data.availableMoney +"</li></ul>";
		return htmlStr;
	};
	$(function(){
		$.get("${path }/account/accountIndexToJson.do",function (data, textStatus){getJson(data);}, "json");
	});
	
	function getJson(data){
		$("#allMoney").html("￥"+addCommas(data.userAccount.allMoney.toFixed(2)));
    	$("#allMoney2").html("￥"+addCommas(data.userAccount.allMoney.toFixed(2)));
    	$("#availableMoney").html("￥"+addCommas(data.userAccount.availableMoney.toFixed(2)));
    	$("#unavailableMoney").html("￥"+addCommas(data.userAccount.unavailableMoney.toFixed(2)));
    	$("#userAllMoney").html("￥"+addCommas(data.userAccount.allMoney.toFixed(2)));
    	$("#userAvailableMoney").html("￥"+addCommas(data.userAccount.availableMoney.toFixed(2)));
    	$("#userunAvailableMoney").html("￥"+addCommas(data.userAccount.unavailableMoney.toFixed(2)));
    	$("#reward").html("￥"+addCommas(data.userAccount.getReward.toFixed(2)));
    	$("#interest").html("￥"+addCommas(data.userAccount.getInterest.toFixed(2)));
    	var allWait = data.userAccount.waitRepossessedCapital+data.userAccount.waitRepossessedInterest;
    	$("#waitAllMoney").html("￥"+addCommas(allWait.toFixed(2)));
    	$("#waitCapital").html("￥"+addCommas(data.userAccount.waitRepossessedCapital.toFixed(2)));
    	$("#waitInterest").html("￥"+addCommas(data.userAccount.waitRepossessedInterest.toFixed(2)));
    	$("#waitMoney").html("￥"+addCommas((data.borrowRepossessed.repossessedCapital+data.borrowRepossessed.repossessedInterest).toFixed(2)));
    	var time = "";
    	if(data.borrowRepossessed==undefined){
    		time = "--";
    	}else{
    		time = data.borrowRepossessed.prepareDatetime;
    	}
    	$("#waitTime").html(time)
    	
    	$("#allWaitRepayMoney").html("￥"+addCommas(data.userAccount.waitRepayCapital.toFixed(2)));
    	$("#waitRepayInterest").html("￥"+addCommas(data.userAccount.waitRepayInterest.toFixed(2)));
    	var repay = "";
    	if(data.repayment==undefined){
    		repay = 0;
    	}else{
    		repay = data.repayment.repaymentPrincipal+data.repayment.repaymentInterest;
    	}
    	
    	$("#repayMoney").html("￥"+addCommas(repay.toFixed(2)));
    	var repayTime = "";
    	if(data.repayment==undefined){
    		repayTime = "--";
    	}else{
    		repayTime = data.repayment.repaymentTime;
    	}
    	
    	$("#waitRepayDate").html(repayTime);
    	
	}
	
	$(function () {
	    $('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: ''
	        },
			
			colors: ['#fda13f', '#52d1ac', '#b6ce34', '#39b5d7', ],
			
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
					innerSize:200,
	                depth: 30,
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            data: [
	                ['',   25],
					['',   25],
					['',   25],
					
	                {
	                    name: '',
	                    y: 25,
	                    sliced: false,
	                    selected: false
	                },
	               
	            ]
	        }]
	    });
	});
/*交易记录  */
</script>
</html>