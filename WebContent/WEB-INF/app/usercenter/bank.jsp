<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html >
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>绑定银行卡</title>
    <%@include file="../taglibss.jsp"%>
    <meta http-equiv="Expires" content="-1">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache"> 
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <%-- <link rel="stylesheet" type="text/css" href="${configjscss}/css/jquery.mobile-1.4.2.min.css"> --%>
	  <link rel="stylesheet" type="text/css" href="${configjscss}/css/common.css">
    <script type="text/javascript" src="${configjscss}/js/jquery-1.8.3.min.js"></script>
    <%--  <script type="text/javascript" src="${configjscss}/js/global-1.1.0.min.js"></script> --%>
    <%-- <script type="text/javascript" src="${configjscss}/js/jquery.mobile-1.4.2.min.js"></script> --%>
    <script type="text/javascript" src="${configjscss }/js/provincecity.js"></script>

    <style type="text/css">
      .sl_fg4 {
        width: 184px;
        height: 56px;
        background: #000;
        border-radius: 5px;
        filter: alpha(opacity:30);
        opacity: 0.6;
        -moz-opacity: 0.3;
        -khtml-opacity: 0.3;
        /* margin: 0 auto; */
        text-align: center;
        color: #fff;
        position: absolute;
        left: 26%;
        line-height: 56px;
        font-size: 17px;
      }
    </style>
  </head>

  <body>
    <div>
      <h5 class="sl_qibd">请绑定本人的储蓄卡，目前仅支持绑定一张卡</h5>

      <div class="jbx_con">   
        <h1><img src="${configjscss }/images/sl_02.png" alt="">绑定银行卡</h1>

        <form action="${path }/account/addBankCard.do" method="post" id="addNewCardForm">
          <ol>
            <li><span>${user.userRealname }</span></li>
            <input type="hidden" name="userId" id="userId"value="${user.id }" />
            <li>
              <select class="txt5" name="bankId" id="bankId" style="margin-left: 0; width: 9.5rem;">
                <option value="">----请选择----</option>
  		          <c:forEach var="bank" items="${bank }">
  				        <option value="${bank.id }">${bank.bankName }</option>
  		          </c:forEach>
              </select>
              <b class="sl_xzyh">选择银行</b>
              <div class="sl_fg4" style="display: none;"></div>
            </li>
            <li>
              <select class="txt5" name="bankProvince" id="bankProvince" onchange="getCity()" style="margin-left: 0; width: 9.5rem;">
                <option value="">----请选择----</option>
              </select>
              <b class="sl_xzyh">开户城市</b>
              <select class="txt5" name="bankCt" id="bankCt" style="margin-left: 0; width: 9.5rem;">
              </select>
            </li>
            <li>
              <!-- <i style="color:#ccc;">银行卡号</i> -->
              <span><input type="text" name="bankAccount"
    							id="bankAccount" onkeyup="value=value.replace(/[^0-9]/g,'')"
    							minlength="10" maxlength="19" placeholder="银行卡号"></span>
            </li>
            <li>
              <i style="color:#ccc;">开户支行</i>
              <span><input type="text"  name="bankBranch" id="bankBranch"></span>
            </li>
            <input type="hidden" name="bankCity" id="bankCity" />
            <li>
              <div class="btn">
                <a rel="external" onclick="javascript:addnewcard()" class="com_btn com_btn1">确认</a>
              </div>
            </li>
          </ol>
        </form>

      </div><!-- yhk_con -->

      <script type="text/javascript">
        $(function(){
        	var msg = '${mesg}';
        	if(msg!=''){
        		showLoader(msg);
        	}
        })
        function addnewcard() {
      		var regexBank = /^\d{16}|\d{19}$/;
          if($("#bankName").val()==''||$("#bankAccount").val()==''||$("#bankProvince option:selected").val()=='----请选择----'||$("#bankBranch").val()==''){
        		$(".sl_fg4").text("请输入合法的字段");
        		$(".sl_fg4").show();
        		setInterval(reloadPage,3000);
      		}
          else if(!regexBank.test($("#bankAccount").val())){
      			$(".sl_fg4").text("请填写正确的卡号");
      			$(".sl_fg4").show();
      			setInterval(reloadPage,3000);
      		}
          else{
      			$("#bankCity").val($("#bankProvince option:selected").text()+"|"+$("#bankCt option:selected").text()+"|"
      					+$("#bankProvince option:selected").val()+"|"+$("#bankCt option:selected").val()
      					+"|"+$("#bankProvince option:selected").attr('curcode')+"|"+$("#bankCt option:selected").attr('curcode'));
      			$.ajax({
      				type : "POST",
      				url : "${pathApp}/userCenterOperte/addBankCard.do?token=${token}",
      				data : $('#addNewCardForm').serialize(),
      				async : false,
      				success : function(data) {
      					if (data.code == '100') {
      						$(".sl_fg4").text(data.msg);
      						$(".sl_fg4").show();
      						setInterval(reloadPage,3000);
      					}
                else {
      						$(".sl_fg4").text(data.msg);
      						$(".sl_fg4").show();
      						setInterval(reloadPage,3000);
      					}
      				}
      			});
      		}
        }
        function reloadPage(){
        	$(".sl_fg4").hide();
        }
      </script>

    </div>
  </body>
</html>