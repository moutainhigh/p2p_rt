<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="userEvaluateApply/${PRE_PATH_VIEW }getUserEvaluateApplyList?right_id=${right_id}" method="post">
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
							状态：
							<select name="evaluateapplyStatus">
								<option value="">全部</option>
								<c:forEach var="item" items="${APPLY_ALL_APPLY_STATUS}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							发布时间：
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
				var queryStatus = "${searchParams.evaluateapplyStatus}";
				if(queryStatus){
					$("select[name='evaluateapplyStatus']").val(queryStatus);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'userEvaluateApply/v_getUserEvaluateApplyById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"  target="dialog"  width="780" height="550"><span>${rightsub.moduleName}</span></a>
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
						<th width="10%" align="center">用户名称</th>
						<th width="10%" align="center">申请类型</th>
						<th width="10%" align="center">原来额度</th>
						<th width="10%" align="center">申请额度</th>
						<th width="10%" align="center">新额度</th>
						<th width="12%" align="center">申请时间</th>
						<th width="12%" align="center">内容</th>
						<th width="9%" align="center">状态</th>
						<th width="15%" align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="apply" items="${pm.list }">
						<tr  target="sid_support" rel="${apply.id }">
						
							<th  align="center">
							    ${apply.id}
							</th>
							<th  align="center">
							    ${apply.userAccount}
							    <input type="hidden" name="userId" value="${apply.userId}"/>
							</th>
							<th  align="center">
							   <c:if test="${apply.evaluateapplyType==1}">
							   	借款信用额度
							   </c:if>
							</th>
							<th  align="center">
								${apply.amountBefore}
							</th>
							<th  align="center">
								${apply.amountApply}
							</th>
							<th  align="center">
							    ${apply.amount}
							</th>
							<th  align="center">
								<fmt:formatDate value="${apply.evaluateapplyAddTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${apply.evaluateapplyContent}
							</th>
							<th  align="center">
								${APPLY_ALL_APPLY_STATUS[apply.evaluateapplyStatus] }
							</th>
							<th  align="center">
								${apply.evaluateapplyRemark}
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