<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="attestation/${PRE_PATH_VIEW }realnameAttestation?right_id=${right_id}" method="post">
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
							<select name="realname" id="realname"  onchange="change();">
									<option value="">全部</option>
								<c:forEach var="name" items="${USER_REALNAME_STATUS }">
									<option value="${name.key }">${name.value }</option>
								</c:forEach>
							</select>
							<input type="hidden" id="realnameStatus" name="realnameStatus" value="${searchParams.realnameStatus }">
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
						 <c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_toSaveRealnameAttestation')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog" width="640" height="580" rel="jbsxBox"><span>${rightsub.moduleName}</span>
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
						<th align="center">性别</th>
						<th align="center">民族</th>
						<th align="center">生日</th>
						<th align="center">证件类型</th>
						<th align="center">证件号码</th>
						<th align="center">籍贯</th>
						<th align="center">证件图片</th>
						<th  align="center">状态</th>
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
								${user.userSex }
							</th>
							<th  align="center">
								${user.userNation }
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.userBirthday }" pattern="yyyy-MM-dd"/>
							</th>
							<th  align="center">
								${user.viewCard }
							</th>
							<th  align="center">
								${user.cardNumber }
							</th>
							<th  align="center">
								${user.userProvince }&nbsp;${user.userCity }&nbsp;${user.userArea }
							</th>
							<th  align="center">
							<c:if test="${not empty user.cardFrontImg }">
									<a href="<%=path %>${user.cardFrontImg}" target="_blank">正面查看</a>
								</c:if>
								<c:if test="${empty user.cardFrontImg }">
									[正面查看]
								</c:if>
					
							<c:if test="${not empty user.cardBackImg }">
									<a href="<%=path %>${user.cardBackImg}"  target="_blank">反面查看</a>
								</c:if>
								<c:if test="${empty user.cardBackImg }">
									[反面查看]
								</c:if> 
							</th>
							<th  align="center">
								${user.viewRealName }
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
			 var realname = $("#realname").val();
			 if(realname){
				 $("#realnameStatus").val(realname);
				 $("select[name='realname']").val(realname);
			 }else if(realname==""||realname==null){
				 $("#realnameStatus").val(realname);
				 $("select[name='realname']").val(realname);
			 }
		}
		$(document).ready(function(){
			//获取隐藏域中的值
			var realname = $("#realnameStatus").val();
			 if(realname){
				 $("select[name='realname']").val(realname);
			 }else if(realname==""||realname==null){
				 $("select[name='realname']").val(realname);
			 }
		});
		</script>
</form>