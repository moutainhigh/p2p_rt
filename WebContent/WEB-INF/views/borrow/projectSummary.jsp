<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getProjectSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						借款标题：
						<input type="text" class="textInput" name="borrow_title" id="borrow_title" value="${params.borrow_title }"/>					
					</td>
					<td>
						借款人账号：
						<input type="text" class="textInput" name="user_account" id="user_account" value="${params.user_account }"/>					
					</td>
					<td>
						满标日期：
						<input type="text" name="verify_review_begin_time" id="verify_review_begin_time" value="${params.verify_review_begin_time }" class="date textInput readonly" datefmt="yyyy-MM-dd"   maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="verify_review_end_time" id="verify_review_end_time" value="${params.verify_review_end_time }" class="date textInput readonly" datefmt="yyyy-MM-dd"  maxDate="{%y}-%M-{%d}" size="10"/>
					</td>
				</tr>
				<tr>
					<td>
						是否天标:
						<input name="isDay" type="radio" value="1" onclick="changeIsDay();" <c:if test="${'1' eq params.isDay }">checked="checked"</c:if>>是 
						<input name="isDay" type="radio" value="2" onclick="changeIsDay();" <c:if test="${'1' ne params.isDay }">checked="checked"</c:if>>否
					</td>
					<td>借款期限: 
						<select name="borrowTimeLimitStart" class="required" id="monthSelect">
							<option value="" selected="selected">--贷款期限--</option>
							<option value="1">1个月</option>
							<option value="2">2个月</option>
							<option value="3">3个月</option>
							<option value="4">4个月</option>
							<option value="5">5个月</option>
							<option value="6">6个月</option>
							<option value="7">7个月</option>
							<option value="8">8个月</option>
							<option value="9">9个月</option>
							<option value="10">10个月</option>
							<option value="11">11个月</option>
							<option value="12">12个月</option>
							<option value="24">24个月</option>
							<option value="36">36个月</option>
						</select>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<select name="borrowTimeLimitEnd" class="required" id="monthSelect">
							<option value="" selected="selected">--贷款期限--</option>
							<option value="1">1个月</option>
							<option value="2">2个月</option>
							<option value="3">3个月</option>
							<option value="4">4个月</option>
							<option value="5">5个月</option>
							<option value="6">6个月</option>
							<option value="7">7个月</option>
							<option value="8">8个月</option>
							<option value="9">9个月</option>
							<option value="10">10个月</option>
							<option value="11">11个月</option>
							<option value="12">12个月</option>
							<option value="24">24个月</option>
							<option value="36">36个月</option>
						</select>
					</td>
					<td>
						到期还款日期：
						<input type="text" name="repayment_time_begin" id="repayment_time_begin" value="${params.repayment_time_begin }" class="date textInput readonly" datefmt="yyyy-MM-dd"   maxDate="{%y}-%M-{%d}" size="10"/>
						&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" name="repayment_time_end" id="repayment_time_end" value="${params.repayment_time_end }" class="date textInput readonly" datefmt="yyyy-MM-dd"  maxDate="{%y}-%M-{%d}" size="10"/>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" style="padding-left: 11px;padding-right: 11px;">查询</button>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toProjectExcel')}">
						<li>
							<a class="add" onclick="toExcel('${rightsub.moduleUrl}?right_id=${params.right_id}&borrow_id=');"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">
							line
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
			
		<!-- 数据表 -->
		<table class="list" width="100%" layoutH="115">
			<thead>
				<tr>
					<th width="9%" align="center">借款标题</th>
					<th width="5%" align="center">借款人账号</th>
					<th width="8%" align="center">借款金额</th>
					<th width="7%" align="center">借款期限</th>
					<th width="7%" align="center">满标日期</th> 
					<th width="7%" align="center">到期还款日期</th>
					<th width="7%" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list }" var="re">
					<tr target="sid_support" rel="${re.id }">
						<th align="center">
							<a href="borrow/v_getBorrowDetailById?supportID=${re.id }&right_id=${right_id}" target="dialog" width="850" height="600">${re.borrow_title }</a>
						</th>
						<th align="center">${re.user_account }</th>
						<th align="center">${re.borrow_sum }</th>
						<th align="center">${re.borrow_time_limit }${re.day_type }</th>
						<th align="center">
							<fmt:formatDate value="${re.verify_review_time }" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/>
						</th>
						<th align="center">
							<fmt:formatDate value="${re.repayment_time }" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/>
						</th>
						<th align="center">
							<c:if test="${re.borrow_status == 2 }">初审通过</c:if>
							<c:if test="${re.borrow_status == 4 }">满标</c:if>
							<c:if test="${re.borrow_status == 5 }">收益中</c:if>
							<c:if test="${re.borrow_status == 6 }">还款完成</c:if>
							<c:if test="${re.borrow_status == 10 }">复审通过</c:if>
							<c:if test="${re.borrow_status == 11 }">复审未通过</c:if>
						</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 分页 -->
		<c:set var="page" value="${list }"></c:set>
		<%@ include file="../page.jsp" %>
	</div>
</form>
<script type="text/javascript">
	//是否为天标，借款期限改变
	function changeIsDay() {
		var val = $("input[name='isDay']:checked").val();
		if (val == 1) { //天标
			initBorrowTimeLimit(30, "天");
			$("#dayAlert").show();
		} else {
			initBorrowTimeLimit(12, "个月");
			$("#dayAlert").hide();
		}
	}
	function initBorrowTimeLimit(len, show) {
		var timeLimitSelectStart = $("select[name='borrowTimeLimitStart']");
		var timeLimitSelectEnd = $("select[name='borrowTimeLimitEnd']");
		timeLimitSelectStart.html(getOption("", "--贷款期限--"));
		timeLimitSelectEnd.html(getOption("", "--贷款期限--"));
		for (i = 1; i <= len; i++) {
			timeLimitSelectStart.html(timeLimitSelectStart.html()
					+ getOption(i, i + show));
			timeLimitSelectEnd.html(timeLimitSelectEnd.html()
					+ getOption(i, i + show));
		}
		timeLimitSelectStart.html(timeLimitSelectStart.html()
				+ getOption(45, 45 + show));
		timeLimitSelectEnd.html(timeLimitSelectEnd.html()
				+ getOption(45, 45 + show));
	}
	function getOption(value, text) {
		return "<option value=\""+value+"\">" + text + "</option>";
	}
	function toExcel(url) {
		var borrowId = $("tr.selected").attr("rel");
		
		if (borrowId) {
			window.open(url + borrowId);
		} else {
			window.open(url);
		}
	}
</script>