
	var couponEntry = $("#couponEntry"),
    couponItem = couponEntry.find(".couponItem");
	var couponPageSize = 5,couponRecordsCount = couponItem.length,
    couponPageCount = Math.ceil(couponRecordsCount/couponPageSize);

	
	var couponPageSize = 5,couponRecordsCount = couponItem.length,
    couponPageCount = Math.ceil(couponRecordsCount/couponPageSize);
	
	
	var paramsObject;
	var urlObject;
	var callBackObject;
	var tableIdObject;
	var pager_container = null; //分页组件容器ID
	var pager_function = createPager; //构造分页方法
	var params_callBackOnFinish;
	
	function couponGoToPage(currentPage){
	    var start = (currentPage-1)*couponPageSize,end = currentPage*couponPageSize-1;
	    couponItem.hide();
	    for(var i=start;i<=end;i++){
	        $(couponItem[i]).show();
	    }
	    $("#couponTablePager").remove();
	    if(couponRecordsCount > couponPageSize){
	        couponEntry.append(pager_function(currentPage,couponPageCount,"couponTablePager","couponGoToPage"));
	    }
	}
	
	
	function initInvestRecordsTable(){
	    $("#"+tableIdObject+"").html("");
	}


	
  
	
	function createPager(page){
		var currentPage = Number(page.currentPage);
		var	pageCount = Number(page.totalPage);  
		
	    var pagerHtml = '';
	    pagerHtml += '<div style="clear:both; text-align:center;"></div> <div id="pages" class="text-c clearfix"  style="text-align:center;width:'+(100+pageCount*50)+'px;">';

	    if(currentPage>1){
	        pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-1) +');" class="aa a1"><</a>';
	    }else{
	        pagerHtml += '<a href="javascript:;" class="aa a1"><</a>';
	    }
	          
	    var showTotalPageNum = 8;
	        if(pageCount > showTotalPageNum){
	            // 在第四页和倒数第四页之间
	            if(currentPage>4 &&  currentPage<=pageCount-4){
	                pagerHtml += '<a href="javascript:jumpPage(1);">1</a>';
	                pagerHtml += '<span>...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-2) +');">'+ (currentPage-2) +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-1) +');">'+ (currentPage-1) +'</a>';
	                pagerHtml += '<a class="current" href="javascript:;" >'+ currentPage +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage+1) +');">'+ (currentPage+1) +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage+2) +');">'+ (currentPage+2) +'</a>';
	                pagerHtml += '<span>...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ pageCount +');">'+ pageCount +'</a>';
	            // 第四页之前
	            }else if(currentPage<=4 ){
	                for(var i=1; i<=4 ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a class="current" href="javascript:;" >'+ i +'</a>';
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');">'+ i +'</a>';
	                    }
	                }
	                if(currentPage==3){
	                    pagerHtml += '<a href="javascript:jumpPage(5);">5</a>';
	                }
	                if(currentPage==4){
	                    pagerHtml += '<a href="javascript:jumpPage(5);">5</a>';
	                    pagerHtml += '<a href="javascript:jumpPage(6);">6</a>';
	                }
	                
	                pagerHtml += '<span>...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ pageCount +');">'+ pageCount +'</a>';
	            //倒数第四页之后
	            }else if(currentPage>pageCount-4){
	                pagerHtml += '<a href="javascript:jumpPage(1);">1</a>';
	                pagerHtml += '<span>...</span>';

	                if(currentPage==pageCount-2){
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-4) +');">'+(pageCount-4)+'</a>';
	                }

	                if(currentPage==pageCount-3){
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-5) +');">'+(pageCount-5)+'</a>';
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-4) +');">'+(pageCount-4)+'</a>';                  
	                }
	                                
	                for(var i=pageCount-3; i<=pageCount ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a class="current" href="javascript:;" >'+ i +'</a>';    
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');">'+ i +'</a>';
	                    }                   
	                }
	            }
	        // 小于页数限额
	        }else{
	                for(var i=1; i<=pageCount ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a class="current" href="javascript:;" >'+ i +'</a>';
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');">'+ i +'</a>';
	                    }
	                }
	        }
	               
	        if(pageCount>=currentPage+1){
	            pagerHtml += '<a href="javascript:jumpPage('+(currentPage+1) +');" class="aa a2">></a>';
	        }else{
	            pagerHtml += '<a href="javascript:;" class="aa a2">></a>';;
	        }
	        pagerHtml += '</div>';
	        return pagerHtml;
	}
	
	
	
