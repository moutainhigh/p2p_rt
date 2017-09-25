<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../../taglibs.jsp"%>
	<title>${showTitle }-自动投资</title>
</head>
<body style="font-size: 14px;">
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
			<div class="J-ma-nav jboxsize">
				<input type="hidden" value="left4" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
			</div>
  
			<!---自动投标 star--->
			<div class="J-ma-invest jboxsize J-ma-zqzr">
				<div class="J-mainv-list J-ma-zqzr-list">
					<dl class="clearfix J-mainv-click">
						<dd class="J-mainvlist-current">自动投标</dd>
					</dl>
					<div class="J-mainvl-con">
     
					<div class="J-ma-zdtb jmt40">
						<h3>自动投标生效状态</h3>
						<div class="J-ma-zdtb-wen clearfix jboxsize">
						    <ol>
						    	<li style="font-weight:bold; font-size:16px">状态</li>
								<li style="font-weight:bold; font-size:16px; margin-top:19px;">金额</li>
							</ol>
							<ul>
								<li class="J-mazdtb-radio J-mazdtb-checkbox ">
									<dl class="clearfix">
										<dd><input type="radio" name="rulesStatus" value="1" id="rulesStatus1" <c:if test="${not empty autoTenderRules && autoTenderRules.rulesStatus eq '1' }"> checked="checked" </c:if> <c:if test="${empty autoTenderRules}"> checked="checked" </c:if>>启用</dd>
										<dd><input type="radio" name="rulesStatus" value="0" id="rulesStatus2" <c:if test="${not empty autoTenderRules && autoTenderRules.rulesStatus eq '0' }"> checked="checked" </c:if> >不启用</dd>
									</dl>
								</li>
								<li class="J-mazd-cb-input">
									<span>每次投标</span> <input type="text" name="tenderMoney" value="${autoTenderRules.tenderMoney }"/><span>元(最少100元)</span>
								</li>
							</ul>
						</div> 
					</div>
        			<div class="J-ma-zdtb">
				        <h3>选择标的限制</h3>
				        <div class="J-ma-zdtb-wen clearfix jboxsize">
							<ol>
								<li>还款方式</li>
								<li style="margin-bottom:20px">利率范围</li>
								<li>项目期限范围</li>
								<li>投标奖励</li>
								<li>投标标种</li>
							</ol>
         					<ul>
         						<li>
						           <select style="width:173px;" name="repayType">
						            <option value="0"
										<c:if test="${not empty autoTenderRules && autoTenderRules.repayType eq '0' }">  selected="selected" </c:if>>所有</option>
									<option value="1"
										<c:if test="${not empty autoTenderRules && autoTenderRules.repayType eq '1' }">  selected="selected" </c:if>>一次性还款</option>
									<option value="2"
										<c:if test="${not empty autoTenderRules && autoTenderRules.repayType eq '2' }">  selected="selected" </c:if>>按月分期还款</option>
									<option value="3"
										<c:if test="${not empty autoTenderRules && autoTenderRules.repayType eq '3' }">  selected="selected" </c:if>>每月还息到期还款</option>
						           </select>
       							</li>
         
          <li class="J-ma-zdtb-lv clearfix">
           <select name="aprMin" id="aprMin" >
            <option value="1">1%</option>
												<option value="2">2%</option>
												<option value="3">3%</option>
												<option value="4">4%</option>
												<option value="5">5%</option>
												<option value="6">6%</option>
												<option value="7">7%</option>
												<option value="8">8%</option>
												<option value="9">9%</option>
												<option value="10">10%</option>
												<option value="11">11%</option>
												<option value="12">12%</option>
												<option value="13">13%</option>
												<option value="14">14%</option>
												<option value="15">15%</option>
												<option value="16">16%</option>
												<option value="17">17%</option>
												<option value="18">18%</option>
												<option value="19">19%</option>
												<option value="20">20%</option>
												<option value="21">21%</option>
												<option value="22">22%</option>
												<option value="23">23%</option>
												<option value="24">24%</option>
												<option value="25">25%</option>
           </select>
           <p>~</p>
           <select name="aprMax" id="aprMax">
            <option value="5">5%</option>
												<option value="6">6%</option>
												<option value="7">7%</option>
												<option value="8">8%</option>
												<option value="9">9%</option>
												<option value="10">10%</option>
												<option value="11">11%</option>
												<option value="12">12%</option>
												<option value="13">13%</option>
												<option value="14">14%</option>
												<option value="15">15%</option>
												<option value="16">16%</option>
												<option value="17">17%</option>
												<option value="18">18%</option>
												<option value="19">19%</option>
												<option value="20">20%</option>
												<option value="21">21%</option>
												<option value="22">22%</option>
												<option value="23">23%</option>
												<option value="24">24%</option>
												<option value="25">25%</option>
           </select>
          </li>
          
          
									<li class="J-mazdtb-input">
										<dl class="clearfix">
											<dd><input type="text" placeholder="0" id="limitMinDay" value="${autoTenderRules.periodBegin }"/><span>月</span></dd>
											<dd style="line-height:35px;">~</dd>
											<dd><input type="text" placeholder="0" id="limitMaxDay" value="${autoTenderRules.periodEnd }"/><span>月</span></dd>
										</dl>
									</li>
									<li class="J-mazdtb-radio jmt20">
										<dl class="clearfix">
											<dd><input type="radio" name="rewardType" value="0" checked="checked"/>不限制奖励<span style="display: none;" id="span0"></span></dd>
											<dd><input type="radio" name="rewardType" value="1"/>按投标金额比例奖励<span style="display: none;" id="span1"><input class="reward" id="funds" type="text" value="${autoTenderRules.rewardType==1 ? autoTenderRules.reward : '' }"  />%</span></dd>
											<dd><input type="radio" name="rewardType" value="2"/>按固定金额分摊奖励<span style="display: none;" id="span2"><input class="reward" id="partAccount" type="text" value="${autoTenderRules.rewardType==2 ? autoTenderRules.reward : '' }"  />元</span></dd>
										</dl>
									</li>
									<li style="margin-top: 28px">
										<label>
											<input type="checkbox"  id="8"  value="8"  name="borrowTypes">新手专享标
										</label>
										<label>
										    <input type="checkbox"  id="14" value="14" name="borrowTypes">争分夺秒标
										</label>
									    <label>
										    <input type="checkbox"  id="10" value="10" name="borrowTypes">浩茗理财计划1月标
										 </label>
									   <label>
										    <input type="checkbox"  id="9" value="9"   name="borrowTypes">浩茗理财计划3月标
										 </label>
									   <label>
										    <input type="checkbox"  id="11" value="11" name="borrowTypes">浩茗理财计划6月标
										</label>
									   <label>
										    <input type="checkbox"  id="12" value="12" name="borrowTypes">浩茗理财计划9月标
										</label>
									   <label>
										    <input type="checkbox"  id="13" value="13" name="borrowTypes">浩茗理财计划12月标
										</label>
									</li>
									<li class="J-ma-btn jmt25"><a onclick="sub();" target="_blank" style="font-size:15px;" id="subValue">保存或修改自动投标</a></li>
         						</ul>
        					</div> 
						</div>
						<div class="J-mazdtb-action jboxsize">
							<h5>温馨提示：</h5>
							<ul>
								<li>
									<span>&minus;</span>
									<p>自动投标时，只有满足上面您选择的条件时系统才会为您自动投标。</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>按启用自动投标的时间进行排序，启用时间早的靠前；</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>自动投标成功，重新排序；</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>投标资金部分通过的用户，排序不变，优先投标；</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>当启用时间相同时，注册时间早的用户优先投标；</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>启用自动投标后，修改为不启用时，排序清空；（修改其它设置，排序不变）</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>自动投标触发时，自动无效，重新排序；（无效包括但不限于设置错误，可用余额不足50元）</p>
								</li>
								<li>
									<span>&minus;</span>
									<p>可用余额三个月之内未变动过的用户，自动投标功能将被自动关闭；</p>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!---自动投标 end--->
		</div>
	</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script>
