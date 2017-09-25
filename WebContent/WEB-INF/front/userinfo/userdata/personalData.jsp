<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-个人资料</title>
<script type="text/javascript" src="${frontPath }/js/jquery.dialog.js"></script>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
		
	<%-- <div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<div class="setInfo fr">
					<c:set var="curNav" value="title1"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" id="presonal" method="post">
						<input type="hidden" name="id" id="id" value="${personal.id }">

							<div class="alerts">请填写你的个人相关信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">账&nbsp;户：</td>
										<td class="bl"><span class="leftspace"></span>
											${sessionScope.FRONT_USER_SESSION.userAccount }
										</td>
									</tr>
									<tr>
										<td class="ls">真实姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<c:if test="${not empty sessionScope.FRONT_USER_SESSION.userRealname }">${sessionScope.FRONT_USER_SESSION.userRealname} </c:if>
		                					<c:if test="${ empty sessionScope.FRONT_USER_SESSION.userRealname }">还未实名认证  &nbsp;</c:if>
										</td>
									</tr>
									<tr>
										<td class="ls">性&nbsp;别：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="radio" checked="checked" name="sex" value="male" <c:if test="${'male' eq sessionScope.FRONT_USER_SESSION.userSex}">checked="checked"</c:if> style="width: 20px;float: left;"/>
						                	<span style="float: left;margin-top: 0px;">男</span>
											<input type="radio" name="sex" value="female" <c:if test="${'female' eq sessionScope.FRONT_USER_SESSION.userSex}">checked="checked"</c:if>style="width: 20px;float: left;"/>
											<span style="float: left;margin-top: 0px;">女</span>
										</td>
									</tr>
									<tr>
										<td class="ls">手机号码：</td>
										<td class="bl"><span class="leftspace"></span>
											${sessionScope.FRONT_USER_SESSION.userPhone }
										</td>
									</tr>
									<tr>
										<td class="ls">籍贯：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="userProvince" id="userProvince">
			                                </select>
			                                <select name="userCity" id="userCity">
			                                </select>
			                                <select name="userArea" id="userArea">
			                                </select>
										</td>
									</tr>
									<tr>
										<td class="ls">婚姻状况：</td>
										<td class="bl"><span class="leftspace"></span>
										<select name="marital" id="marital">
										    <!-- <option >---请选择婚姻状况---</option> -->
										    <option  value="0" <c:if test="${'0' eq personal.marital}">selected="selected"</c:if>>未婚</option>
										    <option  value="1" <c:if test="${'1' eq personal.marital}">selected="selected"</c:if>>已婚</option>
										    <option  value="2" <c:if test="${'2' eq personal.marital}">selected="selected"</c:if>>离异</option>
										    <option  value="3" <c:if test="${'3' eq personal.marital}">selected="selected"</c:if>>丧偶</option>
										 </select>
										</td>
									</tr>
									<tr>
										<td class="ls">子女：</td>
										<td><span class="leftspace"></span>
											<select name="children" id="children">
											<!-- <option >---请选择子女状况---</option> -->
											    <option  value="0" <c:if test="${'0' eq personal.children}">selected="selected"</c:if>>没有</option>
											    <option  value="1" <c:if test="${'1' eq personal.children}">selected="selected"</c:if>>1个</option>
											    <option  value="2" <c:if test="${'2' eq personal.children}">selected="selected"</c:if>>2个</option>
											    <option  value="3" <c:if test="${'3' eq personal.children}">selected="selected"</c:if>>3个</option>
											    <option  value="4" <c:if test="${'4' eq personal.children}">selected="selected"</c:if>>4个或4个以上</option>
										    </select>
										</td>
									</tr>
									<tr>
										<td class="ls">月收入：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="income" id="income">
											  <!--  <option >--请选择月收入状况--</option> -->
											   <option value="1" <c:if test="${'1' eq personal.income}">selected="selected"</c:if>>1000元以下</option>
											   <option value="2" <c:if test="${'2' eq personal.income}">selected="selected"</c:if>>1000-2000元</option>
											   <option value="3" <c:if test="${'3' eq personal.income}">selected="selected"</c:if>>2000-3000元</option>
											   <option value="4" <c:if test="${'4' eq personal.income}">selected="selected"</c:if>>3000-4000元</option>
											   <option value="5" <c:if test="${'5' eq personal.income}">selected="selected"</c:if>>4000-5000元</option>
											   <option value="6" <c:if test="${'6' eq personal.income}">selected="selected"</c:if>>5000-6000元</option>
											   <option value="7" <c:if test="${'7' eq personal.income}">selected="selected"</c:if>>6000-8000元</option>
											   <option value="8" <c:if test="${'8' eq personal.income}">selected="selected"</c:if>>8000-10000元</option>
											   <option value="9" <c:if test="${'9' eq personal.income}">selected="selected"</c:if>>1万以上</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">社保：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="social" id="social">
											  <!--  <option >---请选择社保状况---</option> -->
											   <option value="0"  <c:if test="${'0' eq personal.social}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.social}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">社保电脑号：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="socialId" value="${personal.socialId}" id="socialId" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">住房条件：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="housing" id="housing">
										      <!--  <option >---请选择住房状况---</option> -->
											   <option value="1" <c:if test="${'1' eq personal.housing}">selected="selected"</c:if>>租房</option>
											   <option value="2" <c:if test="${'2' eq personal.housing}">selected="selected"</c:if>>自己有房</option>
											   <option value="0" <c:if test="${'0' eq personal.housing}">selected="selected"</c:if>>其他</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">是否有车：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="car" id="car">
											  <!--  <option >----请选择车状况----</option> -->
											   <option value="0" <c:if test="${'0' eq personal.car}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.car}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">逾期记录：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="overdue" id="overdue">
											  <!--  <option >---请选择逾期状况---</option> -->
											   <option value="0" <c:if test="${'0' eq personal.overdue}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.overdue}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="javascript:subForm('presonal');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
				</div>
