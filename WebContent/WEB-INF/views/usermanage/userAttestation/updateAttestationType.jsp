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
			<form method="post" action="attestation/${PRE_PATH_EDIT }saveAttestationType" name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${attestationType.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>类型名称:</label><input name="attestationName" class="required" type="text" alt="请输入类型名称" size="30"  value="${attestationType.attestationName }" /></p>
					<div class="divider"></div>
					<p><label>积分:</label><input name="attestationScore" id="attestationScore" class="required" type="text" alt="请输入积分" size="30"  value="${attestationType.attestationScore }" /></p>
					<div class="divider"></div>
					<p><label>排序:</label><input name="attestationSequence" class="required" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" alt="请输入序列" size="30" value="${attestationType.attestationSequence }" /></p>
					<div class="divider"></div>
					<p><label>状态:</label>
						<input type="radio" name="attestationStatus" value="0" <c:if test="${attestationType.attestationStatus==0 }">checked="checked"</c:if>>关闭
						<input type="radio" name="attestationStatus" value="1" <c:if test="${attestationType.attestationStatus==1 || attestationType.attestationStatus==null }">checked="checked"</c:if> />开启
					</p>
					<div class="divider"></div>
					<p style="height:80px;"><label>简介:</label>
						<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="attestationSummary">${attestationType.attestationSummary }</textarea>
					</p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="attestationRemark">${attestationType.attestationRemark }</textarea>
					</p>
				</div>
				<div class="formBar">
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
					</ul>
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
