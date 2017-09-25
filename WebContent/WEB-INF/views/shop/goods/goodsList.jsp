<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="shopGoods/${PRE_PATH_VIEW }getList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												名称：
												<input type="text" name="goodsName" value="${searchParams.goodsName }"/>
											</td>
											<td>
												编码：
												<input type="text" name="goodsCode" value="${searchParams.goodsCode }"/>
											</td>
											<td>
											分类：
												<select name="ctCode"  class="required">
													<option value="">所有分类</option>
														<c:forEach var="c" items="${shopCtg }">
														<option value="${c.categoryCode }">${c.categoryName }</option>
													</c:forEach>
												</select>
											</td>
											<td>
											状态：
												<select name="isEnable"  class="required">
													<option value="">所有状态</option>
														<c:forEach var="c" items="${SHOP_GOODS_ALL_isEnable }">
														<option value="${c.key }">${c.value }</option>
													</c:forEach>
												</select>
												
											</td>
											
										</tr>
										<tr>
										<td colspan="2">
												发布时间：
												<input type="text" name="pubTime_beg" id="pubTime_beg" value="${searchParams.pubTime_beg}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
												&nbsp;&nbsp;到&nbsp;&nbsp;<input type="text" name="pubTime_end" id="pubTime_end" value="${searchParams.pubTime_end}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
											</td>
											<td colspan="2">
												剩余数量：
												<input type="text" name="remainCount_beg" id="remainCount_beg" value="${searchParams.remainCount_beg}" style="width: 40px"/>
												&nbsp;&nbsp;到&nbsp;&nbsp;<input type="text" name="remainCount_end" id="remainCount_end" value="${searchParams.remainCount_end}" style="width: 40px"/>
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
									<script type="text/javascript">
									$("[name='ctCode']").val("${searchParams.ctCode }");
									$("[name='isEnable']").val("${searchParams.isEnable }");
									</script>
								</div>
							</div>
								
							<div class="pageContent">
								<div class="panelBar">
									<ul class="toolBar">
										 <c:forEach items="${righSubtList}" var="rightsub">
											<c:if test="${fn:contains(rightsub.moduleName,'添加')}">
												<li>
													<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="navTab" rel="editGoods" width="800" ><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleName,'修改')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}?id={r_id}&right_id=${right_id}"
														target="navTab" rel="editGoods" width="800" ><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}/{r_id}?right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBoxAllBank"  title="确认要删除商品？"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
										</c:forEach> 
										
										<c:if test="${searchParams.isEnable eq '0' }">
										<!-- <font color="red">
												
												包括不可用的分类也不会显示出来
												
												</font> -->
												</c:if>
									</ul>
								</div>
								<table class="list" width="100%" layoutH="115">
									<thead>
										<tr>
											 <th width="5%"  align="center">序号</th>
											 <th width="10%" align="center">分类</th>
											 <th width="10%" align="center">名称</th>
											 <th width="10%" align="center">编码</th>
											 <th width="10%" align="center">兑换积分</th>
											 <th width="15%" align="center">数量(已售/总量)</th> 
											 <th width="10%" align="center">排序</th> 
											 <th width="10%" align="center">状态</th> 
											 <th width="10%" align="center">发布时间</th> 
											 <th width="10%" align="center">修改时间</th> 
										</tr>
									</thead>
									<!-- 
									g.id id,
				c.category_name categoryName,
				g.goods_name gName,
				g.require_credit reqCredit,
				g.total_count totalCount,
				g.remain_count remainCount,
				g.order_by orderBy,
				g.is_enable isEnable,
				g.publish_time publishTime,
				g.create_time createTime
									 -->
									<tbody>
										<c:forEach var="r" items="${pm.list }">
											<tr target="r_id" rel="${r.id }">
												<th  align="center">
												    ${r.id }
												</th>
												<th  align="center">
												    ${r.categoryName }
												</th>
												<th  align="center">
												    ${r.gName }
												</th>
												<th  align="center">
												  ${r.gCode}
												  <%--  <input value="${allBank.bankSequence}"  name="bankSequence"style="width: 20px;" onblur="blurSequence(${allBank.id},this)" /> --%>
												</th>
												<th  align="center">
												    ${r.reqCredit }
												</th>
												<th  align="center">
												   ${r.sellCount }/${r.totalCount }
												</th>
												<th  align="center">
												   ${r.orderBy }
												</th>
												<th  align="center">
												   ${SHOP_GOODS_ALL_isEnable[r.isEnable] }
												</th>
												<th  align="center">
												   <fmt:formatDate value="${r.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</th>
												<th  align="center">
												   <fmt:formatDate value="${r.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
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