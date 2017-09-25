<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
	<form id="frm" method="post"
		action="quickBorrow/checkAudit?type=${qb.status }"
		onsubmit="return validateCallback(this, dialogAjaxDone);"
		class="pageForm required-validate">
		<input type="hidden" name="right_id" value="${right_id}" /> <input
			type="hidden" name="id" id="id" value="${qb.id }">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">贷款人:</label><input
					type="text" readonly="readonly" value="${qb.userName }" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">用户联系方式:</label><input
					type="text" readonly="readonly" value="${qb.userTel }" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">地区:</label><input
					type="text" readonly="readonly"
					value="${qb.userProvince}&nbsp;${qb.userCity}&nbsp;${qb.userArea}" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">借款金额（万元）:</label><input
					type="text" readonly="readonly" value="${qb.borrowAmount/10000}" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">借款期限:</label><input
					type="text" readonly="readonly" value="${qb.borrowPeriod}" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">利率:</label><input
					type="text" readonly="readonly"
					value=" ${qb.interestrateMin} ~ ${qb.interestrateMax}" />
			</p>
			<div class="divider"></div>

			<p style="height: 10%; width: 80%;">
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">用途:</label>
				<textarea cols="30" rows="2" readonly="readonly"> ${qb.borrowUse}</textarea>
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">申请时间:</label><input
					type="text" readonly="readonly"
					value="<fmt:formatDate value="${qb.createTime }" pattern="yyyy-MM-dd  HH:mm:ss" />" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">初审人:</label><input
					type="text" readonly="readonly" value="${user.userAccount }" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">初审时间:</label><input
					type="text" readonly="readonly"
					value="<fmt:formatDate value="${qb.fistAuditTime }" pattern="yyyy-MM-dd  HH:mm:ss" />" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">初审IP:</label><input
					type="text" readonly="readonly" value="${qb.firstAuditIp }" />
			</p>
			<div class="divider"></div>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">初审状态:</label>
				<input name="first" value="2" type="radio" checked />通过
				&nbsp;&nbsp;&nbsp;&nbsp; <input name="first" value="3" type="radio" />未通过
			</p>
			<div class="divider"></div>


			<p style="height: 20%; width: 80%;">
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">初审意见:</label>
				<textarea cols="30" rows="3" readonly="readonly">${qb.firstAuditRemark }</textarea>
			</p>
			<div class="divider"></div>
			
			<c:if test="${qb.status eq 4 || qb.status eq 5 }">
					<p>
						<label
							style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">复审人:</label><input
							type="text" readonly="readonly" value="${secUser.userAccount }" />
					</p>
					<div class="divider"></div>
		
					<p>
						<label
							style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">复审时间:</label><input
							type="text" readonly="readonly"
							value="<fmt:formatDate value="${qb.secondAuditTime }" pattern="yyyy-MM-dd  HH:mm:ss" />" />
					</p>
					<div class="divider"></div>
		
					<p>
						<label
							style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">复审IP:</label><input
							type="text" readonly="readonly" value="${qb.secondAuditIp }" />
					</p>
					<div class="divider"></div>			
			</c:if>

			<p>
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">复审状态:</label>
				<input name="secondStatus" id="secondStatus" value="4" type="radio"
					<c:if test="${qb.status eq 4}">checked="checked"</c:if> />通过
				&nbsp;&nbsp;&nbsp;&nbsp; <input name="secondStatus"
					id="secondStatus" value="5" type="radio"
					<c:if test="${qb.status ne 4}">checked="checked"</c:if> />未通过
			</p>
			<div class="divider"></div>

			<p style="height: 20%; width: 80%;">
				<label
					style="float: left; width: 100px; padding: 0 5px; line-height: 21px;">复审意见:</label>
				<textarea cols="30" rows="3" name="secondAuditRemark"
					class="required">${qb.secondAuditRemark }</textarea>
			</p>
			<div class="divider"></div>

		</div>

		<div class="formBar" id="saveDiv">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
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
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
			</ul>
		</div>

	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {

		var status = '${qb.status}';
		if (status == 1 || status == 2 ) {
			$("#saveDiv").show();
		} else {
			$("#queryDiv").show();
			$("#saveDiv").hide();
		}

	});
</script>