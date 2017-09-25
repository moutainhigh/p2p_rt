<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="userDetail/${PRE_PATH_EDIT}saveHouseMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="userId" value="${userId }"> 
				<div class="tabsContent" style="height: 390px;">
			
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">房产地址:</label><input class="required"  type="text" name="address"  value="${houseMessage.address }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">建筑面积:</label><input class="required number" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  type="text" name="mianji"  value="${houseMessage.mianji }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">建筑年份:</label><input  class="date textInput readonly"  type="text" name="year1"  value="<fmt:formatDate value="${houseMessage.year }" pattern="yyyy-MM-dd"/>" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">所有权人1:</label><input class="required" type="text" name="ownership1" value="${houseMessage.ownership1 }" />产权份额<input class="required" value="${houseMessage.share1 }" name="share1"/></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">所有权人2:</label><input type="text" name="ownership2" value="${houseMessage.ownership2 }" />产权份额<input value="${houseMessage.share2 }" name="share2"/></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">若房产尚在按揭中, 请填写：贷款年限:</label><input class="date textInput readonly" type="text" name="loanYear" value="${houseMessage.loanYear }" />每月供款<input  type="text" name="monthMoney" value="${houseMessage.monthMoney }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">尚欠贷款余额：</label><input type="text" onkeyup="value=value.replace(/[^0-9|.]/g,'')"  class="required number" name="debtMoney"  value="${houseMessage.debtMoney }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:210px; padding:0 5px; line-height:21px;">按揭银行:</label><input type="text" class="required" name="bank"  value="${houseMessage.bank }" /></p>
					<div class="divider"></div>
				
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<!-- <li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">
</script>
</html>