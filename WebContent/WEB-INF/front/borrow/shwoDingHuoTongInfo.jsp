<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<link type="text/css" rel="stylesheet"
	href="${frontPath }/css/invest.css" />
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
</head>
<body>
<!--头部-->
<jsp:include flush="false" page="/top.do"></jsp:include>
<!--广告--><!--内容-->
<!--最新公告-->
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
  <div class="nrbao">
  <div class="wydkxq-nr">
    <div class="wytzxq-k">
    
    <div class="b-ze">
    
      <div class="b-left">
      <div class="wytzxq-topbt">
    <ul>
 
    <li style="font-size:20px; line-height: 25px;"><span >
    						<img src="${frontPath}/images/ny/ding.jpg" width="24" height="24" />
    			</span>  ${borrow.borrowTitle  }</li>
   
    </ul>
    </div>
      <div class="lx-leftbao">
<div class="b-leftje" style="margin-left:20px;">
  <div class="b-leftb">
  <ul>
        <li>债权总额：</li>
        <li style="font-size:30px; color:#000;"><i style="font-size:26px; font-style:normal;">¥</i><fmt:formatNumber value="${borrow.borrowSum  }" pattern="#,#00.00"/><i style="font-size:14px; font-style:normal;">元</i></li>          
        </ul>  
             
      </div>
    </div>
      <div class="b-leftje" style="border-left:1px solid #ccc;">
        <div class="b-leftb" style="text-indent:15px;">
          <ul>
            <li>年化收益率：</li>
            <li style="font-size:30px; color:#d12e2e;">${borrow.annualInterestRate }<i  style="font-size:26px; font-style:normal;">%</i></li>
          </ul>
        </div>
      </div>
      <div class="b-leftje" style="border-left:1px solid #ccc;">
        <div class="b-leftb" style="text-indent:15px;">
          <ul>
            <li>投资期限：</li>
            <li style="font-size:30px; color:#000;"><c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}<i  style="font-size:26px; font-style:normal;">天</i>
									</c:if> <c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}<i  style="font-size:26px; font-style:normal;">个月</i>
									</c:if></li>
          </ul>
        </div>
      </div>
      </div>
      <div class="lx-leftwz"><span style="line-height:24px;">保障范围：</span><span style=" margin-right:300px; "><img src="${frontPath}/images//ny/tz_07.jpg" width="22" height="24" /> <img src="${frontPath}/images//ny/tz_09.jpg" width="23" height="24" /></span>
      <span>还款方式：</span><span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span></div>
      <div class="lx-leftdi">
      <span >
      <p style="float:left;">投资进度：</p>
    
      <span style="display: inline-block;font-size: 12px;margin: 0;padding: 0; width: 158px; ">
      <div class="dcb-jdt" style=" margin-top:15px; ">
              <div class="jd" style="width:${percentage}%"></div>
      </div>${percentage}%</span><br/>
      <p style="float:left;" id="lastDateTimeDiv"></p>
      </span>

        <span ><p style="float:left;">起投金额：</p><p style="float:left;">￥${borrow.minAmount }</p><br>
        <p style="float:left;">可投金额：</p><p style="float:left;">￥${borrow.borrowSum-borrow.tenderSum  }</p></span></div>
      </div>
      
      <div  class="b-right">      
          <div class="b-right-zd">
         
			<ul class="b-ul-wz">
             <li style="line-height:28px; font-size:14px;">投标奖励：</li>  
               <li style="line-height:28px; font-size:14px;">
					无
				</li>            
            </ul>
			<ul class="b-ul-wz">
             <li style="line-height:28px; font-size:14px;">可投金额：</li>  
               <li style="line-height:28px; font-size:14px;">￥${borrow.borrowSum-borrow.tenderSum  }</li>            
            </ul>
            <ul class="b-ul-wz">
              <li style="line-height:28px; font-size:14px;">账户可用余额：</li>  
                <c:if test="${not empty FRONT_USER_SESSION }">
            		<li style="line-height:28px; font-size:14px;"> ${userAccount.availableMoney } <a style="color:#ef8200;" href="${path }/account/recharge.html">充值</a></li>
            	</c:if>
            	<c:if test="${ empty FRONT_USER_SESSION }"><li style="line-height:28px; font-size:14px;">
               	<span ><a href="${path }/login" style="color:#ef8200;">登录</a></span>后可见</li> 
               </c:if>
                          
            </ul>
        </div>
            <p style="font-size:14px; color:#666; clear:both; margin-left:20px;  margin:0px; padding:0px; text-indent:13px;line-height:40px; margin-bottom:7px; margin-top:50px;">
          		  借款协议书：<c:if test="${agreementStatus==1 }">
									<a style="color:#ef8200;"  href="${path }${agreementPath}">下载</a>
								</c:if> <c:if test="${agreementStatus==0 }">
									<a  style="color:#ef8200;"  href="javascript:alertc('您当前无权限下载；');">下载</a>
								</c:if></p>
		    <div style="margin-left:15px; margin-top:0px; border-radius:5px;" class="sq-anniou">
		    <a id="tenderBtnSpan" href="javascript:;" style="font-size:18px; color:#FFF;"></a></div>
 
      </div>
    </div>
  </div>
  <div class="lc-xqk" style="clear:both; border-bottom:2px solid #e70815;"><div class="wydktop-bao" style="height:48px;" >
      <div class="wydk-qh red" onmouseover="changeTabs(this,1)" ><a  href="#">项目概述</a></div>
      <div class="wydk-qh" onmouseover="changeTabs(this,2)"><a href="#">投资记录</a></div>
  </div></div>
    <div class="lc-bao">

    <div class="lcxq-b">
      <div class="jkjbxx-top" id="biaoDetail">
     		<div id="info_tab1" class="info_tab">
					<h1>
						<span>借款者资料</span>
					</h1>
					<div class="user">
						<a ><img onerror="imgError(this)" <c:if test="${ not empty borrowUser.avatarImg}">src="${path }${borrowUser.avatarImg}"</c:if> <c:if test="${  empty borrowUser.avatarImg}">src="${frontPath }/images/list.jpg"</c:if> class="fleft"></a>
						<ul>
							<li><font>发布者：</font>${borrowUser.userAccount }</li>
							<li><font>性别：</font>${borrowUser.userSex }</li>
							<li><font>用户籍贯：</font>${borrowUser.userProvince }&nbsp;&nbsp;${borrowUser.userCity }&nbsp;&nbsp;${borrowUser.userArea }</li>
							<li><font>上次登陆时间：</font>
							<fmt:formatDate value="${borrowUser.userLogintime}"
									pattern="yyyy-MM-dd" /></li>
							<li><font>注册时间：</font>
							<fmt:formatDate value="${borrowUser.userAddtime}"
									pattern="yyyy-MM-dd" /></li>
							<li><font>婚姻状况：</font>
							<c:if test="${personalMessage.marital==0 }">未婚</c:if>
								<c:if test="${personalMessage.marital==1 }"> 已婚</c:if>
								<c:if test="${personalMessage.marital==2 }"> 离异</c:if>
								<c:if test="${personalMessage.marital==3 }">丧偶</c:if></li>

							<li><font></font></li>
							<li><font></font></li>

							<li><font></font></li>
							<li><font></font></li>

						</ul>
					</div>

					<h1>
						<span>借款描述</span>
					</h1>
					<div class="info_text">
						<span>借款详情：</span>${borrow.borrowContent }<br> <span>借款方借款通途：</span>本次借款用于${BORROW_ALL_BORROW_USE[borrow.borrowUse]}。
					</div>

					<h1>
						<span>信用档案</span>
					</h1>
					<ul>
						<li>成功借款（笔）${borrowSuccessSum }</li>
						<li>借款总额（元）${borrowSum }</li>
						<li class="clearR">逾期次数（次）${repayOverdueCount }</li>
						<li>还清笔数（笔）${repaySuccessCount }</li>
						<li>待还笔数（笔）${repayingCount }</li>
						<li class="clearR">流标次数（次）${borrowFlowStandard }</li>
						<li>投资担保总额（元）${userEvaluate.tenderVouch}</li>
						<li>可用借款担保额度（元）${userEvaluate.borrowVouchAvailable}</li>
						<li class="clearR"></li>
					</ul>

					<h1>
						<span>资料审核</span>
					</h1>
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<th>序号</th>
								<th>资料类型</th>
								<th>资料说明</th>
								<th>积分</th>
								<th>上传时间</th>
								<th>审核时间</th>
							</tr>
							<c:if test="${attestationApplyListSize>0 }">
								<c:forEach items="${attestationApplyList}"
									var="attestationApply" varStatus="status">
									<tr>
										<td>${status.index+1 }</td>
										<td>${attestationApply.attestationName  }</td>
										<td>${attestationApply.attestationName  }</td>
										<td style="width: 169px;">${attestationApply.attestationScore }</td>
										<td style="width: 169px;"><fmt:formatDate
												value="${attestationApply.attestationApplyDatetime}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width: 169px;"><fmt:formatDate
												value="${attestationApply.attestationVerifyDatetime}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width: 169px;">${attestationApply.attestationVerifyReview}</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${attestationApplyListSize==0 }">
								<tr>
									<td colspan="6" style="text-align: center;">暂无数据</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
      </div>
      <div class="jkjbxx-top" id="record" style="display: none;">
        <div class="wytz-lby" style="width: 900px;">
						<div class="lby-top">
							<span>投标人</span> <span>当前年利率</span> <span>投标金额 </span> <span>有效金额
							</span> <span>投标时间 </span> <span>状态</span>
						</div>
						<div id="borrowTenderList"></div>
		</div>
      </div>
    </div>
    </div>
  </div>
