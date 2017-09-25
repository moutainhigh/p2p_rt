<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EducationMessage</title>
<script type="text/javascript" src="../../js/user/city.js"></script>

</head>
<body >
	<div class="pageContent">
		<form id="frm" method="post"
			action="userDetail/o_saveEducationMessage"
			onsubmit="return validateCallback(this,dialogAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="right_id" value="${right_id}" /> <input
				type="hidden" name="userId" id="id" value="${userId }"> <input
				type="hidden" name="id" id="id" value="${edu.id }">
			<div class="pageFormContent" style="height: 376px;">
				<p>
					<label
						style="float: left; width: 120px; padding: 0 5px; line-height: 21px;">最高学历:</label>
					<select name="maxEducation" id="maxEducation" style="width: auto">
						<option value="0"
							<c:if test="${not empty edu.maxEducation and 0 eq edu.maxEducation}">selected="selected"</c:if>>小学</option>
						<option value="1"
							<c:if test="${not empty edu.maxEducation and 1 eq edu.maxEducation}">selected="selected"</c:if>>初中</option>
						<option value="2"
							<c:if test="${not empty edu.maxEducation and 2 eq edu.maxEducation}">selected="selected"</c:if>>高中</option>
						<option value="3"
							<c:if test="${not empty edu.maxEducation and 3 eq edu.maxEducation}">selected="selected"</c:if>>中专</option>
						<option value="4"
							<c:if test="${not empty edu.maxEducation and 4 eq edu.maxEducation}">selected="selected"</c:if>>专科</option>
						<option value="5"
							<c:if test="${not empty edu.maxEducation and 5 eq edu.maxEducation}">selected="selected"</c:if>>本科</option>
						<option value="6"
							<c:if test="${not empty edu.maxEducation and 6 eq edu.maxEducation}">selected="selected"</c:if>>硕士</option>
						<option value="7"
							<c:if test="${not empty edu.maxEducation and 7 eq edu.maxEducation}">selected="selected"</c:if>>博士</option>
						<option value="8"
							<c:if test="${not empty edu.maxEducation and 8 eq edu.maxEducation}">selected="selected"</c:if>>博士后</option>
						<option value="9"
							<c:if test="${not empty edu.maxEducation and 9 eq edu.maxEducation}">selected="selected"</c:if>>其他</option>
					</select>
				</p>
				<div class="divider"></div>

				<p>
					<label
						style="float: left; width: 120px; padding: 0 5px; line-height: 21px;">最高学历学校:</label><input
						type="text" class="required" maxlength="100" name="college"
						value="" />
				</p>
				<div class="divider"></div>

				<p>
					<label
						style="float: left; width: 120px; padding: 0 5px; line-height: 21px;">专业:</label><input
						type="text" class="required" maxlength="100" name="major"
						value="${edu.major }" />
				</p>
				<div class="divider"></div>

				<p>
					<label
						style="float: left; width: 120px; padding: 0 5px; line-height: 21px;">时间:</label>
					<input type="text" class="date textInput readonly required"
						datefmt="yyyy-MM-dd" readonly="readonly" name="datebegin"
						value="<fmt:formatDate value="${edu.dateFrom }" pattern="yyyy-MM-dd"/>" />
					<label
						style="float: left; width: 10px; padding: 0 5px; line-height: 21px;">到</label>
					<input type="text" class="date textInput readonly required"
						datefmt="yyyy-MM-dd" readonly="readonly" name="dateend"
						value="<fmt:formatDate value="${edu.dateTo }" pattern="yyyy-MM-dd"/>" />
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
					<!-- <li>
						<div class="button">
							<div class="buttonContent">
								<button  type="button" class="close">取消</button>
							</div>
						</div>
					</li> -->
				</ul>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">

</script>

</html>

