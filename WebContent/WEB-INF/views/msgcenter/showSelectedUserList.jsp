<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return dwzSearch(this, 'dialog');"
		action="msg/${PRE_PATH_VIEW }getAllFrontUserNew" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="username" value="${param.username }"/>
						</td>
						<td>
						<div>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										查询
									</button>
								</div>
							</div>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="ensureBtn">
										确定
									</button>
								</div>
							</div>
							
							</div>
						</td>
					</tr>
					
				</table>
			</div>
		</div>
		<div class="pageContent" style="text-align: center">
			
			<table class="list" width="100%" layoutH="68" targetType="dialog">
				<thead>
					<tr>
						<th  align="center"><input type="checkbox" id="checkAll"/> </th>
						<th  align="center">用户帐号</th>
						<th  align="center">手机号</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.list }" >
						<tr>
							<th  align="center">
							    <input type="checkbox" name="checkName" uname="${user.userAccount }" value="${user.uid }"/>
							</th>
							<th  align="center">
								${user.userAccount }
							</th>
							<th  align="center">
							    ${user.phone }
							</th>
							
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<c:set var="targetTypeParam" value="dialog"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
		<script type="text/javascript">
		$(document).ready(function() {
			 $("#checkAll").bind("click",function (){
			      var checked=$(this).attr("checked");
			      if("checked" == checked){
			    	   $("input[name='checkName']").attr("checked",true);
			      }else{
			    	   $("input[name='checkName']").attr("checked",false);
			      }
		 });

		 
		 $("input[name='checkName']").each(function (i,o){
			  $(o).bind("click",function (){
				  var checkedAll=true; 
				  $("input[name='checkName']").each(function (v,m){
					  var checked=$(m).attr("checked");
					  if("checked" != checked){
						  checkedAll=false;
						  return false;
					  }
				  });
				  if(checkedAll){
					  $("#checkAll").attr("checked",true);
				  }else{
					  $("#checkAll").attr("checked",false);
				  }
			  });
			       
		 });
		 
		 $("#ensureBtn").bind("click",function(){
			 
				var selectedId = "";
				var selectedName = "";
				$("input[name='checkName']:checked").each(function() {
					selectedId += $(this).val() + ",";
					selectedName += $(this).attr("uname") + ",";
				});
		//		alert(selectedId+"-----"+selectedName);
				selectedId = selectedId.substring(0, selectedId.length - 1);//除，
				selectedName = selectedName.substring(0, selectedName.length - 1);
				getSelectedUser(selectedId, selectedName);//调用
				$.pdialog.closeCurrent();
		 
		 });
		});
		
		</script>
</form>