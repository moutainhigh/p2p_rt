<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function(){
	 	$('.rIcon_toTop1').click(function(){
		 	$('body,html').animate({
			 	'scroll-top':0,
			 },'slow');
		 });
	 });
	function onOverDiv(){
		$('.J-phonenumb').show().addClass('J-addwidth');
	}
	function onLeaveDiv(){
		$('.J-phonenumb').hide().removeClass('J-addwidth');
	}
</script>
<ul class="rightIcon" id="rightIcon">
	 <!--<li style="margin-bottom:0;"><img src="${frontPath }/images/J-zgQ.png" onclick="javascript:window.location.href='${path}/index.html'"/></li>
	<li class="rIcon_tel" onclick="javascript:window.open('http://wpa.b.qq.com/cgi/wpa.php?ln=2&uin=4006114589&ref='+document.location, '_blank', 'height=544, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');">
		<span><a style="text-decoration:none;" href="javascript:(void)" title="点击访问浩茗金融企业QQ"><em></em></a></span>
	</li>
	<li class="rIcon_QQ">
		<span id="J-phonepic" onmouseover="onOverDiv();" onmouseleave="onLeaveDiv();"></span>
		<div class="J-phonenumb jboxsize " style="width: 250px">客服热线: ${websitetel}</div>
	</li> -->
	<li class="rIcon_erweima"><span><em><img src="${frontPath }/images/right_weixin.png" style="width:134px;heigth:134px;"/></em></span></li>
	<!--<li class="rIcon_weixin"><span onclick="javascript:window.location.href='${path }/borrow/toCalculator.do'"></span></li>-->
	<li class="rIcon_toTop1"><span></span></li>
</ul>