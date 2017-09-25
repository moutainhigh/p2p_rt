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
		<title>添加权限</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="role/o_saveRole" 
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="type" value="add">
				<div class="pageFormContent" layoutH="56">
					<p><label>角色名称:</label><input name="role_name" class="required" minlength="2" maxlength="10" type="text" alt="请输入角色名称" size="30"  /></p>
					<div class="divider"></div>
					<p><label>上级角色:</label>
						<input name="district.id" value="" type="hidden" />
						<input class="textInput readonly" name="district.districtName"  type="text" readonly="readonly" />
						<a class="btnLook" href="role/v_showRoleList" width="280" height="470" lookupgroup="district">上级菜单</a>	
					</p>
					<div class="divider"></div>
					<p><label>是否启用:</label><input name="roleStatus"  checked="checked" type="radio" value="1" />启用&nbsp;&nbsp;&nbsp;&nbsp;<input name="roleStatus" value="0"  type="radio" />禁用</p>
					<div class="divider"></div>
					<label>角色描述:</label><textarea class="editor" name="role_summary" rows="10" cols="60" tools="simple"></textarea>
					<br/><div class="divider"></div>
					<label>注意事项:</label><textarea class="editor" name="role_remark" rows="10" cols="60" tools="simple"></textarea>
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
