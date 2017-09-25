<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%
String path = request.getContextPath()+"";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="attestation/o_saveSceneAttestation" onsubmit="return validateCallback(this, backRequest);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${user.id }"> 
				<input type="hidden" name="userAccount" value="${user.userAccount }"> 
				<div class="pageFormContent" layoutH="56">
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">用户名称:</label><input type="text" readonly="readonly"  value="${user.userAccount }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">真实姓名:</label><input type="text" readonly="readonly" value="${user.userRealname }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">现场认证:</label><input type="text" readonly="readonly"  value="${user.viewScene }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">通过所得积分:</label><input type="text" name="creditScore" readonly="readonly"  value="${creditType.creditScore }" />
					</p><div class="divider"></div>
						
					<p style="height:20%;width: 80%;"><label style="float:left; width:130px; padding:0 5px; line-height:21px;">备注:</label><textarea cols="30" rows="3"   name="messageContent"  class="required"></textarea>
					</p><div class="divider"></div>
					
					<div id="sceneDiv1" style="display: none;">
						
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">状态:</label>
								<input name="status"  type="radio"  value="2" />审核通过
								<input name="status"  type="radio"  checked="checked" value="1" />审核不通过
						</p>
						
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">验证码:</label>
							<input maxlength="4" style="width:60px; margin-bottom: 10px;" name="captcha" type="text" id="captcha" class="input"  />
							<img style="margin-top: -5px;" id="captchaImg" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
						</p><div class="divider"></div>
					</div>
					<div id="sceneDiv2" style="display: none;">
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">状态:</label>
								<input type="text" name="viewScene" readonly="readonly" value="${user.viewScene }"/>
						</p>
						<div class="divider"></div>
					</div>
					

				</div>
				<div class="formBar" id="saveDiv" style="display: none;">
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
				<div class="formBar" id="queryDiv" style="display: none;">
					<ul>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										确定
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//刷新验证码
	$("#captchaImg").click();
	$("input[name='captcha']").val("");
	 //当手机认证状态为申请中时，可以修改
	  var status="${user.viewScene}";
	  if(status=="申请中"){
		  $("#sceneDiv1").show();
		  $("#saveDiv").show();
	  }else{
		  $("#sceneDiv1").hide();
		  $("#sceneDiv2").show();
		  $("#queryDiv").show();
	  }
});
function backRequest(json) {
	$("#captchaImg").click();
	$("input[name='captcha']").val("");
	dialogAjaxDone(json);
}
</script>
</html>