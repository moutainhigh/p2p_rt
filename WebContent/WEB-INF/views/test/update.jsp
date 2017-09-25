<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加用户</title>
		<style type="text/css">  
    .inputcss  
    {  
        color:#333333;  
        font-family: "Tahoma";   
        font-size: 12px;   
        border:solid 1px #CCCCCC;  
    }  
    .buttoncss  
    {  
        color:#333333;  
        font-family: "Tahoma";   
        font-size: 12px;   
        background-color:#FFFFFF;  
        border:solid 1px #CCCCCC;  
    }  
    </style>  
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="test/${PRE_PATH_EDIT }save"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${usr.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>用户名:</label><input name="name" class="required" minlength="2" maxlength="10" type="text" alt="请输入用户名" size="30"  value="${usr.name }" /></p>
					<div class="divider"></div>
					<p><label>年龄:</label><input name="age" class="required" minlength="2" maxlength="10" type="text" alt="请输入年龄" size="30"  value="${usr.age }" /></p>
					<div class="divider"></div>
					<table style="width: 90%;">  
            <tr>  
                <td style="width: 100px;">  
                    <input type="file" name="uploadify" id="uploadify" />  
                </td>  
                <td align="left">  
                    <a href="javascript:$('#uploadify').uploadifyUpload()">开始上传</a>|  
                <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>  
                <span id="result" style="font-size: 13px;color: red"></span>  
                </td>  
            </tr>  
        </table>  
            <div id="fileQueue" style="width: 400px;height: 300px; border: 2px solid green;"></div>  
    
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
    $(document).ready(function() {  
        $("#uploadify").uploadify({  
            'uploader'       : '<%=path %>/uploadify/scripts/uploadify.swf',  
            'script'         : '<%=path %>/up2.jsp',  
            'cancelImg'      : '<%=path %>/uploadify/cancel.png',  
       //     'buttonImg'      : 'uploadify/buttonImg.png',  
            'folder'         : '/jxdBlog/photos',  
            'queueID'        : 'fileQueue',  
            'auto'           : false,  
            'multi'          : true,  
            'wmode'          : 'transparent',  
            'simUploadLimit' : 999,  
            'fileExt'        : '*.png;*.gif;*.jpg;*.bmp;*.jpeg',  
            'fileDesc'       : '图片文件(*.png;*.gif;*.jpg;*.bmp;*.jpeg)',  
            'onAllComplete'  :function(event,data)   
            {  
                $('#result').html(data.filesUploaded +'个图片上传成功');  
            }  
        });  
    });  
    </script>  
</html>
