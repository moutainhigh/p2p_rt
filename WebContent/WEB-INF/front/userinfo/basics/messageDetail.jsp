<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-站内信</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
<script charset="utf-8" src="${frontPath }/js/jquery.dialog.js"></script>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left24" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<div class="setInfo fr">
					<table class="uniceTable siteInfoWidth1">
						<tbody>
							<tr>
								<td style="width: 30px;" id="receiveTab" class="userDh"><a
									href="javascript:;">查看站内信</a></td>
								<td style="border-right: none;">&nbsp;</td>
							</tr>
						</tbody>
					</table>

					<div class="reg-items" id="sendMessage">
						<table class="tableLs siteInfoWidth1 tab_br ">
							<tr>
								<td class="ls">发件人：</td>
								<td><input type="text"
									value="${message.sendUser.userAccount}" class="input"
									readonly="readonly" />
							</tr>
							<tr>
								<td class="ls">收件人：</td>
								<td><input type="text" name="userAccount" id="userAccount"
									value="${message.receiveUser.userAccount}" class="input"
									readonly="readonly" /> <span class="tip" id="userAccountTip"></span>
								</td>
							</tr>
							<tr>
								<td class="ls">标题：</td>
								<td><input size="38" class="input" readonly="readonly"
									type="text" name="messageTitle" id="messageTitle"
									value="${message.messageTitle }" /></td>
							</tr>
							<tr>
								<td class="ls">内容：</td>
								<td><textarea readonly="readonly"
										style="border: 1px solid #B4B9BD" name="messageContent"
										id="messageContent" rows="7" cols="50">${message.messageContent}</textarea></td>
							</tr>
							<tr>
							<td colspan="2">
							<span class="wdzh-buttu" onclick="goBack()" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">返回</span>
                     		</td>
                     		</tr>
						</table>
					</div>

				</div>
    </div>
  </div>	
    
</div>
<script type="text/javascript">
function goBack(){
	window.location.href="${path }/basics/userMessage.html";
}
</script>
 <!--/bottom-->
 <jsp:include page="/foot.do"></jsp:include>
</body>
</html>