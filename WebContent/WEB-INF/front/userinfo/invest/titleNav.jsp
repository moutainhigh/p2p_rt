<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
		 <table class="uniceTable siteInfoWidth1">
        <tbody>
            <tr>
	            <td id="title1" style="width: 13%;"><a href="${path }/invest/inBid.html">正在投标的借款</a></td>
				<td id="title2" style="width: 13%;"><a href="${path}/invest/inRepaying.do">正在收款的项目</a></td>
				<td id="title3" style="width: 12%;"><a href="${path}/invest/getRepByStatus.do">未收款明细账</a></td>
				<td id="title4" style="width: 12%;"><a href="${path}/invest/getRepByStatus1.do">已收款明细账</a></td>
				<td id="title5" style="width: 12%;"><a href="${path}/invest/getRecord.do">借出明细账</a></td>
				<td id="title6" style="width: 12%;"><a href="${path}/invest/inRepayed.do">已还清的项目</a></td>
				<td id="title7"><a href="${path }/invest/transferRedeem.html">债权转让</a></td>
				<%-- <td id="title9"><a href="${path }/invest/investRedeem.do">投资赎回</a></td> --%>
				<td id="title8"><a href="${path }/invest/autoInvestment.html">自动投标</a></td>
            </tr>
        </tbody>
    </table>
	<script type="text/javascript">
	try{
		var curNav = "${curNav }";
		$("#"+curNav).addClass("userDh");
	}catch(e){}
	</script>
	