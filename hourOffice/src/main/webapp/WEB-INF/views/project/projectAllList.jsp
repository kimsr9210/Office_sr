<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="kr.or.houroffice.project.model.vo.Project"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
<!-- 폰트어썸 -->
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/header&sideNavi.css" />
<link rel="stylesheet" type="text/css"
	href="/resources/css/project/projectAllList.css" />
</head>
<body>
<%
	ArrayList<Project> myList = (ArrayList<Project>)request.getAttribute("myList");
	ArrayList<Project> publicList = (ArrayList<Project>)request.getAttribute("publicList");
	ArrayList<Project> favoriteList = (ArrayList<Project>)request.getAttribute("favoriteList");
	Member m = (Member)session.getAttribute("member");
%>

	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						프로젝트 전체 목록
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						<div>참여 프로젝트</div>
						<div id="newProjectBtn">+ 새 프로젝트</div>

						<div class="projectList">

							<!-- 얘가 여러개 생겨남 -->
						<%if(!myList.isEmpty()){ %>
							<%for(Project p : myList) {%>
							
							<%
								boolean like = false;
								for(Project project : favoriteList){
									if(project.getProNo()==p.getProNo()){
										like = true;
									}
								}
								
							%>
								<%if(p.getCompYN()=='N'){ %>
								<form action="/projectDetail.ho" method="post">
								<div class="projectBox">
									<a>
										<div class="projectSubject"><input class="projectDetailBtn" type="submit" value="<%=p.getProSubject() %>"/></div>
										<div class="projectMember"><%=p.getMemCount() %>명 참가중</div>
									</a>
									<div class="projectLike">
									<%if(!like){ %>
										<i class="far fa-star"></i>
									<%}else{ %>
										<i class="fas fa-star likeBtn"></i>
									<%} %>
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<input type="hidden" name="proSubject" value="<%=p.getProSubject()%>"/>
    									<input type="hidden" name="boardType" value="post"/>
									</div>
								</div>
								</form>
								<%} %>
							<%} %>
						<%}else{ %>
							<div class="noneProject">참여하고 있는 프로젝트가 없습니다</div>
						<%} %>
						</div>


						<br>
						<div class="projectClass">공개 프로젝트</div>
						<div class="projectList">
						<%if(!publicList.isEmpty()){ %>
							<%for(Project p : publicList) {%>
							<%
								boolean like = false;
								for(Project project : favoriteList){
									if(project.getProNo()==p.getProNo()){
										like = true;
									}
								}
								
							%>
								<form action="/projectDetail.ho" method="post">
								<div class="projectBox">
									<a>
										<div class="projectSubject"><input class="projectDetailBtn" type="submit" value="<%=p.getProSubject() %>"/></div>
										<div class="projectMember"><%=p.getMemCount() %>명 참가중</div>
									</a>
									<div class="projectLike">
									<%if(!like){ %>
										<i class="far fa-star"></i>
									<%}else{ %>
										<i class="fas fa-star likeBtn"></i>
									<%} %>
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<input type="hidden" name="proSubject" value="<%=p.getProSubject()%>"/>
    									<input type="hidden" name="boardType" value="post"/>
									</div>
								</div>
								</form>
							<%} %>
						<%}else{ %>
							<div class="noneProject">공개 프로젝트가 없습니다</div>
						<%} %>

						</div>

						<br>
						<div class="projectClass">완료 프로젝트</div>
						<div class="projectList">
						
						
						<%if(!myList.isEmpty()){ %>
						
							<%int count=0; %>
							<%for(Project p : myList) {%>
							<%
								boolean like = false;
								for(Project project : favoriteList){
									if(project.getProNo()==p.getProNo()){
										like = true;
									}
								}
								
							%>
								<%if(p.getCompYN()=='Y'){ %>
								<%count++; %>
								<div class="projectBox">
								<form action="/projectDetail.ho" method="post">
									<a href="#">
										<div class="projectSubject"><input class="projectDetailBtn" type="submit" value="<%=p.getProSubject() %>"/></div>
										<div class="projectMember"><%=p.getMemCount() %>명 참가중</div>
									</a>
									<div class="projectLike">
										<%if(!like){ %>
										<i class="far fa-star"></i>
										<%}else{ %>
											<i class="fas fa-star likeBtn"></i>
										<%} %>
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<input type="hidden" name="proSubject" value="<%=p.getProSubject()%>"/>
    									<input type="hidden" name="boardType" value="post"/>
									</div>
								</form>
								</div>
								<%} %>
							<%} %>
							<%if(count==0){ %>
								<div class="noneProject">완료된 프로젝트가 없습니다</div>
							<%} %>
						<%} %>
							
							
						</div>
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 새 프로젝트 박스 -->
	<div id="newProjectBox">
		<div id="newProjectHeader">
			프로젝트 만들기
			<div id="newProjectExit">
				<i class="fas fa-times"></i>
			</div>
		</div>
		<form id="projectForm" action="/createProject.ho" method="get">
			<input type="text" placeholder="프로젝트 명을 입력하세요" name="proSubject"
				id="newProjectSubject" />
			<textarea placeholder="설명 글을 입력할 수 있습니다." name="proExp"
				id="newProjectExplain"></textarea>
			<br>
			<br>
			<div id="newProjectOption">옵션 설정</div>
			<div class="optionList">
				<section class="setting st-login">
				<p>
					<i class="fas fa-check"></i> 공개 프로젝트 여부 <label class="btn-switch">
						<input type="checkbox" name="publicYN" id="public_check" value="on"/> <i
						class="slide-object"></i>
					</label>
				</p>
				</section>
			</div>
			<input type="hidden" name="publicYN" id="public_check_hidden" value="off"/>
			<br>
			<br>
			<div class="projectBtnSize" id="projectInviteMember">
				<i class="fas fa-user-plus"></i> 초대하기
			</div>
			<input type="hidden" name="memNo" value=""/>
			<div class="projectBtnSize">
				<button type="submit" id="newProjectSubmitBtn">프로젝트 생성</button>
			</div>
		</form>
	</div>

	<!-- 멤버 추가 박스 Ajax로 하기 -->
	<div id="inviteBox">
		<div id="inviteHeader">
			<i id="inviteExit" class="fas fa-arrow-left"></i>
			<div id="inviteName">초대하기</div>
		</div>
		<div id="inviteSearchBox">
			<form id="memberSearchForm" action="#" method="get">
				<input id="memberSearch" type="text" name="memberNo"
					placeholder=" 프로젝트를 함께할 친구를 찾아보세요!" /><i id="memberSearchImg"
					class="fas fa-search"></i>
			</form>
		</div>
		<div id="inviteContents">

			<!--얘가 여러개 생겨남-->
			<div class="inviteMemberList">
				<div class="memberImg">
					<i class="fas fa-user-circle"></i>
				</div>
				<div class="memberInfo">
					<div class="memberName">이름</div>
					<div class="memberPosition">직급</div>
				</div>
				<div class="memberAdd">
					<div class="memberAddBox">+&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추가</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 자바 스크립트    -->
	<script>
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script type="text/javascript" src="/resources/js/projectAllList.js"></script>
</body>
</html>