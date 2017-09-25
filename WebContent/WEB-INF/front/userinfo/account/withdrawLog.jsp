<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-提现记录</title>
<script src="${frontPath }/timepicker/WdatePicker.js" type="text/javascript"></script>
</head>

<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_1_left18" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title5"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
   	<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
   	<table class="search">
      <tbody><tr>
          <td>时间：
			<input type="text" id="beginTime" name="beginTime"
				class="inputBorder Wdate"
				onClick="WdatePicker({isShowClear:false,readOnly:true})" />
		  </td>
          <td>&nbsp;到&nbsp;</td>
          <td>
          <input type="text" id="endTime" name="endTime" class="inputBorder Wdate" onClick="WdatePicker({isShowClear:false,readOnly:true})"/>
          </td>
          <td>&nbsp;<input type="submit" onclick="javaScript:subForm();" class="searchbtn" value="搜索"></td>
      </tr>
    </tbody></table>
	<div class="siteInfoWidth ht1"></div>
	<table class="siteInfoWidth1 tableDate">
            <thead>
            	<tr class="tdwait">
					<td class="tdwait1">提现银行</td>
					<td>提现账号</td>
	                <td class="tdwait1">提现金额(￥)</td>
					<td>到账金额</td>
					<td class="tdwait1">手续费</td>
	                <td >&nbsp;时间</td>
	                <td class="tdwait1">交易类型</td>
	                <td>状态</td>
	                <td class="tdwait1">操作</td>

	                
         		</tr>
        </thead>
        
        <tbody id="investRecordsTable"></tbody>
        </table> 
	</div>
</div>
    
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>

<script type="text/javascript">
	$(function() {
		 var data={"cxType":2};
		 getPageTable(data,"${path }/account/cashValuePage.do",generateInvestRecordsTable,"investRecordsTable",9);
	})

	function subForm(){
		var cxType=$("#cxType").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		 var data={"cxType":2,
				 "beginTime":beginTime,
				 "endTime":endTime
		 };
		 getPageTable(data,"${path }/account/cashValuePage.do",generateInvestRecordsTable,"investRecordsTable",9);
	}

	//遍历类型
	var tradeCode = {};
	<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}"> 
		tradeCode["${item.key}"] = "${item.value}";
	</c:forEach>

	//拼成表格
	function generateInvestRecordsTable(data){
	
	
		var status='';
		var sub='--';
		if(data.leixing == "充值" && data.cashStatus==1){
			status="申请中";
		}
		if(data.leixing == "充值" && data.cashStatus==2){
			status="通过";
		}
		if(data.leixing == "充值" && data.cashStatus==3){
			status="未通过";
		}
		if(data.leixing == "提现" && data.cashStatus==0){
			status="申请中";
		}
		if(data.leixing == "提现" && data.cashStatus==1){
			status="提现成功";
		}
		if(data.leixing == "提现" && data.cashStatus==2){
			status="未通过";
		}
		if(data.leixing == "提现" && data.cashStatus==3){
			status="已撤销";
		}
		if(data.leixing == "提现" && data.cashStatus==0){
			sub="<a href=\"qxCashValue.do?cid="+data.sid+"&&toMoney="+data.cashTotal+"\" class=\"toback r3\">撤销</a>";
		}
		
		var htmlStr = '<tr">';
	     htmlStr+=
    	 "<td>"+data.cashBank+"</td>"+
	     "<td>"+data.cashBankAccount+"</td>"+
	     "<td>"+data.cashTotal+'元'+"</td>"+
	     "<td>"+data.cashAccount+'元'+"</td>"+
	     "<td>"+data.cashFee+'元'+"</td>"+
	     "<td>"+data.cashAddtime+"</td>"+
	     "<td>"+data.leixing+"</td>"+
	     "<td>"+status+"</td>"+
	     "<td>"+sub+"</td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
</script>
</html>