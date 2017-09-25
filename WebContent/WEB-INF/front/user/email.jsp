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
 <div class="J-forgetpassword nav-con jboxsize jborder">
         <h2>密码找回</h2>
         <ol>
          <li>1 填写账号信息</li>
          <li class="J-current">2 验证账户信息</li>
          <li>3 重置密码</li>
          <li style="border:none;">4 成功</li>
          <div class="jclear"></div>
         </ol>
         <form id="formButton">
         <ul>
          <li>
          <!--  <div class="J-fg-list clearfix J-fg-yzzh" style="position:relative;">
               <p>用户名</p>
               <input type="text" id="userAccount" name="userAccount" onblur="checkUserAccount();" placeholder="用户名" class="cyyj_in_hover"/>
              </div>
               <p class="rci_prompt text-overflow J-fg-ml" id="userAccountTip">用户名不正确</p>
              <br/> -->
            <div class="J-fg-list clearfix J-fg-yzzh" style="position:relative;">
               <p>邮箱地址</p>
               <input type="text" id="userEmail" name="userEmail" onblur="checkEmail();" placeholder="请您输入邮箱地址" class="cyyj_in_hover"/> 
              </div>
              <p class="rci_prompt text-overflow J-fg-ml" id="userEmailTip"><!-- 邮箱地址不正确 --></p>
          </li>
          <li>
            <div class="J-fg-list clearfix">
               <p style="width:29%;">&nbsp;</p>
               <div class="J-btn jfl jmt25" style="width:71%;"><a onclick="goToEmail();" style="width:100%;">下一步</a></div>
                 <input type="hidden" name="userId" value="${userId}" id="uId">
                <input type="hidden" name="setPasswordType" id="setPasswordType"/>
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
var account;
function checkUserAccount() {
	var url = "${path}/checkUserAccount2.do";
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
			/* 	$("#userAccountTip").text("该用户名已存在，请重新输入").css('color',
						'red'); */
				$("#userAccount").focus();
				account = false;
				return account;
			}
		}
	});
}

function checkAccount() {
	//var accountPartten = /^[a-zA-Z0-9]{4,15}$/;
	var accountPartten= /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
	if ($("#userAccount").val() == "") {
		$("#userAccountTip").text("用户名不能为空").css('color', '#F7772D;');
		$("#userAccount").focus();
		return false;
	} else if (accountPartten.test($("#userAccount").val()) == false) {
		$("#userAccountTip").text("用户名书写不正确,请输入6-16位字符.英文,数字").css('color', '#F7772D;');
		$("#userAccount").focus();
		return false;
	} else {
		$("#userAccountTip").text("ok").css('color', '#F7772D;');
		return true;
	}
}


var flag = true;
function checkEmail(){
	   var emailPartten=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/ ;
	    if($("#userEmail").val()==''){
	    	$("#userEmailTip").text("邮箱不能为空").css('color','red');
	    	flag=false;
	    	return flag;
	    }else if(!emailPartten.test($("#userEmail").val())){
	    	$("#userEmailTip").text("请输入正确的邮箱").css('color','red');
	    	flag=false;
	    	return flag;
	    }else{
	    	$("#userEmailTip").text("ok").css('color','red');
	    }
}
/**
 * 发送邮件
 */
function goToEmail(){
	//alertc("fffff");
	 checkEmail();
	   if(flag){
		   $.ajax({
		          dataType: 'json',
		            url:'${path}/reSendEmais.do',
		            data:$("#formButton").serialize(),
		            type: 'POST',
		            success: function(data) {
		            	alertc(data.msg);
		            },
		            error: function() {
		            		alertc("发送邮件失败！！！");
		            }
		        });     
	   }
}
</script>
</html>