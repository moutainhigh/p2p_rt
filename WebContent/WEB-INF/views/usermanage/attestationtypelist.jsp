<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListUserCredit" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value=""/>
						</td>
						 <!-- <td>
							开始时间:
							<input type="text" name="startTime" class="date textInput readonly"  readonly="readonly"/>
						</td>
						<td>
							结束时间: 
							<input type="text" name="endTime" class="date textInput readonly"  readonly="readonly" />
						</td>  -->
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
							<li>
								<a class="edit" href="credit/${PRE_PATH_VIEW }getUserCredit/{usrId}?rel=${rel }" target="dialog" rel="editCredit" >
								<span>修改</span>
								</a>
							</li>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="138">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="7%" align="center">类型名称</th>
						<th width="7%" align="center">排序</th>
						<th width="7%" align="center">状态</th>
						<th width="7%" align="center">积分</th>
						<th width="7%" align="center">添加时间</th>
						<th width="7%" align="center">添加IP</th>
						<th width="7%" align="center">简介</th>
						<th width="7%" align="center">备注</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${pm.list }">
						<tr target="usrId" rel="${credit.id }">
							<th  align="center">
							    ${credit.id }
							</th>
							<th  align="center">
							    ${credit.attestationName }
							</th>
							<th  align="center">attestationSequence
							    <img src="<%=path %>/images/credit/${credit.rankIcon }"/>
							</th>
							<th  align="center">
							    ${credit.attestationStatus }
							</th>
							<th  align="center">
							   ${credit.attestationScore }
							</th>
							<th  align="center">
							    ${credit.attestationDatetime }
							</th>
							<th  align="center">
								${credit.attestationIp }
							</th>
							<th  align="center">
								${credit.attestationSummary }
							</th>
							<th  align="center">
							   	${credit.attestationRemark }
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