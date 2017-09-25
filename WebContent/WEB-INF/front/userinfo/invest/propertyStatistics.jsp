<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }资产统计</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
<div class="gywm-bao">
<div class="gywm-nrbao">
  <jsp:include page="/account/userTop.do"></jsp:include>
  <div class="wdzh-nrb">
    <jsp:include page="/account/userLeft.do"></jsp:include>
    <div class="wdzh-right-k">
    <div class="gywm-right-bt"><span style=" border-right:5px solid #ff4922; display:block; width:5px; height:24px; float:left; margin-right:10px;"></span><span style="line-height:24px; font-size:18px; color:#ff4922;">资产统计</span></div>
    <div style="border:0;" class="wdzh-sx">
    	<div class="stats-items assets-tab">
                <div id="TabTit" class="items">
                    <dl class="tab-tit stats-entry current">
                        <dt><b>账户余额</b><a title="帮助" href="javascript:;"><img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></a></dt>
                        <dd>
                            <strong><ins>￥</ins>
                                ${userAccount.allMoney }
                            </strong>
                            <p style="font-size:13px;"><span>可用余额</span> ¥${userAccount.availableMoney }<a title="帮助" href="javascript:;"><img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></a></p>
                        </dd>
                    </dl>
                    <dl class="tab-tit stats-entry">
                        <dt><b>账户总资产</b><a title="帮助" href="javascript:;"><img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></a></dt>
                        <dd><strong><ins>￥</ins>
                            ${userAccount.allMoney }
                        </strong></dd>
                    </dl>
                    <dl class="tab-tit stats-entry">
                        <dt><b>可用总额</b><a title="帮助" href="javascript:;"><img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></a></dt>
                        <dd><strong><ins>￥</ins>${userAccount.availableMoney }</strong></dd>
                    </dl>
                    <dl class="tab-tit stats-entry">
                        <dt><b>冻结金额</b><a title="帮助" href="javascript:;"><img width="12" height="14" src="${frontPath }/images/ny/wdzh_40.jpg"></a></dt>
                        <dd><strong>${userAccount.unavailableMoney }</strong> </dd>
                    </dl>
                </div>                
            </div>  
            
            
            <div style="font-size:18px; margin:30px 0px 15px 0px;">账号余额</div>
            <div style=" margin-bottom:20px;" class="wdzh-sx">
                    <table cellspacing="0" cellpadding="0" class="assets-table">
                        <tr>
                            <th></th>
                            <th>定存宝(￥)</th>
                            <th>月息通(￥)</th>
                            <th>小计(￥)</th>
                        </tr>
                        <tr>
                            <td class="txt">总充值金额</td>
                            <td colspan="2" class="center"><span class="light-green">+ ${sumMoney }</span></td>
                            <td>+ ${sumMoney }</td>
                        </tr>
                        <tr>
                            <td class="txt">回收本金</td>
                            <td><span class="light-green">+ ${repossessedBao.capital }</span></td>
                            <td><span class="light-green">+ ${repossessedSum.capital-repossessedBao.capital }</span></td>
                            <td>+ ${repossessedSum.capital }</td>
                        </tr>
                        <tr>
                            <td class="txt">回收利息</td>
                            <td><span class="light-green">+ ${repossessedBao.interest }</span></td>
                            <td><span class="light-green">+ ${repossessedSum.interest-repossessedBao.interest }</span></td>
                            <td>+ ${repossessedSum.interest }</td>
                        </tr>
                        <!-- <tr>
                            <td class="txt">复投收益</td>
                            <td><span class="light-org">- 0.00</span></td>
                            <td>--</td>
                            <td>- 0.00</td>
                        </tr>
                        <tr>
                            <td class="txt">回收罚息</td>
                            <td>--</td>
                            <td><span class="light-green">+ 0.00</span></td>
                            <td>+ 0.00</td>
                        </tr>
                        <tr>
                            <td class="txt">赎回投资</td>
                            <td><span class="light-green">+ 0.00</span>
                            </td>
                            <td><span class="light-green">+ 0.00</span></td>
                            <td>+ 0.00</td>
                        </tr> -->
                        <!-- <tr>
                            <td class="txt">获得垫付资金</td>
                            <td>--</td>
                            <td><span class="light-green">+ 0.00</span></td>
                            <td>+ 0.00</td>
                        </tr> -->
                        <tr>
                            <td class="txt">已投资金额</td>
                            <td><span class="light-org">- ${effBao.effectiveAmount }</span></td>
                            <td><span class="light-org">- ${eff.effectiveAmount-effBao.effectiveAmount }</span></td>
                            <td>- ${eff.effectiveAmount }</td>
                        </tr>
                       <!--  <tr>
                            <td class="txt">买入债权</td>
                            <td>--</td>
                            <td><span class="light-org">- 0.00</span></td>
                            <td>- 0.00</td>
                        </tr> -->
                        <tr>
                            <td class="txt">完成提现金额</td>
                            <td colspan="2" class="center"><span class="light-org">- ${txsumMoney }</span></td>
                            <td>- ${txsumMoney }</td>
                        </tr>
                        <tr>
                            <td class="txt">手续费</td>
                            <td colspan="2" class="center"><span class="light-org">- ${repossessedSum.interestFee }</span></td>
                            <td>- ${repossessedSum.interestFee }</td>
                        </tr>
                        <tr class="high">
                            <td class="txt">账户余额</td>
                            <td></td>
                            <td></td>
                            <td><span class="light-blue">${userAccount.allMoney }</span></td>
                        </tr>
                        <!-- <tr>
                            <td class="txt">投资中冻结金额</td>
                            <td>--</td>
                            <td><span class="light-org">- 0.00</span></td>
                            <td>- 0.00</td>
                        </tr> -->
                        <!-- <tr>
                            <td class="txt">提现中冻结金额</td>
                            <td colspan="2" class="center"><span class="light-org">- 0.00</span></td>
                            <td>- 0.00</td>
                        </tr> -->
                        <tr class="high">
                            <td class="txt">可用余额</td>
                            <td></td>
                            <td></td>
                            <td><span class="light-blue">${userAccount.availableMoney }</span></td>
                        </tr>
                    </table>
                </div>
            
	</div>
    </div>
  </div>
</div>
</div>

 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">

$(function(){
	userTopHover(6);
})
</script>
</html>