<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%
		String path = request.getContextPath()+""; 
	%>
    <c:set var="east" value="<%=path %>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.pageFormContent ul {
			display:block;
	}
	
	.adminIcon{
		background: url(${east }/common/views/images/boy.gif) no-repeat;
		width: 16px;
		height: 16px;
		float: left;
	}
	
	.titleSpan{	
		color: #183152;
		height: 26px;
		float: left;
		text-align: center;
		line-height:20px; 
		overflow:hidden; 
	}
	
	.pageFormContent ul li{
		width:80%;
		margin-top:2px;
		height:26px;
		margin-left: 22px;
	}

</style>
</head>
<body >
<div class="pageContent">
	<c:if test="${code==1}">
			<div class="pageFormContent" layoutH="56">
				<!--   ${requestScope.outGroupHtml} -->
				<ul >
				<c:forEach items="${requestScope.userTreeModelList}" var="treeModel">
					<li><div class="adminIcon"></div><span class="titleSpan">${treeModel.title }</span></li>
				</c:forEach>
				</ul>
				
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
					</li>
				</ul>
			</div>
	</c:if>
	<c:if test="${code==0}">
		${message}
	</c:if>
</div>
</body>
</html>