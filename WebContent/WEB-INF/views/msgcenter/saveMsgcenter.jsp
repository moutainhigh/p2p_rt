<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
	<div class="pageContent">
			<form id="messageForm" method="post"   action="msg/${PRE_PATH_EDIT }toSaveMsgCenter" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${user.id }"> 
				<input type="hidden" id="receiveUserIds" name="receiveUserIds"/> 
				<input type="hidden" id="noticeTypeIds" name="noticeTypeIds"/> 
				<div class="pageFormContent" layoutH="56">
					
					<p><label>标题:</label><input name="messageTitle" id="messageTitle" value="${user.messageTitle }"  type="text" alt="请输入标题" size="30" class="required" value="" /></p>
					<div class="divider"></div>
					
					<p><label>收件人:</label>
						<span  id="showCheckUserName" style="color: red; float: left;"></span>
			      		<a class="btnLook"  lookupgroup="user" href="msg/v_getAllFrontUserNew" width="800" height="396" rel="jbsxBox">所有收件人</a></p>
					<div class="divider"></div>
					
					<p><label>消息类型:</label>
					<!-- <input type="checkbox" name="typeid" id="typeid" class="typeid" value="1" disabled="disabled" checked="checked"/>站内信 -->
						<select name="noticeTypeId">
							<c:forEach var="type" items="${MSG_ALL_TYPE }">
								<%-- <input type="checkbox" name="typeid" id="typeid" class="typeid" value="${type.key }"/>${type.value } --%>
								<option value="${type.key }">${type.value }</option>
							</c:forEach>
						</select>
					</p>
					<div class="divider"></div>
					
					<p style="height: 84%;width: 100%;"><label>内容:</label> 
					<textarea rows="15" cols="100"  id="messageContent" name="messageContent" upImgUrl="upload/editorImg"  ></textarea></p>
					<div class="divider"></div>
					
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="frmSubmit()" >
										发送
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
function frmSubmit(){
	$("#messageForm").submit();
	
}
$(function() {
    $(".typeid").click(function() {
        var aa = document.getElementsByName("typeid");
        var ss = "";
        for (var i = 0; i < aa.length; i++) {
            if (aa[i].checked) {
                ss += aa[i].value+",";
            }
        }
        $("#noticeTypeIds").val(ss);
    });
})
function getSelectedUser(userId,userAccount){
    	 $("input[name='receiveUserIds']").val(userId);
    	$("#showCheckUserName").text(userAccount); 
    }

</script>
</html>