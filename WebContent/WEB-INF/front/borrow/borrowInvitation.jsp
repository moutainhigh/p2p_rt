<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<script type="text/javascript"src="${frontPath }/js/browser.js"></script>
<title>${showTitle }</title>
<style type="text/css">
.wydk-qh1{_min-width:100px; min-width:100px;  height:30px; background-color: #d9d9d9;line-height:30px; text-align:center; margin-right:5px; float:left; font-size:14px;}
.wydk-qh1 a:hover{ background-color:#df3130; display:block; color:#fff;}
.wydk-qh1 .hover{ background-color:#df3130; display:block; color:#fff;}
</style>
<!--[if IE 6]>
<script type="text/javascript" src="/v2/local/js/common/DD_belatedPNG.js"></script>
<![endif]-->
<link rel="stylesheet"
	href="${frontPath }/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${frontPath }/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${frontPath }/js/kindeditor/lang/zh_CN.js"></script>
<script>
document.onkeydown=function(event){
	e = event ? event : (window.event ? window.event : null);
	if(e.keyCode==13){
		$('#submitBtn').trigger('click');
	}
};
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="borrowContent"]', {
		cssPath :'${frontPath}/js/kindeditor/plugins/code/prettify.css',
		allowPreviewEmoticons : false,
		resizeMode : 1,
		allowImageUpload : true,
		imageUploadJson : '${path}/upload/editorImgForKindEditor.do',
		uploadJson : '${path}/upload/editorImgForKindEditor.do',
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
		afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
		}
	});
});

	//是否为天标，借款期限改变
	function changeIsDay() {
		var val = $("input[name='isDay']:checked").val();
		if (val == 1) { //天标
			$("#desc").show();
			initBorrowTimeLimit(30, "天");
			$("#dayAlert").show();
		} else {
			$("#desc").hide();
			initBorrowTimeLimit(12, "个月");
			$("#dayAlert").hide();
		}
	}

	function initBorrowTimeLimit(len, show) {
		var timeLimitSelect = $("select[name='borrowTimeLimit']");
		timeLimitSelect.html(getOption("", "--贷款期限--"));
		for (i = 1; i <= len; i++) {
			timeLimitSelect.html(timeLimitSelect.html()
					+ getOption(i, i + show));
		}
	}

	function getOption(value, text) {
		return "<option value=\""+value+"\">" + text + "</option>";
	}

	//设置投资奖励
	var lastAwardType = "";
	function changeTenderAward(val) {
		if (lastAwardType == val) {
			return;
		}
		lastAwardType = val;
		$("#rateDiv").find("input").val("");
		$("#partDiv").find("input").val("");
		$("#rateDiv").find("input").removeClass("required").removeClass(
				"number");
		$("#partDiv").find("input").removeClass("required").removeClass(
				"number");
		if (val == 1) {
			$("#rateDiv").hide();
			$("#partDiv").hide();
		}
		if (val == 2) {
			$("#rateDiv").show();
			$("#partDiv").hide();
			$("#rateDiv").find("input").addClass("required").addClass("number");
			$("#partDiv").find("input").removeClass("required").removeClass(
					"number");
		}
		if (val == 3) {
			$("#rateDiv").hide();
			$("#partDiv").show();
			$("#rateDiv").find("input").removeClass("required").removeClass(
					"number");
			$("#partDiv").find("input").addClass("required").addClass("number");
		}
	}

	function fromSubmit() {
		editor.sync();
		$("#borrowFrm").validate({
			rules : {
				borrowSum : {
					required : true,
					number : true
				},
				annualInterestRate : {
					required : true,
					number : true
				},
				borrowTimeLimit : {
					required : true,
					number : true
				},
				borrowUse : {
					required : true,
					number : true
				},
				validTime : {
					required : true,
					number : true
				},
				borrowTitle : {
					required : true
				},
				captcha : {
					required : true
				}
			}
		});
		if ($("#borrowFrm").valid()) {
			subAjax($("#borrowFrm").serialize());
		}
	}
