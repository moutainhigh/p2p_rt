<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="attestation/${PRE_PATH_VIEW }vipAttestation?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text"  name="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							真实姓名:
							<input type="text"  name="userRealname" value="${searchParams.userRealname }"/>
						</td>
						<td>
						状态
							<select name="vip" id="vip"  onchange="change();">
									<option value="">全部</option>
								<c:forEach var="name" items="${VIP_STATUS }">
									<option value="${name.key }">${name.value }</option>
								</c:forEach>
							</select>
							<input type="hidden" id="vipStatus" name="vipStatus" value="${searchParams.vipStatus }">
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
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						 <c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_toSaveVipAttestation')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog"  width="500" height="490" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if> 
						 <c:if test="${fn:contains(rightsub.moduleUrl,'vip/v_updateVipUser')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog"  width="410" height="395" rel="jbsxBox"><span>${rightsub.moduleName}</span>
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
						<th  align="center">用户名称</th>
						<th  align="center">真实姓名</th>
						<th align="center">客服名称</th>
						<th align="center">添加时间</th>
						<th align="center">开始时间</th>
						<th align="center">结束时间</th>
						<th align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.list }" >
						<tr target="userId" rel="${user.id }">
							<th  align="center">
								${user.user.userAccount }
							</th>
							<th  align="center">
							    ${user.user.userRealname }
							</th>
							<th  align="center">
								${user.userCustomer.userAccount }
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.vipAddDatetime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.vipStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.vipEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th>
								${user.vipView }
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../../page.jsp" %>
		</div>
		<script type="text/javascript">
		function change(){
			//获取下拉框中的值
			 var vip = $("#vip").val();
			 if(vip){
				 $("#vipStatus").val(vip);
				 $("select[name='vip']").val(vip);
			 }else if(vip==""||vip==null){
				 $("#vipStatus").val(vip);
				 $("select[name='vip']").val(vip);
			 }
		}
		$(document).ready(function(){
			//获取隐藏域中的值
			var vip = $("#vipStatus").val();
			 if(vip){
				 $("select[name='vip']").val(vip);
			 }else if(vip==""||vip==null){
				 $("select[name='vip']").val(vip);
			 }
		});
		</script>
</form>