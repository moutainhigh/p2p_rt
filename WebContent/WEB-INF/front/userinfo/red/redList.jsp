<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${showKeywords}" />
<meta name="description" content="${description}" />

<title>${showTitle }-我的账户</title>
<script type="text/javascript" src="${frontPath }/js/pageRed.js"></script>
</head>
<body>
  <jsp:include page="/top.do"></jsp:include>
  <div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
	<input type="hidden" value="left13" id="curTitle">
    <jsp:include page="/account/userLeft.do"></jsp:include>
  </div>
  <div class="J-ma-conR hb_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);">我的红包</a>
    </div>
     <div class="txt">
      <!-- <p>红包使用规则：</p>
      <p>1、XXX红包分为发放和打开两个流程。注册、投资、参加活动等均可获得红包。</p>
      <p>2、发放到用户账户的红包需满足一定条件方能打开，打开后的红包可以直接提现。</p>
 -->    </div> 
    <div class="hb_num jhb_num jhbn-two" >
      <!-- <h4>我的红包</h4> -->
      <ul class="jhb_num-title">
       <li>金额(元)</li>
       <li>红包名称</li>
       <li>状态</li>
       <li class="jhbnt-time" >有效期</li>
       <li>使用条件</li>
       <li style="color: #F7772D;">操作</li>
      </ul>
      
      <c:if test="${not empty deductionMoney && deductionMoney > 0}">
	      <ul >
		      <c:if test="${empty deductionMoney}">
		      	<li>0元</li>
		      </c:if>
		      <c:if test="${not empty deductionMoney}">
		      	<li>${deductionMoney}元</li>
		      </c:if>
		       <li>抵扣红包</li>
		       <li>使用中</li>
		       <li class="jhbnt-time" >至使用完为止</li>
		       <li>按投资额*1%抵用</li>
		       <c:choose>
			       	<c:when test="${empty deductionMoney}">
			       		<li style="color: #F7772D;">已用完</li>
			       	</c:when>
			       	<c:otherwise>
			       		<li><span onclick='toBorrowInfo()' style='cursor: pointer; color: #F7772D;'>马上投资使用</span></li>
			       	</c:otherwise>
		       </c:choose>
	      </ul>
      </c:if>
      <c:if test="${activitySwitch == '1'}">
	      <ul >
		       <li>${unUseMoney}元</li>
		       <li>活动红包</li>
		       <c:choose>
                	<c:when test="${unUseMoney == '0.00'}">
                		<li>已用完</li>
                	</c:when>
                	<c:otherwise>
                		<li>使用中</li>
                	</c:otherwise>
                </c:choose>
		       <li class="jhbnt-time" >2015-12-17~2016-1-17</li>
		       <li>按投资额*1%赠送</li>
		        <c:choose>
                	<c:when test="${unUseMoney == '0.00'}">
                		<li style="color: #F7772D;">已用完</li>
                	</c:when>
                	<c:otherwise>
                		<li><span onclick='toBorrowInfo()' style='cursor: pointer; color: #F7772D;'>马上投资使用</span></li>
                	</c:otherwise>
                </c:choose>
	      </ul>
      </c:if>
      <div id="redenvelopesTables" align="center"></div>
      </div>
  </div>
 </div>
</div>

<!--footer start-->
<jsp:include page="/foot.do"></jsp:include>
<%@ include file="../../onlineSupport.jsp"%>
<!---footer end--->
</body>
<script type="text/javascript">
$(function() {
	 var data={};
	 getPageTable(data,"${path }/account/redenvelopesLogPage.do",generateRedenvelopesTable,"redenvelopesTables");
});


//遍历类型
var tradeCode = {};
<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}"> 
	tradeCode["${item.key}"] = "${item.value}";
</c:forEach>

//拼成表格
function generateRedenvelopesTable(data){
	var status = "";//红包状态
	var statusButton = "";//操作
	if(data.status == 1){
		status = "未打开";
		statusButton = "<div id='"+data.id+"' onclick=openRedEncelope('"+data.id+"')  style='cursor: pointer; color:#ec4b5e;'>打开</div>";
	}else if(data.status == 2){
		status = "未满足条件";
		statusButton = "<span onclick='toBorrowInfo()' style='cursor: pointer; color: #F7772D;'>马上投资使用</span>";
	}else if(data.status == 3){
		status = "已打开";
		statusButton = "已打开";
	}else if(data.status == 4){
		status = "已过期";
		statusButton = "已过期";
	}
	var htmlStr = "<ul>";
     htmlStr+=
	 /* "<td>"+data.id+"</td>"+
     "<td>"+data.user_account+"</td>"+ */
     "<li>"+data.amount+'元'+"</li>"+
     "<li>"+data.name+"</li>"+
     "<li>"+status+"</li>"+
     "<li class=jhbnt-time>"+data.periodBeginTime+"~"+data.periodEndTime+"</li>"+
     "<li>"+data.remark+"</li>"+
     "<li>"+statusButton+"</li>";
     htmlStr+="</ul>";
    return htmlStr;
};

//跳转我要投资
function toBorrowInfo(){
	var path = "${path}";
	location.href = path+"/invest";
}

//打开红包
function openRedEncelope(id){
	var path = "${path }";
	$.ajax({
        dataType: 'json',
        url:path+"/account/openRedEncelope.do",
        data:{"id":id},
        type: 'POST',
        success: function(data) {
        	if(data.code == "200"){
				alertc("红包已打开");
				window.location.reload();
			}else{
				alertc("打开红包失败");
			}
        }
	});
}
</script>
</html>
 <!-- <script type="text/javascript">
