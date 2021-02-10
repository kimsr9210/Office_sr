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

#pic {
	border: 1px black solid;
	width: 180px;
	height: 180px;
	margin: 0px auto;
}

.imgbox {
	background: none;
}
</style>

<body>

	<%Member m = (Member)session.getAttribute("member");%>
	<%MemDept md = (MemDept)session.getAttribute("MemDept");%>
	
	
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">



					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						내인사정보
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->


<table>
        <tr>
            <th rowspan="5" class="imgbox"><div src="" id="pic"></div></th>
            <th>이름</th>
            <td><%=m.getMemName() %></td>
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
            <td><%=m.getDeptName() %></td>
        </tr>
        <tr>
            
            <th>이메일</th>
            <td><%=m.getMemEmail() %></td>
            <th>내선번호</th>
            <td><%=m.getMemTell() %></td>
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
            <th>자격증</th>
            <th>자격번호</th>
            <th>발급처</th>
            <th>등급</th>
            <th>취득일</th>
        </tr>
        <tr>
            <td>1</td>
            <td>1</td>
            <td>1</td>
            <td>1</td>
            <td></td>
        </tr>
    </table><br><br>

    <table>
        <tr>
            <th>근무처</th>
            <th>직위</th>
            <th>담당업무</th>
            <th>입사일</th>
            <th>퇴사일</th>
        </tr>
        <tr>
            <td>1</td>
            <td>1</td>
            <td>1</td>
            <td>1</td>
            <td>1</td>
        </tr>
    </table>

						<!----------------------------------->
					</div>

				</div>
			</div>
		</div>

		<!-- 자바 스크립트    -->
		<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>

	</div>
</body>
</html>


</body>
</html>