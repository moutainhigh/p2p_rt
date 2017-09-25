<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="dis/questions" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<input type="hidden" name="right_id" value="${right_id}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						提问人: <input type="text" name="userName" value="${searchParams.userName}" />
					</td>
					<td>
						提问标题: <input type="text" name="title" value="${searchParams.title}" />
					</td>
					<td> 
						起始时间:<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td> 
						截止时间:<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>
						是否显示:
						<select name="hidden" id="hidden">
							<option value="">全部</option>
							<option value="0" <c:if test="${hidden eq 0 }">selected</c:if> >显示</option>
							<option value="1" <c:if test="${hidden eq 1 }">selected</c:if> >隐藏</option>
						</select>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/question/answers')}">
						<li>
							<a class="add" href="${rightsub.moduleUrl}?questionId={questionId}&right_id=${searchParams.right_id}" target="navTab">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/question/edit')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?questionId={questionId}&right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="520" height="490">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/question/delete')}">
						<li>
							<a class="delete" href="${rightsub.moduleUrl}?questionId={questionId}&right_id=${searchParams.right_id}" target="ajaxTodo" rel="jbsxBox-dic" title="确认要删除此问题？">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/question/addAnswer')}">
						<li>
							<a class="add" href="${rightsub.moduleUrl}?questionId={questionId}&right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="520" height="590">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
					</c:forEach>
			</ul>
		</div>
		
		<table class="list" width="100%" layoutH="90">
			<thead>
				<tr>
					<th width="2%" align="center">ID</th>
					<th width="3%" align="center">提问人</th>
					<th width="5%" align="center">问题标题</th>
					<th width="7%" align="center">问题内容</th>
					<th width="2%" align="center">是否显示</th>
					<th width="1%" align="center">回答数量</th>
					<th width="3%" align="center">提问时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="q" items="${pm.list }">
					<tr target="questionId" rel="${q.id }">
						<th align="center">${q.id }</th>
						<th align="center">${q.userName }</th>
						<th align="center">${q.title }</th>
						<th align="center">${q.content }</th>
						<th align="center">
							<c:if test="${q.hidden eq 1 or empty q.hidden}">隐藏</c:if>
							<c:if test="${q.hidden eq 0}">显示</c:if>
						</th>
						<th align="center">${q.replyNum }</th>
						<th align="center">
							<fmt:formatDate value="${q.questionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
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