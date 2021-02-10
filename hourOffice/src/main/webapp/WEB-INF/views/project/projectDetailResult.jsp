<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- jquery CDN -->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<form id="form" action="/projectDetail.ho" method="get">
<input type="hidden" name="proNo" value="${requestScope.proNo }"/>
<input type="hidden" name="boardType" value="${requestScope.boardType }"/>
</form>


<script>
		alert('${msg}');
		$('#form').submit();
</script>
</body>
</html>