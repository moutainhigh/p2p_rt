<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-尚未发布的借款</title>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_3_left10" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title2"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
   	<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
    <table class="search">
      <tbody><tr>
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
	                <td class="tdwait1">&nbsp;标题</td>
	                <td>类型</td>
	                <td class="tdwait1">金额(元)</td>
	                <td>年利率</td>
	                <td class="tdwait1">期限</td>
	                <td>发布时间</td>
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
function subForm(){
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	 var data={"beginTime":beginTime,
			 "endTime":endTime
	 };
	 getPageTable(data,"${path }/iborrow/borrowStatusPage1.do",generateInvestRecordsTable,"investRecordsTable",9);
}

	$(function(){
		var data = {};
		getPageTable(data,"${path }/iborrow/borrowStatusPage1.do",generateInvestRecordsTable,"investRecordsTable",9);
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
		var htmlStr = '<tr>';
	     htmlStr+="<td> <a href=${path}/invests/"+data.ebId+">"+data.borrowTitle+"</a></td>"+
	     "<td>"+data.tName+"</td>"+
	     "<td>"+data.borrowSum+"</td>"+
	     "<td>"+data.annualInterestRate+"%</td>"+
	     "<td>"+data.borrowTimeLimit+""+day+"</td>"+
	     "<td>"+data.borrowAddTime+"</td>"+
	     "<td>"+jindu+"%</td>"+
	     "<td>待审核</td>"+
	     "<td><a href=\"cancelBorrow.do?id="+data.id+"\" onClick=\"return ConfirmDel();\">取消</a></td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
	function ConfirmDel()
	{
	   if(confirm("确定要取消吗？取消后将无法恢复！"))
	     return true;
	   else
	     return false;
	}
</script>
</html>