</script>
</head>

<body>
	<jsp:include flush="false" page="/top.do"></jsp:include>
	<div class="xian-dd"></div>
	<div class="Position">
		<span><%-- <img width="18" height="24"
			src="${frontPath}/images/lx-jk_03.jpg"></span><a
			href="${path }/index.html"><span>首页</span></a><span>&gt;</span> <a
			href="${path }/invest/toInvest.do"><span>我要贷款</span></a> --%> <span style="padding-left: 30px;">&gt;</span><a
			href="javascript:;"> <span>发布贷款《${borrowType.name }》</span></a>
	</div>
	<div class="hb-list-b">
		<form action="#" method="post" id="borrowFrm">
			<input type="hidden" name="borrowTypeCode"
				value="${borrowType.code }" /> <input type="hidden" name="bidKind"
				value="${borrowType.id }" />
			<div class="wydk-xqb">
				<div class="wydkxq-nr">
					<div class="wydktop-bao">
						<div class="wydk-qh1">
							<a href="javascript:;" id="one1" class="hover"
								onmouseover="setTab('one',1,3)">贷款信息</a>
						</div>
						<div class="wydk-qh1">
							<a href="javascript:;" onmouseover="setTab('one',2,3)" id="one2">投标奖励</a>
						</div>
						<div class="wydk-qh1">
							<a href="javascript:;" id="one3" onmouseover="setTab('one',3,3)">账户信息</a>
						</div>
					</div>
					<div class="wydkxq-nrbb" id="con_one_1">
						<div class="wydkxq-nrwz">
							<table cellspacing="0" cellpadding="0" border="0" width="940"
								style="line-height: 40px;">

								<tbody>
									<tr>
										<td width="161" align="right">贷款总金额：</td>
										<td width="275"><label for="textfield"></label><input
											type="text" name="borrowSum" id="borrowSum" value=""
											onkeyup="value=value.replace(/[^0-9|.]/g,'')" /><span
											style="color: rgb(255, 0, 0);" class="tip" id="borrowSumTip"></span></td>
										<td width="100" align="right">年利率：</td>
										<td width="168"><input type="text"
											name="annualInterestRate"
											onkeyup="value=value.replace(/[^0-9|.]/g,'')"
											id="annualInterestRate" value="" />% <span
											style="color: rgb(255, 0, 0);" class="tip"
											id="annualInterestRateTip"></span></td>
										<td width="31">&nbsp;</td>
										<td width="162">&nbsp;</td>
										<td width="43">&nbsp;</td>
									</tr>

									<tr>
									<c:choose>
									<c:when test="${borrowType.code  eq BORROW_TYPE_ZHUAN or borrowType.code eq BORROW_TYPE_ZHUAN_DF }">
									<c:set var="isZhuan" value="true"></c:set>
									<td width="100" align="right">每份金额：<input name="isDay"
												type="hidden" value="2" /></td>
											<td width="168"><input name="amountPerCopies"
												id="amountPerCopies"
												onkeyup="value=value.replace(/[^0-9|.]/g,'')"
												class="required digits" min="100" type="text" /></td>
									</c:when>
									<c:otherwise>
									<td width="100" align="right">是否天标：</td>
											<td width="168"><input type="radio" name="isDay"
												onclick="changeIsDay();" value="1" <c:if test="${borrowType.code eq 'MIAO' }">  disabled="disabled" </c:if> /> 是 <input type="radio"
												name="isDay" onclick="changeIsDay();" <c:if test="${borrowType.code eq 'MIAO' }">  disabled="disabled" </c:if> value="2" checked />
												否</td>
									</c:otherwise>
									</c:choose>
										
										
										<td align="right">贷款期限： <label for="select"></label></td>
										<td>
										<select name="borrowTimeLimit" id="borrowTimeLimit" <c:if test="${borrowType.code eq 'MIAO' }">  disabled="disabled" </c:if>>
												<option value="" <c:if test="${borrowType.code ne 'MIAO' }">selected="selected" </c:if> >--贷款期限--</option>
												<option value="1" <c:if test="${borrowType.code eq 'MIAO' }">selected="selected" </c:if>>1个月</option>
												<option value="2">2个月</option>
												<option value="3">3个月</option>
												<option value="4">4个月</option>
												<option value="5">5个月</option>
												<option value="6">6个月</option>
												<option value="7">7个月</option>
												<option value="8">8个月</option>
												<option value="9">9个月</option>
												<option value="10">10个月</option>
												<option value="11">11个月</option>
												<option value="12">12个月</option>
										</select> <span style="color: rgb(255, 0, 0);" class="tip"
											id="borrowTimeLimitTip"></span></td>
										<td width="31">&nbsp;</td>
										<td width="162">&nbsp;</td>
										<td width="43">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">贷款用途：</td>
										<td><label for="select"></label><select name="borrowUse"
											id="borrowUse">
												<option value="" selected="selected">--请选择用途--</option>
												<option value="1">短期周转</option>
												<option value="2">生意周转</option>
												<option value="3">生活周转</option>
												<option value="4">购物消费</option>
												<option value="5">不提现借款</option>
												<option value="6">创业借款</option>
												<option value="7">其他借款</option>
										</select> <span style="color: rgb(255, 0, 0);" class="tip"
											id="borrowUseTip"></span></td>
										<td align="right">投标密码： <label for="select"></label></td>
										<td><input type="password" name="tenderPassword"
											id="tenderPassword" value="" /></td>
										<td width="31">&nbsp;</td>
										<td width="162">&nbsp;</td>
										<td width="43">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">最低投标金额：</td>
										<td><select name="minAmount" id="minAmount">
												<option value="50">50元</option>
												<option value="100">100元</option>
												<option value="150">150元</option>
												<option value="200">200元</option>
												<option value="300">300元</option>
												<option value="500">500元</option>
												<option value="1000">1000元</option>
												<option value="1500">1500元</option>
												<option value="3000">3000元</option>
												<option value="5000">5000元</option>
												<option value="10000">10000元</option>
										</select></td>
										<td align="right">最多投标金额：</td>
										<td><select name="maxAmount" id="maxAmount">
												<option value="0">没有限制</option>
												<option value="2000">2000元</option>
												<option value="5000">5000元</option>
												<option value="10000">10000元</option>
												<option value="15000">15000元</option>
												<option value="20000">20000元</option>
												<option value="50000">50000元</option>
												<option value="100000">100000元</option>
												
										</select></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">有效时间：</td>
										<td><select name="validTime" id="validTime">
												<option value="" selected="selected">--有效时间--</option>
												<option value="1">1天</option>
												<option value="2">2天</option>
												<option value="3">3天</option>
												<option value="4">4天</option>
												<option value="5">5天</option>
												<option value="6">6天</option>
												<option value="7">7天</option>
										</select> <span style="color: rgb(255, 0, 0);" class="tip"
											id="validTimeTip"></span></td>
										<td align="right">还款方式：</td>
										<td><select name="repaymentStyle" id="repaymentStyle">
											<c:choose>
											<c:when test="${isZhuan eq 'true' }">
												<option value="1">一次性还款</option>
											</c:when>
											<c:otherwise>
												<option value="2">按月分期还款</option>
													<option value="1">一次性还款</option>
													<option value="3">每月还息到期还本</option>
											</c:otherwise>
											</c:choose>
												
										</select></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="wydkxq-nrbb" id="con_one_2" style="display: none;">
						<div class="wydkxq-nrwz">
							<table cellspacing="0" cellpadding="0" border="0" width="940"
								style="line-height: 40px;">
								<tbody>
									<tr>
										<td><input type="radio" name="tenderAward" value="1"
											onclick="changeTenderAward(1);" checked="checked" />不设置奖励</td>
										<td><input type="radio" name="tenderAward"
											onclick="changeTenderAward(2);" value="2" />按投标金额比例奖励 <span
											id="rateDiv" style="display: none;"> <input
												name="proportionRate" size="10" id="proportionRate" />%
										</span></td>
										<td><input type="radio" name="tenderAward"
											onclick="changeTenderAward(3);" value="3" />按固定金额分摊奖励 <span
											id="partDiv" style="display: none;"> <input
												name="partAmount" size="10" id="partAmount" />元
										</span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="wydkxq-nrbb" id="con_one_3" style="display: none;">
						<div class="wydkxq-nrwz">
							<table cellspacing="0" cellpadding="0" border="0" width="940"
								style="line-height: 40px;">
								<tbody>
									<tr>
										<td><input type="checkbox" name="openAccount" id="openAccount" value="1" checked="checked"/>&nbsp;&nbsp;公开我的帐户资金情况</td>
										<td><input type="checkbox" name="openBorrow" id="openBorrow" value="1" checked="checked" readonly="readonly"/>&nbsp;&nbsp;公开我的借款资金情况</td>
										<td><input type="checkbox" name="openTender" id="openTender" value="1" />&nbsp;&nbsp;公开我的投标资金情况</td>
										<td><input type="checkbox" name="openCredit" id="openCredit" value="1" checked="checked" readonly="readonly"/>&nbsp;&nbsp;公开我的信用额度情况</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="wydkxq-bxq" style="margin-top: 0px;">
						<div
							style="background-color: #f1f1f1; border-bottom: 1px solid #d1d0d5;"
							class="wydktop-bao">
							<div class="wydk-qh">投标的详细说明</div>
						</div>
						<div class="bxq-bjk">
							<table cellspacing="0" cellpadding="0" border="0" width="806"
								style="line-height: 50px;">
								<tbody>
									<tr>
										<td width="72" bgcolor="#f6f7f6" align="right">标题：</td>
										<td width="15">&nbsp;</td>
										<td colspan="2"><label for="textfield4"></label> <input
											type="text" size="30" name="borrowTitle" id="borrowTitle"
											value="" /> *单个图片上传大小不能超过2M<span
											style="color: rgb(255, 0, 0);" class="tip"
											id="borrowTitleTip"></span></td>
										<td width="8">&nbsp;</td>
									</tr>
									<tr>
										<td valign="top" bgcolor="#f6f7f6" align="right">信息：</td>
										<td>&nbsp;</td>
										<td colspan="2"><textarea name="borrowContent" id="borrowContent"></textarea></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td bgcolor="#f6f7f6" align="right">验证码：</td>
										<td>&nbsp;</td>
										<td width="694"><table cellspacing="0" cellpadding="0"
												border="0" width="100">
												<tbody>
													<tr>
														<td><input name="captcha" type="text" id="captcha"
															class="required"></td>
														<td><span style="color: rgb(255, 0, 0);" class="tip"
															id="captchaTip"></span></td>
														<td><img id="captchaImg" src="<%=path%>/captcha.svl"
															onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1"
															valign="bottom" alt="点击更新" title="点击更新" /></td>
													</tr>
												</tbody>
											</table></td>
										<td width="17">&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</tbody>
							</table>
							<div class="tj-but">
								<a id="submitBtn" href="javascript:;" onclick="fromSubmit()"
									style="color: #fff;">确认提交</a>
							</div>
						</div>

					</div>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp" %>
</body>

<script type="text/javascript">
	$(function(){
		changeTopHover(3);
	});
	function subAjax(params) {
		$.ajax({
			dataType : 'json',
			url : "${path}/iborrow/saveBorrow.do",
			data : params,
			type : 'POST',
			success : function(data) {
				initCaptcha();
				alertc(data.msg);
			},
			error : function() {
				initCaptcha();
			}
		});

	}
</script>
</html>