<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@taglib prefix="s" uri="/struts-tags" %> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
</script>

<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<%-- 	<ul class="tree expand">
			<!-- <li><a href="javascript:" onclick="$.bringBack({id:'0', districtName:'根目录'})">根目录</a> 
				<ul>-->
				${treeInfo }
				   <c:forEach var="channel" items="${pm.list }">
							<li><a href="javascript:" onclick="$.bringBack({id:'${channel.id }', districtName:'${channel.channelTitle}'})">${channel.channelTitle}</a>
	  			   </c:forEach>
				<!-- </ul>
			</li> -->
		</ul> --%>
		<ul class='tree treeFolder collapse'>
		    <li>
		    	<a  href="javascript:" onclick="$.bringBack({id:'0', districtName:'根目录'})" >根目录</a>
		    	 ${requestScope.treeInfo }
		     </li>
	   </ul>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>

