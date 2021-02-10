<%@page import="kr.or.houroffice.project.model.vo.ProjectCode"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectComment"%>
<%@page import="org.springframework.web.bind.annotation.SessionAttribute"%>
<%@page import="javax.websocket.Session"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectBoard"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectMember"%>
<%@page import="java.util.ArrayList"%>
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
	href="/resources/css/project/projectDetail.css" />
	
	
<!-- TextArea 자동 높이 조절 CDN -->
<script src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
<body>
<style>
	.imgUp{
		max-width: 90%;
		margin: 0px 30px 0px 30px;
		height: auto;
	}
	
.codeTextBox {
	width: 90%;
	margin: 30px;
	font-family: "맑은 고딕";
	font-size: 0.875rem;
	resize: none;
	border: 0;
	outline: none;
	background-color: #272822;
	color: white;
	border: 2px solid red;
}

.codeTextBox>*{
	font-size: 1rem;
	color: white;
}

.codeYellow{
	font-weight: bolder;
	color: #f2c13f;
}

.codeGreen{
	font-weight: bolder;
	color: green;
}

.codeRed{
	font-weight: bolder;
	color: #f86634;
}

.codeBlue{
	font-weight: bolder;
	color: #36bcfc;
}
</style>
    		

<%
	Project p = (Project)request.getAttribute("project");
	ArrayList<Member> projectMemberList = (ArrayList<Member>)request.getAttribute("projectMemberList");
	ArrayList<ProjectBoard> boardList = (ArrayList<ProjectBoard>)request.getAttribute("boardList");
	ArrayList<Member> boardMemberList = (ArrayList<Member>)request.getAttribute("boardMemberList");
	ArrayList<ProjectMember> projectMgrList = (ArrayList<ProjectMember>)request.getAttribute("projectMgrList");
	ArrayList<ProjectComment> commentList = (ArrayList<ProjectComment>)request.getAttribute("commentList");
	ArrayList<Project> favoriteList = (ArrayList<Project>)request.getAttribute("favoriteList");
	ArrayList<ProjectCode> codeList = (ArrayList<ProjectCode>)request.getAttribute("codeList");
	String boardType = (String)request.getAttribute("boardType");
	Member m = (Member)session.getAttribute("member");
	
	
	boolean like = false;
	for(Project project : favoriteList){
		if(project.getProNo()==p.getProNo()){
			like = true;
		}
	}
%>



<style>
	<%if(boardType.equals("post")) {%>
	#projectNavi>li:nth-child(1)>a{
		color : #2CF2D8;
		font-weight: bolder;
		}
	<%}else if(boardType.equals("file")){%>
	#projectNavi>li:nth-child(2)>a{
		color : #2CF2D8;
		font-weight: bolder;
		}
	<%}else if(boardType.equals("code")){%>
	#projectNavi>li:nth-child(3)>a{
		color : #2CF2D8;
		font-weight: bolder;
		}
	<%}else if(boardType.equals("plan")){%>
	#projectNavi>li:nth-child(4)>a{
		color : #2CF2D8;
		font-weight: bolder;
		}
	<%}else if(boardType.equals("work")){%>
	#projectNavi>li:nth-child(5)>a{
		color : #2CF2D8;
		font-weight: bolder;
		}
	<%}%>	