$(function(){
  if("${autoTenderRules}"!=""){
		var aprMin=${autoTenderRules.aprMin};
		var aprMax=${autoTenderRules.aprMax};
		$("#aprMin").val(aprMin);
		$("#aprMax").val(aprMax);	
		
		var borrowTypes="${autoTenderRules.borrowType}".split(",");
		 $('input:checkbox[name="borrowTypes"]').each(function(i) {
			 	var val=$(this).val();
			 	for ( var j = 0; j < borrowTypes.length; j++) {
					if(val==borrowTypes[j]){
						$(this).attr("checked",true);
					}
				}
		}); 
	}  
});
</script>

<script>

$(function(){
	
	var rulesStatus="${autoTenderRules.rulesStatus}";
	var msg;
	if(rulesStatus=="" || rulesStatus == "1"){
		$("#subValue").text("保存或修改自动投标");
		msg="已关闭自动投标";
	}else if(rulesStatus == "0"){
		$("#subValue").text("已关闭自动投标");
		msg="自动投标开关已开启";
	}
	

	//验证投标状态
 	$("#rulesStatus1,#rulesStatus2").click(function(){
 		var autoTenderRules="${autoTenderRules}";
 		var statusVal=$(this).val();
 		if(autoTenderRules==""){
 			if(statusVal=='0'){
 				alertc("请先创建自动投标");
 	 			$(this).attr("checked",false);
 	 			$("#rulesStatus1").attr("checked",true);
 			}
 		}else{
 			var url = $_path + "/invest/saveAuto.do";
 			$.ajax({
 				type:'post',
 				url:url,
 				data:{
 					'rulesStatus':statusVal
 				},
 				async: false,
 				success:function(data){
 					if (data.code == '203'){
 						alertc(msg);
 						setInterval(reloadPage,1500);
 					}
 				}
 			});
 		}	
	}); 
	
	
	$("[name=rewardType]").click(function(){
		$("[name=rewardType]").each(function(i){
			$("#span"+i).hide();
		});
		$("#span"+$(this).val()).show();
	});
	
	var rewardType="${autoTenderRules.rewardType}"
	
	if(rewardType!=""){
		$("[name=rewardType]").each(function(i){
			if(rewardType==$(this).val()){
				$(this).attr("checked",true);
			}
			$("#span"+i).hide();
		});
		$("#span"+rewardType).show();
	}
	
	
});

