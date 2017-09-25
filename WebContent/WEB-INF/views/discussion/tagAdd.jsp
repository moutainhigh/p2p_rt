<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
			<form method="post" action="dis/tag/saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>标签名称：</label>
						<input name="tagName" class="required"  type="text" alt="请输入标签名称" size="20"  /></span></p>
					<div class="divider"></div>
					
					<p><label>标签编码：</label>
						<input name="tagCode" id="code" class="required"  type="text" alt="请输入标签编码" size="20" onblur="checkCode(this.value)"/>&nbsp;<span id="tip" style="color:red;"></span></p>
					<div class="divider"></div>
					
					<p><label>标签排序：</label>
						<input name="tagSequence"  class="required"  type="text" alt="请输入标签排序" size="20"/></p>
					<div class="divider"></div>
					
					<p><label>是否显示:</label>
						<input name="tagIshidden"  checked="checked" type="radio" value="0" />显示&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="tagIshidden" value="1"  type="radio" />隐藏</p>
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
					url : "dis/tag/checkExist?tagCode="+code,
					success : function(data){
						if(data == "1"){
							$("#tip").text("编码已存在!");
							$("#code").val("");
						}else{
							$("#tip").text("");
						}
					},
				});
			};
		</script>