</div>
    
    </div>
  </div> --%>	
  
  <div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
 <input type="hidden" value="left9" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
 
   </div>

  <div class="J-ma-conR jb_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);" class="tap">基本信息</a>
    </div>
    <div class="jb_txt">
    <!-- ${path }${user.avatarImg} -->
      <div class="pic"><a href="${path }/basics/avatar.do"><img src="${path }${user.avatarImg}" height="122" width="121" alt=""></a></div>
      <ul id="saftyPageList" class="user-safety-options-list clearfix" style="overflow:hidden">
                        <li style="width: 500px;margin: auto;" class="user-safety-options-item clearfix">
                            <div style="width: 500px;margin: auto;" class="user-safety-options-show clearfix">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong>用户名</strong></span>
                                <span class="user-safety-options-value">${user.userAccount }&nbsp;&nbsp;<img src="${frontPath }/images/wdzh-gou.png" width="19" height="15"></span>
                                <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证"> </span>
                            </div>
                        </li>
                        
                        <li style="width: 500px;margin: auto;" class="user-safety-options-item clearfix">
                            <div style="width: 500px;margin: auto;" class="user-safety-options-show clearfix">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong>手机</strong></span>
                                <span class="user-safety-options-value"><script>document.write("${user.userPhone}".substr(0,"${user.userPhone}".length-8)+"***"+"${user.userPhone}".substr("${user.userPhone}".length-4,"${user.userPhone}".length));</script></span>
                                <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证"><a style="color: #fff;">修改</a></span>
                            </div>
                            <div id="phoneWrapper" class="user-safety-options-edit" style="display: none; height: 185px; width: 420px;">
                              <!--修改手机步骤-->
                                <div id="phoneStatus1" class="options-step" style="display:none">
                                    <div class="options-step-con clearfix">
                                        <span class="options-step-current">
                                            验证原手机号码
                                            <strong>1</strong>
                                        </span>
                                        <span>
                                            验证新手机号码
                                            <strong>2</strong>
                                        </span>
                                        <span>
                                            成功
                                            <strong>3</strong>
                                        </span>
                                    </div>
                                </div>
                                <!--手机认证begin-->
                                <div id="phoneCon1" class="form-style-1 clearfix" _step="1">
                                    <form>
                                    <ul class="form-style-1-field ">
                                        <li class="form-style-1-item">
                                            <label id="phoneNewText" class="form-style-1-label">手机号码</label>
                                            <span class="form-style-1-value" style="width:163px;"><input style="width:163px;background-color: #f5f5f5;" readonly="readonly" id="userPhone" name="userPhone" value="${user.userPhone}" class="input-text-style-1 form-style-1-value" type="text"></span>
                                            <span class="form-style-1-other" style="width:auto"><a onclick="sendMsg();" id="sendCode"  class="btn-style-1 sms-channel" type="button" style="color: #fff;">获取短信验证码</a></span>
                                        </li>
                                        <li class="form-style-1-item yuyin-channel" style="display:none;">
                                            <label class="form-style-1-label">&nbsp;</label><span class="form-style-1-other"><i class="js_info0">如果没有收到短信，您可</i><i class="js_info3"></i><i class="js_info1">请注意接听 010-5321233* 的来电，若没收到，您可</i><i class="yyct"></i><i class="js_info2">重新</i><i style="display:block">获取</i><input value="语音验证码" data-voice="true" class="voicech" type="button"></span>
                                        </li>
                                        <li class="form-style-1-item">
                                            <label class="form-style-1-label" style="height:29px;">短信验证码</label>
                                            <span class="form-style-1-value"><input name="smsCode" id="smsCode" onBlur="validateVerifyCode()"  class="input-text-style-1 form-style-1-value" type="text"></span>
                                            <span class="form-style-1-error"></span>
                                        </li>
                                        <li class="form-style-1-item">
                                            <label id="phoneNewText" class="form-style-1-label">新手机号码</label>
                                            <span class="form-style-1-value" style="width:163px;"><input style="width:263px;" onBlur="checkUserPhone();"  id="newUserPhone" name="newUserPhone" class="input-text-style-1 form-style-1-value" type="text"></span>
                                            <span class="form-style-1-error"></span>
                                        </li>
                                    </ul>
                                    <div class="form-style-1-submit">
                                       <a onclick="phoneCheck();" style="color: #fff;">认证</a> <a style="background-color:#cccccc; color:#fff;" href="#" id="cancel" >取消</a>
                                    </div>
                                    <input class="js_vnum" value="1" disabled="disabled" type="hidden">
                                    </form>
                                </div>
                                <!--手机认证 end-->
                                <div id="phoneCon2" class="form-style-1 clearfix" style="display:none">
                                    <div class="user-safety-options-succeed clearfix">
                                        <span class="user-safety-options-succeed-true">&nbsp;</span>
                                        <span class="user-safety-options-succeed-txt">恭喜您成功修改手机号</span>
                                    </div>
                                </div>
                                <!--手机认证状态提示end-->                                
                                <!-- <div class="form-warm-prompt-1 clearfix" style="width: 400px; margin-left: 20px;">
                                    <span class="form-warm-prompt-title">温馨提示：</span>
                                    <p>1. &nbsp;&nbsp;请填写真实有效的手机号，手机号将作为验证用户身份的重要手段。</p>
                                    <p>2. &nbsp;&nbsp;爱投资会对用户的所有资料进行严格保密。</p>
                                    <p>3. &nbsp;&nbsp;手机认证过程遇到问题，请联系客服，400-600-4300。</p>
                                </div> -->
                            </div>
                        </li>
                        
                        
                        <li style="width: 500px;margin: auto;" class="user-safety-options-item clearfix">
                            <div style="width: 500px;margin: auto;" class="user-safety-options-show clearfix">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong>实名</strong></span>
                                <c:choose>
					      <c:when test="${user.realnameStatus==2}">
					               <span class="user-safety-options-value" style="color:#F7772D;">已认证</span>
					                   <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证"><a style="color: #fff;">已认证</a></span>
                            
					      </c:when>
					      <c:otherwise>
                                <span class="user-safety-options-value" style="color:#F7772D;">未认证</span>
                                    <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证"><a style="color: #fff;">立即认证</a></span>
                           
					      </c:otherwise>
					    </c:choose>
                            </div>
                            <c:choose>
					      <c:when test="${user.realnameStatus==2}">
					              <div id="phoneWrapper" class="user-safety-options-edit" style="display: none; width: 420px;">
                              <!--修改手机步骤-->
                                <div id="realApiCon" class="form-style-1 clearfix">
                                    <form>
                                        <ul class="form-style-1-field ">
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label" style="font-size:14px;">用户名</label>
                                               ${user.userAccount }
                                           </li>
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label">真实姓名</label>
                                               <input id="pwdpas" name="newpassword" value="${user.userRealname }" class="input-text-style-1 form-style-1-value" readonly="readonly" type="text">
                                           </li>
                                           <li class="form-style-1-item" style="margin-bottom: 0px;">
                                               <label class="form-style-1-label">身份证号码</label>
                                               <input name="newpassword1" value="${user.cardNumber }" class="input-text-style-1 form-style-1-value" readonly="readonly" type="text">
                                           </li>
                                        </ul>
                                   </form>
                                </div>
                                <!--手机认证begin-->
                                
                                <!--手机认证 end-->
                                <div id="phoneCon2" class="form-style-1 clearfix" style="display:none">
                                    <div class="user-safety-options-succeed clearfix">
                                        <span class="user-safety-options-succeed-true">&nbsp;</span>
                                        <span class="user-safety-options-succeed-txt">恭喜您成功修改手机号</span>
                                    </div>
                                </div>
                                <!--手机认证状态提示end-->                                
                                
                            </div>
					      </c:when>
					      <c:otherwise>
                                <div id="phoneWrapper" class="user-safety-options-edit" style="display: none; width: 420px;">
                              <!--修改手机步骤-->
                                <div id="realApiCon" class="form-style-1 clearfix">
                                    <form id="formRealname">
                                        <ul class="form-style-1-field ">
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label" style="font-size:14px;">用户名</label>
                                               ${user.userAccount }
                                           </li>
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label">真实姓名</label>
                                               <input  name="userRealname" id="userRealname" class="input-text-style-1 form-style-1-value" type="text">
                                           </li>
                                           <li class="form-style-1-item" style="margin-bottom: 0px;">
                                               <label class="form-style-1-label">身份证号码</label>
                                               <input  name="cardNumber" id="cardNumber" class="input-text-style-1 form-style-1-value" type="text">
                                           </li>
                                            <li style="font-size:14px;">
                                              <label class="form-style-1-label">&nbsp;</label>
                                             <%--   <input class="sl_r_d_o" type="checkbox" name="protocol" id="protocol" value="book"> 我已阅读并同意签署<a class="sl_a_t_z" id="read" onclick="readRegisterProtocol();">《亿中良服务协议》</a>
