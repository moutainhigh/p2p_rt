<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
String basePath = path+"/common/views";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加积分类型</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="attestation/${PRE_PATH_EDIT }addUserAttestationApply" name="from1" id="from1"
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${attestationType.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>用户名称:</label><input name="userAccount" id="userAccount" class="required" type="text" alt="请输入用户名称" size="30" value="${attestationType.attestationName }" /></p>
					<div class="divider"></div>
					<p><label>证件类型:</label>
					<select name="attestationTypeid" onclick="chcekType()">
						<c:forEach items="${attestationList }" var="att">
							<option value="${att.id }">${att.attestationName }</option>
						</c:forEach>
					</select>
					</p>
					<div class="divider"></div>
					<%-- <p><label>排序:</label><input name="attestationSequence" class="required" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" alt="请输入序列" size="30" value="${attestationType.attestationSequence }" /></p>
					<div class="divider"></div> --%>
					<p><label>上传图片:</label>
						<input type="text" readonly="readonly" id="url" name="url" />
						<input type="file" name="uploadify" id="uploadify" />
					</p>
					<div class="divider"></div>
					<p style="height:80px;"><label>内容简介:</label>
						<textarea rows="4" cols="35" style="max-height: 80px; max-width: 250px;" name="attestationRemarks">${attestationType.attestationSummary }</textarea>
					</p>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="testNum1()">
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
<script>
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
            /* $('#result').html('还剩'+data.fileCount +'个附件没上传'); */
            
            var img = "";
            if(imgSuffix.indexOf(retJson.suffix) != -1){
            	img = "<img style='width:100px;height:100px;' src=\"<%=path %>"+retJson.filepath+"\" />";
            }
            
            $("#url").val(retJson.filepath);
           /*  $('#fileQueue').append("<li id='li_"+indx+"'>&nbsp;附件名：<input name='attachRealTitle' value='"+retJson.filename+"' ></input>"
            +"&nbsp;&nbsp;重命名：<input name='attachTitle' value='"+retJson.filename+"'></input>"
            +"<input name='attachPath' type='hidden' value='"+retJson.filepath+"' ></input>"
            
            +"<input name='attachFileType'  type='hidden' value='"+retJson.suffix+"'></input>"
            
            + "&nbsp;&nbsp;&nbsp;&nbsp;排序：<input name='attachSequence' value='"+indx+"'></input>"
             +"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a></li>"
            
            +"<div name='sty'>&nbsp;</div>"); 
            
            $('input[name="attachRealTitle"]').css("width","200px");
            $('input[name="attachRealTitle"]').css("border","none");
            $('input[name="attachTitle"]').css("width","200px");
            $('input[name="attachSequence"]').css("width","20px"); */
            /* $('div[name="sty"]').css("height","10px");  */
            
        }  
    }); 
    
});  


function testNum1(){
var re =/^\-?[0-9\,]*\.?\d*$/; 
	if(!re.test(document.getElementById("attestationScore").value)){
	   alert("请输入正确的积分数值！");
	   return false; 
	}
	$('form1').submit();
  
}

function chcekType(){
	var param = $("userAccount").val();
	$.ajax({
		type : "POST",
		url : "${path }/attestation/v_attType",
		data : param,
		async : false,
		success : function(data) {
			alertc(data);
		}
	});
}


</script> 
</html>
