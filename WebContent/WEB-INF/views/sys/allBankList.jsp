<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="sys/${PRE_PATH_VIEW }getAllBankList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												银行名称：
												<input type="text" name="bankName" value="${searchParams.bankName }"/>
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
													<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog" rel="jbsxBoxAllBank" width="800" ><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}?id={allBankId}&right_id=${right_id}"
														target="dialog" rel="jbsxBoxAllBank" width="800" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'sys/o_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?id={allBankId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBoxAllBank"  title="确认要删除银行类型？"><span>${rightsub.moduleName}</span>
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
											 <th width="10%" align="center">银行名称</th>
											 <th width="10%" align="center">银行编码</th>
											 <th width="10%" align="center">排&nbsp;&nbsp;序</th>
											 <th width="10%" align="center">状&nbsp;&nbsp;态</th> 
											 <th width="10%" align="center">修改时间</th> 
										</tr>
									</thead>
									<tbody>
										<c:forEach var="allBank" items="${pm.list }">
											<tr target="allBankId" rel="${allBank.id }">
												<th  align="center">
												    ${allBank.id }
												</th>
												<th  align="center">
												    ${allBank.bankName }
												</th>
												<th  align="center">
												    ${allBank.bankCode }
												</th>
												<th  align="center">
												  ${allBank.bankSequence}
												  <%--  <input value="${allBank.bankSequence}"  name="bankSequence"style="width: 20px;" onblur="blurSequence(${allBank.id},this)" /> --%>
												</th>
												<th  align="center">
												   <c:if test="${'0' eq allBank.bankStatus}">隐藏</c:if> <c:if test="${'1' eq allBank.bankStatus}">显示</c:if>
												</th>
												<th  align="center">
												   <fmt:formatDate value="${allBank.bankUpdatetime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</th>
												
											</tr>
											</c:forEach>
									</tbody>
								</table>
								<!-- 分页 -->
								<c:set var="page" value="${pm }"></c:set>
								<%-- <c:set var="pagedRel" value="jbsxBoxAllBank"></c:set> --%>
								<%@ include file="../page.jsp" %>
							</div>
					</form>