<div id="reglaw" style='display: none;width: 550px;height: 300px;overflow:scroll;' >${registerProtocol}</div>
                                            --%></li> 
                                        </ul>
                                       <div class="form-style-1-submit form-style-1-submit-p" style="margin-top:0px;">
                                           <a onclick="formRealName();"class="btn-style-3 btn-style-3-two" value="" type="submit" style="color: #fff;">提交实名认证</a>
                                          
                                       </div>
                                   </form>
                                </div>
                            </div>
					      </c:otherwise>
					    </c:choose>
                            
                        </li>
                        <!--邮箱验证begin-->
                        <li style="width: 500px;margin: auto;" class="user-safety-options-item clearfix">
                            <div style="width: 500px;margin: auto;" class="user-safety-options-show clearfix">
                                 <span class="user-safety-options-name"><i class="icon-check"></i><strong>邮箱</strong><a id="pwdAnchor" name="pwdAnchor"> </a></span>
                                 
                                 <c:choose>
					      <c:when test="${not empty user.userEmail && user.emailStatus==2}">
                                 <span style="color:#F7772D;" class="user-safety-options-value">已绑定</span>
                                  <span id="onpwdBtn1" class="user-safety-options-state" switchtext="取消修改"><a style="color: #fff;">已绑定</a></span>
					      </c:when>
					      <c:otherwise>
                                 <span style="color:#F7772D;" class="user-safety-options-value">未绑定</span>
                                  <span id="onpwdBtn1" class="user-safety-options-state" switchtext="取消修改"><a style="color: #fff;">立即绑定</a></span>
					      </c:otherwise>
					    </c:choose>
                            </div>
                            
                            <c:choose>
					      <c:when test="${not empty user.userEmail && user.emailStatus==2}">
                                <div id="onpwdWrapper" class="user-safety-options-edit" style="display: none; width: 420px;">
                            <div id="pwdCon1" class="form-style-1 clearfix">
                                <form>
                                 <ul class="form-style-1-field ">
                                         <li class="form-style-1-item">
                                             <label id="phoneNewText" class="form-style-1-label">邮箱</label>
                                             <span class="form-style-1-value"><input id="phoneNum" name="phone" value="${user.userEmail }" class="input-text-style-1 form-style-1-value" type="text" readonly="readonly"></span>
                                             <span class="form-style-1-error"></span>
                                         </li>
                                        
                                     </ul>
                               
                             
                                </form>
                            </div>
                         </div>
					      </c:when>
					      <c:otherwise>
                                <div id="onpwdWrapper" class="user-safety-options-edit" style="display: none; width: 420px;">
                            <div id="pwdCon1" class="form-style-1 clearfix">
                                <form id="formEmail">
                                 <ul class="form-style-1-field ">
                                         <li class="form-style-1-item">
                                             <label id="phoneNewText" class="form-style-1-label">邮箱</label>
                                             <span class="form-style-1-value"><input id="userEmail" name="userEmail" class="input-text-style-1 form-style-1-value" type="text"></span>
                                             <div class="form-style-1-submit">
                                             <a onclick="subFormEmail('formEmail');" class="sl_o_p_u" style="color: #fff;">发送邮箱认证</a>
                                                                 </div>
                                             <span class="form-style-1-error"></span>
                                         </li>
                                        
                                     </ul>
                               
                             
                                </form>
                            </div>
                            <div id="pwdCon2" class="form-style-1 clearfix" style="display:none">
                                <div class="user-safety-options-succeed clearfix">
                                    <span class="user-safety-options-succeed-true">&nbsp;</span>
                                    <span class="user-safety-options-succeed-txt">恭喜您成功登陆密码</span>
                                </div>
                            </div>
                            
                         </div>
					      </c:otherwise>
					    </c:choose>
                            
                         </li>
                         
                         
                    <c:if test="${not empty relationMessage}">
					 <li style="width: 500px;margin: auto; " class="user-safety-options-item clearfix">
                            <div style="width: 500px;margin: auto; overflow:inherit;" class="user-safety-options-show clearfix">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong>收货地址</strong></span>
                                <div class="jbxx-k">
											<i>${relationMessage.linkman2Name}</i>
											<i>${relationMessage.linkman2Phone}</i>
											<i>${relationMessage.province}&nbsp;${relationMessage.city}&nbsp;${relationMessage.area}</i>
											<i>${relationMessage.newAddress}(${relationMessage.postcode})</i>
											<a id="delRelationMessage" delRelationMessageId="${relationMessage.id}" >删除</a><input type="hidden" value="${relationMessage.id }" id="deleteId">
										</div>
                            </div>
                            
                        </li>
                    </c:if>
                         <!-- 收货地址 -->
                         <li style="width: 500px;margin: auto;" class="user-safety-options-item clearfix">
          
                            <div style="width: 500px;margin: auto;" class="user-safety-options-show clearfix">             
                              <c:if test="${empty relationMessage }">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong>收货地址</strong></span>
                                <span class="user-safety-options-value" style="color:#F7772D;">无地址</span>
                                <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证"><a style="color: #fff;" >添加新地址</a></span>
                              </c:if>
                              
                               <c:if test="${not empty relationMessage }">
                                <span class="user-safety-options-name"><i class="icon-check"></i><strong></strong></span>
                                <span class="user-safety-options-value" style="color:#e46f6f;"></span>
                                <span id="phoneBtn1" class="user-safety-options-state" switchtext="取消认证" style=" display:block; margin-left: 124px; margin-top: 20px;"
                                ><a style="color: #fff;">修改</a></span>
                              </c:if>
                            </div>
    
                         
                        
                            <div id="phoneWrapperLation" class="user-safety-options-edit" style="display:none; width:420px;  ">
                              <!--修改收货地址-->
                                <div id="realApiCon" class="form-style-1 clearfix">
                                    <form id="relationMessageForm">
                                        <ul class="form-style-1-field ">
                                           <li class="form-style-1-item">
                                              
                                               <label class="form-style-1-label">真实姓名</label>
                                               <input id="linkman2Name" name="linkman2Name" class="input-text-style-1 form-style-1-value" type="text" value="${relationMessage.linkman2Name}">
                                           </li>
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label">手机号</label>
                                               <input id="linkman2Phone" name="linkman2Phone" class="input-text-style-1 form-style-1-value" type="text" value="${relationMessage.linkman2Phone}" onkeyup="value=value.replace(/[^0-9|]/g,'')">
                                           </li>
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label">收货地址</label>
                                           <select name="province" id="userProvince" class="input-text-style-1, input-text-style-readonly" style="width:140px;padding:0 ; color:#999;" value="relationMessage">
											</select>
											 <select style="width:140px;padding:0 ; color:#999;" name="city" id="userCity" class="input-text-style-1, input-text-style-readonly" value="relationMessage">
											</select>

                                           </li>
                                            <li class="form-style-1-item">
                                               <label class="form-style-1-label">&nbsp;</label>
                                           <select style="width:283px; padding:0 ; color:#999;" name="area" id="userArea" class="input-text-style-1, input-text-style-readonly" value="${relationMessage.area}">
											</select>
                                           </li>
                                           <li class="form-style-1-item">
                                               <label class="form-style-1-label">&nbsp;</label>
                                          <textarea  class="input-text-style-1, input-text-style-readonly" style="width:283px; padding:0 ; min-height:50px; font-size:13px; color:#999;"  name="newAddress" id="newAddress" cols="" rows="">${relationMessage.newAddress}</textarea>
                                           </li>
                                            <li class="form-style-1-item" >
                                               <label class="form-style-1-label">邮政编码</label>
                                               <input name="postcode" class="input-text-style-1 form-style-1-value" type="text" id="postcode" value="${relationMessage.postcode}" onkeyup="value=value.replace(/[^0-9|]/g,'')">
                                           </li>
                                           
                                        </ul>
                                      <div class="form-style-1-submit">
                                       <a href="#" id="saveRelationMessage" style="color: #fff;">保存</a> <a href="#" id="cancelRelation"style="background-color:#cccccc;color: #fff;" >取消</a>
                                    </div>
                                   </form>
                                </div>
                                <!--手机认证begin-->
                                
                                <!--手机认证 end-->
                                <div id="phoneCon2" class="form-style-1 clearfix" style="display:none">
                                    <div class="user-safety-options-succeed clearfix">
                                        <span class="user-safety-options-succeed-true">&nbsp;</span>
                                        <span class="user-safety-options-succeed-txt">恭喜您成功修改手机号</span>
                                    </div>
                                </div>
                                <!--手机认证状态提示end-->                                
                                
                            </div>
                        </li>
                    </ul>
    </div><!-- jb_txt -->
    
  </div><!-- J-ma-conR -->
 </div>
