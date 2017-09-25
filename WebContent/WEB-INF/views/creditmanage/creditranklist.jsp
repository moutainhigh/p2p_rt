<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListCreditRank?right_id=${right_id}" method="post">
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_addCreditRank')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_getCreditRank')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/o_deleteCreditRank')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该积分等级吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<%-- <ul class="toolBar">
						<li>
						<a class="add" href="credit/${PRE_PATH_VIEW }get1CreditRank?rel=${rel }" rel="addCredit" target="dialog"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a class="edit" href="credit/${PRE_PATH_VIEW }getCreditRank/{usrId}?rel=${rel }" rel="editCredit"
									target="dialog"><span>修改</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="credit/${PRE_PATH_EDIT }deleteCreditRank/{usrId}"
									target="ajaxTodo" title="确认要删除该用户吗？"><span>删除</span>
								</a>
							</li>
				</ul> --%>
			</div>
			<table class="list" width="100%" layoutH="50">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="7%" align="center">等级名称</th>
						<th width="7%" align="center">等级</th>
						<th width="7%" align="center">开始分值</th>
						<th width="7%" align="center">最后分值</th>
						<th width="7%" align="center">图片</th>
						<th width="7%" align="center">添加时间</th>
						<th width="7%" align="center">添加IP</th>
						<th width="7%" align="center">备注</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${pm.list }">
						<tr target="sid_support" rel="${credit.id }">
							<th  align="center">
							    ${credit.id }
							</th>
							<th  align="center">
							    ${credit.rankName }
							</th>
							<th  align="center">
							    ${credit.rankValue }
							</th>
							<th  align="center">
							   ${credit.rankMincredit }
							</th>
							<th  align="center">
							    ${credit.rankMaxcredit }
							</th>
							<th  align="center">
								<img src="<%=path %>/common/views/images/credit/${credit.rankIcon }"/>
							</th>
							<th  align="center">
								<fmt:formatDate value="${credit.rankAddtime }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							   	${credit.rankAddip }
							</th>
							<th  align="center">
							    ${credit.rankRemarks }
							</th>
							
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
</form>