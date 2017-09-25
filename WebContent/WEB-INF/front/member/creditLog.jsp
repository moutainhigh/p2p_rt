<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${showTitle }-积分记录</title>

<script type="text/javascript" src="${frontPath }/js/page4info.js"></script>
<script charset="utf-8" src="${frontPath }/js/pagerFunction.js"></script> 
<script type="text/javascript" src="${frontPath }/js/scroll.js"></script>
 <script type="text/javascript">
$(function(){
	$('.myscroll').myScroll({
		speed: 60, //数值越大，速度越慢
		rowHeight: 40 //li的高度
	});
});
</script>
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
         <a href="${path }/member/creditLog.html"><li class="J-Imccrt-select">积分明细</li></a>
        <a href="${path }/usrOrder/myOrderList.html"><li  >我的兑换</li></a>
        <a href="${path }/member/memberIntegral.html"><li style="border-right:none;">积分规则</li></a>
       </ol>
      </div>
      <div class="J-Imxc-wen">
       <div class="J-Imxc-wen-r">
        <table cellpadding="0" cellspacing="0">
         <tr>
          <th>积分类型</th>
          <th>变动类型 </th>
          <th>操作积分值</th>
          <th>操作时间</th>
          <th>备注</th>
         </tr>
         
        <tbody id="datatables"></tbody>
         <tr style="text-align:center;">
			<td colspan="11" class="page" id="pagerDiv"></td>
		 </tr>
        </table>
        </div>
      </div>
     </div>
   </div>
 </div>
	<jsp:include page="/foot.do"></jsp:include>	
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		
		pager_container = "pagerDiv";
		 pager_function = usrInfoPager;
		 var data={"tojsp":1};
		 getPageFrom(data,"${path }/member/creditLogPage.do",generateInvestRecordsTable,"datatables");
		 
	});
	
	function toDate(obj,format){
		 var date = new Date();
		 if(typeof(obj)!="undefined"){
		 date.setTime(obj.time);
		 date.setHours(obj.hours);
		 date.setMinutes(obj.minutes);
		 date.setSeconds(obj.seconds);}
		 if(format == undefined || format == null){
			 format="yyyy-MM-dd hh:mm:ss";
		 }
		 return date.format(format); 
		}
	
	function subForm(){
		
		 var data={
		 };
		 getPageFrom(data,"${path }/member/creditLogPage.do",generateInvestRecordsTable,"datatables");
	}
	
	//遍历类型
	var tradeCode = {};
	<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}"> 
		tradeCode["${item.key}"] = "${item.value}";
	</c:forEach>
	
	//拼成表格
	function generateInvestRecordsTable(data){
		var jiaOrJian='';
		if(data.creditOperateType==1){
			jiaOrJian='增加';
		}else{
			jiaOrJian='减少';
		}
		var htmlStr = '<tr class="J_moneyminfxi">';
	     htmlStr+="<td>"+data.creditType.creditName+"</td>"+
	     "<td>"+jiaOrJian+"</td>"+
	     "<td>"+data.creditOperateValue+"</td>"+
	     "<td>"+toDate(data.creditOperateDatetime)+"</td>"+ 
	     "<td>"+data.creditOperateRemark+"积分</td>";
	     htmlStr+="</tr>";
	    return htmlStr;
	};
</script>
</html>
