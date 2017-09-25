<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../../taglibs.jsp"%>
	<title>${showTitle }-投资管理</title>
	<style type="text/css">
		#l1{
			width:14%;
		}
		#l2{
			width:16%;
		}
		#l3{
			width:14%;
		}
		#l4{
			width:16%;
		}
		#l5{
			width:14%;
		}
		#l6{
			width:13%;
		}
		#l7{
			width:13%;
		}
	</style>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
			<div class="J-ma-nav jboxsize">
			 	<!--左侧导航栏  -->
			    <input type="hidden" value="left2" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
			</div>
 
			<!---投资管理 star--->
			<div class="J-ma-invest jboxsize">
				<ul class="J-mainv-top">
					<li style="width:28%;">
						累计投资(元)
						<p>${effAmount }</p>
					</li>
					<li style="width:28%;">
						投资中冻结金额(元)
						<p>${unavailableMoney}</p>
					</li>
					<li>
						投标中(笔)
						<p>${tendeing }</p>
					</li>
					<li>
						还款中(笔)
						<p>${backing }</p>
					</li>
					<li style="border-right:none;">
						转让中(笔)
						<p>${transfing }</p>
					</li>
				</ul>
				<div class="J-mainv-list">
					<div>
						<dl class="clearfix J-mainv-click">
							<dd class="J-mainvlist-current" onclick="tabChange(1);">投资中</dd>
							<dd onclick="tabChange(2);">还款中</dd>
							<dd onclick="tabChange(3);">已完成</dd>
							<dd onclick="tabChange(4);">转让中</dd>
						</dl>
					</div>
					<div class="J-mainvl-con">
						<div class="J-mainvl-wen">
							<dl>
								<dd>项目名称</dd>
								<dd>借款利率</dd>
								<dd>投资金额</dd>
								<dd>锁定期限</dd>
								<dd>投资日期</dd>
								<dd>利息处理方式</dd>
								<dd>状态</dd>
							</dl>
							<div id="inBidTable" align="center"></div>
						</div>
						<div class="J-mainvl-wen" style="display:none;">
							<dl>
								<dd style="width: 14%">项目名称</dd>
								<dd style="width: 16%">应收日期</dd>  
								<dd style="width: 15%">借款者</dd>
								<dd style="width: 16%">第几期/总期数</dd>
								<dd style="width: 13%">应收总额</dd>
								<dd style="width: 13%">应收利息</dd>
								<dd style="width: 13%">状态</dd>
							</dl>
							<div id="investRecordsTable" align="center"></div>
						</div>
						<div class="J-mainvl-wen" style="display:none;">
							<dl>
								<dd style="width: 10%">项目名称</dd>
								<dd style="width: 13%">实收日期</dd>  
								<dd style="width: 12%">借款者</dd>
								<dd style="width: 13%">第几期/总期数</dd>
								<dd style="width: 10%">应收总额</dd>
								<dd style="width: 10%">应收利息</dd>
								<dd style="width: 11%">逾期金额</dd>
								<dd style="width: 11%">逾期天数</dd>
								<dd style="width: 10%">状态</dd>
							</dl>
							<div id="investRecordedTable" align="center"></div>
						</div>
						<div class="J-mainvl-wen" style="display:none;">
							<dl>
								<dd>标题</dd>
								<dd>年化利率</dd>
								<dd>总期数</dd>
								<dd>待收本金(元)</dd>
								<dd>折让率(%)</dd>
								<dd>待收金额(元)</dd>
								<dd>状态</dd>
							</dl>
							<div id="table2" align="center"></div>
						</div>
					</div>
				</div>
			</div>
			<!---投资管理 end--->
		</div>
	</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
