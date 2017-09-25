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
<title>${showTitle }-借款</title>
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
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page" style="background:#edf1f4;" >
     <!--  <div class="header">
        <h1>借款</h1>
      </div>header -->
        <dl class="sl_gejdg">
            <a class="current" rel="external" onclick="javascript:window.location.href='${pathWeb}/content/appGetH5Content.do?contentId=personLoan'" href="javascript:;">
                <dt>
                    个人借款指南
                </dt>
            </a>
            <dd></dd>
            <a rel="external" onclick="javascript:window.location.href='${pathWeb}/content/appGetH5Content.do?contentId=enterpriseLoan'" href="javascript:;" ><dt>企业借款指南</dt></a>
        </dl>
      <div class="gr_dl">
        <div class="del_txt">
          <div class="del_con">
            <h5>关于借款申请</h5>
            <p>小诸葛金服平台的借款功能旨在帮助借款用户以低成本获得借款。用户在需要资金时，可通过抵押、质押、信用、担保等借款方式申请借款，小诸葛金服线下审核评估后，将会根据借款方式给出融资方案。</p>
          </div>
          <div class="del_con1">
            <h5>申请指南</h5>
            <ul>
              <li>1、持中国居民身份证的中国大陆公民;</li>
              <li>2、年龄在20-55周岁之间（法定年龄是按周岁计算,且以生日为准）；</li>
              <li>3、手机绑定；</li>
              <li>4、有固定收入来源；</li>
            </ul>
            <h5>准备材料</h5>
            <ul>
              <li>1、居民身份证；</li>
              <li>2、居住证明文件：银行对账单、公用事业缴费单、房产证、户口簿；</li>
              <li>3、手机绑定；</li>
              <li>4、收入证明文件：央行征信记录、交易流水或银行存折。</li>
            </ul>
          </div>
        </div>
        
      </div><!-- gr_dl -->
     <!-- <div class="footer">
         <ul>
           <li><a rel="external" href="首页.html"><i></i><span>首页</span></a></li>
           <li><a rel="external" href="理财.html"><i class="one3"></i><span>理财</span></a></li>
           <li class="current"><a rel="external" href="个人借款指南.html"><i class="one1"></i><span>借款</span></a></li>
           <li><a rel="external" href="我的.html"><i class="one2"></i><span>我的</span></a></li>
         </ul>
      </div> -->
    </div>
   
    </body>
</html>