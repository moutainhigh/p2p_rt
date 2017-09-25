<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-更换头像</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	
<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
   	<input type="hidden" value="left9" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
  </div>

  <div class="J-ma-conR jb_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);" class="tap">更改头像</a>
    </div>
   <div class="tab_info">
              <div class="g-alert">自定义您的头像</div>
              <table class="userPtb">
                   	<tbody><tr>
                        <td height="96" class="ls">当前使用头像：</td>
                        <td class="bl" style=" text-align:left;"><c:if test="${empty sessionScope.FRONT_USER_SESSION.avatarImg}">
									<img src="${frontPath }/images/imgurl.gif"
										style="margin: 10 auto;" />
								</c:if>
								<c:if
									test="${not empty sessionScope.FRONT_USER_SESSION.avatarImg}">
									<img src="${path}${sessionScope.FRONT_USER_SESSION.avatarImg}"
										style="margin: 10 auto;" id="avatarImg" />
								</c:if></td>
                    </tr>
                    <tr>
                      	<td class="ls">自定义头像：</td>
                        <td class="bl" style=" text-align:left;"><script src="${frontPath }/js/camera.js?B6k"
									type="text/javascript"></script>
								<script type="text/javascript">
								document.write(AC_FL_RunContent("width","450","height","253","scale","exactfit","src","${frontPath }/swf/camera.swf?nt=1&inajax=1&appid=1&uploadtype=head&uploadSize=400&ucapi=${httpUrlPath}/basics/avatar.do;jsessionid=<%=session.getId()%>",
													"id", "mycamera", "name",
													"mycamera", "quality",
													"high", "bgcolor",
													"#ffffff", "wmode",
													"transparent", "menu",
													"false", "swLiveConnect",
													"true",
													"allowScriptAccess",
													"always"));
								</script></td>
                    </tr>
                </tbody></table>               
      		</div>
    
  </div><!-- J-ma-conR -->
 </div>
</div>
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	function updateavatar() {
		window.location = window.location.href + "?" + new Date() * 1;
	};
</script>
</html>