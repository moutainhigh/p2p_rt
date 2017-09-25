<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<link href="${frontPath }/css/finance.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${frontPath }/js/tab.js"></script> 
<style type="text/css">
.list_text li {
    color: #8a949d;
    float: left;
    height: 40px;
    line-height: 25px;
    margin-bottom: 3px;
    text-align: center;
}
</style>
<title>${showTitle }-实时财务</title>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
<div style="background-color:#f2f2f2;" id="content1"><!--实时财务-->
<div style="height:80px;" id="hkct-sscw">
    <div style="margin-top:8px;" class="xt-cw"></div>
<div style="background-color:#ee2121; height:90px; " class="hkct-k">
  <ul style="padding-top:10px;">
	<li style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">交易总额</p><p class="hkct-sscw-p2" id="totleMoney"></p></li>
    <li  style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">待收总额</p><p class="hkct-sscw-p2" id="allInterestAndRewardStr"></p></li>
    <li  style="line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">今日回款总额</p><p class="hkct-sscw-p2" id="repossessed"></p></li>
    <li style="border:none; line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">注册人数</p><p class="hkct-sscw-p2" id="userNum"></p></li>
  </ul>
    </div>
  </div>
  
</div>
<div class="con">
    <div class="wealthOne" style="margin-top: 0px;">
   	  <h2>截止目前:</h2>
        <ul>
       	  <li>上线运营：<strong id="countDays"></strong></li>
           <!--  <li>当前待收总额：<strong id="waitMoney"></strong></li> -->
            <li>今日满标额：<strong id="todayFullMoney"></strong></li>
            <li>本周满标额：<strong id="weekFullMoney"></strong></li>
            <li>逾期总数：<strong id="newIncreasedCount"></strong></li>
      </ul>
        <ul>
        	<!-- <li>平台总人数：<strong id="allUsers"></strong></li> -->
            <li>已回款总额：<strong id="returnMoney"></strong></li>
            <li>昨日满标额：<strong id="yesterdayFullMoney"></strong></li>
            <li>上周满标额<strong id="forwardWeekFullMoney"></strong></li>
            <li>逾期总额<strong id="newIncreasedAmount"></strong></li>
        </ul>
        <ul>
        	<li>投资人数：<strong id="tenderUsers"></strong></li>
            <li>已创造财富：<strong id="wealth"></strong></li>
            <!-- <li>自动投标设置总额：<strong id="allTenderMoney"></strong></li> -->
            <li>本月满标额：<strong id="monthFullMoney"></strong></li>
            <li>还款总数：<strong id="repayedCount"></strong></li>
        </ul>
        <ul>
        	<li>借款人数：<strong id="borrowUsers"></strong></li>
            <li>提前还款次数：<strong id="forwardPayment"></strong></li>
            <!-- <li>自动投标有效余额：<strong id="allEffAutoMoney"></strong></li> -->
            <li>上月满标额：<strong id="forwardMonthFullMoney"></strong></li>
            <li>还款总额：<strong id="repayedAmount"></strong></li>
        </ul>
        <div class="clear"></div>
  </div>
  <div class="wealthTwo">
    	<div class="title">
        <ul>
    		<li id="one1" onclick="repayed();" class="hover" style="cursor: pointer;">已回款标列表</li>
        	<li id="one2" onclick="twoWeeks();" class="" style="cursor: pointer;">两周内应回款标列表</li>
        	<li id="one3" onclick="twoMonth();" class="" style="cursor: pointer;">两月内待还列表</li>
            <li id="one4" onclick="threeMonth();" class="" style="cursor: pointer;">三月内待还列表</li>
            <li id="one5" onclick="countByMonthFun();" class="" style="cursor: pointer;">迅拓月度数据统计</li>
      	</ul>
       </div>
       <div class="list_text">
       	    <div id="con_one_1" style="display: block;">
            	<ul class="list_text_con" >
                	<li class="wealthlist_title">
                    	<span class="no">借款编号</span>
                        <span class="name">借款人</span>
                        <span class="head">借款标题</span>
                        <span class="period">期数</span>
                        <span class="time">还款时间</span>
                        <span class="money">还款金额</span>
                        <span class="interest">还款利息</span>
                    </li>
                    <div class="clear"></div>
                </ul>
                <div id="repaymentSucTable"></div>
            </div>
            <!-- 两周内应还列表 -->
            <div id="con_one_2" style="display: none;">
            <ul class="list_text_con" >
                	<li class="wealthlist_title">
                    	<span class="no">借款编号</span>
                        <span class="name">借款人</span>
                        <span class="head">借款标题</span>
                        <span class="period">期数</span>
                        <span class="time">应还时间</span>
                        <span class="money">应还金额</span>
                        <span class="interest">应还利息</span>
                    </li>
                    <div class="clear"></div>
           </ul>
           <div id="towWeeksRepaymentsTable"></div>
            </div>
            <div id="con_one_3" style="display: none;">
            <ul class="list_text_con" >
                	<li class="wealthlist_title">
                    	<span class="no">借款编号</span>
                        <span class="name">借款人</span>
                        <span class="head">借款标题</span>
                        <span class="period">期数</span>
                        <span class="time">应还时间</span>
                        <span class="money">应还金额</span>
                        <span class="interest">应还利息</span>
                    </li>
                    <div class="clear"></div>
           </ul>
           <div id="towMonthRepaymentsTable"></div>
            </div>
            <div id="con_one_4" style="display: none;">
            <ul class="list_text_con" >
                	<li class="wealthlist_title">
                    	<span class="no">借款编号</span>
                        <span class="name">借款人</span>
                        <span class="head">借款标题</span>
                        <span class="period">期数</span>
                        <span class="time">应还时间</span>
                        <span class="money">应还金额</span>
                        <span class="interest">应还利息</span>
                    </li>
                    <div class="clear"></div>
           </ul>
           <div id="threeMontyRepaymentsTable"></div>
            </div>
            
            <div id="con_one_5" style="display: none;">
            <ul class="list_text_con" >
                	<li class="wealthlist_title">
                    	<span class="head" style="width:130px;text-align: center;">月份</span>
                        <span class="head" style="width:130px;text-align: center;">发布总量</span>
                        <span class="head" style="width:130px;text-align: center;">发布总额</span>
                        <span class="head" style="width:130px;text-align: center;">投标总数</span>
                        <span class="head" style="width:130px;text-align: center;">投标总额</span>
                        <span class="head" style="width:130px;text-align: center;">还款总数</span>
                        <span class="head" style="width:130px;text-align: center;">还款总额</span>
                    </li>
                    <div class="clear"></div>
           </ul>
           <div id="countByMonthTable"></div>
            </div>
       </div>
       
    </div>
