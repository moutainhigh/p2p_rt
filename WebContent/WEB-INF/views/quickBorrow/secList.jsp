<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="quickBorrow/${PRE_PATH_VIEW }getList?right_id=${right_id}&audit=2" method="post">
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
							<c:choose>
								<c:when test="${searchParams.status eq 1 || searchParams.status eq 3  }">
									<option value="1">待初审</option>
								<!-- 	<option value="2">初审通过</option> -->
									<option value="3">初审未通过</option>
								</c:when>
								<c:when test="${searchParams.status eq 2 || searchParams.status eq 5 || searchParams.status eq 4}">
									<option value="2">待复审</option>
									<option value="4">复审通过</option>
									<option value="5">复审未通过</option>
								</c:when>
								<c:otherwise>
									<option value="">全部</option>
								<c:forEach var="item" items="${QUICKBORROW_ALL_STATUS}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
								</c:otherwise>
							</c:choose>
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
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&audit=2"
									target="dialog"  width="470" height="550" rel="jbsxBox"><span>${rightsub.moduleName}</span>
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
						<th width="6%" align="center">贷款人</th>
						<th width="7%" align="center">手机号</th>
						<th width="7%" align="center">地区</th>
						<th width="6%" align="center">借款金额（万元）</th>
						<th width="7%" align="center">借款期限</th>
						<th width="7%" align="center">用途</th>
						<th width="6%" align="center">借款方式</th>
						<th width="6%" align="center">公司主营行业</th>
						<th width="6%" align="center">公司月净利润</th>
						<th width="16%" align="center">申请时间</th>
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
							<th  align="center">
							    ${row.borrowWay}
							</th>
							<c:choose>
							<c:when test="${ empty row.mainUse }">
							<th  align="center">
							    --
							</th>
							</c:when>
							<c:otherwise>
							<th  align="center">
							    ${row.mainUse }
							</th>
							</c:otherwise>
							</c:choose>
							<c:choose>
							<c:when test="${ empty row.interestMonth }">
							<th  align="center">
							    --
							</th>
							</c:when>
							<c:otherwise>
							<th  align="center">
							    ${row.interestMonth }
							</th>
							</c:otherwise>
							</c:choose>
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