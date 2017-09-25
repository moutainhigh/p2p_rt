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
			<form method="post" action="quickInvestmentApplications/${PRE_PATH_EDIT}checkQuickInvestment"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${application.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>用户姓名:</label>
					<input type="text" readonly="readonly" value="${application.investUserName}" /></p>
					<div class="divider"></div>
					<p><label>预约产品:</label>
					<input type="text" readonly="readonly" value="${application.investProduct.productsTitle}" />
					</p>
					<div class="divider"></div>
					<p><label>性别:</label><input type="text"  readonly="readonly" value="${application.investUserSex}"/></p>
					<div class="divider"></div>
					<p><label>地区:</label><input type="text" readonly="readonly" value="${application.investUserArea}" /></p>
					<div class="divider"></div>
					<p><label>联系方式:</label><input type="text" readonly="readonly" value="${application.investUserTel}" /></p>
					<div class="divider" ></div>
					<p><label>投资周期:</label><input type="text"  readonly="readonly" value="${application.investUserCycle}个月" /></p>
					<div class="divider"></div>
					<p><label>理财资金:</label><input type="text" readonly="readonly"  value="${application.investMoney}元" /></p>
					<div class="divider" ></div>
					<p><label>方便联络:</label><input type="text"  readonly="readonly" value="${application.investBeginTime}:00-${application.investEndTime}:00" /></p>
					<div class="divider"></div>
					<p><label>预约时间:</label><input type="text" readonly="readonly" value="<fmt:formatDate value="${application.investAddDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider" ></div>
					
					<!-- 状态不为1即待审核时显示 -->
					<div id="checkDiv1" style="display: none;">
					<p><label>状态:</label>
						<input type="text" readonly="readonly" value="${QUICKINVESTMENT_ALL_QUICKINVESTMENT_STATUS[application.investVerifyStatus]}"/>
					</p>
					<div class="divider"></div>
					<p><label>审核人:</label><input type="text" readonly="readonly" value="${application.virifyUser.userAccount}"/></p>
					<div class="divider"></div>
					<p><label>审核时间:</label><input type="text" readonly="readonly" value="<fmt:formatDate value="${application.investVerifyDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider" ></div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" readonly="readonly" rows="5" cols="45">${application.investVerifyRemark}</textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					</div>
					
					<!-- 状态为1即待审核时显示 -->
					<div id="checkDiv2" style="display: none;">
					<p><label>状态:</label>
						<input type="radio" name="investVerifyStatus" value="2" checked="checked" />通过
						<input type="radio" name="investVerifyStatus" value="3" />不通过
					</p>
					<div class="divider"></div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" name="investVerifyRemark" rows="5" cols="45"></textarea>
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
		  var status="${application.investVerifyStatus}";
		  if(status==1){
			  $("#checkDiv2").show();
		  }else{
			  $("#checkDiv2").hide();
			  $("#checkDiv1").show();
			  $("#buttonActive").hide();
		  }
	});
	</script>
</html>