<div class="clear"></div>
    
    
</div>
<div class="clear"></div>
 <!--/bottom-->
<jsp:include page="/foot.do"></jsp:include>
<script type="text/javascript">
$(function(){
	changeTopHover(6);
	$("#one1").addClass("hover");
	getStatistics();
	$.post("${path }/currentTimeFinance/getCurrentTimeFinanceToJson.do",function (data, textStatus){getCountData(data);}, "json");
	repayed();
	
});
function getCountData(data){
	$("#countDays").html(data.countDays+"天");
	/* $("#allUsers").html(data.allUsers+"人"); */
	$("#tenderUsers").html(data.tenderUsers+"人");
	$("#borrowUsers").html(data.borrowUsers+"人");
	/* $("#waitMoney").html("￥"+addCommas(data.waitMoney.toFixed(2))); */
	$("#todayFullMoney").html("￥"+addCommas(data.todayFullMoney.toFixed(2)));
	$("#weekFullMoney").html("￥"+addCommas(data.weekFullMoney.toFixed(2)));
	$("#returnMoney").html("￥"+addCommas(data.returnMoney.toFixed(2)));
	$("#yesterdayFullMoney").html("￥"+addCommas(data.yesterdayFullMoney.toFixed(2)));
	$("#forwardWeekFullMoney").html("￥"+addCommas(data.forwardWeekFullMoney.toFixed(2)));
	$("#newIncreasedAmount").html("￥"+addCommas(data.newIncreasedAmount.toFixed(2)));
	$("#wealth").html("￥"+addCommas(data.wealth.toFixed(2)));
	/* $("#allTenderMoney").html("￥"+addCommas(data.allTenderMoney.toFixed(2))); */
	$("#monthFullMoney").html("￥"+addCommas(data.monthFullMoney.toFixed(2)));
	/* $("#allEffAutoMoney").html("￥"+addCommas(data.allEffAutoMoney.toFixed(2))); */
	$("#forwardMonthFullMoney").html("￥"+addCommas(data.forwardMonthFullMoney.toFixed(2)));
	$("#repayedAmount").html("￥"+addCommas(data.repayedAmount.toFixed(2)));
	$("#newIncreasedCount").html(data.newIncreasedCount+"次");
	$("#repayedCount").html(data.repayedCount+"次");
	$("#forwardPayment").html(data.forwardPayment+"次");
}
/**
 * 统计
 */
