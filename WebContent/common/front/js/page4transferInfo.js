
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
	var pageDivIdObject;
	var pager_container = null; //分页组件容器ID
	var pager_function = createPager; //构造分页方法
	
	
	function couponGoToPage(currentPage){
	    var start = (currentPage-1)*couponPageSize,end = currentPage*couponPageSize-1;
	    couponItem.hide();
	    for(var i=start;i<=end;i++){
	        $(couponItem[i]).show();
	    }
	    $("#couponTablePager").remove();
	    if(couponRecordsCount > couponPageSize){
	        couponEntry.append(createPager(currentPage,couponPageCount,"couponTablePager","couponGoToPage"));
	    }
	}
	
	
	function initInvestRecordsTable(){
	    $("#"+tableIdObject+"").html("");
	    $("#"+pageDivIdObject+"").html("");
	}


	//动态创建页标签
	function createPager(page){
		var currentPage = Number(page.currentPage);
		var	pageCount = Number(page.totalPage);   
	    var pagerHtml = '';
	    
	   /* pagerHtml += '<div class="invest_page"><div id="pages" class="page">';*/
	    pagerHtml += '';
	    if(currentPage>1){
	    	pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-1) +');" class="page"><</a>';
	     //   pagerHtml += '<a href="javascript:jumpPage(1);" class="arr1">首页</a><a href="javascript:jumpPage('+ (currentPage-1) +');" class="page"><</a>';
	    }else{
	    	pagerHtml += '<a href="javascript:;"  class="arr1"><</a>';
	      //  pagerHtml += '<a href="javascript:;" class="arr1">首页</a><a href="javascript:;" class="aa"><</a>';
	    }
	          
	    var showTotalPageNum = 8;
	        if(pageCount > showTotalPageNum){
	            // 在第四页和倒数第四页之间
	            if(currentPage>4 &&  currentPage<=pageCount-4){
	                pagerHtml += '<a href="javascript:jumpPage(1);" >1</a>';
	                pagerHtml += '<span>...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-2) +');">'+ (currentPage-2) +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage-1) +');">'+ (currentPage-1) +'</a>';
	                pagerHtml += '<a class="page" href="javascript:;" >'+ currentPage +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage+1) +');">'+ (currentPage+1) +'</a>';
	                pagerHtml += '<a href="javascript:jumpPage('+ (currentPage+2) +');">'+ (currentPage+2) +'</a>';
	                pagerHtml += '<span class="dian">...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ pageCount +');">'+ pageCount +'</a> ';
	            // 第四页之前
	            }else if(currentPage<=4 ){
	                for(var i=1; i<=4 ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a  href="javascript:;" class="page" >'+ i +'</a>';
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');"> '+ i +'</a>';
	                    }
	                }
	                if(currentPage==3){
	                    pagerHtml += '<a href="javascript:jumpPage(5);">5</a>';
	                }
	                if(currentPage==4){
	                    pagerHtml += '<a href="javascript:jumpPage(5);">5</a>';
	                    pagerHtml += '<a href="javascript:jumpPage(6);">6</a>';
	                }
	                
	                pagerHtml += '<span class="dian">...</span>';
	                pagerHtml += '<a href="javascript:jumpPage('+ pageCount +');">'+ pageCount +'</a>';
	            //倒数第四页之后
	            }else if(currentPage>pageCount-4){
	                pagerHtml += '<a href="javascript:jumpPage(1);">1</a>';
	                pagerHtml += '<span class="dian">...</span>';

	                if(currentPage==pageCount-2){
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-4) +');">'+(pageCount-4)+'</a>';
	                }

	                if(currentPage==pageCount-3){
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-5) +');">'+(pageCount-5)+'</a>';
	                    pagerHtml += '<a href="javascript:jumpPage('+ (pageCount-4) +');">'+(pageCount-4)+'</a>';                  
	                }
	                                
	                for(var i=pageCount-3; i<=pageCount ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a class="page" href="javascript:;" >'+ i +'</a>';    
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');">'+ i +'</a>';
	                    }                   
	                }
	            }
	        // 小于页数限额
	        }else{
	                for(var i=1; i<=pageCount ; i++){
	                    if(i == currentPage){
	                        pagerHtml += '<a class="page" href="javascript:;" >'+ i +'</a>';
	                    }else{
	                        pagerHtml += '<a href="javascript:jumpPage('+ i +');">'+ i +'</a>';
	                    }
	                }
	        }
	               
	        if(pageCount>=currentPage+1){
	        	 pagerHtml += '<a href="javascript:jumpPage('+(currentPage+1) +');" class="aa a2">></a>';
	           // pagerHtml += '<a href="javascript:jumpPage('+(currentPage+1) +');" class="aa a2">></a><a href="javascript:jumpPage('+pageCount+');" class="aa a1">尾页</a>';
	        }else{
	        	pagerHtml += '<a href="javascript:;" >></a>';
	        //    pagerHtml += '<a href="javascript:;" class="aa a2">></a><a href="javascript:;" class="aa a1">尾页</a>';;
	        }
	        pagerHtml += '</div></div>';
	        return pagerHtml;
	}
	
	
	function jumpPage(page){
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
		
		
		getPageFrom4BorrowInfo(null,null,null,null,page,pageDivIdObject);
	}
	
	
	
	//分页获取投资记录
	function getPageFrom4TransferInfo(params,url,callBack,tableId,curPage,pageDivId){
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
		if(pageDivId != undefined && pageDivId != null){
			pageDivIdObject = pageDivId;
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
	            		var cu=data.currentPage;
	            		dataHtml = "";
	            		if(listData.length > 0){
	            			for(var i=0;i<listData.length;i++){
		            			dataHtml += callBackObject(listData[i],(cu-1)*10+i+1);
		            		}
	            			if(dataHtml!=""){
	            				var createPagerStr = createPager(data);
	            				if(pageDivId != undefined && pageDivId != null){
	            					$("#"+pageDivId).html(createPagerStr);
	            				}else{
	            					dataHtml += createPagerStr;
	            				}
	            				$(".r5").html(data.totalRecord);
		            			$("#"+tableIdObject).html(dataHtml);
		            			
            			}
	            		}else{
	            			 //$("#"+tableId).append(Util.callType.loading("暂无数据"));
	            			 $("#"+tableId).append('<tr><td colspan="6">暂无数据</td></tr>');
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
	
	 function jumpPageTable(page){
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
			getPageTable(null,null,null,null,page,pageDivIdObject);
	 }
	
var colspanObj;
	
	//分页获取投资记录
	function getPageTable(params,url,callBack,tableId,colspan,curPage){
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
		if(colspan != undefined && colspan != null){
			colspanObj = colspan;
		}else{
			colspanObj=8;
		}

		paramsObject.pageNum = curPage;
	    $.ajax({
	            dataType: 'json',
	            url:urlObject,
	            data:paramsObject,
	            type: 'POST',
	            beforeSend: function(){
	                initInvestRecordsTable();
	                var str='<tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">加载中...</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr>';
	                $("#"+tableId).html(str);
	            },
	            success: function(data) {
	            		initInvestRecordsTable();
	            	if(data!=null){
	            		listData = data.list;
	            		dataHtml = "";
	            		if(listData.length > 0){
	            			for(i=0;i<listData.length;i++){
		            			dataHtml += callBackObject(listData[i]);
		            		}
	            			if(dataHtml!=""){
	            				var currentPage = Number(data.currentPage);
	            				var	pageCount = Number(data.totalPage);   
	            				var totalRecord=Number(data.totalRecord);
	            				//dataHtml += '<tr style="border-bottom:none;" class="page"><td style="text-align: center;border:0px;" colspan="'+colspanObj+'">共'+totalRecord+'条 ';
	            				if(pageCount>1){
	            					dataHtml +='<a href="javascript:jumpPageTable(1);" class="arr1">首页</a><a href="javascript:jumpPageTable('+(currentPage-1)+');" >上一页</a>';
	            				}else{
	            					dataHtml +='<a href="javascript:;" class="arr1">首页</a><a href="javascript:;" class="arr1">上一页</a>';
	            				}
	            				if(currentPage==pageCount){
	            					dataHtml +='<a href="javascript:;" class="">下一页</a>'
	            					+'<a href="javascript:;">尾页</a>';
	            				}else{
	            					dataHtml +='<a href="javascript:jumpPageTable('+(currentPage+1)+');" class="">下一页</a>'
	            					+'<a href="javascript:jumpPageTable('+pageCount+');">尾页</a> ';
	            				}
	            				dataHtml +='&nbsp;&nbsp;&nbsp;&nbsp;页次:<strong>'+currentPage+'</strong>/'+pageCount+'</tr>';
	            				
		            			$("#"+tableIdObject).html(dataHtml);
	            			}
	            		}else{
	            			var str='<tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">暂无数据</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr>';
	            			 $("#"+tableId).html(str);
	            		}
	            	}
	            },
	            error: function() {
	            	 initInvestRecordsTable();
	            	 var str='<tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">加载错误</td></tr><tr class="page" style="border-bottom:none;"><td colspan="'+colspanObj+'">&nbsp;</td></tr>';
	            	 $("#"+tableId).html(str);
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
						w: 55,    
						R: 23,
						sW: 4,
						color: ["#2aa239", "#2aa239", "#2aa239"],
						perent: [100, nPercent],
						speed: 150,
						delay: 400
					});
				}
			});
		}
	}