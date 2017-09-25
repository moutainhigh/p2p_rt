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
          <li class="J-current">1 填写账号信息</li>
          <li>2 验证账户信息</li>
          <li>3 重置密码</li>
          <li style="border:none;">4 成功</li>
          <div class="jclear"></div>
         </ol>
         <form action="${path}/resetPassword.do" method="post" id="resetForm" name="resetForm">
         <ul>
          <li>
            <div class="J-fg-list clearfix">
               <p>账户</p>
               <input type="text" id="userAccount" name="userAccount" onblur="checkUserAccount();" placeholder="用户名" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt text-overflow J-fg-textindent" id="userAccountTip"><!-- 用户名错误 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix">
               <p>验证码</p>
               <input type="text" id="captcha" name="captcha" onblur="checkVerificationCode();" placeholder="验证码" style="width:50%; margin-right:5px;"/>
               <span><img onclick="this.src='${path}/captcha.svl?d='+new Date()*1" src="${path }/captcha.svl" id="imagg"/></span>
              </div>
              <p class="rci_prompt text-overflow J-fg-textindent" id="captchaTip"><!-- 验证码错误 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix">
               <p>&nbsp;</p>
               <div class="J-btn jfl jmt25" style="width:80%;"><a onclick="nextOne();" style="width:100%;">下一步</a></div>
                 <input type="hidden" name="setPasswordType" id="setPasswordType"/>
              </div>
          </li>
         </ul>
         </form>
        </div>
	<!-- 尾部 -->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script type="text/javascript">

var msg="${message}";
if(msg){
	alert(msg);
}

function nextOne(){
	if(account == true&&captchaflag==true){
		$("#resetForm").submit();
	}else{
		if(account != true){
			$("#userAccount").val("");
			alert("请重新输入用户名！");
			return;
		}
	}
}


var account ;
function checkUserAccount(){
	var url="${path}/checkUserAccount.do";
	$.ajax({
		  type: "post",
		  data: {"userAccount":$("#userAccount").val()},
		  url: url,
		  async:false,
		  success:function (ret){
			  if(ret.code == '200'){
				  if(checkAccount() == false){
					  account = false;
					  return account;
					}else{
						$("#userAccountTip").text("该用户名不存在，请重新输入");
						  $("#userAccount").focus();
						  account = false;
						  return account;
					}
			  }else{
				  $("#userAccountTip").text("ok");
					account = true;
					return account;
			  }
		  }} );
}

var email ;
function checkUserEmail(){
	if(!checkEmail()){
		return;
	}
	var url="${path}/checkUserEmail.do";
	$.ajax({
		  type: "post",
		  data: {"userEmail":$("#userEmail").val()},
		  url: url,
		  async:false,
		  success:function (ret){
			  if(ret.code == '200'){
				  if(checkEmail() == false){
					  email = false;
					  return email;
				  }else{
					  $("#userEmailTip").text("该邮箱不存在，请重新输入").css('color','red');
					  $("#userEmail").focus();
					  email = false;
					  return email; 
				  }
			  }else{
				  $("#userEmailTip").text("");
				  email = true;
				  return email;
			  }
		  }} );
}

function checkAccount(){
	var accountPartten = /^[a-zA-Z0-9]{4,15}$/;
	if($("#userAccount").val()==""){
		$("#userAccountTip").text("用户名不能为空");
		$("#userAccount").focus();
		return false;
	}else if(accountPartten.test($("#userAccount").val()) == false){
		$("#userAccountTip").text("请输入4-15位字符.英文,数字");
		$("#userAccount").focus();
		return false;
	}else{
		$("#userAccount").text("ok");
		return true;
	}
}

function checkEmail(){
	var emailPartten = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if($("#userEmail").val()==""){
		$("#userEmailTip").text("邮箱不能为空").css('color','red');
		$("#userEmail").focus();
		return false;
	}else if(emailPartten.test($("#userEmail").val()) == false){
		$("#userEmailTip").text("邮箱输入有误").css('color','red');
		$("#userEmail").focus();
		return false;
	}else{
		return true;
	}
}


function checkCaptcha(){
	if($("#captcha").val() == ""){
		$("#captchaTip").text("验证码不能为空");
		return false;
	}else{
		$("#captchaTip").text("ok");
		return true;
	}
}

//验证验证码
var captchaflag=false;
function checkVerificationCode() {
	var captcha = $.trim($("#captcha").val());
	if(checkCaptcha()){
		$.ajax({
			type : "post",
			url : "${path }/checkVerificationCode.do",
			data : {
				"captcha" : captcha
			},
			dataType : "json",
			success : function(ret) {
				if (ret.code != '200') {
					flag = 1;
					$("#captchaTip").text("验证码不正确");
					//刷新验证码
					fresh();
					captchaflag = false;
					return captchaflag;
				} else {
					flag = 0;
					$("#captchaTip").text("ok");
					captchaflag = true;
					return captchaflag;
				}
			}});
	}

}

//刷新验证码
function fresh() {
	$("#imagg").attr("src", "${path}/captcha.svl?d=" + new Date() * 1);
} 

</script>
</html>
