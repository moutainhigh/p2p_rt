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
				<input type="hidden" name="id"  value="${tag.id}" />
				<input type="hidden" id="oldCode" value="${tag.tagCode}" />
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>标签名称：</label>
						<input name="tagName" class="required"  type="text"  size="20" value="${tag.tagName }"  /></span></p>
					<div class="divider"></div>
					
					<p><label>标签编码：</label>
						<input name="tagCode" id="code" class="required"  type="text"  size="20" value="${tag.tagCode }" onblur="checkCode(this.value)"/><span id="msg2" style="color:red;"></span></p>
					<div class="divider"></div>
					
					<p><label>标签排序：</label>
						<input name="tagSequence"  class="required"  type="text" size="20" value="${tag.tagSequence }"/></p>
					<div class="divider"></div>
					
					<p><label>是否显示:</label>
						<c:if test="${tag.tagIshidden eq 0 }">
							<input name="tagIshidden"  type="radio" value="0" checked="checked"/>显示&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="tagIshidden" type="radio"   value="1" />隐藏</p>
						</c:if>
						<c:if test="${tag.tagIshidden eq 1 }">
							<input name="tagIshidden"  type="radio" value="1"  checked="checked" />隐藏&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="tagIshidden" value="0"  type="radio" />显示</p>
						</c:if>
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
				var oldCode = $("#oldCode").val();
				var newCode = $("#code").val();
			//alert(oldCode+"---"+newCode);
			if($.trim(newCode) != '' && newCode != oldCode){
				$.ajax({
					type : "post",
					url : "dis/tag/checkExist?tagCode="+code,
					success : function(data){
						if(data == "1"){
							  $("#msg2").text("编码已存在!");
							  $("#code").val("");
						}else{
							$("#msg2").text("");
						}
					},
				});
			 }
			}
			/* function checkCode(code){
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
			}; */
		</script>