</div>
</div>
<!--尾部-->

<jsp:include page="/foot.do"></jsp:include>
<%@ include file="../onlineSupport.jsp" %>
<div style="display: none;" class="toubiao white_content"
		id="tenderDiv">

		<div class="tan_tt">
			<div class="fleft">${borrow.borrowTitle }</div>
			<div class="fright">
				<a style="color: #fff;" href="javascript:;" onclick="closeDialog();"
					class="white">×</a>
			</div>
		</div>
		<div class="tan_info">
			<div class="fleft">


				借款人：${borrow.user.userAccount }<br /> 借款金额：<strong
					style="color: #cd4b00; font-size: 14px;" class="orange">￥${borrow.borrowSum }
					元</strong><br />
				<c:choose>
					<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
	每份金额：￥${borrow.amountPerCopies }元<br />
	总份数：<fmt:formatNumber
							value="${(borrow.borrowSum)/borrow.amountPerCopies }" pattern="#"
							type="number" />
	份<br />
	还剩份数：<fmt:formatNumber
							value="${(borrow.borrowSum-borrow.tenderSum)/borrow.amountPerCopies }"
							pattern="#" type="number" />
	份<br />
					</c:when>
					<c:otherwise>
		还需借款：￥${borrow.borrowSum-borrow.tenderSum }<br />
					</c:otherwise>
				</c:choose>
				借款年利率: ${borrow.annualInterestRate } %<br /> 已经完成：${percentage } %<br />
				借款期限：
				<c:if test="${borrow.isDay==1}">
	${borrow.borrowTimeLimit }天
	</c:if>
				<c:if test="${borrow.isDay==2}">
	${borrow.borrowTimeLimit }个月
	</c:if>
				<br /> 还款方式: ${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}

			</div>

			<div class="fright">
				<form id="tenderFrm" action="" style="line-height: 25px;">
					<input type="hidden" name="annualInterestRate" value="${borrow.annualInterestRate }"/> 
					<input type="hidden" name="borrowSum" value="${borrow.borrowSum }"/>
					<input type="hidden" name="borrowTimeLimit" value="${borrow.borrowTimeLimit }"/> 
					<input type="hidden" id="borrowId" name="borrowId" value="${borrow.id }"> <input
						type="hidden" name="bidKind" value="${borrow.bidKind }"> <input
						type="hidden" name="SignBorrowId" value="${SignBorrowId }">
					您的可用余额：${userAccount.availableMoney }元 <a style="color: #cd4b00;"
						href="${path }/account/recharge.html" class="orange" target="_blank">[我要充值]</a><br />
					最多投标总额：
					<c:if test="${borrow.maxAmount <= 0}">不限制</c:if>
					<c:if test="${borrow.maxAmount > 0}">${borrow.maxAmount }</c:if>
					<br /> 最少投标总额:
					<c:choose>
						<c:when
							test="${CACHE_SYS_FEES_RATE.sysInvestMinmoney > borrow.minAmount}">
					${CACHE_SYS_FEES_RATE.sysInvestMinmoney}
					</c:when>
						<c:otherwise>
					${borrow.minAmount }
					</c:otherwise>
					</c:choose>
					<br />
					<c:choose>
						<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
					购买份数：
					<span><input size="2"
								onkeyup="value=value.replace(/[^0-9|.]/g,'')"
								style="border: 1px solid #B4B9BD; height: 10px; width: 120px;margin-left:-1px;"
								type="text" id="tenderCopies" name="tenderCopies" class="input"
								maxlength="18"> <span>份</span></span>
			</c:when>
			<c:otherwise>
					投标金额：<span><input size="2"
					onkeyup="value=value.replace(/[^0-9|.]/g,'')"
					style="border: 1px solid #B4B9BD;margin-left:2px; height: 10px; width: 120px;"
					type="text" id="signName" name="tenderAmount" class="input"
					maxlength="18"> <span>元</span> <input
					onclick="fillTenderMoney()" value="自动填充金额"
					style="padding: 5px;  border: 1px solid #EC7529; background-color: #EC7529; color: #fff; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; outline: 0;"
					type="button" /></span>
			</c:otherwise>
			</c:choose>
			<br /> 支付密码：<span><input type="hidden" name="publickey"
				id="publickey" value="${publickey}" /><input size="2"
				style="border: 1px solid #B4B9BD;margin: 12px 0 12px 2px; height: 10px; width: 120px;"
				type="password" id="payPassword" name="payPassword" class="input"
				maxlength="18"></span> 
			<c:if test="${not empty borrow.tenderPassword }">
			<br/><span>
					投标密码：<input size="2" 
						style="border: 1px solid #B4B9BD;margin-left:1px;margin-bottom:12px; height: 10px; width: 120px;"
						type="password" id="tenderPassword" name="tenderPassword"
						class="input" maxlength="18">
				</span>
				
			</c:if>
			<br/><span >验&nbsp;证&nbsp;&nbsp;码： <input
				size="2"
				style="border: 1px solid #B4B9BD; height: 10px; width: 120px;"
				type="text" id="captcha" name="captcha" class="input" maxlength="18">&nbsp;&nbsp;&nbsp;&nbsp;
			</span> <img id="captchaImg" src="${path }/captcha.svl"
				onclick="this.src='${path }/captcha.svl?d='+new Date()*1"
				valign="bottom" alt="点击更新" title="点击更新" /> <br /> <br /> <span
				class="btnBlue" onclick="saveTender()">确认投标</span> <font
				class="darkblue">注意：点击按钮表示您将确认投标金额并同意支付.</font>
			</form>
		</div>
		</div>

	</div>

	<div id="fade" class="black_overlay"></div>
