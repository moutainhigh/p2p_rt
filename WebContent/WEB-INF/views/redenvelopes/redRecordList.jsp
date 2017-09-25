<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="redenvelopes/${PRE_PATH_VIEW }redRecordList?right_id=${right_id}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					
						<td>
							红包类型：
							<select name="redType" >
								<option value="">全部</option>
								 <c:forEach var="item" items="${RED_ALL_TYPE}">
									<option value="${item.key}">${item.value}</option>
								</c:forEach> 
							</select>
						</td>
						<td>
							红包状态：
							<select name="redStatus" id="redStatus">
								<option value="">全部</option>
								<c:forEach var="item" items="${RED_ALL_STATUS}">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							用户名：
							<input type="text"  id="userAccount" name="userAccount" value="${searchParams.userAccount }">
						</td>
						<td>
							发放人：
							<input type="text"  id="sendUserId" name="sendUserAccount" value="${searchParams.sendUserAccount }">
						
						</td>
						
						
						
						
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
						<script type="text/javascript">
						$("select[name='redStatus']").val("${searchParams.redStatus }");
						$("select[name='redType']").val("${searchParams.redType }");
						</script>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="pageContent">
	
		<%-- <div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/answer/answers')}">
						<li>
							<a class="add" href="${rightsub.moduleUrl}?questionId={questionId}&right_id=${searchParams.right_id}" target="navTab">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/answer/edit')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?answerId={answerId}&right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="520" height="490">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/answer/delete')}">
						<li>
							<a class="delete" href="${rightsub.moduleUrl}?answerId={answerId}&right_id=${searchParams.right_id}" target="ajaxTodo" rel="jbsxBox-dic" title="确认要删除此问题？">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
					</c:forEach>
			</ul>
		</div> --%>
		<div class="panelBar">
			<ul class="toolBar">
				<c:forEach items="${righSubtList}" var="rightsub">
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&redType=${searchParams.redType}&redStatus=${searchParams.redStatus}&userAccount=${searchParams.userAccount}&sendUserAccount=${searchParams.sendUserAccount}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		
		
		
		<table class="list" width="100%" layoutH="90">
			<thead>
				<tr>
					<th width="2%" align="center">用户账户</th>
					<th width="2%" align="center">金额</th>
					<th width="3%" align="center">类型</th>
					<th width="9%" align="center">发放时间</th>
					<th width="9%" align="center">发放人 </th>
					<th width="2%" align="center">红包状态</th>
					<th width="3%" align="center">打开时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="a" items="${pm.list }">
					<tr target="answerId" rel="${a.userId }">
						<th align="center">${a.userAccount }</th>
						<th align="center">${a.amount }</th>
						<th align="center">${RED_ALL_TYPE[a.redType] }</th>
						<th align="center">${a.sendTime }</th>
						<th align="center">${a.sendUserAccount }</th>
						<th align="center">${RED_ALL_STATUS[a.status] }</th>
						<th align="center">
						<c:choose>
							<c:when test="${a.status == 3 }">
							${a.updateTime }
							</c:when>
							<c:otherwise>
							--
							</c:otherwise>
						</c:choose>
						
						
						</th>
						
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>