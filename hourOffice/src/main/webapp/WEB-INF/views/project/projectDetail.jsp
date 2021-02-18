
<%@page import="kr.or.houroffice.project.model.vo.ProjectPlan"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectRequest"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectFileData"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectWork"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectCode"%>
<%@page import="kr.or.houroffice.project.model.vo.ProjectComment"%>
<%@page import="org.springframework.web.bind.annotation.SessionAttribute"%>
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

<!--풀캘린더-->
<link href='/resources/api/fullcalendar-5.5.1/lib/main.css' rel='stylesheet'>
	
<!-- TextArea 자동 높이 조절 CDN -->
<script src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
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

@font-face {
    font-family: 'NanumSquareRound';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
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
	font-size: 0.875rem;
	color: white;
	font-family: NanumSquareRound;
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

.codeGray{
	font-weight: normal;
	color: #8C8C8C;
}
.codeLine{
	width:30px;
	float:left;
	text-align:left;
	cursor:pointer;
	user-select: none;
}
.codeLine:hover{
	font-weight: bolder;
}
	/* 할일  */
	.checks {
    position: relative;
    padding: 10px;
}

.checks input[type="checkbox"] {  /* 실제 체크박스는 화면에서 숨김 */
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
  border: 0
}
.checks input[type="checkbox"] + label {
  display: inline-block;
  position: relative;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
}
.checks input[type="checkbox"] + label:before {  /* 가짜 체크박스 */
  content: ' ';
  display: inline-block;
  width: 15px;  /* 체크박스의 너비를 지정 */
  height: 15px;  /* 체크박스의 높이를 지정 */
  line-height: 15px; /* 세로정렬을 위해 높이값과 일치 */
  margin: -2px 8px 0 0;
  text-align: center; 
  vertical-align: middle;
  background: #fafafa;
  border: 1px solid #cacece;
  border-radius : 3px;
  box-shadow: 0px 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05);
}
.checks input[type="checkbox"] + label:active:before,
.checks input[type="checkbox"]:checked + label:active:before {
  box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px 1px 3px rgba(0,0,0,0.1);
}

.checks.etrans input[type="checkbox"] + label {
  padding-left: 30px;
}
.checks.etrans input[type="checkbox"] + label:before {
  position: absolute;
  left: 0;
  top: 0;
  margin-top: 0;
  opacity: .6;
  box-shadow: none;
  border-color: #1D9F8E;
  -webkit-transition: all .12s, border-color .08s;
  transition: all .12s, border-color .08s;
}

.checks.etrans input[type="checkbox"]:checked + label:before {
  position: absolute;
  content: "";
  width: 10px;
  top: -5px;
  left: 5px;
  border-radius: 0;
  opacity:1; 
  background: transparent;
  border-color:transparent #1D9F8E #1D9F8E transparent;
  border-top-color:transparent;
  border-left-color:transparent;
  -ms-transform:rotate(45deg);
  -webkit-transform:rotate(45deg);
  transform:rotate(45deg);
}

