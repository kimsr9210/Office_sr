<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>

	<!-- 폰트 어썸 CDN -->
	<script src="https://use.fontawesome.com/releases/v5.8.2/js/all.js"></script>
	<!-- 제이쿼리 CDN -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>

	<!-- 관리자 페이지 공통 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/style.css" />

</head>
<body>

	<!--JSTL core Tag 사용 선언  -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<!-- JSTL format Tag 사용 선언 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div id="wrap">
		<%@ include file ="../adminForm/header.jsp" %>
		
		<div id="contentsBox">
			<%@ include file ="../adminForm/sideNaviBar.jsp" %>
			
			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						삭제 조회
						<c:choose>
							<c:when test="${empty searchCount}">
								<span>결재안 ( ${countApproval}개 )</span>
							</c:when>
							<c:otherwise>
								<span>결재안 ( ${searchCount}개 )</span>
							</c:otherwise>
						</c:choose>
    				</div>
    				<div id="TitleContents">
    					<div class="searchStyle selectStyle">
    						<form class="formStyle" action="adminSearchApproval.ho" method="get">
	    						<select class="optionStyle" name="searchType">
	    							<option value="docuNo">문서번호</option>
	    							<option value="docuType">결재 양식</option>
	    							<option value="memNo">기안자</option>
	    						</select>
								<input type="text" name="keyword"/>
								<button><i class="fas fa-search"></i></button>
							</form>
						</div>
    				
						<table id="approvalList" class="tblStyle">
                                <tr>
                                    <th><input type="checkbox" name="checkApproval" value="all"/></th>
                                    <th>문서번호</th>
                                    <th>결재 양식</th>
                                    <th>제목</th>
                                    <th>기안자</th>
                                    <th>기안일</th>
                                </tr>
                                <c:forEach items="${list}" var="li" varStatus="status">
	                                <tr>
	                                    <td><input type="checkbox" name="checkBoard" value="checkApproval" value="${li.docuNo}"/></td>
	                                    <td>${li.docuNo}</td>
	                                    <td>${li.docuType}</td>
	                                    <td>${li.title}</td>
	                                    <td>${li.memNo}</td>
	                                    <td><fmt:formatDate value="${li.docuDate}" type="both" pattern="yyyy년 MM월 dd일"/></td>
	                                </tr>
                                </c:forEach>
                    	</table>
                    	<center><div id="page-navi">${pageNavi }</center>
    					<div class="buttonSet buttonStyle">
                        	<button class="agreeButtonType">복원</button>
                            <button class="refuseButtonType">삭제</button>
                        </div>
    				</div>
				</div>
			</div> 
		</div>
	</div>
	
<script type='text/javascript'>
	
	$(function(){

		//checkbox
		var checkApproval = [];//체크한 사번 넣을 곳			
		$('input[name=checkApproval]').click(function(){//checkbox 클릭하면
				
			var countApproval = "<c:out value='${countApproval}'/>"
			var searchCount = "<c:out value='${searchCount}'/>"
				
			if($(this).is(':checked')){ 
				if($(this).val()=='all'){//th의 전체 checkbox 체크
					$('input[name=checkApproval]').prop('checked',true);//td의 전체 checkbox 체크
					checkApproval = [];//비우기
					$('input:checkbox[name=Approval]:checked').each(function(){
						checkApproval.push($(this).val());//배열에  전체 사번 넣어주기
					})						
				} else { //td의 일부 checkbox 체크	
					checkApproval.push($(this).val());//배열에 사번 넣어주기
	
					if(searchCount=="") {
						if(countApproval==($('input:checkbox[name=checkApproval]:checked')).length){
							$('input[name=checkApproval][value=all]').prop('checked',true);
						}
					} else {
						if(searchCount==($('input:checkbox[name=checkApproval]:checked')).length){
							$('input[name=checkApproval][value=all]').prop('checked',true);
						}
					}
				}
			} else {
				if($(this).val()=='all'){//th의 전체 checkbox 체크가 아니라면
					$('input[name=checkApproval]').prop('checked',false);//td의 전체 checkbox 체크 해제
					checkApproval = [];//비우기
				} else {//td의 체크가 아니라면
					$('input[name=checkApproval][value=all]').prop('checked',false);
					checkApproval.splice(checkApproval.indexOf($(this).val()),1);
				}
			}
		});
	
		//복원 로직
		$('#TitleContents').children(':nth-child(4)').children(':nth-child(1)').click(function(){
			if(checkApproval.length==0){
				alert('복원할 게시글을 선택해주세요.');
			} else {
				if(checkApproval[0]=='all'){
					checkApproval.splice(checkApproval.indexOf(checkApproval[0]),1);
				}
				if(confirm('해당 게시글을 복원하시겠습니까?')){
					$.ajax({
						url: '/adminDeleteApprovalCancel.ho',
						data : {'appNoList':checkApproval},
						type : 'post',
						success : function(result){
							alert('게시글 복원을 성공하였습니다.');
							history.go(0);
						},
						error : function(){
							alert('게시글 복원을 실패하였습니다.');
						}
					});
				}
			}
		});
		
		//삭제 로직
		$('#TitleContents').children(':nth-child(4)').children(':nth-child(2)').click(function(){
			if(checkApproval.length==0){
				alert('삭제할 게시글을 선택해주세요.');
			} else {
				if(checkApproval[0]=='all'){
					checkApproval.splice(checkApproval.indexOf(checkApproval[0]),1);
				}
				if(confirm('해당 게시글을 영구 삭제하시겠습니까?')){
					$.ajax({
						url: '/adminDeleteApproval.ho',
						data : {'appNoList':checkApproval},
						type : 'post',
						success : function(result){
							alert('게시글을 영구 삭제하였습니다.');
							history.go(0);
						},
						error : function(){
							alert('게시글의 영구 삭제를 실패하였습니다.');
						}
					});
				}
			}
		});
	});
	
	var $deleteList = $('#deleteList');			
	var $deleteSelect = $('#deleteSelect');
		
	/* 삭제 관리일 때 */
    $deleteSelect.children().eq(2).children().attr('class','iArrow fas fa-angle-left');
    $deleteList.css('height','110px');
            
    $deleteSelect.removeClass('hoverColor').addClass('click');
	$deleteList.removeClass('accordion');
	$deleteList.addClass('click');
	$deleteList.children().eq(2).children().removeClass('hoverColor');	
				
	</script>
</body>
</html>