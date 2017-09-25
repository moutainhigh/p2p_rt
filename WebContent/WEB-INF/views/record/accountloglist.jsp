<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="accountLog/${PRE_PATH_VIEW }getAccountLogList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="userId" value=${searchParams.userId }>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount}"/>
						</td>
						<td>
							类型：
							<select name="tradeCode">
								<option value="">全部</option>
								<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
				var queryTradeCode = "${searchParams.tradeCode}";
				if(queryTradeCode){
					$("select[name='tradeCode']").val(queryTradeCode);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
		
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountLog/logToExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&beginTime=${searchParams.beginTime}&&endTime=${searchParams.endTime}&&userAccount=${searchParams.userAccount}&&tradeCode=${searchParams.tradeCode}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					</c:forEach>
			</ul>
			</div>
			
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="7%" align="center">用户名称</th>
						<th width="7%" align="center">真实姓名</th>
						<th width="9%" align="center">类型</th>
						<th width="7%" align="center">总金额</th>
						<th width="7%" align="center">操作金额</th>
						<th width="7%" align="center">可用金额</th>
						<th width="7%" align="center">冻结金额</th>
						<th width="7%" align="center">收回金额</th>
						<th width="7%" align="center">交易对方</th>
						<th width="11%" align="center">添加时间</th>
						<th width="7%" align="center">添加IP</th>
						<th width="20%" align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${pm.list }">
						<tr  target="sid_support" rel="${log.id }">
							<th  align="center">
							    ${log.id}
							</th>
							<th  align="center">
							    ${log.userAccount}
							</th>
							<th  align="center">
							    ${log.userRealname}
							</th>
							<th  align="center">
								${LOG_ALL_TRADE_CODE[log.tradeCode]}
							</th>
							<th  align="center">
								<fmt:formatNumber value="${log.allMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
								<fmt:formatNumber value="${log.dealMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${log.availableMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${log.unavailableMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${log.repossessedMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
							    ${log.tradeCodeUser}
							</th>
							<th  align="center">
								<fmt:formatDate value="${log.logAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${log.logAddip}
							</th>
							<th  align="center">
							    ${log.logRemark}
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