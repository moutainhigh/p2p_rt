<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-密码设置</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/account.css" />
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
<%-- <div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
	<div class="setInfo fr">
					<table class="uniceTable siteInfoWidth1">
						<tbody>
							<tr id="attul">
								<td id="loanTab1" class="userDh" style="width: 20%;"><a href="javascript:tabChange(1);">修改登陆密码</a></td>
						        <td id="loanTab2" style="width: 20%;"><a href="javascript:tabChange(2);">修改交易密码</a></td>
						        <td id="loanTab3" style="width: 20%;"><a href="javascript:tabChange(3);">重置交易密码</a></td>
						        <td style=" border-right:none;">&nbsp;</td>
							</tr>
						</tbody>
					</table>
					<div class="alerts">请定期修改密码，6-16个字符，建议使用字母加数字或符号的组合密码</div>
					<div class="reg-items">
					<form action="" method="post" id="form1">
						<table class="tableLs siteInfoWidth1 tab_br ">
							<tr>
								<td class="ls">当前密码：</td>
								<td>
								<input type="password" id="userPassword" name="userPassword" class="input" value="" <c:if test="">readonly="readonly"</c:if> />
                    				<span class="tip error" id="mailTip" style="display:none;"><i class="icons reg-error"></i></span>
                    			</td>
							</tr>
							<tr>
								<td class="ls">新密码：</td>
								<td>
									<input type="hidden" name="publickey" id="publickey" value="${publickey}" />
				                    <input type="password" id="newUserPassword" name="newUserPassword" class="input">
				                    <span class="tip error" id="payPwdTip" style="display:none;"><i class="icons reg-error"></i></span>
								</td>
							</tr>
							<tr>
								<td class="ls">确认新密码：</td>
								<td>
									<input type="password" id="newUserPassword2" name="newUserPassword2" class="input">
	                    			<span class="tip error" id="payPwdTip" style="display:none;"><i class="icons reg-error"></i></span>
								</td>
							</tr>
							<tr>
							<td colspan="2">
							<span class="wdzh-buttu" onclick="javascript:subForm();" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">提交</span>
                     		</td>
                     		</tr>
						</table>
						</form>
					</div>
					<div class="alerts">* 温馨提示：我们将严格对用户的所有资料进行保密</div>
				</div>
    </div>
  </div>	
    
</div> --%>

<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
		<input type="hidden" value="left10" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>

  </div>

  <div class="J-ma-conR mm_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);" <c:if test="${type==null }">class="tap"</c:if> >登录密码</a>
      <a href="javascript:void(0);" <c:if test="${type!=null }">class="tap"</c:if>>支付密码</a>
    </div>
    <div class="mm_txt">
      <div class="code_num" id="newPwd">
        <form action="" method="post" id="form1">
        <div class="enter">
          <div class="input"><i>旧密码</i><input type="password" id="userPassword" name="userPassword"  value="" <c:if test="">readonly="readonly"</c:if> /></div>
          <div class="input"><i>新密码</i><input type="hidden" name="publickey" id="publickey" value="${publickey}" />
				                    <input type="password" id="newUserPassword" name="newUserPassword" ></div>
          <div class="input"><i>确认新密码</i><input type="password" id="newUserPassword2" name="newUserPassword2"></div>
          <a onclick="javascript:subForm();" class="md_btn">确认修改</a>
        </div>
        </form>
        <div class="tip">
          <h5>温馨提示：</h5>
            <ul>
              <li>1、密码要求设置6-16个字符，建议使用字母加数字或符号的组合密码；</li>
              <li>2、初始支付密码默认为登录密码，为确保账户安全，请将支付密码修改为与登录密码不一致。</li>
            </ul>
          </div><!-- tip -->        
      </div><!-- code_num -->

      <div class="code_num" style="display:none;" id="newPayPwd">
         <form action="" method="post" id="form2">
        <div class="enter">
         <!--  <div class="input"><i>旧密码</i><input type="password" id="userPaypassword"
								name="userPaypassword"  value=""
								<c:if test="">readonly="readonly"</c:if> onblur="chekEmailfor()"><span style="margin-left:10px; font-size:12px; color:#f00;">注意：始密码与登录密码保持一致</span></div> -->
          <div class="input"><i>新密码</i><input type="hidden" name="publickey" id="publickey" value="${publickey}" /> 
									<input type="password" id="newUserPaypassword" name="newUserPaypassword" ></div>
          <div class="input"><i>确认新密码</i><input type="password" id="newUserPaypassword2" name="newUserPaypassword2" ></div>
          <div class="input"><i>验证码</i><input type="text" id="smsCode" name="smsCode"  style="width:16%; margin-right:7px; float:left;" ><a  onclick="sendMsg()" id="sendCode" class="J-updatePwd-ta">获取验证码</a></div>
          
          <a onclick="javascript:subForm2();" class="md_btn">确认修改</a>
        </div>
        </form>
        <div class="tip">
          <h5>温馨提示：</h5>
            <ul>
               <li>1、密码要求设置6-16个字符，建议使用字母加数字或符号的组合密码；</li>
              <li>2、初始支付密码默认为登录密码，为确保账户安全，请将支付密码修改为与登录密码不一致。</li>
            </ul>
        </div><!-- tip -->        
      </div><!-- code_num -->      
    </div><!-- mm_txt -->
    
  </div><!-- J-ma-conR -->
 </div>
