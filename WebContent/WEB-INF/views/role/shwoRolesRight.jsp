<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
</script>

<div class="pageContent">
	<c:if test="${code==1}">
		<form method="post" action="role/v_showRoleModuleList" 
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="role_id" value="${role_id}" />
				<input type="hidden" name="type" value="update" />
			<div class="pageFormContent" layoutH="56">
				${requestScope.outGroupHtml}
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button  type="submit">保存</button></div></div></li>
					<li>
						<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
					</li>
				</ul>
			</div>
		</form>
	</c:if>
	<c:if test="${code==0}">
		${message}
	</c:if>
</div>
