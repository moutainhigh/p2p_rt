<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<%-- <script type="text/javascript" src="<%=path %>/common/js/city.js"></script>
<script type="text/javascript" src="<%=path %>/common/front/js/dic-area.js"></script>
<script type="text/javascript" src="<%=path %>/common/front/js/jquery.js"></script> --%>

    <script type="text/javascript" src="${frontPath}/js/ScrollPic.js"></script>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/quickBorrow/Style.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/quickBorrow/index.css" />
<script type="text/javascript" src="${path }/common/js/common.js"></script>
<title>快速借款-${showTitle}</title>
<meta name="Keywords" content="我要借款">
<meta name="Description" content="浩茗金融旗下的我要借款栏目,帮助借款用户以低成本获得相应的资金支持,借款平台专注普惠金融,旨在为借款者提供创业基金,更好地促进中小企业的发展,客户可以提出自己的申请,浩茗金融进行线下的审核评估从而给出相应的融资方案.">
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-jiekuang">
         <div class="J-jk-banner"><img src="${frontPath }/images/J-wyjk-001.png"/></div>
         <div class="J-jk-content nav-con clearfix">
          <div class="J-jkcon-content jboxsize clearfix">
           <ol class="clearfix J-jkcc-title">
            <li class="J-jkc-select" style="cursor: pointer;"><p class="J-jkcct-one">个人借款</p></li>
            <li style="cursor: pointer;"><p class="J-jkcct-two">企业借款</p></li>
           </ol>
          <div class="J-jkcc-con">
           <div class="J-jkcc-list clearfix jboxsize">
             <div class="J-jkccl-L jboxsize">
              <p class="J-jkccl-tit">浩茗金融平台的借款功能旨在帮助借款用户以低成本获得借款。用户在需要资金时，可通过抵押、质押、信用、担保等借款方式申请借款，浩茗金融线下审核评估后，将会根据借款方式给出融资方案。</p>
              <img src="${frontPath }/images/J-wyjk-004.png"/>
              <dl>
               <dt>申请条件：</dt>
               <dd><i>1、</i><p>年龄在22周岁（含）—55周岁（含），具有完全民事行为能力的自然人；</p></dd>
               <dd><i>2、</i><p>中华人民共和国大陆公民，港、澳、台除外；</p></dd>
               <dd><i>3、</i><p>具有稳定收入来源；</p></dd>
               <dd><i>4、</i><p>在现单位工作满6个月及以上（不含试用期）。</p></dd>
              </dl>
              <dl>
               <dt>申请材料：</dt>
               <dd><i>1、</i><p>二代身份证（须在有效期内，不认可护照、户口簿、临时身份证等）；</p></dd>
               <dd><i>2、</i><p>个人信用报告(进件日期与信用报告开具日期应在15天内有效)；</p></dd>
               <dd><i>3、</i><p>收入证明（发薪流水、税单等）；</p></dd>
               <dd><i>4、</i><p>工作证明（劳动合同、工牌、单位开具的工作证明等）；</p></dd>
               <dd><i>5、</i><p>居住证明（公用事业缴费单、户口簿、银行对账单等）；</p></dd>
               <dd><i>6、</i><p>资产证明（房产证、车辆登记证、定期存款等，此项可选）。</p></dd>
              </dl>
             </div>
             <div class="J-jkccl-R">
              <div class="J-jkcclr-con">
               <h2>填写借款申请</h2>
               <div class="J-jkcclr-listcon clearfix">
                <ol>
                 <li>申请人</li>
                 <li>手机号码</li>
                 <li>所在地区</li>
                 <li>借款金额</li>
                 <li>借款期限</li>
                 <li>借款方式</li>
                 <li>借款用途</li>
                 <li>验证码</li>
                </ol>
               <form action="#" method="post" id="quickFrm">
                <ul>
                 <li><input type="text" value="${FRONT_USER_SESSION.userAccount}" name="userName" readonly="readonly"/></li>
                 <li><input type="text" name="userTel" value="${FRONT_USER_SESSION.userPhone}" readonly="readonly"/></li>
                 <li><input type="text" name="userArea" value="${FRONT_USER_SESSION.userAddress}"/></li>
                 <li class="J-jkcclr-l-jine clearfix"><input type="text" name="borrowAmount" onkeyup="value=value.replace(/[^0-9|.]/g,'')"/><span>万</span></li>
                 <li class="J-jkcclr-l-jine clearfix"><input type="text" name="borrowPeriod" /><span>月</span></li>
                 <li>
                 	<select name="borrowWay" class="required" id="pBorrowWay" style="width: 280px;height: 39px;border: 1px solid #DDDDDD;">
                        <option value="" selected="selected">--信用类型--</option>
                            <c:forEach items="${BORROW_ALL_CREDIT_TYPE }" var="item">
                                <option value="${item.key }">${item.value }</option>
                            </c:forEach>
                    </select>
                 </li>
                 <li><input type="text" name="borrowUse" ></li>
                 <li class="J-jkcclr-yzm"><input type="text" name="captcha"/><img  src="<%=path%>/captcha.svl" id="captcha"
															onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1"
															valign="bottom" alt="点击更新" title="点击更新" /><a onclick="changeImg('captcha')">换一张</a></li>
                 <li class="J-btn" style="width:100%;">
                 	 <a onclick="saveUser()"  style="width:100%;">提交申请</a>
                 </li>
                </ul>
                </form>
               </div>
              </div>
             </div>
           </div>
           <!---企业申请star---->
           <div class="J-jkcc-list clearfix jboxsize" style="display:none;">
            <div class="J-jkccl-L jboxsize">
              <p class="J-jkccl-tit">浩茗金融平台的借款功能旨在帮助借款用户以低成本获得借款。用户在需要资金时，可通过抵押、质押、信用、担保等借款方式申请借款，浩茗金融线下审核评估后，将会根据借款方式给出融资方案。</p>
              <img src="${frontPath }/images/J-wyjk-004.png"/>
              <dl>
               <dt>申请条件：</dt>
               <dd><i>1、</i><p>年龄在25周岁（含）至55周岁（含）且具有完全民事行为能力的自然人；</p></dd>
               <dd><i>2、</i><p>企业实际经营年限满3年及以上；</p></dd>
               <dd><i>3、</i><p>企业年销售额5000万及以上；</p></dd>
               <dd><i>4、</i><p>企业信用记录良好，2年内无不良记录。</p></dd>
              </dl>
              <dl>
               <dt>申请资料：</dt>
               <dd><i>1、</i><p>法人代表及配偶身份证、股东身份证、财务主管身份证；</p></dd>
               <dd><i>2、</i><p>企业资料：企业三证、开户许可证、公司章程、验资报告、经营许可证、贷款卡等；</p></dd>
               <dd><i>3、</i><p>经营材料：财务年度报表、增值税纳税申报表、银行结算账户对账单、上下游购销合同、办公租赁合同及租金发票；</p></dd>
               <dd><i>4、</i><p>企业/公司相关简介；</p></dd>
               <dd><i>5、</i><p>资产证明：房产证、车辆登记证、定期存款、有价证券等；</p></dd>
               <dd><i>6、</i><p>抵押物相关权证。</p></dd>
              </dl>
             </div>
             <div class="J-jkccl-R">
              <div class="J-jkcclr-con">
               <h2>填写借款申请</h2>
               <div class="J-jkcclr-listcon clearfix">
                <ol>
                 <li>申请人</li>
                 <li>联系方式</li>
                 <li>所在地区</li>
                 <li>借款金额</li>
                 <li>借款期限</li>
                 <li>借款方式</li>
                 <li>公司主营行业</li>
                 <li>公司月净利润</li>
                 <li>借款用途</li>
                 <li>验证码</li>
                </ol>
                <form action="" name="formEnterprise" id="formEnterprise">
                <ul>
                 <li><input type="text" name="userName" id="userName"/></li>
                 <li><input type="text" name="userTel" id="userTel" onblur="checkPhone()" maxlength="11" onkeyup="value=value.replace(/[^0-9|.]/g,'')"/></li>
                 <li><input type="text" name="userArea" id="userArea"/></li>
                 <li class="J-jkcclr-l-jine clearfix"><input type="text" name="borrowAmount" id="borrowAmount"/><span>万</span></li>
                 <li class="J-jkcclr-l-jine clearfix"><input type="text" name="borrowPeriod" id="borrowPeriod"/><span>月</span></li>
                 <li>
                 	<select name="borrowWay" class="required" id="borrowWay" style="width: 280px;height: 39px;border: 1px solid #DDDDDD;">
                        <option value="" selected="selected">--信用类型--</option>
                            <c:forEach items="${BORROW_ALL_CREDIT_TYPE }" var="item">
                                <option value="${item.key }">${item.value }</option>
                            </c:forEach>
                    </select>
                 </li>
                 <li><input type="text" name="mainUse" id="mainUse"/></li>
                 <li class="J-jkcclr-l-jine clearfix"><input type="text" name="interestMonth" id="interestMonth"/><span>元</span></li>
                 <li><input type="text" name="borrowUse" id="borrowUse"/></li>
                 <li class="J-jkcclr-yzm"><input type="text" name="captcha" id="captchaVal"/><img id="captchaImg" src="<%=path%>/captcha.svl"
															onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1"
															valign="bottom" alt="点击更新" title="点击更新" /><a onclick="changeImg('captchaImg')">换一张</a></li>
                 <li class="J-btn" style="width:100%;"><a onclick="saveEnterprise();" style="width:100%;">提交申请</a></li>
                </ul>
                </form>
               </div>
              </div>
             </div>
           </div>
          </div>
          <!--J-jkcc-con end--->
         </div>
        </div>
        <!---小轮播图开始---->
        <div class="J-little-banner jmt30 hide">
         <div class="J-lb-con nav-con">
           <!--滚动图片 start-->
            <DIV class=rollphotos>
                <DIV class=blk_29>
                	<DIV class=LeftBotton id=LeftArr></DIV> 
                    <DIV class=Cont id=ISL_Cont_1>
                        <!-- 图片列表 begin -->
                        <c:forEach var="data" items="${borrowList}">
                        <div class=box>
                        <dl>
                        <dt><img src="${path }/common/front/images/J-pic.png"/></dt>
                         <dd><i>${ data.userName}</i></dd>
                         <dd><p>贷款：<span>${ data.borrowAmount}元</span> 状态：
                         <c:if test="${ data.status == 1}">待审核</c:if>
                          <c:if test="${ data.status == 2}">初审通过</c:if>
                           <c:if test="${ data.status == 3}">初审未通过</c:if>
                           <c:if test="${ data.status == 5}">复审通过</c:if>
                         <%-- <c:choose>
                         <c:when test=" ${ data.status == 1}">待审核</c:when>
                         <c:when test="  ${ data.status eq 2}">初审通过</c:when>
                          <c:when test="  ${ data.status eq 3}">初审未通过</c:when>
                           <c:otherwise>复审通过</c:otherwise> 
                         </c:choose> --%>
                       </p></dd>
                        </dl>
                       </div>
                        </c:forEach>
                       
                        <!-- 图片列表 end -->
                   </DIV>
                   <DIV class=RightBotton id=RightArr></DIV> 
                </DIV>
                
                <SCRIPT language=javascript type=text/javascript>
                    <!--//--><![CDATA[//><!--
                    var scrollPic_02 = new ScrollPic();
                    scrollPic_02.scrollContId   = "ISL_Cont_1"; //内容容器ID
                    scrollPic_02.arrLeftId      = "LeftArr";//左箭头ID
                    scrollPic_02.arrRightId     = "RightArr"; //右箭头ID
                    scrollPic_02.frameWidth     = 1030;//显示框宽度
                    scrollPic_02.pageWidth      = 257; //翻页宽度
                    scrollPic_02.speed          = 10; //移动速度(单位毫秒，越小越快)
                    scrollPic_02.space          = 10; //每次移动像素(单位px，越大越快)
                    scrollPic_02.autoPlay       = true; //自动播放
                    scrollPic_02.autoPlayTime   = 1; //自动播放间隔时间(秒)
                    scrollPic_02.initialize(); //初始化							
                    //--><!]]>
                </SCRIPT> 
              </DIV> 
            <!--滚动图片 end-->   
         </div>
        </div>
       </div>
	<jsp:include page="/foot.do"></jsp:include>
	<script type="text/javascript">
	changeTopHover(4);
	function checkPhone() {
		var phonePartten = /^1[3|4|5|8|7][0-9]\d{8}$/;
			if (phonePartten.test($("#userTel").val()) == false) {
			alertc("手机号码格式有误");
		} 
	}
