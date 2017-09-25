<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="report/${PRE_PATH_VIEW }getReportList?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>起始时间: 
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>截止时间: 
						<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly" maxDate="{%y}-%M-{%d}"  />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<script type="text/javascript">
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
	</script>
	
	<div class="pageContent">
	
		<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'report/o_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${params.right_id}&&beginTime=${params.beginTime}&&endTime= ${params.endTime}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			
		<!-- 数据表 -->
		<table class="list" width="2600px"  layoutH="90">
			<thead>
				<tr>
					<th  width="2%"  align="center">序号</th>
					<th 	width="3%"   align="center">统计时间</th>
					<th 	width="3%"   align="center">注册人数</th>
					<th  width="3%"  align="center">VIP认证数</th>
					<th 	width="3%"   align="center">实名认证数</th>
					<th width="3%"   align="center">手机认证数</th>
					<th  width="3%"  align="center">借款手续费</th>
					<th  width="3%"  align="center">利息管理费</th>
					<th  width="3%"  align="center">提现手续费</th>
					<th  width="3%"  align="center">vip管理费</th>
					<th  width="3%"  align="center">充值手续费</th>
					
				    <th  width="3%"   align="center">逾期已还款数量</th>
					<th  width="3%"   align="center">逾期已还款金额</th>
					<th  width="3%"   align="center">新增逾期数</th>
					<th  width="4%"   align="center">新增逾期金额</th>
					<th  width="3%"   align="center">线上充值</th>
					<th  width="3%"   align="center">线下充值</th>
					<th  width="3%"   align="center">提现</th>
					<th  width="3%"   align="center">充值-提现</th>
					<th  width="3%"   align="center">利息</th>
					
					<th  width="3%"    align="center">投标奖励</th>
					<th   width="3%"   align="center">线下充值奖励</th>
					<th   width="3%"   align="center">续投奖励</th>
					<th  width="3%"    align="center">推荐奖励</th>
					<th  width="3%"    align="center">注册奖励</th>

					<th  width="3%"    align="center">发布数量</th>
					<th  width="3%"    align="center">发布总额</th>
					<th  width="3%"    align="center">还款数</th>
					<th  width="3%"    align="center">还款总额</th>
					<th  width="3%"    align="center">投标数</th>
					<th  width="3%"    align="center">投标总额</th>
					<th  width="3%"    align="center">有效投标数</th>
					<th  width="3%"    align="center">有效投标总额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pm.list }"   var="re">
					<tr target="usrId" rel="${re.id }">
					
						<th align="center">${re.id }</th>
						<th align="center">
							<fmt:formatDate value="${re.reportDate }" pattern="yyyy-MM-dd" />
						</th>
						<th align="center">${re.registerCount }</th>
						<th align="center">${re.vipCount }</th>
						<th align="center">${re.attestationRealnameCount }</th>
						<th align="center">${re.attestationTelCount }</th>
						
						<th align="center">${re.borrowFee }</th>
						<th align="center">${re.interestFee }</th>
						<th align="center">${re.cashFee }</th>
						<th align="center">${re.vipFee }</th>
						<th align="center">${re.rechargeFee }</th>
						
						<th align="center">${re.overdueRepayedCount }</th>
						<th align="center">${re.overdueRepayedAmount }</th>
						<th align="center">${re.newIncreasedCount }</th>
						<th align="center">${re.newIncreasedAmount }</th>
						<th align="center">${re.rechargeOnline }</th>
						<th align="center">${re.rechargeOffline }</th>
						<th align="center">${re.cash }</th>
						<th align="center">${re.getIn }</th>
						<th align="center">${re.interest }</th>

						<th align="center">${re.tenderReward }</th>
						<th align="center">${re.rechargeOfflineReward }</th>
						<th align="center">${re.continueReward }</th>
						<th align="center">${re.recommendReward }</th>
						<th align="center">${re.registerReward }</th>
						
						<th align="center">${re.publishCount }</th>
						<th align="center">${re.publishAmount }</th>
						<th align="center">${re.repayedCount }</th>
						<th align="center">${re.repayedAmount }</th>
						<th align="center">${re.tenderCount }</th>
						<th align="center">${re.tenderAmount }</th>
						<th align="center">${re.effeTenderCount }</th>
						<th align="center">${re.effeTenderAmount }</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp" %>
	</div>
</form>