<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="resources/js/highcharts.js"></script>
<script type="text/javascript" src="resources/js/exporting.js"></script>
<script type="text/javascript">
var totalCount=${news.count};
var totalMoney=${news.money};
$(function(){
	$("#oneThreeCount").text(parseInt($("#oneThreeCount").text())+parseInt($("#newCount input").val())+totalCount);
	$("#oneThreeMoney").text(parseFloat($("#oneThreeMoney").text())+parseFloat($("#newMoney input").val())+parseFloat(totalMoney)+'.00');
	
})
</script>
	<script type="text/javascript">
		var type = "${params.type}";
		var year = $("#dateYear").val();
		$(function() {
			$.ajax({
				type : "post",
				url : "summary/getBorrowByWhatData",
				data : {dateYear: year, type: type},
				dataType : "json",
				success : function(data) {
					$("#borrowByDate").highcharts( {
						chart : {
							type : 'pie'
						},
						title : {
							text : year + '年成交金额同比增长柱状图'
						},
						credits : {
							enabled : false
						},
						plotOptions: {//显示数值
							pie: {
				                innerSize: 100
				            }
						},
						series : [data]
					});
				},
			});
		});
	</script>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getBorrowSummaryByWhat?right_id=${params.right_id}&type=${params.type }" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>年份: 
							<input type="text" name="dateYear" id="dateYear" value="${params.dateYear }" class="date textInput readonly" datefmt="yyyy"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="pageContent">
		<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toBorrowExcel?type=date')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}&&right_id=${params.right_id}&&dateYear=${params.dateYear}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			
		<!-- 数据表 -->
		<table class="list" width="100%" layoutH="500">
			<thead>
				<tr>
					<th width="33%" align="center">期限（月）</th>
					<th width="33%" align="center">笔数</th>
					<th width="34%" align="center">金额（元）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="re" varStatus="index">
					<tr>
				
				        <th align="center">${re.borrow_limit }</th>
						<c:if test="${index.index==7 }">
						<th align="center" id="newCount">0<input type="hidden" value="${re.count }"></th>
						<th align="center" id="newMoney">0.00<input type="hidden" value="${re.money }"></th>
						</c:if>
						<c:if test="${index.index==1||index.index==2||index.index==3||index.index==4||index.index==5||index.index==6}">
						<th align="center">${re.count }</th>
						<th align="center">${re.money }</th>
						</c:if>
						<c:if test="${index.index==0}">
						<th align="center" id="oneThreeCount">${re.count }</th>
						<th align="center" id="oneThreeMoney">${re.money }</th>
						</c:if>
		
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 图表显示区 -->
		<div id="borrowByDate" style="min-width: 900px; height: 350px;"></div>
	</div>
</form>
