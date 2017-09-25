<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="user/${PRE_PATH_VIEW }getUserBlackPage?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							真实姓名:
							<input type="text" name="userRealname"  value="${searchParams.userRealname }"/>
						</td>
						
						<td>
							手机:
							<input type="text" name="userPhone" value="${searchParams.userPhone }"/>
						</td>
						<td>
							性别:
							<select name="sex" id="sex" onchange="changeSex();">
								<option value="">不限</option>
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
							<input type="hidden" name="userSex" id="userSex" value="${searchParams.userSex }"/>
						</td>
					</tr>
					<tr>
					    <td>
							证件号码:
							<input type="text" name="cardNumber" value="${searchParams.cardNumber }"/>
						</td>
						<td>
							拉黑时间:
							<input type="text" name="beginTimeBlack" class="date textInput readonly" datefmt="yyyy-MM-dd" value="${searchParams.beginTimeBlack }"  readonly="readonly"/>
						</td>
						<td>到
							<input type="text" name="endTimeBlack" class="date textInput readonly" datefmt="yyyy-MM-dd"  value="${searchParams.endTimeBlack }" readonly="readonly" />
						</td> 
						<!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
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
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_updateBlackList')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}" 
									target="ajaxTodo" rel="jbsxBox"  title="确定将改用户移除黑名单吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/toExcelLevel')}">
							<li><a class="add" href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">line</li>
						</c:if>
					</c:forEach>
				</ul>
				
			</div>
			<table class="list" width="100%" layoutH="114">
				<thead>
					<tr>
						<th  align="center">序号</th>
						<th  align="center">用户类型</th>
						<th  align="center">用户名</th>
						<th align="center">真实姓名</th>
						<th align="center">性别</th>
						 <th align="center">邮箱</th>
						<th align="center">电话</th>
						<th align="center">手机</th>
						<!--<th align="center">民族</th> -->
						<th align="center">证件类型</th>
						<th  align="center">证件号</th>
						<th align="center">注册时间</th>
						<th align="center">拉黑时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.list }" >
						<tr target="userId" rel="${user.id }">
							<th  align="center">
							    ${user.id }
							</th>
							<th  align="center">
								${user.type.name }
							</th>
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
								${user.userEmail }
							</th>
							<th  align="center">
								${user.userTel }
							</th>
							<th  align="center">
								${user.userPhone }
							</th>
							<%-- <th  align="center">
								${user.userNation }
							</th> --%>
							<th  align="center">
								${user.viewCard }
							</th>
							<th  align="center">
								${user.cardNumber }
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.userAddtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.addBlackTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
		<script type="text/javascript">
			function changeSex(){
				var sex=$("#sex").val();
				if(sex==""){
					$("#userSex").val("");
					 $("select[name='sex']").val(sex);
				}
				if(sex=="男"){
					$("#userSex").val("男");
					 $("select[name='sex']").val(sex);
				}
				if(sex=="女"){
					$("#userSex").val("女");
					 $("select[name='sex']").val(sex);
				}
			}
			 $(document).ready(function(){
				 var sex = $("#userSex").val();
				 if(sex){
					 $("select[name='sex']").val(sex);
				 }
			}); 
		</script>
</form>