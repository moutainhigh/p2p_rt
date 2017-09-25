	/**
	 *资讯中心、专题中心
	 */
	function getContentList() {
		$.get($_path +"/getContentList.do", function(data, status) {
			var videoHtml = '';
			var aqbzHtml = '';
			
			$.each(data.vodeo, function(i, vodeo) {
				videoHtml += getVideoHtml(i, vodeo);
			});
			
			$.each(data.zygg, function(i, zygg) {
				aqbzHtml += getContentHtml(i, zygg);
			});
			
			$("#aqbz").html(aqbzHtml);
		    $("#video").html(videoHtml);
		});
	}
	
	function getVideoHtml(i, data) {
		var title = data.content_title;
		var titles = "";
		if (title.length > 10) {
			titles = title.substr(0, 10)+'...';
		} else {
			titles = title;
		}
		var path = data.attach_path;
		if (typeof (path) != 'undefined') {
			path = $_path + path;
		} else {
			path = $_frontPath + "/images/mintaiLOGO.png";
		}
		var cont = data.content_txt;
		cont = cont.replace(/<[^>]+>/g, "").replace(/&nbsp;/g, "");
		if (cont.length > 30) {
			cont = cont.subString(0, 20) + "...";
		}
		var imgHtml = '';
		if(i<=0){
			imgHtml = '<img src="'+$_frontPath+'/images/tb6.png">';
		}else{
			imgHtml = '<img src="'+$_frontPath+'/images/tb5.png">';
		}
		channelss= '<li><a href="' + $_path
		+ '/content/contentDetails/' + data.channel_id + '/' + data.id
		+ '.do">'+imgHtml+titles+'<p>'+toDate(data.content_add_datetime,"MM-dd")+'</p></a></li>';
		return channelss;
	}
	
	function getContentHtml(index, data) {
		var title = data.content_title;
		var titles = "";
		if (title.length > 10) {
			titles = title.substr(0, 10)+'...';
		} else {
			titles = title;
		}
		var path = data.attach_path;
		if (typeof (path) != 'undefined') {
			path = $_path + path;
		} else {
			path = $_frontPath + "/images/mintaiLOGO.png";
		}
		var cont = data.content_txt;
		cont = cont.replace(/<[^>]+>/g, "").replace(/&nbsp;/g, "");
		if (cont.length > 20) {
			cont = cont.subString(0, 20) + "...";
		}
		var imgHtml = '';
		if(index<=0){
			imgHtml = '<img src="'+$_frontPath+'/images/tb6.png">';
		}else{
			imgHtml = '<img src="'+$_frontPath+'/images/tb5.png">';
		}
		channelss= '<li><a href="' + $_path
		+ '/content/contentDetails/' + data.channel_id + '/' + data.id
		+ '.do">'+imgHtml+titles+'<p>'+toDate(data.content_add_datetime,"MM-dd")+'</p></a></li>';
		return channelss;
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
	
	// 相关文章\上一篇\下一篇
	function relatedContent(){
		var suffix = '';
		if(pChannel != null && pChannel != undefined && pChannel == 77){
			suffix = '.html';
		}
        $.ajax({
            dataType: 'json',
            url:$_path+"/content/getContentListMapJson.do",
            data:paramsObject,
            type: 'POST',
            beforeSend: function(){
                
            },
            success: function(data) {
           	  $.each(data,function(i,item){
           		  if(item.id==currentId){
           			  if(i==0){
           				  var nextHtml = '下一篇：<a href="'+$_path+contentUrl+data[i+1].id+suffix+'" target="_blank" class="clearfix">'+data[i+1].content_title+'</a>';
           				  $("#next").html(nextHtml);
           			  }else if(i>0 && i <data.length-1){
           				  var nextHtml = '下一篇：<a href="'+$_path+contentUrl+data[i+1].id+suffix+'" target="_blank" class="clearfix">'+data[i+1].content_title+'</a>';
           				  var preHtml = '上一篇：<a href="'+$_path+contentUrl+data[i-1].id+suffix+'" target="_blank" class="clearfix">'+data[i-1].content_title+'</a>';
           				  $("#next").html(nextHtml);
           				  $("#pre").html(preHtml);
           			  }else{
           				  var preHtml = '上一篇：<a href="'+$_path+contentUrl+data[i-1].id+suffix+'" target="_blank" class="clearfix">'+data[i-1].content_title+'</a>';
           				  $("#pre").html(preHtml);
           			  }
           		  }else{
           			  relationContents.push(item);
           		  }
           	  });
           	  
           	  var relationContentHtml = '';
           	  $.each(relationContents,function(i,item){
						var title = item.content_title; 
						if(title.length>16){
							title = title.substr(0, 16) + "......";
						}
           		  if((i+1)%2==0){
           			  relationContentHtml += '<span class="right"><a href="'+$_path+contentUrl+item.id+suffix+'" target="_blank" title="'+item.content_title+'">'+title+'</a></span></div>';
           		  }else{
           			  relationContentHtml += '<div class="relationContentBody"><span><a href="'+$_path+contentUrl+item.id+suffix+'" target="_blank" title="'+item.content_title+'">'+title+'</a></span>';
           		  }
           	  });
           	  $("#relationContent").append(relationContentHtml);
            },
            error: function() {
                
            }
        }); 
    }
	
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
	    