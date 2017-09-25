<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="sys/${PRE_PATH_EDIT }saveSysLetterTemplates"  onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="id" value="${sysLetterTemplates.id}"/>
				
				<div class="pageFormContent" layoutH="55">
					<div style="margin-left: 15%;">
					<div style="color: red;">#userAccount#:用户名&nbsp;&nbsp;&nbsp;&nbsp;#url#：链接地址</div>
						
				</div>
					<p style="height: 100%;width: 100%;"><label>注册邮件:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysRegisteredMailinfo" >${sysLetterTemplates.sysRegisteredMailinfo}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>审核邮件:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysVerifyMailinfo" >${sysLetterTemplates.sysVerifyMailinfo}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>找回登录密码邮件:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysPasswordMailinfo" >${sysLetterTemplates.sysPasswordMailinfo}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>找回支付密码邮件:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysPaypwdMailinfo" >${sysLetterTemplates.sysPaypwdMailinfo}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>修改手机号码邮件:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysUpdataphone">${sysLetterTemplates.sysUpdataphone}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>短信:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysSmsInfo" >${sysLetterTemplates.sysSmsInfo}</textarea></p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>站内信:</label>
						<textarea cols="110" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysLetter" >${sysLetterTemplates.sysLetter}</textarea></p>
					<div class="divider"></div>
					
					<p>
						<label>开启短信：</label>
						<c:if test="${sysLetterTemplates.sysSmsIsopen == null || sysLetterTemplates.sysSmsIsopen == 1}">
							<input type="radio" name="sysSmsIsopen" value="1" checked="checked" />启用
							<input type="radio" name="sysSmsIsopen" value="2"/> 停用
						</c:if>
						<c:if test="${sysLetterTemplates.sysSmsIsopen == 2}">
							<input type="radio" name="sysSmsIsopen" value="1"/>启用
							<input type="radio" name="sysSmsIsopen" value="2" checked="checked" /> 停用
						</c:if>
					</p>
					<div class="divider"></div>
					
					<p>
						<label>开启邮箱：</label>
						<c:if test="${sysLetterTemplates.sysMailIsopen == null || sysLetterTemplates.sysMailIsopen == 1}">
							<input type="radio" name="sysMailIsopen" value="1" checked="checked" />启用
							<input type="radio" name="sysMailIsopen" value="2"/> 停用
						</c:if>
						<c:if test="${sysLetterTemplates.sysMailIsopen ==  2}">
							<input type="radio" name="sysMailIsopen" value="1" />启用
							<input type="radio" name="sysMailIsopen" value="2" checked="checked"/> 停用
						</c:if>
					</p>
					<div class="divider"></div>
					
					<p>
						<label>开启站内信：</label>
						<c:if test="${sysLetterTemplates.sysLetterIsopen == null || sysLetterTemplates.sysLetterIsopen == 1}">
							<input type="radio" name="sysLetterIsopen" value="1" checked="checked" />启用
							<input type="radio" name="sysLetterIsopen" value="2"/> 停用
						</c:if>
						<c:if test="${sysLetterTemplates.sysLetterIsopen == 2}">
							<input type="radio" name="sysLetterIsopen" value="1"/>启用
							<input type="radio" name="sysLetterIsopen" value="2" checked="checked" /> 停用
						</c:if>
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
</body>
</html>