<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-重置交易密码</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/account.css" />
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_5_left28" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<div class="setInfo fr">
					<table class="uniceTable siteInfoWidth1">
						<tbody>
							<tr id="attul">
						        <td class="userDh" style="width: 20%;"><a onclick="javascript:;">重置交易密码</a></td>
						        <td style=" border-right:none;">&nbsp;</td>
							</tr>
						</tbody>
					</table>
					<div class="alerts">请把密码设置复杂,并认真保管好自己的密码!（字母+符号尤佳）</div>
					<div class="reg-items">
					<form action="" method="post" id="form1">
						<table class="tableLs siteInfoWidth1 tab_br ">
							<tr>
								<td class="ls">新密码：</td>
								<td>
								<input type="hidden" name="publickey" id="publickey" value="${publickey}" />
			                    <input type="password" id="newUserPaypassword" name="newUserPaypassword" class="input">
			                    <span class="tip error" id="payPwdTip" style="display:none;"><i class="icons reg-error"></i></span>
                    			</td>
							</tr>
							<tr>
								<td class="ls">确认新密码：</td>
								<td>
									<input type="hidden" name="publickey2" id="publickey2" value="${publickey}" />
				                    <input type="password" id="newUserPaypassword2" name="newUserPaypassword2" class="input">
				                    <span class="tip error" id="payPwdTip" style="display:none;"><i class="icons reg-error"></i></span>
								</td>
							</tr>
							<tr>
							<td colspan="2">
							<span class="wdzh-buttu" onclick="javascript:subForm();" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;" id="submit">提交</span>
                     		</td>
                     		</tr>
						</table>
						</form>
					</div>
					<div class="alerts">* 温馨提示：我们将严格对用户的所有资料进行保密</div>
				</div>
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">

function subForm(){
	$("#form1").validate({						  
		rules: {
			newUserPaypassword: {
				required: true,
				minlength:6,
				maxlength:16
			},
			newUserPaypassword2: {
				required: true,
				minlength:6,
				maxlength:16,
				equalTo:"#newUserPaypassword"
			}
		}
	});
	if($("#form1").valid()){
		form1.newUserPaypassword.value=encryptByDES(form1.newUserPaypassword.value,form1.publickey.value);
		form1.newUserPaypassword2.value=encryptByDES(form1.newUserPaypassword2.value,form1.publickey.value);
		$.ajax({
			type : "POST",
			url : "${path }/verify/resetPayPwd.do",
			data : $('#form1').serialize(),
			async : false,
			success : function(data) {
				alertc(data.message,reloadPage);
			}
		});
	}
	
}

function reloadPage(){
	window.location.reload(true);
}

</script>
</html>