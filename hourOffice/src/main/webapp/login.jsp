<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/login/login.css" />

<!-- JSTL:C -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>

<body>
<c:choose>
	<c:when test="${empty sessionScope.member }">
		<form id="form" action="/memberLogin.ho" method="post">
	        <p><img src="resources/images/logo4.png" width="300"/></p>
	        <br><br>
	        <input type="text" name="memNo" class="in-type" placeholder="계정"><br>
	        <input type="password" name="memPwd" class="in-type" placeholder="비밀번호"><br>
	        <input class="log-type" type="submit" value="로그인">
	    </form>
	</c:when>
	<c:otherwise>
		<script>
			var todayMon = new Date().getMonth()+1;
			location.replace('/main.ho?todayMon='+todayMon);
		</script>
	</c:otherwise>
</c:choose>
	
</body></html>