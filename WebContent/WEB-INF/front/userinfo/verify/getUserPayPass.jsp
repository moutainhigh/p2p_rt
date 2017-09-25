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
								<td id="loanTab1" style="width: 20%;"><a href="javascript:tabChange(1);">修改登陆密码</a></td>
						        <td id="loanTab2" style="width: 20%;"><a href="javascript:tabChange(2);">修改交易密码</a></td>
						        <td id="loanTab3" class="userDh" style="width: 20%;"><a href="javascript:tabChange(3);">重置交易密码</a></td>
						        <td style=" border-right:none;">&nbsp;</td>
							</tr>
						</tbody>
					</table>
					<div class="alerts">基于对您账户信息的检测，我们提供以下方式供您重置交易密码：</div>
					<div class="model-box reset-passwd">
			            <ul>
			            	
			                <li>
			                    <h2>通过提示邮件找回密码</h2>
			                    <p>您可以通过提示邮件中的提示找回支付密码，点击“立即找回”按钮发送提示邮件。</p>
			                    <span class="wdzh-buttu" id="getPayPwd" onclick="javascript:;" style="cursor: pointer;">立即找回</span>
			                     </li>
			                <!-- <li>
			                    <h2>通过人工服务找回密码</h2>
			                    <p>您可以通过邮件将您的信息发送至 <a href="mailto:2038393255@qq.com" target="_blank">2038393255@qq.com</a> 申请重置支付密码(处理时限为1个工作日)，请您在邮件中提供：姓名、用户名、身份证正反面照片或影印件(需注明"仅供${CACHE_SYS_CONFIG.sysWebsitename}重置支付密码使用")</p></li> -->
			            </ul>
			        </div>
			        <div class="alerts">注：如有更多问题请联系客服！</div>
				</div>
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>

</body>

<script type="text/javascript">

function tabChange(obj){
	if(obj==1){
		window.location.href="${path }/verify/saveUserLoginPass.html";
	}else if(obj==2){
		window.location.href="${path }/verify/saveUserPayPass.do";
	}else if(obj==3){
		window.location.href="${path }/verify/getUserPayPass.do";
	}
}

$('#getPayPwd').click(function (){
	$.post("getPayPwd.do","",function (data){
	    alertc(data.msg,reloadPage);	  
	   });
});
function reloadPage(){
	window.location.reload(true);
}
</script>
</html>