function saveUser() {
	 if($("input[name='userArea']").val()==''||$("input[name='borrowAmount']").val()==''||$("input[name='borrowPeriod']").val()==''||$("#pBorrowWay").val()==''||$("input[name='borrowUse']").val()==''||$("input[name='captcha']").val()==''){
	alertc("请填写完整信息");
	 initCaptcha();
	}else{
		saveSubmit();
		
	} 
	
	}
	function saveSubmit() {
		var data = $('#quickFrm').serialize();
		$.ajax({
			cache : false,
			type : "POST",
			url : "${path }/quickBorrow/save.do",
			data : data,
			async : false,
			error : function(request) {
				alertc("服务器异常");				
				   initCaptcha();
			},
			success : function(data) {
				alertc(data.msg);
				  initCaptcha();
			}
		});
	}
	
	//企业快速借款
	function saveEnterprise(){
		if($("#userName").val()==''||$("#userTel").val()==''||$("#userArea").val()==''||
			$("#borrowAmount").val()==''||$("#borrowPeriod").val()==''||$("#borrowWay").val()==''||
			$("#borrowUse").val()==''||$("#captchaVal").val()==''||$("#mainUse").val()==''||$("#interestMonth").val()==''){
			alertc("请填写完整信息");
			initCaptcha();
		}else{
			var data = $('#formEnterprise').serialize();
			$.ajax({
				cache : false,
				type : "POST",
				url : "${path }/quickBorrow/save.do",
				data : data,
				async : false,
				error : function(request) {
					alertc("服务器异常");				
					initCaptcha();
				},
				success : function(data) {
					alertc(data.msg);
					 initCaptcha();
				}
			});
		}
		
	}
	function reloadPage(){
		window.location.reload(true);
	}
	function changeImg(id){
		var src="<%=request.getContextPath()%>/captcha.svl?d="+new Date()*1;
		$("#"+id).attr("src",src);
		
	}
	</script>
</html>