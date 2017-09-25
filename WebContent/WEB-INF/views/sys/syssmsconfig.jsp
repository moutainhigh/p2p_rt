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
		<title>短信配置</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="sys/${PRE_PATH_EDIT }save"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${sysSmsConfig.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>用户名:</label><input name="sysSmsAccount" class="required" minlength="2" maxlength="16" type="text" alt="请输入用户名" size="30"  value="${sysSmsConfig.sysSmsAccount }" /></p>
					<div class="divider"></div>
					<p><label>密  码:</label><input name="sysSmsPassword" class="required" minlength="6" maxlength="16" type="password" alt="请输入密码" size="30"  value="${sysSmsConfig.sysSmsPassword }" /></p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
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

</html>
