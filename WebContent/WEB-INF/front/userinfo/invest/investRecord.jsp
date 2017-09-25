<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-借出明细账</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
<script src="${cssJsPath }/timepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_2_left5" id="curTitle">
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
          <td>状态：<select name="tenderStatus" id="tenderStatus">
				<c:forEach var="item" items="${TENDER_ALL_STATUS}"> 
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			 </select></td>
          
          <!-- <td>&nbsp;关键字：<input type="text" id="keywords" name="keywords"></td> -->
          <td>&nbsp;<input type="submit" onclick="subForm();" class="searchbtn" value="搜索"></td>
      </tr>
    </tbody></table>
    <div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->

        <table class="siteInfoWidth1 tableDate">
            <thead>
            	<tr class="tdwait">
	                <td class="tdwait1">&nbsp;标题</td>
	                <td>借款者</td>
	                <td class="tdwait1">投入总额(元)</td>
	                <td>利息总额(元)</td>
	                <td class="tdwait1">已收总额(元)</td>
	                <td>已收利息(元)</td>
	                <td class="tdwait1">待收本金(元)</td>
	                <td>待收利息 (元)</td>
	                <td class="tdwait1">状态</td>
         		</tr>
        </thead>
        
        <tbody id="inRepayTable"></tbody>
        </table>
	</div>
</div>
    
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
function subForm(){
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	var tenderStatus = $("#tenderStatus").val();
	 var data={"beginTime":beginTime,
			 "endTime":endTime,
			 "tenderStatus":tenderStatus
	 };
	 getPageTable(data,"${path }/invest/inRepayingPage.do",generateInRepayTable,"inRepayTable","9");
}

	$(function(){
		 var data={};
		 getPageTable(data,"${path }/invest/inRepayingPage.do",generateInRepayTable,"inRepayTable","9");
	});
	
	var TENDER_STATUS = {};
	<c:forEach var="item" items="${TENDER_ALL_STATUS}"> 
		TENDER_STATUS["${item.key}"] = "${item.value}";
	</c:forEach>
	//拼成表格
	function generateInRepayTable(data){
		var status = data.tenderStatus+"";
		 var htmlStr = '<tr>';
	     htmlStr+="<td><a href=${path}/invests/"+data.id+">"+data.borrowTitle+"</a></td>"+
	     "<td>"+data.userAccount+"</td>"+
	     "<td>"+data.tenderAmount+"</td>"+
	     "<td>"+data.interestAmount+"</td>"+
	     "<td>"+data.paidAmount+"</td>"+
	     "<td>"+data.interestPaid+"</td>"+
	     "<td>"+data.stayingAmount+"</td>"+
	     "<td>"+data.stayingInterest+"</td>"+
	     "<td>"+TENDER_STATUS[status]+"</td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	
</script>
</html>