<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/views";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>修改/添加内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="content/${PRE_PATH_EDIT}saveOrUpdate"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate" id="contentForm">

			<input type="hidden" name="right_id" value="${right_id}" /> <input
				type="hidden" name="id" value="${content.id}"> <input
				type="hidden" name="channelId" value="${channeIdNow }">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>所属栏目：</label> <input name="district.id" value=""
						type="hidden"> <input class="required textInput readonly"
						value="<c:forEach items="${channelList }"   var="result"  varStatus="status">${result.channelTitle }<c:if test="${!status.last}">,</c:if></c:forEach>"
						name="district.districtName" type="text" readonly=""> <a
						class="btnLook" href="content/treeInfo?contentId=${content.id}"
						lookupgroup="district" width="250">栏目</a> <input name="ids"
						type="hidden"
						value="<c:forEach items="${channelList }"   var="result"  varStatus="status">${result.id }<c:if test="${!status.last}">,</c:if></c:forEach>" />
				</p>
				<div class="divider"></div>
				<p>
					<label>内容标题：</label><input name="contentTitle" class="required"
						type="text" alt="请输入内容标题" size="30"
						value="${content.contentTitle }" />
				</p>
				<div class="divider"></div>
				<p>
					<label>标题外部链接url：</label> <input type="text"
						name="externalLinkTitle" value="${content.externalLinkTitle }" /><span
						class="info">url以http://开头</span>
				</p>

				<div class="divider"></div>
				<p>
					<label>文章来源：</label> <input name="contentSource" type="text"
						value="${content.contentSource }" style="margin-right: 10px;" /> <input
						name="contentSourceLink" type="text"
						value="${content.contentSourceLink }" alt="请输入来源文章URL" /><span
						class="info">不写默认为原创</span>
				</p>
				<div class="divider"></div>
				<%-- <c:if test="${empty content.id}"> --%>
				<p>
					<label>排序：</label> <input name="contentSequence" type="text"
						<c:if test="${empty content.contentChannels[0].contentSequence}">value="100" </c:if>
						<c:if test="${not empty content.contentChannels[0].contentSequence}">value="${content.contentChannels[0].contentSequence}" </c:if> />
				</p>
				<%-- </c:if> --%>
				<div class="divider"></div>
				<div class="unit">
					<label>预定发布时间：</label> <input
						class="date textInput readonly required" name="contentPublishTime"
						<c:if test="${empty content.contentPublishTime  }">value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
						<c:if test="${not empty content.contentPublishTime  }"> value="<fmt:formatDate value="${content.contentPublishTime  }" pattern="yyyy-MM-dd HH:mm:ss"/>" </c:if>
						type="text" readonly="true" datefmt="yyyy-MM-dd HH:mm:ss"
						name="date10"> <a class="inputDateButton"
						href="javascript:;">选择</a> <span class="info">yyyy-MM-dd
						HH:mm:ss</span>
				</div>
				<div class="divider"></div>
				<p>
					<label>作&nbsp;&nbsp;&nbsp;&nbsp;者：</label> <input
						name="contentAuthor" type="text" value="${content.contentAuthor }" />
				</p>
				<div class="divider"></div>
				<p style="height: 100px;">
					<label>内容摘要：</label>
					<textarea name="contentSummary" cols="50" rows="5" onKeyUp="textCounter(contentSummary,remLen,150);" id="contentSummary">${content.contentSummary }</textarea>
					<span>您还可以输入:<input name="remLen" type="text" value="150" readonly="readonly" size="2" style="float: none;color: #F10D0D;border: 0px;background: #FFFFFF;width: 23px;">个字符</span> 
				</p>
				<div class="divider"></div>
				<p>
					<label>状&nbsp;&nbsp;&nbsp;&nbsp;态：</label> <input
						name="contentStatus" type="radio" value="1"
						<c:if test="${'0' ne content.contentStatus }">checked="checked"</c:if> />显示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="contentStatus" type="radio" value="0"
						<c:if test="${'0' eq content.contentStatus }">checked="checked"</c:if> />隐藏
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>内容详细：</label>
					<textarea name="contentTxt" class="editor"
						upImgUrl="upload/editorImg" cols="110" rows="15">${content.contentTxt }</textarea>
				</p>
				<div class="divider"></div>
				<p>
					<input type="file" name="uploadify" id="uploadify" />
					<!--  <a href="javascript:$('#uploadify').uploadifyUpload()">开始上传</a>|  
	              	    <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>   -->
					<span id="result" style="font-size: 13px; color: red"></span>
				<div class="divider"></div>
				<div id="fileQueue"
					style="width: 740px auto; border: 1px solid #99BBE8;">
					<ul id="fileUL">
						<c:forEach var="attach" items="${content.attachs }">
							<li id="${attach.id }"><input type="hidden"
								name="attachPath" value="${attach.attachPath}"
								style="width: 300px;"></input> <input type="hidden"
								name='attachFileType' value="${attach.attachFileType}"></input>
								<%-- <input name="attachId" value="${attach.id }"/> --%>
								&nbsp;附件名：<input name="attachRealTitle"
								value="${attach.attachRealTitle}"
								style="width: 200px; border: none;"></input> &nbsp;重命名： <input
								name="attachTitle" value="${attach.attachTitle}"
								style="width: 200px;"></input> &nbsp; 排序： <input
								name="attachSequence" value="${attach.attachSequence}"
								style="width: 20px;"></input> <a
								href="javaScript:delet(${attach.id })">&nbsp;删除</a>
								<div style="height: 10px;"></div></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="submitForm();">保存</button>
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

