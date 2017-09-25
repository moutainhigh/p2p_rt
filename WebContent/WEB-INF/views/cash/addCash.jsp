<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
  <%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="<%=path %>/common/js/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data"  action="cashStage/${PRE_PATH_EDIT }saveOrUpdate" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${cash.id }"> 
				
				 <div class="pageFormContent" layoutH="56">
					
					<p><label>最小金额:</label><input name="minMoney" value="${cash.minMoney }" class="required" min="100" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  type="text" alt="请输入最小金额" size="30"  /></p>
					<div class="divider"></div>
					
					<p><label>最大金额:</label><input name="maxMoney" value="${cash.maxMoney }"    min="100" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  type="text" alt="请输入最大金额" size="30"   /></p>
					<div class="divider"></div>
					
					<p><label>手续费:</label><input name="cashFee" value="${cash.cashFee }"   class="required" min="0" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  type="text" alt="请输入最大金额" size="30"   /></p>
					<div class="divider"></div>
					
					<p style="height: 60%;width: 100%;"><label>备注:</label>
						<textarea rows="15" cols="70" class="editor"  id="remark" name="remark" upImgUrl="upload/editorImg"  ></textarea>
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
</html>