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
                        <span id="sub-name">보관메일함</span>
                        <span>${mailCount.nonReadCount}/${mailCount.allCount }</span>
                        <form action="/mailList.ho" method="get" id="mail-search-form">
                        <input type="hidden" name="listType" value="K"/>
                           <select name="searchType" id="search-option">
                               <option value="title">제목</option>
                               <option value="sender">보낸사람</option>
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
                                <span id="mail_read_btn">읽음</span>
                                <span id="mail_rep_btn">답장</span>
                                <span id="mail_send_btn">전달</span>
                            </div>
                       <a href="/mailWritePage.ho" id="new-mail-btn">+ 새 메일쓰기</a>
                        <table id="mail-docu-list">
                            <tr>
                                <th><input type="checkbox" id="mail-all-select"></th>
                                <th>보관</th>
                                <th>읽음</th>
                                <th>첨부</th>
                                <th class="sender-head">보낸사람</th>
                                <th class="title-head">제목</th>
                                <th>받은날짜</th>
                            </tr>
                            <c:forEach var="mail" items="${requestScope.mailList.list }">
	                            <tr>
	                                <td><input type="checkbox" name="mail-one-select" value="${mail.mailNo }"><input type="hidden" name="listType" value="${mail.listType }"/></td>
	                                <td>
	                                <c:choose><c:when test="${mail.keepYN =='Y'.charAt(0) }"><i class="fas fa-star"></i></c:when>
	                                <c:otherwise><i class="far fa-star"></i></c:otherwise></c:choose></td>
	                                <td><c:choose><c:when test="${mail.readYN =='Y'.charAt(0) }"><i class="fas fa-envelope-open"></i></c:when><c:otherwise><i class="fas fa-envelope"></i></c:otherwise></c:choose></td>
	                                <td><c:if test="${mail.fileYN =='Y'.charAt(0) }"><i class="fas fa-paperclip"></i></c:if></td>
	                                <td><div class="mail-sender" memno="${mail.sendMemNo }">${mail.sendMemName } ${mail.sendMemPosition }</div></td>
	                                <td><div class="mail-title"><a href="/mailView.ho?mailNo=${mail.mailNo }&other=${mail.recMemNo}&listType=${mail.listType }K&readYN=${mail.readYN}"><c:choose><c:when test="${mail.listType=='R'.charAt(0) }">[받은메일함]</c:when><c:when test="${ mail.listType=='F'.charAt(0)}">[참조메일함]</c:when></c:choose>
	                                <c:choose><c:when test="${mail.title !=null }">${mail.title}</c:when><c:otherwise>(제목없음)</c:otherwise></c:choose></a></div></td>
	                                <td><fmt:formatDate value="${mail.recDate }" pattern="yyyy-MM-dd (E) hh:mm"/></td>
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
        	
        	$('#categoryMail').next().children().eq(3).children().css('font-weight','800');
        	$('#categoryMail').next().children().eq(3).children().css('color','#ffcc29');
            //all 선택
            $('#mail-all-select').click(function(){
                console.log($(this).prop('checked'));
                if($(this).prop('checked')){
                    $('input[name=mail-one-select]').prop('checked',true);
                }else{
                    $('input[name=mail-one-select]').prop('checked',false);
                }
            });
         // 보관 상태 변경 처리
            $('.fa-star').click(function(){
            	var $this = $(this);
                var keepYN = '';
                var mailNo = $this.parent().prev().children('input[name=mail-one-select]').val();
                var listType = $this.parent().prev().children('input[name=listType]').val();
                console.log(mailNo);
                console.log(listType);
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
            // 읽음 상태 변경 처리
            $('i[class*=fa-envelope]').click(function(){
                //ajax처리 필요.
                var $this = $(this);
                var readYN = '';
                var mailNo = $this.parent().prev().prev().children('input[name=mail-one-select]').val();
                var listType = $this.parent().prev().prev().children('input[name=listType]').val();
                console.log(mailNo); console.log(listType);
                if($this.hasClass('fa-envelope')){ readYN = 'Y';}
                else{readYN= 'N';}
                
                $.ajax({
                	url : "/changeReadYN.ho",
                	data : {"readYN": readYN, "mailNo":mailNo, "listType":listType},
                	type : "get",
                	success : function(result){
                		if(result){
	                		if(readYN=='Y'){
	                			$this.removeClass('fa-envelope').addClass('fa-envelope-open');		
	                		}else if(readYN=='N'){
	                			$this.removeClass('fa-envelope-open').addClass('fa-envelope');		
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
            	var inputArray = [];
            	var listTypeArr = [];
            	 $('input[name=mail-one-select]:checked').each(function(){
            		 inputArray.push($(this).val());
            		 listTypeArr.push($(this).next().val());
            	 }); 
            	 if(inputArray.length>0){ 
            		 var answer = window.confirm('정말로 삭제하시겠습니까?');
                     if(answer){
		            	  $.ajax({
		            		 url : "/allChange.ho",
		            		 traditional : true,
		            		 data : {"listType": listTypeArr, "mailNoList" : inputArray,"ptype" : 'D'},
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
          //읽음 버튼
            $('#mail_read_btn').click(function(){
            	var inputArray = [];
            	var listTypeArr = [];
            	var $checkedList = $('input[name=mail-one-select]:checked'); 
            	 $checkedList.each(function(){
            		 inputArray.push($(this).val());
            		 listTypeArr.push($(this).next().val());
            	 }); 
            	 if(inputArray.length>0){ 
            	  $.ajax({
            		 url : "/allChange.ho",
            		 traditional : true,
            		 data : {"listType": listTypeArr, "mailNoList" : inputArray, "ptype" : 'R'},
            		 type : "post",
            		 success : function(result){
            			 if(result){
            				 $checkedList.each(function(){
            					 $(this).parent().next().next().children().removeClass('fa-envelope').addClass('fa-envelope-open');	
            					 $(this).prop('checked',false);
            				 });
            				 $('#mail-all-select').prop('checked',false);
            			 }else{
            				 alert('처리 실패');
            			 }
            		 },
            		 error : function(){
            			 alert('처리 에러');
            		 }
            	 });
            	 }else{
            		 alert('대상을 1개 이상 선택해주세요');
            	 }
            	  
            });
            //답장 버튼
            $('#mail_rep_btn').click(function(){
            	var checked = $('input[name=mail-one-select]:checked');
            	if(checked.length==1){
                    var memNo = checked.parent().next().next().next().next().children('.mail-sender').attr('memno');
                    var memName = checked.parent().next().next().next().next().children('.mail-sender').text();
                    location.href="/sendToOne.ho?memNo="+memNo+"&memName="+memName;
            	}else if(checked.length==0){
            		alert('답장할 대상을 선택하세요');
            	}else{
            		alert('대상은 한 명만 선택하세요');
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
        });
    </script>
</body>
</html>