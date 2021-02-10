<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="kr.or.houroffice.board.model.vo.PartBoard"%>
<%@page import="java.util.ArrayList"%>
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
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- 게시판 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/board.css" />
	<!-- CSS 테이블만 있는 것 -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/userBoard.css" />

<style>
	th{
		
		background-color:#D2E6E6;
	}
</style>
	
</head>
<body>
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp" %>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						${list[0].deptName } 게시판
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						<span><a href="/writePostPartBoard.ho?deptCode=${list[0].deptCode }"><i class="fas fa-feather-alt i-icon"></i> 새글쓰기</a></span>
						<table>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                            </tr>
                    <% SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd."); %>
                    <% Date toDay = new Date(System.currentTimeMillis()); // 현재 날짜 구하기 %>
                  	<% ArrayList<PartBoard> list = (ArrayList<PartBoard>)request.getAttribute("list"); %>
                  	<% if(!list.isEmpty()){ %>
                  	<% for(PartBoard pb : list){ %>
                            <tr>
                                <td><%=pb.getPartNo() %></td>
                                <td><div><a href="/partBoard.ho?deptCode=${list[0].deptCode }&partNo=<%=pb.getPartNo()%>"><%=pb.getPartContent() %></a></div></td>
                                <td><%=pb.getPartWriter() %></td>
                    <% if(format.format(pb.getPartDate()).equals(format.format(toDay))){ %>
                    <% format = new SimpleDateFormat("hh:mm"); %>
                    			<td><%=format.format(pb.getPartDate()) %></td>
					<% }else{ %>
								<td><%=format.format(pb.getPartDate()) %></td>
                    <% } %>
                            </tr>
                    <% } %>
                    <% } %>
                        </table>
                        
                        <div id="pageNavi">< 1 2 3 4 5 ></div>
                        <div id="search-div">
                            <form action="#" method="get">
                            <select name="searchType">
                                <option value="both">제목+내용</option>
                                <option value="notName">제목</option>
                                <option value="notContent">내용</option>
                            </select>
                            <input type="text" name="text"/>
                            <button><i class="fas fa-search i-icon"></i></button>
                            </form>
                        </div>
						
						
						
						
						
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>

	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>
</body>
</html>