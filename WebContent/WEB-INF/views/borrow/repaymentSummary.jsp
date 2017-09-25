<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getRepaymentSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						借款人账号：
						<input type="text" class="textInput" name="user_account" id="user_account" value="${params.user_account }">
					</td>
					<td>
						垫付人账号：
						<input type="text" class="textInput" name="users" id="users" value="${params.users }"/>					
					</td>
					<td>
						垫付时间: 
						<input type="text" name="repayment_paidtime_begin" id="repayment_paidtime_begin" value="${params.repayment_paidtime_begin }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="repayment_paidtime_end" id="repayment_paidtime_end" value="${params.repayment_paidtime_end }" class="date textInput readonly" datefmt="yyyy-mm-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}" size="10"/>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toRepaymentExcel')}">
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
					<th width="14%" align="center">借款人账号</th>
					<th width="14%" align="center">借款标题</th>
					<th width="14%" align="center">垫付人账号</th>
					<th width="14%" align="center">垫付金额</th>
					<th width="14%" align="center">期数</th>
					<th width="15%" align="center">预计垫付时间</th>
					<th width="15%" align="center">实际垫付时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list }" var="re">
					<tr>
						<th align="center">${re.user_account }</th>
						<th align="center">
							<a href="borrow/v_getBorrowDetailById?supportID=${re.borrow_id}&right_id=${right_id}"  target="dialog" width="850" height="600">${re.borrow_title }</a>
						</th>
						<th align="center">${re.users }</th>
						<th align="center">${re.repayment_realamount }</th>
						
						<th align="center">${re.curPer }/${re.tolPer }</th>
						<th align="center">
							<fmt:formatDate value="${re.repayment_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</th>
						<th align="center">
							<fmt:formatDate value="${re.repayment_paidtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
