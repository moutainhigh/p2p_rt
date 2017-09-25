<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
		<c:if test="${code==1}">
			<form id="frm" method="post" enctype=""
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" /> <input
					type="hidden" name="userId" value="${userId}" /> <input
					type="hidden" name="type" value="update" />
				<div class="pageFormContent" layoutH="56">
					${requestScope.outGroupHtml}</div>
				<div class="formBar">
					<ul>
						<li>
							<p>
								<input type="checkbox" name="checkItems" id="checkItems" />全选/反选
							</p>
						</li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="backSelected();">确定</button>
								</div>
							</div></li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" id="buttonClose" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</c:if>
		<c:if test="${code==0}">
		${message}
	</c:if>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#checkItems").click(function() {
			if ($("#checkItems").attr("checked") == "checked")
				$("div .ckbox").removeClass("unchecked").addClass("checked");
			else
				$("div .ckbox").removeClass("checked").addClass("unchecked");

		});
	});
	function backSelected() {
		var selectedId = "";
		var selectedName = "";
		$("div .checked").each(function() {
			selectedId += $(this).children("input").attr("value") + ",";
			selectedName += $(this).children("input").attr("text") + ",";
		});
		selectedId = selectedId.substring(0, selectedId.length - 1);//除，
		selectedName = selectedName.substring(0, selectedName.length - 1);
		getSelectedUser(selectedId, selectedName);//调用
		$("#buttonClose").click();//关闭
	}
</script>
</html>
