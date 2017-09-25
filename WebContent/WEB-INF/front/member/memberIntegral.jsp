<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${showKeywords}" />
<meta name="description" content="${description}" />
<title>${websiteName }</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link href="css/wdzh.css" rel="stylesheet" type="text/css" />
<link href="${cssJsPath }/css/stock-loan.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.menu.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" charset="utf-8">

</head>

<body>
	
	<jsp:include page="/top.do"></jsp:include>
	
		<div class="J-Integral-mx J-Integralmall">
   <div class="J-Imx-con nav-con clearfix">
    <div class="J-Imx-con-L jfl">
     <div class="J-Imc-top-pic jboxsize">
        <dl class="clearfix">
         <c:if test="${not empty sessionScope.FRONT_USER_SESSION.avatarImg}">
						 <dt><img src="${path}${sessionScope.FRONT_USER_SESSION.avatarImg}" id="avatarImg"  /></dt>
					</c:if>
					<c:if test="${empty sessionScope.FRONT_USER_SESSION.avatarImg}">
		            	 <dt><img src="${frontPath }/images/J-jifen-pic.png"/></dt>
		            </c:if>
      
       
       
       <dd>
        <p>${user.userAccount }</p>
        <p>积分：<span><c:if test="${empty creditValueUsable}">
	                    0
	                    </c:if>
	                    <fmt:formatNumber value="${creditValueUsable}" /></span></p>
       </dd>
        </dl>
        <ul class="clearfix">
         <li style="border-right:1px solid #ededed;" class="jboxsize"><a href="${path }/member/creditLog.html">积分明细</a></li>
         <li><a href="${path }/usrOrder/myOrderList.html">我的兑换</a></li>
        </ul>
      </div>
      <div class="J-Imx-conL-wen">
       <h1>大家在兑换</h1>
       <div class="J-Imxcw-div1">
        <div class="myscroll">
          <ul>
          
          <c:forEach items="${pm }" var="shop">
		                    <li>
		                     <c:choose>
		                    <c:when test="${fn:length(shop.userName) > 3 }">
		                    	${fn:substring(shop.userName, 0, 3) }**
		                    </c:when>
		                    <c:otherwise>
		                    	${fn:substring(shop.userName, 0, 1) }**
		                    </c:otherwise>
		                    </c:choose>
		                    <span >兑换了</span>
		                    <c:choose>
		                    <c:when test="${fn:length(shop.userName) > 9 }">
		                    	${fn:substring(shop.goodsName, 0, 9) }...
		                    </c:when>
		                    <c:otherwise>
		                    	${shop.goodsName }
		                    </c:otherwise>
		                    </c:choose>
		                    </li>
	                	</c:forEach>
          
           
          </ul>
        </div>
        <a href="JavaScript:" class="J-down"><!--<img src="images/J-jifen-downbtn.png"/>--></a>
       </div>
      
       
      </div>
     </div><!---J-Imx-con-L--->
     <div class="J-Imx-con-R jfl jboxsize">
      <div class="J-Imxcr-top">
       <ol class="clearfix">
        <a href="${path }/member/creditLog.html"><li >积分明细</li></a>
        <a href="${path }/usrOrder/myOrderList.html"><li  >我的兑换</li></a>
        <a href="${path }/member/memberIntegral.html"><li style="border-right:none;" class="J-Imccrt-select">积分规则</li></a>
       </ol>
       </ol>
      </div>
      <div class="J-Imxc-wen">
       
        
        <div class="J-Imxc-wen-r" >
         <dl>
          <dt>什么是积分、如何获得积分？</dt>
          <dd><i>1、</i><p>积分是指积分商的积分，可以累计增加的积分。在兑换积分商城的物品时，需达到所要求积分数量才可进行兑换。</p>
</dd>
<dd><i>2、</i><p>积分数量越多，通过积分商城可兑换的商品选择就越多。</p></dd>
<dd><i>3、</i><p>目前，您可通过以下1种方式获得积分：投资给力标、信用标、净值标(月标)可获得相应积分（不含秒标）。</p></dd>
<dd><i>4、</i><p>积分获得计算方式为：100元=1个积分。</p></dd>
<dd><i>5、</i><p>积分实际到账时间为投资者所投的标成功放款后为准。</p></dd>
         </dl>
         
         <dl>
          <dt>了解兑换相关规则</dt>
          <dd><i>1、</i><p>在积分商城点击物品旁的点击兑换按钮可进行兑换。
</p>
</dd>
<dd><i>2、</i><p>使用积分可兑换标有积分价格的物品。</p></dd>
<dd><i>3、</i><p>在兑换物品时需达到所要求的积分数量才可进行兑换。</p></dd>
<dd><i>4、</i><p> 兑换时会扣除账户中的积分，若您的积分不足则不能兑换。</p></dd>
<dd><i>5、</i><p>兑换成功后，物品将在7-15个工作日内进行审核。</p></dd>
<dd><i>6、</i><p>点击积分商城“我的兑换”可查看您已兑换成功的信息及发放情况。</p></dd>
         </dl>
         
        </div>
      </div>
     </div>
   </div>
 </div>
	
	
	
	


<!--footer 开始部分-->
	<jsp:include page="/foot.do"></jsp:include>





</body>
</html>
