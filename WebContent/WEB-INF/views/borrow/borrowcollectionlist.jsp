<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowCollection/v_getBorrowCollectionList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							借款人：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							催收方式：
							<select name="collectionType" >
								<option value="">全部</option>
								<c:forEach var="type" items="${BORROW_BC_STATUS}">   
									<option value="${type.key}">${type.value}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="collectionType" value="${collectionType }" >
						</td>					
							<td>
							催收时间：
							<input type="text" size="10" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" size="10" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
			</div>
		</div>
		<script type="text/javascript">
				var collectionType = "${searchParams.collectionType}";
				if(collectionType){
					$("select[name='collectionType']").val(collectionType);
				}
				
				</script>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrowCollection/v_toRepayExcel')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}"><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
					</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="7%" align="center">借款标题</th>
						<th width="7%" align="center">借款人</th>
						<th width="9%" align="center">催收方式</th>
						<th width="9%" align="center">催收人</th>
						<th width="9%" align="center">催收时间</th>
						<th width="9%" align="center">催收概要</th>
						<th width="9%" align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="repaymentBorrow" items="${pm.list }" varStatus="status">
						<tr  target="ids" rel="${repaymentBorrow.borrowId}">
							<th  align="center">
							    ${status.count}
							</th>
							<th  align="center">
							    <a href="borrow/v_getBorrowDetailById?supportID=${repaymentBorrow.borrowId}&right_id=${right_id}"  target="dialog"  width="850" height="600" >${repaymentBorrow.borrowTitle}</a>
							</th>
							<th  align="center">
							    <a href="user/v_getUserDetail?supportID=${repaymentBorrow.userId}&right_id=${right_id}"  target="dialog"  width="850" height="600" > ${repaymentBorrow.userAccount}</a>
							</th>
							<th  align="center">
								 ${BORROW_BC_STATUS[repaymentBorrow.collectionType] }
							</th>
							<th  align="center">
								${repaymentBorrow.collectionUser}
							</th>
							<th  align="center">
								<fmt:formatDate value="${repaymentBorrow.collectionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${repaymentBorrow.collectionResult}
							</th>
							<th  align="center">
							   ${repaymentBorrow.collectionRemark}
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