</div>
<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		var type="${type}";
		if(type != null && type != "" ){
			$("#newPayPwd").show();
			$("#newPwd").hide();
		}
		
	})
</script>
<script type="text/javascript">


function subForm(){
	$("#form1").validate({						  
		rules: {
			userPassword: {
				required: true
			},
			newUserPassword: {
				required: true,
				minlength:6,
				maxlength:16
			},
			newUserPassword2: {
				required: true,
				minlength:6,
				maxlength:16,
				equalTo:"#newUserPassword"
			}
		}
	});
	
	if($("#form1").valid()){
		form1.userPassword.value=encryptByDES(form1.userPassword.value,form1.publickey.value);
		form1.newUserPassword.value=encryptByDES(form1.newUserPassword.value,form1.publickey.value);
		form1.newUserPassword2.value=encryptByDES(form1.newUserPassword2.value,form1.publickey.value);
		$.ajax({
			type : "POST",
			url : "${path }/verify/requestSaveUserLoginPass.do",
			data : $('#form1').serialize(),
			async : false,
			success : function(data) {
				alertc(data.message,reloadPage);
			}
		});
	}
	
}

/*修改支付密码  */
function subForm2() {
		$("#form2").validate({
			rules : {
				newUserPaypassword : {
					required : true,
					minlength : 6,
					maxlength : 16
				},
				newUserPaypassword2 : {
					required : true,
					minlength : 6,
					maxlength : 16,
					equalTo : "#newUserPaypassword"
				}
			}
		});
		
		
		
		if ($("#form2").valid()) {

			validateVerifyCode();
			if(!codeStatus){
				return false;
			} 
			
			form2.newUserPaypassword.value = encryptByDES(
					form2.newUserPaypassword.value, form2.publickey.value);
			form2.newUserPaypassword2.value = encryptByDES(
					form2.newUserPaypassword2.value, form2.publickey.value);
			$.ajax({
				type : "POST",
				url : "${path }/verify/requestSaveUserPayPass.do",
				data : $('#form2').serialize(),
				async : false,
				success : function(data) {
					alertc(data.message, reloadPage);
				}
			});
		}

	}

var userPhone="${FRONT_USER_SESSION.userPhone}";
var codeStatus = false;
function validateVerifyCode() {
	var smsCode = $("#smsCode").val();
	if (smsCode == '') {
		alertc("短信验证码不能为空！");
		return;
	}
	var url = "${path}/checkSmsCode.do";
	$.ajax({
		type : "post",
		data : {
			"tel" : userPhone,
			"smsCode" : smsCode
		},
		url : url,
		async : false,
		success : function(ret) {
			if (ret.code == '200') {
				codeStatus = true;
				return;
			} else if (ret.code == '302') {
				alertc("验证码错误!");
				codeStatus = false;
				return;
			} else if (ret.code == "101") {
				alertc("验证码输入错误!");
				codeStatus = false;
				return;
			}
		}
	});
}
var wait=60;
function sendMsg() {
	if (wait != 60) {
		return;
	} 
	var url="${path }/sendSms.do";
	$.ajax({
		type : "post",
		url : url,
		data : {
			"tel" : userPhone
		},
		success : function(ret) {
			if (ret.code == '200') {
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
}

function time() {
	if (wait == 0) {
		$("#sendCode").text("获取短信验证码");
		$("#sendCode").attr("onclick", "sendMsg()");
		wait = 60;
	} else {
		$("#sendCode").attr("onclick", null);
		$("#sendCode").text(wait + "秒后重新发送");
		wait--;
		setTimeout(function() {
			time()
		}, 1000)
	}
}

function reloadPage(){
	window.location.reload(true);
}
</script>
</html>