<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
String basePath = path+"/common/views";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EducationMessage</title>
	<script type="text/javascript" src="<%=basePath %>/js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post"   action="userDetail/o_saveRelationMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="id" value="${userId }"> 
				<input type="hidden" name="id" id="id" value="${rg.id }"> 
				<div class="pageFormContent" style="height: 376px;" >
						<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">居住地电话:</label><input  maxlength="20" type="text" name="addressTel" class="phone required"  value="${rg.addressTel }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">手机号码:</label><input  maxlength="20" type="text" name="mobilePhone" class="phone required"  value="${rg.mobilePhone }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">居住所在省市:</label>
						<select name="province" id="province"  style="width:auto">
		                                </select>
		                               <select name="city" id="city" style="width:auto">
		                               </select>
		                               <select name="area" id="area"  style="width:auto">
		                               </select>
					</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">居住地邮编:</label><input maxlength="20" type="text" class="required" name="postcode" value="${rg.postcode }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">现居住地址:</label><input maxlength="200" type="text" class="required" name="newAddress" value="${rg.newAddress }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第二联系人姓名:</label><input maxlength="50" type="text" name="linkman2Name" value="${rg.linkman2Name }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第二联系人关系:</label><input maxlength="10"  type="text" name="linkman2Relation"  value="${rg.linkman2Relation }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第二联系人联系电话:</label><input maxlength="20" class="phone" type="text" name="linkman2Tel"  value="${rg.linkman2Tel }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第二联系人联系手机:</label><input maxlength="20" class="phone" type="text" name="linkman2Phone"  value="${rg.linkman2Phone }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第三联系人姓名:</label><input maxlength="50" type="text" name="linkman3Name" value="${rg.linkman3Name }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第三联系人关系:</label><input maxlength="10" type="text" name="linkman3Relation" value="${rg.linkman3Relation }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第三联系人联系电话:</label><input maxlength="20" class="phone" type="text" name="linkman3Tel" value="${rg.linkman3Tel }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第三联系人联系手机:</label><input maxlength="20" class="phone" type="text" name="linkman3Phone"  value="${rg.linkman3Phone }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第四联系人姓名:</label><input maxlength="50"  type="text" name="linkman4Name"  value="${rg.linkman4Name }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第四联系人关系:</label><input maxlength="10"  type="text" name="linkman4Relation"  value="${rg.linkman4Relation }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第四联系人联系电话:</label><input maxlength="20" class="phone" type="text" name="linkman4Tel" value="${rg.linkman4Tel }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">第四联系人联系手机:</label><input maxlength="20" class="phone" type="text" name="linkman4Phone" value="${rg.linkman4Phone }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">MSN:</label><input maxlength="50"  type="text" name="msn" value="${rg.msn }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">QQ:</label><input maxlength="50"  type="text" name="qq" value="${rg.qq }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">旺旺:</label><input  maxlength="50" type="text" name="ww" value="${rg.ww }" /></p>
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
									<button type="button" class="close" >
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
new PCAS("province","city","area",'${rg.province}','${rg.city}','${rg.area}');
</script>
</html>

