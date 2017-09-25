// JavaScript Document


var sw = 1;
function sliderDO(){
	
	//滑入停止动画，滑出开始动画
	$("#banner").hover(function () {
		if(myTime){
		   clearInterval(myTime);
		}
	},function(){
		myTime = setInterval(function(){
		  myShow(sw)
		  sw++;
		  if(sw==7){sw=0;}
		} , 5000);
	});
	
	//自动开始
	var myTime = setInterval(function(){
	   myShow(sw)
	   sw++;
	   if(sw==7){sw=0;}
	} , 3500);
}

function liTab(obj){
	myShow(obj);
}

function myShow(i){
    $("#banner #num  a").eq(i).addClass("num-active").siblings("a").removeClass("num-active");
    $("#banner #slider  a").eq(i).stop(true, true).fadeIn(600).siblings("a").fadeOut(600);
}

