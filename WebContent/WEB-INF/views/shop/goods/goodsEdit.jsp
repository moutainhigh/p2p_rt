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
		<form method="post" action="shopGoods/${PRE_PATH_EDIT}save"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">

			<input type="hidden" name="right_id" value="${right_id}" /> 
			<c:if test="${not empty record}">
			<input type="hidden" id="id" name="id" value="${record.id}"> 
			</c:if>
			
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>商品分类：</label> 
					<select name="categoryCode"  class="required">
					<option value="">请选择分类</option>
					<c:forEach var="c" items="${shopCtg }">
					<option value="${c.categoryCode }">${c.categoryName }</option>
					</c:forEach>
					</select>
					
				</p>
				<div class="divider"></div>
				<p>
					<label>商品标题：</label><input name="goodsName" class="required"
						type="text" alt="请输入商品标题" style="width: 400px;"
						value="${record.goodsName }" />
				</p>
				<div class="divider"></div>
				<p>
					<label>商品编码：</label> <input type="text" class="required"
					id="goodsCode"	name="goodsCode" value="${record.goodsCode }" /><span
						id="code_info" class="info" style="color: red"></span>
				</p>

				<div class="divider"></div>
				<p>
					<label>总数量：</label> <input name="totalCount" type="text"  class="required digits"
						value="${record.totalCount }" style="margin-right: 10px;"  min="0" max="2000000000"/> <span
						class="info"></span>
				</p>
			
			<div class="divider"></div>
				<p>
					<label>兑换所需积分：</label> <input name="requireCredit" type="text"  class="required digits"
						value="${record.requireCredit }" style="margin-right: 10px;" min="0" max="2000000000" /> <span
						class="info"></span>
				</p>
				<div class="divider"></div>
				
				<p>
					<label>排序：</label> <input name="orderBy" type="text" class="required digits"  min="0" max="200000"/>
				</p>
				
				<div class="divider"></div>
				<div class="unit">
					<label>预定发布时间：</label> <input
						class="date textInput readonly required" name="publishTime"
						<c:if test="${empty record.publishTime  }">value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
						<c:if test="${not empty record.publishTime  }"> value="<fmt:formatDate value="${record.publishTime  }" pattern="yyyy-MM-dd HH:mm:ss"/>" </c:if>
						type="text" readonly="true" datefmt="yyyy-MM-dd HH:mm:ss"
						name="date10"> <a class="inputDateButton"
						href="javascript:;">选择</a> <span class="info">yyyy-MM-dd
						HH:mm:ss</span>
				</div>
				
				<div class="divider"></div>
				<p style="height: 100px;">
					<label>商品摘要：</label>
					<textarea name="goodsSummary" cols="50" rows="5">${record.goodsSummary }</textarea>
				</p>
				<div class="divider"></div>
				<p>
					<label>状&nbsp;&nbsp;&nbsp;&nbsp;态：</label> <input
						name="isEnable" type="radio" value="1"
						<c:if test="${'0' ne record.isEnable }">checked="checked"</c:if> />显示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="isEnable" type="radio" value="0"
						<c:if test="${'0' eq record.isEnable }">checked="checked"</c:if> />隐藏
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>商品详细：</label>
					<textarea name="goodsDetail" class="editor"
						upImgUrl="upload/editorImg" cols="110" rows="15">${record.goodsDetail }</textarea>
				</p>
				<div class="divider"></div>
				<p>
	                    <input type="file" name="uploadify" id="uploadify" /> 
	                      <div class="divider"></div> 
					    <div id="fileQueue" style="width: 740px auto; border: 1px solid #99BBE8;">
					      <ul id="fileUL" style="list-style: none;">
						      <c:if test="${not empty record.logPath}">
						        <li id='img_${record.id }'> 
						          <img src='<%=path %>${record.logPath}' style="width: 200px;height: 200px;"></img>
						          <input type="hidden" name="logPath" value="${record.logPath}"/>&nbsp;&nbsp;&nbsp;
						          &nbsp;&nbsp;<a href="javaScript:delet('img_${record.id }')" >删除</a>
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

</body>
<script type="text/javascript">  

function navTabAjaxDone(json) {
	
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok) {
		if (json.navTabId) { //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			navTabPageBreak();
		}
		if($("#id").val() != undefined){ //修改
		//	navTab.reload(json.forwardUrl);
			setTimeout(function() {
				navTab.closeCurrentTab();
				
			}, 100);
			
		}else{ //添加
			navTab.reloadFlag("editGoods");
		//	navTabPageBreak();//刷新当前页面
		}
		
	}

}

	var imgSuffix = ".jpg,";
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
	     //   	alert(1);
	        //	$("#fileQueue").html();
	        	indx++;
	        	var retJson = eval(response)[0];
	        	
	        	$("#fileUL").html("<li  id='li_"+indx+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 200px;height: 200px;' ></img>"
	        	+"<input type='hidden' name='logPath' type='text' value='"+retJson.filepath+"' ></input>"
	        	+"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a>"
	        	+"</li>");
	        	
	        }  
	    }); 
		$("[name='categoryCode']").val("${record.categoryCode }");
		var orderByVal = "${record.orderBy }";
		if(orderByVal == ""){
			orderByVal = "100";
		}
		$("[name='orderBy']").val(orderByVal);
	});  
    
    
    function delet(attachId){
    	$("#"+attachId).remove();
    }
    function getSelectedChannel(channelId,channelName){
    	$("input[name='district.id']").val(channelId);
    	$("input[name='district.districtName']").val(channelName);
    }
    $("#goodsCode").blur(function(){
		$("#code_info").text("");
		var url="<%=path %>${ADMIN_URL }/shopGoods/checkCode.do";
		$.ajax({
			  type: "post",
			  data: {"id":$("#id").val(),"code":$("#goodsCode").val()},
			  url: url,
			  async:false,
			  success:function (ret){
				  if(ret == 'fail'){
					  $("#code_info").text( $("#goodsCode").val()+"编码已存在!");
					  $("#goodsCode").val(null);
					  return false;
				  }else{
				  }
			  }} );
	} );
    
    </script>



</html>
