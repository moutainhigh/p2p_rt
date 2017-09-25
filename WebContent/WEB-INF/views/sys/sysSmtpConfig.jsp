<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="sys/${PRE_PATH_EDIT }saveSysSmtpConfig"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="id" value="${sysSmtpConfig.id}"/>
				<div class="pageFormContent" layoutH="55">
					<p><label>SMTP服务器</label><input type="text" size="30" name="sysSmtpServer"  maxlength="50" value="${sysSmtpConfig.sysSmtpServer }" /></p>
					<div class="divider"></div>
					
					<p><label>服务器是否需要验证</label>
					<c:if test="${sysSmtpConfig.id==null}">
						<input type="radio" name="sysSmtpNeedauth" checked="checked" value="0" />是
						<input type="radio" name="sysSmtpNeedauth" value="1" />否
					</c:if>
					<c:if test="${sysSmtpConfig.id!=null}">
						<!-- 1为是，2为否 -->
						<c:if test="${sysSmtpConfig.sysSmtpNeedauth=='0' }">
							<input type="radio" name="sysSmtpNeedauth" checked="checked" value="0" />是
							<input type="radio" name="sysSmtpNeedauth" value="1" />否
						</c:if>
						<c:if test="${sysSmtpConfig.sysSmtpNeedauth=='1' }">
							<input type="radio" name="sysSmtpNeedauth"  value="0" />是
							<input type="radio" name="sysSmtpNeedauth" checked="checked" value="1" />否
						</c:if>
					</c:if>
					
					</p>
					<div class="divider"></div>
					
					<p><label>邮箱地址</label><input type="text" size="30" maxlength="30" class="email" name="sysMailAddress" alt="请输入邮箱地址" value="${sysSmtpConfig.sysMailAddress }" /></p>
					<div class="divider"></div>
					
					<p><label>邮箱密码</label><input type="password" size="30" minlength="6" maxlength="20" class="required" alt="请输入邮箱密码"  name="sysMailPassword" value="${sysSmtpConfig.sysMailPassword }" /></p>
					<div class="divider"></div>
					
					<p><label>发件人昵称</label><input type="text" size="30" maxlength="30" name="sysMailSender" alt="请输入发件人昵称" value="${sysSmtpConfig.sysMailSender }"/></p>
					<div class="divider"></div>
					
					<p><label>发件人姓名</label><input type="text" size="30"  maxlength="30" name="sysMailName" alt="请输入发件人姓名" value="${sysSmtpConfig.sysMailName }"  /></p>
					<div class="divider"></div>
					
					<p><label>邮件端口</label><input type="text" size="30" maxlength="11" class="digits" name="sysSmtpPort" alt="请输入邮件端口" value="${sysSmtpConfig.sysSmtpPort }"  /></p>
					<div class="divider"></div>
					
					<p><label>是否开启ssl认证</label>
						<c:if test="${sysSmtpConfig.sysSslAuthentication=='1' }">
							<input type="radio" name="sysSslAuthentication" checked="checked" value="1" />是
							<input type="radio" name="sysSslAuthentication" value="0" />否
						</c:if>
						<c:if test="${sysSmtpConfig.sysSslAuthentication=='0' }">
							<input type="radio" name="sysSslAuthentication"  value="1" />是
							<input type="radio" name="sysSslAuthentication" checked="checked" value="0" />否
						</c:if>
						
					</p>
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
</body>
</html>