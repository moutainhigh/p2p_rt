<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="msg/${PRE_PATH_VIEW }getMsgPage?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							标题:
							<input type="text" name="messageTitle" value="${searchParams.messageTitle }"/>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										查询
									</button>
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
						 <%-- <c:if test="${fn:contains(rightsub.moduleUrl,'msg/v_toInsertMsgCenter')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  
								target="dialog"  width="800" height="500"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if> --%>
						<%--<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toUpdateUser')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog"  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if> --%>
						
						 <c:if test="${fn:contains(rightsub.moduleUrl,'msg/o_toDeleteMsgCenter')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={msgId}&right_id=${right_id}" 
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该消息吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
				
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th  align="center">序号</th>
						<th  align="center">标题</th>
						<th  align="center">内容</th>
						<th align="center">发送人</th>
						<th align="center">接收人</th>
						<th align="center">消息状态</th>
						<th align="center">发送时间</th>
						<th align="center">发送IP</th>
						<th align="center">消息类型</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="msg" items="${page.list }" >
						<tr target="msgId" rel="${msg.id }">
							<td  align="center">
							    ${msg.id }
							</td>
							<td align="center">${msg.messageTitle }</td>
							<td width="20%" align="center">${msg.messageContent }</td>
							<td align="center">${msg.sendUser.userAccount }</td>
							<td align="center">${msg.receiveUser.userAccount }</td>
							<td align="center">${msg.viewMsgStatus }</td>
							<td align="center"><fmt:formatDate value="${msg.messageSendDatetime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td align="center">${msg.messageSendIp }</td>
							<td align="center">${msg.viewNoticeType }</td>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${page }"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
		<script type="text/javascript">
			function deleteUser(supportID){
				alert(supportID.val());
			}
		</script>
</form>