</body>
<script type="text/javascript">

var borrowTime = 0;
var nowDate=0;
var bStatus = "${borrow.borrowStatus }";
var Published = ${Published}; //发布状态
var intereValId = null;
var noSetPayPassword = false;
var hasLogin = ${hasLogin};
var noSetPayPassword = false;
<c:if test="${not empty noSetPayPassword}">
	noSetPayPassword = ${noSetPayPassword};
</c:if>
//遍历标状态
var ALL_STATUS = {};
<c:forEach var="item" items="${BORROW_ALL_STATUS}"> 
	ALL_STATUS["${item.key}"] = "${item.value}";
</c:forEach>
//遍历投标状态
var TENDER_STATUS = {};
<c:forEach var="item" items="${TENDER_ALL_STATUS}"> 
	TENDER_STATUS["${item.key}"] = "${item.value}";
</c:forEach>


$(function() {
		nowDate=${nowDate};
		changeEndTime();
		var data={};
		if(Published){
			if(bStatus == '2'){
				intereValId = setInterval(changeEndTime,1000);
				enabledBtn();
			}else{
				document.getElementById("lastDateTimeDiv").innerHTML="已结束";
				disabledBtn(ALL_STATUS[bStatus]);
			}
		}else{
			disabledBtn("待开始");
		}
		//初始加载分页
		var borrowId=$("#borrowId").val();
		var data = {"borrowId":borrowId};
		getPageFrom(data,
				"${path }/borrow/shwoBorrowTenderInfoByPage.do",
				showBorrowTenderList, "borrowTenderList");
		
		changeEndTime();
		intereValId = setInterval(changeEndTime,1000);
		getStatistics();
		changeTopHover(2);
		
	});

