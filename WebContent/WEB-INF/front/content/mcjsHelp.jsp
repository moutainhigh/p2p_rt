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
        <%--  <li><a href="${path }/content/mcjs.do"  class="select">名词解释</a></li> --%>
         <li><a href="${path }/content/wzzf.html">网站资费</a></li>
       <%--   <li><a href="${path }/content/zcfg.do">政策法规</a></li>
         <li><a href="${path }/content/ysbh.do">隐私保护</a></li> --%>
       </ul>
      </div>
      <div class="J-hcc-con jfl jboxsize">
       <h1>名词解释</h1>
       <div class="J-hccc-nr J-nounexplan">
        <p><em>网贷：</em>即P2P网络借款，指个体与个体之间个体与企业之间通过网站实现借贷，中间网站称为网贷平台，简称平台。</p>
        <p><em>投资人:</em>也叫出借人，指在平台上进行投资的用户，就是投资人。</p>
        <p><em>借款人：</em>是指有资金需求，在平台上借款的个人或者企业。</p>
        <p><em>线上充值:</em>利用第三方支付平台进行充值，将资金划入网络借贷平台的对公账户或者个人在第三方支付的虚拟账户。</p>
        <p><em>线下充值：</em>直接将资金通过银行转入平台账户(一般是直接转入法人账户)，然后联系平台财务手动入账。碰到这种充值方式的平台请尽量远离，没有第三方支付作为中介，对于平台的道德风险很难把握。</p>
        <p><em>提现：</em>投资者向平台提出申请，将个人虚拟账户上的资金转入到投资者银行卡上。提现出来的才是真金白银。</p>
        <p><em>自融：</em>所谓自融就是有实体企业的人来线上开一个网贷平台，从网上融到资金，用于自己企业或者关联企业使用，就是给自己用!这样的平台，从法律上踩了非法集资的红线，应该远离。</p>
        <p><em>资金站岗：</em>资金站岗也称为“当哨兵”，是指投资人投资某平台某借款标迟迟不能满标，或者充值后一直没有标的可投，或者项目很少抢不到标，让资金闲置的情况。</p>
        <p><em>债权转让：</em>投资人将待收还款转让给其他人，提前取出本金，利息收益也一同转让。转让债权的标成为净值标。P2P平台现在都有债权转让功能，人气活跃的平台转让速度较快。一般转让有手续费。</p>
        <p><em>标的（biāo dì）：</em>又称“借款标的”、“借款项目”，经常简称为“标”。指P2P借贷平台发布的借款信息，内容一般包括：标题、借款人、借款总额、借款用途、借款期限、还款方式、利率、招标期限、逾期（违约）时的保障措施等。</p>
        <p><em>投标：</em>指投资人选择处于招标期限之内、未满标的借款标的，表达向该标的的借款人出借资金的意愿和数额。投资人投标后，所投资金将被暂时锁定，但并不直接转给借款人，一般在满标之后才会真正划转给借款人。这种投标方式因为需要投资人自己选择标的、自己设置投标金额，因此又称手工投标。</p>
        <p><em>自动投标：</em>懒人改变世界，这就是给懒鬼们准备的。在某些网站你可以自己设置好投标条件和投标金额，之后网站会自动帮你投标，但是，有的时候可能会投到风险大的标的上哦，这点要注意了。
</p>
        <p><em>满标：</em>就是标的投资额满的状态，下一步就是借款人开始回款了。</p>
        <p><em>放款：</em>这就是网贷平台或第三方托管平台把投资人的资金汇集到一起转给对应的借款人。</p>
        <p><em>逾期：</em>其实完全不想解释这个的，这就是字面意思啊！就是借款人到期了没还钱，这就是逾期！此外，逾期的标有可能就变成跑路了哦~~~</p>
       </div>
       <!---J-hccc-nr--->
      </div>
     </div>
    </div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>
</html>
