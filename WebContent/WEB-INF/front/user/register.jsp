<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>注册-${showTitle }</title>
<meta name="Keywords"  content="注册">
<meta name="Description" content="浩茗金融作为互联网金融理财平台,全方位保障你的投资理财安全,实现你的收益最大化">
<script charset="utf-8" src="${frontPath }/js/jquery.dialog.js"></script>
<script charset="utf-8" src="${frontPath }/js/jquery.reveal.js"></script>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<style type="text/css">
a{
cursor: pointer;
}
</style>
</head>
<body>
<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
<form name="form1" id="form1" action="${path}/register.do" method="post">
  <div class="J-register jborder jboxsize" id="one">
          <div class="J-register-top"><img src="${frontPath}/images/J-zhuce01.png"/></div>
          <div class="J-reg-con clearfix" >
           <div class="J-regcon-list jfl clearfix">
            <ul>
             <li>
              <div class="J-regconl-input">
              <input type="hidden" name="registerType"  value="registerType" /> 
             <%--  <input type="hidden" name="inviteUserid" value="${param.u} " /> --%>
               <input type="hidden" name="publickey" id="publickey" value="${publickey}" />
               <input type="hidden" name="loginType" value="loginType" />
               <!-- <p>用户名</p>
               <input type="text" id="userAccount" name="userAccount" onblur="checkUserAccount();" placeholder="请输入用户名" class="cyyj_in_hover"/> -->
               <p>手机号码</p>
               <input type="text" name="userPhone" id="userPhone"  onBlur="checkUserPhone();" placeholder="手机号码" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="userPhoneTip" ></p>
             </li>
             <li>
              <div class="J-regconl-input">
               <p>密码</p>
               <input type="password" id="userPassword" name="userPassword" onBlur="checkPassword();"  maxlength="16"  placeholder="6-16位数字和字符" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="userPasswordTip"><!-- 密码不符合要求 --></p>
             </li>
             <li>
              <div class="J-regconl-input">
               <p>确认密码</p>
               <input type="password" id="passwordr" name="passwordr"  onBlur="checkPwdRepeat();"  maxlength="16" placeholder="6-16位数字和字符" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="passwordrTip"><!-- 两次密码不一致 --></p>
             </li>
             <li class="J-regconl-two">
              <div class="J-regconl-input clearfix">
               <p>验证码</p>
               <input type="text" id="smsCode" name="smsCode" style="width:28%; margin-right:7px; float:left;"/>
               <a  onclick="sendMsg()" id="sendCode" class="J-regconl-ta">获取验证码</a>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="duanXinTip"><!-- 验证码不正确 --></p>
             </li>
             <c:choose>
	             <c:when test="${'1' eq recommendStatus}">
		             <li>
			              <div class="J-regconl-input">
			               <p>邀请人</p>
			               <c:if test="${not empty inviteUserid}">
			                 <input type="text" id="inviteUserid" name="inviteUserid" value="${inviteUserid }" placeholder="邀请码(非必填)" class="cyyj_in_hover"/>
			               </c:if>
			               <c:if test="${empty inviteUserid}">
			                 <input type="text" id="inviteUserid" name="inviteUserid"    placeholder="邀请码(非必填)" class="cyyj_in_hover"/>
			                </c:if>
			              </div>
		             </li>
	             </c:when>
	             <c:otherwise>
	             	 <input type="hidden" id="inviteUserid" name="inviteUserid" placeholder="邀请码" class="cyyj_in_hover"/>
	             </c:otherwise>
             </c:choose>
             
             <!-- <li>
              <div class="J-regconl-input clearfix">
               <p>验证码</p>
               <input type="text" onblur="checkVerificationCode()" style="width:28%; margin-right:7px; float:left;" id="captcha" name="captcha"/>
               <span><img src="<%=path%>/captcha.svl" onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1" id="imagg" title="点击更换" style="cursor: pointer;"/></span>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="captchaTip"></p>
             </li> -->
             <li style="height:20px; min-height:inherit;">
              <div class="J-regconl-checkbox">
               <p>&nbsp;</p>
               <input type="checkbox" id="protocol" name="protocol" checked="checked"/>
               <b> 我已阅读，并同意 <a id="read"
							href="javascript:;" onclick="readRegisterProtocol();" class="big-link" data-reveal-id="myModal" data-animation="fade">《注册协议》</a></b>
               <div id="myModal" class="reveal-modal">
                <!--  <h1>《浩茗金融用户注册协议》</h1>-->
                 <div>${registerProtocol}</div>
                <!--   <p id="reglaw" > ${registerProtocol}

                <span style="padding-left:150px;">浩茗金融平台投资咨询与管理服务电子协议</span><br/>

