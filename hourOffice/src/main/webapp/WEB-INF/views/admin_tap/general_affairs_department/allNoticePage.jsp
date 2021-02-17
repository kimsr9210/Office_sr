<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="kr.or.houroffice.board.model.vo.BoardPost"%>
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
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- 게시판 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/board.css" />
	<!-- 페이지 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/board/pageNavi.css" />
    
    <script>
        $(function(){
            // 게시판 번호 배열
            var checkPost = [];
            // checkbox 클릭 이벤트
            $('input[type=checkbox]').click(function(){
                var checked = $(this).attr('checked'); //ture || false
                if($(this).is(':checked')){
                    if($(this).val()=='all'){ // 전체 선택을 하면
                    	$('input[name=postNo]').prop('checked',true); // 모든 checkbox:checked
                        checkPost = []; // 변수 비워주기
                        $('input:checkbox[name=postNo]:checked').each(function(){
                            // 반복문 checked 된 checkbox가 있는만큼 반복하여라
                            checkPost.push($(this).val());
                        })
                    }else{
                    	checkPost.push($(this).val());
                    }
                }else{
                    if($(this).val()=='all'){
                        $('input[name=postNo]').prop('checked',false);
                        checkPost = []; // 변수 비워주기
                    }else{
                    	$('input[type=checkbox]').each(function(){
                    		if($(this).val()=='all'){
                    			$(this).prop('checked',false);
                    			checkPost.splice(checkPost.indexOf($(this).val()),1);
                    		}
                    	});
                    	checkPost.splice(checkPost.indexOf($(this).val()),1);
                    }
                }
            });
            
         // 삭제 클릭 이벤트
            $('#del-btn').click(function(){
            	if(checkPost.length>0){
                    if(checkPost[0]=='all'){
                    	checkPost.splice(checkPost.indexOf(checkPost[0]),1);
                    }
                    if(confirm('정말 삭제하시겠습니까?')){
                    	
                        $.ajax({
                        	url : '/admin_tap_deleteNotice.ho',
                        	data : {'postNoList':checkPost},
                        	type : 'post',
                        	success : function(result){
                        		if(result){
                        			alert('해당 게시글 삭제를 성공하였습니다.');
                        			history.go(0);
                        		}else{
                        			alert('게시글 삭제를 실패하였습니다. \n지속적인 오류시 관리자에 문의하세요.');
                        		}
                        	},
                        	error:function(){
                        		alert('게시글 삭제를 실패하였습니다. \n지속적인 오류시 관리자에 문의주세요.');
                        	}
                        });
                    }
                }else{
                    alert('삭제할 게시글을 선택해주세요');
                }
            });
        })
    </script>
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
						총무관리 전체 공지
						<!----------------------------------->
			<% ArrayList<BoardPost> list = (ArrayList<BoardPost>)request.getAttribute("list"); %>
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						
						<span><a href="/admin_tap_noticeWrite.ho"><i class="fas fa-feather-alt i-icon"></i> 새글쓰기</a></span> <span id="del-btn"><i class="far fa-trash-alt i-icon"></i> 삭제</span>
                        <table>
                            <!--<tr style="padding:0;"><td colspan="4" style="padding:0;"><hr></td></tr>-->
                            <tr>
                                <th><input type="checkbox" name="postNo" value="all"/></th>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                            </tr>
                            <!--<tr><td colspan="4" style="padding:0;"><hr></td></tr>-->
                <% SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd."); %>
                <% SimpleDateFormat formatToDay = new SimpleDateFormat("hh:mm"); %>
                <% Date toDay = new Date(System.currentTimeMillis()); // 현재 날짜 구하기 %>
                <% if(list!=null){ %>
             	<% for(BoardPost bp : list){ %>
                            <tr>
                                <td><input type="checkbox" name="postNo" value="<%=bp.getPostNo()%>"/></td>
                                <td><%=bp.getPostNo() %></td>
                                <td><div><a href="/admin_tap_modifyNotice.ho?notNo=<%=bp.getPostNo()%>"><%=bp.getTitle() %></a></div></td>
                                <td><%=bp.getWriter() %> <%=bp.getMemPosition() %></td>
                <% if(format.format(bp.getPostingDate()).equals(format.format(toDay))){ %>
                                <td><%=formatToDay.format(bp.getPostingDate()) %></td>
                <% }else{ %>
                				<td><%=format.format(bp.getPostingDate()) %></td>
                <% } %>
                            </tr>
                <% } %>
                <% } %>
                
                        </table>
                        </form>
                        
                        <ul id="page-navi">${pageNavi.url }</ul>
                        <div id="search-div">
                            <form action="/admin_tap_searchNotice.ho" method="get">
                            <select name="searchType">
                                <option value="both">제목+내용</option>
                                <option value="title">제목</option>
                                <option value="content">내용</option>
                            </select>
                            <input type="text" name="keyword"/>
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