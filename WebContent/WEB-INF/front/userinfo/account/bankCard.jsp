<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-银行卡</title>
</head>
<body>
<script type="text/javascript" src="<%=path %>/common/front/js/provincecity.js"></script>
	<jsp:include page="/top.do"></jsp:include>

				
	<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
   <input type="hidden" value="left8" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
  </div>

  <div class="J-ma-conR yh_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);" class="tap">绑定银行卡</a>
    </div>
    <div class="yh_txt">
    <form method="post" id="addNewCardForm">
      <div class="input"><i>持卡人</i><span>${user.userRealname }</span><input type="hidden" name="userId" id="userId" value="${user.id }"/></div>
      <div class="input"><i>银行</i>
        <c:if test="${empty userBank }">
			  <select  name="bankId" id="bankId"  >
            	<option value="">----请选择----</option>
            	<c:forEach var="bank" items="${bank }">
					<option value="${bank.id }">${bank.bankName }</option>
            	</c:forEach>
           	 </select>
		</c:if>
		<c:if test="${not empty userBank }">
			<span>${userBank.allBank.bankName }</span>
		</c:if>
      </div>
      <div class="input"><i>卡号</i>
		<c:if test="${empty userBank }">
			<input type="text"  name="bankAccount" id="bankAccount" onkeyup="value=value.replace(/[^0-9]/g,'')" minlength="10" maxlength="19"/>
		</c:if>
		<c:if test="${not empty userBank }">
			<span>${userBank.bankAccount }</span>
		</c:if>
      </div>
      <div class="input blank"><i>开户行</i>
      <c:if test="${empty userBank }">
      <select name="bankProvince" onchange="getCity()" id="bankProvince">
        </select>
        <select name="bankCt" id="bankCt">
        </select>
      </c:if>
      <c:if test="${not empty userBank }">
      	<span>${userBank.bankCity }</span>
      </c:if>
        
		<c:if test="${empty userBank }">
			<input type="text"  name="bankBranch" id="bankBranch">
		</c:if>
		<c:if test="${not empty userBank }">
			<span>${userBank.bankBranch }</span>
		</c:if>
      </div>
      <!-- <div class="input"><i>交易密码</i><input type="text"></div> -->
      <c:if test="${empty userBank }">
      <input type="hidden" name="bankCity" id="bankCity" />
      <a href="javascript:addnewcard();" class="btn">确定</a>
      </c:if>
      	<c:if test="${not empty userBank && !isRelease}">
			<a href="javascript:toRelaseCard();" class="btn">解绑申诉</a>
		</c:if>
      </form>
      <div class="tip">
          <h5>温馨提示：</h5>
            <ul>
              <li>- 禁止信用卡套现、虚假交易等行为,一经发现将予以处罚,包括但不限于：限制收款、冻结账户、永久停止服务,并有可能影响相关信用记录。</li>
              <li>- 为了您的资金账户安全，如有修改提现银行卡，请联系客服给予修改。浩茗金融客服热线：${websitetel}。</li>
             <!--  <li>- 充值金额需大于或等于10.00元；</li>
              <li>- 您的资金将从充值银行卡转入合作支付公司的监管账户；</li>
              <li>- 每日的充值限额依据各银行限额为准，请注意您的银行卡充值限制，以免造成不便；</li> -->
            </ul>
      </div> <!-- tip -->   
    </div><!-- yh_txt -->
    
  </div><!-- J-ma-conR -->
 </div>
</div>

	<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	function addBank() {
		$(".bank-add-button").hide();
		$(".bank-card-opt").show();
	}
	function reset() {
		$(".bank-add-button").show();
		$(".bank-card-opt").hide();
	}
	function addnewcard() {
		$("#addNewCardForm").validate({
			rules : {
				bankAccount : {
					required : true
				},
				bankId : {
					required : true
				},
				bankName : {
					required : true
				},
				bankBranch : {
					required : true
				}
			},
		});
		$("#bankCity").val($("#bankProvince option:selected").text()+"|"+$("#bankCt option:selected").text()+"|"
				+$("#bankProvince option:selected").val()+"|"+$("#bankCt option:selected").val()
				+"|"+$("#bankProvince option:selected").attr('curcode')+"|"+$("#bankCt option:selected").attr('curcode'));
		if ($("#addNewCardForm").valid()) {
			$
					.ajax({
						type : "POST",
						url : "${path}/account/addBankCard.do",
						data : $('#addNewCardForm').serialize(),
						async : false,
						success : function(data) {
							if (data.code == '100') {
								alertc(
										data.msg,
										function() {
											window.location.href = "${path}/account/bankCard.html"
										});
							} else {
								alertc(data.msg);
							}
						}
					});
		}
	}
	
	function toRelaseCard(){
		window.location.href = "${path}/account/toRelaseCard.html";
	}
	/* new PCAS("userProvince", "userCity", "userArea", '${user.userProvince}',
			'${user.userCity}', '${user.userArea}'); */
</script>
</html>