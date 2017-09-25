<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }-账户提现</title>
</head>

<body>
	<jsp:include page="/top.do"></jsp:include>

<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
   <input type="hidden" value="left7" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>

  </div>

  <div class="J-ma-conR tx_con" style="padding:30px 0;">
    <div class="title">
      <a href="javascript:void(0);" class="tap">提现</a>
    </div>
    <div class="tx_txt">
    
        <%-- <div class="bank clearfix">
          <h5>选择银行卡</h5>
          <a class="pic" href="${path }/account/bankCard.html"><img src="${frontPath}/images/bank_bg.png" ></a>
          <p>请绑定您本人开户的银行卡作为提现银行卡</p>
        </div> --%>
        <div class="mes">
          <h5>填写提现信息</h5>
          <form id="withdrawForm" method="post" action="">
          <ul class="txt">
            <li><i>可提金额</i><span>￥${userAccount.availableMoney}</span>
            <input type="hidden" class="input" id="available"  value="${userAccount.availableMoney }"/>
            </li>
            <li class="input"><i>提现金额</i><input type="text" class="input" name="cashTotal" id="cashTotal" onkeyup="onkey()" onblur="calculateCashAccount();" onchange="calculateCashAccount();" value="0"><em>元</em></li>
            <li><i>手续费</i><span id="serviceFee"></span><em></em></li>
            <li><i>实际提现金额</i><span id="actualAmount"></span></li>
            <li class="input"><i>交易密码</i>
            <input type="hidden"  id="cashFeePer" name="cashFeePer" value="${sFRate.sysWithdrawalPoundage }"/>
          <input type="hidden"  id="cashFeeOne" name="cashFeeOne" value="${sFRate.sysWithdrawalOne }"/>
          <input type="hidden"  id="cashAccount" name="cashAccount"/>
          <input type="hidden"  id="cashFee" name="cashFee"/>
									<input type="hidden" name="publickey" id="publickey" value="${publickey}" /><input type="password" class="input" id="payPassword" name="payPassword" /></li>
            <li><a onclick="subForm()" class="cash_btn">提现</a></li>
          </ul>
          </form>
        </div>
               
    </div><!-- mm_txt -->
    <div class="tip">
          <h5>温馨提示：</h5>
            <ul>
              <li>- 单笔提现限额：100元 —— 100万元 (特殊帐户清理可例外)。</li>
              <li>- 提现费率：免手续费（有效期截止2016年12月31日）。</li>
              <li>- 提现处理时间：
              <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周一到周五11:00之前的申请，当日16：00之前完成所有提现申请的内部审核及转账；</p>
           		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11:00到15:00之间的申请,16:00之前完成所有提现申请的内部审核，第二日11:00之前完成 转帐；</p> 
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;15:00以后的申请，第二日16：00之前进行处理。</p></li>
              <li>- 不处理当天充值当天提现的申请。（以24小时系统自动核算为准）。</li>
              <li>- 对于用户恶意提现的行为，公司保留采取进一步法律措施的权利。</li>
            </ul>
        </div><!-- tip -->  
  </div><!-- J-ma-conR -->
 </div>
