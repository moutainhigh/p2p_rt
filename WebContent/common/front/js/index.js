﻿﻿﻿var nowDate ;
var borrowTime ="";
var Published = true;
var borrowStatus ;
var ignoreIds = new Array();
/**
 * 统计
 */
function getStatistics() {
	
	$.get($_path+"/getStatistics.do", function(data, status) {
		//投资人数
		var userNum = data.userNum;
		$("#tenderUsers").html(addCommas(userNum));
		//累计收益
		var getAllInterestMoney=data.getAllInterestMoney;
		$("#getAllInterestMoney").html("￥"+addCommas(getAllInterestMoney));
		var totleMoney = data.totleMoney.toFixed(2);
		$("#totleMoney").html("￥"+addCommas(totleMoney));
		
		var allInterestAndRewardStr=data.allInterestAndRewardStr;
		$("#allInterestAndRewardStr").html("￥"+addCommas(allInterestAndRewardStr));
		var repossessed=data.repossessed;
		$("#repossessed").html("￥"+addCommas(repossessed));
		
	});
	
}
	
	/**
	*得到首页项目推荐
	*/
	function getBorrowRecommend(){
		$.ajax({
            type: "POST",
            url: $_path+"/getBorrowRecommend.do",
            async: false, //设为false就是同步请求
            cache: false,
            success: function(data, status) {
				/*var borrowHtml='';
				var transList='';*/
				var XSBlist='';
				/*var MXBlist='';*/
				var HDBlist='';
				/*var LYBlist='';
				var JYBlist='';
				var SEYBlist='';*/
				var LBlist ='';
				//新手标--新手专享
				if(data.XSBList.list.length>0){
					$.each(data.XSBList.list,function(i, borrow) {
						ignoreIds.push(borrow.id);//用于记录新手专享标，在后边展示 理财项目列表去除该标
						XSBlist += getXSBList(borrow);
					});
				}else{
					XSBlist += '<div class="main_data_div"><div class="biao_title">新手专享</div><img src="'+$_frontPath+'/images/d3.png" style="margin-top: -29px;"></div>';
				}
				
				/*if(data.MXBlist.list.length>0){
					$.each(data.MXBlist.list,function(i, borrow) {
						MXBlist += getMXBList(borrow);
					});
				}else{
					MXBlist += '<img src="'+$_frontPath+'/images/d3.png"/>';
				}*/
				//给力标--五星推荐
				if(data.LBlist.list.length>0){
					$.each(data.LBlist.list,function(i, borrow) {
						ignoreIds.push(borrow.id);//用于记录五星推荐标，在后边展示 理财项目列表去除该标
						LBlist += getLBList(borrow);
					});
				}else{
					LBlist += '<div class="main_data_div"><div class="biao_title">五星推荐</div><img src="'+$_frontPath+'/images/d3.png" style="margin-top: -29px;"></div>';
				}
				//活动标--定期理财
				if(data.HDBlist.list.length>0){
					$.each(data.HDBlist.list,function(i, borrow) {
						ignoreIds.push(borrow.id);//用于记录定期理财标，在后边展示 理财项目列表去除该标
						borrowTime = toDate(borrow.allow_tender_time);
						borrowStatus = borrow.borrow_status;
						HDBlist += getHDBList(borrow);
					});
				}else{
					HDBlist += '<div class="main_data_div"><div class="biao_title">定期理财</div><img src="'+$_frontPath+'/images/d3.png" style="margin-top: -29px;"></div>';
				} 
				/*$.each(data.LYBlist.list,function(i, borrow) {
					LYBlist += getLYBList(borrow);
				}); 
				$.each(data.JYBlist.list,function(i, borrow) {
					JYBlist += getJYBList(borrow);
				}); 
				$.each(data.SEYBlist.list,function(i, borrow) {
					SEYBlist += getSEYBList(borrow);
				});*/ 
				
				
				$("#xinshouList").html(XSBlist);
				/*$("#mingxingList").html(MXBlist);*/
				$("#huodongList").html(HDBlist);
				$("#geiliList").html(LBlist);
				
				/*if(LYBlist == null || LYBlist == ""){
					$("#liuyueList").html("<h3>30天</h3><img src='"+$_frontPath+"/images/d2.png'></span><div class='icon_hot_null'></div>");
				}else{
					$("#liuyueList").html(LYBlist);
				}
				if(JYBlist == null || JYBlist == ""){
					$("#jiuyueList").html("<h3>60天</h3><img src='"+$_frontPath+"/images/d2.png'></span><div class='icon_hot_null'></div>");
				}else{
					$("#jiuyueList").html(JYBlist);
				}
				if(SEYBlist == null || SEYBlist == ""){
					$("#shieryueList").html("<h3>90天</h3><img src='"+$_frontPath+"/images/d2.png'></span><div class='icon_hot_null'></div>");
				}else{
					$("#shieryueList").html(SEYBlist);
				}*/
			
				//当前时间
				borrowTime = borrowTime.replace(/-/g,"/");
				borrowTime = new Date(borrowTime).getTime();
				
				nowDate = data.nowDate;
				changeEndTime();
				setInterval(changeEndTime,1000);
				yanchi();
            }
		});
	}
	
	function getUserTenderData(){// 理财风云榜
		$.get(
				$_path+'/getUserTenderData.do',
				{
					'_d' : new Date().getTime()
				},
				function(data) {
					if (data.zong != null && data.zong.length > 0) {
						var zongStr = '<table><thead><th>排名</th><th>用户</th><th>投资金额</th></thead><tbody>';
						$.each(
							data.zong,
							function(i, user) {
								if(i < 5){
									if(i<=2){
										zongStr += '<tr><td><div class="top3 paiming">'+(i+1)+'</div></td><td>'+ user.lphone+ '***'+ user.rphone+'</td><td><span>￥</span>'+user.amount+'</td></tr>';
									}else{
										zongStr += '<tr><td><div class="top5 paiming">'+(i+1)+'</div></td><td>'+ user.lphone+ '***'+ user.rphone+'</td><td><span>￥</span>'+user.amount+'</td></tr>';
									}
								}
						});
						zongStr += '</div>';
						$("#zongList").html(zongStr);
					}

					if (data.yue != null && data.yue.length > 0) {
						var yueStr = '';
		                       $.each(
									data.yue,
									function(i, user) {
										if (i < 3) {
											yueStr += '<li><i class="J-red1">'
													+ (i + 1)
													+ '</i><span>'
													+ user.lphone
													+ '***'
													+ user.rphone
													+ '</span><p>￥'
													+ user.amount
													+ '</p></li>';
										} else {
											yueStr += '<li><i>'
													+ (i + 1)
													+ '</i><span>'
													+ user.lphone
													+ '***'
													+ user.rphone
													+ '</span><p>￥'
													+ user.amount
													+ '</p></li>';
										}
									});
								yueStr += '</div>';
								$("#yueList").html(yueStr);
					}

					if (data.zhou != null && data.zhou.length > 0) {
						var zhouStr = '';
		                       $.each(
									data.zhou,
									function(i, user) {
										if (i < 3) {
											zhouStr += '<li><i class="J-red1">'
													+ (i + 1)
													+ '</i><span>'
													+ user.lphone
													+ '***'
													+ user.rphone
													+ '</span><p>￥'
													+ user.amount
													+ '</p></li>';
										} else {
											zhouStr += '<li><i>'
													+ (i + 1)
													+ '</i><span>'
													+ user.lphone
													+ '***'
													+ user.rphone
													+ '</span><p>￥'
													+ user.amount
													+ '</p></li>';
										}

									});
								zhouStr += '</div>';
								$("#zhouList").html(zhouStr);
					};
				});
	}	
	function yanchi() {
		var animateDiv = $('.percent_small');
        //var animateDiv = $('.percent_small');
        animateEle();
        $(window).scroll(function () { animateEle() });
        function animateEle() {
            var playRange = { top: $(window).scrollTop(), bottom: $(window).scrollTop() + $(window).height() };
            animateDiv.each(function () {
                if (playRange.top <= $(this).offset().top && playRange.bottom >= $(this).offset().top && !$(this).data('bPlay')) {
                    $(this).data('bPlay', true);
                    var nPercent = $(this).parent().find('font').text().replace(/\%/, '');
                    //var nPercent = $(this).find('div').text().replace(/\%/, '');
                    $(this).svgCircle({
                        parent: $(this)[0],
                        w: 166,//插件整体宽度
                        R: 79,//圆的半径
                        sW: 7,//圆环的宽度
                        color: ["#F7772D", "#F7772D", "#F7772D"],
                        perent: [100, nPercent],
                        speed: 150,
                        delay: 400
                    });
                }
            });
        }
	}
	/**
	 * 债权转让列表
	 * @param data
	 * @returns {String}
	 */
	function getBorrowTransList(data){
		var htmlStr='';
		if(data.is_day==2){
			isDayStr=data.borrow_time_limit+"个月";
		}else if(data.is_day==1){
			isDayStr=data.borrow_time_limit+"天";
			isDayHtml='<img style="margin-left:5px;margin-top:-5px; " alt="天标" title="天标" src="'+$_frontPath+'/images/ny/tian.jpg" width="19" height="19" >';
		}
		var endTime = data.end_time;
		var nowDate=data.nowDate;
		var buttonHtml='';
		switch(data.transfer_status){
		case 1:
			if(nowDate.time-endTime.time<0){
				buttonHtml+='<a  href='+$_path+'/borrowTransfer/showBorrowTransInfo/'+data.id+'.do ><span class="hover"><div class=\"ljtb\" style=\"margin-right: 20px;\">竞拍</div></span></a>';
			}else{
				buttonHtml+='<a href="javascript:;" ><span><div class=\"ljtb\" style=\"margin-right: 20px;\">已结束</div></span></a>';
			}
			break;
		case 2:
			buttonHtml+='<a href="javascript:;" ><span><div class=\"ljtb\" style=\"margin-right: 20px;\">转让成功</div></span></a>';
			break;
		case 3:
			buttonHtml+='<a href="javascript:;" ><span><div class=\"ljtb\" style=\"margin-right: 20px;\">转让失败</div></span></a>';
			break;
		}
		
		
		var imgHtml='';
		var styles='';
		switch(data.bid_kind){
		case 1:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/dilogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="抵押标" title="抵押标" src="'+$_frontPath+'/images/ny/li.png" width="19" height="19" >';
			break;
		case 2:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/liulogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px; margin-top:-5px;" alt="流转标" title="流转标" src="'+$_frontPath+'/images/ny/liu.jpg" width="19" height="19" >';
			break;
		case 3:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/miaologo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="秒标" title="秒标" src="'+$_frontPath+'/images/ny/miao.jpg" width="19" height="19" >';
			break;
		case 4:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/jinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="净值标" title="净值标" src="'+$_frontPath+'/images/ny/jing.jpg" width="19" height="19" >';
			break;
		case 5:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/xinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="信用标" title="信用标" src="'+$_frontPath+'/images/ny/xin.jpg" width="19" height="19" >';
			break;
		}
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		var lastAuctionMoney;
		if(data.last_auction_money==undefined){
			lastAuctionMoney=data.transfer_money
		}else{
			lastAuctionMoney=data.last_auction_money;
		}
		endTime=toDate(endTime,"yyyy-MM-dd hh:mm:ss");
		
		
		htmlStr='<li style=\"border-top:none;\">'
				+'<div class=\"hkct-tz-list-01 fl\" '+styles+'>'
				+'<h3><a alt='+titlelength+' title='+titlelength+' href='+$_path+'/borrowTransfer/showBorrowTransInfo/'+data.id+'.do >'+title+'</a>'+imgHtml+'</h3>'
				+'<p>待收本金：<span class=\"span_01\">￥'+addCommas(data.repossessed_capital.toFixed(2))+'</span>元</p><p>转让金额：<span class=\"span_01\">￥'+addCommas(data.transfer_money.toFixed(2))+'</span>元</p>'
				+'</div>'
				+'<div class=\"hkct-tz-list-02 fl\"><p style=\"float:left;width:220px;\">最新竞拍价格：￥'+lastAuctionMoney+'</p>'
				+'<p style=\"clear:both;width:220px;\">结束时间：'+endTime+'</p></div>'
				+'<div class=\"hkct-tz-list-03 fl\"><p style=\"padding: 20px 0 0 50px;width:220px;\">年利率：<span class=\"span_01\">'+data.transfer_interest_rate.toFixed(2)+'%</span></p>'
				+'</div>'
				+'<div class=\"hkct-tz-list-05 fr\">'+buttonHtml
				+'</div>'
				+'</li>';
		return htmlStr;
	}
	
	/**
	 * 投资列表
	 * @param data
	 * @returns {String}
	 */
	function getBorrowHtml(data){
		var htmlStr='';
		var isDayStr="";
		var isDayHtml="";
		if(data.is_day==2){
			isDayStr=data.borrow_time_limit+"个月";
		}else if(data.is_day==1){
			isDayStr=data.borrow_time_limit+"天";
			isDayHtml='<img style="margin-left:5px;margin-top:-5px; " alt="天标" title="天标" src="'+$_frontPath+'/images/ny/tian.jpg" width="19" height="19" >';
		}
		
		var buttonHtml='';
		if(BORROW_ALL_STATUS[data.borrow_status]=='初审通过'){
			buttonHtml='<a  href='+$_path+'/invests/'+data.id+' ><span class="hover"><div class=\"ljtb\" style=\"margin-right: 20px;\">立即投标</div></span></a>';
		}
		else{
			buttonHtml='<a  href="javascript:;"><span ><div class=\"ljtb\" style=\"margin-right: 20px;\">'+BORROW_ALL_STATUS[data.borrow_status]+'</div></span></a>';
		}
		
		var imgHtml='';
		var styles='';
		switch(data.bid_kind){
		case 1:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/dilogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="抵押标" title="抵押标" src="'+$_frontPath+'/images/ny/li.png" width="19" height="19" >';
			break;
		case 2:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/liulogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px; margin-top:-5px;" alt="流转标" title="流转标" src="'+$_frontPath+'/images/ny/liu.jpg" width="19" height="19" >';
			break;
		case 3:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/miaologo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="秒标" title="秒标" src="'+$_frontPath+'/images/ny/miao.jpg" width="19" height="19" >';
			break;
		case 4:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/jinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="净值标" title="净值标" src="'+$_frontPath+'/images/ny/jing.jpg" width="19" height="19" >';
			break;
		case 5:
			styles='style=\"margin-left:15px; background:url('+$_frontPath+'/images/ny/xinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="信用标" title="信用标" src="'+$_frontPath+'/images/ny/xin.jpg" width="19" height="19" >';
			break;
		}
		var wardHtml='';
		var tenderAward="";
		if(data.tender_award!=1){
			if(data.tender_award==2){
				tenderAward=data.proportion_rate+"%";
			}else if(data.tender_award==3){
				tenderAward=data.part_amount+"元";
			}
			wardHtml='<img style="margin-left:5px;margin-top:-5px; " alt="奖" title="奖" src="'+$_frontPath+'/images/ny/jiang.jpg" width="19" height="19" >';
		}else{
			tenderAward="无";
		}
		
		var needMoney=data.borrow_sum-data.tender_sum;
		
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		var len=data.tenderProgressRate+"";
		var flag=(len+"").indexOf(".");
		if(flag!="-1"){
			var leng=len.subString(0,(len+"").indexOf(".")+3);
		}else{
			leng=len;
		}
		htmlStr='<li style=\"border-top:none;\">'
				+'<div class=\"hkct-tz-list-01 fl\" '+styles+' >'
				+'<h3><a alt='+titlelength+' title='+titlelength+' href='+$_path+'/invests/'+data.id+'>'+title+'</a>'+imgHtml+wardHtml+isDayHtml+'</h3>'
				+'<p>借款金额：<span class=\"span_01\">￥'+addCommas(data.borrow_sum.toFixed(2))+'</span>元</p><p>剩余金额：<span class=\"span_01\">￥'+addCommas(needMoney.toFixed(2))+'</span>元</p>'
				+'</div>'
				 +'<div class=\"hkct-tz-list-02 fl\"><p style=\"float:left;\">进度：<span class=\"span_01\"></span></p><div class=\"jdt-k\" style=\"margin-left:40px\">'
				+'<div class=\"jdt-hs\" style=\"width:'+leng+'%\">'+leng+'%</div></div>'+'<p style=\"clear:both;\">还款方式：'+BORROW_ALL_REPAYMENT_STYLE[data.repayment_style]+'</p>'
				+'<p>开标时间:'+toDate(data.publish_datetime)+'</p>'
				+'</div>'
				+'<div class=\"hkct-tz-list-03 fl\"><p>年利率：<span class=\"span_01\">'+data.annual_interest_rate.toFixed(2)+'%</span></p><p>奖励：<span class=\"span_01\">'+tenderAward
				+'</span></p></div>'
				+'<div class=\"hkct-tz-list-03 fl\"><p>用途：'+BORROW_ALL_BORROW_USE[data.borrow_use]+'<p>借款期限：'+isDayStr
				+'</p></div>'
				+'<div class=\"hkct-tz-list-05 fr\">'+buttonHtml
				+'</li>';
		return htmlStr;
		
	}
	
	/**
	 * 新手标列表
	 * @param data
	 * @returns {String}
	 */
	function getXSBList(data){
		
		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<div class="touziDiv touzi"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">我要投资</div></a></div>';
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">已经结束</div></a></div>';
				} else {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">待开始</div></a></div>';
				}
			}
		} else {
			buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">'
					+ BORROW_ALL_STATUS[data.borrow_status] + '</div></a></div>';
		}

		var isDayStr = "";
		if (data.is_day == 2) {
			isDayStr = "个月";
		} else if (data.is_day == 1) {
			isDayStr = "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
		var rate= data.annual_interest_rate.toFixed(2);
		var bignum = Math.round(rate);
		var samllnum = rate.substring(rate.indexOf("."),4);
		var percent= data.tenderProgressRate.toFixed(2)+"%";
		var fullHtml = '';
		if(percent == '100.00%'){
			fullHtml = '<div class="full"><img src = "'+$_frontPath+'/images/full.png"/></div>';
		}
		//限投金额限制
		var maxAmount = '不限投';
		if(data.max_amount =='2000'){
			maxAmount = '限投两千';
		}else if(data.max_amount =='5000'){
			maxAmount = '限投五千';
		}else if(data.max_amount =='10000'){
			maxAmount = '限投一万';
		}else if(data.max_amount =='15000'){
			maxAmount = '限投一万五';
		}else if(data.max_amount =='20000'){
			maxAmount = '限投两万';
		}else if(data.max_amount =='50000'){
			maxAmount = '限投五万';
		}else if(data.max_amount =='100000'){
			maxAmount = '限投十万';
		}
		
		var reypaymentStr = '';
		if(data.repayment_style == '1'){
			reypaymentStr = '到期还本付息';
		}else if(data.repayment_style == '2'){
			reypaymentStr = '按月分期';
		}else if(data.repayment_style == '3'){
			reypaymentStr = '先息后本';
		}
		/*htmlStr='<li style=\"border-top:none;\">'
				+'<div class=\"hkct-tz-list-01 fl\" style=\"margin-left:15px;\">'
				+'<h3><a alt='+titlelength+' title='+titlelength+' href='+$_path+'/borrow/toDingHuoTongInfo/'+id+'/'+relatedId+'.do>'+title+'</a><img alt="定活通" src="'+$_frontPath+'/images/ny/ding.jpg" width="19" height="19" style="margin-top:-5px;"></h3>'
				+'<p>借款金额：<span class=\"span_01\">￥'+addCommas(data.borrow_sum.toFixed(2))+'</span>元</p><p>剩余金额：<span class=\"span_01\">￥'+addCommas((data.borrow_sum - data.tender_sum).toFixed(2))+'</span>元</p>'
				+'</div>'
				+'<div class=\"hkct-tz-list-02 fl\"><p style=\"float:left;\">进度：<span class=\"span_01\"></span></p><div class=\"jdt-k\" style=\"margin-left:40px\">'
				+'<div class=\"jdt-hs\" style=\"width:'+data.tenderProgressRate.toFixed(2)+'%\">'+data.tenderProgressRate.toFixed(2)+'%</div></div>'+'<p></p><p style=\"clear:both;\">还款方式：每月还息到期还本</p>'
				+'</div>' 
				 +'<div class=\"hkct-tz-list-03 fl\"><p>年利率：'+data.annual_interest_rate.toFixed(2)+'%</p><p>奖励：无'
				+'</p></div>'
				+'<div class=\"hkct-tz-list-03 fl\"><p>起投额度：￥'+data.min_amount+'起<p>借款期限：'+isDayStr
				+'</p></div>'
				+'<div class=\"hkct-tz-list-05 fr\">'+buttonHtml
				+'</li>';*/
		/* htmlStr = '<li class="per"><p>预期年化</p><span>'
	        	+data.annual_interest_rate.toFixed(2)+'<i class="J-precent">%</i></span><div class="begin">'
	        	+data.min_amount+'元起投</div></li><li class="per2"><div class="percent"><i>进度</i><div class="percent_in"><span style="width:'+leng+'%"><em>'
	        	+leng+'%</em></span></div></div><div class="day"><i>期限</i><span><em>'
	        	+isDayStr+'</em></span></div><div class="day"><i>总额</i><span>￥<em>'
	        	+addCommas(data.borrow_sum.toFixed(2))+'</em></span></div>'
	        	+buttonHtml
	        	+'</li>'
	        	*/
		
		/*htmlStr = '<div class="xinshou_left"><img src="'+$_frontPath+'/images/tb1.png"><div class="xinshou_left_div">'+
			'<img src="'+$_frontPath+'/images/pic1.png"><div>新手标</div></div></div><div class="xinshou_right"><span>预期年化利率</span>'+								
			'<div class="xinshou_data"><div>'+data.annual_interest_rate.toFixed(2)+'%</div><div>'+data.borrow_time_limit+isDayStr+'</div></div>'+
			'<div class="xinshou_limit"><div>'+data.min_amount+'元起投</div><div>'+maxAmount+'</div></div>'+
			'<div class="process_div"><div class="process"><div class="process_bar progress-bar-striped" style="width:'+percent+'"></div></div><div class="process_data">'+percent+'</div></div>'+
			'<div class="xinshou_btn">'+buttonHtml+'</div>';*/
		htmlStr = buttonHtml+'<div class="main_data_div">'+fullHtml+'<div class="biao_title xszx"></div>'+
				  '<div class="biao_body"><div><span>'+data.min_amount+'元起投</span><span>'+maxAmount+'</span><span>'+reypaymentStr+'</span></div>'+
				  '<div><span>预期年化利率:&nbsp;<i>'+data.annual_interest_rate.toFixed(2)+'%</i></span><span>'+data.borrow_time_limit+isDayStr+'</span></div>'+
				  '<div><span class="process"><span class="process_bar" style="width:'+percent+'"></span></span><span class="baifenbi">'+percent+'</span></div></div></div>';
		
		return htmlStr;
	}
	
	/**
	 * 给力标
	 * @param data
	 * @returns
	 */
	function getLBList(data){
		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<div class="touziDiv touzi"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">我要投资</div></a></div>';
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">已经结束</div></a></div>';
				} else {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">待开始</div></a></div>';
				}
			}
		} else {
			buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">'
					+ BORROW_ALL_STATUS[data.borrow_status] + '</div></a></div>';
		}

		var isDayStr = "";
		if (data.is_day == 2) {
			isDayStr = "个月";
		} else if (data.is_day == 1) {
			isDayStr = "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
		var rate= data.annual_interest_rate.toFixed(2);
		var bignum = Math.round(rate);
		var samllnum = rate.substring(rate.indexOf("."),4);
		var percent= data.tenderProgressRate.toFixed(2)+"%";
		var fullHtml = '';
		if(percent == '100.00%'){
			fullHtml = '<div class="full"><img src = "'+$_frontPath+'/images/full.png"/></div>';
		}
		//限投金额限制
		var maxAmount = '不限投';
		if(data.max_amount =='2000'){
			maxAmount = '限投两千';
		}else if(data.max_amount =='5000'){
			maxAmount = '限投五千';
		}else if(data.max_amount =='10000'){
			maxAmount = '限投一万';
		}else if(data.max_amount =='15000'){
			maxAmount = '限投一万五';
		}else if(data.max_amount =='20000'){
			maxAmount = '限投两万';
		}else if(data.max_amount =='50000'){
			maxAmount = '限投五万';
		}else if(data.max_amount =='100000'){
			maxAmount = '限投十万';
		}
		
		var reypaymentStr = '';
		if(data.repayment_style == '1'){
			reypaymentStr = '到期还本付息';
		}else if(data.repayment_style == '2'){
			reypaymentStr = '按月分期';
		}else if(data.repayment_style == '3'){
			reypaymentStr = '先息后本';
		}
		htmlStr = buttonHtml+'<div class="main_data_div">'+fullHtml+'<div class="biao_title wxtj"></div>'+
				  '<div class="biao_body"><div><span>'+data.min_amount+'元起投</span><span>'+maxAmount+'</span><span>'+reypaymentStr+'</span></div>'+
				  '<div><span>预期年化利率:&nbsp;<i>'+data.annual_interest_rate.toFixed(2)+'%</i></span><span>'+data.borrow_time_limit+isDayStr+'</span></div>'+
				  '<div><span class="process"><span class="process_bar" style="width:'+percent+'"></span></span><span class="baifenbi">'+percent+'</span></div></div></div>';
		
		return htmlStr;
	}
	
	/**
	 * 明星标列表
	 * @param data
	 * @returns {String}
	 */
	function getMXBList(data){

		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<a class="bg_light_blue font_white" href='+$_path+'/invests/'+data.id+'>立即投资</a>';
			
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+'>已经结束</a>';
				} else {
					buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+'>待开始</a>';
				}
			}
		} else {
			buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+'>'
					+ BORROW_ALL_STATUS[data.borrow_status] + '</a>';
		}

		var isDayStr = "";
		//类别
		if (data.is_day == 2) {
			isDayStr = "个月";
		} else if (data.is_day == 1) {
			isDayStr = "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
		var rate= data.annual_interest_rate.toFixed(2);
		var bignum = Math.round(rate);
		var samllnum = rate.substring(rate.indexOf("."),4);
		var percent= data.tenderProgressRate.toFixed(2)+"%";
		//限投金额限制
		var maxAmount = '不限投';
		if(data.max_amount =='2000'){
			maxAmount = '限投两千';
		}else if(data.max_amount =='5000'){
			maxAmount = '限投五千';
		}else if(data.max_amount =='10000'){
			maxAmount = '限投一万';
		}else if(data.max_amount =='15000'){
			maxAmount = '限投一万五';
		}else if(data.max_amount =='20000'){
			maxAmount = '限投两万';
		}else if(data.max_amount =='50000'){
			maxAmount = '限投五万';
		}else if(data.max_amount =='100000'){
			maxAmount = '限投十万';
		}
		/*
	  htmlStr = '<li class="per"><p>预期年化</p><span>'
	        	+data.annual_interest_rate.toFixed(2)+'<i class="J-precent">%</i></span><div class="begin">'
	        	+data.min_amount+'元起投</div></li><li class="per2"><div class="percent"><i>进度</i><div class="percent_in"><span style="width:'+leng+'%"><em>'
	        	+leng+'%</em></span></div></div><div class="day"><i>期限</i><span><em>'
	        	+isDayStr+'</em></span></div><div class="day"><i>总额</i><span>￥<em>'
	        	+addCommas(data.borrow_sum.toFixed(2))+'</em></span></div>'
	        	+buttonHtml
	        	+'</li>'
	        	*/
		/*htmlStr= '<div class="pb_left">'+
					'<div class="pb_return_date">'+
						'<font class="pb_font_blue pb_font_big">浩茗金融</font>'+
						'<font class="pb_font_blue pb_font_mid"></font>'+
						'<div style="width:100%;height:35%;">'+
							'<div style="float:left">'+
								'<img src="'+$_frontPath+'/images/logo.png" style="width:88px" title="浩茗金融">'+
							'</div>'+
							'<div class="pb_hkqx">'+
								'<div class="pb_hkqxfont">投资期限</div>'+
								'<div>'+
									'<font class="pb_font_blue pb_font_big">'+data.borrow_time_limit+'</font>'+
									'<font class="pb_font_blue pb_font_mid">'+isDayStr+'</font>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div>'+buttonHtml+		
						'</div>'+
					'</div>'+
					'<div class="pb_rate">'+
						'<div class="pb_bignumblock">'+
							'<font class="pb_font_blue" style="font-size:150px;float:right;">'+bignum+'</font>'+
						'</div>'+
						'<div class="pb_smallnumblock">'+
							'<font style="font-size:large">年化收益</font></br>'+	
							'<font class="pb_font_big pb_font_blue">'+samllnum+'%</font>'+	
						'</div>'+
					'</div>'+
				'</div>';*/
		htmlStr = '<div class="hot_left"><img src="'+$_frontPath+'/images/tb2.png"><div class="hot_left_div">'+
			'<img src="'+$_frontPath+'/images/pic2.png"><div>热门标</div></div></div><div class="hot_right"><span>预期年化利率</span>'+								
			'<div class="hot_data"><div>'+data.annual_interest_rate.toFixed(2)+'%</div><div>'+data.borrow_time_limit+isDayStr+'</div></div>'+
			'<div class="hot_limit"><div>'+data.min_amount+'元起投</div><div>'+maxAmount+'</div></div>'+
			'<div class="process_div"><div class="process"><div class="process_bar progress-bar-striped" style="width:'+percent+'"></div></div><div class="process_data">'+percent+'</div></div>'+
			'<div class="hot_btn">'+buttonHtml+'</div>';
		
		return htmlStr;
	}
	
	/**
	 * 活动标列表
	 * @param data
	 * @returns {String}
	 */
	function getHDBList(data){

		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<div class="touziDiv touzi"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">我要投资</div></a></div>';
			
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">已经结束</div></a></div>';
				} else {
					buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">待开始</div></a></div>';
				}
			}
		} else {
			buttonHtml = '<div class="touziDiv HkzDksYjs"><a href='+$_path+'/invests/'+data.id+' target="_blank"><div class="touziSpan">'
					+ BORROW_ALL_STATUS[data.borrow_status] + '</div></a></div>';
		}

		var isDayStr = "";
		//类别
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		//可允许投标时间
		var lastTime = toDate(data.allow_tender_time);
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
		var percent= data.tenderProgressRate.toFixed(2)+"%";
		var fullHtml = '';
		if(percent == '100.00%'){
			fullHtml = '<div class="full"><img src = "'+$_frontPath+'/images/full.png"/></div>';
		}
		//限投金额限制
		var maxAmount = '不限投';
		if(data.max_amount =='2000'){
			maxAmount = '限投两千';
		}else if(data.max_amount =='5000'){
			maxAmount = '限投五千';
		}else if(data.max_amount =='10000'){
			maxAmount = '限投一万';
		}else if(data.max_amount =='15000'){
			maxAmount = '限投一万五';
		}else if(data.max_amount =='20000'){
			maxAmount = '限投两万';
		}else if(data.max_amount =='50000'){
			maxAmount = '限投五万';
		}else if(data.max_amount =='100000'){
			maxAmount = '限投十万';
		}
		var reypaymentStr = '';
		if(data.repayment_style == '1'){
			reypaymentStr = '到期还本付息';
		}else if(data.repayment_style == '2'){
			reypaymentStr = '按月分期';
		}else if(data.repayment_style == '3'){
			reypaymentStr = '先息后本';
		}
	 /*htmlStr = '<ul class="list"><li class="list1">预期年化<span><i>'
		 	+data.annual_interest_rate.toFixed(2)+'</i>%</span></li><li class="list2">期限 &nbsp;<span>'
		 	+isDayStr+'<em></em></span></li></ul><div class="txt"><ul><li>起购金额：<span>￥'
		 	+data.min_amount+'</span></li><li><i id="endTimeName">剩余时间：</i><span><i id="endTime">'
		 	+lastTime+'</i></span></li></ul>'
		 	+buttonHtml+'</div>';*/
		htmlStr = buttonHtml+'<div class="main_data_div">'+fullHtml+'<div class="biao_title dqlc"></div>'+
		  '<div class="biao_body"><div><span>'+data.min_amount+'元起投</span><span>'+maxAmount+'</span><span>'+reypaymentStr+'</span></div>'+
		  '<div><span>预期年化利率:&nbsp;<i>'+data.annual_interest_rate.toFixed(2)+'%</i></span><span>'+isDayStr+'</span></div>'+
		  '<div><span class="process"><span class="process_bar" style="width:'+percent+'"></span></span><span class="baifenbi">'+percent+'</span></div></div></div>';
	
	return htmlStr;
	 
	}
	
	/**
	 * 六月标列表
	 * @param data
	 * @returns {String}
	 */
	function getLYBList(data){

		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				//buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">马上投资</a>'
				buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="bg_orange font_white">我要投资</a>';
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">已经结束</a>';
				} else {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">待开始</a>';
				}
			}
		} else {
			//buttonHtml = '<a href='+$_path+'/invests/'+data.eid+' class="met_btn">'
				//	+ BORROW_ALL_STATUS[data.borrow_status] + '</a>';
			buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+' >'+ BORROW_ALL_STATUS[data.borrow_status] +'</a>';
		}

		var isDayStr = "";
		//类别
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>12){
			title = titlelength.substring(0, 12)+"..."; 
		}else{
			title=titlelength;
		}
		//金额,万元单位
		var borrowSum = data.borrow_sum.toFixed(2) / 10000;
		//还款方式
		var borrowstyle = BORROW_ALL_REPAYMENT_STYLE[data.repayment_style];
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
		
		htmlStr = '<h3><img src="'+ $_frontPath+'/images/point.png" class="pointImg">'+title+'</h3><span class="circle"><div class="percent_small"><p>预期<br>年化利率</p><em>'
			+data.annual_interest_rate.toFixed(2)+'<i>%</i></em><font style="display: none;"><i>'
			+leng+'</i></font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+'<div class="project_content"><table><tr><td>投资期限</td><td><span class="font_blue project_blue">'
			+isDayStr
			+'</span></td></tr><tr><td>项目总额</td><td><span class="font_blue project_blue">'
			+borrowSum+'</span>万</td></tr><tr><td>起投金额</td><td><span class="font_blue project_blue">'
			+data.min_amount+'</span>元</td></tr><tr><td>还款方式</td><td><span class="font_blue project_blue">'
			+borrowstyle+'</span></td></tr></table>'
			+buttonHtml+'</div>';
			
	return htmlStr;
	 
	}	
	
	/**
	 * 九月标列表
	 * @param data
	 * @returns {String}
	 */
	function getJYBList(data){

		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="bg_orange font_white">我要投资</a>'
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">已经结束</a>'
				} else {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">待开始</a>'
				}
			}
		} else {
			buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+' >'+ BORROW_ALL_STATUS[data.borrow_status] +'</a>';
		}

		var isDayStr = "";
		//类别
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>12){
			title = titlelength.substring(0, 12)+"..."; 
		}else{
			title=titlelength;
		}
		//金额,万元单位
		var borrowSum = data.borrow_sum.toFixed(2) / 10000;
		//还款方式
		var borrowstyle = BORROW_ALL_REPAYMENT_STYLE[data.repayment_style];
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
	
		/*htmlStr = '<h5><i></i><span>9个月</span></h5><span class="circle"><div class="percent_small"><p>预期年化</p><em>'
			+data.annual_interest_rate.toFixed(2)+'<i>%</i></em><font><i>'
			+leng+'</i></font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+'<div class="txt"><p><i>项目总额</i><span><em>'
			+borrowSum+'</em>&nbsp;&nbsp;万</span></p><p><i>起投金额</i><span><em>'
			+data.min_amount+'</em>&nbsp;&nbsp;元</span></p><p style="border-bottom:1px solid #e5e5e5;;"><i>还款方式</i><span class="txt1">'
			+borrowstyle+'</span></p></div>'
			+buttonHtml;*/
		htmlStr = '<h3><img src="'+ $_frontPath+'/images/point.png" class="pointImg">'+title+'</h3><span class="circle"><div class="percent_small"><p>预期<br>年化利率</p><em>'
			+data.annual_interest_rate.toFixed(2)+'<i>%</i></em><font style="display: none;"><i>'
			+leng+'</i></font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+'<div class="project_content"><table><tr><td>投资期限</td><td><span class="font_blue project_blue">'
			+isDayStr
			+'</span></td></tr><tr><td>项目总额</td><td><span class="font_blue project_blue">'
			+borrowSum+'</span>万</td></tr><tr><td>起投金额</td><td><span class="font_blue project_blue">'
			+data.min_amount+'</span>元</td></tr><tr><td>还款方式</td><td><span class="font_blue project_blue">'
			+borrowstyle+'</span></td></tr></table>'
			+buttonHtml+'</div></span><div class="icon_hot"></div>';
			
	return htmlStr;
	 
	}	
	
	/**
	 * 十二月标列表
	 * @param data
	 * @returns {String}
	 */
	function getSEYBList(data){

		var htmlStr='';
		//标名
		var titlelength=data.borrow_title;
		var title="";
		if(titlelength.length>6){
			title = titlelength.substring(0, 6)+"..."; 
		}else{
			title=titlelength;
		}
		
		var relatedId =data.erelatedId; 
		var id = data.eid ;
		var nowTime = toDate(data.nowTime);
		var allow_tender_time = toDate(data.allow_tender_time);
		var publish_datetime = toDate(data.publish_datetime);
		
		var buttonHtml = '';
		//2：初审通过
		if (data.borrow_status == 2) {
			if (isStartEndDate(nowTime, allow_tender_time)
					&& isStartEndDate(publish_datetime, nowTime)) {
				buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="bg_orange font_white">我要投资</a>'
			} else {
				if (!isStartEndDate(nowTime, allow_tender_time)) {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">已经结束</a>'
				} else {
					buttonHtml = '<a href='+$_path+'/invests/'+data.id+' class="met_btn">待开始</a>'
				}
			}
		} else {
			buttonHtml = '<a class="bg_orange font_white" href='+$_path+'/invests/'+data.id+' >'+ BORROW_ALL_STATUS[data.borrow_status] +'</a>';
		}

		var isDayStr = "";
		//类别
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
		}
		var titlelength=data.borrow_title;
		//题目
		var title="";
		if(titlelength.length>12){
			title = titlelength.substring(0, 12)+"..."; 
		}else{
			title=titlelength;
		}
		//金额,万元单位
		var borrowSum = data.borrow_sum.toFixed(2) / 10000;
		//还款方式
		var borrowstyle = BORROW_ALL_REPAYMENT_STYLE[data.repayment_style];
		
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
			if (leng.length == 1) {
				leng = leng + ".00";
			}
			if (leng.length == 2) {
				leng = leng + ".0";
			}
		}
	
		/*htmlStr = '<h5><i></i><span>12个月</span></h5><span class="circle"><div class="percent_small"><p>预期年化</p><em>'
			+data.annual_interest_rate.toFixed(2)+'<i>%</i></em><font><i>'
			+leng+'</i></font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+'<div class="txt"><p><i>项目总额</i><span><em>'
			+borrowSum+'</em>&nbsp;&nbsp;万</span></p><p><i>起投金额</i><span><em>'
			+data.min_amount+'</em>&nbsp;&nbsp;元</span></p><p style="border-bottom:1px solid #e5e5e5;;"><i>还款方式</i><span class="txt1">'
			+borrowstyle+'</span></p></div>'
			+buttonHtml;*/
		htmlStr = '<h3><img src="'+ $_frontPath+'/images/point.png" class="pointImg">'+title+'</h3><span class="circle"><div class="percent_small"><p>预期<br>年化利率</p><em>'
			+data.annual_interest_rate.toFixed(2)+'<i>%</i></em><font style="display: none;"><i>'
			+leng+'</i></font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+'<div class="project_content"><table><tr><td>投资期限</td><td><span class="font_blue project_blue">'
			+isDayStr
			+'</span></td></tr><tr><td>项目总额</td><td><span class="font_blue project_blue">'
			+borrowSum+'</span>万</td></tr><tr><td>起投金额</td><td><span class="font_blue project_blue">'
			+data.min_amount+'</span>元</td></tr><tr><td>还款方式</td><td><span class="font_blue project_blue">'
			+borrowstyle+'</span></td></tr></table>'
			+buttonHtml+'</div><div class="icon_hot"></div>';
			
	return htmlStr;
	 
	}
	
	/**
	 * 投资列表轮换
	 */
	function changeTab(obj, index) {
		$(obj).parent().find(".red").removeClass("red");
		$(obj).addClass("red");
		if (index == 1) {
			 $("#hkct-tz-list").show();
			$(".transfer").hide();
			$(".dht").hide();
			$("#qiehuan").attr("href",$_path+"/invest"); 
		} else if(index==2){
			$("#hkct-tz-list").hide();
			$(".transfer").show();
			$(".dht").hide();
			$("#qiehuan").attr("href",$_path+"/creditassignment"); 
		}else{
			$("#hkct-tz-list").hide();
			$(".transfer").hide();
			$(".dht").show();
			$("#qiehuan").attr("href",$_path+"/borrow/dingHuoTong.do"); 
		}
	}
	/*function changeTab1(obj) {
		$(obj).parent().find(".red").removeClass("red");
	}*/
	/**
	 * 广告图片
	 */
	function getContentListPic() {
		$.get($_path+"/getContentListPic.do",
						function(data, status) {
							var html = '';
							$.each(data.contentListPic,
											function(i, pic) {
												var urlStr = '';
												var target = '';
												if (pic.external_link_title) {
													urlStr = pic.external_link_title ;
													target = ' target="_blank" ';
												} else if (pic.content_txt) {
													urlStr = $_path+'/content/contentDetails/'
															+ data.picChannelId
															+ '/'
															+ pic.id
															+ '.do';
													target = ' target="_blank" ';
												} else {
													urlStr = 'javascript:void(0)';
													target = '';
												}
												html+='<div class="ban_c album"><a href='+urlStr+''+target+' ><img src='+$_path+ pic.attach_path+' /></a></div>';
							});
							$("#sliders").html(html);
							sliderDO();
						});
		
		
	}

	
	

	function changeEndTime(){	
		//先暂定为：为发布
		if(Published){
			if(borrowStatus == '2'){
				$("#endTimeName").html("剩余时间：");
				$("#endTime").html(getTimeStr());
			}else{
				$("#endTimeName").html("已结束");
				$("#endTime").html('0天'+'0时'+'0分'+'0秒');
				//按钮不可点
			}
		}else{
			//按钮不可点
			$("#endTimeName").html("等待开始：");
			$("#endTime").html('0天'+'0时'+'0分'+'0秒');
		}
	}
	
	//设置时间的改变
	function getTimeStr(){
		var a,b,c,d,cy=cd=ch=cm=cs=0;
		d = borrowTime;
		nowDate = parseInt(nowDate);
		nowDate = nowDate+1000;
		b = nowDate;
		d = parseInt(borrowTime);
		time_distance = Math.floor((d-b));
		if(time_distance < 0){
			//已结束
			Published = false;
		}
		int_day = Math.floor(time_distance/86400000); // 1000*60*60*24
		int_hour = Math.floor((time_distance-int_day*86400000)/3600000);
		int_minute = Math.floor((time_distance-int_hour*3600000-int_day*86400000)/60000);
		int_second = Math.floor((time_distance-int_minute*60000-int_hour*3600000-int_day*86400000)/1000);
		
		if(int_day<0){
			int_day="0";
			int_hour="0";
			int_minute="0";
			int_second="0";
		}
	  	if(int_hour<10)
		int_hour="0"+int_hour;
		if(int_minute<10)
		int_minute="0"+int_minute;
		if(int_second<10)
		int_second="0"+int_second;

		if(int_day <= 0 && int_hour <= 0 && int_minute<=0 && int_second <= 0){
			dealTimeZero();
		}
		
	 	return int_day+'天'+int_hour+'时'+int_minute+'分'+int_second+'秒';
	}
	    
	
	function dealTimeZero(){
		window.clearInterval(intereValId);
		if(Published){
			$("#endTimeName").html("已结束:");
			$("#endTime").html('0天'+'0时'+'0分'+'0秒');
		}else{
			$("#endTimeName").html("等待开始：");
			$("#endTime").html('0天'+'0时'+'0分'+'0秒');
		}
	}
	
	function getBottomItem(url,callBack,tableId,curPage){
		if(url != undefined && url != null){
			urlObject = url;
		}
		if(callBack != undefined && callBack != null){
			callBackObject = callBack;
		}
		if(tableId != undefined && tableId != null){
			tableIdObject = tableId;
		}
		var rateArray = new Array();
		var dateArray = new Array();
		var statuArray = new Array();
		var borrowtypeArray = new Array();
		var securitymodeArray = new Array();
		var paramsObject = {};
		rateArray.length = 0;
		dateArray.length = 0;
		statuArray.length = 0;
		securitymodeArray.length = 0;
		dateArray.push("all");
		rateArray.push("all");
		statuArray.push("all");
		securitymodeArray.push("all");
		paramsObject.rateArrays = rateArray.unique2().toString();
		paramsObject.dateArrays = dateArray.unique2().toString();
		paramsObject.statuArrays = statuArray.unique2().toString();
		paramsObject.borrowtypeArrays = borrowtypeArray.unique2().toString();
		paramsObject.securitymodeArrays = securitymodeArray.unique2().toString();
		paramsObject.pageNum = 1;
		
		$.ajax({
            dataType: 'json',
            url:urlObject,
            data:paramsObject,
            type: 'POST',
            beforeSend: function(){
                initInvestRecordsTable();
                $("#"+tableId).append(Util.callType.loading("加载中..."));
            },
            success: function(data) {
            	initInvestRecordsTable();
            	if(data!=null){
            		listData = data.list;
            		dataHtml = '';
            		if(listData.length > 0){
            			//去除上面的项目
            			var newListData = [];
            			for(var i=0;i<listData.length;i++){
            				var ignoreFlg = false;
            				for (var j = 0; j < ignoreIds.length; j++) {
            					if (ignoreIds[j] == listData[i].id) {
            						ignoreFlg = true;
            						break;
            					}
            				}
            				
            				if (!ignoreFlg) {
            					newListData[newListData.length] = listData[i];
            				}
            			}
            			
            			for(var i=0;i<newListData.length && i<4;i++){
	            			dataHtml += callBackObject(newListData[i]);
	            		}
            			if(dataHtml!=""){
	            			$("#"+tableIdObject).html(dataHtml);
            			}
            		}else{
            			 $("#"+tableId).append(Util.callType.loading("暂无数据"));
            			 $(".r5").removeClass("r5");
            			 $(".load32").removeClass("load32");
            		}
            	}
            },
            error: function() {
            	 initInvestRecordsTable();
            	 $("#"+tableId).append(Util.callType.loading("加载错误"));
            }
        });      
	}

	function getBottomItemInfo(data){
		var htmlStr = '';
		var isDayStr = "";
		var isDayHtml = "";
		if (data.is_day == 2) {
			isDayStr = data.borrow_time_limit + "个月";
		} else if (data.is_day == 1) {
			isDayStr = data.borrow_time_limit + "天";
			isDayHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="天标" title="天标" src="${frontPath}/images/ny/tian.jpg" width="19" height="19" >';
		}

		var buttonHtml = '';
		if (BORROW_ALL_STATUS[data.borrow_status] == '初审通过') {
			var publicsh = data.publish_datetime.time ;
			var nowDate = new Date().getTime() ;
			if(publicsh > nowDate ){
				buttonHtml = '<a  href="javascript:;" class="invest_btn"><div class="project_main_button HkzDksYjs"><div class="project_main_button_left">预告中</div></div></a>';
			}else{
				buttonHtml = '<a  href="'+$_path+'/invests/'+data.id+'" class="invest_btn" target="_blank"><div class="project_main_button touzi"><div class="project_main_button_left">我要投资</div></div></a>';
			}
			
		} else {
			buttonHtml = '<a  href="javascript:;" class="invest_btn"><div class="project_main_button HkzDksYjs"><div class="project_main_button_left">' + BORROW_ALL_STATUS[data.borrow_status] + '</div></div></a>';
		}

		var imgHtml = '';
		var styles = '';
		switch (data.bid_kind) {
		case 1:
			styles = 'style=\"margin-left:15px; background:url(' + $_frontPath
					+ '/images/ny/dilogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="抵押标" title="抵押标" src="'+$_frontPath+'/images/ny/li.png" width="19" height="19" >';
			break;
		case 2:
			styles = 'style=\"margin-left:15px; background:url(' + $_frontPath
					+ '/images/ny/liulogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px; margin-top:-5px;" alt="流转标" title="流转标" src="'+$_frontPath+'/images/ny/liu.jpg" width="19" height="19" >';
			break;
		case 3:
			styles = 'style=\"margin-left:15px; background:url(' + $_frontPath
					+ '/images/ny/miaologo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="秒标" title="秒标" src="'+$_frontPath+'/images/ny/miao.jpg" width="19" height="19" >';
			break;
		case 4:
			styles = 'style=\"margin-left:15px; background:url(' + $_frontPath
					+ '/images/ny/jinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="净值标" title="净值标" src="'+$_frontPath+'/images/ny/jing.jpg" width="19" height="19" >';
			break;
		case 5:
			styles = 'style=\"margin-left:15px; background:url(' + $_frontPath
					+ '/images/ny/xinglogo.png) no-repeat 0 center;\"';
			imgHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="信用标" title="信用标" src="'+$_frontPath+'/images/ny/xin.jpg" width="19" height="19" >';
			break;
		}
		var wardHtml = '';
		var tenderAward = "";
		if (data.tender_award != 1) {
			if (data.tender_award == 2) {
				tenderAward = data.proportion_rate + "%";
			} else if (data.tender_award == 3) {
				tenderAward = data.part_amount + "元";
			}
			wardHtml = '<img style="margin-left:5px;margin-top:-5px; " alt="奖" title="奖" src="${frontPath}/images/ny/jiang.jpg" width="19" height="19" >';
		} else {
			tenderAward = "无";
		}

		var needMoney = data.borrow_sum - data.tender_sum;

		var titlelength = data.borrow_title;
		var title = "";
		if (titlelength.length > 6) {
			title = titlelength.substring(0, 6) + "...";
		} else {
			title = titlelength;
		}
		var len = data.tenderProgressRate + "";
		var flag = (len + "").indexOf(".");
		if (flag != "-1") {
			var leng = len.subString(0, (len + "").indexOf(".") + 3);
		} else {
			leng = len;
		}
		var fullHtml = '';
		if(data.tender_sum == data.borrow_sum){
			fullHtml = '<img src="'+$_frontPath+'/images/full.png" style="margin-left: 28px;width: 68px;margin-top: -7px;"/>';
		}else{
			fullHtml = '<em style="padding-top: 16px;">项目总额</em><span class="special">'+ fmoney(data.borrow_sum,3) + '<font>元</font></span>';
		}
		var percent= (data.tender_sum/data.borrow_sum*100).toFixed(2)+"%";
		var creditStr ='';
		if(data.credit_type == '1'){
			creditStr ='信用';
		}else if(data.credit_type == '2'){
			creditStr ='质押';
		}else if(data.credit_type == '3'){
			creditStr ='抵押';
		}else if(data.credit_type == '4'){
			creditStr ='担保';
		}
		var bidRankStr = 'A';
		switch (data.bid_rank) {
		case 1:
			bidRankStr = 'A';
			break;
		case 2:
			bidRankStr = 'A+';
			break;
		case 3:
			bidRankStr = 'AA';
			break;
		case 4:
			bidRankStr = 'AA+';
			break;
		case 5:
			bidRankStr = 'AAA';
			break;
		case 6:
			bidRankStr = 'AAA+';
			break;
		}
		htmlStr = '<div class="project_body"><div class="project_main"><div class="project_main_left"><h4>'
				+ '<a alt='+data.borrow_title+' title='+data.borrow_title+' href="'+$_path+'/invests/'+data.id+'" target="_blank">'
				+ data.borrow_title
				+ '<img src="'+$_frontPath+'/images/zst.png"/></a><span class="hezuojigou">'+bidRankStr+'</span><span class="difengxian">&nbsp;'+creditStr+'&nbsp;</span></h4>'
				+ '<div class="showProject"><p class="blue yqnh"><em style="padding-top: 0px;">预期年化利率</em><span><i class="nianhualilv">'+ data.annual_interest_rate.toFixed(2)+'</i>%</span></p><p class="blue"><em>投资期限</em><span class="special">'
				+ isDayStr
				+ '</span></p><p class="blue"><em>还款方式</em><span>'
				+ BORROW_ALL_REPAYMENT_STYLE[data.repayment_style]
				+ '</span></p><p class="blue"><em>信用等级</em><span>'
				+ bidRankStr
				+ '</span></p><p class="l_txt"><em>借款进度</em><span class="process"><span class="process_bar" style="width:'
				+ percent
				+'"></span></span><span><i class="needpay">'
				+ data.tender_sum
				+ '/'
				+ data.borrow_sum
				+ '</i><i class="percent">'
				+ percent
				+ '</i></span></p>'
				+ '<p class="l_txt">'
				+ fullHtml
				+ '</p></div></div>'
				+ buttonHtml
				+ '</div></div>'
				;
		return htmlStr;
	};
	
	function fmoney(s, n)  
	{  
	   n = n > 0 && n <= 20 ? n : 2;  
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	   var l = s.split(".")[0].split("").reverse(),  
	   r = s.split(".")[1];  
	   t = "";  
	   for(i = 0; i < l.length; i ++ )  
	   {  
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	   }  
	   //return t.split("").reverse().join("") + "." + r;
	   return t.split("").reverse().join("");
	} 