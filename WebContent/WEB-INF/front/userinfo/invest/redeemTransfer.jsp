<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow: hidden;">
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-投资赎回</title>
<style type="text/css">
.form-opt {
  padding: 50px 30px 30px;
  position: relative;
}
.btn-submit{
	border-style: solid;
    border-color: #6C92AD;
    border-width: 1px 0px 0px;
    clear: both;
    height: 42px;
    padding: 0 8px;
    background-color: #F6F9FC;
    padding-left: 330px;
}
.btn{
	display: inline-block;
    width: 41px;
    float: left;
    line-height: 26px;
    border: 1px solid #999999;
    border-radius: 5px;
    padding-left: 12px;
    margin-top: 6px;
    cursor: pointer;
}
a.btn:hover{
	color:#666666;
}
</style>
</head>
<body style="width: 458px;">
	<div id="formDiv">
		<form action="" method="post" id="form">
			<input type="hidden" name="tenderId" id="tenderId"
				value="${tenderId }" /> <input type="hidden" name="transferFee"
				id="transferFee" value="${transferFee }" />
			<div id="from-opt" class="form-opt" style="padding-top: 19px;height: 109px;">
				<ul class="items" id="items" style="margin-left: 43px;margin-top: 30px;">
					<li id="txt" style="width: 77px; display: inline; float: left; margin-right: 3px; padding-top: 10px; text-align: left;">转让金额：</li>
					<li>
						<input type="text" value="${transferMoney }" name="transferMoney" id="transferMoney"
						style="border: 1px solid #B4B9BD; height: 18px; line-height: 18px; padding: 11px 5px; width: 200px;">
					</li>
					<li style="padding-top: 8px;">
						<span style="height: 18px; line-height: 25px; padding-left: 79px; padding-bottom: 10px; width: 200px; color: #F7772D;">您本次转让手续费为：${transferFee}元</span>
					</li>
				</ul>
			</div>
			<div class="btn-submit">
				<a href="javascript:;" onclick="frmSubmit();"
					class="btn btnSize_1 btn_orange" id="realNameAuthenBt">提交</a> 
				<a href="javascript:;" onclick="closeCurrent();"
					class="btn btnSize_1 btn_orange" id="realNameAuthenBt" style="margin-left: 9px;">取消</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	function frmSubmit(){
		
		window.open('', '_self');window.close();
		$("#form").validate({						  
			rules: {
				transferMoney: {
					required: true,
					number:true
				},
				transferFee:{
					required:true
				},
			},
		});
		if($("#form").valid()){
			$.ajax({
				dataType:'json',
				url:"${path}/borrowTransfer/requestTransfer.do",
				data:$("#form").serialize(),
				type:'post',
				success:function(data){
					alertc(data.msg,closeCurrent);
					window.parent.location.reload();
					this.window.close();
					//window.location.href="${path}/invest/transferRedeem.html";
				},
				error:function(data){
					alertc("转让失败");
					this.window.close();
					window.location.href="${path}/invest/transferRedeem.html";
				}
			});  
		}
	}
	function closeCurrent(){
		window.parent.reflashTable();
	}
 </script>
</html>