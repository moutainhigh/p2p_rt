<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
			<form method="post" action="dic/nation/saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>民族名称：</label>
						<input name="nationName" class="required"  type="text" alt="请输入民族名称" size="20"  /></span></p>
					<div class="divider"></div>
					
					<p><label>民族编码：</label>
						<input name="nationCode"  class="required"  type="text" alt="请输入民族编码" size="20"  onblur="checkCode(this.value)"/><span id="msg2" style="color:red;"></span></p>
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
		<script type="text/javascript">
			function checkCode(code){
				$.ajax({
					type : "post",
					url : "dic/nation/checkExist?nationCode="+code,
					success : function(data){
						if(data == "false"){
							  $("#msg2").text("编码已存在!");
							return false;
						}else{
							$("#msg2").text("");
						}
					},
				});
			};
		</script>