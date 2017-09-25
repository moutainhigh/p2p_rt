<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<title>${showTitle }</title>
</head>
<body>
  <!-- 头部 -->
<jsp:include page="/top.do"></jsp:include>
 <!---忘记密码star--->
        <div class="J-forgetpassword nav-con jboxsize jborder">
         <h2>密码找回</h2>
         <ol>
          <li>1 填写账号信息</li>
          <li>2 验证账户信息</li>
          <li class="J-current">3 重置密码</li>
          <li style="border:none;">4 成功</li>
          <div class="jclear"></div>
         </ol>
           <form name="changePasswordForm" id="changePasswordForm" action="${path }/changePassword4.do" method="post"> 
         <ul>
          <li>
            <div class="J-fg-list clearfix J-fg-yzzh">
               <p>输入新密码</p>
               <input type="password" id="userPassword" name="userPassword" onblur="checkPassword();" placeholder="6-16个字符，字母/数字/符号组合" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt text-overflow J-fg-ml" id="userPasswordTip"><!-- 密码不符合要求 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix J-fg-yzzh">
               <p>确认密码</p>
               <input type="password" placeholder="确认密码" id="passwordr"  name="passwordr" onblur="checkPwdRepeat();" style="width:70%;"/>
              </div>
              <p class="rci_prompt text-overflow J-fg-ml" id="passwordrTip"><!-- 密码错误 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix J-fg-yzzh">
               <p>&nbsp;</p>
               <div class="J-btn jfl jmt25" style="width:70%;"><a href="javascript:;" onclick="resetPassword();" style="width:100%;">下一步</a></div>
                  <input type="hidden" name="publickey" id="publickey" value="${publickey}" />
		         <input type="hidden" name="setPasswordType" id="setPasswordType" value="setPasswordType" />
		         <input type="hidden" name="userId" id="userId" value="${userId}" />
		         <input type="hidden" name="userPhone" id="userPhone" value="${userPhone}" />
              </div>
          </li>
         </ul>
         </form>
        </div>
	 <!-- 底部 -->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script type="text/javascript">

	var msg="${message}";
	if(msg){
		alert(msg);
	}
	
	function resetPassword(){
		if(checkPassword()==true &&checkPwdRepeat()==true){
			//修改密码时，密码框里的显示值
			changePasswordForm.userPassword.value=encryptByDES(changePasswordForm.userPassword.value,changePasswordForm.publickey.value);
			changePasswordForm.passwordr.value=encryptByDES(changePasswordForm.passwordr.value,changePasswordForm.publickey.value);
			$("#changePasswordForm").submit();
		}
	}
	
	
	//密码
   function checkPassword() {
		var passwordPartten = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;///^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
		if ($("#userPassword").val() == "") {
			//alertc("密码不能为空");
			$("#userPasswordTip").text("密码不能为空").css('color', '#F7772D;');
			return false;
		} else if (passwordPartten.test($("#userPassword").val()) == false) {
			$("#userPasswordTip").text("请输入6-16位字符").css('color', '#F7772D;');
			return false;
		} else {
			$("#userPasswordTip").text("ok").css('color', '#F7772D;');
			return true;
		}
	}

	//确认密码
	function checkPwdRepeat() {
		if ($("#passwordr").val() == "") {
			//alertc("字段不能为空");
			$("#passwordrTip").text("不能为空").css('color', '#F7772D;');
			return false;
		} else if ($("#userPassword").val() != $("#passwordr").val()) {
			//alertc("密码不一致，请确认");
			$("#passwordrTip").text("密码不一致，请确认").css('color', '#F7772D;');
			return false;
		} else {
			$("#passwordrTip").text("ok").css('color', '#F7772D;');
			return true;
		}
	}
</script>
</html>