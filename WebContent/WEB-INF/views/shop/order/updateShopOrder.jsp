<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>订单列表设置</title>
</head>
<body>
  <div class="pageContent">
		<form method="post" action="shop/${PRE_PATH_VIEW }update"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${shopOrder.id}" />
			
				<input type="hidden" name="type" value="${type }" >
				
				
				<div class="pageFormContent" layoutH="55">
				
						<p><label>订单状态：</label><font color="#F7772D;">${ALL_SHOP_ORDER_STATUS[shopOrder.status] }</font></p>
						<div class="divider"></div>
				
						<p><label>用户名：</label>${usr.userAccount }</p>
						<div class="divider"></div>
						
						<p><label>订单号：</label>${shopOrder.orderNo }</p>
						<div class="divider"></div>
						
						<p><label>商品名称：</label>${goods.goodsName }</p>
						<div class="divider"></div>
						
						<c:if test="${type eq '4' }">
							<p><label>快递名：</label><input type="text" size="22" name="misc1" value="${shopOrder.misc1 }" class="required"/></p>
									<div class="divider"></div>
							<p><label>快递单号：</label><input type="text" size="22" name="misc2" id="misc2" value="${shopOrder.misc2 }" class="required" /></p>
						
						</c:if>
					
					<div class="divider"></div>
					
					<p>
						<label>备注： </label>
						<textarea rows="4" name="remark" cols="40">${shopOrder.remark} </textarea>
					</p>
					
					
					
					
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
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
		</form>
</div>
</body>

</html>