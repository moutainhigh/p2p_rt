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
					<table class="uniceTable siteInfoWidth1">
						<tbody>
							<tr id="attul">
								<td class="userDh" id="loanTab0"><a href="javascript:tabChange(0);">个人详情</a></td>
						        <td id="loanTab1"><a href="javascript:tabChange(1);">房产资料</a></td>
						        <td id="loanTab2"><a href="javascript:tabChange(2);">单位资料</a></td>
						        <td id="loanTab3"><a href="javascript:tabChange(3);">私营业主</a></td>
						        <td id="loanTab4"><a href="javascript:tabChange(4);">财务状况</a></td>
						        <td id="loanTab5"><a href="javascript:tabChange(5);">联系方式</a></td>
						        <td id="loanTab6"><a href="javascript:tabChange(6);">配偶资料</a></td>
						        <td id="loanTab7"><a href="javascript:tabChange(7);">教育背景</a></td>
						        <td id="loanTab8"><a href="javascript:tabChange(8);">其他</a></td>
								<!-- <td style="border-right: none;"></td> -->
							</tr>
						</tbody>
					</table>
					
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" id="presonal" method="post">
						<input type="hidden" name="id" id="id" value="${personal.id }">

							<div class="alerts">请填写你的个人相关信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">账&nbsp;户：</td>
										<td class="bl"><span class="leftspace"></span>
											${sessionScope.FRONT_USER_SESSION.userAccount }
										</td>
									</tr>
									<tr>
										<td class="ls">真实姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<c:if test="${not empty sessionScope.FRONT_USER_SESSION.userRealname }">${sessionScope.FRONT_USER_SESSION.userRealname} </c:if>
		                					<c:if test="${ empty sessionScope.FRONT_USER_SESSION.userRealname }">还未实名认证  &nbsp;</c:if>
										</td>
									</tr>
									<tr>
										<td class="ls">性&nbsp;别：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="radio" checked="checked" name="sex" value="male" <c:if test="${'male' eq sessionScope.FRONT_USER_SESSION.userSex}">checked="checked"</c:if> style="width: 20px;float: left;"/>
						                	<span style="float: left;margin-top: 0px;">男</span>
											<input type="radio" name="sex" value="female" <c:if test="${'female' eq sessionScope.FRONT_USER_SESSION.userSex}">checked="checked"</c:if>style="width: 20px;float: left;"/>
											<span style="float: left;margin-top: 0px;">女</span>
										</td>
									</tr>
									<tr>
										<td class="ls">手机号码：</td>
										<td class="bl"><span class="leftspace"></span>
											${sessionScope.FRONT_USER_SESSION.userPhone }
										</td>
									</tr>
									<tr>
										<td class="ls">籍贯：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="userProvince" id="userProvince">
			                                </select>
			                                <select name="userCity" id="userCity">
			                                </select>
			                                <select name="userArea" id="userArea">
			                                </select>
										</td>
									</tr>
									<tr>
										<td class="ls">婚姻状况：</td>
										<td class="bl"><span class="leftspace"></span>
										<select name="marital" id="marital">
										    <!-- <option >---请选择婚姻状况---</option> -->
										    <option  value="0" <c:if test="${'0' eq personal.marital}">selected="selected"</c:if>>未婚</option>
										    <option  value="1" <c:if test="${'1' eq personal.marital}">selected="selected"</c:if>>已婚</option>
										    <option  value="2" <c:if test="${'2' eq personal.marital}">selected="selected"</c:if>>离异</option>
										    <option  value="3" <c:if test="${'3' eq personal.marital}">selected="selected"</c:if>>丧偶</option>
										 </select>
										</td>
									</tr>
									<tr>
										<td class="ls">子女：</td>
										<td><span class="leftspace"></span>
											<select name="children" id="children">
											<!-- <option >---请选择子女状况---</option> -->
											    <option  value="0" <c:if test="${'0' eq personal.children}">selected="selected"</c:if>>没有</option>
											    <option  value="1" <c:if test="${'1' eq personal.children}">selected="selected"</c:if>>1个</option>
											    <option  value="2" <c:if test="${'2' eq personal.children}">selected="selected"</c:if>>2个</option>
											    <option  value="3" <c:if test="${'3' eq personal.children}">selected="selected"</c:if>>3个</option>
											    <option  value="4" <c:if test="${'4' eq personal.children}">selected="selected"</c:if>>4个或4个以上</option>
										    </select>
										</td>
									</tr>
									<tr>
										<td class="ls">月收入：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="income" id="income">
											  <!--  <option >--请选择月收入状况--</option> -->
											   <option value="1" <c:if test="${'1' eq personal.income}">selected="selected"</c:if>>1000元以下</option>
											   <option value="2" <c:if test="${'2' eq personal.income}">selected="selected"</c:if>>1000-2000元</option>
											   <option value="3" <c:if test="${'3' eq personal.income}">selected="selected"</c:if>>2000-3000元</option>
											   <option value="4" <c:if test="${'4' eq personal.income}">selected="selected"</c:if>>3000-4000元</option>
											   <option value="5" <c:if test="${'5' eq personal.income}">selected="selected"</c:if>>4000-5000元</option>
											   <option value="6" <c:if test="${'6' eq personal.income}">selected="selected"</c:if>>5000-6000元</option>
											   <option value="7" <c:if test="${'7' eq personal.income}">selected="selected"</c:if>>6000-8000元</option>
											   <option value="8" <c:if test="${'8' eq personal.income}">selected="selected"</c:if>>8000-10000元</option>
											   <option value="9" <c:if test="${'9' eq personal.income}">selected="selected"</c:if>>1万以上</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">社保：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="social" id="social">
											  <!--  <option >---请选择社保状况---</option> -->
											   <option value="0"  <c:if test="${'0' eq personal.social}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.social}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">社保电脑号：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" name="socialId" value="${personal.socialId}" id="socialId" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">住房条件：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="housing" id="housing">
										      <!--  <option >---请选择住房状况---</option> -->
											   <option value="1" <c:if test="${'1' eq personal.housing}">selected="selected"</c:if>>租房</option>
											   <option value="2" <c:if test="${'2' eq personal.housing}">selected="selected"</c:if>>自己有房</option>
											   <option value="0" <c:if test="${'0' eq personal.housing}">selected="selected"</c:if>>其他</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">是否有车：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="car" id="car">
											  <!--  <option >----请选择车状况----</option> -->
											   <option value="0" <c:if test="${'0' eq personal.car}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.car}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ls">逾期记录：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="overdue" id="overdue">
											  <!--  <option >---请选择逾期状况---</option> -->
											   <option value="0" <c:if test="${'0' eq personal.overdue}">selected="selected"</c:if>>没有</option>
											   <option value="1" <c:if test="${'1' eq personal.overdue}">selected="selected"</c:if>>有</option>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="javascript:subForm('presonal');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 房产资料 -->
					<div class="tab_info" id="tab1" style="display: none;">
						<form action="" id="house" method="post">
						<input type="hidden" name="id" id="id" value="${house.id }">

							<div class="alerts">请填写你个人的房产资料相关信息</div>
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
											<span class="wdzh-buttu" onclick="javascript:subForm('house');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 单位资料 -->
					<div class="tab_info" id="tab2" style="display: none;">
						<form action="" id="unit" method="post">
						<input type="hidden" name="id" id="id" value="${unit.id }">
						
							<div class="alerts">请填写你个人的最近的单位资料</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">单位名称：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${unit.companyName}" name="companyName" id="companyName" class="input">
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">单位性质：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="companyIndustry" id="companyIndustry">
		                                   		<option value="0" <c:if test="${not empty unit.companyIndustry and 0 eq unit.companyIndustry}">selected="selected"</c:if>>政府机关</option>
						              			<option value="1" <c:if test="${not empty unit.companyIndustry and 1 eq unit.companyIndustry}">selected="selected"</c:if>>国有企业</option>
						              			<option value="2" <c:if test="${not empty unit.companyIndustry and 2 eq unit.companyIndustry}">selected="selected"</c:if>>台（港，澳）资企业</option>
						              			<option value="3" <c:if test="${not empty unit.companyIndustry and 3 eq unit.companyIndustry}">selected="selected"</c:if>>外资企业</option>
						              			<option value="4" <c:if test="${not empty unit.companyIndustry and 4 eq unit.companyIndustry}">selected="selected"</c:if>>合资企业</option>
						              			<option value="5" <c:if test="${not empty unit.companyIndustry and 5 eq unit.companyIndustry}">selected="selected"</c:if>>个体户</option>
						              			<option value="6" <c:if test="${not empty unit.companyIndustry and 6 eq unit.companyIndustry}">selected="selected"</c:if>>事业性单位</option>
						              			<option value="7" <c:if test="${not empty unit.companyIndustry and 7 eq unit.companyIndustry}">selected="selected"</c:if>>私营企业</option>
	                         				</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">单位行业：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="work" id="work">
		                                	<option value="0" <c:if test="${not empty unit.work and 0 eq unit.work}">selected="selected"</c:if>>农，林，牧，渔业</option>
						              			<option value="1" <c:if test="${not empty unit.work and 1 eq unit.work}">selected="selected"</c:if>>制造业</option>
						              			<option value="2" <c:if test="${not empty unit.work and 2 eq unit.work}">selected="selected"</c:if>>电子，燃气及水的生产和供应业</option>
						              			<option value="3" <c:if test="${not empty unit.work and 3 eq unit.work}">selected="selected"</c:if>>建筑业</option>
						              			<option value="4" <c:if test="${not empty unit.work and 4 eq unit.work}">selected="selected"</c:if>>交通运输，仓储和邮政业</option>
						              			<option value="5" <c:if test="${not empty unit.work and 5 eq unit.work}">selected="selected"</c:if>>信息传输，计算机服务和软件业</option>
						              			<option value="6" <c:if test="${not empty unit.work and 6 eq unit.work}">selected="selected"</c:if>>批发和零售业</option>
						              			<option value="7" <c:if test="${not empty unit.work and 7 eq unit.work}">selected="selected"</c:if>>金融业</option>
						              			<option value="8" <c:if test="${not empty unit.work and 8 eq unit.work}">selected="selected"</c:if>>地产业</option>
						              			<option value="9" <c:if test="${not empty unit.work and 9 eq unit.work}">selected="selected"</c:if>>采矿业</option>
						              			<option value="10" <c:if test="${not empty unit.work and 10 eq unit.work}">selected="selected"</c:if>>租凭和商务服务业</option>
						              			<option value="11" <c:if test="${not empty unit.work and 11 eq unit.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="12" <c:if test="${not empty unit.work and 12 eq unit.work}">selected="selected"</c:if>>水利，环境和公共设备管理业</option>
						              			<option value="13" <c:if test="${not empty unit.work and 13 eq unit.work}">selected="selected"</c:if>>居民服务和其他服务业</option>
						              			<option value="14" <c:if test="${not empty unit.work and 14 eq unit.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="15" <c:if test="${not empty unit.work and 15 eq unit.work}">selected="selected"</c:if>>教育</option>
						              			<option value="16" <c:if test="${not empty unit.work and 16 eq unit.work}">selected="selected"</c:if>>卫生，社会保障和社会福利业</option>
						              			<option value="17" <c:if test="${not empty unit.work and 17 eq unit.work}">selected="selected"</c:if>>文化，体育和娱乐业</option>
						              			<option value="18" <c:if test="${not empty unit.work and 18 eq unit.work}">selected="selected"</c:if>>公共管理和社会组织</option>
						              			<option value="19" <c:if test="${not empty unit.work and 19 eq unit.work}">selected="selected"</c:if>>国际组织</option>
	                         	 			</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作级别：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="workGrade" id="workGrade" >
	                                   			<option value="0" <c:if test="${not empty unit.workGrade and 0 eq unit.workGrade}">selected="selected"</c:if>>普通员工</option>
						              			<option value="1" <c:if test="${not empty unit.workGrade and 1 eq unit.workGrade}">selected="selected"</c:if>>管理人员</option>
						              			<option value="2" <c:if test="${not empty unit.workGrade and 2 eq unit.workGrade}">selected="selected"</c:if>>股东</option>
						              			<option value="3" <c:if test="${not empty unit.workGrade and 3 eq unit.workGrade}">selected="selected"</c:if>>私营业主</option>
	                        				</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">职 位：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="zhiwei" id="zhiwei" class="input08">
			                               		<option value="77" <c:if test="${not empty unit.zhiwei and 77 eq unit.zhiwei}">selected="selected"</c:if>>财务总监</option>
							              		<option value="78" <c:if test="${not empty unit.zhiwei and 78 eq unit.zhiwei}">selected="selected"</c:if>>财务主管</option>
							              		<option value="79" <c:if test="${not empty unit.zhiwei and 79 eq unit.zhiwei}">selected="selected"</c:if>>审计/税务</option>
							              		<option value="80" <c:if test="${not empty unit.zhiwei and 80 eq unit.zhiwei}">selected="selected"</c:if>>总账/成本</option>
							              		<option value="81" <c:if test="${not empty unit.zhiwei and 81 eq unit.zhiwei}">selected="selected"</c:if>>会计人员</option>
							              		<option value="82" <c:if test="${not empty unit.zhiwei and 82 eq unit.zhiwei}">selected="selected"</c:if>>人力资源总监</option>
							              		<option value="83" <c:if test="${not empty unit.zhiwei and 83 eq unit.zhiwei}">selected="selected"</c:if>>人力资源经理/主管</option>
							              		<option value="84" <c:if test="${not empty unit.zhiwei and 84 eq unit.zhiwei}">selected="selected"</c:if>>招聘经理/主管</option>
							              		<option value="85" <c:if test="${not empty unit.zhiwei and 85 eq unit.zhiwei}">selected="selected"</c:if>>薪资福利经理/主管</option>
							              		<option value="86" <c:if test="${not empty unit.zhiwei and 86 eq unit.zhiwei}">selected="selected"</c:if>>财务经理</option>
							              		<option value="87" <c:if test="${not empty unit.zhiwei and 87 eq unit.zhiwei}">selected="selected"</c:if>>培训经理/主管</option>
							               		<option value="88" <c:if test="${not empty unit.zhiwei and 88 eq unit.zhiwei}">selected="selected"</c:if>>人事专员/助理</option>
							               		<option value="89" <c:if test="${not empty unit.zhiwei and 89 eq unit.zhiwei}">selected="selected"</c:if>>行长/副行长</option>
							               		<option value="90" <c:if test="${not empty unit.zhiwei and 90 eq unit.zhiwei}">selected="selected"</c:if>>信贷/信用管理</option>
							               		<option value="91" <c:if test="${not empty unit.zhiwei and 91 eq unit.zhiwei}">selected="selected"</c:if>>资产管理/评估</option>
							               		<option value="92" <c:if test="${not empty unit.zhiwei and 92 eq unit.zhiwei}">selected="selected"</c:if>>投融资项目/基金</option>
							               		<option value="93" <c:if test="${not empty unit.zhiwei and 93 eq unit.zhiwei}">selected="selected"</c:if>>外汇管理/国际结算</option>
							               		<option value="94" <c:if test="${not empty unit.zhiwei and 94 eq unit.zhiwei}">selected="selected"</c:if>>风险管理</option>
							               		<option value="95" <c:if test="${not empty unit.zhiwei and 95 eq unit.zhiwei}">selected="selected"</c:if>>证券/期货</option>
							               		<option value="96" <c:if test="${not empty unit.zhiwei and 96 eq unit.zhiwei}">selected="selected"</c:if>>客户经理/主管</option>
							               		<option value="97" <c:if test="${not empty unit.zhiwei and 97 eq unit.zhiwei}">selected="selected"</c:if>>核保/理赔</option>
							               		<option value="98" <c:if test="${not empty unit.zhiwei and 98 eq unit.zhiwei}">selected="selected"</c:if>>保险代理人/内勤</option>
							               		<option value="99" <c:if test="${not empty unit.zhiwei and 99 eq unit.zhiwei}">selected="selected"</c:if>>银行专员/柜台</option>
							               		<option value="100" <c:if test="${not empty unit.zhiwei and 100 eq unit.zhiwei}">selected="selected"</c:if>>精算师</option>
							               		<option value="101" <c:if test="${not empty unit.zhiwei and 101 eq unit.zhiwei}">selected="selected"</c:if>>编辑/记者/文案</option>
							               		<option value="102" <c:if test="${not empty unit.zhiwei and 102 eq unit.zhiwei}">selected="selected"</c:if>>版面设计</option>
							               		<option value="103" <c:if test="${not empty unit.zhiwei and 103 eq unit.zhiwei}">selected="selected"</c:if>>平面设计</option>
							               		<option value="104" <c:if test="${not empty unit.zhiwei and 104 eq unit.zhiwei}">selected="selected"</c:if>>装潢/陈列设计</option>
							               		<option value="105" <c:if test="${not empty unit.zhiwei and 105 eq unit.zhiwei}">selected="selected"</c:if>>纺织/服装设计</option>
							               		<option value="106" <c:if test="${not empty unit.zhiwei and 106 eq unit.zhiwei}">selected="selected"</c:if>>工艺品/珠宝设计</option>
							               		<option value="107" <c:if test="${not empty unit.zhiwei and 107 eq unit.zhiwei}">selected="selected"</c:if>>经理</option>
							               		<option value="108" <c:if test="${not empty unit.zhiwei and 108 eq unit.zhiwei}">selected="selected"</c:if>>主任</option>
							               		<option value="109" <c:if test="${not empty unit.zhiwei and 109 eq unit.zhiwei}">selected="selected"</c:if>>生物工程</option>
							               		<option value="110" <c:if test="${not empty unit.zhiwei and 110 eq unit.zhiwei}">selected="selected"</c:if>>药物注册</option>
							               		<option value="111" <c:if test="${not empty unit.zhiwei and 111 eq unit.zhiwei}">selected="selected"</c:if>>临床研究/协调</option>
							               		<option value="112" <c:if test="${not empty unit.zhiwei and 112 eq unit.zhiwei}">selected="selected"</c:if>>药物研发/分析工程师</option>
							               		<option value="113" <c:if test="${not empty unit.zhiwei and 113 eq unit.zhiwei}">selected="selected"</c:if>>化学工程师</option>
							               		<option value="114" <c:if test="${not empty unit.zhiwei and 114 eq unit.zhiwei}">selected="selected"</c:if>>环保工程师</option>
							               		<option value="115" <c:if test="${not empty unit.zhiwei and 115 eq unit.zhiwei}">selected="selected"</c:if>>化工人员</option>
							               		<option value="116" <c:if test="${not empty unit.zhiwei and 116 eq unit.zhiwei}">selected="selected"</c:if>>结构/建筑工程师</option>
							               		<option value="117" <c:if test="${not empty unit.zhiwei and 117 eq unit.zhiwei}">selected="selected"</c:if>>土建工程师/建造师</option>
							               		<option value="118" <c:if test="${not empty unit.zhiwei and 118 eq unit.zhiwei}">selected="selected"</c:if>>机电/给排水/暖通工程师</option>
							               		<option value="119" <c:if test="${not empty unit.zhiwei and 119 eq unit.zhiwei}">selected="selected"</c:if>>工程造价师/预决算</option>
							               		<option value="120" <c:if test="${not empty unit.zhiwei and 120 eq unit.zhiwei}">selected="selected"</c:if>>监理/安全工程师</option>
							               		<option value="121" <c:if test="${not empty unit.zhiwei and 121 eq unit.zhiwei}">selected="selected"</c:if>>房地产开发/策划</option>
							               		<option value="122" <c:if test="${not empty unit.zhiwei and 122 eq unit.zhiwei}">selected="selected"</c:if>>园林景观/城市规划师</option>
							               		<option value="123" <c:if test="${not empty unit.zhiwei and 123 eq unit.zhiwei}">selected="selected"</c:if>>物业管理</option>
							               		<option value="124" <c:if test="${not empty unit.zhiwei and 124 eq unit.zhiwei}">selected="selected"</c:if>>房地产评估/中介/交易</option>
							               		<option value="125" <c:if test="${not empty unit.zhiwei and 125 eq unit.zhiwei}">selected="selected"</c:if>>电子/电路工程师</option>
							               		<option value="126" <c:if test="${not empty unit.zhiwei and 126 eq unit.zhiwei}">selected="selected"</c:if>>电气工程师</option>
							               		<option value="127" <c:if test="${not empty unit.zhiwei and 127 eq unit.zhiwei}">selected="selected"</c:if>>半导体工程师</option>
							               		<option value="128" <c:if test="${not empty unit.zhiwei and 128 eq unit.zhiwei}">selected="selected"</c:if>>测试工程师</option>
							               		<option value="129" <c:if test="${not empty unit.zhiwei and 129 eq unit.zhiwei}">selected="selected"</c:if>>自动化工程师</option>
							               		<option value="130" <c:if test="${not empty unit.zhiwei and 130 eq unit.zhiwei}">selected="selected"</c:if>>光学工程师</option>
							               		<option value="131" <c:if test="${not empty unit.zhiwei and 131 eq unit.zhiwei}">selected="selected"</c:if> >电力工程师</option>
							               		<option value="132" <c:if test="${not empty unit.zhiwei and 132 eq unit.zhiwei}">selected="selected"</c:if>>水利/水电工程师</option>
							               		<option value="133" <c:if test="${not empty unit.zhiwei and 133 eq unit.zhiwei}">selected="selected"</c:if>>单片机/DLC/DSP/底层软件开发</option>
							               		<option value="134" <c:if test="${not empty unit.zhiwei and 134 eq unit.zhiwei}">selected="selected"</c:if>>其他工程师</option>
							               		<option value="135" <c:if test="${not empty unit.zhiwei and 135 eq unit.zhiwei}">selected="selected"</c:if>>技术人员</option>
							               		<option value="136" <c:if test="${not empty unit.zhiwei and 136 eq unit.zhiwei}">selected="selected"</c:if>>助理</option>
							               		<option value="137" <c:if test="${not empty unit.zhiwei and 137 eq unit.zhiwei}">selected="selected"</c:if>>厂长/副厂长</option>
							               		<option value="138" <c:if test="${not empty unit.zhiwei and 138 eq unit.zhiwei}">selected="selected"</c:if>>生产经理/车间主任</option>
							               		<option value="139" <c:if test="${not empty unit.zhiwei and 139 eq unit.zhiwei}">selected="selected"</c:if>>产品开发</option>
							               		<option value="140" <c:if test="${not empty unit.zhiwei and 140 eq unit.zhiwei}">selected="selected"</c:if>>工业/产品设计</option>
							               		<option value="141" <c:if test="${not empty unit.zhiwei and 141 eq unit.zhiwei}">selected="selected"</c:if>>生产/计划/调度</option>
							               		<option value="142" <c:if test="${not empty unit.zhiwei and 142 eq unit.zhiwei}">selected="selected"</c:if>>质量管理</option>
							               		<option value="143" <c:if test="${not empty unit.zhiwei and 143 eq unit.zhiwei}">selected="selected"</c:if>>项目管理</option>
							               		<option value="144" <c:if test="${not empty unit.zhiwei and 144 eq unit.zhiwei}">selected="selected"</c:if>>工程设备管理</option>
							               		<option value="145" <c:if test="${not empty unit.zhiwei and 145 eq unit.zhiwei}">selected="selected"</c:if>>安全/健康/环境管理</option>
							               		<option value="146" <c:if test="${not empty unit.zhiwei and 146 eq unit.zhiwei}">selected="selected"</c:if>>生产工艺/技术</option>
							               		<option value="147" <c:if test="${not empty unit.zhiwei and 147 eq unit.zhiwei}">selected="selected"</c:if>>技工</option>
							               		<option value="148" <c:if test="${not empty unit.zhiwei and 148 eq unit.zhiwei}">selected="selected"</c:if>>机械工程师</option>
							               		<option value="149" <c:if test="${not empty unit.zhiwei and 149 eq unit.zhiwei}">selected="selected"</c:if>>维修工程师</option>
							               		<option value="150" <c:if test="${not empty unit.zhiwei and 150 eq unit.zhiwei}">selected="selected"</c:if>>机械设计/制图</option>
							               		<option value="151" <c:if test="${not empty unit.zhiwei and 151 eq unit.zhiwei}">selected="selected"</c:if>>机电工程师</option>
							               		<option value="152" <c:if test="${not empty unit.zhiwei and 152 eq unit.zhiwei}">selected="selected"</c:if>>模具工程师</option>
							               		<option value="153" <c:if test="${not empty unit.zhiwei and 153 eq unit.zhiwei}">selected="selected"</c:if>>精密机械/仪器仪表</option>
							               		<option value="154" <c:if test="${not empty unit.zhiwei and 154 eq unit.zhiwei}">selected="selected"</c:if>>船舶工程师</option>
							               		<option value="155" <c:if test="${not empty unit.zhiwei and 155 eq unit.zhiwei}">selected="selected"</c:if>>冲压/注塑/焊接</option>
							               		<option value="156" <c:if test="${not empty unit.zhiwei and 156 eq unit.zhiwei}">selected="selected"</c:if>>餐饮/娱乐管理</option>
							               		<option value="157" <c:if test="${not empty unit.zhiwei and 157 eq unit.zhiwei}">selected="selected"</c:if>>酒店/宾馆管理</option>
							               		<option value="158" <c:if test="${not empty unit.zhiwei and 158 eq unit.zhiwei}">selected="selected"</c:if>>服务人员/领班</option>
							               		<option value="159" <c:if test="${not empty unit.zhiwei and 159 eq unit.zhiwei}">selected="selected"</c:if>>导游/旅行顾问/票务</option>
							               		<option value="160" <c:if test="${not empty unit.zhiwei and 160 eq unit.zhiwei}">selected="selected"</c:if>>营业员/收银员</option>
							               		<option value="161" <c:if test="${not empty unit.zhiwei and 161 eq unit.zhiwei}">selected="selected"</c:if>>技术总监/首席技术执行官</option>
							               		<option value="162" <c:if test="${not empty unit.zhiwei and 162 eq unit.zhiwei}">selected="selected"</c:if>>技术经理</option>
							               		<option value="163" <c:if test="${not empty unit.zhiwei and 163 eq unit.zhiwei}">selected="selected"</c:if>>项目经理</option>
							               		<option value="164" <c:if test="${not empty unit.zhiwei and 164 eq unit.zhiwei}">selected="selected"</c:if>>系统分析工程师</option>
							               		<option value="165" <c:if test="${not empty unit.zhiwei and 165 eq unit.zhiwei}">selected="selected"</c:if>>ERP应用顾问</option>
							               		<option value="166" <c:if test="${not empty unit.zhiwei and 166 eq unit.zhiwei}">selected="selected"</c:if>>数据库工程师/管理员</option>
							               		<option value="167" <c:if test="${not empty unit.zhiwei and 167 eq unit.zhiwei}">selected="selected"</c:if>>软件开发工程师</option>
							               		<option value="168" <c:if test="${not empty unit.zhiwei and 168 eq unit.zhiwei}">selected="selected"</c:if>>硬件开发工程师</option>
							               		<option value="169" <c:if test="${not empty unit.zhiwei and 169 eq unit.zhiwei}">selected="selected"</c:if>>信息支持/安全维护</option>
							               		<option value="170" <c:if test="${not empty unit.zhiwei and 170 eq unit.zhiwei}">selected="selected"</c:if>>网页设计/编辑</option>
							               		<option value="171" <c:if test="${not empty unit.zhiwei and 171 eq unit.zhiwei}">selected="selected"</c:if>>通讯工程师</option>
							               		<option value="172" <c:if test="${not empty unit.zhiwei and 172 eq unit.zhiwei}">selected="selected"</c:if>>多媒体/游戏开发</option>
							               		<option value="173" <c:if test="${not empty unit.zhiwei and 173 eq unit.zhiwei}">selected="selected"</c:if>>采购经理/主管</option>
							               		<option value="174" <c:if test="${not empty unit.zhiwei and 174 eq unit.zhiwei}">selected="selected"</c:if>>采购专员/助理</option>
							               		<option value="175" <c:if test="${not empty unit.zhiwei and 175 eq unit.zhiwei}">selected="selected"</c:if>>贸易经理/主管</option>
							               		<option value="176" <c:if test="${not empty unit.zhiwei and 176 eq unit.zhiwei}">selected="selected"</c:if>>市场咨询/调研</option>
							               		<option value="177" <c:if test="${not empty unit.zhiwei and 177 eq unit.zhiwei}">selected="selected"</c:if>>创意与策划</option>
							               		<option value="178" <c:if test="${not empty unit.zhiwei and 178 eq unit.zhiwei}">selected="selected"</c:if>>客服经理/主管</option>
							               		<option value="179" <c:if test="${not empty unit.zhiwei and 179 eq unit.zhiwei}">selected="selected"</c:if>>客服专员/助理</option>
							               		<option value="180" <c:if test="${not empty unit.zhiwei and 180 eq unit.zhiwei}">selected="selected"</c:if>>客户关系管理</option>
							               		<option value="181" <c:if test="${not empty unit.zhiwei and 181 eq unit.zhiwei}">selected="selected"</c:if>>客户咨询/热线支持</option>
							               		<option value="182" <c:if test="${not empty unit.zhiwei and 182 eq unit.zhiwei}">selected="selected"</c:if>>销售总监</option>
							               		<option value="183" <c:if test="${not empty unit.zhiwei and 183 eq unit.zhiwei}">selected="selected"</c:if>>销售经理</option>
							               		<option value="184" <c:if test="${not empty unit.zhiwei and 184 eq unit.zhiwei}">selected="selected"</c:if>>销售经理</option>
							               		<option value="185" <c:if test="${not empty unit.zhiwei and 185 eq unit.zhiwei}">selected="selected"</c:if>>业务拓展经理</option>
							               		<option value="186" <c:if test="${not empty unit.zhiwei and 186 eq unit.zhiwei}">selected="selected"</c:if>>销售主管/主任</option>
							               		<option value="187" <c:if test="${not empty unit.zhiwei and 187 eq unit.zhiwei}">selected="selected"</c:if>>售前/售后支持</option>
							               		<option value="188" <c:if test="${not empty unit.zhiwei and 188 eq unit.zhiwei}">selected="selected"</c:if>>销售工程师</option>
							               		<option value="189" <c:if test="${not empty unit.zhiwei and 189 eq unit.zhiwei}">selected="selected"</c:if>>销售代表</option>
							               		<option value="190" <c:if test="${not empty unit.zhiwei and 190 eq unit.zhiwei}">selected="selected"</c:if>>医药代表</option>
							               		<option value="191" <c:if test="${not empty unit.zhiwei and 191 eq unit.zhiwei}">selected="selected"</c:if>>销售助理</option>
							               		<option value="192" <c:if test="${not empty unit.zhiwei and 192 eq unit.zhiwei}">selected="selected"</c:if>>专业顾问</option>
							               		<option value="193" <c:if test="${not empty unit.zhiwei and 193 eq unit.zhiwei}">selected="selected"</c:if>>咨询师</option>
							               		<option value="194" <c:if test="${not empty unit.zhiwei and 194 eq unit.zhiwei}">selected="selected"</c:if>>调研员</option>
							               		<option value="195" <c:if test="${not empty unit.zhiwei and 195 eq unit.zhiwei}">selected="selected"</c:if>>英语翻译</option>
							               		<option value="196" <c:if test="${not empty unit.zhiwei and 196 eq unit.zhiwei}">selected="selected"</c:if>>日语翻译</option>
							               		<option value="197" <c:if test="${not empty unit.zhiwei and 197 eq unit.zhiwei}">selected="selected"</c:if>>德语翻译</option>
							               		<option value="198" <c:if test="${not empty unit.zhiwei and 198 eq unit.zhiwei}">selected="selected"</c:if>>法语翻译</option>
							               		<option value="199" <c:if test="${not empty unit.zhiwei and 199 eq unit.zhiwei}">selected="selected"</c:if>>俄语翻译</option>
							               		<option value="200" <c:if test="${not empty unit.zhiwei and 200 eq unit.zhiwei}">selected="selected"</c:if>>西班牙语翻译</option>
							               		<option value="201" <c:if test="${not empty unit.zhiwei and 201 eq unit.zhiwei}">selected="selected"</c:if>>其他翻译</option>
							               		<option value="202" <c:if test="${not empty unit.zhiwei and 202 eq unit.zhiwei}">selected="selected"</c:if>>董事/总经理/总裁</option>
							               		<option value="203" <c:if test="${not empty unit.zhiwei and 203 eq unit.zhiwei}">selected="selected"</c:if>>首席执行官/运营官</option>
							               		<option value="204" <c:if test="${not empty unit.zhiwei and 204 eq unit.zhiwei}">selected="selected"</c:if>>营运总监/经理</option>
							               		<option value="205" <c:if test="${not empty unit.zhiwei and 205 eq unit.zhiwei}">selected="selected"</c:if>>合伙人</option>
							               		<option value="206" <c:if test="${not empty unit.zhiwei and 206 eq unit.zhiwei}">selected="selected"</c:if>>行政总监</option>
							               		<option value="207" <c:if test="${not empty unit.zhiwei and 207 eq unit.zhiwei}">selected="selected"</c:if> >行政经理</option>
							               		<option value="208" <c:if test="${not empty unit.zhiwei and 208 eq unit.zhiwei}">selected="selected"</c:if>>主管/主任</option>
							               		<option value="209" <c:if test="${not empty unit.zhiwei and 209 eq unit.zhiwei}">selected="selected"</c:if>>助理/秘书</option>
							               		<option value="210" <c:if test="${not empty unit.zhiwei and 210 eq unit.zhiwei}">selected="selected"</c:if>>前台/文员</option>
							               		<option value="211" <c:if test="${not empty unit.zhiwei and 211 eq unit.zhiwei}">selected="selected"</c:if>>物流经理/主管</option>
							               		<option value="212" <c:if test="${not empty unit.zhiwei and 212 eq unit.zhiwei}">selected="selected"</c:if>>物流专员/助理</option>
							               		<option value="213" <c:if test="${not empty unit.zhiwei and 213 eq unit.zhiwei}">selected="selected"</c:if>>物料/仓库管理</option>
							               		<option value="214" <c:if test="${not empty unit.zhiwei and 214 eq unit.zhiwei}">selected="selected"</c:if>>供应链</option>
							               		<option value="215" <c:if test="${not empty unit.zhiwei and 215 eq unit.zhiwei}">selected="selected"</c:if>>供应链</option>
							               		<option value="216" <c:if test="${not empty unit.zhiwei and 216 eq unit.zhiwei}">selected="selected"</c:if>>单证/报关员</option>
							               		<option value="217" <c:if test="${not empty unit.zhiwei and 217 eq unit.zhiwei}">selected="selected"</c:if>>律师/法务</option>
							               		<option value="218" <c:if test="${not empty unit.zhiwei and 218 eq unit.zhiwei}">selected="selected"</c:if>>能源/动力</option>
							               		<option value="219" <c:if test="${not empty unit.zhiwei and 219 eq unit.zhiwei}">selected="selected"</c:if>>医疗/护理</option>
							               		<option value="220" <c:if test="${not empty unit.zhiwei and 220 eq unit.zhiwei}">selected="selected"</c:if>>科研人员</option>
							               		<option value="221" <c:if test="${not empty unit.zhiwei and 221 eq unit.zhiwei}">selected="selected"</c:if>>培训/教育</option>
							               		<option value="222" <c:if test="${not empty unit.zhiwei and 222 eq unit.zhiwei}">selected="selected"</c:if>>储备干部/培训生</option>
							               		<option value="223" <c:if test="${not empty unit.zhiwei and 223 eq unit.zhiwei}">selected="selected"</c:if>>在校学生</option>
							               		<option value="224" <c:if test="${not empty unit.zhiwei and 224 eq unit.zhiwei}">selected="selected"</c:if>>其他职位</option>
	                                		</select> 
		                    				<span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">服务时间：</td>
										<td><span class="leftspace"></span>
											<input  type="text" class="input" name="yearString" id="uninBeginTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" readonly="readonly" value="<fmt:formatDate value="${unit.serveTime}" pattern="yyyy-MM-dd"/>"> 
				                			到
			                               	<input class="input" type="text"  name="yearString1" id="uninEndTime" onclick="WdatePicker({isShowClear:false,readOnly:true});" readonly="readonly" value="<fmt:formatDate value="${unit.toServeTime}" pattern="yyyy-MM-dd"/>">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作年限：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="workYear" id="workYear" >
			                                    <option value="0" <c:if test="${not empty unit.workYear and 0 eq unit.workYear}">selected="selected"</c:if>>一年以内</option>
						              			<option value="1" <c:if test="${not empty unit.workYear and 1 eq unit.workYear}">selected="selected"</c:if>>一年以上</option>
						              			<option value="2" <c:if test="${not empty unit.workYear and 2 eq unit.workYear}">selected="selected"</c:if>>两年以上</option>
						              			<option value="3" <c:if test="${not empty unit.workYear and 3 eq unit.workYear}">selected="selected"</c:if>>三年以上</option>
						              			<option value="4" <c:if test="${not empty unit.workYear and 4 eq unit.workYear}">selected="selected"</c:if>>四年年以上</option>
						              			<option value="5" <c:if test="${not empty unit.workYear and 5 eq unit.workYear}">selected="selected"</c:if>>五年以上</option>
						              			<option value="6" <c:if test="${not empty unit.workYear and 6 eq unit.workYear}">selected="selected"</c:if>>六年以上</option>
			                                </select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.workTel}" onkeyup="value=value.replace(/[^0-9]/g,'')" name="workTel" id="workTel" />
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">公司地址：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.companyAdd}" name="companyAdd" id="companyAdd" />
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">公司网站：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.companyWebsite}" name="companyWebsite" id="companyWebsite"/>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">备注说明：</td>
										<td class="bl"><span class="leftspace"></span>
											<textarea style="width:500px; height:100px; margin-top:10px;" name="remark" id="remark">${unit.remark}</textarea>
		                   					<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="javascript:subForm('unit');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 私营业主 -->
					<div class="tab_info" id="tab3" style="display: none;">
						<form action="" id="privatebusiness" method="post">
						<input type="hidden" name="id" id="id" value="${privatebusiness.id }">
						
							<div class="alerts">请填写您业主信息</div>
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
										<td><span class="leftspace"></span>
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
											<span class="wdzh-buttu" onclick="javascript:subForm('privatebusiness');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 财务状况 -->
					<div class="tab_info" id="tab4" style="display: none;">
						<form action="" id="finance" method="post">
						<input type="hidden" name="id" id="id" value="${finance.id }">
						
							<div class="alerts">请填写您财务状况</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">每月无抵押贷款还款额：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${finance.refundMoney}" name="refundMoney" id="refundMoney" class="input">
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
										<td><span class="leftspace"></span>
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
											<span class="wdzh-buttu" onclick="javascript:subForm('finance');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 联系方式 -->
					<div class="tab_info" id="tab5" style="display: none;">

						<form action="" id="relation" method="post">
						<input type="hidden" name="id" id="id" value="${relation.id }">

							<div class="alerts">请填写您的联系方式</div>
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
											<span class="wdzh-buttu" onclick="javascript:subForm('relation');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 配偶资料 -->
					<div class="tab_info" id="tab6" style="display: none;">
						<form action="" id="spouse" method="post">
						<input type="hidden" name="id" id="id" value="${spouse.id }">
						
							<div class="alerts">请填写您的配偶信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">配偶姓名：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="spouseName" name="spouseName" value="${spouse.spouseName }" class="input">
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">每月薪金：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="monthlyPay" name="monthlyPay" onkeyup="value=value.replace(/[^0-9|.]/g,'')" value="${spouse.monthlyPay}" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">移动电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="phone" name="phone" onkeyup="value=value.replace(/[^0-9|.]/g,'')" value="${spouse.phone}" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">单位电话：</td>
										<td><span class="leftspace"></span>
											<input type="text" id="unitPhone" name="unitPhone" onkeyup="value=value.replace(/[^0-9|.]/g,'')" value="${spouse.unitPhone }" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作单位：</td>
										<td><span class="leftspace"></span>
											 <select name="unitType" id="unitType"  style="width:auto">
			                        			<option value="0" <c:if test="${not empty spouse.unitType and 0 eq spouse.unitType}">selected="selected"</c:if>>农，林，牧，渔业</option>
						              			<option value="1" <c:if test="${not empty spouse.unitType and 1 eq spouse.unitType}">selected="selected"</c:if>>制造业</option>
						              			<option value="2" <c:if test="${not empty spouse.unitType and 2 eq spouse.unitType}">selected="selected"</c:if>>电子，燃气及水的生产和供应业</option>
						              			<option value="3" <c:if test="${not empty spouse.unitType and 3 eq spouse.unitType}">selected="selected"</c:if>>建筑业</option>
						              			<option value="4" <c:if test="${not empty spouse.unitType and 4 eq spouse.unitType}">selected="selected"</c:if>>交通运输，仓储和邮政业</option>
						              			<option value="5" <c:if test="${not empty spouse.unitType and 5 eq spouse.unitType}">selected="selected"</c:if>>信息传输，计算机服务和软件业</option>
						              			<option value="6" <c:if test="${not empty spouse.unitType and 6 eq spouse.unitType}">selected="selected"</c:if>>批发和零售业</option>
						              			<option value="7" <c:if test="${not empty spouse.unitType and 7 eq spouse.unitType}">selected="selected"</c:if>>金融业</option>
						              			<option value="8" <c:if test="${not empty spouse.unitType and 8 eq spouse.unitType}">selected="selected"</c:if>>地产业</option>
						              			<option value="9" <c:if test="${not empty spouse.unitType and 9 eq spouse.unitType}">selected="selected"</c:if>>采矿业</option>
						              			<option value="10" <c:if test="${not empty spouse.unitType and 10 eq spouse.unitType}">selected="selected"</c:if>>租凭和商务服务业</option>
						              			<option value="11" <c:if test="${not empty spouse.unitType and 11 eq spouse.unitType}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="12" <c:if test="${not empty spouse.unitType and 12 eq spouse.unitType}">selected="selected"</c:if>>水利，环境和公共设备管理业</option>
						              			<option value="13" <c:if test="${not empty spouse.unitType and 13 eq spouse.unitType}">selected="selected"</c:if>>居民服务和其他服务业</option>
						              			<option value="14" <c:if test="${not empty spouse.unitType and 14 eq spouse.unitType}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="15" <c:if test="${not empty spouse.unitType and 15 eq spouse.unitType}">selected="selected"</c:if>>教育</option>
						              			<option value="16" <c:if test="${not empty spouse.unitType and 16 eq spouse.unitType}">selected="selected"</c:if>>卫生，社会保障和社会福利业</option>
						              			<option value="17" <c:if test="${not empty spouse.unitType and 17 eq spouse.unitType}">selected="selected"</c:if>>文化，体育和娱乐业</option>
						              			<option value="18" <c:if test="${not empty spouse.unitType and 18 eq spouse.unitType}">selected="selected"</c:if>>公共管理和社会组织</option>
						              			<option value="19" <c:if test="${not empty spouse.unitType and 19 eq spouse.unitType}">selected="selected"</c:if>>国际组织</option>
			                               </select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">职位：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="postType" id="postType" class="input08" style="width:auto">
			                        			<option value="77" <c:if test="${not empty spouse.postType and 77 eq spouse.postType}">selected="selected"</c:if>>财务总监</option>
							              		<option value="78" <c:if test="${not empty spouse.postType and 78 eq spouse.postType}">selected="selected"</c:if>>财务主管</option>
							              		<option value="79" <c:if test="${not empty spouse.postType and 79 eq spouse.postType}">selected="selected"</c:if>>审计/税务</option>
							              		<option value="80" <c:if test="${not empty spouse.postType and 80 eq spouse.postType}">selected="selected"</c:if>>总账/成本</option>
							              		<option value="81" <c:if test="${not empty spouse.postType and 81 eq spouse.postType}">selected="selected"</c:if>>会计人员</option>
							              		<option value="82" <c:if test="${not empty spouse.postType and 82 eq spouse.postType}">selected="selected"</c:if>>人力资源总监</option>
							              		<option value="83" <c:if test="${not empty spouse.postType and 83 eq spouse.postType}">selected="selected"</c:if>>人力资源经理/主管</option>
							              		<option value="84" <c:if test="${not empty spouse.postType and 84 eq spouse.postType}">selected="selected"</c:if>>招聘经理/主管</option>
							              		<option value="85" <c:if test="${not empty spouse.postType and 85 eq spouse.postType}">selected="selected"</c:if>>薪资福利经理/主管</option>
							              		<option value="86" <c:if test="${not empty spouse.postType and 86 eq spouse.postType}">selected="selected"</c:if>>财务经理</option>
							              		<option value="87" <c:if test="${not empty spouse.postType and 87 eq spouse.postType}">selected="selected"</c:if>>培训经理/主管</option>
							               		<option value="88" <c:if test="${not empty spouse.postType and 88 eq spouse.postType}">selected="selected"</c:if>>人事专员/助理</option>
							               		<option value="89" <c:if test="${not empty spouse.postType and 89 eq spouse.postType}">selected="selected"</c:if>>行长/副行长</option>
							               		<option value="90" <c:if test="${not empty spouse.postType and 90 eq spouse.postType}">selected="selected"</c:if>>信贷/信用管理</option>
							               		<option value="91" <c:if test="${not empty spouse.postType and 91 eq spouse.postType}">selected="selected"</c:if>>资产管理/评估</option>
							               		<option value="92" <c:if test="${not empty spouse.postType and 92 eq spouse.postType}">selected="selected"</c:if>>投融资项目/基金</option>
							               		<option value="93" <c:if test="${not empty spouse.postType and 93 eq spouse.postType}">selected="selected"</c:if>>外汇管理/国际结算</option>
							               		<option value="94" <c:if test="${not empty spouse.postType and 94 eq spouse.postType}">selected="selected"</c:if>>风险管理</option>
							               		<option value="95" <c:if test="${not empty spouse.postType and 95 eq spouse.postType}">selected="selected"</c:if>>证券/期货</option>
							               		<option value="96" <c:if test="${not empty spouse.postType and 96 eq spouse.postType}">selected="selected"</c:if>>客户经理/主管</option>
							               		<option value="97" <c:if test="${not empty spouse.postType and 97 eq spouse.postType}">selected="selected"</c:if>>核保/理赔</option>
							               		<option value="98" <c:if test="${not empty spouse.postType and 98 eq spouse.postType}">selected="selected"</c:if>>保险代理人/内勤</option>
							               		<option value="99" <c:if test="${not empty spouse.postType and 99 eq spouse.postType}">selected="selected"</c:if>>银行专员/柜台</option>
							               		<option value="100" <c:if test="${not empty spouse.postType and 100 eq spouse.postType}">selected="selected"</c:if>>精算师</option>
							               		<option value="101" <c:if test="${not empty spouse.postType and 101 eq spouse.postType}">selected="selected"</c:if>>编辑/记者/文案</option>
							               		<option value="102" <c:if test="${not empty spouse.postType and 102 eq spouse.postType}">selected="selected"</c:if>>版面设计</option>
							               		<option value="103" <c:if test="${not empty spouse.postType and 103 eq spouse.postType}">selected="selected"</c:if>>平面设计</option>
							               		<option value="104" <c:if test="${not empty spouse.postType and 104 eq spouse.postType}">selected="selected"</c:if>>装潢/陈列设计</option>
							               		<option value="105" <c:if test="${not empty spouse.postType and 105 eq spouse.postType}">selected="selected"</c:if>>纺织/服装设计</option>
							               		<option value="106" <c:if test="${not empty spouse.postType and 106 eq spouse.postType}">selected="selected"</c:if>>工艺品/珠宝设计</option>
							               		<option value="107" <c:if test="${not empty spouse.postType and 107 eq spouse.postType}">selected="selected"</c:if>>经理</option>
							               		<option value="108" <c:if test="${not empty spouse.postType and 108 eq spouse.postType}">selected="selected"</c:if>>主任</option>
							               		<option value="109" <c:if test="${not empty spouse.postType and 109 eq spouse.postType}">selected="selected"</c:if>>生物工程</option>
							               		<option value="110" <c:if test="${not empty spouse.postType and 110 eq spouse.postType}">selected="selected"</c:if>>药物注册</option>
							               		<option value="111" <c:if test="${not empty spouse.postType and 111 eq spouse.postType}">selected="selected"</c:if>>临床研究/协调</option>
							               		<option value="112" <c:if test="${not empty spouse.postType and 112 eq spouse.postType}">selected="selected"</c:if>>药物研发/分析工程师</option>
							               		<option value="113" <c:if test="${not empty spouse.postType and 113 eq spouse.postType}">selected="selected"</c:if>>化学工程师</option>
							               		<option value="114" <c:if test="${not empty spouse.postType and 114 eq spouse.postType}">selected="selected"</c:if>>环保工程师</option>
							               		<option value="115" <c:if test="${not empty spouse.postType and 115 eq spouse.postType}">selected="selected"</c:if>>化工人员</option>
							               		<option value="116" <c:if test="${not empty spouse.postType and 116 eq spouse.postType}">selected="selected"</c:if>>结构/建筑工程师</option>
							               		<option value="117" <c:if test="${not empty spouse.postType and 117 eq spouse.postType}">selected="selected"</c:if>>土建工程师/建造师</option>
							               		<option value="118" <c:if test="${not empty spouse.postType and 118 eq spouse.postType}">selected="selected"</c:if>>机电/给排水/暖通工程师</option>
							               		<option value="119" <c:if test="${not empty spouse.postType and 119 eq spouse.postType}">selected="selected"</c:if>>工程造价师/预决算</option>
							               		<option value="120" <c:if test="${not empty spouse.postType and 120 eq spouse.postType}">selected="selected"</c:if>>监理/安全工程师</option>
							               		<option value="121" <c:if test="${not empty spouse.postType and 121 eq spouse.postType}">selected="selected"</c:if>>房地产开发/策划</option>
							               		<option value="122" <c:if test="${not empty spouse.postType and 122 eq spouse.postType}">selected="selected"</c:if>>园林景观/城市规划师</option>
							               		<option value="123" <c:if test="${not empty spouse.postType and 123 eq spouse.postType}">selected="selected"</c:if>>物业管理</option>
							               		<option value="124" <c:if test="${not empty spouse.postType and 124 eq spouse.postType}">selected="selected"</c:if>>房地产评估/中介/交易</option>
							               		<option value="125" <c:if test="${not empty spouse.postType and 125 eq spouse.postType}">selected="selected"</c:if>>电子/电路工程师</option>
							               		<option value="126" <c:if test="${not empty spouse.postType and 126 eq spouse.postType}">selected="selected"</c:if>>电气工程师</option>
							               		<option value="127" <c:if test="${not empty spouse.postType and 127 eq spouse.postType}">selected="selected"</c:if>>半导体工程师</option>
							               		<option value="128" <c:if test="${not empty spouse.postType and 128 eq spouse.postType}">selected="selected"</c:if>>测试工程师</option>
							               		<option value="129" <c:if test="${not empty spouse.postType and 129 eq spouse.postType}">selected="selected"</c:if>>自动化工程师</option>
							               		<option value="130" <c:if test="${not empty spouse.postType and 130 eq spouse.postType}">selected="selected"</c:if>>光学工程师</option>
							               		<option value="131" <c:if test="${not empty spouse.postType and 131 eq spouse.postType}">selected="selected"</c:if> >电力工程师</option>
							               		<option value="132" <c:if test="${not empty spouse.postType and 132 eq spouse.postType}">selected="selected"</c:if>>水利/水电工程师</option>
							               		<option value="133" <c:if test="${not empty spouse.postType and 133 eq spouse.postType}">selected="selected"</c:if>>单片机/DLC/DSP/底层软件开发</option>
							               		<option value="134" <c:if test="${not empty spouse.postType and 134 eq spouse.postType}">selected="selected"</c:if>>其他工程师</option>
							               		<option value="135" <c:if test="${not empty spouse.postType and 135 eq spouse.postType}">selected="selected"</c:if>>技术人员</option>
							               		<option value="136" <c:if test="${not empty spouse.postType and 136 eq spouse.postType}">selected="selected"</c:if>>助理</option>
							               		<option value="137" <c:if test="${not empty spouse.postType and 137 eq spouse.postType}">selected="selected"</c:if>>厂长/副厂长</option>
							               		<option value="138" <c:if test="${not empty spouse.postType and 138 eq spouse.postType}">selected="selected"</c:if>>生产经理/车间主任</option>
							               		<option value="139" <c:if test="${not empty spouse.postType and 139 eq spouse.postType}">selected="selected"</c:if>>产品开发</option>
							               		<option value="140" <c:if test="${not empty spouse.postType and 140 eq spouse.postType}">selected="selected"</c:if>>工业/产品设计</option>
							               		<option value="141" <c:if test="${not empty spouse.postType and 141 eq spouse.postType}">selected="selected"</c:if>>生产/计划/调度</option>
							               		<option value="142" <c:if test="${not empty spouse.postType and 142 eq spouse.postType}">selected="selected"</c:if>>质量管理</option>
							               		<option value="143" <c:if test="${not empty spouse.postType and 143 eq spouse.postType}">selected="selected"</c:if>>项目管理</option>
							               		<option value="144" <c:if test="${not empty spouse.postType and 144 eq spouse.postType}">selected="selected"</c:if>>工程设备管理</option>
							               		<option value="145" <c:if test="${not empty spouse.postType and 145 eq spouse.postType}">selected="selected"</c:if>>安全/健康/环境管理</option>
							               		<option value="146" <c:if test="${not empty spouse.postType and 146 eq spouse.postType}">selected="selected"</c:if>>生产工艺/技术</option>
							               		<option value="147" <c:if test="${not empty spouse.postType and 147 eq spouse.postType}">selected="selected"</c:if>>技工</option>
							               		<option value="148" <c:if test="${not empty spouse.postType and 148 eq spouse.postType}">selected="selected"</c:if>>机械工程师</option>
							               		<option value="149" <c:if test="${not empty spouse.postType and 149 eq spouse.postType}">selected="selected"</c:if>>维修工程师</option>
							               		<option value="150" <c:if test="${not empty spouse.postType and 150 eq spouse.postType}">selected="selected"</c:if>>机械设计/制图</option>
							               		<option value="151" <c:if test="${not empty spouse.postType and 151 eq spouse.postType}">selected="selected"</c:if>>机电工程师</option>
							               		<option value="152" <c:if test="${not empty spouse.postType and 152 eq spouse.postType}">selected="selected"</c:if>>模具工程师</option>
							               		<option value="153" <c:if test="${not empty spouse.postType and 153 eq spouse.postType}">selected="selected"</c:if>>精密机械/仪器仪表</option>
							               		<option value="154" <c:if test="${not empty spouse.postType and 154 eq spouse.postType}">selected="selected"</c:if>>船舶工程师</option>
							               		<option value="155" <c:if test="${not empty spouse.postType and 155 eq spouse.postType}">selected="selected"</c:if>>冲压/注塑/焊接</option>
							               		<option value="156" <c:if test="${not empty spouse.postType and 156 eq spouse.postType}">selected="selected"</c:if>>餐饮/娱乐管理</option>
							               		<option value="157" <c:if test="${not empty spouse.postType and 157 eq spouse.postType}">selected="selected"</c:if>>酒店/宾馆管理</option>
							               		<option value="158" <c:if test="${not empty spouse.postType and 158 eq spouse.postType}">selected="selected"</c:if>>服务人员/领班</option>
							               		<option value="159" <c:if test="${not empty spouse.postType and 159 eq spouse.postType}">selected="selected"</c:if>>导游/旅行顾问/票务</option>
							               		<option value="160" <c:if test="${not empty spouse.postType and 160 eq spouse.postType}">selected="selected"</c:if>>营业员/收银员</option>
							               		<option value="161" <c:if test="${not empty spouse.postType and 161 eq spouse.postType}">selected="selected"</c:if>>技术总监/首席技术执行官</option>
							               		<option value="162" <c:if test="${not empty spouse.postType and 162 eq spouse.postType}">selected="selected"</c:if>>技术经理</option>
							               		<option value="163" <c:if test="${not empty spouse.postType and 163 eq spouse.postType}">selected="selected"</c:if>>项目经理</option>
							               		<option value="164" <c:if test="${not empty spouse.postType and 164 eq spouse.postType}">selected="selected"</c:if>>系统分析工程师</option>
							               		<option value="165" <c:if test="${not empty spouse.postType and 165 eq spouse.postType}">selected="selected"</c:if>>ERP应用顾问</option>
							               		<option value="166" <c:if test="${not empty spouse.postType and 166 eq spouse.postType}">selected="selected"</c:if>>数据库工程师/管理员</option>
							               		<option value="167" <c:if test="${not empty spouse.postType and 167 eq spouse.postType}">selected="selected"</c:if>>软件开发工程师</option>
							               		<option value="168" <c:if test="${not empty spouse.postType and 168 eq spouse.postType}">selected="selected"</c:if>>硬件开发工程师</option>
							               		<option value="169" <c:if test="${not empty spouse.postType and 169 eq spouse.postType}">selected="selected"</c:if>>信息支持/安全维护</option>
							               		<option value="170" <c:if test="${not empty spouse.postType and 170 eq spouse.postType}">selected="selected"</c:if>>网页设计/编辑</option>
							               		<option value="171" <c:if test="${not empty spouse.postType and 171 eq spouse.postType}">selected="selected"</c:if>>通讯工程师</option>
							               		<option value="172" <c:if test="${not empty spouse.postType and 172 eq spouse.postType}">selected="selected"</c:if>>多媒体/游戏开发</option>
							               		<option value="173" <c:if test="${not empty spouse.postType and 173 eq spouse.postType}">selected="selected"</c:if>>采购经理/主管</option>
							               		<option value="174" <c:if test="${not empty spouse.postType and 174 eq spouse.postType}">selected="selected"</c:if>>采购专员/助理</option>
							               		<option value="175" <c:if test="${not empty spouse.postType and 175 eq spouse.postType}">selected="selected"</c:if>>贸易经理/主管</option>
							               		<option value="176" <c:if test="${not empty spouse.postType and 176 eq spouse.postType}">selected="selected"</c:if>>市场咨询/调研</option>
							               		<option value="177" <c:if test="${not empty spouse.postType and 177 eq spouse.postType}">selected="selected"</c:if>>创意与策划</option>
							               		<option value="178" <c:if test="${not empty spouse.postType and 178 eq spouse.postType}">selected="selected"</c:if>>客服经理/主管</option>
							               		<option value="179" <c:if test="${not empty spouse.postType and 179 eq spouse.postType}">selected="selected"</c:if>>客服专员/助理</option>
							               		<option value="180" <c:if test="${not empty spouse.postType and 180 eq spouse.postType}">selected="selected"</c:if>>客户关系管理</option>
							               		<option value="181" <c:if test="${not empty spouse.postType and 181 eq spouse.postType}">selected="selected"</c:if>>客户咨询/热线支持</option>
							               		<option value="182" <c:if test="${not empty spouse.postType and 182 eq spouse.postType}">selected="selected"</c:if>>销售总监</option>
							               		<option value="183" <c:if test="${not empty spouse.postType and 183 eq spouse.postType}">selected="selected"</c:if>>销售经理</option>
							               		<option value="184" <c:if test="${not empty spouse.postType and 184 eq spouse.postType}">selected="selected"</c:if>>销售经理</option>
							               		<option value="185" <c:if test="${not empty spouse.postType and 185 eq spouse.postType}">selected="selected"</c:if>>业务拓展经理</option>
							               		<option value="186" <c:if test="${not empty spouse.postType and 186 eq spouse.postType}">selected="selected"</c:if>>销售主管/主任</option>
							               		<option value="187" <c:if test="${not empty spouse.postType and 187 eq spouse.postType}">selected="selected"</c:if>>售前/售后支持</option>
							               		<option value="188" <c:if test="${not empty spouse.postType and 188 eq spouse.postType}">selected="selected"</c:if>>销售工程师</option>
							               		<option value="189" <c:if test="${not empty spouse.postType and 189 eq spouse.postType}">selected="selected"</c:if>>销售代表</option>
							               		<option value="190" <c:if test="${not empty spouse.postType and 190 eq spouse.postType}">selected="selected"</c:if>>医药代表</option>
							               		<option value="191" <c:if test="${not empty spouse.postType and 191 eq spouse.postType}">selected="selected"</c:if>>销售助理</option>
							               		<option value="192" <c:if test="${not empty spouse.postType and 192 eq spouse.postType}">selected="selected"</c:if>>专业顾问</option>
							               		<option value="193" <c:if test="${not empty spouse.postType and 193 eq spouse.postType}">selected="selected"</c:if>>咨询师</option>
							               		<option value="194" <c:if test="${not empty spouse.postType and 194 eq spouse.postType}">selected="selected"</c:if>>调研员</option>
							               		<option value="195" <c:if test="${not empty spouse.postType and 195 eq spouse.postType}">selected="selected"</c:if>>英语翻译</option>
							               		<option value="196" <c:if test="${not empty spouse.postType and 196 eq spouse.postType}">selected="selected"</c:if>>日语翻译</option>
							               		<option value="197" <c:if test="${not empty spouse.postType and 197 eq spouse.postType}">selected="selected"</c:if>>德语翻译</option>
							               		<option value="198" <c:if test="${not empty spouse.postType and 198 eq spouse.postType}">selected="selected"</c:if>>法语翻译</option>
							               		<option value="199" <c:if test="${not empty spouse.postType and 199 eq spouse.postType}">selected="selected"</c:if>>俄语翻译</option>
							               		<option value="200" <c:if test="${not empty spouse.postType and 200 eq spouse.postType}">selected="selected"</c:if>>西班牙语翻译</option>
							               		<option value="201" <c:if test="${not empty spouse.postType and 201 eq spouse.postType}">selected="selected"</c:if>>其他翻译</option>
							               		<option value="202" <c:if test="${not empty spouse.postType and 202 eq spouse.postType}">selected="selected"</c:if>>董事/总经理/总裁</option>
							               		<option value="203" <c:if test="${not empty spouse.postType and 203 eq spouse.postType}">selected="selected"</c:if>>首席执行官/运营官</option>
							               		<option value="204" <c:if test="${not empty spouse.postType and 204 eq spouse.postType}">selected="selected"</c:if>>营运总监/经理</option>
							               		<option value="205" <c:if test="${not empty spouse.postType and 205 eq spouse.postType}">selected="selected"</c:if>>合伙人</option>
							               		<option value="206" <c:if test="${not empty spouse.postType and 206 eq spouse.postType}">selected="selected"</c:if>>行政总监</option>
							               		<option value="207" <c:if test="${not empty spouse.postType and 207 eq spouse.postType}">selected="selected"</c:if> >行政经理</option>
							               		<option value="208" <c:if test="${not empty spouse.postType and 208 eq spouse.postType}">selected="selected"</c:if>>主管/主任</option>
							               		<option value="209" <c:if test="${not empty spouse.postType and 209 eq spouse.postType}">selected="selected"</c:if>>助理/秘书</option>
							               		<option value="210" <c:if test="${not empty spouse.postType and 210 eq spouse.postType}">selected="selected"</c:if>>前台/文员</option>
							               		<option value="211" <c:if test="${not empty spouse.postType and 211 eq spouse.postType}">selected="selected"</c:if>>物流经理/主管</option>
							               		<option value="212" <c:if test="${not empty spouse.postType and 212 eq spouse.postType}">selected="selected"</c:if>>物流专员/助理</option>
							               		<option value="213" <c:if test="${not empty spouse.postType and 213 eq spouse.postType}">selected="selected"</c:if>>物料/仓库管理</option>
							               		<option value="214" <c:if test="${not empty spouse.postType and 214 eq spouse.postType}">selected="selected"</c:if>>供应链</option>
							               		<option value="215" <c:if test="${not empty spouse.postType and 215 eq spouse.postType}">selected="selected"</c:if>>供应链</option>
							               		<option value="216" <c:if test="${not empty spouse.postType and 216 eq spouse.postType}">selected="selected"</c:if>>单证/报关员</option>
							               		<option value="217" <c:if test="${not empty spouse.postType and 217 eq spouse.postType}">selected="selected"</c:if>>律师/法务</option>
							               		<option value="218" <c:if test="${not empty spouse.postType and 218 eq spouse.postType}">selected="selected"</c:if>>能源/动力</option>
							               		<option value="219" <c:if test="${not empty spouse.postType and 219 eq spouse.postType}">selected="selected"</c:if>>医疗/护理</option>
							               		<option value="220" <c:if test="${not empty spouse.postType and 220 eq spouse.postType}">selected="selected"</c:if>>科研人员</option>
							               		<option value="221" <c:if test="${not empty spouse.postType and 221 eq spouse.postType}">selected="selected"</c:if>>培训/教育</option>
							               		<option value="222" <c:if test="${not empty spouse.postType and 222 eq spouse.postType}">selected="selected"</c:if>>储备干部/培训生</option>
							               		<option value="223" <c:if test="${not empty spouse.postType and 223 eq spouse.postType}">selected="selected"</c:if>>在校学生</option>
							               		<option value="224" <c:if test="${not empty spouse.postType and 224 eq spouse.postType}">selected="selected"</c:if>>其他职位</option>
			                               </select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">单位地址：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="unitAddress" name="unitAddress" value="${spouse.unitAddress }" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">月收入：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" id="unitIncome" name="unitIncome" onkeyup="value=value.replace(/[^0-9|.]/g,'')" value="${spouse.unitIncome }" class="input">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="javascript:subForm('spouse');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 教育背景 -->
					<div class="tab_info" id="tab7" style="display: none;">
						<form action="" id="education" method="post">
						<input type="hidden" name="id" id="id" value="${education.id }">
						
							<div class="alerts">请填写您教育背景</div>
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
											<span class="wdzh-buttu" onclick="javascript:subForm('education');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
					<!-- 其他 -->
					<div class="tab_info" id="tab8" style="display: none;">
						<form action="" id="other" method="post">
						<input type="hidden" name="id" id="id" value="${other.id }" />
						
							<div class="alerts">请填写您其他信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">个人能力：</td>
										<td class="bl"><span class="leftspace"></span>
											<textarea style="width:300px; height:80px; margin-top:10px;" id="personalAbility" name="personalAbility" onblur="notNull('personalAbility');">${other.personalAbility }</textarea>
		                  	 				<br><span style="font-size: 12px;margin-left: 20px;">（如电脑能力、组织协调能力或其他）</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">个人爱好：</td>
										<td><span class="leftspace"></span>
											<textarea style="width:300px; height:80px; margin-top:10px;" id="personalHobbies" name="personalHobbies" onblur="notNull('personalHobbies');">${other.personalHobbies }</textarea>
		                   					<br><span style="font-size: 12px;margin-left: 20px;">（突出自己的个性，工作态度或他人对自己的评价等）</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">其他说明：</td>
										<td class="bl"><span class="leftspace"></span>
										<textarea style="width:300px; height:80px; margin-top:10px;" id="otherState" name="otherState" onblur="notNull('otherState');">${other.otherState}</textarea>
		                    			<br><span style="font-size: 12px;margin-left: 20px;">个人能力、个人爱好、其他说明必须填一项！</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;" onclick="javascript:subForm('other');">确认</span>
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