var openLinkId = "openLink_";
	$(function() {
		
		<c:if test="${not empty openAutoRedFlag }">
		$("[name='setAutoOpened']").attr("checked",true);
		</c:if>
		 var data={"cxType":1};
		 getPageTable(data,"${path }/redenvelopes/getPagedList.do",generateInvestRecordsTable,"dataList",1);
	
		 $("input[name='setAutoOpened']").bind("click",function(){
			 var checked=$(this).attr("checked");
		     if("checked" == checked){ //设置自动打开
		    	  confirmc("开启自动打开红包，您将失去红包翻倍的机会。<br><br> 确认开启？",function(){
		    		  setAuto(2);
		    	  },function(){
		    		  $("input[name='setAutoOpened']").attr("checked",false);
		    	  });
		     }else{ //取消自动打开
		    	 setAuto(1);
		     }
		 });
		 
		 $('[title]').poshytip({className: 'tip-violet',
				bgImageFrameSize: 9});
	});
	function setAuto(type){
		$.ajax({
			dataType : 'json',
			url : "${path}/redenvelopes/setAutoFlag.do",
			data : {"flag":type},
			type : 'POST',
			success : function(data) {
				alertc(data.msg,function(){
					document.location.reload();
				});
				
			},
			error : function() {
				alertc("操作失败",function(){
					document.location.reload();
				});
			}
		});
	}

	function subForm(){
		var cxType=$("#cxType").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		 var data={"cxType":1,
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
		/**
		<ul class="content-list">
                    <li class="co-1">48</li>
                    <li class="co-2 ">新手红包</li>
                    <li class="co-3">未打开</li>              
                    <li class="co-4">2014.11.2至2014.12.2</li>
                    <li class="co-5"><a href="#">打开</a></li>
                    <li class="co-6">xxxxxxxxxxxxxxxxxxxxxxxxx</li>
                </ul>
		
		*/
		//红包操作共有5种：打开、继续投资、已登陆n天、已过期、已打开
		var dakai = "&nbsp;";
		var fillin = data.fillLimitParam;
		var amount = data.amount;
		
	//	var showTitle = "";//
		if(data.status == 1){ //未打开
			dakai = "<a cur=\""+data.id+"\" money=\""+data.amount+"\" onclick=\"openr(this)\" href=\"javascript:;\">打开</a>";
		}else if(data.status == 2){ //为满足条件
			
			if(data.openLimitType == 1 || data.openLimitType == 4){//投标
				dakai = "<a cur=\""+data.id+"\" onclick=\"gotoTz(this)\" href=\"javascript:;\">继续投资</a>";
			}else if(data.openLimitType == 2){//累计登录
				dakai = "已登录"+fillin+"天";
			}else{ //连续
				dakai = "已登录"+fillin+"天";
			}
		}else if(data.status == 3){ //已打开
			dakai = "<font title='您的红包于"+data.viewUpdateDate+"打开'>已打开</font>";
			amount = data.realAmount;
		}else{ //过期
			dakai = "已过期";
		}
	var nameTitle = "";
	if(data.name.length > 5){
		nameTitle = " title='"+data.name+"' ";
	}
		
		/* var htmlStr = "<ul class=\"content-list\">"+
				      "  <li class=\"co-1\" >"+addCommas(amount.toFixed(2))+"</li>"+
				      "  <li class=\"co-2\" "+nameTitle+">"+data.name+"</li>"+
				      "  <li class=\"co-3\">"+data.viewStatus+"</li>"+       
				      "  <li class=\"co-4\">"+data.viewPriodStartTime+"至"+data.viewPriodEndTime+"</li>"+
				      "  <li class=\"co-5\">"+dakai+"</li>"+
				      "  <li class=\"co-6\">"+data.remark+"</li>"+
				    "</ul>"; */
				    var htmlStr = "<tr";
				     htmlStr+=
					 /* "<td>"+data.id+"</td>"+
				     "<td>"+data.user_account+"</td>"+ */
				     "<td>"+data.amount+'元'+"</td>"+
				     "<td>"+data.name+"</td>"+
				     "<td>"+status+"</td>"+
				     "<td>"+data.periodBeginTime+"~"+data.periodEndTime+"</td>"+
				     "<td>"+statusButton+"</td>"+
				     "<td>"+data.remark+"</td>";
				     htmlStr+="</tr>";
				    return htmlStr;
	    return htmlStr;
	};
	function gotoTz(obj){
		window.location.href = "${path}/borrow/toOptimization.do";
	}
	
	function openr(obj){
		var id = $(obj).attr("cur");
		var oldMoney = $(obj).attr("money");
		$.ajax({
			dataType : 'json',
			url : "${path}/redenvelopes/open.do",
			data : {"id":id},
			type : 'POST',
			success : function(data) {
				if(data.code == '200'){
					var showMsg = data.msg+"元红包已发放到您的账户";
					if(parseFloat(data.msg) > parseFloat(oldMoney)){
						/* $("#redTip1").text("恭喜您!您的"+oldMoney+"元红包翻倍了");
						$("#redTip1").show(); */
						showMsg = "恭喜您!您的"+oldMoney+"元红包翻倍了<br/>" +showMsg;
						$("#redTip2").css("line-height","20px"); //
					}
					
					$("#redTip2").html(showMsg);
					
					$('#myModal').reveal($("#myModal").data());
				}else{
					alertc("操作失败",function(){
						document.location.reload();
					});
				}
			},
			error : function() {
				alertc("操作失败",function(){
					document.location.reload();
				});
			}
		});
	}
	
	function closeMyModal(){
		document.location.reload();
	}
</script> -->