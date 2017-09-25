<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	        <c:set var="curNav" value="title3"></c:set>
           <%@include file="titleNav.jsp"%>
	        <div id="phone" class="model-box" >
		        <div class="form-opt">
			       <form action="" method="POST" id="formPhone">
			       <div class="alerts" id="showMsg">
				            <c:if test="${user.phoneStatus==-2 }">绑定手机有利于我们及时通知您在${CACHE_SYS_CONFIG.sysWebsitename}网的交易情况以及最新动态。</c:if>
				            <c:if test="${user.phoneStatus==-1 }">您的手机正在认证，请等待结果。</c:if>
				            <c:if test="${user.phoneStatus==1 }">您的手机认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.phoneStatus==2 }">恭喜您，您的手机认证已经通过。</c:if>
				            </div>
		            <ul class="items">
		                <li class="txt"><em>*</em>当前手机号码：</li>
		                <li style="width:350px;">
		                	<input type="text" id="phoneNum"  name="phoneNum" value="${user.userPhone }" class="input" style="width:100px;margin-top: 6px;">
		                	
		                	<c:choose>
		                		<c:when test="${empty user.userPhone || user.phoneStatus==1 || user.phoneStatus==-2}">
				                	 <span class="wdzh-buttu" id="phoneSub" onclick="phoneCheck();" style="cursor: pointer;margin-left: 120px;margin-top: 3px;">人工审核</span>
		                		</c:when>
		                	</c:choose>
		                	 <%-- <c:if test="${not empty user.userPhone && user.phoneStatus==2}">
				                	 <span class="wdzh-buttu" id="savePhone"  title="输入新手机号即可修改" style="cursor: pointer;margin-left: 120px;margin-top: 3px;">修改号码</span>
				                	 <!-- <span class="wdzh-buttu" id="getCode" onclick="getUpdatePhoneCode();" title="输入新手机号即可修改" style="cursor: pointer;margin-left: 230px;margin-top: -42px;">获取验证码</span> -->
				            </c:if>  --%>
		                </li>
		            </ul>
		         <c:choose>
		                <c:when test="${empty user.userPhone || user.phoneStatus==1 || user.phoneStatus==-2}">
			            <ul class="items" id="smsVerifyCode">
			                <li class="txt"><em>*</em>验证码：</li>
			                <li style="width:350px;">
			                    <input type="text" id="smsCode" name="smsCode" class="input" style="width:100px;margin-top: 6px;" maxlength="6">
			                    <span class="wdzh-buttu" id="getCode" onclick="getVerifyCode();" style="cursor: pointer;margin-left: 120px;margin-top: 3px;">获取验证码</span>
			                    <span class="tip error" id="codeTip">
			                    </span> 
			                    <div class="light-gray" id="phoneCodeInfos" style="clear:both"></div>
			                    </li>
			            </ul>
			            <div class="btn-submit">
			            	<span class="wdzh-buttu" onclick="validateVerifyCode();" style="cursor: pointer;margin-top: 3px;">短信验证</span>
						</div>
						</c:when>
	                </c:choose> 
		            
		            <div class="alerts2" >若您收不到短信或手机号码已停止使用，可联系客服 申请人工更换绑定手机。</div>
		        </form>
		        </div>
	        </div>
	   
    </div>
    </div>
    </div>
    </div>
    <!--/main--> 
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">

function checkTel(){
	var pattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	 if($("#phoneNum").val()==""){
		    alertc("请输入手机号");
			$("#phoneNum").focus();
			return false;
	}
	 else if(!pattern.test($("#phoneNum").val())){
		    alertc("请输入合法手机号");
			$("#phoneNum").focus();
			return false;
	 }
	 return true;
}

var userTel;
function checkUserPhone(){
	var url="${path}/verify/checkUserPhone.do";
	$.ajax({
		  type: "post",
		  data: {"userPhone":$("#phoneNum").val()},
		  url: url,
		  async:false,
		  success:function (ret){
			  if(ret.code == '200'){

				userTel = true;
				return userTel;
			  }else if(ret.code == "101"){
				  $("#phoneNum").focus();
				  userTel = false;
				  return userTel; 
			  }else{
				  alertc("该手机号码已存在，请重新输入");
				  $("#phoneNum").focus();
				  userTel = false;
				  return userTel; 
			  }
		  }} );
}

