<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="attestation/${PRE_PATH_VIEW }sceneAttestation?right_id=${right_id}" method="post">
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
							<select name="scene" id="scene"  onchange="change();">
									<option value="">全部</option>
								<c:forEach var="name" items="${USER_SCENE_STATUS }">
									<option value="${name.key }">${name.value }</option>
								</c:forEach>
							</select>
							<input type="hidden" id="sceneStatus" name="sceneStatus" value="${searchParams.sceneStatus }">
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
						 <c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_toSaveSceneAttestation')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog" width="480" height="400" rel="jbsxBox"><span>${rightsub.moduleName}</span>
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
						<th align="center">实名认证</th>
						<th align="center">手机认证</th>
						<th align="center">邮箱认证</th>
						<th align="center">视频认证</th>
						<th align="center">现场认证</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.list }" >
						<tr target="userId" rel="${user.id }">
							<th  align="center">
								${user.userAccount }
							</th>
							<th  align="center">
							    ${user.userRealname }
							</th>
							<th  align="center">
								${user.viewRealName }
							</th>
							<th  align="center">
								${user.viewPhone }
							</th>
							<th  align="center">
								${user.viewEmail }
							</th>
							<th  align="center">
								${user.viewVideo }
							</th>
							<th  align="center">
								${user.viewScene }
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
			 var scene = $("#scene").val();
			 if(scene){
				 $("#sceneStatus").val(scene);
				 $("select[name='scene']").val(scene);
			 }else if(scene==""||scene==null){
				 $("#sceneStatus").val(scene);
				 $("select[name='scene']").val(scene);
			 }
		}
		$(document).ready(function(){
			//获取隐藏域中的值
			var scene = $("#sceneStatus").val();
			 if(scene){
				 $("select[name='scene']").val(scene);
			 }else if(scene==""||scene==null){
				 $("select[name='scene']").val(scene);
			 }
		});
		</script>
</form>