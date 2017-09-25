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
					<c:set var="curNav" value="title5"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="finance" method="post">
                		<input type="hidden" name="id" id="id" value="${finance.id }">

							<div class="alerts">请填写你的财务状况</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">每月无抵押贷款还款额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${finance.refundMoney}" name="refundMoney" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="refundMoney" class="input">
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">自有房产：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="houseFreedom" id="houseFreedom"  style="width:auto">
			                                  	<option value="0" <c:if test="${not empty finance.houseFreedom and 0 eq finance.houseFreedom}">selected="selected"</c:if>>购买完毕</option>
			             						<option value="1" <c:if test="${not empty finance.houseFreedom and 1 eq finance.houseFreedom}">selected="selected"</c:if>>仍在按揭</option>
	                                		</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">每月房屋按揭金额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${finance.jiejinMoney}" name="jiejinMoney" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="jiejinMoney" class="input">元
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">自有汽车：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="carFreedom" id="carFreedom" class="input08" style="width:auto">
			                                    <option value="0" <c:if test="${not empty finance.carFreedom and 0 eq finance.carFreedom}">selected="selected"</c:if>>购买完毕</option>
			             						<option value="1" <c:if test="${not empty finance.carFreedom and 1 eq finance.carFreedom}">selected="selected"</c:if>>仍在按揭</option>
			                                </select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">每月汽车按揭金额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${finance.carJiejin}" name="carJiejin" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="carJiejin" class="input">元
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">每月信用卡还款金额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${finance.refundXinyong}" name="refundXinyong" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="refundXinyong" class="input">元
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('finance');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
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
		var temp = /^\d+(\.\d+)?$/;
		if ($("#refundMoney").val() == "") {
			alert("请输入每月无抵押贷款还款额");
			$("#refundMoney").focus();
			return;
		} else if (temp.test($("#refundMoney").val()) == false) {
			alert("请输入大于0的数字");
			$("#refundMoney").focus();
			return;
		}
		if ($("#jiejinMoney").val() == "") {
			alert("请输入每月房屋按揭金额");
			$("#jiejinMoney").focus();
			return;
		} else if (temp.test($("#jiejinMoney").val()) == false) {
			alert("请输入大于0的数字");
			$("#jiejinMoney").focus();
			return;
		}

		if ($("#carJiejin").val() == "") {
			alert("请输入每月汽车按揭金额");
			$("#carJiejin").focus();
			return;
		} else if (temp.test($("#carJiejin").val()) == false) {
			alert("请输入大于0的数字");
			$("#carJiejin").focus();
			return;
		}

		if ($("#refundXinyong").val() == "") {
			alert("请输入每月信用卡还款金额");
			$("#refundXinyong").focus();
			return;
		} else if (temp.test($("#refundXinyong").val()) == false) {
			alert("请输入大于0的数字");
			$("#refundXinyong").focus();
			return;
		}
		url = "${path}/basics/savaFinance.do";
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