　　本网站由上海融腾金融信息服务有限公司（平台名称：浩茗金融）负责运营（以下本网站均指网站及浩茗金融）。在您注册成为本网站用户前请务必仔细阅读以下条款。若您一旦注册，则视为您接受本网站的服务并受以下条款的约束。若您不接受以下条款，请停止注册本网站。

　　本使用协议内容包括以下条款及已经发布的或将来可能发布的各类规则。所有规则为协议不可分割的一部分，与协议正文具有同等法律效力。本协议是由您与本网站共同签订的，适用于您在本网站的全部活动。在您注册成为用户时，您已经阅读、理解并接受本协议的全部条款及各类规则，并承诺遵守中华人民共和国现行的法律、法规、规章及其他政府规定，如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。

　　本网站有权根据需要不时地制定、修改本协议或各类规则，如本协议及规则有任何变更，一切变更以本网站最新公布条例为准。经修订的协议、规则一经在本网站公布，即自动生效或在该等协议、规则指定的时间生效。您应不时地注意本协议及附属规则地变更，若您不同意相关变更，本网站有权不经任何告知终止、中止本协议或者限制您进入本网站的全部或者部分板块且不承担任何法律责任。但该终止、中止或限制行为并不能豁免您在本网站已经进行的交易下所应承担的义务。

　　您确认本使用协议后，本使用协议即在您和本网站之间产生法律效力。您只要在“我已阅读并且同意浩茗金融平台的使用协议及隐私条款”前的选择框里打钩并按照本网站规定的注册程序成功注册为用户，您的行为既表示同意并签署了本使用协议。本使用协议不涉及您与本网站的其他用户之间因网上交易而产生的法律关系及法律纠纷，但您在此同意将全面接受和履行与本网站其他用户在本网站签订的任何电子法律文本，并承诺按该法律文本享有和/或放弃相应的权利、承担和/或豁免相应的义务。

　　一、使用限制

　　本网站中的全部内容的著作权、版权及其他知识产权均属于本网站所有，该等内容包括但不限于文本、数据、文章、设计、源代码、软件、图片、照片及其他全部信息（以下称“网站内容”）。网站内容受中华人民共和国著作权法及各国际版权公约的保护。未经本网站事先书面同意，您承诺不以任何方式、不以任何形式复制、模仿、传播、出版、公布、展示网站内容，包括但不限于电子的、机械的、复印的、录音录像的方式和形式等。您承认网站内容是属于本网站的财产。未经本网站书面同意，您亦不得将本网站包含的资料等任何内容镜像到任何其他网站或者服务器。任何未经授权对网站内容的使用均属于违法行为，本网站将追究您的法律责任。

　　您承诺合法使用本网站提供的服务及网站内容。禁止在本网站从事任何可能违反中华人民共和国现行的法律、法规、规章和政府规范性文件的行为或者任何未经授权使用本网站的行为，如擅自进入本网站的未公开的系统、不正当的使用密码和网站的任何内容、窃取他人的账号和密码、窃取他人的个人隐私信息等。

　　本网站只接受持有中国大陆有效身份证的18周岁以上的具有完全民事行为能力的自然人成为网站用户。如您不符合资格，请勿注册，否则本网站保留随时中止或终止您用户资格的权利。您在此向本网站保证，您已符合年龄和国籍条件，如因您向本网站提供虚假信息或承诺而导致本网站或浩茗金融（上海）融腾金融信息服务有限公司蒙受任何损失，您将承担全部责任并赔偿相关损失。

　　您注册成功后，不得将本网站的用户名转让给第三方使用。您确认，您用您的用户名和密码登录本网站后在本网站的一切行为均代表您本身作出的行为并由您承担相应的法律后果。任何利用您的用户名以及密码登录本网站并从事交易等行为均将被视为您的行为。因此，您有义务非常谨慎的保管自己的用户名以及密码。本网站对您的用户名和密码的遗失或被盗取所产生的后果不承担任何责任。

　　您有义务在注册时提供自己的真实资料，并保证诸如电子邮件地址、联系电话、联系地址、邮政编码等内容的有效性及安全性。如您因网上交易与其他用户产生诉讼的，其他用户有权通过司法部门要求网站提供相关资料。

　　经国家生效法律文书或行政处罚决定确认您存在违法行为，或者本网站有足够事实依据可以认定您存在违法或违反本使用协议的行为，或者您借款逾期未还的，本网站有权在本网站及互联网络上公布您的违法、违约行为，并有权将该内容记入任何与您相关的信用资料和档案。

　　二、涉及第三方网站

