<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<form id="frm" method="post" action="userDetail/${PRE_PATH_EDIT}saveOtherMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="id" value="${userId }"> 
				<input type="hidden" name="id" id="id" value="${other.id }">  
				<div class="pageFormContent" style="height: 376px;">
					<p style="width: 80%;height: 28%"><label style="float:left; width:120px; padding:0 5px; line-height:21px;">个人能力：</label>
					<textarea rows="4" cols="50"  maxlength="1000" style="max-height: 110px; max-width: 450px;" name="personalAbility">${other.personalAbility }</textarea>
					（如电脑能力、组织协调能力或其他） </p>
					<div class="divider"></div>
					
					<p style="width: 80%;height: 28%"><label style="float:left; width:120px; padding:0 5px; line-height:21px;">个人爱好：</label>
					<textarea rows="4" cols="50" maxlength="1000" style="max-height: 110px; max-width: 450px;"  name="personalHobbies">${other.personalHobbies }</textarea>（突出自己的个性，工作态度或他人对自己的评价等） </p>
					<div class="divider"></div>
					
					<p style="width: 80%;height: 28%"><label style="float:left; width:120px; padding:0 5px; line-height:21px;">其他说明:</label>
					<textarea rows="4" cols="50" maxlength="1000" style="max-height: 110px; max-width: 450px;"  name="otherState">${other.otherState }</textarea>(个人能力、爱好、其他说明必填一项！)</p>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="frmSubmit();">
										保存
									</button>
								</div>
							</div>
						</li>
						<!-- <li>
							<div class="button">
								<div class="buttonContent">
									<button  type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">
function frmSubmit(){
	if($("#personalAbility").val()!=null||$("#personalHobbies").val()!=null||$("#otherState").val()!=null){
		alert($("#personalAbility").val());
		alert($("#personalHobbies").val());
		alert($("#otherState").val());
		$("#frm").submit();
	}else{
		alert("个人能力、爱好、其他说明必填一项！");
	}
	
}
</script>
</html>