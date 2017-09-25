<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${showKeywords}" />
<meta name="description" content="${description}" />

<title>${showTitle }-我的账户</title>
<!--[if lte IE 6]>
<style type="text/css">
body { behavior:url("js/csshover.htc"); }
</style>
<![endif]-->
<script type="text/javascript" src="${frontPath }/js/organictabs.jquery.js"></script>
<script type="text/javascript">
$(function() {
	// 调用插件
	/* $("#example-one").organicTabs();
	$("#example-two").organicTabs({
		"speed": 100,
	}); */
});
</script>

</head>

<body>
<div id="container">
	<!---header start--->
	<jsp:include page="/top.do"></jsp:include>
	<!---header end--->

<div class="acc-w1140 m50">
	<!--left-->
	<input type="hidden" value="redLeft" id="curTitle">
    <jsp:include page="/account/userLeft.do"></jsp:include>
    
    <!--right-->
    <div class="acc-right">    	
        <!--我的红包-->
        <div class="por-intro" style="height:auto;">
            <div>
            	<p class="por-tit">我的红包<a href="javascript:;" onclick="javascript:alertc('尽请期待...');" style="clear:none; margin-left:580px;"><img src="${frontPath }/image/Account/more_hb.png" width="82" height="25" alt="" style="vertical-align:middle;" /></a></p>
                <div class="note">
               	  	<p class="c3b589c">红包使用规则：</p>
                    <p>1、红包打开之后，可以直接提现</p>
                    <p>2、打开红包有获得红包金额翻倍机会。</p>
                </div>                            
          	</div>  
        </div>        
        <!--我的红包-->
         <p class="por-tit" style="border:none;">我的红包<span class="cff8738" style="font-size:12px;margin-left:580px;" ><input name="setAutoOpened" type="checkbox" style=" margin-right:5px;" title=""/>自动打开红包</span></p>   
          	<!--数据-->
            <div class="content-date">
                <div>
                    <ul class="content-th">
                        <li class="co-1" style="color:#999;">金额（￥）</li>
                        <li class="co-2">红包来源</li>
                        <li class="co-3">状态</li>
                        <li class="co-4">有效期</li>
                        <li class="co-5" style="color:#999;">操作</li>
                        <li class="co-6">使用条件</li>
                    </ul>
                </div>
                <div id="dataList">
                <!-- <ul class="content-list">
                    <li class="co-1">48</li>
                    <li class="co-2">新手红包</li>
                    <li class="co-3">未打开</li>              
                    <li class="co-4">2014.11.2至2014.12.2</li>
                    <li class="co-5"><a href="#">打开</a></li>
                    <li class="co-6">xxxxxxxxxxxxxxxxxxxxxxxxx</li>
                </ul> -->
               
                </div>
            </div>  
            <!--数据END-->       
   
</div>  

  
</div>

<!--footer start-->
<jsp:include page="/foot.do"></jsp:include>

<!-- <object id="topFlash" name="topFlash" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="686" height="101">
     <param name="movie" value="_top.swf" />
     <param name="quality" value="high" />
     <param name="allowScriptAccess" value="always" />
     <embed allowScriptAccess="always" src="_top.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="686" height="101"></embed>
</object> -->
<object id="topFlash" name="topFlash" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="686" height="101">
     <param name="movie" value="${frontPath }/swf/red.swf" />
     <param name="quality" value="high" />
     <param name="allowScriptAccess" value="always" />
     <embed allowScriptAccess="always" src="${frontPath }/swf/red.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="686" height="101"></embed>
