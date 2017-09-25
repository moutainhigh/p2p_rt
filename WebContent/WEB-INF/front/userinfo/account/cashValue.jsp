<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }充值提现</title>
<%-- <link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
 --%>
 <link rel="stylesheet" type="text/css" href="${frontPath }/css/ny.css" />
<script src="${cssJsPath }/timepicker/WdatePicker.js" type="text/javascript"></script>

<body>
<jsp:include page="/top.do"></jsp:include>
<div class="gywm-bao">
<div class="gywm-nrbao">
  <jsp:include page="/account/userTop.do"></jsp:include>
  <div class="wdzh-nrb">
    <jsp:include page="/account/userLeft.do"></jsp:include>
    <div class="wdzh-right-k">
    <div class="gywm-right-bt"><span style=" border-right:5px solid #ff4922; display:block; width:5px; height:24px; float:left; margin-right:10px;"></span><span style="line-height:24px; font-size:18px; color:#ff4922;">充值提现</span></div>
    <div style="height:75px;" class="wdzh-zck">
      <div style="border-right:none; border-right:1px solid #becad4;" class="zhzl-left"> <span style="font-size:20px; color:#444;">账户余额</span><span>可用余额 <img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></span></div>
      <div style="border-right:none; margin-left:0;" class="zhzl-left"> <span style="font-size:20px; color:#444;">￥${userAccount.allMoney }</span><span>￥${userAccount.availableMoney }</span></div>
      <a href="${path }/account/recharge.html"><div style=" width:125px;  height:50px; line-height:50px; font-size:18px; float:left; margin:18px 20px 0px 150px; text-align:center; color:#fff; background:#ff4922; border-radius:4px;">充值</div></a>
      <a href="${path }/account/withdraw.html"><div style=" width:125px;  height:50px; line-height:50px; font-size:18px; float:left; margin:18px 20px 0px 20px; text-align:center; color:#fff; background:#4bc600; border-radius:4px;">提现</div></a>
    </div>
    <div style=" margin-top:30px;" class="wdzh-sx">
    	<div class="model-box trade-filter">
            <dl class="filter-items">
                <dt>起止日期</dt>
                <dd>
                    <div class="query-input">
                        <input type="text" id="beginTime" name="beginTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" >
                        <label>-</label>
                        <input type="text" id="endTime" name="endTime" onClick="WdatePicker({isShowClear:false,readOnly:true})">
                    </div>
                   	 交易类型
                    <select name="cxType" id="cxType">
					<option value="">全部</option>
					<option value="1">充值</option>
					<option value="2">提现</option>
				</select> 
				<a href="javaScript:subForm()" class="selected" data="6" style="float: right;cursor: pointer;">查询</a>
                </dd>
            </dl>
        </div>
    </div>
    
    <div style="margin-top:30px;" class="wdzh-sx">
        <div id="tradeTable" class="trade-table">
            <table cellspacing="0" cellpadding="0" class="user-table">
            <thead><tr><th>时间</th><th>交易类型 </th><th>金额(￥)</th><th>状态</th><th>操作</th></tr></thead>
            <tbody id="investRecordsTable"></tbody>
            
            </table>
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
		userTopHover(3);
		 var data={"tojsp":1};
		 getPageTable(data,"${path }/account/cashValuePage.do",generateInvestRecordsTable,"investRecordsTable",5);
	})
	
	function subForm(){
		var cxType=$("#cxType").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		 var data={"cxType":cxType,
				 "beginTime":beginTime,
				 "endTime":endTime
		 };
		 getPageTable(data,"${path }/account/cashValuePage.do",generateInvestRecordsTable,"investRecordsTable",5);
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
			status="通过";
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
	     htmlStr+="<td>"+data.cashAddtime+"</td>"+
	     "<td>"+data.leixing+"</td>"+
	     "<td>"+data.cashTotal+"</td>"+
	     "<td>"+status+"</td>"+
	     "<td>"+sub+"</td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
</script>
</html>
