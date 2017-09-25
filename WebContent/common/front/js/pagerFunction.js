
	//动态创建页标签
	function usrInfoPager(page){
		var currentPage = Number(page.currentPage);//当前页
		var	pageCount = Number(page.totalPage);// 总页数
		var totalRecord=Number(page.totalRecord);//总记录数
	    var pagerHtml = '';
	    pagerHtml +='<span style="text-align:center;line-height:40px;">';
	    pagerHtml +='共'+totalRecord+'条&nbsp;';
	    pagerHtml +='<span class="">';
	    pagerHtml +='<a href="javascript:jumpPage(1);">首页</a></span>&nbsp;';
	    pagerHtml +='<span class=""><a href="javascript:jumpPage('+ (currentPage-1) +');">上一页</a></span>&nbsp;';
	    pagerHtml +='<span class=""><a href="javascript:jumpPage('+ (currentPage+1) +');">下一页</a></span>&nbsp;';
	    pagerHtml +='<span class=""><a href="javascript:jumpPage('+ pageCount +');">末页</a></span>&nbsp;';
	    pagerHtml +='第<strong>'+currentPage+'</strong>页/共'+pageCount+'页&nbsp;&nbsp;';
	    pagerHtml+='第<input style="border: 1px solid #eaedf0;text-align:center;" name="pageNum" type="text" id="pageNum" size="6" value="'+currentPage+'"/>页&nbsp;';
	    pagerHtml +='<span class=""><a href="javascript:jumpPage();">跳转</a></span>';
	    pagerHtml +='</span>';
	   
	    return pagerHtml;
	}