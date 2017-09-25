<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="shop/${PRE_PATH_VIEW }getShopOrderList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								
								
								
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												商品名称：
												<input type="text" name="goodsName" id="goodsName" value="${searchParams.goodsName }"/>
											</td>
											<td>
												用户名：
												<input type="text" name="userName" id="userName" value="${searchParams.userName }" />
											</td>
											<td>
												订单号：
												<input type="text"  name="orderNo" id="orderNo" value="${searchParams.orderNo }" />
											</td>												
											<td>
												下单时间：
												<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime }"  class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"/>
											到	<input type="text" name="endTime"  id="endTime" value="${searchParams.endTime }" class="date textInput readonly " datefmt="yyyy-MM-dd" readonly="readonly" />
											</td>
										</tr>	
										<tr>
											<td>
												状态：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<select name="status" >
													<option value="">全部</option>
													<c:forEach var="item" items="${ALL_SHOP_ORDER_STATUS }" >
														<option value="${item.key}">${item.value } </option>
													</c:forEach>
												
												</select>
											</td>
											<td>
												电话：&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="text" name="recvTel" id="recvTel" value="${searchParams.recvTel}"  />
												
											</td>
											
											
											<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
											<%-- <c:choose>
												<c:when test="${fn:contains(rightsub.moduleName,'取消')}">
												<li>
												
													<a class="edit" href="${rightsub.moduleUrl}?id={shopOrderId}&right_id=${right_id}&type=2"
														target="dialog" rel="jbsxBoxAllBank" width="800" height="430" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
												</c:when>
												<c:otherwise>
												<li>
												
													<a class="edit" href="${rightsub.moduleUrl}?id={shopOrderId}&right_id=${right_id}&type=4"
														target="dialog" rel="jbsxBoxAllBank" width="800" height="430" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
												</c:otherwise>
											</c:choose>
											 --%>
											 <c:if test="${fn:contains(rightsub.moduleName,'取消')}">
											 	<li>
												
													<a class="edit" href="${rightsub.moduleUrl}?id={shopOrderId}&right_id=${right_id}&type=2"
														target="dialog" rel="jbsxBoxAllBank" width="800" height="430" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											 
											 </c:if>
											 
											  <c:if test="${fn:contains(rightsub.moduleName,'发货')}">
											 	<li>
												
													<a class="edit" href="${rightsub.moduleUrl}?id={shopOrderId}&right_id=${right_id}&type=4"
														target="dialog" rel="jbsxBoxAllBank" width="800" height="430" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											 
											 </c:if>
											 
											<c:if test="${fn:contains(rightsub.moduleUrl,'shop/toExcel')}">
													<li>
														<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}" ><span>${rightsub.moduleName}</span></a>
													</li>
													<li class="line">
														line
													</li>
						
											</c:if>
											<%-- <c:if test="${fn:contains(rightsub.moduleUrl,'shop/o_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?id={shopCategoryId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBoxAllBank"  title="确认要删除？"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if> --%>
										</c:forEach> 
									</ul>
								</div>
								<table class="list" width="100%" layoutH="115" fetchSize="200">
									<thead>
										<tr>
											 <th width="10%"  align="center">用户名</th>
											 <th width="10%" align="center">订单号</th>
											 <th width="10%" align="center">商品名称</th>
											 <th width="10%" align="center">订单商品数量</th>
											 <th width="10%" align="center">下单时间</th> 
											 <th width="10%" align="center">收件人地址</th> 
											 <th width="10%" align="center">收件人名称</th> 
											 <th width="10%" align="center">收件人电话</th> 
											 <th width="10%" align="center">状态</th> 
										</tr>
									</thead>
									<tbody>
										<c:forEach var="shopOrderList" items="${pm.list }">
											<tr target="shopOrderId" rel="${shopOrderList.id }">
												
												<th  align="center">
												   ${shopOrderList.userName }
												</th>
												<th  align="center">
												    ${shopOrderList.orderNo }
												</th>
												<th  align="center">
												    ${shopOrderList.goodsName }
												</th>
												
												<th  align="center">
												    ${shopOrderList.goodsCount }
												</th>
												<th  align="center">
												    <fmt:formatDate value="${shopOrderList.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />   
												</th>
												<th  align="center">
												    ${shopOrderList.recvAddress }
												</th>
												<th  align="center">
												    ${shopOrderList.recvUser }
												</th>
												<th  align="center">
												    ${shopOrderList.recvTel }
												</th>
												<th  align="center">
												    ${ALL_SHOP_ORDER_STATUS[shopOrderList.status]   }
												</th>
												
												
												
												
												
												
												<%-- <th  align="center">
												  ${shopList.orderBy}
												   <input value="${allBank.bankSequence}"  name="bankSequence"style="width: 20px;" onblur="blurSequence(${allBank.id},this)" />
												</th>
												<th  align="center">
												   <c:if test="${'0' eq shopList.isEnable}">隐藏</c:if> <c:if test="${'1' eq shopList.isEnable}">显示</c:if>
												</th>
												<th  align="center">
												   <fmt:formatDate value="${shopList.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</th>
												<th align="center">
													${shopList.remark }
												</th> --%>
												
												
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