</div>
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
	<div id='tishizheceng' style="position: absolute; z-index: 99998; top: 0px; left: 0px; width: 100%; height: 1584px; opacity: 0.3; background: rgb(255, 255, 255);display: none;"></div>
	<div id='tishikuang' class="gSys_msg_box" style="position: absolute; z-index: 99999; padding: 0px; margin: 0px; width: 460px; top: 300px; left: 561.5px;display: none;">
		<div class="ptitle"><span class="fn-bg rc-l"></span><h4>提示</h4><span class="fn-bg rc-r"></span><a href="javascript:closePop();" class="fn-bg aclose" title="关闭">关闭</a></div>
		<div class="pbox"><div class="pmsg"><b title="提示:" class="ico alert"></b><div class="ct" id="rechargeContent"></div></div></div>
		<div class="popt">
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="closePop()">取消</span></div></div>
			<div class="opt"><div class="pbtn" tabindex="0"><span onclick="makeSure()">确定</span></div></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var bankStatus ='${bankCheck}';
	$(function(){
		if(bankStatus=='ok'){
			alertc("请先进行绑定银行卡操作");
			setInterval(aa,3000);
		}
	});
	function aa(){
		window.location.href="${path}/account/bankCard.html";
	}

	function calculateCashAccount(){
		var cashTypeFee='${cashTypeFee}';
		var cashTotal=$("#cashTotal").val();//提现总额
		var cashF=0;//提现费
		var available=$("#available").val();//可用余额
		//提现金额是否大于0
		if(cashTotal>0){
			
			if(cashTypeFee==1){
				var cashFee=$("#cashFeePer").val();//费率
				
				cashF=cashTotal*cashFee/1000;
				/* if(cashF<2){
						cashF=2;//最小提现手续费
					
					}else if(cashF>50){
						cashF=50;//最大提现手续费
					}  */
			}else if(cashTypeFee==2){
				$.ajax({
					cache : false,
					type : "POST",
					url : "${path }/account/calcCashFees.do",
					data : {"cashTotal":cashTotal},
					async : false,
					error : function(request) {
						alertc("系统繁忙，请稍后重试");
					},
					success : function(data) {
						cashF=data;
					}
				});
			}
			else if(cashTypeFee==3){
				cashF = $("#cashFeeOne").val();
				$("#cashFee").val(cashF);
				$("#cashAccount").val($("#cashTotal").val()-cashF);
			}
			var cashAccount=cashTotal-cashF;
			$("#actualAmount").html("￥"+cashAccount);
			$("#serviceFee").html("￥"+cashF);
			var ava=available-cashTotal;
			if(ava<0){
				$("#withdrawCashErr").show();
			}else{
				$("#withdrawCashErr").hide();
			}
			if(cashAccount>0){
				$("#realTackOut").text(cashAccount);
				$("#errMsg").hide();
			}
			if(cashAccount==0){
				$("#realTackOut").text(0);
				$("#errMsg").hide();
			}
			if(cashAccount<0){
				$("#errMsg").show();
			}
		}
	}
	 function onkey(){
   	  /* value=value.replace(/[^0-9|.]/g,'') */
   	  var str =   $("#cashTotal").val();
   	  $("#cashTotal").val(str.replace(/[^0-9|.]/g,''));
   	  if(str>=100){
   		$("#serviceFee").html($("#cashFeeOne").val()+"元")
   	  }
   	  var cashTotal='';
   		if($("#cashTotal").val()==''){
   			 cashTotal=0;
   		}else{
   			cashTotal= parseFloat($("#cashTotal").val());//提现总额
   		}
   		var available= parseFloat($("#available").val());//可用余额
   		if(cashTotal>available){
     			alertc("账户可用余额不足");
     			/* setInterval(reloadPage,2000); */
     		}
     }
	function subForm(){
		var cashTotal= parseFloat($("#cashTotal").val());//提现总额
		var maxWithdrawal= '${sFRate.sysMaxWithdrawal}';
		var minWithdrawal= '${sFRate.sysMinWithdrawal}';
		var available= parseFloat($("#available").val());//可用余额
		var payPassword = $("#payPassword").val();
		 if(cashTotal<100){
			alertc("提现额度必须大于等于100元");
		}else if(cashTotal>maxWithdrawal){
			alertc("最大提现金额为"+maxWithdrawal+"元");
		}else if(cashTotal<minWithdrawal){
			alertc("最小提现金额为"+minWithdrawal+"元");
		}else if(cashTotal>available){
			alertc("账户可用余额不足");
		}else if(payPassword == undefined || payPassword == ''){
			alertc("请输入交易密码");
			$("#payPassword").focus();
		}else{ 
			$("#rechargeContent").html("确定提现"+$("#cashTotal").val()+"元！");
			$("#tishizheceng").show();
			$("#tishikuang").show();
		} 
	}
	
	function makeSure(){
		closePop();
		withdrawForm.payPassword.value=encryptByDES(withdrawForm.payPassword.value,withdrawForm.publickey.value);
		$.ajax({
          dataType: 'json',
            url:"${path }/account/applyWithDraw.do",
            data:$("#withdrawForm").serialize(),
            type: 'POST',
            success: function(data) {
          		//视频
          		if(data.code=='204'){
          			alertc(data.msg);
          			$("#withdraw").hide();
          			setInterval(reloadPage,3000);
          		}else if(data.code=='212'){
          			alertc(data.msg);
          			setInterval(toWant,3000);
          		}else
            	alertc(data.msg);
            },
            error: function() {
           		 alertcc("提现申请失败！！！");
            }
        });
	}
	
	function closePop(){
		$("#tishizheceng").hide();
		$("#tishikuang").hide();
	}
	
	function toWant(){
		setInterval(window.location.href="${path }/verify/saveUserLoginPass.html",3000);
	}
	function reloadPage(){
		window.location.reload(true);
	}
</script>
</html>
