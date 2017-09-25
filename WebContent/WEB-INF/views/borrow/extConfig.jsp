<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>标基本信息修改</title>
<script type="text/javascript">
$(function(){
	//重置P元素的高度
	$("p[id^='p_id_']").each(function(){
		
		var itemId = $(this).attr("id").replace("p_id_","");
		var inputField = $("[name^='id_"+itemId+"']");
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
	<form method="post" action="extConfig/${PRE_PATH_EDIT }saveExtConfig"  onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
		<input type="hidden" name="borrowId" value="${firstBorrowId}" />
		<input type="hidden" name="right_id" value="${right_id}"/>
		<div class="pageFormContent" layoutH="55">
			 
			 <p><label id="p_id_">借款标题:</label>${borrowInfo.borrowTitle}</p>
			 <div class="divider"></div>
			 <c:forEach items="${extFieldList}" var="efl">
			 	<c:if test="${not empty efl.fFldDesc and efl.fFldType == 'textarea'}">
				 <div style="margin-left: 10%;">
					<div style="color: red;">${efl.fFldDesc }</div>
				 </div>
				</c:if>
				
				<p id="p_id_${efl.fid}"><label >${efl.fFldName}：</label>
				<c:choose>
					<c:when test="${efl.fFldType == 'textarea' }">
					<textarea ${efl.fStyleCode}  name="id_${efl.fid }_fValuew">${efl.vValuew}</textarea>
					</c:when>
					<c:otherwise>
					<input type="text" ${efl.fStyleCode}  name="id_${efl.fid }_fValue"  value="${efl.vValue }" />
					<span style="float:left; width:640px; padding:0 5px; line-height:21px;">${efl.fFldDesc }</span>
					</c:otherwise>
				</c:choose>
				
				</p>
				<div class="divider"></div>
				<input type="hidden" name="fFldInTable" value="${efl.fFldInTable}"/>
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
				<li>
					<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
		
</body>
</html>