function getStatistics() {
	$.get("${path}/getStatistics.do", function(data, status) {
		var totleMoney = data.totleMoney.toFixed(2);
		$("#totleMoney").html("￥"+addCommas(totleMoney));
		var userNum = data.userNum;
		$("#userNum").html(addCommas(userNum));
		var allInterestAndRewardStr=data.allInterestAndRewardStr;
		$("#allInterestAndRewardStr").html("￥"+addCommas(allInterestAndRewardStr));
		var repossessed=data.repossessed;
		$("#repossessed").html("￥"+addCommas(repossessed));
	});
}

function repayed(){
	$("#one1").addClass("hover");
	$("#one2").removeClass("hover");
	$("#one3").removeClass("hover");
	$("#one4").removeClass("hover");
	$("#one5").removeClass("hover");
	$("#con_one_1").show();
	$("#con_one_2").hide();
	$("#con_one_3").hide();
	$("#con_one_4").hide();
	$("#con_one_5").hide();
	var data={};
	getPageFrom(data,"${path }/currentTimeFinance/getRepaymentsPage.do",generaterepaymentSucTable,"repaymentSucTable");
}

//拼成表格已回款
function generaterepaymentSucTable(data){
	 var htmlStr = '<ul class=\"wealthlist_con bg\"><li class=\"moreReplace\">';
	 htmlStr+="<span title="+data.borrowNo+" class=\"no\">"+data.borrowNo+"</span>"+
	 "<span class=\"name\">"+data.userAccount+"</span>"+
	 "<span class=\"head\" title="+data.borrowTitle+">"+data.borrowTitle+"</span>"+
	 "<span class=\"period\">"+data.currentPeriod+"/"+data.totalPeriod+"</span>"+
	 "<span class=\"time\">"+toDate(data.repaymentPaidtime,'yyyy-MM-dd')+"</span>"+
	 "<span class=\"money red1\">"+data.repaymentRealamount+"元</span>"+
	 "<span class=\"interest blue\">"+data.repaymentInterest+"元</span>";
     htmlStr+="</li></ul><div class=\"clear\"></div>";
    return htmlStr;
}


function twoWeeks(){
	$("#one2").addClass("hover");
	$("#one1").removeClass("hover");
	$("#one3").removeClass("hover");
	$("#one4").removeClass("hover");
	$("#one5").removeClass("hover");
	$("#con_one_2").show();
	$("#con_one_1").hide();
	$("#con_one_3").hide();
	$("#con_one_4").hide();
	$("#con_one_5").hide();
	var data={};
	getPageFrom(data,"${path }/currentTimeFinance/getRepayingsPage.do?type="+2,generatetowWeeksRepaymentsTable,"towWeeksRepaymentsTable");
}

