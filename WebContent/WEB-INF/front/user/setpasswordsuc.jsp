<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<title>${showTitle }</title>
</head>
<body>
       <!-- 头部 -->
    <jsp:include page="/top.do"></jsp:include>
		
		<!---忘记密码star--->
        <div class="J-forgetpassword nav-con jboxsize jborder">
         <h2>密码找回</h2>
         <ol>
          <li>1 填写账号信息</li>
          <li>2 验证账户信息</li>
          <li >3 重置密码</li>
          <li style="border:none;" class="J-current">4 成功</li>
          <div class="jclear"></div>
         </ol>
         <div class="J-fg-laststep clearfix">
           <dl class="clearfix jmb50">
            <dt><img src="${frontPath}/images/J-zhuce-success.png"/></dt>
            <dd>
             <p>恭喜您，密码修改成功,</p>
             <p>现在您可以使用新密码登录浩茗金融！</p>
            </dd>
           </dl>
           <div class="J-fg-btn"><a href="${path }/login">完成</a></div>
         </div>
        </div>
		
      <!-- 底部 -->
	<jsp:include page="/foot.do"></jsp:include>
</body>
</html>