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
        <a class="back_arr" href="javascript:;" onclick="history.back(-1)"></a>
        <h1>借款</h1>
      </div>header -->

      <dl class="sl_gejdg">
            <a rel="external" href="javascript:;" onclick="javascript:window.location.href='${ pathWeb}/content/appGetH5Content.do?contentId=personLoan'" >
                <dt>
                    个人借款指南
                </dt>
            </a>
            <dd></dd>
            <a class="current" rel="external" href="javascript:;" href="javascript:window.location.href='${ pathWeb}/content/appGetH5Content.do?contentId=enterpriseLoan'"><dt>企业借款指南</dt></a>
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
              <li>1、中国大陆合法经营企业；</li>
              <li>2、法定代表人年龄须在20-55周岁之间（法定年龄是按周岁计算,且以生 日为准）；</li>
              <li>3、提供借款相关资料。</li>
            </ul>
            <h5>准备材料</h5>
            <ul>
              <li>1、法定代表人身份证、财务主管身份证、法人授权委托书及代理人身份证；</li>
              <li>2、三证文件：企业（公司）营业执照、组织机构代码证、税务登记证；</li>
              <li>3、贷款卡及银行信贷征信报告书；</li>
              <li>4、近三年年度审计报告、财务报表、税务登记证及纳税证明；</li>
              <li>5、企业（公司）相关简介；</li>
              <li>6、抵押、质押、信用、担保等借款方式凭证；</li>
              <li>7、公司主要经营者个人资产证明（如房产证、购房合同、行驶证复印件、 股票清单等）。</li>
            </ul>
          </div>
        </div>
      </div><!-- gr_dl -->
    <!--   <div class="footer">
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