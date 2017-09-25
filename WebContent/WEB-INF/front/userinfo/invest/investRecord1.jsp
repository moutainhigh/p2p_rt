<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }投资记录</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
<div class="gywm-bao">
<div class="gywm-nrbao">
  <jsp:include page="/account/userTop.do"></jsp:include>
  <div class="wdzh-nrb">
    <jsp:include page="/account/userLeft.do"></jsp:include>
    <div class="wdzh-right-k">
    <div class="gywm-right-bt"><span style=" border-right:5px solid #ff4922; display:block; width:5px; height:24px; float:left; margin-right:10px;"></span><span style="line-height:24px; font-size:18px; color:#ff4922;">投资记录</span></div>
    <div style="border:0;" class="wdzh-sx">
     	<div class="invest-tabs">
          <ul id="investTabs">
            <li id="planTab" class="jTab"><a href="javascript:tabChange(1);">定存宝</a></li>
            <li id="loanTab" class="jTab current"><a href="javascript:tabChange(2);"> 月息通</a></li>
          </ul>
        </div>
        <input type="hidden" name="bidKindNo" id="bidKindNo" value="${bidKindNo }"/>
    <div class="wdzh-sx" id="investPanel">
      <div id="investLoanTable" style="position: relative; display: block;" class="jPanel">
        <div id="tradeTable" class="trade-table">
      <table cellspacing="0" cellpadding="0" class="user-table">
            	<thead>
            		<tr>
            			<th>名称</th>
            			<th>年化收益率 </th>
            			<th>金额(￥)</th>
            			<th>购买次数</th>
            			<th></th>
            		</tr>
            	</thead>
            	<tbody id="investRecordsTable"></tbody>
            </table>
      </div>
    </div>
       
	</div>
    </div>
  </div>
</div>
</div>
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	$(function(){
		userTopHover(7);
		 var data={"tojsp":1};
		 var bidKindNo = $("#bidKindNo").val();
		 getPageTable(data,"${path }/invest/investRecordPage.do?bidKindNo="+bidKindNo,generateInvestRecordsTable,"investRecordsTable",5);
	})
	//拼成表格
	function generateInvestRecordsTable(data){
		 var htmlStr = '<tr>';
	     htmlStr+="<td>"+data.borrowTitle+"</td>"+
	     "<td>"+data.annualInterestRate+"</td>"+
	     "<td>"+data.sumEff+"</td>"+
	     "<td>"+data.coun+"</td>"+
	     "<td></td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	
	
	
	function tabChange(obj){
		if(obj==1){
			location.href="${path}/invest/investRecord.do?bidKind=6";
		/* 	$("#planTab").addClass("current");
			$("#loanTab").removeClass("current");
			$("#investPlanList").show();
			$("#investLoanTable").hide(); */
			
		}else if(obj==2){
			location.href="${path}/invest/showInvestPlanList.do?bidKindNo=6";
			/* $("#loanTab").addClass("current");
			$("#planTab").removeClass("current");
			$("#investLoanTable").show();
			$("#investPlanList").hide(); */
		}
	}
	
	
</script>
</html>