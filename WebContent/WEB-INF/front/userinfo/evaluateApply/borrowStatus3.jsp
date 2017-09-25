<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-已还完的借款</title>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_3_left12" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title4"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
   	<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
    <table class="search">
      <tbody><tr>
      <td>标题：<input type="text" id="borrowTitle" name="borrowTitle" class="input" value="${searchParams.borrowTitle }">
		  </td>
          <td>发布时间：
			<input type="text" id="beginTime" name="beginTime"
				class="inputBorder Wdate"
				onClick="WdatePicker({isShowClear:false,readOnly:true})" />
		  </td>
          <td>&nbsp;到&nbsp;</td>
          <td>
          <input type="text" id="endTime" name="endTime" class="inputBorder Wdate" onClick="WdatePicker({isShowClear:false,readOnly:true})"/>
          </td>
          <td>&nbsp;<input type="submit" onclick="subForm();" class="searchbtn" value="搜索"></td>
      </tr>
    </tbody></table>
    <div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->

        <table class="siteInfoWidth1 tableDate">
            <thead>
            	<tr class="tdwait">
	                <td class="tdwait1">借款标题</td>
	                <td>类型</td>
	                <td class="tdwait1">应还时间</td>
	                <td>应还金额(元)</td>
	                <td class="tdwait1">利息</td>
	                <td>期限</td>
	                <td class="tdwait1">进度</td>
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

//遍历类型
var repaymentStatus = {};
<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}"> 
	repaymentStatus["${item.key}"] = "${item.value}";
</c:forEach>


function subForm(){
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	var borrowTitle=$("#borrowTitle").val();
	 var data={"beginTime":beginTime,
			 "endTime":endTime,
			 "repaymentStatus":2,
			 "borrowTitle":borrowTitle
	 };
	 getPageTable(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable");
}

	$(function(){
		var data = {"repaymentStatus":2};
		getPageTable(data,"${path }/iborrow/borrowRepaymentPage.do",generateInvestRecordsTable,"investRecordsTable");
	});
	
	//拼成表格
	function generateInvestRecordsTable(data){
		var day='';
		if(data.idDay==1){
			day='天';
		}
		if(data.idDay==2){
			day='月';
		}
		var jindu = '';
		if(data.tenderSum!=null){
			jindu=data.tenderSum/data.borrowSum*100;
		}
		
		var  repayBtn = "--";
		
		//<a href='+$_path+'/invests/'+data.eid>
		
		var htmlStr = '<tr>';
	     htmlStr+="<td> <a href=${path}/invests/"+data.ebId+">"+data.borrowTitle+"</a></td>"+
	     "<td>"+data.currentPeriod+"/"+data.totalPeriod+"</td>"+
	     "<td>"+data.repaymentTime+"</td>"+
	     "<td>"+data.repaymentAmount+"</td>"+
	     "<td>"+data.repaymentInterest+"</td>"+
	     "<td>"+data.lateInterest+"</td>"+
	     "<td>"+data.lateDays+"</td>"+
	     "<td>"+repaymentStatus[data.repaymentStatus]+"</td>"+
	     "<td>"+repayBtn+"</td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	

</script>
</html>