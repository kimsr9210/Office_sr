<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
<!-- 폰트어썸 -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
<link href="/resources/css/mail/mail_view.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
                    
                    <div id="TitleName">
                        <span id="page-name">메일</span>
                        <span id="sub-name">메일 읽기</span>
                    </div>
                    <div id="TitleContents">
                      <div id="mail_btn_wrap">
                      	<c:choose>
                      		<c:when test="${listType=='S' }"> <!-- 페이지 내 구분을 위한 String listType -->
	                      		<span id="mail_del_btn" type="S" class="left-btn">삭제</span>
	                      		<span id="mail_send_btn" class="left-btn">전달</span>
	                      		<span id="mail_res_btn" class="left-btn">재발송</span>
	                      		<span id="mail_list_btn" type="S" class="right-btn">목록</span>
                      		</c:when>
                      		<c:when test="${listType=='R' }"><span id="mail_del_btn" type="R" class="left-btn">삭제</span>
                                <span id="mail_rep_btn" class="left-btn">답장</span>
                                <span id="mail_send_btn" class="left-btn">전달</span>
                                <span id="mail_list_btn" type="R" class="right-btn">목록</span>
                            </c:when>
                            <c:when test="${listType=='F'}"><span id="mail_del_btn" type="F" class="left-btn">삭제</span>
                                <span id="mail_rep_btn" class="left-btn">답장</span>
                                <span id="mail_send_btn" class="left-btn">전달</span>
                                <span id="mail_list_btn" type="F" class="right-btn">목록</span>
                             </c:when>
                             <c:when test="${listType=='RK' }"><span id="mail_del_btn" type="R" class="left-btn">삭제</span>
                                <span id="mail_rep_btn" class="left-btn">답장</span>
                                <span id="mail_send_btn" class="left-btn">전달</span>
                                <span id="mail_list_btn" type="R" class="right-btn">목록</span>
                            </c:when>
                             <c:when test="${listType=='FK'}"><span id="mail_del_btn" type="F" class="left-btn">삭제</span>
                                <span id="mail_rep_btn" class="left-btn">답장</span>
                                <span id="mail_send_btn" class="left-btn">전달</span>
                                <span id="mail_list_btn" type="F" class="right-btn">목록</span>
                             </c:when>
                             <c:when test="${listType=='FD'}">
                             	<span id="mail_fdel_btn" type="F" class="left-btn">영구삭제</span>
                                <span id="mail_res_btn" class="left-btn">복원</span>
                                <span id="mail_list_btn" type="D" class="right-btn">목록</span>
                             </c:when>
                             <c:when test="${listType=='RD'}">
                             	<span id="mail_fdel_btn" type="R" class="left-btn">영구삭제</span>
                                <span id="mail_res_btn" class="left-btn">복원</span>
                                <span id="mail_list_btn" type="D" class="right-btn">목록</span>
                             </c:when>
                      	</c:choose>
                            </div>
                       <div id="mail-page-wrap">
                           <div id="mail-info-wrap">
                               <div id="mail-info-title" mailno="${mail.mailNo }"><div>제목</div><div><c:choose>
                               <c:when test="${mail.title !=null }">${mail.title }</c:when>
                               <c:otherwise>(제목없음)</c:otherwise>
                               </c:choose> 
                               	<c:if test="${listType!='S' }">
                               <c:choose><c:when test="${mail.keepYN=='Y'.charAt(0) }"><i class="fas fa-star"></i></c:when>
                               <c:otherwise><i class="far fa-star"></i></c:otherwise></c:choose>
                               </c:if>
                               </div></div>
                               <div id="mail-info-sender" memno="${mail.sendMemNo }"><div>보낸사람</div><div>${mail.sendMemName } ${mail.sendMemPosition }</div></div>
                               <div id="mail-info-receiver"><div>
                               <c:choose><c:when test="${listType=='F' or listType=='FD' }">참조</c:when><c:otherwise>받는사람</c:otherwise></c:choose>
                               </div><div>${mail.recMemName } ${mail.recMemPosition }</div></div>
                               <div id="mail-info-rdate"><div>발신일</div><div><fmt:formatDate value="${mail.sendDate }" pattern="yyyy-MM-dd (E) HH:mm:ss"/></div></div>
                               <div id="mail-attached"><div>첨부파일</div><div> <c:if test="${mail.attachNo!=0 }"><a href="/fileDownload.ho?attachNo=${mail.attachNo }" title="클릭시 다운로드합니다.">${mail.fileName }</a></c:if></div></div>
                           </div>
                           <div id="mail-content">${mail.content }</div>
                       </div>
                    </div>
                </div>
			</div>
		</div>
	</div>

	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script>
        $(function(){
        	$('#categoryMail').next().css('display','block');
        	$('#categoryMail').next().css('height','150px');
        	$('#categoryMail').children().last().children().attr('class','fas fa-chevron-left');
        	// 보관 상태 변경 처리
            $('.fa-star').click(function(){
            	var $this = $(this);
                var keepYN = '';
                var mailNo = $this.parent().parent().attr("mailno");
                var listType = $('#mail_del_btn').attr('type');
                //ajax처리 필요.
                if($this.hasClass('far')){keepYN = 'Y';}
                else{keepYN = 'N';}
                
                $.ajax({
                	url : "/changeKeepYN.ho",
                	data : {"keepYN": keepYN, "mailNo":mailNo, "listType":listType},
                	type : "get",
                	success : function(result){
                		if(result){
	                		if(keepYN=='Y'){
	                			$this.removeClass('far').addClass('fas');		
	                		}else if(keepYN=='N'){
	                			$this.removeClass('fas').addClass('far');		
	                		}
                		}
                	},
                	error : function(){
                		alert('변환 에러');
                	}
                });
                
            });
            //삭제 버튼
            $('#mail_del_btn').click(function(){
            	var answer = window.confirm('정말로 삭제하시겠습니까?');
                if(answer){
	            	var mailNo= $('#mail-info-title').attr('mailno');
	            	var listType = $('#mail_del_btn').attr('type');
	            	$.ajax({
	            		 url : "/deleteMail.ho",
	            		 traditional : true,
	            		 data : {"listType": listType, "mailNoList" : [mailNo]},
	            		 type : "post",
	            		 success : function(result){
	            			 if(result){
	            			 	alert('처리 성공');
	            			 	location.href="/mailList.ho?listType="+listType; //받은, 보낸, 참조
	            			 }else{
	            				 alert('처리 실패');
	            			 }
	            		 },
	            		 error : function(){
	            			 alert('처리 에러');
	            		 }
	            	 });
                }
            });
            //답장 버튼
            $('#mail_rep_btn').click(function(){
                var $this = $('#mail-info-sender');
                var memNo = $this.attr('memno');
                var memName = $this.children().eq(1).text();
                location.href="/sendToOne.ho?memNo="+memNo+"&memName="+memName;
            });
          //전달 버튼
            $('#mail_send_btn').click(function(){
            	var mailNo= $('#mail-info-title').attr('mailno');
                location.href="/transferMail.ho?mailNo="+mailNo;
            });
          	//재발송버튼
          	$('#mail_res_btn').click(function(){
          		var mailNo= $('#mail-info-title').attr('mailno');
              	 $.ajax({
            		 url : "/resendMail.ho",
            		 traditional : true,
            		 data : {"mailNoList" : [mailNo]},
            		 type : "post",
            		 success : function(result){
            			 if(result){
            			 	alert('재발송 성공');
            			 }else{
            				 alert('재발송 실패');
            			 }
            		 },
            		 error : function(){
            			 alert('처리 에러');
            		 }
            	 });
          	});
          //복원 버튼
            $('#mail_res_btn').click(function(){
            	var mailNo = $('#mail-info-title').attr('mailno');
            	var listType = $('#mail_fdel_btn').attr('type');
            	  $.ajax({
            		 url : "/allChange.ho",
            		 traditional : true,
            		 data : {"listType": [listType], "mailNoList" : [mailNo], "ptype" : 'T'},
            		 type : "post",
            		 success : function(result){
            			 if(result){
            			 	alert('처리 성공');
            			 	location.reload();
            			 }else{
            				 alert('처리 실패');
            			 }
            		 },
            		 error : function(){
            			 alert('처리 에러');
            		 }
            	 });
            });
          //영구 삭제 버튼
            $('#mail_fdel_btn').click(function(){
            	var answer = window.confirm('정말로 삭제하시겠습니까?');
                if(answer){
	            	var mailNo = $('#mail-info-title').attr('mailno');
	            	var listType = $('#mail_fdel_btn').attr('type');
	            	  $.ajax({
	            		 url : "/allChange.ho",
	            		 traditional : true,
	            		 data : {"listType": [listType], "mailNoList" : [mailNo], "ptype" : 'P'},
	            		 type : "post",
	            		 success : function(result){
	            			 if(result){
	            			 	alert('처리 성공');
	            			 	location.href="/mailList.ho?listType=D";
	            			 }else{
	            				 alert('처리 실패');
	            			 }
	            		 },
	            		 error : function(){
	            			 alert('처리 에러');
	            		 }
	            	 });
                }
            });
            //목록버튼
            $('#mail_list_btn').click(function(){
            	var type = $(this).attr('type');
            	location.href="/mailList.ho?listType="+type;
            });
        });
    </script>
</body>
</html>