<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="about-banner"></div>
<div style="background-color:#f2f2f2;" id="content1"><!--实时财务-->
<div style="height:80px;" id="hkct-sscw">
    <div style="margin-top:8px;" class="xt-cw"></div>
<div style="background-color:#ee2121; height:90px; " class="hkct-k">
  <ul style="padding-top:10px;">
	<li style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">交易总额</p><p class="hkct-sscw-p2" id="totleMoney"></p></li>
    <li  style="line-height:70px; height:70px;" ><p style="padding-top:0px;" class="hkct-sscw-p1">待收总额</p><p class="hkct-sscw-p2" id="allInterestAndRewardStr"></p></li>
    <li  style="line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">今日回款总额</p><p class="hkct-sscw-p2" id="repossessed"></p></li>
    <li style="border:none; line-height:70px; height:70px;"><p style="padding-top:0px;" class="hkct-sscw-p1">注册人数</p><p class="hkct-sscw-p2" id="userNum"></p></li>
  </ul>
    </div>
  </div>
  
</div>
<script type="text/javascript">
$(function(){
	changeTopHover(7);
	getStatistics();
});
/**
 * 统计
 */
function getStatistics() {
	$.get("${path}/getStatistics.do", function(data, status) {
		var totleMoney = data.totleMoney.toFixed(2);
		$("#totleMoney").html("￥"+addCommas(totleMoney));
		var userNum = data.userNum;
		$("#userNum").html(addCommas(userNum));
		var allInterestAndRewardStr=data.allInterestAndRewardStr;
		$("#allInterestAndRewardStr").html("￥"+addCommas(allInterestAndRewardStr));
		var repossessed=data.repossessed;
		$("#repossessed").html("￥"+addCommas(repossessed));
	});
}
</script>