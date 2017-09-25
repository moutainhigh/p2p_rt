<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/account.css" />
<title>${showTitle }投资赎回</title>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_2_left29" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title9"></c:set>
        <%@include file="titleNav.jsp"%>
			<div class="tab_info">
						<div class="alerts">
							自充值之日起15天后，投资者在特殊情况下急需资金时，可向${CACHE_SYS_CONFIG.sysWebsitename}申请提前赎回投资本金，经双方协商一致同意后，${CACHE_SYS_CONFIG.sysWebsitename}将在7个工作日内垫付投资本金。<br> 
             				即从待收本息中扣除用户该部分投资款的全部应得利息和己得奖励，返还剩余资金。
						</div>

						<div class="siteInfoWidth ht1"></div>

						<table class="siteInfoWidth1 tableDate">
							<thead>
								<tr class="tdwait">
									<td class="tdwait1">标题</td>
									<td>年化收益率</td>
									<td class="tdwait1">投资金额(￥)</td>
									<td>赎回金额(￥)</td>
									<td class="tdwait1">状态</td>
									<td>赎回</td>
								</tr>
							</thead>
							<tbody id="table1"></tbody>
						</table>
						<div class="siteInfoWidth ht1"></div>

					</div>
</div>
    
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
$(function(){
	 var data={"tojsp":1};
	 getPageTable(data,"${path }/invest/selectInvestRedeem.do",generateInvestTabledht,"table1",6);
});

function statusChange(obj,status){
	$("#"+obj).addClass("filter-active");
	$("#"+status).show();
}
function tabChange(obj){
	if(obj==1){
		window.location.href="${path}/invest/investRedeem.do";
	}else if(obj==2){
		window.location.href="${path}/invest/transferRedeem.html";
	}
}
//根据状态查询 定活通
function setFinancePlanStatus(obj){
	$("#dhtStatus").hide();
	$("#dhtfilter").removeClass("filter-active");
	var data={"tojsp":1};
	getPageTable(data,"${path }/invest/selectInvestRedeem.do?redeemStatus="+obj,generateInvestTabledht,"table1");
}
//遍历定活通类型

var tenderStatus = {};
<c:forEach var="item" items="${TENDER_ALL_STATUS}"> 
	tenderStatus["${item.key}"] = "${item.value}";
</c:forEach>
var redeemStatus={};
<c:forEach var="items" items="${BorrowRedeem_ALL_STATUS}"> 
	redeemStatus["${items.key}"] = "${items.value}";
</c:forEach>
function generateInvestTabledht(data){
	 var htmlStr = '<tr>';
	 var str='';
	 var sta='';
	 if(data.redeemStatus==null||data.reddemStatus==''){
	
		 sta=tenderStatus[data.tenderStatus];
	 }else{
		
		 sta=redeemStatus[data.redeemStatus];
	 }
	 
	 if(data.redeemStatus==3||data.tenderStatus==3||data.tenderStatus==5){
		 if(data.redeemStatus==1){
			 str="--";	 
		 }else{
			 str="<a class=\"zrtn \" href=\"javascript:doitSubmit("+data.tenderId+")\" >赎回</a></td>";
		 } 
	 }else{
		 str="--";
	 }
	 
	 var money="";
	 if(data.redeemMoney==undefined){
		 money=0;
	 }else{
		 money=data.redeemMoney;
	 }
	 
     htmlStr+="<td> "+data.borrowTitle+" </td>"+
     "<td>"+data.annualInterestRate+"%"+"</td>"+
     "<td>"+data.effectiveAmount+"</td>"+
     "<td>"+money+"</td>"+
     "<td>"+sta+"</td>"+
     "<td>"
    +str;
    htmlStr+="</tr>";
   return htmlStr;
};
//定活通
function doitSubmit(obj){
		  $.ajax({
			dataType:'json',
			url:"${path}/borrowRedeem/borrowRedeemRedeemAuditing/"+obj+".do",
			type:'post',
			success:function(data){
				if(data.code=='200'){
					alertc(data.msg);
				}
				if(data.code=='300'){
					alertc(data.msg);
				}
				var data={"tojsp":1};
				getPageFrom(data,"${path }/invest/selectInvestRedeem.do",generateInvestTabledht,"table1");
			},
			error:function(data){
				alertc("赎回申请出错");
			}
		});  
}
</script>
</html>