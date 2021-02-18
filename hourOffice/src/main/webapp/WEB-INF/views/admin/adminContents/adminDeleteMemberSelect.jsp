<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<style>
/*페이지 네비 css*/
#page-navi {
    height: 30px;
    margin-top: 50px;
    margin-bottom: 50px;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.page-list {
    width: 30px;
    height: 30px;
    border: 1px solid darkgray;
    text-align: center;
}
.page-link {
    width: 100%;
    height: 100%;
    line-height: 28px;
    display: inline-block;
}

.page-link:hover{
    background: #eaeaea;
}
</style>

</head>
<body>
	<!--JSTL core Tag 사용 선언  -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
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
								<span>사원 조회 ( ${countAll}명 )</span>
							</c:when>
							<c:otherwise>
								<span>사원 조회 ( ${searchCount}명 )</span>
							</c:otherwise>
						</c:choose>
    				</div>
    				<div id="TitleContents">
    					<div class="searchStyle selectStyle">
    						<form class="formStyle" action="/adminSearchDeleteMember.ho" method="get">
	    						<select class="optionStyle" name="searchType">
	    							<option value="memNo">사번</option>
	    							<option value="memName">이름</option>
	    							<option value="memPosition">직위</option>
	    							<option value="deptName">부서</option>
	    						</select>		
								<input type="text" name="keyword"/>
								<button><i class="fas fa-search"></i></button>
							</form>
						</div>
    				
						<table id="memberList" class="tblStyle">
                                <tr>
                                    <th><input type="checkbox" name="checkMem" value="all"/></th>
                                    <th>사번</th>
                                    <th>이름</th>
                                    <th>직위</th>
                                    <th>부서</th>
                                    <th>이메일</th>
                                    <th>전화번호</th>
                                    <th>퇴사일</th>
                                </tr>
                             	<c:forEach items="${list}" var="li" varStatus="status">
		                                <tr>
		                                    <td><input type="checkbox" name="checkMem" value="${li.memNo}"/></td>
		                                    <td>${li.memNo}</td>
		                                    <td>${li.memName}</td>
		                                    <td>${li.memPosition}</td>
		                                    <td>${li.deptName}</td>
		                                    <td>${li.memEmail}</td>
		                                    <td>${li.memPhone}</td>
		                                    <td><fmt:formatDate value="${li.memResignDate}" type="both" pattern="yyyy년 MM월 dd일"/></td>
		                                </tr>
                                </c:forEach>
                    	</table>
						<center><ul id="page-navi">${pageNavi }</ul></center>    					
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
			var checkMem = [];//체크한 사번 넣을 곳			
			$('input[name=checkMem]').click(function(){//checkbox 클릭하면
				
				var countAll = "<c:out value='${countAll}'/>"
				var searchCount = "<c:out value='${searchCount}'/>"
				
				if($(this).is(':checked')){ 
					if($(this).val()=='all'){//th의 전체 checkbox 체크
						$('input[name=checkMem]').prop('checked',true);//td의 전체 checkbox 체크
						checkMem = [];//비우기
						$('input:checkbox[name=checkMem]:checked').each(function(){
							checkMem.push($(this).val());//배열에  전체 사번 넣어주기
						})						
					} else { //td의 일부 checkbox 체크	
						checkMem.push($(this).val());//배열에 사번 넣어주기

						if(searchCount=="") {
							if(countAll==($('input:checkbox[name=checkMem]:checked')).length){
								$('input[name=checkMem][value=all]').prop('checked',true);
							}
						} else {
							if(searchCount==($('input:checkbox[name=checkMem]:checked')).length){
								$('input[name=checkMem][value=all]').prop('checked',true);
							}
						}
					}
				} else {
					if($(this).val()=='all'){//th의 전체 checkbox 체크가 아니라면
						$('input[name=checkMem]').prop('checked',false);//td의 전체 checkbox 체크 해제
						checkMem = [];//비우기
					} else {//td의 체크가 아니라면
						$('input[name=checkMem][value=all]').prop('checked',false);
						checkMem.splice(checkMem.indexOf($(this).val()),1);
					}
				}
			});
			
			//복원 로직
			$('#TitleContents').children(':nth-child(4)').children(':nth-child(1)').click(function(){
				if(checkMem.length==0){
					alert('복원할 사원을 선택해주세요.');
				} else {
					if(checkMem[0]=='all'){
						checkMem.splice(checkMem.indexOf(checkMem[0]),1);
					}
					if(confirm('해당 사원을 복원하시겠습니까?')){
						$.ajax({
							url: '/adminDeleteMemberCancel.ho',
							data : {'memNoList':checkMem},
							type : 'post',
							success : function(result){
								alert(memName+'의 사원 복원을 성공하였습니다.');
								history.go(0);
							},
							error : function(){
								alert('사원 복원을 실패하였습니다.');
							}
						});
					}
				}
			});
			
			//삭제 로직
			$('#TitleContents').children(':nth-child(4)').children(':nth-child(2)').click(function(){
				if(checkMem.length==0){
					alert('삭제할 사원을 선택해주세요.');
				} else {
					if(checkMem[0]=='all'){
						checkMem.splice(checkMem.indexOf(checkMem[0]),1);
					}
					if(confirm('해당 사원을 영구 삭제하시겠습니까?')){
						$.ajax({
							url: '/adminDeleteMember.ho',
							data : {'memNoList':checkMem},
							type : 'post',
							success : function(result){
								alert('사원을 영구 삭제하였습니다.');
								history.go(0);
							},
							error : function(){
								alert('사원의 영구 삭제를 실패하였습니다.');
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
			$deleteList.children().eq(0).children(":first").removeClass('hoverColor');
	</script>
</body>
</html>