function reloadPage(){
	window.location.reload(true);
}


var msg = "${error}";
if(msg=='80'){
	alertc("请先进行实名、手机、邮箱认证",function(){window.location.href="${path }/basics/personalData.html"});
}
function resetFrm(){
	$("#frm")[0].reset();
}
function sub() {
	var rulesStatus="${autoTenderRules.rulesStatus}";
	if(rulesStatus=='0'){
		alertc("已关闭自动投标");
		return false;
	}
	var data1 = {};
	/* //生效状态
	if ($('input[name="rulesStatus"]').attr("checked") == "checked") {
		$('input[name="rulesStatus"]').val("1");
	}
	data1.rulesStatus = $('input[name="rulesStatus"]').val(); */

	//还款方式 默认为所有为 “-1”
	data1.repayType = $('select[name="repayType"]').val();
	//借款期限方式
	/* if ($('input[name="periodType"]:checked').val() == '0') {
		data1.periodType = '0';
	} */
	/* if ($('input[name="periodType"]:checked').val() == '1') { */
   var pt="1";
		if (pt == "1") { //按月****
			data1.periodType = '1';
			var monthBegin =  $("#limitMinDay").val();
			var monthEnd = $("#limitMaxDay").val();
			if (Number(monthBegin) > Number(monthEnd)) {
				alertc("选择范围由小到大")
				return false;
			}
			data1.periodBegin = monthBegin;
			data1.periodEnd = monthEnd;
		}
		if (pt == "2") {//按天
			data1.periodType = '2';
			var dayBegin = $("#limitMinDay").val();
			var dayEnd = $("#limitMaxDay").val();
			if (Number(dayBegin) > Number(dayEnd)) {
			alertc("选择范围由小到大")
				return false;
			}
			data1.periodBegin = dayBegin;
			data1.periodEnd = dayEnd;
		}

	/* } */
	//年利率状态 默认为0 
		var aprMin = $('select[name="aprMin"]').val();//利率范围
		var aprMax = $('select[name="aprMax"]').val();
		if (Number(aprMin) > Number(aprMax)) {
			alertc("选择范围由小到大")
			return false;
		}
		data1.aprMin = aprMin;
		data1.aprMax = aprMax;

	/* data1.aprStatus = $('input[name="aprStatus"]').val(); */
	//利率范围  (不选传1--5)

	//投资奖励****
	if ($('input[name="rewardType"]:checked').val() == '0') {
		data1.rewardType = '0';
	} else if ($('input[name="rewardType"]:checked').val() == '1') {
		var funds = $('#funds').val();
		if (funds == '' || funds == null) {
			alertc("必填项");
			return false;
		}else if(isNaN(funds)){
			alertc("投标金额比例奖励请填写数字");
			return false;
		}else if(funds<=0){
			alertc("投标金额比例奖励大于零");
			return false;
		}
		data1.rewardType = '1';
		data1.reward = funds;
	} else if ($('input[name="rewardType"]:checked').val() == '2') {
		var partAccount = $('#partAccount').val();
		if (partAccount == '' || partAccount == null) {
			alertc("必填项");
			return false;
		}else if(isNaN(partAccount)){
			alertc("固定金额分摊奖励请填写数字");
			return false;
		}else if(partAccount<=0){
			alertc("固定金额分摊奖励大于零");
			return false;
		}
		data1.rewardType = '2';
		data1.reward = partAccount;
	}
	//标种 数组
	var str = "";
	 $('input:checkbox[name="borrowTypes"]:checked').each(function(i) {
		str += $(this).val() + ",";
	}); 

	if (str == "" || str == null) {
  		 alertc("请选择标种");
			return;
	} else {
		data1.borrowType = str;
	}
data1.rulesStatus=$("input:radio[name='rulesStatus']:checked").val();
if(data1.rulesStatus==null){
	alertc("请选中状态");
	return;
}
//每次投标金额
var tenderMoney = $('input[name="tenderMoney"]').val();
if (tenderMoney == "" || tenderMoney == null) {
	alertc("必须填写投标金额");
	return;
}else if(isNaN(tenderMoney)){
	alertc("投标金额请填写数字");
	return;
}else if (tenderMoney < 100) {
	alertc("投标金额必须大于100元");
	return;
}
data1.tenderMoney = tenderMoney;
	var url = $_path + "/invest/saveAuto.do";
	$.ajax({
		type : "post",
		url : url,
		async : false,
		data : data1,
		success : function(data) {
			if (data.code == '201') {
				alertc(data.msg);
			} else if (data.code == '202') {
				alertc(data.msg);
			} else if (data.code == '203') {
				alertc(data.msg);
				
			} else if (data.code == '204') {
				alertc(data.msg);
			}
			setInterval(reloadPage,1500);
		}
	});

}
</script>

<style type="text/css">
	.reward{
		width:50px;
		height:15px;
		border:1px solid #d7dade;
		border-right:none;
		text-indent:5px;
		background:#fcfcfc;
	}
</style>

</html>