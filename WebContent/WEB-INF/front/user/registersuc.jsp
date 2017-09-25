<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
<%-- <div class="Position"><span><img src="${frontPath}/images/lx-jk_03.jpg" width="18" height="24" /></span><a href="${path }/index.html"><span>首页</span></a><span>&gt;</span><a href="javascript:;"><span>注册成功</span></a></div>
<div  class="wydk-xqb" style=" border:5px solid #ccc;height: 300px;"> 
      <div class="dlh-k">
      <div class="dlcg-kuang">
        <div class="dlcg-tu"><img src="${frontPath}/images/dlh_03.jpg" width="81" height="59" /><span style="font-size:30px; font-family:'微软雅黑'; color:#fe0000; line-height:50px;">恭喜您，邮箱激活成功！</span></div>
        <div class="dlcg-wz" style="padding-left: 20px;padding-bottom: 20px;">
	        <a href="${path}/account/accountIndex.html" style=" margin-top:25px; float:left;">
	        <input type="button" style="cursor: pointer;color:#fff; height: 35px; background-color:#df3130; width:160px; border:0; font-size:14px;" value="进入用户中心" /></a>
        </div>
      </div>
    </div>

</div> --%>
<div class="J-forgetpassword nav-con jboxsize jborder">
         <div class="J-fgp-tishi J-email-success">
          <p> 恭喜您，邮箱激活成功!</p>
         </div>
         <a href="${path}/account/accountIndex.html" class="J-es-btn">进入账户中心</a>
        </div>
	<jsp:include page="/foot.do"></jsp:include>

</body>
<script type="text/javascript">
	var msg = "${message}";
	if (msg) {
		alert(msg);
	}
</script>
</html>