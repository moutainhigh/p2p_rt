<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }还款明细</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
<script src="${cssJsPath }/timepicker/WdatePicker.js" type="text/javascript"></script>
 <style type="text/css">
 #title{
 	padding-left:0px;
 }
 #title li{
 	width:75px;
 	text-align: center;
 }
 #investRecordsTable ul{
 	padding-left:0px;
 }
 #investRecordsTable ul li{
 	width:75px;
 	text-align: center;
 }
 </style>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>

<div id="wrapper"> 
    <!--sidebar-->
    <jsp:include page="/account/userLeft.do"></jsp:include>
    <!--/sidebar--> 
    <!--main-->
    <div id="main">
              <div class="title-items backtohis"><a href="javascript:history.back();" class="toback r3"><i class="icons arrow-gray-dotl"></i>返回</a>
            <h2>还款明细</h2>
            <!-- <div class="r"><span class="excel-export"><a href="javascript:void(0)" id="export">导出到Excel<i class="icons"></i></a></span> </div> -->
            <b class="line" data-line="一条横线"></b></div>
	        <div class="model-box trade-filter">
	            <dl class="filter-items">
	                <dt>标题：</dt>
	                <dd>
	                    <div class="query-input">
	                        <input type="text" id="borrowTitle" name="borrowTitle" value="${searchParams.borrowTitle }">
	                    </div>交易类型：
	                    <select name="repaymentStatus" id="repaymentStatus">
							<option value="">全部</option>
							<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}">   
							<option value="${item.key}" <c:if test="${item.key==searchParams.repaymentStatus }">selected="selected"</c:if>>${item.value}</option>
							</c:forEach>
						</select>
						<a href="javaScript:subForm()" class="selected" data="6" style="float: right;">查询</a>
	                 </dd>
	           	</dl>
	        </div>
			<div class="model-box rec-items">
				<ul class="items title" id="title">
					<li style="width:100px;">标题</li>
					<li style="width:100px;">第几期/共几期</li>
					<li style="width:100px;">应还款日期</li>
					<li style="width:100px;">本期应还本息</li>
					<li>利息</li>
					<li>滞纳金</li>
					<li>逾期天数</li>
					<li>状态</li>
					<li>操作</li>
				</ul>
				<div id="investRecordsTable">
					<jsp:include page="../../page.jsp"></jsp:include>					
				</div>
			</div>
			
    </div>
    <!--/main--> 
</div>

 <!--/bottom-->
 <jsp:include page="/foot.do"></jsp:include>
</body>

<script type="text/javascript">
	$(function(){
		 changeCurrent();
		 var data={"tojsp":1};
		 getPageFrom(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable");
	})
	
	
	function changeCurrent(){
		var current=$("#menu").find(".current");
		$(current).removeClass("current");
		$("#borrowRepayment").addClass("current");
	}
	
	
	function subForm(){
		var repaymentStatus=$("#repaymentStatus").val();
		var borrowTitle=$("#borrowTitle").val();
		 var data={"repaymentStatus":repaymentStatus,
				 "borrowTitle":borrowTitle
		 };
		 getPageFrom(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable");
	}
	
	
	//遍历类型
	var repaymentStatus = {};
	<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}"> 
		repaymentStatus["${item.key}"] = "${item.value}";
	</c:forEach>
	
	//拼成表格
	function generateInvestRecordsTable(data){
		var  repayBtn = "--";
		if(data.repaymentStatus != '2'){ //不是还款成功
			var  repayBtn = "<a href=\"javaScript:confirmc('确认还款？',function(){repay('"+data.repayId+"','"+data.erepayId+"');})\" class=\"selected\" >还款</a>";
		}
		
		 var htmlStr = '<ul class="items">';
	     htmlStr+="<li style=\"width:100px;\">"+data.borrowTitle+"</li>"+
	     "<li style=\"width:100px;\">"+data.currentPeriod+"/"+data.totalPeriod+"</li>"+
	     "<li style=\"width:100px;\">"+data.repaymentTime+"</li>"+
	     "<li style=\"width:100px;\">"+data.repaymentAmount+"</li>"+
	     "<li>"+data.repaymentInterest+"</li>"+
	     "<li>"+data.lateInterest+"</li>"+
	     "<li>"+data.lateDays+"</li>"+
	     "<li>"+repaymentStatus[data.repaymentStatus]+"</li>"+
	     "<li>"+repayBtn+"</li>";
	     htmlStr+="</ul>";
	    return htmlStr;
	};
	function repay(repayId,signRepayId){
		$.ajax({
			cache : false,
			type : "POST",
			url : "${path }/borrowRepayment/repay/"+repayId+".do",
			data : {"signStr":signRepayId},
			async : false,
			error : function(request) {
				alertc("服务器异常");				
			},
			success : function(data) {
				alertc(data.msg,subForm);
			}
		});
	}
</script>
</html>