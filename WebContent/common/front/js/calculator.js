function  calculate(){
	calculator();
}

function calculator() {
    var principle = parseFloat($('#principal').val());
    var month = parseInt($('#month').val());
    var rate = parseFloat($('#rate').val() / 100);
    var date = new Date();  
    var hkfs = $("#hkfs").val();
    
    var startDate = date;
    var monthlyRate="";
   if(hkfs==3||hkfs==2||hkfs==4){//3:按月分期还款(按月) //2:一次性还款(按月)//4:每月还息到期还本(按月)
   	 	monthlyRate = rate / 12;
   }else if(hkfs==1){//按天分期还款(按天)
   		monthlyRate = rate / 30 /12;
   }
    
    var powOfAll = Math.pow(1 + monthlyRate, month);
    var powSubtractOne = powOfAll - 1;
    var divideValue = monthlyRate * powOfAll / powSubtractOne;

    var totalAmount = 0;
    var monthlyIncome;
    if (powOfAll != 1) {
        monthlyIncome = (principle * divideValue);
    } else {
        monthlyIncome = (principle / month);
    }

    var restPrinciple = principle;
    var paidPrincipal = parseFloat(0);
    var content = '';

    $('#investRecordsTable').html("");
    if(hkfs!=2){
	    for (var i = 1; i <= month; i++) {
	        var payDate = getPayDate(startDate, i,hkfs);
	        payDate = changeDateType(payDate);
	        if (i <= month - 1 || month == 1) {
	        	var tmpk=0;
	        	var principleOfKMonth=0;
	            var interestOfKMonth=0;
	        	var principleOfKMonth2=0;
	        	if(hkfs!=4){
	        		
	        		tmpk = Math.pow(1 + monthlyRate, i - 1);
	 	             principleOfKMonth = (principle * monthlyRate * tmpk) / powSubtractOne;
	 	             interestOfKMonth = (monthlyIncome - principleOfKMonth).toFixed(2);
	 	             principleOfKMonth2 = (monthlyIncome - interestOfKMonth).toFixed(2);
	 	            
	 	            
	 	            paidPrincipal += parseFloat(principleOfKMonth2);
	 	            restPrinciple -= principleOfKMonth2;
	 	            totalAmount += parseFloat(monthlyIncome.toFixed(2));
	        	}
	           
	            if(hkfs==4){//每月还息到期还本(按月)
	            	if(month == 1){
						tmpk = Math.pow(1 + monthlyRate, i - 1);
						principleOfKMonth = (principle * monthlyRate * tmpk) / powSubtractOne;
						interestOfKMonth = (monthlyIncome - principleOfKMonth).toFixed(2);
						principleOfKMonth2 = (monthlyIncome - interestOfKMonth).toFixed(2);
						paidPrincipal += parseFloat(principleOfKMonth2);
						restPrinciple -= principleOfKMonth2;
						totalAmount =parseFloat(monthlyIncome.toFixed(2));
					}else{
						interestOfKMonth = principle * monthlyRate;
						monthlyIncome = interestOfKMonth;
						principleOfKMonth2 = 0.00;
						restPrinciple = principle;
						totalAmount += parseFloat(monthlyIncome.toFixed(2));
					}
	            	
	            	
	            }
          
            
            content +='<tr ><td  >'
	    		+payDate
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(monthlyIncome.toFixed(2))
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(interestOfKMonth)
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(principleOfKMonth2)
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(restPrinciple.toFixed(2))
	    		+'</td></tr>';
        } else {
          	var lastMonthIncome=0;
        	var lastMonthPrincipal=0;
        	var lastMonthInterest=0;
        	var remainPrinciple =0;
        	if(hkfs!=4){
        		lastMonthIncome = (getLastMonthIncome(principle, monthlyRate, powOfAll, powSubtractOne, month, monthlyIncome)).toFixed(2);
	            lastMonthPrincipal = (getLastMonthPrincipal(principle, paidPrincipal)).toFixed(2);
	            lastMonthInterest = (lastMonthIncome - lastMonthPrincipal).toFixed(2);
	            //remainPrinciple = 0;
	            totalAmount += parseFloat(lastMonthIncome);
        	}
            
            if(hkfs==4){//每月还息到期还本(按月)
            	lastMonthInterest=principle*monthlyRate;
            	lastMonthIncome=principle+lastMonthInterest;
            	lastMonthPrincipal=principle;
            	//restPrinciple = 0;
            	totalAmount +=lastMonthIncome;
            }

          
            content +='<tr ><td >'
	    		+payDate
	    		+'</td><td>'
	    		+numberFormatWithoutCurrency(lastMonthIncome)
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(lastMonthInterest)
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(lastMonthPrincipal)
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(remainPrinciple.toFixed(2))
	    		+'</td></tr>';
        }
        //$('#resultTable').find('.content_wrap:odd').addClass("even");
    }
    var totalInterest = totalAmount.toFixed(2) - principle;
    
    content +='<tr > <td >合计'
		+'</td><td >'
		+numberFormatWithoutCurrency(totalAmount.toFixed(2))
		+'</td><td >'
		+numberFormatWithoutCurrency(totalInterest.toFixed(2))
		+'</td><td >'
		+numberFormatWithoutCurrency(principle.toFixed(2))
		+'</td><td >0.00'
		+'</td></tr>';
    
    
    }else if(hkfs==2){//一次性还款(按月)
	    var payDate = getPayDate(startDate, month,hkfs);
	    payDate=changeDateType(payDate);
	    totalInterest =principle*monthlyRate*month;
	    totalAmount =principle+totalInterest;
	   
	    content +='<tr > <td>'
	    		+payDate
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(totalAmount.toFixed(2))
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(totalInterest.toFixed(2))
	    		+'</td><td >'
	    		+numberFormatWithoutCurrency(principle.toFixed(2))
	    		+'</td><td >0.00</td></tr>';
	    
	    
}
    $("#investRecordsTable").html(content);
}

