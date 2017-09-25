var checkNum = 0;
var data = {};
var loanTypeArray = new Array();
var termArray = new Array();
var creditLevelArray = new Array();
var rateArray = new Array();
//数组初始化
var rateArray = new Array();//利率
var dateArray = new Array();//期限
var statuArray = new Array();//状态
var securitymodeArray = new Array();//担保方式
var borrowtypeArray = new Array();//标种

var fenbiaoArray = new Array();//区分新手标和其他标
function getBorrowInfo(data) {
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
		var publicsh = data.publish_datetime.time;
		var end_time = data.end_time.time;
		var nowDate = new Date().getTime() ;
		if(publicsh > nowDate){
			buttonHtml = '<a href="javascript:;" class="invest_btn HkzDksYjs_btn">预告中</a>';
		}else if(end_time <= nowDate) {
			buttonHtml = '<a href="javascript:;" class="invest_btn HkzDksYjs_btn" target="_blank">已结束</a>';
		}else{
			buttonHtml = '<a href="'+$_path+'/invests/'+data.id+'" class="invest_btn" target="_blank">我要投资</a>';
		}
		
	} else {
		buttonHtml = '<a href="javascript:;" class="invest_btn HkzDksYjs_btn" target="_blank">' + BORROW_ALL_STATUS[data.borrow_status] + '</a>';
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
	if(leng == 100){
		fullHtml = '<p class="l_text"><img src="'+$_frontPath+'/images/full.png" style="margin-top: -4px;width: 68px;"/></p>';
	}
	htmlStr = '<li><h4><a alt='+data.borrow_title+' title='+data.borrow_title+' href="'+$_path+'/invests/'+data.id+'" target="_blank">'
			+ data.borrow_title
			+ '<img src="'+$_frontPath+'/images/zst.png" style="padding-left: 7px; width: 18px;"/></a></h4><div class="txt"><p class="l_txt"><em>预期年化利率</em><span><i>'
			+ data.annual_interest_rate.toFixed(2)
			+ '</i>%</span></p><p class="blue"><em>投资期限</em><span>'
			+ isDayStr
			+ '</span></p><p class="blue"><em>还款方式</em><span>'
			+ BORROW_ALL_REPAYMENT_STYLE[data.repayment_style]
			+ '</span></p><p class="blue"><em>项目总额</em><span>'
			+ addCommas(data.borrow_sum.toFixed(2))
			+ '元</span></p><span class="circle"><div class="percent_small"><font>'
			+ leng
			+ '%</font><svg style="overflow: hidden; position: relative; left: -0.5px;" xmlns="http://www.w3.org/2000/svg" width="52" version="1.1" height="52"></svg></div></span>'
			+fullHtml+ buttonHtml + '</div></li>';
	return htmlStr;
}

//数组初始化
function initArray(obj) {
	rateArray.length = 0;
	dateArray.length = 0;
	statuArray.length = 0;
	securitymodeArray.length = 0;
	borrowtypeArray.length = 0;
	fenbiaoArray.length = 0;
	rateArray.push("all");
	dateArray.push("all");
	statuArray.push("all");
	securitymodeArray.push("all");
	borrowtypeArray.push("all");
	fenbiaoArray.push("feixin");
}
//点击赋值
function sort(obj, type) {
	var value = obj;
	var type = type;
	switch (type) {
	case 'status':
		statuArray.length = 0;
		statuArray.push(value);
		if(value =="all"){
			$("#statusall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
			$("#statusall span").removeClass('J-chose');
		}else{
			$("#statusall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
		}
		break;
	case 'rate':
		rateArray.length = 0;
		rateArray.push(value);
		if(value =="all"){
			$("#rateall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
			$("#rateall span").removeClass('J-chose');
		}else{
			$("#rateall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
		}
		break;
	case 'date':
		dateArray.length = 0;
		dateArray.push(value);
		if(value =="all"){
			$("#dateall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
			$("#dateall span").removeClass('J-chose');
		}else{
			$("#dateall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
		}
		break;
	case 'securitymode':
		securitymodeArray.length = 0;
		securitymodeArray.push(value);
		if(value =="all"){
			$("#securitymodeall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
			$("#securitymodeall span").removeClass('J-chose');
		}else{
			$("#securitymodeall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
		}
		break;
	case 'borrowtype':
		borrowtypeArray.length = 0;
		borrowtypeArray.push(value);
		if(value =="all"){
			$("#borrowtypeall em").css({background:"#c6c6c6 none repeat scroll 0 0",color:"#fff"});
			$("#borrowtypeall span").removeClass('J-chose');
		}else{
			$("#borrowtypeall em").css({background:"#ffffff none repeat scroll 0 0",color:"#706f6f"});
		}
		break;
	}
	searchByParams();
}

function searchByParams() {
	var data = {};
	data.rateArrays = rateArray.unique2().toString();
	data.dateArrays = dateArray.unique2().toString();
	data.statuArrays = statuArray.unique2().toString();
	data.borrowtypeArrays = borrowtypeArray.unique2().toString();
	data.securitymodeArrays = securitymodeArray.unique2().toString();
	
	getPageFrom(data, $_path+"/borrow/showBorrowStatusInfoPageByParam.do", getBorrowInfo, "hkct-tz-list");
}