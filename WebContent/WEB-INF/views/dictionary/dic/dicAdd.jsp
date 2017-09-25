<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
String basePath = path+"/common/views";
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
	<form method="post" action="dic/dicSave"
		onsubmit="return validateCallback(this, dialogAjaxDone);"
		class="pageForm required-validate">
		<input type="hidden" name="right_id" value="${right_id}" />
		<div class="pageFormContent" layoutH="56">

			<p>
				<label>上级栏目：</label> <input name="district.id" type="hidden">
				<input class="required textInput readonly"
					name="district.districtName" type="text" readonly=""> <a
					class="btnLook" href="dic/rootTree" lookupgroup="district"
					width="250">栏目</a>
			</p>
			<div class="divider"></div>

			<p>
				<label>字典名称：</label> <input name="dicName" class="required"
					type="text" alt="请输入地区名称" size="20" />
			</p>
			<div class="divider"></div>

			<p>
				<label>字典代码：</label> <input name="dicCode" class="required"
					type="text" alt="请输入字典代码" size="20" onblur="checkCodeExist(this.value)" /><span id="msg" style="color:red;"></span>
			</p>
			<div class="divider"></div>
			
			<p>
				<label>链接：</label> <input name="dicUrl" 
					type="text" alt="请输入链接" size="20"  /><span id="msg" style="color:red;"></span>
			</p>
			<div class="divider"></div>

			<p>
				<label>排&nbsp;&nbsp;&nbsp;&nbsp;序：</label> <input
					name="dicSequence" class="digits required" type="text" alt="请输入排序"
					size="20" />
			</p>
			<div class="divider"></div>
			
			<p><label>是否启用:</label>
				<input name="dicIshidden"  checked="checked" type="radio" value="1" />启用&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="dicIshidden" value="0"  type="radio" />禁用</p>
			<div class="divider"></div>

			<p>
				<label>上传图片:</label>
						<input type="text" readonly="readonly" id="url" name="url" />&nbsp;&nbsp;
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
