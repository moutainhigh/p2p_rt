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
		<title>修改/添加栏目</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="channel/${PRE_PATH_EDIT }saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${channel.id }">
				<div class="pageFormContent" layoutH="56">
					<p><label>上级栏目：</label>
					    <input name="district.id"  <c:if  test="${'0' ne pId }" >value="${channelP.id}" </c:if> <c:if test="${'0' eq pId }">value="0"</c:if> type="hidden">
						<input class="required textInput readonly" name="district.districtName" type="text" readonly="" <c:if test="${'0' ne pId }" >value="${channelP.channelTitle}"</c:if> <c:if test="${'0' eq pId}">value="根目录"</c:if>>
						<a class="btnLook" href="channel/treeInfo" lookupgroup="district" width="250">栏目</a>
						
					 </p>
					<div class="divider"></div>
					<p><label>栏目名称：</label><input name="channelTitle" class="required"  type="text" alt="请输入栏目名" size="30"  value="${channel.channelTitle }" /></p>
					<div class="divider"></div>
					<p><label>栏目编码：</label><input name="channelCode" id="channelCode" class="required"  type="text" alt="请输入栏目编码" size="30"  value="${channel.channelCode }" onblur="uniqueCode()" /><span id="msg1" style="color:red;"></span> </p>
					<div class="divider"></div>
					<p><label>排&nbsp;&nbsp;&nbsp;&nbsp;序：</label><input name="channelSequence" class="digits required"  type="text" alt="请输入排序" size="10" <c:if test="${empty channel.channelSequence}">value="100"</c:if> <c:if test="${not empty channel.channelSequence}">value="${channel.channelSequence}"</c:if> /></p>
					<div class="divider"></div>
					<p><label>是否显示：</label>
					    <input name="channelDisplay" class="required"  type="radio" value="1" <c:if test="${'0' ne channel.channelDisplay }">checked="checked"</c:if> />显示&nbsp;&nbsp;
					    <input name="channelDisplay" class="required"  type="radio" value="0" <c:if test="${'0' eq channel.channelDisplay }">checked="checked"</c:if> />隐藏
					 </p>
					    
					<div class="divider"></div>
					<p><label>是否外部url连接：</label>
					   <input type="text" name="channelUrl" <c:if test="${'1' ne channel.channelIsUrl }">style="display: none;"</c:if> value="${channel.channelUrl }"/>
					   <input name="channelIsUrl" class="required"  type="radio"   value="1" <c:if test="${'1' eq channel.channelIsUrl }">checked="checked"</c:if> onclick="displayUrl()"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   <input name="channelIsUrl" class="required"  type="radio"   value="0" <c:if test="${'1' ne channel.channelIsUrl }">checked="checked"</c:if>onclick="displayUrl()"/>否
				    </p>
					<div class="divider"></div>
					<p><label>Keyword：</label><input name="keyword" id="keyword" class="required"  type="text" alt="请输入栏目关键字" size="30"  value="${channel.keyword }" /><span id="msg1" style="color:red;"></span> </p>
					<div class="divider"></div>
					<p style="height: 120px;"><label>栏目描述(Description)：</label>
					   <textarea name="channelDescribe"  cols="35" rows="5" >${channel.channelDescribe }</textarea>
					</p>
					<div class="divider"></div>
					<p style="height: 250px; width: 950px;"><label>栏目内容：</label>
					    <textarea name="channelContent" class="editor" upImgUrl="upload/editorImg" cols="95" rows="10"  >${channel.channelContent }</textarea> 
					</p>
					<div class="divider"></div>
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
	function displayUrl(){
		if($("input[name='channelIsUrl']:checked").val()=='1')
			{
				$("input[name='channelUrl']").css("display","inline");
			}
		if($("input[name='channelIsUrl']:checked").val()=='0')
		{
			$("input[name='channelUrl']").css("display","none");
		}
	}
	 function uniqueCode(){
		 var channelCode=$("#channelCode").val();
		 if(channelCode!=null&&channelCode!=""){
			var url="<%=path %>${ADMIN_URL }/channel/checkChannelCode.do";
			$.ajax({
				  type: "post",
				  data: {"channelId":$("#id").val(),"channelCode" : $("#channelCode").val()},
				  url: url,
				  async:false,
				  success:function (ret){
					  if(ret == 'fail'){
						  $("#msg1").text( $("#channelCode").val()+"编码已存在!");
						  $("#channelCode").val(null);
						  return false;
					  }else{
						/*   $("#msg1").text( $("#channelCode").val()+"编码可以使用"); */
					  }
				  }} );
		 }
	} 
	
	</script>
</html>
