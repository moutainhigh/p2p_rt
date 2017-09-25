<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-个人资料</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left22" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
		<div class="setInfo fr">
					<c:set var="curNav" value="title2"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="house" method="post">
                		<input type="hidden" name="id" id="id" value="${house.id }">

							<div class="alerts">请填写你的房产相关信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">房产地址：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="address" name="address" class="input" value="${house.address}">
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">建筑面积：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="mianji" name="mianji" onkeyup="value=value.replace(/[^0-9|.]/g,'')" class="input" value="${house.mianji}">平方米
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">建筑年份：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="yearString" name="yearString" onclick="WdatePicker({isShowWeek:true,el:'yearString'});" readonly="readonly" class="input" value="<fmt:formatDate value="${house.year}" pattern="yyyy-MM-dd"/>" >
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">供款状况：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${house.money}" name="money" id="money" class="input" >
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">所有权人1：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${house.ownership1}" name="ownership1" id="ownership1" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">产权份额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${house.share1}" name="share1" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="share1" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">所有权人2：</td>
										<td><span class="leftspace"></span>
											<input type="text" value="${house.ownership2}" name="ownership2" id="ownership2"  class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">产权份额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${house.share2 }" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="share2" id="share2" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">贷款年限：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="loanYear" value="${house.loanYear}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="loanYear" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">每月供款：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="monthMoney" value="${house.monthMoney}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="monthMoney" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">尚欠贷款余额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="debtMoney" value="${house.debtMoney}" onkeyup="value=value.replace(/[^0-9|.]/g,'')" id="debtMoney" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">按揭银行：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="bank" value="${house.bank}" id="bank" class="input">
		                   					<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('house');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
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
		if ($("#address").val() == "") {
			alert("请输入房产地址");
			$("#address").focus();
			return;
		}
		var temp = /^\d+(\.\d+)?$/;
		if ($("#mianji").val() == "") {
			alert("请输入建筑面积");
			$("#mianji").focus();
			return false;
		} else if (temp.test($("#mianji").val()) == false) {
			alert("请输入大于0的数字");
			$("#area").focus();
			return;
		}

		if ($("#yearString").val() == "") {
			alert("请输入建筑年份");
			$("#yearString").focus();
			return;
		}

		if ($("#money").val() == "") {
			alert("请输入供款状况");
			$("#money").focus();
			return;
		}

		if ($("#ownership1").val() == "") {
			alert("请输入所有权人");
			$("#ownership1").focus();
			return;
		}

		if ($("#share1").val() == "") {
			alert("请输入产权份额");
			$("#share1").focus();
			return;
		}

		if ($("#loanYear").val() == "") {
			alert("请输入贷款年限");
			$("#loanYear").focus();
			return;
		} else if (temp.test($("#loanYear").val()) == false) {
			alert("请输入大于0的数字");
			$("#loanYear").focus();
			return;
		}

		if ($("#monthMoney").val() == "") {
			alert("请输入每月供款");
			$("#monthMoney").focus();
			return;
		} else if (temp.test($("#monthMoney").val()) == false) {
			alert("请输入大于0的数字");
			$("#monthMoney").focus();
			return;
		}

		if ($("#debtMoney").val() == "") {
			alert("请输入尚欠贷款余额");
			$("#debtMoney").focus();
			return;
		} else if (temp.test($("#debtMoney").val()) == false) {
			alert("请输入大于0的数字");
			$("#debtMoney").focus();
			return;
		}

		if ($("#bank").val() == "") {
			alert("请输入按揭银行");
			$("#bank").focus();
			return;
		}
		url = "${path}/basics/savaHouse.do";
		
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
