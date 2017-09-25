<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
	<form method="post" action="area/saveOrUpdate"
		onsubmit="return validateCallback(this, dialogAjaxDone);"
		class="pageForm required-validate">
		<input type="hidden" name="right_id" value="${right_id}" />

		<div class="pageFormContent" layoutH="56">

			<p>
				<label>上级栏目：</label> <input name="district.id" type="hidden">
				<input class="required textInput readonly"
					name="district.districtName" type="text" readonly=""> <a
					class="btnLook" href="area/rootTree" lookupgroup="district"
					width="250">栏目</a>
			</p>
			<div class="divider"></div>

			<p>
				<label>地区名称：</label> <input name="areaName" class="required"
					type="text" alt="请输入地区名称" size="20" />
			</p>
			<div class="divider"></div>

			<p>
				<label>地区编码：</label> <input name="areaCode" class="required"
					type="text" alt="请输入地区编码" size="20" onblur="checkAreaCode(this.value)" /><span id="msg" style="color:red;"></span>
			</p>
			<div class="divider"></div>

			<p>
				<label>排&nbsp;&nbsp;&nbsp;&nbsp;序：</label> <input
					name="areaSequence" class="digits required" type="text" alt="请输入排序"
					size="10" />
			</p>
			<div class="divider"></div>

			<p>
				<label>域&nbsp;&nbsp;&nbsp;&nbsp;名：</label> <input name="areaDomain"
					type="text" alt="请输入域名" size="20" />
			</p>
			</p>
			<div class="divider"></div>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	function checkAreaCode(code) {
		$.ajax({
			type : "post",
			url : "area/checkAreaExist?areaCode=" + code,
			success : function(data) {
				if (data == "false") {
					$("#msg").text("编码已存在!");
					return false;
				}else{
					$("#msg").text(" ");
				}
			},
		});
	};
</script>
