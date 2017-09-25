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
<title>${showTitle }-新手必读</title>
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
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page" style="padding-bottom: 5.4rem;" >
     <!--  <div class="header">
        <a class="back_arr" href="javascript:;" onclick="history.back(-1)"></a>
        <h1>新手必读</h1>
      </div>header -->

      <div class="xxz_con">   
        <div class="xq_con" style="position:relative;">
        <div class="list">
          <a href="javascript:void(0);" class="">
              <h5 class="xxz_con2"><span class="txt_l"><i><img class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" ></i>小诸葛是什么？为什么选择小诸葛？</span></h5> 
            <div class="list_txt">
              <h3><img  class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" > 小诸葛提供的理财产品</h3>
      <h4>诸葛计划</h4>
      <p>经过平台严格风控的投资项目，包括企业经营贷及多种形式的贷款投资产品；产品类型分新手专享、争分夺秒、小诸葛理财计划1月标、小诸葛理财计划3月标、小诸葛理财计划6月标、小诸葛理财计划9月标、小诸葛理财计划12月标。</p>
       <h4>私募基金</h4>
      <p>指相对于受中国政府主管部门监管的，向不特定投资人公开发行受益凭证的证券投资基金而言，是一种非公开宣传的，私下向特定投资人募集资金进行的一种集合投资。其方式基本有两种，一是基于签订委托投资合同的契约型集合投资基金，二是基于共同出资入股成立股份公司的公司型集合投资基金。</p>
            </div>
         
          </a>
     
        </div><!-- list -->
         
      </div>
      <div class="xq_con" style="position:relative;">
        <div class="list">
          <a href="javascript:void(0);" class="">
              <h5 class="xxz_con2"><span class="txt_l"><i><img class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" ></i>小诸葛提供的理财产品</span></h5> 
            <div class="list_txt">
              <h3><img  class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" > 小诸葛提供的理财产品</h3>
                <h4>诸葛计划</h4>
                <p>1111</p>
            </div>
          </a>
        </div><!-- list -->
      </div>
       <div class="xq_con" style="position:relative;">
        <div class="list">
          <a href="javascript:void(0);" class="">
              <h5 class="xxz_con2"><span class="txt_l"><i><img class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" ></i>如何保障小诸葛平台上理财产品的资金安全？</span></h5> 
            <div class="list_txt">
              <h3><img  class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" > 小诸葛提供的理财产品</h3>
                <h4>诸葛计划</h4>
                <p>2222</p>
            </div>
          </a>
        </div><!-- list -->
      </div>
       <div class="xq_con" style="position:relative;">
        <div class="list">
          <a href="javascript:void(0);" class="">
              <h5 class="xxz_con2"><span class="txt_l"><i><img class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" ></i>产品出现风险怎么办？</span></h5> 
            <div class="list_txt">
              <h3><img  class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" > 小诸葛提供的理财产品</h3>
                <h4>诸葛计划</h4>
                <p>3333</p>
            </div>
          </a>
        </div><!-- list -->
      </div>
       <div class="xq_con" style="position:relative;">
        <div class="list">
          <a href="javascript:void(0);" class="">
              <h5 class="xxz_con2"><span class="txt_l"><i><img class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" ></i>在小诸葛投资需要费用吗？</span></h5> 
            <div class="list_txt">
              <h3><img  class="xxz_con22" src="${configjscss }/images/icon_xxz1.jpg" > 小诸葛提供的理财产品</h3>
                <h4>诸葛计划</h4>
                <p>4444</p>
            </div>
          </a>
        </div><!-- list -->
      </div>
     
       
      </div><!-- xxz_con -->
      
    </div>

      <script type="text/javascript">
      
          $(".xq_con .list a").click(function(event) {
            $(this).toggleClass('chose');
          });
      
      </script>

    </body>
</html>