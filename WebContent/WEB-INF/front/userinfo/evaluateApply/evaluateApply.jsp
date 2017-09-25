<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-额度申请</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<style type="text/css">
.user_right_border {
  font-size: 13px;
  height: auto;
  line-height: 25px;
  margin: 10px 0;
  overflow: hidden;
  text-align: left;
  width: 100%;
}
.user_right_border .e {
  float: left;
  padding: 4px 5px 0 0;
  text-align: right;
  width: 220px;
}
</style>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_3_left13" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<!-- right-->    
    <div class="setInfo fr">
        <c:set var="curNav" value="title5"></c:set>
        <%@include file="titleNav.jsp"%>
<div class="tab_info">
   	<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
   	<!--额度申请 开始-->
				<div class="alerts" id="tip-text">
					<span style="color: red;">* 温馨提示：额度申请后
					无论申请是否批准 必须隔一个月后才能再次申请，每个月只能申请一次如有问题,请与我们联系。<br><br></span>
					<c:if test="${apply.evaluateapplyStatus==1 }">您的额度申请正在审核中，请等待结果。</c:if>
				</div>

				<div id="divs"
					style="<c:if test="${apply.evaluateapplyStatus==1 }">display: none;</c:if>">
					<form action="${path }/verify/toEvaluateApply.do" method="post"
						name="form1" id="form1">
						<table class="tableLs siteInfoWidth1" id="divs"
					style="<c:if test="${apply.evaluateapplyStatus==1 }">display: none;</c:if>">
		                    <tbody>
		                    	<tr>
		                    		<td class="ls">申请者：</td>
		                    		<td><span class="leftspace"></span>
		                    		${FRONT_USER_SESSION.userAccount}
		                    		</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="ls">申请类型：</td>
		                    		<td><span class="leftspace"></span>
		                    		<select name="evaluateapplyType" id="evaluateapplyType"
										disabled="disabled">
										<option value="1">借款信用额度</option>
									</select>
		                    		</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="ls">申请金额：</td>
		                    		<td><span class="leftspace"></span>
		                    		<input type="text" id="amountApply" name="amountApply"
									onkeyup="value=value.replace(/[^0-9]/g,'')" class="input" /> <span
									class="tip error" style="display: none;" id="idCardNoTip"></span>
		                    		</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="ls">详细说明：</td>
		                    		<td><span class="leftspace"></span>
		                    		<textarea rows="7" cols="45" id="evaluateapplyContent"
									name="evaluateapplyContent"></textarea>
								<span class="tip error" style="display: none;" id="idCardNoTip"></span>
		                    		</td>
		                    	</tr>
		                    <tr>
		                    <td colspan="2"><input type="button" class="submit_btn" style="margin-left: 115px;cursor: pointer;" value="确认提交" onclick="subForm('form1');" size="30">
		                     </td></tr>
		                </tbody></table>
					</form>
				</div>
				<div class="siteInfoWidth ht1"></div>  <!--  占用高度20px    -->
				<table  class="siteInfoWidth1 tableDate">
					<thead>
						<tr class="tdwait">
							<td class="tdwait1">申请时间</td>
							<td>申请类型</td>
							<td class="tdwait1">申请金额(元)</td>
							<td>通过金额(元)</td>
							<td class="tdwait1">备注说明</td>
							<td>状态</td>
							<td class="tdwait1">审核时间</td>
							<td>审核备注</td>
						</tr>
					</thead>
					<tbody id="evaluateApplyTable">

					</tbody>
				</table>
	</div>
</div>
    
    </div>
  </div>	
    
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	var msg = "${error}";
	if (msg == '80') {
		alertc("您还不是Vip用户，请先申请Vip用户", function() {
			window.location.href = "${path}/verify/userAttestation.do"
		});
	}
	function subForm(obj) {
		var amountApply = $("#amountApply").val();
		var evaluateapplyContent = $("#evaluateapplyContent").val();
		if (amountApply == null || amountApply == "") {
			alertc("额度申请不允许为空");
			$("#amountApply").focus();
			return;
		} else if (evaluateapplyContent == null || evaluateapplyContent == "") {
			alertc("额度申请详细说明不允许为空");
			$("#evaluateapplyContent").focus();
			return;
		} else {
			$
					.ajax({
						dataType : 'json',
						url : "${path }/verify/toEvaluateApply.do",
						data : $("#" + obj).serialize(),
						type : 'POST',
						success : function(data) {
							if (data.code == '200') {
								$("#tip-text").html("您的额度申请正在审核中，请等待结果。");
								$("#divs").hide();
								var data = {};
								getPageTable(data, "${path }/verify/evaluateApplyPage.do",
										generateEvaluateApplyTable, "evaluateApplyTable");
							}
							if (data.code == '201') {
								alertc(data.msg, reloadPage);
							}
							if (data.code == '202') {
								alertc(data.msg);
							}
							if (data.code == '203') {
								alertc(
										data.msg,
										function() {
											window.location.href = "${path}/verify/userAttestation.do"
										});
							}
						},
						error : function() {
							alertc("额度申请失败！！！", reloadPage);
						}
					});
		}

	}
	function reloadPage() {
		window.location.reload(true);
	}

	$(function() {
		var data = {};
		getPageTable(data, "${path }/verify/evaluateApplyPage.do",
				generateEvaluateApplyTable, "evaluateApplyTable");
	});

	//拼成表格
	function generateEvaluateApplyTable(data) {
		var type = '';
		if (data.evaluateapplyType == 1) {
			type = '借款信用额度';
		}
		var vtime = '--';
		if(data.verifyDatetime != null){
			vtime = toDate(data.verifyDatetime, 'yyyy-MM-dd');
		}
		var passAmount = '--';
		if(data.evaluateapplyStatus != 1){
			passAmount = data.amount;
		}
		var htmlStr = '<tr>';
		htmlStr += "<td>" + toDate(data.evaluateapplyAddtime, 'yyyy-MM-dd')
				+ "</td>" + "<td>" + type + "</td>" + "<td>" + data.amountApply
				+ "</td>" + "<td>" + passAmount + "</td>" + "<td>"
				+ data.evaluateapplyRemark + "</td>" + "<td>"
				+ data.viewApplyStatus + "</td>" + "<td>"
				+ vtime + "</td>"
				+ "<td title = "+data.verifyRemark+">" + data.verifyRemark
				+ "</td>";
		htmlStr += "</tr>";
		return htmlStr;
	};
</script>
</html>