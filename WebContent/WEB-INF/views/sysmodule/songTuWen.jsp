<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/views";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

</head>
	<body>
		<div class="pageContent">
		 <p><input id="appid" name="appid" type="hidden" size="32" name="sysLoanRate"  value="${APP}" readonly="readonly"/></p>
		 <p><input id="appser" name="appser" type="hidden" size="32" name="sysLoanPoundage"   value="${secret}" readonly="readonly"/></p>
			<!-- 分开 -->
			<form id="tenderFrm" name="tenderFrm" method="post" action=""   class="pageForm required-validate" >
				<div class="pageFormContent" layoutH="56">
					<p><label>标题:</label><input name="name" id="name"  minlength="2" maxlength="10" class="required" type="text" alt="请输入标题" size="30"  /></p>
					<div class="divider"></div>
					<p><label>作者:</label><input name="author" id="author" type="text" alt="请输入作者" size="30" class="required" /></p>
					<div class="divider"></div>
					<p><label>封面:</label><input id="uploadify" name="uploadify" type="file"  value="选择图片">
							<div style="float: left; margin-left: 130px;">
							<ul id="fileUL" style="list-style: none;">
						    </ul>
						    </div>
					</p>
					<div class="divider"></div>
					<p><label>描述</label><input type="text" id="miaosu" name="miaosu" size="30" class="required"></p>
					<div class="divider"></div>
					<p><label>原文链接</label><input type="text" id="url" name="url" size="30" class="required"></p>
					<div class="divider"></div>
					<p><label>正文:</label><textarea class="editor" id="moduleDescribe" name="moduleDescribe" class="required" rows="20" cols="60" tools="simple"></textarea></p>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="bn">
										群发
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
			
		</div>

	</body>
	<script type="text/javascript">
	var indx = 0;
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
	        'fileExt'        : '*.png;*.gif;*.jpg;*.jpeg',  
	        'fileDesc'       : '*.png,*.gif,*.jpg,*.jpeg',  
	        'onComplete'  :function(event,queueId,fileObj,response,data){
	        	indx++;
	        	var retJson = eval(response)[0];
	        	$("#fileUL").append("<li  id='li_"+indx+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 200px;height: 200px;' ></img>"
	        	+"<input type='hidden' id='borrowPicture' name='borrowPicture' type='text' value='"+retJson.filepath+"' ></input>"
	        	+"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a>"
	        	+"</li>");	
	        }  
	    }); 
	});
	
	function delet(attachId){
		$("#"+attachId).remove();
	}
	</script>
	
	<script type="text/javascript">
		$("#bn").click(function(){
			var name=$("#name").val();
			var author=$("#author").val();
			var miaosu=$("#miaosu").val();
			var url=$("#url").val();
			var moduleDescribe=$("#moduleDescribe").val();
			
			if (name=='') {
				alert('标题不能为空!');
				$(":text[id=name]").focus();  
				return;
			}
			if (author=='') {
				alert('作者不能为空!');
				$(":text[id=author]").focus();  
				return;
			}
			if (miaosu=='') {
				alert('描述不能为空!');
				$(":text[id=miaosu]").focus();  
				return;
			}
			if (url=='') {
				alert('网址不能为空!');
				$(":text[id=url]").focus();  
				return;
			}
			if(!url.match(/^((https?|http):\/\/)([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/)){
				alert('网址格式不正确！');
				$(":text[id=url]").focus();  
			return;
			}
			if (moduleDescribe=='') {
				alert('正文不能为空!');
				$(":text[id=moduleDescribe]").focus();  
				return;
			}
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : "module/o_songTuWens",
				data : $('#tenderFrm').serialize(),
				error : function(request) {
					alert(data.msg);
				},
				success : function(data) {
					if (data.code == '200') {
						alert(data.msg);
						window.location.reload();
					} else {
						alert("错误状态码："+data.code+"\n错误消息："+data.msg);
					}
				}
			});
		});
	
	
	
		function bn() {
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : "module/o_songTuWens",
				data : $('#tenderFrm').serialize(),
				error : function(request) {
					alert(data.msg);
				},
				success : function(data) {
					if (data.code == '200') {
						alert(data.msg);
						window.location.reload();
					} else {
						alert("错误状态码："+data.code+"\n错误消息："+data.msg);
					}
				}
			});
			
		}
	</script>
</html>