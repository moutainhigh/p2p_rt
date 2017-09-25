<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
     <table class="uniceTable siteInfoWidth1">
						<tbody>
							<tr id="attul">
								<td id="title1"><a href="${path }/basics/personalData.html">个人详情</a></td>
						        <td id="title2"><a href="${path }/basics/buliding.do">房产资料</a></td>
						        <td id="title3"><a href="${path }/basics/unit.do">单位资料</a></td>
						        <td id="title4"><a href="${path }/basics/privatebusiness.do">私营业主</a></td>
						        <td id="title5"><a href="${path }/basics/finance.do">财务状况</a></td>
						        <td id="title6"><a href="${path }/basics/relation.do">联系方式</a></td>
						        <td id="title7"><a href="${path }/basics/spouse.do">配偶资料</a></td>
						        <td id="title8"><a href="${path }/basics/education.do">教育背景</a></td>
						        <td id="title9"><a href="${path }/basics/other.do">其他</a></td>       
							</tr>
						</tbody>
	</table>
	<script type="text/javascript">
	try{
		var curNav = "${curNav }";
		$("#"+curNav).addClass("userDh");
	}catch(e){}
	</script>
	