</div>
 	<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
var wait = 60;
$("#cancel").click(function(){
	$("#smsCode").val("");
	$("#userPhone").val("");
});
//取消保存
$("#cancelRelation").click(function(){
	$("#phoneWrapperLation").css("display","none");
})
$(function(){
	
	$('.user-safety-options-state').click(function(){
		$(this).parents('.user-safety-options-item').siblings().find('.user-safety-options-edit').slideUp();
	    $(this).parent('.user-safety-options-show').siblings('.user-safety-options-edit').slideToggle();		
	})
})

// 验证手机

function checkPhone() {
		var emailPartten = /^1[3|4|5|8|7][0-9]\d{8}$/;
		if ($("#newUserPhone").val() == "") {
			alertc("手机号不能为空");
			return false;

		} else if (emailPartten.test($("#newUserPhone").val()) == false) {
			alertc("请输入正确的手机号");
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
		var url = "${path}/verify/checkUserPhone.do";
		$
				.ajax({
					type : "post",
					data : {
						"userPhone" : $("#newUserPhone").val()
					},
					url : url,
					async : false,
					success : function(ret) {
						if (ret.code == '200') {
							if (checkPhone() == false) {
								phone = false;
								return phone;
							} else {
								phone = true;
								return phone;
							}
						} else {
							alertc("手机号已存在");
							//$("#userEmail").focus();
							phone = false;
							return phone;
						}
					}
				});
	}

//短信验证码

function sendMsg() {
		if (wait != 60) {
			return;
		}

		/* if (!phone)
			alertc("您的手机号码输入有误，请重新输入");
		else { */
			var telNo = $.trim($("#userPhone").val());
			if (!telNo) {
				alertc("请先输入您的手机号码");
				return;
			}
			$
					.ajax({
						type : "post",
						url : "${path }/verify/user/sendSms.do?tel=" + telNo,
						success : function(ret) {
							
							if (ret.code == '200') {
								alertc(ret.msg);
								time();
							} else if (ret.code == '899') {
								alertc("短信已发送,请在1分钟后在点击发送");
								time();
							}else if(ret.code=='301'){
								alertc(ret.msg);
							}
						},
					});
		/* } */
	}
function time() {
		if (wait == 0) {
			// $("#sendCode").attr("disabled", false); 
			$("#sendCode").text("获取短信验证码");
			// $("#sendCode").removeClass("dis"); 
			 $("#sendCode").attr("onclick", "sendMsg()");
			wait = 60;
		} else {
			// $("#sendCode").attr("disabled", true); 
			 //$("#sendCode").addClass("dis"); 
			 $("#sendCode").attr("onclick", null);
			$("#sendCode").text(wait + "秒后重新发送");
			wait--;
			setTimeout(function() {
				time()
			}, 1000)
		}
	}

	var codeStatus = false;
	function validateVerifyCode() {
		/* if (!checkPhone()) {
			return;
		} */
		var smsCode = $("#smsCode").val();
		if (smsCode == '') {
			alertc("请输入短信验证码");
			return;
		}
		var url = "${path}/verify/user/checkSmsCode.do";
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
					$("#duanxinTip").text("");
					codeStatus = true;
					return;
				} else if (ret.code == '302') {
alertc("验证码错误");
					codeStatus = false;
					return;
				} else if (ret.code == "101") {
					//alert("验证码已失效，请重新获取验证码");
					alertc("验证输入错误码错误");
					codeStatus = false;
					return;
				}
			}
		});
	}

	/* 手机号码认证 */
	
	function phoneCheck(){
		if($("#userPhone").val()==""){
			alertc("请输入手机号码");
			return;
		}else if(!codeStatus){
			alertc("验证码错误");
		}else if(codeStatus&&phone){
			$.ajax({
		      	dataType: 'json',
		        url:"${path}/verify/checkPhone.do",
		        data:{"phoneNum":$("#newUserPhone").val()},
		        type: 'POST',
		        async : false,
		        success: function(data) {
		       		//资料上传
		       		if(data.code=='201'){
		       			
		       			alertc("手机认证成功",reloadPage);
		       		}
		       		if(data.code=='202'){
		       			alertc(data.msg,reloadPage);
		       		}
		       		if(data.code=='203'){
		       			alertc(data.msg,reloadPage);
		       		}
		        },
		        error: function() {
		        		 alertc("认证申请失败！！！",reloadPage);
		        }
		    }); 
		}
			
		}
	//实名认证
	function formRealName(){
		if(checkAgreement()){
			if($("#userRealname").val()==''||$("#cardNumber").val()==''){
				alertc("请填写完整信息");
				
			}else{
				$.ajax({
					type : "POST",
					url : "${path}/verify/requestRealnameAttestation.do",
					data : {
						"userRealname":$("#userRealname").val(),
						"cardNumber":$("#cardNumber").val()
					},
					async : false,
					success : function(data) {
						if(data.code=='10001'){
							alertc(data.msg,reloadPage);
							/* var con = confirmc("您的实名认证申请已提交，请等待审核人员处理。");
							if(con){
								window.location.href="${path}/basics/personalData.html";
							} */
	            			
	            		}
						if(data.code=='10002'){
							alertc(data.msg,reloadPage);
							
						}
						if(data.code=='10003'){
							alertc(data.msg,reloadPage);
							
						}
						if(data.code=='1001'){
							
							alertc(data.msg,reloadPage);
						}
						
						if(data.code=='104'){
		            		alertc(data.msg,reloadPage);
		            	}
						if(data.code=='102'){
		            		alertc(data.msg,reloadPage);
		            	}
					}
				});
			}
		}
		}
			
		
		
	
	/*协议 */
	function readRegisterProtocol() {
		$('#reglaw').dialog({
			title : $('#read').html()
		});
		
	}

	function checkAgreement() {
		/* if (document.getElementById("protocol").checked == false) {
			document.getElementById("protocol").focus();
			alertc("请同意服务协议");
			return false;
		} */
		return true;
	} 
	/* 邮箱 */
	
	function subFormEmail(obj){
	/* 	alert(123);
		$.post('${path }/verify/reSendEmai.do', {
			'userEmail' : $.trim(email)
		}, function(data) {
			alertc(data.msg);
		}); */
		$.ajax({
	          dataType: 'json',
	            url:'${path }/verify/reSendEmai.do',
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	
	            	alertc(data.msg,reloadPage);
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！",reloadPage);
	            }
	        });     
	}
