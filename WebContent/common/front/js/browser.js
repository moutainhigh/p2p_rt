$(function() {
	if ($.browser.msie && ($.browser.version == "6.0")) {
		alert("尊敬的用户，由于您浏览器版本太低，为了获得更好用户体验，建议您升级浏览器后重新打开，谢谢！");
		window.opener = null;
		window.open("", "_self");
		window.close();
	} else if ($.browser.msie && ($.browser.version == "7.0")) {
		//alert("尊敬的用户，由于您浏览器版本太低，为了获得更好用户体验，建议您升级浏览器后重新打开，谢谢！");
		//window.opener = null;
		//window.open("", "_self");
		//window.close();
		window.location.href = $_path+"/discussion/updateBrowser.do";
	} else if ($.browser.msie && ($.browser.version == "8.0")) {
	} else if ($.browser.msie && ($.browser.version == "9.0")) {
	} else if ($.browser.msie && ($.browser.version == "10.0")) {
	} else if ($.browser.msie && ($.browser.version == "11.0")) {
	} else if ($.browser.safari) {

	} else if ($.browser.webkit) {

	} else if ($.browser.mozilla) {

	} else if ($.browser.opera) {

	} else {

	}

});