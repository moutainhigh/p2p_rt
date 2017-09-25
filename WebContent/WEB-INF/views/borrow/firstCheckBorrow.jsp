<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
java.util.Date currentTime = new java.util.Date();
String date = formatter.format(currentTime); 
String path = request.getContextPath();
String basePath = path+"/common/views";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body >
		<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data" action="borrow/o_firstCheckBorrow"
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="type" value="update" />
				<input type="hidden" name="borrowId" value="${borrow.id }" />
				<input type="hidden" name="validTimes" value="${borrow.validTime }" />
				<div class="pageFormContent" layoutH="55">
					<fieldset>
						<legend>贷款信息</legend>
						<dl>
                            <dt>信用类型:</dt>
                            <dd class="unit">   
                                <select name="creditType" disabled="disabled">
                                    <option value="${borrow.creditType }" selected="selected">${BORROW_ALL_CREDIT_TYPE[borrow.creditType]}</option>
                                </select>
                            </dd>
                        </dl>
                        <dl></dl>
						<dl>
							<dt>贷款总额:</dt>
							<dd class="unit"><input name="borrowSum" id="borrowSum" value="${borrow.borrowSum }" readonly="readonly" type="text" /></dd>
						</dl>
						<dl>
							<dt>年利率:</dt>
							<dd class="unit"><input name="annualInterestRate" id="annualInterestRate" value="${borrow.annualInterestRate }" readonly="readonly" type="text" /></dd>
						</dl>
						<c:if test="${borrow.bidKind !=2 }">
						<dl>
							<dt>是否天标:</dt>
							<dd class="unit">
							<c:if test="${borrow.isDay==1 }">
							<input name="isDay" type="radio" disabled="disabled" value="1" checked="checked" />是
							<input name="isDay" type="radio"  value="2" disabled="disabled"	 />否
							</c:if>
							<c:if test="${borrow.isDay==2 }">
							<input name="isDay" type="radio" disabled="disabled" value="1"  />是
							<input name="isDay" type="radio"  value="2" disabled="disabled"	checked="checked" />否
							</c:if>
							</dd>
						</dl>
						</c:if>
						<c:if test="${borrow.bidKind ==2 }">
						<dl>
							<dt>每份金额:</dt>
							<dd class="unit">
								<input name="amountPerCopies" id="amountPerCopies" value="${borrow.amountPerCopies }" readonly="readonly" type="text" />
							</dd>
						</dl>
						</c:if>
						<dl>
							<dt>贷款期限:</dt>
							<dd class="unit">
								<select name="borrowTimeLimit" id="monthSelect" disabled="disabled" style="display: block;">
								<option value="${borrow.borrowTimeLimit }" selected="selected">${borrow.borrowTimeLimit }<c:if test="${borrow.isDay==1 }">天</c:if><c:if test="${borrow.isDay==2 }">个月</c:if></option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>贷款用途:</dt>
							<dd class="unit">	
								<select name="borrowUse" disabled="disabled">
									<option value="${borrow.borrowUse }" selected="selected">${BORROW_ALL_BORROW_USE[borrow.borrowUse]}</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>投标密码:</dt>
							<dd class="unit">
								<input value="${borrow.tenderPassword }" name="tenderPassword" readonly="readonly" class="alphanumeric" id="tenderPassword" type="password"  />
							</dd>
						</dl>
							<dl>
							<dt>最低投标金额:</dt>
							<dd class="unit">
								<select name="minAmount" class="valid">						
									<option  value="50">50元</option>
									<option  value="100">100元</option>
									<option  value="150">150元</option>
									<option  value="200">200元</option>
									<option  value="300">300元</option>
									<option  value="500">500元</option>
									<option  value="1000">1000元</option>
									<option  value="1500">1500元</option>
									<option  value="3000">3000元</option>
									<option  value="5000">5000元</option>
									<option  value="10000">10000元</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>最多投标金额:</dt>
							<dd class="unit">
							<select name="maxAmount" class="valid">
								<option  value="0">没有限制</option>
								<option  value="2000">2000元</option>
								<option  value="5000">5000元</option>
								<option  value="10000">10000元</option>
								<option  value="15000">15000元</option>
								<option  value="20000">20000元</option>
								<option  value="50000">50000元</option>
								<option  value="100000">100000元</option>
							</select>
							</dd>
						</dl>
						<dl>
							<dt>有效时间:</dt>
							<dd class="unit">
								<select name="validTime" id="validTime" disabled="disabled">
									<option value="${borrow.validTime }" selected="selected">${borrow.validTime }天</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>还款方式:</dt>
							<dd class="unit">
								<select name="repaymentStyle" disabled="disabled">
									<option value="${borrow.repaymentStyle }" selected="selected">${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}</option>
								</select>
							</dd>
						</dl>
					</fieldset>
					<fieldset>
							<legend>投标奖励</legend>
							<c:if test="${borrow.tenderAward==1 }">
							<p style="width: 25%">
								<input type="radio" name="tenderAward" disabled="disabled" value="1" onclick="changeTenderAward(1);"  checked="checked"/>不设置奖励
							</p>
							<p style="width: 30%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(2);" value="2"/>按投标金额比例奖励
									<span id="rateDiv" style="display: none;">
										<input name="proportionRate" size="10" id="proportionRate"/>%
									</span>
							</p>
							<p style="width: 33%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(3);" value="3"/>按固定金额分摊奖励
									<span id="partDiv" style="display: none;">
										<input name="partAmount" size="10" id="partAmount" />元
									</span>
							</p>
							</c:if>
							<c:if test="${borrow.tenderAward==2 }">
							<p style="width: 25%">
								<input type="radio" name="tenderAward" disabled="disabled" value="1" onclick="changeTenderAward(1);"  />不设置奖励
							</p>
							<p style="width: 30%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(2);" checked="checked" value="2"/>按投标金额比例奖励
									<span id="rateDiv">
										<input name="proportionRate" value="${borrow.proportionRate }" readonly="readonly" size="10" id="proportionRate"/>%
									</span>
							</p>
							<p style="width: 33%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(3);" value="3"/>按固定金额分摊奖励
									<span id="partDiv" style="display: none;">
										<input name="partAmount" size="10" id="partAmount" readonly="readonly" />元
									</span>
							</p>
							</c:if>
							<c:if test="${borrow.tenderAward==3 }">
							<p style="width: 25%">
								<input type="radio" name="tenderAward" disabled="disabled" value="1" onclick="changeTenderAward(1);"  />不设置奖励
							</p>
							<p style="width: 30%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(2);"  value="2"/>按投标金额比例奖励
									<span id="rateDiv" style="display: none;">
										<input name="proportionRate"  size="10" id="proportionRate"/>%
									</span>
							</p>
							<p style="width: 33%">
								<input type="radio" name="tenderAward" disabled="disabled" onclick="changeTenderAward(3);" value="3"/>按固定金额分摊奖励
									<span id="partDiv" >
										<input name="partAmount" value="${borrow.partAmount }" size="10" checked="checked" id="partAmount" />元
									</span>
							</p>
							</c:if>
					</fieldset>
					<fieldset>
							<legend>账户信息</legend>
							<p style="width: 22%;">
								<input type="checkbox" disabled="disabled" name="openAccount"  <c:if test="${borrow.openAccount==1 }">checked="checked"</c:if> id="openAccount" value="1"/>公开我的帐户资金情况
							</p>
							<p style="width: 22%;">
								<input type="checkbox" disabled="disabled" name="openBorrow" <c:if test="${borrow.openBorrow==1 }">checked="checked"</c:if> id="openBorrow" value="1"/>公开我的借款资金情况
							</p>
							<p style="width: 22%;">
								<input type="checkbox" disabled="disabled" name="openTender" <c:if test="${borrow.openTender==1 }">checked="checked"</c:if> id="openTender" value="1"/>公开我的投标资金情况
							</p>
							<p style="width: 22%;">
								<input type="checkbox" disabled="disabled" name="openCredit" <c:if test="${borrow.openCredit==1 }">checked="checked"</c:if> id="openCredit" value="1"/>公开我的信用额度情况
							</p>
					</fieldset>
					<fieldset>
							<legend>详细信息</legend>
							<dl class="nowrap">
								<dt>贷款标题:</dt>
								<dd><input name="borrowTitle" id="borrowTitle" value="${borrow.borrowTitle}"  readonly="readonly" type="text" /></dd>
							</dl>
							<dl class="nowrap">
								<dt>详细信息:</dt>
								<dd>
								<textarea class="editor textInput disabled"  disabled="disabled"  readonly="readonly" rows="16" cols="100" id="borrowContent" name="borrowContent"  tools="Cut,Copy,Paste,|,Source,Fullscreen,About">${borrow.borrowContent}</textarea>
								</dd>
							</dl>
							<dl class="nowrap">
								<dt>&nbsp;</dt>
								<dd>
								    <div id="fileQueue" style="width: 740px auto; ">
								      <ul id="fileUL" style="list-style: none;">
									      <c:if test="${not empty borrow.borrowPicture}">
									        <li id='img_${borrow.id }'> 
									          <img src='<%=path %>${borrow.borrowPicture}' alt="标图片" title="标图片" style="width: 200px;height: 200px;"></img>
									          <input type="hidden" name="borrowPicture" value="${borrow.borrowPicture}"/>&nbsp;&nbsp;&nbsp;
									          &nbsp;&nbsp;
									        </li>
									       </c:if>
									       <c:if test="${empty borrow.borrowPicture}">
									       	没有上传图片
									       </c:if>
								       </ul>
								    </div>
								    </dd>
							</dl>
					</fieldset>
					<fieldset style="height: auto;">
							<legend>审核</legend>
							<dl class="nowrap">
								<dt>审核状态:</dt>
								<dd><input name="borrowStatus" onclick="changeHiddenDiv(2)" id="borrowStatus" value="2" type="radio" />通过  &nbsp;&nbsp;&nbsp;&nbsp;<input name="borrowStatus" id="borrowStatus" checked="checked" value="3" onclick="changeHiddenDiv(3)" type="radio" />未通过</dd> 
							</dl>
							<div id="hidden" style="display: none;">
							<dl>
								<dt>限制投标人待收款 :</dt>
								<dd>
								<select name="minWaitRepossess" id="minWaitRepossess" >
								<option value="0" selected="selected">不限</option>
								<option value="1000">1000元</option>
								<option value="2000">2000元</option>
								<option value="5000">5000元</option>
								<option value="10000">10000元</option>
								<option value="20000">20000元</option>
								</select>
								</dd>
							</dl>
							<dl class="nowrap">
								<dt>发布时间:</dt>
								<dd><input type="text" value="<%=date %>" name="publishDatetime" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true"><a class="inputDateButton" href="javascript:;">选择</a></dd>
							</dl>
							<dl class="nowrap">
								<dt>用户等级:</dt>
								<dd>
									<select name="repaymentStyle" id="repaymentStyle" >
									<option value=" " selected="selected">不限</option>
									<c:forEach items="${userTypeList }" var="userType">
										<option value="${userType.id }">${userType.name }</option>
									</c:forEach>
									</select>
								</dd>
							</dl>
							<dl>
								<dt>标星等级:</dt>
								<dd>
									<select name="bidRank" id="bidRank" >
									<option value="" selected="selected">--请选择--</option>
									<c:forEach items="${BORROW_ALL_BID_RANK }" var="item">
										<option value="${item.key }">${item.value }</option>
									</c:forEach>
									</select>
								</dd>
							</dl>
						    <!-- <dl class="nowrap">
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
									</dl>-->
								</div>
							<dl class="nowrap">
								<dt>审核备注:</dt>
								<dd><textarea class="textInput valid" name="verifyTrialRemark" rows="5" cols="45"></textarea></dd>
							</dl>
					</fieldset>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
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
function delet(attachId){
	$("#"+attachId).remove();
}
var imgSuffix = ".jpg,";
$(document).ready(function() { 
    <%-- $("#uploadifys").uploadify({  
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
    }); --%> 
    
});  
	$(function(){
		var minTenderMoney = ${borrow.minAmount };
		$("select[name='minAmount']").val(minTenderMoney+"");
		var maxAmount = ${borrow.maxAmount };
		$("select[name='maxAmount']").val(maxAmount+"");
	})
	
	function changeHiddenDiv(val){
		if(val == 2){
			$("#hidden").show();
		}
		if(val == 3){
			$("#hidden").hide();
		}
	}
	
	
	
</script>
</html>