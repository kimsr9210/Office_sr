<%@page import="kr.or.houroffice.board.model.vo.BoardPost"%>
<%@page import="java.text.SimpleDateFormat"%>
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
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- 게시물 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/post.css" />
	
	<style>
        #TitleContents #title span,  #TitleContents #board-content > div:first-child{ /* 글쓰기 */ color: white; cursor: default; }
    	#TitleContents img{
			width:100%; height: 100%;
			border-radius:100%;
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
						전체 공지 <span>게시글</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						<div id="title"><span><i class="fas fa-feather-alt"></i></span><a href="/allNoticePage.ho"><button type="button">목록</button></a>
					<% if((Integer)request.getAttribute("prevPost")>0){ %>
						<a href="/notice.ho?notNo=${prevPost }"><i class="fas fa-arrow-down i-icon"></i> 아랫글</a>
					<% }else{ %>
						<a style="color:lightgray;"><i class="fas fa-arrow-down i-icon" style="color:lightgray; cursor:default;"></i> 아랫글</a>
					<% } %>
					<% if((Integer)request.getAttribute("nextPost")>0){ %>
						<a href="/notice.ho?notNo=${nextPost }"><i class="fas fa-arrow-up i-icon"></i> 윗글</a></div>
					<% }else{ %>
						<a style="color:lightgray;"><i class="fas fa-arrow-up i-icon" style="color:lightgray; cursor:default;"></i> 윗글</a></div>
					<% } %>	
						
                        <hr>
                        <div id="board-content">
                            <div><span><i class="fas fa-feather-alt"></i></span></div>
                            <div>${bp.title }</div>
                            <div>
                                <div class="float"><img src="/resources/images/profile/${bp.memProfile }"/></div>
                                <div class="float">
                                    <div>${bp.writer } ${bp.memPosition }</div>
                     	<% SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); %>
                                    <div><%=format.format(((BoardPost)request.getAttribute("bp")).getPostingDate()) %></div>
                                </div>
                            </div>
                            <div>${bp.content }</div>
                            <div><span>조회수 ${bp.hits } </span><span class="wall"></span><span id="filePlace"><i class="fas fa-paperclip i-icon" style="cursor:default;"></i> 첨부파일 <% if(((BoardPost)request.getAttribute("bp")).getFileNo()>0){ %>[ <span id="fileName-sapn">${bp.origName }</span> ]<% } %></span>
                            	<form action="/downloadFileNotice.ho" method="post" style="display:none;"></form>
                            </div>
                        </div>
                        <hr>
		<script>
			$(function(){
				// 첨부파일
	            $('#filePlace').hover(function(){
	            	$('#fileName-sapn').css('color','blue').css('text-decoration','underline');
	            },function(){
	            	$('#fileName-sapn').css('color','').css('text-decoration','');
	            });
	            // 첨부파일 다운로드
	            $('#fileName-sapn').click(function(){
	            	var $frm = $('#filePlace').next();
	            	$frm.html('<input type="text" name="notNo" value="${bp.postNo}"/><input type="text" name="fileNo" value="${bp.fileNo}"/>').submit();
	            });
			})
		</script>

						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>

	<!-- 자바 스크립트    -->
	<script>
		$(function(){
			$('#categoryBoard').next().css('display','block');
			$('#categoryBoard').next().css('height','150px');
			$('#categoryBoard').children().last().children().attr('class','fas fa-chevron-left');
			
			$('#categoryBoard').next().children().eq(2).children().css('font-weight','800');
			$('#categoryBoard').next().children().eq(2).children().css('color','#ffcc29');
		})
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>
</body>
</html>