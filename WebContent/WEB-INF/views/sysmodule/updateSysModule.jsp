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
			<form method="post" action="module/o_updateSysModule" 
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="type" value="update">
				<input type="hidden" name="id" value="${module.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>菜单名称:</label><input name="moduleName" value="${module.moduleName }" class="required" minlength="2" maxlength="10" type="text" alt="请输入菜单名称" size="30"  /></p>
					<div class="divider"></div>
					<p><label>菜单路径:</label><input name="moduleUrl" value="${module.moduleUrl }"   type="text" alt="请输入菜单路径" size="40"  /></p>
					<div class="divider"></div>
					<p><label>排序:</label><input name="moduleSequence" value="${module.moduleSequence}" type="text" alt="请输入排序值" size="20"  /></p>
					<div class="divider"></div>
					<p><label>上级菜单:</label>
						<input name="district.id" value="${module.moduleParentId }" type="hidden" />
						<input class="textInput readonly" value="${module.moduleParentName }"  name="district.districtName"  type="text" readonly="readonly" />
						<a class="btnLook" href="module/v_shwoRightList" width="280" height="470" lookupgroup="district">上级菜单</a>	
					</p>
					<div class="divider"></div>
					<p><label>是否启用:</label>
					<c:if test="${module.moduleView==1 }">
						<input name="moduleView"  checked="checked" type="radio" value="1" />启用&nbsp;&nbsp;&nbsp;&nbsp;<input name="moduleView" value="0"  type="radio" />禁用</p>
					</c:if>
					<c:if test="${module.moduleView==0 }">
						<input name="moduleView"   type="radio" value="1" />启用&nbsp;&nbsp;&nbsp;&nbsp;<input name="moduleView" value="0" checked="checked" type="radio" />禁用</p>
					</c:if>
					<div class="divider"></div>
					<p><label>菜单描述:</label><textarea class="editor" name="moduleDescribe" rows="10" cols="60" tools="simple">${module.moduleDescribe }</textarea></p>
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