/**
 * 获取修改手机号验证码
 */
function getUpdatePhoneCode(){
	window.location.href="${path}/verify/user/updatePhoneForCode.do?tel="+$("#phoneNum").val();
}

/**
 * 获取验证码
 */
function getVerifyCode(){
	var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	if($("#phoneNum").val()==""){
		alertc("请输入手机号码");
		$("#phone").focus();
		return;
	}else if(!workTelPattern.test($("#phoneNum").val())){
		alertc("请输入合法电话");
		$("#phone").focus();
		return;
	}
	checkUserPhone();
	if(userTel != true){
		return;
	}
	 $("#getCode").attr("disabled","disabled");
     $("#getCode").css("background","#CCCCCC");
     var url="${path }/verify/user/sendSms.do?tel="+$("#phoneNum").val();
       	$.ajax({
  		  type: "post",
  		  url: url,
  		  async:false,
  		  success:function (ret){
  				$('#phoneNum').attr("readonly","readonly");
  				if(ret.code == '200'){
  					$("#showMsg").html("验证码已短信发到您的手机上，请输入短信中的验证码进行验证");
  				}else if(ret.code == '301'){
  					$("#showMsg").html("验证码发送失败");
  				}
  		  }
  		});
  		setTimeout("reload()",60000);
       }
/* 1分钟后重新加载 */
function reload(){
	$('#getCode').removeAttr("disabled");
}
function validateVerifyCode(){
	if(!checkTel()){
		return;
	}
	var smsCode = $("#smsCode").val();
	if(smsCode == ''){
		alertc("请输入验证码");
		$("#smsCode").focus();
		return;
	}
	var url="${path}/verify/user/checkSmsCode.do";
	$.ajax({
		  type: "post",
		  data: {"tel" : $("#phoneNum").val(),"smsCode" : smsCode},
		  url: url,
		  async:false,
		  success:function (ret){
			  if(ret.code == '200'){
				  alertc("手机认证成功");
				  window.location.reload();
			  }else if(ret.code == '301'){
				  alertc("验证码或手机号输入错误");
				  $("#phoneNum").focus();
			  }else if(ret.code == '302'){
				  alertc("验证码输入错误");
				  $("#smsCode").focus();
			  }else if(ret.code == "101"){
				  alertc("验证码已失效，请重新获取验证码");
				  $("#smsCode").focus();
			  }else{
				  alertc("操作失败，请尝试人工操作");
			  }
		  }
		});
}
/* 手机号码认证 */
function phoneCheck(){
	var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	if($("#phoneNum").val()==""){
		alertc("请输入手机号码");
		$("#phone").focus();
		return;
	}else if(!workTelPattern.test($("#phoneNum").val())){
		alertc("请输入合法电话");
		$("#phone").focus();
		return;
	}
	checkUserPhone();
	if(userTel != true){
		return;
	}
	$.ajax({
      	dataType: 'json',
        url:"${path}/verify/checkPhone.do",
        data:$("#formPhone").serialize(),
        type: 'POST',
        
        success: function(data) {
       		//资料上传
       		if(data.code=='201'){
       			$("#formPhone").find(".alerts").html("您的手机正在认证，请等待结果。");
       			$("#formPhone").find("#phoneSub").hide();
       			$("#formPhone").find("#submit").hide();
       			$("#getCode").hide();
       			$("#smsVerifyCode").hide();
       			$("#validateCode").hide();
       			alertc("您的手机认证申请已提交，请等待审核人员处理。");
       		}
       		if(data.code=='202'){
       			alertc(data.msg);
       		}
       		if(data.code=='203'){
       			alertc(data.msg);
       		}
        },
        error: function() {
        		 alertc("认证申请失败！！！");
        }
    }); 
}
</script>
</html>