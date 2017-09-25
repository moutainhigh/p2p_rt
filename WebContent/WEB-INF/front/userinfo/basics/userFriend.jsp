<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }好友管理</title>
<script type="text/javascript" src="${frontPath }/js/redAndFriendpage.js"></script>

</head>
<body>
<jsp:include page="/top.do"></jsp:include>
    <!--/sidebar--> 
    <div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">

		<input type="hidden" value="left12" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
  </div>

  <div class="J-ma-conR yq_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);">邀请好友</a>
    </div>
    <div class="pic"><a href="<c:url value='/content/contentDetails/35/295.do'/>" ><img  src="${frontPath }/images/yq_banner.png" height="89" width="792" alt=""></a></div>
    <div class="yq_code">
      <h4>发送链接邀请好友</h4>
      <div class="txt"><i>您的邀请码：</i><span>${signUserIdNew }</span></div>
      <div class="btn_txt" >
         <p style="width: 500px;">${recommendPth }${signUserId }</p>
        <!--<input type="text" value="http://121.41.54.214:80/toRegister.html?u=MjE">-->
        <input type="button" id="copyIviteHref" onclick="move_swf(this)"  passwordValue="${recommendPth }${signUserId }" value="复制链接">
        
      </div>
    </div>
    <div class="hb_num jhb_num">
      <h4>邀请记录</h4>
      <ul class="jhb_num-title">
       <li>邀请用户</li>
       <li>用户邀请时间</li>
       <li>用户状态</li>
       <li>分享级别</li>
       <li>奖励金额(元)</li>
       <li>是否生效</li>
      </ul>
      <div id="friendPushMoneyTable" align="center"></div>
  
    </div><!-- hb_num -->
    <div class="tip">
        <h5>温馨提示：</h5>
        <ul>
          <li>
            <p>1. 邀请好友注意事项：</p>
            <p>好友需通过你的专属链接注册才能建立推荐关系，好友投资成功后邀请奖励实时到账，将有短信、微信提醒，用户可在”我的账户——邀请好友“中查看；现金奖励可用于投资抵用或直接提现；</p>
            <p>邀请奖励无上限，邀请越多，奖励越多；</p>
            <p>请不要随意邀请您不熟悉的人，避免给别人带来不必要的困扰；</p>
          </li>
          <!-- 
          <li>2. 使用过程中遇到问题，请联系客服：${websitetel}。</li>
           -->
        </ul>
    </div><!-- tip -->
  </div><!-- J-ma-conR -->
 </div>
</div>

 <!--/bottom-->
 <jsp:include page="/foot.do"></jsp:include>
 <%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
$(function(){
	init();
	var data = {"tojsp" : 1};
 getPageTable(data, "${path }/basics/friendSumReward.do",generatefriendPushMoneyTable, "friendPushMoneyTable");
})
/* 好友提成表 */
function generatefriendPushMoneyTable(data) {
	var status = "";
	if(data.sta==1){
		status='<li>已生效</li>';
	}else{
		status= '<li class="jhb_numcolor">未生效</li>';
	}
	
		var htmlStr='<ul>';
		 htmlStr+="<li>"+data.userAccount+"</li><li>"+toDate(data.time, 'yyyy-MM-dd')+ "</li><li>"+data.typename+"</li><li>"+data.r+"</li><li>"+data.recommendMoney+"</li>"+status+"";
				   htmlStr+='</ul>';
	    return htmlStr;
	};
function init() {
		var others = $("[id^=ZeroClipboardMovie]").parent("div");
		others.remove();		
		ZeroClipboard.setMoviePath("${path }/common/front/js/ZeroClipboard.swf"); 
		clip = new ZeroClipboard.Client();
		clip.setHandCursor( true );
		clip.addEventListener('complete',function(client, text) {
	        //alert("已把该代码"+text+"复制到剪切板，按CTRL+V可以粘贴。");   
	        alertc("已把该代码复制到剪切板，按CTRL+V可以粘贴。");
	    });
	}
function move_swf(ee){
   var copything = $("#"+ee.id).attr("passwordValue");
   clip.setText(copything);
   if (clip.div){
        clip.receiveEvent('mouseover', null);
        clip.reposition(ee.id);
   }else{
      	clip.glue(ee.id);   
   }

   //clip.receiveEvent('mouseover', null);
}
	function reloadPage() {
		window.location.reload(true);
		//window.location.href="${path }/basics/userFriend.html";
	}


</script>
</html>