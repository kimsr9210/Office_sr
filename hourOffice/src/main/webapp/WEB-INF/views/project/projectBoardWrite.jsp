<%@page import="kr.or.houroffice.project.model.vo.ProjectRequest"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectComment"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectMember"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectBoard"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="kr.or.houroffice.project.model.vo.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
</head>
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
	href="/resources/css/project/projectBoardWrite.css" />


<!-- TextArea 자동 높이 조절 CDN -->
<script
	src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
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
#boardWriteTitle{
	font-family: GongGothicLight;
	font-size: 1.2rem;
	font-weight: 100;
}
#codeTextBox {
	width: 100%;
	height: auto;
}

.codeWriteText {
	width: 90%;
	margin: 30px;
	font-family: "맑은 고딕";
	font-size: 0.875rem;
	resize: none;
	border: 0;
	outline: none;
	background-color: black;
	color: white;
	border: 2px solid red;
	overflow: scroll;
}

#dateInput {
	width: 100px;
	height: 100%;
	padding: 6px;
	border: 0;
	float: left;
	color: #808080; . codeYellow { font-weight : bolder;
	color: yellow;
	}
}

.codeGreen {
	font-weight: bolder;
	color: green;
}

.codeRed {
	font-weight: bolder;
	color: red;
}

.codeBlue {
	font-weight: bolder;
	color: blue;
}

.memberImg {
	width: 7%;
	height: 100%;
	float: left;
	color: #999999;
	font-size: 2rem;
	padding: 10px 2px 2px 20px;
}

.nc-img{
	display: block;
	width: 35px;
	height: 35px;
	border-radius: 50px;
	background-color: none;
}

.memberAllListImg {
	width: 10%;
	height: 100%;
	float: left;
	color: #999999;
	font-size: 2rem;
	padding: 10px 2px 2px 15px;
}

