<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../../taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/auto.css" />
	<title>${showTitle }-债权转让</title>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
  			<div class="J-ma-nav jboxsize">
  				<input type="hidden" value="left3" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
			</div>
			<!---债权转让 star--->
			<div class="J-ma-invest jboxsize J-ma-zqzr">
				<div class="J-mainv-list J-ma-zqzr-list">
					<dl class="clearfix J-mainv-click">
						<dd class="J-mainvlist-current">债权转让</dd>
					</dl>
					<div class="J-mainvl-con">
						<div class="J-ma-zqzrl-wen">
							<dl>
								<dd>标题</dd>
								<dd>年化利率</dd>
								<dd>总期数</dd>
								<dd>待收本金(元)</dd>
								<dd>折让率(%)</dd>
								<dd>待收金额(元)</dd>
								<dd>状态</dd>
								<dd>操作</dd>
							</dl>
							<div id="table2" align="center"></div>
						</div>
					</div>
				</div>
			</div>
			<!---债权转让 end--->
		</div>
	</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		var data = {
				"numPerPage" : 20
			};
			getPageTable(data, "${path }/invest/queryInvestRedeem.do", generateInvestTableztq, "table2");
	});
	function statusChange(obj, status) {
		$("#" + obj).addClass("filter-active");
		$("#" + status).show();
	}
	function tabChange(obj) {
		if (obj == 1) {
			window.location.href = "${path}/invest/investRedeem.do";
		} else if (obj == 2) {
			window.location.href = "${path}/invest/transferRedeem.html";
		}
	}
	//根据状态查询 直投区
	function setLoanInvestorStatus(obj) {
		$("#filter-epsztq").hide();
		$("#filterztq").removeClass("filter-active");
		initDate(obj);
	}

	//遍历直投区类型
	var transferStatus = {};
	<c:forEach var="item" items="${BORROW_TRANSFER_STATUS}">
		transferStatus["${item.key}"] = "${item.value}";
	</c:forEach>

	var tenderStatus = {};
	<c:forEach var="item" items="${TENDER_ALL_STATUS}">
		tenderStatus["${item.key}"] = "${item.value}";
	</c:forEach>
	function generateInvestTableztq(data) {
		var htmlStr = '<tr>';
		var str = '';
		var showStatus = transferStatus[data.transferStatus];
		if (data.tenderStatus == 3 || data.tenderStatus == 5) {
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
		}

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
		htmlStr = '<ul><li style=" width:18%;"><a href=${path}/invests/'+data.ebId+'>'+ data.borrowTitle + '</a></li><li>'+data.annualInterestRate+'</li><li>'+data.borrowTimeLimit+'</li><li>'+data.stayingAmount+'</li><li>'+rate+'</li><li>'+ money +'</li><li>'+showStatus+'</li><li><span>'+str+'</span></li></ul>';
		return htmlStr;
	}

	var tab = null;

	function reflashTable() {
		$.alerts._hide();
		initDate();
	}

	function transferJsp(obj) {
		window.open('', '_self');
		tab = jIframe('${path}/invest/redeemTransfer/' + obj + '.do', '转让', '460', '200');
		return false;
	};
</script>
</html>