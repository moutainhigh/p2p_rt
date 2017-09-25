<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/Jane-css.css"/>
    
    <script type="text/javascript" src="${frontPath }/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${frontPath }/jS/jquery.SuperSlide.2.1.js"></script>
	<script type="text/javascript" src="${frontPath }/js/common.js"></script>
    <script type="text/javascript" src="${frontPath }/js/cebianlan.js"></script>
    <script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
<style type="text/css">
.on{
background-color:#ee2121;
color:white;
}
</style>
</head>
<body>
<!--头部-->
<jsp:include page="/top.do"></jsp:include>
<div class="about-banner"></div>
<!--广告--><!--内容-->
<!--最新公告-->
    <div class="J-helpcenter jmt30 jmb30">
     <div class="J-hc-con nav-con clearfix">
      <div class="J-hcc-nav jfl">
       <h3>帮助中心</h3>
       <ul>
         <li><a href="${path }/help">产品介绍</a></li>
        <%--  <li><a href="${path }/content/mcjs.do">名词解释</a></li> --%>
         <li><a href="${path }/content/wzzf.html">网站资费</a></li>
      <%--    <li><a href="${path }/content/zcfg.do" class="select">政策法规</a></li>
         <li><a href="${path }/content/ysbh.do">隐私保护</a></li> --%>
       </ul>
      </div>
      <div class="J-hcc-con jfl jboxsize">
       <h1>政策法规</h1>
       <div class="J-hccc-nr J-hc-regulation">
        <ul>
         <li style="padding-top:0;">
          <h5>关于投资人及借款人双方民间借贷的合法性<em>明确合法</em></h5>
          <p>根据《合同法》第196条规定"借款合同是借款人向贷款人借款，到期返还借款并支付利息的合同"，《合同法》允许普通民事主体之间发生借贷关系，并允许出借方到期可以收回本金和符合法律规定的利息。
</p>
         </li>
          <li>
          <h5>关于浩茗金融提供居间撮合服务的合法性<em>明确合法</em></h5>
          <p>根据《合同法》第23章关于"居间合同"的规定，特别是第424条规定的"居间合同是居间人向委托人报告订立合同的机会或者提供订立合同的媒介服务，委托人支付报酬的合同"，浩茗金融为民间借贷提供撮合借贷双方形成借贷关系的居间服务有着明确的法律基础。
</p>
         </li>
         <li>
          <h5>关于电子合同的合法性及可执行性 <em>现行法律明确确定了合同效力</em></h5>
          <p>根据《电子签名法》的规定，民事活动中的合同或者其他文件、单证等文书，当事人可以约定使用电子签名、数据电文，不能因为合同采用电子签名、数据电文就否定其法律效力。同时，《电子签名法》中还规定，可靠的电子签名与手写签名或者盖章具有同等的法律效力。明确肯定了符合条件的电子签名与手写签名或盖章具有同等的效力。
</p>
         </li>
         <li>
          <h5>关于投资人在浩茗金融获得的出借理财收益的合法性 <em>为合法收益,受到法律保护</em></h5>
          <p>根据最高人民法院《关于人民法院审理借贷案件的若干意见》第6条："民间借贷的利率可以适当高于银行的利率，各地人民法院可以根据本地区的实际情况具体掌握，但最高不得超过银行同类贷款利率的四倍，（包含利率本款）。超出此限度的，超出部分的利息不予保护。"当期人民银行一年期基准贷款利率为6.00%，则当期基准贷款利率的四倍为24.00%，浩茗金融的出借理财收益低于24.00%，为合法利息收益，受到法律保护。
</p>
         </li>
         
        </ul>
       </div>
       <!---J-hccc-nr--->
      </div>
     </div>
    </div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>
</html>
