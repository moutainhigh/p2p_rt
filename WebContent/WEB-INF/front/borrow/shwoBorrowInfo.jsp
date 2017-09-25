<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${borrow.borrowTitle}_投资年化收益率${borrow.annualInterestRate }%_<c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}天</c:if><c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}个月</c:if>期高收益理财产品-浩茗金融</title>
	<meta name="Keywords" content="高收益理财产品,投资收益率,收益率${borrow.annualInterestRate }%理财产品, <c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}天</c:if><c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}个月</c:if>天理财产品">
	<meta name="Description" content="${borrow.borrowTitle}是浩茗金融（www.hmjr99.com）为互联网用户提供的短期高利理财投资产品，每期<c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}天</c:if><c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}个月</c:if>天，可根据自身需求自动续投，或预约退出，随取随用，方便灵活。作为一款消费品供应链金融服务商，浩茗金融提供的新手小许标年化收益率可达${borrow.annualInterestRate	}%。是投资理财的最佳选择！">
	<%@include file="../taglibs.jsp"%>
	<script type="text/javascript" src="${frontPath }/js/page4borrowInfo.js"></script>
	<!---弹出层-->
	<script type="text/javascript" charset="utf-8" src="${frontPath }/js/jquery.leanModal.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/css/jquery.fancybox.css" media="screen" />
	<style>
		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
		.blk_29 { padding:10p 0px; OVERFLOW: hidden; ZOOM: 1; POSITION: relative; height:156px;}
		.blk_29 .LeftBotton {	BACKGROUND:url(${frontPath }/images/ts.png) no-repeat 0px 0px; LEFT: -1px; FLOAT: left; WIDTH: 19px; CURSOR: pointer; POSITION: absolute; TOP: 73px; HEIGHT: 36px}
		.blk_29 .RightBotton {	RIGHT: -2px; BACKGROUND: url(${frontPath }/images/ts.png) no-repeat -19px 0px; FLOAT: right; WIDTH: 19px; CURSOR: pointer; POSITION: absolute; TOP: 73px; HEIGHT:36px}
		.blk_29 .Cont {	MARGIN: 0px auto; OVERFLOW: hidden; WIDTH: 1012px!important; PADDING-TOP: 5px; }
		.blk_29 .box{	FLOAT: left; TEXT-ALIGN: center; position:relative;;margin-right:29px;width: 177px;}
		.blk_29 .box IMG {	border:3px solid #f0f0f0;  DISPLAY: block;  BACKGROUND: #fff;  MARGIN: 0px auto;}
		.blk_29 .box P {display:block;  position:absolute; top:87px; background:#242424; opacity:0.6; line-height:30px; text-align:center; color:#fff; z-index:999; font-size:14px; font-weight:bold; width:97.5%; left:3px;}
		.rollphotos {	MARGIN-TOP: 10px}
	</style> 
	<!-- fancybox -->
	<script type="text/javascript" src="${frontPath }/js/fancybox/jquery.fancybox.js"></script>
	<link rel="stylesheet" type="text/css" href="${frontPath }/js/fancybox/jquery.fancybox.css" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${frontPath }/js/fancybox/jquery.fancybox-buttons.css" />
	<script type="text/javascript" src="${frontPath }/js/fancybox/jquery.fancybox-buttons.js"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${frontPath }/js/fancybox/jquery.fancybox-thumbs.css" />
	<script type="text/javascript" src="${frontPath }/js/fancybox/jquery.fancybox-thumbs.js"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${frontPath }/js/fancybox/jquery.fancybox-media.js"></script>
	<script type="text/javascript" src="${frontPath }/js/fancybox/dealimage.js"></script>
</head>
<body>
	<!--头部-->
	<jsp:include flush="false" page="/top.do"></jsp:include>
	<div class="content xm_con">
		<div class="content_in">
			<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<a href="${path }/invest">我要理财</a>
				>
				<a>${borrow.borrowTitle}</a>
			</div>
			<div class="plan_con">
				<div class="title">
					<h1>${borrow.borrowTitle}</h1>
					<input id="borrowTitle" type="hidden" value="${borrow.borrowTitle }"/>
					<a href="${path }/invest">更多项目></a>
				</div>
				<div class="plan_txt">
					<div class="plan_l">
						<div class="txt_con">
							<p>
								<i>预期年化收益率</i>
								<span>${borrow.annualInterestRate }<em>%</em></span>
							</p>
							<ul>
								<li>
									<i>投资期限：</i>
									<span class="bold"> 
										<c:if test="${borrow.isDay==1 }">
											${borrow.borrowTimeLimit}天
										</c:if>
										<c:if test="${borrow.isDay==2 }">
											${borrow.borrowTimeLimit}个月
										</c:if>
									</span>
								</li>
								<input id="isDay" type="hidden" value="${borrow.isDay}"/>
								<input id="borrowTimeLimit" type="hidden" value="${borrow.borrowTimeLimit}"/>
								<li>
									<i>项目总额：</i>
									<span class="bold">
										<fmt:formatNumber value="${borrow.borrowSum  }" pattern="#,#00.00" />元
									</span>
								</li>
								<li>
									<i>还款方式：</i>
									<span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span>
								</li>
							</ul>
							<ul>
								<li>
									<i>剩余金额：</i>
									<span>
										<fmt:formatNumber value="${borrow.borrowSum -borrow.tenderSum }" pattern="#,#00.00" />元
									</span>
								</li>
								<li>
									<i>发布日期：</i>
									<span>
										<fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" />
									</span>
								</li>
								<div id="qxr">
									<li>
										<i>起&nbsp;&nbsp;息&nbsp;&nbsp;日：</i>
										<span>满标当日开始计息</span>
									</li>
								</div>
							</ul>
						</div>
						<div class="percent">
							<div class="percent_in" style="width:${percentage}%;"></div>
						</div>
						<div class="num_time">
							<p>
								进度：<span>${percentage}%</span>
								<input id="percentage" type="hidden" value="${percentage}"/>
							</p>
							<p class="time" id="lastDateTimeDiv"></p>
						</div>
					</div>
					<!-- plan_l -->
					<div class="plan_r">
						<div class="money_con">
							<p>
								账户余额<span>${userAccount.availableMoney }</span>元
							</p>
							<a href="${path }/account/recharge.html" target="_blank">充值</a>
						</div>
						<div class="input add_input">
							<input type="text" placeholder="${borrow.minAmount }元起投 "
								id="tenderMoney" onblur ="setTenderMoney();" onkeyup="value=value.replace(/[^0-9]/g,'')">
								<a href="#" class="balan">元</a>
						</div>
						<div class="input">
							<input  type="password"  placeholder="请输入支付密码" id="payPassword2">
							<!-- <p style="color: #f78182;" align="center">始支付密码为用户登录密码</p> -->
						</div>
						<c:if test="${!empty userAccount.availableMoney}">
							<a href="#loginmodal" class="join_btn flatbtn" id="modaltrigger">马上投资</a>
						</c:if>
						<c:if test="${empty userAccount.availableMoney}">
							<a href="${path }/login" class="join_btn flatbtn" id="modaltrigger">马上投资</a>
						</c:if>

						<div id="loginmodal">
							<div class="clearfix J-xmxq-popcon">
								<ol>
									<li>项目名称：${borrow.borrowTitle }</li>
									<li>项目总额：<em>&yen;${borrow.borrowSum}</em></li>
									<li>起投金额：&yen;${borrow.minAmount }</li>
									<%-- <li>还需借款：&yen;${borrow.borrowSum-borrow.tenderSum}</li> --%>
									<li>预期年化收益率：${borrow.annualInterestRate }%</li>
									<%-- <li>已经完成：${percentage }%</li> --%>
									<li>投资期限： 
										<c:if test="${borrow.isDay==1}">${borrow.borrowTimeLimit }天</c:if> 
										<c:if test="${borrow.isDay==2}">${borrow.borrowTimeLimit }个月</c:if>
									</li>
									<li>还款方式：${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</li>
									<li>计息方式：满标计息</li>
									<c:if test="${borrow.bidKind==8}">
									<li>投标上限：10000元</li>
									</c:if>
									<%-- <li>
										<p>
											最多投标总额：
											<c:if test="${borrow.maxAmount <= 0}">不限制</c:if>
											<c:if test="${borrow.maxAmount > 0}">${borrow.maxAmount }</c:if>
										</p>
										<p>
											最少投标总额：
											<c:choose>
												<c:when
													test="${CACHE_SYS_FEES_RATE.sysInvestMinmoney > borrow.minAmount}">
								${CACHE_SYS_FEES_RATE.sysInvestMinmoney}
							</c:when>
												<c:otherwise> ${borrow.minAmount }
					        </c:otherwise>
											</c:choose>
											元
										</p>
									</li> --%>

								</ol>
								<ul>
									<form id="tenderFrm" method="post">
										<input type="hidden" id="borrowId" name="borrowId" value="${borrow.id }"> 
										<input type="hidden" name="publickey" id="publickey" value="${publickey}" /> 
										<input type="hidden" name="SignBorrowId" value="${SignBorrowId }">
										<input type="hidden" name="annualInterestRate" value="${borrow.annualInterestRate }" />
										<input type="hidden" name="borrowTimeLimit" value="${borrow.borrowTimeLimit}" /> 
										<input type="hidden" name="borrowSum" value="${borrow.borrowSum}" /> 
										<input type="hidden" name="bidKind" value="${borrow.bidKind}" /> 
										<input type="hidden" name="userdeductionStatus" value="${userAccount.deductionStatus}" /> 
										<input type="hidden" name="deductionStatus" id="deductionStatus" value="0" />
										<li>
											<p>
												您的可用余额：${userAccount.availableMoney }元
												<a onclick="recharge()" style="background: none;" target="_blank">[我要充值]</a>
											</p>
										</li>
										<c:choose>
											<c:when test="${activitySwitch == '1'}">
												<li id="deductionDivIs">用户优惠选择
													<span><input type="radio" name="deduction" value="1" />抵扣金</span>
													<span><input type="radio" name="deduction" value="0" checked="checked" />红包</span>
												</li>
											</c:when>
											<c:otherwise>
												<c:if test="${ userAccount.deductionStatus == '1' && userAccount.deductionMoney != '0.00' }">
													<li id="deductionDivIs">使用抵扣金
														<span><input type="radio" name="deduction" value="1" />是</span>
														<span><input type="radio" name="deduction" value="0" checked="checked" />否</span>
													</li>
												</c:if>
											</c:otherwise>
										</c:choose>
										<li>
											<label>投标金额：</label>
											<input type="text" onkeyup="value=value.replace(/[^0-9]/g,'')" id="tenderAmount" name="tenderAmount" readonly="readonly"/>
											<a href="#" class="balan">元</a>
											<!-- <a href="#" onclick="fillTenderMoney()">自动填充金额</a> -->
											<br>
											<div id="deductionDiv" style="display: none;">
												<label>抵扣金额：</label>
												<input type="text" id="deductionMoney" name="deductionMoney" value="0"
												onkeyup="value=value.replace(/[^0-9]/g,'')" />元<br>
											</div> 
											<c:if test="${not empty borrow.tenderPassword }">
												<label>投标密码：</label>
												<input type="password" name="tenderPassword" id="tenderPassword"  />
												<br>
											</c:if>
											<label>支付密码：</label>
											<input type="password" id="payPasswords" readonly="readonly"name="payPassword" />
											<a href="${path }/verify/saveUserLoginPass.html?type=newPayPwd" style="background:none; margin:0; color:#666;">
												忘记支付密码?
											</a>
											<br> 
											<label>验证码:</label>
											<input type="text" id="captcha" name="captcha" />
											<img id="captchaImg" src="${path }/captcha.svl"
											onclick="this.src='${path }/captcha.svl?d='+new Date()*1"
											valign="bottom" alt="点击更新" title="点击更新" />
										</li>
									</form>
									<li class="J-btn J-xmxqp-btn">
										<a href="#" onclick="saveTender()">确认投标</a>
										<p>注意：点击按钮表示您将确认投标金额并同意支付</p>
									</li>
								</ul>
								<a href="javascript:" class="J-xmxq-close" style="font-size: 20px;">×</a>
							</div>
						</div>
						<div class="J-xmxq-mask" id="J-xmxq-mask"></div>
					</div>
					<!-- plan_r -->
				</div>
			</div>
		  <div id="xszx1">
 			<c:if test="${borrow.repaymentStyle ==1}">
				<div class="plan_pro">
					<span class="pro_con">项目周期</span>
					<c:choose>
						<c:when test="${borrow.borrowStatus ==2 || borrow.borrowStatus ==3 || borrow.borrowStatus ==4}">
							<c:choose>
								<c:when test="${Published == false}">
									<div class="circle1">
										<i>发布</i>
										<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
									</div>
									<div class="circle1">
										<i>加入期</i>
										<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
									</div>
								</c:when>
								<c:otherwise>
									<div class="circle1 add_back">
										<i>发布</i>
										<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
									</div>
									<div class="circle1 add_back">
										<i>加入期</i><span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
									</div>
								</c:otherwise>
							</c:choose>

							<div class="circle1">
								<i>开始收益</i>
								<span>暂未</span>
							</div>
							<div class="circle2">
								<i>一次结清退出</i>
								<span>暂未</span>
							</div>
						</c:when>
						<c:when test="${borrow.borrowStatus ==5 || borrow.borrowStatus ==7 ||borrow.borrowStatus ==10 || borrow.borrowStatus ==11 || borrow.borrowStatus ==12}">
							<div class="circle1 add_back">
								<i>发布</i>
								<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
							<div class="circle1 add_back">
								<i>加入期</i>
								<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1 add_back">
								<i>开始收益</i>
								<span><fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle2">
								<i>一次结清退出</i>
								<span><fmt:formatDate value="${borrow.repaymentTime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
						</c:when>
						<c:when test="${borrow.borrowStatus ==6 }">
							<div class="circle1 add_back">
								<i>发布</i>
								<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
							<div class="circle1 add_back">
								<i>加入期</i>
								<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1 add_back">
								<i>开始收益</i>
								<span><fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle2 add_back">
								<i>一次结清退出</i>
								<span><fmt:formatDate value="${borrow.repaymentTime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
						</c:when>
					</c:choose>
				</div>
			</c:if>
			<c:if test="${borrow.repaymentStyle ==3}">
				<div class="plan_pro">
					<span class="pro_con">项目周期</span>
					<c:choose>
						<c:when test="${borrow.borrowStatus ==2 || borrow.borrowStatus ==3 || borrow.borrowStatus ==4}">
							<div class="circle1 add_back" style="width: 205px;">
								<i>发布</i>
								<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>加入期</i>
								<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1" style="width: 205px;">
								<i>开始收益</i>
								<span>暂未</span>
							</div>
							<div class="circle1" style="width: 205px;">
								<i>定期收益</i>
								<span>暂未</span>
							</div>
							<div class="circle2">
								<i>到期退出</i>
								<span>暂未</span>
							</div>
						</c:when>
						<c:when
							test="${borrow.borrowStatus ==5 || borrow.borrowStatus ==7 ||borrow.borrowStatus ==10 || borrow.borrowStatus ==11 || borrow.borrowStatus ==12}">
							<div class="circle1 add_back" style="width: 205px;">
								<i>发布</i>
								<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>加入期</i>
								<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>开始收益</i>
								<span><fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1" style="width: 205px;">
								<i>定期收益</i>
								<span>每月<fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="dd" />号</span>
							</div>
							<div class="circle2">
								<i>到期退出</i>
								<span><fmt:formatDate value="${borrow.repaymentTime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
						</c:when>
						<c:when test="${borrow.borrowStatus ==6 }">
							<div class="circle1 add_back" style="width: 205px;">
								<i>发布</i>
								<span><fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>加入期</i>
								<span><fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>开始收益</i>
								<span><fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="MM-dd" /></span>
							</div>
							<div class="circle1 add_back" style="width: 205px;">
								<i>定期收益</i>
								<span>每月<fmt:formatDate value="${borrow.verifyReviewTime}" type="both" pattern="dd" />号</span>
							</div>
							<div class="circle2 add_back">
								<i>到期退出</i>
								<span><fmt:formatDate value="${borrow.repaymentTime}" type="both" pattern="yyyy-MM-dd" /></span>
							</div>
						</c:when>
					</c:choose>

				</div>

			</c:if>
			</div>
        <input id="verifyReviewTime" type="hidden" value="${borrow.verifyReviewTime}"/>
			<div class="plan_del">
				<div class="title" id="xmOption">
					<a href="javascript:;" class="onchoice" onclick="changeTabs(this,0)">项目简介</a>
					<a href="javascript:;" onclick="changeTabs(this,1)">项目详情</a>
					<a href="javascript:;" onclick="changeTabs(this,2)">相关资料披露</a>
					<a href="javascript:;" onclick="changeTabs(this,3)">加入记录</a>
				</div>
				<div class="detail_con">
					<!-- 项目简介 begin -->
					<div class="pro1" id="projectProfile">
						<div>${borrowContent}</div>
					</div>
					<!-- 项目简介 end -->
					<!-- 项目详情 begin -->
					<ul class="pro1 detial1" id="biaoDetail" style="display: none;">
		              <div id="xszx2">
						<li>
							<i>计划名称</i>
							<span>${borrow.borrowTitle  }</span>
						</li>
						<!-- <li>
							<i>理财计划简介</i>
							<span id="lcjhjj1" >${borrowContent}</span>
						</li>
						<div style="display: none;">
							${borrowContent}
						</div> -->
						<li>
							<i>可加入日期</i>
							<span>
								<fmt:formatDate value="${borrow.publishDatetime}" type="both" pattern="yyyy-MM-dd" /> 
								至 <fmt:formatDate value="${borrow.allowTenderTime}" type="both" pattern="yyyy-MM-dd" />
							</span>
						</li>
						<input id="publishDatetime" type="hidden" value="${borrow.publishDatetime}"/>
						<input id="allowTenderTime" type="hidden" value="${borrow.allowTenderTime}"/>
						<li>
							<i>锁定期</i>
							<span>
								<c:if test="${borrow.isDay==1 }">${borrow.borrowTimeLimit}天</c:if> 
								<c:if test="${borrow.isDay==2 }">${borrow.borrowTimeLimit}个月</c:if>
							</span>
						</li>
						<li>
							<i>预期年化收益率</i>
							<span>${borrow.annualInterestRate }%</span>
						</li>
						<li>
							<i>加入条件</i>
							<span>
								<c:choose>
									<c:when test="${CACHE_SYS_FEES_RATE.sysInvestMinmoney > borrow.minAmount}">
										${CACHE_SYS_FEES_RATE.sysInvestMinmoney}
									</c:when>
									<c:otherwise>
										${borrow.minAmount }
									</c:otherwise>
								</c:choose> 元起
							</span>
						</li>
						<!-- <li><i>收益方式</i><span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span></li> -->
						<li>
							<i>还款方式</i>
							<span>${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</span>
						</li>
						<li>
							<i>起息日</i>
							<span>起息日为该理财计划锁定期开始日当天</span>
						</li>
						<div id="bdzc">
							<li>
								<i>标的资产</i>
								<span>优质住宅房产抵押贷款</span>
							</li>
						</div>
						<li>
							<i>保障方式</i>
							<span id="bzfs">优质住宅房产抵押 100%本金保障</span>
						</li>
						<div id="tcsm">
							<li>
								<i>退出说明</i>
								<span>转让专区转让</span>
							</li>
						</div>
						<div id="dqsj"></div>
						<li>
							<i>费用说明</i>
							<span>加入费率：0.00%；退出费用：0.00%</span>
						</li>
					 </div>
					 <c:set var="bc" value="${borrowContent}"/>
				     <div id="lcjhjj" style="display: none;">${borrowContent}</div>
						<!-- <li>
							<i>协议文本</i>
							<span>见
								<c:if test="${agreementStatus==1 }">
									<a href="${path }${agreementPath}">《浩茗投资协议》</a>
								</c:if>
								<c:if test="${agreementStatus==0 }">
									<a href="javascript:alertc('投资满标复审后可查看');">《浩茗投资协议》</a>
								</c:if>
							</span>
						</li> -->
					</ul>
					<!-- 项目详情 end -->
					<!-- 相关资料披露 begin -->
					<div class="pro1" style="display: none; padding: 15px;" id="qiyeinfo">
						<div class=rollphotos style="width: auto;">
							<div class=blk_29>
								<div class=LeftBotton id=LeftArr></div>
								<div class=Cont id=ISL_Cont_1>
									<!-- 图片列表 begin -->

									<c:forEach items="${att }" var="att" varStatus="a">
										<div class="box">
											<a class="imgBorder fancybox-buttons"
												data-fancybox-group="button"
												href="${path }${att.attachPath}" target=_blank
												alt="${att.attachTitle }">
												<img src="${path }${att.attachPath}" alt="" style="height: 140px;width: 174px;"/></a>
										</div>
									</c:forEach>
								</div>
								<div class=RightBotton id=RightArr></div>
							</div>
						</div>
					</div>
					<!-- 相关资料披露 end -->
					<!-- 加入记录 begin -->
					<div class="pro1" style="display: none;" id="record">
						<table class="pro_num">
							<thead>
								<tr>
									<th width="184">投资人</th>
									<th width="320">投资金额</th>
									<th width="280">投标时间</th>
									<th width="194">投标方式</th>
								</tr>
							</thead>
							<tbody id="borrowTenderList"></tbody>
						</table>
						<div class="page_num de_num" style="padding-top: 40px;" id="divs"></div>
					</div>
					<!-- 加入记录 end -->
				</div>
				<!-- plan_del -->
			</div>
			<!-- content_in -->
		</div>
		<!-- content -->

		<jsp:include page="/foot.do"></jsp:include>
		<%@ include file="../onlineSupport.jsp"%>
		<div id="fade" class="black_overlay"></div>
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script type="text/javascript">
/* function fullTenderPage(){
	if($("#tenderMoney").val()==''||$("#payPassword").val()){
		alertc("请填写投标金额和支付密码");

	}else{
		$("#tenderAmount").val($("#tenderMoney").val());
		$("#payPasswords").val($("#payPassword").val());
	}
	
} */
	var borrowTime = 0;
	var nowDate=0;
	var bStatus = "${borrow.borrowStatus }";
	var Published = ${Published}; //发布状态
	var intereValId = null;
	var hasLogin = ${hasLogin};
	var noSetPayPassword = false;
	var decstatus = 0;
	var  deductionMoneyPercent = 1 ;
	var activityStatus = ${activitySwitch};
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
	$(function(){
		deductionMoneyPercent = ${deductionMoneyPercent };
		yanchi();
		nowDate=${nowDate};
		changeEndTime();
		var data={};
		if(Published){
			if(bStatus == '2'){
				intereValId = setInterval(changeEndTime,1000);
				//设置字母提示
				enabledBtn();
			}else{
				disabledBtn();
				$("#lastDateTimeDiv").html("已结束：<span ><i>0</i> 天 <i>00</i> 时 <i>00</i> 分 <i>00</i> 秒</span>");
			}
		}else{
			intereValId = setInterval(changeEndTime,1000);
			disabledBtn();
			$('#modaltrigger').html("预告中");
		}
		var borrowId=$("#borrowId").val();
		var data = {"borrowId":borrowId};
		
		getPageFrom4BorrowInfo(data,
				"${path }/borrow/shwoBorrowTenderInfoByPage.do",
				getTenderRecord, "borrowTenderList",null,"divs");
		changeTopHover(2);
		//抵扣金 按钮加事件
		$('input[name="deduction"]').change(function(){
			 decstatus = $('input[name="deduction"]:checked').val();
			if(decstatus == 1 ){
				$("#deductionDiv").css("display","block");
				$("#deductionStatus").val("1");
			}else{
				$("#deductionDiv").css("display","none");
				$("#deductionStatus").val("0");
			}
		})
		//如果是新手标，那么就不能使用抵扣金
		var borrowTypeName = "${borrowType.code }";
		if(borrowTypeName == "XINSHOU"){
			$("#deductionDivIs").css("display","none");
		}
	})
	function changeTabs(obj, index) {
		$(obj).parent().find(".onchoice").removeClass("onchoice");
		$(obj).addClass("onchoice");
		if (index == 0) {
			$("#projectProfile").show();
			$("#biaoDetail").hide();
			$("#record").hide();
			$("#qiyeinfo").hide();
		}else if (index == 1) {
			$("#projectProfile").hide();
			$("#biaoDetail").show();
			$("#record").hide();
			$("#qiyeinfo").hide();
		} else if(index == 2){
			$("#projectProfile").hide();
			$("#record").hide();
			$("#biaoDetail").hide();
			$("#qiyeinfo").show();
		}else{
			$("#projectProfile").hide();
			$("#biaoDetail").hide();
			$("#record").show();
			$("#qiyeinfo").hide();
		}
	}
	function saveTender() {
		var availableMoney=${userAccount.availableMoney }+"";
		$("#tenderFrm").validate({						  
			rules: {
				tenderAmount: {
					required: true,
					number:true
				},
			
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
					error.appendTo(element.parent());
			}
			
		});
		if($("#tenderFrm").valid()){
			tenderFrm.payPassword.value=encryptByDES(tenderFrm.payPassword.value,tenderFrm.publickey.value);
		    var tenderMoney = $("#tenderAmount").val();
		    var userValidity =${userAccount.availableMoney };
		    
		    <c:if test="${borrowType.code eq 'XINSHOU' }">
			if ("${isFirst}" != 0) {
				alertc("对不起，您不是首次投资,不能继续购买新手标！");
				return;
			}
			</c:if>
		    //判断是否设置了支付密码
			if(noSetPayPassword){
				alertc("请先设置支付密码!",jumpToSetPayPassword);
				return;
			}
			//判断活动开关是否开启：value：0,得到选择，是为抵扣金还是红包
			if(activityStatus == 1){
				var borrowTypeName = "${borrowType.code}"
				//适用于长期标
				if(decstatus == 1){
					if(borrowTypeName == "MINGXING" || borrowTypeName == "LIUYUE" || borrowTypeName == "JIUYUE" ||borrowTypeName == "SHIERYUE" ){
						$("#payPassword").val("");
						
						$("#deductionMoney").val("");
						$("#payPassword2").val("");
						alertc("抵扣金不能适用于长期标，请重新选择优惠！");
						return ;
					}
				}else{//使用于短期标
					if(borrowTypeName == "HUODONG" || borrowTypeName == "ZHENGFEN" ){
						alertc("红包不能适用于短期标，请重新选择优惠！");
						$("#payPassword").val("");
						$("#payPassword2").val("");
						return ;
					}
				}
			}
		    //使用抵扣金\判断可用余额
		    if(decstatus == 1){
		    	var deductionMoney = $("#deductionMoney").val();
			    var flag = deductionValid();
			    if(true == flag){
			    	var newAmount = parseFloat(tenderMoney) - parseFloat(deductionMoney);
			    	if(parseFloat(userValidity) < parseFloat(newAmount)){
			    		alertc("可用余额不足，请先充值？",goToRecharge);
			    		return ;
			    	}
					confirmc("确认投资金额" + tenderMoney + "元，使用抵扣金"+deductionMoney+"元?", tenderConfirmed);
			    }else{
			    	return ;
			    }
		    }else{
		    	if(parseFloat(userValidity) < parseFloat(tenderMoney)){
		    		alertc("可用余额不足，请先充值？",goToRecharge);
		    		return ;
		    	}
		    	confirmc("确认投资金额" + tenderMoney + "元?", tenderConfirmed);
		    }
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
					  $('#loginmodal').hide();
					  $('.J-xmxq-mask').hide();
					  //还原单选状态
					  $("input[name='deduction'][value='0']").attr("checked",true);  
					  $("#deductionMoney").val("");
					  alertc("投标成功",reload);
					  
				}else if(data.code=='256'){
					
				alertc(data.msg,callBack);
				$("#payPasswords").removeAttr("readonly");
				$("#payPasswords").val("");
				}else{
				alertc(data.msg,callBack);
				$("#payPasswords").val($("#payPassword2").val());
				}
			}
		});
	}
	function reload(){
		  setInterval(window.location.reload(),2000);
	}
	/* //输入抵扣金时，投资金额跟随着抵扣金而变化
	function flashTender(){
		var deductionMoney = $("#deductionMoney").val();
		var tenderMoney = $("#tenderAmount").val();
		$("#tenderAmount").val(parseInt(tenderMoney) - parseInt(deductionMoney));
	}
	 */
	//判断抵扣金的验证
	function deductionValid(){
		//1、输入值不能大于可用抵扣金
		//2、输入值最多为投资金额的1%
		var deductionMoney = $("#deductionMoney").val();
		var availableDeduction=${userAccount.deductionMoney };
		if(deductionMoney == null || deductionMoney ==""){
			alertc("请输入你的抵扣金数目！");
			$("#payPassword").val("");
			$("#payPassword2").val("");
			return false;
		}
		if(parseFloat(deductionMoney) > parseFloat(availableDeduction)){
			alertc("使用的抵扣金不能大于你拥有的抵扣金，请重新输入！");
			$("#deductionMoney").val("");
			$("#payPassword").val("");
			$("#payPassword2").val("");
			return false;
		}
		var tenderMoney = $("#tenderAmount").val();
		var percent = deductionMoney / tenderMoney * 100;
		if(parseFloat(percent) > parseFloat(deductionMoneyPercent)){
			alertc("抵扣金最多只能为投资金额的"+deductionMoneyPercent+"%，请重新输入！")
			$("#deductionMoney").val("");
			$("#payPassword").val("");
			$("#payPassword2").val("");
			return false;
		}
		//抵扣金修改为只试用与一月标和45天标
		var borrowTypeName = "${borrowType.code}";
		if(borrowTypeName == "MINGXING" || borrowTypeName == "LIUYUE" ||borrowTypeName == "JIUYUE"||borrowTypeName == "SHIERYUE"){
			alertc("长期标不能使用抵扣金，请重新选择！");
			$("#payPassword2").val("");
			return false;
		}
		return true ;
	}
	 function dealTimeZero(){
		window.clearInterval(intereValId);
		if(Published){
			/* document.getElementById("lastDateTimeDiv").innerHTML="已结束"; */
			$("#lastDateTimeDiv").html("已结束：<span ><i>0</i> 天 <i>00</i> 时 <i>00</i> 分 <i>00</i> 秒</span>");
			disabledBtn();
			$('#modaltrigger').html("已流标");
		}else{
			Published = true;
			enabledBtn();
		}
	}
	 
	function jumpToSetPayPassword(){
		window.location.href = "${path }/verify/saveUserLoginPass.html?type=paymentPassword";
	}
	
	function goToRecharge(){
		window.location.href = "${path }/account/recharge.html";
	}
	//投资记录
	function getTenderRecord(data,i){
				var  investTime= data.tender_addtime;
				investTime = toDate(investTime, "yyyy-MM-dd hh:mm");
				var fullNameInvestor = data.investorAccount;
				var investor = fullNameInvestor.subChsString(0, 2)
						+ '***'
						+ fullNameInvestor.subChsString(
								fullNameInvestor.lengthStr() - 2, 2);
				var type='';
				if(data.tender_type==1){
					type = "手动投标";
				}else if(data.tender_type==2){
					type = "自动投标";
				}else{
					type = "债权转让";
				}
				var tenderStatus = data.tender_status + "";
				var content = "";
				if(i%2 == 0 ){
					content='<tr style="background:#f7f8fa;">'
				}else{
					content='<tr>'
				}
					content=content + '<td>'
							+investor+'</td><td>￥'
							+ addCommas(data.tender_amount, 2) + '</td><td>'
							+investTime+'</td><td>'
							+type+'</td>';	
			return content;
	}
	//倒计时
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
				
		 	return '<i>'+int_day+'</i> 天 <i>'+int_hour+'</i> 时 <i>'+int_minute+'</i> 分 <i>'+int_second+'</i> 秒';
		}
	//更改时间
	function changeEndTime(){	
		if(Published){
			borrowTime = ${allowTenderTime };
			//可投时间：<span ><i>01</i> 天 <i>03</i> 时 <i>21</i> 分 <i>34</i> 秒</span>
			$("#lastDateTimeDiv").html("可投时间：<span >"+getTimeStr()+"</span>");
		}else{
			borrowTime = ${publishTime };
			$("#lastDateTimeDiv").html("开始时间:"+getTimeStr());
			//document.getElementById("lastDateTime").innerHTML=getTimeStr();
		}
	 	
	}
	//马上投资时，设置状态
	function enabledBtn(){
		$("#tenderAmount").val("");
		$('#modaltrigger').html('马上投资');
		$('#tenderMoney').removeAttr("disabled");
		$('#payPassword').removeAttr("disabled");
		$('#modaltrigger').removeAttr("disabled");
		  $('#loginform').submit(function(e){
			return false;
		  });
		  $('#modaltrigger').leanModal({ top: 110, overlay: 0.45, closeButton: ".hidemodal" });
		  $('.J-xmxq-close').click( function(){
			  $('#loginmodal').hide();
			  $('.J-xmxq-mask').hide();
			  });
		  $('#modaltrigger').click(function(){
			  if($("#tenderMoney").val()==''||$("#payPassword2").val()==''){
					alertc("请填写投标金额和支付密码");
					 $('#loginmodal').hide();
			  }else{
				  $('.J-xmxq-mask').show(); 
				  $("#tenderAmount").val($("#tenderMoney").val());
					$("#payPasswords").val($("#payPassword2").val());
			  }
			  });
		  $('.J-xmxq-mask').click(function(){
			  $(this).hide();
			  $('#loginmodal').hide();
			 });
	}
	//不能投资时，不可显示马上投资
	function disabledBtn(){
		var borrowStatusTender = ALL_STATUS[bStatus];
		$('#tenderMoney').attr("disabled","disabled");
		$('#payPassword').attr("disabled","disabled");
		$('#modaltrigger').attr("disabled","disabled");
		$('#modaltrigger').html(borrowStatusTender);
	}
	//自动填充金额
	function fillTenderMoney(){
			//最大金额
		   var tenderMax1 = "${borrow.maxAmount}";
		   var tenderMax = Math.floor(tenderMax1);
		   var url="${path}/borrowTender/getTotalNoSuccess.do";
		   var obj=$(this);
		   $.post(url,{borrowId:$("#borrowId").val()},function (data){
			   if(tenderMax !='' && tenderMax !=0 && data > tenderMax){
				   $("#tenderAmount").val(tenderMax);
			   }else{
			   	   $("#tenderAmount").val(data);
			   }
		   });
	};
	/*
	 *   功能:实现VBScript的DateAdd功能.
	 *   参数:interval,字符串表达式，表示要添加的时间间隔.
	 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
	 *   参数:date,时间对象.
	 *   返回:新的时间对象.
	 *   var now = new Date();
	 *   var newDate = DateAdd( "d", 5, now);
	 *---------------   DateAdd(interval,number,date)   -----------------
	 */
	function DateAdd(interval, number, date) {
	    switch (interval) {
	    case "y": {
	        date.setFullYear(date.getFullYear() + number);
	        return date;
	        break;
	    }
	    case "q": {
	        date.setMonth(date.getMonth() + number * 3);
	        return date;
	        break;
	    }
	    case "m": {
	        date.setMonth(date.getMonth() + number);
	        return date;
	        break;
	    }
	    case "w": {
	        date.setDate(date.getDate() + number * 7);
	        return date;
	        break;
	    }
	    case "d": {
	        date.setDate(date.getDate() + number);
	        return date;
	        break;
	    }
	    case "h": {
	        date.setHours(date.getHours() + number);
	        return date;
	        break;
	    }
	    case "mm": {
	        date.setMinutes(date.getMinutes() + number);
	        return date;
	        break;
	    }
	    case "s": {
	        date.setSeconds(date.getSeconds() + number);
	        return date;
	        break;
	    }
	    default: {
	        date.setDate(date.getDate() + number);
	        return date;
	        break;
	    }
	    }
	}
	 function jsTimeToString(time){   
		    var year=time.getFullYear();   
		    var month=time.getMonth()+1;   
		    var day=time.getDate();   
		    var hour=time.getHours();   
		    var minute=time.getMinutes();   
		    var second=time.getSeconds();   
		    if(month<10){   
		        month="0"+month;   
		    }   
		    if(day<10){   
		        day="0"+day;   
		    }   
		    if(hour<10){   
		        hour="0"+hour;   
		    }   
		    if(minute<10){   
		        minute="0"+minute;   
		    }   
		    if(second<10){   
		        second="0"+second;   
		    }   
		    var strTime = year+"-"+month+"-"+day;//+" "+hour+":"+minute+":"+second;    
		return strTime;   
		} 
	//1-8
	$(document).ready(function() {
		$('#qxr').html('');
	   // $('#xmOption').children("a:eq(1)").hide();
	    $('#xgzlpl').html('');
	    $('#bdzc').html('');
	    $('#bzfs').html('风险准备金逾期垫付，100%本息保障，阳光保险资金安全保障');
	    $('#tcsm').html('');
	    //开始日期
	    var startDate = '';
	    //var Date = '';
	    //加入期
	    var startDate = $('#allowTenderTime').val();
	    var percentage = $('#percentage').val();
	    if(percentage == '100.00'){
	    	startDate = $('#verifyReviewTime').val();
	    }
	    var startDate1 = new Date(startDate);
	    
	    startDate1 = startDate1.setHours(startDate1.getHours()-14);
	    startDate1 = new Date(startDate1);
	    //锁定期
	    if($('#isDay').val()=='1'){
	    	var dates = parseInt($('#borrowTimeLimit').val());
	    	startDate1 = DateAdd("d",dates,startDate1);
	    }else if($('#isDay').val()=='2'){
	    	var dates = parseInt($('#borrowTimeLimit').val());
	    	startDate1 = DateAdd("m",dates,startDate1);
	    }
	    startDate1 = new Date(startDate1);
	    var newDate = jsTimeToString(startDate1);
	    $('#dqsj').html('<li><i>到期时间</i><span>'+newDate+'</span></li>');
		//---------------
		var borrowTitle = $('#borrowTitle').val();
		//发标日期
		var diffDate = $('#publishDatetime').val();
		//区分新旧版本日期
		var nowDateTime = '2016-01-18';
		diffDate = new Date(diffDate);
		nowDateTime = new Date(nowDateTime);
		//如果发标日期大于区分日期，新手专享、争分夺秒、理财计划则应用新样式
		if(diffDate.getTime() >= nowDateTime.getTime()){
			if(borrowTitle.indexOf("浩茗金融计划一月标")>= 0
					|| borrowTitle.indexOf("浩茗金融计划三月标")>= 0
					|| borrowTitle.indexOf("浩茗金融计划六月标")>= 0
					|| borrowTitle.indexOf("浩茗金融计划九月标")>= 0
					|| borrowTitle.indexOf("浩茗金融计划十二月标")>= 0){
				$('#xszx2').children("li:eq(1)").css('display','block');
				$('#xszx2').children("li:eq(1)").children("i")
				                                 .css('display','block')
				                                 .css('float','left');
				$('#lcjhjj1').css('display','block')
				             .css('float','left')
				             .css('width','830px')
				             .css('height','auto');
				$('#lcjhjj1 *').css('clear','left');
			}else if(borrowTitle.indexOf("争分夺秒")>= 0 || borrowTitle.indexOf("新手专享")>= 0){
				//	$('#xmOption').children("a:eq(1)").show();
				$('#xszx2').html('');
				$('#xszx1').html('');
				$('#lcjhjj').css('display','block')
				            .css('margin-left','50px')
				            .css('margin-right','50px');
			};
		};
	});
</script>
<SCRIPT src="${frontPath }/js/ScrollPic.js" type=text/javascript></SCRIPT>
<SCRIPT language=javascript type=text/javascript>
       var scrollPic_02 = new ScrollPic();
       scrollPic_02.scrollContId   = "ISL_Cont_1"; //内容容器ID
       scrollPic_02.arrLeftId      = "LeftArr";//左箭头ID
       scrollPic_02.arrRightId     = "RightArr"; //右箭头ID
       scrollPic_02.frameWidth     = 908;//显示框宽度
       scrollPic_02.pageWidth      = 205; //翻页宽度
       scrollPic_02.speed          = 10; //移动速度(单位毫秒，越小越快)
       scrollPic_02.space          = 10; //每次移动像素(单位px，越大越快)
       scrollPic_02.autoPlay       = false; //自动播放
       scrollPic_02.autoPlayTime   = 3; //自动播放间隔时间(秒)
       scrollPic_02.initialize(); //初始化  
       function recharge(){
    	   $("#J-xmxq-mask").css("display","none");
    	   $("#loginmodal").css("display","none");
    	   window.open("${path }/account/recharge.html");
       }
</SCRIPT> 
</html>