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
		<title>添加积分类型</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="credit/${PRE_PATH_EDIT }saveCreditType" name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${creditType.id }" id="id">
				<div class="pageFormContent" layoutH="56">
					<p><label>积分名称:</label><input name="creditName" class="required" type="text" alt="请输入积分名称" size="30"  value="${creditType.creditName }" /></p>
					<div class="divider"></div>
					<p><label>积分编码:</label><input name="creditCode" class="required" type="text" alt="请输入积分编码" size="30"  value="${creditType.creditCode }" id="creditCode" onblur="uniqueCode()"/><span id="msg1" style="color:red;"></span></p>
					<div class="divider"></div>
					<p><label>积分数值:</label><input name="creditScore" id="creditScore" class="required" type="text" alt="请输入积分数值" size="30" value="${creditType.creditScore }" /></p>
					<div class="divider"></div>
					<p><label>积分周期:</label>
						<input type="radio" name="creditCycle" value="1" <c:if test="${creditType.creditCycle==1 || creditType.creditCycle==null }">checked="checked"</c:if>>一次
						<input type="radio" name="creditCycle" value="2" <c:if test="${creditType.creditCycle==2 }">checked="checked"</c:if> />每天
						<input type="radio" name="creditCycle" value="3" <c:if test="${creditType.creditCycle==3 }">checked="checked"</c:if> >间隔分钟
						<input type="radio" name="creditCycle" value="4" <c:if test="${creditType.creditCycle==4 }">checked="checked"</c:if> >不限
					</p>
					<div class="divider"></div>
					<p><label>奖励次数:</label>
						<input type="radio" name="creditAwardTimes" value="0" <c:if test="${creditType.creditAwardTimes==0 || creditType.creditAwardTimes==null }">checked="checked"</c:if> >不限
					</p>
					<div class="divider"></div>
					<p><label>时间间隔:</label><input name="creditInterval" class="required" type="text" alt="请输入时间间隔" size="30" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${creditType.creditInterval }" /><span>单位分钟</span></p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="4" cols="27" name="creditRemark">${creditType.creditRemark }</textarea>
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
	if(!re.test(document.getElementById("creditScore").value)){
	   alert("请输入正确的积分数值！");
	   return false; 
	}
	$('form1').submit();

}
function uniqueCode(){
	var url="<%=path %>${ADMIN_URL }/credit/checkCreditCode.do";
	$.ajax({
		  type: "post",
		  data: {"creditId":$("#id").val(),"creditCode" : $("#creditCode").val()},
		  url: url,
		  async:false,
		  success:function (ret){
			  if(ret == 'fail'){
				  $("#msg1").text( $("#creditCode").val()+"编码已存在!");
				  $("#creditCode").val(null);
				  return false;
			  }else{
				   $("#msg1").text("");
			  }
		  }} );
} 
</script>
</html>