</object>
<!---footer end--->
</body>
</html>
<script type="text/javascript">
var openLinkId = "openLink_";
	$(function() {
		$("#topFlash").hide();
		<c:if test="${not empty openAutoRedFlag }">
		$("[name='setAutoOpened']").attr("checked",true);
		</c:if>
		 var data={"cxType":1};
		 getPageTable(data,"${path }/redenvelopes/getPagedList.do",generateInvestRecordsTable,"dataList",1);
	
		 $("input[name='setAutoOpened']").bind("click",function(){
			 var checked=$(this).attr("checked");
		     if("checked" == checked){ //设置自动打开
		    	  confirmc("开启自动打开红包，您将失去红包翻倍的机会。<br><br> 确认开启？",function(){
		    		  setAuto(2);
		    	  },function(){
		    		  $("input[name='setAutoOpened']").attr("checked",false);
		    	  });
		     }else{ //取消自动打开
		    	 setAuto(1);
		     }
		 });
		 
		 $('[title]').poshytip({className: 'tip-violet',
				bgImageFrameSize: 9});
	});
	function setAuto(type){
		$.ajax({
			dataType : 'json',
			url : "${path}/redenvelopes/setAutoFlag.do",
			data : {"flag":type},
			type : 'POST',
			success : function(data) {
				alertc(data.msg,function(){
					document.location.reload();
				});
				
			},
			error : function() {
				alertc("操作失败",function(){
					document.location.reload();
				});
			}
		});
	}

	function subForm(){
		var cxType=$("#cxType").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		 var data={"cxType":1,
				 "beginTime":beginTime,
				 "endTime":endTime
		 };
		 getPageTable(data,"${path }/account/cashValuePage.do",generateInvestRecordsTable,"investRecordsTable",5);
	}

	//遍历类型
	var tradeCode = {};
	<c:forEach var="item" items="${LOG_ALL_TRADE_CODE}"> 
		tradeCode["${item.key}"] = "${item.value}";
	</c:forEach>
	
	
	//拼成表格
	function generateInvestRecordsTable(data){
		/**
		<ul class="content-list">
                    <li class="co-1">48</li>
                    <li class="co-2 ">新手红包</li>
                    <li class="co-3">未打开</li>              
                    <li class="co-4">2014.11.2至2014.12.2</li>
                    <li class="co-5"><a href="#">打开</a></li>
                    <li class="co-6">xxxxxxxxxxxxxxxxxxxxxxxxx</li>
                </ul>
		
		*/
		//红包操作共有5种：打开、继续投资、已登陆n天、已过期、已打开
		var dakai = "&nbsp;";
		var fillin = data.fillLimitParam;
		var amount = data.amount;
		
	//	var showTitle = "";//
		if(data.status == 1){ //未打开
			dakai = "<a cur=\""+data.id+"\" onclick=\"openr(this)\" href=\"javascript:;\">打开</a>";
		}else if(data.status == 2){ //为满足条件
			
			if(data.openLimitType == 1 || data.openLimitType == 4){//投标
				dakai = "<a cur=\""+data.id+"\" onclick=\"gotoTz(this)\" href=\"javascript:;\">继续投资</a>";
			}else if(data.openLimitType == 2){//累计登录
				dakai = "已登录"+fillin+"天";
			}else{ //连续
				dakai = "已登录"+fillin+"天";
			}
		}else if(data.status == 3){ //已打开
			dakai = "<font title='您的红包于"+data.viewUpdateDate+"打开'>已打开</font>";
			amount = data.realAmount;
		}else{ //过期
			dakai = "已过期";
		}
	var nameTitle = "";
	if(data.name.length > 5){
		nameTitle = " title='"+data.name+"' ";
	}
		
		var htmlStr = "<ul class=\"content-list\">"+
				      "  <li class=\"co-1\" >"+addCommas(amount.toFixed(2))+"</li>"+
				      "  <li class=\"co-2\" "+nameTitle+">"+data.name+"</li>"+
				      "  <li class=\"co-3\">"+data.viewStatus+"</li>"+       
				      "  <li class=\"co-4\">"+data.viewPriodStartTime+"至"+data.viewPriodEndTime+"</li>"+
				      "  <li class=\"co-5\">"+dakai+"</li>"+
				      "  <li class=\"co-6\">"+data.remark+"</li>"+
				    "</ul>";
	    return htmlStr;
	};
	function gotoTz(obj){
		$("#topFlash").show();
		return;
		window.location.href = "${path}/borrow/toOptimization.do";
	}
	function openr(obj){
		var id = $(obj).attr("cur");
		$.ajax({
			dataType : 'json',
			url : "${path}/redenvelopes/open.do",
			data : {"id":id},
			type : 'POST',
			success : function(data) {
				alertc(data.msg,function(){
					document.location.reload();
				});
				
			},
			error : function() {
				alertc("操作失败",function(){
					document.location.reload();
				});
			}
		});
	}
</script>