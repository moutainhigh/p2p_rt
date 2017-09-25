$(function(){

	/*page5的切换效果*/
	$(".page5 .con1 .pic1 a").click(function(event) {
		$(this).parents().siblings('.pic2').children('img').eq($(this).index()).show().siblings('').hide();
		$(this).addClass('yel').siblings('').removeClass('yel');
	});

	//翻页效果	
	$('#safe').fullpage({
		verticalCentered:true,
		sectionsColor:['#55a5fe','#FFFFFF','#55a5fe','#FFFFFF','#55A5FE','#FFFFFF','#55A5FE'],
		navigation:true,
		scrollingSpeed:1500,
		navigationPosition:'right',
	});
});