.inviteMemberImg {
	width: 10%;
	height: 100%;
	float: left;
	color: #999999;
	font-size: 2rem;
	padding: 10px 2px 2px 15px;
}
</style>

	<%
		Project p = (Project) request.getAttribute("project");
		ArrayList<Member> projectMemberList = (ArrayList<Member>) request.getAttribute("projectMemberList");
		ArrayList<ProjectBoard> boardList = (ArrayList<ProjectBoard>) request.getAttribute("boardList");
		ArrayList<Member> boardMemberList = (ArrayList<Member>) request.getAttribute("boardMemberList");
		ArrayList<ProjectMember> projectMgrList = (ArrayList<ProjectMember>) request.getAttribute("projectMgrList");
		ArrayList<ProjectComment> commentList = (ArrayList<ProjectComment>) request.getAttribute("commentList");
		ArrayList<Project> favoriteList = (ArrayList<Project>) request.getAttribute("favoriteList");
		Member m = (Member) session.getAttribute("member");
		ArrayList<Member> allMemberList = (ArrayList<Member>) request.getAttribute("allMemberList");
		ArrayList<ProjectRequest> requestList = (ArrayList<ProjectRequest>) request.getAttribute("requestList");

		boolean like = false;
		for (Project project : favoriteList) {
			if (project.getProNo() == p.getProNo()) {
				like = true;
			}
		}
	%>
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">

					<div id="projectBox">
						<div id="projectLeft">
							<div id="projectDetail">
								<div id="DetailTop">
									<p id="projectName">
										<span id="projectFavor">
										<%if(!like){ %>
										<i class="far fa-star"></i>
										<%}else{ %>
										<i class="fas fa-star likeBtn"></i>
										<%} %>
										</span>
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<input type="hidden" name="proSubject" value="<%=p.getProSubject()%>"/>
										<%=p.getProSubject() %>
                                        <%
                                        	for(ProjectMember pm : projectMgrList){
                                        		if(pm.getMemNo()==m.getMemNo()){
                                        %>
                                        <span id="projectSetting"><i class="fas fa-cog"></i></span>
                                        	<% } %>
                                        <% } %>
									</p>
									<div id="projectOption">
										<p class="optionBold">프로젝트 설정</p>
										<p id="projectExit" class="optionRegular">프로젝트 나가기</p>
										<p id="projectModify" class="optionRegular">프로젝트 수정</p>
										<p id="projectDelete" class="optionRegular">프로젝트 삭제</p>
										<hr>
										<p class="optionBold">프로젝트 관리자 설정</p>
                                        <p id="projectComplate" class="optionRegular">프로젝트 완료 설정</p>
										<hr>
										<p class="optionBold">프로젝트 번호</p>
										<p class="optionRegular"><%=p.getProNo() %></p>
									</div>
									<p id="projectExp"><%=p.getProExp() %></p>
								</div>
							</div>

							<!-- 게시물 작성 -->
							<br>
							<p id="boardWriteTitle">게시물 작성</p>
							<div id="boardWrite">
								<ul id="boardSelect">
									<li><span class="modeSelect"><i class="far fa-edit"></i> 글작성</span></li>
									<li><span class="modeSelect"><i
											class="fas fa-laptop-code"></i> 코드</span></li>
									<li><span class="modeSelect"><i
											class="fas fa-briefcase"></i> 일정</span></li>
									<li><span class="modeSelect"><i
											class="fas fa-briefcase"></i> 할일</span></li>
								</ul>
								<div id="boardWriteContents">

									<!-- 일반 글 작성일 때 -->
									<form action="/insertProjectBoard.ho" method="post"  enctype="multipart/form-data"> <input
											type="hidden" value="<%=p.getProNo() %>" name="proNo" /> <input
											type="hidden" value="${sessionScope.member.memNo }"
											name="memNo" />
										<div id="writeBox" class="boardAllStyle">
											<textarea id="postTextarea" class="boardWriteText" name="boardText"
												placeholder="내용을 입력해 주세요"></textarea>
											<img id="img1" class="imgUp"/>
											<div class="boardWriteBot">
												<div class="fileUploadBox">
													<input type="file" class="fileUpload" id="fileUpload1" name="file"/>
													<input type="file" class="imgUpload" id="imgUpload1" name="imgFile"/>
													<label for="fileUpload1" class="fileUploadImg"><i class="fas fa-paperclip"></i></label>
													<label for="imgUpload1" class="fileUploadImg"><i class="far fa-image"></i></label>
												</div>
												<div class="fileNameBox">
													<input text="text" class="fileName" readonly="readonly"/>
													<input text="text" class="fileSize" readonly="readonly"/>
												</div>
												<div class="boardUpload">
													<button type="submit" class="uploadBtn">올리기</button>
												</div>
											</div>
										</div>
									</form>

									<!-- 코드 일때 -->
									<form action="/insertProjectCode.ho" method="post" enctype="multipart/form-data">
										<input type="hidden" value="<%=p.getProNo() %>" name="proNo" />
										<input type="hidden" value="${sessionScope.member.memNo }" name="memNo" />
										<div id="codeBox" class="boardAllStyle">
										<div id="codeTextBox">
											<textarea id="codeText"class="codeWriteText" name="codeText"
												placeholder="코드을 입력해 주세요"></textarea>
											<textarea id="codeTextarea"class="boardWriteText" name="boardText"
												placeholder="내용을 입력해 주세요"></textarea>
	
												
												
												
										</div>
											<img id="img2" class="imgUp"/>
											<div class="boardWriteBot">
												<div class="fileUploadBox">
													<input type="file" class="fileUpload" id="fileUpload2" name="file"/>
													<input type="file" class="imgUpload" id="imgUpload2" name="imgFile"/>
													<label for="fileUpload2" class="fileUploadImg"><i class="fas fa-paperclip"></i></label>
													<label for="imgUpload2" class="fileUploadImg"><i class="far fa-image"></i></label>
												</div>
												<div class="fileNameBox">
													<input text="text" class="fileName" readonly="readonly"/>
													<input text="text" class="fileSize" readonly="readonly"/>
												</div>
												<div class="boardUpload">
													<button type="submit" class="uploadBtn">올리기</button>
												</div>
											</div>
										</div>
									</form>

									<!--일정 일 때-->
									<form action="/insertProjectPlan.ho" method="post">
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<div id="scheduleBox" class="boardAllStyle">
											<input type="text" class="boardWriteText" name="subject"
												placeholder="일정 제목을 입력해 주세요." />
											<div id="scheduleBoxTop">
												<i class="far fa-clock scheduleImg"></i>
												<input type="date" class="scheduleDate" name="startDate"/>
												<div id="line">~</div>
												<input type="date" class="scheduleDate" name="endDate"/>
											</div>
											<div id="scheduleMemoBox">
												<i class="far fa-sticky-note scheduleImg"></i> <input
													id="scheduleMemo" type="text" name="memo" placeholder="메모를 입력하세요"/>
											</div>
											<div class="boardWriteBot">
												<div class="boardUpload">
													<button type="submit" class="uploadBtn">올리기</button>
												</div>
											</div>
										</div>
									</form>

									<!--할일 일 때-->
									<form action="/insertProjectWork.ho" method="post">
										<input type="hidden" value="work" name="type" />
										<div id="workBox" class="boardAllStyle">
											<input type="text" class="boardWriteText" name="Title"
												placeholder="할일 제목을 입력해 주세요." />
											<div class="workList">
											<div id="dateInput">날짜 입력 : </div><input type="date" class="workDate" name="workDay" />
											</div>
											<!-- 얘가 여러개 -->
											<div class="workList">
												<span class="workDelete"><i class="fas fa-minus-circle"></i></span>
												 <input class="workTitle" type="text" placeholder="할일 입력" name="workCon" />
											</div>

											<div id="workAdd">
												<span class="workAddBtn"><i class="fas fa-plus-circle"></i></span>
												<input class="workTitle" type="text" placeholder="할일 추가" readonly="readonly" />
											</div>
											<div class="boardWriteBot">
												<div class="boardUpload">
													<button type="submit" class="uploadBtn">올리기</button>
												</div>
											</div>
										</div>
										<input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
									</form>

									<div id="workHidden" class="workList">
										<span class="workDelete"><i class="fas fa-minus-circle"></i></span>
											<input class="workTitle" type="text" placeholder="할일 입력" name="workCon" />
									</div>
								</div>

							</div>



						</div>
						<div id="projectRight">
                            <button id="backward"><i class="fas fa-chevron-left"></i> 이전 화면</button>
                            <button id="inviteBtn"><i class="fas fa-user-plus"></i> 초대하기</button>
                            <div id="memberListBox">
                                <div id="memberListTop">
                                    <span>전체 참여자</span><div id="memberAllList">전체보기</div>
                                </div>
                                <div id="memberListBot">
                                    
                                    <!-- 멤버 목록 여러개 -->
                                    <%for(Member member : projectMemberList) {%>
                                    <div class="memberList">
                                        <div>
                                       <%if(member.getMemProfile()!=null){ %>
                                        <img class="nc-img" src="/resources/images/profile/<%=member.getMemProfile()%>">
                                        <%}else{ %>
                                        <i class="fas fa-user-circle"></i>
                                        <%} %>
                                        </div>
                                        <div class="memberListName"><%=member.getMemName() %></div>
                                    </div>
                                    <%} %>
                                    
                                </div>
                                
                            </div>
                        </div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 새 프로젝트 박스 -->
    <div id="newProjectBox">
        <div id="newProjectHeader">
            프로젝트 수정<div id="newProjectExit"><i cl ass="fas fa-times"></i></div>
        </div>
        <form id="projectForm" action="/updateProject.ho" method="post">
            <input type="text" value="<%=p.getProSubject() %>" name="proSubject" id="newProjectSubject" />
            <textarea placeholder="설명 글을 입력할 수 있습니다." name="proExp" id="newProjectExplain" ><%=p.getProExp() %></textarea>
            <br><br>
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
            <input type="hidden" name="memNo" value="<%=p.getMemNo()%>"/>
            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
            <div class="projectBtnSize">
                <button type="submit" id="newProjectSubmitBtn">프로젝트 수정</button>
            </div>
        </form>
    </div>
    
    
    <!-- 프로젝트 멤버 추가 하기 -->
    <div id="inviteBox">
        <div id="inviteHeader">
            <span id="inviteExit"><i class="fas fa-times"></i></span>
            <div id="inviteName">초대하기</div>
        </div>
        <div id="inviteContents">
            
            <!--얘가 여러개 생겨남-->
			<%
				for(Member member : allMemberList){
					boolean except = false;
					boolean invite = false;
					for(Member proMember : projectMemberList){
						if(proMember.getMemNo()==member.getMemNo()){
							except = true;
						}
					}
					for(ProjectRequest pr : requestList){
						if(pr.getResponseMem()==member.getMemNo()){
							invite = true;
						}
					}
					if(except==false){
			%>            
            <div class="inviteMemberList">
                <div class="inviteMemberImg">
                <%if(member.getMemProfile()!=null){ %>
                <img class="nc-img" src="/resources/images/profile/<%=member.getMemProfile()%>">
                <%}else{ %>
                <i class="fas fa-user-circle"></i>
                <%} %>
                </div>
                <div class="inviteMemberInfo">
                    <div class="inviteMemberName"><%=member.getMemName() %></div>
                    <div class="inviteMemberPosition"><%=member.getMemPosition() %></div>
                </div>
                <div class="memberAdd">
                    <div class="memberAddBox"><%if(invite==false){ %>추가<%}else{ %>초대완료<%} %></div>
                    <input type="hidden" value="<%=member.getMemNo() %>"/>
                    <input type="hidden" value="<%=p.getProNo() %>"/>
                </div>
            </div>
            <%} %>
            <%} %>
        </div>
    </div>
    
    <!--참가자 목록 -->
    <div id="memberAllListBox">
        <div id="memberAllListHeader" class="displayNone">
            <span id="memberAllListExit"><i class="fas fa-times"></i></span>
            <div id="memberAllListName">프로젝트 참가자</div>
        </div>
        <div id="memberAllListContents">
            
            <!--얘가 여러개 생겨남-->
            <%for(Member member : projectMemberList) {%>
            <%
            	char sessionMgrYN = 'N';
            	char mgrYN = 'N';
            	for(ProjectMember pm : projectMgrList){
            		if(member.getMemNo()==pm.getMemNo()){
            			mgrYN = pm.getMgrYN();
            		}
            		if(pm.getMgrYN()=='Y'){
            			if(pm.getMemNo()==m.getMemNo()){
            				sessionMgrYN = 'Y';
            			}
            		}
            	}
            %>
            <div class="memberAllListLabel">
                <div class="memberAllListImg displayNone">
                <%if(member.getMemProfile()!=null){ %>
                <img class="nc-img" src="/resources/images/profile/<%=member.getMemProfile()%>">
                <%}else{ %>
                <i class="fas fa-user-circle"></i>
                <%} %>
                </div>
                <div class="memberAllListInfo displayNone">
                    <div class="memberAllListName"><%=member.getMemName() %></div>
                    <div class="memberAllListPosition"><%=member.getMemPosition() %></div>
                </div>
                <div class="memberAllListSet">
                <%if(mgrYN=='Y'){ %>
                    <div class="memberAllListSetAdminDis displayNone">관리자</div>
               	<%} %>
               	<%if(sessionMgrYN=='Y'||member.getMemNo()==m.getMemNo()){ %>
                    <div class="memberAllListSetAdmin"><i class="fas fa-ellipsis-v"></i>
                        <!-- 프로젝트 관리자만 보임 -->
                        <div class="memberAdminBox">
                        <%if(m.getMemNo()==member.getMemNo()){ %>
                            <div class="memberAdminList outProject">나가기</div>
                        <%} else{%>
                            <div class="memberAdminList outProjectMember">내보내기</div>
                            <input type="hidden" name="memNo" value="<%=member.getMemNo()%>"/>
                            <input type="hidden" name="" value="<%=member.getMemName() %>"/>
                        <%} %>
                        <%if(sessionMgrYN=='Y') {%>
                        <%if(mgrYN=='N'){ %>
                            <div class="memberAdminList adminSet">관리자 지정</div>
                            <input type="hidden" name="memNo" value="<%=member.getMemNo()%>"/>
                            <input type="hidden" name="" value="<%=member.getMemName() %>"/>
                        <%}else{%>
                            <div class="memberAdminList adminCancel">관리자 지정 해제</div>
                            <input type="hidden" name="memNo" value="<%=member.getMemNo()%>"/>
                            <input type="hidden" name="" value="<%=member.getMemName() %>"/>
                        <%} %>
                        <%} %>
                        </div>
                        <!--  -->
                    </div>
                 <%} %>
                </div>
            </div>
            <%} %>
            
        </div>
    </div>
        

	<!-- 자바 스크립트    -->
	<script>
		$(function(){

			$('#categoryProject').next().css('display', 'block');
			$('#categoryProject').next().css('height', '125px');
			$('#categoryProject').children().last().children().attr('class',
					'fas fa-chevron-left');
			
			//프로젝트 나가기
			$('#projectExit, .outProject').click(function(){
				var proNo = '<%=p.getProNo()%>';
				var proMemNo = '<%=p.getMemNo()%>';
				var memNo = '<%=m.getMemNo()%>'
				var result = window.confirm("해당 프로젝트에서 나가시겠습니까?");
				if(result){
					if(proMemNo==memNo){
						alert('프로젝트 생성자는 프로젝트를 나갈 수 없습니다');
					}else{
						$.ajax({
			            	url : "/updateProjectMemberExit.ho",
			            	data : {"proNo" : proNo, "memNo" : memNo},
			            	type : "post",
			            	success : function(result){
			            		if(result=="true"){
			            			alert("해당 프로젝트에서 나갔습니다.");
			            		}else{
			            			alert("해당 프로젝트 나가기에 실패하였습니다 \n지속적인 오류시 관리자에게 문의하세요");
			            		}
			            	},
			            	error : function(){
			            		console.log("프로젝트 나가기 ajax 통신 실패");
			            	}
			            });
						
					}
					location.replace('/projectAllList.ho');
				}
			});
			
			//프로젝트 삭제
			$('#projectDelete').click(function(){
				var proNo = '<%=p.getProNo()%>';
				var proMemNo = '<%=p.getMemNo()%>';
				var memNo = '<%=m.getMemNo()%>';
				var proSubject = '<%=p.getProSubject()%>'
				var result = window.confirm("해당 프로젝트를 삭제하시겠습니까?");
				if(result){
					if(!(proMemNo==memNo)){
						alert('프로젝트 생성자만 삭제할 수 있습니다.');
					}else{
						$.ajax({
			            	url : "/deleteProject.ho",
			            	data : {"proNo" : proNo},
			            	type : "post",
			            	success : function(result){
			            		if(result=="true"){
			            			alert("해당 프로젝트가 삭제되었습니다.")
			            		}else{
			            			alert("해당 프로젝트 삭제에 실패하였습니다 \n지속적인 오류시 관리자에게 문의하세요");
			            		}
			            	},
			            	error : function(){
			            		console.log("프로젝트 삭제 ajax 통신 실패");
			            	}
			            });
						location.replace('/projectAllList.ho');
					}
				}
			});
			
			//프로젝트 내보내기
			$('.outProjectMember').click(function(){
				var memNo = $(this).next().val();
				var memName = $(this).next().next().val();
				var proMemNo = '<%=p.getMemNo()%>';
				var proNo = '<%=p.getProNo()%>';
				var result = window.confirm("["+memName+"] 님을 해당 프로젝트에서 내보내시겠습니까?");
				if(memNo==proMemNo){
					alert('프로젝트 생성자는 내보낼 수 없습니다');
				}else{
					if(result){
						$.ajax({
				            url : "/updateProjectMemberExit.ho",
				            data : {"proNo" : proNo, "memNo" : memNo},
				            type : "post",
				            success : function(result){
				            	if(result=="true"){
				            		alert("["+memName+"] 님을 해당 프로젝트에서 내보냈습니다");
				            	}else{
				            		alert("참가자 내보내기에 실패하였습니다 \n지속적인 오류시 관리자에게 문의하세요");
				            	}
				            },
				            error : function(){
				            	console.log("참가자 내보내기 ajax 통신 실패");
				            }
				        });
					}
		    		location.reload();
				}
			});
			
			//관리자 지정
			$('.adminSet').click(function(){
				var memNo = $(this).next().val();
				var memName = $(this).next().next().val();
				var proMemNo = '<%=p.getMemNo()%>';
				var proNo = '<%=p.getProNo()%>';
				var result = window.confirm("["+memName+"] 님을 관리자로 설정하시겠습니까?");
				if(result){
					$.ajax({
				           url : "/updateProjectMgrSet.ho",
				           data : {"proNo" : proNo, "memNo" : memNo},
				           type : "post",
				           success : function(result){
				           	if(result=="true"){
				           		alert("["+memName+"] 님을 관리자로 설정하였습니다");
				           	}else{
				           		alert("관리자 권한 지정에 실패하였습니다 \n지속적인 오류시 관리자에게 문의하세요");
				           	}
				           },
				           error : function(){
				           	console.log("관리자 지정 ajax 통신 실패");
				           }
				       });
				}
		    	location.reload();
			});
			
			//관리자 지정 해제
			$('.adminCancel').click(function(){
				var memNo = $(this).next().val();
				var memName = $(this).next().next().val();
				var proMemNo = '<%=p.getMemNo()%>';
				var proNo = '<%=p.getProNo()%>';
				var result = window.confirm("["+memName+"] 님의 관리자 권한을 해제하시겠습니까?");
				if(memNo==proMemNo){
					alert('프로젝트 생성자는 관리자 권한을 해제할 수 없습니다');
				}else{
					if(result){
						$.ajax({
				            url : "/updateProjectMgrCancel.ho",
				            data : {"proNo" : proNo, "memNo" : memNo},
				            type : "post",
				            success : function(result){
				            	if(result=="true"){
				            		alert("["+memName+"] 님의 관리자 권한을 해제하였습니다");
				            	}else{
				            		alert("관리자 권한 해제에 실패하였습니다 \n지속적인 오류시 관리자에게 문의하세요");
				            	}
				            },
				            error : function(){
				            	console.log("관리자 지정 해제 ajax 통신 실패");
				            }
				        });
					}
		    		location.reload();
				}
			});
			//이미지 미리 보기
			$('#imgUpload1').on("change", handleImgFileSelectPost);
			$('#imgUpload2').on("change", handleImgFileSelectCode);
			
			function handleImgFileSelectPost(e) {
				var files = e.target.files;
				var filesArr = Array.prototype.slice.call(files);
				
				filesArr.forEach(function(f) {
					if(!f.type.match("image.*")){
						alert("확장자는 이미지 확장자만 가능합니다.");
						return;
					}
					
					sel_file = f;
					var reader = new FileReader();
					reader.onload = function(e){
						$('#img1').attr("src", e.target.result);
					}
					reader.readAsDataURL(f);
				})
			}
			
			function handleImgFileSelectCode(e) {
				var files = e.target.files;
				var filesArr = Array.prototype.slice.call(files);
				
				filesArr.forEach(function(f) {
					sel_file = f;
					var reader = new FileReader();
					reader.onload = function(e){
						$('#img2').attr("src", e.target.result);
					}
					reader.readAsDataURL(f);
				})
			}
			
			// 파일 올릴 시 이름 보여주기
			$('.fileUpload').change(function() {
				$(this).parent().next().children().eq(0).val("첨부파일 : "+$(this)[0].files[0].name);
				$(this).parent().next().children().eq(1).val($(this)[0].files[0].size + ' KB');
			});
			
			//프로젝트 완료처리
    		$('#projectComplate').click(function(){
    			var compYN = "<%=p.getCompYN()%>";
    			var proNo = "<%=p.getProNo()%>";
    			console.log(compYN);
    			if(compYN=='Y'){
    				var result = window.confirm("현재 프로젝트는 완료되어있습니다\n미완료 처리 하시겠습니까?");
    				if(result){
    					$.ajax({
    			            url : "/updateProjectComplate.ho",
    			            data : {"proNo" : proNo, "compYN" : compYN},
    			            type : "post",
    			            success : function(result){
    			            	if(result=="true"){
    			            		alert("현재 프로젝트는 미완료 처리 되었습니다");
    			            	}else{
    			            		alert("현재 프로젝트의 완료처리가 실패하였습니다\n지속적인 오류시 관리자에게 문의하세요");
    			            	}
    			            },
    			            error : function(){
    			            	console.log("프로젝트 완료처리 ajax 통신 실패");
    			            }
    			        });
    					
    					location.reload();
    				}
    			}else{
    				var result = window.confirm("현재 프로젝트를 완료처리 하시겠습니까?");
    				if(result){
    					$.ajax({
    			            url : "/updateProjectComplate.ho",
    			            data : {"proNo" : proNo, "compYN" : compYN},
    			            type : "post",
    			            success : function(result){
    			            	if(result=="true"){
    			            		alert("현재 프로젝트는 완료처리 되었습니다");
    			            	}else{
    			            		alert("현재 프로젝트의 완료처리에 실패하였습니다\n지속적인 오류시 관리자에게 문의하세요");
    			            	}
    			            },
    			            error : function(){
    			            	console.log("프로젝트 완료처리 ajax 통신 실패");
    			            }
    			        });
    					location.reload();
    				}
    			}
    		});
			
    		/* 멤버 추가 누를 시 */
    		$('.memberAddBox').click(function() {
    			var memNo = $(this).next().val();
    			var proNo = $(this).next().next().val();
    			if ($(this).text() == '초대완료') {
    				$.ajax({
    		            url : "/deleteProjectRequest.ho",
    		            data : {"memNo" : memNo, "proNo" : proNo},
    		            type : "post",
    		            success : function(result){
    		            	if(result=="true"){
			            	}else{
			            	}
    		            },
    		            error : function(){
    		            	console.log("프로젝트 초대하기 ajax 통신 실패");
    		            }
    		        });
    				$(this).html('추가');
    				$(this).css('background-color', 'white');
    				$(this).css('border-color', '#808080');
    				$(this).css('color', '#808080');
    			} else {
    				$.ajax({
    		            url : "/insertProjectRequest.ho",
    		            data : {"memNo" : memNo, "proNo" : proNo},
    		            type : "post",
    		            success : function(result){
    		            	if(result=="true"){
			            	}else{
			            	}
    		            },
    		            error : function(){
    		            	console.log("프로젝트 초대하기 ajax 통신 실패");
    		            }
    		        });
    				$(this).css('background-color', '#1D9F8E');
    				$(this).css('color', 'white');
    				$(this).css('border-color', '#1D9F8E');
    				$(this).text('초대완료');
    			}
    		});
    		
    		//이전화면 누르기
    		$('#backward').click(function(){
    			history.back(-1);
    		});
		});
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script type="text/javascript" src="/resources/js/projectBoardWrite.js"></script>
</body>
</html>