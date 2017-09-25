<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath()+"";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RealnameAttestation</title>
		<script type="text/javascript" src="<%=path %>/common/js/city.js"></script>	<style type="text/css">

.input {
	width: 150px;
	height: 17px;
	border-top: 1px solid #404040;
	border-left: 1px solid #404040;
	border-right: 1px solid #D4D0C8;
	border-bottom: 1px solid #D4D0C8;
}
</style>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="attestation/o_saveRealnameAttestation" onsubmit="return validateCallback(this, backRequest);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${user.id }"> 
				<div class="pageFormContent" layoutH="56">
					
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">用户名称:</label><input type="text" readonly="readonly"  name="userAccount" value="${user.userAccount }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">真实姓名:</label><input type="text" readonly="readonly" name="userRealname" value="${user.userRealname }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">性别:</label><input type="text" readonly="readonly" name="userSex"  value="${user.userSex }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">民族:</label>
					
							<select name="userNation" disabled="disabled">
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
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">生日:</label><input type="text" readonly="readonly" value="<fmt:formatDate value="${user.userBirthday }" pattern="yyyy-MM-dd"/> " /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">证件类型:</label><input type="text" readonly="readonly"   value="${user.viewCard }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">证件号码:</label><input type="text" readonly="readonly"  value="${user.cardNumber }" /></p>
					<div class="divider"></div>
					
					<p style="height:220px;"><label style="float:left; width:130px; padding:0 5px; line-height:21px;">证件正面照:</label>
						<c:if test="${not empty user.cardFrontImg }">
							<a href="<%=path %>${user.cardFrontImg }"><img width="200" height="200" alt="${user.cardFrontImg }" src="<%=path %>${user.cardFrontImg }"></a>
						</c:if>
					</p>
					<div class="divider"></div>
					
					<p style="height:220px;"><label style="float:left; width:130px; padding:0 5px; line-height:21px;">证件背面照:</label>
						<c:if test="${not empty user.cardBackImg }">
							<a href="<%=path %>${user.cardBackImg }"><img width="200" height="200" alt="${user.cardBackImg }" src="<%=path %>${user.cardBackImg }"></a>
						</c:if>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">籍贯:</label>
								<select name="userProvince" id="userProvince" disabled="disabled"></select>
                                 <select name="userCity" id="userCity" disabled="disabled"></select>
                                 <select name="userArea" id="userArea" disabled="disabled"></select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">通过所得积分:</label><input type="text" name="creditScore" readonly="readonly"  value="${creditType.creditScore }" />
					</p><div class="divider"></div>
					
					<p style="height:18%;width: 80%;"><label style="float:left; width:130px; padding:0 5px; line-height:21px;">备注:</label><textarea cols="30" rows="3"   name="messageContent"  class="required"></textarea>
					</p><div class="divider"></div>
						
					<div id="nameDiv" style="display: none;">
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">状态:</label>
								<input name="status"  type="radio"  value="2" />审核通过
								<input name="status"  type="radio" checked="checked"  value="1" />审核不通过
						</p><div class="divider"></div>
						
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">验证码:</label>
							<input maxlength="4" style="width:60px; margin-bottom: 10px;" name="captcha" type="text" id="captcha" class="input"  />
							<img style="margin-top: -5px;" id="captchaImg" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
						</p><div class="divider"></div>
						
					</div>
					
					<div id="realnamediv" style="display: none;">
						<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">状态:</label>
								<input type="text" name="viewRealName" readonly="readonly" value="${user.viewRealName }"/>
						</p><div class="divider"></div>
					</div>
					
					

				</div>
				<div class="formBar" id="saveDiv" style="display: none;">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
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
				<div class="formBar" id="queryDiv" style="display: none;">
					<ul>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										确定
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
new PCAS("userProvince","userCity","userArea",'${user.userProvince}','${user.userCity}','${user.userArea}');
$(document).ready(function(){
	//刷新验证码
	$("#captchaImg").click();
	$("input[name='captcha']").val("");
	 var nation = "${user.userNation }";
	 if(nation){
		 $("select[name='userNation']").val(nation);
	 }
	 
	 //当实名认证状态为申请中时，可以修改
	  var status="${user.viewRealName}";
	  if(status=="申请中"){
		  $("#nameDiv").show();
		  $("#saveDiv").show();
	  }else{
		  $("#nameDiv").hide();
		  $("#realnamediv").show();
		  $("#queryDiv").show();
	  }
});
function backRequest(json) {
	$("#captchaImg").click();
	$("input[name='captcha']").val("");
	dialogAjaxDone(json);
}
</script>
</html>

