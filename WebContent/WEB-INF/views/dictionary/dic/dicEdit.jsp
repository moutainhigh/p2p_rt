<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
String basePath = path+"/common/views";
%>

<c:set var="path" value="<%=path%>"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
	<form method="post" action="dic/dicUpdate"
		onsubmit="return validateCallback(this, dialogAjaxDone);"
		class="pageForm required-validate">
		<input type="hidden" name="right_id" value="${right_id}" />
		<input type="hidden"  name="id" value="${dictionary.id }">
		<div class="pageFormContent" layoutH="56">

			<p>
				<label>上级栏目：</label> 
				<input name="district.id" type="hidden" value=${pid }>
				<input class="required textInput readonly"
					name="district.districtName" type="text" readonly=""   value="${pname }" > 
					<a class="btnLook" href="dic/rootTree" lookupgroup="district"
					width="250">栏目</a>
			</p>
			<div class="divider"></div>

			<p>
				<label>字典名称：</label> <input name="dicName" class="required"
					type="text" alt="" size="20"  value="${dictionary.dicName }"/>
			</p>
			<div class="divider"></div>

			<p>
				<label>字典代码：</label> <input name="dicCode" class="required"
					type="text" alt="" size="20" onblur="checkCodeExist(this.value)"  value="${dictionary.dicCode }"/>
					<span id="msg" style="color:red;"></span>
			</p>
			<div class="divider"></div>
			
			<p>
				<label>链接：</label> <input name="dicUrl" 
					type="text" alt="" size="20"   value="${dictionary.dicUrl }"/>
			</p>
			<div class="divider"></div>
			
			<p style="height: 100px">
				<label>图片：</label> 
				<%-- <input 	type="text" alt="" size="20"   value="${dictionary.dicAttach }" readonly/> --%>
				<img alt="" src="${path }${dictionary.dicAttach }" style="height: 100px;">
			</p>
			<div class="divider"></div>

			<p>
				<label>排&nbsp;&nbsp;&nbsp;&nbsp;序：</label> <input
					name="dicSequence" class="digits required" type="text" alt=""
					size="20"   value="${dictionary.dicSequence }"/>
			</p>
			<div class="divider"></div>
			
			<p><label>是否启用:</label>
				<c:if test="${dictionary.dicIshidden eq 0 }">
					<input name="dicIshidden"  type="radio" value="1" />启用&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="dicIshidden" type="radio"   value="0"  checked="checked" />禁用</p>
				</c:if>
				<c:if test="${dictionary.dicIshidden eq 1 }">
					<input name="dicIshidden"  type="radio" value="1"  checked="checked" />启用&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="dicIshidden" value="0"  type="radio" />禁用</p>
				</c:if>
			<div class="divider"></div>

			<p>
				<label>重新上传图片:</label>
						<input type="text" readonly="readonly" id="url" name="url"  />&nbsp;&nbsp;
						<input type="file" name="uploadify" id="uploadify" />
			</p>
			<div class="divider"></div>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	function checkCodeExist(code) {
		$.ajax({
			type : "post",
			url : "dic/checkCodeExist?dicCode=" + code,
			success : function(data) {
				if (data == "true") {
					$("#msg").text("编码已存在!");
					return false;
				}else{
					$("#msg").text(" ");
				}
			},
		});
	};
	
	var indx = 0;
	var imgSuffix = ".jpg,";
	$(document).ready(function() { 
	    $("#uploadify").uploadify({  
	        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
	        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
	        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
	       	'buttonImg'      : '<%=basePath %>/uploadify/buttonImg.png',
	        'folder'         : '/jxdBlog/photos',  
	        'queueID'        : 'fileQueue',  
	        'auto'           : true,  
	        'multi'          : true,  
	        'wmode'          : 'transparent',  
	        'simUploadLimit' : 999,  
	        'fileExt'        : '*',  
	        'fileDesc'       : '*',  
	        'onComplete'  :function(event,queueId,fileObj,response,data){  
	        	indx ++;
	        	var retJson = eval(response)[0];
	            
	            var img = "";
	            if(imgSuffix.indexOf(retJson.suffix) != -1){
	            	img = "<img style='width:100px;height:100px;' src=\"<%=path %>"+retJson.filepath+"\" />";
	            }
	            $("#url").val(retJson.filepath);
	        }  
	    }); 
	});  
</script>
