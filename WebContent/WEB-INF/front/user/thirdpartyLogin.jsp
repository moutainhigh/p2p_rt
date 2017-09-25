<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script charset="utf-8" src="${frontPath }/js/jquery.dialog.js"></script>
<style type="text/css">
a{
cursor: pointer;
}
</style>
</head>
<body>
<!--头部-->
<div id="header">
	<jsp:include page="/top.do"></jsp:include>

  <div class="nav_sx"><img src="${frontPath}/images/ny/biao_02.jpg" width="1920" height="3" /></div>
</div>
<!--广告--><!--内容-->
<!--最新公告-->
<div id="content" style="background-color:#f2f2f2;" ><!--实时财务-->
  <div id="hkct-tz" style="background-color:#f2f2f2;">
  <div  class="wydk-xqb" style="background-color:#fff; border:1px solid #ccc; margin-top:40px;"> 
<div class="zc-nrb" style="height:300px;">
    <form name="form1" id="form1" action="${path}/register.do" method="post">
     <input type="hidden" value="${accessToken }" id="accessToken" name="accessToken"/>
	<input type="hidden" value="${uid }" id="uid" name="uid"/>
	<input type="hidden" value="${isThirdparty }" id="isThirdparty" name="isThirdparty"/>
      <div class="zc-left">
      <i style="font-style:normal; font-size:18px; font-weight:bold; color:#ee2121; line-height:35px;">个人注册</i>
        <div class="zc-nr">
        <ul class="zc-b">
    
        <li>
          <div class="zc-kuang">
          <div class="zc-tub"><img src="${frontPath}/images/ny/zc_03.jpg" width="22" height="16" /></div>
          <div class="zc-sr">
          <label for="textfield"></label>
          <input style="color:#666;" id="userEmail" name="userEmail" onblur="checkUserEmail();" type="text"  placeholder="请输入邮箱" size="29" /></div>
          </div>
        </li>
         <li id="zc-tis"><span style="color:red;">*</span>&nbsp;<span id="userEmailTip">请输入正确的常用邮箱地址</span></li>
        </ul>
        </div>
        <div class="zc-nr">
          <ul class="zc-b" style="width: 628px;">
         
            <li>
              <div class="zc-kuang">
                <div class="zc-tub"><img src="${frontPath}/images/ny/zc_10.jpg" width="21" height="19" /></div>
                <div class="zc-sr">
                  <label for="textfield2"></label>
                  <input type="text" id="userAccount" name="userAccount" onblur="checkUserAccount();" style=" color:#666;" placeholder="请输入用户名" size="16" />
                </div>
              </div>
            </li>
            <li id="zc-tis"><span style="color:red;">*</span>&nbsp;<span id="userAccountTip">请输入4-16位字符,可包含英文字母,数字</span></li>
          </ul>
        </div>
        <div class="zc-nr">
          <ul class="zc-b" style="width: 628px;">
           
            <li>
              <div class="zc-kuang">
                <div class="zc-tub"><img src="${frontPath}/images/ny/zc_15.jpg" width="20" height="21" /></div>
                <div class="zc-sr">
                  <label for="textfield3"></label>
                  <input type="hidden" name="publickey" id="publickey" value="${publickey}" />
                  <input placeholder="请输入密码" style="color:#666;"  type="password" id="userPassword" name="userPassword"  onBlur="checkPassword();"  maxlength="16" />
                </div>
              </div>
            </li>
            <li id="zc-tis"><span style="color:#F7772D;">*</span>&nbsp;<span id="userPasswordTip" >请输入6-16位密码,可包含英文字母,数字,符号</span></li>
           
          </ul>
        </div>
        <div class="zc-nr">
          <ul class="zc-b" style="width: 628px;">
          
            <li>
              <div class="zc-kuang">
                <div class="zc-tub"><img src="${frontPath}/images/ny/zc_18.jpg" width="22" height="22" /></div>
                <div class="zc-sr">
                  <label for="textfield6"></label>
                  <input style="color:#666;" placeholder="请确认密码" type="password" id="passwordr" name="passwordr"  onBlur="checkPwdRepeat();"  maxlength="16"/>
                </div>
              </div>
            </li>
            <li id="zc-tis"><span style="color:#F7772D;">*</span>&nbsp;<span id="passwordrTip">请重复输入上面密码</span></li>
          </ul>
        </div>
        <div style=" margin-top:0px;" class="zc-nr">
          <ul class="zc-b" style="width: 628px;">
            <li style="margin-top:2px; "><input type="checkbox" id="protocol" name="protocol" checked="checked"></li>       
            <li id="zc-tis"> 自觉遵守《<a id="read" href="javascript:;" onclick="readRegisterProtocol();">${CACHE_SYS_CONFIG.sysWebsitename}注册协议</a>》            
            </li> 
            <li><div id="reglaw" style="display: none;width: 550px;height: 300px;overflow:scroll;" >${registerProtocol}</div></li>
             <li >  <a id="loginBtn" style="color:#fff;" onclick="register()"><div style=" margin-left:20px;" class="xq-anniou">注册</div></a> </li>         
          </ul>
        </div>
      </div>
      		<input type="hidden" name="registerType"  value="registerType" /> 
       			  <input type="hidden" name="inviteUserid" value="${param.u} " />
		</form>
        <div class="zc-xian"></div>
          <div class="yhzc-rightdiv">
        <img width="200" height="55" style="margin-top: -70px;padding-bottom: 30px;" src="${frontPath }/images/logo.png">
            <div class="zc-mydiv">已有${CACHE_SYS_CONFIG.sysWebsitename}账号？<span id="zc"><a style="color:#ee2121; font-size:14px; text-decoration:underline;" href="${path }/login">直接登录</a></span></div>
          </div>
  </div>
    </div>