/*	function jumpPage(page){
		if(page == undefined){
			page = $("#pageNum").val();
			if(!/^\d+$/.test(page)){
				alertc("只能输入数字");
				return;
			}
			var maxPage = Number($("#pageNum").attr("maxPage"));
			if(page > maxPage){
				page = maxPage;
			}
		}
		
		
		getPageFrom(null,null,null,null,page);
	}*/
	
	
	function jumpPage(page){
		getPageFrom4JiFen(null,null,null,null,null,page);
	}
	//分页获取投资记录
	function getPageFrom(params,url,callBack,tableId,curPage){
		if(curPage == undefined || curPage == null){
			curPage = 1;
		}
		if(params != undefined && params != null){
			paramsObject = params;
		}
		if(url != undefined && url != null){
			urlObject = url;
		}
		if(callBack != undefined && callBack != null){
			callBackObject = callBack;
		}
		if(tableId != undefined && tableId != null){
			tableIdObject = tableId;
		}
		paramsObject.pageNum = curPage;
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
	            		dataHtml = "";
	            		if(listData.length > 0){
	            			for(i=0;i<listData.length;i++){
		            			dataHtml += callBackObject(listData[i],i);
		            		}
	            			if(dataHtml!=""){
	            				
	            				$(".r5").html(data.totalRecord);
		            			
		            			if(pager_container == null){
		            				dataHtml += pager_function(data);
		            			}else{
		            				$("#"+pager_container).html(pager_function(data));
		            			}
		            			
		            			$("#"+tableIdObject).html(dataHtml);
		            			
	            			}
	            		}else{
	            			 //$("#"+tableId).append(Util.callType.loading("暂无数据"));
	            			 $("#"+tableId).append('<div class="invest_page"><font color="#F7772D;">暂无数据</font></div>');
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

	
	//分页获取投资记录
	function getPageFrom4JiFen(params,url,callBack,callBackOnFinish,tableId,curPage){
		if(curPage == undefined || curPage == null){
			curPage = 1;
		}
		if(params != undefined && params != null){
			paramsObject = params;
		}
		if(url != undefined && url != null){
			urlObject = url;
		}
		if(callBack != undefined && callBack != null){
			callBackObject = callBack;
		}
		if(callBackOnFinish != undefined && callBackOnFinish != null){
		
			params_callBackOnFinish = callBackOnFinish;
		}
		if(tableId != undefined && tableId != null){
			tableIdObject = tableId;
		}
		paramsObject.pageNum = curPage;
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
	            		dataHtml = "";
	            		if(listData.length > 0){
	            			for(var i=0;i<listData.length;i++){
		            			dataHtml += callBackObject(listData[i],i);
		            		}
	            			if(dataHtml!=""){
	            				dataHtml += createPager(data);
	            				$(".r5").html(data.totalRecord);
		            			$("#"+tableIdObject).html(dataHtml);
		            			yanchi();
	            			}
	            			if(params_callBackOnFinish){
	            				params_callBackOnFinish(data); //表格加载完成后回调
	            			}
	            		}else{
	            			 /*$("#"+tableId).html(Util.callType.loading("暂无数据"));*/
	            			 $("#"+tableId).append('<div class="invest_page"><div class="page"><font color="#F7772D;">暂无数据</font></div></div>');
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
	
	
	/*延迟加载*/
	function yanchi(){
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
                        w: 80,
                        R: 32,
                        sW: 6,
                        color: ["#78b461", "#78b461", "#78b461"],
                        perent: [100, nPercent],
                        speed: 150,
                        delay: 400
                    });
                }
            });
        }
	}
	
	