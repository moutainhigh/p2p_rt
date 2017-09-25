<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }交易记录</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
<script src="${cssJsPath }/timepicker/WdatePicker.js" type="text/javascript"></script>
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
            <h2>交易记录</h2>
            <!-- <div class="r"><span class="excel-export"><a href="javascript:void(0)" id="export">导出到Excel<i class="icons"></i></a></span> </div> -->
            <b class="line" data-line="一条横线"></b></div>
	        <div class="model-box trade-filter">
	            <dl class="filter-items bank-items">
	                <dt>标题</dt>
	                <dd>
	                    <input type="text" id="borrowTitle" class="input" style="background:#f0f5f7;padding: 5px;"  name="borrowTitle">
						<a href="javaScript:subForm()" class="selected" data="6" style="float: right;">查询</a>
	                 </dd>
	           	</dl>
	        </div>
			<div class="model-box rec-items">
				<ul class="items title">
					<li style="text-align: center;" class="col_1">标题</li>
					<li style="text-align: center;" class="col_2">竞拍价格(￥)</li>
					<li style="text-align: center;" class="col_3">当前年利率</li>
					<li style="text-align: center;width: 20%;" class="col_4">竞拍时间</li>
					<li style="text-align: center;" class="col_5">状态</li>
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
		 var data={};
		 getPageFrom(data,"${path }/account/getBorrowTransferByUserId.do",generateInvestRecordsTable,"investRecordsTable");
	})
	
	
	function changeCurrent(){
		var current=$("#menu").find(".current");
		$(current).removeClass("current");
		$("#borrowTransfer").addClass("current");
	}
	
	
	function subForm(){
		var borrowTitle=$("#borrowTitle").val();
		 var data={"borrowTitle":borrowTitle};
		 getPageFrom(data,"${path }/account/getBorrowTransferByUserId.do",generateInvestRecordsTable,"investRecordsTable");
	}
	
	
	
	//拼成表格
	function generateInvestRecordsTable(data){
		 var  status="";
		 switch(data.status){
		 case 1:
			 status="完成";
			 break;
		 }
		 var createTime = data.create_time;
		 createTime=toDate(createTime,"yyyy-MM-dd hh:mm:ss");
		 var htmlStr = '<ul class="items">';
	     htmlStr+="<li class=\"col_1\" style=\"text-align: center;\">"+data.borrow_title+"</li>"+
	     "<li class=\"col_2\" style=\"text-align: center; \">"+data.auction_money+"</li>"+
	     "<li class=\"col_3\" style=\"text-align: center;\">"+data.cur_interest_rate+"%</li>"+
	     "<li class=\"col_4\" style=\"text-align: center;width: 20%; \">"+createTime+"</li>"+
	     "<li class=\"col_5\" style=\"text-align: center; \">"+status+"</li>";
	     htmlStr+="</ul>";
	    return htmlStr;
	};
</script>
</html>