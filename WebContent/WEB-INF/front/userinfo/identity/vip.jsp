<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	        <c:set var="curNav" value="title8"></c:set>
            <%@include file="titleNav.jsp"%>
	        <div id="vip" class="model-box">
	        		<div class="vip_k">
	        		<form name="formVip" id="formVip" method="post" >
						  <div class="vip_top">
						    <div class="vip_top_bt" id="vip_top_bt">您的vip状态：
						    		<c:if test="${vip.vipView ==null || vip.vipView == ''}">
								  		VIP未申请
								  	</c:if>
								  	<c:if test="${vip.vipView !=null || vip.vipView != ''}">
								  		${vip.vipView }
								  	</c:if>
								  	  ${cus.avatarImg}
							</div>
						  </div>
						 <div class="vip_nrk">
						  <c:forEach var="cus" items="${CUSTOMER_SERVICE }">
							  		<c:if test="${not empty vip.userId}">
							  		 	<c:if test="${vip.vipCustomer eq cus.id }">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd>
											  	<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a>
											  </dd>
											  <dd><input name="vipCustomer" id="vipCustomer" type="radio" checked="checked" value="${vip.vipCustomer }" /></dd>
											</dl>
											</div>
							  		 	</c:if>
							  		 	<c:if test="${vip.vipCustomer ne cus.id }">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd>
											  	<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a>
											  </dd>
											  <dd><input name="vipCustomer" id="vipCustomer" type="radio"  value="${vip.vipCustomer }" /></dd>
											</dl>
											</div>
							  		 	</c:if>
							  		 	
							  		</c:if>
							  		<c:if test="${ empty vip.userId}">
							  		 		<div class="vip_left_k">
											<dl>
											  <dt><img src="${path }${cus.avatarImg}" width="140" height="110" /></dt>
											  <dd>姓名：<span>${cus.userRealname }</span></dd>
											  <dd>电话：<span>${cus.userPhone }</span></dd>
											  <dd><a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${cus.userQq}&amp;site=qq&amp;menu=yes" target="_blank">
				                         			 <img border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:${cus.userQq}:41">
				                      			 </a></dd>
											  <dd><input name="vipCustomer" class="btn btnSize_1 btn_orange" id="vipCustomer"  type="radio"  value="${cus.id }" /></dd>
											</dl>
										</div>
							  		</c:if>
						  </c:forEach>
						 </div>
						 <!--vip  -->
						 <div class="vip_ztt">
						  <div class="vip_ztk">
							  <ul>
								  <li>您的状态：</li>
								  <li style="margin-right:45px;" id="statusd">
								  	<c:if test="${vip.vipView ==null || vip.vipView == ''}">
								  		VIP未申请
								  	</c:if>
								  	<c:if test="${vip.vipView !=null || vip.vipView != ''}">
								  		${vip.vipView }
								  	</c:if>
								  </li>
								  <li>用户名：</li>
								  <li style="margin-right:45px;">${user.userAccount }</li>
								  <li>姓名：</li>
								  <li style="margin-right:45px;">${user.userRealname }</li>
								  <li>邮箱：</li>
								  <li>${user.userEmail }</li>
							  </ul>
						  </div>
						  <div class="vip_wz">vip申请后有效时间是一年 &nbsp; 
						  	<c:if test="${ not empty vip.vipEndDate && vip.vipStatus==1 }">
						 		 到期日：<fmt:formatDate value="${vip.vipEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						  	</c:if>
					      </div>
						  <div class="vip_wz">申请VIP会员将扣除VIP管理费用:${vipFee}元，您的当前可用余额${userAccount }元 ，
						  		<c:choose>
									  <c:when test="${not empty userAccount && userAccount gt vipFee }">
									  </c:when>
									  <c:otherwise>不够扣除VIP费用，请先<a href="${path }/account/recharge.html">[充值]</a> </c:otherwise>
									</c:choose>
								 本网站非VIP会员期间投标不享有本网站的垫付保障权益!</div>
								 
						  <div id="vipDiv" style="display: block;">
							  <div class="vip_bgk">
								  <dl>
								  <c:if test="${vip.vipCustomer ==null ||vip.vipCustomer=='' ||vip.vipStatus==5||vip.vipStatus==3}">
								    <dt><em>*</em>验证码：
								    	<input maxlength="4" style="width:100px; margin-bottom: 10px;" name="captcha" type="text" id="captchaVip" class="input"  />&nbsp;
										<span  class="txt"><img style="margin-top: 7px;margin-bottom: -8px;" id="captchaImg2" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" /></span>
								    </dt>
								  </c:if>
								  </dl>
							  </div>
							  <div class="form-opt" style="padding:10px 30px 10px;">
							  	<c:if test="${vip.vipCustomer ==null ||vip.vipCustomer=='' ||vip.vipStatus==5||vip.vipStatus==3}">
			       		  			<span class="wdzh-buttu" onclick="submitVip();" style="cursor: pointer;margin-top: 3px;" id="submit">申请</span>
							  	</c:if>
			       		 	</div>
		       		 	</div>
		       		 </div>
	        		</form>
	       		 </div>
	       		 
       		</div>
	   
    </div>
    </div>
    </div>
    </div>
    <!--/main--> 
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	//Vip申请
	function submitVip(){
		var vipFee=${vipFee};
		var userAccount=${userAccount};
		var account;
		account=userAccount-vipFee;
		if(account<0){
			alertc("用户余额不足!");
			$("#captchaImg2").click();
		}else if($("#captchaVip").val()==""||$("#captchaVip").val()==null){
			
			alertc("请填写验证码");
			$("#captchaImg2").click();
			
		}else if($("#vipCustomer").val()==""||$("#vipCustomer").val()==null){
			alertc("请选择客服");
			$("#captchaImg2").click();
		}else{
			$.ajax({
	          	dataType: 'json',
	            url:"${path}/verify/toVipApply.do",
	            data:$("#formVip").serialize(),
	            type: 'POST',
	            
	            success: function(data) {
	           		//资料上传
	           		if(data.code=='212'){
	           			alertc("vip申请正在处理中");
	           			$("#vipDiv").hide();
	           			$("#vip_top_bt").text("您的vip状态：vip申请中");
	           			$("#statusd").text("vip申请中");
	           		}
	           		if(data.code=='213'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='210'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='211'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='202'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='215'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='216'){
	           			alertc(data.msg,function(){window.location.href="${path}/verify/userAttestation.do"});
	           		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        }); 
		}
		 
	} 
</script>
</html>