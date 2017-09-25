<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="quickInvestmentApplications/${PRE_PATH_VIEW }getQuickInvestmentList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							状态：
							<select name="investVerifyStatus" id="investVerifyStatus">
								<option value="">全部</option>
								<option value="1">待审核</option>
								<option value="2">通过</option>
								<option value="3">未通过</option>
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
				var queryStatus = "${searchParams.investVerifyStatus}";
				if(queryStatus){
					$("select[name='investVerifyStatus']").val(queryStatus);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
		
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'quickInvestmentApplications/v_getQuickInvestmentById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" width="900" height="550" rel="jbsxBox"><span>${rightsub.moduleName}</span>
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
						<th width="5%"  align="center">序号</th>
						<th width="15%" align="center">理财产品</th>
						<th width="4%" align="center">姓名</th>
						<!-- <th width="3%" align="center">性别</th>
						<th width="7%" align="center">地区</th> -->
						<th width="4%" align="center">联系方式</th>
					<!-- 	<th width="5%" align="center">投资周期</th> -->
						<th width="9%" align="center">理财资金</th>
						<!-- <th width="6%" align="center">方便联络</th> -->
						<th width="3%" align="center">状态</th>
						<th width="9%" align="center">预约时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="quickinvestment" items="${pm.list }">
						<tr  target="sid_support" rel="${quickinvestment.id }">
							<th  align="center">
							    ${quickinvestment.id}
							</th>
							<th  align="center">
							    ${quickinvestment.productsTitle}
							</th>
							<th  align="center">
							    ${quickinvestment.investUserName}
							</th>
							<%-- <th  align="center">
								${quickinvestment.investUserSex}
							</th>
							<th  align="center">
							   ${quickinvestment.investUserArea}
							</th> --%>
							<th  align="center">
								${quickinvestment.investUserTel}
							</th>
							<%-- <th  align="center">
								${quickinvestment.investUserCycle}个月
							</th> --%>
							<th  align="center">
							    <fmt:formatNumber value="${quickinvestment.investMoney}" pattern="#.##"/>元
							</th>
							<%-- <th  align="center">
							    ${quickinvestment.investBeginTime}:00
							    -
							    ${quickinvestment.investEndTime}:00
							</th> --%>
							<th  align="center">
							    ${QUICKINVESTMENT_ALL_QUICKINVESTMENT_STATUS[quickinvestment.investVerifyStatus]}
							</th>
							<th  align="center">
							    <fmt:formatDate value="${quickinvestment.investAddDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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