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
	<!-- 관리자 페이지 헤더 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/header.css" />
</head>
<body>
	<div id="header">
		<span id="headerClock"></span>
		<div id="headerMember" >
			<a href="/memberLoginPage.ho" class="aStyle" style="color:white;"><i class="headerIcon fas fa-door-open fa-2x"></i></a>
			<i class="headerIcon fas fa-user-circle fa-2x"></i>
			<span id="headerText">땡땡팀, 누구님 환영합니다.</span>
		</div>
	</div>
	
	<script type='text/javascript'>	    
		setInterval(function(){             
	        var date = new Date();
	        var year = date.getYear();
	        var month = date.getMonth()+1;
	        var clockDate = (date.getDate()<10) ? "0"+date.getDate() : date.getDate();
	        var day = date.getDay();
	        var week = ['일','월','화','수','목','금','토'];
	        var hours = (date.getHours()<10) ? "0"+date.getHours() : date.getHours();
	        var minutes = (date.getMinutes()<10) ? "0"+date.getMinutes() : date.getMinutes();
	        var seconds = (date.getSeconds()<10) ? "0"+date.getSeconds() : date.getSeconds();
	        document.getElementById('headerClock').innerHTML = (year+1900) + "년 " + ((month<10)? "0"+month : month) + "월 " + clockDate + "일 " +week[day] +"요일 " + ((hours<13)? "오전 "+hours : "오후 "+hours) + "시 " + minutes + "분 " + seconds + "초";
	    },1000);
	</script>
</body>
</html>