<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<!-- 폰트 어썸 CDN -->
	<script src="https://use.fontawesome.com/releases/v5.8.2/js/all.js"></script>
	<!-- 제이쿼리 CDN -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>

	<!-- 관리자 페이지 공통 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/style.css" />
	<!-- 관리자 페이지 사이드 네비 바 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/sideNaviBar.css" />

</head>
<body>
	
	<div id="sideNavi">
		<ul>
			<li>
				<a href="/adminMainPage.ho" class="aStyle">
					<p id="teamName" class="hoverColor">BlackBoard</p>
				</a>
			</li>
			<li>
				<a href="/adminSelectPage.ho" class="aStyle">
	    			<div class="naviNo hoverColor" id="adminUpdate">
	        			<div class="naviIcon"><i class="fas fa-user-friends"></i></div>
	        			<span class="naviText">관리자 관리</span>
	        			<span class="naviClick"><i class="iArrow fa fa-angle-right"></i></span>
	    			</div>
				</a>
			</li>
			<li>
				<div class="aStyle">
					<div class="naviNo hoverColor" id="deleteSelect">
						<div class="naviIcon"><i class="fas fa-folder-minus"></i></div>
						<span class="naviText">삭제 조회</span>
						<span class="naviClick"><i class="iArrow fa fa-angle-right"></i></span>
					</div>
					<div class="accordion" id="deleteList">
						<a href="/adminDeleteMemberPage.ho" class="aStyle"><p class="accordionList hoverColor">사원 조회</p></a>
						<a href="/adminDeleteNoticePage.ho" class="aStyle"><p class="accordionList hoverColor">게시글 조회</p></a>
						<a href="/adminDeleteApprovalPage.ho" class="aStyle"><p class="accordionList hoverColor">결재안 조회</p></a>
					</div>
				</div>
			</li>
			<li>
				<a href="/adminDeleteDataPage.ho" class="aStyle">
					<div class="naviNo hoverColor" id="dataList">
						<div class="naviIcon"><i class="fas fa-folder-open"></i></div>
						<span class="naviText">데이터 / 문서 관리</span>
						<span class="naviClick"><i class="iArrow fa fa-angle-right"></i></span>
					</div>
				</a>
			</li>
			<li>
				<div class="aStyle">
					<div class="naviNo hoverColor" id="error">
						<div class="naviIcon"><i class="fas fa-question"></i></div>
						<span class="naviText">오류 관리</span>
						<span class="naviClick"><i class="iArrow fa fa-angle-right"></i></span>
					</div>
					<div class="accordion" id="errorList">
						<a href="/adminPasswordInitPage.ho" class="aStyle"><p class="accordionList hoverColor">비밀번호 초기화</p></a>
						<a href="#" class="aStyle"><p class="accordionList hoverColor">문의 사항</p></a>
					</div>
				</div>
			</li>
		</ul>
	</div>
    <script type='text/javascript'>	    
        $(function(){
            $('.aStyle').click(function(){
                $naviName = $(this).children().children().eq(1).text();
                $naviArrow = $(this).children().children().eq(2).children();
                $naviState = $(this).children().next();

                if($naviState.css('height')=='0px'){
                    $naviState.css('display','block');
                    $naviArrow.attr('class','iArrow fas fa-angle-left');
                    
                    if($naviName=='삭제 조회'){
                        $naviState.animate({height:'110px'},300);
                    }else if($naviName=='오류 관리'){
                        $naviState.animate({height:'72px'},300);
                    }
                    
                } else {
                    $naviState.animate({height:'0px'},300);
                    $naviArrow.attr('class','iArrow fas fa-angle-right');                   
                }
            });
            
        });
    </script>
</body>
</html>