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
		<title>审核 用户费用扣除</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="accountDeduct/${PRE_PATH_EDIT }saveCheck"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${accountDeduct.id }">
				<input type="hidden" name="userId"  value="${accountDeduct.userId }">
				<div class="pageFormContent" layoutH="56">
					 <p><label>用户名：</label><input name="userAccount" value="${accountDeduct.userAccount }" readonly="readonly"/>
					<div class="divider"></div>
					 <p><label>扣费类型：</label><span>${ACCOUNTDEDUCT_ALL_TYPE[accountDeduct.deductType] }</span>
					    <input name="deductType" value="${accountDeduct.deductType}" type="hidden"/>
					 </p>
					<div class="divider"></div>
					<p><label>金&nbsp;&nbsp;&nbsp;&nbsp;额：</label><input name="deductAmount" value="${accountDeduct.deductAmount }" readonly="readonly"></p>
					<div class="divider"></div>
				    <p><label>扣费创建人：</label><input name="addUserAccount" value="${accountDeduct.addUserAccount }" readonly="readonly"/>
				   <input name="addUserId" value="${accountDeduct.addUserId }" type="hidden"/>
				    </p>
					<div class="divider"></div>
				    <p><label>扣费创建时间：</label><span><fmt:formatDate value="${ accountDeduct.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
					<div class="divider"></div>
					<p><label>扣费创建IP：</label><span>${accountDeduct.addIp }</span></p>
					<div class="divider"></div>
					<c:if test="${'0'eq accountDeduct.checkState  }">
						<p><label>审核状态：</label><span>未审核</span></p>
						<div class="divider"></div>
						<p><label>审&nbsp;&nbsp;&nbsp;&nbsp;核：</label>
						   <input type="radio" name="checkState" value="1" checked="checked"  />通过
						   <input type="radio" name="checkState" value="0" />未通过
						 </p>
						<div class="divider"></div>
						<p style="height: 80px;"><label>备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
					 	  <textarea rows="4" cols="30" name="remark" style="max-height: 70px;" >${accountDeduct.remark }</textarea>
				         <div class="divider"></div>
				    </p>
					</c:if>
					<c:if test="${'1'eq accountDeduct.checkState  }">
					    <p><label>审核状态：</label><span style="color: red;">已审核</span></p>
						<div class="divider"></div>
						<p><label>审核人：</label><span>${accountDeduct.checkUserAccount }</span></p>
						<div class="divider"></div>
						<p><label>审核时间：</label><span><fmt:formatDate value="${accountDeduct.checkTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
						<div class="divider"></div>
						<p><label>审核IP：</label><span>${accountDeduct.checkIp }</span></p>
						<div class="divider"></div>
					 <p style="height: 80px;"><label>备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
					   <textarea rows="4" cols="30" name="remark" style="max-height: 70px;" readonly="readonly">${accountDeduct.remark }</textarea>
				    </p>
				    <div class="divider"></div>
				    </c:if>
					<p><label>验证码:</label>
						<input maxlength="4" style="width:60px; margin-bottom: 10px;" name="captcha" type="text" id="captcha" class="input" >
						<img style="margin-top: -5px;" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
					</p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
									   确定审核
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取&nbsp;&nbsp;消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
</html>
