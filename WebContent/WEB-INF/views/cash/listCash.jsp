<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					 <c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'cashStage/v_toAddCashStage')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  
								target="dialog"  width="800" height="630"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'cashStage/v_toUpdateCashStage')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={cId}&right_id=${right_id}"
									target="dialog"  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<%-- <c:if test="${fn:contains(rightsub.moduleUrl,'user/o_toAdminDeleteUser')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}" 
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该用户吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if> --%>
					</c:forEach> 
				</ul>
				
			</div>
			<table class="list" width="100%" layoutH="66">
				<thead>
					<tr>
						<th align="center">id</th>
						<th align="center">起始金额</th>
						<th align="center">最大金额</th>
						<th align="center">手续费</th>
						<th align="center">添加时间</th>
						<th align="center">ip</th>
						<th align="center">操作人</th>
						<th align="center">修改时间</th>
						<th align="center">修改ip</th>
						<th align="center">修改人</th>
						<th align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					 <c:forEach var="c" items="${pm.list }" >
						<tr target="cId" rel="${c.id }">
							<th  align="center">
							    ${c.id }
							</th>
							<th  align="center">
								${c.minMoney }
							</th>
							<th  align="center">
								${c.maxMoney }
							</th>
							<th  align="center">
								${c.cashFee }
							</th>
							<th  align="center">
								<fmt:formatDate value="${c.addTime }" pattern="yyyy-MM-dd"/>
							</th>
							<th  align="center">
								${c.addUsers.userAccount }
							</th>
							<th  align="center">
								${c.addIp }
							</th>
							<th  align="center">
								<fmt:formatDate value="${c.updateTime }" pattern="yyyy-MM-dd"/>
							</th>
							<th  align="center">
								${c.updateUsers.userAccount }
							</th>
							<th  align="center">
								${c.updateIp }
							</th>
							<th  align="center">
								${c.remark }
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