　　本网站的网站内容可能涉及由第三方所有、控制或者运营的其他网站的内容或链接（以下称“第三方网站”）。本网站不能保证也没有义务保证第三方网站上的信息的真实性和有效性。对于该等内容或链接，您确认按照第三方网站的使用协议确定相关权利义务，而不是按照本协议。第三方网站的内容、产品、广告和其他任何信息均由您自行判断并承担风险，而与本网站无关。

　　三、不保证

　　除非本网站或其合作方在特定投资交易成立后的电子协议书中明示承担责任外，本网站提供的服务中不带有对本网站中的任何用户、任何交易的任何保证，因此本网站及其股东、创建人、高级职员、董事、代理人、关联公司、母公司、子公司和雇员（以下称“本网站方”）不保证由其他用户或机构提供的内容的真实性、充分性、及时性、可靠性、完整性和有效性，并且不承担任何由此引起的法律责任。

　　四、责任限制

　　在任何情况下，本网站方对您使用本网站服务而产生的任何形式的直接或间接损失均不承担法律责任，包括但不限于资金损失、利润损失、营业中断损失等。因为本网站或者涉及的第三方网站的设备、系统问题或者因为计算机病毒造成的损失，本网站均不负责赔偿，您的补救措施只能是与本网站终止本协议并停止使用本网站。但是，中华人民共和国现行法律、法规另有规定的除外。

　　五、网站内容监测

　　本网站没有义务监测网站内容，但是您确认并同意本网站有权不时地根据法律、法规、政府要求透露、修改、屏蔽或者删除必要的、合适的信息，以便更好地运营本网站并保护本网站的用户的合法权益。

　　六、个人信息的使用

　　本网站对于您提供的、自行收集到的、经认证的个人信息将按照本网站的隐私条款予以保护、使用或者披露。

　　七、补偿

　　就任何第三方提出的，由于您违反本协议及纳入本协议的条款和规则或您违反任何法律、法规或侵害第三方的权利而产生或引起的每一种类和性质的任何索赔、要求、诉讼、损失和损害（实际的、特别的及有后果的），无论是已知或未知的，包括合理的律师费，您同意就此对本网站和本网站的母公司、 子公司、关联公司、高级职员、董事、代理人和雇员（如适用）进行补偿并使其免受损害。

　　八、服务的中止或终止

　　1、除非本网站终止本协议或者您申请终止本协议及注销相应用户账户且经本网站同意，否则本协议始终有效。在您违反了本协议、相关规则，或在相关法律法规、政府部门的要求下，本网站有权通过电子邮件告知方式终止本协议、关闭您的账户或者限制您使用本网站。但本网站的终止行为不能免除您根据本使用协议或在本网站生成的其他协议项下的还未履行完毕的义务。

　　2、发生下列情形之一的，本网站有权随时中止或终止向用户提供服务：

　　(1)对于网络设备进行必要的保养及施工时；

　　(2)出现突发性的网络设备故障时；

　　(3)本网站所使用的网络通信设备由于不明原因停止，无法提供服务时；

　　(4)由于不可抗力因素致使本网站无法提供线上服务时；

　　(5)相关政府机构的要求。

　　3、发生下列情形之一的，本网站有权随时中止或终止向用户提供服务而无需通知用户：

　　(1)用户提供的个人资料不准确，不真实，不合法有效；

　　(2)用户使用任何第三方程序进行登录或使用服务；

　　(3)用户滥用所享受的权利；

　　(4)用户有损害其他用户的行为；

　　(5)用户有违背社会风俗和社会道德的行为；

　　(6)用户有违反本协议中相关规定的行为。

　　4、如因系统维护或升级的需要而需暂停网络服务，本网站将尽可能事先进行通告。

　　5、终止用户服务后，用户使用本网站服务的权利立即终止。从终止用户服务即刻起，本网站不再对用户承担任何责任和义务。

　　九、适用法律和管辖

　　因本网站提供服务所产生的争议均适用中华人民共和国法律，并由浩茗金融住所地的人民法院管辖。

　　十、附加条款

　　在本网站的某些部分或页面中、或者您使用本网站相关服务产品的过程中，可能存在除本协议以外的单独的附加服务条款或协议，当这些服务条款或协议与本协议条款及规则存在冲突时，则前述本协议条款和规则以外的服务条款或协议将优先适用。

　　十一、条款的独立性

　　若本协议的部分条款被认定为无效或者无法实施时，本协议中的其他条款仍然有效。

　　十二、对应账户

