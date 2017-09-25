<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/views";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加、修改标种设置</title>
</head>
<body>
  <div class="pageContent">
		<form method="post" action="sys/${PRE_PATH_EDIT }saveOrUpdateBorrowType"  
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${borrowType.id}" />
				<div class="pageFormContent" layoutH="55">
					<p><label>标种名称：</label><input type="text" size="22" name="name" value="${borrowType.name }" class="required"/></p>
					<div class="divider"></div>
					<p><label>标种编码：</label><input type="text" size="22" name="code" id="code" value="${borrowType.code }" class="required" onblur="uniqueCode()"/><span id="msg1" style="color:red;"></span></p>
					<div class="divider"></div>
					<p><label>前台发标：</label>
					   <input type="radio" name="frontPublish"  value="1" <c:if test="${'2'ne borrowType.frontPublish }">checked="checked" </c:if>/>允许
					   <input type="radio" name="frontPublish"  value="2" <c:if test="${'2'eq borrowType.frontPublish }">checked="checked" </c:if>/>不允许
					</p>
					<div class="divider"></div>
					<p><label>后台发标：</label>
					   <input type="radio" name="adminPublish"  value="1" <c:if test="${'2'ne borrowType.adminPublish }">checked="checked" </c:if>/>允许
					   <input type="radio" name="adminPublish"  value="2" <c:if test="${'2'eq borrowType.adminPublish }">checked="checked" </c:if>/>不允许
					</p>
					<div class="divider"></div>
					<p><label>是否手动：</label>
					   <input type="radio" name="repayType"  value="1" <c:if test="${'2'ne borrowType.repayType }">checked="checked" </c:if>/>人工还
					   <input type="radio" name="repayType"  value="2" <c:if test="${'2'eq borrowType.repayType }">checked="checked" </c:if>/>自动还
					</p>
					<div class="divider"></div>
					<p><label>处理逻辑service：</label>
					  <input type="text" name="dealService" value="${borrowType.dealService }" class="required"/>
					<div class="divider"></div>
					 <p><label>是否可用：</label>
					   <input type="radio" name="status"  value="1" <c:if test="${'2'ne borrowType.status }">checked="checked" </c:if>/>可用
					   <input type="radio" name="status"  value="2" <c:if test="${'2'eq borrowType.status }">checked="checked" </c:if>/>不可用
					 </p>
					<div class="divider"></div>
					<p style="height: 120px;"><label>说明：</label>
					   <textarea name="remark"  cols="35" rows="5" >${borrowType.remark }</textarea>
					<div class="divider"></div>
					<p style="height: 250px; width: 950px;"><label>协议内容：</label>
					    <span style="color:red;">#agreementNo#：借款协议号 &nbsp; &nbsp;   #borrowUser#： 借款人 &nbsp; &nbsp; #DATE#：签订日期 &nbsp; &nbsp; #tenderList#：借款详情表</span>
						<textarea name="agreementContent"  cols="95" rows="15"  >${borrowType.agreementContent }</textarea> 
					<div class="divider"></div>
					<p>
	                    <input type="file" name="uploadify" id="uploadify" /> 
	                      <div class="divider"></div> 
					    <div id="fileQueue" style="width: 740px auto; border: 1px solid #99BBE8;">
					      <ul id="fileUL" style="list-style: none;">
						      <c:if test="${not empty borrowType.logPath}">
						        <li id='img_${borrowType.id }'> 
						          <img src='<%=path %>${borrowType.logPath}' style="width: 200px;height: 200px;"></img>
						          <input type="hidden" name="logPath" value="${borrowType.logPath}"/>&nbsp;&nbsp;&nbsp;
						          &nbsp;&nbsp;<a href="javaScript:delet('img_${borrowType.id }')" >删除</a>
						        </li>
						       </c:if>
					       </ul>
					    </div>
					</p>
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
        'buttonImg'      : '<%=basePath %>/uploadify/buttonImgPic.png',  
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
        	$("#fileQueue").append("<li  id='li_"+indx+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 200px;height: 200px;' ></img>"
        	+"<input type='hidden' name='logPath' type='text' value='"+retJson.filepath+"' ></input>"
        	+"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a>"
        	+"</li>");
        	
        }  
    }); 
    
});  


	function uniqueCode(){
		var url="<%=path %>${ADMIN_URL }/sys/checkBorrowTypeCode.do";
		$.ajax({
			  type: "post",
			  data: {"id":$("#id").val(),"code" : $("#code").val()},
			  url: url,
			  async:false,
			  success:function (ret){
				  if(ret == 'fail'){
					  $("#msg1").text( $("#code").val()+"编码已存在!");
					  $("#code").val(null);
					  return false;
				  }else{
				  }
			  }} );
	} 
	function delet(attachId){
    	$("#"+attachId).remove();
    }
</script>
</html>