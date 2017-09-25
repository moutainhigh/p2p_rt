<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加、修改银行设置</title>
</head>
<body>
  <div class="pageContent">
		<form method="post" action="shop/${PRE_PATH_EDIT }saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${shopCategory.id}" />
				<div class="pageFormContent" layoutH="55">
					<p><label>名称：</label><input type="text" size="22" name="categoryName" value="${shopCategory.categoryName }" class="required"/></p>
					<div class="divider"></div>
					<p><label>编码：</label><input type="text" size="22" name="categoryCode" id="categoryCode" value="${shopCategory.categoryCode }" class="required" onblur="uniqueCode()"/><span id="msg1" style="color:red;"></span></p>
					<div class="divider"></div>
					<p><label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：</label><input type="text" size="22" name="orderBy" <c:if test="${not empty shopCategory.orderBy }">value="${shopCategory.orderBy }"</c:if> <c:if test="${ empty shopCategory.orderBy }">value="100"</c:if> /></p>
					<div class="divider"></div>
					 <p><label>是否显示：</label>
					   <input type="radio" name="isEnable"  value="1" <c:if test="${'0'ne shopCategory.isEnable }">checked="checked" </c:if>/>显示
					   <input type="radio" name="isEnable"  value="0" <c:if test="${'0'eq shopCategory.isEnable }">checked="checked" </c:if>/>隐藏
					 </p>
					<div class="divider"></div>
					<p>
						<label>备注： </label>
						<textarea rows="4" cols="40">${shopCategory.remark} </textarea>
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
<script type="text/javascript">
$(function(){
	//if($)
});
	function uniqueCode(){
		$("#msg1").text("");
		var url="<%=path %>${ADMIN_URL }/shop/checkChannelCode.do";
		$.ajax({
			  type: "post",
			  data: {"shopCategoryId":$("#id").val(),"categoryCode" : $("#categoryCode").val()},
			  url: url,
			  async:false,
			  success:function (ret){
				  if(ret == 'fail'){
					  $("#msg1").text( $("#categoryCode").val()+"编码已存在!");
					  $("#categoryCode").val(null);
					  return false;
				  }else{
				  }
			  }} );
	} 
</script>
</html>