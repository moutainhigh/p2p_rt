<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>修改总积分</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="credit/${PRE_PATH_EDIT }saveUserCredit"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate" name="form1">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${userCredit.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>信用积分:</label><input name="creditValue" class="required" type="text" alt="请输入信用积分" size="30" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${userCredit.creditValue }" /></p>
					<div class="divider"></div>
					<p><label>可用积分:</label><input name="creditValueUsable" class="required" type="text" alt="请输入可用积分" size="30" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${userCredit.creditValueUsable }" /></p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="4" class="required" cols="27" id="remark" name="remark"></textarea>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="testNum1()" >
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
	var re =$("#remark").val();
		if(re==''){
		   alert("备注不能为空！");
		   return false; 
		}
		$('form1').submit();

	}
</script>
</html>
