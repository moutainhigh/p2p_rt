<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
		 <table class="uniceTable siteInfoWidth1">
        <tbody>
            <tr>
	            <td id="title1"><a href="${path }/iborrow/borrowStatus.do">正在招标的借款</a></td>
				<td id="title2"><a href="${path }/iborrow/borrowStatus1.do">尚未发布的借款</a></td>
				<td id="title3"><a href="${path }/iborrow/borrowStatus2.do">正在还款的借款</a></td>
				<td id="title4"><a href="${path }/iborrow/borrowStatus3.do">已还完的借款</a></td>
				<td id="title6"><a href="${path }/iborrow/borrowStatus4.do">逾期的借款</a></td>
				<td id="title5"><a href="${path }/verify/evaluateApply.do">额度申请</a></td>
				<td style="border-right:none; ">&nbsp;</td>
            </tr>
        </tbody>
    </table>
	<script type="text/javascript">
	try{
		var curNav = "${curNav }";
		$("#"+curNav).addClass("userDh");
	}catch(e){}
	</script>
	