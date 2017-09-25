<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String frontPath = path + "/common/front";
%>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="frontPath" value="<%=frontPath%>"></c:set>

<!-- 公用头部 -->
		<div class="header">
			<div class="header_con">
				<div class="header_L">
					<ul>
						<!-- <li><i><img src="${frontPath}/images/icon_phone.png" style="margin-top:10px;"></i><span>客服热线${websitetel}</span></li>-->
						<li style="position:relative;" class="J-add-app"><span style="cursor: pointer;">App下载</span><!-- <div class="J-add-appimg"><img src="${frontPath}/images/J-Apptwo.png" width="133" height="142"/></div> --></li>
						<!-- <li class="weixin"><i><img src="${frontPath}/images/icon_weixin.png" style="margin-top:10px;" ></i><span>微信公众号</span><div class="weixin_pic"><img src="${frontPath}/images/weixin.png" height="142" width="133" ></div></li> -->
						<li><span><a style="color: #FFFFFF;" href="${path }/help" rel="nofollow">帮助中心</a></span></li>
					</ul>
				</div>
				<c:if test="${sessionScope.FRONT_USER_SESSION == null}">
					<div class="header_R">
						<ul>
							<!-- <li><a href="${path }/toRegister.html">注册</a></li>
							<li>|</li>
							<li><a href="${path }/login">登录</a></li>
							<li>|</li>
							<li><a href="${path }/shop/toShopStore.html">积分商城</a></li>
							<li>|</li>
							<li style="padding-right:0;"><a href="${path }/help">帮助中心</a></li> -->
							<li><a href="${path }/login" rel="nofollow">登录</a></li>
							<li>|</li>
							<li><a href="${path }/register" rel="nofollow">免费注册</a></li>
						</ul>
					</div>
				</c:if>
				<c:if test="${sessionScope.FRONT_USER_SESSION != null}">
				<div class="header_R">
					<ul>
						<li class="J-afterlogin"><a href="${path }/account/accountIndex.html">欢迎您<span>&nbsp;&nbsp;${sessionScope.FRONT_USER_SESSION.userAccount}&nbsp;&nbsp;账户中心</span></a></li>
						<li>|</li>
						<li><a href="${path }/layout.html">退出</a></li>
						<li>|</li>
						<li><a href="${path }/shop/toShopStore.html">积分商城</a></li>
						<!-- <li>|</li>
						<li style="padding-right:0;"><a href="${path }/help">帮助中心</a></li> -->
					</ul>
				</div>
				</c:if>
			</div>
		</div><!-- header -->


		<div class="nav">
         <div class="nav-con">
			<div class="logo"><a href="${path }/"><img src="${frontPath}/images/logo.png"></a></div>
<%-- 			<div class="title"><img src="${frontPath}/images/title.png"></div> --%>
			<div class="slogan"><img src="${frontPath}/images/logo_title.png"></div>
			<div class="slogan" style="display:none;">最专业的消费品供应链金融服务平台</div>
			<ul>
				<li id="li1"><a href="${path }/">首页</a></li>
				<li>|</li>
				<li id="li2"><a href="${path }/invest">我要理财</a></li>
				<li>|</li>
				<li id="li4"><a href="${path }/quickBorrow/input.html">我要借款</a></li>
				<li>|</li>
                <li id="li3"><a href="${path }/safeGuarantee">安全保障</a></li>
				<li>|</li>
				<li id="li6" style="margin-left:0;"><a href="${path }/about">关于我们</a></li>
				<li>|</li>
                <li id="li5"><a href="${path }/account/accountIndex.html">我的帐户</a></li>
			</ul>
           </div>
		</div><!-- nav -->
		<!-- 公用头部 -->
<div class="clear"></div>
<script type="text/javascript">
	function changeTopHover(obj){
		$(".nav-con ul").find("li").removeClass("current");
		$("#li"+obj).addClass("current");	
	}
</script>
