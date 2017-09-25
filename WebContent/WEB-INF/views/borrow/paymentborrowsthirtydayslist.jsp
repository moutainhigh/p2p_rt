<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowThirtyDays/${PRE_PATH_VIEW }getPaymentBorrowsList?right_id=${right_id}&days=30" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							借款标题：
							<input type="text" name="borrowTitle" id="borrowTitle" value="${searchParams.borrowTitle }"/>
						</td>
						<td>
							标种：
							<select name="bidKind" >
								<option value="">全部</option>
								<c:forEach var="type" items="${borrowTypes}">   
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							状态：<input type="hidden" name="repaymentStatusAll" value="${repaymentStatusAll }"/>
							<!-- 查询全部 -->
							<c:if test="${'0'eq repaymentStatusAll}">
								<select name="repaymentStatus" >
									<option value="">全部</option>
									<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}">   
									<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<!-- 查询非全部 -->
							<c:if test="${'0'ne repaymentStatusAll}">
							<select name="repaymentStatus" disabled="disabled">
								<option value="">全部</option>
								<c:forEach var="item" items="${BORROWREPLAYMENT_ALL_REPAYMENT_STATUS}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="repaymentStatus" value="${repaymentStatus }" > 
							</c:if>
						</td>
						
												
							</tr>
							<tr>
							
							<td>
							应还时间：
							<input type="text" size="10" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" size="10" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						</td>
						<td>
								实际还款时间：
								<input type="text" size="10" name="actualbeginTime" id="actualbeginTime" value="${searchParams.actualbeginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
								到<input type="text" size="10" name="actualendTime" id="actualendTime" value="${searchParams.actualendTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							</td>
						
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
				var queryStatus = "${searchParams.repaymentStatus}";
				if(queryStatus){
					$("select[name='repaymentStatus']").val(queryStatus);
				}
				var type="${searchParams.bidKind}";
				if(type){
					$("select[name='bidKind']").val(type);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
			<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_getRepay')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}&right_id=${right_id}&ids={ids}"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toRepayExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toNearlyRepayExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toBeyondRepayExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrowCollection/v_toUpdateBorrowCollection')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}}&ids={ids}" target="dialog" width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
					</ul>
			</div>
			<table class="list" width="100%" layoutH="115">
				<thead>
					<tr>
						
						<th width="5%"  align="center">序号</th>
						<th width="5%" align="center">借款人</th>
						<th width="5%" align="center">标种</th>
						<th width="5%" align="center">借款标题</th>	
						<th width="5%" align="center">借款金额（元）</th>
						<th width="5%" align="center">剩余期数</th>
						<th width="5%" align="center">本期应还金额（元）</th>
						<th width="5%" align="center">剩余应还本金（元）</th>
						<th width="5%" align="center">距离下次还款</th>
						<th width="5%" align="center">下一还款日</th>
				
					
					</tr>
				</thead>
				<tbody>
					<c:forEach var="repaymentBorrow" items="${pm.list }" varStatus="status">
						<tr  target="ids" rel="${repaymentBorrow.borrowId},${repaymentBorrow.repayId }">
							<th  align="center">
							    ${status.count}
							</th>
							<th  align="center">
							    <a href="user/v_getUserDetail?supportID=${repaymentBorrow.userId}&right_id=${right_id}"  target="dialog"  width="850" height="600" > ${repaymentBorrow.userAccount}</a>
							</th>
							<th  align="center">
							    ${repaymentBorrow.TypeName}
							</th>
							<th  align="center">
								<a href="borrow/v_getBorrowDetailById?supportID=${repaymentBorrow.borrowId}&right_id=${right_id}"  target="dialog"  width="850" height="600" >${repaymentBorrow.borrowTitle}</a>
							</th>							
							<th  align="center">
							    ${repaymentBorrow.borrowSum}
							</th>
							<th  align="center">
								${repaymentBorrow.surplusPeriod}
							</th>
							<th  align="center">
								${repaymentBorrow.repaymentAmount}
							</th>
							<th  align="center">
								${repaymentBorrow.surplusSum}
							</th>
							<th  align="center">
								<c:if test="${repaymentBorrow.surplusTime <0}">
								过期
								</c:if>
								<c:if test="${repaymentBorrow.surplusTime >=0}">
								${repaymentBorrow.surplusTime}
								</c:if>
							</th>
							<th  align="center">
								<fmt:formatDate value="${repaymentBorrow.repaymentTiime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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