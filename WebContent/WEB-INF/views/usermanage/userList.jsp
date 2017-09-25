<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="user/${PRE_PATH_VIEW }getUserPage?right_id=${right_id}" method="post">
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
							内部员工号:
							<input type="text" name="rtUserFlag" value="${searchParams.rtUserFlag }"/>
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
							开始时间:
							<input type="text" name="beginTime" class="date textInput readonly" datefmt="yyyy-MM-dd" value="${searchParams.beginTime }"  readonly="readonly"/>
						</td>
						<td>
							结束时间: 
							<input type="text" name="endTime" class="date textInput readonly" datefmt="yyyy-MM-dd"  value="${searchParams.endTime }" readonly="readonly" />
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toInsertUser')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  
								target="dialog"  width="800" height="600"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toUpdateUser')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="dialog"  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_toDeleteUser')}">
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_getUserDetail')}">
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_addBlackList')}">
							<li>
								<a class="icon"
									href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox" title="确定要将改用户加入黑名单吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_toExcel')}">
							<li>
								<a class="add"
									href="${rightsub.moduleUrl}"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
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
						<th align="center">民族</th>
						<th align="center">证件类型</th>
						<th  align="center">证件号</th>
						<th  align="center">银行卡</th>
						<th align="center">注册时间</th>
						<th align="center">内部员工号</th>
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
							<th  align="center">
								${user.userNation }
							</th>
							<th  align="center">
								${user.viewCard }
							</th>
							<th  align="center">
								${user.cardNumber }
							</th>
							<th  align="center">
								${user.bankAccount }
							</th>
							<th  align="center">
								<fmt:formatDate value="${user.userAddtime }" pattern="yyyy-MM-dd"/>
							</th>
							<th  align="center">
								${user.rtUserFlag }
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