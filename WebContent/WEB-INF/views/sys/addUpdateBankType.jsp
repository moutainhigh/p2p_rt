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
		<form method="post" action="sys/${PRE_PATH_EDIT }saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${allBank.id}" />
				<div class="pageFormContent" layoutH="55">
					<p><label>银行名称：</label><input type="text" size="22" name="bankName" value="${allBank.bankName }" class="required"/></p>
					<div class="divider"></div>
					<p><label>银行编码：</label><input type="text" size="22" name="bankCode" id="bankCode" value="${allBank.bankCode }" class="required" onblur="uniqueCode()"/><span id="msg1" style="color:red;"></span></p>
					<div class="divider"></div>
					<p><label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：</label><input type="text" size="22" name="bankSequence" <c:if test="${not empty allBank.bankSequence }">value="${allBank.bankSequence }"</c:if> <c:if test="${ empty allBank.bankSequence }">value="100"</c:if> /></p>
					<div class="divider"></div>
					
					 <p><label>是否显示：</label>
					   <input type="radio" name="bankStatus"  value="1" <c:if test="${'0'ne allBank.bankStatus }">checked="checked" </c:if>/>显示
					   <input type="radio" name="bankStatus"  value="0" <c:if test="${'0'eq allBank.bankStatus }">checked="checked" </c:if>/>隐藏
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
</div>
</body>
<script type="text/javascript">
	function uniqueCode(){
		var url="<%=path %>${ADMIN_URL }/sys/checkChannelCode.do";
		$.ajax({
			  type: "post",
			  data: {"allBankId":$("#id").val(),"bankCode" : $("#bankCode").val()},
			  url: url,
			  async:false,
			  success:function (ret){
				  if(ret == 'fail'){
					  $("#msg1").text( $("#bankCode").val()+"编码已存在!");
					  $("#bankCode").val(null);
					  return false;
				  }else{
				  }
			  }} );
	} 
</script>
</html>