function disabledBtn(text){
		$("#tenderBtnSpan").html(text);
		$("#tenderBtnSpan").unbind("click");
	}



function changeTabs(obj, index) {
	$(obj).parent().find(".red").removeClass("red");
	$(obj).addClass("red");
	if (index == 1) {
		$("#biaoDetail").show();
		$("#record").hide();
	} else {
		$("#record").show();
		$("#biaoDetail").hide();
	}
}

function saveTender() {
	var availableMoney=${userAccount.availableMoney }+"";
	$("#tenderFrm").validate({						  
		rules: {
			<c:choose>
			<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
			tenderCopies: {
				required: true,
				number:true,
				min:1,
		//		max:$("#remainCopies").text(),
				digits:true
			},
			</c:when>
			<c:otherwise>
			tenderAmount: {
				required: true,
				number:true
				/* max:availableMoney */
			},
			</c:otherwise>
		</c:choose>
			
		<c:if test="${not empty borrow.tenderPassword }">
			tenderPassword: {
				required: true
			},
		</c:if>
			payPassword: {
				required: true
			},
			captcha : {
				required : true
			}
			
			
		},
		//指定错误信息位置
		errorPlacement: function (error, element) { 
			//	if(element.)
				/*var curName = element.attr("name");
				 if(curName == 'tenderAmount'){
			//		error.appendTo(element.parent());
				}else{
					
				} */
				error.appendTo(element.parent());
				
      			
		}
		
	});
	if($("#tenderFrm").valid()){
		tenderFrm.payPassword.value=encryptByDES(tenderFrm.payPassword.value,tenderFrm.publickey.value);
		/* <c:if test="${not empty borrow.tenderPassword }">
		   tenderFrm.tenderPassword.value=encryptByDES(tenderFrm.tenderPassword.value,tenderFrm.publickey.value); 
		</c:if> */
		<c:choose>
		<c:when test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
			var copies = $("input[name='tenderCopies']").val();
			var perMoney = ${borrow.amountPerCopies };
			var totalMoney = copies*perMoney;
			confirmc("确认购买" + copies + "份，总金额"+totalMoney+"元?", tenderConfirmed);
		</c:when>
		<c:otherwise>
			var tenderMoney = $("input[name='tenderAmount']").val();
			confirmc("确认投资金额" + tenderMoney + "元?", tenderConfirmed);
		</c:otherwise>
	</c:choose>
		
	}
	
}

