<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加积分类型</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="credit/${PRE_PATH_EDIT }saveCreditRank"  name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${creditRank.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>等级名称:</label><input name="rankName" class="required" type="text" alt="请输入等级名称" size="30"  value="${creditRank.rankName }" /></p>
					<div class="divider"></div>
					<p><label>等级:</label><input name="rankValue" class="required" type="text" alt="请输入等级" size="30" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${creditRank.rankValue }" /></p>
					<div class="divider"></div>
					<p><label>开始分值:</label><input name="rankMincredit" id="rankMincredit" class="required" type="text" alt="请输入开始分值" size="30"  value="${creditRank.rankMincredit }" /></p>
					<div class="divider"></div>
					<p><label>最后分值:</label><input name="rankMaxcredit" id="rankMaxcredit" class="required" type="text" alt="请输入最后分值" size="30" value="${creditRank.rankMaxcredit }" /></p>
					<div class="divider"></div>
					<p><label>备注:</label>
						<textarea rows="4" cols="27" name="rankRemarks">${creditRank.rankRemarks }</textarea>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="testNum()">
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
<script>
function testNum(){
var re =/^\-?[0-9\,]*\.?\d*$/; 
	if(!re.test(document.getElementById("rankMincredit").value)){
	   alert("请输入正确的开始分值！");
	   return false; 
	}
	if(!re.test(document.getElementById("rankMaxcredit").value)){
		   alert("请输入正确的最后分值！");
		   return false; 
		}
	$('form1').submit();
  
}
</script>
</html>
