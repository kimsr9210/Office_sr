/*근태관리*/
    $(function() {
		printTime();
		
	    var $go = $('#go-clock');
	    var $back = $('#back-clock');
	    
	    $('#go').click(function(){
	    	if($go.text().indexOf(':')>0){
	    		alert('이미 출근하였습니다.');
	    	}else{
	    		var goClock = new Date();
	    		$('#startDate').val(goClock);
	    		
	            $.ajax({ // 출근 기록 ajax
	            	url : "/startWork.ho",
                	type : "post",
                	success : function(result){
                		if(result=="false"){
                			alert("출근 오류!");
                		}
                	},
                	error : function(){
                		alert("서버 통신 오류");
                	}
	            });
	            
	            $go.html(getClockFormat(goClock));
	            
	    	}

	    });
	    
	    $('#back').click(function(){
	        if($go.text().indexOf(':')>0){
	        	if($back.text().indexOf(':')>0){
		    		alert('이미 퇴근하였습니다.');
		    	}else{
		    		var $hour = $('#work-hour');
		            var $minute = $('#work-minute');
		            var $startDate = $('#startTime').val();
		            
		            var start = new Date($startDate);
		            var end = new Date();
		            
		            var workTime = end - start;
		            var hour = Math.floor((workTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		            var minute = Math.floor((workTime % (1000 * 60 * 60)) / (1000 * 60));
		            
		            $back.html(getClockFormat(end));
		            $hour.html(hour);
		            $minute.html(minute);
		            
		            $('#workTime').val(hour+'h'+minute+'m');
		            console.log($('#workTime').val());
		            $.ajax({ // 퇴근 기록 ajax
		            	url : "/endWork.ho",
		            	data : {"todayWork" : $('#workTime').val() },
	                	type : "get",
	                	success : function(result){
	                		if(result=="false"){
	                			alert("퇴근 오류!");
	                		}
	                	},
	                	error : function(){
	                		alert("서버 통신 오류");
	                	}
		            });
		    	}
	                    	
	        }else{
	        	alert('출근을 먼저 해주세요');
	        }
	    });
	    
	});
    
    function getClockFormat(date){ //시간 출력 포멧 함수
    	var monthFormat = date.getMonth()+1;
    	var dateFormat = date.getDate();
    	var hourFormat = date.getHours();
    	if(hourFormat < 10){
    		hourFormat = '0'+hourFormat;
    	}
    	var minuteFormat = date.getMinutes();
    	if(minuteFormat < 10){
    		minuteFormat = '0'+minuteFormat;
    	}
    	var secondFormat = date.getSeconds();
    	if(secondFormat < 10){
    		secondFormat = '0'+secondFormat;
    	}
    	return monthFormat +"/"+ dateFormat +" "+ hourFormat +":"+ minuteFormat +":"+ secondFormat;
    }
    
    function printTime() { //실시간 시간 표시 함수
		var clock = document.getElementById("clock");
		var today = new Date();
        var week = new Array('일','월','화','수','목','금','토');
        var weekDay = week[today.getDay()];
		
		clock.innerHTML = today.getFullYear() + "년 " +
		(today.getMonth()+1) + "월 " +
		today.getDate() + "일 " +
        "("+weekDay+") "+
        today.getHours() + ":" +
        today.getMinutes() + ":" +
        today.getSeconds();
		
		setTimeout("printTime()", 1000);
	}