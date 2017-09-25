<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="pageContent">
	<input type="hidden" name="right_id" value="${right_id}" />
	<input type="hidden" name="userId" value="${user.id }">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>基本资料</span></a></li>
					<li><a href="userDetail/v_personalMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>个人详细资料</span></a></li>
					<li><a href="userDetail/v_houseMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>房产资料</span></a></li>
					<li><a href="userDetail/v_unitMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>单位资料</span></a></li>
					<li><a href="userDetail/v_privateBusinessMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>私营业主资料</span></a></li>
					<li><a href="userDetail/v_financeMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>财务状况</span></a></li>
					<li><a href="userDetail/v_relationMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>联系方式</span></a></li>
					<li><a href="userDetail/v_spouseMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>配偶资料</span></a></li>
					<li><a href="userDetail/v_educationMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>教育背景</span></a></li>
					<li><a href="userDetail/v_otherMessage?supportID=${user.id}&right_id=${right_id}" class="j-ajax"><span>其他信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height: 470px;">
			<div>
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">用户名:</label>${user.userAccount }</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">真实姓名:</label>${user.userRealname }</p>
				<div class="divider"></div>
			</div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent">
				
			</div>
		</div>
	</div>
</div>
</body>
</html>