<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:if test="${code==1}">
   <c:choose>
   <c:when test="${cashtype==1 }">
   <form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="accountCash/v_getH5AccountCashList?right_id=${right_id}"
		method="post">
   </c:when>
   <c:when test="${cashtype==2 }">
   <form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="accountCash/v_getAppAccountCashList?right_id=${right_id}"
		method="post">
   </c:when>
   <c:otherwise>
   <form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="accountCash/v_getAccountCashList?right_id=${right_id}"
		method="post">
   </c:otherwise>
   </c:choose>
	
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="cashStatus" value="${cashStatus }" >
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${userAccount}" />
						</td>
						<td>
							开始时间:
							<input type="text" name="startTime" class="date textInput readonly" datefmt="yyyy-MM-dd" value="${startTime }"  readonly="readonly"/>
						</td>
						<td>
							结束时间:
							<input type="text" name="endTime" class="date textInput readonly" datefmt="yyyy-MM-dd" value="${endTime }"  readonly="readonly"/>
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
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'accountCash/o_updateAccountCash')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp&cashStatus=${cashStatus}"
									target="dialog"  width="800" height="630"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'accountCash/v_shwoAccountLogList')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="navTab"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'accountCash/v_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&cashStatus=${cashStatus}&userAccount=${userAccount}&startTime=${startTime }&endTime=${endTime }" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="4%"  align="center">序号</th>
						<th width="9%" align="center">用户名称</th>
						<th width="4%" align="center">真实姓名</th>
						<th width="7%" align="center">手机号码</th>
						<th width="9%" align="center">订单号</th>
						<th width="9%" align="center">提现账号</th>
						<th width="6%" align="center">提现银行</th>
						<th width="9%" align="center">提现支行</th>
						<th width="9%" align="center">银行地址</th>
						<th width="7%" align="center">提现总额</th>
						<th width="9%" align="center">到账金额</th>
						<th width="5%" align="center">手续费</th>
						<th width="9%" align="center">申请时间</th>
						<th width="5%" align="center">提现状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pm.list }" var="accountCash" varStatus="st">
						<tr target="sid_support" rel="${accountCash.id}">
							<th  align="center">
							   ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							<th  align="center">
							   	${accountCash.userAccount }
							</th>
							<th  align="center">
							   	${accountCash.userRealname }
							</th>
							<th  align="center">
							   	${accountCash.userPhone }
							</th>
							<th  align="center">
							   	${accountCash.noOrder }
							</th>
							<th  align="center">
							   	${accountCash.cashBankAccount }
							</th>
							<th  align="center">
							   	${accountCash.cashBank }
							</th>
							<th  align="center">
							   	${accountCash.cashBranch }
							</th>
							<th  align="center">
							 ${fn:split(accountCash.bankCity,'|')[0]}|${fn:split(accountCash.bankCity,'|')[1]}
							 
							</th>
							<th  align="center">
							   	${accountCash.cashTotal }
							</th>
							<th  align="center">
							   	${accountCash.cashAccount }
							</th>
							<th  align="center">
							   	${accountCash.cashFee }
							</th>
							<th  align="center">
							   	<fmt:formatDate value="${accountCash.cashAddtime }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							<c:if test="${accountCash.cashStatus ==0}">
								待审
							</c:if>
							<c:if test="${accountCash.cashStatus ==4 ||accountCash.cashStatus ==5}">
								提现中
							</c:if>
							<c:if test="${accountCash.cashStatus ==1}">
								成功
							</c:if>
							<c:if test="${accountCash.cashStatus ==2}">
								失败
							</c:if>   
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%-- <c:set var="pagedRel" value="moduleBox"></c:set> --%>
			<%@ include file="../page.jsp" %>
		</div>
	</form>
</c:if>
<c:if test="${code==0}">
	${message}
</c:if>