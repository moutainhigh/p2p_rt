<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="quickBorrow/${PRE_PATH_VIEW }getAllList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							贷款人：
							<input type="text" name="userName" id="userName" value="${searchParams.userName}"/>
						</td>
						<td>
						状态：
							<select name="status">
							<option value="">全部</option>
							<c:forEach var="item" items="${QUICKBORROW_ALL_STATUS}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
					</select>
					</td>
						<td>
							申请时间：
							<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly" maxDate="{%y}-%M-{%d}"/>
							&nbsp;&nbsp;到&nbsp;&nbsp;<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly" maxDate="{%y}-%M-{%d}"/>
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
				var queryTradeCode = "${searchParams.status}";
				if(queryTradeCode){
					$("select[name='status']").val(queryTradeCode);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
				<c:forEach items="${righSubtList}" var="rightsub">
						 <c:if test="${fn:contains(rightsub.moduleUrl,'quickBorrow/o_quickBorrow_audit')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=${searchParams.status}"
									target="dialog"  width="470" height="550" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if> 
						
						
						 <c:if test="${fn:contains(rightsub.moduleUrl,'quickBorrow/v_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}"><span>${rightsub.moduleName}</span>
								</a>
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
						<th width="3%"  align="center">序号</th>
						<th width="8%" align="center">贷款人状态</th>
						<th width="7%" align="center">真实姓名</th>
						<th width="7%" align="center">用户名</th>
						<th width="10%" align="center">手机号</th>
						<th width="15%" align="center">地区</th>
						<th width="12%" align="center">借款金额（万元）</th>
						<th width="7%" align="center">借款期限（月）</th>
						<th width="15%" align="center">用途</th>
						<!-- <th width="8%" align="center">利率</th> -->
						<th width="10%" align="center">申请时间</th>
						<th width="7%" align="center">当前状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="row" items="${pm.list }">
						<tr  target="sid_support" rel="${row.id }">
							<th  align="center">
							    ${row.id}
							</th>
							<c:choose>
							<c:when test="${ empty row.mainUse }">
							<th  align="center">
							    个人
							</th>
							</c:when>
							<c:otherwise>
							<th  align="center">
							    企业
							</th>
							</c:otherwise>
							</c:choose>
							<th  align="center">
							    ${row.realname}
							</th>
							<th  align="center">
							    ${row.userName}
							</th>
							<th  align="center">
							    ${row.userTel}
							</th>
							<th  align="center">
								${row.userProvince}&nbsp;${row.userCity}&nbsp;${row.userArea}
							</th>
							<th  align="center">
								<fmt:formatNumber minFractionDigits="0" maxFractionDigits="2" value="${row.borrowAmount * 0.0001}" pattern="0.00"/>
							</th>
							<th  align="center">
								 ${row.borrowPeriod}
							</th>
							<th  align="center">
							    ${row.borrowUse}
							</th>
							<%-- <th  align="center">
							    ${row.interestrateMin} ~ ${row.interestrateMax}
							</th> --%>
							<th  align="center">
							    <fmt:formatDate value="${row.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${QUICKBORROW_ALL_STATUS[row.status]}
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