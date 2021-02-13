<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>

	<!-- 폰트 어썸 CDN -->
	<script src="https://use.fontawesome.com/releases/v5.8.2/js/all.js"></script>
	<!-- 제이쿼리 CDN -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>

	<!-- 관리자 페이지 공통 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/style.css" />

	<!-- 관리자 페이지 오류 관리 비밀번호 초기화  css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/passwordInitialization.css" />
<script>
	$(function(){
		// 초기화 / 사번
		var memNo = "";
		// 초기화할 member 검색 버튼 클릭 이벤트
		$('#init-search-btn').click(function(){
			memNo = $(this).prev().val();
			if(memNo!=''){
				alert(memNo);
				// $.ajax();
				$(this).next().show();
			}else{
				alert('검색할 사번을 입력해주세요');
			}
		});
		
		// 초기화 버튼 클릭 이벤트
		$('#init-btn').click(function(){
			
			if(confirm('해당 사원의 비밀번호를 초기화하기겠습니까?')){
				alert(memNo);
				// $.ajax();
			}
		});
		
	})
</script>
</head>
<body>
	<div id="wrap">
		<%@ include file ="../adminForm/header.jsp" %>
		
		<div id="contentsBox">
			<%@ include file ="../adminForm/sideNaviBar.jsp" %>
			
			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						오류 관리 <span>비밀번호 초기화</span>
    				</div>
    				<div id="TitleContents">
                        <!--여기서 각자 id 만드시면 됩니다-->                     
                        <div id="init-div">
                            <input type="text" class="float" placeholder="사번을 입력하세요"/> <button type="button" id="init-search-btn" class="agreeButtonType float"><i class="fas fa-search"></i></button>
                            <div id="selectMember">
	                            <table class="float">
	                                <tr>
	                                    <td>210130001</td>
	                                    <td>주다빈</td>
	                                    <td>사원</td>
	                                    <td>인사팀</td>
	                                </tr>
	                            </table>
	                            <button  type="button" id="init-btn" class="agreeButtonType float">비밀번호 초기화</button>
                            </div>
                        </div>
                        
                        <div id="log-div">
                            <div><form action="#" method="get"><input type="text" name="searchKeyword"/> <button class="agreeButtonType"><i class="fas fa-search"></i></button></from></div>
                            <hr>
                            <table>
                                <tr>
                                    <td>2021/01/30(토) 22:16</td>
                                    <td>주다빈 tazadab@gmail.com</td>
                                    <td>부서명</td>
                                    <td>000.000.00.000</td>
                                    <td><div class="successLog">로그인 성공</div></td>
                                </tr>
                                <tr>
                                    <td>2021/01/30(토) 22:16</td>
                                    <td>장효진 tazadab@gmail.com</td>
                                    <td>부서명</td>
                                    <td>000.000.00.000</td>
                                    <td><div class="failLog">로그인 실패</div></td>
                                </tr>
                                <tr>
                                    <td>2021/01/30(토) 22:16</td>
                                    <td>장효진 tazadab@gmail.com</td>
                                    <td>부서명</td>
                                    <td>000.000.00.000</td>
                                    <td>로그인 실패</td>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                </tr>
                            </table>
                            <hr>
                            <div id="pageNabi">1 2 3 4 5 ></div>
                        </div>
                        <!----------------------------------->
    				</div>
				</div>
			</div> 
		</div>
	</div>
	
	<script type='text/javascript'>	      
	      var $errorList = $('#errorList');         
	      var $error = $('#error');
	      
	      /* 오류 관리일 때*/
	      $error.children().eq(2).children().attr('class','iArrow fas fa-angle-left');
          $errorList.css('height','72px');
          	      
	      $error.removeClass('hoverColor').addClass('click');
	      $errorList.removeClass('accordion');
	      $errorList.addClass('click');
	      $errorList.children().eq(0).children(":first").removeClass('hoverColor');
	</script>
</body>
</html>