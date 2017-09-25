<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-资金记录</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${frontPath }/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${frontPath }/css/jquery-ui.css" />

<script src="${frontPath }/timepicker/WdatePicker.js" type="text/javascript"></script>
<script src="${frontPath }/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="${frontPath }/js/jquery.multiselect.js" type="text/javascript"></script>
<!-- <style type="text/css">
.page {
	border-top: 1px solid #e9e9e9;
}
</style> -->
<script type="text/javascript">
	$(function(){
	  $("#tradeCode").multiselect({
	    header: false,
	    height: 200,
	    minWidth: 200,
		selectedList: 10,//预设值最多显示10被选中项
	    hide: ["explode", 500],
	    noneSelectedText: '请选择类型',
	    close: function(){
	      var values= $("#tradeCode").val();
		  $("#hfexample").val(values);
		}
 	  });
	});
</script>
</head>

<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
<input type="hidden" value="left5" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>

  </div>
  
   <!---资金记录 star--->
   <div class="J-ma-invest jboxsize J-ma-zqzr">
    <div class="J-mainv-list J-ma-zqzr-list">
     <dl class="clearfix J-mainv-click">
      <dd class="J-mainvlist-current">资金记录</dd>
     </dl>
     <div class="J-mainvl-con">
        <ul class="J-ma-zjjl-top clearfix">
         <li>
          <a class="time start_time"><input class="val_txt" id="beginTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></a>
          <a class="time start_time"><input class="val_txt" id="endTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></a>
         </li>
         <li class="J-ma-zjjl-choice">
          <div style="width: 32px;float: left;margin-top: 6px;">类型</div>
          <select id="tradeCode" multiple="multiple">
					<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}">
						<option value="${item.key}"
							<c:if test="${item.key==searchParams.tradeCode }">selected="selected"</c:if>>${item.value}</option>
					</c:forEach>
				</select>
				<input type="hidden" id="hfexample" name="tradeCode"/>
         </li>
         <li class="J-ma-btn J-ma-sjjl-btn"><a onclick="subForm();" target="_blank">查询</a></li>
        </ul>
        <div class="J-macRl-con J-ma-zjjl-con">
           <dl>
            <dd>交易时间</dd>
            <dd>交易类型</dd>
<!--             <dd>交易详情</dd> -->
            <dd>金额</dd>
            <dd>余额</dd>
           </dl>
           <div id="investRecordsTable" align="center">
           <ul>
           </ul>
           </div>
        </div>
     </div>
    </div>
   </div>
   <!---自动投标 end--->
  
 </div>
</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>

<script type="text/javascript">
	$(function() {
		var data = {
			"tojsp" : 1
		};
		getPageTable(data, "${path }/account/transcationRecordPage.do",
				generateInvestRecordsTable, "investRecordsTable", 8);
	})

	function subForm() {
		var tradeCode = $("#hfexample").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var data = {
			"tradeCode" : tradeCode,
			"beginTime" : beginTime,
			"endTime" : endTime
		};
		getPageTable(data, "${path }/account/transcationRecordPage.do",
				generateInvestRecordsTable, "investRecordsTable", 8);
	}

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
		if (data.tradeCodeUser != null) {
			tradeCodeUser = data.tradeCodeUser;
		}
		if (data.dealMoney != null && data.dealMoney != '') {
			dealMoeny = data.dealMoney;
		}
		if (data.tradeCode != null && data.tradeCode != '') {
			tradeCodes = tradeCode[data.tradeCode];
		}
		htmlStr = "<ul><li>"+data.alLogAddTime+" </li><li>"+tradeCodes+"</li><li>"+dealMoeny+"</li><li>"+data.availableMoney +"</li></ul>";
		return htmlStr;
	};
</script>
</html>