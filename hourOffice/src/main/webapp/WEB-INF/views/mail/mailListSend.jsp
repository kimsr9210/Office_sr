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
<link href="/resources/css/mail/mail_list.css" rel="stylesheet" type="text/css" />
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
                        <span id="sub-name">보낸메일함</span>
                        <span>${mailCount.allCount }</span>
                        <form action="/mailList.ho" method="get" id="mail-search-form">
                        <input type="hidden" name="listType" value="S"/>
                           <select name="searchType" id="search-option">
                               <option value="title">제목</option>
                               <option value="receiver">받는 사람</option>
                               <option value="contents">내용</option>
                           </select>
                            <input type="text" name="keyword" id="mail-search"><button type="submit" id="mail-search-btn">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>
                    </div>
                    <div id="TitleContents">
                      <div id="mail_btn_wrap">
                                <span id="mail_del_btn">삭제</span>
                                <span id="mail_send_btn">전달</span>
                                <span id="mail_res_btn">재발송</span>
                            </div>
                       <a href="/mailWritePage.ho" id="new-mail-btn">+ 새 메일쓰기</a>
                        <table id="mail-docu-list">
                            <tr>
                                <th><input type="checkbox" id="mail-all-select"></th>
                                <th>첨부</th>
                                <th class="sender-head">받는 사람</th>
                                <th class="title-head">제목</th>
                                <th>보낸 날짜</th>
                            </tr>
                            <c:forEach var="mail" items="${requestScope.mailList.list }">
	                            <tr>
	                                <td><input type="checkbox" name="mail-one-select" value="${mail.mailNo }"></td>
	                                <td><c:if test="${mail.fileYN =='Y'.charAt(0) }"><i class="fas fa-paperclip"></i></c:if></td>
	                                <td><div class="mail-sender" memno="${mail.recMemNo }">${mail.recMemName } ${mail.recMemPosition }</div></td>
	                                <td><div class="mail-title"><a href="/mailView.ho?mailNo=${mail.mailNo }&other=${mail.recMemNo}&listType=S">
	                                <c:choose><c:when test="${mail.title !=null }">${mail.title}</c:when><c:otherwise>(제목없음)</c:otherwise></c:choose></a></div></td>
	                                <td><fmt:formatDate value="${mail.sendDate }" pattern="yyyy-MM-dd (E) hh:mm"/></td>
	                            </tr>
                            </c:forEach>
                        </table>
                        <ul id="page-navi">${requestScope.mailList.pageNavi}</ul>
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
        	
        	$('#categoryMail').next().children().eq(2).children().css('font-weight','800');
        	$('#categoryMail').next().children().eq(2).children().css('color','#ffcc29');
            //all 선택
            $('#mail-all-select').click(function(){
                console.log($(this).prop('checked'));
                if($(this).prop('checked')){
                    $('input[name=mail-one-select]').prop('checked',true);
                }else{
                    $('input[name=mail-one-select]').prop('checked',false);
                }
            });
          //삭제 버튼
            $('#mail_del_btn').click(function(){
            	var inputArray = [];
            	 $('input[name=mail-one-select]:checked').each(function(){
            		 inputArray.push($(this).val());
            	 });
            	 if(inputArray.length>0){ 
            		 var answer = window.confirm('정말로 삭제하시겠습니까?');
                     if(answer){
		            	 $.ajax({
		            		 url : "/deleteMail.ho",
		            		 traditional : true,
		            		 data : {"listType": 'S', "mailNoList" : inputArray},
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
                     }
            	 }else{
            		 alert('대상을 1개 이상 선택해주세요');
            	 }
            });
          	//전달 버튼
            $('#mail_send_btn').click(function(){
            	var checked = $('input[name=mail-one-select]:checked');
            	if(checked.length==1){
                    var mailNo = checked.val();
                    location.href="/transferMail.ho?mailNo="+mailNo;
            	}else if(checked.length==0){
            		alert('전달할 메일을 선택하세요');
            	}else{
            		alert('메일은 한 개만 선택하세요');
            	}
            });
          	//재발송버튼
          	$('#mail_res_btn').click(function(){
          		var inputArray = [];
              	 $('input[name=mail-one-select]:checked').each(function(){
              		 inputArray.push($(this).val());
              	 });
              	 $.ajax({
            		 url : "/resendMail.ho",
            		 traditional : true,
            		 data : {"mailNoList" : inputArray},
            		 type : "post",
            		 success : function(result){
            			 if(result){
            			 	alert('재발송 성공');
            			 	location.reload();
            			 }else{
            				 alert('재발송 실패');
            			 }
            		 },
            		 error : function(){
            			 alert('처리 에러');
            		 }
            	 });
          	});
        });
    </script>
</body>
</html>