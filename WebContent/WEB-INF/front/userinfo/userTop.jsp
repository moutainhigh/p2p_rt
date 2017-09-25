<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String cssJsPath = path + "/common/front";
%>
<c:set var="path" value="<%=path %>"></c:set>
<c:set var="cssJsPath" value="<%=cssJsPath %>"></c:set>
   <div class="wdzh-top">
    <div class="wdzh-k">
    	<a href="${path }/account/accountIndex.html"><span id="account1" ><img width="16" height="15" src="${cssJsPath }/images/ny/wdzh_16.jpg"> 账户总览 </span></a>
    	<a href="${path }/account/transcationRecord.html"><span id="account2"><img width="16" height="15" src="${cssJsPath }/images/ny/wdzh_18.jpg"> 交易记录 </span></a>
     	<a href="${path }/account/cashValue.do"><span id="account3"><img width="16" height="17" src="${cssJsPath }/images/ny/wdzh_03.jpg"> 充值提现 </span></a>
    	<a href="${path }/account/bankCard.html"><span id="account4"><img width="16" height="15" src="${cssJsPath }/images/ny/wdzh_05.jpg"> 银行卡   </span></a>
     	<a href="${path }/verify/userAttestation.do"><span id="account5"><img width="16" height="15" src="${cssJsPath }/images/ny/wdzh_21.jpg"> 账户管理 </span></a>
    	<a href="${path }/invest/propertyStatistics.do"><span id="account6"><img width="16" height="15" src="${cssJsPath }/images/ny/wdzh_23.jpg"> 资产统计 </span></a>
     	<a href="${path }/invest/investRecord.do?bidKind=6"><span id="account7"><img width="12" height="17" src="${cssJsPath }/images/ny/wdzh_07.jpg"> 投资记录 </span></a>
    	<a href="${path }/invest/investRedeem.do"><span id="account8"><img width="16" height="17" src="${cssJsPath }/images/ny/wdzh_09.jpg"> 投资赎回 </span></a>
     	<a href="${path }/invest/loanAgreement.do?bidKind=6"><span id="account9"><img width="16" height="17" src="${cssJsPath }/images/ny/wdzh_11.jpg"> 借款协议 </span></a>
    	<a href="${path }/invest/autoInvestment.html"><span id="account10"><img width="16" height="17" src="${cssJsPath }/images/ny/wdzh_13.jpg"> 投资助手 </span></a>
    </div>
  </div>
  <script type="text/javascript">
	function userTopHover(obj){
		$(".userHover").removeClass("userHover");
		switch(obj){
			case 1:
				$("#account1").addClass("userHover");
				break;
			case 2:
				$("#account2").addClass("userHover");
				break;
			case 3:
				$("#account3").addClass("userHover");
				break;
			case 4:
				$("#account4").addClass("userHover");
				break;
			case 5:
				$("#account5").addClass("userHover");
				break;
			case 6:
				$("#account6").addClass("userHover");
				break;
			case 7:
				$("#account7").addClass("userHover");
				break;
			case 8:
				$("#account8").addClass("userHover");
				break;
			case 9:
				$("#account9").addClass("userHover");
				break;
			case 10:
				$("#account10").addClass("userHover");
				break;
		}
	}
	
	</script>