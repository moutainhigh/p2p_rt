<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }提醒设置</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/notice.css" />
<link rel="stylesheet" type="text/css" href="${frontPath }/css/account.css" />
<script charset="utf-8" src="${frontPath }/js/jquery.dialog.js"></script>
<style type="text/css">
h1{font-family:"微软雅黑"; font-size:12px; margin:10px; padding:0px; margin-bottom:10px;  margin-left:40px; clear:both; }
</style>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left25" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
	<div class="setInfo fr">
		<form id="setNoticeForm" method="post" action="${path }/basics/setNoitce.do">
    	<div id="main">
					<div class="alerts">请勾选设置来收取您所需要的消息提醒。为了您的交易安全，部分重要信息不能取消接收。</div>
					<c:forEach var="noticeType" items="${noticeTypes}">
					<h1><b>${noticeType.noticeName}</b></h1>
					<div class="nr_k">
					<c:forEach var="notice" items="${noticeList}">
						<c:if test="${notice.noticeTypeId eq noticeType.id}">
							<div class="txsz_bao">
									<ul>
										<li style="color:#ff4400;">${notice.noticeTitle }
											<input type="hidden" name="id" value="${notice.id}" />
										</li>
										<li><input name="messagge_${notice.id}" type="checkbox" value="1" 
										<c:if test="${notice.message==1 }">disabled="disabled" checked="checked"</c:if>
										<c:if test="${notice.message==2 }">disabled="disabled"</c:if>
										<c:if test="${notice.message==3 }">checked="checked"</c:if>
										 />站内信提醒</li>
										<li><input name="email_${notice.id}" type="checkbox" value="1"
										<c:if test="${notice.email==1 }">disabled="disabled" checked="checked"</c:if>
										<c:if test="${notice.email==2 }">disabled="disabled"</c:if>
										<c:if test="${notice.email==3 }">checked="checked"</c:if>
										 />邮件提醒</li>
										<li><input name="phone_${notice.id}" type="checkbox" value="1"
										<c:if test="${notice.phone==1 }">disabled="disabled" checked="checked"</c:if>
										<c:if test="${notice.phone==2 }">disabled="disabled"</c:if>
										<c:if test="${notice.phone==3 }">checked="checked"</c:if>
										 />手机提醒</li>
									</ul>
							</div>
						</c:if>
					</c:forEach>
					</div>
					</c:forEach>
					<div style="margin-top: 80px;clear:both;margin-left: 180px;">
					<span class="wdzh-buttu" onclick="setNoticeFormSubmit();" style="cursor: pointer;">确认提交</span>
					</div>
					
					<div class="sztx_wz">* 温馨提示：如果手机短信功能为空，所有的短信功能将不能使用 </div> 
					</div>
			</form>			
	</div>
    </div>
  </div>	
    
</div>
 <jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function(){
	initUserSetting();
	changeCurrent();
	});
function changeCurrent(){
	var current=$("#menu").find(".current");
	$(current).removeClass("current");
	$("#userNotice").addClass("current");
}
function setNoticeFormSubmit(){
	$.ajax({
		type : "POST",
        url:'${path }/basics/setNoitce.do',
        data:$("#setNoticeForm").serialize(),
        async:false,
        success: function(data) {
        	if(data.code == '200'){
            	initCaptcha();
				alertc("提醒设置成功。",reloadPage);
        	}else{
        		initCaptcha();
				alertc("提醒设置失败。");
        	}
        }
    });  
}
function reloadPage(){
	window.location.reload(true);
}
function initUserSetting(){
	
	<c:forEach var="item" items="${noticeUsers}"> 
		<c:if test="${item.message == 1}">
		$("input[name='messagge_${item.noticeId}']").attr("checked","checked");
		</c:if>
		<c:if test="${item.email == 1}">
		$("input[name='email_${item.noticeId}']").attr("checked","checked");
		</c:if>
		<c:if test="${item.phone == 1}">
		$("input[name='phone_${item.noticeId}']").attr("checked","checked");
		</c:if>
	</c:forEach>
}
</script>

</html>