<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="test/${PRE_PATH_VIEW }getList"
		method="post">
		<div class="pageHeader" >
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							名字:
							<input type="text" name="name" value="${searchParams.name }"/>
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
						<a class="add" href="#" target="navTab"><span>添加用户</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a class="edit" href="test/${PRE_PATH_VIEW }get/{usrId}?rel=${rel }" rel="testEditUser"
									target="dialog"><span>修改用户</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="test/${PRE_PATH_EDIT }delete/{usrId}"
									target="ajaxTodo" title="确认要删除该用户吗？"><span>删除用户</span>
								</a>
							</li>
				</ul>
				
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="25%"  align="center">序号</th>
						<th width="25%" align="center">名字</th>
						<th width="25%" align="center">性别</th>
						<th width="25%" align="center">年龄</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usr" items="${pm.list }">
						<tr target="usrId" rel="${usr.id }">
							<th  align="center">
							    ${usr.id }
							</th>
							<th  align="center">
							    ${usr.name }
							</th>
							<th  align="center">
							   ${usr.viewSex }
							</th>
							<th  align="center">
							    ${usr.age }
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