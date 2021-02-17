<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.houroffice.personnel.model.vo.MemDept"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 폰트어썸 -->
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/header&sideNavi.css" />

</head>

<style>
table {
	width: 100%;
	text-align: center;
}

tr {
	height: 40px;
}

th {
	width: 200px;
	background: #eee;
}

.pic {
	border: 1px black solid;
	width: 160px;
	height: 180px;
	margin: 0px auto;
}

.imgbox {
	background: none;
}

@font-face {
    font-family: 'GongGothicMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'GongGothicLight';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

#title1{
font-family: GongGothicMedium;
font-size:1.5rem;
}

#title2{
font-family: GongGothicLight;
}

</style>

<body>

	<%
		Member m = (Member)session.getAttribute("member");
		ArrayList<MemDept> list = (ArrayList<MemDept>) request.getAttribute("memDept");
		//MemDept md  = (MemDept)request.getAttribute("memDept");
	%>
	
	
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">



					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
                        <span id="title1">인사관리</span>
						<span id="title2">&nbsp;&nbsp; 내인사정보</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->


<table>
        <tr>
            <th rowspan="5" class="imgbox">
            
            <div class="pic"><img src="/resources/images/profile/<%=m.getMemProfile()%>" class="pic"></div></th>
            <th>이름</th>
            <td width="25%"><%=m.getMemName() %></td>
            <th>사번</th>
            <td><%=m.getMemNo() %></td>
        </tr>
        <tr>
            
            <th>생년월일</th>
            <td><%=m.getMemBirth()%></td>
            <th>직위</th>
            <td><%=m.getMemPosition() %></td>
        </tr>
        <tr>
            
            <th>연락처</th>
            <td><%=m.getMemPhone() %></td>
            <th>부서명</th>
            <td><%= m.getDeptName() ==  null ? "" : m.getDeptName() %></td>
        </tr>
        <tr>
            
            <th>이메일</th>
            <td><%= m.getMemEmail() ==  null ? "" : m.getMemEmail() %></td>
            <th>내선번호</th>
            <td><%= m.getMemTell() ==  null ? "" : m.getMemTell() %></td>
        </tr>
        <tr>
            
            <th>주소</th>
            <td id="add"><%=m.getMemAddress() %></td>
            <th>병역</th>
            <td>군필</td>
        </tr>
    </table><br><br>
    
    <table>
        <tr>
            <th>학교명</th>
            <th>입학일</th>
            <th>졸업일</th>
            <th>전공명</th>
            <th>졸업여부</th>
        </tr>
        <%for(MemDept md : list){ %>
        <tr>
            <td><%= md.getAcaSchoolName() ==  null ? "" : md.getAcaSchoolName() %></td>
            <td><%= md.getAcaEnrollDate() ==  null ? "" : md.getAcaEnrollDate().substring(0,10) %></td>
            <td><%= md.getAcaGradDate() ==  null ? "" : md.getAcaGradDate().substring(0,10) %></td>
            <td><%= md.getAcaMajorName() ==  null ? "" : md.getAcaMajorName() %></td>
            <td><%= md.getAcaGrad() ==  null ? "" : md.getAcaGrad() %></td>
        </tr>
        <%} %>
    </table><br><br>

    <table>
        <tr>
            <th>자격증</th>
            <th>자격번호</th>
            <th>발급처</th>
            <th>등급</th>
            <th>취득일</th>
        </tr>
        <%for(MemDept md : list){ %>
        <tr>
            <td><%=md.getLicName() %></td>
            <td>12345678</td>
            <td><%=md.getLicOrigin() %></td>
            <td>A</td>
            <td><%=md.getLicDate()%></td>
        </tr>
        <%} %>
    </table><br><br>

    <table>
        <tr>
            <th>근무처</th>
            <th>직위</th>
            <th>담당업무</th>
            <th>입사일</th>
            <th>퇴사일</th>
        </tr>
         <%for(MemDept md : list){ %>
        <tr>
            <td><%=md.getCarPlace() %></td>
            <td><%=md.getCarPosition() %></td>
            <td><%=md.getCarContent() %></td>
            <td><%=md.getCarJoinDate() %></td>
            <td><%=md.getCarResignDate() %></td>
        </tr>
        <%} %>
    </table><br>

						<!----------------------------------->
					</div>

				</div>
			</div>
		</div>

		<!-- 자바 스크립트    -->
		<script>
			$(function(){
				$('#categoryMypage').next().css('display','block');
				$('#categoryMypage').next().css('height','150px');
				$('#categoryMypage').children().last().children().attr('class','fas fa-chevron-left');
				
				$('#categoryMypage').next().children().eq(2).children().css('font-weight','800');
				$('#categoryMypage').next().children().eq(2).children().css('color','#ffcc29');
			})
		</script>
		<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>

</body>
</html>