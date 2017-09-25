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
					<c:set var="curNav" value="title4"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="privatebusiness" method="post">
                		<input type="hidden" name="id" id="id" value="${privatebusiness.id }">

							<div class="alerts">请填写您的业主信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">私营企业类型：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="privateType" id="privateType">
			                                    <option value="0" <c:if test="${not empty privatebusiness.privateType and 0 eq privatebusiness.privateType}">selected="selected"</c:if>>农，林，牧，渔业</option>
						              			<option value="1" <c:if test="${not empty privatebusiness.privateType and 1 eq privatebusiness.privateType}">selected="selected"</c:if>>制造业</option>
						              			<option value="2" <c:if test="${not empty privatebusiness.privateType and 2 eq privatebusiness.privateType}">selected="selected"</c:if>>电子，燃气及水的生产和供应业</option>
						              			<option value="3" <c:if test="${not empty privatebusiness.privateType and 3 eq privatebusiness.privateType}">selected="selected"</c:if>>建筑业</option>
						              			<option value="4" <c:if test="${not empty privatebusiness.privateType and 4 eq privatebusiness.privateType}">selected="selected"</c:if>>交通运输，仓储和邮政业</option>
						              			<option value="5" <c:if test="${not empty privatebusiness.privateType and 5 eq privatebusiness.privateType}">selected="selected"</c:if>>信息传输，计算机服务和软件业</option>
						              			<option value="6" <c:if test="${not empty privatebusiness.privateType and 6 eq privatebusiness.privateType}">selected="selected"</c:if>>批发和零售业</option>
						              			<option value="7" <c:if test="${not empty privatebusiness.privateType and 7 eq privatebusiness.privateType}">selected="selected"</c:if>>金融业</option>
						              			<option value="8" <c:if test="${not empty privatebusiness.privateType and 8 eq privatebusiness.privateType}">selected="selected"</c:if>>地产业</option>
						              			<option value="9" <c:if test="${not empty privatebusiness.privateType and 9 eq privatebusiness.privateType}">selected="selected"</c:if>>采矿业</option>
						              			<option value="10" <c:if test="${not empty privatebusiness.privateType and 10 eq privatebusiness.privateType}">selected="selected"</c:if>>租凭和商务服务业</option>
						              			<option value="11" <c:if test="${not empty privatebusiness.privateType and 11 eq privatebusiness.privateType}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="12" <c:if test="${not empty privatebusiness.privateType and 12 eq privatebusiness.privateType}">selected="selected"</c:if>>水利，环境和公共设备管理业</option>
						              			<option value="13" <c:if test="${not empty privatebusiness.privateType and 13 eq privatebusiness.privateType}">selected="selected"</c:if>>居民服务和其他服务业</option>
						              			<option value="14" <c:if test="${not empty privatebusiness.privateType and 14 eq privatebusiness.privateType}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="15" <c:if test="${not empty privatebusiness.privateType and 15 eq privatebusiness.privateType}">selected="selected"</c:if>>教育</option>
						              			<option value="16" <c:if test="${not empty privatebusiness.privateType and 16 eq privatebusiness.privateType}">selected="selected"</c:if>>卫生，社会保障和社会福利业</option>
						              			<option value="17" <c:if test="${not empty privatebusiness.privateType and 17 eq privatebusiness.privateType}">selected="selected"</c:if>>文化，体育和娱乐业</option>
						              			<option value="18" <c:if test="${not empty privatebusiness.privateType and 18 eq privatebusiness.privateType}">selected="selected"</c:if>>公共管理和社会组织</option>
						              			<option value="19" <c:if test="${not empty privatebusiness.privateType and 19 eq privatebusiness.privateType}">selected="selected"</c:if>>国际组织</option>
				                           </select>
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">成立日期：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text"name="yearString" id="completeTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" class="input" readonly="readonly" value="<fmt:formatDate value="${privatebusiness.establishTime}" pattern="yyyy-MM-dd"/>">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">经营场所：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.runSite}" id="runSite" name="runSite" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">租金：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text"  value="${privatebusiness.rentMoney}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="rentMoney" name="rentMoney" class="input">(元)
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">租期：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.rentDate}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="rentDate" name="rentDate" class="input">月
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">税务编号：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.taxId}" id="taxId" name="taxId" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工商登记号：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.registerId}" id="registerId" name="registerId" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">全年盈利/亏损额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.profit}" id="profit" name="profit" class="input">元（年度）
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">雇员人数：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${privatebusiness.hireNumber}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="hireNumber" name="hireNumber" class="input">人
		                   					<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('privatebusiness');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
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
		if ($("#completeTime").val() == "") {
			alert("请输入成立日期");
			$("#completeTime").focus();
			return;
		}

		if ($("#runSite").val() == "") {
			alert("请输入经营场所");
			$("#runSite").focus();
			return;
		}

		var temp = /^\d+(\.\d+)?$/;
		if ($("#rentMoney").val() == "") {
			alert("请输入租金");
			$("#rentMoney").focus();
			return;
		} else if (temp.test($("#rentMoney").val()) == false) {
			alert("请输入大于0的数字");
			$("#rentMoney").focus();
			return;
		}

		var rentDatePattern = /^\d+$/;
		if ($("#rentDate").val() == "") {
			alert("请输入租期");
			$("#rentDate").focus();
			return;
		} else if (rentDatePattern.test($("#rentDate").val()) == false) {
			alert("请输入正整数");
			$("#rentDate").focus();
			return;
		}

		if ($("#taxId").val() == "") {
			alert("请输入税务编号");
			$("#taxId").focus();
			return;
		}

		if ($("#registerId").val() == "") {
			alert("请输入工商登记号");
			$("#registerId").focus();
			return;
		}

		if ($("#profit").val() == "") {
			alert("请输入全年盈利/亏损额");
			$("#profit").focus();
			return;
		}

		var hireNumberPattern = /^[1-9]*[1-9][0-9]*$/;
		if ($("#hireNumber").val() == "") {
			alert("请输入雇员人数");
			$("#hireNumber").focus();
			return;
		} else if (hireNumberPattern.test($("#hireNumber").val()) == false) {
			alert("请输入正整数");
			$("#hireNumber").focus();
			return;
		}
		url = "${path}/basics/savaPrivateBusiness.do";
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
