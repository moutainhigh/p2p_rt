<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/page4jifen.js"></script>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	
<div class="J-Integralmall">
  <div class="J-Integralmall-con nav-con">
   <div class="J-Imc-top clearfix">
    <div class="J-Imc-top-pic jfl jboxsize">
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
    <div class="J-Imc-top-condi jfl jboxsize">
     <ul id="reqCreditUl">
      <li class="J-Imc-select" id="reqCreditall" onclick="sort('all','reqCredit')"  style="cursor: pointer;">不限积分</li>
      <li id="reqCredit1"  onclick="sort(1,'reqCredit')"  style="cursor: pointer;">0-1000</li>
      <li id="reqCredit15" onclick="sort(15,'reqCredit')"  style="cursor: pointer;">1000-2000</li>
      <li id="reqCredit510" onclick="sort(510,'reqCredit')" style="cursor: pointer;">2000-3000</li>
      <li id="reqCredit1050"  onclick="sort(1050,'reqCredit')" style="cursor: pointer;">3000-5000</li>
      <li id="reqCredit50100" onclick="sort(50100,'reqCredit')"  style="cursor: pointer;">5000-10000</li>
      <li  id="reqCredit100"  onclick="sort(100,'reqCredit')" style="cursor: pointer;">10000-20000</li>
      <li id="reqCredit200"  onclick="sort(200,'reqCredit')" style="cursor: pointer;">20000以上</li>
     </ul>
      <ul id="categoryUl">
      <li class="J-Imc-select" id="categoryall" onclick="sort('all','category')" style="cursor: pointer;">全部商品</li>
       <c:forEach items="${categorys }" var="category">
       	<li id="category${category.categoryCode }" onclick="sort('${category.categoryCode }','category')"   style="cursor: pointer;">
                            
                                ${category.categoryName }

                        </li> 
       </c:forEach>
     </ul>
    </div>
   </div>
   <!---J-Imc-top end--->
   <div class="J-Imc-con jboxsize " id="shop_store">
    <ul  >
     <li>
      <div class="J-Imcc-product-img"><img src="${frontPath }/images/J-jifen-01.png"/></div>
      <dl>
       <dt class="text-overflow">佳能5D Mark III 单反套机</dt>
       <dd>
        <div class="J-Imcc-wen">
         <p>积分：<span>85000</span></p>
         <p>库存：100</p> 
        </div>
        <a href="#">我要兑换</a>
       </dd>
      </dl>
     </li>
     <li>
      <div class="J-Imcc-product-img"><img src="${frontPath }/images/J-jifen-02.png"/></div>
      <dl>
       <dt class="text-overflow">佳能5D Mark III 单反套机</dt>
       <dd>
        <div class="J-Imcc-wen">
         <p>积分：<span>85000</span></p>
         <p>库存：100</p> 
        </div>
        <a href="#">我要兑换</a>
       </dd>
      </dl>
     </li>
     <li style="margin-right:0;">
      <div class="J-Imcc-product-img"><img src="${frontPath }/images/J-jifen-03.png"/></div>
      <dl>
       <dt class="text-overflow">佳能5D Mark III 单反套机</dt>
       <dd>
        <div class="J-Imcc-wen">
         <p>积分：<span>85000</span></p>
         <p>库存：100</p> 
        </div>
        <a href="#">我要兑换</a>
       </dd>
      </dl>
     </li>
    </ul>
    
    
    
   </div>
   
   
  </div>
 </div>
	
	
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	
	
	$(function () {
		
		var data = {};
		data.ctCodeArrays = 'all';
		data.reqCreditArrays = 'all';
		//加载商品列表
		getPageFrom4JiFen(data, "${path }/shop/getShopStore.do", getShop, null,"shop_store");
		
		
		initArray();
	}); 
	
	function getShop(data, index) {
		var line = '';
		
		
		if((index + 1) % 3 == 0) {
			line += '<li style="margin-right:0;">';
		} else {
			line += '<li>';
		}
		
		
		
		//line+='<div class="J-Imcc-product-img"><img src="${path }'+data.logPath+'" alt="" width="192px;" height="203px;"/><p title="'+data.gName+'">'
		//	+getTitle(data.gName, 13)+'</p><h5>积分：<strong>'+addCommas(data.reqCredit)+'</strong></h5><a href="${path}/shop/toShopDetail/' + data.id + '.do" class="sl_i">我要兑换</a></div></li>'
		
		line+='<div class="J-Imcc-product-img"><img src="${path }'+data.logPath+'" width="320px;" height="210px;"/></div><dl><dt class="text-overflow" title="'+data.gName+'">'
			+getTitle(data.gName, 21)+'</dt><dd><div class="J-Imcc-wen"><p>积分：<span>'+addCommas(data.reqCredit)+'</span></p><p>库存：'
			+(data.totalCount-data.sellCount)+'</p> </div><a href="javascript:;" onclick="submit('+data.id+','+data.reqCredit+')">我要兑换</a></dd></dl></li>';
		
		
        return line;
	}
	
	
	var ctCodeArray = new Array();//商品分类
	var reqCreditArray = new Array();//积分范围
	
	//数组初始化
	function initArray(obj) {
		ctCodeArray.length = 0;
		reqCreditArray.length = 0;
		
		ctCodeArray.push("all");
		reqCreditArray.push("all");
		
	}
	
	
	function sort(obj, type) {
		
		var value = obj;
		var type = type;

		switch (type) {
		case 'category':
			$("#categoryUl").find("li").removeClass("J-Imc-select");
			
			$("#category"+value).addClass("J-Imc-select");
			
			ctCodeArray.length = 0;
			ctCodeArray.push(value);
			
			break;
		case 'reqCredit':
			$("#reqCreditUl").find("li").removeClass("J-Imc-select");
			$("#reqCredit"+value).addClass("J-Imc-select");
			reqCreditArray.length = 0;
			reqCreditArray.push(value);
			break;
		
		}
		searchByParams();
	

	}
	function searchByParams() {
		var data = {};
		data.ctCodeArrays = ctCodeArray.unique2().toString();
		data.reqCreditArrays = reqCreditArray.unique2().toString();
		
		getPageFrom4JiFen(data, "${path }/shop/getShopStore.do", getShop, null,"shop_store");

	}
	
	function submit(id,reqCredit){
		var url="${path }/shop/cashGoods.do";
		var gid=id;
		var num=1;
		var sumCredit=reqCredit*num;
		var creditValue="${creditValueUsable}";
		
		if(num != 0 && num !=null){
			if(sumCredit>creditValue){
				alertc("积分不足！");
			}else{
				$.post(url,{goodsId:gid,number:num},function(data){
					if(data.code=="101"){
						alertc(data.msg);
					}
					if(data.code=="102"){
						alertc(data.msg);
					}
					if(data.code=="103"){
						alertc(data.msg);
					}
					if(data.code=="104"){
						alertc(data.msg);
					}
					if(data.code=="105"){
						alertc(data.msg);
					}
					if(data.code=="106"){
						alertc(data.msg);
					}
					if(data.code=="107"){
						alertc(data.msg);
					}
					if(data.code=="108"){
						alertc(data.msg);
					}
					if(data.code=="200"){
						alertc(data.msg,function () {
							window.location.reload();
						});
					}
				});
			}
		}else{
			alert("请选择正确的兑换数量！");
		}
	}
	
	function getTitle(title, maxLen) {

		if (title.length > maxLen + 3) {
			title = title.substr(0, maxLen) + "...";
		}
		return title;
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