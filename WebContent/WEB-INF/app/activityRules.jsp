<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<%@include file="taglibs.jsp"%>
<title>${showTitle }-活动规则</title>
<link rel="stylesheet" type="text/css"
	href="${configjscss }/css/jquery.mobile-1.4.2.min.css">
<link rel="stylesheet" type="text/css"
	href="${configjscss }/css/common.css">
<script type="text/javascript"
	src="${configjscss }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${configjscss }/js/jquery.mobile-1.4.2.min.js"></script>
</head>

<body>
<div class="ui-page ui-page-theme-a ui-page-active tx" data-role="page" style="background:#edf1f4;">
    <!--   <div class="header">
        <a class="back_arr" href="javascript:;" onclick="history.back(-1)"></a>
        <h1>活动规则</h1>
      </div>header -->
<!---我的账户-我的红包-->
      <!---我的账户-我的红包-->
        <div class="hud_con">
            <a href="#">
                <div class="pic"><img src="${configjscss }/images/sl_01.png" /></div>
                <div class="txt">
                    <h5>A可拿如下奖励（* 皆以一年标为准）</h5>
                    <ul>
                      <li>
                        <span>一级奖励：好友投资额*6‰（年化）</span>
                        <p>好友投资100,000元，您拿600元+好友投资200,000元，您拿1200元+......奖励无上限</p>
                      </li>
                      <li>
                        <span>二级奖励：好友投资额*2.4‰（年化）</span>
                        <p>好友投资200,000元，您拿480元+好友投资400,000元，您拿960元+......奖励无上限</p>
                      </li>
                      <li>
                        <span>三级奖励：好友投资额*0.96‰（年化）</span>
                        <p>好友投资300,000元，您拿288元+好友投资600,000元，您拿576元+.......奖励无上限</p>
                      </li>
                    </ul>
                </div>
                 <div class="txt1" style="background:#25c7af;height: 29rem;">
                    <h5>【活动规则】</h5>
                    <ul>
                        <li>1、如何邀请：好友需通过你的专属链接注册才能建立推荐关系；</li>
                        <li>2、活动时间：长期有效；</li>
                        <li>3、奖励发放：好友投资成功后实时到账，将有短信、微信提醒，用户可在“我的账户——邀请好友”中查看；现金奖励可用于投资抵用或直接提现；</li>
                        <li>4、奖励无上限; 被邀请人需关注小诸葛金服官方微信：xzgjfcom；</li>
                        <li>5、小诸葛金服保留本次活动的最终解释权 。</li>
                    </ul>
                </div>
            </a>
          
        </div><!-- hud_con -->
    </div>
</body>
</html>
