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
					<c:set var="curNav" value="title8"></c:set>
            <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="education" method="post">
                		<input type="hidden" name="id" id="id" value="${education.id }">

							<div class="alerts">请填写你的教育背景</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">最高学历：</td>
										<td class="bl"><span class="leftspace"></span>
											 <select name="maxEducation" id="maxEducation" style="width:auto" >
		                                    <option value="0"
													<c:if test="${not empty education.maxEducation and 0 eq education.maxEducation}">selected="selected"</c:if>>小学</option>
												<option value="1"
													<c:if test="${not empty education.maxEducation and 1 eq education.maxEducation}">selected="selected"</c:if>>初中</option>
												<option value="2"
													<c:if test="${not empty education.maxEducation and 2 eq education.maxEducation}">selected="selected"</c:if>>高中</option>
												<option value="3"
													<c:if test="${not empty education.maxEducation and 3 eq education.maxEducation}">selected="selected"</c:if>>中专</option>
												<option value="4"
													<c:if test="${not empty education.maxEducation and 4 eq education.maxEducation}">selected="selected"</c:if>>专科</option>
												<option value="5"
													<c:if test="${not empty education.maxEducation and 5 eq education.maxEducation}">selected="selected"</c:if>>本科</option>
												<option value="6"
													<c:if test="${not empty education.maxEducation and 6 eq education.maxEducation}">selected="selected"</c:if>>硕士</option>
												<option value="7"
													<c:if test="${not empty education.maxEducation and 7 eq education.maxEducation}">selected="selected"</c:if>>博士</option>
												<option value="8"
													<c:if test="${not empty education.maxEducation and 8 eq education.maxEducation}">selected="selected"</c:if>>博士后</option>
												<option value="9"
													<c:if test="${not empty education.maxEducation and 9 eq education.maxEducation}">selected="selected"</c:if>>其他</option>
											</select>	
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">最高学历学校：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="college" id="college" value="${education.college }" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">专业：</td>
										<td><span class="leftspace"></span>
											<input type="text" name="major" id="major" value="${education.major }" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">时间：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" class="input" name="yearString" id="schoolBeginTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" readonly="readonly" value="<fmt:formatDate value="${education.dateFrom}" pattern="yyyy-MM-dd"/>"  onblur="checkDate();"/>  到 
	                     					<input class="input" type="text" name="yearString1" id="schoolEndTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" readonly="readonly" value="<fmt:formatDate value="${education.dateTo}" pattern="yyyy-MM-dd"/>" onblur="checkDate();" />
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('education');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
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
		if ($("#college").val() == "") {
			alert("必填，最高学历学校不允许为空");
			$("#college").focus();
			return;
		}
		if ($("#major").val() == "") {
			alert("必填，专业不允许为空");
			$("#major").focus();
			return;
		}
		if ($("#schoolBeginTime").val() == "") {
			alert("必填，开始时间不允许为空");
			$("#schoolBeginTime").focus();
			return;
		}
		if ($("#schoolEndTime").val() == "") {
			alert("必填，结束时间不允许为空");
			$("#schoolEndTime").focus();
			return;
		}
		url = "${path}/basics/savaEducation.do";
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
</script>
</html>