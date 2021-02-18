$(function() {
	/* ----- 페이지 별 카테고리 고정시키기 ----- */
	/*
	//메일 페이지 일 때
	$('#categoryMail').next().css('display','block');
	$('#categoryMail').next().css('height','150px');
	$('#categoryMail').children().last().children().attr('class','fas fa-chevron-left');
	//인사관리 일 때
	$('#categoryMypage').next().css('display','block');
	$('#categoryMypage').next().css('height','150px');
	$('#categoryMypage').children().last().children().attr('class','fas fa-chevron-left');
	//근태관리 일 때
	$('#categoryWork').next().css('display','block');
	$('#categoryWork').next().css('height','75px');
	$('#categoryWork').children().last().children().attr('class','fas fa-chevron-left');
	//전자 결재 일 때
	$('#categoryElecAppr').next().css('display','block');
	$('#categoryElecAppr').next().css('height','150px');
	$('#categoryElecAppr').children().last().children().attr('class','fas fa-chevron-left');
	//프로젝트 일 때
	$('#categoryProject').next().css('display','block');
	$('#categoryProject').next().css('height','125px');
	$('#categoryProject').children().last().children().attr('class','fas fa-chevron-left');
	
	$('#categoryProject').next().children().eq(2).children().css('font-weight','800');
	$('#categoryProject').next().children().eq(2).children().css('color','#ffcc29');
	
	
	//관리 페이지 일 때
	$('#categoryAdmin').next().css('display','block');
	$('#categoryAdmin').next().css('height','200px');
	$('#categoryAdmin').children().last().children().attr('class','fas fa-chevron-left');
	 */

	/* 검색창 애니메이션 */
	$('#headerNavi').children().eq(0).click(function() {
		$('#searchBox').css('height', '20px');
		$('#searchBox').css('margin-top', '0');
		$('#searchBox').css('transition', '0.3s');
		$('#searchBox').css('cursor', 'text');
		$('#searchBox').attr('placeholder','통합 검색...'); /*진원 추가*/
		$('#searchBox').focus(); /*진원 추가*/
	});
	$('#searchBox').focusout(function() {
		$('#searchBox').css('height', '0.01px');
		$('#searchBox').css('margin-top', '29px');
		$('#searchBox').css('transition', '0.3s');
		$('#searchBox').css('cursor', 'point');
		$('#searchBox').attr('placeholder',''); /*진원 추가*/
	});

	/* 카테고리 나타내기 */
	$('.sideNaviClick').click(function() {
		var $category = $(this).next();
		var $arrow = $(this).children().last().children();
		var $name = $(this).children().eq(1).text();
		if (!($name == '홈' || $name == '자료실')) {
			if ($category.css('height') == '0px') {
				$category.css('display', 'block');
				if ($name == '메일') {
					$category.animate({
						height : '150px'
					}, 300);
				} else if ($name == '인사관리') {
					$category.animate({
						height : '150px'
					}, 300);
				} else if ($name == '근태관리') {
					$category.animate({
						height : '75px'
					}, 300);
				} else if ($name == '게시판') {
					$category.animate({
						height : '150px'
					}, 300);
				} else if ($name == '전자결재') {
					$category.animate({
						height : '150px'
					}, 300);
				} else if ($name == '프로젝트') {
					$category.animate({
						height : '125px'
					}, 300);
				} else if ($name == '관리') {
					$category.animate({
						height : '75px'
					}, 300);
				}
				$arrow.attr('class', 'fas fa-chevron-left');
			} else {
				$category.animate({
					height : '0px'
				}, 300);
				$arrow.attr('class', 'fas fa-chevron-right');
			}
		}

	});
	/* 페이지 새로고침 */
	$('#logoImg').click(function() {
		location.reload();
	})

	/* 말풍선 */
	$('#searchBtn').hover(function() {
		$('#searchTooltip').css('visibility', 'visible');
	}, function() {
		$('#searchTooltip').css('visibility', 'hidden');
	});

	$('#userLogout').hover(function() {
		$('#exitTooltip').css('visibility', 'visible');
	}, function() {
		$('#exitTooltip').css('visibility', 'hidden');
	});
	
	$('#myInfoHead').hover(function() {
		$('#myInfoTooltip').css('visibility', 'visible');
	}, function() {
		$('#myInfoTooltip').css('visibility', 'hidden');
	});
});