</body>
<script type="text/javascript">  
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
            'simUploadLimit' : 20,  
            'fileExt'        : '*.jpg;*.jpeg;*.gif;*.png;*.bmp;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.wps;*.pdf;*.rar;*.zip',  
            'fileDesc'       : '请上传合法文件(*.jpg;*.jpeg;*.gif;*.png;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.rar;*.zip)',  
            'sizeLimit'		 : 20000000,    
            'onComplete'  :function(event,queueId,fileObj,response,data){  
            	var retJson = eval(response)[0];
                /* $('#result').html('还剩'+data.fileCount +'个附件没上传'); */
                
                $('#fileUL').append("<li id='"+queueId+"'>&nbsp;附件名：<input name='attachRealTitle' value='"+retJson.filename+"' ></input>"
                +"&nbsp;&nbsp;重命名：<input name='attachTitle' value='"+retJson.filename+"'></input>"
                +"<input name='attachPath' type='hidden' value='"+retJson.filepath+"' ></input>"
                
                +"<input name='attachFileType'  type='hidden' value='"+retJson.suffix+"'></input>"
                
                + "&nbsp;&nbsp;&nbsp;&nbsp;排序：<input name='attachSequence' value='0'></input>"
                 +"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('"+queueId+"')\" >删除</a></li>"
                 ); 
                
                $('input[name="attachRealTitle"]').css("width","200px");
                $('input[name="attachRealTitle"]').css("border","none");
                $('input[name="attachTitle"]').css("width","200px");
                $('input[name="attachSequence"]').css("width","20px");
               
                
            }
        }); 
        
    });  
    
    
    function delet(attachId){
    	$("#"+attachId).remove();
    }
    function getSelectedChannel(channelId,channelName){
    	$("input[name='district.id']").val(channelId);
    	$("input[name='district.districtName']").val(channelName);
    }
   
    function textCounter(field, countfield, maxlimit) {   
	    // 函数，3个参数，表单名字，表单域元素名，限制字符；   
	    if (field.value.length > maxlimit)   
	    //如果元素区字符数大于最大字符数，按照最大字符数截断；   
	    field.value = field.value.substring(0, maxlimit);   
	    else   
	    //在记数区文本框内显示剩余的字符数；   
	    countfield.value = maxlimit - field.value.length;   
    }
    function submitForm(){
    	var len = $("#contentSummary").val().length;
    	if(len>150){
    		$("#contentSummary").focus();
    		return false;
    	}
    	$("#contentForm").submit();
    }
</script>
</html>
