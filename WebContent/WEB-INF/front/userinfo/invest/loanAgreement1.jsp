<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }借款协议</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
<div class="gywm-bao">
<div class="gywm-nrbao">
  <jsp:include page="/account/userTop.do"></jsp:include>
  <div class="wdzh-nrb">
    <jsp:include page="/account/userLeft.do"></jsp:include>
    <div class="wdzh-right-k">
    <div class="gywm-right-bt"><span style=" border-right:5px solid #ff4922; display:block; width:5px; height:24px; float:left; margin-right:10px;"></span><span style="line-height:24px; font-size:18px; color:#ff4922;">借款协议</span></div>
    <div style="border:0;" class="wdzh-sx">
     	<div class="invest-tabs">
            <ul id="investTabs">
                <a href="javascript:tabChange(1);"><li class="jTab"> 定存宝<!-- <span id="investPlanRecordCount" class="r5">0</span>  --></li></a>
                <a href="javascript:tabChange(2);"><li class="jTab current"> 月息通<!-- <span id="contractCount" class="r5">0</span> --> </li></a>
            </ul>
        </div>
        <div class="wdzh-sx" id="investPanel">
            <div id="contractTable" class="jPanel" style="display: block;">
            <div id="tradeTable" class="trade-table">
      		<table cellspacing="0" cellpadding="0" class="user-table">
            	<thead>
            		<tr>
            			<th>名称</th>
            			<th>借款人 </th>
            			<th>金额(￥)</th>
            			<th>购买次数</th>
            			<th>担保</th>
            			<th>项目协议</th>
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
</div>
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	$(function(){
		userTopHover(9);
		 var data={"tojsp":1};
		 var bidKindNo = $("#bidKindNo").val();
		 getPageTable(data,"${path }/invest/loanAgreementPage.do?bidKindNo="+bidKindNo,generateInvestRecordsTable,"investRecordsTable",6);
	})
	//拼成表格
	function generateInvestRecordsTable(data){
		var xz="";
		if(data.agreementPath!=null){
			xz="下载";
		}
		 var htmlStr = '<tr>';
	     htmlStr+="<td>"+data.borrowTitle+"</td>"+
	     "<td>"+data.userAccount+"</td>"+
	     "<td>"+data.sumEff+"</td>"+
	     "<td>"+data.coun+"</td>"+
	     "<td></td>"+
	     "<td><a href=${path}"+data.agreementPath+">"+xz+"</a></td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	
	
	
	function tabChange(obj){
		if(obj==1){
			location.href="${path}/invest/loanAgreement.do?bidKind=6";
		/* 	$("#planTab").addClass("current");
			$("#loanTab").removeClass("current");
			$("#investPlanList").show();
			$("#investLoanTable").hide(); */
			
		}else if(obj==2){
			location.href="${path}/invest/showLoanAgreement.do?bidKindNo=6";
			/* $("#loanTab").addClass("current");
			$("#planTab").removeClass("current");
			$("#investLoanTable").show();
			$("#investPlanList").hide(); */
		}
	}
	
	
</script>
</html>