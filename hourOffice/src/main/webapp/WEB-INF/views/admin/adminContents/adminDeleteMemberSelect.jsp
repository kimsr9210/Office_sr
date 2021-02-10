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
						<span>사원 조회 ( ${countAll} )</span>
    				</div>
    				<div id="TitleContents">
    					<div class="searchStyle selectStyle">
    						<form action="#" method="get">
	    						<select class="optionStyle">
	    							<option value="memNo">사번</option>
	    							<option value="memName">이름</option>
	    							<option value="memPosition">직위</option>
	    							<option value="deptName">부서</option>
	    						</select>
    						</form>
							<input type="text" name="#"/>
							<button><i class="fas fa-search"></i></button>
						</div>
    				
						<table id="memberList" class="tblStyle">
                                <tr>
                                    <th><input type="checkbox"/></th>
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
		                                    <td><input type="checkbox"/></td>
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
						<center><div id="pageNavi">${pageNavi }</div></center>    					
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
		$(document).ready(function(){
			var $deleteList = $('#deleteList');			
			var $deleteSelect = $('#deleteSelect');
			
			 /* 삭제 관리일 때 */
			$deleteSelect.children().eq(2).children().attr('class','iArrow fas fa-angle-left');
            $deleteList.css('height','110px');
            			
			$deleteSelect.removeClass('hoverColor').addClass('click');
			$deleteList.removeClass('accordion');
			$deleteList.addClass('click');
			$deleteList.children().eq(0).children(":first").removeClass('hoverColor');
            $deleteSelect.children().eq(2).children().attr('class','iArrow fas fa-angle-left');
		});
			
		$(function(){
			// 사원 조회 checkbox 전부 누르기		
			$('#memberList').find('input').first().click(function(){
							
				if($(this).prop('checked')) {
					$('#memberList input').each(function(){
						$(this).prop('checked',true);
					});
				} else {
					$('#memberList input').each(function(){
						$(this).prop('checked',false);
					});
				}	
			});
		});	
	</script>
</body>
</html>