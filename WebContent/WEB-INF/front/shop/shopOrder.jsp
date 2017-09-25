<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/page4info.js"></script>
<script type="text/javascript" src="${frontPath }/js/pagerFunction.js"></script>
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
        <a href="${path }/usrOrder/myOrderList.html"><li  class="J-Imccrt-select">我的兑换</li></a>
        <a href="${path }/member/memberIntegral.html"><li style="border-right:none;">积分规则</li></a>
       </ol>
      </div>
      <div class="J-Imxc-wen">
       
        <div class="J-Imxc-wen-r" >
        <table cellpadding="0" cellspacing="0" class="J-Imxcw-table">
         <tr>
          <th>商品 </th>
          <th>数量 </th>
          <th>积分</th>
          <th>地址</th>
          <th>日期</th>
          <th>状态</th>
          <th>操作</th>
         </tr>
         
         <tbody id="orderList"></tbody>
         
         <tr style="text-align:center;">
					<td colspan="11" class="page" id="pagerDiv">
					</td></tr>
        </table>
        </div>
        <div class="J-Imxc-wen-r" style="display:none;">
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
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	$(function () {
		 var data={"tojsp":1};
		 pager_container = "pagerDiv";
		 pager_function = usrInfoPager;
		 getPageFrom(data,"${path }/usrOrder/getMyOrderList.do",generateMyOrderListTable,"orderList");	
		
	});
	
	function generateMyOrderListTable(data){
		
		var orderListHtml='';
		
		var orderStatus='';
		var operate='';
		if(data.status==1){
			orderStatus='未发货';
			
			operate='<td><a href="javascript:;" onclick="cancelOrder('+data.id+')">取消</a></td>';
			
		}else if(data.status==2){
			orderStatus='已发货';
			operate='<td>--</td>';
		}else if(data.status==3){
			orderStatus='用户取消';
			operate='<td>--</td>';
		}else if(data.status==4){
			orderStatus='平台取消';
			operate='<td>--</td>';
		}
		
		
		var gongsi='';
		var danhao='';
		if(data.misc1=='' || data.misc1==undefined){
			gongsi='--';
			
		}else{
			gongsi=data.misc1;
		}
		if(data.misc2=='' || data.misc2==undefined){
			danhao='--';
			
		}else{
			danhao=data.misc2;
		}
		
		
		orderListHtml='<tr><td class="J-Imxcw-img"><img src="${path }/'
					+data.logPath+'"/><p>'
					+data.goodsName+'</p></td><td>'
					+data.goodsCount+'</td><td class="J-t-color">'
					+data.requireCredit+'</td><td>'
					+data.recvAddress+'</td><td>'
					+toDate(data.createTime)+'</td><td>'
					+orderStatus+'</td>'
					+operate+'</tr>';
		return orderListHtml;
	}
	
	function reloadPage(){
		window.location.reload();
	}
	function cancelOrder(id){
		$.ajax({
			type : "POST",
			data:{'orderId':id},
            url:'${path }/usrOrder/cancelOrder.do',
            async:false,
            success: function(data) {
            	if(data.code == '200'){
	            //	initCaptcha();
					alertc("取消订单成功",reloadPage);
            	}else{
    				alertc("取消订单失败。");
            	}
            }
        });  
	}
	
	function getTitle(title, maxLen) {

		if (title.length > maxLen + 3) {
			title = title.substr(0, maxLen) + "...";
		}
		return title;
	}
	
	function toDate(obj, format) {
		var date = new Date();
		if (typeof (obj) != "undefined") {
			date.setTime(obj.time);
			date.setHours(obj.hours);
			date.setMinutes(obj.minutes);
			date.setSeconds(obj.seconds);
		}
		if (format == undefined || format == null) {
			format = "yyyy-MM-dd hh:mm:ss";
		}
		return date.format(format);
	}
	/**
	 * 数字加逗号
	 * @param nStr
	 * @returns
	 */
	function addCommas(nStr) {
		nStr += '';
		x = nStr.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	}
</script>
</html>