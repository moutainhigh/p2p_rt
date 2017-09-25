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
			<form id="frm" method="post" action="userDetail/${PRE_PATH_EDIT}savePrivateBusinessMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="userId" value="${userId}"> 
				<div class="tabsContent" style="height: 390px;">
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">私营企业类型:</label><input class="required"  type="text" name="privateType"  value="${privateBusinessMessage.privateType}" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">成立日期:</label><input class="date textInput readonly requried" type="text" name="establishTime1"  value="<fmt:formatDate value="${privateBusinessMessage.establishTime}" pattern="yyyy-MM-dd"/>" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">经营场所:</label><input  type="text" class="required" name="runSite"  value="${privateBusinessMessage.runSite}" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">租金:</label><input type="text" class="required number" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="rentMoney" value="${privateBusinessMessage.rentMoney}" />元</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">租期:</label><input type="text" class="required digits" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="rentDate" value="${privateBusinessMessage.rentDate}" />月</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">税务编号:</label><input  type="text" class="required"  name="taxId" value="${privateBusinessMessage.taxId}" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">工商登记号：</label><input type="text" class="required" name="registerId"  value="${privateBusinessMessage.registerId}" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">全年盈利/亏损额:</label><input type="text" class="required number" onkeyup="value=value.replace(/[^0-9|.]/g,'')" name="profit"  value="${privateBusinessMessage.profit}" />元（年度）</p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">雇员人数:</label><input type="text" class="required digits" onkeyup="value=value.replace(/[^0-9]/g,'')" name="hireNumber"  value="${privateBusinessMessage.hireNumber}" />人</p>
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
									<button type="button"  class="close">
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