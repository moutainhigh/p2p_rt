<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String cssJsPath = path + "/common/front";
%>
<c:set var="path" value="<%=path %>"></c:set>
<c:set var="cssJsPath" value="<%=cssJsPath %>"></c:set>
<style>
.leftHover a{
	color:#e85720;
}
</style>
<%-- <div class="wdzh-left" id="menu">
    		<div id="wdzh-di" style="border-bottom:1px solid #e6e6e6;">
          		<div style="background:none" >
                	<a><img width="20" height="19" src="${cssJsPath }/images/account/wdzh_03.jpg"></a>
                    <a href="#" style="font-size:16px;color:#E85720;"> 账户中心</a>
                 </div>
             </div>
             <div class="wdzh-di">
                <div  class="ljdl-wz">
                    <a href="#">资金管理</a>
                    <a style="margin-left:30px;"><img width="5" height="7" src="${cssJsPath }/images/account/wdzh_11.jpg"></a>
                 </div>
            </div>
             <div id="tit_1" class="wdzh-zlmdiv" style="display: none;">
                <ul >
                	<li id="tit_1_left14"><a href="${path }/account/bankCard.html">银行账户</a></li>
                    <li id="tit_1_left15"><a href="${path }/account/recharge.html">账户充值</a></li>
					<li id="tit_1_left16"><a href="${path }/account/rechargeLog.do">充值记录</a></li>
					<li id="tit_1_left17"><a href="${path }/account/withdraw.html">账户提现</a></li>
					<li id="tit_1_left18"><a href="${path }/account/withdrawLog.do">提现记录</a></li>
					<li id="tit_1_left19"><a href="${path }/account/transcationRecord.html">资金记录</a></li>
               </ul>
           </div>
            
            <div class="wdzh-di">
                <div  class="ljdl-wz">
                    <a href="#">我的投资</a>               
                     <a style="margin-left:30px;"><img width="5" height="7" src="${cssJsPath }/images/account/wdzh_11.jpg"></a>
                 </div>
             </div> 
             <div id="tit_2" class="wdzh-zlmdiv" style="display: none;">
                <ul >
                    <li id="tit_2_left1"><a href="${path }/invest/inBid.html">正在投标的借款</a></li>
					<li id="tit_2_left2"><a href="${path}/invest/inRepaying.do">正在收款的项目</a></li>
					<li id="tit_2_left3"><a href="${path}/invest/getRepByStatus.do">未收款明细账</a></li>
					<li id="tit_2_left4"><a href="${path}/invest/getRepByStatus1.do">已收款明细账</a></li>
					<li id="tit_2_left5"><a href="${path}/invest/getRecord.do">借出明细账</a></li>
					<li id="tit_2_left6"><a href="${path}/invest/inRepayed.do">已还清的项目</a></li>
					<li id="tit_2_left7"><a href="${path }/invest/transferRedeem.html">债权转让</a></li>
					<li id="tit_2_left29"><a href="${path }/invest/investRedeem.do">投资赎回</a></li>
					<li id="tit_2_left8"><a href="${path }/invest/autoInvestment.html">自动投标</a></li>
               </ul>
            </div>
            
            <div class="wdzh-di">
                <div  class="ljdl-wz">
                    <a href="#">我的借款</a>               
                     <a style="margin-left:30px;"><img width="5" height="7" src="${cssJsPath }/images/account/wdzh_11.jpg"></a>
                 </div>
             </div> 
             <div id="tit_3" class="wdzh-zlmdiv" style="display: none;">
                <ul >
                    <li id="tit_3_left9"><a href="${path }/iborrow/borrowStatus.do">正在招标的借款</a></li>
					<li id="tit_3_left10"><a href="${path }/iborrow/borrowStatus1.do">尚未发布的借款</a></li>
					<li id="tit_3_left11"><a href="${path }/iborrow/borrowStatus2.do">正在还款的借款</a></li>
					<li id="tit_3_left12"><a href="${path }/iborrow/borrowStatus3.do">已还完的借款</a></li>
					<li id="tit_3_left14"><a href="${path }/iborrow/borrowStatus4.do">逾期的借款</a></li>
					<li id="tit_3_left13"><a href="${path }/verify/evaluateApply.do">授信额度</a></li>
               </ul>
            </div>
             
           <div class="wdzh-di">
                <div  class="ljdl-wz">
                    <a href="#">基本设置</a>
                     <a style="margin-left:30px;"><img width="5" height="7" src="${cssJsPath }/images/account/wdzh_11.jpg"></a>
                </div>
                 
           </div>
           <div id="tit_4" class="wdzh-zlmdiv" style="display: none;" >
                <ul >
                     <li id="tit_4_left20"><a href="${path }/verify/userAttestation.do">认证中心</a></li>
                     <li id="tit_4_left21"><a href="${path }/basics/userFriend.html">好友中心</a></li>
                     <li id="tit_4_left22"><a href="${path }/basics/personalData.html">个人资料</a></li>
					 <li id="tit_4_left23"><a href="${path }/basics/avatar.do">更换头像</a></li>
					 <li id="tit_4_left24"><a href="${path }/basics/userMessage.html">站内信</a></li> 
					 <li id="tit_4_left25"><a href="${path }/basics/userNotice.do"> 提醒设置</a></li>
               </ul>
           </div>
          
           <div class="wdzh-di">
                <div  class="ljdl-wz"><a>安全中心</a>
               	  <a style="margin-left:30px;"><img width="5" height="7" src="${cssJsPath }/images/account/wdzh_11.jpg"></a>
                </div>
                
           </div>
           <div id="tit_5" class="wdzh-zlmdiv" style="display: none;">
               <ul >
                   <li id="tit_5_left26"><a href="${path }/verify/saveUserLoginPass.html">修改登录密码</a></li>  
                   <li id="tit_5_left27"><a href="${path }/verify/saveUserPayPass.do"> 修改交易密码</a></li>
                   <li id="tit_5_left28"><a href="${path }/verify/getUserPayPass.do"> 重置交易密码</a></li>
               </ul>
           </div>
           
                      
    </div> --%>
    <div class="J-ma-nav-top">
   
   <div class="J-mant-img"><img src="${path }${user.avatarImg}"/></div> 
    <p class="text-overflow">${FRONT_USER_SESSION.userAccount }</p>
    <h4><script>document.write("${FRONT_USER_SESSION.userPhone}".substr(0,"${FRONT_USER_SESSION.userPhone}".length-8)+"***"+"${FRONT_USER_SESSION.userPhone}".substr("${FRONT_USER_SESSION.userPhone}".length-4,"${FRONT_USER_SESSION.userPhone}".length));</script></h4>
    <dl>
             <c:choose>
			      <c:when test="${not empty user.userPhone && user.phoneStatus==2}">
			        <dd><a style="padding:0px;" title="手机认证" class="acc-sec3 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-phored.png"/></a></dd>
			      </c:when>
			      <c:otherwise>
			        <dd><a style="padding:0px;" title="手机认证" class="acc-sec3 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-pho.png"/></a></dd>
			      </c:otherwise>
			    </c:choose>
			    <c:choose>
			      <c:when test="${not empty user.userEmail && user.emailStatus==2}">
			        <dd><a title="邮箱认证" class="acc-sec1 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-emailred.png"/></a></dd>
			      </c:when>
			      <c:otherwise>
			        <dd><a title="邮箱认证" class="acc-sec1 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-email.png"/></a></dd>
			      </c:otherwise>
			    </c:choose>
			    <c:choose>
			      <c:when test="${not empty user.userRealname && user.realnameStatus==2}">
			        <dd><a title="身份认证" class="acc-sec2 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-idred.png"/></a></dd>         
			      </c:when>
			      <c:otherwise>
			        <dd><a title="身份认证" class="acc-sec2 hint hint--bottom" href="${path }/basics/personalData.html"><img src="${cssJsPath }/images/J-myaccount-id.png"/></a></dd>         
			      </c:otherwise>
			    </c:choose>
    </dl>
   </div>
   
   <div class="J-ma-nav-con">
    <ul>
     <li ><a href="${path }/account/accountIndex.html" id="left1" >我的账户</a></li>
     <li ><a href="${path }/invest/inBid.html" id="left2">投资管理</a></li>
     <li ><a href="${path }/invest/transferRedeem.html" id="left3">债权转让</a></li>
     <li ><a href="${path }/invest/autoInvestment.html" id="left4">自动投标</a></li>
     <li ><a href="${path }/account/transcationRecord.html" id="left5">资金记录</a></li>
    </ul>
    <ul>
     <li><a href="${path }/account/recharge.html" id="left6">充值</a></li>
     <li><a href="${path }/account/withdraw.html" id="left7">提现</a></li>
     <li><a href="${path }/account/bankCard.html" id="left8">银行卡管理</a></li>
    </ul>
    <ul>
     <li><a href="${path }/basics/personalData.html" id="left9">基本信息</a></li>
     <li><a href="${path }/verify/saveUserLoginPass.html" id="left10">密码设置</a></li>
     <li><a href="${path }/basics/userMessage.html" id="left11">消息中心</a></li>
    </ul>
    <ul>
     <li><a href="${path }/basics/userFriend.html" id="left12">邀请好友</a></li>
     <li><a href="${path }/redenvelopes/showList.html" id="left13">我的红包</a></li>
    </ul> 
   </div>
<script type="text/javascript">

	/* jQuery("#menu").slide({
	    titCell:".wdzh-di", //鼠标触发对象
	    targetCell:".wdzh-zlmdiv", //与titCell一一对应，第n个titCell控制第n个targetCell的显示隐藏
	    effect:"slideDown", //targetCell下拉效果
	    delayTime:300 , //效果时间
	    trigger:"click",
	    triggerTime:50, //鼠标延迟触发时间（默认150）
	    defaultPlay:false,//默认是否执行效果（默认true）
	    returnDefault:false //鼠标从.sideMen移走后返回默认状态（默认false）
	});  */
	 /*  jQuery("#menu").slide({ titCell:".ljdl-wz", targetCell:".wdzh-zlmdiv",defaultPlay:false,
	effect:"slideDown",trigger:"click" });  
	try{
		var curTitle = $("#curTitle").val();
		var titleSplit = curTitle.split("_");
		$("#"+titleSplit[0]+"_"+titleSplit[1]).show();
		$("#" + curTitle).addClass("leftHover");
	}catch(e){}
	$(function(){
		changeTopHover(4);
	}); */
   
   var curTitle = $("#curTitle").val();
		$("#" + curTitle ).addClass("J-manav-current")
</script>