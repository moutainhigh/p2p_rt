<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BorrowCollectionDetail</title>
	<script type="text/javascript" src="<%=path %>/common/js/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post"  action="borrowCollection/o_saveBorrowCollection?userId=${pm.userId}" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<div class="pageFormContent" layoutH="56">
				<div class="divider"></div>	
				
					<p><label>借款人:</label><input  value="${pm.userAccount }" readonly="readonly"/></p>
					<div class="divider"></div>
					
					<p><label>真实姓名:</label><input  value="${pm.userRealName }" readonly="readonly"/></p>
					<div class="divider"></div>
					
					<p><label>借款ID:</label><input name="borrowId" value="${pm.borrowId }" readonly="readonly"/></p>
					<div class="divider"></div>
					
					<p><lable>催收方式:</lable>
					<c:forEach var="item" items="${BORROW_BC_STATUS}">
					<input type="radio" id="collectionType" name="collectionType" value="${item.key}">${item.value}</input>
					</c:forEach>
					</p>
					<div class="divider"></div>
					
					<p><label>催收时间:</label>
					<input type="text" name="collectionTime" id="collectionTime" value="${searchParams.beginTime}" class="required date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</p>
					<div class="divider"></div>
					
					<p><label>催收人:</label><input type="text" name="collectionUser" id="collectionUser"/></p>
					<div class="divider"></div>
					
					<p style="height:80px;"><label >结果概要:</label>
					<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="collectionResult"></textarea>
					</p>
					<div class="divider"></div>
					
					<p style="height:80px;"><label>备注:</label>
					<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="collectionRemark"></textarea>
					</p>					
					<div class="divider"></div>
				</div>
				<script type="text/javascript">
				document.getElementById("collectionType").checked="checked";
				</script>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
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

</html>