<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${showKeywords}" />
<meta name="description" content="${description}" />
<title>${websiteName }</title>
<link href="${cssJsPath }/css/index.css" rel="stylesheet" type="text/css" />
<link href="${cssJsPath }/css/wdzh.css" rel="stylesheet" type="text/css" />
<script src="${cssJsPath }/js/jquery.menu.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${cssJsPath }/css/style.css" type="text/css" media="screen" charset="utf-8">
</head>

<body>
<!-- 头部开始 -->
	<jsp:include page="/top.do"></jsp:include>
	
	
	<div class="ct_newguys ct_invest ct_login">
    <div class="ct_fin_top">首页 &gt; 我的账户</div>
		<div class="ct_nguys_con ">
			<jsp:include page="/account/userLeft.do"></jsp:include>
			<div class="ct_ngc_R">
				<div class="ngcr_neirong">
					<p class="ngcr_mc_title">我的积分</p>
					<ul class="ct_bankmanage_title list_button">
						
						<li ><a href="${path }/member/memberIntegral.html" style="width: 106px;">积分规则</a></li>
						
						<li ><a href="${path }/member/creditLog.html"
							style="width: 106px;">积分记录</a></li>
						<li class="ct_bm_curse"><a href="${path }/member/getRedPacket.do"
							style="width: 106px;">积分转换</a></li>
						<div class="clear"></div>
					</ul>
					
					<div class="tab_info">
            <div class="wdzh-rightk">
                <!--银行卡管理 star-->
                <div class="tab_info">
                    <div class="alert">
                    领取红包<br/>

1.红包设有5元、10元、20元、50元、100元、200元、500元、1000元共8种<br/>
2. 5元=1000积分、10元=1850积分、20元=3600积分、50元=8000积分、100元=15000积分、200元=28000分、500元=60000积分、1000元=100000分 
                    </div>
                </div>
                <!--银行卡管理 star-->
                <div class="jfzh">
                <ul>
                    <li><a href="javascript:trsfer(5);">5元</a></li>
                    <li><a href="javascript:trsfer(10);">10元</a></li>
                    <li><a href="javascript:trsfer(20);" style="border-right:8px solid #a3a3a3;">20元</a></li>
                    <li><a href="javascript:trsfer(50);">50元</a></li>
                    <li><a href="${path }/member/memberIntegral.html"><img style=" position:static;" src="${cssJsPath }/images/cj.jpg"></a></li>
                    <li><a href="javascript:trsfer(100);" style="border-right:8px solid #a3a3a3;">100元</a></li>
                    <li><a href="javascript:trsfer(200);" style="border-bottom:8px solid #a3a3a3;">200元</a></li>
                    <li><a href="javascript:trsfer(500);" style="border-bottom:8px solid #a3a3a3;">500元</a></li>
                    <li><a href="javascript:trsfer(1000);" style="border-right:8px solid #a3a3a3;border-bottom:8px solid #a3a3a3;">1000元</a></li>
                </ul>
            </div>
            </div>
        </div>
        <div class="clear"></div>
      </div><!--ct_nguys_con end-->
    </div><!--ct_newguys end-->
</div>
</div>	
	

	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>

</body>
<script type="text/javascript">
$(function(){
	
});
function trsfer(money){
	$.ajax({
		cache : false,
		type : "POST",
		url : "${path }/account/transferToMoney.do",
		data : {"money":money},
		async : false,
		error : function(request) {
			alertc("服务器异常");				
		//	initCaptcha();
		},
		success : function(data) {
			alertc(data.msg);
			
		}
	});
}
</script>
</html>
