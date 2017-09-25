<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	//重置P元素的高度
	$("p[id^='p_id_']").each(function(){
		
		var itemId = $(this).attr("id").replace("p_id_","");
		var inputField = $("[name='id_"+itemId+"']");
		$(this).height(inputField.height());
		
		if(inputField.is("textarea") && parseInt(inputField.attr("cols")) > 100 ){
			//cols大于100以后会换行，所以高度+30
			$(this).height($(this).height()+30);
		}
	});
});
</script>
</head>
<body>
	<form method="post" action="cfgParam/${PRE_PATH_EDIT }saveCfgParam"  onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				
				<div class="pageFormContent" layoutH="55">
				 <c:forEach items="${ cfgList}" var="cfg">
				 <c:if test="${not empty cfg.remark and cfg.inputType == 'textarea'}">
				 <div style="margin-left: 10%;">
					<div style="color: red;">${cfg.remark }</div>
				 </div>
				 </c:if>
				 
					<p id="p_id_${cfg.id }"><label style="width: 150px;">${cfg.sysName }：</label>
					<c:choose>
						<c:when test="${cfg.inputType == 'textarea' }">
						
						<textarea ${cfg.limitCode }  name="id_${cfg.id }">${cfg.sysValueBig }</textarea>
						</c:when>
						<c:otherwise>
						<input type="text" ${cfg.limitCode }  name="id_${cfg.id }"  value="${cfg.sysValue }" />
						<span style="float:left; width:640px; padding:0 5px; line-height:21px;">${cfg.remark }</span>
						</c:otherwise>
					</c:choose>
					
					
					</p>
					<div class="divider"></div>
				</c:forEach>
							
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
						
					</ul>
				</div>
		</form>
		
</body>
</html>
