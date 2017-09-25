 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="pageContent" id="pageContent">
	<div class="tabs">	
		<div class="tabsContent">
			<div>
				<div layoutH="14" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		           <ul class='tree treeFolder collapse'>
				    <li>
				    	<a href="area/getSonList?parentId=0&right_id=${right_id}" id="areaRoot" target="ajax" rel="jbsxBox-area" >根目录</a>
				    	 ${requestScope.treeInfo }
				     </li>
				   </ul>
		           
				</div>
				
				<div id="jbsxBox-area" class="unitBox" style="margin-left:246px;">
					
				</div>
	
			</div>
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
</div>
<script type="text/javascript">
$(document).ready(function(){
	setTimeout(function(){$("#areaRoot").click();},10);
	 
});
</script>