<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>债权转让-浩茗金融</title>
	<meta  name="Keywords" content="投资理财，债权转让">
	<meta name="Description" content="债权转让是浩茗金融（www.hmjr99.com）提供的一款产品资金流动服务，保证投资人因资金缺口需要资金周转时，可根据自身需求对产品，进行自由变更或转让的服务。">
	<%@include file="../taglibs.jsp"%>
    <script type="text/javascript" src="${frontPath }/js/page4TransferList.js"></script>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="content tz_con">
		<div class="content_in">
			<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<a>债权转让</a>
			</div>
			<div class="tap">
				<div class="tap_list">
					<a href="${path }/invest">项目列表</a> 
					<a href="${path }/creditassignment" class="onchose">债权转让</a>
				</div>
				<ul class="Z-liemu">
					<li id="borrowtypeall"><i style="font-size: 16px;">标的种类</i><em class="chose"
						onclick="sort('all','borrowtype')">不限</em><span
						onclick="sort(8,'borrowtype')">新手标</span><span
						onclick="sort(9,'borrowtype')">活动标</span><span
						onclick="sort(10,'borrowtype')">3月标</span><span
						onclick="sort(11,'borrowtype')">6月标</span><span
						onclick="sort(12,'borrowtype')">9月标</span><span
						onclick="sort(13,'borrowtype')">12月标</span></li>
					<li id="rateall"><i style="font-size: 16px;">利率范围</i><em class="chose"
						onclick="sort('all','rate')">不限</em><span
						onclick="sort(12,'rate')">12%以下</span><span
						onclick="sort(1218,'rate')">12%~18%</span><span
						onclick="sort(18,'rate')">18%以上</span></li>
					<li id="dateall"><i style="font-size: 16px;">借款期限</i><em class="chose"
						onclick="sort('all','date')">不限</em><span
						onclick="sort(3090,'date')">1~3个月</span><span
						onclick="sort(90180,'date')">3~6个月</span><span
						onclick="sort(180360,'date')">6~12个月</span></li>
					<li id="securitymodeall"><i style="font-size: 16px;">还款方式</i><em class="chose"
						onclick="sort('all','securitymode')">不限</em><span
						onclick="sort(2,'securitymode')">先息后本</span><span
						onclick="sort(1,'securitymode')" style="width: 85px;">到期还本付息</span></li>
				</ul>
			</div>
			<ul class="tap_con2" id="quzlList">
				<li>
					暂无数据
				</li>
			</ul>
		</div>
	</div>
	<!-- content -->

	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	var BORROW_ALL_STATUS = {};
	<c:forEach var="item" items="${BORROW_ALL_STATUS}">
		BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
	</c:forEach>

	var BORROW_ALL_REPAYMENT_STYLE = {};
	<c:forEach var="item" items="${BORROW_ALL_REPAYMENT_STYLE}">
		BORROW_ALL_REPAYMENT_STYLE["${item.key}"] = "${item.value}";
	</c:forEach>

	//贷款用途
	var BORROW_ALL_BORROW_USE = {};
	<c:forEach var="item" items="${BORROW_ALL_BORROW_USE}">
		BORROW_ALL_BORROW_USE["${item.key}"] = "${item.value}";
	</c:forEach>

	var checkNum = 0;
	var data = {};
	var loanTypeArray = new Array();
	var termArray = new Array();
	var creditLevelArray = new Array();
	var rateArray = new Array();

	function getBorrowTransInfo(data) {
		var htmlStr = '';
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
		}
		var endTime = data.end_time;
		var nowDate = data.nowDate;
		var buttonHtml = '';
		var leftDays = data.leftDays;
		switch (data.transfer_status) {
		case 1:
			if (nowDate.time - endTime.time < 0) {
				buttonHtml += '<a  href="${path}/borrowTransfer/showBorrowTransInfo/'+data.id+'.do" class="base_btn invest_btn" target="_blank">竞拍</a>';
			} else {
				buttonHtml += '<a  href="javascript:;" class="base_btn HkzDksYjs_btn" >已结束</a>';
				leftDays = "0";
			}
			break;
		case 2:
			buttonHtml += '<a  href="javascript:;" class="base_btn HkzDksYjs_btn" >转让成功</a>';
			leftDays = "0";
			break;
		case 3:
			buttonHtml += '<a  href="javascript:;" class="base_btn HkzDksYjs_btn" >转让失败</a>';
			leftDays = "0";
			break;
		}

		var imgHtml = '';
		var styles = '';
		//项目名
		var titlelength = data.borrow_title;
		var title = "";
		if (titlelength.length > 6) {
			title = titlelength.substring(0, 6) + "...";
		} else {
			title = titlelength;
		}
		//金额
		var lastAuctionMoney;
		if (data.last_auction_money == undefined) {
			lastAuctionMoney = data.transfer_money ;
		} else {
			lastAuctionMoney = data.last_auction_money;
		}

		endTime = toDate(endTime, "yyyy-MM-dd hh:mm:ss");
		
		
		htmlStr = '<li><a href="${path}/borrowTransfer/showBorrowTransInfo/'+data.id+'.do" target="_blank"><h4>'
				+ titlelength
				+ '</h4></a><div class="txt"><p class="l_txt"><em>预期年化利率</em><span><i>'
				+ data.annual_interest_rate.toFixed(2)
				+ '</i>%</span></p><p class="c_txt"><em>剩余期限</em><span>'
				+ leftDays + '天</span></p><p><em>转让金额</em><span>'
				+ addCommas(data.transfer_money.toFixed(2)) + '元</span></p>'
				+ buttonHtml + '</div></li>';
		return htmlStr;
	}

	$(function() {
		getPageFromTransfer(data, "${path }/borrow/getBorrowTransList.do", getBorrowTransInfo, "quzlList");
		changeTopHover(2);
	});

	//数组初始化
	var rateArray = new Array();//利率
	var dateArray = new Array();//期限
	var securitymodeArray = new Array();//担保方式
	var borrowtypeArray = new Array();//标种

	//数组初始化
	function initArray(obj) {
		rateArray.length = 0;
		dateArray.length = 0;
		securitymodeArray.length = 0;
		borrowtypeArray.length = 0;
		fenbiaoArray.length = 0;
		rateArray.push("all");
		dateArray.push("all");
		securitymodeArray.push("all");
		borrowtypeArray.push("all");
	}
	//点击赋值
	function sort(obj, type) {
		var value = obj;
		var type = type;
		switch (type) {
		case 'rate':
			rateArray.length = 0;
			rateArray.push(value);
			if(value =="all"){
				$("#rateall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
				$("#rateall span").removeClass('J-chose');
			}else{
				$("#rateall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
			}
			break;
		case 'date':
			dateArray.length = 0;
			dateArray.push(value);
			if(value =="all"){
				$("#dateall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
				$("#dateall span").removeClass('J-chose');
			}else{
				$("#dateall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
			}
			break;
		case 'securitymode':
			securitymodeArray.length = 0;
			securitymodeArray.push(value);
			if(value =="all"){
				$("#securitymodeall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
				$("#securitymodeall span").removeClass('J-chose');
			}else{
				$("#securitymodeall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
			}
			
			break;
		case 'borrowtype':
			borrowtypeArray.length = 0;
			borrowtypeArray.push(value);
			if(value =="all"){
				$("#borrowtypeall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
				$("#borrowtypeall span").removeClass('J-chose');
			}else{
				$("#borrowtypeall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
			}
			break;
		}
		searchByParams();
	}
	
	function searchByParams() {
		var data = {};
		data.rateArrays = rateArray.unique2().toString();
		data.dateArrays = dateArray.unique2().toString();
		data.borrowtypeArrays = borrowtypeArray.unique2().toString();
		data.securitymodeArrays = securitymodeArray.unique2().toString();
	
		getPageFromTransfer(data, "${path }/borrow/getBorrowTransList.do", getBorrowTransInfo, "quzlList");
	}
</script>
</html>
