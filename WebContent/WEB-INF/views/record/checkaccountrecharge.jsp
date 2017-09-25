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
			<form method="post" action="accountRecharge/${PRE_PATH_EDIT}checkAccountRecharge"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="accountRechargeId" value="${accountRecharge.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>订单号:</label>
						<input type="text" name="rechargeTradeNo" readonly="readonly" value="${accountRecharge.rechargeTradeNo}" />
					</p>
					<p><label>用户名称:</label>
						<input type="text" readonly="readonly" value="${accountRecharge.user.userAccount}" /></p>
						<input type="hidden" name="userId" value="${accountRecharge.userId}"/>
					<div class="divider"></div>
					<p><label>类型:</label>
						<input type="text" readonly="readonly" value="${ACCOUNTRECHARGE_ALL_RECHAREGE_TYPE[accountRecharge.rechargeType]}"/>
						<input type="hidden" name="rechargeType" value="${accountRecharge.rechargeType}"/>
					</p>
					<div class="divider"></div>
					<p><label>所属银行:</label>
						<input type="text" name="rechargePayment" readonly="readonly" value="${accountRecharge.rechargePayment}" />
					</p>
					<div class="divider" ></div>
					<p><label>充值金额:</label>
						<input type="text" name="rechargeMoney" readonly="readonly" value="${accountRecharge.rechargeMoney}" />
					</p>
					<div class="divider" ></div>
					<p><label>费用:</label>
							<input type="text" name="rechargeFee" readonly="readonly" value="${accountRecharge.rechargeFee}"/>
					</p>
					<div class="divider"></div>
					<p><label>到账金额:</label>
							<input type="text" readonly="readonly" value="${accountRecharge.rechargeMoney - accountRecharge.rechargeFee}"/>
					</p>
					<div class="divider"></div>
					<div id="rechargeAwardDiv" style="display: none;">
					<p><label>充值奖励:</label>
							<input type="text" readonly="readonly" value="${rechargeAward}"/>
					</p>
					<div class="divider"></div>
					</div>
					<p><label>银行返回:</label>
						<input type="text" name="rechargeReturn" readonly="readonly" value="${accountRecharge.rechargeReturn}" />
					</p>
					<div class="divider" ></div>
					<dl class="nowrap">
					    <dt>
					         备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" name="rechargeRemark" readonly="readonly" rows="5" cols="45">${accountRecharge.rechargeRemark}</textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					<p><label>充值时间:</label><input type="text"  name="rechargeAddtime" readonly="readonly" value="<fmt:formatDate value="${accountRecharge.rechargeAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					<p><label>添加IP:</label>
						<input type="text" name="rechargeAddip" readonly="readonly" value="${accountRecharge.rechargeAddip}" />
					</p>
					<div class="divider"></div>
					
					<!-- 状态不为1即待审核时显示 -->
					<div id="checkDiv1" style="display: none;">
						<p><label>状态:</label>
							<input type="text" readonly="readonly" value="${ACCOUNTRECHARGE_ALL_RECHAREGE_STATUS[accountRecharge.rechargeStatus]}"/>
						</p>
						<div class="divider"></div>
						<p><label>审核人:</label>
							<input type="text" readonly="readonly" value="${accountRecharge.verifyUser.userAccount}"/>
						</p>
						<div class="divider"></div>
						<p><label>审核时间:</label>
							<input type="text"  readonly="readonly" value="<fmt:formatDate value="${accountRecharge.verifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
						<div class="divider"></div>
						<dl class="nowrap">
						    <dt>
						          审核备注：
						    </dt>
						    <dd>
						        <textarea class="textInput valid" readonly="readonly" rows="5" cols="45">${accountRecharge.verifyRemark}</textarea>
						    </dd>
						<dl>
						<div class="divider"></div>
					</div>
					
					<!-- 状态为1即待审核时显示 -->
					<div id="checkDiv2" style="display: none;">
						<p><label>状态:</label>
							<input type="radio" name="rechargeStatus" value="2" />通过
							<input type="radio" name="rechargeStatus" value="3" checked="checked" />不通过
						</p>
						<div class="divider"></div>
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
									<button type="submit">
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
		  var rechargeStatus="${accountRecharge.rechargeStatus}";
		  if(rechargeStatus==1){
			  $("#checkDiv2").show();
		  }else{
			  $("#checkDiv2").hide();
			  $("#checkDiv1").show();
			  $("#buttonActive").hide();
		  }
		  var type="${accountRecharge.rechargeType}";
		  if(type=="2"){
			  $("#rechargeAwardDiv").show();
		  }
	});
	</script>
</html>
