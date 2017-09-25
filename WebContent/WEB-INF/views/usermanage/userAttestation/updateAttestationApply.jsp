<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加证件类型</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="attestation/${PRE_PATH_EDIT }saveAttestationApply" name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${attestationApply.id }">
				<input type="hidden" name="userId" value="${attestationApply.userId }">
				<div class="pageFormContent" layoutH="56">
					<p><label>类型名称:</label><input name="attestationName" readonly="attestationName" class="required" type="text" alt="请输入类型名称" size="30"  value="${attestationApply.attestationName }" /></p>
					<div class="divider"></div>
					<p><label>积分:</label><input name="attestationScore" readonly="attestationScore" id="attestationScore" class="required" type="text" alt="请输入积分" size="30"  value="${attestationApply.attestationScore }" /></p>
					<div class="divider"></div>
					<p style="height:220px;"><label>图片</label>
					<c:if test="${not empty attestationApply.attestationImg }">
						<a href="<%=path %>${attestationApply.attestationImg }"><img width="200" height="200" alt="${attestationApply.attestationName }" src="<%=path %>${attestationApply.attestationImg }"></a>
					</c:if>
					</p>
					<div class="divider"></div>
					<p><label>状态:</label>
						<input type="radio" name="attestationStatus" value="1" <c:if test="${attestationApply.attestationStatus==1 || attestationApply.attestationStatus==0}">checked="checked"</c:if>>审核通过
						<input type="radio" name="attestationStatus" value="-1" <c:if test="${attestationApply.attestationStatus==-1}">checked="checked"</c:if> />审核不通过
					</p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="attestationVerifyReview">${attestationApply.attestationVerifyReview }</textarea>
					</p>
				</div>
				<div class="formBar">
				<c:if test="${attestationApply.attestationStatus==0 }">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="testNum1()">
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
					</ul></c:if>
				</div>
			</form>
		</div>

	</body>
<script>
function testNum1(){
var re =/^\-?[0-9\,]*\.?\d*$/; 
	if(!re.test(document.getElementById("attestationScore").value)){
	   alert("请输入正确的积分数值！");
	   return false; 
	}
	$('form1').submit();
  
}
</script>
</html>
