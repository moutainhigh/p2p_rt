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
		<title>添加用户类型</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="user/${PRE_PATH_EDIT }saveUserType" name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${userType.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>类型名称:</label><input name="name" class="required" type="text" alt="请输入类型名称" size="30"  value="${userType.name }" /></p>
					<div class="divider"></div>
					<p><label>排序:</label><input name="sort" class="required" type="text" alt="请输入序列" size="30"  value="${userType.sort }" /></p>
					<!-- <div class="divider"></div>
					<p><label>类型:</label>
						<input type="radio" name="type" value="0" <c:if test="${userType.type==0}">checked="checked"</c:if>>后台
						<input type="radio" name="type" value="1" <c:if test="${userType.type==1 || userType.type==null}">checked="checked"</c:if> />前台
					 --><input type="hidden" name="type" value="1" /><div class="divider"></div>
					<p><label>状态:</label>
						<input type="radio" name="status" value="1" <c:if test="${userType.status==1 || userType.status==null }">checked="checked"</c:if>>开启
						<input type="radio" name="status" value="2" <c:if test="${userType.status==2 }">checked="checked"</c:if> />关闭
					<div class="divider"></div>
					<p style="height: 150px;"><label>简要说明:</label>
						<textarea rows="6" cols="45" style="max-width: 290px; max-height: 150px" name="briefly">${userType.briefly }</textarea>
					</p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="6" cols="45" style="max-width: 290px; max-height: 150px" name="remark">${userType.remark }</textarea>
					</p>
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
