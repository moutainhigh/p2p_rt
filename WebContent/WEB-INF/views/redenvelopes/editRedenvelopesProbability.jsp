<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
	function dodel(id){
		$("#"+id).remove();
	}
	var indx = 100;
	function doAdd(){
		var divId = "AddDiv_"+(indx++);
		$("#formDiv").append("<div id=\""+divId+"\">"+
				
			"<td><label>&nbsp;&nbsp;&nbsp;&nbsp;概率:</label><input name=\"probability\" class=\"required number textInput\" max=\"100\" type=\"text\" alt=\"请输入概率\" size=\"20\" />  "+
			
			"<td><label>&nbsp;&nbsp;&nbsp;&nbsp;倍率:</label><input name=\"multi\" class=\"required number textInput\" max=\"10\" type=\"text\" alt=\"请输入倍率\" size=\"20\" />"+
		
			"<td><label>&nbsp;&nbsp;&nbsp;&nbsp;最小金额:</label><input name=\"min\" class=\"number textInput\" type=\"text\" max=\"9999999999\" alt=\"请输入最小金额\" size=\"20\"  />"  +
			
			"<td><label>&nbsp;&nbsp;&nbsp;&nbsp;最大金额:</label><input name=\"max\" class=\"number textInput\" type=\"text\" max=\"9999999999\" alt=\"请输入最大金额\" size=\"20\"  />" +
			
			"&nbsp;&nbsp;<a href=\"javascript:;\"  onclick=\"dodel('"+divId+"');\">删除</a>"+
			"<div class=\"divider\"></div>"+
		"</div>");
	}
	

	
	
</script>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>红包概率倍率</title>
		<style type="text/css">
		span.error { display:block; overflow:hidden; width:auto; height:21px; 
		padding:0 3px; line-height:21px; background:#F00; color:#FFF;
		 position:absolute; left:410px;}
		</style>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="redenvelopes/${PRE_PATH_EDIT }save"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				
				<div class="panelBar">
				<ul class="toolBar">
						<li><a class="add"
							href="javascript:;"  onclick="doAdd();"><span>添加</span></a>
						</li>
						<li class="line">line</li>
					
			</ul>
				</div>
				
				
				<div id="formDiv" class="pageFormContent" layoutH="79">
				<c:forEach items="${multiList}" var="multis" varStatus="indx">
							<div id="oldDiv_${indx.index }">
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;概率:</label><input name="probability" class="required number textInput" max="100" type="text" alt="请输入概率" size="20"  value="${multis.probability }"/>  
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;倍率:</label><input name="multi" class="required number textInput" max="10" type="text" alt="请输入倍率" size="20"  value="${multis.multi }" />
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;最小金额:</label><input name="min" class="number textInput" type="text" max="9999999999" alt="请输入最小金额" size="20"  value="${multis.min}"/>  
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;最大金额:</label><input name="max" class="number textInput" type="text" max="9999999999" alt="请输入最大金额" size="20"  value="${multis.max}"/>
							&nbsp;&nbsp;<a href="javascript:;"  onclick="dodel('oldDiv_${indx.index }');">删除</a>
							<div class="divider"></div>
							
						</div>
				    </c:forEach>
					
					
					
					
				<c:if test="${empty   multiList}">
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;概率:</label><input name="probability" class="required" minlength="1" maxlength="4" type="text" alt="请输入概率" size="30"  value="" />
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;倍率:</label><input name="multi" class="required" minlength="1" maxlength="4" type="text" alt="请输入倍率" size="30"  value="" />
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;最小金额:</label><input name="min" class="number textInput" type="text" max="9999999999" alt="请输入最小金额" size="20"  value="${multis.min}"/>  
							
							<label>&nbsp;&nbsp;&nbsp;&nbsp;最大金额:</label><input name="max" class="number textInput" type="text" max="9999999999" alt="请输入最大金额" size="20"  value="${multis.max}"/>
						    <div class="divider"></div>
							
				</c:if>
				
				</div>
				
				
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							
						</li>
					</ul>
				</div>
			</form>
		</div>

	</body>

</html>
