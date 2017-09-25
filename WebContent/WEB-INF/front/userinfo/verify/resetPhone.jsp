<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-修改手机号</title>
<style type="text/css">
.user_right_border {
	font-size: 13px;
	height: auto;
	line-height: 25px;
	margin: 10px 0;
	overflow: hidden;
	text-align: left;
	width: 100%;
}

.user_right_border .e {
	float: left;
	padding: 4px 5px 0 0;
	text-align: right;
	width: 220px;
}
</style>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="banner-ny"
		style="background:url(${frontPath}/images/ny/wyjk-lx_02.jpg) no-repeat center;"></div>
	<div class="Position">
		<span><img src="${frontPath}/images/lx-jk_03.jpg" width="18"
			height="24" /></span><a href="${path }/index.html"><span>首页</span></a><span>&gt;</span><a
			href="${path }/account/accountIndex.html"><span>用户中心</span></a> <span>&gt;</span><a
			href="javascript:;"><span>修改手机号</span></a>
	</div>
	<div style="margin-bottom: 30px; margin-top: 30px;" class="nrbao">
		<div class="myct-aqbz-left">
			<jsp:include page="/account/userLeft.do"></jsp:include>
			<!--/sidebar-->
			<!--main-->
			<div class="clearfix main_wrapper">
				<div class="container">
					<div class="clearfix fluid mb_10">
						<div class="module">
							<div class="tab_u">
							 <a
									href="javascript:;"><span
									style="padding: 0px 18px;" class="active">修改手机号</span></a>
							</div>
						</div>
					</div>
				</div>
				<div style="text-align: center; margin-top: 30px; color: red"
					id="tip-text" class="user_main_title"></div>

				<div style="" id="divs">
					 <form action="" method="post" id="form1">
						<div class="user_right_border">
							<div class="e"><em style="color: red;">*</em>手机号码：</div>
							<div class="c">
								<input type="text" id="phoneNum" name="phoneNum" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${user.userPhone }" onblur="checkUserPhone();" class="input" >
							</div>
						</div>
						<div class="user_right_border">
							<div class="e"><em style="color: red;">*</em>验证码：</div>
							<div class="c">
								 <input type="text" id="smsCode" name="smsCode" class="input"  maxlength="6">
							</div>
						</div>
						<div class="user_right_border">
							<div class="e"></div>
							<div class="c">
								<input type="button" id="realNameAuthenBt"
									onclick="validateVerifyCode();" class="btn btnSize_1 btn_blue"
									size="30" value="短信验证" name="name">
							</div>
						</div>
					</form>
				</div>
				<div style="color: #f00;" class="user_right_foot">* 若您收不到短信或手机号码已停止使用，可联系客服： 申请人工更换绑定手机。</div>
				<div id="s"></div>
			</div>
		</div>
	</div>
	<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>

</body>

<script type="text/javascript">
	var userTel;
	function checkUserPhone() {
		var url = "${path}/verify/checkUserPhone.do";
		$.ajax({
			type : "post",
			data : {
				"userPhone" : $("#phoneNum").val()
			},
			url : url,
			async : false,
			success : function(ret) {
				if (ret.code == '200') {
					if (checkTel() == false) {
						userTel = false;
						return userTel;
					} else {
						userTel = true;
						return userTel;
					}
				} else if (ret.code == "101") {
					$("#phoneNum").focus();
					userTel = false;
					return userTel;
				} else {
					alertc("该手机号码已存在，请重新输入");
					$("#phoneNum").focus();
					userTel = false;
					return userTel;
				}
			}
		});
	}

	function checkTel() {
		var pattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if ($("#phoneNum").val() == "") {
			alertc("请输入手机号");
			$("#phoneNum").focus();
			return false;
		} else if (!pattern.test($("#phoneNum").val())) {
			alertc("请输入合法手机号");
			$("#phoneNum").focus();
			return false;
		}
		return true;
	}
	/* 1分钟后重新加载 */
	function reload() {
		$('#getCode').removeAttr("disabled");
	}
	function validateVerifyCode() {
		if ($("#phoneNum").val() == "") {
			alertc("请输入手机号");
			$("#phoneNum").focus();
			return false;
		}
		if (userTel != true) {
			return;
		}
		var smsCode = $("#smsCode").val();
		if (smsCode == '') {
			alertc("请输入验证码");
			$("#smsCode").focus();
			return;
		}
		var url = "${path}/verify/user/checkSmsCode.do";
		$.ajax({
			type : "post",
			data : {
				"tel" : $("#phoneNum").val(),
				"smsCode" : smsCode
			},
			url : url,
			async : false,
			success : function(ret) {
				if (ret.code == '200') {
					alertc("手机号修改成功", reloadPage());
				} else if (ret.code == '301') {
					alertc("验证码输入错误", reloadPage());
					$("#smsCode").focus();
				} else if (ret.code == "101") {
					alertc("验证码已失效，请重新获取验证码", reloadPage());
					$("#smsCode").focus();
				} else {
					alertc("操作失败，请尝试人工操作");
					setTimeout(reloadPage,3000);
				}
			}
		});
	}
	$(function() {
		changeCurrent();
	})

	function changeCurrent() {
		var current = $("#menu").find(".current");
		$(current).removeClass("current");
		$("#userAttestation").addClass("current");
	}
	function reloadPage() {
		window.location.href = "${path }/verify/userAttestation.do";
	}
</script>
</html>