　　您在本网站注册成功后，将由本网站向您分配一个固定的普通账户并与您的用户名相绑定。该账户将与指定第三方支付机构或银行的，以该支付机构或银行为名义开立的托管账户相对应。您可以通过向托管账户充值的方式，增加本网站普通账户上的余额。与本网站相关的所有资金的往来、划转均实际通过该支付机构或银行进行。您在此特不可撤销地授权本网站按照本协议、附属规则及所有服务产品的具体使用协议及规则管理和划扣您普通账户余额对应于托管账户中的资金。

　　在用户将资金汇入其在本网站平台上的账户但并未使用之前，本网站对于用户的资金有保管义务，本网站保证用户的资金安全。该普通账户将在您在本网站注册的账户注销后自动关闭，关闭时普通账户余额对应的资金将进行退还。

　　十三、投诉和咨询

　　若您在使用本网站的过程中有任何的疑问、投诉和咨询，可随时致电客服热线或发送电子邮件到本网站指定的电子邮箱：service@xzgjf.com。

　　浩茗金融平台

　　2015年11月

</p> -->
                <a class="close-reveal-modal">&#215;</a>
                </div>
              </div>
             </li>
             <li>
              <div class="J-regconl-checkbox clearfix" style="width:100%;">
               <div class="clearfix">
                 <p>&nbsp;</p>
                 <div class="J-btn jfl"><a <%-- href="${path }/register" --%> id="registerButton" onclick="register()" target="_blank">下一步</a></div>
               </div>
               <h4>已有账户，<a href="${path }/login">登录</a></h4>
              </div>
             </li>
            </ul>
           </div>
           <div class="J-regcon-pic jfl jmt30"></div>
          </div>
        </div> 
        
        <!-- <div class="J-register jborder jboxsize" id="two" >
          <div class="J-register-top"><img src="${frontPath}/images/J-zhuce02.png"/></div>
          <div class="J-reg-con clearfix">
           <div class="J-regcon-list jfl clearfix">
            <ul>
             <li>
              <div class="J-regconl-input">
               <p>手机号码</p>
               <input type="text" name="userPhone" id="userPhone"  onBlur="checkUserPhone();" placeholder="手机号码" class="cyyj_in_hover"/>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="userPhoneTip" ></p>
             </li>
             <li class="J-regconl-two">
              <div class="J-regconl-input clearfix">
               <p>验证码</p>
               <input type="text" id="smsCode" name="smsCode" style="width:28%; margin-right:7px; float:left;"/>
               <a  onclick="sendMsg()" id="sendCode" class="J-regconl-ta">获取验证码</a>
              </div>
              <p class="rci_prompt jtext-indent text-overflow" id="duanXinTip"></p>
             </li>
             <li>
              <div class="J-regconl-checkbox clearfix" style="width:100%;">
               <div class="clearfix">
                 <p>&nbsp;</p>
                 <div class="J-btn jfl jmt40"><a  onclick="register()">下一步</a></div>
               </div>
               <h4><a href="${path }/login">返回修改手机密码</a></h4>
              </div>
             </li>
            </ul>
           </div>
           <div class="J-regcon-pic jfl"></div>
          </div>
        </div> -->
         </form>    
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>

<script type="text/javascript">
$(function(){
	$("#two").hide();
});

var wait=60;
	function next(){
		if(account == true && checkPassword() && checkPwdRepeat() && checkAgreement() && captchaflag == true){
			$("#one").hide();
			$("#two").show();
		}else{
			checkUserAccount(); 验证用户名称
			checkPassword();
			checkPwdRepeat();
			//checkVerificationCode();
			//checkAgreement();
		}
	}
    //刷新验证码
	function fresh() {
		$("#imagg").attr("src", "${path}/captcha.svl?d=" + new Date() * 1);
	} 
	
	 function checkCaptcha(){
		if($("#captcha").val() == ""){
			$("#captchaTip").text("验证码不能为空").css('color','#F7772D;');
			return false;
		}else{
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
					$("#captchaTip").text("");
					if (ret.code != '200') {
						flag = 1;
						$("#captchaTip").text("验证码不正确").css('color', '#F7772D;')
								.css("float", "left");
						fresh();
						captchaflag = false;
						return captchaflag;
					} else {
						flag = 0;
						//$("#captchaTip").text("ok").css('color','red');
						$("#captchaTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
						captchaflag = true;
						return captchaflag;
					}
				}
			});
		}

	}
	
