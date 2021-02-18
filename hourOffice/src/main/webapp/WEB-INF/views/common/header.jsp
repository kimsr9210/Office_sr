<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header">
		<div id="headerLogo">
			<img id="logoImg"
				src="resources/images/logo4.png" />
		</div>
		<form action="/search" method="post">
			<ui id="headerNavi">
			<li><input type="text" name="keyword" id="searchBox" /></li>
			<li>
				<button type="submit" id="searchBtn">
					<i class="fas fa-search naviIcon"></i> <span id="searchTooltip">검색</span>
				</button>
			</li>
			<li><a href="#"><i class="fas fa-bell naviIcon"></i></a></li>
			<li><a id="myInfoHead" href="/mypage.ho"><i class="fas fa-user-circle naviIcon"></i></a><span id="myInfoTooltip">내정보</span></li>
			<li><a id="userLogout" href="/memberLogout.ho"><i
					class="fas fa-sign-out-alt naviIcon"></i></a> <span id="exitTooltip">로그아웃</span></li>
			<li id="welcomeName">${sessionScope.member.memName } 님, 환영합니다</li>
			</ui>
		</form>
	</div>
	
	<script>
		$(function(){
			$('#headerLogo').click(function(){
				var todayMon = new Date().getMonth()+1;
				location.replace('/main.ho?todayMon='+todayMon);
			});
		});
	</script>
</body>
</html>