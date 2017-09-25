<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
		 <table class="uniceTable siteInfoWidth1">
        <tbody>
            <tr>
	            <td id="title1"><a href="${path }/account/bankCard.html">银行账户</a></td>
				<td id="title2"><a href="${path }/account/recharge.html">账户充值</a></td>
				<td id="title3"><a href="${path }/account/rechargeLog.do">充值记录</a></td>
				<td id="title4"><a href="${path }/account/withdraw.html">账户提现</a></td>
				<td id="title5"><a href="${path }/account/withdrawLog.do">提现记录</a></td>
				<td id="title6"><a href="${path }/account/transcationRecord.html">资金记录</a></td>
            </tr>
        </tbody>
    </table>
	<script type="text/javascript">
	try{
		var curNav = "${curNav }";
		$("#"+curNav).addClass("userDh");
	}catch(e){}
	</script>
	