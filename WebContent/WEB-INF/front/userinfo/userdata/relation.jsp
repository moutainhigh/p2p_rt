<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-个人资料</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
<script src="${frontPath }/js/city.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left22" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
		<div class="setInfo fr">
					<c:set var="curNav" value="title6"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="relation" method="post">
                		<input type="hidden" name="id" id="id" value="${relation.id }">

							<div class="alerts">请填写你的联系方式</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">居住地电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.addressTel }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="addressTel" id="addressTel" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">手机号码：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.mobilePhone }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="mobilePhone" id="mobilePhone" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">居住所在省市：</td>
										<td class="bl"><span class="leftspace"></span>
											 <select name="province" id="province">
				                                </select>
				                               <select name="city" id="city">
				                               </select>
				                               <select name="area" id="area">
				                              </select>
										</td>
									</tr>
									<tr>
										<td class="ls">居住地邮编：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.postcode }" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  name="postcode" id="postcode" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">现居住地址：</td>
										<td><span class="leftspace"></span>
											<input type="text" value="${relation.newAddress }" name="newAddress" id="newAddress" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第二联系人姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman2Name }" name="linkman2Name" id="linkman2Name" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第二联系人关系：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="linkman2Relation" id="linkman2Relation" class="input08" style="width:auto">
			                                    <option value="0" <c:if test="${not empty relation.linkman2Relation and 0 eq relation.linkman2Relation}">selected="selected"</c:if>>配偶</option>
						             			<option value="1" <c:if test="${not empty relation.linkman2Relation and 1 eq relation.linkman2Relation}">selected="selected"</c:if>>父亲</option>
						             			<option value="2" <c:if test="${not empty relation.linkman2Relation and 2 eq relation.linkman2Relation}">selected="selected"</c:if>>母亲</option>
						             			<option value="3" <c:if test="${not empty relation.linkman2Relation and 3 eq relation.linkman2Relation}">selected="selected"</c:if>>子女</option>
						             			<option value="4" <c:if test="${not empty relation.linkman2Relation and 4 eq relation.linkman2Relation}">selected="selected"</c:if>>兄弟姐妹</option>
			                                </select>
										</td>
									</tr>
									<tr>
										<td class="ls">第二联系人电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman2Phone }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="linkman2Phone" id="linkman2Phone" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">第二联系人手机：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman2Tel }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="linkman2Tel" id="linkman2Tel" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第三联系人姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman3Name }" name="linkman3Name" id="linkman3Name" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第三联系人关系：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="linkman3Relation" id="linkman3Relation" style="width:auto">
			                                   	<option value="0" <c:if test="${not empty relation.linkman3Relation and 0 eq relation.linkman3Relation}">selected="selected"</c:if>>配偶</option>
						             			<option value="1" <c:if test="${not empty relation.linkman3Relation and 1 eq relation.linkman3Relation}">selected="selected"</c:if>>父亲</option>
						             			<option value="2" <c:if test="${not empty relation.linkman3Relation and 2 eq relation.linkman3Relation}">selected="selected"</c:if>>母亲</option>
						             			<option value="3" <c:if test="${not empty relation.linkman3Relation and 3 eq relation.linkman3Relation}">selected="selected"</c:if>>子女</option>
						             			<option value="4" <c:if test="${not empty relation.linkman3Relation and 4 eq relation.linkman3Relation}">selected="selected"</c:if>>兄弟姐妹</option>
				                            </select>
										</td>
									</tr>
									<tr>
										<td class="ls">第三联系人电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman3Phone }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="linkman3Phone" id="linkman3Phone" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">第三联系人手机：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman3Tel }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="linkman3Tel" id="linkman3Tel" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第四联系人姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman4Name }"  name="linkman4Name" id="linkman4Name" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">第四联系人关系：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="linkman4Relation" id="linkman4Relation" style="width:auto">
			                                   	<option value="0" <c:if test="${not empty relation.linkman4Relation and 0 eq relation.linkman4Relation}">selected="selected"</c:if>>配偶</option>
						             			<option value="1" <c:if test="${not empty relation.linkman4Relation and 1 eq relation.linkman4Relation}">selected="selected"</c:if>>父亲</option>
						             			<option value="2" <c:if test="${not empty relation.linkman4Relation and 2 eq relation.linkman4Relation}">selected="selected"</c:if>>母亲</option>
						             			<option value="3" <c:if test="${not empty relation.linkman4Relation and 3 eq relation.linkman4Relation}">selected="selected"</c:if>>子女</option>
						             			<option value="4" <c:if test="${not empty relation.linkman4Relation and 4 eq relation.linkman4Relation}">selected="selected"</c:if>>兄弟姐妹</option>
				                            </select>
										</td>
									</tr>
									<tr>
										<td class="ls">第四联系人电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman4Phone }" name="linkman4Phone" id="linkman4Phone" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">第四联系人手机：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.linkman4Tel }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="linkman4Tel" id="linkman4Tel" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">QQ：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.qq }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="qq" id="qq" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">MSN：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.msn }" name="msn" id="msn" class="input">
										</td>
									</tr>
									<tr>
										<td class="ls">旺旺：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${relation.ww }" name="ww" id="ww" class="input">
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('relation');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
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
  </div>	
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
function subForm(obj) {
	var url = "";
		//居住地电话验证
		var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if ($("#addressTel").val() == "") {
			alert("请输入居住地号码");
			$("#addressTel").focus();
			return;
		} 
		/* else if (!workTelPattern.test($("#addressTel").val())) {
			alert("请输入合法电话");
			$("#addressTel").focus();
			return;
		} */
		//手机号验证
		if ($("#mobilePhone").val() == "") {
			alert("请输入手机号码");
			$("#mobilePhone").focus();
			return;
		} else if (!workTelPattern.test($("#mobilePhone").val())) {
			alert("请输入合法电话");
			$("#mobilePhone").focus();
			return;
		}
		if ($("#linkman2Tel").val() != '') {
			if (!workTelPattern.test($("#linkman2Tel").val())) {
				alert("请输入合法电话");
				$("#linkman2Tel").focus();
				return;
			}
		}

		if ($("#linkman2Phone").val() != '') {
			if (!workTelPattern.test($("#linkman2Phone").val())) {
				alert("请输入合法电话");
				$("#linkman2Phone").focus();
				return;
			}
		}

		if ($("#linkman3Tel").val() != '') {
			if (!workTelPattern.test($("#linkman3Tel").val())) {
				alert("请输入合法电话");
				$("#linkman3Tel").focus();
				return;
			}
		}

		if ($("#linkman3Phone").val() != '') {
			if (!workTelPattern.test($("#linkman3Phone").val())) {
				alert("请输入合法电话");
				$("#linkman3Phone").focus();
				return;
			}
		}

		if ($("#linkman4Tel").val() != '') {
			if (!workTelPattern.test($("#linkman4Tel").val())) {
				alert("请输入合法电话");
				$("#linkman4Tel").focus();
				return;
			}
		}

		if ($("#linkman4Phone").val() != '') {
			if (!workTelPattern.test($("#linkman4Phone").val())) {
				alert("请输入合法电话");
				$("#linkman4Phone").focus();
				return;
			}
		}
		//验证邮编只能为数字
		var patrn = /^[0-9]{1,20}$/;
		if ($("#postcode").val() == "") {
			alert("请输入邮政编码");
			$("#postcode").focus();
			return;
		} else if (!patrn.exec($("#postcode").val())) {
			alert("邮政编码只能为数字！");
			$("#postcode").focus();
			return;
		}
		//先居住地址
		if ($("#newAddress").val() == "") {
			alert("必填，现居住地址不允许为空！");
			$("#newAddress").focus();
			return;
		}
		url = "${path}/basics/savaRelation.do";
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
new PCAS("province","city","area",'${relation.province}','${relation.city}','${relation.area}');
</script>
</html>