//正在投资的项目
	$(function() {
		var data = {};
		/* data.borrowStatus=2; */
		getPageTable(data, "${path }/invest/inBidPage.do",
				generateInBidTableTable, "inBidTable");
	});

	//正在投资的项目拼成表格
	function generateInBidTableTable(data) {
		var jindu = '';
		var status='';
		if(data.repaymentStyle==1){
			status = '一次性还款';
		}else if(data.repaymentStyle==2){
			status='按月分期还款';
		}else if(data.repaymentStyle==3){
			status = '每月还息到期还款';
		}
		if (data.tenderSum != null) {	
			jindu = data.tenderSum / data.borrowSum * 100;
		}
		var borrowLimitVal = ''
		if(data.isDay == 1){
			borrowLimitVal = data.borrowTimeLimit + "天";
		}else{
			borrowLimitVal = data.borrowTimeLimit + "个月";
		}
		
		 htmlStr = "<ul><li style=width:16% ><a href=${path}/invests/"+data.ebId+">" + data.borrowTitle + "</a></li><li>"+data.annualInterestRate+"%</li><li style=width:16% >"+data.tenderAmount+"</li><li style=width:13% >"+borrowLimitVal+"</li><li style=width:17% >"+toDate(data.tenderAddtime, 'yyyy-MM-dd')+"</li><li >"+status+"</li><li style=width:15.5% ><span>审核中</span></li></ul>";
		
		return htmlStr;
	};
	//可赎回拼成表格
	function generateInvestRecordsTable1(data){
		var htmlStr = '<ul>';
		htmlStr += "<li id=l1> <a href=${path}/invests/"+data.ebId+">" + data.borrowTitle + "</a></li>" 
				+ "<li id=l2>"+ toDate(data.prepareDatetime, 'yyyy-MM-dd') + "</li>" 
				+ "<li id=l3>"+ data.userAccount + "</li>" 
				+ "<li id=l4>" + data.currentPeriod+ "/" + data.totalPeriod + "</li>" 
				+ "<li id=l5>"+ data.prepareAmount + "</li>" 
				 + "<li id=l6>"+ data.repossessedInterest + "</li>" 
				/* + "<li >"+ data.lateInterest + "</li>" 
				+ "<li >" + data.lateDays+ "</li>" */ 
				+ "<li id=l7>" + "还款中" + "</li>"
		htmlStr += "</ul>";
	    return htmlStr;
	};
	//已赎回拼成表格
	function generateInvestRecordsTable2(data){
		var htmlStr = '<ul>';
		htmlStr += "<li > <a href=${path}/invests/"+data.ebId+">" + data.borrowTitle + "</a></li>" 
		+ "<li >"+ toDate(data.paidDatetime, 'yyyy-MM-dd') + "</li>" 
		+ "<li >"+ data.userAccount + "</li>" 
		+ "<li >" + data.currentPeriod+ "/" + data.totalPeriod + "</li>" 
		+ "<li >"+ data.prepareAmount + "</li>" 
		+ "<li >"+ data.repossessedInterest + "</li>" 
		+ "<li >"+ data.lateInterest + "</li>" 
		+ "<li >" + data.lateDays+ "</li>" 
		+ "<li >" + "已回款" + "</li>"
		htmlStr += "</ul>";
	    return htmlStr;
	};

	function tabChange(obj){
		if(obj==1){
			$(function() {
				var data = {};
				/* data.borrowStatus=2; */
				getPageTable(data, "${path }/invest/inBidPage.do", generateInBidTableTable, "inBidTable");
			});
		}else if(obj==2){
			$(function(){
				 var data={"tojsp":1,"repaymentStatus":1};
				 getPageTable(data,"${path }/invest/getRepByStatusPage.do?repossessedStatus=" + 1,generateInvestRecordsTable1,"investRecordsTable");
			});
		}else if(obj==3){
			$(function(){
				var data = {"repaymentStatus":2};
				getPageTable(data,"${path }/invest/getRepByStatusPage.do?repossessedStatus="+2,generateInvestRecordsTable2,"investRecordedTable");
			});
		}else if(obj==4){
			$(function(){
				var data = {"numPerPage" : 20};
				getPageTable(data, "${path }/invest/queryInvestRedeem.do", generateInvestTableztq, "table2");
			});
		}
	}
	
	function generateInvestTableztq(data) {
		var htmlStr = '<ul>';
		/* var str = ''; */
		var showStatus = transferStatus[data.transferStatus];
		/* if (data.tenderStatus == 3 || data.tenderStatus == 5) {
			if(data.transferStatus == 1){
				str='正在转让';
			}else{
				str = "<a class=\"zrtn \"  href=\"javascript:void(0)\" onclick='javascript:transferJsp("
					+ data.tenderId + ")' >转让</a></td>";
			}
		}else if(data.tenderStatus == 7){
			str='已转让';
			
		}else {
			str = "--";
		} */

		if (showStatus == undefined) {
			showStatus = tenderStatus[data.tenderStatus];
		} else {
			/* if (data.transferStatus == 3) {
				str = "<a class=\"zrtn \"  href=\"javascript:void(0)\" onclick='javascript:transferJsp("
						+ data.tenderId + ")' >转让</a></td>";
			} else {
				str = "--";
			} */
		}
		var rate;
		if (data.tranferInterestRate > 0 && data.annualInterestRate) {
			rate = data.tranferInterestRate - data.annualInterestRate;
			rate = rate.toFixed(2);
		} else {
			rate = 0;
		}

		var money;
		if (data.lastAuctionMoney) {
			money = data.lastAuctionMoney;
		} else {
			money = data.transferMoney;
		}
		if (money == undefined) {
			money = "0";
		}
		htmlStr += '<li style=" width:18%;"><a href=${path}/invests/'+data.ebId+'>'+ data.borrowTitle + '</a></li><li>'+data.annualInterestRate+'</li><li>'+data.borrowTimeLimit+'</li><li>'+data.stayingAmount+'</li><li>'+rate+'</li><li>'+ money +'</li><li>'+showStatus+'</li>';
		htmlStr += "</ul>";
		return htmlStr;
	}
</script>
</html>