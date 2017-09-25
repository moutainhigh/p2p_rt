<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>待收人信息</title>
	</head>
	<body>
	
		<div class="pageHeader">
		<div class="panelBar">
			<ul class="toolBar">
		    	<c:if  test="${repaymentStatus!=2 }">
					<li>
						<a class="edit" onclick="sub()"><span>确定还款</span>
						</a>
					</li>
					<li>
						<a class="icon" onclick="closeform()"><span>取&nbsp;消</span>
						</a>
					</li>
				</c:if>
				<c:if test="${'2'eq repaymentStatus }">
				  <li>
				     <a class="icon"><span>收款详细列表</span></a>
				  </li>
				</c:if>
			</ul>
			</div>
	</div>
	<div class="pageContent" id="repossessedBox">
			
			<form id="subForm" 
			method="post" onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate"
			action="borrow/${PRE_PATH_EDIT}repay" >
				<input type="hidden" name="ids" id="ids" value="${ids }">
				<input type="hidden" name="right_id"  value="${right_id}" />
			</form>
			<form  id="pagerForm" method="post" action="borrow/${PRE_PATH_VIEW }getRepay"  
				onsubmit="return navTabSearch(this);" class="pageForm required-validate">
				<%-- <input type="hidden" name="right_id"  value="${right_id}" /> --%>
				<input type="hidden" name="ids" id="idss" value="${ids }">
				<div class="pageContent">
					<table class="list" width="100%" layoutH="65" fetchSize="200">
						<thead>
							<tr>
								<th width="10%"  align="center">序&nbsp;&nbsp;号</th>
								<th width="10%" align="center">收款人</th>
								<th width="10%" align="center">总额</th>
								<th width="10%" align="center">本金</th>
								<th width="10%" align="center">利息</th>
								<th width="10%" align="center">逾期利息</th> 
								<th width="10%" align="center">状态</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach var="repossess" items="${pm.list }">
								<tr target="" rel="">
									<th  align="center">
										${repossess.repayId }
									</th>
									<th  align="center">
									    ${repossess.userAccount }
									</th>
									<th  align="center">
								    	${repossess.repossessedCapital+repossess.repossessedInterest }
									</th>
									<th  align="center">
										${repossess.repossessedCapital }
									</th>
									<th  align="center">
										${repossess.repossessedInterest }
									</th> 
									<th  align="center">
										${repossess.lateInterest }
									</th>
									<th  align="center">
									    ${BORROW_ALL_REPOSSESSED_STATUS[repossess.repossessedStatus] }
									</th>
								</tr>
								</c:forEach>
						</tbody>
					</table>
					
					<!-- 分页 -->
					<c:set var="page" value="${pm }"></c:set>
				    <c:set var="pagedRel" value="repossessedBox"></c:set> 
					<%@ include file="../page.jsp" %>
				</div>
				
			</form>
		</div>
	</body>
	<script type="text/javascript">
	  function sub(){
		  var r=confirm("您确认还款吗？"); 
		   if (r==true) 
		    { 
			   $("#subForm").submit();
		     } 
	  }
	  function closeform(){
		navTab.closeCurrentTab();
	  }
	</script>
</html>
