<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="accountDeduct/${PRE_PATH_VIEW }getAccountDeductList?right_id=${right_id}"
	method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名： <input type="text" name="userAccount"
						value="${searchParams.userAccount }" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查 询</button>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'check')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?id={accountDeductId}&right_id=${right_id}"
							target="dialog" rel="jbsxBoxAccountDeduct" width="800"
							height="480"><span>${rightsub.moduleName}</span> </a></li>
						<li class="line">line</li>
					</c:if>

					<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountDeduct/o_toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&userAccount=${searchParams.userAccount }"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					
				</c:forEach>
			</ul>
		</div>
		<table class="list" width="100%" layoutH="90" fetchSize="200">
			<thead>
				<tr>
					<th width="5%" align="center">序&nbsp;&nbsp;号</th>
					<th width="10%" align="center">用户名</th>
					<th width="15%" align="center">扣费类型</th>
					<th width="10%" align="center">扣费金额</th>
					<th width="10%" align="center">备注</th>
					<th width="10%" align="center">创建时间</th>
					<!-- <th width="10%" align="center">创建Ip地址</th> -->

					<th width="10%" align="center">创建人</th>
					<th width="10%" align="center">状&nbsp;&nbsp;态</th>
					<th width="10%" align="center">审核人</th>
					<th width="10%" align="center">审核时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="accountDeduct" items="${pm.list }">
					<tr target="accountDeductId" rel="${accountDeduct.id }">
						<th align="center">${accountDeduct.id }</th>
						<th align="center">${accountDeduct.userAccount }</th>
						<th align="center">
							${ACCOUNTDEDUCT_ALL_TYPE[accountDeduct.deductType] }</th>
						<th align="center">${accountDeduct.deductAmount }</th>
						<th align="center">${accountDeduct.remark }</th>

						<th align="center"><fmt:formatDate
								value="${ accountDeduct.addTime }" pattern="yyyy-MM-dd HH:mm:ss" />
						</th>

						<th align="center">${accountDeduct.addUserAccount }</th>
						<th align="center"><c:if
								test="${'0'eq accountDeduct.checkState }">未审核</c:if> <c:if
								test="${'1'eq accountDeduct.checkState  }">已审核</c:if></th>
						<th align="center"><c:if
								test="${'0'eq accountDeduct.checkState  }">无</c:if> <c:if
								test="${'1'eq accountDeduct.checkState }">${accountDeduct.checkUserAccount  }</c:if>
						</th>
						<th align="center"><fmt:formatDate
								value="${accountDeduct.checkTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>

