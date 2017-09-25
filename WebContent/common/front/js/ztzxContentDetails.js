function getBottomItem(url,callBack,tableId){
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
	paramsObject.numPerPage = 6;
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
        			for(var i=0;i<listData.length;i++){
            			dataHtml += callBackObject(listData[i]);
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

	var titlelength = data.borrow_title;
	if (titlelength.length > 10) {
		title = titlelength.substring(0, 10) + "...";
	} else {
		title = titlelength;
	}
	
	htmlStr = '<div class="project_body"><div class="project_main" style="width: 360px;"><div class="project_main_left" style="border-bottom: 0px;"><h4 style="padding-left: 14px;">'
			+ '<a alt='+data.borrow_title+' title='+ data.borrow_title +' href="'+$_path+'/invests/'+data.id+'" target="_blank">'
			+ title
			+ '<img src="'+$_frontPath+'/images/zst.png"/></a></h4>'
			+ '<div class="showProject"><p class="blue yqnh" style="width: 120px;"><em style="padding-top: 0px;">预期年化利率</em><span><i class="nianhualilv">'+ data.annual_interest_rate.toFixed(2)+'</i>%</span></p><p class="blue"><em>投资期限</em><span class="special">'
			+ isDayStr
			+ '</span></p></div></div></div></div>';
	
	return htmlStr;
};