<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<form method="post" action="sys/${PRE_PATH_EDIT }saveSysFeesRate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="id" value="${entity.id}"/>
				<div class="pageFormContent" layoutH="55">
					 <p><label>最大借款利率</label><input type="text" size="22" name="sysLoanRate" alt="请输入借款利息费" maxlength="22" value="<fmt:formatNumber value="${entity.sysLoanRate }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>借款手续费</label><input type="text" size="22" name="sysLoanPoundage" alt="请输入借款手续费" maxlength="24" value="<fmt:formatNumber value="${entity.sysLoanPoundage }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按万分之几‱</span></p>
					<div class="divider"></div>
					
					<p><label>最大借款额</label><input type="text" size="22" name="sysMaxLoan" alt="请输入最大借款额" maxlength="22" value="${entity.sysMaxLoan }" class="digits"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">万元</span></p>
					<div class="divider"></div>
					
					 <p><label>提现手续费</label><input type="text" size="22" name="sysWithdrawalPoundage" alt="请输入提现手续费" maxlength="24" value="<fmt:formatNumber value="${entity.sysWithdrawalPoundage }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按千分之几‰<!-- (线下充值) --></span></p>
					<div class="divider"></div> 
					 <p><label>提现手续费</label><input type="text" size="22" name="sysWithdrawalOne" alt="请输入提现手续费" maxlength="24" value="<fmt:formatNumber value="${entity.sysWithdrawalOne }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按每笔收费<!-- (线下充值) --></span></p>
					<div class="divider"></div> 
					
					<%-- <p><label>提现手续费</label><input type="text" size="22" name="sysWithdrawalPoundageOnline" alt="请输入提现手续费" maxlength="24" value="<fmt:formatNumber value="${entity.sysWithdrawalPoundageOnline }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按千分之几‰(线上充值)</span></p>
					<div class="divider"></div>  --%>
					
					
					<p><label>在线充值手续费</label><input type="text" size="22" name="sysOnlinePoundage" alt="请输入在线充值手续费" maxlength="24" value="<fmt:formatNumber value="${entity.sysOnlinePoundage }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按万分之几‱</span></p>
					<div class="divider"></div>
					
					<p><label>最大提现</label><input type="text" size="22" name="sysMaxWithdrawal" alt="请输入最大提现" maxlength="24" value="<fmt:formatNumber value="${entity.sysMaxWithdrawal }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>最小提现</label><input type="text" size="22" name="sysMinWithdrawal" alt="请输入最小提现" maxlength="24" value="<fmt:formatNumber value="${entity.sysMinWithdrawal }" pattern="0.00" type="number"/>" min="100" class="number"/></p>
					<div class="divider"></div>
					<%-- <p><label>收费提现期限</label><input type="text" size="22" name="sysWithdrawalDay" alt="请输入最小提现" maxlength="24" value="${entity.sysWithdrawalDay }"  class="number"/></p>
					<div class="divider"></div> --%>
					
					<p><label>续投奖励</label><input type="text" size="22" name="sysContinueReward" alt="请输入续投奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysContinueReward }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按万分之几‱</span></p>
					<div class="divider"></div>
					
					<p><label>注册奖励方式</label><input type="text" size="22" name="sysRegisteredType" alt="请输入注册奖励方式" maxlength="24" value="${entity.sysRegisteredType }" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">1,注册送。2三个认证送3，投标达到一定资金送</span></p>
					<div class="divider"></div>
					
					<p><label>注册奖励</label><input type="text" size="22" name="sysRegisteredReward" alt="请输入注册奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysRegisteredReward }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>注册奖励投标金额下限</label><input type="text" size="22" name="sysRegisteredTenderMoney" alt="注册奖励投标金额下限" maxlength="24" value="<fmt:formatNumber value="${entity.sysRegisteredTenderMoney }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>逾期利息</label><input type="text" size="22" name="sysOverdueRate" alt="请输入逾期利息" maxlength="24" value="<fmt:formatNumber value="${entity.sysOverdueRate }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;" style="float:left; width:120px; padding:0 5px; line-height:21px;">按万分之几‱，逾期一天的费率</span></p>
					<div class="divider"></div>
					
					<p><label>逾期利息管理费</label><input type="text" size="22" name="sysPlatformOverdueRate" alt="请输入逾期利息管理费" maxlength="24" value="<fmt:formatNumber value="${entity.sysOverdueRate }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">平台在逾期利息中收取一定的管理费，按百分比%</span></p>
					<div class="divider"></div>
					
					<p><label>线下充值奖励</label><input type="text" size="22" name="sysOfflineReward" alt="请输入线下充值奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysOfflineReward }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按千分之几‰</span></p>
					<div class="divider"></div>
					
					<p><label>线下充值最小金额</label><input type="text" size="22" name="sysOfflineMinmoney" alt="请输入线下充值最小金额" maxlength="24" value="<fmt:formatNumber value="${entity.sysOfflineMinmoney }" pattern="0.00" type="number"/>" min="100" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>线下充值获得奖励最小金额</label><input type="text" size="22" name="sysOfflineRewardMinmoney" alt="请输入线下充值获得奖励最小金额" maxlength="24" value="<fmt:formatNumber value="${entity.sysOfflineRewardMinmoney}" pattern="0.00" type="number"/>" min="100" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">收费时间为 day+1</span></p>
					<div class="divider"></div>
					
					<p><label>利息管理费</label><input type="text" size="22" name="sysInterestRate" alt="请输入利息管理费" maxlength="24" value="<fmt:formatNumber value="${entity.sysInterestRate }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按百分之几%</span></p>
					<div class="divider"></div>
					
					<p><label>实名认证费</label><input type="text" size="22" name="sysAuthRate" alt="请输入实名认证费" maxlength="24" value="<fmt:formatNumber value="${entity.sysAuthRate }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>视频认证费</label><input type="text" size="22" name="sysVideoRate" alt="请输入视频认证费" maxlength="24" value="<fmt:formatNumber value="${entity.sysVideoRate }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<p><label>VIP会员费</label><input type="text" size="22" name="sysVipRate" alt="请输入VIP会员费" maxlength="24" value="<fmt:formatNumber value="${entity.sysVipRate }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div>
					
					<%-- <p><label>邀请奖励A</label><input type="text" size="22" name="sysInviteReward" alt="请输入邀请奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysInviteReward }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按固定金额，邀请一人奖励一次</span></p>
					<div class="divider"></div>
					
					<p><label>邀请奖励B</label><input type="text" size="22" name="sysInviteRewardRate" alt="请输入邀请奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysInviteRewardRate }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">被邀请人在进行投资回款时，平台付给邀请人一定比例的金额(按照利息管理费的百分比)</span></p>
					<div class="divider"></div> 
					
				    <p><label>邀请奖励C</label><input type="text" size="22" name="sysInviteRewardMoney" alt="请输入邀请奖励" maxlength="24" value="<fmt:formatNumber value="${entity.sysInviteRewardMoney }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">被邀请人投资总额的千分比给邀请人</span></p>
					<div class="divider"></div>
					--%>
					<p><label>投资最低额</label><input type="text" size="22" name="sysInvestMinmoney" alt="请输入开始投资最低额" maxlength="24"  value="<fmt:formatNumber value="${entity.sysInvestMinmoney }" pattern="0.00" type="number"/>" class="number"/></p>
					<div class="divider"></div> 
					
					<p><label>发布净值标比例</label><input type="text" size="22" name="sysWorthRate" alt="请输入发布净值标比例" maxlength="22" value="<fmt:formatNumber value="${entity.sysWorthRate }" pattern="0.00" type="number"/>" class="number"/><span style="float:left; width:340px; padding:0 5px; line-height:21px;">按百分比%</span></p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
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
		</form>
</body>
</html>