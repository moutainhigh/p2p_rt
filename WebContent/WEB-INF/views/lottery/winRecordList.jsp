<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="lotteryManager/getRecordList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="usrName" id="usrName" value="${searchParams.usrName }"/>
						</td>
						
						<td>
							奖品：
							<select name="disc" >
								<option value="">全部</option>
								<c:forEach var="type" items="${Prizes}">   
								<option value="${type.id}">${type.disc}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							状态：
							<!-- 查询全部 -->
							
								<select name="status" >
									<option value="">全部</option>
									<c:forEach var="item" items="${ALL_LOTTERY_RECORD_STATUS}">   
									<option value="${item.key}">${item.value}</option>
									</c:forEach>
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
				var disc = "${searchParams.disc}";
				if(disc){
					$("select[name='disc']").val(disc);
				}
				var status="${searchParams.status}";
				if(status){
					$("select[name='status']").val(status);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
				<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'lotteryManager/updateToPrized')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}&ids={ids}"
									target="ajaxTodo" title="确定标记为已兑奖吗?" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							
						</c:if>
							<c:if test="${fn:contains(rightsub.moduleUrl,'lotteryManager/toExcel')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}" ><span>${rightsub.moduleName}</span></a>
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
						<th width="5%"  align="center">ID</th>
						<th width="7%" align="center">用户名</th>
						<th width="7%" align="center">真实姓名</th>
						<th width="9%" align="center">奖品</th>
						<th width="9%" align="center">中奖时间</th>
						<th width="9%" align="center">电话</th>
						<th width="9%" align="center">备注</th>
						<th width="9%" align="center">状态</th>
						
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${pm.list }" varStatus="status">
						<tr  target="ids" rel="${u.id}">
							<th  align="center">
							    ${u.id}
							</th>
							<th  align="center">
							    ${u.usrname}
							</th>
							<th  align="center">
							    ${u.realname}
							</th>
							<th  align="center">
							    ${u.disc}
							</th>
							
							<th  align="center">
								<fmt:formatDate value="${u.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${u.tel}
							</th>
							<th  align="center">
								${u.remark}
							</th>
							<th  align="center">
								${ALL_LOTTERY_RECORD_STATUS[u.stats]}
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