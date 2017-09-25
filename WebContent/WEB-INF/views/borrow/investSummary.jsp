<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getInvestSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						投资人姓名：
						<input type="text" class="textInput" name="user_realname" id="user_realname" value="${params.user_realname }">
					</td>
					<td>
						投资人账户：
						<input type="text" class="textInput" name="user_accounts" id="user_accounts" value="${params.user_accounts }">
					</td>
					<td>投资时间: 
						<input type="text" name="tender_addtime_begin" id="tender_addtime_begin" value="${params.tender_addtime_begin }" class="date textInput readonly" datefmt="yyyy-MM-dd"   maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="tender_addtime_end" id="tender_addtime_end" value="${params.tender_addtime_end }" class="date textInput readonly" datefmt="yyyy-MM-dd"  maxDate="{%y}-%M-{%d}" size="10"/>
					</td>
					<td>
						本金范围：
						<input type="text" class="textInput" name="tender_amount_begin" id="tender_amount_begin" value="${params.tender_amount_begin }" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;<input type="text" class="textInput" name="tender_amount_end" id="tender_amount_end" value="${params.tender_amount_end }" size="10"/>
					</td>
				</tr>
				<tr>
					<td>
						借款标题：
						<input type="text" class="textInput" name="borrow_title" id="borrow_title" value="${params.borrow_title }"/>					
					</td>
					<td>
						借款人账号：
						<input type="text" class="textInput" name="user_account" id="user_account" value="${params.user_account }"/>					
					</td>
					<td>
						投资人手机号：
						<input type="text" class="textInput" name="user_phone" id="user_account" value="${params.user_phone }"/>					
					</td>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toInvestExcel')}">
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
		<table class="list" width="100%" layoutH="115">
			<thead>
				<tr>
					<th width="7%" align="center">投资人账号</th>
					<th width="7%" align="center">投资人姓名</th>
					<th width="6%" align="center">手机号</th>
					<th width="7%" align="center">投资总额</th>
					<th width="7%" align="center">利息总额</th>
					<th width="7%" align="center">待还本金</th>
					<th width="7%" align="center">待还利息</th>
					<th width="7%" align="center">投标方式</th>
					<th width="9%" align="center">投资时间</th>
					<th width="7%" align="center">投资状态</th>
					<th width="5%" align="center">借款人账号</th> 
					<th width="9%" align="center">借款标题</th>
					<th width="8%" align="center">借款金额</th>
					<th width="9%" align="center">预期年化收益率</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list }" var="re">
					<tr>
						<th align="center">${re.user_accounts }</th>
						<th align="center">${re.user_realname }</th>
						<th align="center">${re.user_phone }</th>
						<th align="center">${re.tender_amount }</th>
						<th align="center">${re.interest_amount }</th>
						<th align="center">${re.staying_amount }</th>
						<th align="center">${re.staying_interest }</th>
						<th align="center">${TENDER_ALL_TYPE[re.tender_type] }</th>
						<th align="center">
							<fmt:formatDate value="${re.tender_addtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</th>
						<th align="center">${TENDER_ALL_STATUS[re.tender_status] }</th>
						<th align="center">${re.user_account }</th>
						<th align="center">
							<a href="borrow/v_getBorrowDetailById?supportID=${re.borrow_id}&right_id=${right_id}"  target="dialog" width="850" height="600">${re.borrow_title }</a>
						</th>
						<th align="center">${re.borrow_sum }</th>
						<th align="center">${re.annual_interest_rate }%</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 分页 -->
		<c:set var="page" value="${list }"></c:set>
		<%@ include file="../page.jsp" %>
	</div>
</form>