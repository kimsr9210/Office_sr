$(function(){
	
    
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
