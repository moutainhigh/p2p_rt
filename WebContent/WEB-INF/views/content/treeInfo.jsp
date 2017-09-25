<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@taglib prefix="s" uri="/struts-tags" %> --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	function backSelected(){
		var selectedId = "";
		var selectedName="";
		$("div .checked").each(function(){
			var curLi = $(this).parent().parent("li");
			var id = curLi.attr("curId");
			if(id && !curLi.find("li").attr("curId")){
				
				selectedId+=$(this).parent().parent("li").attr("curId")+",";
				selectedName+=$(this).children("input").attr("text")+",";
			}
			
		  });
		selectedId = selectedId.substring(0, selectedId.length - 1);//除，
		selectedName=selectedName.substring(0, selectedName.length - 1);
		
		getSelectedChannel(selectedId,selectedName);//调用
		$("#closeBtn").click();//关闭
	}
</script>

<div class="pageContent">
	<form method="post" action="content/${PRE_PATH_EDIT}saveTree"
		onsubmit="return validateCallback(this, navTabAjaxDone);"
		class="pageForm required-validate">
		<div class="pageFormContent" layoutH="58">
			<ul class='tree treeFolder collapse treeCheck'>
			    <li>
			    	<a  href="javascript:" onclick="$.bringBack({id:'0', districtName:'根目录'})" >根目录</a>
			    	 ${requestScope.treeInfo }
			     </li>
		    </ul>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="backSelected()">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button id="closeBtn" class="close" type="button">关闭</button>
						</div>
					</div></li>
				

			</ul>
		</div>
	</form>
</div>






