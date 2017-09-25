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
		<title>添加用户</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="sys/${PRE_PATH_EDIT }saveNoticeType"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="noticeTypeId" value="${noticeType.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>名称:</label><input name="noticeName" class="required" type="text" value="${noticeType.noticeName}" /></p>
					<div class="divider"></div>
					<c:if test="${noticeType.id != null}">
						<p><label>编码:</label><input name="noticeCode" readonly="readonly" class="required" type="text"  value="${noticeType.noticeCode}" /></p>
					</c:if>
					<c:if test="${noticeType.id == null}">
						<p><label>编码:</label><input name="noticeCode" class="required" type="text"  value="${noticeType.noticeCode}" /></p>
					</c:if>
					<div class="divider"></div>
					<p><label>排序:</label><input name="noticeSequence" class="digits" type="text"  value="${noticeType.noticeSequence}" /></p>
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
