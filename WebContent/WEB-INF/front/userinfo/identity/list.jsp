<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	         <c:set var="curNav" value="title7"></c:set>
            <%@include file="titleNav.jsp"%>
            <div id="data" class="model-box rec-items">
       			<div class="siteInfoWidth ht1"></div>
						<table class="siteInfoWidth1 tableDate">
							<thead>
								<tr class="tdwait">
									<td class="tdwait1">&nbsp;说明信息</td>
									<td>资料类型</td>
									<td class="tdwait1">审核时间</td>
									<td>审核说明</td>
									<td class="tdwait1">积分</td>
								</tr>
							</thead>
							<tbody id="dataDiv"></tbody>
						</table>
						<div class="siteInfoWidth ht1"></div>
			</div>
	   
    </div>
    </div>
    </div>
    </div>
    <!--/main--> 
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
$(function (){
	var data={"tojsp":1};
	getPageTable(data,"${path }/verify/dataPage.do",dataTable,"dataDiv",5);
});
//拼成表格
function dataTable(data){
var c="";
	if(data.attestationStatus==0){
		 c="申请中";
	}
	if(data.attestationStatus==1){
		 c="通过";
	}
	if(data.attestationStatus==-1){
		 c="未通过";
	}
	 var htmlStr = '<tr>';
     htmlStr+="<td>"+data.attestationRemarks+"</td>"+
     "<td>"+data.attestationName+"</td>"+
     "<td>"+toDate(data.attestationVerifyDatetime)+"</td>"+
     "<td>"+c+"</td>"+
     "<td>"+data.attestationScore+"</td>";
     htmlStr+="</tr>";
    return htmlStr;
};
</script>
</html>
