<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/views";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>发标</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="issueForm" name="issueForm" method="post" action="borrow/${PRE_PATH_EDIT }saveBorrow"  
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="borrowTypeCode" value="${borrowType.code}"/>
				<input type="hidden" name="userId" value="0"/>
				<div class="pageFormContent" layoutH="55">

				<fieldset>
						<legend>借款人</legend>
						
						<dl style="width:100%;">
							<dt  style="width:90px;">请选择借款人：</dt>
							<dd class="unit"  style="width:444px;">
								<span  id="showCheckUserName" style="color: red; float: left;"></span>
			      		<a class="btnLook" width="700" lookupgroup="user" href="user/v_getAllowBorrowUserList">会员列表</a>
			      		<span  id="showCheckUserName" style="color: red; float: left;">（当没有选择借款人，默认是平台发布）</span>
							</dd>
						</dl>
				</fieldset>
				<fieldset>
					<legend>贷款信息</legend>

                    <dl>
                        <dt>信用类型:</dt>
                        <dd class="unit" style="width:400px;">
                            <select name="creditType" class="required" id="creditType">
                                <option value="" selected="selected">--信用类型--</option>
                                    <c:forEach items="${BORROW_ALL_CREDIT_TYPE }" var="item">
                                        <option value="${item.key }">${item.value }</option>
                                    </c:forEach>
                            </select>
                        </dd>
                    </dl>
                    <dl></dl>
					<dl>
						<dt>贷款总额:</dt>
						<dd class="unit">
							<input name="borrowSum" id="borrowSum" class="required digits"
								type="text" />
						</dd>
					</dl>
					<dl>
						<dt>年利率:</dt>
						<dd class="unit">
							<input name="annualInterestRate" size="20"
								id="annualInterestRate" class="required number" type="text" />%
						</dd>
					</dl>
					<c:if test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
					<input name="isDay" type="hidden" value="2"/>
						<dl>
							<dt>每份金额:</dt>
							<dd class="unit">
								<input name="amountPerCopies" id="amountPerCopies"
									class="required digits" min="100" type="text" />
							</dd>
						</dl>
					</c:if>
					<c:if test="${borrowType.code ne BORROW_TYPE_ZHUAN }">
						<dl>
							<dt>是否天标:</dt>
							<dd class="unit">
								<input name="isDay" type="radio" value="1"
									onclick="changeIsDay();" />是 <input name="isDay" type="radio"
									value="2" onclick="changeIsDay();" checked="checked" />否
							</dd>
						</dl>
					</c:if>
					<dl  style="width:550px;">
						<dt>贷款期限:</dt>
						<dd class="unit" style="width:400px;">
							<select name="borrowTimeLimit" class="required" id="monthSelect"
								style="display: block;">
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
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span> <label id="dayAlert"
								style="color: red; width: 200px; display: none">系统约定按每月30天计算利率</label>
						</dd>
					</dl>
					<dl>
						<dt>贷款用途:</dt>
						<dd class="unit">
							<select name="borrowUse" class="required">
								<option value="" selected="selected">--请选择用途--</option>
								<c:forEach var="item" items="${BORROW_ALL_BORROW_USE}"> 
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span> 
						</dd>
					</dl>
					<dl>
						<dt>投标密码:</dt>
						<dd class="unit">
							<input name="tenderPassword" class="alphanumeric"
								id="tenderPassword" type="password" />
						</dd>
					</dl>
					<dl>
						<dt>最低投标金额:</dt>
						<dd class="unit">
							<select name="minAmount">
								<option  value="1">1元</option>	
								<option value="50">50元</option>
								<option value="100">100元</option>
								<option value="150">150元</option>
								<option value="200">200元</option>
								<option value="300">300元</option>
								<option value="500">500元</option>
								<option value="1000">1000元</option>
								<option value="1500">1500元</option>
								<option value="3000">3000元</option>
								<option value="5000">5000元</option>
								<option value="10000">10000元</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>最多投标金额:</dt>
						<dd class="unit">
							<select name="maxAmount">
								<option value="0">没有限制</option>
								<option value="2000">2000元</option>
								<option value="5000">5000元</option>
								<option value="10000">10000元</option>
								<option value="15000">15000元</option>
								<option value="20000">20000元</option>
								<option value="50000">50000元</option>
								<option value="100000">100000元</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>有效时间:</dt>
						<dd class="unit">
							<select name="validTime" class="required">
								<option value="" selected="selected">--有效时间--</option>
								<option value="1">1天</option>
								<option value="2">2天</option>
								<option value="3">3天</option>
								<option value="4">4天</option>
								<option value="5">5天</option>
								<option value="6">6天</option>
								<option value="7">7天</option>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span>
						</dd>
					</dl>
					<dl>
						<dt>还款方式:</dt>
						<dd class="unit">
							<select name="repaymentStyle"  class="required">
							<option value="" selected="selected">---还款方式----</option>
							<c:forEach var="item" items="${BORROW_ALL_REPAYMENT_STYLE}"> 
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>投标奖励</legend>
					<p style="width: 25%">
						<input type="radio" name="tenderAward" value="1"
							onclick="changeTenderAward(1);" checked="checked" />不设置奖励
					</p>
					<p style="width: 30%">
						<input type="radio" name="tenderAward"
							onclick="changeTenderAward(2);" value="2" />按投标金额比例奖励 <span
							id="rateDiv" style="display: none;"> <input 
							name="proportionRate" size="10" id="proportionRate" />%
						</span>
					</p>
					<p style="width: 33%">
						<input type="radio" name="tenderAward"
							onclick="changeTenderAward(3);" value="3" />按固定金额分摊奖励 <span
							id="partDiv" style="display: none;"> <input 
							name="partAmount" size="10" id="partAmount" />元
						</span>
					</p>
				</fieldset>
				<fieldset>
					<legend>账户信息</legend>
					<p style="width: 22%;">
						<input type="checkbox" name="openAccount" id="openAccount" checked="checked" value="1" />公开我的帐户资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openBorrow" id="openBorrow" checked="checked" value="1" />公开我的借款资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openTender" id="openTender"  value="1" />公开我的投标资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openCredit" id="openCredit" checked="checked" value="1" />公开我的信用额度情况
					</p>
				</fieldset>
				<fieldset>
					<legend>详细信息</legend>
					<dl class="nowrap">
						<dt>贷款标题:</dt>
						<dd>
							<input name="borrowTitle" id="borrowTitle" type="text" size="50"
								class="required" />
						</dd>
					</dl>

					<dl class="nowrap">
						<dt>详细信息:</dt>
						<dd>
							<textarea rows="16" cols="100" class="editor" id="borrowContent"
								name="borrowContent" upImgUrl="upload/editorImg"></textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<p>
							<input type="file" name="uploadifys" id="uploadifys" />
							<span id="result" style="font-size: 13px; color: red"></span></p>
							<div class="divider" style="border-style:none;"></div>
							<div id="fileQueues"
								style="width: 740px auto; <c:if test="${not empty  attachList}"> border: 1px solid #99BBE8;</c:if>">
								<ul id="fileULs" style="">
									<c:forEach var="attach" items="${attachList }">
										<li id="${attach.id }"><input type="hidden"
											name="attachPath" value="${attach.attachPath}"
											style="width: 300px;"></input> <input type="hidden"
											name='attachFileType' value="${attach.attachFileType}"></input>
											<%-- <input name="attachId" value="${attach.id }"/> --%>
											&nbsp;附件名：<input name="attachRealTitle"
											value="${attach.attachRealTitle}"
											style="width: 200px; border: none;"></input> &nbsp;重命名： <input
											name="attachTitle" value="${attach.attachTitle}"
											style="width: 200px;"></input> &nbsp; 排序： <input
											name="attachSequence" value="${attach.attachSequence}"
											style="width: 20px;"></input> <a
											href="javaScript:delet(${attach.id })">&nbsp;删除</a>
											<div style="height: 10px;"></div></li>
									</c:forEach>
								</ul>
							</div>
						<p>
					</dl>
					<dl class="nowrap">
						<dt>验证码:</dt>
						<dd>
							<input maxlength="4" style="width: 60px; margin-bottom: 10px;"
								name="captcha" type="text" id="captcha" class="required">
							<img id="captchaImg" style="margin-top: -5px;"
								src="<%=path%>/captcha.svl"
								onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1"
								valign="bottom" alt="点击更新" title="点击更新" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt></dt>
						<dd>
							<input type="file" name="uploadify" id="uploadify" /> 
						    <div id="fileQueue" style="width: 740px auto;">
						      <ul id="fileUL" style="list-style: none;">
							        <%-- <li id='img_1'> 
							          <img src='<%=path %>${.borrowPicture}' style="width: 200px;height: 200px;"></img>
							          <input type="hidden" name="borrowPicture" value="${borrowType.borrowPicture}"/>&nbsp;&nbsp;&nbsp;
							          &nbsp;&nbsp;<a href="javaScript:delet('img_1')" >删除</a>
							        </li> --%>
						       </ul>
						    </div>
						</dd>
					</dl>
				</fieldset>

			</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="formSubmit();">
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
<script type="text/javascript">
var indx = 0;
$(document).ready(function() {
	$("#uploadifys").uploadify({  
        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
        'buttonImg'      : '<%=basePath %>/uploadify/buttonImg.png',  
        'folder'         : '/jxdBlog/photos',  
        'queueID'        : 'fileQueues',  
        'auto'           : true,  
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'simUploadLimit' : 20,  
        'fileExt'        : '*.jpg;*.jpeg;*.gif;*.png;*.bmp;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.wps;*.pdf;*.rar;*.zip',  
        'fileDesc'       : '请上传合法文件(*.jpg;*.jpeg;*.gif;*.png;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.rar;*.zip)',  
        'sizeLimit'		 : 20000000,    
        'onComplete'  :function(event,queueId,fileObj,response,data){  
        	var retJson = eval(response)[0];
            /* $('#result').html('还剩'+data.fileCount +'个附件没上传'); */
            
            $('#fileULs').append("<li id='"+queueId+"'>&nbsp;附件名：<input name='attachRealTitle' value='"+retJson.filename+"' ></input>"
            +"&nbsp;&nbsp;重命名：<input name='attachTitle' value='"+retJson.filename+"'></input>"
            +"<input name='attachPath' type='hidden' value='"+retJson.filepath+"' ></input>"
            
            +"<input name='attachFileType'  type='hidden' value='"+retJson.suffix+"'></input>"
            
            + "&nbsp;&nbsp;&nbsp;&nbsp;排序：<input name='attachSequence' value='0'></input>"
             +"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('"+queueId+"')\" >删除</a></li>"
             ); 
            
            $('input[name="attachRealTitle"]').css("width","200px");
            $('input[name="attachRealTitle"]').css("border","none");
            $('input[name="attachTitle"]').css("width","200px");
            $('input[name="attachSequence"]').css("width","20px");
           
            
        }
    });
	
    $("#uploadify").uploadify({  
        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
        'buttonImg'      : '<%=basePath %>/uploadify/buttonImglogo.png',  
        'folder'         : '/jxdBlog/photos',  
        'queueID'        : 'fileQueue',  
        'auto'           : true,  
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'simUploadLimit' : 999,  
        'fileExt'        : '*.png;*.gif;*.jpg;*.jpeg',  
        'fileDesc'       : '*.png,*.gif,*.jpg,*.jpeg',  
        'onComplete'  :function(event,queueId,fileObj,response,data){
        	indx++;
        	var retJson = eval(response)[0];
        	$("#fileUL").append("<li  id='li_"+indx+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 200px;height: 200px;' ></img>"
        	+"<input type='hidden' name='borrowPicture' type='text' value='"+retJson.filepath+"' ></input>"
        	+"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a>"
        	+"</li>");
        	
        }  
    }); 
});
function delet(attachId){
	$("#"+attachId).remove();
}
function navTabAjaxDone(json) {
	$("#captchaImg").click();
	$("input[name='captcha']").val("");
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok) {
		if (json.navTabId) { //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			navTabPageBreak();
		}
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function() {
				navTab.closeCurrentTab();
			}, 100);
		}
		else if("forward" == json.callbackType){
			navTab.reload(json.forwardUrl);
		}

	}

}
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
		var timeLimitSelect = $("select[name='borrowTimeLimit']");
		timeLimitSelect.html(getOption("", "--贷款期限--"));
		for (i = 1; i <= len; i++) {
			timeLimitSelect.html(timeLimitSelect.html()
					+ getOption(i, i + show));
		}
		timeLimitSelect.html(timeLimitSelect.html()
				+ getOption(45, 45 + show));
	}
	function getOption(value, text) {
		return "<option value=\""+value+"\">" + text + "</option>";
	}

	//设置投资奖励
	var lastAwardType = "";
	function changeTenderAward(val) {
		if(lastAwardType == val){
			return;
		}
		lastAwardType = val;
		$("#rateDiv").find("input").val("");
		$("#partDiv").find("input").val("");
		$("#rateDiv").find("input").removeClass("required").removeClass("number");
		$("#partDiv").find("input").removeClass("required").removeClass("number");
		if (val == 1) {
			$("#rateDiv").hide();
			$("#partDiv").hide();
		}
		if (val == 2) {
			$("#rateDiv").show(); 
			$("#partDiv").hide();
			$("#rateDiv").find("input").addClass("required").addClass("number");
			$("#partDiv").find("input").removeClass("required").removeClass("number");
		}
		if (val == 3) {
			$("#rateDiv").hide();
			$("#partDiv").show();
			$("#rateDiv").find("input").removeClass("required").removeClass("number");
			$("#partDiv").find("input").addClass("required").addClass("number");
		}
	}

	function formSubmit() {

		$("#issueForm").submit();
	}
</script>
<style type="text/css">
	.pageFormContent p{
		width:50%;
	}
</style>
</html>