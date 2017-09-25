<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=path %>/common/js/city.js"></script>
</head>
<body>
	<div class="pageContent">
		<form id="frm" method="post" enctype="multipart/form-data"
			action="user/${PRE_PATH_EDIT }saveUser"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="right_id" value="${right_id}" /> <input
				type="hidden" name="id" value="${user.id }">
			<div class="pageFormContent" layoutH="56">

				<p>
					<label>用户名:</label><input value="${user.userAccount }"
						readonly="readonly" />
				</p>
				<div class="divider"></div>

				<p>
					<label>真实姓名:</label><input value="${user.userRealname }"
						readonly="readonly" />
				</p>
				<div class="divider"></div>

				<p>
					<label>性别:</label> <input readonly="readonly"
						value="${user.userSex }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>生日:</label><input readonly="readonly"
						value="<fmt:formatDate value="${user.userBirthday }" pattern="yyyy-MM-dd"/>" />
				</p>
				<div class="divider"></div>


				<p>
					<label>邮箱:</label><input readonly="readonly"
						value="${user.userEmail }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>电话:</label><input readonly="readonly"
						value="${user.userTel }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>手机:</label><input readonly="readonly"
						value="${user.userPhone }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>QQ:</label><input readonly="readonly"
						value="${user.userQq }" />
				</p>
				<div class="divider"></div>
				<p>
					<label>民族:</label> <select name="userNation" disabled="disabled">
						<option value="">-请选择民族-</option>
						<option value="汉族">汉族</option>
						<option value="壮族">壮族</option>
						<option value="满族">满族</option>
						<option value="回族">回族</option>
						<option value="苗族">苗族</option>
						<option value="维吾尔族">维吾尔族</option>
						<option value="土家族">土家族</option>
						<option value="彝族">彝族</option>
						<option value="蒙古族">蒙古族</option>
						<option value="藏族">藏族</option>
						<option value="布依族">布依族</option>
						<option value="侗族">侗族</option>
						<option value="瑶族">瑶族</option>
						<option value="朝鲜族">朝鲜族</option>
						<option value="白族">白族</option>
						<option value="哈尼族">哈尼族</option>
						<option value="哈萨克族">哈萨克族</option>
						<option value="黎族">黎族</option>
						<option value="傣族">傣族</option>
						<option value="畲族">畲族</option>
						<option value="傈傈族">傈傈族</option>
						<option value="仡佬族">仡佬族</option>
						<option value="东乡族">东乡族</option>
						<option value="高山族">高山族</option>
						<option value="拉祜族">拉祜族</option>
						<option value="水族">水族</option>
						<option value="佤族">佤族</option>
						<option value="纳西族">纳西族</option>
						<option value="羌族">羌族</option>
						<option value="土族">土族</option>
						<option value="仫佬族">仫佬族</option>
						<option value="锡伯族">锡伯族</option>
						<option value="柯尔克孜族">柯尔克孜族</option>
						<option value="达斡尔族">达斡尔族</option>
						<option value="景颇族">景颇族</option>
						<option value="毛南族">毛南族</option>
						<option value="撒拉族">撒拉族</option>
						<option value="布朗族">布朗族</option>
						<option value="塔吉克族">塔吉克族</option>
						<option value="阿昌族">阿昌族</option>
						<option value="普米族">普米族</option>
						<option value="鄂温克族">鄂温克族</option>
						<option value="怒族">怒族</option>
						<option value="京族">京族</option>
						<option value="基诺族">基诺族</option>
						<option value="德昂族">德昂族</option>
						<option value="保安族">保安族</option>
						<option value="俄罗斯族">俄罗斯族</option>
						<option value="裕固族">裕固族</option>
						<option value="乌孜别克族">乌孜别克族</option>
						<option value="门巴族">门巴族</option>
						<option value="鄂伦春族">鄂伦春族</option>
						<option value="独龙族">独龙族</option>
						<option value="塔塔尔族">塔塔尔族</option>
						<option value="赫哲族">赫哲族</option>
						<option value="珞巴族">珞巴族</option>
					</select>
				</p>
				<div class="divider"></div>


				<p>
					<label>籍贯:</label> <select name="userProvince" id="userProvince"
						disabled="disabled"></select> <select name="userCity"
						id="userCity" disabled="disabled"></select> <select
						name="userArea" id="userArea" disabled="disabled"></select>
				</p>
				<div class="divider"></div>

				<p>
					<label>地址:</label><input readonly="readonly"
						value="${user.userAddress }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>担保资格:</label><input readonly="readonly"
						value="${user.viewVouch }" />
				<div class="divider"></div>

				<p>
					<label>是否解锁:</label><input readonly="readonly"
						value="${user.viewLock }" />

				</p>
				<div class="divider"></div>

				<p>
					<label>邀请好友:</label><input readonly="readonly"
						value="${user.inviteUserid }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>邀请注册提成:</label><input readonly="readonly"
						value="${user.inviteMoney }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>证件类型:</label><input readonly="readonly"
						value="${user.viewCard }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>证件号:</label><input readonly="readonly"
						value="${user.cardNumber}" />
				</p>
				<div class="divider"></div>

				<p <c:if test="${not empty user.cardFrontImg }">style="height: 300px; line-height: 300px;"</c:if>>
					<label>证件正面照:</label>
					<c:if test="${not empty user.cardFrontImg }">
						<a href="<%=path %>${user.cardFrontImg}" target="_blank"><img
							src="<%=path %>${user.cardFrontImg}" width="400" height="300" /></a>
					</c:if>
					<c:if test="${ empty user.cardFrontImg }">
									无
					</c:if>
				<div class="divider"></div>

				<p <c:if test="${not empty user.cardBackImg }">style="height: 300px; line-height: 300px;"</c:if> >
					<label>证件背面照:</label>
					<c:if test="${not empty user.cardBackImg }">
						<a href="<%=path %>${user.cardBackImg}" target="_blank"><img
							src="<%=path %>${user.cardBackImg}" width="400" height="300" /></a>
					</c:if>
					<c:if test="${ empty user.cardBackImg }">
									无
					</c:if>
				<div class="divider"></div>

				<p>
					<label>实名认证:</label><input readonly="readonly"
						value="${user.viewRealName }" />
				</p>

				<div class="divider"></div>

				<p>
					<label>手机认证:</label><input readonly="readonly"
						value="${user.viewPhone }" />
				</p>

				<div class="divider"></div>

				<p>
					<label>邮箱认证:</label><input readonly="readonly"
						value="${user.viewEmail }" />
				</p>

				<div class="divider"></div>

				<p>
					<label>视频认证:</label><input readonly="readonly"
						value="${user.viewVideo }" />
				</p>

				<div class="divider"></div>

				<p>
					<label>现场认证:</label><input readonly="readonly"
						value="${user.viewScene }" />
				</p>

				<div class="divider"></div>


				<p>
					<label>资料完整度:</label><input readonly="readonly"
						value="${user.userIntegral }" />
				</p>
				<div class="divider"></div>

				<p <c:if test="${not empty user.avatarImg }">style="height: 120px; line-height: 120px;"</c:if>>
					<label>头像:</label>
					<c:if test="${not empty user.avatarImg }">
						<img src="<%=path %>${user.avatarImg}" />
					</c:if>
					<c:if test="${ empty user.avatarImg }">
						无
					</c:if>
				<div class="divider"></div>

				<p>
					<label>问题:</label><input readonly="readonly"
						value="${user.userQuestion }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>答案:</label><input readonly="readonly"
						value="${user.userAnswer }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>提醒设置:</label><input readonly="readonly"
						value="${user.userRemind }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>隐私设置:</label><input readonly="readonly"
						value="${user.userPrivacy }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>添加时间:</label><input readonly="readonly"
						value="<fmt:formatDate value="${user.userAddtime }" pattern="yyyy-MM-dd"/>" />
				</p>
				<div class="divider"></div>

				<p>
					<label>添加IP:</label><input readonly="readonly"
						value="${user.userAddip }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>登陆时间:</label><input readonly="readonly"
						value="<fmt:formatDate value="${user.userLogintime }" pattern="yyyy-MM-dd"/>" />
				</p>
				<div class="divider"></div>

				<p>
					<label>登陆IP:</label><input readonly="readonly"
						value="${user.userLoginip }" />
				</p>
				<div class="divider"></div>

				<p>
					<label>是否系统管理员:</label><input readonly="readonly"
						value="${user.viewSystem }" />

				</p>
				<div class="divider"></div>

			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	 var nation = "${user.userNation }";
	 if(nation){
		 $("select[name='userNation']").val(nation);
	 }
	 var type="${user.type.name}";
	 if(type==null||type==""){
		 $("#type").val("管理员用户");
	 } 
});
new PCAS("userProvince","userCity","userArea",'${user.userProvince}','${user.userCity}','${user.userArea}');

</script>
</html>