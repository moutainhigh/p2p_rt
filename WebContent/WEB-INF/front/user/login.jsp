<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../taglibs.jsp"%>
	<title>登录-${showTitle }</title>
	<meta name="Keywords" content="登录">
	<meta name="Description" content="浩茗金融作为互联网金融理财平台,全方位保障你的投资理财安全,为用户提供有保障的理财服务,最高年化达14%实现你的收益最大化.">
	<%
		String flag = (String) session.getAttribute("flag") == null ? "" : (String) session.getAttribute("flag");
		String name = "";
		String password = "";
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("cookie_user")) {
						String value = cookies[i].getValue();
						value = java.net.URLDecoder.decode(value, "UTF-8");
						System.out.println("value=" + value);
						if (value != null && !"".equals(value)) {
							name = value.split("-")[0];
							if (cookies[i].getValue().split("-")[1] != null
									&& !cookies[i].getValue().split("-")[1]
											.equals("null")) {
								password = value.split("-")[1];
							}
						}
					}
					request.setAttribute("name", name);
					request.setAttribute("passward", password);
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-login jmb50">
		<form action="${path}/login.do" name="loginForm" id="loginForm" method="post">
			<input type="hidden" name="loginType" value="loginType" />
			<input type="hidden" name="publickey" id="publickey" value="${publickey}" />
			<div class="J-login-con nav-con clearfix">
<%-- 		  		<div class="J-login-ewm fl"><img src="${frontPath}/images/J-login-ewm.png"/></div> --%>
		  		<div class="J-loginc-con jborder jboxsize">
		   			<h1>&nbsp;&nbsp;&nbsp;</h1>
					<ul>
			    		<li>
							<div class="J-loginc-list clearfix"><p>用户名</p><input type="text" name="userAccount" placeholder="请输入手机号/用户名" type="text" id="userAccount" value="<%=name %>"/></div>
						</li>
						<li>
		  					<div class="J-loginc-list clearfix"><p>密码</p><input  name="password" type="password" id="password" placeholder="6-16位数字和字符"/></div>
						</li>
					</ul>
					<ol class="J-loginc-fpassword clearfix">
						<li class="jfl"><input type="checkbox" value="1" name="flag" id="flag"/>记住我</li>
						<li class="jfr text-right"><a href="${path}/resetPassword">忘记密码？</a></li>
					</ol>
					<div class="J-btn jmt30" style="width:100%;"><a href="javascript:;" id="loginword" onclick="login();" style="width:100%;">确认登录</a></div>
					<p class="J-login-bottom">还没账户？<a href="${path }/register">免费注册</a></p>
				</div>
			</div>
		</form>
	</div>
	<!---登录的脚部-->
	<div class="J-reg-footer nav-con">
		<ul>
			<li><a href="${path }/about" >关于我们</a></li>
			<li><a href="${path }/security" >安全保障</a></li>
			<li style="border:none;"><a href="${path }/help" >帮助中心</a></li>
		</ul>
		<p>Copyright&copy;2014   ${websiteName}  版权所有　${websiteicp}</p>
	</div>
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script type="text/javascript">
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			login();
		}
	};

	var msg = "${message}";
	if (msg) {
		alertc(msg);
	}

    $(function(){
       var array="${flag}";
        if(array!=null && array == "1" ){
            $("#flag").attr("checked",true);
         }else{
             $("#flag").attr("checked",false);
        }
     });

	function login() {
		var passwordPartten = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
		if (loginForm.userAccount.value == "") {
			alertc("用户名不能为空。");
			return false;
		} else if (loginForm.password.value == "") {
			alertc("密码不能为空。");
			return false;
		} else if (!passwordPartten.test(loginForm.password.value)) {
			alertc("密码只能为6-16位字符。");
			return false;
		} else {
			loginForm.password.value = encryptByDES(loginForm.password.value,
					loginForm.publickey.value);
			$("#loginForm").submit();
		}
	}
</script>
</html>