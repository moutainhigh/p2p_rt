<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();

%>   

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>审核</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="userEvaluateApply/${PRE_PATH_EDIT }checkUserEvaluateApply"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="applyId" value="${apply.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>用户名:</label>
					<input type="text" readonly="readonly" value="${apply.user.userAccount}" /></p>
					<input type="hidden" name="userId" value="${apply.userId }" />
					<div class="divider"></div>
					<p><label>申请类型:</label><input type="text" name="evaluateapplyType" readonly="readonly" value="${apply.evaluateapplyType}" /></p>
					<div class="divider"></div>
					<p><label>原来额度:</label><input type="text" name="amountBefore" readonly="readonly" value="${apply.amountBefore}" /></p>
					<div class="divider"></div>
					<p><label>申请额度:</label><input type="text" name="amountApply" id="amountApply" readonly="readonly" value="${apply.amountApply}" /></p>
					<div class="divider"></div>
					<p><label>内容:</label><input type="text" name="evaluateapplyContent" readonly="readonly" value="${apply.evaluateapplyContent}" /></p>
					<div class="divider"></div>
					<p><label>备注:</label><input type="text" name="evaluateapplyRemark" readonly="readonly" value="${apply.evaluateapplyRemark}" /></p>
					<div class="divider"></div>
					<p><label>申请时间:</label><input type="text" name="evaluateapplyAddtime" readonly="readonly" value="<fmt:formatDate value="${apply.evaluateapplyAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					
					<div id="checkDiv1" style="display: none;">
					<p><label>审核状态:</label>
						<input value="${APPLY_ALL_APPLY_STATUS[apply.evaluateapplyStatus]}" type="text" readonly="readonly"/>
					</p>
					<div class="divider"></div>
					<div style="<c:if test="${apply.evaluateapplyStatus == 3}">display:none;</c:if>">
						<p><label>通过额度:</label>
							<input type="text" value="${apply.amount}" class="number" />
						</p>
						<div class="divider"></div>
					</div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" readonly="readonly" rows="5" cols="45">${apply.verifyRemark}</textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					</div>
					
					<!-- 状态为待审核时显示 -->
					<div id="checkDiv2" style="display: none;">
					<p><label>审核状态:</label>
						<input type="radio" name="evaluateapplyStatus" value="2" onclick="setAmount(2);" />通过
						<input type="radio" name="evaluateapplyStatus" value="3" onclick="setAmount(3);" checked="checked"  />不通过
					</p>
					<div class="divider"></div>
					<div id="amountDiv" style="display: none;">
						<p><label>通过额度:</label>
							<input type="text" name="amount" id="amount" class="number" onblur="checkAmount();" />
							<span id="amountTip"></span>
						</p>
						<div class="divider"></div>
					</div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" name="verifyRemark" rows="5" cols="45"></textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive" id="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="subForm();">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
<script type="text/javascript">
$(document).ready(function(){
	  var evaluateapplyStatus="${apply.evaluateapplyStatus}";
	  if(evaluateapplyStatus==1){
		  $("#checkDiv2").show();
	  }else{
		  $("#checkDiv2").hide();
		  $("#checkDiv1").show();
		  $("#buttonActive").hide();
	  }
});
	function setAmount(val){
		if(val == 2){
			var amountApply = "${apply.amountApply}";
			$("#amount").val(amountApply);
			$("#amountDiv").show();
		}
		else if(val == 3){
			var amountBefore = "${apply.amountBefore}";
			$("#amount").val(amountBefore);
			$("#amountDiv").hide();
		}
	}
	
	function checkAmount(){
		var money = Number($("#amountApply").val());
		var amountVal = Number($("#amount").val());
		if(amountVal > money){
			$("#amountTip").text("不能超过申请额度").css("color","red");
			return false;
		}else{
			$("#amountTip").text("");
			return true;
		}
	}
	function subForm(){
		if(checkAmount()==true){
			$("#frm").submit();
		}
	}
</script>
</html>