<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="products/${PRE_PATH_VIEW }getProductsList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							理财产品类型：
							<select name="productsType" id="productsType">
								<option value="">全部</option>
								<c:forEach var="productsType" items="${productsTypes}">
									<option value="${productsType.id}">${productsType.dicName}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							理财产品状态：
							<select name="productsStatus" id="productsStatus">
								<option value="">全部</option>
								<option value="1">可配置</option>
								<option value="2">已成立</option>
								<option value="3">已兑付</option>
							</select>
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
				<script type="text/javascript">
				var queryType = "${searchParams.productsType}";
				if(queryType){
					$("select[name='productsType']").val(queryType);
				}
				var queryStatus = "${searchParams.productsStatus}";
				if(queryStatus){
					$("select[name='productsStatus']").val(queryStatus);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'products/v_toAddProduct')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog" width="900" height="550"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'products/v_getProductById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" width="900" height="550" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'quickInvestmentApplications/v_getQuickInvestmentList')}">
							<li>
								<a class="icon" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"  target="navTab" width="900" height="550"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<!--<c:if test="${fn:contains(rightsub.moduleUrl,'sys/o_deleteNotice')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该提醒吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					--></c:forEach>
				</ul>
				<!--  
				<ul class="toolBar">
						<li>
						<a class="add" href="sys/${PRE_PATH_VIEW }forAddNotice?rel=${rel }" rel="addNotice" target="dialog"  width="600" height="560"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a  class="edit" href="sys/${PRE_PATH_VIEW }getNoticeById/{noticeId}?rel=${rel }" rel="editNotice"
									target="dialog"  width="600" height="560"><span>修改</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="sys/${PRE_PATH_EDIT }deleteNotice/{noticeId}"
									target="ajaxTodo" title="确认要删除该提醒吗？"><span>删除</span>
								</a>
							</li>
				</ul>-->
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="15%" align="center">产品标题</th>
						<th width="9%" align="center">添加用户</th>
						<th width="7%" align="center">产品状态</th>
						<th width="7%" align="center">产品类别</th>
						<th width="7%" align="center">募集规模</th>
						<th width="7%" align="center">产品期限</th>
						<th width="9%" align="center">年化收益</th>
						<th width="9%" align="center">最低投资额</th>
						<th width="10%" align="center">添加时间</th>
						<th width="10%" align="center">发行时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${pm.list }">
						<tr  target="sid_support" rel="${product.id }">
							<th  align="center">
							    ${product.id}
							</th>
							<th  align="center">
							    ${product.productsTitle}
							</th>
							<th  align="center">
								${product.userAccount}
							</th>
							<th  align="center">
							   ${PRODUCTS_ALL_PRODUCTS_STATUS[product.productsStatus]}
							</th>
							<th  align="center">
								${product.dicName}
							</th>
							<th  align="center">
								<fmt:formatNumber value="${product.productsScale}" pattern="#.##"/>元
							</th>
							<th  align="center">
							    ${product.productsTimeLimit}个月
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${product.productsMinProfit}" pattern="#.##"/>%
							    -
							    <fmt:formatNumber value="${product.productsMaxProfit}" pattern="#.##"/>%
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${product.productsInvestBegin}" pattern="#.##"/>元
							</th>
							<th  align="center">
							    <fmt:formatDate value="${product.productsAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    <fmt:formatDate value="${product.productsPublishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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