.no-csstransforms .checks.etrans input[type="checkbox"]:checked + label:before {
  /*content:"\2713";*/
  content: "\2714";
  top: 0;
  left: 0;
  width: 15px;
  line-height: 15px;
  color: #6cc0e5;
  text-align: center;
  border: 1px solid #cccccc;
}
    
    
    .workContents{
		width: 100%;
		height: auto;
		padding: 10px 20px 10px 20px;
		border: 1px solid #cccccc;
        overflow: hidden;
	}
    .workNavi{
        width: 100%;
        height: 40px;
        padding: 10px 0px 0px 0px;
        margin: 0;
        box-sizing: border-box;
        border-radius: 5px;
    }
    .workNavi>li{
        text-decoration: none;
        list-style: none;
        float: left;
    }
    
    .workNavi>li:nth-child(1){
        width: calc(65% - 20px);
        padding-left: 20px;
    }
    .workNavi>li:nth-child(2){
        width: 10%;
        text-align: right;
    }
    .workNavi>li:nth-child(3){
        width: calc(25% - 20px);
        text-align: right;
        padding-right: 20px;
    }
    /* 프로그레스바 */
    .progressbar {
      background-color: #E6E6E6;
      border-radius: 13px;
      /* (height of inner div) / 2 + padding */
      padding: 2px;
      margin-bottom: 5px;
    }

    .progressbar>div {
        background-color: #25CCB6;
    /* Adjust with JavaScript */
        height: 5px;
        border-radius: 10px;
    }
    
    
    #fileBox{
    	width: 98%;
		border: 1px solid #cccccc;
		margin-top: 20px;
		border-radius: 5px;
		padding: 20px;
    }
    
    .fileListNavi{
    	width: 100%;
    	padding: 0px 10px 0px 10px;
    	height: 50px;
    	border-bottom: 1px solid #cccccc;
    }
    .fileListNavi>li{
    	float: left;
    	height: 100%;
    	padding: 15px 5px 10px 5px;
    	text-align: center;
    }
    .fileListNavi:nth-child(1){
    	font-weight: bolder;
    }
    .fileListNavi>li:nth-child(1){
    	width: 40%;
    }
    .fileListNavi>li:nth-child(2){
    	width: 10%;
    }
    .fileListNavi>li:nth-child(3){
    	width: 10%;
    }
    .fileListNavi>li:nth-child(4){
    	width: 20%;
    }
    .fileListNavi>li:nth-child(5){
    	width: 10%;
    }
    .fileListNavi>li:nth-child(6){
    	width: 10%;
    }
    
    .fileListNavi>li>svg{
    	color: #47C83E;
    	font-size: 1.5rem;
    }
    
    .fileSelect:hover{
    	background-color: #F7F7F7;
    }
    
    .fileDownloadBox{
    	padding :3px;
    	background-color : #1D9F8E;
    	color : white;
    	font-weight: bolder;
    	border-radius: 3px;
    	cursor: pointer;
    }
    
    .fileDeleteBox{
    	padding :3px;
    	color : white;
    	font-weight: bolder;
    	border-radius: 3px;
    	background-color : #FF6363;
    	cursor: pointer;
    }
    
    .boarbNone{
    	width: 98%;
    	padding: 70px;
    	font-size: 1.5rem;
    	text-align: center;
    	border: 1px solid #cccccc;
    	margin-top: 20px;
    	border-radius: 3px;
    	font-family: GongGothicLight;
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
	
	/*-------------------- 풀캘린더 ------------------*/
	#calendar {
	    max-width: 785px;
	    margin-top: 10px;
	    margin-bottom: 50px;
	}
	.koHolidays{
	    color: red;
	    font-weight: 600;
	}
	.fc-day-sat { color:#0000FF; }     /* 토요일 */
	.fc-day-sun { color:#FF0000; }    /* 일요일 */
</style>
    		

<%
	Project p = (Project)request.getAttribute("project");
	ArrayList<Member> projectMemberList = (ArrayList<Member>)request.getAttribute("projectMemberList");
	ArrayList<ProjectBoard> boardList = (ArrayList<ProjectBoard>)request.getAttribute("boardList");
	ArrayList<Member> boardMemberList = (ArrayList<Member>)request.getAttribute("boardMemberList");
	ArrayList<Member> codeMemberList = (ArrayList<Member>)request.getAttribute("codeMemberList");
	ArrayList<Member> workMemberList = (ArrayList<Member>)request.getAttribute("workMemberList");
	ArrayList<Member> fileMemberList = (ArrayList<Member>)request.getAttribute("fileMemberList");
	ArrayList<Member> allMemberList = (ArrayList<Member>)request.getAttribute("allMemberList");
	
	ArrayList<ProjectRequest> requestList = (ArrayList<ProjectRequest>)request.getAttribute("requestList");
	
	ArrayList<ProjectMember> projectMgrList = (ArrayList<ProjectMember>)request.getAttribute("projectMgrList");
	ArrayList<ProjectComment> postCommentList = (ArrayList<ProjectComment>)request.getAttribute("postCommentList");
	ArrayList<ProjectComment> codeCommentList = (ArrayList<ProjectComment>)request.getAttribute("codeCommentList");
	
	ArrayList<ProjectWork> projectWorkList = (ArrayList<ProjectWork>)request.getAttribute("projectWorkList");
	ArrayList<ProjectComment> workCommentList = (ArrayList<ProjectComment>)request.getAttribute("workCommentList");
	
	
	ArrayList<Project> favoriteList = (ArrayList<Project>)request.getAttribute("favoriteList");
	ArrayList<ProjectCode> codeList = (ArrayList<ProjectCode>)request.getAttribute("codeList");
	ArrayList<ProjectFileData> fileList = (ArrayList<ProjectFileData>)request.getAttribute("fileList");
	String boardType = (String)request.getAttribute("boardType");
	Member m = (Member)session.getAttribute("member");
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	int workCount = 1;
	boolean like = false;
	for(Project project : favoriteList){
		if(project.getProNo()==p.getProNo()){
			like = true;
		}
	}
	
	SimpleDateFormat planForm = new SimpleDateFormat("yyyy-MM-dd");
	ArrayList<ProjectPlan> planList = (ArrayList<ProjectPlan>)request.getAttribute("planList");
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
                                        		if(pm.getMemNo()==m.getMemNo() && pm.getMgrYN()=='Y'){
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
                            
                            <!----------------------------- 게시물 여러개 ------------------------------------>
                            <%if(boardType.equals("post")){ %>
                            <%if(!boardList.isEmpty()) {%>
                            <%for(ProjectBoard pb : boardList){ %>
                            <%
                            	
                            	String name = "";
                            	String profile = null;
	                            Date date = new Date(pb.getBoardDate().getTime());
	                        	String time = sdf.format(date);
                            %>
                            <%for(Member member : boardMemberList){ %>
                            	<%if(member.getMemNo()==pb.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%} %>
                            	<%
                            	if(member.getMemProfile()!=null){
                            		profile = member.getMemProfile();
                            	}
                            	%>
                            	
                            	
                            <%} %>
                            <div class="boardBox">
                                <div class="boardInfo">
                                    <div class="memberImg">
                                    
                                    <%if(profile==null){ %>
                                    <i class="fas fa-user-circle"></i>
                                    <%}else{ %>
                                    <img class="nc-img" src="/resources/images/profile/<%=profile%>">
                                    <%} %>
                                    
                                    </div>
                                    <div class="memberInfo">
                                    
                                        <div class="memberName"><%=name %></div>
                                    
                                        <div class="boardTime"><%=time %></div>
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
	                                    <span class="boardModifyBtn textHide">수정&nbsp;</span>
	                                    <span class="modifyCancelBtn textHide">취소</span>
                                    </form>
                                </div>
                                <%
                                int count = 0;
                                for(ProjectComment pc : postCommentList){
                                	if(pb.getBoardNo()==pc.getBoardNo()){
                                		count++;
                                	}
                                }
                                	
                                %>
                                <div class="commentCount">
                                    <%=count %>개 댓글
                                </div>
                                <!-- 댓글 하나 코드 -->
                                <%for(ProjectComment pc : postCommentList){ %>
                                <%if(pc.getBoardNo()==pb.getBoardNo()){ %>
                                <%
                                	String commentProfile = null;
	                                Date commentDate = new Date(pc.getCommentDate().getTime());
	                            	String commentTime = sdf.format(commentDate);
                                	String memName = "";
                                	for(Member member : allMemberList){
                                		if(pc.getMemNo()==member.getMemNo()){
                                			memName = member.getMemName();
                                			commentProfile = member.getMemProfile();
                                		}
                                	}
                                %>
                                <div class="commentList">
                                    <div class="commentUserImg">
                                        <%if(commentProfile==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=commentProfile%>"/>
                                    	<%} %>
                                    </div>
                                    <div class="commentInfo">
                                        <div class="commentName">
                                        	<%=memName %><span class="commentTime"><%=commentTime %></span>
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
                                            <input type="hidden" name="boardType" value="post"/>
                                        </form>
                                    </div>
                                </div>
                                <%} %>
                                <%} %>
                                
                                <div class="commentWrite">
                                    <div class="commentUser">
                                        <%if(m.getMemProfile()==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=m.getMemProfile()%>">
                                    	<%} %>
                                    </div>
                                    <div class="commentText">
                                        <form action="/insertBoardComment.ho" method="post">
                                            <input class="boardComment" type="text" name="commentCon" placeholder=" 댓글을 입력하세요 (Enter는 입력)"/>
                                            <input type="hidden" name="boardNo" value="<%=pb.getBoardNo()%>"/>
                                            <input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                            <input type="hidden" name="boardType" value="post"/>
                                        </form>
                                    </div>
                                </div>
                                
                                
                            </div>
                            <%} %>
                            <%}else{ %>
                            	<div class="boarbNone">업로드한 게시물이 없습니다.</div>
                            <%} %>
                            <%} %>
                            
                            
                            
                            <!------------------------ 코드 게시물 여러개 ----------------------------------->
                            <%if(boardType.equals("code")){ %>
                            <%if(!codeList.isEmpty()){ %>
                            <%for(ProjectCode pCode : codeList){ %>
                            <%
                            String profile = null;
                            String name = ""; 
                            Date date = new Date(pCode.getCodeDate().getTime());
                        	String time = sdf.format(date);
                            %>
                            <%for(Member member : codeMemberList){ %>
                            	<%if(member.getMemNo()==pCode.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%if(member.getMemProfile()!=null){
                            		profile = member.getMemProfile();
                            	}
                            	%>
                            	
                            	<%} %>
                            <%} %>
                            <div class="boardBox">
                                <div class="boardInfo">
                                    <div class="memberImg">
                                    <%if(profile==null){ %>
                                    <i class="fas fa-user-circle"></i>
                                    <%}else{ %>
                                    <img class="nc-img" src="/resources/images/profile/<%=profile%>">
                                    <%} %>
                                    </div>
                                    <div class="memberInfo">
                                    
                                        <div class="memberName"><%=name %></div>
                                    
                                        <div class="boardTime"><%=time %></div>
                                    </div>
                                    <!--관리자 일 때-->
                                    <%if(pCode.getMemNo()==m.getMemNo()){ %>
                                    <div class="memberAdmin">
                                        <span class="boardSet"><i class="fas fa-ellipsis-v"></i></span>
                                        <div class="boardModifyBox">
                                            <div class="boardModifyList boardModify">게시물 수정</div>
                                            <div class="boardModifyList boardDelete">게시물 삭제</div>
                                            <input type="hidden" name="codeNo" value="<%=pCode.getCodeNo() %>"/>
                                            <input type="hidden" name="boardType" value="code"/>
                                        </div>
                                    </div>
                                    <%} %>
                                    <!--------------->
                                </div>
                                
                                <div class="boardContents">
                                	<form action="/updateProjectCode.ho" method="post">
                                		<div class="codeTextBox">
                                		<%=pCode.getCodeText() %>
                                		</div>
	                                    <textarea class="textarea"  disabled="disabled" readonly="readonly" name="boardText"><%=pCode.getBoardText() %></textarea>
	                                    <%if(pCode.getImgName()!=null){ %>
	                                    <img class="imgUp" src="/resources/file/project/<%=pCode.getImgName()%>"/>
	                                    <%} %>
	                                    <input type="hidden" name="codeNo" value="<%=pCode.getCodeNo()%>"/>
	                                    <input type="hidden" name="proNo" value="<%=p.getProNo() %>"/>
	                                    <span class="boardModifyBtn textHide">수정&nbsp;</span>
	                                    <span class="modifyCancelBtn textHide">취소</span>
                                    </form>
                                </div>
                                <%
                                int count = 0;
                                for(ProjectComment pc : codeCommentList){
                                	if(pCode.getCodeNo()==pc.getBoardNo()){
                                		count++;
                                	}
                                }
                                	
                                %>
                                <div class="commentCount">
                                    <%=count %>개 댓글
                                </div>
                                <!-- 댓글 하나 코드 -->
                                <%for(ProjectComment pc : codeCommentList){ %>
                                <%if(pc.getBoardNo()==pCode.getCodeNo()){ %>
                                <%
                                	String commentProfile = null;
	                                Date commentDate = new Date(pc.getCommentDate().getTime());
	                            	String commentTime = sdf.format(commentDate);
                                	String memName = "";
                                	for(Member member : allMemberList){
                                		if(pc.getMemNo()==member.getMemNo()){
                                			memName = member.getMemName();
                                			commentProfile = member.getMemProfile();
                                		}
                                	}
                                %>
                                <div class="commentList">
                                    <div class="commentUserImg">
                                         <%if(commentProfile==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=commentProfile%>"/>
                                    	<%} %>
                                    </div>
                                    <div class="commentInfo">
                                        <div class="commentName">
                                        	<%=memName %><span class="commentTime"><%=commentTime %></span>
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
                                            <input type="hidden" name="boardType" value="code"/>
                                        </form>
                                    </div>
                                </div>
                                <%} %>
                                <%} %>
                                
                                <div class="commentWrite">
                                    <div class="commentUser">
                                        <%if(m.getMemProfile()==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=m.getMemProfile()%>">
                                    	<%} %>
                                    </div>
                                    <div class="commentText">
                                        <form action="/insertBoardComment.ho" method="post">
                                            <input class="boardComment" type="text" name="commentCon" placeholder=" 댓글을 입력하세요 (Enter는 입력)"/>
                                            <input type="hidden" name="boardNo" value="<%=pCode.getCodeNo()%>"/>
                                            <input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                            <input type="hidden" name="boardType" value="code"/>
                                        </form>
                                    </div>
                                </div>
                                
                                
                            </div>
                            <%} %>
                            <%} else{%>
                            <div class="boarbNone">업로드한 게시물이 없습니다.</div>
                            <%} %>
                            <%} %>
                            
                            
                            <!------------------------ 할일 여러개 ---------------------------------->
                            <%if(boardType.equals("work")){ %>
                            <%if(!projectWorkList.isEmpty()) {%>
                            <%for(ProjectWork pw : projectWorkList){ %>
                            <%
                            	String profile = null;
                            	String[] workList = pw.getWorkCon().split(",");
                            	int max = workList.length;
                            	int min = pw.getWorkComp();
                            	int progress = ((min*100) / max);
                            	Date date = new Date(pw.getWorkDate().getTime());
                            	String time = sdf.format(date);
                            %>
                            <%String name = ""; %>
                            <%for(Member member : workMemberList){ %>
                            	<%if(member.getMemNo()==pw.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%} %>
                            	<%
                            		if(member.getMemProfile()!=null){
                            			profile = member.getMemProfile();
                            		}
                            	%>
                            <%} %>
                            <div class="boardBox">
                                <div class="boardInfo">
                                    <div class="memberImg">
                                    <%if(profile==null){ %>
                                    <i class="fas fa-user-circle"></i>
                                    <%}else{ %>
                                    <img class="nc-img" src="/resources/images/profile/<%=profile%>">
                                    <%} %>
                                    </div>
                                    <div class="memberInfo">
                                    
                                        <div class="memberName"><%=name %></div>
                                    
                                        <div class="boardTime"><%=time %></div>
                                    </div>
                                    <!--관리자 일 때-->
                                    <%if(pw.getMemNo()==m.getMemNo()){ %>
                                    <div class="memberAdmin">
                                        <span class="boardSet"><i class="fas fa-ellipsis-v"></i></span>
                                        <div class="boardModifyBox">
                                            <div class="boardModifyList boardModify">게시물 수정</div>
                                            <div class="boardModifyList boardDelete">게시물 삭제</div>
                                            <input type="hidden" name="boardNo" value="<%=pw.getWorkNo()%>"/>
                                            <input type="hidden" name="boardType" value="post"/>
                                        </div>
                                    </div>
                                    <%} %>
                                    <!--------------->
                                </div>

								<div class="boardContents">
									<form action="/updateProjectWork.ho" method="post">
										<textarea class="textarea" disabled="disabled"
											readonly="readonly" name="title"><%=pw.getTitle()%></textarea>
										<input type="hidden" name="workNo" value="<%=pw.getWorkNo()%>" />
										<input type="hidden" name="proNo" value="<%=p.getProNo()%>" />
										<input type="hidden" name="boardType" value="post" /> <span
											class="boardModifyBtn textHide">수정&nbsp;</span> <span
											class="modifyCancelBtn textHide">취소</span>
									</form>
									<div class="workContents">
										<ul class="workNavi">
											<li>날짜 : <%=pw.getWorkDay()%></li>
											<li><%=progress %>%</li>
											<li>완료 <%=min %>건 / 전체 <%=max %>건</li>
										</ul>
										<div class="progressbar">
											<div style="width:<%=progress%>%"></div>
										</div>
										<%for(int i=0; i<workList.length; i++){ %>
										<%
											
											String check = "";
											if(workList[i].contains("Checked")){
												check = "checked='checked'";
												workList[i] = workList[i].replace("Checked", "");
											}
										%>
										<hr>
										<div class="checks etrans">
											<input type="checkbox" id="ex_chk<%=workCount %>" <%=check %> class="workCheck"/>
											<label for="ex_chk<%=workCount %>"><%=workList[i] %></label>
											<input type="hidden" value="<%=pw.getWorkNo()%>"/>
											<input type="hidden" value="<%=pw.getWorkCon() %>"/>
											<input type="hidden" value="<%=max %>"/>
											<input type="hidden" value="<%=pw.getWorkComp() %>"/>
										</div>
										<%workCount++; %>
										<%} %>
									</div>
								</div>
								<%
									int count = 0;
											for (ProjectComment pc : workCommentList) {
												if (pw.getWorkNo() == pc.getBoardNo()) {
													count++;
												}
											}
								%>
                                <div class="commentCount">
                                    <%=count %>개 댓글
                                </div>
                                <!-- 댓글 하나 코드 -->
                                <%for(ProjectComment pc : workCommentList){ %>
                                <%if(pc.getBoardNo()==pw.getWorkNo()){ %>
                                <%
                                	String commentProfile = null;
	                                Date commentDate = new Date(pc.getCommentDate().getTime());
	                            	String commentTime = sdf.format(commentDate);
                                	String memName = "";
                                	for(Member member : allMemberList){
                                		if(pc.getMemNo()==member.getMemNo()){
                                			memName = member.getMemName();
                                			commentProfile = member.getMemProfile();
                                		}
                                	}
                                %>
                                <div class="commentList">
                                    <div class="commentUserImg">
                                         <%if(commentProfile==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=commentProfile%>"/>
                                    	<%} %>
                                    </div>
                                    <div class="commentInfo">
                                        <div class="commentName">
                                        	<%=memName %><span class="commentTime"><%=commentTime %></span>
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
                                            <input type="hidden" name="boardType" value="work"/>
                                        </form>
                                    </div>
                                </div>
                                <%} %>
                                <%} %>
                                
                                <div class="commentWrite">
                                    <div class="commentUser">
                                        <%if(m.getMemProfile()==null){ %>
                                    	<i class="fas fa-user-circle"></i>
                                    	<%}else{ %>
                                    	<img class="nc-img" src="/resources/images/profile/<%=m.getMemProfile()%>">
                                    	<%} %>
                                    </div>
                                    <div class="commentText">
                                        <form action="/insertBoardComment.ho" method="post">
                                            <input class="boardComment" type="text" name="commentCon" placeholder=" 댓글을 입력하세요 (Enter는 입력)"/>
                                            <input type="hidden" name="boardNo" value="<%=pw.getWorkNo()%>"/>
                                            <input type="hidden" name="memNo" value="<%=m.getMemNo()%>"/>
                                            <input type="hidden" name="proNo" value="<%=p.getProNo()%>"/>
                                            <input type="hidden" name="boardType" value="work"/>
                                        </form>
                                    </div>
                                </div>
                                
                                
                            </div>
                            <%} %>
                            <%}else{ %>
                            <div class="boarbNone">업로드한 할일이 없습니다.</div>
                            <%} %>
                            <%} %>
                            
                            
                            <!----------------------------- 파일 일때 ------------------------------------>
                            <%if(boardType.equals("file")){ %>
                            
                            <%if(!fileList.isEmpty()){ %>
                            <div id="fileBox">
                            	<ul class="fileListNavi">
                            		<li>파일명</li>
                            		<li>크기</li>
                            		<li>등록자</li>
                            		<li>업로드 날짜</li>
                            		<li>다운로드</li>
                            		<li>삭제</li>
                            	</ul>
                            <%for(ProjectFileData pfd : fileList){ %>
                            <%
                            	String name = ""; 
                            	Date date = new Date(pfd.getUploadTime().getTime());
                            	String time = sdf.format(date);
                            %>
                            <%for(Member member : fileMemberList){ %>
                            	<%if(member.getMemNo()==pfd.getMemNo()){ %>
                            	<%name = member.getMemName(); %>
                            	<%} %>
                            <%} %>
                                <ul class="fileListNavi fileSelect">
                            		<li style="text-align:left">
                            		<%if(pfd.getOriginalFileName().contains(".png")==true || pfd.getOriginalFileName().contains(".jpg")==true){ %>
                            		<i class="far fa-image"></i>
                            		<%} else if(pfd.getOriginalFileName().contains(".pdf")==true){%>
                            		<i class="far fa-file-pdf"></i>
                            		<%} else{%>
                            		<i class="far fa-file"></i>
                            		<%} %>
                            		<%=pfd.getOriginalFileName() %>
                            		</li>
                            		<li><%=pfd.getFileSize() %> KB</li>
                            		<li><%=name %></li>
                            		<li><%=time %></li>
                            		<li>
                            		<form action="/projectFileDownload.ho" method="post">
                            		<div class="fileDownloadBox">다운로드</div>
                            		<input type="hidden" name="fileNo" value="<%=pfd.getFileNo() %>"/>
                            		</form>
                            		</li>
                            		<li>
                            		<div class="fileDeleteBox">삭제</div>
                            		<input type="hidden" value="<%=pfd.getFileNo() %>"/>
                            		<input type="hidden" value="<%=pfd.getOriginalFileName() %>"/>
                            		</li>
                            	</ul>
                            <%} %>
                            </div>
                            <%}else {%>
                            	<div class="boarbNone">업로드한 파일이 없습니다.</div>
                            <%} %>
                            <%} %>
                            
                            <!----------------------------- 일정 일때 ------------------------------------>
                            <!-- 여기다가 일정  -->
                            <%if(boardType.equals("plan")){ %>
                            	<div id='calendar'></div>
                            
                            <%} %>
                            <!-- 풀캘린더 -->
                            <script src='/resources/api/fullcalendar-5.5.1/lib/main.js'></script>
							<script>
							
							  document.addEventListener('DOMContentLoaded', function() {
							    var calendarEl = document.getElementById('calendar');
							      
							      var today = new Date();
							      var month = today.getMonth()+1;
							      if(month<10){
							          month = '0'+month;
							      }
							
							    var calendar = new FullCalendar.Calendar(calendarEl, {
							        
							        initialDate: (today.getFullYear()+'-'+month+'-'+today.getDate()),
							        editable: true,
							        selectable: false,
							        businessHours: true,
							        dayMaxEvents: true,
							        buttonText: {
							            today : '오늘'
							        },
							        
							        titleFormat : function(date) { // title 설정
							            return date.date.year +"년 "+(date.date.month +1)+"월"; 
							        },
							        dayHeaderFormat : function(date) {
							            var weekList = ['일','월','화','수','목','금','토'];
							            var tDay = new Date().getDay()-1;
							            
							            return weekList[date.date.day - tDay];
							        },
							        
							        dayPopoverFormat: { month: '2-digit', day: '2-digit' },
							        
							        fixedWeekCount : false,
							        eventRender: function(info) {
							            var tooltip = new Tooltip(info.el, {
							              title: info.event.extendedProps.description,
							              placement: 'top',
							              trigger: 'hover',
							              container: 'body'
							            });
							        },
							        googleCalendarApiKey: 'AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE',
							        eventSources: [
							        	<%if(!(planList.isEmpty())){ %>
							        	{events: [
							        		<%for(int i=0; i<planList.size();i++){
							        			ProjectPlan pp = planList.get(i); 
							        			Date startPlan = new Date(pp.getStartDate().getTime());
							        			String startTime = planForm.format(startPlan);
							        			Date endPlan = new Date(pp.getEndDate().getTime());
							        			String endTime = planForm.format(endPlan); %>
							        			
							        			<%if(i==planList.size()-1){ %>
							        			{
							        				title: '<%=pp.getSubject()%>',
							        				description: '<%=pp.getMemo() %>',
									                start: '<%=startPlan%>',
									                end: '<%=endPlan%>'
							        			}
							        			<%}else {%>
							        			{
							        				title: '<%=pp.getSubject()%>',
							        				description: '<%=pp.getMemo() %>',
									                start: '<%=startPlan%>',
									                end: '<%=endPlan%>'
							        			},
							        			<%} %>
							        		<%}%>
							                
							            ]
							            },
							        	<%} %>
							            
							            { /*한국 공휴일*/
							                googleCalendarId : "ko.south_korea#holiday@group.v.calendar.google.com", className : "koHolidays",
							                display : 'background',
							                color : '#CFFFFF',
							                order : 'title'
							            }
							        ],
							      
							        eventClick: function(arg) {
							            // opens events in a popup window
							
							            arg.jsEvent.preventDefault() // don't navigate in main tab
							        }
							        
							    });
							
							    calendar.render();
							  });
							
							</script>
                            <!------------------------------------------------------------------------->

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
    		//코드 복사
    		$('.codeLine').click(function(){
    			var $text = $(this).parent().text();
    			$text = $text+" → ";
    			var comment =$(this).parents('.boardBox').children('.commentWrite').children('.commentText').children().children('.boardComment');
    			comment.val($text);
    			comment.focus();
    		});
    		
    		//일정 클릭 시
    		$('.workCheck').click(function(){
    			var text = $(this).next().text();
    			var workNo = $(this).next().next().val();
    			var workCon = $(this).next().next().next().val();
    			var max = $(this).next().next().next().next().val();
    			var workComp = $(this).next().next().next().next().next().val();
                var progress = $(this).parents('.workContents').children('.progressbar').children();
               
                if($(this).prop('checked')){
                    //progress.animate({'width':(gauge+max)+'%'}, 500);
                    $.ajax({
			            url : "/updateProjectWorkCheck.ho",
			            data : {"workNo" : workNo, "text" : text, "workCon" : workCon, "max": max, "workComp": workComp},
			            type : "post",
			            success : function(result){
			            },
			            error : function(){
			            	console.log("프로젝트 일정처리 ajax 통신 실패");
			            }
			        });
					location.reload();
                }else{
                	$.ajax({
			            url : "/updateProjectWorkUnCheck.ho",
			            data : {"workNo" : workNo, "text" : text, "workCon" : workCon, "max": max, "workComp": workComp},
			            type : "post",
			            success : function(result){
			            },
			            error : function(){
			            	console.log("프로젝트 일정처리 ajax 통신 실패");
			            }
			        });
					location.reload();
                }
            });
    		
    		$('.fileDownloadBox').click(function(){
    			$(this).parent().submit();
    		});
    		
    		$('.fileDeleteBox').click(function(){
    			var fileNo = $(this).next().val();
    			var fileName = $(this).next().next().val();
    			var result = window.confirm("'"+fileName+"' 파일을 삭제하시겠습니까?");
    			if(result){
    				$.ajax({
    		            url : "/deleteProjectFile.ho",
    		            data : {"fileNo" : fileNo},
    		            type : "post",
    		            success : function(result){
    		            	if(result=="true"){
			            		alert("'"+fileName+"' 파일이 정상적으로 삭제되었습니다.");
			            	}else{
			            		alert("파일 삭제에 실패하였습니다\n지속적인 오류시 관리자에게 문의하세요");
			            	}
    		            },
    		            error : function(){
    		            	console.log("프로젝트 일정처리 ajax 통신 실패");
    		            }
    		        });
    				location.reload();
    			}else{
    				
    			}
    			
    		});
    		
    		/* 멤버 추가 누를 시 */
    		$('.memberAddBox').click(function() {
    			var memNo = $(this).next().val();
    			var proNo = $(this).next().next().val();
    			console.log(memNo);
    			console.log(proNo);
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
    		
    		
    	});
    </script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script type="text/javascript" src="/resources/js/projectDetail.js"></script>
</body>
</html>