<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-已收款明细账</title>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_2_left4" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title4"></c:set>
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
          <!-- <td>&nbsp;关键字：<input type="text" id="keywords" name="keywords"></td> -->
          <td>&nbsp;<input type="submit" onclick="subForm();" class="searchbtn" value="搜索"></td>
      </tr>
    </tbody></table>
    <div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->

        <table class="siteInfoWidth1 tableDate">
            <thead>
            	<tr class="tdwait">
	                <td class="tdwait1">&nbsp;借款标题</td>
	                <td>应收日期</td>
	                <td class="tdwait1">借款者</td>
	                <td>第几期/总期数</td>
	                <td class="tdwait1">收款总额</td>
	                <td>应收本金</td>
	                <td class="tdwait1">应收利息</td>
	                <td>逾期利息</td>
	                <td class="tdwait1">逾期天数</td>
	                <td>状态</td>
	                <td class="tdwait1">协议书</td>
         		</tr>
        </thead>
        
        <tbody id="inRepayTable"></tbody>
        </table>
	</div>
</div>
    
    </div>
  </div>	
    
</div>
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
function subForm(){
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	 var data={"beginTime":beginTime,
			 "endTime":endTime
	 };
	 getPageTable(data,"${path }/invest/getRepByStatusPage.do?repossessedStatus="+2,generateInRepayTable,"inRepayTable","11");
}

	$(function(){
		 var data={};
		 getPageTable(data,"${path }/invest/getRepByStatusPage.do?repossessedStatus="+2,generateInRepayTable,"inRepayTable","11");
	});
	//拼成表格
	function generateInRepayTable(data){
		var xz="";
		if(data.agreementPath!=null){
			xz="下载";
		}
		 var htmlStr = '<tr>';
	     htmlStr+="<td>  <a href=${path}/invests/"+data.ebId+">"+data.borrowTitle+" </a></td>"+
	     "<td>"+toDate(data.prepareDatetime,'yyyy-MM-dd')+"</td>"+
	     "<td>"+data.userAccount+"</td>"+
	     "<td>"+data.currentPeriod+"/"+data.totalPeriod+"</td>"+
	     "<td>"+data.prepareAmount+"</td>"+
	     "<td>"+data.repossessedCapital+"</td>"+
	     "<td>"+data.repossessedInterest+"</td>"+
	     "<td>"+data.lateInterest+"</td>"+
	     "<td>"+data.lateDays+"</td>"+
	     "<td>"+"已收款"+"</td>"+
	     "<td><a href=${path}"+data.agreementPath+">"+xz+"</a></td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	
</script>
</html>