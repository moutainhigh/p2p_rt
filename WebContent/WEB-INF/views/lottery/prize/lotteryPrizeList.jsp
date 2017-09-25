<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="lotteryPrize/${PRE_PATH_VIEW }getLotteryPrizeList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												描述：
												<input type="text" name="disc" value="${searchParams.disc }"/>
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
													<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog" rel="jbsxBoxAllBank" width="800" height="430"><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}?id={lotteryPrizeId}&right_id=${right_id}"
														target="dialog" rel="jbsxBoxAllBank" width="800" height="430" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'lotteryPrize/v_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?id={lotteryPrizeId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBoxAllBank"  title="确认要删除？"><span>${rightsub.moduleName}</span>
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
											 <th width="10%" align="center">分类</th>
											 <th width="10%" align="center">名称</th>
											 <th width="10%" align="center">开始角度</th>
											 <th width="10%" align="center">结束角度</th>
											 <th width="10%" align="center">概率</th> 
											  <th width="10%" align="center">已中奖数量</th>  
											 <th width="10%" align="center">奖品总数</th> 
											 <th width="10%" align="center">状态</th> 
										</tr>
									</thead>
									<tbody>
										<c:forEach var="prizeList" items="${pm.list }">
											<tr target="lotteryPrizeId" rel="${prizeList.id }">
												<th  align="center">
												    ${prizeList.id }
												</th>
												<th  align="center">
												    ${LOTTERY_PRZ_ALL_type[prizeList.prizeType] }
												</th>
												<th  align="center">
												    ${prizeList.disc }
												</th>
												<th  align="center">
												    ${prizeList.pointStart }
												</th>
												<th  align="center">
												  ${prizeList.pointEnd}
												</th>
												<th  align="center">
												  ${prizeList.probability}
												</th>
												 <th  align="center">
												  ${prizeList.winAmount}
												</th> 
												<th  align="center">
												  ${prizeList.totalAmount}
												</th>
												
												<th  align="center">
												${LOTTERY_PRZ_ALL_STATS[prizeList.status]}
												   
												</th>
												
												
												
											</tr>
											</c:forEach>
									</tbody>
								</table>
								<!-- 分页 -->
								<c:set var="page" value="${pm }"></c:set>
								<%-- <c:set var="pagedRel" value="jbsxBoxAllBank"></c:set> --%>
								<%@ include file="../../page.jsp" %>
							</div>
					</form>