function getLastMonthIncome(principle, monthlyRate, powOfAll, powSubtractOne, month, monthlyIncome) {
    var totalIncome = (principle * month * monthlyRate * powOfAll) / powSubtractOne;
    var alreadyIncome = monthlyIncome.toFixed(2) * (month - 1);
    return totalIncome - alreadyIncome;
}

function getLastMonthPrincipal(principle, paidPrincipal) {
    return principle - paidPrincipal;
}

function changeToDate(date) {
    return new Date(Date.parse(date.replace(/-/g, "/")));
}

function getPayDate(startDate, num,hkfs) {
	if(hkfs==3||hkfs==2||hkfs==4){//3：按月分期还款(按月)//2：一次性还款(按月)4:每月还息到期还本(按月)
		var oneDayTime = 86400000;
	    var payDate = new Date(startDate.getFullYear(), startDate.getMonth() + num, startDate.getDate());
	    if (isOnSameDayOfMonth(startDate, payDate)) {
	        payDate.setTime(payDate.getTime() - oneDayTime);
	        return payDate;
	    } else {
	        payDate.setTime(payDate.getTime() - payDate.getDate() * oneDayTime);
	        return payDate;
	    }
	}else if(hkfs==1){//按天分期还款(按天)
		var oneDayTime = 86400;
	    var payDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()+num);
	    if (isOnSameDayOfMonth(startDate, payDate)) {
	        payDate.setTime(payDate.getTime() - oneDayTime);
	        return payDate;
	    } else {
	        payDate.setTime(payDate.getTime() - payDate.getDate() * oneDayTime);
	        return payDate;
	    }
	}
}

function isOnSameDayOfMonth(date1, date2) {
    return date1.getDate() == date2.getDate();
}

function changeDateType(payDate) {
    return payDate.getFullYear() + "-" + (payDate.getMonth() < 9 ? "0" + (parseInt(payDate.getMonth()) + 1) : parseInt(payDate.getMonth()) + 1) + "-" + (payDate.getDate() < 10 ? "0" + parseInt(payDate.getDate()) : parseInt(payDate.getDate()));
}



function clearAll() {
	$('#principal').val("");
    $('#month').val("");
    $('#rate').val("");
	$("#investRecordsTable").html("");
}

function numberFormatWithoutCurrency(nStr) {
    if (nStr === 0 || nStr === "0") {
        return "0.00";
    }

    if (!nStr || isNaN(nStr)) {
        return "";
    }

    nStr += '';
    var x = nStr.split('.');
    var x1 = x[0];
    var x2 = x.length > 1 ? '.' + x[1] : '.00';
    if (x2.length < 3) {
        x2 += "00";
    }
    x2 = x2.substring(0, 3);
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}
