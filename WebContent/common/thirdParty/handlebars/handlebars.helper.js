
/**
 * 参考：http://www.cnblogs.com/lvdabao/p/handlebars_helper.html
 */
Handlebars.registerHelper("showMaxLen", function(data,len) {
	var title = "";
	if(data){
		if(data.length > len){
			if(len>6){
				data = data.substr(0,len-6)+"......";
				title = " title='"+data+"' ";
			}
		}
		return new Handlebars.SafeString("<span"+title+">"+data+"</span>");
	}
	return "";
		
});