<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<form id="frm" method="post" enctype="multipart/form-data"  action="user/${PRE_PATH_EDIT }toAdminSaveUser" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${user.id }"> 
				
				<div class="pageFormContent" layoutH="56">
					
					<p><label>用户名:</label><input name="userAccount" value="${user.userAccount }" minlength="4" class="required"  type="text" alt="请输入用户名" size="30"  /></p>
					<div class="divider"></div>
					
					<p><label>真实姓名:</label><input name="userRealname" value="${user.userRealname }"   class="required"  type="text" alt="请输入用户真实姓名" size="30"   /></p>
					<div class="divider"></div>
					
					<p><label>性别:</label>
								<input name="userSex" id="userSex" type="radio" checked="checked" value="男"/>男
								<input name="userSex" id="userSex" type="radio" value="女"/>女
					</p>
					<div class="divider"></div>
					
				    <p><label>生日:</label>
						<input class="date textInput readonly required" value="<fmt:formatDate value="${user.userBirthday }" pattern="yyyy-MM-dd"/>"  type="text" size="30" readonly="readonly" id="uirthday"  name="uirthday"/>
						<input type="hidden" name="userBirthday" id="userBirthday"/>
					<div class="divider"></div>   
					
					
					<p><label>邮箱:</label><input name="userEmail" value="${user.userEmail }"   type="text" class="email" alt="请输入邮箱" size="30" /></p>
					<div class="divider"></div>
					
					<p><label>电话:</label><input name="userTel"  value="${user.userTel }"   type="text" class="phone" alt="请输入电话" size="30"   /></p>
					<div class="divider"></div>
					
					<p><label>手机:</label><input name="userPhone" value="${user.userPhone }"  type="text" class="phone" alt="请输入手机" size="30"  /></p>
					<div class="divider"></div>
					
					<p><label>QQ:</label><input name="userQq" value="${user.userQq }"  class="digits"  type="text" alt="请输入qq" size="30" /></p>
					<div class="divider"></div>
					
					<p><label>证件类型:</label>
						<select name="cardType" >
								<option value="">-请选择证件类型-</option>
							<c:forEach var="card" items="${USER_ALL_CARD }">
								<option value="${card.key }">${card.value }</option>
							</c:forEach>
						</select>
						
					</p>
					<div class="divider"></div>
					
					<p><label>证件号码:</label><input name="cardNumber" value="${user.cardNumber }" minlength="8" maxlength="18"  type="text" alt="请输入证件号码" size="30" /></p>
					<div class="divider"></div>
					
					<p><label>民族:</label>						
							<select name="userNation">
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
					
					
					 <p><label>籍贯:</label>
								 <select name="userProvince" id="userProvince"></select>
                                 <select name="userCity" id="userCity"></select>
                                 <select name="userArea" id="userArea"></select>
								</p>
					<div class="divider"></div> 
					
					<p><label>地址:</label><input name="userAddress" value="${user.userAddress }"  type="text" alt="请输入地址" size="30"  value="" /></p>
					<div class="divider"></div>
					
					<p><label>担保资格:</label>
							<input name="userVouch"   type="radio" checked="checked"  value="0" />否
							<input name="userVouch"   type="radio"   value="1" />是
						</p>
					<div class="divider"></div>
					
					<div id="islock" style="display: none;">
					<p><label>是否锁定:</label>
							<input name="userIslock"  type="radio" checked="checked" value="0" />否
							<input name="userIslock"  type="radio"  value="1" />是
					</p>
					<div class="divider"></div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="submitFrm()">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
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
	 var sex="${user.userSex }";
	 var vouch="${user.userVouch}";
	 var lock="${user.userIslock}";
	 var card="${user.cardType }";
	 if(sex){
		 $("input[name='userSex'][value='"+sex+"']").attr("checked",true);
	 }
	 if(nation){
		 $("select[name='userNation']").val(nation);
	 }
	 if(vouch){
		 $("input[name='userVouch'[value='"+vouch+"']").attr("checked",true);
	 }
	 if(lock){
		 $("input[name='userIslock'][value='"+lock+"']").attr("checked",true);
	 }
	 if(card){
		 $("select[name='cardType']").val(card);
	 }
	//islock
	if($("#id").val()==""||$("#id").val()==null){
		$("#islock").hide();
	}else{
		$("#islock").show();
	}
});
 function submitFrm(){
	if($("#uirthday").val() != ""){
		$("#userBirthday").val($("input[name='uirthday']").val()+" 00:00:00");
	$("#frm").submit();
	}
}
new PCAS("userProvince","userCity","userArea",'${user.userProvince}','${user.userCity}','${user.userArea}');
</script>
</html>