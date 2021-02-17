<%@page import="kr.or.houroffice.project.model.vo.ProjectRequest"%>
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
<style>
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
	.progressBox{
		background-color: #F5A887;
	}
	.publicBox{
		background-color: #86B0BF;
	}
	.complateBox{
		background-color: #9F9DA6;
	}
	#TitleName{
		font-family: GongGothicMedium;
	}
	.pro_font{
		font-family: GongGothicLight;
		font-size: 1rem;
	}
	.requestBox{
		width: 180px;
		height: 150px;
		background-color: #214252;
		margin: 5px;
		border-radius: 10px;
		float: left;
		color:white;
	}
	
	/* 프로젝트 별 상세 내용 */
.requestSubject {
	padding: 10px;
	color: white;
	text-decoration: none;
}

.requestMember {
	color: white;
	padding: 0px 10px 10px 10px;
	width: 80%;
	float: left;
}
.requestBtnBox{
	color: white;
	padding: 40px 10px 10px 10px;
	width: 100%;
	float: left;
}


.joinBtn{
	width: 48%;
	text-align: center;
	cursor: pointer;
	padding: 10px;
	float: left;
	border-radius: 5px;
	background-color: #1D9F8E; 
	font-weight: bolder;
}

.refusalBtn{
	width: 48%;
	padding: 10px;
	text-align: center;
	cursor: pointer;
	float: right;
	border-radius: 5px;
	background-color: #FF6363; 
	font-weight: bolder;
}
.joinBtn:hover{
	background-color: #198C7D; 
}

.refusalBtn:hover{
	background-color: #F25E5E; 
}
</style>
<%
	ArrayList<Project> myList = (ArrayList<Project>)request.getAttribute("myList");
	ArrayList<Project> publicList = (ArrayList<Project>)request.getAttribute("publicList");
	ArrayList<Project> favoriteList = (ArrayList<Project>)request.getAttribute("favoriteList");
	ArrayList<Project> requestList = (ArrayList<Project>)request.getAttribute("requestList");
	ArrayList<Member> allMemberList = (ArrayList<Member>)request.getAttribute("allMemberList");
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
						프로젝트 즐겨찾기
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						<div class="pro_font">프로젝트 즐겨찾기</div>
						<div id="newProjectBtn">+ 새 프로젝트</div>

						<div class="projectList">
							<!-- 얘가 여러개 생겨남 -->
						<%if(!favoriteList.isEmpty()){ %>
							<%for(Project p : favoriteList) {%>
							
							<%
								boolean like = false;
								for(Project project : favoriteList){
									if(project.getProNo()==p.getProNo()){
										like = true;
									}
								}
								
							%>
								
								<form action="/projectDetail.ho" method="post">
								<%if(p.getCompYN()=='Y'){ %>
								<div class="projectBox complateBox">
								<%} else if(p.getPublicYN()=='Y'){%>
								<div class="projectBox publicBox">
								<%}else{ %>
								<div class="projectBox progressBox">
								<%} %>
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
							<div class="noneProject">즐겨찾기에 등록된 프로젝트가 없습니다</div>
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
			<input type="hidden" name="memNo" value=""/>
			<div class="projectBtnSize">
				<button type="submit" id="newProjectSubmitBtn">프로젝트 생성</button>
			</div>
		</form>
	</div>

	<!-- 자바 스크립트    -->
	<script>
		$(function(){
			$('#categoryProject').next().css('display', 'block');
			$('#categoryProject').next().css('height', '125px');
			$('#categoryProject').children().last().children().attr('class',
					'fas fa-chevron-left');

			$('#categoryProject').next().children().eq(4).children().css('font-weight',
					'800');
			$('#categoryProject').next().children().eq(4).children().css('color',
					'#ffcc29');

			//프로젝트 참가하기 버튼 누를 시
			$('.joinBtn').click(function(){
				var joinForm = $(this).next();
				var result = window.confirm("해당 프로젝트에 참가하시겠습니까?");
				if(result){
					joinForm.submit();
				}
			});
			
			//프로젝트 거절하기 버튼 누를 시
			$('.refusalBtn').click(function(){
				var refusalForm = $(this).next();
				var result = window.confirm("해당 프로젝트의 참가를 거절하시겠습니까?");
				if(result){
					refusalForm.submit();
				}
			});
			
			
		});
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script type="text/javascript" src="/resources/js/projectAllList.js"></script>
</body>
</html>