</style>

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
                                        <%if(m.getMemNo()==p.getMemNo()){ %>
                                       	 	<p id="projectModify" class="optionRegular">프로젝트 수정</p>
                                        	<p id="projectDelete" class="optionRegular">프로젝트 삭제</p>
                                        <%} %>
                                        <hr>
                                        <p class="optionBold">프로젝트 관리자 설정</p>
                                        <p id="projectComplate" class="optionRegular">프로젝트 완료 설정</p>
                                        <hr>
                                        <p class="optionBold">프로젝트 번호</p>
                                        <p class="optionRegular"><%=p.getProNo() %></p>
                                    </div>
                                    <p id="projectExp">
                                        <%=p.getProExp() %>
                                    </p>
                                </div>
                                <div id="DetailBot">
                                    <ui id="projectNavi">
                                        <li><a id="postNavi"><i class="fas fa-list"></i> 일반</a></li>
                                        <li><a id="fileNavi"><i class="fas fa-file"></i> 파일</a></li>
                                        <li><a id="codeNavi"><i class="fas fa-laptop-code"></i> 코드</a></li>
                                        <li><a id="planNavi"><i class="far fa-calendar"></i> 일정</a></li>
                                        <li><a id="workNavi"><i class="fas fa-briefcase"></i> 할일</a></li>
                                        <li><a id="memberAllListNavi"><i class="fas fa-users"></i> 멤버</a></li>
                                    	
                                    </ui>
                                    <form action="/projectBoardWrite.ho" method="post">
                                    <input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
                                    <button id="newBoard">작성하기</button>
                                    </form>
                                </div>
                            </div>
                            
                            <!-- 게시물 여러개 -->
                            <%if(boardType.equals("post")){ %>
                            <%for(ProjectBoard pb : boardList){ %>
                            <%String name = ""; %>
                            <%for(Member member : boardMemberList){ %>
                            	<%if(member.getMemNo()==pb.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%} %>
                            <%} %>
                            <div class="boardBox">
                                <div class="boardInfo">
                                    <div class="memberImg"><i class="fas fa-user-circle"></i></div>
                                    <div class="memberInfo">
                                    
                                        <div class="memberName"><%=name %></div>
                                    
                                        <div class="boardTime"><%=pb.getBoardDate() %></div>
                                    </div>
                                    <!--관리자 일 때-->
                                    <%if(pb.getMemNo()==m.getMemNo()){ %>
                                    <div class="memberAdmin">
                                        <span class="boardSet"><i class="fas fa-ellipsis-v"></i></span>
                                        <div class="boardModifyBox">
                                            <div class="boardModifyList boardModify">게시물 수정</div>
                                            <div class="boardModifyList boardDelete">게시물 삭제</div>
                                            <input type="hidden" name="boardNo" value="<%=pb.getBoardNo()%>"/>
                                            <input type="hidden" name="boardType" value="post"/>
                                        </div>
                                    </div>
                                    <%} %>
                                    <!--------------->
                                </div>
                                
                                <div class="boardContents">
                                	<form action="/updateProjectBoard.ho" method="post">
	                                    <textarea class="textarea"  disabled="disabled" readonly="readonly" name="boardText"><%=pb.getBoardText() %></textarea>
	                                    <%if(pb.getImgName()!=null){ %>
	                                    <img class="imgUp" src="/resources/file/project/<%=pb.getImgName()%>"/>
	                                    <%} %>
	                                    <input type="hidden" name="boardNo" value="<%=pb.getBoardNo()%>"/>
	                                    <input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
	                                    <input type="hidden" name="boardType" value="post"/>
	                                    <span class="boardModifyBtn textHide">수정&nbsp;</span>
	                                    <span class="modifyCancelBtn textHide">취소</span>
                                    </form>
                                </div>
                                <%
                                int count = 0;
                                for(ProjectComment pc : commentList){
                                	if(pb.getBoardNo()==pc.getBoardNo()){
                                		count++;
                                	}
                                }
                                	
                                %>
                                <div class="commentCount">
                                    <%=count %>개 댓글
                                </div>
                                <!-- 댓글 하나 코드 -->
                                <%for(ProjectComment pc : commentList){ %>
                                <%if(pc.getBoardNo()==pb.getBoardNo()){ %>
                                <%
                                	String memName = "";
                                	for(Member member : boardMemberList){
                                		if(pc.getMemNo()==member.getMemNo()){
                                			memName = member.getMemName();
                                		}
                                	}
                                %>
                                <div class="commentList">
                                    <div class="commentUserImg">
                                        <i class="fas fa-user-circle"></i>
                                    </div>
                                    <div class="commentInfo">
                                        <div class="commentName">
                                        	<%=memName %><span class="commentTime"><%=pc.getCommentDate() %></span>
                                        	<%if(pc.getMemNo()==m.getMemNo()){ %>
	                                        <span class="commentModifyBtn">수정</span>
	                                        <span  class="commentDeleteBtn">삭제</span>
	                                        <input type="hidden" name="commentNo" value="<%=pc.getCommentNo() %>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
	                                        <%} %>
                                        </div>
                                        <form action="/updateProjectComment.ho" method="get">
	                                        <textarea class="commentTextArea" name="commentCon" disabled="disabled" readonly="readonly"><%=pc.getCommentCon() %></textarea>
	                                        <input type="hidden" name="commentNo" value="<%=pc.getCommentNo()%>"/>
	                                        <input type="hidden" name="memNo" value="<%=pc.getMemNo() %>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                        </form>
                                    </div>
                                </div>
                                <%} %>
                                <%} %>
                                
                                <div class="commentWrite">
                                    <div class="commentUser">
                                        <i class="fas fa-user-circle"></i>
                                    </div>
                                    <div class="commentText">
                                        <form action="/insertBoardComment.ho" method="post">
                                            <input class="boardComment" type="text" name="commentCon" placeholder=" 댓글을 입력하세요 (Enter는 입력)"/>
                                            <input type="hidden" name="boardNo" value="<%=pb.getBoardNo()%>"/>
                                            <input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                        </form>
                                    </div>
                                </div>
                                
                                
                            </div>
                            <%} %>
                            <%} %>
                            
                            
                            
                            <!-- 코드 게시물 여러개 -->
                            <%if(boardType.equals("code")){ %>
                            <%for(ProjectCode pCode : codeList){ %>
                            <%String name = ""; %>
                            <%for(Member member : boardMemberList){ %>
                            	<%if(member.getMemNo()==pCode.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%} %>
                            <%} %>
                            <div class="boardBox">
                                <div class="boardInfo">
                                    <div class="memberImg"><i class="fas fa-user-circle"></i></div>
                                    <div class="memberInfo">
                                    
                                        <div class="memberName"><%=name %></div>
                                    
                                        <div class="boardTime"><%=pCode.getCodeDate() %></div>
                                    </div>
                                    <!--관리자 일 때-->
                                    <%if(pCode.getMemNo()==m.getMemNo()){ %>
                                    <div class="memberAdmin">
                                        <span class="boardSet"><i class="fas fa-ellipsis-v"></i></span>
                                        <div class="boardModifyBox">
                                            <div class="boardModifyList boardModify">게시물 수정</div>
                                            <div class="boardModifyList boardDelete">게시물 삭제</div>
                                            <input type="hidden" name="boardNo" value="<%=pCode.getCodeNo() %>"/>
                                            <input type="hidden" name="boardType" value="code"/>
                                        </div>
                                    </div>
                                    <%} %>
                                    <!--------------->
                                </div>
                                
                                <div class="boardContents">
                                	<form action="/updateProjectBoard.ho" method="post">
                                		<div class="codeTextBox">
                                		<%=pCode.getCodeText() %>
                                		</div>
	                                    <textarea class="textarea"  disabled="disabled" readonly="readonly" name="boardText"><%=pCode.getBoardText() %></textarea>
	                                    <%if(pCode.getImgName()!=null){ %>
	                                    <img class="imgUp" src="/resources/file/project/<%=pCode.getImgName()%>"/>
	                                    <%} %>
	                                    <input type="hidden" name="boardNo" value="<%=pCode.getCodeNo()%>"/>
	                                    <input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
	                                    <span class="boardModifyBtn textHide">수정&nbsp;</span>
	                                    <span class="modifyCancelBtn textHide">취소</span>
                                    </form>
                                </div>
                                <%
                                int count = 0;
                                for(ProjectComment pc : commentList){
                                	if(pCode.getCodeNo()==pc.getBoardNo()){
                                		count++;
                                	}
                                }
                                	
                                %>
                                <div class="commentCount">
                                    <%=count %>개 댓글
                                </div>
                                <!-- 댓글 하나 코드 -->
                                <%for(ProjectComment pc : commentList){ %>
                                <%if(pc.getBoardNo()==pCode.getCodeNo()){ %>
                                <%
                                	String memName = "";
                                	for(Member member : boardMemberList){
                                		if(pc.getMemNo()==member.getMemNo()){
                                			memName = member.getMemName();
                                		}
                                	}
                                %>
                                <div class="commentList">
                                    <div class="commentUserImg">
                                        <i class="fas fa-user-circle"></i>
                                    </div>
                                    <div class="commentInfo">
                                        <div class="commentName">
                                        	<%=memName %><span class="commentTime"><%=pc.getCommentDate() %></span>
                                        	<%if(pc.getMemNo()==m.getMemNo()){ %>
	                                        <span class="commentModifyBtn">수정</span>
	                                        <span  class="commentDeleteBtn">삭제</span>
	                                        <input type="hidden" name="commentNo" value="<%=pc.getCommentNo() %>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
	                                        <%} %>
                                        </div>
                                        <form action="/updateProjectComment.ho" method="get">
	                                        <textarea class="commentTextArea" name="commentCon" disabled="disabled" readonly="readonly"><%=pc.getCommentCon() %></textarea>
	                                        <input type="hidden" name="commentNo" value="<%=pc.getCommentNo()%>"/>
	                                        <input type="hidden" name="memNo" value="<%=pc.getMemNo() %>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                        </form>
                                    </div>
                                </div>
                                <%} %>
                                <%} %>
                                
                                <div class="commentWrite">
                                    <div class="commentUser">
                                        <i class="fas fa-user-circle"></i>
                                    </div>
                                    <div class="commentText">
                                        <form action="/insertBoardComment.ho" method="post">
                                            <input class="boardComment" type="text" name="commentCon" placeholder=" 댓글을 입력하세요 (Enter는 입력)"/>
                                            <input type="hidden" name="boardNo" value="<%=pCode.getCodeNo()%>"/>
                                            <input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                        </form>
                                    </div>
                                </div>
                                
                                
                            </div>
                            <%} %>
                            <%} %>
                            
                            
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
                                        <div><i class="fas fa-user-circle"></i></div>
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
            프로젝트 수정<div id="newProjectExit"><i class="fas fa-times"></i></div>
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
        <div id="inviteSearchBox">
            <form id="memberSearchForm" action="#" method="get">
                <input id="memberSearch" type="text" name="memberNo" placeholder=" 프로젝트를 함께할 친구를 찾아보세요!"/><i id="memberSearchImg" class="fas fa-search"></i>
            </form>
        </div>
        <div id="inviteContents">
            
            <!--얘가 여러개 생겨남-->
            <div class="inviteMemberList">
                <div class="inviteMemberImg"><i class="fas fa-user-circle"></i></div>
                <div class="inviteMemberInfo">
                    <div class="inviteMemberName">이름</div>
                    <div class="inviteMemberPosition">직급</div>
                </div>
                <div class="memberAdd">
                    <div class="memberAddBox">+&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추가</div>
                </div>
            </div>
            
        </div>
    </div>
    
    <!--참가자 목록 -->
    <div id="memberAllListBox">
        <div id="memberAllListHeader" class="displayNone">
            <span id="memberAllListExit"><i class="fas fa-times"></i></span>
            <div id="memberAllListName">프로젝트 참가자</div>
        </div>
        <div id="memberAllListSearchBox" class="displayNone">
            <form class="displayNone" id="memberAllListSearchForm" action="#" method="get">
                <input id="memberAllListSearch" type="text" name="memberNo" placeholder=" 참여자 명으로 검색"/><i id="memberAllListSearchImg" class="fas fa-search"></i>
            </form>
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
                <div class="memberAllListImg displayNone"><i class="fas fa-user-circle"></i></div>
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
                        <%if(mgrYN=='N'){ %>
                            <div class="memberAdminList adminSet">관리자 지정</div>
                            <input type="hidden" name="memNo" value="<%=member.getMemNo()%>"/>
                            <input type="hidden" name="" value="<%=member.getMemName() %>"/>
                        <%}else{%>
                            <div class="memberAdminList adminCancel">관리자 지정 해제</div>
                            <input type="hidden" name="memNo" value="<%=member.getMemNo()%>"/>
                            <input type="hidden" name="" value="<%=member.getMemName() %>"/>
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
        
    
    
    <form id="workListForm" action="/projectDetail.ho" method="post">
    	<input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
    	<input type="hidden" name="boardType" value="work"/>
    </form>
    
    <form id="postListForm" action="/projectDetail.ho" method="post">
    	<input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
    	<input type="hidden" name="boardType" value="post"/>
    </form>
    
    <form id="codeListForm" action="/projectDetail.ho" method="post">
    	<input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
    	<input type="hidden" name="boardType" value="code"/>
    </form>
    
    <form id="planListForm" action="/projectDetail.ho" method="post">
    	<input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
    	<input type="hidden" name="boardType" value="plan"/>
    </form>
    
    <form id="fileListForm" action="/projectDetail.ho" method="post">
    	<input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
    	<input type="hidden" name="boardType" value="file"/>
    </form>
    <!-- 자바 스크립트    -->
    <script>
    	$(function(){
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
    					location.replace('/projectAllList.ho');
    				}
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
    		
    	});
    </script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script type="text/javascript" src="/resources/js/projectDetail.js"></script>
</body>
</html>