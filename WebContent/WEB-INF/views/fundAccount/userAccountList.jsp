<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="userAccount/${PRE_PATH_VIEW }getUserAccountList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									 <table class="searchContent">
										<tr>
											<td>
												用户名：
												<input type="text" name="userAccount" value="${searchParams.userAccount }"/>
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
									  	<c:if test="${fn:contains(rightsub.moduleUrl,'userAccount/v_toExcel')}">
											<li>
												<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&userAccount=${searchParams.userAccount }" ><span>${rightsub.moduleName}</span></a>
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
											 <th width="10%" align="center">用户名</th>
											 <th width="10%" align="center">真实姓名</th>
											  <th width="10%" align="center">总余额</th>
											 <th width="10%" align="center">可用余额</th>
											 <th width="10%" align="center">冻结金额</th> 
											 <th width="10%" align="center">待收本金</th>
											 <th width="10%" align="center">待收利息</th>									
											 <th width="10%" align="center">待还金额</th>
											 <th width="10%" align="center">净资产</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="userAccount" items="${pm.list }">
											<tr target="userAccountId" rel="${userAccount.id }">
												<th  align="center">
												    ${userAccount.id }
												</th>
												<th  align="center">
												    ${userAccount.userAccount }
												</th>
												<th  align="center">
												   ${userAccount.userRealname}
												</th>
												<th  align="center">
												    ${userAccount.allMoney }
												</th>
												<th  align="center">
												    ${userAccount.availableMoney }
												</th>
												<th  align="center">
												    ${userAccount.unavailableMoney }
												</th>
												<th  align="center">
												    ${userAccount.waitRepossessedCapital }
												</th>
												<th  align="center">
												   ${userAccount.waitRepossessedInterest }
												</th>
												<th  align="center">
												    ${userAccount.waitRepayCapital }
												</th>
												<th  align="center">
												    ${userAccount.allMoney - userAccount.waitRepayCapital }
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