//拼成表格
function generatetowWeeksRepaymentsTable(data){
	 var htmlStr = '<ul class=\"wealthlist_con bg\"><li class=\"moreReplace\">';
	 htmlStr+="<span title="+data.borrowNo+" class=\"no\">"+data.borrowNo+"</span>"+
	 "<span class=\"name\">"+data.userAccount+"</span>"+
	 "<span class=\"head\" title="+data.borrowTitle+">"+data.borrowTitle+"</span>"+
	 "<span class=\"period\">"+data.currentPeriod+"/"+data.totalPeriod+"</span>"+
	 "<span class=\"time\">"+toDate(data.repaymentTime,'yyyy-MM-dd')+"</span>"+
	 "<span class=\"money red1\">"+data.repaymentAmount+"元</span>"+
	 "<span class=\"interest blue\">"+data.repaymentInterest+"元</span>";
     htmlStr+="</li></ul><div class=\"clear\"></div>";
    return htmlStr;
}

function twoMonth(){
	$("#one3").addClass("hover");
	$("#one1").removeClass("hover");
	$("#one2").removeClass("hover");
	$("#one4").removeClass("hover");
	$("#one5").removeClass("hover");
	$("#con_one_3").show();
	$("#con_one_1").hide();
	$("#con_one_2").hide();
	$("#con_one_4").hide();
	$("#con_one_5").hide();
	var data={};
	getPageFrom(data,"${path }/currentTimeFinance/getRepayingsPage.do?type="+3,generatetowWeeksRepaymentsTable,"towMonthRepaymentsTable");
}

function threeMonth(){
	$("#one4").addClass("hover");
	$("#one1").removeClass("hover");
	$("#one3").removeClass("hover");
	$("#one2").removeClass("hover");
	$("#one5").removeClass("hover");
	$("#con_one_4").show();
	$("#con_one_1").hide();
	$("#con_one_3").hide();
	$("#con_one_2").hide();
	$("#con_one_5").hide();
	var data={};
	getPageFrom(data,"${path }/currentTimeFinance/getRepayingsPage.do?type="+4,generatetowWeeksRepaymentsTable,"threeMontyRepaymentsTable");
}

function countByMonthFun(){
	$("#one5").addClass("hover");
	$("#one1").removeClass("hover");
	$("#one3").removeClass("hover");
	$("#one4").removeClass("hover");
	$("#one2").removeClass("hover");
	$("#con_one_5").show();
	$("#con_one_1").hide();
	$("#con_one_3").hide();
	$("#con_one_4").hide();
	$("#con_one_2").hide();
	var data={};
	getPageFrom(data,"${path }/currentTimeFinance/getCountByMonth.do",generatecountByMonthTable,"countByMonthTable");
}


//拼成表格
function generatecountByMonthTable(data){
	 var htmlStr = '<ul class=\"wealthlist_con bg\"><li class=\"moreReplace\">';
	 htmlStr+="<span class=\"head\" style=\"width:130px;text-align: center;\">"+data.monthDate+"</span>"+
	 "<span class=\"head blue\" style=\"width:130px;text-align: center;\">"+data.publishCount+"</span>"+
	 "<span class=\"head red1\" style=\"width:130px;text-align: center;\">"+data.publishAmount+"元</span>"+
	 "<span class=\"head blue\" style=\"width:130px;text-align: center;\">"+data.tenderCount+"</span>"+
	 "<span class=\"head red1\" style=\"width:130px;text-align: center;\">"+data.tenderAmount+"元</span>"+
	 "<span class=\"head blue\" style=\"width:130px;text-align: center;\">"+data.repayedCount+"</span>"+
	 "<span class=\"head red1\" style=\"width:130px;text-align: center;\">"+data.repayedAmount+"元</span>";
   htmlStr+="</li></ul><div class=\"clear\"></div>";
  return htmlStr;
}
</script>
</body>
</html>