/* 	function subFormEmail(obj){
		url="${path}/verify/requestAddEmail.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	
            		//视频
            		if(data.code=='102'){
            			alertc("您的邮箱认证正在处理，请等待结果。");
            		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        });     
	} */

	
	$(function(){
		$("#saveRelationMessage").click(function(){
			var linkman2Name = $("#linkman2Name").val().trim();
			var linkman2Phone = $("#linkman2Phone").val().trim();
			var postcode = $("#postcode").val().trim();
			var province = $("#userProvince").val();
			var city = $("#userProvince").val();
			var area = $("#userArea").val();
			var newAddress = $("#newAddress").val().trim();
			if(linkman2Name == null || linkman2Name == ''){
				alertc("真实姓名不能为空!");
				return;
			}
			if(linkman2Phone == null || linkman2Phone == ''){
				alertc("手机号码不能为空!");
				return;
			}
			
			if(province == null || province == ''){
				alertc("省份不能为空!");
				return;
			}
			if(city == null || city == ''){
				alertc("城市不能为空!");
				return;
			}
			if(area == null || area == ''){
				alertc("地区不能为空!");
				return;
			}
			if(postcode == null || postcode == ''){
				alertc("邮政编码不能为空!");
				return;
			}
			if(newAddress == null || newAddress == ''){
				alertc("地址不能为空!");
				return;
			}
			$.ajax({
				type : "POST",
				url : "${path}/basics/saveRelationMessage.do",
				data:$("#relationMessageForm").serialize(),
				async : false,
				success : function(data) {
					if(data.code == '200'){
						reloadPage();
					}else{
						alert(data.msg);
					}		
				}
			});
		});
		
		
		//删除收货地址
		$("#delRelationMessage").click(function(){
			$.ajax({
				type : "POST",
				url : "${path}/basics/delRelationMessage.do?id="+$("#delRelationMessage").attr("delRelationMessageId"),
				async : false,
				success : function(data) {
					if(data.code == '200'){
						reloadPage();
					}else{
						alert(data.msg);
					}					
				}
			});
			
		});
		
		
	});
	
	function reloadPage() {
		window.location.reload(true);
	}
	
	
function subForm(obj) {
	var url = "${path}/basics/savaPersonal.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	if(data.code == '100'){
	            		alertc('成功');
	            	}
	            	if(data.code == '101'){
	            		alertc('失败');
	            	}
	            },
	            error: function() {
	            		 alertc("失败！！！");
	            }
	        });     
}







 $(function(){
	var province="${relationMessage.province}";
	var city="${relationMessage.city}";
	var area="${relationMessage.area}";
	if(province!=null && city!=null && area!=null){
		new PCAS("province","city","area",province,city,area);
	}else{
		new PCAS("province","city","area",'${user.userProvince}','${user.userCity}','${user.userArea}');
	}
});


</script>
</html>