$(function(){
	//즐겨찾기
    $('.fa-star').click(function(){
		console.log('클릭!');
	    var proNo = $(this).next().val();
	    var memNo = $(this).next().next().val();
	    if($(this).css('color')=='rgb(255, 255, 255)'){
	        $.ajax({
	        	url : "/insertProjectFavor.ho",
	        	data : {"proNo" : proNo, "memNo" : memNo},
	        	type : "get",
	        	success : function(result){
	        		if(result=="false"){
	        			alert("프로젝트 즐겨찾기 실패");
	        		}
	        	},
	        	error : function(){
	        		console.log("프로젝트 즐겨찾기 ajax 통신 실패");
	        	}
	        });
	        $(this).attr('class','fas fa-star favor');
	    }else{
	    	$.ajax({
	        	url : "/deleteProjectFavor.ho",
	        	data : {"proNo" : proNo, "memNo" : memNo},
	        	type : "get",
	        	success : function(result){
	        		if(result=="false"){
	        			alert("프로젝트 즐겨찾기 실패");
	        		}
	        	},
	        	error : function(){
	        		console.log("프로젝트 즐겨찾기 ajax 통신 실패");
	        	}
	        });
	        $(this).attr('class','far fa-star');
	    }
    	
        
    });
    
    $('.pro-subject').click(function(){
    	$(this).parents('form').submit();
    });
    
    $('.shortcutsBtn').click(function(){
    	location.replace('/projectProgressList.ho');
    });
    
    $('.detailBtn').click(function(){
    	$(this).parents('form').submit();
    });
    
});
