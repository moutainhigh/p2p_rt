<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
<style>
#attul li{
	width: 85px; 
	padding: 10px 0px 0px 0px;
}
#attul li span{
	margin: 15px 0px 0px 10px;
}

</style>
 <script type="text/javascript">
 KindEditor.ready(function(K) {
	 var editor = K.editor({
			allowFileManager : true,
			uploadJson:'${path}/upload/editorImgForKindEditor.do',
			formatUploadUrl:false
		});
	 K('#productURL').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#productURLs').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#productURLs').val(url);
						$("#div").css("display","block");
						$("#img").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
	 
	 K('#productURL1').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#productURLs1').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#productURLs1').val(url);
						$("#div").css("display","block");
						$("#img").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
	 //资料上传
	 K('#attestationImgId').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#attestationImg').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#attestationImg').val(url);
						$("#div").css("display","block");
						$("#img").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
 	});
 </script>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	        <table class="uniceTable siteInfoWidth1">
			    <tbody><tr id="attul">
			        <td class="userDh" id="loanTab1"><a href="javascript:tabChange(1);">实名认证</a></td>
			        <td id="loanTab2"><a href="javascript:tabChange(2);">邮箱认证</a></td>
			        <td id="loanTab3"><a href="javascript:tabChange(3);">手机认证</a></td>
			        <td id="loanTab4"><a href="javascript:tabChange(4);">视频认证</a></td>
			        <td id="loanTab5"><a href="javascript:tabChange(5);">现场认证</a></td>
			        <td id="loanTab6"><a href="javascript:tabChange(6);">资料上传</a></td>
			        <td id="loanTab7"><a href="javascript:tabChange(7);">证明材料</a></td>
			        <td id="loanTab8"><a href="javascript:tabChange(8);">VIP申请</a></td>
			    </tr>
			</tbody></table>
	        <div id="realname" class="model-box">
	        	<div class="form-opt" style="padding-top: 0px;">
	        		<div class="alerts" >
	        	   <span>*申请实名认证将扣除费用:${sysAuthRate }元,您当前可用余额${availableMoney }元 。</span><br/>
		            </div>
	        	    <div class="alerts"> 
			            <c:if test="${user.realnameStatus==-2 }">请填写正确的真实姓名和证件号码，以便后台进行审核。</c:if>
			            <c:if test="${user.realnameStatus==-1 }">您的实名正在认证，请等待结果...</c:if>
			            <c:if test="${user.realnameStatus==1 }">您的实名认证未通过，请重新认真填写。</c:if>
			            <c:if test="${user.realnameStatus==2 }">恭喜您，您的实名认证已经通过。</c:if>
			            </div> 
		        	<form action="requestRealnameAttestation.do" method="post" id="formRealname">
		            <table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls" style="text-align: center;">真实姓名：</td>
										<td class="bl"><span class="leftspace"></span>
										<input size="38" class="input" id="userRealname" name="userRealname" value="${user.userRealname }" <c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">readonly="readonly"</c:if> />
										</td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证号码：</td>
										<td class="bl">
											<span class="leftspace"></span>
											<input size="38" class="input"id="cardNumber" name="cardNumber" type="text" value="${user.cardNumber }" <c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">readonly="readonly"</c:if> />
											<em style="font-style: normal;"></em></td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证[正面]：</td>
										<td class="bl">
											<c:if test="${user.realnameStatus==1 || user.realnameStatus==-2}">
											<span class="leftspace"></span>
											<input value="上传" type="button" id="productURL" style="width:60px; height: 30px;" /> 
											<input style="border: 1px solid #8A949C; line-height: 30px; width:145px; height:30px;" type="text" value="" name="productURLs" id="productURLs"/>
											</c:if>
											<c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">
				 								<a href="${path }${user.cardFrontImg }" target="_blank" style="margin-left: 9px;" id="aForntImg"><img src="${path }${user.cardFrontImg }" width="150" height="150" id="img" /></a>
				 							</c:if>
										</td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证[背面]：</td>
										<td class="bl">
											<c:if test="${user.realnameStatus==1 || user.realnameStatus==-2}">
											<span class="leftspace"></span>
											<input value="上传" type="button" id="productURL1" style="width:60px; height: 30px;" /> 
											<input style="border: 1px solid #8A949C; line-height: 30px; width:145px; height:30px;" type="text" value="" name="productURLs1" id="productURLs1"/>
											</c:if>
											<c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">
								 				<a href="${path }${user.cardBackImg }" target="_blank" style="margin-left: 9px;"><img src="${path }${user.cardBackImg }" width="150" height="150" id="img" /></a>
								 			</c:if>
										</td>
									</tr>
									<c:if test="${ user.realnameStatus == 1 || user.realnameStatus == -2}">
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" id="subbtn" onclick="javascript:formRealName();" style="cursor: pointer;margin-left: 70px;margin-top: 20px;">确认提交</span>
										</td>
									</tr>
									</c:if>
								</tbody>
							</table>
		            </form>
	        	</div>
	         </div>
	        <div id="email" class="model-box" style="display: none;">
					<div class="form-opt">
			        	<form action="requestAddEmail.do" method="post" id="formEmail">
				            <div class="alerts">
				            <c:if test="${user.emailStatus==-2 }">绑定邮箱有利于我们及时通知您在${CACHE_SYS_CONFIG.sysWebsitename}的交易情况以及最新动态。</c:if>
				            <c:if test="${user.emailStatus==-1 }">您的邮箱正在认证，请等待结果。</c:if>
				            <c:if test="${user.emailStatus==1 }">您的邮箱认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.emailStatus==2 }">恭喜您，您的邮箱认证已经通过。</c:if>
				            </div>
				           <%--  <ul class="items">
				                <li class="txt"><em>*</em>邮箱：</li>
				                <li>
				                    <input type="text" id="userEmail" name="userEmail" class="input" value="${user.userEmail }" <c:if test="${user.userEmail!=null && '' ne user.userEmail }">readonly="readonly"</c:if>/>
				                    <span class="tip error" id="mailTip" style="display:none;"><i class="icons reg-error"></i></span>
				                </li>
				            </ul>
				            	<c:if test="${user.emailStatus==-2||user.emailStatus==1 }">
					           	 	<div class="btn-submit"> <a style="margin-left: 150px;" href="javascript:subForm('formEmail');" class="btn btnSize_1 btn_orange" id="submit">提交</a> </div>
				            	</c:if> --%>
				            	
				            <table class="userPtb">
			    		<tbody>
			    			<tr>
			       	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱：
			       	 				<input type="text" id="userEmail" name="userEmail" class="input" value="${user.userEmail }" <c:if test="${user.userEmail!=null && '' ne user.userEmail }">readonly="readonly"</c:if>/>
			       	 			</td>
			       	 			<td></td>
			   		 		</tr>
			   		 		<tr><td><br/></td></tr>
			   		 		<tr>
			   		 			<td>
			   		 				<c:if test="${user.emailStatus==-2||user.emailStatus==1 }">
			   		 				<span class="wdzh-buttu" id="subbtn" onclick="javascript:subForm('formEmail');" style="cursor: pointer;margin-left: 70px;margin-top: 20px;">确认提交</span>
			   		 				
			   		 				</c:if>
			   		 			</td>
			   		 		</tr>
						</tbody>
						</table>
			       	 	</form>
        			</div>
			</div>
	        <div id="phone" class="model-box" style="display: none;">
		        <div class="form-opt">
			       <form action="/secure/changeBindMobile2.session.action" method="POST" id="formPhone">
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
		                		<c:when test="${empty sessionScope.FRONT_USER_SESSION.userPhone || sessionScope.FRONT_USER_SESSION.phoneStatus==1}">
				                	 <span class="wdzh-buttu" id="phoneSub" onclick="phoneCheck();" style="cursor: pointer;margin-left: 120px;margin-top: 3px;">人工审核</span>
		                		</c:when>
		                	</c:choose>
		                	 <%-- <c:if test="${not empty sessionScope.FRONT_USER_SESSION.userPhone && sessionScope.FRONT_USER_SESSION.phoneStatus==2}">
				                	 <span class="wdzh-buttu" id="savePhone"  title="输入新手机号即可修改" style="cursor: pointer;margin-left: 120px;margin-top: 3px;">修改号码</span>
				                	 <!-- <span class="wdzh-buttu" id="getCode" onclick="getUpdatePhoneCode();" title="输入新手机号即可修改" style="cursor: pointer;margin-left: 230px;margin-top: -42px;">获取验证码</span> -->
				            </c:if>  --%>
		                </li>
		            </ul>
		         <c:choose>
		                <c:when test="${empty sessionScope.FRONT_USER_SESSION.userPhone || sessionScope.FRONT_USER_SESSION.phoneStatus==1}">
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
	        <div id="voide" class="model-box" style="display: none;">
				<div class="form-opt">
			        	<form action="${path }/verify/videoAttestationApply.do" method="post" id="formVideo">
				            <div class="alerts">
				            <c:if test="${user.videoStatus==-2 }">您的视频认证还没有申请</c:if>
				            <c:if test="${user.videoStatus==-1 }">您的视频认证正在处理，请等待结果。</c:if>
				            <c:if test="${user.videoStatus==1 }">您的视频认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.videoStatus==2 }">恭喜您，您的视频认证已经通过。</c:if>
				            </div>
			                <c:if test="${user.videoStatus==1 || user.videoStatus == -2}">
				           		 <span class="wdzh-buttu" onclick="subForm('formVideo');" style="cursor: pointer;margin-top: 3px;" id="submit">申请</span>
				           		 <div class="alerts2">如果您需要视频认证，请您点击申请，等待公司安排，客服将与您联系。</div>
           					</c:if>
			       	 	</form>
        			</div>
			</div>
	        <div id="sport" class="model-box" style="display: none;">
	        	<div class="form-opt">
			        	<form action="" method="post" id="formScene">
				            <div class="alerts">
				            <c:if test="${user.sceneStatus==-2 }">您的现场认证还没有申请</c:if>
				            <c:if test="${user.sceneStatus==-1 }">您的现场认证正在处理，请等待结果。</c:if>
				            <c:if test="${user.sceneStatus==1 }">您的现场认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.sceneStatus==2 }">恭喜您，您的现场认证已经通过。</c:if>
				            </div>
			                <c:if test="${user.sceneStatus==1 || user.sceneStatus == -2}">
				           		 	<span class="wdzh-buttu" onclick="subForm('formScene');" style="cursor: pointer;margin-top: 3px;" id="submit">申请</span>
				           		 	<div class="alerts2">如果您需要现场认证，请您点击申请，等待公司安排，客服将与您联系。</div>
           					</c:if>
			       	 	</form>
        			</div>
	        </div>
	        <div id="uploading" class="model-box" style="display:none;">
	        	<div class="form-opt">
			        	<form action="" id="formUploading" method="post">
							<div class="alerts">
								<font color="#FF0000">*</font> 必须是本人的真实、有效资料
							</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">资料上传：</td>
										<td id="imgUpload"><span class="leftspace"></span>
											<input value="上传" type="button" id="attestationImgId" style="width:60px; height: 30px;" class="w80 l-button"/> 
				                			<input style="border: 1px solid #8A949C; line-height: 30px; width:190px; height:30px;" type="text" value="" name="attestationImg" id="attestationImg"/>
										</td>
									</tr>
									<tr>
										<td class="ls">上传类型：</td>
										<td><span class="leftspace"></span>
											<select id="attestationTypeid" name="attestationTypeid" style="width: 435px;">
						                	<c:forEach var="type" items="${attType }">
						                		<c:if test="${not empty type }">
							                		<option value="${type.id }" style="width: 405px;">${type.attestationName }</option>
						                		</c:if>
						                		<c:if test="${empty type }">
						                			<option value="" style="width: 405px;">-没有要上传的证件-</option>
						                		</c:if>
						                	</c:forEach>
						                	</select>
										</td>
									</tr>
									<tr>
										<td class="ls">备注说明：</td>
										<td><span class="leftspace"></span>
											<textarea rows="5" cols="50"  id="attestationRemarks" name="attestationRemarks"></textarea>
				                    		<span class="tip error" id="mailTip" style="display:none;"><i class="icons reg-error"></i></span>
										</td>
									</tr>
									<tr id="yzm">
										<td class="ls">验&nbsp;证&nbsp;&nbsp;码：</td>
										<td><span class="leftspace"></span>
											<input maxlength="4" style="width:100px; margin-bottom: 10px;margin-top: 10px;float: left;margin-left: 10px;" name="captcha" type="text" id="captcha" class="input" />&nbsp;
											<span class="txt"><img style="margin-top: 15px;" id="captchaImg" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" /></span>
										</td>
									</tr>

									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="formUpload();" style="cursor: pointer;margin-top: 20px;margin-left: 80px;" id="submit">确认提交</span>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
						<div class="alerts2">* 温馨提示：我们将严格对用户的所有资料进行保密</div>
        			</div>
	        </div>
            <div id="data" class="model-box rec-items" style="display:none;">
       			<div class="siteInfoWidth ht1"></div>
						<table class="siteInfoWidth1 tableDate">
							<thead>
								<tr class="tdwait">
									<td class="tdwait1">&nbsp;说明信息</td>
									<td>资料类型</td>
									<td class="tdwait1">审核时间</td>
									<td>审核说明</td>
									<td class="tdwait1">积分</td>
								</tr>
							</thead>
							<tbody id="dataDiv"></tbody>
						</table>
						<div class="siteInfoWidth ht1"></div>
			</div>
	        <div id="vip" class="model-box" style="display:none;">
	        		<div class="vip_k">
	        		<form name="formVip" id="formVip" method="post" >
						  <div class="vip_top">
						    <div class="vip_top_bt" id="vip_top_bt">您的vip状态：
						    		<c:if test="${vip.vipView ==null || vip.vipView == ''}">
								  		VIP未申请
								  	</c:if>
								  	<c:if test="${vip.vipView !=null || vip.vipView != ''}">
								  		${vip.vipView }
								  	</c:if>
								  	  ${cus.avatarImg}
							</div>
						  </div>
						 <div class="vip_nrk">
						  <c:forEach var="cus" items="${CUSTOMER_SERVICE }">
							  		<c:if test="${not empty vip.userId}">
							  		 	<c:if test="${vip.vipCustomer eq cus.id }">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd>
											  	<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a>
											  </dd>
											  <dd><input name="vipCustomer" id="vipCustomer" type="radio" checked="checked" value="${vip.vipCustomer }" /></dd>
											</dl>
											</div>
							  		 	</c:if>
							  		 	<c:if test="${vip.vipCustomer ne cus.id }">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd>
											  	<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a>
											  </dd>
											  <dd><input name="vipCustomer" id="vipCustomer" type="radio"  value="${vip.vipCustomer }" /></dd>
											</dl>
											</div>
							  		 	</c:if>
							  		 	
							  		</c:if>
							  		<c:if test="${ empty vip.userId}">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd><a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a></dd>
											  <dd><input name="vipCustomer" class="btn btnSize_1 btn_orange" id="vipCustomer"  type="radio"  value="${cus.id }" /></dd>
											</dl>
										</div>
							  		</c:if>
						  </c:forEach>
						 </div>
						 <!--vip  -->
						 <div class="vip_ztt">
						  <div class="vip_ztk">
							  <ul>
								  <li>您的状态：</li>
								  <li style="margin-right:45px;" id="statusd">
								  	<c:if test="${vip.vipView ==null || vip.vipView == ''}">
								  		VIP未申请
								  	</c:if>
								  	<c:if test="${vip.vipView !=null || vip.vipView != ''}">
								  		${vip.vipView }
								  	</c:if>
								  </li>
								  <li>用户名：</li>
								  <li style="margin-right:45px;">${user.userAccount }</li>
								  <li>姓名：</li>
								  <li style="margin-right:45px;">${user.userRealname }</li>
								  <li>邮箱：</li>
								  <li>${user.userEmail }</li>
							  </ul>
						  </div>
						  <div class="vip_wz">vip申请后有效时间是一年 &nbsp; 
						  	<c:if test="${ not empty vip.vipEndDate && vip.vipStatus==1 }">
						 		 到期日：<fmt:formatDate value="${vip.vipEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						  	</c:if>
					      </div>
						  <div class="vip_wz">申请VIP会员将扣除VIP管理费用:${vipFee}元，您的当前可用余额${userAccount }元 ，
						  		<c:choose>
									  <c:when test="${not empty userAccount && userAccount gt vipFee }">
									  </c:when>
									  <c:otherwise>不够扣除VIP费用，请先<a href="${path }/account/recharge.html">[充值]</a> </c:otherwise>
									</c:choose>
								 本网站非VIP会员期间投标不享有本网站的垫付保障权益!</div>
								 
						  <div id="vipDiv" style="display: block;">
							  <div class="vip_bgk">
								  <dl>
								  <c:if test="${vip.vipCustomer ==null ||vip.vipCustomer=='' ||vip.vipStatus==5||vip.vipStatus==3}">
								    <dt><em>*</em>验证码：
								    	<input maxlength="4" style="width:100px; margin-bottom: 10px;" name="captcha" type="text" id="captchaVip" class="input"  />&nbsp;
										<span  class="txt"><img style="margin-top: 7px;margin-bottom: -8px;" id="captchaImg2" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" /></span>
								    </dt>
								  </c:if>
								  </dl>
							  </div>
							  <div class="form-opt" style="padding:10px 30px 10px;">
							  	<c:if test="${vip.vipCustomer ==null ||vip.vipCustomer=='' ||vip.vipStatus==5||vip.vipStatus==3}">
			       		  			<span class="wdzh-buttu" onclick="submitVip();" style="cursor: pointer;margin-top: 3px;" id="submit">申请</span>
							  	</c:if>
			       		 	</div>
		       		 	</div>
		       		 </div>
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

	function tabChange(obj){
		
		if(obj==1){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab1").addClass("userDh");
			$("#realname").show();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").hide(); 
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==2){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab2").addClass("userDh");
			$("#realname").hide();
			$("#email").show(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").hide();
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==3){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab3").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").show(); 
			$("#voide").hide(); 
			$("#sport").hide();
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==4){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab4").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").show(); 
			$("#sport").hide();
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==5){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab5").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").show();
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==6){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab6").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").hide();
			$("#uploading").show();
			$("#data").hide();
			$("#vip").hide();
		}else if(obj==7){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab7").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").hide();
			$("#uploading").hide();
			$("#data").show();
			$("#vip").hide();
			var data={"tojsp":1};
			getPageTable(data,"${path }/verify/dataPage.do",dataTable,"dataDiv",5);
			
		}else if(obj==8){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab8").addClass("userDh");
			$("#realname").hide();
			$("#email").hide(); 
			$("#phone").hide(); 
			$("#voide").hide(); 
			$("#sport").hide();
			$("#uploading").hide();
			$("#data").hide();
			$("#vip").show();
		}else if(obj==9){
			window.location.href="${path }/basics/userMessage.html";
		}
		
	}
	
	function subForm(obj){
		//alert(11);
		//$("#formVideo").submit();
		var url="";
		switch(obj){
			case 'formEmail':
				url="${path}/verify/requestAddEmail.do";
				break;
			case 'formPhone':
				url="${path}/verify/requestRealnameAttestation.do";
				break;
			case 'formVideo':
				url="${path }/verify/videoAttestationApply.do";
				break;
			case 'formScene':
				url="${path }/verify/sceneAttestationApply.do";
				break;
				
		}
		
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	
            		//视频
            		if(data.code=='102'){
            			$("#"+obj).find(".wdzh-buttu").hide();
            			$("#"+obj).find(".alerts").html("您的邮箱认证正在处理，请等待结果。");
            		}
	            	//视频
            		if(data.code=='200'){
            			$("#"+obj).find(".wdzh-buttu").hide();
            			$("#"+obj).find(".alerts").html("您的视频认证正在处理，请等待结果。");
            		}else if(data.code=='101'){
            			alertc("邮箱认证、手机认证、实名认证通过后才能申请视频认证。");
            		}
            		//现场
            		if(data.code=='201'){
            			$("#"+obj).find(".wdzh-buttu").hide();
            			$("#"+obj).find(".alerts").html("您的现场认证正在处理，请等待结果。");
            		}else if(data.code=='103'){
            			alertc("邮箱认证、手机认证、实名认证通过后才能申请视频认证。");
            		}
	            		
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        });     
	}
	
	//实名认证
	function formRealName(){
		$("#formRealname").validate({						  
			rules: {
				userRealname: {
					required: true,
					chcharacter:true
				},
				cardNumber:{
					required:true,
					isIdCardNo:"#cardNumber"
				},
				productURLs:{
					required:true
				},
				productURLs1:{
					required:true
				}
			}
		});
		if($("#formRealname").valid()){
			$.ajax({
				type : "POST",
				url : "${path}/verify/requestRealnameAttestation.do",
				data : $('#formRealname').serialize(),
				async : false,
				success : function(data) {
					if(data.code=='100'){
            			window.location.href="${path}/verify/userAttestation.do";
            		}
					if(data.code=='104'){
	            		alertc(data.msg);
	            	}
					if(data.code=='102'){
	            		alertc(data.msg);
	            	}
				}
			});
		}
	}
	
	//资料上传
	function formUpload(){
		
		
		$("#formUploading").validate({						  
			rules: {
				attestationImg: {
					required: true
				},
				captcha:{
					required:true
				},
				attestationTypeid:{
					required:true
				}
			}
		});
		
		if($("#captcha").val()==""||$("#captcha").val()==null){
			
			alertc("请填写验证码");
			$("#captchaImg").click();
			
		}else if($("#captcha").val()==""||$("#captcha").val()==null){
			$("#captchaImg").click();
		}
		
		
		if($("#formUploading").valid()){
			$.ajax({
	          	dataType: 'json',
	            url:"${path}/verify/toAttestationApply.do",
	            data:$("#formUploading").serialize(),
	            type: 'POST',
	            
	            success: function(data) {
	           		//资料上传
	           		if(data.code=='200'){
	           			document.getElementById("imgUpload").style.height="200px";
	           			$("#imgUpload").html('<img alt="" src="'+"${path}"+$("#attestationImg").val()+'" width="200" height="200">');
	           			$("#formUploading").find(".wdzh-buttu").hide();
	           			$("#yzm").hide();
	           			$("#formUploading").find(".alerts").html("您的资料上传正在处理，请等待结果。");
	           		}
	           		if(data.code=='201'){
	           			alertc(data.msg);
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
	//Vip申请
	function submitVip(){
		var vipFee=${vipFee};
		var userAccount=${userAccount};
		var account;
		account=userAccount-vipFee;
		if(account<0){
			alertc("用户余额不足!");
			$("#captchaImg2").click();
		}else if($("#captchaVip").val()==""||$("#captchaVip").val()==null){
			
			alertc("请填写验证码");
			$("#captchaImg2").click();
			
		}else if($("#vipCustomer").val()==""||$("#vipCustomer").val()==null){
			alertc("请选择客服");
			$("#captchaImg2").click();
		}else{
			$.ajax({
	          	dataType: 'json',
	            url:"${path}/verify/toVipApply.do",
	            data:$("#formVip").serialize(),
	            type: 'POST',
	            
	            success: function(data) {
	           		//资料上传
	           		if(data.code=='212'){
	           			alertc("vip申请正在处理中");
	           			$("#vipDiv").hide();
	           			$("#vip_top_bt").text("您的vip状态：vip申请中");
	           			$("#statusd").text("vip申请中");
	           		}
	           		if(data.code=='213'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='210'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='211'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='202'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='215'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='216'){
	           			alertc(data.msg,function(){window.location.href="${path}/verify/userAttestation.do"});
	           		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        }); 
		}
		 
	} 
	//文本框失去焦点 验证身份证
	function checkNumber(obj){
		
		var idcard=$("#"+obj).val();
		if(idcard==""||idcard==null){
			alertc("身份证不允许为空");
			return;
		}else if(idcard.length!=18){
			alertc("身份证号码不满足18位");
			return;
		}else{
			return true;
		}
	}
	//拼成表格
		function dataTable(data){
		var c="";
			if(data.attestationStatus==0){
				 c="申请中";
			}
			if(data.attestationStatus==1){
				 c="通过";
			}
			if(data.attestationStatus==-1){
				 c="未通过";
			}
			 var htmlStr = '<tr>';
		     htmlStr+="<td>"+data.attestationRemarks+"</td>"+
		     "<td>"+data.attestationName+"</td>"+
		     "<td>"+toDate(data.attestationVerifyDatetime)+"</td>"+
		     "<td>"+c+"</td>"+
		     "<td>"+data.attestationScore+"</td>";
		     htmlStr+="</tr>";
		    return htmlStr;
		};
		
	/* 修改密码 */
	$('#savePhone').click(function (){
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
		var phone = $("#phoneNum").val();
		
		$.post("savePhone.do?phoneNum="+phone,"",function (data){
		    alertc(data.msg,reloadPage);	  
		   });
	});
	function reloadPage(){
		window.location.reload(true);
	}
	
	$(function(){
		var type="${type}";
		if(type!=""){
			tabChange(Number(type));
		}
	});
</script>
</html>