document.onkeydown=function(event){
	e = event ? event : (window.event ? window.event : null);
	if(e.keyCode==13){
		$('#registerButton').trigger('click');
	}
};
	function readRegisterProtocol() {
		$('#reglaw').dialog({
			title : $('#read').html()
		});
	}
	
	//注册协议相关
	function readRegisterProtocol(obj) {
		if (obj == 1) {
			/* jHtml($('#reglaw').html(), '商海通注册协议', '560', '310');
			setTimeout("resetDialog()", 50); */

			window.open('${path}/commons/itd_agreement.do', '_blank')
		} else if (obj == 2) {
			jHtml($('#reglaw2').html(), '商海通网站法律声明', '560', '310');
			setTimeout("resetDialog()", 50);
		}

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
		
		validateVerifyCode();
		if(codeStatus){
			if (phone == true  && account == true
					&& checkPassword() && checkPwdRepeat() && checkAgreement()&&codeStatus) {
				form1.userPassword.value = encryptByDES(form1.userPassword.value,
						form1.publickey.value);
				$("#form1").submit();
	            $("#one").hide();
	            $("#three").show();
			} else {
				checkUserPhone();
			/* 	if (email != true) {
					$("#userEmail").val("");
					alert("请重新输入电子邮箱！");
					return;
				}
				if (account != true) {
					$("#userAccount").val("");
					alert("请重新输入用户名！");
					return;
				}
				*/
				if(!codeStatus){
					$("#smsCode").val("");
					alertc("验证码错误！");
				} 
			} 
		}
	}

	var account = true;
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
			//$("#userAccountTip").text("ok").css('color', 'red');
			$("#userAccountTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
			return true;
		}
	}

	function checkPassword() {
		$("#userPasswordTip").text("");
		var passwordPartten = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;///^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
		if ($("#userPassword").val() == "") {
			$("#userPasswordTip").text("密码不能为空").css('color', '#F7772D;');
			return false;
		} else if (passwordPartten.test($("#userPassword").val()) == false) {
			$("#userPasswordTip").text("请输入6-16位字符").css('color', '#F7772D;');
			return false;
		} else {
			//$("#userPasswordTip").text("ok").css('color', 'red');
			$("#userPasswordTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
			return true;
		}
	}

	function checkPwdRepeat() {
		$("#passwordrTip").text("");
		if ($("#passwordr").val() == "") {
			$("#passwordrTip").text("不能为空").css('color', '#F7772D;');
			return false;
		} else if ($("#userPassword").val() != $("#passwordr").val()) {
			$("#passwordrTip").text("密码不一致，请确认").css('color', '#F7772D;');
			return false;
		} else {
			//$("#passwordrTip").text("ok").css('color', '#F7772D;');
			$("#passwordrTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
			return true;
		}
	}

	function checkEmail() {
		$("#userEmailTip").text("");
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
			//$("#userEmailTip").text("ok").css('color', 'red');
			$("#userEmailTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
			return true;
		}
	}
	
	function checkPhone() {
		var emailPartten = /^1[3|4|5|8|7][0-9]\d{8}$/;
		if ($("#userPhone").val() == "") {
			$("#userPhoneTip").text("手机号不能为空").css('color','#F7772D;');
			//$("#userEmail").focus();
			return false;

		} else if (emailPartten.test($("#userPhone").val()) == false) {
			$("#userPhoneTip").text("手机号有误").css('color','#F7772D;');
			//$("#userEmail").focus();
			return false;
		} else {
			return true;
		}
	}
	
	var phone;
	function checkUserPhone() {
		if (!checkPhone()) {
			return;
		}
		var url = "${path}/checkUserPhone.do";
		$.ajax({
					type : "post",
					data : {
						"userPhone" : $("#userPhone").val()
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
							//	$("#userPhoneTip").text("ok");
								$("#userPhoneTip").append('<img src="${frontPath}/images/J-zhuce-tip.png" class="J-regconl-img"/>');
								phone = true;
								return phone;
							}
						} else {
							$("#userPhoneTip").html('手机号已存在');
							//$("#userEmail").focus();
							phone = false;
							return phone;
						}
					}
				});
	}

	
	var codeStatus = false;
	function validateVerifyCode() {
		if (!checkPhone()) {
			return;
		}
		var smsCode = $("#smsCode").val();
		if (smsCode == '') {
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
					alertc("验证码错误");
					$("#duanxinTip").text("验证码错误!").css('color', '#F7772D;');
					codeStatus = false;
					return;
				} else if (ret.code == "101") {
					alertc("验证码已失效，请重新获取验证码");
					$("#duanxinTip").text("验证码输入错误!").css('color', '#F7772D;');
					codeStatus = false;
					return;
				}
			}
		});
	}
	
	function sendMsg() {
		 if (wait != 60) {
			return;
		} 
		 if (!phone)
			alertc("您的手机号码输入有误，请重新输入");
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

</script>
</html>
