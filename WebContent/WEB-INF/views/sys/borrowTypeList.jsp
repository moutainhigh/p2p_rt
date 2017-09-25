<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="sys/${PRE_PATH_VIEW }getAllBorrowTypeList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												标种名称：
												<input type="text" name="name" value="${searchParams.name }"/>
											</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td>
												<div class="buttonActive">
													<div class="buttonContent">
														<button type="submit">
															查  询
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
											<c:if test="${fn:contains(rightsub.moduleUrl,'add')}">
												<li>
													<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="navTab" rel="jbsxBoxBorrowType" width="800" height="500" ><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}?id={borrowTypeId}&right_id=${right_id}"
														target="navTab" rel="jbsxBoxBorrowType" width="800" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'sys/o_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?id={borrowTypeId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBoxBorrowType"  title="确认要删除标种类型？"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
										</c:forEach> 
									</ul>
								</div>
								<table class="list" width="100%" layoutH="90" fetchSize="200">
									<thead>
										<tr>
											 <th width="10%"  align="center">序&nbsp;&nbsp;号</th>
											 <th width="10%" align="center">标种名称</th>
											 <th width="10%" align="center">标种编码</th>
											 <th width="10%" align="center">是否前台发标</th>
											 <th width="10%" align="center">是否后台发标</th> 
											 <th width="10%" align="center">是否可用</th> 
											  <th width="10%" align="center">处理逻辑service</th> 
										</tr>
									</thead>
									<tbody>
										<c:forEach var="borrowType" items="${pm.list }">
											<tr target="borrowTypeId" rel="${borrowType.id }">
												<th  align="center">
												    ${borrowType.id }
												</th>
												<th  align="center">
												    ${borrowType.name }
												</th>
												<th  align="center">
												    ${borrowType.code }
												</th>
												<th  align="center">
												  <c:if test="${'1' eq borrowType.frontPublish}">允许</c:if>
												  <c:if test="${'2' eq borrowType.frontPublish}">不允许</c:if>  
												</th>
												<th  align="center">
												  <c:if test="${'1' eq borrowType.adminPublish }">允许</c:if>
												  <c:if test="${'2' eq borrowType.adminPublish }">不允许</c:if>  
												</th>
												<th  align="center">
												 <c:if test="${'1' eq borrowType.status }">可用</c:if>
												 <c:if test="${'2' eq borrowType.status }">不可用</c:if>
												</th>
												<th  align="center">
												 ${borrowType.dealService }
												</th>
											</tr>
											</c:forEach>
									</tbody>
								</table>
								<!-- 分页 -->
								<c:set var="page" value="${pm }"></c:set>
								<%--  <c:set var="pagedRel" value="jbsxBoxBorrowType"></c:set>  --%>
								<%@ include file="../page.jsp" %>
							</div>
					</form>