function tenderConfirmed() {
	var data = $('#tenderFrm').serialize();
	$.ajax({
		cache : false,
		type : "POST",
		url : "${path }/borrowTender/tender.do",
		data : data,
		async : false,
		error : function(request) {
			initCaptcha();
			alertc("服务器异常");				
		},
		success : function(data) {
			initCaptcha();
			var callBack = null;
			if(data.code == '200'){ //成功
				callBack = closeDialog;
			}
			alertc(data.msg,callBack);
		}
	});
}

function dealTimeZero(){
	window.clearInterval(intereValId);
	if(Published){
		document.getElementById("lastDateTimeDiv").innerHTML="已结束";
		disabledBtn("已结束");
	}else{
		Published = true;
		enabledBtn();
	}
}

function enabledBtn(){
		$("#tenderBtnSpan").html("加入");
		$("#tenderBtnSpan").click(function(){
			showTender();
		});
	}


function showTender() {
	if(!hasLogin){
		window.location.href = "${path }/login";
		return;
	}
	if(noSetPayPassword){
		alertc("请先设置支付密码!",jumpToSetPayPassword);
		return;
	}
	document.getElementById('tenderDiv').style.display = 'block';
	document.getElementById('fade').style.display = 'block';
	initCaptcha();
	document.getElementById("tenderFrm").reset();
	$("#fade").css({ "width": $(document).width(), "height": $(document).height() });
}