</div>
</div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</div>
</body>
<script type="text/javascript">
	
document.onkeydown=function(event){
	e = event ? event : (window.event ? window.event : null);
	if(e.keyCode==13){
		$('#loginBtn').trigger('click');
	}
};
	function readRegisterProtocol() {
		$('#reglaw').dialog({
			title : $('#read').html()
		});
	}

	var msg = "${message}";
	if (msg) {
		alert(msg);
	}

	function checkAgreement() {
		if (document.getElementById("protocol").checked == false) {
			document.getElementById("protocol").focus();
			alert("请同意注册协议");
			return false;
		}
		return true;
	}

	function register() {
		if (email == true  && account == true
				&& checkPassword() && checkPwdRepeat() && checkAgreement()) {
			form1.userPassword.value = encryptByDES(form1.userPassword.value,
					form1.publickey.value);
			$("#form1").submit();

		} else {
			if (email != true) {
				$("#userEmail").val("");
				alert("请重新输入电子邮箱！");
				return;
			}
			if (account != true) {
				$("#userAccount").val("");
				alert("请重新输入用户名！");
				return;
			}
		}
	}

	var account;
	function checkUserAccount() {
		var url = "${path}/checkUserAccount.do";
		$.ajax({
			type : "post",
			data : {
				"userAccount" : $("#userAccount").val()
			},
			url : url,
			async : false,
			success : function(ret) {
				if (ret.code == '200') {
					$("#userAccountTip").text("");
					if (checkAccount() == false) {
						account = false;
						return account;
					} else {
						account = true;
						return account;
					}
				} else {
					$("#userAccountTip").text("该用户名已存在，请重新输入").css('color','#F7772D;');
					$("#userAccount").focus();
					account = false;
					return account;
				}
			}
		});
	}

	var email;
	function checkUserEmail() {
		if (!checkEmail()) {
			return;
		}
		var url = "${path}/checkUserEmail.do";
		$
				.ajax({
					type : "post",
					data : {
						"userEmail" : $("#userEmail").val()
					},
					url : url,
					async : false,
					success : function(ret) {
						if (ret.code == '200') {
							$("#userEmailTip").text("");
							if (checkEmail() == false) {
								email = false;
								return email;
							} else {
								email = true;
								return email;
							}
						} else {
							$("#userEmailTip").text("该邮箱已存在，请重新输入").css(
									'color', '#F7772D;');
							$("#userEmail").focus();
							email = false;
							return email;
						}
					}
				});
	}

	function checkAccount() {
		var accountPartten = /^[a-zA-Z0-9]{4,15}$/;
		if ($("#userAccount").val() == "") {
			$("#userAccountTip").text("用户名不能为空").css('color', '#F7772D;');
			$("#userAccount").focus();
			return false;
		} else if (accountPartten.test($("#userAccount").val()) == false) {
			$("#userAccountTip").text("请输入4-15位字符.英文,数字").css('color', '#F7772D;');
			$("#userAccount").focus();
			return false;
		} else {
			$("#userAccountTip").text("ok").css('color', '#F7772D;');
			return true;
		}
	}

	function checkPassword() {
		var passwordPartten = /^[\s|\S]{6,}$/;///^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
		if ($("#userPassword").val() == "") {
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

	function checkPwdRepeat() {
		if ($("#passwordr").val() == "") {
			$("#passwordrTip").text("不能为空").css('color', '#F7772D;');
			return false;
		} else if ($("#userPassword").val() != $("#passwordr").val()) {
			$("#passwordrTip").text("密码不一致，请确认").css('color', '#F7772D;');
			return false;
		} else {
			$("#passwordrTip").text("ok").css('color', '#F7772D;');
			return true;
		}
	}

	function checkEmail() {
		var emailPartten = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if ($("#userEmail").val() == "") {
			$("#userEmailTip").text("邮箱不能为空").css('color', '#F7772D;');
			$("#userEmail").focus();
			return false;
		} else if (emailPartten.test($("#userEmail").val()) == false) {
			$("#userEmailTip").text("邮箱输入有误").css('color', '#F7772D;');
			$("#userEmail").focus();
			return false;
		} else {
			$("#userEmailTip").text("ok").css('color', '#F7772D;');
			return true;
		}
	}
</script>
</html>
