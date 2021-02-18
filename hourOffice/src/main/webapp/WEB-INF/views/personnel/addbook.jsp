<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
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
.sbbtn{background-color:#1D9F8E; color:white; border: 0; width: 50px; height: 25px;}
               
tr{height: 30px;}
select{height: 25px;}
#search{height: 25px;}

/*페이지 네비 css*/
#page-navi {
    height: 30px;
    margin-top: 50px;
    margin-bottom: 50px;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.page-list {
    width: 30px;
    height: 30px;
    border: 1px solid darkgray;
    text-align: center;
}
.page-link {
    width: 100%;
    height: 100%;
    line-height: 28px;
    display: inline-block;
}

.page-link:hover{
    background: #eaeaea;
}

.pic{width: 45px;
    height: 45px;
    border-radius:50%}
    
    
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
		Member sessionMember = (Member)session.getAttribute("member");
	
	%>

	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">



					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						<span id="title1">인사정보</span>
                        <span id="title2">&nbsp;&nbsp; 사내주소록</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->

</div>
                    <div id="TitleContents">
                        <!--여기서 각자 id 만드시면 됩니다-->
                        
        <table width="100%" style="margin:auto;">
                            
        <tr style="text-align:right;">
            <td>
                <form action="/addbook.ho" method="get">
                    <select name="selectBox">
                    	<option value="" selected>전체</option>
                        <option value="name">이름</option>
                        <option value="dept">부서</option>
                        <option value="position">직책</option>
                    </select>
                    <!--  value="<%=request.getParameter("search")%>" -->
                    <input type="text" id="search" name="searchText" />
                    <input type="submit" class="sbbtn" name="searchBtn" value="검색" style="border-radius: 5px;"/>
                </form>
            </td>
        </tr>
    </table>
    <br><br>

    <table border="1px" width="100%" style="text-align:center; margin:auto; border-collapse:collapse;">
        <tr style="background-color:#1D9F8E; color:white;">
  			<th>순서</th>
            <th>프로필</th>
            <th>이름</th>
            <th>소속부서</th>
            <th>직급</th>
            <th>내선번호</th>
            <th>휴대폰</th>
            <th>이메일</th>
        </tr>

        <!-- 반복문 for 작성 -->
        <!-- 탈퇴자는 리스트에서 안보이게 만들어야됨 -->
        <c:forEach var="result" items="${requestScope.result.list}">
        <tr>
        	<td>${result.rnum}</td>
            <td><img src="/resources/images/profile/${result.memProfile}" class="pic"></td>
            <td>${result.memName}</td>
            <td>${result.deptName}</td>
            <td>${result.memPosition}</td>
            <td>${result.memTell}</td>
            <td>${result.memPhone}</td>
            <td><a href="/transferMail.ho?mailNo=4">${result.memEmail}</a></td>

        </tr>
        </c:forEach>


    </table><br>
    
    <ul id="page-navi">${requestScope.result.pageNavi}</ul>            


						<!----------------------------------->
					</div>

				</div>
			</div>
		</div>

		<!-- 자바 스크립트    -->
		<script>
		$('#categoryMypage').next().css('display','block');
		$('#categoryMypage').next().css('height','150px');
		$('#categoryMypage').children().last().children().attr('class','fas fa-chevron-left');
		
		$('#categoryMypage').next().children().eq(4).children().css('font-weight','800');
		$('#categoryMypage').next().children().eq(4).children().css('color','#ffcc29');
		</script>
		<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>

</body>
</html>
