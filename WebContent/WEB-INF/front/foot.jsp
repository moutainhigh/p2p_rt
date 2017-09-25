<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String frontPath = path + "/common/front";
%>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="frontPath" value="<%=frontPath%>"></c:set>
<!--bottom-->
<!-- footer star--->
<div class="footer">
	<div class="footer-con">
		<!--  友情链接  start -->
		<%-- <ul class="fc-link">
			<li style="font-size:16px;color:#d1d1d2;margin-bottom:14px;">友情链接</li>
			<c:forEach items="${blogrollList}" var="xszn">
				<c:choose>
					<c:when test="${not empty xszn.external_link_title  }">
						<li style="margin-top: 4px">
							<a href="${xszn.external_link_title }" target="blank">${xszn.content_title} </a>
						</li>
					</c:when>
					<c:otherwise>
						<li  style="margin-top: 4px">
							<a href="<c:url value='/content/contentDetails/${xszn.channel_id}/${xszn.id}.do'/>">${xszn.content_title}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach> 
		</ul> --%>
		<!--  友情链接  end -->
		<ol class="fc-content clearfix">
			<li class="fc-con-aboutus" style="text-align: left;">
				<dl><dt><a href="${path }/about">关于我们</a></dt></dl>
				<dl><dt><a href="${path }/help">帮助中心</a></dt></dl>
				<dl><dt><a href="${path }/security">安全保障</a></dt></dl>
				<dl><dt><a href="${path }/zixun">资讯中心</a></dt></dl>
				<dl><dt><a href="${path }/zhuanti">专题中心</a></dt></dl>
			</li>
			<li class="fc-con-pic">
				<ul>
					<li><div><!-- <img src=""/> --></div><span class="fc-conp-bg2"><img src="${frontPath}/images/J-weibo.png"></span><a href="http://weibo.com/hmjr99" target="_blank"><p style="margin-top: -38px;color: #FFFFFF;">新浪微博</p></a></li>
					<li><div class="fc-conp-ewm"><img src="${frontPath}/images/J-erweima01.png" style="width: 129px;"/></div><span class="fc-conp-bg1"><img src="${frontPath}/images/J-weixin.png"></span><a><p style="width: 100px;color: #FFFFFF;">微信</p></a></li>
					<li><div class="fc-conp-appimg"><img src="${frontPath}/images/J-Appone.png" style="width: 129px;"></div><span class="fc-conp-bg3"><img src="${frontPath}/images/J-phone2.gif"></span><a><p style="color: #FFFFFF;">手机APP</p></a></li>
				</ul>
			</li>
			<li class="fc-con-phone">
				<div class="J-numb-phone-title">客服电话</div>
				<div class="J-numb-phone">${websitetel}</div>
				<!-- <p>${serviceTime}</p> -->
				<p style="font-size: 15px;">${CACHE_SYS_SMTP_CONFIG.sysMailAddress}</p>
				<p>09:30 - 18:00</p>
			</li>
  		</ol>
	</div>
	<div class="footer-bottom">
		<div class="footer-bottom-con clearfix">
			<p style="margin-left: 444px">
				<a id='___szfw_logo___' href='https://credit.szfw.org/CX20160503015017880188.html' target='_blank'><img src='${frontPath}/images/cxwz.png' border='0' /></a>
				<script type='text/javascript'>(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();</script>
			</p>
			<P style="margin-left: 300px">Copyright&copy;2014   ${websiteName}  版权所有　${websiteicp}&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <script type="text/javascript">
				var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
				document.write(unescape("%3Cspan id='cnzz_stat_icon_1257739900'%3E%3C/span%3E%3Cscript src='" 
						+ cnzz_protocol + "s95.cnzz.com/z_stat.php%3Fid%3D1257739900%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));
			</script> -->
			</P>
		</div>
	</div>
</div>
<!--bottom end-->
<script type="text/javascript">
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m);
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');
	ga('create', 'UA-74637915-1', 'auto');
	ga('send', 'pageview');
</script>