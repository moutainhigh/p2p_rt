<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
Calendar calendar = Calendar.getInstance();
calendar.add(Calendar.DATE, 1);    //得到前一天
Date dates = calendar.getTime();
String date =  formatter.format(dates)+" 08:00:00";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body >
<form id="issueForm" name="issueForm" method="post" action="borrow/${PRE_PATH_EDIT }savefixedCurrentLinkAccount"  
				onsubmit="return validateCallback(this, saveBack);" class="pageForm required-validate">
	<input type="hidden" name="right_id" value="${right_id}" />
	<input type="hidden" name="borrowTypeCode" value="${borrowType.code}"/>
	<input name="isDay" type="hidden" value="2"/>
	<div class="pageContent">
	<div class="tabs"  >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>综合</span></a></li>
					<li><a href="javascript:;"><span>定活通1</span></a></li>
					<li><a href="javascript:;"><span>定活通2</span></a></li>
					<li><a href="javascript:;"><span>定活通3</span></a></li>					
				</ul>
			</div>
		</div>
		<div class="tabsContent" layoutH="75" style="background-color: white;" >
			<div class="pageFormContent">
				<fieldset>
						<legend>综合</legend>
						<dl>
						<dt class="nowrap">有效时间:</dt>
							<dd >
								<select name="validTime" class="required">
									<option value="1" selected="selected">1天</option>
									<option value="2">2天</option>
									<option value="3">3天</option>
									<option value="4">4天</option>
									<option value="5">5天</option>
									<option value="6">6天</option>
									<option value="7">7天</option>
								</select>
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>发布时间:</dt>
							<dd><input name="relatedPublishTime" value="<%=date %>" class="date textInput readonly" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true" type="text"><a class="inputDateButton" href="javascript:;">选择</a></dd>
						</dl>
						<dl class="nowrap">
							<dt>验证码:</dt>
							<dd><input maxlength="4" style="width: 60px;" name="captcha" id="captcha" class="required" type="text"><img id="captchaImg" src="<%=request.getContextPath() %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新"></dd>
						</dl>
				</fieldset>
			</div>
			<div class="pageFormContent" >
				<fieldset>
						<legend>贷款信息</legend>
						<dl>
							<dt>贷款总额:</dt>
							<dd class="unit"><input name="borrows[0].borrowSum" id="borrowSum1" class="required digits"
								type="text" /></dd>
						</dl>
						<dl>
							<dt>年利率:</dt>
							<dd class="unit"><input name="borrows[0].annualInterestRate" size="20"
								id="annualInterestRate1" class="required number" type="text" />%</dd>
						</dl>
						<dl  >
						<dt>贷款期限:</dt>
						<dd class="unit" >
							<select name="borrows[0].borrowTimeLimit" class="required" id="monthSelect"
								style="display: block;">
								<option value="1">1个月</option>
								<option value="2">2个月</option>
								<option value="3" selected="selected">3个月</option>
								<option value="4">4个月</option>
								<option value="5">5个月</option>
								<option value="6">6个月</option>
								<option value="7">7个月</option>
								<option value="8">8个月</option>
								<option value="9">9个月</option>
								<option value="10">10个月</option>
								<option value="11">11个月</option>
								<option value="12">12个月</option>
							</select>
						</dd>
					</dl>
						
						<dl>
						<dt>还款方式:</dt>
						<dd class="unit">
							<select name="borrows[0].repaymentStyle" disabled="disabled">
									<option value="3">每月还息到期还本</option>
							</select>
						</dd>
					</dl>
							<dl>
							<dt>最低投标金额:</dt>
							<dd class="unit">
								<select name="borrows[0].minAmount" class="valid">						
									<option  value="50">50元</option>
									<option  value="100">100元</option>
									<option  value="150">150元</option>
									<option  value="200">200元</option>
									<option  value="300">300元</option>
									<option  value="500">500元</option>
									<option  value="1000">1000元</option>
									<option  value="1500">1500元</option>
									<option  value="3000">3000元</option>
									<option  value="5000">5000元</option>
									<option  value="10000">10000元</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>最多投标金额:</dt>
							<dd class="unit">
							<select name="borrows[0].maxAmount" class="valid">
								<option  value="0">没有限制</option>
								<option  value="2000">2000元</option>
								<option  value="5000">5000元</option>
								<option  value="10000">10000元</option>
								<option  value="15000">15000元</option>
								<option  value="20000">20000元</option>
								<option  value="50000">50000元</option>
								<option  value="100000">100000元</option>
							</select>
							</dd>
						</dl>
					
					</fieldset>
					<fieldset>
							<legend>详细信息</legend>
							<dl class="nowrap">
								<dt>贷款标题:</dt>
								<dd><input name="borrows[0].borrowTitle" id="borrowTitle1" type="text" size="30"
								class="required" /></dd>
							</dl>
							<dl class="nowrap">
								<dt>详细信息:</dt>
								<dd>
								<textarea rows="16" cols="100" class="editor" id="borrowContent1"
								name="borrows[0].borrowContent" upImgUrl="upload/editorImg"></textarea>
								</dd>
							</dl>
					</fieldset>
			</div>
			<div class="pageFormContent" >
				<fieldset>
						<legend>贷款信息</legend>
						<dl>
							<dt>贷款总额:</dt>
							<dd class="unit"><input name="borrows[1].borrowSum" id="borrowSum2" class="required digits"
								type="text" /></dd>
						</dl>
						<dl>
							<dt>年利率:</dt>
							<dd class="unit"><input name="borrows[1].annualInterestRate" size="20"
								id="annualInterestRate2" class="required number" type="text" />%</dd>
						</dl>
						<dl  >
						<dt>贷款期限:</dt>
						<dd class="unit">
							<select name="borrows[1].borrowTimeLimit" class="required" 
								style="display: block;">
								<option value="1">1个月</option>
								<option value="2">2个月</option>
								<option value="3">3个月</option>
								<option value="4">4个月</option>
								<option value="5">5个月</option>
								<option value="6" selected="selected">6个月</option>
								<option value="7">7个月</option>
								<option value="8">8个月</option>
								<option value="9">9个月</option>
								<option value="10">10个月</option>
								<option value="11">11个月</option>
								<option value="12">12个月</option>
							</select>
						</dd>
					</dl>
						<dl>
							<dt>还款方式:</dt>
							<dd class="unit">
							<select name="borrows[1].repaymentStyle" disabled="disabled">
									<option value="3">每月还息到期还本</option>
							</select>
						</dd>
					</dl>
							<dl>
							<dt>最低投标金额:</dt>
							<dd class="unit">
								<select name="borrows[1].minAmount" class="valid">						
									<option  value="50">50元</option>
									<option  value="100">100元</option>
									<option  value="150">150元</option>
									<option  value="200">200元</option>
									<option  value="300">300元</option>
									<option  value="500">500元</option>
									<option  value="1000">1000元</option>
									<option  value="1500">1500元</option>
									<option  value="3000">3000元</option>
									<option  value="5000">5000元</option>
									<option  value="10000">10000元</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>最多投标金额:</dt>
							<dd class="unit">
							<select name="borrows[1].maxAmount" class="valid">
								<option  value="0">没有限制</option>
								<option  value="2000">2000元</option>
								<option  value="5000">5000元</option>
								<option  value="10000">10000元</option>
								<option  value="15000">15000元</option>
								<option  value="20000">20000元</option>
								<option  value="50000">50000元</option>
								<option  value="100000">100000元</option>
							</select>
							</dd>
						</dl>
						
						
					
					</fieldset>
					<fieldset>
							<legend>详细信息</legend>
							<dl class="nowrap">
								<dt>贷款标题:</dt>
								<dd><input name="borrows[1].borrowTitle" id="borrowTitle2" type="text" size="30"
								class="required" /></dd>
							</dl>
							<dl class="nowrap">
								<dt>详细信息:</dt>
								<dd>
								<textarea rows="16" cols="100" class="editor" id="borrowContent2"
								name="borrows[1].borrowContent" upImgUrl="upload/editorImg"></textarea>
								</dd>
							</dl>
					</fieldset>
			</div>
			<div class="pageFormContent" >
				<fieldset>
						<legend>贷款信息</legend>
						<dl>
							<dt>贷款总额:</dt>
							<dd class="unit"><input name="borrows[2].borrowSum" id="borrowSum3" class="required digits"
								type="text" /></dd>
						</dl>
						<dl>
							<dt>年利率:</dt>
							<dd class="unit"><input name="borrows[2].annualInterestRate" size="20"
								id="annualInterestRate3" class="required number" type="text" />%</dd>
						</dl>
						<dl  >
						<dt>贷款期限:</dt>
						<dd class="unit" >
							<select name="borrows[2].borrowTimeLimit" class="required" 
								style="display: block;">
								<option value="1">1个月</option>
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
								<option value="12"  selected="selected">12个月</option>
							</select>
						</dd>
					</dl>				
						<dl>
						<dt>还款方式:</dt>
						<dd class="unit">
							<select name="borrows[2].repaymentStyle" disabled="disabled">
									<option value="3">每月还息到期还本</option>
							</select>
						</dd>
					</dl>	
							<dl>
							<dt>最低投标金额:</dt>
							<dd class="unit">
								<select name="borrows[2].minAmount" class="valid">						
									<option  value="50">50元</option>
									<option  value="100">100元</option>
									<option  value="150">150元</option>
									<option  value="200">200元</option>
									<option  value="300">300元</option>
									<option  value="500">500元</option>
									<option  value="1000">1000元</option>
									<option  value="1500">1500元</option>
									<option  value="3000">3000元</option>
									<option  value="5000">5000元</option>
									<option  value="10000">10000元</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>最多投标金额:</dt>
							<dd class="unit">
							<select name="borrows[2].maxAmount" class="valid">
								<option  value="0">没有限制</option>
								<option  value="2000">2000元</option>
								<option  value="5000">5000元</option>
								<option  value="10000">10000元</option>
								<option  value="15000">15000元</option>
								<option  value="20000">20000元</option>
								<option  value="50000">50000元</option>
								<option  value="100000">100000元</option>
							</select>
							</dd>
						</dl>
						
					
						
					</fieldset>
					<fieldset>
							<legend>详细信息</legend>
							<dl class="nowrap">
								<dt>贷款标题:</dt>
								<dd><input name="borrows[2].borrowTitle" id="borrowTitle3" type="text" size="30"
								class="required" /></dd>
							</dl>
							<dl class="nowrap">
								<dt>详细信息:</dt>
								<dd>
								<textarea rows="16"  cols="100" class="editor" id="borrowContent3"
								name="borrows[2].borrowContent" upImgUrl="upload/editorImg"></textarea>
								</dd>
							</dl>
					</fieldset>
			</div>
			</div>
				<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
		</div>
		<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
		</div>
</div>
</form>
</body>
<script type="text/javascript">
		function saveBack(json){
			$("#captchaImg").click();
			$("input[name='captcha']").val("");
			navTabAjaxDone(json);
		}
</script>
</html>