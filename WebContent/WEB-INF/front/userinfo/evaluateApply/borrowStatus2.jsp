<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-正在还款的借款</title>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_3_left11" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title3"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
   	<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
    <table class="search">
      <tbody><tr>
          <td>标题：<input type="text" id="borrowTitle" name="borrowTitle" class="input" value="${searchParams.borrowTitle }">
		  </td>
          <td>
          交易类型：
		<select name="repaymentStatus" id="repaymentStatus">
			<option value="">全部</option>
			<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}">   
			<option value="${item.key}" <c:if test="${item.key==searchParams.repaymentStatus }">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
          </td>
          <td>&nbsp;<input type="submit" onclick="subForm();" class="searchbtn" value="搜索"></td>
      </tr>
    </tbody></table>
    <div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->

        <table class="siteInfoWidth1 tableDate">
            <thead>
            	<tr class="tdwait">
	                <td class="tdwait1">&nbsp;标题</td>
	                <td>第几期/共几期</td>
	                <td class="tdwait1">应还款日期</td>
	                <td>本期应还本息</td>
	                <td class="tdwait1">利息</td>
	                <td>年利率</td>
	                <td class="tdwait1">滞纳金</td>
	                <td >逾期天数</td>
	                <td class="tdwait1">状态</td>
	                <td>操作</td>
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
	$(function(){
		 var data={"tojsp":1,"repaymentStatus":1};
		 getPageTable(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable",9);
	});
	
	
	function subForm(){
		var repaymentStatus=$("#repaymentStatus").val();
		var borrowTitle=$("#borrowTitle").val();
		 var data={"repaymentStatus":repaymentStatus,
				 "borrowTitle":borrowTitle
		 };
		 getPageTable(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable",9);
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
		
		 var htmlStr = '<tr>';
	     htmlStr+="<td><a href=${path}/invests/"+data.ebId+">"+data.borrowTitle+"</a></td>"+
	     "<td>"+data.currentPeriod+"/"+data.totalPeriod+"</td>"+
	     "<td>"+data.repaymentTime+"</td>"+
	     "<td>"+data.repaymentAmount+"</td>"+
	     "<td>"+data.repaymentInterest+"</td>"+
	     "<td>"+data.annualInterestRate+"%</td>"+
	     "<td>"+data.lateInterest+"</td>"+
	     "<td>"+data.lateDays+"</td>"+
	     "<td>"+repaymentStatus[data.repaymentStatus]+"</td>"+
	     "<td>"+repayBtn+"</td>";
	     htmlStr+="</tr>";
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