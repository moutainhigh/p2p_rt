<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="user/${PRE_PATH_VIEW }getAdminUserPage?right_id=${right_id}" method="post">
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
							性别:
							<select name="sex" id="sex" onchange="changeSex();">
								<option value="">不限</option>
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
							<input type="hidden" name="userSex" id="userSex" value="${searchParams.userSex }"/>
						</td>
						<td>
							证件号:
							<input type="text" name="cardNumber" value="${searchParams.cardNumber }"/>
						</td>
						<td>
							手机:
							<input type="text" name="userPhone" value="${searchParams.userPhone }"/>
						</td>
						 <!-- <td>
							开始时间:
							<input type="text" name="startTime" class="date textInput readonly"  readonly="readonly"/>
						</td>
						<td>
							结束时间: 
							<input type="text" name="endTime" class="date textInput readonly"  readonly="readonly" />
						</td>  -->
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
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
				<!-- <ul class="toolBar">
						<li>
						<a class="add" href="user/${PRE_PATH_VIEW }getSaveUser?rel=${rel }" rel="addUser" target="dialog" width="800" height="580"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							 <li>
								<a class="edit" href="user/${PRE_PATH_VIEW }getSaveUserById/{userId}?rel=${rel }" rel="editUser" target="dialog"  width="800" height="580"><span>修改</span>
								</a>
							</li>
							<%-- <li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="credit/${PRE_PATH_EDIT }deleteCreditType/{usrId}"
									target="ajaxTodo" title="确认要删除该用户吗？"><span>删除</span>
								</a>
							</li> --%>
				</ul> -->
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toAdminInsertUser')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  
								target="dialog"  width="800" height="630"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toAdminUpdateUser')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog"  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_toAdminDeleteUser')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}" 
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该用户吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_getAdminUserDetail')}">
							<li>
								<a class="icon"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog" rel="jbsxBox" width="800" height="630"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_saveUserRole')}">
							<li>
								<a class="edit"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}&type=toJsp"
									target="dialog" rel="jbsxBox" width="280" height="470"><span>${rightsub.moduleName}</span>
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
						<th  align="center">用户名</th>
						<th align="center">真实姓名</th>
						<th align="center">性别</th>
						<th align="center">生日</th>
						<th align="center">邮箱</th>
						<th align="center">电话</th>
						<th align="center">手机</th>
						<th align="center">QQ</th>
						<th align="center">民族</th>
						<th align="center">证件类型</th>
						<th  align="center">证件号</th>
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
								<fmt:formatDate value="${user.userBirthday }" pattern="yyyy-MM-dd"/>
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
							<th  align="center">
								${user.userQq }
							</th>
							<th  align="center">
								${user.userNation }
							</th>
							<th  align="center">
								${user.viewCard }
							</th>
							<th  align="center">
								${user.cardNumber }
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
			function deleteUser(supportID){
				alert(supportID.val());
			}
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