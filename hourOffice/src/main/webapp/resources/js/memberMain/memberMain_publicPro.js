$(function(){
    $('.fa-star').click(function(){ /*즐겨찾기 색상 바꾸기*/
        console.log('클릭 성공!');
        var $star = $(this);
        
        if($star.hasClass('far')){
        	$star.removeClass('favor');
            
        }else if($star.hasClass('fas')){
        	$star.addClass('favor');
        }
        
    });
    
    $('.pro-subject').click(function(){
    	$(this).parents('form').submit();
    });
    
    $('.shortcutsBtn').click(function(){
    	$(this).parents('form').submit();
    });
});