$(function(){
	 var nation = "${user.userNation }";
	 if(nation){
		 $("select[name='userNation']").val(nation);
	 }
})

	function tabChange(obj){
		if(obj==1){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab1").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").show();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide(); 
		}else if(obj==0){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab0").addClass("userDh");
			$("#tab0").show(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide(); 
		}else if(obj==2){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab2").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").show(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide();
		}else if(obj==3){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab3").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").show(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide();
		}else if(obj==4){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab4").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").show(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide();
		}else if(obj==5){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab5").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").show(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").hide();
		}
		else if(obj==6){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab6").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").show(); 
			$("#tab7").hide(); 
			$("#tab8").hide();
		}else if(obj==7){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab7").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").show(); 
			$("#tab8").hide();
		}else if(obj==8){
			$("#attul").find(".userDh").removeClass("userDh");
			$("#loanTab8").addClass("userDh");
			$("#tab0").hide(); 
			$("#tab1").hide();
			$("#tab2").hide(); 
			$("#tab3").hide(); 
			$("#tab4").hide(); 
			$("#tab5").hide(); 
			$("#tab6").hide(); 
			$("#tab7").hide(); 
			$("#tab8").show();
		}
	}
function subForm(obj) {
	//alert(11);
	//$("#formVideo").submit();
	var url = "";
	switch (obj) {
	case 'presonal':
		url = "${path}/basics/savaPersonal.do";
		break;
	case 'house':
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
		break;
	case 'unit':
		if ($("#companyName").val() == "") {
			alert("请输入单位名称");
			$("#companyName").focus();
			return;
		}

		if ($("#uninBeginTime").val() == "") {
			alert("请输入服务时间");
			$("#uninBeginTime").focus();
			return;
		}

		if ($("#uninEndTime").val() == "") {
			alert("请输入服务时间");
			$("#uninEndTime").focus();
			return;
		}

		//var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if ($("#workTel").val() == "") {
			alert("请输入工作电话");
			$("#workTel").focus();
			return;
		} 
		/* else if (!workTelPattern.test($("#workTel").val())) {
			alert("请输入合法电话");
			$("#workTel").focus();
			return;
		} */

		if ($("#companyAdd").val() == "") {
			alert("请输入公司地址");
			$("#companyAdd").focus();
			return;
		}
		url = "${path}/basics/savaUnit.do";
		break;
	case 'other':
		//不允许为空
		function notNull(ids) {
			var names = document.getElementById(ids).value;
			if (names == "") {

				return false;
			} else {

				return true;
			}
		}
		function dd() {
			if (notNull('personalAbility') == false
					&& notNull('personalHobbies') == false
					&& notNull('otherState') == false) {
				alert("必填一项!");
				return false;
			} else {
				return true;
			}
		}
		if (dd() == false) {
			return;
		}
		url = "${path}/basics/savaOther.do";
		break;
	case 'spouse':
		var temp = /^\d+(\.\d+)?$/;
		//配偶姓名
		if ($("#spouseName").val() == "") {
			alert("必填，配偶姓名不允许为空！");
			$("#spouseName").focus();
			return;
		}
		//每月薪金
		if ($("#monthlyPay").val() == "") {
			alert("必填，月薪不允许为空！");
			$("#monthlyPay").focus();
			return;
		}else {
			if(temp.test($("#monthlyPay").val())==false){
				alert("每月薪金必须大于0");
				$("#monthlyPay").focus();
				return false;
			}
		}
		//月收入
		if ($("#unitIncome").val() == "") {
			alert("必填，月收入不允许为空！");
			$("#unitIncome").focus();
			return;
		} else {
			if(temp.test($("#unitIncome").val())==false){
				alert("每月薪金必须大于0");
				$("#unitIncome").focus();
				return;
			}
		}
		//移动电话

		var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if ($("#phone").val() == "") {
			alert("请输入手机号码");
			$("#phone").focus();
			return;
		} else if (!workTelPattern.test($("#phone").val())) {
			alert("请输入合法电话");
			$("#phone").focus();
			return;
		}
		//单位电话

		if ($("#unitPhone").val() == "") {
			alert("请输入工作电话");
			$("#unitPhone").focus();
			return;
		} else if (!workTelPattern.test($("#unitPhone").val())) {
			alert("请输入合法电话");
			$("#unitPhone").focus();
			return;
		}
		url = "${path}/basics/savaSpouse.do";
		break;
	case 'finance':
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
		break;
	case 'relation':
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
		break;
	case 'privatebusiness':
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
		break;
	case 'education':
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
		break;

	}
		
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
	new PCAS("userProvince","userCity","userArea",'${user.userProvince}','${user.userCity}','${user.userArea}');
</script>
</html>