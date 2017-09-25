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
          <li class="J-current">2 验证账户信息</li>
          <li>3 重置密码</li>
          <li style="border:none;">4 成功</li>
          <div class="jclear"></div>
         </ol>
           <form name="form2" id="form2" action="${path }/resetPassw3.do" method="post">
         <ul>
          <li>
            <div class="J-fg-list clearfix J-fg-yzzh" style="position:relative;">
               <p>绑定手机号码</p>
               <input type="text" placeholder="手机号码" id="userPhone" name="userPhone" onblur="checkUserPhone();" class="cyyj_in_hover"/>
               <i>或者<a href="${path}/toByEmail.do">使用绑定邮箱找回</a></i>
              </div>
              <p class="rci_prompt text-overflow J-fg-ml" id="userPhoneTip"><!-- 手机号码错误 --></p>
          </li>
          
          <li>
            <div class="J-fg-list clearfix">
               <p style="width:29%;">验证码</p>
               <input type="text" id="smsCode" name="smsCode" onblur="validateVerifyCode();" placeholder="验证码" style="width:40%; margin-right:5px;"/>
               <a href="javascript:void(0);" class="J-regconl-ta" id="sendCode" onclick="sendMsg();">点击获取验证码</a>
              </div>
              <p class="rci_prompt text-overflow J-fg-ml" id="duanxinTip"><!-- 验证码错误 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix">
               <p style="width:29%;">&nbsp;</p>
               <div class="J-btn jfl jmt25" style="width:71%;"><a href="javascript:void(0);" onclick="nextTwo();" style="width:100%;">下一步</a></div>
                <input type="hidden" name="userId" value="${userId}" id="uId">
                <input type="hidden" name="setPasswordType" id="setPasswordType" value="${resetPasswordType }" />
                <input type="hidden" name="userAccount" id="userAccount" value="${user.userAccount }" />
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

function nextTwo(){
	if(phone==true&&codeStatus==true){
		$("#form2").submit();
	}
}

//手机号
function checkPhone() {
	var phonePartten = /^1[3|4|5|8|7][0-9]\d{8}$/;
	if ($("#userPhone").val() == "") {
		$("#userPhoneTip").text("手机号不能为空").css('color','#F7772D;');
		//$("#userEmail").focus();
		return false;

	} else if (phonePartten.test($("#userPhone").val()) == false) {
		$("#userPhoneTip").text("手机号格式有误").css('color','#F7772D;');
		//$("#userEmail").focus();
		return false;
	} else {
		$("#userPhoneTip").text("ok").css('color','#F7772D;');
		return true;
	}
}


var phone;
function checkUserPhone() {
	if (!checkPhone()) {
		return;
	}
	var url = "${path}/checkUserPhone2.do";
	$.ajax({
				type : "post",
				data : {
					"userPhone" : $("#userPhone").val(),
					"userAccount" : $("#userAccount").val()
				},
				url : url,
				async : false,
				success : function(ret) {
					if (ret.code == '200') {
						$("#userPhoneTip").text("");
						if (checkPhone() == false) {
							phone = false;
							return phone;
						} else {
							$("#userPhoneTip").text('ok');
							phone = true;
							return phone;
						}
					} else {
						$("#userPhoneTip").text(ret.msg);
						phone = false;
						return phone;
					}
				}
			});
}



var wait=60;
function sendMsg() {
	 if (wait != 60) {
		return;
	} 
	 if (!phone)
		alertc("您的手机号输入有误，请重新输入");
	else { 
		 var telNo = $.trim($("#userPhone").val());
		if (!telNo) {
			alertc("请先输入您的手机号码");
			return;
		} 
		var url="${path }/sendSms.do";
		$.ajax({
					type : "post",
					url : url,
					data : {
						"tel" : $("#userPhone").val()
					},
					success : function(ret) {
						if (ret.code == '200') {
							/* $("#duanxinTip").text("验证码已发送，请输入").css(
									'color', 'red'); */
							alertc(ret.msg);
							time();
						} else if (ret.code == '899') {
							alertc("短信已发送,请在1分钟后在点击发送");
							time();
						}else if(ret.code=='300'){
							alertc(ret.msg);
						}
					},
				});
	/* } */
}

function time() {
	if (wait == 0) {
		//$("#sendCode").attr("disabled", false);
		$("#sendCode").text("获取短信验证码");
		//$("#sendCode").removeClass("dis");
		$("#sendCode").attr("onclick", "sendMsg()");
		wait = 60;
	} else {
		//$("#sendCode").attr("disabled", true);
		//$("#sendCode").addClass("dis");
		$("#sendCode").attr("onclick", null);
		$("#sendCode").text(wait + "秒后重新发送");
		wait--;
		setTimeout(function() {
			time()
		}, 1000)
	}
 }
}

//验证短信验证码
var codeStatus = false;
function validateVerifyCode() {
	if (!checkPhone()) {
		return;
	}
	var smsCode = $("#smsCode").val();
	if (smsCode =='') {
		alertc("短信验证码不能为空！");
		return;
	}
	var url = "${path}/checkSmsCode.do";
	$.ajax({
		type : "post",
		data : {
			"tel" : $("#userPhone").val(),
			"smsCode" : smsCode
		},
		url : url,
		async : false,
		success : function(ret) {
			if (ret.code == '200') {
				$("#duanxinTip").text("ok").css('color','#F7772D;');
				codeStatus = true;
				return;
			} else if (ret.code == '302') {
				$("#duanxinTip").text("验证码错误!").css('color', '#F7772D;');
				codeStatus = false;
				return;
			} else if (ret.code == "101") {
				//alert("验证码已失效，请重新获取验证码");
				$("#duanxinTip").text("验证码输入错误!").css('color', '#F7772D;');
				codeStatus = false;
				return;
			}
		}
	});
}
</script>
</html>