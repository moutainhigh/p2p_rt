<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="userDetail/${PRE_PATH_EDIT}savePersonalMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="userId" value="${userId}"> 
				<div class="tabsContent" style="height: 390px;">
				
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">婚姻状况:</label>
					<select name="marital" id="marital">
							    <!-- <option >---请选择婚姻状况---</option> -->
							    <option  value="0"  <c:if test="${'0' eq personalMessage.marital}">selected="selected"</c:if>>未婚</option>
							    <option  value="1"  <c:if test="${'1' eq personalMessage.marital}">selected="selected"</c:if>>已婚</option>
							    <option  value="2"  <c:if test="${'2' eq personalMessage.marital}">selected="selected"</c:if>>离异</option>
							    <option  value="3"  <c:if test="${'3' eq personalMessage.marital}">selected="selected"</c:if>>丧偶</option>
							 </select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">子 女:</label>
					<select name="children" id="children">
										<!-- <option >---请选择子女状况---</option> -->
									    <option  value="0"  <c:if test="${'0' eq personalMessage.children}">selected="selected"</c:if>>没有</option>
									    <option  value="1"  <c:if test="${'1' eq personalMessage.children}">selected="selected"</c:if>>1个</option>
									    <option  value="2"  <c:if test="${'2' eq personalMessage.children}">selected="selected"</c:if>>2个</option>
									    <option  value="3"  <c:if test="${'3' eq personalMessage.children}">selected="selected"</c:if>>3个</option>
									    <option  value="4"  <c:if test="${'4' eq personalMessage.children}">selected="selected"</c:if>>4个或4个以上</option>
								    </select>
					</p>
					<div class="divider"></div>
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">月收入:</label>
					<select name="income" id="income">
									  <!--  <option >--请选择月收入状况--</option> -->
									   <option value="1"  <c:if test="${'1' eq personalMessage.income}">selected="selected"</c:if>>1000元以下</option>
									   <option value="2"  <c:if test="${'2' eq personalMessage.income}">selected="selected"</c:if>>1000-2000元</option>
									   <option value="3" <c:if test="${'3' eq personalMessage.income}">selected="selected"</c:if>>2000-3000元</option>
									   <option value="4" <c:if test="${'4' eq personalMessage.income}">selected="selected"</c:if>>3000-4000元</option>
									   <option value="5" <c:if test="${'5' eq personalMessage.income}">selected="selected"</c:if>>4000-5000元</option>
									   <option value="6" <c:if test="${'6' eq personalMessage.income}">selected="selected"</c:if>>5000-6000元</option>
									   <option value="7" <c:if test="${'7' eq personalMessage.income}">selected="selected"</c:if>>6000-8000元</option>
									   <option value="8" <c:if test="${'8' eq personalMessage.income}">selected="selected"</c:if>>8000-10000元</option>
									   <option value="9" <c:if test="${'9' eq personalMessage.income}">selected="selected"</c:if>>1万以上</option>
									</select>
					</p>
					<div class="divider"></div>
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">社保:</label>
					<select name="social" id="social">
									  <!--  <option >---请选择社保状况---</option> -->
									   <option value="0"  <c:if test="${'0' eq personalMessage.social}">selected="selected"</c:if>>没有</option>
									   <option value="1" <c:if test="${'1' eq personalMessage.social}">selected="selected"</c:if>>有</option>
									</select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">社保电脑号:</label><input type="text" name="socialId"  value="${personalMessage.socialId }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">住房条件:</label>
					<select name="housing" id="housing">
								      <!--  <option >---请选择住房状况---</option> -->
									   <option value="1" <c:if test="${'1' eq personalMessage.housing}">selected="selected"</c:if>>租房</option>
									   <option value="2" <c:if test="${'2' eq personalMessage.housing}">selected="selected"</c:if>>自己有房</option>
									   <option value="0" <c:if test="${'0' eq personalMessage.housing}">selected="selected"</c:if>>其他</option>
									</select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">是否购车:</label>
					<select name="car" id="car">
									  <!--  <option >----请选择车状况----</option> -->
									   <option value="0" <c:if test="${'0' eq personalMessage.car}">selected="selected"</c:if>>没有</option>
									   <option value="1" <c:if test="${'1' eq personalMessage.car}">selected="selected"</c:if>>有</option>
									</select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">逾期记录:</label>
					<select name="overdue" id="overdue">
									  <!--  <option >---请选择逾期状况---</option> -->
									   <option value="0" <c:if test="${'0' eq personalMessage.overdue}">selected="selected"</c:if>>没有</option>
									   <option value="1" <c:if test="${'1' eq personalMessage.overdue}">selected="selected"</c:if>>有</option>
									</select>
					</p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
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
						<!-- <li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">
</script>
</html>