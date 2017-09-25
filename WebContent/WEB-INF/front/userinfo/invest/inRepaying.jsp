<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-正在收款的项目</title>

</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_2_left2" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title2"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
    <!--正在投标的借款 开始-->
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
	                <td>借款者</td>
	                <td class="tdwait1">待收本金(元)</td>
	                <td>利息总额(元)</td>
	                <td class="tdwait1">已收总额(元)</td>
	                <td>已收利息(元)</td>
	                <td class="tdwait1">待收本金(元)</td>
	                <td>待收利息 (元)</td>
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
	function subForm() {
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var data = {
			"beginTime" : beginTime,
			"endTime" : endTime
		};
		getPageTable(data,
				"${path }/invest/inRepayingPage.do?tenderStatus=" + 3,
				generateInRepayTable, "inRepayTable");
	}

	$(function() {
		var data = {};
		getPageTable(data,
				"${path }/invest/inRepayingPage.do?tenderStatus=" + 3,
				generateInRepayTable, "inRepayTable");
	});

	//拼成表格
	function generateInRepayTable(data) {
		var htmlStr = '<tr>';
		htmlStr += "<td> <a href=${path}/invests/"+data.id+">" + data.borrowTitle + "</a></td>" + "<td>"
				+ data.userAccount + "</td>" + "<td>" + data.stayingAmount
				+ "</td>" + "<td>" + data.interestAmount + "</td>" + "<td>"
				+ data.paidAmount + "</td>" + "<td>" + data.interestPaid
				+ "</td>" + "<td>" + data.stayingAmount + "</td>" + "<td>"
				+ data.stayingInterest + "</td>";
		htmlStr += "</tr>";
		return htmlStr;
	};
</script>
</html>