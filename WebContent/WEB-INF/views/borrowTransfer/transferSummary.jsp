<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getTransferSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						卖出账号：
						<input type="text" class="textInput" name="borrow_user_account" id="borrow_user_account" value="${params.borrow_user_account }">
					</td>
					<td>
						买入账号：
						<input type="text" class="textInput" name="user_account" id="user_account" value="${params.user_account }"/>					
					</td>
					<td>
						申请时间: 
						<input type="text" name="create_time_begin" id="create_time_begin" value="${params.create_time_begin }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="create_time_end" id="create_time_end" value="${params.create_time_end }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
					</td>
					<td>
						购买时间: 
						<input type="text" name="end_time_begin" id="end_time_begin" value="${params.end_time_begin }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="end_time_end" id="end_time_end" value="${params.end_time_end }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
	
	<div class="pageContent">
		<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toTransferExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${params.right_id}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			
		<!-- 数据表 -->
		<table class="list" width="100%" layoutH="90">
			<thead>
				<tr>
					<th width="8%" align="center">借款标题</th>
					<th width="7%" align="center">剩余期限</th>
					<th width="8%" align="center">卖出账号</th>
					<th width="8%" align="center">卖出姓名</th>
					<th width="9%" align="center">申请时间</th>
					<th width="7%" align="center">待收本息</th>
					<th width="7%" align="center">债权价值</th>
					<th width="7%" align="center">成交金额</th>
					<th width="7%" align="center">交易费用</th>
					<th width="7%" align="center">装让盈亏</th> 
					<th width="8%" align="center">买入账号</th>
					<th width="8%" align="center">买入姓名</th>
					<th width="9%" align="center">购买时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list }" var="re">
					<tr>
						<th align="center">${re.borrow_title }</th>
						<th align="center">${re.surplus_count }</th>
						<th align="center">${re.borrow_user_account }</th>
						<th align="center">${re.borrow_user_realname }</th>
						<th align="center">
							<fmt:formatDate value="${re.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</th>
						<th align="center">${re.repossessed_interest }%</th>
						<th align="center">${re.worth }</th>
						<th align="center">${re.last_auction_money }</th>
						<th align="center">${re.transfer_fee }</th>
						<th align="center">${re.profit_loss }</th>
						<th align="center">${re.user_account }</th>
						<th align="center">${re.user_realname }</th>
						<th align="center">
							<fmt:formatDate value="${re.end_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 分页 -->
		<c:set var="page" value="${list }"></c:set>
		<%@ include file="../page.jsp" %>
	</div>
</form>