function jumpToSetPayPassword(){
	window.location.href = "${path }/verify/saveUserPayPass.do";
}


function showBorrowTenderList(data) {
	var investTime = data.tender_addtime;
	investTime = toDate(investTime, "yyyy-MM-dd hh:mm");
	htmlStr = '<div class="lby-nrb">';
	var fullNameInvestor = data.investorAccount;
	var investor = fullNameInvestor.subChsString(0, 2)
			+ '***'
			+ fullNameInvestor.subChsString(
					fullNameInvestor.lengthStr() - 2, 2);
	htmlStr += '<span>' + investor + '</span>';
	htmlStr += '<span>${borrow.annualInterestRate }%</span>';
	htmlStr += '<span>' + Util.numFormat(data.tender_amount, 2) + '</span>';
	htmlStr += '<span>' + Util.numFormat(data.effective_amount, 2)
			+ '</span>';
	htmlStr += '<span>' + investTime + '</span>';
	var tenderStatus = data.tender_status + "";
	htmlStr += '<span>' + TENDER_STATUS[tenderStatus] + '</span>';
	htmlStr += '</div>';
	return htmlStr;
}




function getTimeStr(){
	 var a,b,c,d,cy=cd=ch=cm=cs=0;
	 d=borrowTime;
	 nowDate=nowDate+1000;
	 b = nowDate;
	 time_distance=Math.floor((d-b));
	int_day=Math.floor(time_distance/86400000); // 1000*60*60*24
	int_hour=Math.floor((time_distance-int_day*86400000)/3600000);
	int_minute=Math.floor((time_distance-int_hour*3600000-int_day*86400000)/60000);
	int_second=Math.floor((time_distance-int_minute*60000-int_hour*3600000-int_day*86400000)/1000);
		if(int_day<0){
			int_day="0";
			int_hour="0"
			int_minute="0";
			int_second="0";
		}
	  	if(int_hour<10)
			int_hour="0"+int_hour;
			if(int_minute<10)
			int_minute="0"+int_minute;
			if(int_second<10)
			int_second="0"+int_second;

			if(int_day <= 0 && int_hour <= 0 && int_minute<=0 && int_second <= 0){
				dealTimeZero();
			}
			
	 	return int_day+'天'+int_hour+'时'+int_minute+'分'+int_second+'秒';
	}

function changeEndTime(){	
	if(Published){
		borrowTime = ${allowTenderTime };
		$("#lastDateTimeDiv").html("结束时间:"+getTimeStr());
		//document.getElementById("lastDateTime").innerHTML=getTimeStr();
	}else{
		borrowTime = ${publishTime };
		$("#lastDateTimeDiv").html("开始时间:"+getTimeStr());
		//document.getElementById("lastDateTime").innerHTML=getTimeStr();
	}
 	
}


function fillTenderMoney(){
	  var tenderMax1 = "${borrow.maxAmount}";
	   var tenderMax = Math.floor(tenderMax1);
	   var url="${path}/borrowTender/getTotalNoSuccess.do";
	   var obj=$(this);
	   $.post(url,{borrowId:$("#borrowId").val()},function (data){
		   if(tenderMax !='' && tenderMax !=0 && data > tenderMax){
			   $("#signName").val(tenderMax);
		   }else{
		   	   $("#signName").val(data);
		   }
	   });
};



function closeDialog() {
	document.getElementById('tenderDiv').style.display = 'none';
	document.getElementById('fade').style.display = 'none';
	window.location.reload();
}
</script>
</html>
