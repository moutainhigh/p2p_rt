<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
<link type="text/css" rel="stylesheet"
	href="${frontPath }/css/invest.css" />
	<style type="text/css">
	.on{
	background: none repeat scroll 0 0 #92979b;
	color: #fff;
	}
	</style>
	</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
		<input type="hidden" name="publickey" id="publickey"
			value="${publickey}" />
	<div id="content" style="background-color:#f2f2f2;" ><!--实时财务-->
    <div id="hkct-sscw" style="height:125px;">
    <div class="xt-cw" style="margin-top:8px;"></div>
	<div class="hkct-k" style="background-color:#ee2121; height:90px; ">
 			 <ul style="padding-top:10px;">
				<li style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">交易总额</p><p class="hkct-sscw-p2" id="totleMoney"></p></li>
            	<li  style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">待收总额</p><p class="hkct-sscw-p2" id="allInterestAndRewardStr"></p></li>
            	<li  style="line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">今日回款总额</p><p class="hkct-sscw-p2" id="repossessed"></p></li>
            	<li style="border:none; line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">注册人数</p><p class="hkct-sscw-p2" id="userNum"></p></li>
            </ul>
    </div>
    </div>
  <div id="hkct-tz" style="background-color:#f2f2f2;">
<div class="hkct-k" style="border:1px solid #cccccc; overflow:hidden; margin-bottom:30px;">
       	<div class="siteWidth" style="background:#fff;">
	<div id="search" style="height:83px;">
		<div class="search_lf">
			<div style="overflow:hidden;">
            	<p style="float:left; display:block; color:#ee2121;">筛选投资项目</p>
            	
            </div>
			<ul class="sha1" style="margin-top:10px;">
				<li class="li1">
                	产品类型
				</li>
              <li class="a2"><a href="${path }/borrow/dingHuoTong.html">定存宝</a></li> 
				<li><a href="${path }/invest">月息通</a></li>
				<li><a href="${path }/creditassignment">转让区</a></li>					
            </ul>
			  	<!-- <ul class="sha3" style="height:20px;">
					<li class="hidtype" style="height:20px;"><input type="hidden"  name="type" size="20" value="{$magic.request.type}"/></li>
				</ul> -->
			</div>
  </div>
</div>
<div id="hkct-tz">
<div class="hkct-k">
			<!-- <hr style="width:1000px;color:#fcfcfc;"> -->
        	<ul id="hkct-tz-nav" style="border-bottom:1px solid #ccc; ">
            </ul>  
            <ul id="hkct-tz-list">
    </div>
            </ul>
        </div>
    </div>
</div>
  </div>
</div>
<!--尾部-->

<jsp:include page="/foot.do"></jsp:include>
<%@ include file="../onlineSupport.jsp" %>
</body>
<script type="text/javascript">
var BORROW_ALL_STATUS = {};
<c:forEach var="item" items="${BORROW_ALL_STATUS}">
BORROW_ALL_STATUS["${item.key}"] = "${item.value}";
</c:forEach>
var checkNum=0;

function getBorrowInfo(data){
	
	var htmlStr='';
	
	var titlelength=data.borrow_title;
	var title="";
	if(titlelength.length>6){
		title = titlelength.substring(0, 6)+"..."; 
	}else{
		title=titlelength;
	}
	var relatedId =data.erelatedId; 
	var id = data.eid ;
	var nowTime = toDate(data.nowTime);
	var allow_tender_time = toDate(data.allow_tender_time);
	var publish_datetime = toDate(data.publish_datetime);
	
	
	var buttonHtml = '';
	if (data.borrow_status == 2) {
		if (isStartEndDate(nowTime, allow_tender_time)
				&& isStartEndDate(publish_datetime, nowTime)) {
			buttonHtml = '<a href="${path }/borrow/toDingHuoTongInfo/'+id+'/'+relatedId+'.do"><span class="hover"><div class=\"ljtb\" style=\"margin-right: 20px;\">加入</div></span></a>';
		} else {
			if (!isStartEndDate(nowTime, allow_tender_time)) {
				buttonHtml = '<a href="javascript:;"><span><div class=\"ljtb\" style=\"margin-right: 20px;\">已结束</div></span></a>';
			} else {
				buttonHtml = '<a href="javascript:;"><span><div class=\"ljtb\" style=\"margin-right: 20px;\">待开始</div></span></a>';
			}
		}
	} else {
		buttonHtml = '<a href="javascript:;"><span class="state"><div class=\"ljtb\" style=\"margin-right: 20px;\">'
				+ BORROW_ALL_STATUS[data.borrow_status] + '</div></span></a>';
	}

	var isDayStr = "";
	if (data.is_day == 2) {
		isDayStr = data.borrow_time_limit + "个月";
	} else if (data.is_day == 1) {
		isDayStr = data.borrow_time_limit + "天";
	}
	var titlelength=data.borrow_title;
	var title="";
	if(titlelength.length>6){
		title = titlelength.substring(0, 6)+"..."; 
	}else{
		title=titlelength;
	}
	htmlStr='<li style=\"border-top:none;\">'
			+'<div class=\"hkct-tz-list-01 fl\" style=\"margin-left:15px;\">'
			+'<h3><a alt='+titlelength+' title='+titlelength+' href="${path }/borrow/toDingHuoTongInfo/'+id+'/'+relatedId+'.do">'+title+'</a><img alt="定活通" src="${frontPath}/images/ny/ding.jpg" width="19" height="19" style="margin-top:-5px;"></h3>'
			+'<p>借款金额：<span class=\"span_01\">￥'+addCommas(data.borrow_sum.toFixed(2))+'</span>元</p><p>剩余金额：<span class=\"span_01\">￥'+addCommas((data.borrow_sum - data.tender_sum).toFixed(2))+'</span>元</p>'
			+'</div>'
			+'<div class=\"hkct-tz-list-02 fl\"><p style=\"float:left;\">进度：<span class=\"span_01\"></span></p><div class=\"jdt-k\" style=\"margin-left:40px\">'
			+'<div class=\"jdt-hs\" style=\"width:'+data.tenderProgressRate.toFixed(2)+'%\">'+data.tenderProgressRate.toFixed(2)+'%</div></div>'+'<p></p><p style=\"clear:both;\">还款方式：每月还息到期还本</p>'
			+'</div>' 
			 +'<div class=\"hkct-tz-list-03 fl\"><p>年利率：'+data.annual_interest_rate.toFixed(2)+'%</p><p>奖励：无'
			+'</p></div>'
			+'<div class=\"hkct-tz-list-03 fl\"><p>起投额度：￥'+data.min_amount+'起<p>借款期限：'+isDayStr
			+'</p></div>'
			+'<div class=\"hkct-tz-list-05 fr\">'+buttonHtml
			+'</div>'  
			+'</li>';
	return htmlStr;

	
}

	$(function() {
		var data = {};
		nowDate = $
		{
			nowDate
		}
		; 
		getPageFrom(data, "${path }/borrow/getDingHuoTongJson.do",
				getBorrowInfo, "hkct-tz-list");
		getStatistics();
		changeTopHover(2);
	})
	
</script>
</html>
